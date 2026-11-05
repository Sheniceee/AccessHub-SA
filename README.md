# AccessHub SA

![Android CI](https://github.com/Sheniceee/AccessHub-SA/actions/workflows/build.yml/badge.svg)

AccessHub SA is an Android app that helps South African users find community and public services in one place, submit service requests, track their progress and keep working when they have no internet connection.

## Purpose

Many people don't know which services are available to them, where to find them, what documents they need, or whether their request has been processed. AccessHub SA gives them one simple, secure and multilingual place to do this.

## Features

- Register and log in (passwords are hashed, never stored as plain text)
- Google Single Sign-On
- Settings: update profile, change password, choose language, turn notifications on or off, delete account
- Browse and search services by category 
- Create service requests and track their status 
- **Offline Request Queue (innovative feature):** requests made without internet are saved on the phone with the status *Pending Sync* and uploaded automatically when the connection returns 
- Push notifications for request updates (Firebase Cloud Messaging
- Languages: English, isiZulu and Sesotho 
- Points and achievements (gamification) 

## Design considerations

- **Simple and accessible:** consistent navigation, large touch targets and clear icons.
- **Secure:** password hashing, HTTPS communication and authentication.
- **Works with poor connectivity:** Room stores data locally and WorkManager syncs it later.
- **Inclusive:** all text lives in Android string resources, so languages can be added without changing the code.
- **Handles bad input:** forms validate fields and show messages instead of crashing.

## Tech stack

| Area | Technology |
|---|---|
| App | Kotlin, Android Studio, Material Components, view binding |
| Networking | Retrofit, OkHttp, Gson |
| Local storage / offline | Room, DataStore |
| Background sync | WorkManager |
| Dependency injection | Hilt |
| Backend | REST API (ASP.NET Core) with a database [how yours is hosted] |
| Notifications / SSO | Firebase Cloud Messaging, Google Sign-In  |
| CI | GitHub Actions |

## How GitHub and GitHub Actions were used

- The source code is in this repository and was committed and pushed regularly as features were added.
- A GitHub Actions workflow (`.github/workflows/build.yml`) runs on every push and pull request to `main`. It sets up JDK 21, runs the unit tests (`./gradlew testDebugUnitTest`) and builds the debug APK (`./gradlew assembleDebug`).
- The workflow caught real problems before submission, including a missing Material Components theme library, a missing WorkManager dependency and a misplaced closing brace in `SettingsActivity.kt`. Each was fixed and the build now passes.
- The final commit is tagged `final-PoE`.

**Screenshot of a passing workflow run:** 


## Screenshots

[ADD SCREENSHOTS: login, register, home, settings, language change, offline request]

## Demo video

[ADD YOUTUBE LINK HERE]

The video was recorded on a real Android phone with a voice-over. It shows registration and login, SSO, settings, the REST API and database, offline mode with sync, push notifications and multi-language support.

## Architecture
AccessHub SA uses MVVM + Clean Architecture

- UI Layer: Jetpack Compose Screens (Login, ServiceList, RequestForm)
- ViewModel: Handles business logic + StateFlow
- Data Layer: Repository pattern
    - Local: Room Database (ServiceRequest entity) for offline queue
    - Remote: Firebase Auth (Google SSO) + Firestore / API
    - Sync: WorkManager SyncWorker (auto sync when online)
- Services: FCM Push Notifications, SharedPreferences for language (en/zu/st)

## Release notes: version 1.0 (final PoE)

Updates since the prototype:

- Settings screen: edit profile, change password, delete account, choose language and notification preference
- Offline request sync with Room and WorkManager [CONFIRM]
- Google Single Sign-On [CONFIRM]
- Push notifications [CONFIRM]
- isiZulu and Sesotho language support [CONFIRM]
- GitHub Actions workflow for automated build and tests
- App icon and final image assets
- Bug fixes: build configuration and dependency problems resolved

**Innovative features added in this version:** the Offline Request Queue [CONFIRM], and points and achievements [CONFIRM].

## Building the project

1. Clone the repository.
2. Open it in Android Studio and let Gradle sync (JDK 21 is required).
3. Run the app on an Android phone or emulator (minimum SDK 24).

To run the tests and build from the command line: `./gradlew testDebugUnitTest assembleDebug`

## Author

[YOUR NAME], [STUDENT NUMBER], OPSC
