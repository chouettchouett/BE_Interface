/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Episode;
import model.Model;
import model.Personnage;
import model.Saison;

/**
 *
 * @author ember
 */
public class AdapterFunctional {
    private final ControllerRecherche domainController;

    public AdapterFunctional(Model model) {
        this.domainController = new ControllerRecherche(model);
    }

    public Map<String, Object> rechercheMots(String mots) {
        return domainController.rechercheMots(mots);
    }
    
    public void properlyCloseWindow() {
        domainController.properlyCloseWindow();
    }
    
    public List<String> getPersonnages() {
        return domainController.getPersonnages().stream().map(x -> x.getName()).collect(Collectors.toList());
    }
    
    public List<String> getSaisonsOriginal() {
        return domainController.getSaisons().stream().map(x -> "temp").collect(Collectors.toList());
    }
    
    public List<Saison> getSaisons() {
        return domainController.getSaisons();
    }
    
    public List<String> getEpisodes() {
        return domainController.getEpisodes().stream().map(x -> x.getTitre()).collect(Collectors.toList());
    }
    
    public List<String> recherchePersonnage(String p) {
        return domainController.recherchePersonnage(new Personnage("temp"));
    }
    
    public List<Episode> getEpisodesSaison(int saison) {
        return domainController.getEpisodesSaison(saison);
    }
    
    public int getNombreRepliquesEpisode(int saison, int episode) {
        return domainController.getNombreRepliquesEpisode(saison, episode);
    }
    
    public List<String> getPersonnagesEpisode(int saison, int episode) {
        return domainController.getPersonnagesEpisode(saison, episode);
    }
    
    public List<String> getTopMotsEpisode(int saison, int episode) {
        return domainController.getTopMotsEpisode(saison, episode);
    }
    
    public int getNombreRepliquesSaison(int saison) {
        return domainController.getNombreRepliquesSaison(saison);
    }
    
    public List<String> getPersonnagesSaison(int saison) {
        return domainController.getPersonnagesSaison(saison);
    }
    
    public List<String> getTopMotsSaison(int saison) {
        return domainController.getTopMotsSaison(saison);
    }
    
    public void creerGrapheNombreDeRepliquesSaison(int saison) {
        domainController.creerGrapheNombreDeRepliquesSaison(saison);
    }
    
    public void creerGrapheNombreDeRepliquesEpisode(int saison, int episode) {
        domainController.creerGrapheNombreDeRepliquesEpisode(saison, episode);
    }
    
    public int getNombreMotRepliqueEpisode(int saison, int episode){
        return domainController.getNombreMotRepliqueEpisode(saison, episode);
    }
    public int getNombreMotRepliqueSaison(int saison){
        return domainController.getNombreMotRepliqueSaison(saison);
    }
    
    public void creerGrapheEvolutionNombreMotSaison(int saison){
        domainController.creerGrapheEvolutionNombreMotSaison(saison);
    }
    
    public void creerGrapheEvolutionNombreMotEpisode(int saison, int episode){
        domainController.creerGrapheEvolutionNombreMotEpisode(saison, episode);
    }
}
