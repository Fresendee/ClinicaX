package com.clinicax.models;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Medico olival = new Medico("Olival", "12345", "Oftalmologista");
        Medico gabriela = new Medico("Gabriela", "67890", "Ginecologista");
        
        Paciente p1 = new Paciente("Pedro", "000", 30, "0000");
        Paciente p2 = new Paciente("Karol", "111", 25, "1111");
        Paciente p3 = new Paciente("Marcela", "222", 28, "2222");
        Paciente p4 = new Paciente("Roberto", "333", 34, "3333");

        Agenda agenda1 = new Agenda(olival);
        agenda1.setData("22/10/2030");
        agenda1.adcionar(p1);
        agenda1.adcionar(p4);

        Agenda agenda2 = new Agenda(gabriela);
        agenda2.setData("25/10/2030");
        agenda2.adcionar(p2);
        agenda2.adcionar(p3);

        List<Agenda> agendas = new ArrayList<>();
        agendas.add(agenda1);
        agendas.add(agenda2);

        ClinicaUI.abrirUI(agendas); 
    }
}
