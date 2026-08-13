package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PerfilNicho extends Sockpuppet{
    public PerfilNicho(String idSesion, String plataformaObjetivo, WebDriver driver) {
        super(idSesion, plataformaObjetivo, driver);
    }

    @Override
    public void ejecutarEntrenamiento() {
        System.out.println("Iniciando entrenamiento: PERFIL DE NICHO.");
        try {
            // 1. El bot abre YouTube
            System.out.println("Navegando a: " + plataformaObjetivo);
            driver.get(plataformaObjetivo);

            // Pausa para dejar que cargue la página inicial
            Thread.sleep(4000);

            // 2. Buscar la barra de búsqueda de YouTube por su nombre en el código HTML
            WebElement barraDeBusqueda = driver.findElement(By.name("search_query"));

            // 3. Definir la temática obsesiva del perfil (el "nicho")
            String tematicaNicho = "líneas de bajo de Luis Alberto Spinetta";
            System.out.println("El perfil " + idSesion + " va a buscar: " + tematicaNicho);

            // 4. Escribir en la barra y presionar ENTER mágicamente
            barraDeBusqueda.sendKeys(tematicaNicho);
            Thread.sleep(1000); // Pausa cortita para simular el tipeo
            barraDeBusqueda.sendKeys(Keys.RETURN);

            // Pausa para dejar que carguen los resultados de la búsqueda
            Thread.sleep(5000);
            System.out.println("Extrayendo títulos de los videos recomendados...");

            // Le decimos a Selenium que busque todos los elementos que tengan el ID "video-title"
            java.util.List<WebElement> titulosDeVideos = driver.findElements(By.id("video-title"));

            for (WebElement tituloElemento : titulosDeVideos) {
                // Obtenemos el texto visible del título
                String textoTitulo = tituloElemento.getText();

                // Si el texto no está vacío, lo agregamos a la memoria del bot
                if (textoTitulo != null && !textoTitulo.trim().isEmpty()) {
                    historialRecomendaciones.add(textoTitulo);
                }
            }

            System.out.println("Se lograron extraer " + historialRecomendaciones.size() + " videos recomendados.");

        } catch (Exception e) {
            System.out.println("Hubo un error en el perfil de nicho: " + e.getMessage());
        }
    }
}
