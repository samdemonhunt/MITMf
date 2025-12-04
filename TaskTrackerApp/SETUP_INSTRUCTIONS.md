# How to Open This Project in Android Studio

## IMPORTANT: Open the Correct Folder

You MUST open the `TaskTrackerApp` folder as the project root in Android Studio.

### Correct Path:
```
C:\Users\Samwel\StudioProjects\MITMf\TaskTrackerApp
```

### Steps to Open:

1. **Close any currently open project in Android Studio**
   - File → Close Project

2. **Open the correct folder:**
   - Click "Open" on the Welcome screen
   - Navigate to: `C:\Users\Samwel\StudioProjects\MITMf\TaskTrackerApp`
   - Select the **TaskTrackerApp** folder (not MITMf)
   - Click OK

3. **Wait for Gradle Sync:**
   - Android Studio will download Gradle 8.2 (first time only)
   - Wait for sync to complete
   - This may take a few minutes on first run

4. **If you see any errors after sync:**
   - File → Invalidate Caches → Invalidate and Restart
   - Reopen the TaskTrackerApp folder

### ❌ WRONG - Do NOT open:
```
C:\Users\Samwel\StudioProjects\MITMf
```

### ✅ CORRECT - Open this folder:
```
C:\Users\Samwel\StudioProjects\MITMf\TaskTrackerApp
```

## Verify You Opened the Right Folder

After opening, check the Project view. You should see:
```
TaskTracker (root)
├── .gradle/
├── app/
├── gradle/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
└── gradlew.bat
```

If you see `MITMf` as the root instead of `TaskTracker`, you opened the wrong folder!

## Building the App

Once opened correctly:

1. **Sync Gradle** (if not automatic):
   - File → Sync Project with Gradle Files

2. **Build the APK:**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)

3. **Run on Device:**
   - Connect your Android device or start an emulator
   - Click the green Run button (▶)

## Troubleshooting

### If you still get errors:

1. **Clean the project:**
   - Build → Clean Project
   - Build → Rebuild Project

2. **Clear Gradle cache:**
   - File → Invalidate Caches → Invalidate and Restart

3. **Delete build folders manually:**
   - Close Android Studio
   - Delete `TaskTrackerApp\.gradle` folder
   - Delete `TaskTrackerApp\app\build` folder
   - Reopen Android Studio

4. **Check Java/JDK version:**
   - File → Project Structure → SDK Location
   - JDK should be version 17 or higher
