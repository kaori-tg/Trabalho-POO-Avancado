package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import observer.AtendimentoObserver;

public abstract class Atendimento {
	private final Agendamento agendamento;
	private final String descricao;
	private String resultado;
	private boolean finalizado;
	private final List<AtendimentoObserver> observadores = new ArrayList<AtendimentoObserver>();

	protected Atendimento(Agendamento agendamento, String descricao) {
		this.agendamento = agendamento;
		this.descricao = descricao;
	}

	public void adicionarObservador(AtendimentoObserver observador) {
		observadores.add(observador);
	}

	public final void realizar(String entradaMedico) {
		if (finalizado) {
			throw new IllegalStateException("Atendimento já finalizado.");
		}
		validarEntrada(entradaMedico);
		agendamento.realizar(); // State: lança exceção se não estiver CONFIRMADO
		this.resultado = produzirResultado(entradaMedico);
		this.finalizado = true;
		for (AtendimentoObserver o : observadores) {
			o.aoFinalizar(this);
		}
	}

	protected void validarEntrada(String entradaMedico) {
		// por padrão, qualquer entrada é aceita
	}

	protected abstract String produzirResultado(String entradaMedico);

	public abstract TipoAtendimento getTipo();

	
	public void aplicarAoProntuario(Prontuario prontuario) {
		prontuario.registrar(new RegistroProntuario(LocalDateTime.now(), getTipo(), getMedico().getNomeMedico(),
				resultado));
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public Paciente getPaciente() {
		return agendamento.getPaciente();
	}

	public Medico getMedico() {
		return agendamento.getMedico();
	}

	public String getDescricao() {
		return descricao;
	}

	public String getResultado() {
		return resultado;
	}

	public boolean isFinalizado() {
		return finalizado;
	}
}
