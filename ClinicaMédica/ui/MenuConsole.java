package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import facade.ClinicaFacade;
import modelo.Agendamento;
import modelo.Atendimento;
import modelo.Medico;
import modelo.Paciente;
import modelo.TipoAtendimento;

public class MenuConsole {
	private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

	private final ClinicaFacade clinica;
	private final Scanner in = new Scanner(System.in);

	public MenuConsole(ClinicaFacade clinica) {
		this.clinica = clinica;
	}

	public void iniciar() {
		boolean rodando = true;
		while (rodando) {
			System.out.println("\n===== CLÍNICA MÉDICA =====");
			System.out.println("1) Cadastrar paciente");
			System.out.println("2) Cadastrar médico");
			System.out.println("3) Agendar consulta/exame");
			System.out.println("4) Confirmar agendamento");
			System.out.println("5) Realizar atendimento (registrar resultado)");
			System.out.println("6) Cancelar agendamento");
			System.out.println("7) Médico acessa prontuário de paciente");
			System.out.println("8) Listar agendamentos");
			System.out.println("9) Executar demonstração automática (reinicia a base!)");
			System.out.println("0) Sair");
			String opcao = ler("Opção");
			try {
				switch (opcao) {
				case "1":
					cadastrarPaciente();
					break;
				case "2":
					cadastrarMedico();
					break;
				case "3":
					agendar();
					break;
				case "4":
					clinica.confirmar(lerInt("ID do agendamento"));
					System.out.println("Agendamento confirmado.");
					break;
				case "5":
					realizar();
					break;
				case "6":
					clinica.cancelar(lerInt("ID do agendamento"));
					System.out.println("Agendamento cancelado.");
					break;
				case "7":
					acessarProntuario();
					break;
				case "8":
					listarAgendamentos();
					break;
				case "9":
					Demonstracao.executar(clinica);
					break;
				case "0":
					rodando = false;
					break;
				default:
					System.out.println("Opção inválida.");
				}
			} catch (RuntimeException e) {
				System.out.println("ERRO: " + e.getMessage());
			}
		}
		System.out.println("Até logo!");
	}

	private void cadastrarPaciente() {
		Paciente p = clinica.cadastrarPaciente(ler("Nome"), ler("CPF"), ler("Telefone"),
				ler("Convênio (vazio = Particular)"));
		System.out.println("Cadastrado: " + p + " | Prontuário nº " + p.getProntuario().getNumeroProntuario());
	}

	private void cadastrarMedico() {
		Medico m = clinica.cadastrarMedico(ler("Nome"), ler("Especialidade"), lerInt("Sala"));
		System.out.println("Cadastrado: " + m);
	}

	private void agendar() {
		Paciente paciente = escolher(clinica.listarPacientes(), "paciente");
		TipoAtendimento tipo = "2".equals(ler("Tipo (1 = Consulta, 2 = Exame)")) ? TipoAtendimento.EXAME
				: TipoAtendimento.CONSULTA;
		LocalDate data = LocalDate.parse(ler("Data (dd/MM/aaaa)"), DATA);
		LocalTime hora = LocalTime.parse(ler("Hora (HH:mm)"), HORA);
		int duracao = lerInt("Duração em minutos");
		String especialidade = ler("Filtrar por especialidade (vazio = todas)");

		List<Medico> livres = clinica.medicosDisponiveis(especialidade, data, hora, duracao);
		if (livres.isEmpty()) {
			System.out.println("Nenhum médico disponível nesse horário.");
			return;
		}
		Medico medico = escolher(livres, "médico disponível");
		String descricao = ler(tipo == TipoAtendimento.CONSULTA ? "Motivo da consulta"
				: "Tipo de exame (ex.: HEMOGRAMA, GLICEMIA, COLESTEROL)");
		Agendamento a = clinica.agendar(paciente, medico, tipo, data, hora, duracao, descricao);
		System.out.println("Agendado: " + a);
	}

	private void realizar() {
		int id = lerInt("ID do agendamento");
		Agendamento ag = clinica.buscarAgendamento(id);
		if (ag == null) {
			System.out.println("Agendamento não encontrado.");
			return;
		}
		Atendimento resultado;
		if (ag.getTipo() == TipoAtendimento.CONSULTA) {
			String diagnostico = ler("Diagnóstico");
			List<String> prescricoes = new ArrayList<String>();
			String item;
			while (!(item = ler("Item da receita (vazio para encerrar)")).isEmpty()) {
				prescricoes.add(item);
			}
			resultado = clinica.realizarConsulta(id, diagnostico, prescricoes);
		} else {
			resultado = clinica.realizarExame(id, ler("Observação do médico (opcional)"));
		}
		System.out.println("Resultado registrado:\n" + resultado.getResultado());
	}

	private void acessarProntuario() {
		Medico medico = escolher(clinica.listarMedicos(), "médico");
		Paciente paciente = escolher(clinica.listarPacientes(), "paciente");
		System.out.println(clinica.acessarProntuario(medico, paciente));
	}

	private void listarAgendamentos() {
		if (clinica.listarAgendamentos().isEmpty()) {
			System.out.println("Nenhum agendamento.");
		}
		for (Agendamento a : clinica.listarAgendamentos()) {
			System.out.println(a);
		}
	}

	private <T> T escolher(List<T> opcoes, String rotulo) {
		if (opcoes.isEmpty()) {
			throw new IllegalStateException("Nenhum(a) " + rotulo + " cadastrado(a).");
		}
		for (int i = 0; i < opcoes.size(); i++) {
			System.out.println("  " + (i + 1) + ") " + opcoes.get(i));
		}
		int n = lerInt("Escolha o " + rotulo);
		if (n < 1 || n > opcoes.size()) {
			throw new IllegalArgumentException("Escolha inválida.");
		}
		return opcoes.get(n - 1);
	}

	private String ler(String rotulo) {
		System.out.print(rotulo + ": ");
		return in.hasNextLine() ? in.nextLine().trim() : "0";
	}

	private int lerInt(String rotulo) {
		try {
			return Integer.parseInt(ler(rotulo));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Informe um número válido.");
		}
	}
}
