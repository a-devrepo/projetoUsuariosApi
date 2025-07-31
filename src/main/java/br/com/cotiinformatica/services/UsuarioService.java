package br.com.cotiinformatica.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.CryptoComponent;
import br.com.cotiinformatica.components.JwtBearerComponent;
import br.com.cotiinformatica.components.RabbitMQProducerComponent;
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
	private JwtBearerComponent jwtBearerComponent;
	private RabbitMQProducerComponent rabbitMQProducerComponent;
	
	public UsuarioService(UsuarioRepository usuarioRepository,CryptoComponent cryptoComponent,
			JwtBearerComponent jwtBearerComponent,
			RabbitMQProducerComponent rabbitMQProducerComponent) {
		this.usuarioRepository = usuarioRepository;
		this.cryptoComponent = cryptoComponent;
		this.jwtBearerComponent = jwtBearerComponent;
		this.rabbitMQProducerComponent = rabbitMQProducerComponent;
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
		rabbitMQProducerComponent.send(responseDTO);
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
		responseDTO.setDataHoraExpiracao(jwtBearerComponent
				.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		responseDTO.setToken(jwtBearerComponent.getToken(usuario.getId())); 

		return responseDTO;
	}
}