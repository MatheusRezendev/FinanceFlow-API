package org.example.financeflowapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.financeflowapi.domain.model.repository.service.exception.model.Usuario;
import org.example.financeflowapi.domain.model.repository.service.exception.service.UsuarioService;
import org.example.financeflowapi.dto.usuario.titulo.centrodecusto.usuario.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioService usuarioService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));

            if(auth != null && auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(jwtUtil.validateToken(token)) {
            String email = jwtUtil.getUsernameFromToken(token);
            UsuarioResponseDto usuarioDto = usuarioService.obterPorEmail(email);
            Usuario usuario = mapper.map(usuarioDto, Usuario.class);

            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }

        return null;
    }

}
