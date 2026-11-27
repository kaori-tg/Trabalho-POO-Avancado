package modelo.estado;

public class Cancelado extends EstadoBase {
	@Override
	public String getNome() {
		return "CANCELADO";
	}

	@Override
	public boolean isAtivo() {
		return false;
	}
}
