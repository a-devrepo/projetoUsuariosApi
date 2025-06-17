package br.com.cotiinformatica.components;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class CryptoComponent {
	
	public String getSHA256(String value) {
		
		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder hexString = new StringBuilder();
			
			for (byte b : hashBytes) {
				hexString.append(String.format("%02x", b));
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao gerar hash SHA-256", e);
		}
	}
}
