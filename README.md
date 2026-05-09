# Github Test App

Simple Android application built around the Github API.

## Tech stack

* Kotlin
* Jetpack Compose
* MVVM
* Clean Architecture
* Hilt Dependency Injection
* Retrofit
* Coroutines
* StateFlow
* JUnit
* MockK
* Turbine

## Features

* Displays Octocat repositories
* Shows open issues count for each repository
* Opens repository details screen
* Displays owner avatar and username
* Displays repository name, forks and watchers count
* Displays repository tags with commit SHA
* Loading, error, empty and success states
* Retry support
* Dark mode support

## Architecture

The project is split into three main layers:

```text
data
domain
presentation
```

### Data layer

Responsible for:

* Retrofit API calls
* DTO models
* Mapping DTO models to domain models
* Repository implementation
* Centralized API error handling

### Domain layer

Responsible for:

* Domain models
* Repository abstraction
* Use cases

### Presentation layer

Responsible for:

* Compose screens
* ViewModels
* UiState handling
* Navigation
* Reusable UI components

## Dependency Injection

Hilt is used to provide:

* Retrofit
* OkHttpClient
* GithubApi
* Repository implementation
* API call handler

The repository is injected through an interface, which makes it easy to replace the real implementation with a fake one for testing.

## Testing

Unit tests are added for ViewModels.

Covered cases:

* Repository list success
* Repository list error
* Repository details success
* Repository details error
* Repository tags error

## API

The app uses Github public API:

* `GET /users/octocat/repos`
* `GET /repos/octocat/{repo}`
* `GET /repos/octocat/{repo}/tags`

## How to run

1. Clone the project
2. Open it in Android Studio
3. Sync Gradle
4. Run the app

No API key is required.
