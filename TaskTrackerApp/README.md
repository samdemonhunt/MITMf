# Task Tracker - Fully Offline Android App

A fully offline Android mobile application for task management and progress tracking. Built with modern Android development practices using Kotlin, Room Database, and MVVM architecture.

## Features

### Core Functionality
- ✅ **Create Tasks**: Add new tasks with title, description, priority, and initial progress
- 📊 **Track Progress**: Visual progress bars with percentage tracking (0-100%)
- ✏️ **Edit Tasks**: Update task details, progress, and priority at any time
- ✓ **Complete Tasks**: Mark tasks as completed with checkbox
- 🗑️ **Delete Tasks**: Remove tasks with confirmation dialog
- 🎯 **Priority Levels**: Four priority levels (Low, Medium, High, Urgent) with color coding

### Organization
- 📑 **Three Tab Views**:
  - All Tasks: View all tasks regardless of status
  - Active: View only incomplete tasks
  - Completed: View finished tasks
- 🔄 **Real-time Updates**: Automatic UI updates when tasks change
- 📱 **Material Design**: Clean, modern UI following Material Design guidelines

### Offline Support
- 💾 **Room Database**: All data stored locally using SQLite
- 🔒 **No Internet Required**: Fully functional without network connection
- 🚀 **Fast Performance**: Instant data access and updates

## Technical Stack

### Architecture
- **MVVM Pattern**: Clean separation of concerns
  - Model: Task data class with Room annotations
  - View: Activities, Fragments, XML layouts
  - ViewModel: TaskViewModel with LiveData

### Core Technologies
- **Language**: Kotlin
- **Database**: Room (SQLite wrapper)
- **UI Components**:
  - Material Design Components
  - RecyclerView with DiffUtil
  - ViewPager2 with TabLayout
  - Floating Action Button
- **Async**: Kotlin Coroutines with LiveData
- **Architecture Components**:
  - ViewModel
  - LiveData
  - Room Database

## Project Structure

```
TaskTrackerApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/tasktracker/
│   │       │   ├── data/
│   │       │   │   ├── Task.kt              # Task entity
│   │       │   │   ├── TaskDao.kt           # Database operations
│   │       │   │   ├── TaskDatabase.kt      # Room database
│   │       │   │   ├── TaskRepository.kt    # Data layer
│   │       │   │   └── Converters.kt        # Type converters
│   │       │   ├── ui/
│   │       │   │   ├── MainActivity.kt      # Main activity with tabs
│   │       │   │   ├── TaskListFragment.kt  # Fragment for task lists
│   │       │   │   └── TaskAdapter.kt       # RecyclerView adapter
│   │       │   └── viewmodel/
│   │       │       └── TaskViewModel.kt     # ViewModel
│   │       ├── res/
│   │       │   ├── layout/                  # XML layouts
│   │       │   ├── values/                  # Strings, colors, themes
│   │       │   └── drawable/                # Icons and drawables
│   │       └── AndroidManifest.xml
│   ├── build.gradle                         # App dependencies
│   └── proguard-rules.pro                   # ProGuard rules
├── build.gradle                             # Project build config
├── settings.gradle                          # Gradle settings
├── gradle.properties                        # Gradle properties
└── README.md                                # This file
```

## Data Model

### Task Entity
```kotlin
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val progress: Int = 0,              // 0-100
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.MEDIUM
)

enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}
```

## Building the App

### Prerequisites
- Android Studio (latest version recommended)
- Android SDK 24 or higher (Android 7.0+)
- JDK 17

### Steps to Build

1. **Clone or Copy the Project**
   ```bash
   cd TaskTrackerApp
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to TaskTrackerApp directory
   - Wait for Gradle sync to complete

3. **Build the APK**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - APK will be generated in `app/build/outputs/apk/debug/`

4. **Run on Device/Emulator**
   - Connect Android device or start emulator
   - Click Run button (green triangle) in Android Studio
   - Select target device

### Gradle Commands
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Run tests
./gradlew test
```

## Usage Guide

### Adding a Task
1. Tap the floating "+" button at the bottom right
2. Enter task title (required)
3. Add description (optional)
4. Select priority level
5. Set initial progress using slider
6. Tap "SAVE"

### Editing a Task
1. Tap on any task card
2. Modify any field
3. Tap "SAVE" to update

### Updating Progress
1. Tap on a task to open edit dialog
2. Adjust the progress slider
3. Save changes

### Completing a Task
1. Check the checkbox on the task card
2. Task will move to "Completed" tab
3. Uncheck to mark as active again

### Deleting a Task
1. Tap the delete icon on task card
2. Confirm deletion in dialog
3. Task will be permanently removed

### Viewing Tasks
- **All Tasks Tab**: See every task
- **Active Tab**: See only incomplete tasks sorted by priority
- **Completed Tab**: See finished tasks

## Key Features Explained

### Priority System
Tasks have four priority levels with color coding:
- **LOW** (Green): Low priority tasks
- **MEDIUM** (Blue): Default priority
- **HIGH** (Orange): Important tasks
- **URGENT** (Red): Critical tasks requiring immediate attention

### Progress Tracking
- Progress is tracked as percentage (0-100%)
- Visual progress bar shows completion status
- Can be updated at any time
- Independent of completion status

### Data Persistence
- All data is stored in local SQLite database via Room
- Data persists across app restarts
- No data loss even without internet
- Automatic database creation on first launch

## Dependencies

Key libraries used in this project:

```gradle
// Core Android
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1
com.google.android.material:material:1.11.0

// Lifecycle & ViewModel
androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0
androidx.lifecycle:lifecycle-livedata-ktx:2.7.0

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3

// UI Components
androidx.viewpager2:viewpager2:1.0.0
androidx.fragment:fragment-ktx:1.6.2
```

## Minimum Requirements

- **Android Version**: Android 7.0 (Nougat) - API Level 24
- **Storage**: ~20 MB for app
- **RAM**: 1 GB minimum (2 GB recommended)
- **Permissions**: None required (fully offline)

## Future Enhancements

Possible improvements for future versions:
- Task categories/tags
- Due dates with reminders
- Task notes/comments
- Data export/import
- Dark theme
- Search and filter
- Task statistics
- Recurring tasks
- Subtasks support

## License

This project is provided as-is for educational and personal use.

## Support

For issues or questions:
1. Check the code comments
2. Review the Room database documentation
3. Consult Android developer guides

## Author

Created as a fully offline task tracking solution for Android.
