package banco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelo.Agendamento;
import modelo.Atendimento;
import modelo.Medico;
import modelo.Paciente;

public final class BancoDeDados {

	private static class Holder {
		private static final BancoDeDados INSTANCIA = new BancoDeDados();
	}

	public static BancoDeDados getInstance() {
		return Holder.INSTANCIA;
	}

	private final List<Paciente> pacientes = new ArrayList<Paciente>();
	private final List<Medico> medicos = new ArrayList<Medico>();
	private final List<Agendamento> agendamentos = new ArrayList<Agendamento>();
	private final Map<Integer, Atendimento> atendimentos = new LinkedHashMap<Integer, Atendimento>();
	private int sequenciaProntuario = 1000;
	private int sequenciaAgendamento = 0;

	private BancoDeDados() {
	}


	public void salvarPaciente(Paciente paciente) {
		if (buscarPacientePorCpf(paciente.getCpfPaciente()) != null) {
			throw new IllegalStateException("Já existe paciente com o CPF " + paciente.getCpfFormatado() + ".");
		}
		pacientes.add(paciente);
	}

	public Paciente buscarPacientePorCpf(String cpf) {
		for (Paciente p : pacientes) {
			if (p.getCpfPaciente().equals(cpf)) {
				return p;
			}
		}
		return null;
	}

	public List<Paciente> listarPacientes() {
		return Collections.unmodifiableList(pacientes);
	}


	public void salvarMedico(Medico medico) {
		medicos.add(medico);
	}

	public Medico buscarMedicoPorNome(String nome) {
		for (Medico m : medicos) {
			if (m.getNomeMedico().equalsIgnoreCase(nome)) {
				return m;
			}
		}
		return null;
	}

	public List<Medico> listarMedicos() {
		return Collections.unmodifiableList(medicos);
	}


	public void salvarAgendamento(Agendamento agendamento) {
		agendamentos.add(agendamento);
	}

	public Agendamento buscarAgendamento(int id) {
		for (Agendamento a : agendamentos) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	public List<Agendamento> listarAgendamentos() {
		return Collections.unmodifiableList(agendamentos);
	}

	public void salvarAtendimento(Atendimento atendimento) {
		atendimentos.put(atendimento.getAgendamento().getId(), atendimento);
	}

	public Atendimento buscarAtendimento(int idAgendamento) {
		return atendimentos.get(idAgendamento);
	}


	public synchronized int proximoNumeroProntuario() {
		return ++sequenciaProntuario;
	}

	public synchronized int proximoIdAgendamento() {
		return ++sequenciaAgendamento;
	}


	public synchronized void limpar() {
		pacientes.clear();
		medicos.clear();
		agendamentos.clear();
		atendimentos.clear();
		sequenciaProntuario = 1000;
		sequenciaAgendamento = 0;
	}
}
