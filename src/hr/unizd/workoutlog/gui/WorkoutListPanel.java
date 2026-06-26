package hr.unizd.workoutlog.gui;

import hr.unizd.workoutlog.model.Workout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * The right-hand View component: shows the list of all logged workouts and a
 * statistics summary below it, plus a button to delete the selected workout.
 * <p>
 * The controller fills this panel through {@link #showWorkouts(List)} and
 * {@link #showStats(int, int, int, int)}. When the user presses "Delete
 * Selected", the panel reads which row is selected and reports only that index
 * to its {@link WorkoutListListener} — it never decides whether or how the
 * workout is actually removed (delegation).
 *
 * @author Marko Baranasic
 * @version 1.0
 */
public class WorkoutListPanel extends JPanel {

    /** Model backing the workout list (holds the displayed lines). */
    private DefaultListModel<String> listModel;
    /** List component showing all logged workouts. */
    private JList<String> workoutList;
    /** Button that requests deletion of the selected workout. */
    private JButton deleteButton;

    /** Label showing the total number of workouts. */
    private JLabel totalCountLabel;
    /** Label showing the total minutes across all workouts. */
    private JLabel totalMinutesLabel;
    /** Label showing the number of cardio workouts. */
    private JLabel cardioCountLabel;
    /** Label showing the number of strength workouts. */
    private JLabel strengthCountLabel;

    /** Listener notified when the user wants to delete a workout. */
    private WorkoutListListener listListener;

    /**
     * Builds the list panel.
     */
    public WorkoutListPanel() {
        createComponents();
        layoutComponents();
        activateDelete();
    }

    /**
     * Creates the list, the delete button and the statistics labels.
     */
    private void createComponents() {
        listModel = new DefaultListModel<>();
        workoutList = new JList<>(listModel);
        // Dozvoljen odabir samo jedne stavke (ne više treninga odjednom).
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        deleteButton = new JButton("Delete Selected");

        totalCountLabel = new JLabel("Total workouts: 0");
        totalMinutesLabel = new JLabel("Total minutes: 0");
        cardioCountLabel = new JLabel("Cardio sessions: 0");
        strengthCountLabel = new JLabel("Strength sessions: 0");
    }

    /**
     * Lays out the list (with delete button) above the statistics summary.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout(0, 8)); // 8 px vertikalnog razmaka

        // Gornji dio: lista treninga + dugme za brisanje.
        JPanel listPanel = new JPanel(new BorderLayout(0, 8));
        listPanel.setBorder(BorderFactory.createTitledBorder("Logged Workouts"));
        listPanel.add(new JScrollPane(workoutList), BorderLayout.CENTER); // scroll po potrebi
        listPanel.add(deleteButton, BorderLayout.SOUTH);

        // Donji dio: sažetak statistike.
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 2, 2));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        statsPanel.add(totalCountLabel);
        statsPanel.add(totalMinutesLabel);
        statsPanel.add(cardioCountLabel);
        statsPanel.add(strengthCountLabel);

        add(listPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);
    }

    /**
     * Attaches the click handler to the delete button.
     * <p>
     * The handler reads which row is selected and reports that index to the
     * listener. It is an anonymous {@link ActionListener} (not a lambda).
     */
    private void activateDelete() {
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Panel pročita SVOJ odabir i javi samo indeks (može biti -1
                // ako ništa nije odabrano). Odluku donosi kontroler.
                int index = workoutList.getSelectedIndex();
                if (listListener != null) {
                    listListener.onDeleteWorkout(index);
                }
            }
        });
    }

    /**
     * Registers the listener notified about delete requests.
     *
     * @param listener the listener (the controller)
     */
    public void setListListener(WorkoutListListener listener) {
        this.listListener = listener;
    }

    /**
     * Replaces the displayed list with the given workouts.
     *
     * @param workouts the workouts to display (each shown via its toString)
     */
    public void showWorkouts(List<Workout> workouts) {
        listModel.clear();
        for (Workout w : workouts) {
            listModel.addElement(w.toString());
        }
    }

    /**
     * Updates the statistics labels with the given values.
     *
     * @param totalCount    total number of workouts
     * @param totalMinutes  total minutes across all workouts
     * @param cardioCount   number of cardio workouts
     * @param strengthCount number of strength workouts
     */
    public void showStats(int totalCount, int totalMinutes,
                          int cardioCount, int strengthCount) {
        totalCountLabel.setText("Total workouts: " + totalCount);
        totalMinutesLabel.setText("Total minutes: " + totalMinutes);
        cardioCountLabel.setText("Cardio sessions: " + cardioCount);
        strengthCountLabel.setText("Strength sessions: " + strengthCount);
    }
}