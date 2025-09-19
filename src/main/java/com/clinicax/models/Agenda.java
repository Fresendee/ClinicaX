package com.clinicax.models;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private String data;
    private List<Paciente> pacientes;
    private Medico medico;

    public Agenda(Medico medico) {
        pacientes = new ArrayList<>();
        data = "";
        this.medico = medico;
    }

    public void adicionar(Paciente p) {
        pacientes.add(p);
    }

    public void remover(String cpf) {
        for (int i = pacientes.size() - 1; i >= 0; i--) {
            if (pacientes.get(i).getCpf().equals(cpf)) {
                System.out.println(pacientes.get(i).getNome() + " foi removido com sucesso");
                pacientes.remove(i);
            }
        }
    }

    public void pesquisar(String cpf) {
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(cpf)) {
                System.out.println("Nome: " + p.getNome());
                System.out.println("Telefone: " + p.getTelefone()); // precisa existir no Paciente
                System.out.println("Idade: " + p.getIdade());
                break;
            }
        }
    }
public void setData(String data) {
    this.data = data;
}

public String getData() {
    return data;
}


public Medico getMedico() {
    return medico;
}

public void setMedico(Medico medico) {
    this.medico = medico;
}







}
