package hr.unizd.workoutlog.gui;

/**
 * Listens for the "delete workout" action coming from the workout list panel.
 * <p>
 * The list panel reports only the position of the workout the user wants to
 * remove; the controller decides what to do with that information (confirm,
 * remove from the model, refresh the view).
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public interface WorkoutListListener {

    /**
     * Called when the user selects a workout and presses "Delete Selected".
     *
     * @param index zero-based position of the workout in the list
     */
    public void onDeleteWorkout(int index);
}