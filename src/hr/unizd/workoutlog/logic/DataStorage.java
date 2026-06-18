package hr.unizd.workoutlog.logic;

import hr.unizd.workoutlog.model.Workout;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing workout data to the file system.
 * <p>
 * This class has two responsibilities:
 * <ul>
 *   <li>Binary serialization — saves and loads the full workout list so data
 *       persists between application runs.</li>
 *   <li>Text export — writes a human-readable summary of all workouts to a
 *       plain text file that the user can open in any text editor.</li>
 * </ul>
 * All methods are static because this class holds no state of its own —
 * it only operates on data passed to it.
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class DataStorage {

    /** File used for binary (serialized) storage of workout data. */
    private static final String DATA_FILE = "workouts.dat";

    /** File used for the human-readable text export. */
    private static final String EXPORT_FILE = "workouts_export.txt";

    /**
     * Saves the list of workouts to a binary file using Java serialization.
     * <p>
     * The entire list is written as a single object. The file is created if it
     * does not exist, or overwritten if it does.
     *
     * @param workouts the list of workouts to save
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void save(List<Workout> workouts) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(workouts);
        }
    }

    /**
     * Loads the list of workouts from the binary file.
     * <p>
     * If the file does not yet exist (e.g. on the very first run), an empty
     * list is returned so the application can start without errors.
     *
     * @return the saved list of workouts, or an empty list if no file exists
     * @throws IOException            if an I/O error occurs while reading the file
     * @throws ClassNotFoundException if the file contains an unrecognised class
     */
    public static List<Workout> load() throws IOException, ClassNotFoundException {
        if (!new File(DATA_FILE).exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (List<Workout>) ois.readObject();
        }
    }

    /**
     * Exports all workouts to a plain text file, one workout per line.
     * <p>
     * Each line uses the format produced by {@link Workout#toString()}, e.g.:
     * <pre>
     *   Running | Monday | 30 min | Medium | Cardio
     * </pre>
     * The resulting file can be opened in any text editor.
     *
     * @param workouts the list of workouts to export
     * @throws IOException if an I/O error occurs while writing the file
     */
    public static void export(List<Workout> workouts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPORT_FILE))) {
            for (Workout w : workouts) {
                writer.write(w.toString());
                writer.newLine();
            }
        }
    }
}
