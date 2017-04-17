/*
 * AboutDialog.java
 */

package jettyplay;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 * A dialog box that shows the copyright information for Jettyplay.
 * @author ais523
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

    /**
     * The current Jettyplay version number, a public static constant; other
     * parts of Jettyplay should use this value if they need to know the version
     * number rather than hardcoding it.
     */
    public static final String versionInfo = "0.1alpha";
    /**
     * The current Jettyplay copyright information, as a string wrapped to 80
     * columns with newline characters. It is both valid plaintext, and valid
     * HTML.
     */
    public static final String copyrightInfo =
"Copyright \u00a9 2010,2012 Alex Smith.\n"+
"Java terminal library copyright \u00a9 Matthias L. Jugel, Marcus Mei\u00dfner 1996-2005.\n"+
"All Rights Reserved.\n"+
"Bzip2 decompression library copyright \u00a9 2003 Rob Landley, based on code by\n"+
"Julian R Seward (licenced originally under LGPL version 2, used here under GPL).\n"+
"Licenced under the GNU General Public Licence, version 2 or any later version;\n"+
"and comes with ABSOLUTELY NO WARRANTY.";

    java.awt.Frame parent;
    /**
     * Creates a new dialog box for showing Jettyplay copyright information.
     * @param parent The parent window of the dialog box.
     */
    public AboutDialog(java.awt.Frame parent) {
        super(parent);
        this.parent = parent;
        initComponents();
        getRootPane().setDefaultButton(closeButton);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel versionLabel = new javax.swing.JLabel();
        javax.swing.JLabel appVersionLabel = new javax.swing.JLabel();
        javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
        javax.swing.JLabel appVendorLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        licenceButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About: Jettyplay 0.1alpha "); // NOI18N
        setModal(true);
        setName("aboutBox"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setText("Jettyplay"); // NOI18N
        appTitleLabel.setName("appTitleLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 12, 8);
        getContentPane().add(appTitleLabel, gridBagConstraints);

        appDescLabel.setFont(appDescLabel.getFont());
        appDescLabel.setText("<html><div style=\"width:355\">A program designed to replay recordings of terminal sessions, commonly used to record terminal-based games.</div>");
        appDescLabel.setMaximumSize(new java.awt.Dimension(355, 2147483647));
        appDescLabel.setName("appDescLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 12, 7);
        getContentPane().add(appDescLabel, gridBagConstraints);

        appDescLabel1.setFont(appDescLabel1.getFont());
        appDescLabel1.setText("<html><div style=\"width:355\">"+copyrightInfo+"</div>");
        appDescLabel1.setMaximumSize(new java.awt.Dimension(355, 2147483647));
        appDescLabel1.setName("appDescLabel1"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 7);
        getContentPane().add(appDescLabel1, gridBagConstraints);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 0, 5, 5));

        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
        versionLabel.setText("Product Version:");
        versionLabel.setName("versionLabel"); // NOI18N
        jPanel1.add(versionLabel);

        appVersionLabel.setText(versionInfo);
        appVersionLabel.setName("appVersionLabel"); // NOI18N
        jPanel1.add(appVersionLabel);

        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD));
        vendorLabel.setText("Author:");
        vendorLabel.setName("vendorLabel"); // NOI18N
        jPanel1.add(vendorLabel);

        appVendorLabel.setText("Alex Smith"); // NOI18N
        appVendorLabel.setName("appVendorLabel"); // NOI18N
        jPanel1.add(appVendorLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 12, 7);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        licenceButton.setMnemonic('l');
        licenceButton.setText("Licence information");
        licenceButton.setName("licenceButton"); // NOI18N
        licenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenceButtonActionPerformed(evt);
            }
        });
        jPanel2.add(licenceButton);

        closeButton.setMnemonic('c');
        closeButton.setText("Close");
        closeButton.setName("closeButton"); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jPanel2.add(closeButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void licenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenceButtonActionPerformed
        final JDialog thisDialog = this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                thisDialog.dispose();
            }
        });
        new LicenceDialog(parent).setVisible(true);
    }//GEN-LAST:event_licenceButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton licenceButton;
    // End of variables declaration//GEN-END:variables
    
}
