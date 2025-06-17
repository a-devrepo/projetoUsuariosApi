package br.com.cotiinformatica.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.CryptoComponent;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.AutenticarUsuarioResponseDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private CryptoComponent cryptoComponent;
	
	public UsuarioService(UsuarioRepository usuarioRepository,CryptoComponent cryptoComponent) {
		this.usuarioRepository = usuarioRepository;
		this.cryptoComponent = cryptoComponent;
	}
	
	public CriarUsuarioResponseDTO criarUsuario(CriarUsuarioRequestDTO requestDTO) {
		
		if (usuarioRepository.existsByEmail(requestDTO.getEmail())) {
			throw new EmailJaCadastradoException();
		}
		
		var usuario = new Usuario();
		usuario.setNome(requestDTO.getNome());
		usuario.setEmail(requestDTO.getEmail());
		usuario.setSenha(cryptoComponent.getSHA256(requestDTO.getSenha()));
		
		usuarioRepository.save(usuario);
		
		var responseDTO = new CriarUsuarioResponseDTO();
		responseDTO.setId(usuario.getId());
		responseDTO.setNome(usuario.getNome());
		responseDTO.setEmail(usuario.getEmail());
		responseDTO.setDataHoraCriacao(LocalDateTime.now());
		return responseDTO;
	}
	
	public AutenticarUsuarioResponseDTO autenticarUsuario(AutenticarUsuarioRequestDTO requestDTO) {

		var usuario = usuarioRepository.find(requestDTO.getEmail(),
				cryptoComponent.getSHA256(requestDTO.getSenha()));

		if (usuario == null) {
			throw new AcessoNegadoException();
		}

		var responseDTO = new AutenticarUsuarioResponseDTO();
		responseDTO.setId(usuario.getId());
		responseDTO.setNome(usuario.getNome());
		responseDTO.setEmail(usuario.getEmail());
		responseDTO.setDataHoraAcesso(LocalDateTime.now());
		responseDTO.setToken("token-gerado-aqui"); 

		return responseDTO;
	}
}