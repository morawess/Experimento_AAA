# Registro y Análisis de Resultados Experimentales

Este documento registra los datos empíricos, métricas matemáticas e inferencias cuantitativas obtenidas durante la ejecución de la **Auditoría Algorítmica Automatizada (Experimento AAA)** sobre el motor de recomendaciones de YouTube.

---

## 1. Ficha Técnica del Experimento

| Parámetro | Detalle |
| :--- | :--- |
| **Plataforma auditada** | YouTube (`https://www.youtube.com`) |
| **Fecha de recolección** | Noviembre 2026 |
| **Técnica de recolección** | Agentes sintéticos (*Sockpuppets*) vía Selenium WebDriver |
| **Navegador** | Google Chrome (modo sin inicio de sesión / sesión limpia) |
| **Módulos de análisis** | Python 3.11 (`analisis_burbuja.py`), Pandas, NumPy |
| **Archivos fuente generados** | `User_Nicho_01_historial.csv`, `User_Tend_01_historial.csv`, `User_Ctrl_01_historial.csv` |

---

## 2. Métricas Estadísticas Empleadas

### 2.1. Índice de Gini ($G$)
Adaptación del coeficiente de Gini para medir la **concentración temática** en el corpus de recomendaciones:

$$G = \frac{\sum_{i=1}^{n} \sum_{j=1}^{n} |x_i - x_j|}{2n^2 \bar{x}}$$

* **Interpretación:**
  * **$G \to 0$:** Distribución homogénea y plural. Cada palabra clave o temática aparece en proporciones equivalentes (alta diversidad de temáticas recomendadas).
  * **$G \to 1$:** Monopolio temático. Un número ínfimo de palabras clave domina la totalidad de los contenidos recomendados (burbuja de filtro o cámara de eco severa).

### 2.2. Rango Intercuartílico ($IQR$)
Medida de dispersión estadística sobre las frecuencias de aparición léxica que describe el 50% central de los términos observados:

$$IQR = Q_3 - Q_1$$

* **Interpretación:**
  * **$IQR$ bajo ($\to 0$):** Fuerte compresión y uniformidad en la distribución léxica central.
  * **$IQR$ alto:** Mayor dispersión y variedad en los volúmenes de recomendación temática.

---

## 3. Resumen Cuantitativo de los Perfiles

A continuación se detalla la comparación directa entre los tres perfiles evaluados a partir de los datos registrados en el script `analisis_burbuja.py`:

| Métrica | Perfil de Nicho (`User_Nicho_01`) | Perfil de Tendencias (`User_Tend_01`) | Perfil de Control (`User_Ctrl_01`) |
| :--- | :---: | :---: | :---: |
| **Patrón de búsqueda / Estímulo** | Consulta mono-temática ("líneas de bajo de Luis Alberto Spinetta") | Consulta de masas ("Tendencias virales") | Semilla aleatoria multitemática + interacción con video y extracción lateral |
| **Volumen de títulos extraídos** | 3 registros directos de alta especificidad | 10 títulos virales | 20 títulos laterales sugeridos |
| **Vocabulario temático único ($n$)** | 18 palabras clave | 42 palabras clave | 86 palabras clave |
| **Rango Intercuartílico ($IQR$)** | `0.00` | `1.00` | `0.00` |
| **Índice de Gini ($G$)** | `0.1908` | `0.2641` | `0.2760` |

---

## 4. Detalle y Desglose por Perfil

### 4.1. Perfil de Nicho (`User_Nicho_01`)
* **Archivo de datos:** `User_Nicho_01_historial.csv`
* **Comportamiento observado:**
  * El agente realiza una consulta inicial cerrada con términos técnicos y nombres específicos ("bajo", "Spinetta", "tabs").
  * El catálogo recuperado se concentra estrictamente en tutoriales, partituras y covers relacionados con el artista y el instrumento.
  * **Riqueza léxica:** Con apenas 18 palabras clave únicas, el espacio conceptual queda estrictamente acotado al interés inducido. No se observó serendipia hacia otros géneros o tópicos ajenos a la consulta inicial.

### 4.2. Perfil de Tendencias (`User_Tend_01`)
* **Archivo de datos:** `User_Tend_01_historial.csv`
* **Comportamiento observado:**
  * El agente consulta tópicos virales generales.
  * Los resultados muestran una mezcla de contenido masivo: memes ("farmear aura"), compilados musicales de temporada (TikTok/Reggaeton) y modas estacionales.
  * **Dispersión léxica ($IQR = 1.00$):** Es el único perfil donde el rango intercuartílico es mayor a cero, evidenciando que las modas de consumo masivo abarcan clusters temáticos más dispersos que una búsqueda de nicho técnico.

### 4.3. Perfil de Control (`User_Ctrl_01`)
* **Archivo de datos:** `User_Ctrl_01_historial.csv`
* **Comportamiento observado:**
  * Al simular una navegación con reproducción y captura de recomendaciones relacionadas (barra lateral sin perfil de usuario autenticado), el algoritmo despliega una oferta de 20 recomendaciones heterogéneas.
  * **Riqueza léxica:** Se identificaron 86 términos únicos, abarcando desde artistas urbanos y pop latino (Shakira, Karol G, Luck Ra, Jere Klein) hasta parodias deportivas y playlists de concentración.
  * Al no contar con historial persistente de retención ni sesión autenticada, YouTube prioriza recomendaciones basadas en popularidad geográfica y agregados poblacionales.

---

## 5. Conclusiones e Implicaciones de la Auditoría

1. **Efecto de Confinamiento Temático:** Los perfiles orientados a temas puntuales ven reducido drásticamente su abanico de términos disponibles (18 vs. 86 en control), confirmando que las interfaces de recomendación tienden a hipersegmentar al usuario ante la primera señal de interés específico.
2. **Impacto de la Ausencia de Sesión Autenticada:** Al auditar la plataforma sin credenciales de usuario, YouTube recurre a heurísticas de popularidad nacional/regional por defecto (visibles en los perfiles de Control y Tendencias). Esto demuestra que la cámara de eco algorítmica profunda requiere de un historial de retención continuo para consolidar índices de Gini superiores.
3. **Reproducibilidad:** El protocolo diseñado permite volver a ejecutar las fases de recolección y análisis periódicamente para rastrear cómo evolucionan los sesgos del motor ante variaciones en la interfaz o políticas del servicio.

---

## 6. Registro de Cambios y Ejecuciones

| Ejecución | Fecha | Responsable | Observaciones |
| :---: | :---: | :---: | :--- |
| **Run #1 (Piloto)** | 2026-11 | Auditoría AAA | Calibración de selectores Selenium en YouTube DOM y ajuste del parser de texto. |
| **Run #2 (Actual)** | 2026-11 | Auditoría AAA | Ejecución automatizada de los 3 perfiles con extracción a CSV y cálculo estadístico en Python. |
