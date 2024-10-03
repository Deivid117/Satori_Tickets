<h1 align="center">Satori Tickets</h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/b10a8add-bc5e-490e-93e1-4599f2169cbe" alt="Logo Satori Tickets" width="300">
</p>

![Kotlin](https://img.shields.io/badge/Kotlin-1.8.10-blueviolet?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.4.0-brightgreen?logo=jetpackcompose&logoColor=white)
![Architecture-MVVM](https://img.shields.io/badge/Architecture-MVVM-blue)
![Retrofit](https://img.shields.io/badge/Retrofit-2.9.0-yellow?logo=retrofit)
![Coroutines](https://img.shields.io/badge/Coroutines-1.7.0-purple?logo=kotlin&logoColor=white)
![Room](https://img.shields.io/badge/Room-2.5.0-red?logo=android&logoColor=white)
![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-2.45-yellowgreen?logo=android&logoColor=white)
![DataStore](https://img.shields.io/badge/DataStore-1.1.1-orange?logo=android&logoColor=white)
![Coil](https://img.shields.io/badge/Coil-2.4.0-tomato?logo=coil&logoColor=white)
![BiometricPrompt](https://img.shields.io/badge/BiometricPrompt-1.2.0alpha05-cyan?logo=android&logoColor=white)
![Lottie](https://img.shields.io/badge/Lottie-6.0.0-lightgreen.svg?logo=lottie&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-1.2.1-salmon?logo=android&logoColor=white)
![Min SDK Version](https://img.shields.io/badge/Min%20SDK%20Version-24-lightblue)

## ¡Bienvenido a la Experiencia Definitiva de Videojuegos! :exploding_head:
¿Eres un apasionado de los videojuegos? ¿Te encanta descubrir nuevos títulos y sumergirte en aventuras épicas? Entonces, estás en el lugar correcto. Te presento Games App que te conecta con el fascinante mundo de los videojuegos.

🎮 **Explora, Descubre y Juega**

Nuestra aplicación te ofrece una interfaz intuitiva y atractiva que te permite explorar una vasta colección de videojuegos.<br>
Desde los clásicos que marcaron época hasta los lanzamientos más recientes, aquí encontrarás todo lo que necesitas para satisfacer tu sed de aventura.

🌟 **Características Inigualables**

- **Información Detallada**: Conoce la trama, developers, puntuaciones y plataformas disponibles de tus títulos favoritos.
- **Lanzamientos Recientes**: Recibe información acerca de los nuevos lanzamientos y juegos que estén en tendencia.
- **Navegación Intuitiva**: Disfruta de una experiencia fluida mientras navegas por nuestras secciones.

## Descripción :open_book:


### Características clave

## Tecnologías utilizadas :iphone:
* **Jetpack Compose**: Toolkit moderno de UI de Android que permite construir interfaces de usuario de manera declarativa.
* **MVVM**: Enfoque arquitectónico que desacopla la lógica de negocio de la interfaz de usuario. Facilita la creación de interfaces reactivas con una clara separación de responsabilidades entre las capas.
* **Retrofit**: Librería de cliente HTTP que permite realizar solicitudes HTTP/REST en Android. Facilita el consumo de APIs y convierte las respuestas JSON en objetos Kotlin para su fácil manipulación.
* **Dagger Hilt**: Biblioteca de inyección de dependencias que simplifica la creación y gestión de dependencias en Android.
* **Coroutines**: Son una forma avanzada y sencilla de manejar tareas asíncronas. Facilitan la ejecución de operaciones de larga duración, como solicitudes de red o tareas de I/O, sin bloquear el hilo principal.
* **Navigation Compose**: Permite gestionar la navegación entre pantallas de manera declarativa dentro de aplicaciones construidas con Jetpack Compose.
* **Material Design 3**: Ofrece una experiencia de usuario moderna y coherente a través de temas personalizables, componentes de UI optimizados y nuevas directrices para interfaces accesibles y atractivas.
* **Coil**: Biblioteca ligera para la carga de imágenes en Android. Proporciona una forma rápida y eficiente de mostrar imágenes en la UI.
* **DataStore**: Permite almacenar datos de preferencias o claves-valor de manera eficiente y segura, con un enfoque basado en Kotlin coroutines y Flow.
* **ROOM**: Biblioteca de persistencia local de datos de Android basada en SQLite. Proporciona una capa de abstracción que facilita el acceso a la base de datos, integrándose de manera sencilla con coroutines y LiveData.
* **Lottie**: Biblioteca para Android que permite reproducir animaciones de alta calidad exportadas desde After Effects en formato JSON.

## Estructura del proyecto :hammer_and_wrench:

El proyecto está basado en los principios de Clean Architecture y sigue el patrón MVVM (Model-View-ViewModel), lo que asegura una clara separación de responsabilidades y facilita la escalabilidad y el mantenimiento del código. Cada feature se organiza de manera modular, facilitando su desarrollo, prueba y mantenimiento.

### Capas del proyecto

* **Capa de Datos (Data)**: Aquí se encuentra la implementación de la lógica de persistencia y la interacción con APIs externas. Esta capa es responsable de proporcionar datos a la capa de dominio.
  * ***Database***: Aquí se encuentran las implementaciones de los datos de origen con ROOM para el almacenamiento local.
    * **DAO**: Es la interfaz que define los métodos de las entities para interactuar con la base de datos.
    * **Entities**: Objetos que representan los datos tal como se reciben de la base de datos.
    * **Mappers**: Aquí se encuentran las funciones que se encargan de transformar modelos de database en modelos de dominio.
  * ***Remote***: Aquí se encuentran las implementaciones de los datos de origen con Retrofit para las llamadas a la API.
    * **Mappers**: Aquí se encuentran las funciones que se encargan de transformar modelos dto en modelos de dominio.
    * **Modelos DTO**: Objetos que representan los datos tal como se reciben de la API.
  * ***Repositorios***: Implementan las interfaces de los repositorios definidos en la capa de dominio, y se encargan de obtener datos de diferentes fuentes (API o base de datos local).

* **Capa de DI**: Es responsable de proporcionar las dependencias que las diferentes clases necesitan para funcionar, sin que estos tengan que crearlas por sí mismos. En lugar de instanciar las dependencias directamente, las clases reciben (o inyectan) sus dependencias desde fuera, lo que promueve el principio de inversión de dependencias.

* **Capa de Dominio (Domain)**: Esta capa es independiente de cualquier detalle de implementación. Contiene las reglas de negocio y las entidades del sistema.
  * ***Repositorios***: Interfaces que definen los métodos necesarios para interactuar con los datos del sistema. Estas interfaces no conocen detalles específicos de dónde provienen los datos o cómo se almacenan.
  * ***UseCases***: Definen las acciones que la aplicación puede realizar, como autenticar un usuario, obtener una lista de juegos, etc.
  * ***Modelos***: Representan los objetos de negocio del sistema (por ejemplo, User, Game, etc.).

* **Capa de Presentación (Presentation)**: Esta capa contiene la lógica relacionada con la UI y es donde se maneja la interacción del usuario.

  * ***ViewModels***: Siguiendo el patrón MVVM, los ViewModels son responsables de preparar los datos para ser mostrados en la UI y manejar las interacciones de los usuarios.
  * ***Composables***: Esta es la capa donde se definen las interfaces de usuario utilizando Jetpack Compose. Los Composables reciben los datos del ViewModel y reaccionan a los cambios de estado.
  * ***State***: Utiliza StateFlow o LiveData para observar cambios en los datos.

* **Navegación**: Define la ruta de cada feature, así como también la forma en que navega hacia otras pantallas de acuerdo a las interacciones del usuario. Inicializa el ViewModel y los State.

### Organización modular por features

* **Core**: Contiene todos los archivos que se comparten entre todos los módulos o la lógica para las llamadas a la API, ROOM, inyección de dependencias, Data Store, etc.
* **Splash**: Se encarga de mostrar las animaciones del splash screen y recuperar si el usuario ya ha iniciado sesión o no.
* **Login**: Responsable de permitir que el usuario inicie sesión en la app, así como también de validar sus credenciales o permitirle usar biométricos si se encuentran disponibles.
* **Signup**: Aquí el usuario se da de alta en la app, valida los datos ingresados e igualmente permite activar los datos biométricos.
* **Home**: La entrada principal a la aplicación, en ella podrá encontrar los juegos más recientes o mejores calificados, así como también acceder a la información de las plataformas o géneros.
* **Platforms**: Se encarga de mostrar un listado de plataformas, permitiendo navegar a los detalles de cada una.
  * **Platforms_Details**: Proporciona información detallada acerca de una plataforma en específico.
* **Genres**: Se encarga de mostrar un listado de géneros, permitiendo navegar a los detalles de cada uno.
  * **Genres_Details**: Proporciona información detallada acerca de un género en específico.
* **Games**: Se encarga de mostrar un listado de juegos, permitiendo navegar a los detalles de cada uno.
  * **Games_Details**: Proporciona información detallada acerca de un juego en específico, permitiendo incluso marcarlo como favorito.
* **Favorite_Games**: Se encarga de mostrar el listado de juegos que han sido marcados como favoritos por el usuario.
* **Profile**: Muestra la información de la cuenta del usuario. Desde aquí es posible cerrar sesión.
  * **Edit_Profile**: Permite al usuario editar su información y validar los datos.

## Requisitos :bookmark_tabs:

1. Android Studio Jellyfish | 2023.3.1 o superior
2. Gradle Version 7.5
3. Kotlin 1.8.10
4. Android API 24 o superior (Android 7+)
5. API Key

## Instalación :arrow_down:

1. Clona el repositorio:
   ```
    git clone https://github.com/Deivid117/Games-App.git
2. Obtén tu API Key :key: registrándote en la siguiente página
https://rawg.io/apidocs

3. Agrega tu API Key en el archivo ***local.properties*** de esta manera<br>
***API_KEY="tu api key"***

4. Ejecuta el proyecto :rocket:

## Capturas :camera:

### Modo Claro :sun_with_face:
<p align="center">
  <img src="https://github.com/user-attachments/assets/cab0a0d8-efc4-4eb9-bd2c-54024131325f" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/468934e6-536e-4766-97a0-6dc87b77e1ac" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/70a126bc-dc14-4ebb-a64b-11e8a9d51331" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a8cd5098-d3bf-4164-9d4c-18f846132fb6" alt="Descripción de la imagen">
</p>

### Modo Oscuro :new_moon_with_face:
<p align="center">
  <img src="https://github.com/user-attachments/assets/eee03721-1cd6-47c0-afd1-d6c290fe64f3" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1ebeab9c-321f-4975-8428-4641a9795128" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/9c665b8f-4602-403a-86b0-8d4e00f5585f" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/1764f1af-50ca-456b-9c4f-e5de87dc7d92" alt="Descripción de la imagen">
</p>

## Video demostrativo :movie_camera:

## Autor :man_technologist:

*David Huerta* :copyright:	2022

email :email:: deivid.was.here117@gmail.com<br>
linkedin :man_office_worker:: [Perfil LinkedIn](https://www.linkedin.com/in/david-de-jes%C3%BAs-ju%C3%A1rez-huerta-159695241/)
