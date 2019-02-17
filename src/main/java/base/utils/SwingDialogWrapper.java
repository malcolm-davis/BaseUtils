package base.utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;

import sun.awt.AppContext;

/**
 * DialogWrapper is a dialog utility to wrap panels.
 *
 * @author Malcolm G. Davis
 * @version 1.0
 */
@SuppressWarnings("restriction")
public class SwingDialogWrapper extends JComponent implements Accessible {

    /**
     * <p>newComponent.</p>
     *
     * @param panel a {@link javax.swing.JPanel} object.
     * @return a {@link org.elasolutions.utils.swing.DialogWrapper} object.
     */
    public static SwingDialogWrapper newComponent(final JPanel panel) {
        return new SwingDialogWrapper(panel);
    }

    /**
     * <p>createDialog.</p>
     *
     * @param parentComponent a {@link java.awt.Component} object.
     * @param title a {@link java.lang.String} object.
     * @return a {@link javax.swing.JDialog} object.
     * @throws java.awt.HeadlessException if any.
     */
    public JDialog createDialog(final Component parentComponent,
            final String title) throws HeadlessException {
        return createDialog(parentComponent, title, JRootPane.PLAIN_DIALOG);
    }

    /**
     * <p>createDialog.</p>
     *
     * @param parentComponent a {@link java.awt.Component} object.
     * @param title a {@link java.lang.String} object.
     * @param style a int.
     * @return a {@link javax.swing.JDialog} object.
     * @throws java.awt.HeadlessException if any.
     */
    public JDialog createDialog(final Component parentComponent,
            final String title, final int style) throws HeadlessException {
        return createDialog(parentComponent, title, style, true);
    }

    /**
     * <p>createDialog.</p>
     *
     * @param parentComponent a {@link java.awt.Component} object.
     * @param title a {@link java.lang.String} object.
     * @param style a int.
     * @param border a boolean.
     * @return a {@link javax.swing.JDialog} object.
     * @throws java.awt.HeadlessException if any.
     */
    public JDialog createDialog(final Component parentComponent,
            final String title, final int style, final boolean border) throws HeadlessException {

        final JDialog dialog;
        final Window window = getWindowForComponent(parentComponent);
        if (window instanceof Frame) {
            dialog = new JDialog((Frame) window, title, true);
        } else {
            dialog = new JDialog((Dialog) window, title, true);
        }
        if (window instanceof SharedOwnerFrame) {
            final WindowListener ownerShutdownListener =
                    getSharedOwnerFrameShutdownListener();
            dialog.addWindowListener(ownerShutdownListener);
        }
        initDialog(dialog, style, parentComponent, border);
        return dialog;
    }


    /**
     * Sets the value the user has chosen. <br>
     * beaninfo preferred: true bound: true description: The option pane's value object.
     *
     * @param newValue the chosen value
     */
    public void setValue(final Object newValue) {
        final Object oldValue = value;

        value = newValue;
        firePropertyChange(VALUE_PROPERTY, oldValue, value);
    }

