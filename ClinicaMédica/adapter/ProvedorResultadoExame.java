package adapter;

import modelo.Paciente;

public interface ProvedorResultadoExame {
	String obterResultado(String tipoExame, Paciente paciente);
}
