package br.com.cotiinformatica.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class AutenticarUsuarioResponseDTO {

	private UUID id;
	private String nome;
	private String email;
	private LocalDateTime dataHoraAcesso;
	private String token;
}
