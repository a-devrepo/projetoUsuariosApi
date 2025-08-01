package br.com.cotiinformatica.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponseDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Controle de usuários", description = "Serviços para gerenciamento de usuários.")
public class UsuariosController {

	private UsuarioService usuarioService;

	public UsuariosController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	@Operation(
		    summary = "Cadastro de usuários",
		    description = "Cadastra um novo usuário no sistema"
		)
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
		    description = "Dados do usuário a ser cadastrado",
		    required = true,
		    content = @Content(
		        mediaType = "application/json",
		        schema = @Schema(implementation = CriarUsuarioResponseDTO.class)
		    )
		)
	public CriarUsuarioResponseDTO criar(@RequestBody @Valid CriarUsuarioRequestDTO requestDTO) {

		return usuarioService.criarUsuario(requestDTO);
	}
	
	@PostMapping("/autenticar")
	@Operation(summary = "Autenticar usuário", description = "Retorna as informações do usuário autenticado")
	public AutenticarUsuarioResponseDTO autenticar(@RequestBody @Valid AutenticarUsuarioRequestDTO requestDTO) {

		return usuarioService.autenticarUsuario(requestDTO);
	}
}
