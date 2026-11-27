package facade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import adapter.LaboratorioExterno;
import adapter.LaboratorioExternoAdapter;
import banco.BancoDeDados;
import factory.AtendimentoFactory;
import factory.ConsultaFactory;
import factory.ExameFactory;
import modelo.Agendamento;
import modelo.Atendimento;
import modelo.Consulta;
import modelo.Medico;
import modelo.Paciente;
import modelo.Prontuario;
import modelo.TipoAtendimento;

public class ClinicaFacade {
	private final BancoDeDados banco = BancoDeDados.getInstance();
	private final Map<TipoAtendimento, AtendimentoFactory> fabricas = new EnumMap<TipoAtendimento, AtendimentoFactory>(
			TipoAtendimento.class);

	public ClinicaFacade() {
		fabricas.put(TipoAtendimento.CONSULTA, new ConsultaFactory());
		fabricas.put(TipoAtendimento.EXAME, new ExameFactory(new LaboratorioExternoAdapter(new LaboratorioExterno())));
	}


	public Paciente cadastrarPaciente(String nome, String cpf, String telefone, String convenio) {
		Paciente paciente = new Paciente(nome, cpf, telefone, convenio, banco.proximoNumeroProntuario());
		banco.salvarPaciente(paciente);
		return paciente;
	}

	public Medico cadastrarMedico(String nome, String especialidade, int sala) {
		Medico medico = new Medico(nome, especialidade, sala);
		banco.salvarMedico(medico);
		return medico;
	}


	public List<Medico> medicosDisponiveis(String especialidade, LocalDate data, LocalTime horario, int duracaoMin) {
		List<Medico> livres = new ArrayList<Medico>();
		for (Medico m : banco.listarMedicos()) {
			boolean especialidadeOk = especialidade == null || especialidade.trim().isEmpty()
					|| m.getEspecialidadeMedico().equalsIgnoreCase(especialidade.trim());
			if (especialidadeOk && m.estaDisponivel(data, horario, duracaoMin)) {
				livres.add(m);
			}
		}
		return livres;
	}

	public Agendamento agendar(Paciente paciente, Medico medico, TipoAtendimento tipo, LocalDate data,
			LocalTime horario, int duracaoMin, String descricao) {
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new IllegalArgumentException(tipo == TipoAtendimento.CONSULTA ? "Informe o motivo da consulta."
					: "Informe o tipo de exame.");
		}
		if (LocalDateTime.of(data, horario).isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Não é possível agendar no passado.");
		}
		if (!medico.estaDisponivel(data, horario, duracaoMin)) { // checa antes de consumir um ID
			throw new IllegalStateException(medico.getNomeMedico() + " não está disponível em " + data + " às "
					+ horario + ".");
		}
		Agendamento agendamento = new Agendamento(banco.proximoIdAgendamento(), data, horario, duracaoMin, paciente,
				medico, tipo);
		medico.reservar(agendamento); // lança exceção se houver conflito de horário
		banco.salvarAgendamento(agendamento);
		banco.salvarAtendimento(fabricas.get(tipo).criar(agendamento, descricao.trim()));
		return agendamento;
	}

	public void confirmar(int idAgendamento) {
		exigirAgendamento(idAgendamento).confirmar();
	}

	public void cancelar(int idAgendamento) {
		exigirAgendamento(idAgendamento).cancelar();
	}


	public Atendimento realizarConsulta(int idAgendamento, String diagnostico, List<String> prescricoes) {
		Atendimento atendimento = exigirAtendimento(idAgendamento, TipoAtendimento.CONSULTA);
		((Consulta) atendimento).realizar(diagnostico, prescricoes);
		return atendimento;
	}

	public Atendimento realizarExame(int idAgendamento, String observacaoMedico) {
		Atendimento atendimento = exigirAtendimento(idAgendamento, TipoAtendimento.EXAME);
		atendimento.realizar(observacaoMedico);
		return atendimento;
	}


	public Prontuario acessarProntuario(Medico medico, Paciente paciente) {
		return medico.acessarProntuario(paciente);
	}


	public List<Paciente> listarPacientes() {
		return banco.listarPacientes();
	}

	public List<Medico> listarMedicos() {
		return banco.listarMedicos();
	}

	public List<Agendamento> listarAgendamentos() {
		return banco.listarAgendamentos();
	}

	public Agendamento buscarAgendamento(int id) {
		return banco.buscarAgendamento(id);
	}

	public Atendimento buscarAtendimento(int idAgendamento) {
		return banco.buscarAtendimento(idAgendamento);
	}

	public void reiniciarBase() {
		banco.limpar();
	}

	private Agendamento exigirAgendamento(int id) {
		Agendamento a = banco.buscarAgendamento(id);
		if (a == null) {
			throw new IllegalArgumentException("Agendamento #" + id + " não encontrado.");
		}
		return a;
	}

	private Atendimento exigirAtendimento(int id, TipoAtendimento esperado) {
		exigirAgendamento(id);
		Atendimento a = banco.buscarAtendimento(id);
		if (a == null || a.getTipo() != esperado) {
			throw new IllegalArgumentException("O agendamento #" + id + " não é do tipo " + esperado.getRotulo() + ".");
		}
		return a;
	}
}
