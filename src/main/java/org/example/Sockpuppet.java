package org.example;
import org.openqa.selenium.WebDriver;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Sockpuppet {
    protected String idSesion;
    protected String plataformaObjetivo;
    protected WebDriver driver;
    protected List<String> historialRecomendaciones;


    public Sockpuppet(String idSesion, String plataformaObjetivo, WebDriver driver) {
        this.idSesion = idSesion;
        this.plataformaObjetivo = plataformaObjetivo;
        this.driver = driver;
        this.historialRecomendaciones = new ArrayList<>();
    }

    public void exportarHistorial() {
        System.out.println("Exportando historial para el perfil: "+ idSesion);

        String nombreArchivo = idSesion + "_historial.csv"; //crea el nombre del archivo en base al nombre del bot

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("Titulo Recomendado");

            //recorre todo lo que el bot guardó y lo escribe línea por línea
            for (String recomendacion : historialRecomendaciones) {
                writer.println(recomendacion);
            }
            System.out.println("¡Éxito! Se generó el archivo: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Hubo un error al crear el archivo CSV: " + e.getMessage());
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public abstract void ejecutarEntrenamiento();
}
