/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import model.Database;

/**
 *
 * @author Asus
 */
public class Register extends javax.swing.JFrame {

    /**
     * Creates new form Register
     */
    public Register() {
        initComponents();
        setLocationRelativeTo(null);
        customizeComponents();
    }

    private void customizeComponents() {
        // Set form title
        setTitle("Register - Wellessplate");
        
        // Customize panel background
        jPanel1.setBackground(new Color(0, 41, 107)); // #00296b
        
        // Customize labels
        jLabel1.setForeground(new Color(253, 197, 0)); // #fdc500
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 48));
        
        // Style other labels
        JLabel[] labels = {jLabel2, jLabel3, jLabel4, jLabel5};
        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
        
        // Style "Have an account? Login" label
        jLabel6.setForeground(Color.WHITE);
        jLabel6.setFont(new Font("Segoe UI", Font.BOLD, 12));
        jLabel6.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect and click handler to login label
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Login loginForm = new Login();
                loginForm.setVisible(true);
                dispose();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6.setForeground(new Color(253, 197, 0)); // #fdc500
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6.setForeground(Color.WHITE);
            }
        });
        
        // Style text fields
        JTextField[] fields = {TNama, TUsername};
        for (JTextField field : fields) {
            field.setPreferredSize(new Dimension(250, 30));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 197, 0)), // #fdc500
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
        
        // Style password fields
        JPasswordField[] passwordFields = {TPassword, TPassword2};
        for (JPasswordField field : passwordFields) {
            field.setPreferredSize(new Dimension(250, 30));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(253, 197, 0)), // #fdc500
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
        
        // Style register button
        BRegister.setBackground(new Color(253, 197, 0)); // #fdc500
        BRegister.setForeground(new Color(0, 41, 107)); // #00296b
        BRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        BRegister.setFocusPainted(false);
        BRegister.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        BRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect to register button
        BRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BRegister.setBackground(new Color(255, 213, 0)); // #ffd500
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BRegister.setBackground(new Color(253, 197, 0)); // #fdc500
            }
        });
        
        // Add action listener to register button
        BRegister.addActionListener(evt -> {
            String nama = TNama.getText();
            String username = TUsername.getText();
            String password = new String(((JPasswordField)TPassword).getPassword());
            String confirmPassword = new String(((JPasswordField)TPassword2).getPassword());
            
            // Validate fields
            if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Semua field harus diisi!", 
                    "Peringatan", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "Password dan konfirmasi password tidak sama!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                TPassword.setText("");
                TPassword2.setText("");
                return;
            }
            
            try (Connection conn = Database.koneksiDatabase();
                 PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO user (nama, username, password) VALUES (?, ?, ?)")) {
                
                pstmt.setString(1, nama);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(this, 
                    "Registrasi berhasil! Silakan login.", 
                    "Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Open login form
                Login loginForm = new Login();
                loginForm.setVisible(true);
                this.dispose();
                
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Username sudah digunakan!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                TUsername.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TNama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TUsername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BRegister = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        TPassword = new javax.swing.JPasswordField();
        TPassword2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(540, 720));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setText("REGISTER PAGE");

        jLabel2.setText("Nama");

        TNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNamaActionPerformed(evt);
            }
        });

        jLabel3.setText("Username");

        TUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TUsernameActionPerformed(evt);
            }
        });

        jLabel4.setText("Password");

        jLabel5.setText("Konfirmasi Password");

        BRegister.setText("Register");
        BRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BRegisterActionPerformed(evt);
            }
        });

        jLabel6.setText("Have an account? Login");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TUsername)
                                    .addComponent(TNama, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel6))
                                    .addComponent(TPassword)
                                    .addComponent(TPassword2)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(BRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(TPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(TPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BRegister)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 525, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BRegisterActionPerformed

    private void TNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNamaActionPerformed

    private void TUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TUsernameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    
                    // Customize Nimbus colors to match our theme
                    UIManager.put("nimbusBase", new Color(0, 41, 107)); // #00296b
                    UIManager.put("nimbusBlueGrey", new Color(0, 63, 136)); // #003f88
                    UIManager.put("control", new Color(0, 80, 157)); // #00509d
                    
                    // Set default button colors
                    UIManager.put("Button.background", new Color(253, 197, 0)); // #fdc500
                    UIManager.put("Button.foreground", new Color(0, 41, 107)); // #00296b
                    
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Register().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BRegister;
    private javax.swing.JTextField TNama;
    private javax.swing.JPasswordField TPassword;
    private javax.swing.JPasswordField TPassword2;
    private javax.swing.JTextField TUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
