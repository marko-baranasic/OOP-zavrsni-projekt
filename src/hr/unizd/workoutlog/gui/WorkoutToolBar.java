package hr.unizd.workoutlog.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The toolbar with quick-access file buttons (Save, Load, Export).
 * <p>
 * This panel implements {@link ActionListener} itself: every button registers
 * {@code this} as its listener, and {@link #actionPerformed(ActionEvent)} uses
 * {@link ActionEvent#getSource()} to tell which button was pressed. The toolbar
 * performs no file work of its own — it only reports the chosen command to its
 * {@link ToolBarListener} (the controller), which carries it out (delegation).
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class WorkoutToolBar extends JPanel implements ActionListener {

    /** Button requesting a save. */
    private JButton saveButton;
    /** Button requesting a load. */
    private JButton loadButton;
    /** Button requesting a text export. */
    private JButton exportButton;

    /** Listener notified about toolbar commands. */
    private ToolBarListener toolBarListener;

    /**
     * Builds the toolbar.
     */
    public WorkoutToolBar() {
        createComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT)); // dugmad poredana slijeva
        add(saveButton);
        add(loadButton);
        add(exportButton);
        activateButtons();
    }

    /**
     * Creates the toolbar buttons.
     */
    private void createComponents() {
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        exportButton = new JButton("Export");
    }

    /**
     * Registers this toolbar as the listener for every button.
     */
    private void activateButtons() {
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        exportButton.addActionListener(this);
    }

    /**
     * Handles every button click and forwards the matching command to the
     * listener, based on which button was the source of the event.
     *
     * @param ev the action event
     */
    @Override
    public void actionPerformed(ActionEvent ev) {

        // Bez postavljenog listenera nema kome javiti — samo izađi.
        if (toolBarListener == null) {
            return;
        }

        if (ev.getSource() == saveButton) {
            toolBarListener.onSave();
        } else if (ev.getSource() == loadButton) {
            toolBarListener.onLoad();
        } else if (ev.getSource() == exportButton) {
            toolBarListener.onExport();
        }
    }

    /**
     * Registers the listener notified about toolbar commands.
     *
     * @param listener the listener (the controller)
     */
    public void setToolBarListener(ToolBarListener listener) {
        this.toolBarListener = listener;
    }
}