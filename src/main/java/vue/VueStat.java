package vue;

import java.awt.event.KeyEvent;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import controller.*;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.DefaultComboBoxModel;

public class VueStat extends javax.swing.JFrame {
    private ControllerDialogRecherche dialog;
    
    public void setDialog(ControllerDialogRecherche dialog) {
        this.dialog = dialog;
    }

    public VueStat(ControllerDialogRecherche dialog) {
        this.dialog = dialog;
        initComponents();
        
        // -------------------------------------------------------------
        // -               SECTION ANALYSE SENTIMENT                           -
        // -------------------------------------------------------------
        
        //tab 1 Evolution de la positivité
            Evol_pos_image.setImage("/les_png/Evolution_positivité.png");
            TexteEvolpos.setCaretPosition(0);
        //tab 2 Evolution de la négativité
            Evol_neg_image.setImage("/les_png/Evolution_negativité.png");
            TexteEvolneg.setCaretPosition(0);
        //tab 3 Sentiment_exprimé
            Sentiment_exprime.getVerticalScrollBar().setUnitIncrement(16);
            Sentiment_exprimé_chandler.setImage("/les_png/Sentiment_exprimé_Chandler_V2.png");
            Sentiment_exprimé_ross.setImage("/les_png/Sentiment_exprimé_Ross_V2.png");
            Sentiment_exprimé_joey.setImage("/les_png/Sentiment_exprimé_Joey_V2.png");
            Sentiment_exprimé_monica.setImage("/les_png/Sentiment_exprimé_Monica_V2.png");
            Sentiment_exprimé_rachel.setImage("/les_png/Sentiment_exprimé_Rachel_V2.png");
            Sentiment_exprimé_phoebe.setImage("/les_png/Sentiment_exprimé_Phoebe_V2.png");
            TexteSentimentexprime.setCaretPosition(0);
        //tab 4 Evolution des sentiments
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
        //tab 5 Source
            sentiment_par_personnage_panel.setImage("/les_png/sentiment_par_personnage.png");
            neg_nuage.setImage("/les_png/negative_wordcloud.png");
            pos_nuage.setImage("/les_png/positive_wordcloud.png");
            
        // -------------------------------------------------------------
        // -               SECTION RELATION                           -
        // -------------------------------------------------------------
        //tab1 Carte des relations
        Carte_Relation.setImage("/les_png/Relation_Cartes.png");
        //tab2 Graphique des relations
        Graphique_Relation.getVerticalScrollBar().setUnitIncrement(16);
        Relation_Chandler.setImage("/les_png/Relation_Chandler.png");
        Relation_Monica.setImage("/les_png/Relation_Monica.png");
        Relation_Ross.setImage("/les_png/Relation_Ross.png");
        Relation_Rachel.setImage("/les_png/Relation_Rachel.png");
        Relation_Joey.setImage("/les_png/Relation_Joey.png");
        Relation_Phoebe.setImage("/les_png/Relation_Phoebe.png");
        //tab 3 SourceRelation
        Relation_Mention.setImage("/les_png/Relation_Mention.png");
        
        // -------------------------------------------------------------
        // -               SECTION ANALYSE LANGAGIERE                           -
        // -------------------------------------------------------------
        AnalyseLangagière.setImage("/les_png/Analyselangagière.png");
        
        // -------------------------------------------------------------
        // -               SECTION RECHERCHE                           -
        // -------------------------------------------------------------
        imageMotsCaracteristiques.setImage("/les_png/mots_caractéristiques.png");
        
        imagePersonnage.setImage("/les_png/Joey_Test.png");
        
        recherchePersonnage.setVisible(false);
        rechercheSaison.setVisible(false);
        rechercheEpisode.setVisible(false);
        
        rechercheMot.setColumns(10);
        rechercheMot.setBorder(new javax.swing.border.EmptyBorder(4, 4, 4, 4));
        rechercheMot.setPlaceholder("Recherche de mots");
        
        choixTypeRecherche.setModel(dialog.getComboBoxModelTypeRecherche());
        choixTypeRecherche.addActionListener((evt) -> {
            javax.swing.JComboBox combobox = (javax.swing.JComboBox) evt.getSource();
            String result = (String) combobox.getSelectedItem();
            
            // Masquer tous les panneaux d'abord
            recherchePersonnage.setVisible(false);
            rechercheSaison.setVisible(false);
            rechercheEpisode.setVisible(false);
            rechercheMot.setVisible(false);

            String cardName = ""; // Nom du panneau à afficher

            switch (result) {
                case "Recherche de mots" -> {
                    rechercheMot.setVisible(true);
                    cardName = "MOT";
                }
                case "Recherche de personnages" -> {
                    recherchePersonnage.setVisible(true);
                    cardName = "PERSONNAGE";
                    String personnageParDefaut = (String) recherchePersonnage.getSelectedItem();
                    afficherInfosPersonnage(personnageParDefaut);
                }
                case "Recherche de saisons" -> {
                    rechercheSaison.setVisible(true);
                    cardName = "SAISON";
                }
                case "Recherche d'épisodes" -> {
                    rechercheSaison.setVisible(true);
                    rechercheEpisode.setVisible(true);
                    cardName = "EPISODE";
                }
            }

            ((java.awt.CardLayout) resultats.getLayout()).show(resultats, cardName);
        });
        
        recherchePersonnage.addActionListener((evt) -> {
            String personnage = (String) recherchePersonnage.getSelectedItem();
            afficherInfosPersonnage(personnage);
        });
        
        recherchePersonnage.setModel(dialog.getComboBoxModelPersonnage());
        //rechercheSaison.setModel(dialog.getComboBoxModelSaison());
        //rechercheEpisode.setModel(dialog.getComboBoxModelEpisode());
        
        ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "SANSRECHERCHE");
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(11, "Tendance du mot", "1");
        dataset.addValue(5, "Tendance du mot", "2");
        dataset.addValue(7, "Tendance du mot", "3");
        dataset.addValue(8, "Tendance du mot", "4");
        dataset.addValue(3, "Tendance du mot", "5");
        dataset.addValue(12, "Tendance du mot", "6");
        dataset.addValue(12, "Tendance du mot", "7");
        dataset.addValue(12, "Tendance du mot", "8");
        dataset.addValue(12, "Tendance du mot", "9");
        dataset.addValue(12, "Tendance du mot", "10");
        dataset.addValue(11, "Tendance du mot", "11");
        dataset.addValue(5, "Tendance du mot", "12");
        dataset.addValue(7, "Tendance du mot", "13");
        dataset.addValue(8, "Tendance du mot", "14");
        dataset.addValue(3, "Tendance du mot", "15");
        dataset.addValue(12, "Tendance du mot", "16");
        dataset.addValue(12, "Tendance du mot", "17");
        dataset.addValue(12, "Tendance du mot", "18");
        dataset.addValue(12, "Tendance du mot", "19");
        dataset.addValue(12, "Tendance du mot", "20");
        dataset.addValue(12, "Tendance du mot", "21");
        dataset.addValue(12, "Tendance du mot", "22");
        dataset.addValue(12, "Tendance du mot", "23");
        dataset.addValue(12, "Tendance du mot", "24");
        dataset.addValue(12, "Tendance du mot", "25");

