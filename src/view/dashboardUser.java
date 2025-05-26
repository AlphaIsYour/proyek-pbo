/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Asus
 */
public class dashboardUser extends javax.swing.JFrame {
    // Variables declaration
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel bannerPanel;
    private javax.swing.JLabel bannerLabel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchField;
    private javax.swing.JComboBox<String> kategoriCombo;
    private javax.swing.JButton searchButton;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel card1;
    private javax.swing.JPanel card2;
    private javax.swing.JPanel card3;
    private javax.swing.JPanel card4;
    private javax.swing.JPanel card5;
    private javax.swing.JPanel card6;
    private javax.swing.JPanel card7;
    private javax.swing.JPanel card8;
    private javax.swing.JPanel paginationPanel;
    
    /**
     * Creates new form dashboardUser
     */
    public dashboardUser() {
        initComponents();
        setLocationRelativeTo(null);
        customizeComponents();
    }

    private void customizeComponents() {
        // Set window properties
        setTitle("Wellessplate - Dashboard");
        setSize(1080, 720);
        setLocationRelativeTo(null);
        
        // Style main panel
        mainPanel.setBackground(Color.WHITE);
        
        // Hitung lebar konten berdasarkan card grid
        int cardWidth = 200; // Lebar setiap card
        int cardGap = 24;   // Jarak antar card
        int totalCardsWidth = (cardWidth * 4) + (cardGap * 3); // 4 card dengan 3 gap
        int contentPadding = 40; // Padding kiri-kanan untuk konten
        int contentWidth = totalCardsWidth + (contentPadding * 2); // Total lebar konten
        
        // Setup banner panel agar sejajar dengan card grid
        bannerPanel.setPreferredSize(new Dimension(contentWidth, 280));
        bannerPanel.setMaximumSize(new Dimension(contentWidth, 280));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add top padding
        bannerPanel.setBackground(Color.WHITE); // Set banner panel background to white
        
        try {
            ImageIcon bannerIcon = new ImageIcon("src/images/banner.jpg");
            Image img = bannerIcon.getImage().getScaledInstance(contentWidth, 280, Image.SCALE_SMOOTH);
            bannerLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("Banner image not found: " + e.getMessage());
            bannerPanel.setBackground(new Color(255, 255, 255)); // Fallback color
        }
        
        // Style search components with more compact layout
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchField.setPreferredSize(new Dimension(250, 32));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 197, 0)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        kategoriCombo.setPreferredSize(new Dimension(120, 32));
        kategoriCombo.setModel(new DefaultComboBoxModel<>(
            new String[] {"Semua", "Daging", "Goreng", "Rebus", "Sayur", "Kukus"}
        ));
        kategoriCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        kategoriCombo.setBackground(Color.WHITE);
        
        searchButton.setPreferredSize(new Dimension(80, 32));
        searchButton.setBackground(new Color(253, 197, 0));
        searchButton.setForeground(new Color(0, 41, 107));
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setText("Cari");
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Setup card panels with dummy content
        JPanel[] cardPanels = {card1, card2, card3, card4, card5, card6, card7, card8};
        String[] dummyTitles = {
            "Nasi Goreng", "Soto Ayam", "Rendang", "Gado-gado",
            "Sate Ayam", "Mie Goreng", "Bakso", "Sop Buntut"
        };
        String[] dummyCategories = {
            "Goreng", "Kuah", "Daging", "Sayur",
            "Daging", "Goreng", "Kuah", "Daging"
        };
        for (int i = 0; i < cardPanels.length; i++) {
            setupCard(cardPanels[i], dummyTitles[i], dummyCategories[i]);
        }
        
