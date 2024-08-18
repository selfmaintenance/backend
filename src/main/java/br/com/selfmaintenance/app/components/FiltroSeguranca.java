package br.com.selfmaintenance.app.components;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.selfmaintenance.app.services.TokenService;
import br.com.selfmaintenance.repositories.usuario.UsuarioAutenticavelRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Comment("Filtro de segurança para autenticação de usuários")
@Component
public class FiltroSeguranca extends OncePerRequestFilter {
	private final TokenService tokenService;
	private final UsuarioAutenticavelRepository usuarioRepository;

	public FiltroSeguranca(TokenService tokenService, UsuarioAutenticavelRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		var token = this.recuperarToken(request);
		if (token != null) {
				String login = this.tokenService.validarToken(token);
				UserDetails usuario = this.usuarioRepository.findByEmail(login);
				var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		var token = request.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ") || token.startsWith("Bearer null")) {
				return null;
		}
		return token.substring(7);
	}
}
