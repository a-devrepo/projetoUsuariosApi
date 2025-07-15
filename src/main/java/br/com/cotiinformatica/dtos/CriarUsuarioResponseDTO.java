package br.com.cotiinformatica.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CriarUsuarioResponseDTO {

	private UUID id;
	private String nome;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataHoraCriacao;
}