        // Setup pagination
        paginationPanel.setBackground(Color.WHITE);
        setupPagination();
    }
    
    private void setupCard(JPanel card, String title, String category) {
        card.setLayout(new BorderLayout(5, 5));
        card.setBackground(new Color(0, 63, 136));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 197, 0), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Image panel dengan fixed height yang lebih tinggi
        JPanel imagePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(getWidth(), 160); // Fixed height 160px
            }
            
            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
            
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        imagePanel.setBackground(new Color(253, 197, 0));
        card.add(imagePanel, BorderLayout.CENTER);
        
        // Info panel (title & category)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(0, 63, 136));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        
        // Title label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Category label
        JLabel categoryLabel = new JLabel(category);
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(253, 197, 0));
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(categoryLabel);
        
        card.add(infoPanel, BorderLayout.SOUTH);
        
        // Make card clickable
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(card, "Buka detail resep: " + title);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(0, 80, 157));
                infoPanel.setBackground(new Color(0, 80, 157));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(0, 63, 136));
                infoPanel.setBackground(new Color(0, 63, 136));
            }
        });
    }
    
    private void setupPagination() {
        paginationPanel.removeAll();
        
        JButton prevBtn = new JButton("Prev");
        JButton nextBtn = new JButton("Next");
        JButton[] pageButtons = new JButton[3];
        
        for (int i = 0; i < pageButtons.length; i++) {
            pageButtons[i] = new JButton(String.valueOf(i + 1));
            styleButton(pageButtons[i]);
            paginationPanel.add(pageButtons[i]);
        }
        
        styleButton(prevBtn);
        styleButton(nextBtn);
        
        paginationPanel.add(prevBtn, 0);
        paginationPanel.add(nextBtn);
        
        paginationPanel.revalidate();
        paginationPanel.repaint();
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(253, 197, 0));
        button.setForeground(new Color(0, 41, 107));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize components
        mainPanel = new javax.swing.JPanel();
        bannerPanel = new javax.swing.JPanel();
        bannerLabel = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        kategoriCombo = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        card1 = new javax.swing.JPanel();
        card2 = new javax.swing.JPanel();
        card3 = new javax.swing.JPanel();
        card4 = new javax.swing.JPanel();
        card5 = new javax.swing.JPanel();
        card6 = new javax.swing.JPanel();
        card7 = new javax.swing.JPanel();
        card8 = new javax.swing.JPanel();
        paginationPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.Y_AXIS));

        bannerPanel.setLayout(new java.awt.BorderLayout());
        bannerPanel.add(bannerLabel, java.awt.BorderLayout.CENTER);
        mainPanel.add(bannerPanel);

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));
        searchPanel.setLayout(new javax.swing.BoxLayout(searchPanel, javax.swing.BoxLayout.X_AXIS));
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(kategoriCombo);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel);

        cardPanel.setBackground(new java.awt.Color(255, 255, 255));
        cardPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cardPanel.setLayout(new java.awt.GridLayout(2, 4, 24, 24));
        cardPanel.add(card1);
        cardPanel.add(card2);
        cardPanel.add(card3);
        cardPanel.add(card4);
        cardPanel.add(card5);
        cardPanel.add(card6);
        cardPanel.add(card7);
        cardPanel.add(card8);
        
        // Wrap cardPanel in a panel with vertical scrolling
        JPanel cardScrollContent = new JPanel();
        cardScrollContent.setLayout(new BorderLayout());
        cardScrollContent.setBackground(Color.WHITE);
        cardScrollContent.add(cardPanel, BorderLayout.NORTH); // Align to top
        
        JScrollPane cardScroll = new JScrollPane(cardScrollContent);
        cardScroll.setBorder(null);
        cardScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cardScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(cardScroll);

        paginationPanel.setBackground(new java.awt.Color(255, 255, 255));
        paginationPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 20, 10));
        paginationPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));
        mainPanel.add(paginationPanel);

        // Add scroll capability to main panel
        JScrollPane mainScroll = new JScrollPane(mainPanel);
        mainScroll.setBorder(null);
        mainScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(mainScroll);

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    
                    UIManager.put("nimbusBase", new Color(0, 41, 107));
                    UIManager.put("nimbusBlueGrey", new Color(0, 63, 136));
                    UIManager.put("control", new Color(0, 80, 157));
                    
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
}
