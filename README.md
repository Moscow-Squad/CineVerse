# About the app
--------------------------------------------

<p align="center">
 <img 
  src="https://github.com/user-attachments/assets/04d8c8da-e493-4988-9ca5-e7349e6651c6"
  alt="CineVerse Banner"
  width="100%"
  height="1978"
  />
</p>

## 🎬 Welcome to the Civerse Movies & Series ! 🍿✨
Dive into a world of endless entertainment, where you can explore movies and TV shows from every category  action, drama, comedy, romance, sci-fi, and more! 🎭 From discovering your favorite actors 🎥 to exploring their full filmography 📚, we’ve got you covered. Build your own collections, follow categories you love ❤️, and enjoy a personalized cinematic journey like never before! 🌟

## 📱 App Screens:
<table> 
<tr> 
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/0011bf39-dd4e-48cb-a6d1-3bf56f2eb878" /> </th>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/c470e2db-a772-4d16-9322-7af898d89175" /> </th> 
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/55b61e1d-9afe-4bf5-a714-80bdb3ae59fc" /> </th>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/1217889f-8636-40e7-bbe5-1d24516ec1e6" /> </th>
</tr>
 
<tr> 
<th>Splash Screen</th> 
<th>Onboarding 1</th> 
<th>Onboarding 2</th> 
<th>Onboarding 3</th> 
</tr>

<tr> 
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/ce47c618-7177-4d1b-b83d-3ebcfd20ecc7" /> </th>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/5120e256-e21f-4577-b719-2baf01e97d09" /> </th>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/14f1eb8b-4260-4586-b847-735a1ce9901a" /> </th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/72fabe82-48f1-4451-97c9-74b1b62119d3" /> </th> 
</tr>  

<tr> 
<th>Login Screen</th> 
<th>Home Screen</th> 
<th>Home Screen</th> 
<th>Home Screen</th>  
</tr>
 
<tr>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/6168fde1-0b4a-4d16-b1a7-8f43144f0f96" /></th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/2291f3b5-0858-41c6-a3ec-fa53856b50f3" /> </th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/b6ca4176-8000-48a6-a735-f5c1738d65cd" /> </th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/48cca65a-886a-4239-bef0-22c86552ed2e" /> </th>  
</tr> 

<tr> 
<th>Explore Screen</th> 
<th>Search Explore Screen</th> 
<th>List Explore Screen</th>
<th>History Explore </th>

<tr>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/b853dde0-d05e-4b3b-8a20-7cc688391491" /> </th>  
<th> <img width="200"  alt="Image" src="https://github.com/user-attachments/assets/af8c7eea-2150-40ba-a6e7-6519e039a97f" /> </th>  
<th> <img width="200"  alt="Image" src="https://github.com/user-attachments/assets/b6adc0d8-c39a-4d5e-afb6-112ec1197ac2" /> </th>  
<th> <img width="200"  alt="Image" src="https://github.com/user-attachments/assets/33c4e240-7fcf-44d8-aa61-2c7aa7132c1c" /> </th>  
</tr> 

<tr> 
<th>Movie/ Series Deatils </th> 
<th>Cast Deatils</th> 
<th>Rate Movie/Series</th>
<th>Top Review</th>


  <tr>
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/617f2967-9738-4baf-9244-8b71390fffdb" /></th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/44882243-48f0-4433-b920-1a3401c60aca" /> </th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/5d1675c9-49db-483f-b2c5-d8ad37261066" /> </th>  
<th> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/3b787a2c-78b8-4802-936c-bf27f757ad6f" /> </th> 
</tr> 

<tr> 
<th>Profile Screen</th> 
<th>Edit profile</th> 
<th>Change language</th>
<th> Content Preferences</th>
</tr> 
</table>

### Modularization
<p align="center">
  <img width="100%" alt="Image" src="https://github.com/user-attachments/assets/da390766-6637-4d0b-9b02-d9791986356b" />
</p>

## 🏛️ Modularization Explained

This diagram illustrates a modern, multi-module architecture based on Clean Architecture principles. The arrows indicate the direction of dependency; for example, an arrow from **`:presentation`** to **`:domain`** means the **`:presentation`** module depends on the **`:domain`** module.

This setup ensures that core business logic is independent and that the UI and data layers can be modified or replaced without affecting the rest of the system.

Here's a breakdown of each module's role:

* **`:app`**
    * **Description**: The main application module that assembles the final app.
    * **Responsibilities**: It integrates all other modules (`:presentation`, `:data`, `:domain`, etc.), sets up the Firebase suite (Crashlytics, Analytics), and initializes the Hilt dependency graph for the application.

* **`:presentation`**
    * **Role:** The UI layer, containing Jetpack Compose screens, ViewModels, and navigation. It orchestrates user interactions and displays data.
    * **Dependencies:**
        * **`:domain`** (to access business logic/use cases).
        * **`:design_system`** (to use shared UI components).
        * **`:image_viewer`** (to display images with custom logic).

* **`:data`**
    * **Role:** Implements the repository interfaces defined in the domain layer. It handles all data operations, fetching from remote sources (Retrofit), local caches (Room) and **Jetpack DataStore** for storing user preferences. It is also responsible for securely loading API keys via `buildConfig`.
    * **Dependencies:** **`:domain`** (to implement its interfaces).

* **`:domain`**
    * **Role:** The core of the application. It contains the essential business logic, use cases, and data models. It is pure Kotlin and has no knowledge of the Android framework.
    * **Dependencies:** None. It is the most independent module in the project.

* **`:design_system`**
    * **Role:** A shared library of reusable UI components, Provides a consistent look and feel across the app by centralizing themes, colors, typography, and common Composables like buttons and text fields. It includes specialized components This ensures UI consistency across the app.
    * **Dependencies:** None.

* **`:image_viewer`**
    * **Role:** A **_specialized utility module_** responsible for loading images (using Coil) and handling content safety by classifying and blurring images using TensorFlow Lite.
    * **Dependencies:** None.
 
## 🛠️ Tech Stack & Third-Party Libraries
CineVerse uses modern Android development tools and libraries:

**Languages & Tools**
- Kotlin
- Android Studio
- Gradle
- Jetpack Components (ViewModel, LiveData, Navigation, etc.)

- **UI:** Jetpack Compose, Material 3, Coil 3
- **Architecture:** MVVM, Repository Pattern, Clean Architecture
- **Core:** Kotlin, Coroutines & Flow, Jetpack Paging 3, WorkManager
- **Dependency Injection:** Hilt
- **Networking:** Retrofit, OkHttp, Kotlinx.Serialization
- **Local Storage:** Room, DataStore
- **Machine Learning:** TensorFlow Lite (for NSFW image classification)
- **Analytics:** Firebase Crashlytics, Firebase Performance, Firebase Analytics
- **Testing:** JUnit5, Mockk, Truth

**APIs**
- TMDB API — Movie & TV show data

<br><br>
