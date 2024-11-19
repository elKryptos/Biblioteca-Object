package it.objectmethod.biblioteca.controllers.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.objectmethod.biblioteca.exceptions.NotFoundException;
import it.objectmethod.biblioteca.utils.JwtToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)// filtro che si occupa di autentiocazione
@RequiredArgsConstructor
public class TokenFilter implements Filter {

    private final JwtToken jwtToken;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        if ("/auth/login".equals(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if ("/persona/all".equals(path) || "/persona".equals(path) || "/utente".equals(path) || "/persona/create".equals(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            try {
                Jws<Claims> claimsJws = jwtToken.allClaimsJws(token);
                if (claimsJws == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");
                    return;
                }
                request.setAttribute("claims", claimsJws);
            } catch (NotFoundException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token mancante");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
