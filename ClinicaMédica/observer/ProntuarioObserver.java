package observer;

import modelo.Atendimento;

public class ProntuarioObserver implements AtendimentoObserver {
	@Override
	public void aoFinalizar(Atendimento atendimento) {
		atendimento.aplicarAoProntuario(atendimento.getPaciente().getProntuario());
		System.out.println("  [Prontuário] Resultado de " + atendimento.getTipo().getRotulo().toLowerCase()
				+ " gravado no prontuário nº " + atendimento.getPaciente().getProntuario().getNumeroProntuario() + ".");
	}
}
