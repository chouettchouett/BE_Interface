/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controller.AdapterFunctional;
import controller.ControllerDialogRecherche;
import model.Model;

/**
 *
 * @author ember
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        AdapterFunctional adapter = new AdapterFunctional(model);
        ControllerDialogRecherche dialog = new ControllerDialogRecherche(adapter);
        
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        //    System.out.println("Shutdown hook triggered. Closing resources...");
        //    adapter.properlyCloseWindow(); // Ferme gateway et Python
        //}));
        
        dialog.runUI();
    }
}
