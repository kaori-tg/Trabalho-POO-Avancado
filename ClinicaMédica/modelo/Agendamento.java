package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import modelo.estado.Agendado;
import modelo.estado.EstadoAgendamento;

public class Agendamento {
	private final int id;
	private final LocalDate data;
	private final LocalTime horario;
	private final int duracaoMin;
	private final Paciente paciente;
	private final Medico medico;
	private final TipoAtendimento tipo;
	private EstadoAgendamento estado = new Agendado();

	public Agendamento(int id, LocalDate data, LocalTime horario, int duracaoMin, Paciente paciente, Medico medico,
			TipoAtendimento tipo) {
		if (data == null || horario == null || paciente == null || medico == null || tipo == null) {
			throw new IllegalArgumentException("Dados obrigatórios do agendamento não informados.");
		}
		if (duracaoMin <= 0) {
			throw new IllegalArgumentException("Duração deve ser maior que zero.");
		}
		this.id = id;
		this.data = data;
		this.horario = horario;
		this.duracaoMin = duracaoMin;
		this.paciente = paciente;
		this.medico = medico;
		this.tipo = tipo;
	}

	public void confirmar() {
		estado.confirmar(this);
	}

	public void cancelar() {
		estado.cancelar(this);
	}

	public void realizar() {
		estado.realizar(this);
	}

	public boolean isAtivo() {
		return estado.isAtivo();
	}

	public void mudarEstado(EstadoAgendamento novoEstado) {
		this.estado = novoEstado;
	}

	public boolean conflitaCom(LocalDate outraData, LocalTime outroHorario, int outraDuracao) {
		LocalDateTime ini = LocalDateTime.of(data, horario);
		LocalDateTime fim = ini.plusMinutes(duracaoMin);
		LocalDateTime outroIni = LocalDateTime.of(outraData, outroHorario);
		LocalDateTime outroFim = outroIni.plusMinutes(outraDuracao);
		return ini.isBefore(outroFim) && outroIni.isBefore(fim);
	}

	public int getId() {
		return id;
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public int getDuracaoMin() {
		return duracaoMin;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public TipoAtendimento getTipo() {
		return tipo;
	}

	public EstadoAgendamento getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "#" + id + " [" + estado.getNome() + "] " + tipo.getRotulo() + " - " + data + " " + horario + " ("
				+ duracaoMin + " min) | Paciente: " + paciente.getNomePaciente() + " | Médico: "
				+ medico.getNomeMedico();
	}
}
