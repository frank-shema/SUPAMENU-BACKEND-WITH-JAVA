package com.commerce.supamenu.security.jwt;

import com.commerce.supamenu.security.user.UserSecurityDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    private String token = null;
    private String phoneNumber = null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getServletPath();
        if (requestPath.startsWith("/api/v1/auth/login") || requestPath.startsWith("/api/v1/auth/oauth2")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        try{

            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
                phoneNumber = jwtService.extractEmail(token);
            }
        }catch(Exception e){
            filterChain.doFilter(request, response);
            return;
        }

        if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(UserSecurityDetailsService.class).loadUserByUsername(phoneNumber);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken tk = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                System.out.println(userDetails.getAuthorities());
                tk.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(tk);
            }
        }

        filterChain.doFilter(request, response);
    }
}

