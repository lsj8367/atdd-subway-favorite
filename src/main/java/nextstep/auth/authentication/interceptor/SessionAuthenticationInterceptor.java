package nextstep.auth.authentication.interceptor;

import nextstep.auth.application.UserDetailsService;
import nextstep.auth.authentication.converter.SessionAuthenticationConverter;
import nextstep.auth.context.Authentication;
import nextstep.auth.context.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static nextstep.auth.context.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY;

public class SessionAuthenticationInterceptor extends AuthenticationInterceptor {
    public SessionAuthenticationInterceptor(UserDetailsService userDetailService) {
        super(new SessionAuthenticationConverter(), userDetailService);
    }

    @Override
    public void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, new SecurityContext(authentication));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}