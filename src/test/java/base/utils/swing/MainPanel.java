package base.utils.swing;


public class MainPanel extends javax.swing.JPanel {

    public MainPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        m_mainPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_transparancySider = new javax.swing.JSlider();
        jPanel5 = new javax.swing.JPanel();
        m_exitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        m_plusButton = new javax.swing.JButton();
        m_minusButton = new javax.swing.JButton();

        jPanel1.setMaximumSize(new java.awt.Dimension(1000, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        m_mainPanel.setLayout(new java.awt.BorderLayout());
        jPanel1.add(m_mainPanel, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(50, 300));
        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        m_transparancySider.setOrientation(javax.swing.JSlider.VERTICAL);
        m_transparancySider.setPreferredSize(new java.awt.Dimension(10, 75));
        jPanel4.add(m_transparancySider);

        jPanel3.add(jPanel4);

        m_exitButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        m_exitButton.setText("X");
        m_exitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_exitButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        m_exitButton.setMaximumSize(new java.awt.Dimension(17, 17));
        m_exitButton.setMinimumSize(new java.awt.Dimension(17, 17));
        m_exitButton.setPreferredSize(new java.awt.Dimension(17, 17));
        jPanel5.add(m_exitButton);
        m_exitButton.getAccessibleContext().setAccessibleName("exitButton");

        jLabel1.setText(" ");
        jPanel5.add(jLabel1);

        m_plusButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        m_plusButton.setText("+");
        m_plusButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_plusButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        m_plusButton.setMaximumSize(new java.awt.Dimension(17, 17));
        m_plusButton.setMinimumSize(new java.awt.Dimension(17, 17));
        m_plusButton.setPreferredSize(new java.awt.Dimension(17, 17));
        jPanel5.add(m_plusButton);

        m_minusButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        m_minusButton.setText("-");
        m_minusButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_minusButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        m_minusButton.setMaximumSize(new java.awt.Dimension(17, 17));
        m_minusButton.setMinimumSize(new java.awt.Dimension(17, 17));
        m_minusButton.setPreferredSize(new java.awt.Dimension(17, 17));
        jPanel5.add(m_minusButton);

        jPanel3.add(jPanel5);

        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.CENTER);

    }// </editor-fold>

    private void formFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
    }

    private void formFocusLost(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
    }

    private void formWindowActivated(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
    }


    // Variables declaration - do not modify
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JButton m_exitButton;
    public javax.swing.JPanel m_mainPanel;
    public javax.swing.JButton m_minusButton;
    public javax.swing.JButton m_plusButton;
    public javax.swing.JSlider m_transparancySider;
    // End of variables declaration
}
