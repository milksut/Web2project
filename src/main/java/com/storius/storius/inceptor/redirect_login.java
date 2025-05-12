package com.storius.storius.inceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class redirect_login implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(redirect_login.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI();
        // Allow login page and static resources
        if (!(path.startsWith("/login") || path.startsWith("/register")
                || path.startsWith("/css") || path.startsWith("/js")))
        {
            if(session == null || session.getAttribute("user_id") == null)
            {
                logger.info("redirected to login, from: " +path);
                response.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }
}
