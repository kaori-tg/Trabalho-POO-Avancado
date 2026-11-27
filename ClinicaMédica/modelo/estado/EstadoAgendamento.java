package modelo.estado;

import modelo.Agendamento;

public interface EstadoAgendamento {
	String getNome();

	boolean isAtivo();

	void confirmar(Agendamento agendamento);

	void cancelar(Agendamento agendamento);

	void realizar(Agendamento agendamento);
}
