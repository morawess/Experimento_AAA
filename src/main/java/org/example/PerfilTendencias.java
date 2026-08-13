package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class PerfilTendencias extends Sockpuppet{
    public PerfilTendencias(String idSesion, String plataformaObjetivo, WebDriver driver) {
        super(idSesion, plataformaObjetivo, driver);
    }

    @Override
    public void ejecutarEntrenamiento() {
        System.out.println("Iniciando entrenamiento: PERFIL DE TENDENCIAS.");
        try {
            driver.get(plataformaObjetivo);
            Thread.sleep(4000);

            // Buscamos contenido viral directamente
            WebElement barraDeBusqueda = driver.findElement(By.name("search_query"));
            String busquedaViral = "Tendencias virales Argentina";
            System.out.println("El perfil Tendencias va a buscar: " + busquedaViral);

            barraDeBusqueda.sendKeys(busquedaViral);
            Thread.sleep(1000);
            barraDeBusqueda.sendKeys(Keys.RETURN);
            Thread.sleep(5000); // Esperamos que carguen

            System.out.println("Extrayendo títulos del contenido viral...");
            List<WebElement> titulosDeVideos = driver.findElements(By.id("video-title"));

            for (WebElement tituloElemento : titulosDeVideos) {
                String textoTitulo = tituloElemento.getText();
                if (textoTitulo != null && !textoTitulo.trim().isEmpty()) {
                    historialRecomendaciones.add(textoTitulo);
                }
            }
            System.out.println("Se lograron extraer " + historialRecomendaciones.size() + " videos en tendencias.");

        } catch (Exception e) {
            System.out.println("Error en Perfil Tendencias: " + e.getMessage());
        }
    }
}
