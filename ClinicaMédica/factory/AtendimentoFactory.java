package factory;

import modelo.Agendamento;
import modelo.Atendimento;
import observer.NotificacaoPacienteObserver;
import observer.ProntuarioObserver;

public abstract class AtendimentoFactory {

	protected abstract Atendimento criarAtendimento(Agendamento agendamento, String descricao);

	public final Atendimento criar(Agendamento agendamento, String descricao) {
		Atendimento atendimento = criarAtendimento(agendamento, descricao);
		atendimento.adicionarObservador(new ProntuarioObserver());
		atendimento.adicionarObservador(new NotificacaoPacienteObserver());
		return atendimento;
	}
}
