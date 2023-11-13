# the-movie-db
the-movie-db es una aplicacion de ejemplo para mostrar Kotlin, MVVM, Clean Architecture, Dagger Hilt, Corutinas, Room database, Retrofit, databinding, livedata, material design, animaciones, motion layout, espresso y test unitarios.

<a href="https://play.google.com/store/apps/details?id=com.armandorochin.themoviedb"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" height=70px /></a>


## TMDb API
<a href="https://www.themoviedb.org/">TMDb</a> es una API publica con una base de datos de películas y series detelevisión creada por la comunidad. Cada dato ha sido agregado por nuestra increíble comunidad que se remonta a 2008. 

## Features
* Clean Architecture + MVVM Architecture + Repository design Pattern.
* Jetpack Libraries and Architecture Components.

## Prerrequisitos
Agrega tu Bearer token de [TMDB](https://www.themoviedb.org/) API en el archivo `local.properties`:
```
TMDB_API_TOKEN=<BEARER_TOKEN>
```

## Testing
Test unitarios realizados en RemoteMediator y ViewModels. UI testint se realizo en pantallas con paginacion usando Espresso.

## Librerias
* [Android Jetpack](https://developer.android.com/jetpack)
   * [Paging](https://developer.android.com/topic/libraries/architecture/paging) La biblioteca de Paging te ayuda a cargar y mostrar pequeños fragmentos de datos a la vez. La carga de datos parciales a pedido reduce el uso del ancho de banda de la red y los recursos del sistema.
   * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Se diseñó la clase ViewModel a fin de almacenar y administrar datos relacionados con la IU de manera optimizada para los ciclos de vida. La clase ViewModel permite que se conserven los datos luego de cambios de configuración, como las rotaciones de pantallas.
   * [DataBinding](https://developer.android.com/topic/libraries/data-binding/) La biblioteca de vinculación de datos es una biblioteca de compatibilidad que permite vincular los componentes de la IU de tus diseños a las fuentes de datos de tu app usando un formato declarativo en lugar de la programación.
   * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) LiveData está optimizado para ciclos de vida, lo que significa que respeta el ciclo de vida de otros componentes de las apps, como actividades, fragmentos o servicios.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) La biblioteca de persistencias de Room brinda una capa de abstracción para SQLite que permite acceder a la base de datos sin problemas y, al mismo tiempo, aprovechar toda la potencia de SQLite.
* [Glide](https://github.com/bumptech/glide) is an image loading and caching library for Android.
* [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Hilt es una biblioteca de inserción de dependencias para Android que permite reducir el trabajo repetitivo de insertar dependencias de forma manual en tu proyecto.
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) Código que se ejecuta de forma asíncrona.
* [Retrofit](https://square.github.io/retrofit/) is a Type-safe HTTP client for Android, Java and Kotlin by Square.
* [Gson](https://github.com/google/gson) is a serialization/deserialization library to convert objects into JSON and back.
* [MockK](https://mockk.io/ANDROID.html) mocking library for Kotlin
* [Espresso](https://developer.android.com/training/testing/espresso) Espresso es un framework de testing open source lanzado por Google el cual provee una API que permite crear pruebas de interfaz de usuario para simular interacciones de usuarios en una aplicación Android.

## Licence
    MIT License

    Copyright (c) 2023 Armando Rochin

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
