package modelo;

import util.ValidadorCpf;

public class Paciente {
	private String nomePaciente;
	private final String cpfPaciente;
	private String telefonePaciente;
	private String convenio;
	private final Prontuario prontuario;

	public Paciente(String nomePaciente, String cpfPaciente, String telefonePaciente, String convenio,
			int numeroProntuario) {
		if (nomePaciente == null || nomePaciente.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome do paciente é obrigatório.");
		}
		if (!ValidadorCpf.valido(cpfPaciente)) {
			throw new IllegalArgumentException("CPF inválido: " + cpfPaciente);
		}
		this.nomePaciente = nomePaciente.trim();
		this.cpfPaciente = ValidadorCpf.somenteDigitos(cpfPaciente);
		this.telefonePaciente = telefonePaciente;
		this.convenio = (convenio == null || convenio.trim().isEmpty()) ? "Particular" : convenio.trim();
		this.prontuario = new Prontuario(numeroProntuario);
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public String getCpfPaciente() {
		return cpfPaciente;
	}

	public String getCpfFormatado() {
		return cpfPaciente.substring(0, 3) + "." + cpfPaciente.substring(3, 6) + "." + cpfPaciente.substring(6, 9)
				+ "-" + cpfPaciente.substring(9);
	}

	public String getTelefonePaciente() {
		return telefonePaciente;
	}

	public void setTelefonePaciente(String telefonePaciente) {
		this.telefonePaciente = telefonePaciente;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public Prontuario getProntuario() {
		return prontuario;
	}

	@Override
	public String toString() {
		return nomePaciente + " (CPF " + getCpfFormatado() + ", convênio: " + convenio + ")";
	}
}
