package hr.unizd.workoutlog;

import hr.unizd.workoutlog.logic.WorkoutLog;
import hr.unizd.workoutlog.model.Intensity;
import hr.unizd.workoutlog.model.Workout;

/**
 * Entry point of the WorkoutLog application.
 * <p>
 * For now this class only confirms that the project is set up correctly
 * and that it compiles and runs. In a later step the {@code main} method
 * will load the saved workouts and open the main application window.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class Main {

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Temporary test — will be replaced with real startup logic later
        WorkoutLog log = new WorkoutLog();
        log.addWorkout(new Workout("Running", 30, "Monday", Intensity.MEDIUM, true, false, "Morning run"));
        log.addWorkout(new Workout("Push-ups", 20, "Wednesday", Intensity.HARD, false, true, ""));
        log.addWorkout(new Workout("Cycling", 45, "Friday", Intensity.LIGHT, true, false, "Easy ride"));

        for (Workout w : log.getWorkouts()) {
            System.out.println(w);
        }
        System.out.println("---");
        System.out.println("Total workouts : " + log.getTotalCount());
        System.out.println("Total minutes  : " + log.getTotalMinutes());
        System.out.println("Cardio sessions: " + log.getCardioCount());
        System.out.println("Strength sessions: " + log.getStrengthCount());
    }
}
