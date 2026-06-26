package hr.unizd.workoutlog.gui;

import java.util.EventListener;

/**
 * Listens for the "add workout" action coming from the input form panel.
 * <p>
 * This is the decoupling point between the input panel (the View) and the
 * controller. The panel knows only this interface — it reports <em>what</em>
 * happened (the user wants to add a workout) by calling {@link #onAddWorkout},
 * and the controller decides <em>how</em> to handle it.
 * <p>
 * It extends {@link EventListener} because it carries a {@link WorkoutFormEvent}
 * object, following the standard Java event-listener model.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public interface WorkoutFormListener extends EventListener { // prenosi  WorkoutFormEvent(koji je EventObject) pa je idiomatski a bude EventListener

    /**
     * Called when the user fills in the form and presses "Add Workout".
     *
     * @param event the form data the user entered
     */
    public void onAddWorkout(WorkoutFormEvent event);
}