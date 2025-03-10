package com.cybersecurity.progetto_cybersecurity.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static com.cybersecurity.progetto_cybersecurity.utility.Utils.extractIdUserFormCookie;
import static com.cybersecurity.progetto_cybersecurity.utility.Utils.extractJwtFromCookie;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //viene chiamato ogni volta che ua richiesta HTTP passa per il filtro
    //o fa altro che estrarre il token dai cookie e se è valido estrae le inforamzioni da esso e continua con la catena di filtri
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractJwtFromCookie(request);


        if (token != null && jwtUtil.validateToken(token)) {
            String userId= extractIdUserFormCookie(request);
            String password=jwtUtil.extractPassword(token);
            // Se il token è valido, continua con la catena di filtri
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, password, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
