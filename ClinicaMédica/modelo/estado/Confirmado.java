package modelo.estado;

import modelo.Agendamento;

public class Confirmado extends EstadoBase {
	@Override
	public String getNome() {
		return "CONFIRMADO";
	}

	@Override
	public void realizar(Agendamento a) {
		a.mudarEstado(new Realizado());
	}

	@Override
	public void cancelar(Agendamento a) {
		a.mudarEstado(new Cancelado());
	}
}
