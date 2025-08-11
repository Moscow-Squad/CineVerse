# 🎥 About the app
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

**Key Features:**
- 🔍 **Search** for any movie or series.
- 👤 **Actor Details** — learn about your favorite stars.
- ⭐ **Rate & Review** — give feedback on movies/series.
- 🎯 **MCQ Game** — get personalized recommendations.
- 📂 **Custom Collections** — create and manage your own watchlists.
- 🚫 **Content Filter** — blur inappropriate visuals.
- 🌓 **Dark & Light Theme** support.
- 🌐 **Multi-language** — English & Arabic.

## App Screens:

<table>
<tr>
  <td><img width="200" src="https://github.com/user-attachments/assets/284788ed-1095-4d7b-8150-b01954bfd385" /></td>
  <td><img width="200" alt="Image" src="https://github.com/user-attachments/assets/c6eceaf2-8706-4abd-b612-dc306fde83e0" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/6cbe3c47-c802-4c11-844e-457b959ca3a5" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/b1abea25-3e38-4d32-b058-62166c7e1a3e" /></td>
</tr>
<tr>
  <td>Splash Screen</td>
  <td>Onboarding 1</td>
  <td>Onboarding 2</td>
  <td>Onboarding 3</td>
</tr>

<tr>
  <td><img width="200" src="https://github.com/user-attachments/assets/eb88c8f2-5aa7-4c97-b556-ed7608596327" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/5120e256-e21f-4577-b719-2baf01e97d09" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/ae0f6282-4355-401e-a34f-79cd5c99db1d" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/72fabe82-48f1-4451-97c9-74b1b62119d3" /></td>
</tr>
<tr>
  <td>Login Screen</td>
  <td>Home Screen</td>
  <td>Home Screen</td>
  <td>Home Screen</td>
</tr>

<tr>
  <td><img width="200" src="https://github.com/user-attachments/assets/5d0e6ec0-872a-4a25-9c6f-1933ea8a5682" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/65ae0c2c-5a03-4487-a310-4ad1cd5b0d8b" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/0ec88b52-0aea-43a3-b858-ace5e263303e" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/fd193fe0-29c4-4c29-925b-1693f9db1112" /></td>
</tr>
<tr>
  <td>Explore Screen</td>
  <td>Search Explore Screen</td>
  <td>List Explore Screen</td>
  <td>History Explore</td>
</tr>

<tr>
  <td><img width="200" src="https://github.com/user-attachments/assets/b853dde0-d05e-4b3b-8a20-7cc688391491" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/4625c3b3-4459-4b7d-9cdc-c13d384323f5" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/b6adc0d8-c39a-4d5e-afb6-112ec1197ac2" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/aaa8db6b-6723-4839-942d-c76e8b1e5df0" /></td>
</tr>
<tr>
  <td>Movie/ Series Details</td>
  <td>Cast Details</td>
  <td>Rate Movie/Series</td>
  <td>Top Review</td>
</tr>

<tr>
  <td><img width="200" src="https://github.com/user-attachments/assets/fbfbfecc-3952-4df6-a114-b2991d64fee9" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/4ead251f-fe9a-4351-be97-c2d3b1ccf1fe" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/6616eca8-a886-4b37-ab14-744939f528a8" /></td>
  <td><img width="200" src="https://github.com/user-attachments/assets/afff28d1-724e-494d-8f76-595c6d21875b" /></td>
</tr>
<tr>
  <td>Profile Screen</td>
  <td>Edit profile</td>
  <td>Change language</td>
  <td>Content Preferences</td>
</tr>
</table>
# Modularization
<p align="center">
  <img width="100%" alt="Image" src="https://github.com/user-attachments/assets/da390766-6637-4d0b-9b02-d9791986356b" />
</p>

## 🏛️ Modularization Explained

This diagram illustrates a modern, multi-module architecture based on Clean Architecture principles. The arrows indicate the direction of dependency; for example, an arrow from **`:presentation`** to **`:domain`** means the **`:presentation`** module depends on the **`:domain`** module.

This setup ensures that core business logic is independent and that the UI and data layers can be modified or replaced without affecting the rest of the system.
This separation of concerns makes the codebase modular, testable, and easy to maintain.

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
 
## 🛠️ Tech Stack & Key Libraries

CineVerse is built with a modern tools, libraries, robust, and scalable tech stack, leveraging the best of the Android ecosystem.

**Languages & Tools**
- Kotlin
- Android Studio
- Gradle
- Jetpack Components (ViewModel, LiveData, Navigation, etc.)

