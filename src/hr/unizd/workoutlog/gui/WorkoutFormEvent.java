package hr.unizd.workoutlog.gui;

import hr.unizd.workoutlog.model.Intensity;

import java.util.EventObject; //

/**
 * Carries the data the user entered in the input form when the "Add Workout"
 * button is pressed.
 * <p>
 * This is the WorkoutLog equivalent of the classic form-event objects used in
 * Swing MVC examples (such as {@code LeftFormEvent} / {@code RightFormEvent}).
 * The input panel reads its own components, packs the values into one of these
 * objects, and hands it to its listener. That way the panel never has to expose
 * its internal components to the outside world (encapsulation).
 * <p>
 * It extends {@link EventObject} so the data is tied to the source component
 * (the add button) that triggered the event.
 * <p>
 * <b>Note:</b> this is a transport object, not a domain class. It performs no
 * validation — the values are passed on exactly as read from the form, and the
 * domain rules are enforced later by {@link hr.unizd.workoutlog.model.Workout}.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class WorkoutFormEvent extends EventObject {

    /** Exercise name exactly as typed by the user. */
    private String exerciseName;

    /** Duration as raw text — parsed to a number later by the controller. */
    private String duration;

    /** Day of the week chosen in the drop-down. */
    private String day;

    /** Intensity level chosen with the radio buttons. */
    private Intensity intensity;

    /** Whether the "Cardio" box was checked. */
    private boolean cardio;

    /** Whether the "Strength" box was checked. */
    private boolean strength;

    /** Optional note text. */
    private String note;

    /**
     * Creates a new form event.
     *
     * @param source the component that triggered the event (the add button)
     */
    public WorkoutFormEvent(Object source) {
        super(source);
    }

    /**
     * Returns the exercise name as typed.
     *
     * @return exercise name
     */
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Sets the exercise name.
     *
     * @param exerciseName exercise name as typed
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * Returns the duration as raw text (not yet parsed to a number).
     *
     * @return duration text
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration text.
     *
     * @param duration duration as typed
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Returns the chosen day of the week.
     *
     * @return day of the week
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the day of the week.
     *
     * @param day chosen day of the week
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Returns the chosen intensity level.
     *
     * @return intensity level
     */
    public Intensity getIntensity() {
        return intensity;
    }

    /**
     * Sets the intensity level.
     *
     * @param intensity chosen intensity level
     */
    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns whether the cardio box was checked.
     *
     * @return true if cardio was selected
     */
    public boolean isCardio() {
        return cardio;
    }

    /**
     * Sets the cardio flag.
     *
     * @param cardio true if cardio was selected
     */
    public void setCardio(boolean cardio) {
        this.cardio = cardio;
    }

    /**
     * Returns whether the strength box was checked.
     *
     * @return true if strength was selected
     */
    public boolean isStrength() {
        return strength;
    }

    /**
     * Sets the strength flag.
     *
     * @param strength true if strength was selected
     */
    public void setStrength(boolean strength) {
        this.strength = strength;
    }

    /**
     * Returns the optional note text.
     *
     * @return note text
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note text.
     *
     * @param note optional note text
     */
    public void setNote(String note) {
        this.note = note;
    }
}