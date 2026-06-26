package hr.unizd.workoutlog.gui;

import hr.unizd.workoutlog.model.Intensity;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The left-hand View component: a form where the user enters the details of a
 * single workout and presses "Add Workout".
 * <p>
 * This panel is fully self-contained. It builds and lays out its own
 * components, handles its own button click with an anonymous
 * {@link ActionListener}, reads its own fields, packs them into a
 * {@link WorkoutFormEvent}, and hands that event to its
 * {@link WorkoutFormListener}. It never exposes its internal components to the
 * outside world (encapsulation) and never decides what happens to the data
 * (that is the controller's job — delegation).
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class InputFormPanel extends JPanel {

    /** Input field for the exercise name. */
    private JTextField nameField;
    /** Input field for the workout duration in minutes. */
    private JTextField durationField;
    /** Drop-down for choosing the day of the week. */
    private JComboBox<String> dayCombo;
    /** Radio button for "light" intensity. */
    private JRadioButton lightButton;
    /** Radio button for "medium" intensity. */
    private JRadioButton mediumButton;
    /** Radio button for "hard" intensity. */
    private JRadioButton hardButton;
    /** Checkbox marking the workout as cardio. */
    private JCheckBox cardioCheckBox;
    /** Checkbox marking the workout as strength. */
    private JCheckBox strengthCheckBox;
    /** Text area for an optional note. */
    private JTextArea noteArea;
    /** Button that fires the "add workout" event. */
    private JButton addButton;

    /** Listener notified when the user wants to add a workout. */
    private WorkoutFormListener formListener;

    /**
     * Builds the input form panel.
     */
    public InputFormPanel() {
        createComponents();   // 1) napravi komponente
        setBorders();         // 2) okvir s naslovom
        layoutComponents();   // 3) rasporedi ih
        activateForm();       // 4) oživi dugme (anonimni listener)
    }

    /**
     * Creates all input components and configures their initial state.
     */
    private void createComponents() {
        nameField = new JTextField();
        durationField = new JTextField();

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};
        dayCombo = new JComboBox<>(days);

        lightButton = new JRadioButton(Intensity.LIGHT.getDisplayName());
        mediumButton = new JRadioButton(Intensity.MEDIUM.getDisplayName());
        hardButton = new JRadioButton(Intensity.HARD.getDisplayName());
        mediumButton.setSelected(true); // defaultni odabir
        // Grupa osigurava da je odabran samo jedan radio button istovremeno.
        ButtonGroup intensityGroup = new ButtonGroup();
        intensityGroup.add(lightButton);
        intensityGroup.add(mediumButton);
        intensityGroup.add(hardButton);

        cardioCheckBox = new JCheckBox("Cardio");
        strengthCheckBox = new JCheckBox("Strength");

        noteArea = new JTextArea(3, 10);
        noteArea.setLineWrap(true); // automatski prijelom reda

        addButton = new JButton("Add Workout");
    }

    /**
     * Adds a titled border around the form.
     */
    private void setBorders() {
        setBorder(BorderFactory.createTitledBorder("Log Workout"));
    }

    /**
     * Places all components into the panel using a {@link GridBagLayout}.
     */
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);        // 4 px razmaka oko svake komponente
        c.fill = GridBagConstraints.HORIZONTAL;   // komponente se šire po širini

        // Red 0 — naziv vježbe
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        add(new JLabel("Exercise name:"), c);
        c.gridx = 1; c.weightx = 1;               // polje se rasteže pri širenju prozora
        add(nameField, c);

        // Red 1 — trajanje
        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        add(new JLabel("Duration (min):"), c);
        c.gridx = 1; c.weightx = 1;
        add(durationField, c);

        // Red 2 — dan
        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        add(new JLabel("Day:"), c);
        c.gridx = 1; c.weightx = 1;
        add(dayCombo, c);

        // Red 3 — intenzitet (tri radio dugmeta jedan do drugog)
        c.gridx = 0; c.gridy = 3; c.weightx = 0;
        add(new JLabel("Intensity:"), c);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.add(lightButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);
        c.gridx = 1; c.weightx = 1;
        add(radioPanel, c);

        // Red 4 — kategorija (dvije kućice)
        c.gridx = 0; c.gridy = 4; c.weightx = 0;
        add(new JLabel("Category:"), c);
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkPanel.add(cardioCheckBox);
        checkPanel.add(strengthCheckBox);
        c.gridx = 1; c.weightx = 1;
        add(checkPanel, c);

        // Red 5 — bilješka
        c.gridx = 0; c.gridy = 5; c.weightx = 0;
        add(new JLabel("Note:"), c);
        c.gridx = 1; c.weightx = 1;
        add(new JScrollPane(noteArea), c);        // scroll bar po potrebi

        // Red 6 — dugme preko obje kolone
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.weightx = 1;
        add(addButton, c);
    }

    /**
     * Attaches the click handler to the add button.
     * <p>
     * The handler reads the form, packs the values into a
     * {@link WorkoutFormEvent}, and notifies the listener. It is implemented as
     * an anonymous {@link ActionListener} (not a lambda).
     */
    private void activateForm() {
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Panel čita SVOJE komponente i pakira vrijednosti u događaj.
                WorkoutFormEvent event = new WorkoutFormEvent(addButton);
                event.setExerciseName(nameField.getText());
                event.setDuration(durationField.getText());
                event.setDay((String) dayCombo.getSelectedItem());
                event.setIntensity(getSelectedIntensity());
                event.setCardio(cardioCheckBox.isSelected());
                event.setStrength(strengthCheckBox.isSelected());
                event.setNote(noteArea.getText());

                // Delegacija: panel ne odlučuje što dalje, samo javi listeneru.
                if (formListener != null) {
                    formListener.onAddWorkout(event);
                }
            }
        });
    }

    /**
     * Determines which intensity radio button is currently selected.
     *
     * @return the selected {@link Intensity} (defaults to MEDIUM)
     */
    private Intensity getSelectedIntensity() {
        if (lightButton.isSelected()) {
            return Intensity.LIGHT;
        } else if (hardButton.isSelected()) {
            return Intensity.HARD;
        } else {
            return Intensity.MEDIUM;
        }
    }

    /**
     * Registers the listener that will be notified about form events.
     *
     * @param listener the listener (the controller)
     */
    public void setFormListener(WorkoutFormListener listener) {
        this.formListener = listener;
    }

    /**
     * Resets all input fields to their default (empty / initial) state.
     * Called by the controller after a workout has been successfully added.
     */
    public void clearForm() {
        nameField.setText("");
        durationField.setText("");
        dayCombo.setSelectedIndex(0);
        mediumButton.setSelected(true);
        cardioCheckBox.setSelected(false);
        strengthCheckBox.setSelected(false);
        noteArea.setText("");
        nameField.requestFocus(); // kursor odmah u prvo polje
    }
}