        JFreeChart chart = ChartFactory.createLineChart(
                "Tendance d'utilisation du mot",
                "Episode",
                "Utilisation du mot",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        jPanel63.add(chartPanel, java.awt.BorderLayout.CENTER);
        
        
        dataset = new DefaultCategoryDataset();
        dataset.addValue(11, "Tendance du mot", "1");
        dataset.addValue(20, "Tendance du mot", "2");
        dataset.addValue(7, "Tendance du mot", "3");
        dataset.addValue(8, "Tendance du mot", "4");
        dataset.addValue(3, "Tendance du mot", "5");
        dataset.addValue(12, "Tendance du mot", "6");

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
        jPanel39.add(chartPanel2, java.awt.BorderLayout.CENTER);
        
        //jProgressBar1.setVisible(false);
        
        rechercheSaison.setModel(new DefaultComboBoxModel<>(
            dialog.getSaisons()
            .stream()
            .map(s -> "S" + String.format("%02d", s.getNumeroSaison()))
            .toArray(String[]::new)
        ));
        
        rechercheEpisode.setModel(new DefaultComboBoxModel<>(
            dialog
            .getEpisodesSaison(1) // 1 pour S01
            .stream()
            .map(e -> String.format("S%02dE%02d - %s", 
                e.getNumeroSaison(), 
                e.getNumeroEpisode(), 
                e.getTitre()))
            .filter(episodeStr -> episodeStr.startsWith("S01")) // <- valeur fixe
            .toArray(String[]::new)
        ));
    }
    
