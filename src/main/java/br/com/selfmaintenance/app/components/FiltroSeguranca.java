package br.com.selfmaintenance.app.components;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.selfmaintenance.app.services.autenticacao.token.TokenService;
import br.com.selfmaintenance.infra.repositories.usuario.UsuarioAutenticavelRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * [FiltroSeguranca] é o filtro de segurança que intercepta todas as requisições
 * e verifica se o token é válido.
 * 
 * @version 1.0.0
 */
@Component
public class FiltroSeguranca extends OncePerRequestFilter {
	private final TokenService tokenService;
	private final UsuarioAutenticavelRepository usuarioRepository;

	public FiltroSeguranca(TokenService tokenService, UsuarioAutenticavelRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JWTVerificationException, ServletException, IOException {
		try {
			var path = request.getRequestURI();
			boolean rotaPublica = path.equals("/api/auth/login") || path.equals("/api/usuario");

			var token = this.recuperar(request);
			if (token != null) {
				String login = this.tokenService.validar(token);
				UserDetails usuario = this.usuarioRepository.findByEmail(login);
				var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(autenticacao);
			} else if (!rotaPublica) {
				throw new JWTVerificationException("Token inválido");
			}
			filterChain.doFilter(request, response);
		} catch (JWTVerificationException | ServletException | IOException ex ) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"status\":-1,\"mensagem\":\"Token inválido\"}");
		}
	}

	private String recuperar(HttpServletRequest request) {
		var token = request.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ") || token.startsWith("Bearer null")) {
				return null;
		}
		return token.substring(7);
	}
}
