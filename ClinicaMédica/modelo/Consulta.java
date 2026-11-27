package modelo;

import java.util.List;

public class Consulta extends Atendimento {
	private String diagnostico;
	private Receita receita;

	public Consulta(Agendamento agendamento, String queixa) {
		super(agendamento, queixa);
	}

	public void prescrever(String item) {
		if (isFinalizado()) {
			throw new IllegalStateException("Consulta já finalizada; não é possível prescrever.");
		}
		if (receita == null) {
			receita = new Receita(getMedico(), getPaciente());
		}
		receita.adicionarItem(item);
	}

	public void realizar(String diagnostico, List<String> prescricoes) {
		if (prescricoes != null) {
			for (String p : prescricoes) {
				prescrever(p);
			}
		}
		realizar(diagnostico);
	}

	@Override
	protected void validarEntrada(String diagnostico) {
		if (diagnostico == null || diagnostico.trim().isEmpty()) {
			throw new IllegalArgumentException("O diagnóstico é obrigatório para finalizar a consulta.");
		}
	}

	@Override
	protected String produzirResultado(String diagnostico) {
		this.diagnostico = diagnostico.trim();
		return "Queixa: " + getDescricao() + "\nDiagnóstico: " + this.diagnostico;
	}

	@Override
	public void aplicarAoProntuario(Prontuario prontuario) {
		super.aplicarAoProntuario(prontuario);
		prontuario.atualizarDiagnostico(diagnostico);
		if (receita != null) {
			prontuario.adicionarReceita(receita);
		}
	}

	@Override
	public TipoAtendimento getTipo() {
		return TipoAtendimento.CONSULTA;
	}

	public Receita getReceita() {
		return receita;
	}
}
