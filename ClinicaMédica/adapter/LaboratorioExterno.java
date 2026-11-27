package adapter;

public class LaboratorioExterno {
	public String executarAnalise(String codigoAmostra) {
		if (codigoAmostra.contains("HEMOGRAMA")) {
			return "HEMOGLOBINA=13.8 g/dL;LEUCOCITOS=7200 /mm3;PLAQUETAS=250000 /mm3";
		}
		if (codigoAmostra.contains("GLICEMIA")) {
			return "GLICOSE_JEJUM=92 mg/dL";
		}
		if (codigoAmostra.contains("COLESTEROL")) {
			return "COLESTEROL_TOTAL=185 mg/dL;HDL=52 mg/dL;LDL=110 mg/dL";
		}
		return "RESULTADO=SEM_ALTERACOES";
	}
}
