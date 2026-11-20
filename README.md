# Auditoría Algorítmica Automatizada (Experimento AAA)

Framework experimental para la detección, captura y cuantificación matemática de sesgos de recomendación, homogeneización de contenidos y burbujas de filtro (*filter bubbles*) en plataformas digitales mediante agentes sintéticos (*sockpuppets*).

---

## 1. Descripción General

Los sistemas de recomendación modernos optimizan métricas de retención y tiempo de permanencia, lo que con frecuencia deriva en el confinamiento del usuario dentro de clústeres temáticos cerrados (cámaras de eco o filtros burbuja). 

Este proyecto implementa una auditoría algorítmica sobre YouTube compuesta por dos etapas independientes y reproducibles:
1. **Fase de Ingesta y Web Scraping Automatizado (Java + Selenium WebDriver):** Agentes sintéticos parametrizados simulan patrones específicos de búsqueda y consumo de video en sesiones limpias, extrayendo los títulos sugeridos por el algoritmo hacia archivos tabulares (`.csv`).
2. **Fase de Análisis Estadístico (Python + NumPy/Pandas):** Procesa el léxico recopilado, analiza la frecuencia de términos clave y calcula métricas formales de concentración temática y dispersión.

---

## 2. Metodología Experimental

El experimento evalúa el comportamiento del motor de recomendación frente a tres perfiles conductuales controlados:

* **Perfil de Nicho (`PerfilNicho`):** Simula un usuario con un interés hiperespecífico y técnico (por ejemplo, líneas de bajo instrumental). Evalúa la rapidez con la que el sistema restringe las opciones periféricas y satura la oferta en torno a una única temática.
* **Perfil de Tendencias (`PerfilTendencias`):** Simula un consumidor orientado a viralidad y contenido masivo. Permite observar los patrones de recomendación guiados por popularidad general.
* **Perfil de Control (`PerfilControl`):** Actúa como línea base neutral. Selecciona categorías temáticas aleatorias, reproduce un video y recopila las recomendaciones de la barra lateral sin historial previo, evidenciando el comportamiento estándar del algoritmo en ausencia de sesgo previo inducido.

### Métricas de Evaluación Cuantitativa

* **Índice de Gini ($G$):** Adaptado para medir el grado de monopolio o concentración de las palabras clave en las sugerencias:
  * $G \to 0$: Distribución equitativa y plural de contenidos (alta diversidad temática).
  * $G \to 1$: Concentración extrema en un grupo reducido de términos (burbuja cerrada).
* **Rango Intercuartílico ($IQR$):** Cuantifica la dispersión del 50% central de las frecuencias de aparición léxica ($Q_3 - Q_1$), descartando valores atípicos para medir la rigidez o variabilidad de la muestra.

---

## 3. Estructura del Repositorio

```text
Experimento_AAA/
├── pom.xml                               # Configuración de compilación Maven y dependencias Selenium
├── requirements.txt                      # Dependencias del módulo estadístico de Python
├── README.md                             # Documentación técnica principal y guía de uso
├── RESULTADOS.md                         # Registro formal, tablas y análisis de los resultados
├── analisis_burbuja.py                   # Script de procesamiento de lenguaje natural y métricas estadísticas
│
├── src/main/java/org/example/
│   ├── Main.java                         # Punto de entrada y orquestador del ciclo de vida de los bots
│   ├── Sockpuppet.java                   # Clase base abstracta que define el comportamiento del bot
│   ├── PerfilNicho.java                  # Implementación de bot para búsquedas hiperespecíficas
│   ├── PerfilTendencias.java             # Implementación de bot orientado a tendencias virales
│   └── PerfilControl.java                # Implementación de bot de control / navegación neutral
│
└── *.csv                                 # Historiales de títulos exportados por los perfiles
    ├── User_Nicho_01_historial.csv
    ├── User_Tend_01_historial.csv
    └── User_Ctrl_01_historial.csv
```

---

## 4. Requisitos del Entorno

Asegúrese de contar con las siguientes herramientas instaladas en su sistema:

1. **Java Development Kit (JDK):** Versión 21 o superior (compatible con Java 25/26).
2. **Apache Maven:** O un IDE compatible con proyectos Maven (IntelliJ IDEA, Eclipse, VS Code).
3. **Google Chrome:** Versión estable actualizada. *Nota: Selenium 4.x gestiona el controlador `chromedriver` de manera automática a través de Selenium Manager.*
4. **Python:** Versión 3.8 o superior.

---

## 5. Instalación

### 5.1. Clonar el Repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd Experimento_AAA
```

### 5.2. Dependencias de Java (Maven)
Si utiliza Maven desde terminal, descargue las dependencias y compile el proyecto:
```bash
mvn clean compile
```
*Si utiliza IntelliJ IDEA u otro IDE, simplemente abra la carpeta del proyecto y sincronice el archivo `pom.xml`.*

### 5.3. Dependencias de Python
Instale los paquetes necesarios para el cálculo numérico y manipulación de datos:
```bash
pip install -r requirements.txt
```
*(Alternativamente: `pip install pandas numpy`)*

---

## 6. Guía de Ejecución

El flujo experimental se divide en dos fases secuenciales:

### Fase 1: Recolección Automatizada de Datos (Java)

Ejecute la clase principal `Main.java` para iniciar los tres perfiles de navegación.

* **Desde un IDE (Recomendado):**
  Abra `src/main/java/org/example/Main.java` y ejecute el método `main()`.
* **Desde terminal con Maven:**
  ```bash
  mvn compile exec:java -Dexec.mainClass="org.example.Main"
  ```

Durante la ejecución, cada agente abrirá una instancia automatizada de Google Chrome, realizará la navegación programada y generará automáticamente su archivo `.csv` correspondiente en la raíz del proyecto:
* `User_Nicho_01_historial.csv`
* `User_Tend_01_historial.csv`
* `User_Ctrl_01_historial.csv`

### Fase 2: Procesamiento Estadístico (Python)

Una vez generados los archivos `.csv`, ejecute el script de análisis:

```bash
python analisis_burbuja.py
```

El script imprimirá en consola el desglose por perfil:
* Cantidad de palabras clave únicas identificadas.
* Dispersión de términos ($IQR$).
* Nivel de aislamiento / concentración temática (Índice de Gini).

---

## 7. Registro de Resultados

Para consultar la interpretación detallada de los resultados, las tablas comparativas entre perfiles y las implicaciones teóricas de la auditoría algorítmica, revise el archivo:

👉 **[RESULTADOS.md](RESULTADOS.md)**

---

## 8. Configuración y Personalización

Para modificar los parámetros del experimento:
* **Términos de búsqueda:** Modifique las variables `tematicaNicho` en [PerfilNicho.java](src/main/java/org/example/PerfilNicho.java) o `busquedaViral` en [PerfilTendencias.java](src/main/java/org/example/PerfilTendencias.java).
* **Categorías de control:** Ajuste el arreglo `temas` en [PerfilControl.java](src/main/java/org/example/PerfilControl.java).
* **Tiempos de espera y simulación:** Los valores en milisegundos de `Thread.sleep()` pueden calibrarse según el ancho de banda y la latencia de red disponible.
