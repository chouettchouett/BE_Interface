package main;

//import com.mycompany.cours_ihm_version_propre.Vue;

import vue.VueStat;
import javax.swing.*;
public class Main {

    //private static Vue fenetre;
    private static VueStat fenetre;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Création de la fenêtre principale
            fenetre = new VueStat();

            // Afficher la fenêtre
            fenetre.setVisible(true);
        });
    }
}