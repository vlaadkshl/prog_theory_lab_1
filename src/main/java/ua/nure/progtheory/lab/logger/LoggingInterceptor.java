package ua.nure.progtheory.lab.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggerFacade logger;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.logRequest(request.getMethod(), request.getRequestURI());
        return true;
    }
}