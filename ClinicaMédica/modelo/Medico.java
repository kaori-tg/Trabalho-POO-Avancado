package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Medico {
	private String nomeMedico;
	private String especialidadeMedico;
	private int sala;
	private final List<Agendamento> agenda = new ArrayList<Agendamento>();

	public Medico(String nomeMedico, String especialidadeMedico, int sala) {
		if (nomeMedico == null || nomeMedico.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome do médico é obrigatório.");
		}
		this.nomeMedico = nomeMedico.trim();
		this.especialidadeMedico = especialidadeMedico;
		this.sala = sala;
	}

	public boolean estaDisponivel(LocalDate data, LocalTime horario, int duracaoMin) {
		for (Agendamento a : agenda) {
			if (a.isAtivo() && a.conflitaCom(data, horario, duracaoMin)) {
				return false;
			}
		}
		return true;
	}

	public void reservar(Agendamento agendamento) {
		if (!estaDisponivel(agendamento.getData(), agendamento.getHorario(), agendamento.getDuracaoMin())) {
			throw new IllegalStateException(
					nomeMedico + " não está disponível em " + agendamento.getData() + " às " + agendamento.getHorario() + ".");
		}
		agenda.add(agendamento);
	}

	public boolean possuiVinculoCom(Paciente paciente) {
		for (Agendamento a : agenda) {
			if (a.isAtivo() && a.getPaciente() == paciente) {
				return true;
			}
		}
		return false;
	}

	public Prontuario acessarProntuario(Paciente paciente) {
		if (!possuiVinculoCom(paciente)) {
			throw new SecurityException("Acesso negado: " + nomeMedico + " não possui atendimento com "
					+ paciente.getNomePaciente() + ".");
		}
		return paciente.getProntuario();
	}

	public List<Agendamento> getAgenda() {
		return Collections.unmodifiableList(agenda);
	}

	public String getNomeMedico() {
		return nomeMedico;
	}

	public String getEspecialidadeMedico() {
		return especialidadeMedico;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	@Override
	public String toString() {
		return nomeMedico + " - " + especialidadeMedico + " (sala " + sala + ")";
	}
}
