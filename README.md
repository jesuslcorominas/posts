[![Build Status](https://app.bitrise.io/app/c8f440ac39b706ad/status.svg?token=td_FeK7sJD9B5Vmao1iU2A&branch=develop)](https://app.bitrise.io/app/c8f440ac39b706ad)

# Posts
Aplicación Android para mostrar un listado de Posts y el detalle de cada uno de ellos. La información tiene que estar disponible sin conexión. Si en la primera ejecución no hay conexión a internet no se mostrará ningún resultado.

## Arquitectura
Se ha realizado el proyecto siguiendo una arquitectura CLEAN dividida en 4 capas:

* __app__ Capa de aplicación. Interfaz de usuario e implementaciones de las fuentes de datos. Se hace uso de **Dagger 2** para inyección de dependencias y se sigue el patrón **MVVM** para la capa de presentación. 

* __usecases__ O **Interactors**. Casos de uso de la aplicación. Librería java/kotlin.

* __data__ Interfaces de acceso a datos y repositorios de estos datos. Librería java/kotlin.

* __domain__ Clases del dominio de la aplicación. Librería java/kotlin

Cada una de estas capas se ha implementado en un módulo independiente del proyecto para hacer más marcada la diferenciación y más fácil el mantenimiento. Además, hay un 5º módulo (__testShared__) con objetos *mockeados* que facilitan la implementación de los test unitarios.

El lenguaje escogido para el desarrollo de la app es **kotlin** y se usa progración reactiva gracias a **RxJava**, además de haber tratado de seguir una metodología TDD.

## Librerías
Se usan múltiples librerías para facilitar el desarrollo y minimizar errores al ser librerías ampliamente utilizadas y testadas, siendo algunas de las más importantes:

* __Architecture Components__ 

   * LiveData. 

   * ViewModel

   * Room

* __Retrofit__: Llamadas a servicios web.
* __Dagger 2__: Inyección de dependencias.
* __Picasso__: Carga de imágenes.

## Otros
Además de la funcionalidad principal de la aplicación, se hace uso de otros frameworks y librerías para facilitar el desarrollo de la misma, como herramientas para facilitar la depuración, analítica o gestión de errores.

* __Stetho__ Facilita la depuración al mostrar diversa información de la ejecución de la app en la consola de desarrollo de Google Chrome

* __Firebase Analytics__ Analítica.

* __Crashlytics__ Gestión de errores.

* __Bitrise__ Integración continua.
