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
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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

public class VueStat extends javax.swing.JFrame {
    private ControllerDialogRecherche dialog;
    List<List<Object>> dataStoredForEpisodeStats;
    
    public void setDialog(ControllerDialogRecherche dialog) {
        this.dialog = dialog;
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
        imageMotsCaracteristiques.setImage("/les_png/mots_caractéristiques.png");
        imagePersonnage.setImage("/les_png/Joey_Test.png");
        imageMentions1.setImage("/les_png/graphe_orienté_mentions_entre_personnages/mentions_monica.png");
        imageMotCaracteristique.setImage("/les_png/mots_caracteristiques_par_personnages/mots_caracteristique_monica.png");
        imageMotPref.setImage("/les_png/mots_preferes_par_personnage/mots_preferes_monica.png");
        miseEnAvantPersonnageImage.setImage("/les_png/recherche_personnage/graphe_tendance_mise_en_avant_monica_par_scenariste.png");
        
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        
        recherchePersonnage.setVisible(false);
        rechercheSaison.setVisible(false);
        rechercheEpisode.setVisible(false);
        rechercheMotButton.setVisible(false);

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

        // chartPanel2.setPreferredSize(new Dimension(300, 150));
        jPanel13.removeAll();
        jPanel13.add(chartPanel2);
        
        jPanel13.revalidate();
        jPanel13.repaint();
    }
    /*
    public void generateGraphBarMotParPersonnage(List<List<Object>> data) {
        int total = data.stream()
            .mapToInt(row -> (Integer) row.get(1))
            .sum();
        
        DefaultCategoryDataset percentLine = new DefaultCategoryDataset();
        
        for (List<Object> row : data) {
            String name = (String) row.get(0);
            Integer value = ((Integer) row.get(1));
            percentLine.addValue(value, name, "");
        }

        JFreeChart chartPercentLine = ChartFactory.createStackedBarChart(
            null, // titre
            null,
            null,
            percentLine,
            PlotOrientation.HORIZONTAL,
            false, // légende
            false,
            false
        );
        
        CategoryPlot plotPercent = chartPercentLine.getCategoryPlot();
        // plotPercent.setInsets(new org.jfree.ui.RectangleInsets(0, 0, 0, 0));
        plotPercent.setOutlineVisible(false);
        plotPercent.getDomainAxis().setVisible(false);
        plotPercent.getRangeAxis().setVisible(false);
        plotPercent.setRangeGridlinesVisible(false);
        
        chartPercentLine.setBackgroundPaint(new Color(0, 0, 0, 0));
        chartPercentLine.getPlot().setBackgroundPaint(new Color(0, 0, 0, 0));
        
        StackedBarRenderer renderer = new StackedBarRenderer();
        
        renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator() {
            @Override
            public String generateToolTip(CategoryDataset dataset, int row, int column) {
                String name = (String) dataset.getRowKey(row);
                Number value = dataset.getValue(row, column);
                return String.format("%s : %d utilisations", name, value.intValue());
            }
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

        plotPercent.setRenderer(renderer);
        
        ChartPanel chartPanelPercent = new ChartPanel(chartPercentLine);
        chartPanelPercent.setOpaque(false);
        chartPanelPercent.setBackground(new Color(0, 0, 0, 0));

        chartPanelPercent.setPreferredSize(new Dimension(300, 150));
        panelPourcentageMotsPerso.removeAll();
        panelPourcentageMotsPerso.add(chartPanelPercent, java.awt.BorderLayout.NORTH);
        
        panelPourcentageMotsPerso.revalidate();
        panelPourcentageMotsPerso.repaint();
    }
    */

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
        panelSaisonRepartition = new javax.swing.JPanel();
        panelTitre2 = new javax.swing.JPanel();
        labelSaison2 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        panelSaisonDialogue = new javax.swing.JPanel();
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
        panelEpisodeRepartition = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        labelEpisode2 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        panelEpisodeDialogue = new javax.swing.JPanel();
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
        imageMotsCaracteristiques = new controller.ImagePanel();
        resultatMot = new javax.swing.JTabbedPane();
        panelMotUtilisation = new javax.swing.JPanel();
        panelMotCourant1 = new javax.swing.JPanel();
        labelMotCourant1 = new javax.swing.JLabel();
        panelResultatMotUtilisation = new javax.swing.JPanel();
        panelPersonnageReplique = new javax.swing.JPanel();
        labelPersonnageReplique = new javax.swing.JLabel();
        panelPourcentageMotsPerso = new javax.swing.JPanel();
        panelDetailReplique = new javax.swing.JPanel();
        labelDetailReplique = new javax.swing.JLabel();
        scrollPaneDetailReplique = new javax.swing.JScrollPane();
        tableDetailReplique = new javax.swing.JTable();
        jPanel64 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
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
        jLabel64 = new javax.swing.JLabel();
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
        panelPersonnageRepliqueFavorite = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        imageMotPref = new controller.ImagePanel();
        jLabel101 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        imageMotCaracteristique = new controller.ImagePanel();
        jLabel100 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
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
        miseEnAvantPersonnageScroll = new javax.swing.JScrollPane();
        miseEnAvantPersonnageTexte = new javax.swing.JTextArea();
        miseEnAvantPersonnageImage = new controller.ImagePanel();
        miseEnAvantPersonnageScroll1 = new javax.swing.JScrollPane();
        miseEnAvantPersonnageTexte1 = new javax.swing.JTextArea();
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
        jButton6 = new javax.swing.JButton();
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

        panelSaisonDialogue.setLayout(new java.awt.BorderLayout());

        labelSaison3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelSaison3.setText("Saison 1");
        labelSaison3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel89.add(labelSaison3);

        panelSaisonDialogue.add(jPanel89, java.awt.BorderLayout.NORTH);

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

