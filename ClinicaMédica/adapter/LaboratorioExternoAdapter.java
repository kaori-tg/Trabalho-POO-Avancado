package adapter;

import modelo.Paciente;

public class LaboratorioExternoAdapter implements ProvedorResultadoExame {
	private final LaboratorioExterno laboratorio;

	public LaboratorioExternoAdapter(LaboratorioExterno laboratorio) {
		this.laboratorio = laboratorio;
	}

	@Override
	public String obterResultado(String tipoExame, Paciente paciente) {
		String codigoAmostra = "AMOSTRA-" + tipoExame.trim().toUpperCase() + "-" + paciente.getCpfPaciente();
		String bruto = laboratorio.executarAnalise(codigoAmostra);

		StringBuilder laudo = new StringBuilder();
		for (String campo : bruto.split(";")) {
			String[] par = campo.split("=", 2);
			String nome = par[0].replace('_', ' ');
			if (laudo.length() > 0) {
				laudo.append("\n");
			}
			laudo.append("- ").append(nome).append(": ").append(par.length > 1 ? par[1] : "-");
		}
		return laudo.toString();
	}
}
