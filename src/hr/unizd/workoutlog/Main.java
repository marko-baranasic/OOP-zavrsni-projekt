package hr.unizd.workoutlog;

import hr.unizd.workoutlog.gui.MainWindow;
import hr.unizd.workoutlog.logic.DataStorage;
import hr.unizd.workoutlog.logic.WorkoutLog;

import javax.swing.*;

/**
 * Entry point of the WorkoutLog application.
 * <p>
 * Loads any previously saved workouts from disk, then opens the main
 * application window on the Swing Event Dispatch Thread (EDT).
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class Main {

    /**
     * Private constructor to prevent instantiation.
     * This class only provides the static {@code main} entry point.
     */
    private Main() {
    }

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        WorkoutLog workoutLog = new WorkoutLog();

        try {
            workoutLog.getWorkouts().addAll(DataStorage.load());
        } catch (Exception e) {
            // If loading fails (e.g. corrupted file), start with an empty log.
            System.err.println("Could not load saved data: " + e.getMessage());
        }

        final WorkoutLog finalLog = workoutLog;
        SwingUtilities.invokeLater(() -> new MainWindow(finalLog));
    }
}
