/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Database;

/**
 *
 * @author Asus
 */
public class dashboardUser extends javax.swing.JFrame {
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JTable dataTable;
    private JScrollPane scrollPane;
    
    /**
     * Creates new form dashboardUser
     */
    public dashboardUser() {
        initComponents();
        setupUI();
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        // Set window title
        setTitle("Wellessplate User Dashboard");
        setPreferredSize(new Dimension(1200, 800));
        
        // Create main container with BorderLayout
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        
        // Create top navbar with new color scheme
        JPanel navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(0, 41, 107)); // #00296b
        navbarPanel.setPreferredSize(new Dimension(getWidth(), 60));
        navbarPanel.setLayout(new BorderLayout());
        
        // Add brand name to navbar with updated styling
        JLabel brandLabel = new JLabel("Wellessplate");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        brandLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        navbarPanel.add(brandLabel, BorderLayout.WEST);
        
        // Add logout button with new styling
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(253, 197, 0)); // #fdc500
        logoutButton.setForeground(new Color(0, 41, 107)); // #00296b
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setMargin(new Insets(5, 10, 5, 10));
        
        // Add hover effect to logout button
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(255, 213, 0)); // #ffd500
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(253, 197, 0)); // #fdc500
            }
        });
        
        // Add action listener to logout button
        logoutButton.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                Login loginForm = new Login();
                loginForm.setVisible(true);
                this.dispose();
            }
        });
        
        navbarPanel.add(logoutButton, BorderLayout.EAST);
        
        // Create sidebar with updated styling
        sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBackground(new Color(0, 63, 136)); // #003f88
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        // Add menu items to sidebar with new styling
        addSidebarButton("Resep", e -> showResepPanel());
        addSidebarButton("Kategori", e -> showKategoriPanel());
        addSidebarButton("Komentar", e -> showKomentarPanel());
        
        // Create content panel with light background
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        // Add components to main container
        container.add(navbarPanel, BorderLayout.NORTH);
        container.add(sidebarPanel, BorderLayout.WEST);
        container.add(contentPanel, BorderLayout.CENTER);
        
        // Show Resep panel by default
        showResepPanel();
        
        pack();
    }
    
    private void addSidebarButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 45));
        button.setPreferredSize(new Dimension(200, 45));
        button.setBackground(new Color(0, 63, 136)); // #003f88
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 80, 157)); // #00509d
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 63, 136)); // #003f88
            }
        });
        
        sidebarPanel.add(button);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 1))); // Add small gap between buttons
    }
    
    private void showResepPanel() {
        contentPanel.removeAll();
        
        // Create table with updated styling
        String[] columns = {"ID", "Judul", "Bahan", "Alat", "Langkah"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        dataTable = new JTable(model);
        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        dataTable.getTableHeader().setBackground(new Color(0, 41, 107)); // #00296b
        dataTable.getTableHeader().setForeground(Color.WHITE);
        dataTable.setRowHeight(25);
        dataTable.setGridColor(new Color(230, 230, 230));
        
        scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Load data
        loadResepData();
        
        // Add components with padding
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentWrapper.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(contentWrapper);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadResepData() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        
        try (Connection conn = Database.koneksiDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM resep")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_resep"),
                    rs.getString("judul"),
                    rs.getString("bahan"),
                    rs.getString("alat"),
                    rs.getString("langkah")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showKategoriPanel() {
        contentPanel.removeAll();
        
        // Create table with updated styling
        String[] columns = {"ID", "Nama Kategori"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        dataTable = new JTable(model);
        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        dataTable.getTableHeader().setBackground(new Color(0, 41, 107)); // #00296b
        dataTable.getTableHeader().setForeground(Color.WHITE);
        dataTable.setRowHeight(25);
        dataTable.setGridColor(new Color(230, 230, 230));
        
        scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Load data
        loadKategoriData();
        
        // Add components with padding
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentWrapper.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(contentWrapper);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadKategoriData() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        
        try (Connection conn = Database.koneksiDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM kategori")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }
    
    private void showKomentarPanel() {
        contentPanel.removeAll();
        
        // Create table with updated styling
        String[] columns = {"ID", "User", "Komentar"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        dataTable = new JTable(model);
        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        dataTable.getTableHeader().setBackground(new Color(0, 41, 107)); // #00296b
        dataTable.getTableHeader().setForeground(Color.WHITE);
        dataTable.setRowHeight(25);
        dataTable.setGridColor(new Color(230, 230, 230));
        
        scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Load data
        loadKomentarData();
        
        // Add components with padding
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentWrapper.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(contentWrapper);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void loadKomentarData() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        
        try (Connection conn = Database.koneksiDatabase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT k.id_komentar, u.nama, k.isi_komentar " +
                 "FROM komentar k " +
                 "JOIN user u ON k.id_user = u.id_user")) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_komentar"),
                    rs.getString("nama"),
                    rs.getString("isi_komentar")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }// </editor-fold>//GEN-END:initComponents

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
                    
                    // Set table header colors
                    UIManager.put("TableHeader.background", new Color(0, 41, 107)); // #00296b
                    UIManager.put("TableHeader.foreground", Color.WHITE);
                    
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new dashboardUser().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
