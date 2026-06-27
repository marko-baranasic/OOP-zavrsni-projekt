package hr.unizd.workoutlog.model;

import java.io.Serializable;

/**
 * Represents a single workout entry in the WorkoutLog application.
 * <p>
 * This class is a model — it holds data about one workout session and protects
 * the integrity of that data. Every required field is validated in its setter,
 * and the constructor delegates to those same setters, so a {@code Workout}
 * object can never exist in an invalid state (encapsulation).
 * <p>
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
     * <p>
     * The constructor does not assign the fields directly — it calls the
     * validating setters, so the exact same rules apply whether a value is set
     * at construction time or changed later.
     *
     * @param exerciseName    name of the exercise (must not be empty)
     * @param durationMinutes duration in minutes (must be greater than zero)
     * @param dayOfWeek       day of the week (must not be empty)
     * @param intensity       intensity level (must not be null)
     * @param cardio          true if this workout has a cardio component
     * @param strength        true if this workout has a strength component
     * @param note            optional note; may be empty, but not null
     * @throws IllegalArgumentException if any value breaks a validation rule
     */
    public Workout(String exerciseName, int durationMinutes, String dayOfWeek,
                   Intensity intensity, boolean cardio, boolean strength, String note) {
        setExerciseName(exerciseName);     // svaki setter sam provjerava ispravnost,
        setDurationMinutes(durationMinutes); // pa konstruktor ne mora ponavljati provjere
        setDayOfWeek(dayOfWeek);
        setIntensity(intensity);
        setCardio(cardio);
        setStrength(strength);
        setNote(note);
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
     * Sets the exercise name.
     *
     * @param exerciseName name of the exercise (must not be null or blank)
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setExerciseName(String exerciseName) {
        // Obavezno polje: ne smije biti null ni prazno (ni samo razmaci).
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be empty.");
        }
        this.exerciseName = exerciseName.trim();
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
     * Sets the workout duration in minutes.
     *
     * @param durationMinutes duration in minutes (must be greater than zero)
     * @throws IllegalArgumentException if the duration is zero or negative
     */
    public void setDurationMinutes(int durationMinutes) {
        // Brojčani raspon: trajanje mora biti pozitivan broj minuta.
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        this.durationMinutes = durationMinutes;
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
     * Sets the day of the week.
     *
     * @param dayOfWeek day of the week (must not be null or blank)
     * @throws IllegalArgumentException if the day is null or empty
     */
    public void setDayOfWeek(String dayOfWeek) {
        // Obavezno polje: dan mora biti zadan.
        if (dayOfWeek == null || dayOfWeek.trim().isEmpty()) {
            throw new IllegalArgumentException("Day of week cannot be empty.");
        }
        this.dayOfWeek = dayOfWeek.trim();
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
     * Sets the intensity level.
     *
     * @param intensity intensity level (must not be null)
     * @throws IllegalArgumentException if the intensity is null
     */
    public void setIntensity(Intensity intensity) {
        // Obavezno polje: intenzitet mora biti odabran.
        if (intensity == null) {
            throw new IllegalArgumentException("Intensity must be selected.");
        }
        this.intensity = intensity;
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
     * Sets the cardio flag.
     *
     * @param cardio true if this workout has a cardio component
     */
    public void setCardio(boolean cardio) {
        // Boolean nema nevažeću vrijednost (true/false su oba ispravna), pa nema provjere.
        this.cardio = cardio;
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
     * Sets the strength flag.
     *
     * @param strength true if this workout has a strength component
     */
    public void setStrength(boolean strength) {
        this.strength = strength;
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
     * Sets the optional note.
     * <p>
     * The note is optional, so an empty value is allowed. A {@code null} is
     * stored as an empty string so the rest of the program never has to guard
     * against {@code null} when reading the note.
     *
     * @param note note text; may be empty, {@code null} is treated as empty
     */
    public void setNote(String note) {
        // Neobavezno polje: prazno je dopušteno, ali null spremamo kao "".
        if (note == null) {
            this.note = "";
        } else {
            this.note = note.trim();
        }
    }

    /**
     * Returns a concise, human-readable summary of this workout.
     * <p>
     * This is the text displayed in the {@code JList} on the right panel and
     * written to the text export. The format is:
     * <pre>
     *   Running | Monday | 30 min | Medium | Cardio | Note: felt great
     * </pre>
     * The category part shows "Cardio", "Strength", "Cardio + Strength",
     * or "None" depending on which boxes were checked. The note part is
     * appended only when a note was actually entered.
     *
     * @return formatted one-line summary of the workout
     */
    @Override
    public String toString() {
        String category = buildCategory();
        String summary = exerciseName + " | " + dayOfWeek + " | " + durationMinutes
                + " min | " + intensity.getDisplayName() + " | " + category;

        // Bilješka je neobavezna — dodaj je na kraj samo ako je upisana.
        if (!note.isEmpty()) {
            summary = summary + " | Note: " + note;
        }
        return summary;
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