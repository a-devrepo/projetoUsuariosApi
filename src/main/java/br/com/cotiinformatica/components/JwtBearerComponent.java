package br.com.cotiinformatica.components;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtBearerComponent {

	@Value("${jwt.secretkey}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private String jwtExpiration;

	public Date getExpiration() {
		var currentDate = new Date();
		return new Date(currentDate.getTime() + Integer.parseInt(jwtExpiration));
	}

	public String getToken(UUID usuarioId) {
		return Jwts.builder()
				.setSubject(usuarioId.toString())
				.setIssuedAt(new Date())
				.setExpiration(getExpiration())
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}
}
