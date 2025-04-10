package controller;

import java.util.stream.Collectors;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import model.Episode;
import model.Personnage;
import model.Saison;

/**
 *
 * @author ember
 */

public class ControllerDialogRecherche {
    private final DefaultComboBoxModel<String> comboBoxModelTypeRecherche;
    private final DefaultComboBoxModel<String> comboBoxModelPersonnage;
    private final DefaultComboBoxModel<String> comboBoxModelSaison;
    private final DefaultComboBoxModel<String> comboBoxModelEpisode;
    private final style.CustomJTextField rechercheMot;
    private final ControllerRecherche controllerRecherche;
    List<Personnage> personnages;
    List<Saison> saisons;
    List<Episode> episodes;

    public ControllerDialogRecherche(style.CustomJTextField rechercheMot) {
        this.rechercheMot = rechercheMot;
        controllerRecherche = new ControllerRecherche();
        
        comboBoxModelTypeRecherche = new DefaultComboBoxModel<>();
        List<String> choix = List.of("Recherche de mots", "Recherche de personnages", "Recherche de saisons", "Recherche d'épisodes");
        comboBoxModelTypeRecherche.addAll(choix);
        comboBoxModelTypeRecherche.setSelectedItem(choix.get(0));
        
        personnages = controllerRecherche.getPersonnages();
        saisons = controllerRecherche.getSaisons();
        episodes = controllerRecherche.getEpisodes();
        List<String> personnagesString = personnagesToString();
        List<String> saisonsString = saisonsToString();
        List<String> episodesString = episodesToString();
        
        comboBoxModelPersonnage = new DefaultComboBoxModel<>();
        comboBoxModelPersonnage.addAll(personnagesString);
        comboBoxModelPersonnage.setSelectedItem(personnagesString.get(0));
        
        comboBoxModelSaison = new DefaultComboBoxModel<>();
        comboBoxModelSaison.addAll(saisonsString);
        comboBoxModelSaison.setSelectedItem(saisonsString.get(0));
        
        comboBoxModelEpisode = new DefaultComboBoxModel<>();
        comboBoxModelEpisode.addAll(episodesString);
        comboBoxModelEpisode.setSelectedItem(episodesString.get(0));
    }
    
    public List<String> personnagesToString() {
        return personnages.stream().map(Personnage::toString).collect(Collectors.toList());
    }
    
    public List<String> saisonsToString() {
        return saisons.stream().map(Saison::toString).collect(Collectors.toList());
    }
    
    public List<String> episodesToString() {
        return episodes.stream().map(Episode::toString).collect(Collectors.toList());
    }
    
    public Personnage getPersonnageFromString(String stringRepr) {
        return personnages.stream().filter(p -> p.toString().equals(stringRepr)).findFirst().orElse(null);
    }
    
    public Episode getEpisodeFromString(String stringRepr) {
        return episodes.stream().filter(p -> p.toString().equals(stringRepr)).findFirst().orElse(null);
    }
    
    public Saison getSaisonFromString(String stringRepr) {
        return saisons.stream().filter(p -> p.toString().equals(stringRepr)).findFirst().orElse(null);
    }
    
    public List<String> recherchePersonnage(String personnageString) {
        Personnage p = getPersonnageFromString(personnageString);
        
        return controllerRecherche.recherchePersonnage(p);
    }

    public DefaultComboBoxModel<String> getComboBoxModelTypeRecherche() {
        return comboBoxModelTypeRecherche;
    }
    
    public DefaultComboBoxModel<String> getComboBoxModelPersonnage() {
        return comboBoxModelPersonnage;
    }
    
    public DefaultComboBoxModel<String> getComboBoxModelSaison() {
        return comboBoxModelSaison;
    }
    
    public DefaultComboBoxModel<String> getComboBoxModelEpisode() {
        return comboBoxModelEpisode;
    }
}