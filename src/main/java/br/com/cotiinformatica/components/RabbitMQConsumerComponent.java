package br.com.cotiinformatica.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;

@Component
public class RabbitMQConsumerComponent {

	private JavaMailSender javaMailSender;
	private ObjectMapper objectMapper;

	public RabbitMQConsumerComponent(JavaMailSender javaMailSender, ObjectMapper objectMapper) {
		this.javaMailSender = javaMailSender;
		this.objectMapper = objectMapper;
	}

	@RabbitListener(queues = "usuarios")
	public void consume(@Payload String usuario) {
		try {
			var registro = objectMapper.readValue(usuario, CriarUsuarioResponseDTO.class);
			var message = javaMailSender.createMimeMessage();
			var helper = new MimeMessageHelper(message);

			helper.setTo(registro.getEmail());
			helper.setSubject("Olá " + registro.getNome() + "! Bem-vindo ao sistema!");

			String corpoEmail = """
					    <h2>Parabéns! Sua conta foi criada com sucesso.</h2>
					    <p>Você pode acessar o sistema utilizando o email: <strong>%s</strong> e a senha cadastrada.</p>
					    <p>Seja bem-vindo e aproveite as funcionalidades da nossa aplicação!</p>
					""".formatted(registro.getEmail());

			helper.setText(corpoEmail, true);

			javaMailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("Failed to consume message from RabbitMQ", e);
		}
	}
}
