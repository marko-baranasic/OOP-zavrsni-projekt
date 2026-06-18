package hr.unizd.workoutlog.logic;

/**
 * Thrown when the user provides invalid input when adding a workout.
 * <p>
 * Extending {@link Exception} (checked exception) forces the caller to
 * explicitly handle the error with a try-catch block, making error handling
 * visible and intentional throughout the application.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class InvalidInputException extends Exception {

    /**
     * Creates a new InvalidInputException with the given error message.
     *
     * @param message description of what input was invalid
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
