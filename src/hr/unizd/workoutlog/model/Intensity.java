package hr.unizd.workoutlog.model;

/**
 * Represents the intensity level of a workout.
 * <p>
 * Using an enum instead of a plain String ensures that only valid intensity
 * values can be used throughout the application (type safety). Each constant
 * carries a human-readable display name used in the user interface.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public enum Intensity {

    /** Low-effort workout, suitable for warm-up or recovery sessions. */
    LIGHT("Light"),

    /** Moderate-effort workout, the most common training level. */
    MEDIUM("Medium"),

    /** High-effort workout requiring maximum exertion. */
    HARD("Hard");

    // The label shown to the user in the GUI (e.g. in JRadioButtons).
    private final String displayName;

    /**
     * Creates an Intensity constant with the given display name.
     * <p>
     * Enum constructors are always private — Java enforces this automatically.
     * The constructor runs once per constant when the class is first loaded.
     *
     * @param displayName human-readable label for this intensity level
     */
    Intensity(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable name of this intensity level.
     * <p>
     * Used by the GUI to label radio buttons and by {@code Workout.toString()}
     * to produce readable output in the workout list.
     *
     * @return display name (e.g. "Light", "Medium", "Hard")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the display name, so that an {@code Intensity} value can be
     * placed directly into a {@code JComboBox} or printed without extra code.
     *
     * @return same as {@link #getDisplayName()}
     */
    @Override
    public String toString() {
        return displayName;
    }
}
