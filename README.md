# Food Store - Sistema de Gestión de Pedidos

Food Store es una aplicación de consola desarrollada en **Java 21**, diseñada para gestionar el catálogo y los pedidos de un negocio de comidas. El sistema aplica los principios de la Programación Orientada a Objetos (POO) y utiliza persistencia en memoria mediante colecciones.

---

## Requisitos Previos

Para poder ejecutar este proyecto, necesitas tener instalado en tu sistema:
* **Java Development Kit (JDK) 21** o superior.
* Un Entorno de Desarrollo Integrado (IDE) compatible con Java, como **IntelliJ IDEA**, **Eclipse** o **Apache NetBeans**.

---

## Cómo Ejecutar el Proyecto

Sigue estos pasos para levantar el sistema en tu máquina local:

1. **Descargar el proyecto:**
   Clona este repositorio o descarga el archivo `.zip` y descomprímelo en tu computadora.

2. **Abrir en el IDE:**
   Abre tu IDE de preferencia y selecciona la opción de importar o abrir un proyecto existente. Selecciona la carpeta raíz del proyecto `Food Store`.

3. **Localizar el archivo principal:**
   Navega por el árbol de directorios hasta encontrar la clase principal, ubicada en:
   `src/vergara_xavier_tpi/Main.java`

4. **Compilar y Ejecutar:**
   Haz clic derecho sobre el archivo `Main.java` y selecciona la opción **"Run 'Main.main()'"** (o su equivalente según tu IDE).

5. **Interactuar con el menú:**
   Asegúrate de tener abierta la ventana de consola/terminal dentro de tu IDE. El sistema desplegará el menú interactivo, donde podrás utilizar el teclado numérico y la tecla `Enter` para navegar por las distintas opciones de gestión (Categorías, Productos, Usuarios y Pedidos).

---

## Estructura del Proyecto

El código fuente está modularizado en los siguientes paquetes para separar responsabilidades:

* `vergara_xavier_tpi`: Contiene la clase `Main` con la interfaz de usuario en consola.
* `entities`: Clases del modelo de dominio (POO).
* `enums`: Enumeradores para estados, roles y formas de pago.
* `services`: Capa de lógica de negocio y gestión de colecciones en memoria.
* `exception`: Clases para el manejo de excepciones personalizadas.
* `interfaces`: Contratos de comportamiento común.
