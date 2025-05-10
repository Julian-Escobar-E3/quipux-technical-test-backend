package com.example.quipux_technical_test.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter implements Filter {
    private final JwtUtil jwtUtil;
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response); // Continuar sin autenticación
            return;
        }
        try {
            String token = authHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);

            if (jwtUtil.isTokenValid(token, username)) {
                var auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
            return;
        }
        chain.doFilter(request, response);
    }
}