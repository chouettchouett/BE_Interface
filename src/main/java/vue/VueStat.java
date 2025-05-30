package vue;

import java.awt.event.KeyEvent;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import controller.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class VueStat extends javax.swing.JFrame {
    private ControllerDialogRecherche dialog;
    private JWindow infoWindow = null;

    List<List<Object>> dataStoredForEpisodeStats;
    
    public void setDialog(ControllerDialogRecherche dialog) {
        this.dialog = dialog;
        this.dialog.onUserAction("Example");
    }

    public VueStat(ControllerDialogRecherche dialog) {
        this.dialog = dialog;
        initComponents();
        
        initialiserAnalyseSentiment();
        initialiserRelations();
        initialiserAnalyseLangagiere();
        initialiserRechercheUI();
        initialiserComportementRecherche();
        initialiserComboBoxRecherche();
        initialiserStyleRechercheMot();
    }
    
    private void initialiserAnalyseLangagiere() {
        AnalyseLangagière.setImage("/les_png/Analyselangagière.png");
    }

    private void initialiserRechercheUI() {
        ficheIdentite.setImage("/les_png/recherche_personnage/VIGNETTE.png");
        imagePersonnage.setImage("/les_png/Joey_Test.png");
        imageMentions1.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_monica.png");
        imageMotCaracteristique.setImage("/les_png/mots_caracteristiques_par_personnages/mots_caracteristique_monica.png");
        imageMotPref.setImage("/les_png/mots_preferes_par_personnage/mots_preferes_monica.png");
        miseEnAvantPersonnageImage.setImage("/les_png/recherche_personnage/graphe_tendance_mise_en_avant_monica_par_scenariste.png");
        
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane4.getVerticalScrollBar().setUnitIncrement(16);
        
        recherchePersonnage.setVisible(false);
        rechercheSaison.setVisible(false);
        rechercheEpisode.setVisible(false);

        rechercheMot.setColumns(10);
        rechercheMot.setBorder(new javax.swing.border.EmptyBorder(4, 4, 4, 4));
        rechercheMot.setPlaceholder("Recherche de mots");

        ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "SANSRECHERCHE");

        tableDetailReplique.getColumnModel().getColumn(0).setPreferredWidth(300);
        rechercheMotProgBar.setVisible(false);       
    }

    private void initialiserComportementRecherche() {
        choixTypeRecherche.setModel(dialog.getComboBoxModelTypeRecherche());
        choixTypeRecherche.addActionListener(evt -> {
            String result = (String) ((javax.swing.JComboBox<?>) evt.getSource()).getSelectedItem();

            // Cacher tous les composants par défaut
            recherchePersonnage.setVisible(false);
            rechercheSaison.setVisible(false);
            rechercheEpisode.setVisible(false);
            rechercheMot.setVisible(false);
            rechercheMotButton.setVisible(false);

            String cardName = switch (result) {
                case "Recherche de mots" -> {
                    rechercheMot.setVisible(true);
                    rechercheMotButton.setVisible(true);
                    yield "MOT";
                }
                case "Recherche de personnages" -> {
                    recherchePersonnage.setVisible(true);
                    afficherInfosPersonnage((String) recherchePersonnage.getSelectedItem());
                    yield "PERSONNAGE";
                }
                case "Recherche de saisons" -> {
                    rechercheSaison.setVisible(true);
                    yield "SAISON";
                }
                case "Recherche d'épisodes" -> {
                    rechercheSaison.setVisible(true);
                    rechercheEpisode.setVisible(true);
                    yield "EPISODE";
                }
                default -> "SANSRECHERCHE";
            };

            ((java.awt.CardLayout) resultats.getLayout()).show(resultats, cardName);
        });

        recherchePersonnage.addActionListener(evt -> {
            String personnage = (String) recherchePersonnage.getSelectedItem();
            afficherInfosPersonnage(personnage);
        });
    }

    private void initialiserComboBoxRecherche() {
        recherchePersonnage.setModel(dialog.getComboBoxModelPersonnage());

        rechercheSaison.setModel(new DefaultComboBoxModel<>(
            dialog.getSaisons().stream()
                .map(s -> String.format("S%02d", s.getNumeroSaison()))
                .toArray(String[]::new)
        ));

        rechercheEpisode.setModel(new DefaultComboBoxModel<>(
            dialog.getEpisodesSaison(1).stream() // Saison 1 par défaut
                .map(e -> String.format("S%02dE%02d - %s",
                        e.getNumeroSaison(),
                        e.getNumeroEpisode(),
                        e.getTitre()))
                .filter(ep -> ep.startsWith("S01"))
                .toArray(String[]::new)
        ));
        
        comboBoxSaison.setModel(new DefaultComboBoxModel<>(
            dialog.getSaisons().stream()
                .map(s -> String.format("S%02d", s.getNumeroSaison()))
                .toArray(String[]::new)
        ));
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableDetailReplique.getModel());
        tableDetailReplique.setRowSorter(sorter);
        
        // Filtres
        java.awt.event.ActionListener filtreAction = e -> {
            String personnage = (String) filtrePerso.getSelectedItem();
            String saison = (String) filtreSaison.getSelectedItem();
            String episode = (String) filtreEpisode.getSelectedItem();

            javax.swing.RowFilter<TableModel, Integer> filter = new javax.swing.RowFilter<TableModel, Integer>() {
                public boolean include(javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                    if (personnage != null && saison != null && episode != null) {
                        String personnageValue = entry.getStringValue(1);
                        String saisonValue = entry.getStringValue(2);
                        String episodeValue = entry.getStringValue(3);
                        
                        boolean matchPerso = personnage.equals("Tous les personnages") || personnage.equals(personnageValue);
                        boolean matchSaison = saison.equals("Toutes les saisons") || saison.equals(saisonValue);
                        boolean matchEpisode = episode.equals("Tous les épisodes") || episode.equals(episodeValue);
                        return matchPerso && matchSaison && matchEpisode;
                    }
                    return true;
                }
            };
            sorter.setRowFilter(filter);
            
            nombreResultatFiltre.setText("Résultats : " + tableDetailReplique.getRowCount() + "/" + tableDetailReplique.getModel().getRowCount());
        };

        // Ajouter l’action aux combo box
        filtrePerso.addActionListener(filtreAction);
        filtreSaison.addActionListener(filtreAction);
        filtreEpisode.addActionListener(filtreAction);
    }
    
    public JComboBox getComboMotPerso() {
        return filtrePerso;
    }
    
    public JComboBox getComboMotSaison() {
        return filtreSaison;
    }
    
    public JComboBox getComboMotEpisode() {
        return filtreEpisode;
    }
    
    public JComboBox getComboMotEmotion() {
        return filtreSentiment;
    }

    private void initialiserStyleRechercheMot() {
        rechercheMot.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        rechercheMot.setForeground(Color.BLACK);
    }
   
    private void initialiserAnalyseSentiment() {
    // Tab 1 : Évolution positivité/négativité
        Evol_pos_image.setImage("/les_png/Evolution_positivité.png");
        TexteEvolpos.setCaretPosition(0);
        Evol_neg_image.setImage("/les_png/Evolution_negativité.png");
        TexteEvolneg.setCaretPosition(0);

    // Tab 2 : Sentiment exprimé
        Sentiment_exprime.getVerticalScrollBar().setUnitIncrement(16);
        Sentiment_exprimé_chandler.setImage("/les_png/Sentiment_exprimé_Chandler_V2.png");
        Sentiment_exprimé_ross.setImage("/les_png/Sentiment_exprimé_Ross_V2.png");
        Sentiment_exprimé_joey.setImage("/les_png/Sentiment_exprimé_Joey_V2.png");
        Sentiment_exprimé_monica.setImage("/les_png/Sentiment_exprimé_Monica_V2.png");
        Sentiment_exprimé_rachel.setImage("/les_png/Sentiment_exprimé_Rachel_V2.png");
        Sentiment_exprimé_phoebe.setImage("/les_png/Sentiment_exprimé_Phoebe_V2.png");
        TexteSentimentexprime.setCaretPosition(0);

    // Tab 3 : Évolution des sentiments
        Evolution_Sentiment.getVerticalScrollBar().setUnitIncrement(16);
        Evol_Sentiment_chandler.setImage("/les_png/Evolution_sentiment_Chandler.png");
        TexteChandler.setCaretPosition(0);
        Evol_Sentiment_ross.setImage("/les_png/Evolution_sentiment_Ross.png");
        TexteRoss.setCaretPosition(0);
        Evol_Sentiment_joey.setImage("/les_png/Evolution_sentiment_Joey.png");
        TexteJoey.setCaretPosition(0);
        Evol_Sentiment_monica.setImage("/les_png/Evolution_sentiment_Monica.png");
        TexteMonica.setCaretPosition(0);
        Evol_Sentiment_rachel.setImage("/les_png/Evolution_sentiment_Rachel.png");
        TexteRachel.setCaretPosition(0);
        Evol_Sentiment_phoebe.setImage("/les_png/Evolution_sentiment_Phoebe.png");
        TextePhoebe.setCaretPosition(0);
        TexteInterpretationGlobale.setCaretPosition(0);

    // Tab 4 : Nuages & Sources
        sentiment_par_personnage_panel.setImage("/les_png/sentiment_par_personnage.png");
        neg_nuage.setImage("/les_png/negative_wordcloud.png");
        pos_nuage.setImage("/les_png/positive_wordcloud.png");
    }
    
    private void initialiserRelations() {
    // Tab 1 : Carte des relations
        Carte_Relation.setImage("/les_png/Relation_Cartes.png");

    // Tab 2 : Relations par personnage
        Graphique_Relation.getVerticalScrollBar().setUnitIncrement(16);
        TexteGraphiqueRel.setCaretPosition(0);
        Relation_Chandler.setImage("/les_png/Relation_Chandler.png");
        Relation_Monica.setImage("/les_png/Relation_Monica.png");
        Relation_Ross.setImage("/les_png/Relation_Ross.png");
        Relation_Rachel.setImage("/les_png/Relation_Rachel.png");
        Relation_Joey.setImage("/les_png/Relation_Joey.png");
        Relation_Phoebe.setImage("/les_png/Relation_Phoebe.png");
        
    // Tab 3 : Mentions
        Relation_Mention.setImage("/les_png/Relation_Mention.png");
    }

    
    
    public void showLoading(boolean loading) {
        rechercheMotProgBar.setIndeterminate(loading);
        rechercheMotProgBar.setVisible(loading);
        rechercheMotButton.setEnabled(!loading);
    }
    
    public javax.swing.JLabel getJLabel2() {
        return labelMotCourant1;
    }
    
    public javax.swing.JTable getTableDetailReplique() {
        return tableDetailReplique;
    }
    
    public javax.swing.JLabel getLabelMotCourant1() {
        return labelMotCourant1;
    }
    
    public javax.swing.JLabel getLabelMotCourant2() {
        return labelMotCourant2;
    }
    
    public javax.swing.JLabel getLabelMotCourant3() {
        return labelMotCourant3;
    }
    
    public javax.swing.JLabel getLabelUtilisationMotParReplique() {
        return labelUtilisationMotParReplique;
    }    
    
    public javax.swing.JLabel getBestEpisode() {
        return bestEpisode;
    }
    
    public javax.swing.JLabel getBestSeason() {
        return bestSeason;
    }
    
    public void generateGraphLineMotParEpisode(List<List<Object>> data, String season) {
        dataStoredForEpisodeStats = data;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<Object> row : data) {
            String saison = (String) row.get(0);
            String episode = (String) row.get(1);
            Integer value = ((Double) row.get(2)).intValue();
            if (saison.equals(season))
                dataset.addValue(value, "Tendance mot", episode);
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Tendance d'utilisation du mot par épisode",
                "Episode",
                "Utilisation du mot",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setOpaque(false);
        chartPanel.setBackground(new Color(0, 0, 0, 0));

        jPanel16.removeAll();
        jPanel16.add(chartPanel, java.awt.BorderLayout.CENTER);        
        jPanel16.revalidate();
        jPanel16.repaint();
    }
    
    public void generateGraphLineMotParSaison(List<List<Object>> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<Object> row : data) {
            String name = (String) row.get(0);
            Integer value = ((Double) row.get(1)).intValue();
            dataset.addValue(value, "Tendance mot", name);
        }

        JFreeChart chart2 = ChartFactory.createLineChart(
                "Tendance d'utilisation du mot dans les 6 saisons",
                "Saison",
                "Nombre d'utilisation",
                dataset
        );
        
        chart2.getTitle().setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 18));

        CategoryPlot plot = (CategoryPlot) chart2.getPlot();
        plot.getDomainAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14)); // x
        plot.getRangeAxis().setLabelFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14)); // y

        plot.getDomainAxis().setTickLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12));

        ChartPanel chartPanel2 = new ChartPanel(chart2);
        
        chartPanel2.setOpaque(false);
        chartPanel2.setBackground(new Color(0, 0, 0, 0));

        jPanel13.removeAll();
        jPanel13.add(chartPanel2);
        
        jPanel13.revalidate();
        jPanel13.repaint();
    }

    public void generateGraphBarMotParPersonnage(List<List<Object>> data) {
        DefaultCategoryDataset dataset = construireDataset(data);
        int total = calculerTotal(data);

        JFreeChart chart = creerBarChartEmpile(dataset);
        configurerPlot(chart.getCategoryPlot());
        configurerRenderer(chart.getCategoryPlot(), dataset, total);

        ChartPanel chartPanel = new ChartPanel(chart);
        configurerChartPanel(chartPanel);

        afficherDansPanelPourcentage(chartPanel);
    }

    private DefaultCategoryDataset construireDataset(List<List<Object>> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (List<Object> row : data) {
            String name = (String) row.get(0);
            int value = ((Double) row.get(1)).intValue();
            dataset.addValue(value, name, "");
        }
        return dataset;
    }

    private int calculerTotal(List<List<Object>> data) {
        return data.stream()
                   .mapToInt(row -> ((Double) row.get(1)).intValue())
                   .sum();
    }

    private JFreeChart creerBarChartEmpile(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createStackedBarChart(
            null, null, null, dataset,
            PlotOrientation.HORIZONTAL,
            false, false, false
        );
        chart.setBackgroundPaint(new Color(0, 0, 0, 0));
        chart.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0));
        return chart;
    }

    private void configurerPlot(CategoryPlot plot) {
        plot.setOutlineVisible(false);
        plot.getDomainAxis().setVisible(false);
        plot.getRangeAxis().setVisible(false);
        plot.setRangeGridlinesVisible(false);
    }

    private void configurerRenderer(CategoryPlot plot, CategoryDataset dataset, int total) {
        StackedBarRenderer renderer = new StackedBarRenderer();

        renderer.setDefaultToolTipGenerator((ds, row, col) -> {
            String name = dataset.getRowKey(row).toString();
            Number value = dataset.getValue(row, col);
            return String.format("%s : %d utilisations", name, value.intValue());
        });

        renderer.setDefaultItemLabelFont(new Font("Verdana", Font.PLAIN, 9));
                renderer.setDefaultItemLabelsVisible(true);
                renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator() {
                    @Override
                    public String generateLabel(org.jfree.data.category.CategoryDataset dataset, int row, int column) {
                        Number value = dataset.getValue(row, column);
                        String name = dataset.getRowKey(row).toString();
                        int val = value.intValue();
                        double percent = total == 0 ? 0 : (100.0 * val / total);
                        return String.format("%s\n(%d, %.1f%%)", name, val, percent);
                    }
                });

        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(
            ItemLabelAnchor.CENTER, TextAnchor.CENTER
        ));

        plot.setRenderer(renderer);
    }

    private void configurerChartPanel(ChartPanel panel) {
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setPreferredSize(new Dimension(300, 150));
    }

    private void afficherDansPanelPourcentage(ChartPanel panel) {
        panelPourcentageMotsPerso.removeAll();
        panelPourcentageMotsPerso.add(panel, BorderLayout.NORTH);
        panelPourcentageMotsPerso.revalidate();
        panelPourcentageMotsPerso.repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel13 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jRadioButton1 = new javax.swing.JRadioButton();
        labelTitre = new javax.swing.JLabel();
        SectionRecherche = new javax.swing.JTabbedPane();
        panelPageRecherche = new javax.swing.JPanel();
        panelRechercheEtIndication = new javax.swing.JPanel();
        panelRecherche = new javax.swing.JPanel();
        panelRechercheTitre = new javax.swing.JPanel();
        titre = new javax.swing.JLabel();
        panelRechercheButtons = new javax.swing.JPanel();
        choixTypeRecherche = new javax.swing.JComboBox<>();
        rechercheMot = new style.CustomJTextField();
        rechercheMotButton = new javax.swing.JButton();
        rechercheMotProgBar = new javax.swing.JProgressBar();
        recherchePersonnage = new javax.swing.JComboBox<>();
        rechercheSaison = new javax.swing.JComboBox<>();
        rechercheEpisode = new javax.swing.JComboBox<>();
        resultats = new javax.swing.JPanel();
        resultatSaison = new javax.swing.JTabbedPane();
        panelSaisonPresentation = new javax.swing.JPanel();
        panelTitre = new javax.swing.JPanel();
        labelSaison = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jPanel79 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jPanel82 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        panelSaisonRepartition = new javax.swing.JPanel();
        panelTitre2 = new javax.swing.JPanel();
        labelSaison2 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        panelEvolutionMots = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        labelSaison3 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        resultatEpisode = new javax.swing.JTabbedPane();
        panelEpisodePresentation = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        labelEpisode = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        panelEpisodeRepartition = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        labelEpisode2 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        panelEvolutionMot = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        labelEpisode3 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jPanel76 = new javax.swing.JPanel();
        jPanel77 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        resultSansRecherche = new javax.swing.JPanel();
        labelSansRechercheSentence = new javax.swing.JLabel();
        labelSansRechercheExemple = new javax.swing.JLabel();
        labelSansRechercheExemple1 = new javax.swing.JLabel();
        labelSansRechercheExemple2 = new javax.swing.JLabel();
        labelSansRechercheExemple3 = new javax.swing.JLabel();
        resultatMot = new javax.swing.JTabbedPane();
        panelMotUtilisation = new javax.swing.JPanel();
        panelMotCourant1 = new javax.swing.JPanel();
        labelMotCourant1 = new javax.swing.JLabel();
        panelResultatMotUtilisation = new javax.swing.JPanel();
        panelPersonnageReplique = new javax.swing.JPanel();
        labelPersonnageReplique = new javax.swing.JLabel();
        panelPourcentageMotsPerso = new javax.swing.JPanel();
        panelDetailReplique = new javax.swing.JPanel();
        scrollPaneDetailReplique = new javax.swing.JScrollPane();
        tableDetailReplique = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        labelDetailReplique = new javax.swing.JLabel();
        nombreResultatFiltre = new javax.swing.JLabel();
        filtrePerso = new javax.swing.JComboBox<>();
        filtreSaison = new javax.swing.JComboBox<>();
        filtreEpisode = new javax.swing.JComboBox<>();
        filtreSentiment = new javax.swing.JComboBox<>();
        panelMotRepartitionSerie = new javax.swing.JPanel();
        panelMotCourant2 = new javax.swing.JPanel();
        labelMotCourant2 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        labelUtilisationMotParReplique = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        bestSeason = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        panelMotRepartitionSaisonEtEpisode = new javax.swing.JPanel();
        panelMotCourant3 = new javax.swing.JPanel();
        labelMotCourant3 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        comboBoxSaison = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        bestEpisode = new javax.swing.JLabel();
        resultatPersonnage = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelPersonnageProfil = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        nomPersonnage = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        imagePersonnage = new controller.ImagePanel();
        jPanel17 = new javax.swing.JPanel();
        PanelinfoPerso = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        statsperso = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        LiensUtiles = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        nomPersonnage2 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel60 = new javax.swing.JPanel();
        miseEnAvantPersonnageImage = new controller.ImagePanel();
        miseEnAvantPersonnageScroll = new javax.swing.JScrollPane();
        miseEnAvantPersonnageTexte = new javax.swing.JTextArea();
        jPanel70 = new javax.swing.JPanel();
        imageMentions0 = new controller.ImagePanel();
        imageMentions1 = new controller.ImagePanel();
        centraliteScrollS2 = new javax.swing.JScrollPane();
        centraliteTexteS2 = new javax.swing.JTextArea();
        imageMentions2 = new controller.ImagePanel();
        centraliteScrollS3 = new javax.swing.JScrollPane();
        centraliteTexteS3 = new javax.swing.JTextArea();
        imageMentions3 = new controller.ImagePanel();
        imageMentions4 = new controller.ImagePanel();
        imageMentions5 = new controller.ImagePanel();
        imageMentions6 = new controller.ImagePanel();
        imageMentions7 = new controller.ImagePanel();
        imageMentions8 = new controller.ImagePanel();
        imageMentions9 = new controller.ImagePanel();
        centraliteScrollS1 = new javax.swing.JScrollPane();
        centraliteTexteS1 = new javax.swing.JTextArea();
        centraliteScrollS6 = new javax.swing.JScrollPane();
        centraliteTexteS6 = new javax.swing.JTextArea();
        centraliteScrollS4 = new javax.swing.JScrollPane();
        centraliteTexteS4 = new javax.swing.JTextArea();
        centraliteScrollS5 = new javax.swing.JScrollPane();
        centraliteTexteS5 = new javax.swing.JTextArea();
        centraliteScrollS8 = new javax.swing.JScrollPane();
        centraliteTexteS8 = new javax.swing.JTextArea();
        centraliteScrollS10 = new javax.swing.JScrollPane();
        centraliteTexteS10 = new javax.swing.JTextArea();
        centraliteScrollS9 = new javax.swing.JScrollPane();
        centraliteTexteS9 = new javax.swing.JTextArea();
        centraliteScrollS7 = new javax.swing.JScrollPane();
        centraliteTexteS7 = new javax.swing.JTextArea();
        miseEnAvantPersonnageScroll1 = new javax.swing.JScrollPane();
        miseEnAvantPersonnageTexte1 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelPersonnageRepliqueFavorite = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        nomPersonnage1 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        imageMotCaracteristique = new controller.ImagePanel();
        jLabel100 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        labelMotCar = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        imageMotPref = new controller.ImagePanel();
        jLabel101 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        labelMotCarJoie = new javax.swing.JTextArea();
        jPanel30 = new javax.swing.JPanel();
        imageMotCaracteristiqueSaisonX1 = new controller.ImagePanel();
        saisonMarquante1 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        labelMotCarX1 = new javax.swing.JTextArea();
        jPanel31 = new javax.swing.JPanel();
        imageMotCaracteristiqueSaisonX2 = new controller.ImagePanel();
        saisonMarquante2 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        labelMotCarX2 = new javax.swing.JTextArea();
        jPanel32 = new javax.swing.JPanel();
        imageMotCaracteristiqueSaisonX3 = new controller.ImagePanel();
        saisonMarquante3 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        labelMotCarX3 = new javax.swing.JTextArea();
        jPanel33 = new javax.swing.JPanel();
        imageMotCaracteristiqueSaisonX4 = new controller.ImagePanel();
        saisonMarquante4 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        labelMotCarX4 = new javax.swing.JTextArea();
        jPanel22 = new javax.swing.JPanel();
        ficheIdentite = new controller.ImagePanel();
        jLabel20 = new javax.swing.JLabel();
        Analyse_Statistique = new javax.swing.JPanel();
        AnalyseLangagière = new controller.ImagePanel();
        Analyse_Sentiment = new javax.swing.JTabbedPane();
        Evolution_positivite = new javax.swing.JPanel();
        Evol_pos_image = new controller.ImagePanel();
        ScrollEvolpos = new javax.swing.JScrollPane();
        TexteEvolpos = new javax.swing.JTextArea();
        Evolution_negativité = new javax.swing.JPanel();
        Evol_neg_image = new controller.ImagePanel();
        ScrollEvolneg = new javax.swing.JScrollPane();
        TexteEvolneg = new javax.swing.JTextArea();
        Sentiment_exprime = new javax.swing.JScrollPane();
        Sentiment_exprimé_panel = new javax.swing.JPanel();
        Sentiment_exprimé_ross = new controller.ImagePanel();
        Sentiment_exprimé_chandler = new controller.ImagePanel();
        Sentiment_exprimé_rachel = new controller.ImagePanel();
        Sentiment_exprimé_monica = new controller.ImagePanel();
        Sentiment_exprimé_phoebe = new controller.ImagePanel();
        Sentiment_exprimé_joey = new controller.ImagePanel();
        ScrollSentimentexprime = new javax.swing.JScrollPane();
        TexteSentimentexprime = new javax.swing.JTextArea();
        Evolution_Sentiment = new javax.swing.JScrollPane();
        Evolution_Sentiment_Panel = new javax.swing.JPanel();
        Evol_Sentiment_monica = new controller.ImagePanel();
        ScrollMonicatext = new javax.swing.JScrollPane();
        TexteMonica = new javax.swing.JTextArea();
        Evol_Sentiment_ross = new controller.ImagePanel();
        ScrollRosstext = new javax.swing.JScrollPane();
        TexteRoss = new javax.swing.JTextArea();
        Evol_Sentiment_rachel = new controller.ImagePanel();
        ScrollRacheltext = new javax.swing.JScrollPane();
        TexteRachel = new javax.swing.JTextArea();
        Evol_Sentiment_phoebe = new controller.ImagePanel();
        ScrollPhoebetext = new javax.swing.JScrollPane();
        TextePhoebe = new javax.swing.JTextArea();
        Evol_Sentiment_joey = new controller.ImagePanel();
        ScrollJoeytext = new javax.swing.JScrollPane();
        TexteJoey = new javax.swing.JTextArea();
        Evol_Sentiment_chandler = new controller.ImagePanel();
        ScrollChandlertext = new javax.swing.JScrollPane();
        TexteChandler = new javax.swing.JTextArea();
        ScrollInterpretationtext = new javax.swing.JScrollPane();
        TexteInterpretationGlobale = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        Source = new javax.swing.JPanel();
        sentiment_par_personnage_panel = new controller.ImagePanel();
        neg_nuage = new controller.ImagePanel();
        jLabel15 = new javax.swing.JLabel();
        pos_nuage = new controller.ImagePanel();
        Relation = new javax.swing.JTabbedPane();
        Carte_Relation_Panel = new javax.swing.JPanel();
        Carte_Relation = new controller.ImagePanel();
        jLabel1 = new javax.swing.JLabel();
        Graphique_Relation = new javax.swing.JScrollPane();
        Graphique_Relation_panel = new javax.swing.JPanel();
        Relation_Ross = new controller.ImagePanel();
        Relation_Chandler = new controller.ImagePanel();
        Relation_Rachel = new controller.ImagePanel();
        Relation_Monica = new controller.ImagePanel();
        Relation_Phoebe = new controller.ImagePanel();
        Relation_Joey = new controller.ImagePanel();
        ScrollGraphiqueRel = new javax.swing.JScrollPane();
        TexteGraphiqueRel = new javax.swing.JTextArea();
        RelRac = new javax.swing.JLabel();
        RelPho = new javax.swing.JLabel();
        RelMon = new javax.swing.JLabel();
        RelJoe = new javax.swing.JLabel();
        RelRos = new javax.swing.JLabel();
        RelCha = new javax.swing.JLabel();
        SourceRelation = new javax.swing.JPanel();
        Relation_Mention = new controller.ImagePanel();
        jLabel16 = new javax.swing.JLabel();

        jLabel13.setText("jLabel13");

        jRadioButton1.setText("jRadioButton1");

        setTitle("Analyse des scripts de la série Friends");
        setMinimumSize(new java.awt.Dimension(845, 630));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        labelTitre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTitre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/les_png/friends.png"))); // NOI18N
        labelTitre.setText("Analyse des scripts de la série");
        getContentPane().add(labelTitre);

        SectionRecherche.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SectionRecherche.setMinimumSize(new java.awt.Dimension(837, 580));
        SectionRecherche.setName(""); // NOI18N

        panelPageRecherche.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPageRecherche.setLayout(new java.awt.BorderLayout());

        panelRechercheEtIndication.setLayout(new java.awt.BorderLayout());

        panelRecherche.setLayout(new javax.swing.BoxLayout(panelRecherche, javax.swing.BoxLayout.Y_AXIS));

        titre.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        titre.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        titre.setText("RECHERCHE");
        titre.setToolTipText("");
        titre.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRechercheTitre.add(titre);

        panelRecherche.add(panelRechercheTitre);

        choixTypeRecherche.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        choixTypeRecherche.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        choixTypeRecherche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelRechercheButtons.add(choixTypeRecherche);

        rechercheMot.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        rechercheMot.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        rechercheMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheMotActionPerformed(evt);
            }
        });
        rechercheMot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rechercheMotKeyPressed(evt);
            }
        });
        panelRechercheButtons.add(rechercheMot);

        rechercheMotButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        rechercheMotButton.setText("Rechercher");
        rechercheMotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheMotButtonActionPerformed(evt);
            }
        });
        panelRechercheButtons.add(rechercheMotButton);

        rechercheMotProgBar.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        panelRechercheButtons.add(rechercheMotProgBar);

        recherchePersonnage.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        recherchePersonnage.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        recherchePersonnage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recherchePersonnage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                recherchePersonnageItemStateChanged(evt);
            }
        });
        panelRechercheButtons.add(recherchePersonnage);

        rechercheSaison.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        rechercheSaison.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        rechercheSaison.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rechercheSaisonItemStateChanged(evt);
            }
        });
        rechercheSaison.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rechercheSaisonMouseClicked(evt);
            }
        });
        rechercheSaison.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheSaisonActionPerformed(evt);
            }
        });
        panelRechercheButtons.add(rechercheSaison);

        rechercheEpisode.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        rechercheEpisode.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        rechercheEpisode.setMaximumRowCount(25);
        rechercheEpisode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rechercheEpisodeItemStateChanged(evt);
            }
        });
        rechercheEpisode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheEpisodeActionPerformed(evt);
            }
        });
        panelRechercheButtons.add(rechercheEpisode);

        panelRecherche.add(panelRechercheButtons);

        panelRechercheEtIndication.add(panelRecherche, java.awt.BorderLayout.NORTH);

        resultats.setLayout(new java.awt.CardLayout());

        resultatSaison.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultatSaisonMouseClicked(evt);
            }
        });

        panelSaisonPresentation.setLayout(new java.awt.BorderLayout());

        labelSaison.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelSaison.setText("Saison 1");
        labelSaison.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelTitre.add(labelSaison);

        panelSaisonPresentation.add(panelTitre, java.awt.BorderLayout.NORTH);

        jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jPanel78.setLayout(new javax.swing.BoxLayout(jPanel78, javax.swing.BoxLayout.Y_AXIS));

        jPanel79.setLayout(new javax.swing.BoxLayout(jPanel79, javax.swing.BoxLayout.Y_AXIS));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText("Nombre de répliques:");
        jPanel79.add(jLabel22);

        jLabel106.setText("4768");
        jLabel106.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel79.add(jLabel106);

        jPanel78.add(jPanel79);

        jPanel80.setLayout(new javax.swing.BoxLayout(jPanel80, javax.swing.BoxLayout.Y_AXIS));

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(153, 153, 153));
        jLabel107.setText("Personnages impliqués :");
        jPanel80.add(jLabel107);

        jLabel108.setText("Monica, Joey, Chandler, Phoebe, Ross, Rachel");
        jLabel108.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel80.add(jLabel108);

        jPanel78.add(jPanel80);

        jPanel82.setLayout(new javax.swing.BoxLayout(jPanel82, javax.swing.BoxLayout.Y_AXIS));

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(153, 153, 153));
        jLabel111.setText("Top 10 des mots les plus répétés:");
        jPanel82.add(jLabel111);

        jLabel112.setText("know, like, well, out, uh, hey, right, think, here, thats");
        jLabel112.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel82.add(jLabel112);

        jPanel78.add(jPanel82);

        jPanel83.setLayout(new javax.swing.BoxLayout(jPanel83, javax.swing.BoxLayout.Y_AXIS));

        jLabel113.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(153, 153, 153));
        jLabel113.setText("Nombre moyen de mots par réplique:");
        jLabel113.setToolTipText("");
        jPanel83.add(jLabel113);

        jLabel114.setText("11");
        jLabel114.setToolTipText("");
        jLabel114.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel83.add(jLabel114);

        jPanel78.add(jPanel83);

        jPanel14.add(jPanel78);

        panelSaisonPresentation.add(jPanel14, java.awt.BorderLayout.CENTER);

        resultatSaison.addTab("Présentation", panelSaisonPresentation);

        panelSaisonRepartition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelSaisonRepartitionMouseClicked(evt);
            }
        });
        panelSaisonRepartition.setLayout(new java.awt.BorderLayout());

        labelSaison2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelSaison2.setText("Saison 1");
        labelSaison2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelTitre2.add(labelSaison2);

        panelSaisonRepartition.add(panelTitre2, java.awt.BorderLayout.NORTH);

        jPanel85.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel85MouseClicked(evt);
            }
        });
        jPanel85.setLayout(new javax.swing.BoxLayout(jPanel85, javax.swing.BoxLayout.LINE_AXIS));

        jPanel86.setLayout(new javax.swing.BoxLayout(jPanel86, javax.swing.BoxLayout.Y_AXIS));

        jPanel87.setLayout(new javax.swing.BoxLayout(jPanel87, javax.swing.BoxLayout.Y_AXIS));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText("Graphe de répartition de personnage dans l'épisode:");
        jPanel87.add(jLabel25);

        jLabel117.setText("dd");
        jLabel117.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel87.add(jLabel117);

        jPanel86.add(jPanel87);

        jPanel85.add(jPanel86);

        panelSaisonRepartition.add(jPanel85, java.awt.BorderLayout.CENTER);

        resultatSaison.addTab("Répartition de répliques", panelSaisonRepartition);

        panelEvolutionMots.setLayout(new java.awt.BorderLayout());

        labelSaison3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelSaison3.setText("Saison 1");
        labelSaison3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel89.add(labelSaison3);

        panelEvolutionMots.add(jPanel89, java.awt.BorderLayout.NORTH);

        jPanel90.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel90.setLayout(new javax.swing.BoxLayout(jPanel90, javax.swing.BoxLayout.LINE_AXIS));

        jPanel91.setLayout(new javax.swing.BoxLayout(jPanel91, javax.swing.BoxLayout.Y_AXIS));

        jPanel92.setLayout(new javax.swing.BoxLayout(jPanel92, javax.swing.BoxLayout.Y_AXIS));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(153, 153, 153));
        jLabel29.setText("Dialogue entre personnages:");
        jPanel92.add(jLabel29);

        jLabel118.setText("<graphe>");
        jLabel118.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel92.add(jLabel118);

        jPanel91.add(jPanel92);

        jPanel90.add(jPanel91);

        panelEvolutionMots.add(jPanel90, java.awt.BorderLayout.CENTER);

        resultatSaison.addTab("Evolution du nombre de mots par réplique", panelEvolutionMots);

        resultats.add(resultatSaison, "SAISON");

        resultatEpisode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultatEpisodeMouseClicked(evt);
            }
        });

        panelEpisodePresentation.setLayout(new java.awt.BorderLayout());

        labelEpisode.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelEpisode.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        labelEpisode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(labelEpisode);

        panelEpisodePresentation.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.Y_AXIS));

        jPanel50.setLayout(new javax.swing.BoxLayout(jPanel50, javax.swing.BoxLayout.Y_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Nombre de répliques:");
        jPanel50.add(jLabel6);

        jLabel73.setText("265");
        jLabel73.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel50.add(jLabel73);

        jPanel49.add(jPanel50);

        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.Y_AXIS));

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(153, 153, 153));
        jLabel74.setText("Personnages impliqués :");
        jPanel51.add(jLabel74);

        jLabel75.setText("Monica, Joey, Chandler, Phoede, Ross, Rachel");
        jLabel75.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel51.add(jLabel75);

        jPanel49.add(jPanel51);

        jPanel62.setLayout(new javax.swing.BoxLayout(jPanel62, javax.swing.BoxLayout.Y_AXIS));

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(153, 153, 153));
        jLabel78.setText("Top 10 des mots les plus répétés:");
        jPanel62.add(jLabel78);

        jLabel79.setText("out, know, like, paul, right, well, if, guy, go, or");
        jLabel79.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel62.add(jLabel79);

        jPanel49.add(jPanel62);

        jPanel64.setLayout(new javax.swing.BoxLayout(jPanel64, javax.swing.BoxLayout.Y_AXIS));

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(153, 153, 153));
        jLabel80.setText("Nombre moyen de mots par réplique:");
        jPanel64.add(jLabel80);
        jLabel80.getAccessibleContext().setAccessibleName("nombre de mots moyen par réplique");

        jLabel81.setText("12");
        jLabel81.setToolTipText("");
        jLabel81.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel64.add(jLabel81);

        jPanel49.add(jPanel64);

        jPanel7.add(jPanel49);

        panelEpisodePresentation.add(jPanel7, java.awt.BorderLayout.CENTER);

        resultatEpisode.addTab("Présentation", panelEpisodePresentation);

        panelEpisodeRepartition.setLayout(new java.awt.BorderLayout());

        labelEpisode2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelEpisode2.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        labelEpisode2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(labelEpisode2);

        panelEpisodeRepartition.add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel65.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel65.setLayout(new javax.swing.BoxLayout(jPanel65, javax.swing.BoxLayout.LINE_AXIS));

        jPanel66.setLayout(new javax.swing.BoxLayout(jPanel66, javax.swing.BoxLayout.Y_AXIS));

        jPanel67.setLayout(new javax.swing.BoxLayout(jPanel67, javax.swing.BoxLayout.Y_AXIS));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Graphe de répartition de personnage dans l'épisode:");
        jPanel67.add(jLabel7);

        jLabel94.setText("dd");
        jLabel94.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel67.add(jLabel94);

        jPanel66.add(jPanel67);

        jPanel65.add(jPanel66);

        panelEpisodeRepartition.add(jPanel65, java.awt.BorderLayout.CENTER);

        resultatEpisode.addTab("Répartition de répliques", panelEpisodeRepartition);

        panelEvolutionMot.setLayout(new java.awt.BorderLayout());

        labelEpisode3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelEpisode3.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        labelEpisode3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel74.add(labelEpisode3);

        panelEvolutionMot.add(jPanel74, java.awt.BorderLayout.NORTH);

        jPanel75.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel75.setLayout(new javax.swing.BoxLayout(jPanel75, javax.swing.BoxLayout.LINE_AXIS));

        jPanel76.setLayout(new javax.swing.BoxLayout(jPanel76, javax.swing.BoxLayout.Y_AXIS));

        jPanel77.setLayout(new javax.swing.BoxLayout(jPanel77, javax.swing.BoxLayout.Y_AXIS));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("Dialogue entre personnages:");
        jPanel77.add(jLabel21);

        jLabel105.setText("<graphe>");
        jLabel105.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel77.add(jLabel105);

        jPanel76.add(jPanel77);

        jPanel75.add(jPanel76);

        panelEvolutionMot.add(jPanel75, java.awt.BorderLayout.CENTER);

        resultatEpisode.addTab("Evolution du nombre de mots par réplique", panelEvolutionMot);

        resultats.add(resultatEpisode, "EPISODE");

        resultSansRecherche.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        resultSansRecherche.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultSansRecherche.setLayout(new javax.swing.BoxLayout(resultSansRecherche, javax.swing.BoxLayout.Y_AXIS));

        labelSansRechercheSentence.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheSentence.setText("Retrouvez via la recherche ci-dessus des statistiques détaillées sur des personnages typiques de la série, sur des répliques, ....");
        resultSansRecherche.add(labelSansRechercheSentence);

        labelSansRechercheExemple.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheExemple.setText("Vous pouvez rechercher parmi tous les mots et ensemble de mots via la recherche par mots");
        resultSansRecherche.add(labelSansRechercheExemple);

        labelSansRechercheExemple1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheExemple1.setText("Vous pouvez rechercher parmi les 6 personnages principaux via la recherche par personnage");
        resultSansRecherche.add(labelSansRechercheExemple1);

        labelSansRechercheExemple2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheExemple2.setText("Vous pouvez rechercher parmi les 10 saisons de Friends via la recherche par saison");
        resultSansRecherche.add(labelSansRechercheExemple2);

        labelSansRechercheExemple3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheExemple3.setText("Vous pouvez rechercher parmi les 236 épisodes de Friends via la recherche par épisodes");
        resultSansRecherche.add(labelSansRechercheExemple3);

        resultats.add(resultSansRecherche, "SANSRECHERCHE");

        resultatMot.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        panelMotUtilisation.setLayout(new java.awt.BorderLayout());

        panelMotCourant1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N

        labelMotCourant1.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        labelMotCourant1.setText("\"Hello\"");
        labelMotCourant1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelMotCourant1.add(labelMotCourant1);

        panelMotUtilisation.add(panelMotCourant1, java.awt.BorderLayout.NORTH);

        panelResultatMotUtilisation.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 40, 20, 40));
        panelResultatMotUtilisation.setLayout(new java.awt.BorderLayout());

        panelPersonnageReplique.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPersonnageReplique.setLayout(new javax.swing.BoxLayout(panelPersonnageReplique, javax.swing.BoxLayout.Y_AXIS));

        labelPersonnageReplique.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        labelPersonnageReplique.setForeground(new java.awt.Color(153, 153, 153));
        labelPersonnageReplique.setText("Graphique en barre du nombre et du pourcentage d'utilisation utilisation du mot pour chaque personnages :");
        panelPersonnageReplique.add(labelPersonnageReplique);

        panelPourcentageMotsPerso.setLayout(new javax.swing.BoxLayout(panelPourcentageMotsPerso, javax.swing.BoxLayout.LINE_AXIS));
        panelPersonnageReplique.add(panelPourcentageMotsPerso);

        panelResultatMotUtilisation.add(panelPersonnageReplique, java.awt.BorderLayout.NORTH);

        panelDetailReplique.setLayout(new java.awt.BorderLayout());

        tableDetailReplique.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableDetailReplique.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"\"Hello there !\"", "Joey", null, null},
                {"\"Hello\"", "Ross", null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Réplique", "Personnage", "Saison", "Épisode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetailReplique.setRowHeight(25);
        tableDetailReplique.setSelectionBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        tableDetailReplique.setShowGrid(true);
        tableDetailReplique.setShowVerticalLines(false);
        scrollPaneDetailReplique.setViewportView(tableDetailReplique);

        panelDetailReplique.add(scrollPaneDetailReplique, java.awt.BorderLayout.CENTER);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        labelDetailReplique.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        labelDetailReplique.setForeground(new java.awt.Color(153, 153, 153));
        labelDetailReplique.setText("Détails des répliques :");
        jPanel12.add(labelDetailReplique);

        nombreResultatFiltre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        nombreResultatFiltre.setText("x/y résultats");
        jPanel12.add(nombreResultatFiltre);

        filtrePerso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les personnages", "Chandler" }));
        jPanel12.add(filtrePerso);

        filtreSaison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toutes les saisons" }));
        jPanel12.add(filtreSaison);

        filtreEpisode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tous les épisodes" }));
        jPanel12.add(filtreEpisode);

        filtreSentiment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Toutes les émotions" }));
        jPanel12.add(filtreSentiment);

        panelDetailReplique.add(jPanel12, java.awt.BorderLayout.NORTH);

        panelResultatMotUtilisation.add(panelDetailReplique, java.awt.BorderLayout.CENTER);

        panelMotUtilisation.add(panelResultatMotUtilisation, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Personnage & Réplique", panelMotUtilisation);

        panelMotRepartitionSerie.setLayout(new java.awt.BorderLayout());

        labelMotCourant2.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        labelMotCourant2.setText("\"Hello\"");
        labelMotCourant2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelMotCourant2.add(labelMotCourant2);

        panelMotRepartitionSerie.add(panelMotCourant2, java.awt.BorderLayout.NORTH);

        jPanel35.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 40, 20, 40));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jPanel36.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.Y_AXIS));

        jLabel60.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(153, 153, 153));
        jLabel60.setText("Taux d'utilisation par réplique :");
        jPanel38.add(jLabel60);

        labelUtilisationMotParReplique.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labelUtilisationMotParReplique.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel38.add(labelUtilisationMotParReplique);

        jPanel1.add(jPanel38);

        jPanel57.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.Y_AXIS));

        jLabel82.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(153, 153, 153));
        jLabel82.setText("Meilleur saison :");
        jPanel57.add(jLabel82);

        bestSeason.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        bestSeason.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel57.add(bestSeason);

        jPanel1.add(jPanel57);

        jPanel36.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel63.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(153, 153, 153));
        jLabel63.setText("Tendance d'utilisation à travers les saison :");
        jPanel39.add(jLabel63, java.awt.BorderLayout.NORTH);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));
        jPanel39.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel36);

        panelMotRepartitionSerie.add(jPanel35, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Analyse par saison", panelMotRepartitionSerie);

        panelMotRepartitionSaisonEtEpisode.setLayout(new java.awt.BorderLayout());

        labelMotCourant3.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        labelMotCourant3.setText("\"Hello\"");
        labelMotCourant3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelMotCourant3.add(labelMotCourant3);

        panelMotRepartitionSaisonEtEpisode.add(panelMotCourant3, java.awt.BorderLayout.NORTH);

        jPanel55.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 40, 20, 40));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));

        jPanel56.setLayout(new java.awt.BorderLayout());

        jPanel63.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel92.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(153, 153, 153));
        jLabel92.setText("Utilisation pour un certain saison :");
        jPanel9.add(jLabel92, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel93.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel93.setText("Sélectionnez la saison :");
        jLabel93.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel3.add(jLabel93);

        comboBoxSaison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxSaison.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSaisonActionPerformed(evt);
            }
        });
        jPanel3.add(comboBoxSaison);

        jPanel9.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel63.add(jPanel9, java.awt.BorderLayout.NORTH);

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));
        jPanel63.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel56.add(jPanel63, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel61.setLayout(new javax.swing.BoxLayout(jPanel61, javax.swing.BoxLayout.Y_AXIS));
        jPanel2.add(jPanel61);

        jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.Y_AXIS));

        jLabel84.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(153, 153, 153));
        jLabel84.setText("Meilleur épisode :");
        jPanel58.add(jLabel84);

        bestEpisode.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        bestEpisode.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel58.add(bestEpisode);

        jPanel2.add(jPanel58);

        jPanel56.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel55.add(jPanel56);

        panelMotRepartitionSaisonEtEpisode.add(jPanel55, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Analyse par épisode", panelMotRepartitionSaisonEtEpisode);

        resultats.add(resultatMot, "MOT");

        panelPersonnageProfil.setLayout(new java.awt.BorderLayout());

        nomPersonnage.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        nomPersonnage.setText("Joey Tribbiani");
        nomPersonnage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(nomPersonnage);

        panelPersonnageProfil.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        imagePersonnage.setPreferredSize(new java.awt.Dimension(309, 308));

        javax.swing.GroupLayout imagePersonnageLayout = new javax.swing.GroupLayout(imagePersonnage);
        imagePersonnage.setLayout(imagePersonnageLayout);
        imagePersonnageLayout.setHorizontalGroup(
            imagePersonnageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        imagePersonnageLayout.setVerticalGroup(
            imagePersonnageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2250, Short.MAX_VALUE)
        );

        jPanel5.add(imagePersonnage);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.GridLayout(2, 1));

        PanelinfoPerso.setBackground(new java.awt.Color(204, 204, 255));
        PanelinfoPerso.setLayout(new javax.swing.BoxLayout(PanelinfoPerso, javax.swing.BoxLayout.Y_AXIS));

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));
        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.Y_AXIS));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel4.setText("Infos");
        jLabel4.setAlignmentX(0.5F);
        jPanel21.add(jLabel4);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(153, 153, 153));
        jLabel38.setText("Date de naissance:");
        jLabel38.setAlignmentX(0.5F);
        jPanel21.add(jLabel38);

        jLabel42.setText("25 juillet 1987 (Etats-Unis)");
        jLabel42.setAlignmentX(0.5F);
        jLabel42.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel21.add(jLabel42);

        PanelinfoPerso.add(jPanel21);

        jPanel20.setBackground(new java.awt.Color(204, 204, 255));
        jPanel20.setForeground(new java.awt.Color(204, 204, 255));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(153, 153, 153));
        jLabel37.setText("Age:");
        jLabel37.setAlignmentX(0.5F);
        jPanel20.add(jLabel37);

        jLabel41.setBackground(new java.awt.Color(204, 204, 255));
        jLabel41.setText("57 ans");
        jLabel41.setAlignmentX(0.5F);
        jLabel41.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel20.add(jLabel41);

        PanelinfoPerso.add(jPanel20);

        jPanel19.setBackground(new java.awt.Color(204, 204, 255));
        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.Y_AXIS));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(153, 153, 153));
        jLabel35.setText("Nationalité:");
        jLabel35.setAlignmentX(0.5F);
        jPanel19.add(jLabel35);

        jLabel40.setText("Américain");
        jLabel40.setAlignmentX(0.5F);
        jLabel40.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel19.add(jLabel40);

        PanelinfoPerso.add(jPanel19);

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));
        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.Y_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Acteur:");
        jLabel5.setAlignmentX(0.5F);
        jPanel18.add(jLabel5);

        jLabel39.setText("Matt Leblanc");
        jLabel39.setAlignmentX(0.5F);
        jLabel39.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel18.add(jLabel39);

        PanelinfoPerso.add(jPanel18);

        jLabel12.setForeground(new java.awt.Color(204, 204, 255));
        jLabel12.setText("effemere");
        PanelinfoPerso.add(jLabel12);

        jPanel17.add(PanelinfoPerso);

        statsperso.setBackground(new java.awt.Color(204, 255, 255));
        statsperso.setLayout(new javax.swing.BoxLayout(statsperso, javax.swing.BoxLayout.Y_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setText("Stats");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        statsperso.add(jLabel3);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Microsoft PhagsPa", 1, 10)); // NOI18N
        jLabel14.setText("Passer la souris ici pour plus d'information sur ces stats");
        jLabel14.setAlignmentX(0.5F);
        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });
        statsperso.add(jLabel14);

        jPanel23.setBackground(new java.awt.Color(204, 255, 255));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.Y_AXIS));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Nombre total de replique :");
        jLabel8.setAlignmentX(0.5F);
        jPanel23.add(jLabel8);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Matt Leblanc");
        jLabel43.setAlignmentX(0.5F);
        jLabel43.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel23.add(jLabel43);

        statsperso.add(jPanel23);

        jPanel25.setBackground(new java.awt.Color(204, 255, 255));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.Y_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Pourcentage par rapport aux autres :  ");
        jLabel9.setAlignmentX(0.5F);
        jPanel25.add(jLabel9);

        jLabel44.setText("Matt Leblanc");
        jLabel44.setAlignmentX(0.5F);
        jLabel44.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel25.add(jLabel44);

        statsperso.add(jPanel25);

        jPanel26.setBackground(new java.awt.Color(204, 255, 255));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.Y_AXIS));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Position du personnage qui parle le plus : ");
        jLabel10.setAlignmentX(0.5F);
        jPanel26.add(jLabel10);

        jLabel45.setText("Matt Leblanc");
        jLabel45.setAlignmentX(0.5F);
        jLabel45.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel26.add(jLabel45);

        statsperso.add(jPanel26);

        jPanel27.setBackground(new java.awt.Color(204, 255, 255));
        jPanel27.setLayout(new javax.swing.BoxLayout(jPanel27, javax.swing.BoxLayout.Y_AXIS));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Top 5 mots : ");
        jLabel11.setAlignmentX(0.5F);
        jPanel27.add(jLabel11);

        jLabel46.setText("Matt Leblanc");
        jLabel46.setAlignmentX(0.5F);
        jLabel46.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel27.add(jLabel46);

        statsperso.add(jPanel27);
        statsperso.add(jSeparator1);

        LiensUtiles.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        LiensUtiles.setText("Liens utiles");
        LiensUtiles.setAlignmentX(0.5F);
        statsperso.add(LiensUtiles);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("<html><span style='color:#0066cc; text-decoration:underline;'>Son analyse languagière</span></html>\n");
        jLabel18.setAlignmentX(0.5F);
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        statsperso.add(jLabel18);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("<html><span style='color:#0066cc; text-decoration:underline;'>Ses relations avec les autres</span></html>\n");
        jLabel17.setAlignmentX(0.5F);
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        statsperso.add(jLabel17);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("<html><span style='color:#0066cc; text-decoration:underline;'>Evolution de ses sentiments</span></html>\n");
        jLabel19.setAlignmentX(0.5F);
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        statsperso.add(jLabel19);

        jPanel17.add(statsperso);

        jPanel5.add(jPanel17);

        panelPersonnageProfil.add(jPanel5, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(panelPersonnageProfil);

        resultatPersonnage.addTab("Profil", jScrollPane2);

        jPanel8.setLayout(new java.awt.BorderLayout());

        nomPersonnage2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage2.setText("Relation de Joey");
        nomPersonnage2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel47.add(nomPersonnage2);

        jPanel8.add(jPanel47, java.awt.BorderLayout.NORTH);

        jPanel52.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel52.setLayout(new javax.swing.BoxLayout(jPanel52, javax.swing.BoxLayout.LINE_AXIS));

        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Le scénariste choisit quand faire progresser l’intrigue des personnages. Leur évolution suit deux tendances souvent assez corrélées : le nombre de \nrépliques et celui de mentions par les autres. Un personnage très bavard mais peu mentionné se développe de lui-même (parle beaucoup) \nou avec des personnages secondaires, tandis qu’un personnage souvent mentionné, sans être présent, annonce souvent un événement majeur à venir.\nLes deux variables permettent d'annuler certains biais : Ross parle beaucoup mais n'est pas au coeur de l'histoire, Ross absent mais souvent \nmentionné. La saison 7 reflète ce phénomème : Rachel parle et aide Monica à développer son intrigue en saison 7, mais elle n'est pas principale.");
        jScrollPane3.setViewportView(jTextArea1);

        jPanel59.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 90));

        jPanel53.add(jPanel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 90));

        jPanel60.setMinimumSize(new java.awt.Dimension(740, 30));
        jPanel60.setPreferredSize(new java.awt.Dimension(0, 500));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        miseEnAvantPersonnageImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout miseEnAvantPersonnageImageLayout = new javax.swing.GroupLayout(miseEnAvantPersonnageImage);
        miseEnAvantPersonnageImage.setLayout(miseEnAvantPersonnageImageLayout);
        miseEnAvantPersonnageImageLayout.setHorizontalGroup(
            miseEnAvantPersonnageImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        miseEnAvantPersonnageImageLayout.setVerticalGroup(
            miseEnAvantPersonnageImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );

        jPanel60.add(miseEnAvantPersonnageImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 800, 370));

        miseEnAvantPersonnageTexte.setColumns(20);
        miseEnAvantPersonnageTexte.setRows(5);
        miseEnAvantPersonnageScroll.setViewportView(miseEnAvantPersonnageTexte);

        jPanel60.add(miseEnAvantPersonnageScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 800, 130));

        jPanel53.add(jPanel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 840, 540));

        jPanel70.setMinimumSize(new java.awt.Dimension(500, 1460));
        jPanel70.setPreferredSize(new java.awt.Dimension(0, 150));
        jPanel70.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMentions0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions0Layout = new javax.swing.GroupLayout(imageMentions0);
        imageMentions0.setLayout(imageMentions0Layout);
        imageMentions0Layout.setHorizontalGroup(
            imageMentions0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions0Layout.setVerticalGroup(
            imageMentions0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions0, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 265, 270));

        imageMentions1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions1Layout = new javax.swing.GroupLayout(imageMentions1);
        imageMentions1.setLayout(imageMentions1Layout);
        imageMentions1Layout.setHorizontalGroup(
            imageMentions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions1Layout.setVerticalGroup(
            imageMentions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions1, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 210, 265, 270));

        centraliteTexteS2.setColumns(20);
        centraliteTexteS2.setRows(5);
        centraliteScrollS2.setViewportView(centraliteTexteS2);

        jPanel70.add(centraliteScrollS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 480, 265, 120));

        imageMentions2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions2Layout = new javax.swing.GroupLayout(imageMentions2);
        imageMentions2.setLayout(imageMentions2Layout);
        imageMentions2Layout.setHorizontalGroup(
            imageMentions2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions2Layout.setVerticalGroup(
            imageMentions2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 265, 270));

        centraliteTexteS3.setColumns(20);
        centraliteTexteS3.setRows(5);
        centraliteScrollS3.setViewportView(centraliteTexteS3);

        jPanel70.add(centraliteScrollS3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 265, 120));

        imageMentions3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions3Layout = new javax.swing.GroupLayout(imageMentions3);
        imageMentions3.setLayout(imageMentions3Layout);
        imageMentions3Layout.setHorizontalGroup(
            imageMentions3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions3Layout.setVerticalGroup(
            imageMentions3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 265, 270));

        imageMentions4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions4Layout = new javax.swing.GroupLayout(imageMentions4);
        imageMentions4.setLayout(imageMentions4Layout);
        imageMentions4Layout.setHorizontalGroup(
            imageMentions4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions4Layout.setVerticalGroup(
            imageMentions4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions4, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 600, 265, 270));

        imageMentions5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions5Layout = new javax.swing.GroupLayout(imageMentions5);
        imageMentions5.setLayout(imageMentions5Layout);
        imageMentions5Layout.setHorizontalGroup(
            imageMentions5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions5Layout.setVerticalGroup(
            imageMentions5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 600, 265, 270));

        imageMentions6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions6Layout = new javax.swing.GroupLayout(imageMentions6);
        imageMentions6.setLayout(imageMentions6Layout);
        imageMentions6Layout.setHorizontalGroup(
            imageMentions6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions6Layout.setVerticalGroup(
            imageMentions6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 990, 265, 270));

        imageMentions7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions7Layout = new javax.swing.GroupLayout(imageMentions7);
        imageMentions7.setLayout(imageMentions7Layout);
        imageMentions7Layout.setHorizontalGroup(
            imageMentions7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions7Layout.setVerticalGroup(
            imageMentions7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions7, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 990, 265, 270));

        imageMentions8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions8Layout = new javax.swing.GroupLayout(imageMentions8);
        imageMentions8.setLayout(imageMentions8Layout);
        imageMentions8Layout.setHorizontalGroup(
            imageMentions8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions8Layout.setVerticalGroup(
            imageMentions8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 990, 265, 270));

        imageMentions9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions9Layout = new javax.swing.GroupLayout(imageMentions9);
        imageMentions9.setLayout(imageMentions9Layout);
        imageMentions9Layout.setHorizontalGroup(
            imageMentions9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions9Layout.setVerticalGroup(
            imageMentions9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1380, 265, 270));

        centraliteTexteS1.setColumns(20);
        centraliteTexteS1.setRows(5);
        centraliteScrollS1.setViewportView(centraliteTexteS1);

        jPanel70.add(centraliteScrollS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 265, 120));

        centraliteTexteS6.setColumns(20);
        centraliteTexteS6.setRows(5);
        centraliteScrollS6.setViewportView(centraliteTexteS6);

        jPanel70.add(centraliteScrollS6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 870, 265, 120));

        centraliteTexteS4.setColumns(20);
        centraliteTexteS4.setRows(5);
        centraliteScrollS4.setViewportView(centraliteTexteS4);

        jPanel70.add(centraliteScrollS4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 870, 265, 120));

        centraliteTexteS5.setColumns(20);
        centraliteTexteS5.setRows(5);
        centraliteScrollS5.setViewportView(centraliteTexteS5);

        jPanel70.add(centraliteScrollS5, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 870, 265, 120));

        centraliteTexteS8.setColumns(20);
        centraliteTexteS8.setRows(5);
        centraliteScrollS8.setViewportView(centraliteTexteS8);

        jPanel70.add(centraliteScrollS8, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 1260, 265, 120));

        centraliteTexteS10.setColumns(20);
        centraliteTexteS10.setRows(5);
        centraliteScrollS10.setViewportView(centraliteTexteS10);

        jPanel70.add(centraliteScrollS10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1650, 265, 120));

        centraliteTexteS9.setColumns(20);
        centraliteTexteS9.setRows(5);
        centraliteScrollS9.setViewportView(centraliteTexteS9);

        jPanel70.add(centraliteScrollS9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 1260, 265, 120));

        centraliteTexteS7.setColumns(20);
        centraliteTexteS7.setRows(5);
        centraliteScrollS7.setViewportView(centraliteTexteS7);

        jPanel70.add(centraliteScrollS7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1260, 265, 120));

        miseEnAvantPersonnageTexte1.setColumns(20);
        miseEnAvantPersonnageTexte1.setRows(5);
        miseEnAvantPersonnageTexte1.setText("L’analyse des graphes de mentions saison par saison apporte un éclairage inédit sur la construction narrative et relationnelle des personnages\nde Friends. En représentant qui parle de qui au fil des saisons, ces 10 visualisations offrent une lecture fine de l’évolution des dynamiques sociales, \ndes priorités scénaristiques et de la centralité affective des six protagonistes. La centralité mets en avant la répartition équilibrée dans ses mentions.\nCes graphes peuvent être mis en lien avec l'analyse de mise en avant et l'analyse de sentiments, pourquoi il parle plus à un personnage à ce \nmoment là ? comment l'évolution de centralité d'un personnage affectent ses sentiments ? quand est-ce que le groupe est le plus unie ? S5.\nQuels sont les moments où un personnage s’isole du groupe ? Pourquoi ? Y a-t-il corrélation entre des mentions fréquentes et \ndes sentiments positifs ? Le pic de mise en avant correspond-il à une augmentation des liens sociaux ? Quel personnage progresse en social ?");
        miseEnAvantPersonnageScroll1.setViewportView(miseEnAvantPersonnageTexte1);

        jPanel70.add(miseEnAvantPersonnageScroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 800, 130));

        jPanel53.add(jPanel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 530, 860, 1780));

        jPanel52.add(jPanel53);

        jPanel8.add(jPanel52, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel8);

        resultatPersonnage.addTab("Mise en scène", jScrollPane1);

        panelPersonnageRepliqueFavorite.setLayout(new java.awt.BorderLayout());

        nomPersonnage1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        nomPersonnage1.setText("Joey Tribbiani");
        nomPersonnage1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel28.add(nomPersonnage1);

        panelPersonnageRepliqueFavorite.add(jPanel28, java.awt.BorderLayout.NORTH);

        jPanel29.setLayout(new java.awt.GridLayout(3, 2));

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotCaracteristique.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotCaracteristiqueLayout = new javax.swing.GroupLayout(imageMotCaracteristique);
        imageMotCaracteristique.setLayout(imageMotCaracteristiqueLayout);
        imageMotCaracteristiqueLayout.setHorizontalGroup(
            imageMotCaracteristiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueLayout.setVerticalGroup(
            imageMotCaracteristiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        jPanel10.add(imageMotCaracteristique, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 390, 250));

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(153, 153, 153));
        jLabel100.setText("Quels sont ces mots caractéritistiques (passions, haines, ...) ?");
        jPanel10.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        labelMotCar.setColumns(20);
        labelMotCar.setRows(5);
        jScrollPane7.setViewportView(labelMotCar);

        jPanel10.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 390, -1));

        jPanel29.add(jPanel10);

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotPref.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotPrefLayout = new javax.swing.GroupLayout(imageMotPref);
        imageMotPref.setLayout(imageMotPrefLayout);
        imageMotPrefLayout.setHorizontalGroup(
            imageMotPrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMotPrefLayout.setVerticalGroup(
            imageMotPrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.add(imageMotPref, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 370, 220));

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(153, 153, 153));
        jLabel101.setText("Quels domaines/personnages distinctifs lui offre de la joie ? (passions)");
        jPanel11.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("La joie est une émotion importante de Friends, voyons comment\nelle se manifeste distinctement entre les personnages.");
        jTextArea2.setEnabled(false);
        jScrollPane5.setViewportView(jTextArea2);

        jPanel11.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 370, 40));

        labelMotCarJoie.setColumns(20);
        labelMotCarJoie.setRows(5);
        jScrollPane6.setViewportView(labelMotCarJoie);

        jPanel11.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 370, -1));

        jPanel29.add(jPanel11);

        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotCaracteristiqueSaisonX1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotCaracteristiqueSaisonX1Layout = new javax.swing.GroupLayout(imageMotCaracteristiqueSaisonX1);
        imageMotCaracteristiqueSaisonX1.setLayout(imageMotCaracteristiqueSaisonX1Layout);
        imageMotCaracteristiqueSaisonX1Layout.setHorizontalGroup(
            imageMotCaracteristiqueSaisonX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueSaisonX1Layout.setVerticalGroup(
            imageMotCaracteristiqueSaisonX1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel30.add(imageMotCaracteristiqueSaisonX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 390, 250));

        saisonMarquante1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        saisonMarquante1.setForeground(new java.awt.Color(153, 153, 153));
        jPanel30.add(saisonMarquante1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        labelMotCarX1.setColumns(20);
        labelMotCarX1.setRows(5);
        jScrollPane9.setViewportView(labelMotCarX1);

        jPanel30.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 390, -1));

        jPanel29.add(jPanel30);

        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotCaracteristiqueSaisonX2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotCaracteristiqueSaisonX2Layout = new javax.swing.GroupLayout(imageMotCaracteristiqueSaisonX2);
        imageMotCaracteristiqueSaisonX2.setLayout(imageMotCaracteristiqueSaisonX2Layout);
        imageMotCaracteristiqueSaisonX2Layout.setHorizontalGroup(
            imageMotCaracteristiqueSaisonX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueSaisonX2Layout.setVerticalGroup(
            imageMotCaracteristiqueSaisonX2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel31.add(imageMotCaracteristiqueSaisonX2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 390, 250));

        saisonMarquante2.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        saisonMarquante2.setForeground(new java.awt.Color(153, 153, 153));
        jPanel31.add(saisonMarquante2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        labelMotCarX2.setColumns(20);
        labelMotCarX2.setRows(5);
        jScrollPane8.setViewportView(labelMotCarX2);

        jPanel31.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 390, -1));

        jPanel29.add(jPanel31);

        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotCaracteristiqueSaisonX3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotCaracteristiqueSaisonX3Layout = new javax.swing.GroupLayout(imageMotCaracteristiqueSaisonX3);
        imageMotCaracteristiqueSaisonX3.setLayout(imageMotCaracteristiqueSaisonX3Layout);
        imageMotCaracteristiqueSaisonX3Layout.setHorizontalGroup(
            imageMotCaracteristiqueSaisonX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueSaisonX3Layout.setVerticalGroup(
            imageMotCaracteristiqueSaisonX3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel32.add(imageMotCaracteristiqueSaisonX3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 390, 250));

        saisonMarquante3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        saisonMarquante3.setForeground(new java.awt.Color(153, 153, 153));
        jPanel32.add(saisonMarquante3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        labelMotCarX3.setColumns(20);
        labelMotCarX3.setRows(5);
        jScrollPane11.setViewportView(labelMotCarX3);

        jPanel32.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 390, -1));

        jPanel29.add(jPanel32);

        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageMotCaracteristiqueSaisonX4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMotCaracteristiqueSaisonX4Layout = new javax.swing.GroupLayout(imageMotCaracteristiqueSaisonX4);
        imageMotCaracteristiqueSaisonX4.setLayout(imageMotCaracteristiqueSaisonX4Layout);
        imageMotCaracteristiqueSaisonX4Layout.setHorizontalGroup(
            imageMotCaracteristiqueSaisonX4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueSaisonX4Layout.setVerticalGroup(
            imageMotCaracteristiqueSaisonX4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel33.add(imageMotCaracteristiqueSaisonX4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 390, 250));

        saisonMarquante4.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        saisonMarquante4.setForeground(new java.awt.Color(153, 153, 153));
        jPanel33.add(saisonMarquante4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        labelMotCarX4.setColumns(20);
        labelMotCarX4.setRows(5);
        jScrollPane10.setViewportView(labelMotCarX4);

        jPanel33.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 390, -1));

        jPanel29.add(jPanel33);

        panelPersonnageRepliqueFavorite.add(jPanel29, java.awt.BorderLayout.CENTER);

        jScrollPane4.setViewportView(panelPersonnageRepliqueFavorite);

        resultatPersonnage.addTab("Profil de langage", jScrollPane4);

        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ficheIdentite.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ficheIdentiteLayout = new javax.swing.GroupLayout(ficheIdentite);
        ficheIdentite.setLayout(ficheIdentiteLayout);
        ficheIdentiteLayout.setHorizontalGroup(
            ficheIdentiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
        );
        ficheIdentiteLayout.setVerticalGroup(
            ficheIdentiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );

        jPanel22.add(ficheIdentite, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 810, 370));

        jLabel20.setText("Fiche d'identité finale des personnages grâce à la recherche de personnage (* design réalisé grâce la partie analyse relation)");
        jPanel22.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 680, -1));

        resultatPersonnage.addTab("Fiche d'identité", jPanel22);

        resultats.add(resultatPersonnage, "PERSONNAGE");
        resultatPersonnage.getAccessibleContext().setAccessibleName("tab");

        panelRechercheEtIndication.add(resultats, java.awt.BorderLayout.CENTER);

        panelPageRecherche.add(panelRechercheEtIndication, java.awt.BorderLayout.CENTER);

        SectionRecherche.addTab("Recherche", panelPageRecherche);

        javax.swing.GroupLayout AnalyseLangagièreLayout = new javax.swing.GroupLayout(AnalyseLangagière);
        AnalyseLangagière.setLayout(AnalyseLangagièreLayout);
        AnalyseLangagièreLayout.setHorizontalGroup(
            AnalyseLangagièreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );
        AnalyseLangagièreLayout.setVerticalGroup(
            AnalyseLangagièreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Analyse_StatistiqueLayout = new javax.swing.GroupLayout(Analyse_Statistique);
        Analyse_Statistique.setLayout(Analyse_StatistiqueLayout);
        Analyse_StatistiqueLayout.setHorizontalGroup(
            Analyse_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Analyse_StatistiqueLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(AnalyseLangagière, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );
        Analyse_StatistiqueLayout.setVerticalGroup(
            Analyse_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Analyse_StatistiqueLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(AnalyseLangagière, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2157, Short.MAX_VALUE))
        );

        SectionRecherche.addTab("Analyse Langagière", Analyse_Statistique);
        Analyse_Statistique.getAccessibleContext().setAccessibleName("Statistique_");

        Evolution_positivite.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Evol_pos_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_pos_imageLayout = new javax.swing.GroupLayout(Evol_pos_image);
        Evol_pos_image.setLayout(Evol_pos_imageLayout);
        Evol_pos_imageLayout.setHorizontalGroup(
            Evol_pos_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        Evol_pos_imageLayout.setVerticalGroup(
            Evol_pos_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        Evolution_positivite.add(Evol_pos_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 770, 350));

        TexteEvolpos.setColumns(20);
        TexteEvolpos.setRows(5);
        TexteEvolpos.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        ScrollEvolpos.setViewportView(TexteEvolpos);

        Evolution_positivite.add(ScrollEvolpos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 770, 120));

        Analyse_Sentiment.addTab("Evolution de la positivité", Evolution_positivite);

        Evolution_negativité.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Evol_neg_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Evol_neg_image.setPreferredSize(new java.awt.Dimension(770, 350));

        javax.swing.GroupLayout Evol_neg_imageLayout = new javax.swing.GroupLayout(Evol_neg_image);
        Evol_neg_image.setLayout(Evol_neg_imageLayout);
        Evol_neg_imageLayout.setHorizontalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        Evol_neg_imageLayout.setVerticalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        Evolution_negativité.add(Evol_neg_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 770, 350));

        TexteEvolneg.setColumns(20);
        TexteEvolneg.setRows(5);
        TexteEvolneg.setText("La négativité varie fortement d’un personnage à l’autre, mais certains pics s’expliquent par des événements narratifs majeurs.\nSaison 2 : Phoebe connaît une baisse notable, probablement liée à la découverte de son frère et à la confrontation à sa\n               propre histoire familiale. Monica, quant à elle, vit une rupture douloureuse avec Richard. \n               Joey, qui obtient un rôle majeur, affiche un baisse de négativité — sa carrière décolle et sa vie amoureuse s’épanouit. \nSaison 4 : Rachel se montre plus négative — sa jalousie vis-à-vis du mariage de Ross avec Emily devient centrale.\nSaison 7 : nouvelle baisse chez Phoebe, alors que paradoxalement elle se rapproche de Mike — \n               peut-être l’effet d’une réorientation affective plus sérieuse qui l’éloigne de sa posture excentrique.\nLes pics de négativité reflètent souvent des moments d’instabilité amoureuse ou familiale ou même relié au travail.");
        ScrollEvolneg.setViewportView(TexteEvolneg);

        Evolution_negativité.add(ScrollEvolneg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 750, 140));

        Analyse_Sentiment.addTab("Evolution de la négativité", Evolution_negativité);

        Sentiment_exprimé_panel.setMinimumSize(new java.awt.Dimension(808, 895));
        Sentiment_exprimé_panel.setPreferredSize(new java.awt.Dimension(808, 895));
        Sentiment_exprimé_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Sentiment_exprimé_ross.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_rossLayout = new javax.swing.GroupLayout(Sentiment_exprimé_ross);
        Sentiment_exprimé_ross.setLayout(Sentiment_exprimé_rossLayout);
        Sentiment_exprimé_rossLayout.setHorizontalGroup(
            Sentiment_exprimé_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_rossLayout.setVerticalGroup(
            Sentiment_exprimé_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_ross, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 380, 250));

        Sentiment_exprimé_chandler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_chandlerLayout = new javax.swing.GroupLayout(Sentiment_exprimé_chandler);
        Sentiment_exprimé_chandler.setLayout(Sentiment_exprimé_chandlerLayout);
        Sentiment_exprimé_chandlerLayout.setHorizontalGroup(
            Sentiment_exprimé_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_chandlerLayout.setVerticalGroup(
            Sentiment_exprimé_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_chandler, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 380, 250));

        Sentiment_exprimé_rachel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_rachelLayout = new javax.swing.GroupLayout(Sentiment_exprimé_rachel);
        Sentiment_exprimé_rachel.setLayout(Sentiment_exprimé_rachelLayout);
        Sentiment_exprimé_rachelLayout.setHorizontalGroup(
            Sentiment_exprimé_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_rachelLayout.setVerticalGroup(
            Sentiment_exprimé_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_rachel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 380, 250));

        Sentiment_exprimé_monica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_monicaLayout = new javax.swing.GroupLayout(Sentiment_exprimé_monica);
        Sentiment_exprimé_monica.setLayout(Sentiment_exprimé_monicaLayout);
        Sentiment_exprimé_monicaLayout.setHorizontalGroup(
            Sentiment_exprimé_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_monicaLayout.setVerticalGroup(
            Sentiment_exprimé_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_monica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 380, 250));

        Sentiment_exprimé_phoebe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_phoebeLayout = new javax.swing.GroupLayout(Sentiment_exprimé_phoebe);
        Sentiment_exprimé_phoebe.setLayout(Sentiment_exprimé_phoebeLayout);
        Sentiment_exprimé_phoebeLayout.setHorizontalGroup(
            Sentiment_exprimé_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_phoebeLayout.setVerticalGroup(
            Sentiment_exprimé_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_phoebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 380, 250));

        Sentiment_exprimé_joey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_joeyLayout = new javax.swing.GroupLayout(Sentiment_exprimé_joey);
        Sentiment_exprimé_joey.setLayout(Sentiment_exprimé_joeyLayout);
        Sentiment_exprimé_joeyLayout.setHorizontalGroup(
            Sentiment_exprimé_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Sentiment_exprimé_joeyLayout.setVerticalGroup(
            Sentiment_exprimé_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Sentiment_exprimé_panel.add(Sentiment_exprimé_joey, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 630, 380, 250));

        TexteSentimentexprime.setColumns(20);
        TexteSentimentexprime.setRows(5);
        TexteSentimentexprime.setText("Ces graphiques comptabilisent les émotions exprimées phrase par phrase dans la série, du début à la fin. \nIls montrent uniquement la fréquence d’apparition des sentiments dans les dialogues.\nOn remarque que la joie et la colère sont très largement dominantes.\nCe sont les émotions les plus souvent exprimées par les personnages, \nce qui reflète le ton humoristique et parfois conflictuel de Friends.\nCependant, ce type d’analyse ne permet pas de rendre compte de la complexité émotionnelle ou des sentiments plus profonds, \ncomme la tristesse ou l’amour, qui peuvent être présents de manière plus subtile ou moins verbalisée. \nPour avoir des analyses plus fines des sentiments profonds. Voir page \"Evolution des sentiments\".");
        ScrollSentimentexprime.setViewportView(TexteSentimentexprime);

        Sentiment_exprimé_panel.add(ScrollSentimentexprime, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 730, -1));

        Sentiment_exprime.setViewportView(Sentiment_exprimé_panel);

        Analyse_Sentiment.addTab("Sentiment_exprimé", Sentiment_exprime);

        Evolution_Sentiment_Panel.setMinimumSize(new java.awt.Dimension(808, 1400));
        Evolution_Sentiment_Panel.setPreferredSize(new java.awt.Dimension(808, 1400));
        Evolution_Sentiment_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Evol_Sentiment_monica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_monicaLayout = new javax.swing.GroupLayout(Evol_Sentiment_monica);
        Evol_Sentiment_monica.setLayout(Evol_Sentiment_monicaLayout);
        Evol_Sentiment_monicaLayout.setHorizontalGroup(
            Evol_Sentiment_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Evol_Sentiment_monicaLayout.setVerticalGroup(
            Evol_Sentiment_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_monica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 380, 250));

        TexteMonica.setEditable(false);
        TexteMonica.setColumns(20);
        TexteMonica.setRows(5);
        TexteMonica.setText("Saison 2 : Amour bas : cause rupture Richard.\nSaison 3 : Recontre avec Chip Matthews, qu'elle connaissait déjà \n(donc pas de surprise).\nSaison 5 : Relation avec Chandler, bouscule toute ces émotions.\nSaison 8 : Elle se marie, elle as donc plus peur.\nSaison 9 : Apprends qu'elle ne peut pas avoir d'enfants, \nelle est stérile.");
        ScrollMonicatext.setViewportView(TexteMonica);

        Evolution_Sentiment_Panel.add(ScrollMonicatext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 380, 130));

        Evol_Sentiment_ross.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_rossLayout = new javax.swing.GroupLayout(Evol_Sentiment_ross);
        Evol_Sentiment_ross.setLayout(Evol_Sentiment_rossLayout);
        Evol_Sentiment_rossLayout.setHorizontalGroup(
            Evol_Sentiment_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Evol_Sentiment_rossLayout.setVerticalGroup(
            Evol_Sentiment_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_ross, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 380, 250));

        TexteRoss.setEditable(false);
        TexteRoss.setColumns(20);
        TexteRoss.setRows(5);
        TexteRoss.setText("Début de série : Il prend confiance en lui parce qu'il apprend que \nRachel l'aime. Elle le lui avoue alors qu'elle est ivre.\nSaison 4 : Rachel surprend Ross en venant à son mariage.\nSaison 5 : Rupture avec Émilie.\nSaison 6 : Pic d'amour : plan à trois.\nSaison 7 : Apprend qu'il sort avec la même fille que joey.\nSaison 8 : Rupture avec mona, se remet avec Rachel.\nSaison 10 : Fini par être avec Rachel, même si il a cru que cela\n n'allait pas arriver.");
        ScrollRosstext.setViewportView(TexteRoss);

        Evolution_Sentiment_Panel.add(ScrollRosstext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, 380, 130));

        Evol_Sentiment_rachel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_rachelLayout = new javax.swing.GroupLayout(Evol_Sentiment_rachel);
        Evol_Sentiment_rachel.setLayout(Evol_Sentiment_rachelLayout);
        Evol_Sentiment_rachelLayout.setHorizontalGroup(
            Evol_Sentiment_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Evol_Sentiment_rachelLayout.setVerticalGroup(
            Evol_Sentiment_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_rachel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 818, 380, 250));

        TexteRachel.setEditable(false);
        TexteRachel.setColumns(20);
        TexteRachel.setRows(5);
        TexteRachel.setText("Saison 2 : Ce met en couple avec Ross.\nSaison 3 : Dispute avec Ross.\nSaison 4 : Rachel surprend Ross en venant à son mariage.\nSaison 6 : Mariage à Las Vegas.\nSaison 7 : Apprends qu'elle est enceinte (surprise), déprime \nqu'elle n'est pas marié et pas d'enfants à ces 30 ans. \n(baisse amour).\nSaison 8 : A accouché.\nSaison 9 : Mariage accidentel avec joey, Ross sort avec Charlie. \nAlors que ce qui était prévu c'était une de mande de mariage de\nRoss à Rachel.");
        ScrollRacheltext.setViewportView(TexteRachel);

        Evolution_Sentiment_Panel.add(ScrollRacheltext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 1070, 380, 130));

        Evol_Sentiment_phoebe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_phoebeLayout = new javax.swing.GroupLayout(Evol_Sentiment_phoebe);
        Evol_Sentiment_phoebe.setLayout(Evol_Sentiment_phoebeLayout);
        Evol_Sentiment_phoebeLayout.setHorizontalGroup(
            Evol_Sentiment_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Evol_Sentiment_phoebeLayout.setVerticalGroup(
            Evol_Sentiment_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_phoebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 380, 250));

        TextePhoebe.setEditable(false);
        TextePhoebe.setColumns(20);
        TextePhoebe.setRows(5);
        TextePhoebe.setText("Début de série: Personnage triste à cause de la solitude.\nSaison 6 : Phoebe ne s'est pas sentie seul \n(problème du personnage) Parce à été hébergé par ces amies. \nLes pics de peur et de surprise sont induit sa santé avec \nl'infarctus qu'elle à eut. Mais également perdu son travail.\nSaison 9 : Surprise d'avoir trouver son \"âme soeur\"\n mais ce passe mal. \nElle fini par être triste sur la fin de la série à cause de leur rupture.\nEt ce retrouve seul à nouveau. \nParce que les autres sont en couple et délaisse Phoebe");
        ScrollPhoebetext.setViewportView(TextePhoebe);

        Evolution_Sentiment_Panel.add(ScrollPhoebetext, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 380, 130));

        Evol_Sentiment_joey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_joeyLayout = new javax.swing.GroupLayout(Evol_Sentiment_joey);
        Evol_Sentiment_joey.setLayout(Evol_Sentiment_joeyLayout);
        Evol_Sentiment_joeyLayout.setHorizontalGroup(
            Evol_Sentiment_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        Evol_Sentiment_joeyLayout.setVerticalGroup(
            Evol_Sentiment_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_joey, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 818, -1, 250));

        TexteJoey.setEditable(false);
        TexteJoey.setColumns(20);
        TexteJoey.setRows(5);
        TexteJoey.setText("Saison 2 : Sort très brièvement avec Erika,  (métier joey : acteur)\n  perd son rôle de docteur dans la série \"Les jours de notre vie\".\nSaison 3 : est amoureux de sa partenaire au théâtre. Mais il \n  apprend qu'elle est déjà avec le metteur en scène. \nSaison 4 : Ce fait cambrioler, Chandler accepte de passer \n  Thanksgiving avec Joey\nSaison 5 : Il obtient un rôle, mais la série est annulé\nSaison 6 : Tombe amoureux d'une danseuse. Mais ce n'est pas\n réciproque. Il perd également son assurance maladie (peur)\nSaison 7 : Joey subit à son tour un vrai chagrin d'amour,\n rejeté par Erin comme il rejetait ses conquêtes.\n Il reprend son rôle de docteur.\nSaison 8 : Demande accidentel en mariage à Rachel. Qui accepte.\n et commence à éprouver des sentiments à Rachel.\nSaison 10 : Echec de la relation. Déstabilisé par tout les\n changement autour de lui (les couples formé par ces amis).");
        ScrollJoeytext.setViewportView(TexteJoey);

        Evolution_Sentiment_Panel.add(ScrollJoeytext, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 1070, 380, 130));

        Evol_Sentiment_chandler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_Sentiment_chandlerLayout = new javax.swing.GroupLayout(Evol_Sentiment_chandler);
        Evol_Sentiment_chandler.setLayout(Evol_Sentiment_chandlerLayout);
        Evol_Sentiment_chandlerLayout.setHorizontalGroup(
            Evol_Sentiment_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Evol_Sentiment_chandlerLayout.setVerticalGroup(
            Evol_Sentiment_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Evolution_Sentiment_Panel.add(Evol_Sentiment_chandler, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 380, 250));

        TexteChandler.setEditable(false);
        TexteChandler.setColumns(20);
        TexteChandler.setRows(5);
        TexteChandler.setText("Saison 2 : Eddie son nouveau collocataire lui fait peur.\nSaison 4 : Se met en couple avec Kathy, \nil surpris qu'elle à déjà été en couple avec joey.\nSaison 5 : Il révèle à ces amis qu'il est en couple avec ces Monica.\nSaison 6 : Chandler apprend que Monica a réservé \nle Morgan Chase Museum pour leur mariage.\nSaison 7 : Le mariage comme à s'organiser \nil commence à avoir peur.\nSaison 8 : Il est marié.");
        ScrollChandlertext.setViewportView(TexteChandler);

        Evolution_Sentiment_Panel.add(ScrollChandlertext, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 660, 380, 130));

        TexteInterpretationGlobale.setColumns(20);
        TexteInterpretationGlobale.setRows(5);
        TexteInterpretationGlobale.setText("Tous les personnages présentent un taux de tristesse globalement élevé, ce qui suggère une stratégie scénaristique \nvisant à construire l’attachement émotionnel par la vulnérabilité.\nLes courbes de \"Love\" montent chez tous, On peut citer Joey, qui as plus de succès avec les femmes vers la fin de la série. \nMême si il n'as pas de relation stable\nL’évolution générale montre que la série démarre sur un terrain d’insécurité émotionnelle, \npuis tend vers un équilibre affectif plus marqué en fin de série, malgré les obstacles. \nLa surprise est souvent moins marquée mais sert d’outil comique régulier (surtout chez Joey et Chandler).\nLe spectateur est donc guidé par une oscillation entre stabilité affective et conflits émotionnels, \nassurant l’équilibre entre humour et profondeur.");
        ScrollInterpretationtext.setViewportView(TexteInterpretationGlobale);

        Evolution_Sentiment_Panel.add(ScrollInterpretationtext, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 1260, 690, 120));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Interprétation globale : Structure émotionnelle de la série");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Evolution_Sentiment_Panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 1230, 340, -1));

        Evolution_Sentiment.setViewportView(Evolution_Sentiment_Panel);

        Analyse_Sentiment.addTab("Evolution des sentiments", Evolution_Sentiment);

        sentiment_par_personnage_panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout sentiment_par_personnage_panelLayout = new javax.swing.GroupLayout(sentiment_par_personnage_panel);
        sentiment_par_personnage_panel.setLayout(sentiment_par_personnage_panelLayout);
        sentiment_par_personnage_panelLayout.setHorizontalGroup(
            sentiment_par_personnage_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        sentiment_par_personnage_panelLayout.setVerticalGroup(
            sentiment_par_personnage_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 190, Short.MAX_VALUE)
        );

        neg_nuage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout neg_nuageLayout = new javax.swing.GroupLayout(neg_nuage);
        neg_nuage.setLayout(neg_nuageLayout);
        neg_nuageLayout.setHorizontalGroup(
            neg_nuageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        neg_nuageLayout.setVerticalGroup(
            neg_nuageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        jLabel15.setText("Certaines sources utilisé pour l'analyse de sentiment");

        pos_nuage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pos_nuageLayout = new javax.swing.GroupLayout(pos_nuage);
        pos_nuage.setLayout(pos_nuageLayout);
        pos_nuageLayout.setHorizontalGroup(
            pos_nuageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        pos_nuageLayout.setVerticalGroup(
            pos_nuageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout SourceLayout = new javax.swing.GroupLayout(Source);
        Source.setLayout(SourceLayout);
        SourceLayout.setHorizontalGroup(
            SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceLayout.createSequentialGroup()
                .addGap(0, 59, Short.MAX_VALUE)
                .addGroup(SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(neg_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(pos_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(SourceLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(sentiment_par_personnage_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SourceLayout.setVerticalGroup(
            SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(22, 22, 22)
                .addComponent(sentiment_par_personnage_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(neg_nuage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pos_nuage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(2007, Short.MAX_VALUE))
        );

        Analyse_Sentiment.addTab("Source", Source);

        SectionRecherche.addTab("Analyse de Sentiment", Analyse_Sentiment);

        Carte_Relation_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Carte_Relation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Carte_RelationLayout = new javax.swing.GroupLayout(Carte_Relation);
        Carte_Relation.setLayout(Carte_RelationLayout);
        Carte_RelationLayout.setHorizontalGroup(
            Carte_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
        );
        Carte_RelationLayout.setVerticalGroup(
            Carte_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        Carte_Relation_Panel.add(Carte_Relation, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 810, 450));

        jLabel1.setText("Résultat Obtenu avec l'aide des graphiques de relation. Remanié dans une forme plus visuelle");
        Carte_Relation_Panel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 510, -1));

        Relation.addTab("Carte des Relations", Carte_Relation_Panel);

        Graphique_Relation_panel.setMinimumSize(new java.awt.Dimension(800, 950));
        Graphique_Relation_panel.setName(""); // NOI18N
        Graphique_Relation_panel.setPreferredSize(new java.awt.Dimension(800, 950));
        Graphique_Relation_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Relation_Ross.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_RossLayout = new javax.swing.GroupLayout(Relation_Ross);
        Relation_Ross.setLayout(Relation_RossLayout);
        Relation_RossLayout.setHorizontalGroup(
            Relation_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_RossLayout.setVerticalGroup(
            Relation_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Ross, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 380, 250));

        Relation_Chandler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_ChandlerLayout = new javax.swing.GroupLayout(Relation_Chandler);
        Relation_Chandler.setLayout(Relation_ChandlerLayout);
        Relation_ChandlerLayout.setHorizontalGroup(
            Relation_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_ChandlerLayout.setVerticalGroup(
            Relation_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Chandler, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 380, 250));

        Relation_Rachel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_RachelLayout = new javax.swing.GroupLayout(Relation_Rachel);
        Relation_Rachel.setLayout(Relation_RachelLayout);
        Relation_RachelLayout.setHorizontalGroup(
            Relation_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_RachelLayout.setVerticalGroup(
            Relation_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Rachel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, 380, 250));

        Relation_Monica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_MonicaLayout = new javax.swing.GroupLayout(Relation_Monica);
        Relation_Monica.setLayout(Relation_MonicaLayout);
        Relation_MonicaLayout.setHorizontalGroup(
            Relation_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_MonicaLayout.setVerticalGroup(
            Relation_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Monica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 380, 250));

        Relation_Phoebe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_PhoebeLayout = new javax.swing.GroupLayout(Relation_Phoebe);
        Relation_Phoebe.setLayout(Relation_PhoebeLayout);
        Relation_PhoebeLayout.setHorizontalGroup(
            Relation_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_PhoebeLayout.setVerticalGroup(
            Relation_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Phoebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, 380, 250));

        Relation_Joey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_JoeyLayout = new javax.swing.GroupLayout(Relation_Joey);
        Relation_Joey.setLayout(Relation_JoeyLayout);
        Relation_JoeyLayout.setHorizontalGroup(
            Relation_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        Relation_JoeyLayout.setVerticalGroup(
            Relation_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        Graphique_Relation_panel.add(Relation_Joey, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 690, 380, 250));

        TexteGraphiqueRel.setColumns(20);
        TexteGraphiqueRel.setLineWrap(true);
        TexteGraphiqueRel.setRows(5);
        TexteGraphiqueRel.setText("Dans Friends, on s’attend naturellement à ce que la majorité des dialogues reflète une ambiance amicale. \nLes affinités entre personnages se devinent souvent à travers le nombre d’échanges : on parle plus avec les personnes qu’on apprécie.\nC’est sur cette logique que reposent ces graphiques.\n\nLes phrases avec comme étiquette \"affection\" ou \"amoureux\" sont d’ailleurs bien corrélés à ces liens. \nMais ils ne traduisent pas toujours une relation amoureuse. Joey, par exemple, emploie fréquemment ces termes ; cela traduit plutôt un lien d’amitié fort, où il peut se confier sur des sujets comme ceux-ci. \nÀ l’inverse, une vraie relation amoureuse se remarque par une combinaison de \"conflits\" ou \"désaccords\" avec des marqueurs d’\"affection\".");
        ScrollGraphiqueRel.setViewportView(TexteGraphiqueRel);

        Graphique_Relation_panel.add(ScrollGraphiqueRel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 780, 100));

        RelRac.setText("Rachel");
        Graphique_Relation_panel.add(RelRac, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 670, 50, -1));

        RelPho.setText("Phoebe");
        Graphique_Relation_panel.add(RelPho, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, 50, -1));

        RelMon.setText("Monica");
        Graphique_Relation_panel.add(RelMon, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 50, -1));

        RelJoe.setText("Joey");
        Graphique_Relation_panel.add(RelJoe, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 670, 30, -1));

        RelRos.setText("Ross");
        Graphique_Relation_panel.add(RelRos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 50, -1));

        RelCha.setText("Chandler");
        Graphique_Relation_panel.add(RelCha, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 390, 50, -1));

        Graphique_Relation.setViewportView(Graphique_Relation_panel);

        Relation.addTab("Graphique_Relation", Graphique_Relation);

        Relation_Mention.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Relation_MentionLayout = new javax.swing.GroupLayout(Relation_Mention);
        Relation_Mention.setLayout(Relation_MentionLayout);
        Relation_MentionLayout.setHorizontalGroup(
            Relation_MentionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        Relation_MentionLayout.setVerticalGroup(
            Relation_MentionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        jLabel16.setText("Source utilisé pour la fabrication des graphiques, sans compter les bases construites dans la section Analyse de Sentiment.");

        javax.swing.GroupLayout SourceRelationLayout = new javax.swing.GroupLayout(SourceRelation);
        SourceRelation.setLayout(SourceRelationLayout);
        SourceRelationLayout.setHorizontalGroup(
            SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceRelationLayout.createSequentialGroup()
                .addGap(0, 140, Short.MAX_VALUE)
                .addGroup(SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SourceRelationLayout.createSequentialGroup()
                        .addComponent(Relation_Mention, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SourceRelationLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(70, 70, 70))))
        );
        SourceRelationLayout.setVerticalGroup(
            SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceRelationLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(Relation_Mention, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1986, Short.MAX_VALUE))
        );

        Relation.addTab("Source", SourceRelation);

        SectionRecherche.addTab("Relation", Relation);

        getContentPane().add(SectionRecherche);
        SectionRecherche.getAccessibleContext().setAccessibleName("Recherche");
    }// </editor-fold>//GEN-END:initComponents
    
    //NOAH, Enzo LOUIS
    private void afficherInfosPersonnage(String nom) {
        String lower = nom.toLowerCase();

        switch (lower) {
            case "joey" -> setProfil(
                "Joey Tribbiani", "Matt LeBlanc", "Américain", "57 ans", "25 juillet 1967 (États-Unis)",
                "/les_png/Joey_photo_profile.png",
                8182, 16.25, 5, "hey, know, right, like, out", new String[] { "S05", "S07", "S08", "S09" }, "joey"
            );
            case "rachel" -> setProfil(
                "Rachel Green", "Jennifer Aniston", "Américaine", "55 ans", "11 février 1969 (États-Unis)",
                "/les_png/Rachel_photo_profile.png",
                9118, 18.11, 1, "know, well, ross, right, gonna", new String[] { "S01", "S07", "S08" }, "rachel"
            );
            case "ross" -> setProfil(
                "Ross Geller", "David Schwimmer", "Américain", "57 ans", "2 novembre 1966 (États-Unis)",
                "/les_png/Ross_photo_profile.webp",
                9063, 18.01, 2, "know, hey, uh, well, right", new String[] { "S01", "S02", "S03" }, "ross"
            );
            case "phoebe" -> setProfil(
                "Phoebe Buffay", "Lisa Kudrow", "Américaine", "61 ans", "30 juillet 1963 (États-Unis)",
                "/les_png/Phoebe_photo_profile.png",
                7345, 14.59, 6, "know, well, like, hey, right", new String[] { "S05", "S07", "S09", "S10" }, "phoebe"
            );
            case "monica" -> setProfil(
                "Monica Geller", "Courteney Cox", "Américaine", "60 ans", "15 juin 1964 (États-Unis)",
                "/les_png/Monica_photo_profile.png",
                8278, 16.45, 4, "know, chandler, well, right, gonna", new String[] { "S01", "S07", "S09" }, "monica"
            );
            case "chandler" -> setProfil(
                "Chandler Bing", "Matthew Perry", "Américain", "54 ans", "19 août 1969 – 28 octobre 2023",
                "/les_png/Chandler_photo_profile.png",
                8350, 16.59, 3, "well, know, right, hey, out", new String[] { "S04", "S06", "S07" }, "chandler"
            );
            default -> setProfil(
                "Personnage inconnu", "-", "-", "-", "-", "/les_png/default.png",
                0, 0.0, 0, "", new String[] {}, ""
            );
        }
        
        imageMentions0.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S01.png");
        imageMentions1.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S02.png");
        imageMentions2.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S03.png");
        imageMentions3.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S04.png");
        imageMentions4.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S05.png");
        imageMentions5.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S06.png");
        imageMentions6.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S07.png");
        imageMentions7.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S08.png");
        imageMentions8.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S09.png");
        imageMentions9.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_" + lower + "_S10.png");
        imageMotCaracteristique.setImage("/les_png/recherche_personnage/mot_important_personnage_saison_importante/"  + lower + "_all.png");
        imageMotPref.setImage("/les_png/recherche_personnage/mot_important_personnage_saison_importante_joy/" + lower + "_all.png");
        miseEnAvantPersonnageImage.setImage("/les_png/recherche_personnage/graphe_tendance_mise_en_avant_" + lower + "_par_scenariste.png");
    }
    
    private void setProfil(String nomPerso, String acteur, String nationalite, String age,
                       String naissance, String imagePath,int nbRepliques, double pourcentage, int position, String topMots,
String[] saisonMarquante, String nomLower) {
        nomPersonnage.setText(nomPerso);
        nomPersonnage2.setText("Mise en scène de " + nomPerso);
        nomPersonnage1.setText("Recherche langage de " + nomPerso);
        jLabel39.setText(acteur);
        jLabel40.setText(nationalite);
        jLabel41.setText(age);
        jLabel42.setText(naissance);
        imagePersonnage.setImage(imagePath);
        jLabel43.setText("" + nbRepliques);
        jLabel44.setText(String.format("%.2f %%", pourcentage));
        jLabel45.setText(position + " / 6");
        LiensUtiles.setText("Liens utiles pour l’analyse de " + nomPerso);
        jLabel46.setText(topMots);
        
        JLabel[] saisonMarquanteTexte = {saisonMarquante1, saisonMarquante2, saisonMarquante3, saisonMarquante4};
        ImagePanel[] saisonMarquanteImage = {imageMotCaracteristiqueSaisonX1, imageMotCaracteristiqueSaisonX2, imageMotCaracteristiqueSaisonX3, imageMotCaracteristiqueSaisonX4};
        JTextArea[] saisonMarquanteInterpret = {labelMotCarX1, labelMotCarX2, labelMotCarX3, labelMotCarX4};
        
        for (int i = 0; i < saisonMarquanteImage.length; i++) {
            saisonMarquanteTexte[i].setText("");
            saisonMarquanteTexte[i].getParent().setVisible(false);
        }
        
        for (int i = 0; i < Math.min(saisonMarquanteImage.length, saisonMarquante.length); i++) {
            saisonMarquanteTexte[i].getParent().setVisible(true);
            saisonMarquanteTexte[i].setText("Analyse de la saison " + saisonMarquante[i] + " (" + (i+1) + (i==0 ? "ère" : "ème") + " saison marquante)");
            saisonMarquanteImage[i].setImage("/les_png/recherche_personnage/mot_important_personnage_saison_importante/"  + nomLower + "_saison_" + saisonMarquante[i] + ".png");
            saisonMarquanteInterpret[i].setText(RechercheInterpretations.getPersoFromNom(nomLower).get("TEXTS_MOT_SAISON_IMPORTANTE").get(i));
        }
        
        miseEnAvantPersonnageTexte.setText(RechercheInterpretations.getPersoFromNom(nomLower).get("TEXT_TENDANCE").get(0));
        int index = 0;
        for (JTextArea area : new JTextArea[] {centraliteTexteS1, centraliteTexteS2, centraliteTexteS3,centraliteTexteS4, 
            centraliteTexteS5, centraliteTexteS6, centraliteTexteS7, centraliteTexteS8, centraliteTexteS9, centraliteTexteS10}) {
            area.setText(RechercheInterpretations.getPersoFromNom(nomLower).get("TEXTS_CENTRALITE").get(index));
            index++;
        }
        
        labelMotCar.setText(RechercheInterpretations.getPersoFromNom(nomLower).get("TEXT_MOT_GLOBAL").get(0));
        labelMotCarJoie.setText(RechercheInterpretations.getPersoFromNom(nomLower).get("TEXT_MOT_GLOBAL_JOIE").get(0));
    }
    //NOAH FIN
    
    
    
    
    private void rechercheMotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechercheMotKeyPressed
        if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            rechercheMotActionPerformed(null);
        }
    }//GEN-LAST:event_rechercheMotKeyPressed

    private void rechercheMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheMotActionPerformed
        if (dialog != null) {
            ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "MOT");
            dialog.onUserAction(rechercheMot.getText());
        }
    }//GEN-LAST:event_rechercheMotActionPerformed

    private void recherchePersonnageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_recherchePersonnageItemStateChanged
        // Quand un personnage est choisit
    }//GEN-LAST:event_recherchePersonnageItemStateChanged
    
    public static int extractNumber(String code) {
        if (code == null || code.length() < 3) {
            throw new IllegalArgumentException("format non valide: " + code);
        }
        try {
            return Integer.parseInt(code.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("format invalide avec le code code: " + code, e);
        }
    }
    private void rechercheEpisodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheEpisodeActionPerformed
        try {
            int saison = extraireNumeroSaison();
            int episode = extraireNumeroEpisode();

            afficherTitresEpisodes(saison, episode);
            afficherInfosEpisode(saison, episode);

            setGraphsEpisode();
        } catch (Exception e) {
            jLabel73.setText("Erreur: " + e.getMessage());
        }
    }//GEN-LAST:event_rechercheEpisodeActionPerformed

    private int extraireNumeroSaison() {
        Object saisonObj = rechercheSaison.getSelectedItem();
        String saisonStr = saisonObj.toString(); // Format attendu "S01"
        return Integer.parseInt(saisonStr.substring(1));
    }

    private int extraireNumeroEpisode() {
        Object episodeObj = rechercheEpisode.getSelectedItem();
        String episodeStr = episodeObj.toString(); // Format attendu "S01E02 - Titre"
        return Integer.parseInt(episodeStr.substring(episodeStr.indexOf('E') + 1, episodeStr.indexOf(' ')));
    }

    private void afficherTitresEpisodes(int saison, int episode) {
        Object episodeObj = rechercheEpisode.getSelectedItem();
        String episodeStr = episodeObj.toString();
        String titre = episodeStr.substring(6); // À partir de l'espace après "SxxExx"

        String affichage = "Episode " + episode + " de la saison " + saison + " : " + titre;
        labelEpisode.setText(affichage);
        labelEpisode2.setText(affichage);
        labelEpisode3.setText(affichage);
    }

    private void afficherInfosEpisode(int saison, int episode) {
        int nbRepliques = dialog.getNombreRepliquesEpisode(saison, episode);
        List<String> personnages = dialog.getPersonnagesEpisode(saison, episode);
        List<String> mots = dialog.getTopMotsEpisode(saison, episode);
        int nombreMots = dialog.getNombreMotRepliqueEpisode(saison, episode);
        
        jLabel73.setText(String.valueOf(nbRepliques));
        jLabel75.setText(String.join(", ", personnages));
        jLabel79.setText(String.join(", ", mots));
        jLabel81.setText(String.valueOf(nombreMots));
    }

    
    private void rechercheSaisonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        
        if(!rechercheEpisode.isVisible()){
            try {
                Object saisonObj = rechercheSaison.getSelectedItem();

                //Extraction du numéro de saison (format "S01")
                String saisonStr = saisonObj.toString();
                int saison = Integer.parseInt(saisonStr.substring(1));
                
                afficherTitresSaison(saison);
                
                //Nombre de répliques
                int nbRepliques = dialog.getNombreRepliquesSaison(saison);
                
                //Personnages impliqués
                String listePersonnagesImpliques = "";
                List<String> personnages = dialog.getPersonnagesSaison(saison);
                for(String personnage : personnages){
                    listePersonnagesImpliques += personnage;
                    listePersonnagesImpliques += ", ";
                }
                listePersonnagesImpliques = listePersonnagesImpliques.substring(0, listePersonnagesImpliques.length() - 2);
                
                //top 10 mots (cette partie met plusieurs secondes à s'exécuter... à réfléchir)
                String top10Mots = "";
                List<String> mots = dialog.getTopMotsSaison(saison);
                for(String mot : mots){
                    top10Mots += mot;
                    top10Mots += ", ";
                }
                top10Mots = top10Mots.substring(0, top10Mots.length() - 2);
                
                
                
                
                //Affichage du résultat
                afficherInfosSaison(saison);
                setGraphsSaison();
            } catch (Exception e) {
                jLabel106.setText("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
        }else{
            rechercheEpisodeActionPerformed(null);
        }
    }                                               

    private void afficherTitresSaison(int saison) {
        String affichage = "Saison " + saison;
        labelSaison.setText(affichage);
        labelSaison2.setText(affichage);
        labelSaison3.setText(affichage);
    }

    private void afficherInfosSaison(int saison) {
        int nbRepliques = dialog.getNombreRepliquesSaison(saison);
        List<String> personnages = dialog.getPersonnagesSaison(saison);
        List<String> mots = dialog.getTopMotsSaison(saison);
        int nbMots = dialog.getNombreMotRepliqueSaison(saison);
        
        jLabel106.setText(String.valueOf(nbRepliques));
        jLabel108.setText(joinAvecVirgules(personnages));
        jLabel112.setText(joinAvecVirgules(mots));
        jLabel114.setText(String.valueOf(nbMots));
    }

    private String joinAvecVirgules(List<String> liste) {
        return String.join(", ", liste);
    }
    private void jPanel85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel85MouseClicked
        
    }//GEN-LAST:event_jPanel85MouseClicked
    private void setGraphsSaison() {
        Object saisonObj = rechercheSaison.getSelectedItem();
        String saisonStr = saisonObj.toString(); // "S01"
        int saison = Integer.parseInt(saisonStr.substring(1));

        // Chemins des fichiers images
        File fichierRepartition = new File("src/main/resources/les_png/repliques_saison_" + saisonStr + ".png");
        File fichierMots = new File("src/main/resources/les_png/mots_par_replique_" + saisonStr + ".png");

        // Générer les graphes si les fichiers n'existent pas
        if (!fichierRepartition.exists()) {
            dialog.creerGrapheNombreDeRepliquesSaison(saison);
        }
        if (!fichierMots.exists()) {
            dialog.creerGrapheEvolutionNombreMotSaison(saison);
        }

        // Charger les images
        ImageIcon iconRepartition = new ImageIcon(fichierRepartition.getAbsolutePath());
        ImageIcon iconEvolutionMot = new ImageIcon(fichierMots.getAbsolutePath());

        // Création des JLabel et assignation des icônes
        JLabel labelRepartition = new JLabel();
        JLabel labelEvolutionMot = new JLabel();

        labelRepartition.setIcon(iconRepartition);
        labelEvolutionMot.setIcon(iconEvolutionMot);

        // Fixer la taille préférée du JLabel à la taille de l'image
        int width = iconRepartition.getIconWidth();
        int height = iconRepartition.getIconHeight();
        labelRepartition.setPreferredSize(new Dimension(width, height));

        width = iconEvolutionMot.getIconWidth();
        height = iconEvolutionMot.getIconHeight();
        labelEvolutionMot.setPreferredSize(new Dimension(width, height));

        // Définir le layout en FlowLayout pour respecter la taille préférée des labels
        jPanel85.setLayout(new FlowLayout());
        jPanel90.setLayout(new FlowLayout());

        // Mise à jour des panels
        jPanel85.removeAll();
        jPanel85.add(labelRepartition);
        jPanel85.revalidate();
        jPanel85.repaint();

        jPanel90.removeAll();
        jPanel90.add(labelEvolutionMot);
        jPanel90.revalidate();
        jPanel90.repaint();
    }



    
  
    
   

    private void setGraphsEpisode() {
        Object saisonObj = rechercheSaison.getSelectedItem();
        Object episodeObj = rechercheEpisode.getSelectedItem();

        String saisonStr = saisonObj.toString(); // "S01"
        String episodeStrFull = episodeObj.toString();
        String episodeCode = episodeStrFull.substring(episodeStrFull.indexOf('E'), episodeStrFull.indexOf(' ')); // "E01"

        String cheminImageRepartition = "src/main/resources/les_png/repliques_saison_" + saisonStr + "_episode_" + episodeCode + ".png";
        String cheminImageEvolutionMots = "src/main/resources/les_png/evolution_mots_replique_" + saisonStr + "_episode_" + episodeCode + ".png";

        File imageFileRepartition = new File(cheminImageRepartition);
        if (!imageFileRepartition.exists()) {
            dialog.creerGrapheNombreDeRepliquesEpisode(
                Integer.parseInt(saisonStr.substring(1)),
                Integer.parseInt(episodeCode.substring(1))
            );
        }

        File imageFileEvolutionMot = new File(cheminImageEvolutionMots);
        if (!imageFileEvolutionMot.exists()) {
            dialog.creerGrapheEvolutionNombreMotEpisode(
                Integer.parseInt(saisonStr.substring(1)),
                Integer.parseInt(episodeCode.substring(1))
            );
        }

        // Charge les images en ImageIcon
        ImageIcon iconRepartition = new ImageIcon(imageFileRepartition.getAbsolutePath());
        ImageIcon iconEvolutionMot = new ImageIcon(imageFileEvolutionMot.getAbsolutePath());

        // Création des JLabel et assignation des icônes
        JLabel labelRepartition = new JLabel();
        JLabel labelEvolutionMot = new JLabel();

        labelRepartition.setIcon(iconRepartition);
        labelEvolutionMot.setIcon(iconEvolutionMot);

        // Fixe la taille préférée du JLabel à la taille de l'image (ici 600x371)
        labelRepartition.setPreferredSize(new Dimension(600, 371));
        labelEvolutionMot.setPreferredSize(new Dimension(600, 371));

        // Définit le layout des panels en FlowLayout pour respecter la taille préférée des labels
        jPanel65.setLayout(new FlowLayout());
        jPanel75.setLayout(new FlowLayout());

        // Vide les panels et ajoute les labels
        jPanel65.removeAll();
        jPanel65.add(labelRepartition);
        jPanel65.revalidate();
        jPanel65.repaint();

        jPanel75.removeAll();
        jPanel75.add(labelEvolutionMot);
        jPanel75.revalidate();
        jPanel75.repaint();
    }








   
    
    private void resultatSaisonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultatSaisonMouseClicked
        setGraphsSaison();

    }//GEN-LAST:event_resultatSaisonMouseClicked

    private void rechercheSaisonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rechercheSaisonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheSaisonMouseClicked

    private void panelSaisonRepartitionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSaisonRepartitionMouseClicked
        
    }//GEN-LAST:event_panelSaisonRepartitionMouseClicked

    private void resultatEpisodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultatEpisodeMouseClicked
        setGraphsEpisode();
        
    }//GEN-LAST:event_resultatEpisodeMouseClicked

    
    private void rechercheSaisonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rechercheSaisonItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedSaison = (String) rechercheSaison.getSelectedItem();
            int numSaison = Integer.parseInt(selectedSaison.replaceAll("[^0-9]", ""));

            rechercheEpisode.setModel(new DefaultComboBoxModel<>(
                dialog.getEpisodesSaison(numSaison)
                    .stream()
                    .map(ep -> String.format("S%02dE%02d - %s", 
                          ep.getNumeroSaison(), 
                          ep.getNumeroEpisode(), 
                          ep.getTitre()))
                    .toArray(String[]::new)
            ));
        }
    }//GEN-LAST:event_rechercheSaisonItemStateChanged

    private void rechercheEpisodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rechercheEpisodeItemStateChanged
        
    }//GEN-LAST:event_rechercheEpisodeItemStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dialog.properlyCloseWindow();
    }//GEN-LAST:event_formWindowClosing
