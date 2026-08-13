package org.example;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        String plataforma = "https://www.youtube.com";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        System.out.println("--- INICIANDO AUDITORÍA ALGORÍTMICA ---");

        //Perfil de Nicho
        WebDriver driverNicho = new ChromeDriver(options);
        Sockpuppet botNicho = new PerfilNicho("User_Nicho_01", plataforma, driverNicho);
        botNicho.ejecutarEntrenamiento();
        botNicho.exportarHistorial();

        //Perfil de Tendencias
        WebDriver driverTendencias = new ChromeDriver(options);
        Sockpuppet botTendencias = new PerfilTendencias("User_Tend_01", plataforma, driverTendencias);
        botTendencias.ejecutarEntrenamiento();
        botTendencias.exportarHistorial();

        //Perfil de Control
        WebDriver driverControl = new ChromeDriver(options);
        Sockpuppet botControl = new PerfilControl("User_Ctrl_01", plataforma, driverControl);
        botControl.ejecutarEntrenamiento();
        botControl.exportarHistorial();

        System.out.println("--- AUDITORÍA FINALIZADA ---");
        }
    }
