package com.clinicax.models;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicaUI {

    private JFrame frame;
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;
    private JComboBox<Medico> comboMedicos;
    private JTextField txtNome, txtCPF, txtIdade, txtTelefone, txtPesquisa;
    private JButton btnAdicionar, btnRemover, btnPesquisar, btnAtualizar;
    private List<Agenda> agendas;

    public ClinicaUI(List<Agenda> agendas) {
        this.agendas = agendas;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("CLINICAX - Sistema da Clínica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // ===== Topo com logo / nome da clínica =====
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(52, 152, 219));
        JLabel lblTitulo = new JLabel("CLINICAX");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo);
        frame.add(painelTopo, BorderLayout.NORTH);

        // ===== Painel de controle =====
        JPanel painelControle = new JPanel();
        painelControle.setLayout(new GridLayout(2, 1));

        // Linha 1: seleção médico e atualizar
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.add(new JLabel("Selecione o médico:"));
        comboMedicos = new JComboBox<>();
        for (Agenda a : agendas) {
            comboMedicos.addItem(a.getMedico());
        }
        linha1.add(comboMedicos);
        btnAtualizar = new JButton("Atualizar Agenda");
        btnAtualizar.setBackground(new Color(46, 204, 113));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.addActionListener(e -> atualizarTabela());
        linha1.add(btnAtualizar);
        painelControle.add(linha1);

        // Linha 2: campos adicionar / remover / pesquisar
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha2.add(new JLabel("Nome:"));
        txtNome = new JTextField(8);
        linha2.add(txtNome);

        linha2.add(new JLabel("CPF:"));
        txtCPF = new JTextField(6);
        linha2.add(txtCPF);

        linha2.add(new JLabel("Idade:"));
        txtIdade = new JTextField(3);
        linha2.add(txtIdade);

        linha2.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(6);
        linha2.add(txtTelefone);

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(new Color(52, 152, 219));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.addActionListener(e -> adicionarPaciente());
        linha2.add(btnAdicionar);

        btnRemover = new JButton("Remover (CPF)");
        btnRemover.setBackground(new Color(231, 76, 60));
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setFocusPainted(false);
        btnRemover.addActionListener(e -> removerPaciente());
        linha2.add(btnRemover);

        txtPesquisa = new JTextField(6);
        linha2.add(txtPesquisa);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBackground(new Color(241, 196, 15));
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.addActionListener(e -> pesquisarPaciente());
        linha2.add(btnPesquisar);

        painelControle.add(linha2);
        frame.add(painelControle, BorderLayout.SOUTH);

        // ===== Tabela de pacientes =====
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Idade");
        modeloTabela.addColumn("Telefone");

        tabelaPacientes = new JTable(modeloTabela);
        tabelaPacientes.setFillsViewportHeight(true);

        // Cabeçalho estilizado
        tabelaPacientes.getTableHeader().setBackground(new Color(52, 73, 94));
        tabelaPacientes.getTableHeader().setForeground(Color.WHITE);
        tabelaPacientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Linhas alternadas
        tabelaPacientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? new Color(236, 240, 241) : Color.WHITE);
                } else {
                    setBackground(new Color(149, 165, 166));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaPacientes);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        Medico medicoSelecionado = (Medico) comboMedicos.getSelectedItem();
        if (medicoSelecionado == null) return;

        for (Agenda a : agendas) {
            if (a.getMedico().equals(medicoSelecionado)) {
                for (Paciente p : a.getPacientes()) {
                    modeloTabela.addRow(new Object[]{p.getNome(), p.getCpf(), p.getIdade(), p.getTelefone()});
                }
            }
        }
    }

    private void adicionarPaciente() {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCPF.getText().trim();
            int idade = Integer.parseInt(txtIdade.getText().trim());
            String telefone = txtTelefone.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos!");
                return;
            }

            Paciente p = new Paciente(nome, cpf, idade, telefone);
            Agenda agenda = agendas.stream()
                    .filter(a -> a.getMedico().equals(comboMedicos.getSelectedItem()))
                    .findFirst()
                    .orElse(null);
            if (agenda != null) {
                agenda.adcionar(p);
                atualizarTabela();
                JOptionPane.showMessageDialog(frame, "Paciente adicionado com sucesso!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Idade deve ser um número!");
        }
    }

    private void removerPaciente() {
        String cpf = txtCPF.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Digite o CPF do paciente a remover!");
            return;
        }

        Agenda agenda = agendas.stream()
                .filter(a -> a.getMedico().equals(comboMedicos.getSelectedItem()))
                .findFirst()
                .orElse(null);

        if (agenda != null) {
            agenda.remover(cpf);
            atualizarTabela();
        }
    }

    private void pesquisarPaciente() {
        String cpf = txtPesquisa.getText().trim();
        if (cpf.isEmpty()) return;

        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            if (modeloTabela.getValueAt(i, 1).equals(cpf)) {
                tabelaPacientes.setRowSelectionInterval(i, i);
                tabelaPacientes.scrollRectToVisible(tabelaPacientes.getCellRect(i, 0, true));
                JOptionPane.showMessageDialog(frame, "Paciente encontrado: " + modeloTabela.getValueAt(i, 0));
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "CPF não encontrado!");
    }

    public static void abrirUI(List<Agenda> agendas) {
        SwingUtilities.invokeLater(() -> new ClinicaUI(agendas));
    }
}
