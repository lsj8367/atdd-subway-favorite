package atdd.path.application;

import atdd.path.application.dto.CreateUserRequestView;
import atdd.path.application.exception.InvalidUserException;
import atdd.path.domain.entity.User;
import atdd.path.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public User createUser(CreateUserRequestView view) {
        User user = view.toUSer();
        user.encryptPassword();

        return userRepository.save(user);
    }

    public void deleteUser(final long id) {
        userRepository.deleteById(id);
    }

    public String login(final String email, final String password) {
        User findUser;
        try {
            findUser = userRepository.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new InvalidUserException();
        }

        findUser.checkPassword(password);

        return jwtUtils.createToken(findUser.getEmail());
    }

    public User myInfo(final String accessToken) {
        Claims claims = jwtUtils.tokenClaims(accessToken);
        String email = claims.get("email").toString();

        return userRepository.findByEmail(email);
    }
}