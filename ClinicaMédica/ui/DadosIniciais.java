package ui;

import facade.ClinicaFacade;

public final class DadosIniciais {
	private DadosIniciais() {
	}

	public static void carregar(ClinicaFacade clinica) {
		clinica.cadastrarMedico("Dr. Carlos Mendes", "Cardiologia", 101);
		clinica.cadastrarMedico("Dra. Ana Souza", "Clínica Geral", 102);
		clinica.cadastrarMedico("Dr. Paulo Lima", "Ortopedia", 103);

		clinica.cadastrarPaciente("Maria Oliveira", "529.982.247-25", "(71) 99999-1111", "Unimed");
		clinica.cadastrarPaciente("João Santos", "111.444.777-35", "(71) 98888-2222", "Particular");
		clinica.cadastrarPaciente("Beatriz Costa", "390.533.447-05", "(71) 97777-3333", "Bradesco Saúde");
	}
}
