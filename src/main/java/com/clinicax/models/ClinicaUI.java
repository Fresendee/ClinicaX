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
    private JTextField txtPesquisa;
    private JButton btnPesquisar;
    private JButton btnAtualizar;
    private List<Agenda> agendas;

    public ClinicaUI(List<Agenda> agendas) {
        this.agendas = agendas;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("CLINICAX - Sistema da Clínica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

   
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(52, 152, 219));
        painelTopo.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitulo = new JLabel("CLINICAX");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo);
        frame.add(painelTopo, BorderLayout.NORTH);

        JPanel painelControle = new JPanel();
        painelControle.setLayout(new FlowLayout());

        JLabel lblMedico = new JLabel("Selecione o médico:");
        painelControle.add(lblMedico);

        comboMedicos = new JComboBox<>();
        for (Agenda a : agendas) {
            comboMedicos.addItem(a.getMedico());
        }
        painelControle.add(comboMedicos);

        btnAtualizar = new JButton("Atualizar Agenda");
        btnAtualizar.setBackground(new Color(46, 204, 113));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.addActionListener(e -> atualizarTabela());
        painelControle.add(btnAtualizar);

        txtPesquisa = new JTextField(10);
        painelControle.add(txtPesquisa);

        btnPesquisar = new JButton("Pesquisar CPF");
        btnPesquisar.setBackground(new Color(241, 196, 15));
        btnPesquisar.setForeground(Color.BLACK);
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.addActionListener(e -> pesquisarPaciente());
        painelControle.add(btnPesquisar);

        frame.add(painelControle, BorderLayout.SOUTH);

        
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Idade");
        modeloTabela.addColumn("Telefone");

        tabelaPacientes = new JTable(modeloTabela);
        tabelaPacientes.setFillsViewportHeight(true);

       
        tabelaPacientes.getTableHeader().setBackground(new Color(52, 73, 94));
        tabelaPacientes.getTableHeader().setForeground(Color.WHITE);
        tabelaPacientes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

      
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
