package modelo;

public enum TipoAtendimento {
	CONSULTA("Consulta"), EXAME("Exame");

	private final String rotulo;

	TipoAtendimento(String rotulo) {
		this.rotulo = rotulo;
	}

	public String getRotulo() {
		return rotulo;
	}
}
