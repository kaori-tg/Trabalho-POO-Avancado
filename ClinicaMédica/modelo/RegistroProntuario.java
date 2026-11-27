package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroProntuario {
	private final LocalDateTime dataHora;
	private final TipoAtendimento tipo;
	private final String nomeMedico;
	private final String descricao;

	public RegistroProntuario(LocalDateTime dataHora, TipoAtendimento tipo, String nomeMedico, String descricao) {
		this.dataHora = dataHora;
		this.tipo = tipo;
		this.nomeMedico = nomeMedico;
		this.descricao = descricao;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public TipoAtendimento getTipo() {
		return tipo;
	}

	public String getNomeMedico() {
		return nomeMedico;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return "[" + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "] " + tipo.getRotulo()
				+ " com " + nomeMedico + "\n      " + descricao.replace("\n", "\n      ");
	}
}
