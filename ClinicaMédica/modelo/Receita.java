package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receita {
	private final LocalDate data = LocalDate.now();
	private final Medico medico;
	private final Paciente paciente;
	private final List<String> itens = new ArrayList<String>();

	public Receita(Medico medico, Paciente paciente) {
		this.medico = medico;
		this.paciente = paciente;
	}

	public void adicionarItem(String item) {
		if (item == null || item.trim().isEmpty()) {
			throw new IllegalArgumentException("Item da receita não pode ser vazio.");
		}
		itens.add(item.trim());
	}

	public List<String> getItens() {
		return Collections.unmodifiableList(itens);
	}

	public Medico getMedico() {
		return medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	@Override
	public String toString() {
		return "Receita de " + data + " (" + medico.getNomeMedico() + "): " + String.join("; ", itens);
	}
}
