package observer;

import modelo.Atendimento;

public class NotificacaoPacienteObserver implements AtendimentoObserver {
	@Override
	public void aoFinalizar(Atendimento atendimento) {
		System.out.println("  [Notificação] Para " + atendimento.getPaciente().getNomePaciente() + " ("
				+ atendimento.getPaciente().getTelefonePaciente() + "): resultado de "
				+ atendimento.getTipo().getRotulo().toLowerCase() + " disponível.");
	}
}
