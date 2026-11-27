package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import facade.ClinicaFacade;
import modelo.Agendamento;
import modelo.Medico;
import modelo.Paciente;
import modelo.TipoAtendimento;

public class Demonstracao {

	public static void executar(ClinicaFacade clinica) {
		clinica.reiniciarBase();
		DadosIniciais.carregar(clinica);

		Paciente maria = clinica.listarPacientes().get(0);
		Paciente joao = clinica.listarPacientes().get(1);
		Paciente beatriz = clinica.listarPacientes().get(2);
		Medico carlos = clinica.listarMedicos().get(0);
		Medico ana = clinica.listarMedicos().get(1);
		LocalDate amanha = LocalDate.now().plusDays(1);
		LocalTime nove = LocalTime.of(9, 0);

		passo("1) Médicos de Cardiologia disponíveis amanhã às 09:00");
		System.out.println("   " + clinica.medicosDisponiveis("Cardiologia", amanha, nove, 30));

		passo("2) Maria agenda consulta com " + carlos.getNomeMedico());
		Agendamento consulta = clinica.agendar(maria, carlos, TipoAtendimento.CONSULTA, amanha, nove, 30,
				"Dor no peito ao esforço");
		System.out.println("   " + consulta);

		passo("3) Conflito: João tenta o mesmo médico às 09:15");
		tentar(new Acao() {
			public void run() {
				clinica.agendar(joao, carlos, TipoAtendimento.CONSULTA, amanha, LocalTime.of(9, 15), 30, "Check-up");
			}
		});

		passo("4) Cardiologia continua disponível às 09:00?");
		System.out.println("   " + clinica.medicosDisponiveis("Cardiologia", amanha, nove, 30));

		passo("5) João agenda um exame (HEMOGRAMA) com " + ana.getNomeMedico());
		Agendamento exame = clinica.agendar(joao, ana, TipoAtendimento.EXAME, amanha, LocalTime.of(10, 0), 20,
				"HEMOGRAMA");
		System.out.println("   " + exame);

		passo("6) State: realizar consulta SEM confirmar deve falhar");
		tentar(new Acao() {
			public void run() {
				clinica.realizarConsulta(consulta.getId(), "Angina estável", null);
			}
		});

		passo("7) Confirmar e realizar a consulta (com receita) - Observers entram em ação");
		clinica.confirmar(consulta.getId());
		clinica.realizarConsulta(consulta.getId(), "Angina estável",
				Arrays.asList("AAS 100mg - 1 comprimido ao dia", "Atenolol 50mg - 1 comprimido pela manhã"));
		System.out.println("   " + consulta);

		passo("8) Confirmar e realizar o exame - resultado vem do laboratório via Adapter");
		clinica.confirmar(exame.getId());
		clinica.realizarExame(exame.getId(), "Valores dentro da normalidade.");

		passo("9) " + carlos.getNomeMedico() + " acessa o prontuário de Maria");
		System.out.println(clinica.acessarProntuario(carlos, maria));

		passo("10) Regra de acesso: " + carlos.getNomeMedico() + " tenta ler o prontuário de Beatriz (sem vínculo)");
		tentar(new Acao() {
			public void run() {
				clinica.acessarProntuario(carlos, beatriz);
			}
		});

		passo("11) Prontuário de João (resultado do exame)");
		System.out.println(clinica.acessarProntuario(ana, joao));

		passo("12) Cancelar um agendamento libera o horário");
		Agendamento extra = clinica.agendar(beatriz, carlos, TipoAtendimento.CONSULTA, amanha, LocalTime.of(14, 0), 30,
				"Retorno");
		clinica.cancelar(extra.getId());
		System.out.println("   " + extra);
		System.out.println("   Carlos disponível às 14:00? " + carlos.estaDisponivel(amanha, LocalTime.of(14, 0), 30));
		System.out.println("\nDemonstração concluída.");
	}

	private interface Acao {
		void run();
	}

	private static void passo(String titulo) {
		System.out.println("\n>>> " + titulo);
	}

	private static void tentar(Acao acao) {
		try {
			acao.run();
			System.out.println("   (nenhum erro - inesperado)");
		} catch (RuntimeException e) {
			System.out.println("   Bloqueado, como esperado: " + e.getMessage());
		}
	}
}
