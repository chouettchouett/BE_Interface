/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;

import model.Episode;
import model.Personnage;
import model.Saison;

/**
 * Temporaire, quand la liaisons python-java sera implémenté on fera autrement
 * Les données sont seulement des données de tests
 */
public class ControllerRecherche {
    public List<Saison> getSaisons() {
        return List.of(new Saison(1), new Saison(2), new Saison(3), new Saison(4), new Saison(5),
                new Saison(6), new Saison(7), new Saison(8), new Saison(9), new Saison(10));
    }
    
    public List<Episode> getEpisodes() {
        return List.of(new Episode(1, 1, "Monica Gets A Roomate"), new Episode(1, 2, "The Sonogram at the End"));
    }
    
    public List<Personnage> getPersonnages() {
        return List.of(new Personnage("Monica"), new Personnage("Ross"), new Personnage("Rachel"), 
                new Personnage("Joey"), new Personnage("Chandler"), new Personnage("Phoebe"));
    }
    
    public List<String> recherchePersonnage(Personnage p) {
        return List.of("x", "xx", "xxx", "xxxx", "xxxxx");
    }
}
