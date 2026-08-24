import pandas as pd
import numpy as np
from collections import Counter
import re

def calcular_gini(frecuencias):
    """Calcula el Índice de Gini de un array de frecuencias."""
    array = np.array(frecuencias, dtype=np.float64)
    if np.amin(array) < 0:
        array -= np.amin(array)
    array += 0.0000001

    array = np.sort(array)
    indice = np.arange(1, array.shape[0] + 1)
    n = array.shape[0]

    gini = ((np.sum((2 * indice - n  - 1) * array)) / (n * np.sum(array)))
    return gini

def analizar_perfil(archivo_csv, nombre_perfil):
    print(f"\n--- Analizando: {nombre_perfil} ---")

    try:
        df = pd.read_csv(archivo_csv)

        todas_las_palabras = []
        for titulo in df['Titulo Recomendado']:
            palabras = re.findall(r'\b\w+\b', str(titulo).lower())
            palabras_relevantes = [p for p in palabras if len(p) > 3]
            todas_las_palabras.extend(palabras_relevantes)

        conteo = Counter(todas_las_palabras)
        frecuencias = list(conteo.values())

        if not frecuencias:
            print("No hay datos suficientes para analizar.")
            return

        q1 = np.percentile(frecuencias, 25)
        q3 = np.percentile(frecuencias, 75)
        iqr = q3 - q1

        gini = calcular_gini(frecuencias)

        print(f"Total de palabras/temáticas distintas: {len(frecuencias)}")
        print(f"IQR (Dispersión): {iqr:.2f}")
        print(f"Índice de Gini (Aislamiento): {gini:.4f}")

    except FileNotFoundError:
        print(f"Error: No se encontró el archivo {archivo_csv}")

analizar_perfil("User_Nicho_01_historial.csv", "Perfil de Nicho")
analizar_perfil("User_Tend_01_historial.csv", "Perfil de Tendencias")
analizar_perfil("User_Ctrl_01_historial.csv", "Perfil de Control")