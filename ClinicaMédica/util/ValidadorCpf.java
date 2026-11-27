package util;

public final class ValidadorCpf {

	private ValidadorCpf() {
	}

	public static String somenteDigitos(String texto) {
		return texto == null ? "" : texto.replaceAll("\\D", "");
	}

	public static boolean valido(String cpf) {
		String d = somenteDigitos(cpf);
		if (d.length() != 11 || d.matches("(\\d)\\1{10}")) {
			return false;
		}
		return digito(d, 9) == d.charAt(9) - '0' && digito(d, 10) == d.charAt(10) - '0';
	}

	private static int digito(String d, int qtd) {
		int soma = 0;
		for (int i = 0; i < qtd; i++) {
			soma += (d.charAt(i) - '0') * (qtd + 1 - i);
		}
		int resto = (soma * 10) % 11;
		return resto == 10 ? 0 : resto;
	}
}
