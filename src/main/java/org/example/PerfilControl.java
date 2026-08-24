package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerfilControl extends Sockpuppet {

    public PerfilControl(String idSesion, String plataformaObjetivo, WebDriver driver) {
        super(idSesion, plataformaObjetivo, driver);
    }

    @Override
    public void ejecutarEntrenamiento() {
        System.out.println("Iniciando entrenamiento: PERFIL DE CONTROL (Aleatorio).");
        try {
            driver.manage().window().maximize();
            driver.get(plataformaObjetivo);
            Thread.sleep(4000);

            WebElement barra = driver.findElement(By.name("search_query"));
            //ACLARACIÓN: como al no iniciar sesión Youtube no te muestra videos recomendados o tendencia se tuvo que cambiar el enfoque y darle temas de búsqueda amplios.
            String[] temas = {"Recetas de marquise de chocolate", "Técnicas de natación", "Bajo eléctrico tutoriales", "Astrología ascendente"}; //estos temas se pueden cambaiar a lo que queramos
            String tema = temas[new Random().nextInt(temas.length)]; //random de los temas elegidos

            System.out.println("Perfil Control buscando un tema al azar para empezar: " + tema);
            barra.sendKeys(tema);
            Thread.sleep(1000);
            barra.sendKeys(Keys.RETURN);
            Thread.sleep(6000);

            List<WebElement> resultados = driver.findElements(By.id("video-title"));
            List<WebElement> videosNormales = new ArrayList<>();
            for (WebElement res : resultados) {
                String link = res.getAttribute("href");
                if (link != null && !link.contains("/shorts/")) {
                    videosNormales.add(res);
                }
            }

            if (!videosNormales.isEmpty()) {
                int limite = Math.min(5, videosNormales.size());
                WebElement videoElegido = videosNormales.get(new Random().nextInt(limite));

                System.out.println("Haciendo clic aleatorio en un video normal...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", videoElegido);

                System.out.println("Simulando visualización (engagement) y esperando barra lateral...");
                Thread.sleep(10000);

                System.out.println("Extrayendo recomendaciones usando atributos ocultos...");
                List<WebElement> recomendaciones = driver.findElements(By.xpath("//a[@id='video-title']"));

                for (WebElement rec : recomendaciones) {
                    String texto = rec.getAttribute("title");

                    if (texto == null || texto.trim().isEmpty()) {
                        texto = rec.getAttribute("innerText");
                    }

                    if (texto != null && !texto.trim().isEmpty()) {
                        historialRecomendaciones.add(texto.trim());
                    }
                }
                System.out.println("Se extrajeron " + historialRecomendaciones.size() + " videos recomendados.");
            } else {
                System.out.println("No se encontraron videos normales para hacer clic.");
            }

        } catch (Exception e) {
            System.out.println("Error en Perfil Control: " + e.getMessage());
        }
    }
}