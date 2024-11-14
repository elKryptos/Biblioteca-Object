package it.objectmethod.biblioteca.controllers.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.objectmethod.biblioteca.utils.JwtToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class TokenFilter implements Filter {

    @Autowired
    private JwtToken jwtToken;

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
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            Jws<Claims> claimsJws = jwtToken.allClaimsJws(token);
            if (claimsJws == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");
                return;
            }
            request.setAttribute("claims", claimsJws);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token mancante");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
