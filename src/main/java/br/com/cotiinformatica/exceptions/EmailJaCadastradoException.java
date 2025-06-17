package br.com.cotiinformatica.exceptions;

@SuppressWarnings("serial")
public class EmailJaCadastradoException extends RuntimeException {

	@Override
	public String getMessage() {
		return "O email informado já está cadastrado.";
	}
}