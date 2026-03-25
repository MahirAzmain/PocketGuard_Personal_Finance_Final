<p align="center">
  <img src="app/src/main/res/drawable/icon_new.png" alt="PocketGuard Logo" width="120" height="120"/>
</p>

<h1 align="center">💰 PocketGuard — Personal Finance Manager</h1>

<p align="center">
  <em>A feature-rich Android application for managing personal finances, tracking expenses & income, setting savings goals, and making smarter financial decisions — powered by Firebase and AI.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Platform" />
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white" alt="Language" />
  <img src="https://img.shields.io/badge/Min%20SDK-21%20(Lollipop)-blue" alt="Min SDK" />
  <img src="https://img.shields.io/badge/Target%20SDK-34-blue" alt="Target SDK" />
  <img src="https://img.shields.io/badge/Backend-Firebase-FFCA28?logo=firebase&logoColor=black" alt="Firebase" />
  <img src="https://img.shields.io/badge/Maps-Google%20Maps-4285F4?logo=googlemaps&logoColor=white" alt="Google Maps" />
  <img src="https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white" alt="CI/CD" />
</p>

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots & Demo](#-screenshots--demo)
- [Architecture & Design Patterns](#-architecture--design-patterns)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Documentation](#-documentation)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🔍 Overview

**PocketGuard** is a comprehensive personal finance management Android application designed to help users take full control of their financial health. Users can track income and expenses, attach receipt images, set and monitor savings goals, get AI-powered financial advice, find nearby financial institutions on a map, and much more — all secured with PIN lock and user authentication.

---

## ✨ Features

### 💳 Expense & Income Tracking
- Add, edit, and delete income and expense transactions
- Categorize transactions (e.g., Food, Transport, Salary, etc.)
- Select payment method (Cash, Card, etc.)
- Date picker for accurate transaction dating
- Attach receipt images via **Camera** or **Gallery**
- Upload receipt images to **Firebase Storage**
- View real-time balance, total income, and total expenses on the dashboard

### 🎯 Financial Goal Setting
- Create savings goals with a target amount and deadline
- Track progress visually with a RecyclerView-based goal list
- Edit or delete goals as plans change
- Data persisted via **Firebase Realtime Database**

### 🤖 AI Financial Assistant
- Built-in **ChatGPT-powered** chatbot for financial advice
- Ask questions and get AI-generated responses in real-time
- Dedicated chat interface with input and response display

### 🗺️ Maps & Nearby Services
- **Google Maps** integration to locate nearby banks and ATMs
- Real-time location tracking using GPS (fine & coarse location)
- Interactive map with markers for financial institutions

### 👨‍👩‍👧 Parental Controls
- Parents can monitor and manage children's spending
- Location-based tracking on Google Maps
- Observer pattern for real-time data updates from Firebase

### 📊 Currency Exchange Rates
- Live currency exchange rate data fetched via **Volley** HTTP client
- Rankings and rate comparisons in a clean UI
- JSON parsing for structured financial data display

### 🔔 Push Notifications
- **Firebase Cloud Messaging (FCM)** for push notifications
- Notification feed with a list of received alerts
- Custom notification handling with `FCMMessageReceiverService`

### 🔖 Bookmarks
- Bookmark financial articles and web resources
- Manage bookmarks with SQLite-backed local storage
- Ad-free built-in web browser for reading articles

### 🔐 Security & Settings
- **PIN Lock** with setup, confirmation, and entry screens
- User authentication via **Firebase Realtime Database**
- Registration and login system with email & password
- Profile image upload with Firebase Storage
- Settings panel for app customization

### 🎨 UI & UX
- Animated **Splash Screen** with Lottie animations and progress bars
- **Navigation Drawer** with 8 quick-access menu items
- Material Design components throughout
- Custom animations (fade, scale, slide) for smooth transitions
- Dark mode support via night-qualified themes
- Custom fonts (Antic, Bangers via Google Fonts)
- Zoomable image viewer for receipt inspection

---

## 🏗️ Architecture & Design Patterns

PocketGuard follows clean Android architecture principles with well-documented design patterns:

| Pattern | Usage |
|---|---|
| **MVC** (Model-View-Controller) | Separation of data, UI, and logic across activities |
| **Singleton** | SharedPreferences access, Volley RequestQueue |
| **Observer** | Firebase `ValueEventListener` for real-time data sync |
| **Adapter** | RecyclerView adapters for goals, transactions, notifications |
| **Factory Method** | Alert dialog creation, utility method abstraction |
| **Template Method** | Activity UI setup and lifecycle management |

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| **Language** | Java |
| **IDE** | Android Studio |
| **Min SDK** | 21 (Android 5.0 Lollipop) |
| **Target SDK** | 34 (Android 14) |
| **Build System** | Gradle 7.3.1 |
| **Backend** | Firebase Realtime Database, Firebase Storage |
| **Authentication** | Custom auth via Firebase DB |
| **Push Notifications** | Firebase Cloud Messaging (FCM) |
| **Maps** | Google Maps SDK, Play Services Location |
| **Networking** | Volley, OkHttp3 |
| **JSON Parsing** | Gson |
| **Image Loading** | Glide 4.12 |
| **Image Zoom** | Zoomage 1.3.1 |
| **Animations** | Lottie 3.4.0 |
| **Local Storage** | SQLite (custom helper classes) |
| **UI Framework** | Material Design, ConstraintLayout, CardView |
| **View Binding** | Enabled (ViewBinding + DataBinding) |
| **Testing** | JUnit 4, Espresso, Robolectric 4.12, Mockito |
| **CI/CD** | GitHub Actions |

---

## 📁 Project Structure

```
PocketGuard/
├── .github/workflows/          # CI/CD pipeline (GitHub Actions)
│   └── android.yml
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pocketguard/
│   │   │   │   ├── Account/             # Login, Register, User management, Profile images
│   │   │   │   ├── Bookmarks/           # SQLite-based bookmark system
│   │   │   │   ├── Database/            # SQLite helper, model, and data source
│   │   │   │   ├── GoalSet/             # Financial goal setting & tracking
│   │   │   │   ├── Json/                # Currency exchange rates & rankings
│   │   │   │   ├── Map/                 # Google Maps, location, parental controls
│   │   │   │   ├── Settings/            # PIN lock, app settings, login
│   │   │   │   ├── ShowData/            # Transaction list & adapter
│   │   │   │   ├── WebPages/            # In-app browser, notification feed
│   │   │   │   ├── AddExpenceIncomeActivity.java  # Core expense/income entry
│   │   │   │   ├── App.java             # Application class & notification channels
│   │   │   │   ├── FCMMessageReceiverService.java # Push notification handler
│   │   │   │   ├── Gpt.java             # AI chatbot integration
│   │   │   │   ├── HomeActivity.java    # Main dashboard
│   │   │   │   ├── Key_SharedPreference.java      # SharedPreferences keys
│   │   │   │   ├── Key_Static.java      # Static constants
│   │   │   │   ├── Key_Static_Methods.java        # Utility methods
│   │   │   │   └── SPLASH_SCREEN.java   # Animated splash screen
│   │   │   ├── res/
│   │   │   │   ├── anim/               # Custom animations
│   │   │   │   ├── drawable/           # Icons, backgrounds, vector assets
│   │   │   │   ├── font/              # Custom fonts
│   │   │   │   ├── layout/            # XML layouts (30+ screens)
│   │   │   │   ├── menu/              # Navigation & options menus
│   │   │   │   └── values/            # Colors, strings, themes, dimensions
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                # UI tests (Espresso)
│   │   │   └── UITest/
│   │   │       ├── BookmarksTest.java
│   │   │       ├── GoalSetTest.java
│   │   │       ├── HomeActivityTest.java
│   │   │       ├── LoginTest.java
│   │   │       └── Parental_ControlTest.java
│   │   └── test/                       # Unit tests (JUnit, Mockito, Robolectric)
│   │       └── UnitTest/
│   │           ├── LoginTest.java
│   │           ├── RegisterTest.java
│   │           └── ValidEmailTest.java
│   └── build.gradle                    # App-level Gradle config
├── build.gradle                        # Project-level Gradle config
├── settings.gradle
├── PocketGuardFinal.pptx               # Project presentation
├── PocketGuardFinalReport.pdf          # Detailed project documentation
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Arctic Fox or later
- **JDK 8** or higher
- **Android SDK** with API Level 34 installed
- A **Firebase** project (for database, storage, and messaging)
- A **Google Maps API Key**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/MahirAzmain/PocketGuard_Personal_Finance_Final.git
   cd PocketGuard_Personal_Finance_Final
   ```

2. **Open in Android Studio**
   - Open Android Studio → `File` → `Open` → Select the project directory

3. **Configure Firebase**
   - Create a project in the [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json` and place it in the `app/` directory
   - Enable **Realtime Database**, **Storage**, and **Cloud Messaging**

4. **Configure Google Maps**
   - Obtain an API key from the [Google Cloud Console](https://console.cloud.google.com/)
   - Add it to your `local.properties` file:
     ```properties
     MAPS_API_KEY=your_google_maps_api_key_here
     ```

5. **Build & Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or simply click the **Run ▶** button in Android Studio.

---

## ⚙️ Configuration

| Configuration | Location | Description |
|---|---|---|
| Firebase | `app/google-services.json` | Firebase project configuration |
| Google Maps API | `local.properties` | Maps API key (`MAPS_API_KEY`) |
| App Namespace | `app/build.gradle` | `com.example.pocketguard` |
| Min SDK | `app/build.gradle` | API 21 (Android 5.0) |
| Target SDK | `app/build.gradle` | API 34 (Android 14) |

### Required Permissions

| Permission | Purpose |
|---|---|
| `INTERNET` | Firebase, API calls, web browsing |
| `ACCESS_NETWORK_STATE` | Network connectivity checks |
| `CAMERA` | Receipt photo capture |
| `ACCESS_FINE_LOCATION` | Precise location for maps |
| `ACCESS_COARSE_LOCATION` | Approximate location for maps |

---

## 🧪 Testing

PocketGuard includes both **Unit Tests** and **UI Tests** for comprehensive quality assurance.

### Unit Tests (JUnit + Mockito + Robolectric)

```bash
./gradlew test
```

| Test Class | Coverage |
|---|---|
| `LoginTest` | Login validation, Firebase interaction mocking |
| `RegisterTest` | Registration form validation |
| `ValidEmailTest` | Email format validation logic |

### UI Tests (Espresso)

```bash
./gradlew connectedAndroidTest
```

| Test Class | Coverage |
|---|---|
| `LoginTest` | Login screen UI interactions |
| `HomeActivityTest` | Dashboard navigation and display |
| `GoalSetTest` | Goal creation and management flow |
| `BookmarksTest` | Bookmark add/delete operations |
| `Parental_ControlTest` | Parental control screen interactions |

---

## ⚡ CI/CD Pipeline

The project uses **GitHub Actions** for continuous integration:

```yaml
# Triggered on push/PR to master branch
- Checkout code
- Setup Java 17 (Adopt)
- Grant Gradle permissions
- Build with Gradle
- Upload APK artifact (app-debug.apk)
```

The build artifact (`PocketGuard.apk`) is automatically uploaded after each successful build.

---

## 📚 Documentation

| Resource | Description |
|---|---|
| [📄 Project Presentation (PPT)](PocketGuardFinal.pptx) | Comprehensive slide deck covering features and architecture |
| [📑 Project Report (PDF)](PocketGuardFinalReport.pdf) | Detailed written documentation of the project |

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

---

## 📄 License

This project is open source and available for educational and personal use.

---

<p align="center">
  Made by <a href="https://github.com/MahirAzmain">Mahir Azmain</a>
</p>