        panelSaisonDialogue.add(jPanel90, java.awt.BorderLayout.CENTER);

        resultatSaison.addTab("Dialogue entre personnages", panelSaisonDialogue);

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

        panelEpisodeDialogue.setLayout(new java.awt.BorderLayout());

        labelEpisode3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelEpisode3.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        labelEpisode3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel74.add(labelEpisode3);

        panelEpisodeDialogue.add(jPanel74, java.awt.BorderLayout.NORTH);

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

        panelEpisodeDialogue.add(jPanel75, java.awt.BorderLayout.CENTER);

        resultatEpisode.addTab("Dialogue entre personnages", panelEpisodeDialogue);

        resultats.add(resultatEpisode, "EPISODE");

        resultSansRecherche.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        resultSansRecherche.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultSansRecherche.setLayout(new javax.swing.BoxLayout(resultSansRecherche, javax.swing.BoxLayout.Y_AXIS));

        labelSansRechercheSentence.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheSentence.setText("Retrouvez via la recherche ci-dessus des statistiques détaillées sur des personnages typiques de la série, sur des répliques, ....");
        resultSansRecherche.add(labelSansRechercheSentence);

        labelSansRechercheExemple.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        labelSansRechercheExemple.setText("Exemple :");
        resultSansRecherche.add(labelSansRechercheExemple);

        javax.swing.GroupLayout imageMotsCaracteristiquesLayout = new javax.swing.GroupLayout(imageMotsCaracteristiques);
        imageMotsCaracteristiques.setLayout(imageMotsCaracteristiquesLayout);
        imageMotsCaracteristiquesLayout.setHorizontalGroup(
            imageMotsCaracteristiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        imageMotsCaracteristiquesLayout.setVerticalGroup(
            imageMotsCaracteristiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2075, Short.MAX_VALUE)
        );

        resultSansRecherche.add(imageMotsCaracteristiques);

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

        labelDetailReplique.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        labelDetailReplique.setForeground(new java.awt.Color(153, 153, 153));
        labelDetailReplique.setText("Détails des répliques :");
        panelDetailReplique.add(labelDetailReplique, java.awt.BorderLayout.NORTH);

