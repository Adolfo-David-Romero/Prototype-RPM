### 📱 Prototype RPM

A **prototype Android app** built with **Kotlin & Jetpack Compose** to test remote patient monitoring (**RPM**) technologies before integrating them into the final patient-facing app. This prototype evaluates **camera functionality, patient vitals monitoring, and BLE connectivity** with a custom smartwatch. The app uses **Firebase** as the backend.

* * * * *

🚀 Features
-----------

-   📷 **Camera Integration** -- Test camera-based patient monitoring functionalities.
-   📡 **Remote Patient Vitals Monitoring** -- Collect and display real-time patient data.
-   🔗 **Bluetooth Low Energy (BLE) Support** -- Connect to a custom smartwatch to sync health metrics.
-   ☁️ **Firebase Backend** -- Store and process patient data securely.
-   🎨 **Jetpack Compose UI** -- Modern UI design with a fully declarative approach.

* * * * *

🛠️ Tech Stack
--------------

-   **Programming Language:** Kotlin
-   **UI Framework:** Jetpack Compose
-   **Data Storage & Backend:** Firebase Firestore / Firebase Realtime Database
-   **Bluetooth:** BLE API for smartwatch connectivity
-   **Camera API:** Android CameraX
-   **Dependency Injection:** Hilt
-   **Build Tool:** Gradle

* * * * *

📦 Installation & Setup
-----------------------

1.  **Clone the repository**:
    ```
    git clone https://github.com/Adolfo-David-Romero/Prototype-RPM.git
    cd Prototype-RPM
    ```

2.  **Open in Android Studio** and ensure you have the latest **Kotlin & Jetpack Compose** dependencies installed.

3.  **Set up Firebase**:

    -   Create a Firebase project in the Firebase Console.
    -   Download `google-services.json` and place it in the `app/` directory.
    -   Enable Firestore & Authentication (if used).
4.  **Enable Bluetooth Permissions** in `AndroidManifest.xml`:
    ```
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT"/>
    ```

5.  **Run the app** on a physical device (BLE features may not work on an emulator):
    ```
    ./gradlew assembleDebug
    ```

* * * * *

🏗️ Roadmap
-----------

-   ✅ Implement BLE smartwatch connectivity
-   ✅ Camera-based patient monitoring
-   ⏳ Integrate real-time vitals tracking dashboard
-   ⏳ Optimize data sync between Firebase and local storage

* * * * *

🤝 Contributing
---------------

If you'd like to contribute or test out new features, feel free to fork the repo and submit a pull request.

* * * * *
