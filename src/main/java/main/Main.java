/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controller.AdapterFunctional;
import controller.ControllerDialogRecherche;

/**
 *
 * @author ember
 */
public class Main {
    public static void main(String[] args) {
        AdapterFunctional adapter = new AdapterFunctional();
        ControllerDialogRecherche dialog = new ControllerDialogRecherche(adapter);
        
        dialog.runUI();
    }
}