//NOAH
    private void rechercheMotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheMotButtonActionPerformed
        if (dialog != null) {
            ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "MOT");
            dialog.onUserAction(rechercheMot.getText());
        }
    }//GEN-LAST:event_rechercheMotButtonActionPerformed

    private void comboBoxSaisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSaisonActionPerformed
        JComboBox<String> combo = (JComboBox<String>) evt.getSource();
        generateGraphLineMotParEpisode(dataStoredForEpisodeStats, combo.getSelectedItem().toString());
    }//GEN-LAST:event_comboBoxSaisonActionPerformed

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
            if (infoWindow == null) {
            infoWindow = new JWindow();
            JLabel content = new JLabel(
                "<html>"
                    + "<h2>Explication des stats</h2>"
                    + "<ul>"
                    + "<li><b>Nombre total de répliques :</b><br>"
                    + "Le nombre de phrases ou de répliques prononcées par ce personnage tout au long de la série (toutes saisons confondues).</li><br>"
                    + "<li><b>Pourcentage par rapport aux autres :</b><br>"
                    + "La proportion de répliques de ce personnage par rapport au total des six personnages principaux. Permet de voir qui parle le plus ou le moins.</li><br>"
                    + "<li><b>Position du personnage qui parle le plus :</b><br>"
                    + "Le rang du personnage selon le nombre total de répliques. 1/6 = celui qui parle le plus, 6/6 = celui qui parle le moins parmi les principaux.</li><br>"
                    + "<li><b>Top 5 mots :</b><br>"
                    + "Les cinq mots les plus fréquemment utilisés par ce personnage dans la série, hors mots très courants (stop words).</li>"
                    + "</ul>"
                + "</html>"
            );            
            content.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            content.setBackground(Color.WHITE);
            content.setOpaque(true);
            infoWindow.getContentPane().add(content);
            infoWindow.pack();


            Point labelLocation = evt.getComponent().getLocationOnScreen();
            int labelWidth = evt.getComponent().getWidth();
            int infoWidth = infoWindow.getWidth();
            int infoHeight = infoWindow.getHeight();
            int x = labelLocation.x + (labelWidth / 2) - (infoWidth / 2);
            int y = labelLocation.y - infoHeight - 8;
            infoWindow.setLocation(x, y);


        }
        infoWindow.setVisible(true);
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        if (infoWindow != null) {
        infoWindow.setVisible(false);
    }
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        SectionRecherche.setSelectedIndex(1); 
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        SectionRecherche.setSelectedIndex(3); // Aller à "Relation"
        Relation.setSelectedIndex(0); 
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        SectionRecherche.setSelectedIndex(2); 
        Analyse_Sentiment.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel19MouseClicked

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.ImagePanel AnalyseLangagière;
    private javax.swing.JTabbedPane Analyse_Sentiment;
    private javax.swing.JPanel Analyse_Statistique;
    private controller.ImagePanel Carte_Relation;
    private javax.swing.JPanel Carte_Relation_Panel;
    private controller.ImagePanel Evol_Sentiment_chandler;
    private controller.ImagePanel Evol_Sentiment_joey;
    private controller.ImagePanel Evol_Sentiment_monica;
    private controller.ImagePanel Evol_Sentiment_phoebe;
    private controller.ImagePanel Evol_Sentiment_rachel;
    private controller.ImagePanel Evol_Sentiment_ross;
    private controller.ImagePanel Evol_neg_image;
    private controller.ImagePanel Evol_pos_image;
    private javax.swing.JScrollPane Evolution_Sentiment;
    private javax.swing.JPanel Evolution_Sentiment_Panel;
    private javax.swing.JPanel Evolution_negativité;
    private javax.swing.JPanel Evolution_positivite;
    private javax.swing.JScrollPane Graphique_Relation;
    private javax.swing.JPanel Graphique_Relation_panel;
    private javax.swing.JLabel LiensUtiles;
    private javax.swing.JPanel PanelinfoPerso;
    private javax.swing.JLabel RelCha;
    private javax.swing.JLabel RelJoe;
    private javax.swing.JLabel RelMon;
    private javax.swing.JLabel RelPho;
    private javax.swing.JLabel RelRac;
    private javax.swing.JLabel RelRos;
    private javax.swing.JTabbedPane Relation;
    private controller.ImagePanel Relation_Chandler;
    private controller.ImagePanel Relation_Joey;
    private controller.ImagePanel Relation_Mention;
    private controller.ImagePanel Relation_Monica;
    private controller.ImagePanel Relation_Phoebe;
    private controller.ImagePanel Relation_Rachel;
    private controller.ImagePanel Relation_Ross;
    private javax.swing.JScrollPane ScrollChandlertext;
    private javax.swing.JScrollPane ScrollEvolneg;
    private javax.swing.JScrollPane ScrollEvolpos;
    private javax.swing.JScrollPane ScrollGraphiqueRel;
    private javax.swing.JScrollPane ScrollInterpretationtext;
    private javax.swing.JScrollPane ScrollJoeytext;
    private javax.swing.JScrollPane ScrollMonicatext;
    private javax.swing.JScrollPane ScrollPhoebetext;
    private javax.swing.JScrollPane ScrollRacheltext;
    private javax.swing.JScrollPane ScrollRosstext;
    private javax.swing.JScrollPane ScrollSentimentexprime;
    private javax.swing.JTabbedPane SectionRecherche;
    private javax.swing.JScrollPane Sentiment_exprime;
    private controller.ImagePanel Sentiment_exprimé_chandler;
    private controller.ImagePanel Sentiment_exprimé_joey;
    private controller.ImagePanel Sentiment_exprimé_monica;
    private javax.swing.JPanel Sentiment_exprimé_panel;
    private controller.ImagePanel Sentiment_exprimé_phoebe;
    private controller.ImagePanel Sentiment_exprimé_rachel;
    private controller.ImagePanel Sentiment_exprimé_ross;
    private javax.swing.JPanel Source;
    private javax.swing.JPanel SourceRelation;
    private javax.swing.JTextArea TexteChandler;
    private javax.swing.JTextArea TexteEvolneg;
    private javax.swing.JTextArea TexteEvolpos;
    private javax.swing.JTextArea TexteGraphiqueRel;
    private javax.swing.JTextArea TexteInterpretationGlobale;
    private javax.swing.JTextArea TexteJoey;
    private javax.swing.JTextArea TexteMonica;
    private javax.swing.JTextArea TextePhoebe;
    private javax.swing.JTextArea TexteRachel;
    private javax.swing.JTextArea TexteRoss;
    private javax.swing.JTextArea TexteSentimentexprime;
    private javax.swing.JLabel bestEpisode;
    private javax.swing.JLabel bestSeason;
    private javax.swing.JScrollPane centraliteScrollS1;
    private javax.swing.JScrollPane centraliteScrollS10;
    private javax.swing.JScrollPane centraliteScrollS2;
    private javax.swing.JScrollPane centraliteScrollS3;
    private javax.swing.JScrollPane centraliteScrollS4;
    private javax.swing.JScrollPane centraliteScrollS5;
    private javax.swing.JScrollPane centraliteScrollS6;
    private javax.swing.JScrollPane centraliteScrollS7;
    private javax.swing.JScrollPane centraliteScrollS8;
    private javax.swing.JScrollPane centraliteScrollS9;
    private javax.swing.JTextArea centraliteTexteS1;
    private javax.swing.JTextArea centraliteTexteS10;
    private javax.swing.JTextArea centraliteTexteS2;
    private javax.swing.JTextArea centraliteTexteS3;
    private javax.swing.JTextArea centraliteTexteS4;
    private javax.swing.JTextArea centraliteTexteS5;
    private javax.swing.JTextArea centraliteTexteS6;
    private javax.swing.JTextArea centraliteTexteS7;
    private javax.swing.JTextArea centraliteTexteS8;
    private javax.swing.JTextArea centraliteTexteS9;
    private javax.swing.JComboBox<String> choixTypeRecherche;
    private javax.swing.JComboBox<String> comboBoxSaison;
    private controller.ImagePanel ficheIdentite;
    private javax.swing.JComboBox<String> filtreEpisode;
    private javax.swing.JComboBox<String> filtrePerso;
    private javax.swing.JComboBox<String> filtreSaison;
    private javax.swing.JComboBox<String> filtreSentiment;
    private controller.ImagePanel imageMentions0;
    private controller.ImagePanel imageMentions1;
    private controller.ImagePanel imageMentions2;
    private controller.ImagePanel imageMentions3;
    private controller.ImagePanel imageMentions4;
    private controller.ImagePanel imageMentions5;
    private controller.ImagePanel imageMentions6;
    private controller.ImagePanel imageMentions7;
    private controller.ImagePanel imageMentions8;
    private controller.ImagePanel imageMentions9;
    private controller.ImagePanel imageMotCaracteristique;
    private controller.ImagePanel imageMotCaracteristiqueSaisonX1;
    private controller.ImagePanel imageMotCaracteristiqueSaisonX2;
    private controller.ImagePanel imageMotCaracteristiqueSaisonX3;
    private controller.ImagePanel imageMotCaracteristiqueSaisonX4;
    private controller.ImagePanel imageMotPref;
    private controller.ImagePanel imagePersonnage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel labelDetailReplique;
    private javax.swing.JLabel labelEpisode;
    private javax.swing.JLabel labelEpisode2;
    private javax.swing.JLabel labelEpisode3;
    private javax.swing.JTextArea labelMotCar;
    private javax.swing.JTextArea labelMotCarJoie;
    private javax.swing.JTextArea labelMotCarX1;
    private javax.swing.JTextArea labelMotCarX2;
    private javax.swing.JTextArea labelMotCarX3;
    private javax.swing.JTextArea labelMotCarX4;
    private javax.swing.JLabel labelMotCourant1;
    private javax.swing.JLabel labelMotCourant2;
    private javax.swing.JLabel labelMotCourant3;
    private javax.swing.JLabel labelPersonnageReplique;
    private javax.swing.JLabel labelSaison;
    private javax.swing.JLabel labelSaison2;
    private javax.swing.JLabel labelSaison3;
    private javax.swing.JLabel labelSansRechercheExemple;
    private javax.swing.JLabel labelSansRechercheExemple1;
    private javax.swing.JLabel labelSansRechercheExemple2;
    private javax.swing.JLabel labelSansRechercheExemple3;
    private javax.swing.JLabel labelSansRechercheSentence;
    private javax.swing.JLabel labelTitre;
    private javax.swing.JLabel labelUtilisationMotParReplique;
    private controller.ImagePanel miseEnAvantPersonnageImage;
    private javax.swing.JScrollPane miseEnAvantPersonnageScroll;
    private javax.swing.JScrollPane miseEnAvantPersonnageScroll1;
    private javax.swing.JTextArea miseEnAvantPersonnageTexte;
    private javax.swing.JTextArea miseEnAvantPersonnageTexte1;
    private controller.ImagePanel neg_nuage;
    private javax.swing.JLabel nomPersonnage;
    private javax.swing.JLabel nomPersonnage1;
    private javax.swing.JLabel nomPersonnage2;
    private javax.swing.JLabel nombreResultatFiltre;
    private javax.swing.JPanel panelDetailReplique;
    private javax.swing.JPanel panelEpisodePresentation;
    private javax.swing.JPanel panelEpisodeRepartition;
    private javax.swing.JPanel panelEvolutionMot;
    private javax.swing.JPanel panelEvolutionMots;
    private javax.swing.JPanel panelMotCourant1;
    private javax.swing.JPanel panelMotCourant2;
    private javax.swing.JPanel panelMotCourant3;
    private javax.swing.JPanel panelMotRepartitionSaisonEtEpisode;
    private javax.swing.JPanel panelMotRepartitionSerie;
    private javax.swing.JPanel panelMotUtilisation;
    private javax.swing.JPanel panelPageRecherche;
    private javax.swing.JPanel panelPersonnageProfil;
    private javax.swing.JPanel panelPersonnageReplique;
    private javax.swing.JPanel panelPersonnageRepliqueFavorite;
    private javax.swing.JPanel panelPourcentageMotsPerso;
    private javax.swing.JPanel panelRecherche;
    private javax.swing.JPanel panelRechercheButtons;
    private javax.swing.JPanel panelRechercheEtIndication;
    private javax.swing.JPanel panelRechercheTitre;
    private javax.swing.JPanel panelResultatMotUtilisation;
    private javax.swing.JPanel panelSaisonPresentation;
    private javax.swing.JPanel panelSaisonRepartition;
    private javax.swing.JPanel panelTitre;
    private javax.swing.JPanel panelTitre2;
    private controller.ImagePanel pos_nuage;
    private javax.swing.JComboBox<String> rechercheEpisode;
    private style.CustomJTextField rechercheMot;
    private javax.swing.JButton rechercheMotButton;
    private javax.swing.JProgressBar rechercheMotProgBar;
    private javax.swing.JComboBox<String> recherchePersonnage;
    private javax.swing.JComboBox<String> rechercheSaison;
    private javax.swing.JPanel resultSansRecherche;
    private javax.swing.JTabbedPane resultatEpisode;
    private javax.swing.JTabbedPane resultatMot;
    private javax.swing.JTabbedPane resultatPersonnage;
    private javax.swing.JTabbedPane resultatSaison;
    private javax.swing.JPanel resultats;
    private javax.swing.JLabel saisonMarquante1;
    private javax.swing.JLabel saisonMarquante2;
    private javax.swing.JLabel saisonMarquante3;
    private javax.swing.JLabel saisonMarquante4;
    private javax.swing.JScrollPane scrollPaneDetailReplique;
    private controller.ImagePanel sentiment_par_personnage_panel;
    private javax.swing.JPanel statsperso;
    private javax.swing.JTable tableDetailReplique;
    private javax.swing.JLabel titre;
    // End of variables declaration//GEN-END:variables

    private Image getScaledImage(BufferedImage imgRepartition, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

