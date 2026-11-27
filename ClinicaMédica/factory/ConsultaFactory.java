package factory;

import modelo.Agendamento;
import modelo.Atendimento;
import modelo.Consulta;

public class ConsultaFactory extends AtendimentoFactory {
	@Override
	protected Atendimento criarAtendimento(Agendamento agendamento, String queixa) {
		return new Consulta(agendamento, queixa);
	}
}
