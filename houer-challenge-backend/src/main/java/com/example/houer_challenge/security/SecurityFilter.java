package com.example.houer_challenge.security;

import com.example.houer_challenge.dto.AuthUserDTO;
import com.example.houer_challenge.model.UserInfo;
import com.example.houer_challenge.repository.UserInfoRepository;
import com.example.houer_challenge.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserInfoRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if(login != null){
            UserInfo user = usersRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));

            // Decode the token to get the id and email
            var decodedJWT = tokenService.decodeToken(token);
            Long userId = decodedJWT.getClaim("userId").asLong();
            String username = decodedJWT.getClaim("username").asString();
            String email = decodedJWT.getSubject();
            AuthUserDTO principal = new AuthUserDTO(userId, email);

            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}