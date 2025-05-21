package controller;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import model.Episode;
import model.Personnage;
import model.Saison;
import vue.VueStat;
import model.CsvUtils;

/**
 *
 * @author ember
 */

public class ControllerDialogRecherche {
    private final AdapterFunctional adapter;
    private VueStat ui;
    private CsvUtils csvUtils = CsvUtils.getInstance();


    
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
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        UIManager.put("TabbedPane.selected", new Color(245, 245, 245));
        UIManager.put("TabbedPane.contentOpaque", false);
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 20, 10, 20));
        UIManager.put("TabbedPane.selectedBackground", new Color(240, 240, 250));
        UIManager.put("TabbedPane.selectedForeground", new Color(33, 33, 33));
        SwingUtilities.invokeLater(() -> {
            ui = new VueStat(this);
            ui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            ui.setDialog(this);
            ui.setVisible(true);
        });
    }
    
    
    public void onUserAction(String mots) {
        if (!mots.trim().isEmpty()) {
            ui.showLoading(true);

            new SwingWorker<Map<String, Object>, Void>() {
                @Override
                protected Map<String, Object> doInBackground() {
                    return adapter.rechercheMots(mots);
                }

                @Override
                protected void done() {
                    Map<String, Object> result;
                    try {
                        result = get();
                        ui.showLoading(false);
                        List<List<String>> row = (List<List<String>>) result.get("lines_with_words");
                        DefaultTableModel modelTableau = (DefaultTableModel) ui.getTableDetailReplique().getModel();
                        modelTableau.setRowCount(0);
                        for (List<String> rowData : row) {
                            modelTableau.addRow(rowData.toArray());
                        }
                        
                        List<List<Object>> wordCountByCharacter = (List<List<Object>>) result.get("word_count_by_character");
                        ui.generateGraphBarMotParPersonnage(wordCountByCharacter);
                        Integer nbUtilisations = ((Double) result.get("nb_word_used")).intValue();
                        
                        ui.getLabelMotCourant1().setText("\"" + mots + "\" (" + nbUtilisations + " utilisations)");
                        ui.getLabelMotCourant2().setText("\"" + mots + "\" (" + nbUtilisations + " utilisations)");
                        ui.getLabelMotCourant3().setText("\"" + mots + "\" (" + nbUtilisations + " utilisations)");
                        
                        Integer nbLinesContainsWords = ((Double) result.get("number_lines_with_words")).intValue();
                        Double percentLinesContainsWords = (Double) result.get("words_usage_ratio_by_line");
                        
                        ui.getLabelUtilisationMotParReplique().setText(nbLinesContainsWords + " répliques contienne ce mot (" + String.format("%.2f", percentLinesContainsWords*100) + "% des répliques)");
                        
                        String saison = (String) result.get("best_season_word_count");
                        ui.getBestSeason().setText("Saison " + saison);
                        
                        List<List<Object>> wordCountBySeason = (List<List<Object>>) result.get("word_count_by_season");
                        ui.generateGraphLineMotParSaison(wordCountBySeason);
                        
                        Map bestEpisode = (Map) result.get("best_episode_word_count");
                        String season = (String) bestEpisode.get("season");
                        String episode = (String) bestEpisode.get("episode");
                        ui.getBestEpisode().setText("Épisode " + episode + " de la saison " + season);
                        
                        List<List<Object>> wordCountByEpisode = (List<List<Object>>) result.get("word_count_by_episode");
                        ui.generateGraphLineMotParEpisode(wordCountByEpisode, "S01");
                        
                        /*System.out.println("Nb utilisations du mot : " + data.get("nb_word_used"));
                        System.out.println("Nombre de lignes contenant le mot : " + data.get("number_lines_with_words"));
                        System.out.println("Ratio d’usage par réplique : " + data.get("words_usage_ratio_by_line"));

                        List<List<String>> lignes = (List<List<String>>) data.get("lines_with_words");
                        System.out.println("Extrait de répliques :");
                        for (int i = 0; i < Math.min(3, lignes.size()); i++) {
                            System.out.println("  - " + lignes.get(i));
                        }

                        List<List<Object>> parPerso = (List<List<Object>>) data.get("word_count_by_character");
                        System.out.println("Stats par personnage :");
                        for (int i = 0; i < Math.min(3, parPerso.size()); i++) {
                            System.out.println("  - " + parPerso.get(i));
                        }

                        List<List<Object>> wordCountByEpisode = (List<List<Object>>) data.get("word_count_by_episode");
                        System.out.println("word_count_by_episode");
                        for (int i = 0; i < Math.min(3, parPerso.size()); i++) {
                            System.out.println("  - " + wordCountByEpisode.get(i));
                        }

                        List<List<Object>> wordCountBySeason = (List<List<Object>>) data.get("word_count_by_season");
                        System.out.println("word_count_by_season");
                        for (int i = 0; i < Math.min(3, parPerso.size()); i++) {
                            System.out.println("  - " + wordCountBySeason.get(i));
                        }

                        String bestSeason = (String) data.get("best_season_word_count");
                        System.out.println(bestSeason);

                        Map<String, Integer> bestEpisode = (Map<String, Integer>) data.get("best_episode_word_count");
                        System.out.println("Best season-ep" + bestEpisode.get("season") + bestEpisode.get("episode"));*/
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControllerDialogRecherche.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        Logger.getLogger(ControllerDialogRecherche.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.execute();
        }
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
        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);
        return csvUtils.getEpisodeStats(saisonStr, episodeStr);
    }

    
    public int getNombreRepliquesSaison(int saison) {
        return adapter.getNombreRepliquesSaison(saison);
    }
    
    public List<String> getPersonnagesSaison(int saison) {
        return adapter.getPersonnagesSaison(saison);
    }
    
    public List<String> getTopMotsSaison(int saison) {
        String saisonStr = String.format("S%02d", saison);
        return csvUtils.getSaisonStats(saisonStr);

    }
    
    public void creerGrapheNombreDeRepliquesSaison(int saison) {
        adapter.creerGrapheNombreDeRepliquesSaison(saison);
    }
    
    public void creerGrapheNombreDeRepliquesEpisode(int saison, int episode) {
        adapter.creerGrapheNombreDeRepliquesEpisode(saison, episode);
    }
}