| Category | Technologies & Libraries |
| :--- | :--- |
| **Core & Architecture** | Kotlin, Coroutines & Flow, Clean Architecture, MVVM, Repository Pattern |
| **UI & Design** | Jetpack Compose, Material 3, Coil 3 (for image loading) |
| **Jetpack Suite** | Paging 3, WorkManager, DataStore, Navigation Component |
| **Dependency Injection**| Hilt |
| **Networking** | Retrofit, OkHttp, Kotlinx.Serialization |
| **Local Storage** | Room (for database caching)و DataStore (for key-value preferences) |
| **Machine Learning** | TensorFlow Lite (for on-device content classification) |
| **Firebase Suite** | Crashlytics, Performance Monitoring, Analytics |
| **Testing** | JUnit5, Mockk, Truth|
| **APIs** | The Movie Database (TMDb) API |

<br><br>
--------------
# 🚀 How to Setup & Run the Project Locally

Follow these steps to clone and run **CineVerse** on your local machine:

---

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/Moscow-Squad/CineVerse.git
```
-------
### 2️⃣ Open the Project
Open Android Studio.

Click File > Open.

Select the cloned project folder.
-----
 ### 3️⃣ Add Required Files
 
 📄 service-account-key.json
Place the `service-account-key.json` file in: `<project-root>/app/`

### ⬇️ Put the code of  `service-account-key.json`
```bash
{
  "type": "service_account",
  "project_id": "cineverse-c46f9",
  "private_key_id": "c43ae0b7b519ca1188ffa97c0d8a6b70524d0fb7",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7dT1w2lVZLBUY\nkcYT8ohHiE/z9IRJ6+9QUKCdsYOZOibTO1ymNQmc4NGgW3TylpBXxujVHegwN0XR\nf2Cuj0aFGHxZ6/fcZ6PdyM/YvrweD7F5ZSEIZ8uMxK+d5SLXpCRoJZB/sB2BCRG1\nhUETQwIzQ1x6hbswUQHkeG11PepEqql6kwus4/aipFQfI9FUY9JIaysvpjCyE4Ep\nBNWn1+/vxpsTkXjQ5EwkzoNCmcbpiBcmw4H9FK5lBAEjihzKs/5I1dYiDIDA1Px0\nuXLrHO4BXMnY+0JVwpmvQVEmgk56ABCunLVaIcvkXRDwPjISgcJRNsDHZKcCFneK\nE8Aom+ArAgMBAAECggEACNi3aGgtXJ12IL2Jva7q8TFE0KxVQpB7Wpd2KlWnx7yU\n+3qoMdcml9GLowfd5Nrn1J9NpU0ZhDj/jFZOD5QxuEiXtGislFQMdC7XZuZYG1+9\n447luFn++FWJ53zaSuyd+TpDRPtgkRKGqZcweIJnjALITlHvARCO8yN0dZ1756Hv\nVLf+xFZsEnz0LCEkMcaj38GG43UYlnOTZHU3XLC+VWqFYWo36msxU0Dfs2LiiXIz\nymPDItntu/ss42VZqghdoQwgyOvjXZRNfn6aZqqaS1Ikbptfft5SLMk4OyTHEXKC\nZUWtls5EjxQr/ijxumY0UjMYiAhe38JiAJ+VhbTWfQKBgQD7FwCSjXLRt+1cM4AJ\naTyqLe2ejPc546ya9iQybI3xkZv3JxWNS+lRk+fG0Yor0oG46ZRR12O1303LLijY\n3maVLl0eiuIJq0qD+J6E2H31o3uJJONW1DvCy9+CIGH0uSRK6YrQtb6u3Ru9Cpjj\nezWQWAOCj9yg404+A88PCHxFDQKBgQC/H6+YpSwNBFgjy+mA2hMAyBXWfzsQ1YjO\nqT8KSlvNKRSSIhIcKgvfJTZWxq53Jz3/uDTsv2S+luFxPrXBu3307SHx0QXiP72e\nHxbJRaZMe7Jli0L3eWAECA8Mj/t/4Ps9KK4N0pJtxXW+etx72keRv6OdT7HxHO0T\n9ZNh2OVcFwKBgQDtRjhhRU+qhP/FsFkfC5arTsmMmbOKve6/ZTihednM3Qeg0Ata\nwdCTKYzOYfcLIFs7zWc+y6bLGGEgTteE0O3pra2Ljjy6XGcHPSs3aInnVX0JBZXb\n2KU8mIH3KvjGVSrJq9ZlEkpGXmW3B8ugtC7S34k5mQ8p7oNisSC7GQ6uBQKBgCNK\n8JwZdCb7NGN31iirHHM6f33ahMe5BshSzIdT79vUyC2sCPgWc5bDQmOH0NPD9Vjx\nZSSKu0nOhtboo5ugycOevnPvC00aRCOczhJgwDLDbM//hWA5k5dq/YxuUhztKLfE\neU+oIM4Qdou0GF7ukeTCJK559vv1QXo8EPkymjgFAoGALhiVqX0abW3YJF13UFbo\n7n/UHgA+dMnJVLW0xDg8t9BRxdth2pQExzBVhkeaQhcOjZCgVuIvY1RTdPV5Z5oX\n2h0uzowbAruawCYyzvEAnv0y3JMk46NZAfE0HnXGeMDS5VXvLMLr89lNhZEx8KZ1\nwL+sHncpE29+LbOfBJJ6Voc=\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-app-distribution@cineverse-c46f9.iam.gserviceaccount.com",
  "client_id": "114637347518423044995",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-app-distribution%40cineverse-c46f9.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}
