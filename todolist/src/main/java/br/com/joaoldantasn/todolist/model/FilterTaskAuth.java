package br.com.joaoldantasn.todolist.model;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.joaoldantasn.todolist.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
	
	@Autowired
	private UserRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Pegar a autenticacao (usuario e senha)
		var authorization = request.getHeader("Authorization");
		
		var authEncoded = authorization.substring("Basic".length()).trim();
		
		byte[] authDecoded =  Base64.getDecoder().decode(authEncoded);
		
		var authString = new String(authDecoded);
		
		System.out.println("Authorization");
		System.out.println(authString);
		
		String[] credentials = authString.split(":");
		String username = credentials[0];
		String password = credentials[1];
		
		//Validar se ele existe
		var user = this.repository.findByUsername(username);
		
		if(user == null) {
			response.sendError(401, "Usuário sem autorização");
		}else {
			// validar senha
			var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
			if(passwordVerify.verified) {
				filterChain.doFilter(request, response);
			}
			else {
				response.sendError(401);
			}
		}
		
		
	}


}
