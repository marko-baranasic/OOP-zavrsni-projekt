package hr.unizd.workoutlog.logic;

import hr.unizd.workoutlog.model.Workout;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of all recorded workouts.
 * <p>
 * This class is the logical core of the application. It holds an
 * {@code ArrayList} of {@link Workout} objects and provides methods for
 * adding, removing, and retrieving workouts, as well as computing basic
 * summary statistics over the entire collection.
 * <p>
 * This class knows nothing about files or the GUI — it only manages data.
 * That separation of responsibilities makes each class easier to understand
 * and test independently.
 *
 * @author Marko Baranasic
 * @version 1.0
 * @see Workout
 */
public class WorkoutLog {

    /** Internal storage for all workout entries. */
    private ArrayList<Workout> workouts;

    /**
     * Creates a new, empty WorkoutLog.
     * The internal list is initialised here so it is always ready to use.
     */
    public WorkoutLog() {
        workouts = new ArrayList<>();
    }

    /**
     * Adds a workout to the log.
     *
     * @param workout the workout to add (must not be null)
     */
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    /**
     * Removes the workout at the specified position in the list.
     *
     * @param index zero-based index of the workout to remove
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void removeWorkout(int index) {
        workouts.remove(index);
    }

    /**
     * Returns the list of all recorded workouts.
     * <p>
     * The return type is {@code List} rather than {@code ArrayList} to hide
     * the concrete implementation — callers only need to know it is a list.
     *
     * @return list of workouts (may be empty, never null)
     */
    public List<Workout> getWorkouts() {
        return workouts;
    }

    /**
     * Returns the total number of workouts in the log.
     *
     * @return number of workouts
     */
    public int getTotalCount() {
        return workouts.size();
    }

    /**
     * Returns the sum of durations of all workouts in minutes.
     *
     * @return total minutes across all workouts, or 0 if the log is empty
     */
    public int getTotalMinutes() {
        int total = 0;
        for (Workout w : workouts) {
            total += w.getDurationMinutes();
        }
        return total;
    }

    /**
     * Returns the number of workouts that include a cardio component.
     *
     * @return count of cardio workouts
     */
    public int getCardioCount() {
        int count = 0;
        for (Workout w : workouts) {
            if (w.isCardio()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the number of workouts that include a strength component.
     *
     * @return count of strength workouts
     */
    public int getStrengthCount() {
        int count = 0;
        for (Workout w : workouts) {
            if (w.isStrength()) {
                count++;
            }
        }
        return count;
    }
}
