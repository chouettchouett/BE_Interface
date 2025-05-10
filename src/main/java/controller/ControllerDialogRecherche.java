package controller;

import java.util.stream.Collectors;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import model.Episode;
import model.Personnage;
import model.Saison;
import vue.VueStat;

/**
 *
 * @author ember
 */

public class ControllerDialogRecherche {
    private final AdapterFunctional adapter;
    private VueStat ui;
    
    private final DefaultComboBoxModel<String> comboBoxModelTypeRecherche;
    private final DefaultComboBoxModel<String> comboBoxModelPersonnage;
    private final DefaultComboBoxModel<String> comboBoxModelSaison;
    private final DefaultComboBoxModel<String> comboBoxModelEpisode;
    
    List<Personnage> personnages;
    List<Saison> saisons;
    List<Episode> episodes;
    
    public ControllerDialogRecherche(AdapterFunctional adapter) {
        this.adapter = adapter;
        
        comboBoxModelTypeRecherche = new DefaultComboBoxModel<>();
        List<String> choix = List.of("Recherche de mots", "Recherche de personnages", "Recherche de saisons", "Recherche d'épisodes");
        comboBoxModelTypeRecherche.addAll(choix);
        comboBoxModelTypeRecherche.setSelectedItem(choix.get(0));
        
        List<String> personnagesString = adapter.getPersonnages();
        List<String> saisonsString = adapter.getSaisonsOriginal();
        List<String> episodesString = adapter.getEpisodes();
        
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
    
    public void runUI() {
        SwingUtilities.invokeLater(() -> {
            ui = new VueStat(this);
            ui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            ui.setDialog(this);
            ui.setVisible(true);
        });
    }
    
    
    public void onUserAction() {
        ui.showLoading(true);

        new SwingWorker<List<List<Integer>>, Void>() {
            @Override
            protected List<List<Integer>> doInBackground() {
                return adapter.lancerChargement();
            }

            @Override
            protected void done() {
                List<List<Integer>> result;
                try {
                    result = get();
                    ui.showLoading(false);
                    ui.getJLabel2().setText(result.get(0).get(0).toString());
                    ui.refresh();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControllerDialogRecherche.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(ControllerDialogRecherche.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.execute();
    }
    
    public void properlyCloseWindow() {
        adapter.properlyCloseWindow();
        ui.dispose();
    }
    
    // à déplacer ECB (les 3 suivantes)
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
        
        return adapter.recherchePersonnage(p.getName()); // temp
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
    
    public List<Saison> getSaisons() {
        return adapter.getSaisons();
    }
    
    public List<Episode> getEpisodesSaison(int saison) {
        return adapter.getEpisodesSaison(saison);
    }
    
    public int getNombreRepliquesEpisode(int saison, int episode) {
        return adapter.getNombreRepliquesEpisode(saison, episode);
    }
    
    public List<String> getPersonnagesEpisode(int saison, int episode) {
        return adapter.getPersonnagesEpisode(saison, episode);
    }
    
    public List<String> getTopMotsEpisode(int saison, int episode) {
        return adapter.getTopMotsEpisode(saison, episode);
    }
    
    public int getNombreRepliquesSaison(int saison) {
        return adapter.getNombreRepliquesSaison(saison);
    }
    
    public List<String> getPersonnagesSaison(int saison) {
        return adapter.getPersonnagesSaison(saison);
    }
    
    public List<String> getTopMotsSaison(int saison) {
        return adapter.getTopMotsSaison(saison);
    }
    
    public void creerGrapheNombreDeRepliquesSaison(int saison) {
        adapter.creerGrapheNombreDeRepliquesSaison(saison);
    }
    
    public void creerGrapheNombreDeRepliquesEpisode(int saison, int episode) {
        adapter.creerGrapheNombreDeRepliquesEpisode(saison, episode);
    }
}