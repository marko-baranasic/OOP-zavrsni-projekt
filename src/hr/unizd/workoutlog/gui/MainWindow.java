package hr.unizd.workoutlog.gui;

import hr.unizd.workoutlog.logic.DataStorage;
import hr.unizd.workoutlog.logic.InvalidInputException;
import hr.unizd.workoutlog.logic.WorkoutLog;
import hr.unizd.workoutlog.model.Workout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The main application window of WorkoutLog — the controller of the MVC design.
 * <p>
 * It owns the model ({@link WorkoutLog}) and assembles the View (the input
 * panel, the list panel and the toolbar). It implements every listener
 * interface and registers itself with each panel, so all user actions flow
 * through this single point. When an action changes the model, the controller
 * refreshes the affected part of the View.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class MainWindow extends JFrame
        implements WorkoutFormListener, WorkoutListListener, ToolBarListener {

    /** The workout log that holds all workout data (the model). */
    private WorkoutLog workoutLog;

    /** Left View: the input form. */
    private InputFormPanel inputPanel;
    /** Right View: the list of workouts and the statistics. */
    private WorkoutListPanel listPanel;
    /** Top View: the file toolbar. */
    private WorkoutToolBar toolBar;

    /**
     * Creates and displays the main application window.
     *
     * @param workoutLog the workout log to display and modify
     */
    public MainWindow(WorkoutLog workoutLog) {
        this.workoutLog = workoutLog;

        setTitle("WorkoutLog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null); // prozor na sredini ekrana

        createComponents();   // 1) napravi View komponente
        registerListeners();  // 2) kontroler se prijavi kao listener svima
        buildMenuBar();       // 3) izbornik
        layoutComponents();   // 4) rasporedi traku + glavni panel

        refreshView();        // 5) početni prikaz iz modela

        setVisible(true);
    }

    /**
     * Creates the three View panels.
     */
    private void createComponents() {
        inputPanel = new InputFormPanel();
        listPanel = new WorkoutListPanel();
        toolBar = new WorkoutToolBar();
    }

    /**
     * Registers this controller as the listener of every panel.
     */
    private void registerListeners() {
        inputPanel.setFormListener(this);
        listPanel.setListListener(this);
        toolBar.setToolBarListener(this);
    }

    /**
     * Builds the menu bar. Each item delegates to the same controller methods
     * the toolbar uses, via anonymous listeners (no lambdas).
     */
    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exportItem = new JMenuItem("Export");
        JMenuItem exitItem = new JMenuItem("Exit");

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoad();
            }
        });
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExport();
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfo("WorkoutLog v1.0\nAuthor: Marko Baranasic", "About");
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Assembles the toolbar (north) and the two main panels (center).
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());

        add(toolBar, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // lijevo | desno
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel);
        mainPanel.add(listPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    // ----------------------------------------------------------------
    //  Listener implementations — the controller's reaction to events
    // ----------------------------------------------------------------

    /**
     * Handles the "add workout" event from the input form.
     * <p>
     * Parses the duration, builds a {@link Workout} (whose setters validate the
     * domain rules), adds it to the model, refreshes the view and clears the
     * form. Any validation problem is shown to the user as a dialog.
     *
     * @param event the data entered in the form
     */
    @Override
    public void onAddWorkout(WorkoutFormEvent event) {
        try {
            // 1) Parsiranje oblika unosa (GUI sloj) -> InvalidInputException.
            int duration = parseDuration(event.getDuration());

            // 2) Stvaranje domenskog objekta; raspone/obavezna polja
            //    validira Workout -> IllegalArgumentException.
            Workout workout = new Workout(
                    event.getExerciseName(),
                    duration,
                    event.getDay(),
                    event.getIntensity(),
                    event.isCardio(),
                    event.isStrength(),
                    event.getNote());

            // 3) Promjena modela.
            workoutLog.addWorkout(workout);

            // 4) Osvježi prikaz i resetiraj formu.
            refreshView();          // zadatak A: prikaz se ažurira
            inputPanel.clearForm(); // zadatak B: reset forme nakon uspjeha

        } catch (InvalidInputException | IllegalArgumentException ex) {
            showError(ex.getMessage(), "Input Error");
        }
    }

    /**
     * Handles the "delete workout" event from the list panel.
     *
     * @param index position of the workout to delete, or -1 if none selected
     */
    @Override
    public void onDeleteWorkout(int index) {
        if (index == -1) {
            showError("Please select a workout to delete.", "No Selection");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete the selected workout?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            workoutLog.removeWorkout(index);
            refreshView();
        }
    }

    /**
     * Saves all workouts to the binary data file.
     */
    @Override
    public void onSave() {
        try {
            DataStorage.save(workoutLog.getWorkouts());
            showInfo("Workouts saved successfully.", "Saved");
        } catch (IOException ex) {
            showError("Could not save: " + ex.getMessage(), "Save Error");
        }
    }

    /**
     * Loads workouts from the binary data file, replacing the current list.
     */
    @Override
    public void onLoad() {
        try {
            workoutLog.getWorkouts().clear();
            workoutLog.getWorkouts().addAll(DataStorage.load());
            refreshView();
            showInfo("Workouts loaded successfully.", "Loaded");
        } catch (IOException | ClassNotFoundException ex) {
            showError("Could not load: " + ex.getMessage(), "Load Error");
        }
    }

    /**
     * Exports all workouts to a plain text file.
     */
    @Override
    public void onExport() {
        try {
            DataStorage.export(workoutLog.getWorkouts());
            showInfo("Exported to workouts_export.txt", "Exported");
        } catch (IOException ex) {
            showError("Could not export: " + ex.getMessage(), "Export Error");
        }
    }

    // ----------------------------------------------------------------
    //  Private helpers
    // ----------------------------------------------------------------

    /**
     * Parses the raw duration text into a positive-friendly integer.
     * <p>
     * This is form-level validation (is it present, is it a number?). The rule
     * that the duration must be greater than zero lives in the {@link Workout}
     * setter, not here.
     *
     * @param text the raw duration text
     * @return the parsed number of minutes
     * @throws InvalidInputException if the text is empty or not a whole number
     */
    private int parseDuration(String text) throws InvalidInputException {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            throw new InvalidInputException("Duration cannot be empty.");
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Duration must be a whole number (e.g. 30).");
        }
    }

    /**
     * Refreshes the list and the statistics from the current model state.
     */
    private void refreshView() {
        listPanel.showWorkouts(workoutLog.getWorkouts());
        listPanel.showStats(
                workoutLog.getTotalCount(),
                workoutLog.getTotalMinutes(),
                workoutLog.getCardioCount(),
                workoutLog.getStrengthCount());
    }

    /**
     * Shows an information dialog.
     *
     * @param message the message text
     * @param title   the dialog title
     */
    private void showInfo(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error / warning dialog.
     *
     * @param message the message text
     * @param title   the dialog title
     */
    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title,
                JOptionPane.ERROR_MESSAGE);
    }
}