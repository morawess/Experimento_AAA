package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;

public class PerfilControl extends Sockpuppet{
    public PerfilControl(String idSesion, String plataformaObjetivo, WebDriver driver) {
        super(idSesion, plataformaObjetivo, driver);
    }

    @Override
    public void ejecutarEntrenamiento() {
        System.out.println("Iniciando entrenamiento: PERFIL DE CONTROL (Aleatorio).");
        try {
            driver.get(plataformaObjetivo);
            Thread.sleep(4000);

            // 1. Rompemos la página en blanco buscando algo genérico al azar
            WebElement barraDeBusqueda = driver.findElement(By.name("search_query"));
            String[] temasGenericos = {"Música", "Noticias", "Documentales", "Deportes", "Cine"};
            String temaAzar = temasGenericos[new Random().nextInt(temasGenericos.length)];

            System.out.println("El perfil Control busca un tema al azar para empezar: " + temaAzar);
            barraDeBusqueda.sendKeys(temaAzar);
            Thread.sleep(1000);
            barraDeBusqueda.sendKeys(Keys.RETURN);
            Thread.sleep(5000); // Esperamos los resultados

            // 2. Ahora que hay videos en pantalla, capturamos esos resultados
            List<WebElement> videosDisponibles = driver.findElements(By.id("video-title"));

            // 3. Hacemos el consumo aleatorio
            if (!videosDisponibles.isEmpty()) {
                WebElement videoElegido = videosDisponibles.get(new Random().nextInt(videosDisponibles.size()));
                System.out.println("Haciendo clic aleatorio en un video...");
                videoElegido.click();

                // Esperamos que cargue el video y aparezca la barra lateral de recomendaciones
                Thread.sleep(6000);

                System.out.println("Extrayendo recomendaciones laterales post-clic...");
                List<WebElement> recomendacionesLaterales = driver.findElements(By.id("video-title"));

                for (WebElement tituloElemento : recomendacionesLaterales) {
                    String textoTitulo = tituloElemento.getText();
                    if (textoTitulo != null && !textoTitulo.trim().isEmpty()) {
                        historialRecomendaciones.add(textoTitulo);
                    }
                }
                System.out.println("Se lograron extraer " + historialRecomendaciones.size() + " videos recomendados.");
            }
        } catch (Exception e) {
            System.out.println("Error en Perfil Control: " + e.getMessage());
        }
    }
}
