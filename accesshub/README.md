# AccessHub SA — Submission Version

AccessHub SA is an Android Kotlin/XML client with an ASP.NET Core REST API and SQL Server/Entity Framework Core backend.

## Implemented functionality

- User registration and login
- Server-side password hashing with ASP.NET PasswordHasher
- JWT authentication and authenticated API calls
- Android session persistence with SharedPreferences
- Service catalogue and search/category support
- Service request creation and status tracking
- Room local database for offline requests
- Automatic offline request synchronisation with WorkManager when connectivity returns
- Online request caching for My Requests
- Profile editing
- Preferred language setting: English, isiZulu and Sesotho
- Notification preference setting
- Change password with current-password verification
- Account deletion with confirmation and dependent data cleanup
- Notification API and read/unread status
- Achievement/gamification API structure
- FCM token storage endpoint
- Swagger/OpenAPI support in development

## Backend setup

1. Open `AccessHub.Api` in Visual Studio/VS Code.
2. Set the SQL Server connection string in `appsettings.json`.
3. Set a strong private JWT key before deployment.
4. Run:

```bash
dotnet restore
dotnet run
```

The API creates the database schema on first run and seeds the sample catalogue data.

## Android setup

Open `AccessHubAndroid` in Android Studio.

For an Android emulator connecting to an API running on the same development computer, update `RetrofitClient.kt` if the API uses a different port:

```text
http://10.0.2.2:5000/
```

The Android manifest permits cleartext HTTP for local development only. Use HTTPS for production.

## Offline synchronisation

When a request cannot reach the API, AccessHub saves it locally with `Pending Sync`. WorkManager is then scheduled with a `CONNECTED` network constraint. When internet connectivity is available, the worker posts each pending request to the API and marks it as submitted.

## Google SSO and Firebase Cloud Messaging

Google SSO and FCM require credentials belonging to the student's own Google/Firebase project. Private credentials are intentionally not included in a submission archive.

The backend contains an FCM-token endpoint and the Android login screen clearly identifies Google SSO as an external configuration requirement. To enable real Google SSO/FCM, add the project's Firebase configuration and OAuth credentials supplied by Google/Firebase.

## Security notes

- Do not commit production database passwords, JWT secrets, OAuth secrets or `google-services.json` to GitHub.
- Replace the development JWT key with a long random secret.
- Use HTTPS in production.
- Google sign-in must verify a Google ID token server-side before accepting it as an authenticated identity.

## Main application flow

Splash -> Login/Register -> Home -> Services -> Create Request -> My Requests -> Settings

Offline request flow:

Create Request -> Room -> Pending Sync -> Network Available -> WorkManager -> REST API -> Submitted

## Suggested submission checklist

- [x] Android Kotlin/XML project
- [x] ASP.NET Core backend
- [x] REST API
- [x] SQL Server/EF Core data model
- [x] Authentication
- [x] Profile/settings
- [x] Service requests
- [x] Offline storage
- [x] Automatic offline synchronisation
- [x] Change password
- [x] Delete account
- [x] Notifications API
- [x] Achievement structure
- [ ] Student-specific Google/Firebase credentials (must be configured by the student)
