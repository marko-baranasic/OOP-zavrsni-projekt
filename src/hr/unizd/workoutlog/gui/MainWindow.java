package hr.unizd.workoutlog.gui;

import hr.unizd.workoutlog.logic.DataStorage;
import hr.unizd.workoutlog.logic.InvalidInputException;
import hr.unizd.workoutlog.logic.WorkoutLog;
import hr.unizd.workoutlog.model.Intensity;
import hr.unizd.workoutlog.model.Workout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The main application window of WorkoutLog.
 * <p>
 * This class builds and displays the entire Swing user interface. It is
 * divided into two panels side by side:
 * <ul>
 *   <li><b>Left panel</b> - a form where the user enters workout details.</li>
 *   <li><b>Right panel</b> - a list of all logged workouts and a statistics
 *       summary below it.</li>
 * </ul>
 * A {@link JMenuBar} and a {@link JToolBar} at the top provide access to
 * file operations (save, load, export) and application exit.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class MainWindow extends JFrame {

    private WorkoutLog workoutLog;

    private JTextField nameField;
    private JTextField durationField;
    private JComboBox<String> dayCombo;
    private JRadioButton lightButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private JCheckBox cardioCheckBox;
    private JCheckBox strengthCheckBox;
    private JTextArea noteArea;
    private JButton addButton;

    private DefaultListModel<String> listModel;
    private JList<String> workoutList;
    private JButton deleteButton;
    private JLabel totalCountLabel;
    private JLabel totalMinutesLabel;
    private JLabel cardioCountLabel;
    private JLabel strengthCountLabel;

    /**
     * Creates and displays the main application window.
     *
     * @param workoutLog the workout log to display and modify
     */
    public MainWindow(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;

        setTitle("WorkoutLog"); // Naslov na vrhu prozora
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zatvaranje prozora i procesa aplikacije
        setSize(900, 600); // Velicina prozora
        setMinimumSize(new Dimension(800, 500)); // Minimalna velicina prozora
        setLocationRelativeTo(null); // prozor se otvara na sredini ekrana
// Redosljed kojim se GUI gradi i puni podacima
        buildMenuBar(); // Poziva metodu buildMenubar()
        buildToolBar();
        buildMainPanel();

        refreshList();
        refreshStats();

        setVisible(true);
    }

    /**
     * Builds the menu bar with File and Help menus and attaches it to the frame.
     */
    private void buildMenuBar() { // kreira save, load, export, exit
        JMenuBar menuBar = new JMenuBar(); // Kreira traku izbornika

        JMenu fileMenu = new JMenu("File"); // Kreira izbornik File
        JMenuItem saveItem   = new JMenuItem("Save"); // Kreira stavke menija...
        JMenuItem loadItem   = new JMenuItem("Load");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem   = new JMenuItem("Exit");

        saveItem.addActionListener(e -> saveWorkouts()); // Dodavanje akcija
        loadItem.addActionListener(e -> loadWorkouts());
        exportItem.addActionListener(e -> exportWorkouts());
        exitItem.addActionListener(e -> System.exit(0)); // Dodavanje izlazak iz programa

        fileMenu.add(saveItem); // Dodavanje stavki menija u izbornik File
        fileMenu.add(loadItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator(); // Dodaje vodoravnu crtu izbornika
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "WorkoutLog v1.0\nAuthor: Marko Baranasic",
                        "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Builds the toolbar with quick-access buttons and adds it to the frame.
     */
    private void buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton saveBtn   = new JButton("Save");
        JButton loadBtn   = new JButton("Load");
        JButton exportBtn = new JButton("Export");

        saveBtn.addActionListener(e -> saveWorkouts());
        loadBtn.addActionListener(e -> loadWorkouts());
        exportBtn.addActionListener(e -> exportWorkouts());

        toolBar.add(saveBtn);
        toolBar.add(loadBtn);
        toolBar.add(exportBtn);

        add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Builds the main content area: left input panel and right list panel.
     */
    private void buildMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(buildInputPanel());
        mainPanel.add(buildListPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Builds the left panel containing the workout entry form.
     *
     * @return the fully constructed input panel
     */
    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Log Workout"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        panel.add(new JLabel("Exercise name:"), c);
        nameField = new JTextField();
        c.gridx = 1; c.weightx = 1;
        panel.add(nameField, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        panel.add(new JLabel("Duration (min):"), c);
        durationField = new JTextField();
        c.gridx = 1; c.weightx = 1;
        panel.add(durationField, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0;
        panel.add(new JLabel("Day:"), c);
        String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        dayCombo = new JComboBox<>(days);
        c.gridx = 1; c.weightx = 1;
        panel.add(dayCombo, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0;
        panel.add(new JLabel("Intensity:"), c);
        lightButton  = new JRadioButton(Intensity.LIGHT.getDisplayName());
        mediumButton = new JRadioButton(Intensity.MEDIUM.getDisplayName());
        hardButton   = new JRadioButton(Intensity.HARD.getDisplayName());
        mediumButton.setSelected(true);
        ButtonGroup intensityGroup = new ButtonGroup();
        intensityGroup.add(lightButton);
        intensityGroup.add(mediumButton);
        intensityGroup.add(hardButton);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.add(lightButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);
        c.gridx = 1; c.weightx = 1;
        panel.add(radioPanel, c);

        c.gridx = 0; c.gridy = 4; c.weightx = 0;
        panel.add(new JLabel("Category:"), c);
        cardioCheckBox   = new JCheckBox("Cardio");
        strengthCheckBox = new JCheckBox("Strength");
        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkPanel.add(cardioCheckBox);
        checkPanel.add(strengthCheckBox);
        c.gridx = 1; c.weightx = 1;
        panel.add(checkPanel, c);

        c.gridx = 0; c.gridy = 5; c.weightx = 0;
        panel.add(new JLabel("Note:"), c);
        noteArea = new JTextArea(3, 10);
        noteArea.setLineWrap(true);
        c.gridx = 1; c.weightx = 1;
        panel.add(new JScrollPane(noteArea), c);

        addButton = new JButton("Add Workout");
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.weightx = 1;
        panel.add(addButton, c);
        addButton.addActionListener(e -> handleAddWorkout());

        return panel;
    }

    /**
     * Builds the right panel containing the workout list and statistics.
     *
     * @return the fully constructed list panel
     */
    private JPanel buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Logged Workouts"));

        listModel   = new DefaultListModel<>();
        workoutList = new JList<>(listModel);
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(workoutList), BorderLayout.CENTER);

        deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> handleDeleteWorkout());
        panel.add(deleteButton, BorderLayout.SOUTH);

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 2, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        totalCountLabel    = new JLabel("Total workouts: 0");
        totalMinutesLabel  = new JLabel("Total minutes: 0");
        cardioCountLabel   = new JLabel("Cardio sessions: 0");
        strengthCountLabel = new JLabel("Strength sessions: 0");
        statsPanel.add(totalCountLabel);
        statsPanel.add(totalMinutesLabel);
        statsPanel.add(cardioCountLabel);
        statsPanel.add(strengthCountLabel);

        JPanel rightWrapper = new JPanel(new BorderLayout(0, 8));
        rightWrapper.add(panel, BorderLayout.CENTER);
        rightWrapper.add(statsPanel, BorderLayout.SOUTH);
        return rightWrapper;
    }

    /**
     * Reads the form, validates the input, creates a new {@link Workout}, and
     * adds it to the log. Shows an error dialog if validation fails.
     */
    private void handleAddWorkout() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new InvalidInputException("Exercise name cannot be empty.");
            }

            String durationText = durationField.getText().trim();
            if (durationText.isEmpty()) {
                throw new InvalidInputException("Duration cannot be empty.");
            }

            int duration;
            try {
                duration = Integer.parseInt(durationText);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Duration must be a whole number (e.g. 30).");
            }
            if (duration <= 0) {
                throw new InvalidInputException("Duration must be greater than zero.");
            }

            String day = (String) dayCombo.getSelectedItem();

            Intensity intensity;
            if (lightButton.isSelected()) {
                intensity = Intensity.LIGHT;
            } else if (hardButton.isSelected()) {
                intensity = Intensity.HARD;
            } else {
                intensity = Intensity.MEDIUM;
            }

            boolean cardio   = cardioCheckBox.isSelected();
            boolean strength = strengthCheckBox.isSelected();
            String note      = noteArea.getText().trim();

            Workout workout = new Workout(name, duration, day, intensity, cardio, strength, note);
            workoutLog.addWorkout(workout);

            refreshList();
            refreshStats();
            clearForm();

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes the workout currently selected in the list.
     * Shows a warning if no workout is selected.
     */
    private void handleDeleteWorkout() {
        int selectedIndex = workoutList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a workout to delete.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete the selected workout?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            workoutLog.removeWorkout(selectedIndex);
            refreshList();
            refreshStats();
        }
    }

    /**
     * Saves all workouts to the binary data file.
     */
    private void saveWorkouts() {
        try {
            DataStorage.save(workoutLog.getWorkouts());
            JOptionPane.showMessageDialog(this, "Workouts saved successfully.",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save: " + ex.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads workouts from the binary data file, replacing the current list.
     */
    private void loadWorkouts() {
        try {
            workoutLog.getWorkouts().clear();
            workoutLog.getWorkouts().addAll(DataStorage.load());
            refreshList();
            refreshStats();
            JOptionPane.showMessageDialog(this, "Workouts loaded successfully.",
                    "Loaded", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Could not load: " + ex.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Exports all workouts to a plain text file.
     */
    private void exportWorkouts() {
        try {
            DataStorage.export(workoutLog.getWorkouts());
            JOptionPane.showMessageDialog(this, "Exported to workouts_export.txt",
                    "Exported", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not export: " + ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rebuilds the JList from the current contents of the workout log.
     */
    private void refreshList() {
        listModel.clear();
        for (Workout w : workoutLog.getWorkouts()) {
            listModel.addElement(w.toString());
        }
    }

    /**
     * Updates the statistics labels with the latest values from the workout log.
     */
    private void refreshStats() {
        totalCountLabel.setText("Total workouts: "       + workoutLog.getTotalCount());
        totalMinutesLabel.setText("Total minutes: "      + workoutLog.getTotalMinutes());
        cardioCountLabel.setText("Cardio sessions: "     + workoutLog.getCardioCount());
        strengthCountLabel.setText("Strength sessions: " + workoutLog.getStrengthCount());
    }

    /**
     * Resets all input fields to their default (empty / initial) state.
     */
    private void clearForm() {
        nameField.setText("");
        durationField.setText("");
        dayCombo.setSelectedIndex(0);
        mediumButton.setSelected(true);
        cardioCheckBox.setSelected(false);
        strengthCheckBox.setSelected(false);
        noteArea.setText("");
        nameField.requestFocus();
    }
}