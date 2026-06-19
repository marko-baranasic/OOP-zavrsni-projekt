# 🏋️ WorkoutLog

> A simple desktop application for tracking personal workouts — built in Java with Swing.

![Java](https://img.shields.io/badge/Java-25-orange)
![GUI](https://img.shields.io/badge/GUI-Swing-blue)
![Dependencies](https://img.shields.io/badge/dependencies-none-success)

**WorkoutLog** is a lightweight desktop application that lets you record your workouts,
review them in a clear list, see summary statistics, and export everything to a readable
text file. It runs completely offline, requires no registration, and stores all data
locally on your computer.

---

## 📑 Table of Contents
- [About](#-about)
- [Features](#-features)
- [Technologies](#-technologies)
- [Project Structure](#-project-structure)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [Data Files](#-data-files)
- [Author](#-author)

---

## 📖 About

Many people who train regularly do not keep any record of their workouts, which makes it
hard to track progress or plan ahead. Most mobile apps are either too complex for everyday
use or require an account and an internet connection.

WorkoutLog solves this with a minimal, offline desktop tool focused on one thing:
**quickly logging and reviewing your workouts.**

---

## ✨ Features

- ➕ **Add workouts** with name, duration, day of the week, intensity, category (cardio / strength) and an optional note
- 📃 **View all workouts** in a clear, scrollable list
- 🗑️ **Delete** a selected workout (with confirmation)
- 📊 **Summary statistics** — total workouts, total minutes, cardio sessions, strength sessions
- 💾 **Save / Load** all data to a binary file using Java serialization, so it persists between runs
- 📤 **Export** to a human-readable `.txt` file you can open anywhere
- ⚠️ **Input validation** with clear error messages (empty name, invalid duration, etc.)

---

## 🛠️ Technologies

- **Java 25** (standard library only)
- **Swing** for the graphical user interface
- **Java Serialization** (`java.io`) for binary storage
- **Java Collections** (`ArrayList`) for in-memory data
- No external libraries, no build tools, no database

---

## 📂 Project Structure

```
OOP-zavrsni-projekt/
├── README.md
├── .gitignore
└── src/
    └── hr/
        └── unizd/
            └── workoutlog/
                ├── Main.java                 # Entry point
                ├── model/
                │   ├── Workout.java          # One workout (data model)
                │   └── Intensity.java        # Enum: LIGHT, MEDIUM, HARD
                ├── logic/
                │   ├── WorkoutLog.java        # Holds the list + statistics
                │   ├── DataStorage.java       # Save / load / export files
                │   └── InvalidInputException.java  # Custom exception
                └── gui/
                    └── MainWindow.java        # Swing user interface
```

---

## 🏗️ Architecture

The application follows a simple **three-layer architecture** with clear separation of
responsibilities:

| Layer | Class(es) | Responsibility |
|-------|-----------|----------------|
| **Model** | `Workout`, `Intensity` | Represent the data |
| **Logic** | `WorkoutLog`, `DataStorage`, `InvalidInputException` | Manage data, files, and validation |
| **Presentation** | `MainWindow` | Display the GUI and handle user actions |
| **Entry point** | `Main` | Load data and launch the window |

The model and logic layers know nothing about the GUI, which makes each class easy to
understand and test independently.

---

## 🚀 Getting Started

### Prerequisites
- **JDK 17 or newer** (the project was developed and tested on **JDK 25**)

### Run in IntelliJ IDEA (recommended)
1. Open the project folder in IntelliJ IDEA.
2. Make sure the `src` folder is marked as **Sources Root**
   (right-click `src` → *Mark Directory as* → *Sources Root* if needed).
3. Open `src/hr/unizd/workoutlog/Main.java`.
4. Click the green ▶ **Run** button.

### Run from the command line
From the project root folder:

```bash
# Compile (sourcepath lets the compiler find all classes automatically)
javac -d out -sourcepath src src/hr/unizd/workoutlog/Main.java

# Run
java -cp out hr.unizd.workoutlog.Main
```

---

## 💡 Usage

1. **Add a workout** — fill in the form on the left (exercise name, duration in minutes,
   day, intensity, category, optional note) and click **Add Workout**.
2. **Review** — every workout appears in the list on the right, with the summary updating
   automatically below it.
3. **Delete** — select a workout in the list and click **Delete Selected**.
4. **Save** — click **Save** (toolbar or *File → Save*) to store your workouts permanently.
5. **Load** — click **Load** to restore previously saved workouts.
6. **Export** — click **Export** to write a readable `workouts_export.txt` file.

**Example list entry:**
```
Running | Monday | 30 min | Medium | Cardio
```

---

## 💾 Data Files

| File | Format | Purpose |
|------|--------|---------|
| `workouts.dat` | Binary (serialized) | Permanent storage of all workouts between runs |
| `workouts_export.txt` | Plain text | Human-readable export for sharing or printing |

Both files are created automatically in the folder from which the application is run.

---

## 👤 Author

**Marko Baranašić**
Course: *Osnove objektnog programiranja*
Smjer Informacijske tehnologije — Sveučilište u Zadru
Mentor: doc. dr. sc. Ante Panjkota

---

> This project was created for educational purposes as a final project for the
> *Object-Oriented Programming Fundamentals* course.