    public void showLoading(boolean loading) {
        //jProgressBar1.setIndeterminate(loading);
        //jProgressBar1.setVisible(loading);
        // btn set 
    }
    
    public void refresh() {
        System.out.println("refreshing");
    }
    
    public javax.swing.JLabel getJLabel2() {
        return jLabel2;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        SectionRecherche = new javax.swing.JTabbedPane();
        panelPageRecherche = new javax.swing.JPanel();
        panelRechercheEtIndication = new javax.swing.JPanel();
        panelRecherche = new javax.swing.JPanel();
        panelRechercheTitre = new javax.swing.JPanel();
        titre = new javax.swing.JLabel();
        panelRechercheButtons = new javax.swing.JPanel();
        choixTypeRecherche = new javax.swing.JComboBox<>();
        rechercheMot = new style.CustomJTextField();
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
        resultatPersonnage = new javax.swing.JTabbedPane();
        panelPersonnageProfil = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        nomPersonnage = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        imagePersonnage = new controller.ImagePanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        panelPersonnageRepliqueFavorite = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        panelPersonnageInteractions = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        nomPersonnage1 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        resultatMot = new javax.swing.JTabbedPane();
        panelMotUtilisation = new javax.swing.JPanel();
        panelMotCourant1 = new javax.swing.JPanel();
        labelMotCourant1 = new javax.swing.JLabel();
        panelResultatMotUtilisation = new javax.swing.JPanel();
        panelPersonnageReplique = new javax.swing.JPanel();
        labelPersonnageReplique = new javax.swing.JLabel();
        valeurPersonnageReplique = new javax.swing.JLabel();
        panelDetailReplique = new javax.swing.JPanel();
        labelDetailReplique = new javax.swing.JLabel();
        scrollPaneDetailReplique = new javax.swing.JScrollPane();
        tableDetailReplique = new javax.swing.JTable();
        panelMotRepartitionSerie = new javax.swing.JPanel();
        panelMotCourant2 = new javax.swing.JPanel();
        labelMotCourant2 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
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
        ScrollCarte = new javax.swing.JScrollPane();
        TexteCarte = new javax.swing.JTextArea();
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
        SourceRelation = new javax.swing.JPanel();
        Relation_Mention = new controller.ImagePanel();
        jLabel16 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(837, 626));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Interface Friends");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 20);

        SectionRecherche.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SectionRecherche.setName(""); // NOI18N

        panelPageRecherche.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPageRecherche.setLayout(new java.awt.BorderLayout());

        panelRechercheEtIndication.setLayout(new java.awt.BorderLayout());

        panelRecherche.setLayout(new javax.swing.BoxLayout(panelRecherche, javax.swing.BoxLayout.Y_AXIS));

        titre.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        titre.setText("Recherche");
        titre.setToolTipText("");
        titre.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelRechercheTitre.add(titre);

        panelRecherche.add(panelRechercheTitre);

