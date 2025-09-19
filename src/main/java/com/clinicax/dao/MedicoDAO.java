package com.clinicax.dao;

import com.clinicax.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicoDAO {
    
    public void inserirMedico(String nome, String especialidade) {
        String sql = "INSERT INTO Medico(nome, especialidade) VALUES(?, ?)";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nome);
            pstmt.setString(2, especialidade);
            pstmt.executeUpdate();
            System.out.println("✅ Médico inserido com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir médico: " + e.getMessage());
        }
    }

    public void listarMedicos() {
        String sql = "SELECT * FROM Medico";
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   " | Nome: " + rs.getString("nome") +
                                   " | Especialidade: " + rs.getString("especialidade"));
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar médicos: " + e.getMessage());
        }
    }
}
