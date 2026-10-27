package com.servicehub.servicehub_api.security;

import com.servicehub.servicehub_api.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Extrai o cabeçalho "Authorization" da requisição
        final String authHeader = request.getHeader("Authorization");

        // 2. Se não tiver cabeçalho ou não começar com "Bearer ", ignora e passa pro próximo filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (tira os primeiros 7 caracteres correspondentes a "Bearer ")
        final String token = authHeader.substring(7);
        final String username = jwtService.extractUsername(token);

        // 4. Se encontrou um e-mail no token e o usuário ainda não está autenticado no contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busca o usuário no banco usando o CustomUserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Valida se o token pertence ao usuário e não está expirado
            if (jwtService.isTokenValid(token, userDetails)) {

                // Cria o objeto de autenticação que o Spring Security exige
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Salva o usuário no contexto da aplicação
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}