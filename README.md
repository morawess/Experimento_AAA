## Análisis Estadístico: Midiendo la Burbuja de Filtro

Para poder cuantificar empíricamente en qué medida la personalización extrema de contenido genera una homogeneización del gusto cultural, este proyecto audita los catálogos de recomendación extraídos utilizando dos métricas matemáticas clave.

### 1. Índice de Gini (Nivel de Aislamiento)
Tradicionalmente utilizado en economía para medir la desigualdad de la riqueza en un país, en nuestro experimento lo adaptamos para medir la **"desigualdad en la distribución del contenido"**. 

$$G = \frac{\sum_{i=1}^{n} \sum_{j=1}^{n} |x_i - x_j|}{2n^2 \bar{x}}$$

Lo que evalúa es si YouTube nos recomienda todos los temas por igual, o si le da un monopolio de atención a un solo tema:
*   **Gini cercano a $0$:** Representa igualdad total. El algoritmo recomienda una gran diversidad de temáticas sin favoritismos (Pluralidad cultural).
*   **Gini cercano a $1$:** Representa desigualdad extrema. Una sola temática o un grupo muy reducido de palabras clave acapara casi todas las recomendaciones, confirmando el encierro del usuario (Cámara de eco / Filtro burbuja).

### 2. Rango Intercuartílico - IQR (Dispersión de Datos)
El IQR es una medida estadística que calcula la dispersión del 50% central de nuestros datos. Lo usamos porque elimina los valores extremos (outliers) y nos permite ver cómo se comporta el "núcleo duro" de las recomendaciones del algoritmo.

$IQR = Q_3 - Q_1$

*   **IQR Alto:** Significa que las temáticas sugeridas están muy dispersas y son variadas. Hay serendipia.
*   **IQR Bajo (o $0$):** Indica que la concentración de datos es altísima. El sistema está entregando un bloque de recomendaciones empaquetado, rígido y repetitivo.

---
### Cómo interpretar nuestros resultados

Al ejecutar la simulación y pasar los historiales por nuestro script de Python, los resultados validan la hipótesis del proyecto:

1.  **Perfil de Nicho:** Arrojó un Índice de Gini de **0.4767**. Esta alta desigualdad demuestra matemáticamente que el sistema de retención restringe severamente las opciones del usuario, homogeneizando su consumo hacia la temática que buscó inicialmente.
2.  **Perfil de Control:** Al realizar un consumo completamente aleatorio, arrojó un Índice de Gini de **0.0000** y un IQR de **0.00**. Al no existir un historial previo de retención para explotar, el algoritmo no pudo sesgar el contenido, lo que evidencia que la diversidad cultural solo se mantiene cuando no hay una personalización agresiva operando de fondo.

---
### Ejecutar el programa
1. Ejecutrar Main.java
2. Abrir terminal y ejecuar comando: 
         python analisis_burbuja.py
