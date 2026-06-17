package hr.unizd.workoutlog.model;

import java.io.Serializable;

/**
 * Represents a single workout entry in the WorkoutLog application.
 * <p>
 * This class is a model — it holds data about one workout session and provides
 * controlled access to that data through getter methods (encapsulation).
 * It implements {@link Serializable} so that workout objects can be saved to
 * and loaded from a binary file using Java's built-in serialization mechanism.
 *
 * @author Marko Baranasic
 * @version 1.0
 * @see Intensity
 */
public class Workout implements Serializable {

    /**
     * Version identifier for serialization.
     * If we change the class in the future, updating this number prevents
     * the application from silently reading corrupt data from old save files.
     */
    private static final long serialVersionUID = 1L;

    /** Name of the exercise (e.g. "Running", "Push-ups"). */
    private String exerciseName;

    /** Duration of the workout in minutes. */
    private int durationMinutes;

    /** Day of the week when the workout took place (e.g. "Monday"). */
    private String dayOfWeek;

    /** Intensity level of the workout. */
    private Intensity intensity;

    /** True if this workout includes a cardio component. */
    private boolean cardio;

    /** True if this workout includes a strength component. */
    private boolean strength;

    /** Optional free-text note about the workout. */
    private String note;

    /**
     * Creates a new Workout with all required information.
     *
     * @param exerciseName    name of the exercise (must not be empty)
     * @param durationMinutes duration in minutes (must be greater than zero)
     * @param dayOfWeek       day of the week (e.g. "Monday")
     * @param intensity       intensity level (Light, Medium, or Hard)
     * @param cardio          true if this workout has a cardio component
     * @param strength        true if this workout has a strength component
     * @param note            optional note; may be an empty string, not null
     */
    public Workout(String exerciseName, int durationMinutes, String dayOfWeek,
                   Intensity intensity, boolean cardio, boolean strength, String note) {
        this.exerciseName = exerciseName;
        this.durationMinutes = durationMinutes;
        this.dayOfWeek = dayOfWeek;
        this.intensity = intensity;
        this.cardio = cardio;
        this.strength = strength;
        this.note = note;
    }

    /**
     * Returns the name of the exercise.
     *
     * @return exercise name
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Returns the duration of the workout in minutes.
     *
     * @return duration in minutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * Returns the day of the week when the workout took place.
     *
     * @return day of the week (e.g. "Monday")
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Returns the intensity level of the workout.
     *
     * @return intensity level
     * @see Intensity
     */
    public Intensity getIntensity() {
        return intensity;
    }

    /**
     * Returns whether this workout includes a cardio component.
     *
     * @return true if cardio
     */
    public boolean isCardio() {
        return cardio;
    }

    /**
     * Returns whether this workout includes a strength component.
     *
     * @return true if strength
     */
    public boolean isStrength() {
        return strength;
    }

    /**
     * Returns the optional note attached to this workout.
     *
     * @return note text, or an empty string if no note was provided
     */
    public String getNote() {
        return note;
    }

    /**
     * Returns a concise, human-readable summary of this workout.
     * <p>
     * This is the text displayed in the {@code JList} on the right panel
     * of the application. The format is:
     * <pre>
     *   Running | Monday | 30 min | Medium | Cardio
     * </pre>
     * The category part shows "Cardio", "Strength", "Cardio + Strength",
     * or "None" depending on which boxes were checked.
     *
     * @return formatted one-line summary of the workout
     */
    @Override
    public String toString() {
        String category = buildCategory();
        return exerciseName + " | " + dayOfWeek + " | " + durationMinutes
                + " min | " + intensity.getDisplayName() + " | " + category;
    }

    /**
     * Builds the category label based on the cardio and strength flags.
     * Extracted into its own method to keep {@link #toString()} readable.
     *
     * @return category string: "Cardio", "Strength", "Cardio + Strength", or "None"
     */
    private String buildCategory() {
        if (cardio && strength) {
            return "Cardio + Strength";
        } else if (cardio) {
            return "Cardio";
        } else if (strength) {
            return "Strength";
        } else {
            return "None";
        }
    }
}
