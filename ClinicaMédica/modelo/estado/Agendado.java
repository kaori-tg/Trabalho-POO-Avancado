package modelo.estado;

import modelo.Agendamento;

public class Agendado extends EstadoBase {
	@Override
	public String getNome() {
		return "AGENDADO";
	}

	@Override
	public void confirmar(Agendamento a) {
		a.mudarEstado(new Confirmado());
	}

	@Override
	public void cancelar(Agendamento a) {
		a.mudarEstado(new Cancelado());
	}
}