    private void initDialog(final JDialog dialog, final int style,
            final Component parentComponent, final boolean border) {
        dialog.setComponentOrientation(getComponentOrientation());

        if(border) {
            m_panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        } else {
            m_panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }
        dialog.setLayout(new BorderLayout());
        dialog.add(m_panel, BorderLayout.CENTER);
        dialog.setResizable(true);

        if (JDialog.isDefaultLookAndFeelDecorated()) {
            final boolean supportsWindowDecorations =
                    UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                dialog.setUndecorated(true);
                if(getRootPane()!=null) {
                    getRootPane().setWindowDecorationStyle(style);
                }
            }
        }
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        final WindowAdapter adapter = new WindowAdapter() {

            @Override
            public void windowClosing(final WindowEvent we) {
                setValue(null);
            }

            @Override
            public void windowGainedFocus(final WindowEvent we) {
                // Once window gets focus, set initial focus
                if (!gotFocus) {
                    // selectInitialValue();
                    gotFocus = true;
                }
            }

            private boolean gotFocus = false;
        };
        dialog.addWindowListener(adapter);
        dialog.addWindowFocusListener(adapter);
        dialog.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentShown(final ComponentEvent ce) {
                // reset value to ensure closing works properly
                setValue(JOptionPane.UNINITIALIZED_VALUE);
            }
        });
        addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                // Let the defaultCloseOperation handle the closing
                // if the user closed the window without selecting a button
                // (newValue = null in that case).  Otherwise, close the dialog.
                if (dialog.isVisible()
                        && (event.getSource() == SwingDialogWrapper.this)
                        && (event.getPropertyName().equals(VALUE_PROPERTY))
                        && (event.getNewValue() != null)
                        && (event.getNewValue() != JOptionPane.UNINITIALIZED_VALUE)) {
                    dialog.setVisible(false);
                }
            }
        });
    }

    static Object appContextGet(final Object key) {
        return AppContext.getAppContext().get(key);
    }

    static void appContextPut(final Object key, final Object value) {
        AppContext.getAppContext().put(key, value);
    }

    static void appContextRemove(final Object key) {
        AppContext.getAppContext().remove(key);
    }

    static Frame getRootFrame() throws HeadlessException {
        Frame sharedFrame = (Frame) appContextGet(sharedFrameKey);
        if (sharedFrame == null) {
            sharedFrame = getSharedOwnerFrame();
            appContextPut(sharedFrameKey, sharedFrame);
        }
        return sharedFrame;
    }

    static Window getWindowForComponent(final Component parentComponent)
            throws HeadlessException {
        if (parentComponent == null) {
            return getRootFrame();
        }
        if ((parentComponent instanceof Frame)
                || (parentComponent instanceof Dialog)) {
            return (Window) parentComponent;
        }
        return SwingDialogWrapper.getWindowForComponent(parentComponent.getParent());
    }


    /*
     * Don't make these AppContext accessors public or protected -- since
     * AppContext is in sun.awt in 1.2, we shouldn't expose it even indirectly
     * with a public API.
     */
    // REMIND(aim): phase out use of 4 methods below since they
    // are just private covers for AWT methods (?)

    /**
     * Returns a toolkit-private, shared, invisible Frame to be the owner for
     * JDialogs and JWindows created with {@code null} owners.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    static Frame getSharedOwnerFrame() throws HeadlessException {
        Frame sharedOwnerFrame = (Frame) appContextGet(sharedOwnerFrameKey);
        if (sharedOwnerFrame == null) {
            sharedOwnerFrame = new SharedOwnerFrame();
            appContextPut(sharedOwnerFrameKey, sharedOwnerFrame);
        }
        return sharedOwnerFrame;
    }

    /**
     * Returns a SharedOwnerFrame's shutdown listener to dispose the
     * SharedOwnerFrame if it has no more displayable children.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    static WindowListener getSharedOwnerFrameShutdownListener()
            throws HeadlessException {
        final Frame sharedOwnerFrame = getSharedOwnerFrame();
        return (WindowListener) sharedOwnerFrame;
    }

    /**
     * Currently selected value, will be a valid option, or
     * <code>UNINITIALIZED_VALUE</code> or <code>null</code>.
     */
    transient protected Object value;

    static class SharedOwnerFrame extends Frame implements WindowListener {

        @Override
        public void addNotify() {
            super.addNotify();
            installListeners();
        }

        @Override
        public void dispose() {
            try {
                getToolkit().getSystemEventQueue();
                super.dispose();
            } catch (final Exception e) {
                // untrusted code not allowed to dispose
            }
        }

        @SuppressWarnings("deprecation")
        @Deprecated
        @Override
        public void show() {
            // This frame can never be shown
        }

        @Override
        public void windowActivated(final WindowEvent e) {
        }

        /**
         * Watches for displayability changes and disposes shared instance if
         * there are no displayable children left.
         */
        @Override
        public void windowClosed(final WindowEvent e) {
            synchronized (getTreeLock()) {
                final Window[] windows = getOwnedWindows();
                for(final Window window : windows) {
                    if (window != null) {
                        if (window.isDisplayable()) {
                            return;
                        }
                        window.removeWindowListener(this);
                    }
                }
                dispose();
            }
        }

        @Override
        public void windowClosing(final WindowEvent e) {
        }

        @Override
        public void windowDeactivated(final WindowEvent e) {
        }

        @Override
        public void windowDeiconified(final WindowEvent e) {
        }

        @Override
        public void windowIconified(final WindowEvent e) {
        }

        @Override
        public void windowOpened(final WindowEvent e) {
        }

        /**
         * Install window listeners on owned windows to watch for displayability
         * changes
         */
        void installListeners() {
            final Window[] windows = getOwnedWindows();
            for(final Window window : windows) {
                if (window != null) {
                    window.removeWindowListener(this);
                    window.addWindowListener(this);
                }
            }
        }
    }

    // Don't use String, as it's not guaranteed to be unique in a Hashtable.
    private static final Object sharedOwnerFrameKey =
            new StringBuilder("DialogUtil.sharedOwnerFrame");

    private static final Object sharedFrameKey = SwingDialogWrapper.class;

    /** Bound property name for <code>value</code>. */
    public static final String VALUE_PROPERTY = "value";

    private final JPanel m_panel;

    SwingDialogWrapper() {
        m_panel = null;
    }

    /**
     * @param panel
     */
    SwingDialogWrapper(final JPanel panel) {
        m_panel = panel;
    }

}
