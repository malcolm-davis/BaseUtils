package base.utils.swing;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import base.utils.SwingDialogWrapper;


public class DialogWrapperTest {

    public static void main(String[] args) {
        System.out.println("Start: DialogWrapperTest ");
        System.out.println("---------------------------------------------");

        long start = System.currentTimeMillis();

        DialogWrapperTest test = new DialogWrapperTest();
        test.run();

        System.out.println("---------------------------------------------");
        System.out.println("Runtime: " + (System.currentTimeMillis() - start));
        System.out.println("Finished.");
        System.exit(0);
    }

    private void run() {
        m_parentComponent = new JFrame();
        getDialog().setVisible(true);
    }

    private JDialog m_dialog;
    private SwingDialogWrapper m_dialogWrapper;
    JFrame m_parentComponent;

    JDialog getDialog() {
        if (m_dialog == null) {
            m_dialogWrapper = SwingDialogWrapper.newComponent(getPanel());
            m_dialog = m_dialogWrapper.createDialog(m_parentComponent, "test dialog wrapper");
            m_dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            m_dialog.setAlwaysOnTop(true);
            m_dialog.setResizable(false);
            m_dialog.setLocationByPlatform(true);
            m_dialog.setModal(true);
            m_dialog.add(getPanel(), java.awt.BorderLayout.CENTER);
            m_dialog.pack();
        }
        return m_dialog;
    }

    private JPanel getPanel() {
        if( panel==null ) {
            panel = new MainPanel();
        }
        return panel;
    }

    MainPanel panel;
}
