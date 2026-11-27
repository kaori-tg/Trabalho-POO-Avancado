package modelo;

import adapter.ProvedorResultadoExame;

public class Exame extends Atendimento {
	private final String tipoExame;
	private final ProvedorResultadoExame provedor; // abstração: Exame não conhece o laboratório concreto

	public Exame(Agendamento agendamento, String tipoExame, ProvedorResultadoExame provedor) {
		super(agendamento, tipoExame);
		this.tipoExame = tipoExame;
		this.provedor = provedor;
	}

	@Override
	protected String produzirResultado(String observacaoMedico) {
		String laudo = provedor.obterResultado(tipoExame, getPaciente());
		String texto = "Exame: " + tipoExame + "\n" + laudo;
		if (observacaoMedico != null && !observacaoMedico.trim().isEmpty()) {
			texto += "\nObservação do médico: " + observacaoMedico.trim();
		}
		return texto;
	}

	@Override
	public TipoAtendimento getTipo() {
		return TipoAtendimento.EXAME;
	}

	public String getTipoExame() {
		return tipoExame;
	}
}
