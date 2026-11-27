package modelo.estado;

import modelo.Agendamento;

public abstract class EstadoBase implements EstadoAgendamento {

	@Override
	public boolean isAtivo() {
		return true;
	}

	@Override
	public void confirmar(Agendamento agendamento) {
		recusar("confirmar");
	}

	@Override
	public void cancelar(Agendamento agendamento) {
		recusar("cancelar");
	}

	@Override
	public void realizar(Agendamento agendamento) {
		recusar("realizar");
	}

	private void recusar(String acao) {
		throw new IllegalStateException("Não é possível " + acao + " um agendamento no estado " + getNome() + ".");
	}
}
