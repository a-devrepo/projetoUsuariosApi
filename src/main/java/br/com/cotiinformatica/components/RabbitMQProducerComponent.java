package br.com.cotiinformatica.components;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;

@Component
public class RabbitMQProducerComponent {

	private RabbitTemplate rabbitTemplate;
	private ObjectMapper objectMapper;
	private Queue queue;

	public RabbitMQProducerComponent(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, Queue queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.queue = queue;
	}
	
	public void send(CriarUsuarioResponseDTO message) {
		try {
			String jsonMessage = objectMapper.writeValueAsString(message);
			rabbitTemplate.convertAndSend(queue.getName(), jsonMessage);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send message to RabbitMQ", e);
		}
	}
}
