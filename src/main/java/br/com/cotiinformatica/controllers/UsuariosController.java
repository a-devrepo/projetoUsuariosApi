package br.com.cotiinformatica.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;
import br.com.cotiinformatica.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuariosController {

	private UsuarioService usuarioService;

	public UsuariosController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public CriarUsuarioResponseDTO criar(@RequestBody @Valid CriarUsuarioRequestDTO requestDTO) {

		return usuarioService.criarUsuario(requestDTO);
	}
}
