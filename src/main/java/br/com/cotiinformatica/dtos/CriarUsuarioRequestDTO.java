package br.com.cotiinformatica.dtos;

import lombok.Data;

@Data
public class CriarUsuarioRequestDTO {

	private String nome;
	private String email;
	private String senha;
}
