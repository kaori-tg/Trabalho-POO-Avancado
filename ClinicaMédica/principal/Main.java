package principal;

import facade.ClinicaFacade;
import ui.DadosIniciais;
import ui.Demonstracao;
import ui.MenuConsole;

public class Main {
	public static void main(String[] args) {
		ClinicaFacade clinica = new ClinicaFacade();
		if (args.length > 0 && "--demo".equals(args[0])) {
			Demonstracao.executar(clinica);
			return;
		}
		DadosIniciais.carregar(clinica);
		new MenuConsole(clinica).iniciar();
	}
}
