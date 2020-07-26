package nextstep.subway.auth.ui.interceptor.authorization;

import nextstep.subway.auth.application.SecurityContextPersistenceHandler;
import nextstep.subway.auth.infrastructure.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityContextPersistenceInterceptor implements HandlerInterceptor {

    protected final SecurityContextPersistenceHandler persistenceHandler;

    public SecurityContextPersistenceInterceptor(SecurityContextPersistenceHandler persistenceHandler) {
        this.persistenceHandler = persistenceHandler;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        SecurityContextHolder.clearContext();
    }
}