        choixTypeRecherche.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        choixTypeRecherche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelRechercheButtons.add(choixTypeRecherche);

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

        recherchePersonnage.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        recherchePersonnage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recherchePersonnage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                recherchePersonnageItemStateChanged(evt);
            }
        });
        panelRechercheButtons.add(recherchePersonnage);

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
            .addGap(0, 732, Short.MAX_VALUE)
        );
        imageMotsCaracteristiquesLayout.setVerticalGroup(
            imageMotsCaracteristiquesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );

        resultSansRecherche.add(imageMotsCaracteristiques);

        resultats.add(resultSansRecherche, "SANSRECHERCHE");

        panelPersonnageProfil.setLayout(new java.awt.BorderLayout());

        nomPersonnage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage.setText("Joey Tribbiani");
        nomPersonnage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(nomPersonnage);

        panelPersonnageProfil.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout imagePersonnageLayout = new javax.swing.GroupLayout(imagePersonnage);
        imagePersonnage.setLayout(imagePersonnageLayout);
        imagePersonnageLayout.setHorizontalGroup(
            imagePersonnageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        imagePersonnageLayout.setVerticalGroup(
            imagePersonnageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );

        jPanel5.add(imagePersonnage);
        jPanel5.add(filler1);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.Y_AXIS));

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.Y_AXIS));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Acteur:");
        jPanel18.add(jLabel5);

        jLabel39.setText("Matt Leblanc");
        jLabel39.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel18.add(jLabel39);

        jPanel17.add(jPanel18);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.Y_AXIS));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(153, 153, 153));
        jLabel35.setText("Nationalité:");
        jPanel19.add(jLabel35);

        jLabel40.setText("Américain");
        jLabel40.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel19.add(jLabel40);

        jPanel17.add(jPanel19);

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(153, 153, 153));
        jLabel37.setText("Age:");
        jPanel20.add(jLabel37);

        jLabel41.setText("57 ans");
        jLabel41.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel20.add(jLabel41);

        jPanel17.add(jPanel20);

        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.Y_AXIS));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(153, 153, 153));
        jLabel38.setText("Date de naissance:");
        jPanel21.add(jLabel38);

        jLabel42.setText("25 juillet 1987 (Etats-Unis)");
        jLabel42.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel21.add(jLabel42);

        jPanel17.add(jPanel21);

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.Y_AXIS));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(153, 153, 153));
        jLabel43.setText("Centre d'intêrets et mots caractéristiques:");
        jPanel22.add(jLabel43);

        jLabel44.setText("audition, actor, movie, doin, tribbiani, fridge, worry, scene, heyhey, ball");
        jLabel44.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel22.add(jLabel44);

        jPanel17.add(jPanel22);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.Y_AXIS));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(153, 153, 153));
        jLabel45.setText("Mots fréquents:");
        jPanel23.add(jLabel45);

        jLabel46.setText("guy, time, listen, starts, entering, ya, gotta, wanna, pheebs, rach");
        jLabel46.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel23.add(jLabel46);

        jPanel17.add(jPanel23);

        jPanel5.add(jPanel17);

        panelPersonnageProfil.add(jPanel5, java.awt.BorderLayout.CENTER);

        resultatPersonnage.addTab("Profil", panelPersonnageProfil);

        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.Y_AXIS));

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(153, 153, 153));
        jLabel71.setText("Réplique favorite:");
        jPanel48.add(jLabel71);

        jLabel72.setText("<liaison avec partie analyse langagière + lien vers celle-ci : voir <ici> pour les autres répliques>");
        jLabel72.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel48.add(jLabel72);

        panelPersonnageRepliqueFavorite.add(jPanel48);

        resultatPersonnage.addTab("Réplique favorite", panelPersonnageRepliqueFavorite);

        panelPersonnageInteractions.setLayout(new java.awt.BorderLayout());

        nomPersonnage1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage1.setText("Relation de Joey");
        nomPersonnage1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel24.add(nomPersonnage1);

        panelPersonnageInteractions.add(jPanel24, java.awt.BorderLayout.NORTH);

        jPanel25.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));
        jPanel25.add(filler2);

        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.Y_AXIS));

        jPanel27.setLayout(new javax.swing.BoxLayout(jPanel27, javax.swing.BoxLayout.Y_AXIS));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(153, 153, 153));
        jLabel36.setText("Joey interagit le plus avec :");
        jPanel27.add(jLabel36);

        jLabel47.setText("Joey a interagit X fois avec Chandler");
        jLabel47.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel27.add(jLabel47);

        jPanel26.add(jPanel27);

        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.Y_AXIS));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(153, 153, 153));
        jLabel48.setText("Fréquence de mots (quantité et taux):");
        jPanel28.add(jLabel48);

        jLabel49.setText("...");
        jLabel49.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel28.add(jLabel49);

        jPanel26.add(jPanel28);

        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.Y_AXIS));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(153, 153, 153));
        jLabel50.setText("Temps de parole par saison (nb réplique et nb mots) (+ taux) + courbe tendance de parole:");
        jPanel29.add(jLabel50);

        jLabel51.setText("...");
        jLabel51.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel29.add(jLabel51);

        jPanel26.add(jPanel29);

        jPanel30.setLayout(new javax.swing.BoxLayout(jPanel30, javax.swing.BoxLayout.Y_AXIS));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(153, 153, 153));
        jLabel52.setText("Taux de présence dans la scène:");
        jPanel30.add(jLabel52);

        jLabel53.setText("...");
        jLabel53.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel30.add(jLabel53);

        jPanel26.add(jPanel30);

        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.Y_AXIS));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(153, 153, 153));
        jLabel54.setText("Fréquence de répliques (quantité et taux):");
        jPanel31.add(jLabel54);

        jLabel55.setText("...");
        jLabel55.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel31.add(jLabel55);

        jPanel26.add(jPanel31);

        jPanel25.add(jPanel26);

        panelPersonnageInteractions.add(jPanel25, java.awt.BorderLayout.CENTER);

        resultatPersonnage.addTab("Interactions", panelPersonnageInteractions);

        resultats.add(resultatPersonnage, "PERSONNAGE");
        resultatPersonnage.getAccessibleContext().setAccessibleName("tab");

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
        labelPersonnageReplique.setText("Personnage ayant le plus utilisé cette réplique :");
        panelPersonnageReplique.add(labelPersonnageReplique);

        valeurPersonnageReplique.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        valeurPersonnageReplique.setText("Chandler (57 occurences)");
        valeurPersonnageReplique.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelPersonnageReplique.add(valeurPersonnageReplique);

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
        tableDetailReplique.setShowGrid(true);
        tableDetailReplique.setShowVerticalLines(false);
        scrollPaneDetailReplique.setViewportView(tableDetailReplique);

        panelDetailReplique.add(scrollPaneDetailReplique, java.awt.BorderLayout.CENTER);

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

        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.Y_AXIS));

        jLabel58.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(153, 153, 153));
        jLabel58.setText("Taux d'utilisation globale :");
        jPanel37.add(jLabel58);

        jLabel59.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel59.setText("Le mot \"Hello\" a été employé 310 fois, 1% de tous les mots de la série");
        jLabel59.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel37.add(jLabel59);

        jPanel1.add(jPanel37);

        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.Y_AXIS));

        jLabel60.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(153, 153, 153));
        jLabel60.setText("Taux d'utilisation par réplique :");
        jPanel38.add(jLabel60);

        jLabel61.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel61.setText("Le mot \"Hello\" se retrouve dans 298 répliques différentes, soit dans 1% des répliques de la série");
        jLabel61.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel38.add(jLabel61);

        jPanel1.add(jPanel38);

        jPanel57.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.Y_AXIS));

        jLabel82.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(153, 153, 153));
        jLabel82.setText("Meilleur saison :");
        jPanel57.add(jLabel82);

        jLabel83.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel83.setText("Le mot \"Hello\" a été employé 51 fois en saison 1 (15% de toutes ses utilisations) (voir graphique ci-dessous)");
        jLabel83.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel57.add(jLabel83);

        jPanel1.add(jPanel57);

        jPanel36.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel63.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(153, 153, 153));
        jLabel63.setText("Tendance d'utilisation à travers les saison :");
        jPanel39.add(jLabel63, java.awt.BorderLayout.NORTH);

        jLabel64.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel64.setText("* Les mots prononcés en même temps par plusieurs personnage sont comptés une seule fois");
        jLabel64.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel39.add(jLabel64, java.awt.BorderLayout.SOUTH);

        jPanel36.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel36);

        panelMotRepartitionSerie.add(jPanel35, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Statistiques globales", panelMotRepartitionSerie);

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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(jComboBox1);

        jPanel9.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel63.add(jPanel9, java.awt.BorderLayout.NORTH);

        jPanel56.add(jPanel63, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel61.setLayout(new javax.swing.BoxLayout(jPanel61, javax.swing.BoxLayout.Y_AXIS));

        jLabel90.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(153, 153, 153));
        jLabel90.setText("Utilisation pour un certain épisode:");
        jPanel61.add(jLabel90);

        jLabel91.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel91.setText("Sélectionnez l'épisode <combobox>");
        jLabel91.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel61.add(jLabel91);

        jPanel2.add(jPanel61);

        jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.Y_AXIS));

        jLabel84.setFont(new java.awt.Font("Verdana", 1, 9)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(153, 153, 153));
        jLabel84.setText("Meilleur épisode :");
        jPanel58.add(jLabel84);

        jLabel85.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel85.setText("Le mot \"Hello\" a été employé 11 fois dans l'épisode 2 de la saison 5, 2% de son utilisation totale");
        jLabel85.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel58.add(jLabel85);

        jPanel2.add(jPanel58);

        jPanel56.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel55.add(jPanel56);

        panelMotRepartitionSaisonEtEpisode.add(jPanel55, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Analyse par épisode", panelMotRepartitionSaisonEtEpisode);

        resultats.add(resultatMot, "MOT");

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
                .addContainerGap(203, Short.MAX_VALUE))
        );
        Analyse_StatistiqueLayout.setVerticalGroup(
            Analyse_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Analyse_StatistiqueLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(AnalyseLangagière, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(206, Short.MAX_VALUE))
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
            .addGap(0, 358, Short.MAX_VALUE)
        );

        Evolution_positivite.add(Evol_pos_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 770, 360));

        TexteEvolpos.setColumns(20);
        TexteEvolpos.setRows(5);
        TexteEvolpos.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. \n                Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions.\n                Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : Regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers \n                l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, \n                plus qu'à une logique progressive.");
        ScrollEvolpos.setViewportView(TexteEvolpos);

        Evolution_positivite.add(ScrollEvolpos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 770, 120));

        Analyse_Sentiment.addTab("Evolution de la positivité", Evolution_positivite);

        Evolution_negativité.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Evol_neg_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_neg_imageLayout = new javax.swing.GroupLayout(Evol_neg_image);
        Evol_neg_image.setLayout(Evol_neg_imageLayout);
        Evol_neg_imageLayout.setHorizontalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
        );
        Evol_neg_imageLayout.setVerticalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );

        Evolution_negativité.add(Evol_neg_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 750, 350));

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
                .addGap(0, 23, Short.MAX_VALUE)
                .addGroup(SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(neg_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(pos_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SourceLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sentiment_par_personnage_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
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
                .addContainerGap(56, Short.MAX_VALUE))
        );

        Analyse_Sentiment.addTab("Source", Source);

        SectionRecherche.addTab("Analyse de Sentiment", Analyse_Sentiment);

        Carte_Relation_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Carte_Relation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Carte_RelationLayout = new javax.swing.GroupLayout(Carte_Relation);
        Carte_Relation.setLayout(Carte_RelationLayout);
        Carte_RelationLayout.setHorizontalGroup(
            Carte_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );
        Carte_RelationLayout.setVerticalGroup(
            Carte_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );

        Carte_Relation_Panel.add(Carte_Relation, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 770, 360));

        TexteCarte.setColumns(20);
        TexteCarte.setRows(5);
        ScrollCarte.setViewportView(TexteCarte);

        Carte_Relation_Panel.add(ScrollCarte, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 770, 120));

        Relation.addTab("Carte des Relations", Carte_Relation_Panel);

        Graphique_Relation_panel.setMinimumSize(new java.awt.Dimension(808, 895));
        Graphique_Relation_panel.setPreferredSize(new java.awt.Dimension(808, 895));
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

        Graphique_Relation_panel.add(Relation_Ross, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 380, 250));

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

        Graphique_Relation_panel.add(Relation_Chandler, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 380, 250));

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

        Graphique_Relation_panel.add(Relation_Rachel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 380, 250));

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

        Graphique_Relation_panel.add(Relation_Monica, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 380, 250));

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

        Graphique_Relation_panel.add(Relation_Phoebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 380, 250));

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

        Graphique_Relation_panel.add(Relation_Joey, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 630, 380, 250));

        TexteGraphiqueRel.setColumns(20);
        TexteGraphiqueRel.setRows(5);
        TexteGraphiqueRel.setText("Ces graphiques comptabilisent les émotions exprimées phrase par phrase dans la série, du début à la fin. \nIls montrent uniquement la fréquence d’apparition des sentiments dans les dialogues.\nOn remarque que la joie et la colère sont très largement dominantes.\nCe sont les émotions les plus souvent exprimées par les personnages, \nce qui reflète le ton humoristique et parfois conflictuel de Friends.\nCependant, ce type d’analyse ne permet pas de rendre compte de la complexité émotionnelle ou des sentiments plus profonds, \ncomme la tristesse ou l’amour, qui peuvent être présents de manière plus subtile ou moins verbalisée. \nPour avoir des analyses plus fines des sentiments profonds. Voir page \"Evolution des sentiments\".");
        ScrollGraphiqueRel.setViewportView(TexteGraphiqueRel);

        Graphique_Relation_panel.add(ScrollGraphiqueRel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 730, -1));

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

        jLabel16.setText("Certaines sources utilisé pour l'analyse de sentiment");

        javax.swing.GroupLayout SourceRelationLayout = new javax.swing.GroupLayout(SourceRelation);
        SourceRelation.setLayout(SourceRelationLayout);
        SourceRelationLayout.setHorizontalGroup(
            SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceRelationLayout.createSequentialGroup()
                .addGap(0, 72, Short.MAX_VALUE)
                .addGroup(SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SourceRelationLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SourceRelationLayout.createSequentialGroup()
                        .addComponent(Relation_Mention, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        SourceRelationLayout.setVerticalGroup(
            SourceRelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SourceRelationLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(Relation_Mention, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        Relation.addTab("Source", SourceRelation);

        SectionRecherche.addTab("Relation", Relation);

        getContentPane().add(SectionRecherche);
        SectionRecherche.setBounds(0, 30, 820, 580);
        SectionRecherche.getAccessibleContext().setAccessibleName("Recherche");
    }// </editor-fold>//GEN-END:initComponents

    //NOAH
    private void afficherInfosPersonnage(String nom) {
        String lower = nom.toLowerCase();

        switch (lower) {
            case "joey" -> setProfil(
                "Joey Tribbiani", "Matt LeBlanc", "Américain", "57 ans", "25 juillet 1967 (États-Unis)",
                "audition, actor, fridge", "how you doin', guy, hey", "/les_png/Joey_photo_profile.png"
            );
            case "rachel" -> setProfil(
                "Rachel Green", "Jennifer Aniston", "Américaine", "55 ans", "11 février 1969 (États-Unis)",
                "A DEFINIR", "A DEFINIR", "/les_png/Rachel_photo_profile.png"
            );
            case "ross" -> setProfil(
                "Ross Geller", "David Schwimmer", "Américain", "57 ans", "2 novembre 1966 (États-Unis)",
                "paléontologie, dinosaures", "we were on a break, uh", "/les_png/Ross_photo_profile.webp"
            );
            case "phoebe" -> setProfil(
                "Phoebe Buffay", "Lisa Kudrow", "Américaine", "61 ans", "30 juillet 1963 (États-Unis)",
                "musique, excentricité", "smelly cat, weird", "/les_png/Phoebe_photo_profile.png"
            );
            case "monica" -> setProfil(
                "Monica Geller", "Courteney Cox", "Américaine", "60 ans", "15 juin 1964 (États-Unis)",
                "A definir", "clean, okay", "/les_png/Monica_photo_profile.png"
            );
            case "chandler" -> setProfil(
                "Chandler Bing", "Matthew Perry", "Américain", "54 ans", "19 août 1969 – 28 octobre 2023",
                "A DEFINIR", "A DEFINIR", "/les_png/Chandler_photo_profile.png"
            );
            default -> setProfil(
                "Personnage inconnu", "-", "-", "-", "-", "-", "-", "/les_png/default.png"
            );
        }
    }
    
    private void setProfil(String nomPerso, String acteur, String nationalite, String age,
                       String naissance, String interets, String mots, String imagePath) {
        nomPersonnage.setText(nomPerso);
        nomPersonnage1.setText("Relations de " + nomPerso);
        jLabel39.setText(acteur);
        jLabel40.setText(nationalite);
        jLabel41.setText(age);
        jLabel42.setText(naissance);
        jLabel44.setText(interets);
        jLabel46.setText(mots);
        imagePersonnage.setImage(imagePath);
    }
    //NOAH FIN
    
    
    
    
    private void rechercheMotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechercheMotKeyPressed
        if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            rechercheMotActionPerformed(null);
        }
    }//GEN-LAST:event_rechercheMotKeyPressed

    private void rechercheMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheMotActionPerformed
        // La recherche par mot
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
        
    }//GEN-LAST:event_rechercheEpisodeActionPerformed

    
    private void rechercheSaisonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheSaisonActionPerformed
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
    }//GEN-LAST:event_rechercheSaisonActionPerformed

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
    private javax.swing.JTabbedPane Relation;
    private controller.ImagePanel Relation_Chandler;
    private controller.ImagePanel Relation_Joey;
    private controller.ImagePanel Relation_Mention;
    private controller.ImagePanel Relation_Monica;
    private controller.ImagePanel Relation_Phoebe;
    private controller.ImagePanel Relation_Rachel;
    private controller.ImagePanel Relation_Ross;
    private javax.swing.JScrollPane ScrollCarte;
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
    private javax.swing.JTextArea TexteCarte;
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
    private javax.swing.JComboBox<String> choixTypeRecherche;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private controller.ImagePanel imageMotsCaracteristiques;
    private controller.ImagePanel imagePersonnage;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
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
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
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
    private controller.ImagePanel neg_nuage;
    private javax.swing.JLabel nomPersonnage;
    private javax.swing.JLabel nomPersonnage1;
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
    private javax.swing.JPanel panelPersonnageInteractions;
    private javax.swing.JPanel panelPersonnageProfil;
    private javax.swing.JPanel panelPersonnageReplique;
    private javax.swing.JPanel panelPersonnageRepliqueFavorite;
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
    private javax.swing.JTable tableDetailReplique;
    private javax.swing.JLabel titre;
    private javax.swing.JLabel valeurPersonnageReplique;
    // End of variables declaration//GEN-END:variables

}