```
---

 📄  google-services.json
Place the `google-services.json` file in: `<project-root>/app/`

### ⬇️ Put the code of  ` google-services.json` 
```bash
{
  "project_info": {
    "project_number": "843710468188",
    "project_id": "cineverse-c46f9",
    "storage_bucket": "cineverse-c46f9.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:843710468188:android:a2ba354ffc2edbc2111699",
        "android_client_info": {
          "package_name": "com.moscow.cineverse"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "AIzaSyC-p1gRlNztPNv6Yx8vHdQ80lcHY858rLY"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    },
    {
      "client_info": {
        "mobilesdk_app_id": "1:843710468188:android:a2ba354ffc2edbc2111699",
        "android_client_info": {
          "package_name": "com.moscow.cineverse.debug"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "AIzaSyC-p1gRlNztPNv6Yx8vHdQ80lcHY858rLY"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}
```
---

📄 keys.properties
Place the `keys.properties` file in:`<project-root>/`

### ⬇️ Put the code of  ` google-services.json` 
```bash
BEARER_TOKEN = eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzdkOWY1Njg2ODVlZmFkYWZhMmNlMGE4NzE1OTdmYiIsIm5iZiI6MTc1MTQ3ODAxMi40MTgsInN1YiI6IjY4NjU2ZWZjMzVmNTZjNzQ4NzZhYTM0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.m1SEYwrplAih5yTaMhekrgBwD8ZkL9FCdFO9OmXHc_I
```
----
### 5️⃣ Sync & Run
Sync Gradle.

Run the app on your local device or emulator.

-----------------------------------------------------------
# 🧩 App Architecture (Modules & Packages)

```mermaid
%%{init: {
  "flowchart": { "nodeSpacing": 50, "rankSpacing": 65 },
  "themeVariables": { "fontSize": "18px", "primaryColor": "#FDF6E3", "primaryTextColor": "#000", "primaryBorderColor": "#333", "lineColor": "#333" }
}}%%
flowchart TD
  A[CineVerse]

  %% app
  A --> APP
  subgraph APP[app]
    subgraph APPK[com.moscow.cineverse]
      APP1[main_activity/]
      APP2[CineVerseApp.kt]
      APP3[CineVerseRoot.kt]
    end
  end

  %% data
  A --> DATA
  subgraph DATA[data]
    subgraph DATAK[com.moscow.data_source]
      D1[di]
      D2[local]
      D3[mapper]
      D4[preference]
      D5[remote]
      D6[repository]
      D7[utils]
    end
  end

  %% design_system
  A --> DS
  subgraph DS[design_system]
    subgraph DSK[com.moscow.design_system]
      DS1[color]
      DS2[component]
      DS3[icon]
      DS4[radius]
      subgraph DST[theme]
        DST1[typography]
        DST2[utils]
      end
    end
  end

  %% domain
  A --> DOMAIN
  subgraph DOMAIN[domain]
    subgraph DOMK[com.moscow.domain]
      DOM1[exception]
      DOM2[mapper]
      DOM3[model]
      DOM4[repository]
      DOM5[usecase]
      DOM6[utils]
    end
  end

  %% image_viewer
  A --> IV
  subgraph IV[image_viewer]
    subgraph IVK[com.moscow.cineverse.image_viewer]
      IV1[classifier]
      IV2[component]
    end
  end

  %% presentation
  A --> PRES
  subgraph PRES[presentation]
    subgraph PRESK[com.moscow.cineverse]
      P1[base]
      P2[common_ui_state]
      P3[component]
      P4[mapper]
      P5[navigation]
      P6[paging]
      P7[screen]
      P8[utils]
    end
  end
```
-----------------------------------------------------------
# 👥 Contributers:
- [Farah Maytham](https://github.com/Farah-Dev4)
- [Adel Tammer](https://github.com/AdelTamer35)
- [Ahmed Hosny](https://github.com/Ahmed7osny1)
- [Mohamed Omer](https://github.com/MohamedOmar989)
- [Nour elhoda Ahmed](https://github.com/nourelhodaahmed)
- [Shrouk Mohamed](https://github.com/ShroukMohamed16)
- [Eslam Magdy](https://github.com/IslamMagd)
- [Hazem Alkateb](https://github.com/hazemka)
- [Israa Mohamed](https://github.com/israamohamed107)
- [Kareem](https://github.com/kareem-01)
- [Khaled Eid](https://github.com/khaledeid1k)
- [Zyad Abdullah](https://github.com/ZeyadAbdullah679)
<br><br>
