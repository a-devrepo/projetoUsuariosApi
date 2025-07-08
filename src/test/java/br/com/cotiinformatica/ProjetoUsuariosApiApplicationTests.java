package br.com.cotiinformatica;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class ProjetoUsuariosApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	private static String emailAcesso;
	private static String senhaAcesso;

	@Test
	@Order(1)
	@DisplayName("Deve criar um usuário com sucesso.")
	void deveCriarUsuarioComSucesso() {

		try {
			var faker = new Faker();

			var request = new CriarUsuarioRequestDTO();
			request.setNome(faker.name().fullName());
			request.setEmail(faker.internet().emailAddress());
			request.setSenha("Teste@2025");

			var result = mockMvc.perform(post("/api/v1/usuarios").contentType("application/json")
					.content(objectMapper.writeValueAsString(request)));

			result.andExpect(status().isOk());

			emailAcesso = request.getEmail();
			senhaAcesso = request.getSenha();
		} catch (Exception e) {
			fail("Erro:" + e.getMessage());
		}
	}

	@Test
	@Order(2)
	@DisplayName("Deve autenticar um usuário com sucesso.")
	void deveAutenticarUsuarioComSucesso() {
		try {

			var request = new AutenticarUsuarioRequestDTO();
			request.setEmail(emailAcesso);
			request.setSenha(senhaAcesso);

			var result = mockMvc.perform(post("/api/v1/usuario/autenticar").contentType("application/json")
					.content(objectMapper.writeValueAsString(request)));

			result.andExpect(status().isOk());
		} catch (Exception e) {
			fail("Erro: " + e.getMessage());
		}
	}

	@Test
	@Order(3)
	@DisplayName("Não deve cadastrar usuários com o mesmo email")
	void naoDeveCadastrarUsuarioComMesmoEmail() {
		try {

			var faker = new Faker();

			var request = new CriarUsuarioRequestDTO();
			request.setNome(faker.name().fullName());
			request.setEmail(emailAcesso);
			request.setSenha(senhaAcesso);

			var result = mockMvc.perform(post("/api/v1/usuario/criar").contentType("application/json")
					.content(objectMapper.writeValueAsString(request)));

			result.andExpect(status().isBadRequest());

			var response = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

			assertTrue(response.contains("O email informado já está cadastrado. Tente outro."));
		} catch (Exception e) {
			fail("Erro: " + e.getMessage());
		}
	}

	@Test
	@Order(4)
	@DisplayName("Não deve permitir autenticar um usuário inválido")
	void naoDevePermitirAutenticarUsuarioInvalido() {
		try {

			var request = new AutenticarUsuarioRequestDTO();
			request.setEmail("teste@teste.com");
			request.setSenha("@Teste123");

			var result = mockMvc.perform(post("/api/v1/usuario/autenticar").contentType("application/json")
					.content(objectMapper.writeValueAsString(request)));

			result.andExpect(status().isUnauthorized());

			var response = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

			assertTrue(response.contains("Acesso negado. Verifique as credenciais informadas."));
		} catch (Exception e) {
			fail("Erro: " + e.getMessage());
		}
	}

	@Test
	@Order(5)
	@DisplayName("Não deve permitir cadastrar usuário com senha fraca")
	void naoDevePermitirCadastrarUsuarioComSenhaFraca() {
		try {

			var faker = new Faker();

			var request = new CriarUsuarioRequestDTO();
			request.setNome(faker.name().fullName());
			request.setEmail(faker.internet().emailAddress());
			request.setSenha("teste1234");

			var result = mockMvc.perform(post("/api/v1/usuario/criar").contentType("application/json")
					.content(objectMapper.writeValueAsString(request)));

			result.andExpect(status().isBadRequest());

			var response = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

			assertTrue(response.contains(
					"A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."));
		} catch (Exception e) {
			fail("Erro: " + e.getMessage());
		}
	}
}
