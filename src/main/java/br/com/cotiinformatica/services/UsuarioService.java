package br.com.cotiinformatica.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public CriarUsuarioResponseDTO criarUsuario(CriarUsuarioRequestDTO requestDTO) {
		
		if (usuarioRepository.existsByEmail(requestDTO.getEmail())) {
			throw new IllegalArgumentException("O E-mail informado já está cadastrado.");
		}
		
		var usuario = new Usuario();
		usuario.setNome(requestDTO.getNome());
		usuario.setEmail(requestDTO.getEmail());
		usuario.setSenha(requestDTO.getSenha());
		
		usuarioRepository.save(usuario);
		
		var responseDTO = new CriarUsuarioResponseDTO();
		responseDTO.setId(usuario.getId());
		responseDTO.setNome(usuario.getNome());
		responseDTO.setEmail(usuario.getEmail());
		responseDTO.setDataHoraCriacao(LocalDateTime.now());
		return responseDTO;
	}
}