package it.objectmethod.biblioteca.controllers.filter;

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



            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                    jwtToken.verifyToken(token);

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valido");

            }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