        tableDetailReplique.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableDetailReplique.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"\"Hello there !\"", "Joey",  new Integer(1),  new Integer(1)},
                {"\"Hello\"", "Ross",  new Integer(1),  new Integer(1)},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Réplique", "Personnage", "Saison", "Épisode"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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

        panelResultatMotUtilisation.add(panelDetailReplique, java.awt.BorderLayout.CENTER);

        jLabel76.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(153, 153, 153));
        jLabel76.setText("* Étude approfondie des émotions :");
        jPanel64.add(jLabel76);

        jLabel83.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel83.setText("Voir");
        jLabel83.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel24.add(jLabel83);

        jButton3.setBackground(new java.awt.Color(242, 242, 242));
        jButton3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jButton3.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jButton3.setText("analyse des sentiments > sentiments exprimés");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton3);

        jPanel64.add(jPanel24);

        panelResultatMotUtilisation.add(jPanel64, java.awt.BorderLayout.PAGE_END);

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

        jLabel64.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel64.setText("* Les mots prononcés en même temps par plusieurs personnage sont comptés une seule fois");
        jLabel64.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel39.add(jLabel64, java.awt.BorderLayout.SOUTH);

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
            .addGap(0, 428, Short.MAX_VALUE)
        );
        imagePersonnageLayout.setVerticalGroup(
            imagePersonnageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2042, Short.MAX_VALUE)
        );

        jPanel5.add(imagePersonnage);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.GridLayout(2, 1));

        PanelinfoPerso.setBackground(new java.awt.Color(204, 204, 255));
        PanelinfoPerso.setAlignmentX(0.5F);
        PanelinfoPerso.setLayout(new javax.swing.BoxLayout(PanelinfoPerso, javax.swing.BoxLayout.Y_AXIS));

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));
        jPanel21.setAlignmentX(0.5F);
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
        jPanel20.setAlignmentX(0.5F);
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(153, 153, 153));
        jLabel37.setText("Age:");
        jPanel20.add(jLabel37);

        jLabel41.setBackground(new java.awt.Color(204, 204, 255));
        jLabel41.setText("57 ans");
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
        jPanel18.setAlignmentX(0.5F);
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
        statsperso.setAlignmentX(0.5F);
        statsperso.setLayout(new javax.swing.BoxLayout(statsperso, javax.swing.BoxLayout.Y_AXIS));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setText("Stats");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        statsperso.add(jLabel3);

        jPanel23.setBackground(new java.awt.Color(204, 255, 255));
        jPanel23.setAlignmentX(0.5F);
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
        jPanel25.setAlignmentX(0.5F);
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.Y_AXIS));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Pourcentage par rapport au autres :  ");
        jLabel9.setAlignmentX(0.5F);
        jPanel25.add(jLabel9);

        jLabel44.setText("Matt Leblanc");
        jLabel44.setAlignmentX(0.5F);
        jLabel44.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel25.add(jLabel44);

        statsperso.add(jPanel25);

        jPanel26.setBackground(new java.awt.Color(204, 255, 255));
        jPanel26.setAlignmentX(0.5F);
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.Y_AXIS));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Postion du personnage qui parle le plus : ");
        jLabel10.setAlignmentX(0.5F);
        jPanel26.add(jLabel10);

        jLabel45.setText("Matt Leblanc");
        jLabel45.setAlignmentX(0.5F);
        jLabel45.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel26.add(jLabel45);

        statsperso.add(jPanel26);

        jPanel27.setBackground(new java.awt.Color(204, 255, 255));
        jPanel27.setAlignmentX(0.5F);
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

        jPanel17.add(statsperso);

        jPanel5.add(jPanel17);

        panelPersonnageProfil.add(jPanel5, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(panelPersonnageProfil);

        resultatPersonnage.addTab("Profil", jScrollPane2);

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.X_AXIS));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout imageMotPrefLayout = new javax.swing.GroupLayout(imageMotPref);
        imageMotPref.setLayout(imageMotPrefLayout);
        imageMotPrefLayout.setHorizontalGroup(
            imageMotPrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        imageMotPrefLayout.setVerticalGroup(
            imageMotPrefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        jPanel11.add(imageMotPref, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 380, 220));

        jLabel101.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(153, 153, 153));
        jLabel101.setText("Mots préférés :");
        jPanel11.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel12.add(jPanel11);

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout imageMotCaracteristiqueLayout = new javax.swing.GroupLayout(imageMotCaracteristique);
        imageMotCaracteristique.setLayout(imageMotCaracteristiqueLayout);
        imageMotCaracteristiqueLayout.setHorizontalGroup(
            imageMotCaracteristiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        imageMotCaracteristiqueLayout.setVerticalGroup(
            imageMotCaracteristiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        jPanel10.add(imageMotCaracteristique, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 380, 220));

        jLabel100.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(153, 153, 153));
        jLabel100.setText("Mots caractéristiques :");
        jPanel10.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel12.add(jPanel10);

        panelPersonnageRepliqueFavorite.add(jPanel12);

        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));

        jLabel71.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(153, 153, 153));
        jLabel71.setText("Réplique favorite et analyse langagière approfondi du personnage :");
        jPanel48.add(jLabel71);

        jLabel72.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel72.setText("Voir");
        jLabel72.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel22.add(jLabel72);

        jButton1.setBackground(new java.awt.Color(242, 242, 242));
        jButton1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jButton1.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jButton1.setText("partie analyse langagière");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel22.add(jButton1);

        jPanel48.add(jPanel22);

        panelPersonnageRepliqueFavorite.add(jPanel48);

        resultatPersonnage.addTab("Profil de langage", panelPersonnageRepliqueFavorite);

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
        jTextArea1.setText("Le scénariste choisit quand faire progresser l’intrigue des personnages. Leur évolution suit deux tendances souvent non corrélées : le nombre de \nrépliques et celui de mentions par les autres. Un personnage très bavard mais peu mentionné se développe de lui-même ou avec des personnages\nsecondaires, tandis qu’un personnage souvent mentionné, sans être présent, annonce souvent un événement majeur à venir.");
        jScrollPane3.setViewportView(jTextArea1);

        jPanel59.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 50));

        jPanel53.add(jPanel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 50));

        jPanel60.setMinimumSize(new java.awt.Dimension(740, 30));
        jPanel60.setPreferredSize(new java.awt.Dimension(0, 500));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        miseEnAvantPersonnageTexte.setColumns(20);
        miseEnAvantPersonnageTexte.setRows(5);
        miseEnAvantPersonnageScroll.setViewportView(miseEnAvantPersonnageTexte);

        jPanel60.add(miseEnAvantPersonnageScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 800, 120));

        miseEnAvantPersonnageImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout miseEnAvantPersonnageImageLayout = new javax.swing.GroupLayout(miseEnAvantPersonnageImage);
        miseEnAvantPersonnageImage.setLayout(miseEnAvantPersonnageImageLayout);
        miseEnAvantPersonnageImageLayout.setHorizontalGroup(
            miseEnAvantPersonnageImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        miseEnAvantPersonnageImageLayout.setVerticalGroup(
            miseEnAvantPersonnageImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );

        jPanel60.add(miseEnAvantPersonnageImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 360));

        miseEnAvantPersonnageTexte1.setColumns(20);
        miseEnAvantPersonnageTexte1.setRows(5);
        miseEnAvantPersonnageTexte1.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        miseEnAvantPersonnageScroll1.setViewportView(miseEnAvantPersonnageTexte1);

        jPanel60.add(miseEnAvantPersonnageScroll1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 800, 150));

        jPanel53.add(jPanel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 52, 840, 480));

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
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions0, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 265, 270));

        imageMentions1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions1Layout = new javax.swing.GroupLayout(imageMentions1);
        imageMentions1.setLayout(imageMentions1Layout);
        imageMentions1Layout.setHorizontalGroup(
            imageMentions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions1Layout.setVerticalGroup(
            imageMentions1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions1, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 10, 265, 270));

        centraliteTexteS2.setColumns(20);
        centraliteTexteS2.setRows(5);
        centraliteTexteS2.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS2.setViewportView(centraliteTexteS2);

        jPanel70.add(centraliteScrollS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 280, 265, 120));

        imageMentions2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions2Layout = new javax.swing.GroupLayout(imageMentions2);
        imageMentions2.setLayout(imageMentions2Layout);
        imageMentions2Layout.setHorizontalGroup(
            imageMentions2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions2Layout.setVerticalGroup(
            imageMentions2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 265, 270));

        centraliteTexteS3.setColumns(20);
        centraliteTexteS3.setRows(5);
        centraliteTexteS3.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS3.setViewportView(centraliteTexteS3);

        jPanel70.add(centraliteScrollS3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, 265, 120));

        imageMentions3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions3Layout = new javax.swing.GroupLayout(imageMentions3);
        imageMentions3.setLayout(imageMentions3Layout);
        imageMentions3Layout.setHorizontalGroup(
            imageMentions3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions3Layout.setVerticalGroup(
            imageMentions3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 265, 270));

        imageMentions4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions4Layout = new javax.swing.GroupLayout(imageMentions4);
        imageMentions4.setLayout(imageMentions4Layout);
        imageMentions4Layout.setHorizontalGroup(
            imageMentions4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions4Layout.setVerticalGroup(
            imageMentions4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions4, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 400, 265, 270));

        imageMentions5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions5Layout = new javax.swing.GroupLayout(imageMentions5);
        imageMentions5.setLayout(imageMentions5Layout);
        imageMentions5Layout.setHorizontalGroup(
            imageMentions5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions5Layout.setVerticalGroup(
            imageMentions5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 265, 270));

        imageMentions6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions6Layout = new javax.swing.GroupLayout(imageMentions6);
        imageMentions6.setLayout(imageMentions6Layout);
        imageMentions6Layout.setHorizontalGroup(
            imageMentions6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions6Layout.setVerticalGroup(
            imageMentions6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 790, 265, 270));

        imageMentions7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions7Layout = new javax.swing.GroupLayout(imageMentions7);
        imageMentions7.setLayout(imageMentions7Layout);
        imageMentions7Layout.setHorizontalGroup(
            imageMentions7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions7Layout.setVerticalGroup(
            imageMentions7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions7, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 790, 265, 270));

        imageMentions8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions8Layout = new javax.swing.GroupLayout(imageMentions8);
        imageMentions8.setLayout(imageMentions8Layout);
        imageMentions8Layout.setHorizontalGroup(
            imageMentions8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions8Layout.setVerticalGroup(
            imageMentions8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 790, 265, 270));

        imageMentions9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imageMentions9Layout = new javax.swing.GroupLayout(imageMentions9);
        imageMentions9.setLayout(imageMentions9Layout);
        imageMentions9Layout.setHorizontalGroup(
            imageMentions9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imageMentions9Layout.setVerticalGroup(
            imageMentions9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel70.add(imageMentions9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1180, 265, 270));

        jButton6.setBackground(new java.awt.Color(242, 242, 242));
        jButton6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jButton6.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jButton6.setText("Analyse des relations sur toute la série : voir partie analyse relation");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel70.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 1280, -1, 40));

        centraliteTexteS1.setColumns(20);
        centraliteTexteS1.setRows(5);
        centraliteTexteS1.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS1.setViewportView(centraliteTexteS1);

        jPanel70.add(centraliteScrollS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 265, 120));

        centraliteTexteS6.setColumns(20);
        centraliteTexteS6.setRows(5);
        centraliteTexteS6.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS6.setViewportView(centraliteTexteS6);

        jPanel70.add(centraliteScrollS6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 670, 265, 120));

        centraliteTexteS4.setColumns(20);
        centraliteTexteS4.setRows(5);
        centraliteTexteS4.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS4.setViewportView(centraliteTexteS4);

        jPanel70.add(centraliteScrollS4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 670, 265, 120));

        centraliteTexteS5.setColumns(20);
        centraliteTexteS5.setRows(5);
        centraliteTexteS5.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS5.setViewportView(centraliteTexteS5);

        jPanel70.add(centraliteScrollS5, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 670, 265, 120));

        centraliteTexteS8.setColumns(20);
        centraliteTexteS8.setRows(5);
        centraliteTexteS8.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS8.setViewportView(centraliteTexteS8);

        jPanel70.add(centraliteScrollS8, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 1060, 265, 120));

        centraliteTexteS10.setColumns(20);
        centraliteTexteS10.setRows(5);
        centraliteTexteS10.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS10.setViewportView(centraliteTexteS10);

        jPanel70.add(centraliteScrollS10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1450, 265, 120));

        centraliteTexteS9.setColumns(20);
        centraliteTexteS9.setRows(5);
        centraliteTexteS9.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS9.setViewportView(centraliteTexteS9);

        jPanel70.add(centraliteScrollS9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 1060, 265, 120));

        centraliteTexteS7.setColumns(20);
        centraliteTexteS7.setRows(5);
        centraliteTexteS7.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        centraliteScrollS7.setViewportView(centraliteTexteS7);

        jPanel70.add(centraliteScrollS7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1060, 265, 120));

        jPanel53.add(jPanel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 530, 860, 1580));

        jPanel52.add(jPanel53);

        jPanel8.add(jPanel52, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel8);

        resultatPersonnage.addTab("Interactions", jScrollPane1);

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
                .addContainerGap(1949, Short.MAX_VALUE))
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
                .addContainerGap(1799, Short.MAX_VALUE))
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
                .addContainerGap(1778, Short.MAX_VALUE))
        );

        Relation.addTab("Source", SourceRelation);

        SectionRecherche.addTab("Relation", Relation);

        getContentPane().add(SectionRecherche);
        SectionRecherche.getAccessibleContext().setAccessibleName("Recherche");
    }// </editor-fold>//GEN-END:initComponents

    private static final String TEXT_TENDANCE_JOEY = """
Saison 1 à 3 : Vie de famille compliqué, divorce et enfant proche, choix entre Rachel et Carol puis mise en relation avec Rachel, en saison 2 il a un 
                      arc plus dans le triangle amoureux Ross-Julie-Rachel, il est donc moins mentionné par le groupe
Saison 5 : remontée soudaine grâce notamment au rapprochement du groupe (voir Analyse Sentiment) et à son vrai début d'arc amoureux avec
                Chandler (observé dans les sentiments récurrents).
                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.
Saison 7 : Invisibilisé par le mariage de Chandler et Monica, Ross est mis en arrière dans l'intrigue
                au nombre de mentions qui nous permet d'observer un réel pic contrairement à leur taux de répliques qui n'a pas réellement varié)
Saison 9 : On apprend que Rachel est enceinte

Ross a moins de pic d'activité, c'est le personnage le plus centrale de la série d'après la recherche par saison, il est régulier et souvent présent 
avec nos 6 amis.""";
    private static final String TEXT_TENDANCE_RACHEL = """
Saison 1 : un des personnages impliqués en saison 1 pour l'introduction de la série

Saison 8 : augmentation évidente : la grossesse de Rachel

Impression globale : Rachel est régulière dans ses apparitions et est intégré depuis le début.""";
    private static final String TEXT_TENDANCE_ROSS = """
Saison 1 à 3 : Vie de famille compliqué, divorce et enfant proche, choix entre Rachel et Carol pour finalement finir avec avec Rachel en saison 3, 
                en saison 2 il se retrouve dans un triangle amoureux (Ross-Julie-Rachel), il est donc moins mentionné par le groupe mais actif
Saison 5 : remontée soudaine grâce notamment au rapprochement du groupe (voir Analyse Sentiment) et à son vrai début d'arc amoureux avec
                Chandler (observé dans les sentiments récurrents).
                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.
Saison 7 : Invisibilisé par le mariage de Chandler et Monica, Ross est mis en arrière dans l'intrigue
Saison 9 : On apprend que Rachel est enceinte, Ross s'implique plus dans la relation et l'intrigue met de nouveau en avant sur le couple Rachel-Ross

Ross a moins de pic d'activité, c'est le personnage le plus centrale de la série d'après la recherche par saison, il est régulier et souvent présent 
avec nos 6 amis.""";
    private static final String TEXT_TENDANCE_PHOEBE = """
Saison 1 à 3 : Vie de famille compliqué, divorce et enfant proche, choix entre Rachel et Carol puis mise en relation avec Rachel, en saison 2 il a un 
                      arc plus dans le triangle amoureux Ross-Julie-Rachel, il est donc moins mentionné par le groupe
Saison 5 : remontée soudaine grâce notamment au rapprochement du groupe (voir Analyse Sentiment) et à son vrai début d'arc amoureux avec
                Chandler (observé dans les sentiments récurrents).
                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.
Saison 7 : Invisibilisé par le mariage de Chandler et Monica, Ross est mis en arrière dans l'intrigue
                au nombre de mentions qui nous permet d'observer un réel pic contrairement à leur taux de répliques qui n'a pas réellement varié)
Saison 9 : On apprend que Rachel est enceinte

Ross a moins de pic d'activité, c'est le personnage le plus centrale de la série d'après la recherche par saison, il est régulier et souvent
présent avec nos 6 amis.""";
    private static final String TEXT_TENDANCE_MONICA = """
Saison 1 : Monica est le centre initial du groupe (soeur de Ross, meilleure amie de Rachel, ami de longue date de Joey et Chandler) et 
                le début de l'intrigue se passe dans son appartement. On découvre donc principalement la série avec elle.
Saison 5 : remontée soudaine grâce au rapprochement du groupe d'après l'Analyse Sentiment et au rapprochement avec Chandler (observé 
                dans les sentiments récurrents : montée d'amour en saison 5).
Saison 7 : Augmentation prévisible dû au mariage de Monica et Chandler, tout le monde parle de l'évènement (un pic énorme au niveau des
                mentions qui témoigne d'un réel impact de celles-ci, contrairement à leur taux de répliques qui n'a pas réellement varié)
Saison 9 : Après une saison 8 moins marquée (pause dans leur intrigue dû à un gros pic dans la saison 7 et à la grossesse de Ross et Rachel)
                En saison 9 on les revoit après avoir constater qu'ils ne peuvent pas avoir d'enfant, ils envisage l'adoption (Monica parle
                beaucoup mais peu de mentions : les discussions sont entre les deux)""";
    private static final String TEXT_TENDANCE_CHANDLER = """
Saison 7 et 8 : préparatif du mariage avec Monica puis mariage

Saison 9 et 10 : adoption et stabilité dans son métier, observation d'une baisse de négativité dans l'analyse de sentiments

Impression globale : Chandler est toujours relativement impliquant de manière constante""";
    private static final String[] TEXTS_CENTRALITE_JOEY = {
        "Ce début, naïf et drôle mais peu de répliques\n, ne montrent pas grand chose.\n Coloc de Chandler, pourtant peu de discussion", 
        "Développement énorme de l'amitié avec Chandler",
        "",
        "",
        "Intégration au sein du groupe, confirmé dans\nl'analyse sentiment qui évoque une stabilisation\ndu groupe et une positivité grandissante.",
        "",
        "Emménagement avec Rachel",
        "Baisse de discussion avec Chandler, dûe au mariage",
        "Mise en couple avec Rachel peu remarqué, \npeut vouloir montrer que leur relation\nest bel et bien platonique",
        "Emménagement chez Chandler"
    };
    private static final String[] TEXTS_CENTRALITE_RACHEL = {
        "test1 Rachel est ...", 
        "test2TEXTS_CENTRALITE_RACHEL",
        "test3TEXTS_CENTRALITE_RACHEL",
        "test4TEXTS_CENTRALITE_RACHEL",
        "test5TEXTS_CENTRALITE_RACHEL",
        "test6TEXTS_CENTRALITE_RACHEL",
        "test7TEXTS_CENTRALITE_RACHEL",
        "test8TEXTS_CENTRALITE_RACHEL",
        "test9TEXTS_CENTRALITE_RACHEL",
        "test10TEXTS_CENTRALITE_RACHEL"
    };
    private static final String[] TEXTS_CENTRALITE_ROSS = {
        "test1 Ross est ...", 
        "test2 Ross",
        "test3 Ross",
        "test4 Ross",
        "test5 Ross",
        "test6Ross ",
        "test7Ross",
        "test8Ross",
        "test9Ross",
        "test10Ross"
    };
    private static final String[] TEXTS_CENTRALITE_PHOEBE = {
        "test1 Phoebe est ...", 
        "test2P",
        "test3P",
        "test4P",
        "test5P",
        "test6P",
        "test7P",
        "test8P",
        "test9P",
        "test10P"
    };
    private static final String[] TEXTS_CENTRALITE_MONICA = {
        "test1 Monica est ...", 
        "test2Monica",
        "test3Monica",
        "test4Monica",
        "test5Monica",
        "test6Monica",
        "test7Monica",
        "test8Monica",
        "test9Monica",
        "test10Monica"
    };
    private static final String[] TEXTS_CENTRALITE_CHANDLER = {
        "test1 Chandler est ...", 
        "test2C",
        "test3C",
        "tesCt4",
        "tesCt5",
        "teCst6",
        "tesCt7",
        "tesCt8",
        "tesCCt9",
        "tesCt10"
    };
    
    //NOAH, Enzo LOUIS
    private void afficherInfosPersonnage(String nom) {
        String lower = nom.toLowerCase();

        switch (lower) {
            case "joey" -> setProfil(
                "Joey Tribbiani", "Matt LeBlanc", "Américain", "57 ans", "25 juillet 1967 (États-Unis)",
                "/les_png/Joey_photo_profile.png", TEXT_TENDANCE_JOEY, TEXTS_CENTRALITE_JOEY,
                8182, 16.25, 5, "hey, know, right, like, out"
            );
            case "rachel" -> setProfil(
                "Rachel Green", "Jennifer Aniston", "Américaine", "55 ans", "11 février 1969 (États-Unis)",
                "/les_png/Rachel_photo_profile.png", TEXT_TENDANCE_RACHEL, TEXTS_CENTRALITE_RACHEL,
                9118, 18.11, 1, "know, well, ross, right, gonna"
            );
            case "ross" -> setProfil(
                "Ross Geller", "David Schwimmer", "Américain", "57 ans", "2 novembre 1966 (États-Unis)",
                "/les_png/Ross_photo_profile.webp", TEXT_TENDANCE_ROSS, TEXTS_CENTRALITE_ROSS,
                9063, 18.01, 2, "know, hey, uh, well, right"
            );
            case "phoebe" -> setProfil(
                "Phoebe Buffay", "Lisa Kudrow", "Américaine", "61 ans", "30 juillet 1963 (États-Unis)",
                "/les_png/Phoebe_photo_profile.png", TEXT_TENDANCE_PHOEBE, TEXTS_CENTRALITE_PHOEBE,
                7345, 14.59, 6, "know, well, like, hey, right"
            );
            case "monica" -> setProfil(
                "Monica Geller", "Courteney Cox", "Américaine", "60 ans", "15 juin 1964 (États-Unis)",
                "/les_png/Monica_photo_profile.png", TEXT_TENDANCE_MONICA, TEXTS_CENTRALITE_MONICA,
                8278, 16.45, 4, "know, chandler, well, right, gonna"
            );
            case "chandler" -> setProfil(
                "Chandler Bing", "Matthew Perry", "Américain", "54 ans", "19 août 1969 – 28 octobre 2023",
                "/les_png/Chandler_photo_profile.png", TEXT_TENDANCE_CHANDLER, TEXTS_CENTRALITE_CHANDLER,
                8350, 16.59, 3, "well, know, right, hey, out"
            );
            default -> setProfil(
                "Personnage inconnu", "-", "-", "-", "-", "/les_png/default.png", "", new String[] {"","","","","","","","","",""},
                0, 0.0, 0, ""
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
        imageMotCaracteristique.setImage("/les_png/mots_caracteristiques_par_personnages/mots_caracteristique_" + lower + ".png");
        imageMotPref.setImage("/les_png/mots_preferes_par_personnage/mots_preferes_" + lower + ".png");
        miseEnAvantPersonnageImage.setImage("/les_png/recherche_personnage/graphe_tendance_mise_en_avant_" + lower + "_par_scenariste.png");
    }
    
    private void setProfil(String nomPerso, String acteur, String nationalite, String age,
                       String naissance, String imagePath, String remarquePicPopularite, String[] remarquesCentralite,int nbRepliques, double pourcentage, int position, String topMots) {
        nomPersonnage.setText(nomPerso);
        nomPersonnage2.setText("Relations de " + nomPerso);
        jLabel39.setText(acteur);
        jLabel40.setText(nationalite);
        jLabel41.setText(age);
        jLabel42.setText(naissance);
        imagePersonnage.setImage(imagePath);
        jLabel43.setText("" + nbRepliques);
        jLabel44.setText(String.format("%.2f %%", pourcentage));
        jLabel45.setText(position + " / 6");
        jLabel46.setText(topMots);

        
        miseEnAvantPersonnageTexte.setText(remarquePicPopularite);
        int index = 0;
        for (JTextArea area : new JTextArea[] {centraliteTexteS1, centraliteTexteS2, centraliteTexteS3,centraliteTexteS4, 
            centraliteTexteS5, centraliteTexteS6, centraliteTexteS7, centraliteTexteS8, centraliteTexteS9, centraliteTexteS10}) {
            area.setText(remarquesCentralite[index]);
            index++;
        }
    }
    //NOAH FIN
    
    
    
    
    private void rechercheMotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechercheMotKeyPressed
        if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            rechercheMotActionPerformed(null);
        }
    }//GEN-LAST:event_rechercheMotKeyPressed

    private void rechercheMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheMotActionPerformed
        if (dialog != null) {
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

            setGraphEpisodeRepartition();
        } catch (Exception e) {
            jLabel73.setText("Erreur: " + e.getMessage());
        }
        /*
        try {
            Object saisonObj = rechercheSaison.getSelectedItem();
            Object episodeObj = rechercheEpisode.getSelectedItem();
            
            //Extraction du numéro de saison (format "S01")
            String saisonStr = saisonObj.toString();
            int saison = Integer.parseInt(saisonStr.substring(1));
            
            //Extraction du numéro d'épisode (format "S01E02 Titre")
            String episodeStr = episodeObj.toString();
            int episode = Integer.parseInt(episodeStr.substring(episodeStr.indexOf('E') + 1, 
                                                              episodeStr.indexOf(' ')));
            
            labelEpisode.setText("Episode " + episode + " de la saison " + saison + " : " + episodeStr.substring(6));
            labelEpisode2.setText("Episode " + episode + " de la saison " + saison + " : " + episodeStr.substring(6));
            labelEpisode3.setText("Episode " + episode + " de la saison " + saison + " : " + episodeStr.substring(6));
            
            //Appel à la méthode du contrôleur
            int nbRepliques = dialog.getNombreRepliquesEpisode(saison, episode);
            
            //Personnages impliqués
            String listeReconstruite = "";
            List<String> personnages = dialog.getPersonnagesEpisode(saison, episode);
            for(String personnage : personnages){
                listeReconstruite += personnage;
                listeReconstruite += ", ";
            }
            listeReconstruite = listeReconstruite.substring(0, listeReconstruite.length() - 2);
            
            //top 10 mots (cette partie met plusieurs secondes à s'exécuter... à réfléchir)
            String top10Mots = "";
            List<String> mots = dialog.getTopMotsEpisode(saison, episode);
            for(String mot : mots){
                top10Mots += mot;
                top10Mots += ", ";
            }
            top10Mots = top10Mots.substring(0, top10Mots.length() - 2);
                
            //Affichage du résultat
            jLabel73.setText("" + nbRepliques);
            jLabel75.setText(listeReconstruite);
            jLabel79.setText(top10Mots);
            setGraphEpisodeRepartition();
        } catch (Exception e) {
            jLabel73.setText("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
        */
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

        jLabel73.setText(String.valueOf(nbRepliques));
        jLabel75.setText(String.join(", ", personnages));
        jLabel79.setText(String.join(", ", mots));
    }

    
    private void rechercheSaisonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        /*
        if (!rechercheEpisode.isVisible()) {
            try {
                int saison = extraireNumeroSaison();
                afficherTitresSaison(saison);
                afficherInfosSaison(saison);
                setGraphSaisonRepartition();
            } catch (Exception e) {
                jLabel106.setText("Erreur: " + e.getMessage());
            }
        }
        */
        
        if(!rechercheEpisode.isVisible()){
            try {
                Object saisonObj = rechercheSaison.getSelectedItem();

                //Extraction du numéro de saison (format "S01")
                String saisonStr = saisonObj.toString();
                int saison = Integer.parseInt(saisonStr.substring(1));
                
                labelSaison.setText("Saison " + saison);
                labelSaison2.setText("Saison " + saison);
                labelSaison3.setText("Saison " + saison);
                
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
                jLabel106.setText("" + nbRepliques);
                jLabel108.setText(listePersonnagesImpliques);
                jLabel112.setText(top10Mots);
                setGraphSaisonRepartition();
            } catch (Exception e) {
                jLabel106.setText("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
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

        jLabel106.setText(String.valueOf(nbRepliques));
        jLabel108.setText(joinAvecVirgules(personnages));
        jLabel112.setText(joinAvecVirgules(mots));
    }

    private String joinAvecVirgules(List<String> liste) {
        return String.join(", ", liste);
    }
    private void jPanel85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel85MouseClicked
        
    }//GEN-LAST:event_jPanel85MouseClicked
    private void setGraphSaisonRepartition(){
        Object saisonObj = rechercheSaison.getSelectedItem();
        String saisonStr = saisonObj.toString();
        int saison = Integer.parseInt(saisonStr.substring(1));

        //Chemin vers l'image png creee
        String cheminImage = "src/main/resources/les_png/repliques_saison_" + saisonStr + ".png";

        //Verifie si l'image existe déjà
        File imageFile = new File(cheminImage);
        if (!imageFile.exists()) {
            // Crée le graphique uniquement si le fichier n'existe pas
            dialog.creerGrapheNombreDeRepliquesSaison(saison);
        }

        //Convertit le chemin 
        String cheminPourRessource = "src/main/resources/les_png/repliques_saison_" + saisonStr + ".png";
        System.out.println(cheminPourRessource);
        //Crée et configure le panel image
        controller.ImagePanel imagePanel = new controller.ImagePanel();
        imagePanel.setImageV2(cheminPourRessource);

        //Rafraîchit le panel contenant l’image
        jPanel85.removeAll();
        jPanel85.add(imagePanel);
        jPanel85.revalidate();
        jPanel85.repaint();
    }
    
   private void setGraphEpisodeRepartition() {
        //Récupère l'objet sélectionné dans le combo box de recherche de saison et épisode
        Object saisonObj = rechercheSaison.getSelectedItem();
        Object episodeObj = rechercheEpisode.getSelectedItem();

        //Extraction du numéro de saison (format "S01")
        String saisonStr = saisonObj.toString();
        int saison = Integer.parseInt(saisonStr.substring(1)); // Extrait 1 depuis "S01"

        //Extraction du code épisode (format "S01E02 Titre") → récupère "E02"
        String episodeStrFull = episodeObj.toString();
        String episodeCode = episodeStrFull.substring(episodeStrFull.indexOf('E'), episodeStrFull.indexOf(' ')); // "E02"
        int episode = Integer.parseInt(episodeCode.substring(1)); // Extrait 2 depuis "E02"

        //Construction du nom de fichier correct
        String nomFichier = "repliques_saison_" + saisonStr + "_episode_" + episodeCode + ".png";
        String cheminImage = "src/main/resources/les_png/" + nomFichier;

        //Vérifie si l'image existe déjà
        File imageFile = new File(cheminImage);
        if (!imageFile.exists()) {
            // Crée le graphique uniquement si le fichier n'existe pas
            dialog.creerGrapheNombreDeRepliquesEpisode(saison, episode);
        }

        //Convertit le chemin pour le classpath
        String cheminPourRessource = "src/main/resources/les_png/" + nomFichier;

        //Charge et affiche l’image
        controller.ImagePanel imagePanel = new controller.ImagePanel();
        imagePanel.setImageV2(cheminPourRessource);

        jPanel65.removeAll();
        jPanel65.add(imagePanel);
        jPanel65.revalidate();
        jPanel65.repaint();
    }
    
    private void resultatSaisonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultatSaisonMouseClicked

        //Récupère l'objet sélectionné dans le combo box de recherche de saison 
        Object saisonObj = rechercheSaison.getSelectedItem();

        //Extraction du numéro de saison (format "S01")
        String saisonStr = saisonObj.toString();
        int saison = Integer.parseInt(saisonStr.substring(1)); // Extrait 1 depuis "S01"

       

        String cheminImage = "src/main/resources/les_png/repliques_saison_" + saisonStr + ".png";

        //Vérifie si l'image existe dans le système de fichiers (moins long)
        File imageFile = new File(cheminImage);
        if (!imageFile.exists()) {
            //cree le graphique uniquement si le fichier n'existe pas
            dialog.creerGrapheNombreDeRepliquesSaison(saison);
        }

        //Chemin pour accéder à la ressource 
        String cheminPourRessource = "/les_png/repliques_saison_" + saisonStr + ".png";

        //Crée et configure le panel image
        controller.ImagePanel imagePanel = new controller.ImagePanel();
        imagePanel.setImage(cheminPourRessource);

        //Rafraichit le panel
        jPanel85.removeAll();
        jPanel85.add(imagePanel);
        jPanel85.revalidate();
        jPanel85.repaint();

    }//GEN-LAST:event_resultatSaisonMouseClicked

    private void rechercheSaisonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rechercheSaisonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheSaisonMouseClicked

    private void panelSaisonRepartitionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSaisonRepartitionMouseClicked
        
    }//GEN-LAST:event_panelSaisonRepartitionMouseClicked

    private void resultatEpisodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultatEpisodeMouseClicked
        //Récupère l'objet sélectionné dans le combo box de recherche de saison et épisode
        Object saisonObj = rechercheSaison.getSelectedItem();
        Object episodeObj = rechercheEpisode.getSelectedItem();

        //Extraction du numéro de saison (format "S01")
        String saisonStr = saisonObj.toString();
        int saison = Integer.parseInt(saisonStr.substring(1)); // Extrait 1 depuis "S01"

        //Extraction du code épisode (format "S01E02 Titre") → récupère "E02"
        String episodeStrFull = episodeObj.toString();
        String episodeCode = episodeStrFull.substring(episodeStrFull.indexOf('E'), episodeStrFull.indexOf(' ')); // "E02"
        int episode = Integer.parseInt(episodeCode.substring(1)); // Extrait 2 depuis "E02"

  
        String cheminImage = "src/main/resources/les_png/repliques_saison_" + saisonStr + "_episode_" + episodeCode + ".png";

        //Vérifie si l'image existe dans le système de fichiers (moins long)
        File imageFile = new File(cheminImage);
        if (!imageFile.exists()) {
            //cree le graphique uniquement si le fichier n'existe pas
            dialog.creerGrapheNombreDeRepliquesEpisode(saison, episode);
        }

        //Chemin pour accéder à la ressource 
        String cheminPourRessource = "/les_png/repliques_saison_" + saisonStr + "_episode_" + episodeCode + ".png";

        //Crée et configure le panel image
        controller.ImagePanel imagePanel = new controller.ImagePanel();
        imagePanel.setImage(cheminPourRessource);

        //Rafraichit le panel
        jPanel65.removeAll();
        jPanel65.add(imagePanel);
        jPanel65.revalidate();
        jPanel65.repaint();
        
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

    private void rechercheMotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheMotButtonActionPerformed
        if (dialog != null) {
            dialog.onUserAction(rechercheMot.getText());
        }
    }//GEN-LAST:event_rechercheMotButtonActionPerformed

    private void comboBoxSaisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSaisonActionPerformed
        JComboBox<String> combo = (JComboBox<String>) evt.getSource();
        generateGraphLineMotParEpisode(dataStoredForEpisodeStats, combo.getSelectedItem().toString());
    }//GEN-LAST:event_comboBoxSaisonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SectionRecherche.setSelectedComponent(Analyse_Statistique);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SectionRecherche.setSelectedComponent(Analyse_Sentiment);
        Analyse_Sentiment.setSelectedComponent(Sentiment_exprime);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    
    

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
    private controller.ImagePanel imageMotPref;
    private controller.ImagePanel imageMotsCaracteristiques;
    private controller.ImagePanel imagePersonnage;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
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
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
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
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelDetailReplique;
    private javax.swing.JLabel labelEpisode;
    private javax.swing.JLabel labelEpisode2;
    private javax.swing.JLabel labelEpisode3;
    private javax.swing.JLabel labelMotCourant1;
    private javax.swing.JLabel labelMotCourant2;
    private javax.swing.JLabel labelMotCourant3;
    private javax.swing.JLabel labelPersonnageReplique;
    private javax.swing.JLabel labelSaison;
    private javax.swing.JLabel labelSaison2;
    private javax.swing.JLabel labelSaison3;
    private javax.swing.JLabel labelSansRechercheExemple;
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
    private javax.swing.JLabel nomPersonnage2;
    private javax.swing.JPanel panelDetailReplique;
    private javax.swing.JPanel panelEpisodeDialogue;
    private javax.swing.JPanel panelEpisodePresentation;
    private javax.swing.JPanel panelEpisodeRepartition;
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
    private javax.swing.JPanel panelSaisonDialogue;
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
    private javax.swing.JScrollPane scrollPaneDetailReplique;
    private controller.ImagePanel sentiment_par_personnage_panel;
    private javax.swing.JPanel statsperso;
    private javax.swing.JTable tableDetailReplique;
    private javax.swing.JLabel titre;
    // End of variables declaration//GEN-END:variables

}

