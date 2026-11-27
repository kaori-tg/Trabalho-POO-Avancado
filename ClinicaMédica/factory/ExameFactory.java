package factory;

import adapter.ProvedorResultadoExame;
import modelo.Agendamento;
import modelo.Atendimento;
import modelo.Exame;

public class ExameFactory extends AtendimentoFactory {
	private final ProvedorResultadoExame provedor;

	public ExameFactory(ProvedorResultadoExame provedor) {
		this.provedor = provedor;
	}

	@Override
	protected Atendimento criarAtendimento(Agendamento agendamento, String tipoExame) {
		return new Exame(agendamento, tipoExame, provedor);
	}
}
