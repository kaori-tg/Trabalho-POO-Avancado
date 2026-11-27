package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prontuario {
	private final int numeroProntuario;
	private String diagnostico = "Sem diagnóstico registrado";
	private String alergias = "Nenhuma informada";
	private String tipoSangue = "Não informado";
	private final List<RegistroProntuario> historico = new ArrayList<RegistroProntuario>();
	private final List<Receita> receitas = new ArrayList<Receita>();

	public Prontuario(int numeroProntuario) {
		this.numeroProntuario = numeroProntuario;
	}

	public void registrar(RegistroProntuario registro) {
		historico.add(registro);
	}

	public void adicionarReceita(Receita receita) {
		receitas.add(receita);
	}

	public void atualizarDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public int getNumeroProntuario() {
		return numeroProntuario;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getTipoSangue() {
		return tipoSangue;
	}

	public void setTipoSangue(String tipoSangue) {
		this.tipoSangue = tipoSangue;
	}

	public List<RegistroProntuario> getHistorico() {
		return Collections.unmodifiableList(historico);
	}

	public List<Receita> getReceitas() {
		return Collections.unmodifiableList(receitas);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== PRONTUÁRIO Nº ").append(numeroProntuario).append(" ===\n");
		sb.append("Tipo sanguíneo : ").append(tipoSangue).append("\n");
		sb.append("Alergias       : ").append(alergias).append("\n");
		sb.append("Último diagnóstico: ").append(diagnostico).append("\n");
		sb.append("--- Histórico (").append(historico.size()).append(") ---\n");
		if (historico.isEmpty()) {
			sb.append("  (vazio)\n");
		}
		for (RegistroProntuario r : historico) {
			sb.append("  ").append(r).append("\n");
		}
		sb.append("--- Receitas (").append(receitas.size()).append(") ---\n");
		if (receitas.isEmpty()) {
			sb.append("  (nenhuma)\n");
		}
		for (Receita r : receitas) {
			sb.append("  ").append(r).append("\n");
		}
		return sb.toString();
	}
}
