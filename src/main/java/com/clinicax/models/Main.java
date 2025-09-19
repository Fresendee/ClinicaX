package com.clinicax.models;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Medico m1 = new Medico("Olival", "0001", "Oftalmologista");
        Medico m2 = new Medico("Gabriela", "0002", "Ginecologista");
        Medico m3 = new Medico("Carlos", "0003", "Cardiologista");
        Medico m4 = new Medico("Ana", "0004", "Pediatra");

        Paciente p1 = new Paciente("Pedro", "111", 30, "9999-1111");
        Paciente p2 = new Paciente("Karol", "222", 25, "9999-2222");
        Paciente p3 = new Paciente("Marcela", "333", 28, "9999-3333");
        Paciente p4 = new Paciente("Roberto", "444", 34, "9999-4444");
        Paciente p5 = new Paciente("Lucas", "555", 40, "9999-5555");
        Paciente p6 = new Paciente("Fernanda", "666", 32, "9999-6666");

        Agenda a1 = new Agenda(m1);
        a1.adcionar(p1); a1.adcionar(p2);

        Agenda a2 = new Agenda(m2);
        a2.adcionar(p3); a2.adcionar(p4);

        Agenda a3 = new Agenda(m3);
        a3.adcionar(p5);

        Agenda a4 = new Agenda(m4);
        a4.adcionar(p6);

        ClinicaUI.abrirUI(List.of(a1, a2, a3, a4));
    }
} 
