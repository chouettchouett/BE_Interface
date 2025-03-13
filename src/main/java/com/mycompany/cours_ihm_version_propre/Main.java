package com.mycompany.cours_ihm_version_propre;

//import com.mycompany.cours_ihm_version_propre.Vue;

import javax.swing.*;
public class Main {

    private static Vue fenetre;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Création de la fenêtre principale
            fenetre = new Vue();

            // Afficher la fenêtre
            fenetre.setVisible(true);
        });
    }
}