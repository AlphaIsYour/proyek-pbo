/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Database;
import model.Komentar;
import model.Resep;

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
    private javax.swing.JPanel commentPanel;
    private javax.swing.JTextField commentField;
    private javax.swing.JButton submitButton;
    private javax.swing.JPanel commentsListPanel;
    
    private List<Resep> resepList;
    private Database db;
    private int currentPage = 1;
    private int itemsPerPage = 8;
    private List<Komentar> commentList;
    private int currentUserId; // Store the current user's ID
    
    /**
     * Creates new form dashboardUser
     */
    public dashboardUser() {
        db = new Database();
        resepList = new ArrayList<>();
        initComponents();
        setLocationRelativeTo(null);
        loadResepData();
        customizeComponents();
        setupCommentSection();
    }

    private void loadResepData() {
        resepList.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = db.getConnection();
            String query = "SELECT r.*, k.nama_kategori FROM resep r " +
                         "LEFT JOIN kategori k ON r.id_kategori = k.id_kategori";
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Resep resep = new Resep(
                    rs.getInt("id_resep"),
                    rs.getInt("id_kategori"),
                    rs.getString("judul"),
                    rs.getString("bahan"),
                    rs.getString("alat"),
                    rs.getString("langkah"),
                    rs.getString("foto")
                );
                resep.setNama_kategori(rs.getString("nama_kategori"));
                resepList.add(resep);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading recipes: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
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
            ImageIcon bannerIcon = new ImageIcon("src/images/banner.png");
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
        
        // Load kategori for combobox
        loadKategoriToCombo();
        
        // Setup card panels with real data
        updateCardDisplay();
        
        // Setup pagination
        setupPagination();
    }
    
    private void loadKategoriToCombo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = db.getConnection();
            String query = "SELECT nama_kategori FROM kategori";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Semua");
            
            while (rs.next()) {
                model.addElement(rs.getString("nama_kategori"));
            }
            
            kategoriCombo.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading categories: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    private void setupCard(JPanel card, Resep resep) {
        if (resep == null) {
            card.setVisible(false);
            return;
        }
        
        card.setVisible(true);
        card.setLayout(new BorderLayout(5, 5));
        card.setBackground(new Color(0, 63, 136));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 197, 0), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Image panel
        JPanel imagePanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(getWidth(), 160);
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (resep.getFoto() != null && !resep.getFoto().isEmpty()) {
                    try {
                        String imagePath = resep.getFoto();
                        // If the path doesn't start with src/images, prepend it
                        if (!imagePath.startsWith("src/images/")) {
                            imagePath = "src/images/" + imagePath;
                        }
                        File imageFile = new File(imagePath);
                        if (imageFile.exists()) {
                            Image img = ImageIO.read(imageFile);
                            // Calculate dimensions to maintain aspect ratio
                            double aspectRatio = (double) img.getWidth(this) / img.getHeight(this);
                            int targetWidth = getWidth();
                            int targetHeight = getHeight();
                            
                            // Calculate dimensions to fit while maintaining aspect ratio
                            if (targetWidth / aspectRatio <= targetHeight) {
                                // Width is the limiting factor
                                targetHeight = (int) (targetWidth / aspectRatio);
                            } else {
                                // Height is the limiting factor
                                targetWidth = (int) (targetHeight * aspectRatio);
                            }
                            
                            // Center the image
                            int x = (getWidth() - targetWidth) / 2;
                            int y = (getHeight() - targetHeight) / 2;
                            
                            // Draw image with high quality
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            
                            g2d.drawImage(img, x, y, targetWidth, targetHeight, this);
                            return;
                        }
                    } catch (Exception e) {
                        System.err.println("Error loading image for " + resep.getJudul() + ": " + e.getMessage());
                    }
                }
                // Default background if no image
                g.setColor(new Color(200, 200, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw a placeholder icon
                g.setColor(new Color(150, 150, 150));
                int iconSize = 40;
                int x = (getWidth() - iconSize) / 2;
                int y = (getHeight() - iconSize) / 2;
                g.fillRect(x, y, iconSize, iconSize);
                g.setColor(new Color(200, 200, 200));
                g.drawString("No Image", x - 10, y + iconSize + 20);
            }
        };
        imagePanel.setBackground(Color.LIGHT_GRAY);
        card.add(imagePanel, BorderLayout.CENTER);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(0, 63, 136));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        
        JLabel titleLabel = new JLabel(resep.getJudul());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel categoryLabel = new JLabel(resep.getNama_kategori());
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
                halamanResep detailView = new halamanResep(resep);
                detailView.setVisible(true);
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
    
    private void updateCardDisplay() {
        JPanel[] cardPanels = {card1, card2, card3, card4, card5, card6, card7, card8};
        int startIdx = (currentPage - 1) * itemsPerPage;
        
        for (int i = 0; i < cardPanels.length; i++) {
            cardPanels[i].removeAll();
            if (startIdx + i < resepList.size()) {
                setupCard(cardPanels[i], resepList.get(startIdx + i));
            } else {
                cardPanels[i].setVisible(false);
            }
        }
        
        cardPanel.revalidate();
        cardPanel.repaint();
    }
    
    private void setupPagination() {
        paginationPanel.removeAll();
        
        int totalPages = (int) Math.ceil((double) resepList.size() / itemsPerPage);
        
        JButton prevBtn = new JButton("Prev");
        prevBtn.setEnabled(currentPage > 1);
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateCardDisplay();
                setupPagination();
            }
        });
        
        JButton nextBtn = new JButton("Next");
        nextBtn.setEnabled(currentPage < totalPages);
        nextBtn.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateCardDisplay();
                setupPagination();
            }
        });
        
        styleButton(prevBtn);
        styleButton(nextBtn);
        
        paginationPanel.add(prevBtn);
        
        for (int i = 1; i <= totalPages; i++) {
            JButton pageBtn = new JButton(String.valueOf(i));
            styleButton(pageBtn);
            
            if (i == currentPage) {
                pageBtn.setBackground(new Color(0, 63, 136));
                pageBtn.setForeground(Color.WHITE);
            }
            
            final int page = i;
            pageBtn.addActionListener(e -> {
                currentPage = page;
                updateCardDisplay();
                setupPagination();
            });
            
            paginationPanel.add(pageBtn);
        }
        
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

    private void setupCommentSection() {
        // Initialize comment list
        commentList = new ArrayList<>();
        
        // Create comment panel
        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Create input panel
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(Color.WHITE);
        
        // Create comment field
        commentField = new JTextField();
        commentField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commentField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(253, 197, 0)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        commentField.setForeground(Color.GRAY);
        commentField.setText("Tulis komentar disini");
        commentField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (commentField.getText().equals("Tulis komentar disini")) {
                    commentField.setText("");
                    commentField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (commentField.getText().isEmpty()) {
                    commentField.setForeground(Color.GRAY);
                    commentField.setText("Tulis komentar disini");
                }
            }
        });
        
        // Create submit button
        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(253, 197, 0));
        submitButton.setForeground(new Color(0, 41, 107));
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> submitComment());
        
        // Add components to input panel
        inputPanel.add(commentField, BorderLayout.CENTER);
        inputPanel.add(submitButton, BorderLayout.EAST);
        
        // Create comments list panel
        commentsListPanel = new JPanel();
        commentsListPanel.setLayout(new BoxLayout(commentsListPanel, BoxLayout.Y_AXIS));
        commentsListPanel.setBackground(Color.WHITE);
        
        // Add components to comment panel
        commentPanel.add(inputPanel);
        commentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        commentPanel.add(commentsListPanel);
        
        // Add comment panel to main panel after pagination
        mainPanel.add(commentPanel);
        
        // Load existing comments
        loadComments();
    }
    
    private void loadComments() {
        commentList.clear();
        commentsListPanel.removeAll();
        
        try {
            Connection conn = db.getConnection();
            String query = "SELECT k.*, u.nama FROM komentar k " +
                         "JOIN user u ON k.id_user = u.id_user " +
                         "ORDER BY k.tanggal DESC";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Komentar komentar = new Komentar(
                    rs.getInt("id_komentar"),
                    rs.getInt("id_user"),
                    rs.getString("isi_komentar"),
                    rs.getDate("tanggal")
                );
                komentar.setNama_user(rs.getString("nama"));
                commentList.add(komentar);
                addCommentToPanel(komentar);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error loading comments: " + e.getMessage());
        }
        
        commentsListPanel.revalidate();
        commentsListPanel.repaint();
    }
    
    private void submitComment() {
        String commentText = commentField.getText();
        if (commentText.equals("Tulis komentar disini") || commentText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Silakan tulis komentar Anda terlebih dahulu", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Connection conn = db.getConnection();
            
            // First, get the next id_komentar value
            int nextId = 1;
            String getMaxIdQuery = "SELECT MAX(id_komentar) FROM komentar";
            PreparedStatement maxIdStmt = conn.prepareStatement(getMaxIdQuery);
            ResultSet rs = maxIdStmt.executeQuery();
            if (rs.next() && rs.getObject(1) != null) {
                nextId = rs.getInt(1) + 1;
            }
            rs.close();
            maxIdStmt.close();
            
            // Now insert the comment with the new ID
            String query = "INSERT INTO komentar (id_komentar, id_user, isi_komentar, tanggal) VALUES (?, ?, ?, NOW())";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, nextId);
            stmt.setInt(2, currentUserId);
            stmt.setString(3, commentText);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                commentField.setText("Tulis komentar disini");
                commentField.setForeground(Color.GRAY);
                loadComments(); // Reload comments to show the new one
            }
            
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error submitting comment: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Gagal menambahkan komentar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addCommentToPanel(Komentar komentar) {
        // Create comment container
        JPanel commentContainer = new JPanel();
        commentContainer.setLayout(new BorderLayout(10, 5));
        commentContainer.setBackground(new Color(245, 245, 245));
        commentContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // User name and date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(null);
        
        JLabel nameLabel = new JLabel(komentar.getNama_user());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(0, 41, 107));
        
        JLabel dateLabel = new JLabel(new SimpleDateFormat("dd MMM yyyy").format(komentar.getTanggal()));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        
        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);
        
        // Comment text
        JLabel commentLabel = new JLabel("<html><p style='width: 500px;'>" + komentar.getIsi_komentar() + "</p></html>");
        commentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Add components to container
        commentContainer.add(headerPanel, BorderLayout.NORTH);
        commentContainer.add(commentLabel, BorderLayout.CENTER);
        
        // Add container to list panel
        commentsListPanel.add(commentContainer);
        commentsListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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

    // Add this method to set the current user ID
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }
}
