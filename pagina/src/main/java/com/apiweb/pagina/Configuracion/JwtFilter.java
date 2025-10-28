package com.apiweb.pagina.Configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.apiweb.pagina.Servicio.JwtService;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;  // Servicio para validar y extraer info del JWT (debes implementarlo)

    @Autowired
    private UserDetailsService userDetailsService;  // Servicio para cargar usuario por username

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el header Authorization
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Verifica que el header contenga Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extrae token (sin "Bearer ")
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (Exception e) {
                logger.error("Error al extraer username del JWT: " + e.getMessage());
            }
        }

        // Si hay username y no está autenticado aún
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Valida el token
            if (jwtService.validarToken(jwtToken)) {
                // Crea objeto Authentication para el contexto de seguridad
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
