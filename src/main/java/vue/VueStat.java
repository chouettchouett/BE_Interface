package vue;

import java.awt.event.KeyEvent;
import java.util.List;

public class VueStat extends javax.swing.JFrame {


    public VueStat() {
        initComponents(); 
        //
        // SECTION ANALYSE SENTIMENT *******************************************************************
        //
        //tab 1 Evolution de la positivité
            Evol_pos_image.setImage("/les_png/Evolution_positivité.png");
        //tab 2 Evolution de la négativité
            Evol_neg_image.setImage("/les_png/Evolution_negativité.png");
        //tab 3 Evolution des sentiments
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
        //tab 4 Sentiment_exprimé
            Sentiment_exprime.getVerticalScrollBar().setUnitIncrement(16);
            Sentiment_exprimé_chandler.setImage("/les_png/Sentiment_exprimé_Chandler_V2.png");
            Sentiment_exprimé_ross.setImage("/les_png/Sentiment_exprimé_Ross_V2.png");
            Sentiment_exprimé_joey.setImage("/les_png/Sentiment_exprimé_Joey_V2.png");
            Sentiment_exprimé_monica.setImage("/les_png/Sentiment_exprimé_Monica_V2.png");
            Sentiment_exprimé_rachel.setImage("/les_png/Sentiment_exprimé_Rachel_V2.png");
            Sentiment_exprimé_phoebe.setImage("/les_png/Sentiment_exprimé_Phoebe_V2.png");
        //tab 5 Source
            sentiment_par_personnage_panel.setImage("/les_png/sentiment_par_personnage.png");
            neg_nuage.setImage("/les_png/negative_wordcloud.png");
            pos_nuage.setImage("/les_png/positive_wordcloud.png");
        //
        // SECTION  ANALYSE LANGAGIERE ********************************************************************
        //
        AnalyseLangagière.setImage("/les_png/Analyselangagière.png");
        
        imageMotsCaracteristiques.setImage("/les_png/mots_caractéristiques.png");
        
        imagePersonnage.setImage("/les_png/Joey_Test.png");
        
        recherchePersonnage.setVisible(false);
        rechercheSaison.setVisible(false);
        rechercheEpisode.setVisible(false);
        
        rechercheMot.setColumns(10);
        rechercheMot.setBorder(new javax.swing.border.EmptyBorder(4, 4, 4, 4));
        rechercheMot.setPlaceholder("Recherche de mots");
        
        controller.ControllerDialogRecherche dialog = new controller.ControllerDialogRecherche(rechercheMot);
        choixTypeRecherche.setModel(dialog.getComboBoxModelTypeRecherche());
        choixTypeRecherche.addActionListener((evt) -> {
            javax.swing.JComboBox combobox = (javax.swing.JComboBox) evt.getSource();
            String result = (String) combobox.getSelectedItem();
            /*
            // à refaire plus propre
            switch (result) {
                case "Recherche de mots" -> {
                    recherchePersonnage.setVisible(false);
                    rechercheSaison.setVisible(false);
                    rechercheEpisode.setVisible(false);
                    rechercheMot.setVisible(true);
                    ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "MOT");
                    break;
                }
                
                case "Recherche de personnages" -> {
                    recherchePersonnage.setVisible(true);
                    rechercheSaison.setVisible(false);
                    rechercheEpisode.setVisible(false);
                    rechercheMot.setVisible(false);
                    ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "PERSONNAGE");
                    break;
                }
                
                case "Recherche de saisons" -> {
                    recherchePersonnage.setVisible(false);
                    rechercheSaison.setVisible(true);
                    rechercheEpisode.setVisible(false);
                    rechercheMot.setVisible(false);
                    ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "SAISON");
                    break;
                }
                
                case "Recherche d'épisodes" -> {
                    recherchePersonnage.setVisible(false);
                    rechercheSaison.setVisible(true);
                    rechercheEpisode.setVisible(true);
                    rechercheMot.setVisible(false);
                    ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "EPISODE");
                    break;
                }
                    
            }
*/
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
            javax.swing.JComboBox combobox = (javax.swing.JComboBox) evt.getSource();
            String result = (String) combobox.getSelectedItem();
            
            List<String> rechercheResultat = dialog.recherchePersonnage(result);
            
        });
        
        recherchePersonnage.setModel(dialog.getComboBoxModelPersonnage());
        rechercheSaison.setModel(dialog.getComboBoxModelSaison());
        rechercheEpisode.setModel(dialog.getComboBoxModelEpisode());
        
        ((java.awt.CardLayout) resultats.getLayout()).show(resultats, "SANSRECHERCHE");
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
        libelleSaison = new javax.swing.JLabel();
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
        libelleSaison2 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jPanel87 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        panelSaisonDialogue = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        libelleSaison3 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jPanel91 = new javax.swing.JPanel();
        jPanel92 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        resultatEpisode = new javax.swing.JTabbedPane();
        panelEpisodePresentation = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        nomPersonnage5 = new javax.swing.JLabel();
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
        nomPersonnage6 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jPanel67 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        panelEpisodeDialogue = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        nomPersonnage7 = new javax.swing.JLabel();
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
        jPanel44 = new javax.swing.JPanel();
        nomPersonnage3 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panelMotRepartitionSerie = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        nomPersonnage2 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        panelMotRepartitionSaisonEtEpisode = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        nomPersonnage4 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        Analyse_Statistique = new javax.swing.JPanel();
        AnalyseLangagière = new controller.ImagePanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        Evolution_positivite = new javax.swing.JPanel();
        Evol_pos_image = new controller.ImagePanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea14 = new javax.swing.JTextArea();
        Evolution_negativité = new javax.swing.JPanel();
        Evol_neg_image = new controller.ImagePanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        Sentiment_exprime = new javax.swing.JScrollPane();
        Sentiment_exprimé_panel = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        Sentiment_exprimé_ross = new controller.ImagePanel();
        Sentiment_exprimé_chandler = new controller.ImagePanel();
        Sentiment_exprimé_rachel = new controller.ImagePanel();
        Sentiment_exprimé_monica = new controller.ImagePanel();
        Sentiment_exprimé_phoebe = new controller.ImagePanel();
        Sentiment_exprimé_joey = new controller.ImagePanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        Evolution_Sentiment = new javax.swing.JScrollPane();
        Evolution_Sentiment_Panel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
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
        Opinion = new javax.swing.JPanel();

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

        titre.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        titre.setText("Recherche");
        titre.setToolTipText("");
        titre.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelRechercheTitre.add(titre);

        panelRecherche.add(panelRechercheTitre);

        choixTypeRecherche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelRechercheButtons.add(choixTypeRecherche);

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

        recherchePersonnage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recherchePersonnage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                recherchePersonnageItemStateChanged(evt);
            }
        });
        panelRechercheButtons.add(recherchePersonnage);

        rechercheSaison.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelRechercheButtons.add(rechercheSaison);

        rechercheEpisode.setMaximumRowCount(25);
        rechercheEpisode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelRechercheButtons.add(rechercheEpisode);

        panelRecherche.add(panelRechercheButtons);

        panelRechercheEtIndication.add(panelRecherche, java.awt.BorderLayout.NORTH);

        resultats.setLayout(new java.awt.CardLayout());

        panelSaisonPresentation.setLayout(new java.awt.BorderLayout());

        libelleSaison.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        libelleSaison.setText("Saison 1");
        libelleSaison.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelTitre.add(libelleSaison);

        panelSaisonPresentation.add(panelTitre, java.awt.BorderLayout.NORTH);

        jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jPanel78.setLayout(new javax.swing.BoxLayout(jPanel78, javax.swing.BoxLayout.Y_AXIS));

        jPanel79.setLayout(new javax.swing.BoxLayout(jPanel79, javax.swing.BoxLayout.Y_AXIS));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 153));
        jLabel22.setText("Nombre de répliques:");
        jPanel79.add(jLabel22);

        jLabel106.setText("...");
        jLabel106.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel79.add(jLabel106);

        jPanel78.add(jPanel79);

        jPanel80.setLayout(new javax.swing.BoxLayout(jPanel80, javax.swing.BoxLayout.Y_AXIS));

        jLabel107.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(153, 153, 153));
        jLabel107.setText("Personnages impliqués (nb_persos):");
        jPanel80.add(jLabel107);

        jLabel108.setText("...");
        jLabel108.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel80.add(jLabel108);

        jPanel78.add(jPanel80);

        jPanel82.setLayout(new javax.swing.BoxLayout(jPanel82, javax.swing.BoxLayout.Y_AXIS));

        jLabel111.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(153, 153, 153));
        jLabel111.setText("Top 10 des mots les plus répétés:");
        jPanel82.add(jLabel111);

        jLabel112.setText("... (plus de détails analyse langagière)");
        jLabel112.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel82.add(jLabel112);

        jPanel78.add(jPanel82);

        jPanel14.add(jPanel78);

        panelSaisonPresentation.add(jPanel14, java.awt.BorderLayout.CENTER);

        resultatSaison.addTab("Présentation", panelSaisonPresentation);

        panelSaisonRepartition.setLayout(new java.awt.BorderLayout());

        libelleSaison2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        libelleSaison2.setText("Saison 1");
        libelleSaison2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelTitre2.add(libelleSaison2);

        panelSaisonRepartition.add(panelTitre2, java.awt.BorderLayout.NORTH);

        jPanel85.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
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

        libelleSaison3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        libelleSaison3.setText("Saison 1");
        libelleSaison3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel89.add(libelleSaison3);

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

        panelEpisodePresentation.setLayout(new java.awt.BorderLayout());

        nomPersonnage5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage5.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        nomPersonnage5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(nomPersonnage5);

        panelEpisodePresentation.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.Y_AXIS));

        jPanel50.setLayout(new javax.swing.BoxLayout(jPanel50, javax.swing.BoxLayout.Y_AXIS));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Nombre de répliques:");
        jPanel50.add(jLabel6);

        jLabel73.setText("...");
        jLabel73.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel50.add(jLabel73);

        jPanel49.add(jPanel50);

        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.Y_AXIS));

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(153, 153, 153));
        jLabel74.setText("Personnages impliqués (nb_persos):");
        jPanel51.add(jLabel74);

        jLabel75.setText("...");
        jLabel75.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel51.add(jLabel75);

        jPanel49.add(jPanel51);

        jPanel62.setLayout(new javax.swing.BoxLayout(jPanel62, javax.swing.BoxLayout.Y_AXIS));

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(153, 153, 153));
        jLabel78.setText("Top 10 des mots les plus répétés:");
        jPanel62.add(jLabel78);

        jLabel79.setText("... (plus de détails analyse langagière)");
        jLabel79.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel62.add(jLabel79);

        jPanel49.add(jPanel62);

        jPanel7.add(jPanel49);

        panelEpisodePresentation.add(jPanel7, java.awt.BorderLayout.CENTER);

        resultatEpisode.addTab("Présentation", panelEpisodePresentation);

        panelEpisodeRepartition.setLayout(new java.awt.BorderLayout());

        nomPersonnage6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage6.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        nomPersonnage6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel15.add(nomPersonnage6);

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

        nomPersonnage7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nomPersonnage7.setText("Épisode 1 de la saison 1 : Monica Gets A Roomate");
        nomPersonnage7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel74.add(nomPersonnage7);

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
            .addGap(0, 311, Short.MAX_VALUE)
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
            .addGap(0, 278, Short.MAX_VALUE)
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

        panelMotUtilisation.setLayout(new java.awt.BorderLayout());

        nomPersonnage3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        nomPersonnage3.setText("\"Hello\"");
        nomPersonnage3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel44.add(nomPersonnage3);

        panelMotUtilisation.add(jPanel44, java.awt.BorderLayout.NORTH);

        jPanel45.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel45.setLayout(new javax.swing.BoxLayout(jPanel45, javax.swing.BoxLayout.LINE_AXIS));

        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.Y_AXIS));

        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.Y_AXIS));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(153, 153, 153));
        jLabel70.setText("Liste des répliques contenant le mot \"Hello\":");
        jPanel47.add(jLabel70);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setRowHeight(25);
        jTable1.setShowGrid(true);
        jTable1.setShowVerticalLines(false);
        jScrollPane2.setViewportView(jTable1);

        jPanel47.add(jScrollPane2);

        jPanel46.add(jPanel47);

        jPanel45.add(jPanel46);

        panelMotUtilisation.add(jPanel45, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Utilisation", panelMotUtilisation);

        panelMotRepartitionSerie.setLayout(new java.awt.BorderLayout());

        nomPersonnage2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        nomPersonnage2.setText("\"Hello\"");
        nomPersonnage2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel34.add(nomPersonnage2);

        panelMotRepartitionSerie.add(jPanel34, java.awt.BorderLayout.NORTH);

        jPanel35.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));
        jPanel35.add(filler3);

        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.Y_AXIS));

        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.Y_AXIS));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(153, 153, 153));
        jLabel58.setText("Taux d'utilisation globale:");
        jPanel37.add(jLabel58);

        jLabel59.setText("Le mot \"Hello\" a été employé 310 fois, 1% de tous les mots de la série");
        jLabel59.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel37.add(jLabel59);

        jPanel36.add(jPanel37);

        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.Y_AXIS));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(153, 153, 153));
        jLabel60.setText("Taux d'utilisation par réplique:");
        jPanel38.add(jLabel60);

        jLabel61.setText("Le mot \"Hello\" se retrouve dans 298 répliques différentes, soit dans 1% des répliques de la série");
        jLabel61.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel38.add(jLabel61);

        jPanel36.add(jPanel38);

        jPanel39.setLayout(new javax.swing.BoxLayout(jPanel39, javax.swing.BoxLayout.Y_AXIS));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(153, 153, 153));
        jLabel62.setText("Personnage favori:");
        jPanel39.add(jLabel62);

        jLabel63.setText("Chandler a dit 57 fois le mot");
        jLabel63.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel39.add(jLabel63);

        jPanel36.add(jPanel39);

        jPanel35.add(jPanel36);

        panelMotRepartitionSerie.add(jPanel35, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Répartition dans la série", panelMotRepartitionSerie);

        panelMotRepartitionSaisonEtEpisode.setLayout(new java.awt.BorderLayout());

        nomPersonnage4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        nomPersonnage4.setText("\"Hello\"");
        nomPersonnage4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel54.add(nomPersonnage4);

        panelMotRepartitionSaisonEtEpisode.add(jPanel54, java.awt.BorderLayout.NORTH);

        jPanel55.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));
        jPanel55.add(filler5);

        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.Y_AXIS));

        jPanel57.setLayout(new javax.swing.BoxLayout(jPanel57, javax.swing.BoxLayout.Y_AXIS));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(153, 153, 153));
        jLabel82.setText("Meilleur saison:");
        jPanel57.add(jLabel82);

        jLabel83.setText("Le mot \"Hello\" a été employé 51 fois en saison 1 15% de toutes ses utilisations");
        jLabel83.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel57.add(jLabel83);

        jPanel56.add(jPanel57);

        jPanel58.setLayout(new javax.swing.BoxLayout(jPanel58, javax.swing.BoxLayout.Y_AXIS));

        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(153, 153, 153));
        jLabel84.setText("Taux d'utilisation par réplique:");
        jPanel58.add(jLabel84);

        jLabel85.setText("Le mot \"Hello\" a été employé 11 fois dans l'épisode 2 de la saison 5, 2% de son utilisation totale");
        jLabel85.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel58.add(jLabel85);

        jPanel56.add(jPanel58);

        jPanel59.setLayout(new javax.swing.BoxLayout(jPanel59, javax.swing.BoxLayout.Y_AXIS));

        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(153, 153, 153));
        jLabel86.setText("Graphique d'utilisation par saison:");
        jPanel59.add(jLabel86);

        jLabel87.setText("...");
        jLabel87.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel59.add(jLabel87);

        jPanel56.add(jPanel59);

        jPanel60.setLayout(new javax.swing.BoxLayout(jPanel60, javax.swing.BoxLayout.Y_AXIS));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(153, 153, 153));
        jLabel88.setText("Graphique d'utilisation par épisode (TOP 10 épisodes):");
        jPanel60.add(jLabel88);

        jLabel89.setText("...");
        jLabel89.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel60.add(jLabel89);

        jPanel56.add(jPanel60);

        jPanel61.setLayout(new javax.swing.BoxLayout(jPanel61, javax.swing.BoxLayout.Y_AXIS));

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(153, 153, 153));
        jLabel90.setText("Utilisation pour un certain épisode:");
        jPanel61.add(jLabel90);

        jLabel91.setText("Sélectionnez l'épisode <combobox>");
        jLabel91.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel61.add(jLabel91);

        jPanel56.add(jPanel61);

        jPanel55.add(jPanel56);

        panelMotRepartitionSaisonEtEpisode.add(jPanel55, java.awt.BorderLayout.CENTER);

        resultatMot.addTab("Répartition par saison et épisode", panelMotRepartitionSaisonEtEpisode);

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

        jTextArea14.setColumns(20);
        jTextArea14.setRows(5);
        jTextArea14.setText("Contrairement à une tendance linéaire, le graphique révèle des pics distincts de positivité selon les saisons.\nSaison 2 : Monica, vit une rupture douloureuse avec Richard donc baisse de positivité. Phoebe découvre son demi-frère, ce qui renforce son cercle familial.\nSaison 5 : hausse généralisée — le groupe se stabilise émotionnellement, malgré quelques tensions. Chandler et Monica se rapprochent, et Phoebe commence à retrouver un certain équilibre.\nSaison 9 : regain inattendu de positivité (notamment Monica et Chandler) alors qu’ils avancent vers l’adoption et une vie commune plus structurée.\nGlobalement, la positivité semble répondre à des événements ponctuels d'accomplissement personnel ou relationnel, plus qu'à une logique progressive.");
        jScrollPane15.setViewportView(jTextArea14);

        Evolution_positivite.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 770, 120));

        jTabbedPane3.addTab("Evolution de la positivité", Evolution_positivite);

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

        jTextArea13.setColumns(20);
        jTextArea13.setRows(5);
        jTextArea13.setText("La négativité varie fortement d’un personnage à l’autre, mais certains pics s’expliquent par des événements narratifs majeurs.\nSaison 2 : Phoebe connaît une baisse notable, probablement liée à la découverte de son frère et à la confrontation à sa propre histoire familiale. Monica, quant à elle, vit une rupture douloureuse avec Richard. Joey, qui obtient un rôle majeur, affiche un baisse de négativité — sa carrière décolle et sa vie amoureuse s’épanouit. \nSaison 4 : Rachel se montre plus négative — sa jalousie vis-à-vis du mariage de Ross avec Emily devient centrale.\nSaison 7 : nouvelle baisse chez Phoebe, alors que paradoxalement elle se rapproche de Mike — peut-être l’effet d’une réorientation affective plus sérieuse qui l’éloigne de sa posture excentrique.\nLes pics de négativité reflètent souvent des moments d’instabilité amoureuse ou familiale ou même relié au travail.");
        jScrollPane14.setViewportView(jTextArea13);

        Evolution_negativité.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 750, 140));

        jTabbedPane3.addTab("Evolution de la négativité", Evolution_negativité);

        Sentiment_exprimé_panel.setMinimumSize(new java.awt.Dimension(808, 895));
        Sentiment_exprimé_panel.setPreferredSize(new java.awt.Dimension(808, 895));
        Sentiment_exprimé_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Sentiment_exprimé_panel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(749, 115, 749, -1));

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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Ces graphiques comptabilisent les émotions exprimées phrase par phrase dans la série, du début à la fin. \nIls montrent uniquement la fréquence d’apparition des sentiments dans les dialogues.\nOn remarque que la joie et la colère sont très largement dominantes.\nCe sont les émotions les plus souvent exprimées par les personnages, \nce qui reflète le ton humoristique et parfois conflictuel de Friends.\nCependant, ce type d’analyse ne permet pas de rendre compte de la complexité émotionnelle ou des sentiments plus profonds, \ncomme la tristesse ou l’amour, qui peuvent être présents de manière plus subtile ou moins verbalisée. \nPour avoir des analyses plus fines des sentiments profonds. Voir page \"Evolution des sentiments\".");
        jScrollPane1.setViewportView(jTextArea1);

        Sentiment_exprimé_panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 730, -1));

        Sentiment_exprime.setViewportView(Sentiment_exprimé_panel);

        jTabbedPane3.addTab("Sentiment_exprimé", Sentiment_exprime);

        Evolution_Sentiment_Panel.setMinimumSize(new java.awt.Dimension(808, 1400));
        Evolution_Sentiment_Panel.setPreferredSize(new java.awt.Dimension(808, 1400));
        Evolution_Sentiment_Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Evolution_Sentiment_Panel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(749, 115, 749, -1));

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

        jTabbedPane3.addTab("Evolution des sentiments", Evolution_Sentiment);

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

        jTabbedPane3.addTab("Source", Source);

        javax.swing.GroupLayout OpinionLayout = new javax.swing.GroupLayout(Opinion);
        Opinion.setLayout(OpinionLayout);
        OpinionLayout.setHorizontalGroup(
            OpinionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 816, Short.MAX_VALUE)
        );
        OpinionLayout.setVerticalGroup(
            OpinionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Opinion", Opinion);

        SectionRecherche.addTab("Analyse de Sentiment", jTabbedPane3);

        getContentPane().add(SectionRecherche);
        SectionRecherche.setBounds(0, 30, 820, 580);
        SectionRecherche.getAccessibleContext().setAccessibleName("Recherche");
    }// </editor-fold>//GEN-END:initComponents

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.ImagePanel AnalyseLangagière;
    private javax.swing.JPanel Analyse_Statistique;
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
    private javax.swing.JPanel Opinion;
    private javax.swing.JScrollPane ScrollChandlertext;
    private javax.swing.JScrollPane ScrollInterpretationtext;
    private javax.swing.JScrollPane ScrollJoeytext;
    private javax.swing.JScrollPane ScrollMonicatext;
    private javax.swing.JScrollPane ScrollPhoebetext;
    private javax.swing.JScrollPane ScrollRacheltext;
    private javax.swing.JScrollPane ScrollRosstext;
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
    private javax.swing.JTextArea TexteChandler;
    private javax.swing.JTextArea TexteInterpretationGlobale;
    private javax.swing.JTextArea TexteJoey;
    private javax.swing.JTextArea TexteMonica;
    private javax.swing.JTextArea TextePhoebe;
    private javax.swing.JTextArea TexteRachel;
    private javax.swing.JTextArea TexteRoss;
    private javax.swing.JComboBox<String> choixTypeRecherche;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler5;
    private controller.ImagePanel imageMotsCaracteristiques;
    private controller.ImagePanel imagePersonnage;
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
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
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
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
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
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
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea14;
    private javax.swing.JLabel labelSansRechercheExemple;
    private javax.swing.JLabel labelSansRechercheSentence;
    private javax.swing.JLabel libelleSaison;
    private javax.swing.JLabel libelleSaison2;
    private javax.swing.JLabel libelleSaison3;
    private controller.ImagePanel neg_nuage;
    private javax.swing.JLabel nomPersonnage;
    private javax.swing.JLabel nomPersonnage1;
    private javax.swing.JLabel nomPersonnage2;
    private javax.swing.JLabel nomPersonnage3;
    private javax.swing.JLabel nomPersonnage4;
    private javax.swing.JLabel nomPersonnage5;
    private javax.swing.JLabel nomPersonnage6;
    private javax.swing.JLabel nomPersonnage7;
    private javax.swing.JPanel panelEpisodeDialogue;
    private javax.swing.JPanel panelEpisodePresentation;
    private javax.swing.JPanel panelEpisodeRepartition;
    private javax.swing.JPanel panelMotRepartitionSaisonEtEpisode;
    private javax.swing.JPanel panelMotRepartitionSerie;
    private javax.swing.JPanel panelMotUtilisation;
    private javax.swing.JPanel panelPageRecherche;
    private javax.swing.JPanel panelPersonnageInteractions;
    private javax.swing.JPanel panelPersonnageProfil;
    private javax.swing.JPanel panelPersonnageRepliqueFavorite;
    private javax.swing.JPanel panelRecherche;
    private javax.swing.JPanel panelRechercheButtons;
    private javax.swing.JPanel panelRechercheEtIndication;
    private javax.swing.JPanel panelRechercheTitre;
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
    private controller.ImagePanel sentiment_par_personnage_panel;
    private javax.swing.JLabel titre;
    // End of variables declaration//GEN-END:variables

}

