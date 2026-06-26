package hr.unizd.workoutlog.gui;

/**
 * Listens for the file actions triggered from the toolbar.
 * <p>
 * Each method represents one toolbar command. The toolbar knows only this
 * interface and never performs any file work itself — it simply reports which
 * command the user invoked, and the controller carries it out.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public interface ToolBarListener {

    /**
     * Called when the user requests to save all workouts.
     */
    public void onSave();

    /**
     * Called when the user requests to load workouts from disk.
     */
    public void onLoad();

    /**
     * Called when the user requests to export workouts to a text file.
     */
    public void onExport();
}