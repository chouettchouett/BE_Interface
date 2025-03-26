package vue;

public class VueStat extends javax.swing.JFrame {


    public VueStat() {
        initComponents(); 
        Evol_neg_image.setImage("/les_png/Evolution_negativité.png");
        Evol_pos_image.setImage("/les_png/Evolution_positivité.png");
        Evol_sent_image.setImage("/les_png/Evolution_sentiment.png");
        
        Sentiment_exprimé_chandler.setImage("/les_png/Sentiment_exprimé_Chandler.png");
        Sentiment_exprimé_ross.setImage("/les_png/Sentiment_exprimé_Ross.png");
        Sentiment_exprimé_joey.setImage("/les_png/Sentiment_exprimé_Joey.png");
        Sentiment_exprimé_monica.setImage("/les_png/Sentiment_exprimé_Monica.png");
        Sentiment_exprimé_rachel.setImage("/les_png/Sentiment_exprimé_Rachel.png");
        Sentiment_exprimé_phoebe.setImage("/les_png/Sentiment_exprimé_Phoebe.png");
        
        AnalyseLangagière.setImage("/les_png/Analyselangagière.png");
        
        sentiment_par_personnage_panel.setImage("/les_png/sentiment_par_personnage.png");
        neg_nuage.setImage("/les_png/negative_wordcloud.png");
        pos_nuage.setImage("/les_png/positive_wordcloud.png");

    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Recherche = new javax.swing.JPanel();
        Citation_Recherche = new javax.swing.JTextField();
        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Analyse_Statistique = new javax.swing.JPanel();
        AnalyseLangagière = new controller.ImagePanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        Evolution_positivite = new javax.swing.JPanel();
        Evol_pos_image = new controller.ImagePanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        Evolution_negativité = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Evol_neg_image = new controller.ImagePanel();
        jLabel11 = new javax.swing.JLabel();
        Source = new javax.swing.JPanel();
        sentiment_par_personnage_panel = new controller.ImagePanel();
        neg_nuage = new controller.ImagePanel();
        jLabel15 = new javax.swing.JLabel();
        pos_nuage = new controller.ImagePanel();
        Evolution_sentiment = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Evol_sent_image = new controller.ImagePanel();
        jLabel23 = new javax.swing.JLabel();
        Sentiment_exprimé = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Sentiment_exprimé_chandler = new controller.ImagePanel();
        Sentiment_exprimé_ross = new controller.ImagePanel();
        Sentiment_exprimé_joey = new controller.ImagePanel();
        Sentiment_exprimé_monica = new controller.ImagePanel();
        Sentiment_exprimé_rachel = new controller.ImagePanel();
        Sentiment_exprimé_phoebe = new controller.ImagePanel();
        Opinion = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 650));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Interface Friends");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 20);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setName(""); // NOI18N

        Recherche.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Citation_Recherche.setText("Blablobli");

        jLabel5.setText("JMenuDeroulant");

        jLabel6.setText("Toute les options");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Les détails de leur partie");

        jLabel21.setText("Liens vers les différentes section géré de leur côté");

        javax.swing.GroupLayout RechercheLayout = new javax.swing.GroupLayout(Recherche);
        Recherche.setLayout(RechercheLayout);
        RechercheLayout.setHorizontalGroup(
            RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RechercheLayout.createSequentialGroup()
                .addGroup(RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RechercheLayout.createSequentialGroup()
                        .addContainerGap(159, Short.MAX_VALUE)
                        .addGroup(RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RechercheLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Citation_Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(RechercheLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(153, 153, 153))
                    .addGroup(RechercheLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        RechercheLayout.setVerticalGroup(
            RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RechercheLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addGap(191, 191, 191))
            .addGroup(RechercheLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Citation_Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(RechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RechercheLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RechercheLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Recherche", Recherche);

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

        jTabbedPane1.addTab("Analyse Langagière", Analyse_Statistique);
        Analyse_Statistique.getAccessibleContext().setAccessibleName("Statistique_");

        Evol_pos_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_pos_imageLayout = new javax.swing.GroupLayout(Evol_pos_image);
        Evol_pos_image.setLayout(Evol_pos_imageLayout);
        Evol_pos_imageLayout.setHorizontalGroup(
            Evol_pos_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 714, Short.MAX_VALUE)
        );
        Evol_pos_imageLayout.setVerticalGroup(
            Evol_pos_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 326, Short.MAX_VALUE)
        );

        jLabel2.setText("Comme nous pouvons le voir. Au fil des saisons. Les personnages sont en général plus positif à la fin de la série.    ");

        jLabel3.setText("Avec des grosses tendances visible sur une saison 2 marqué par un grand pic de positivité de phoebe et une grande baisse de Monica.");

        jLabel4.setText(" Nous pouvons noter également les pic de joey saison6 et chandler saison8");

        jLabel20.setText("Section détail explication de graph");

        javax.swing.GroupLayout Evolution_positiviteLayout = new javax.swing.GroupLayout(Evolution_positivite);
        Evolution_positivite.setLayout(Evolution_positiviteLayout);
        Evolution_positiviteLayout.setHorizontalGroup(
            Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Evol_pos_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 55, Short.MAX_VALUE))
        );
        Evolution_positiviteLayout.setVerticalGroup(
            Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_positiviteLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Evol_pos_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Evolution de la positivité", Evolution_positivite);

        jLabel8.setText("N");

        jLabel9.setText("N");

        jLabel10.setText(" N");

        Evol_neg_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_neg_imageLayout = new javax.swing.GroupLayout(Evol_neg_image);
        Evol_neg_image.setLayout(Evol_neg_imageLayout);
        Evol_neg_imageLayout.setHorizontalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 732, Short.MAX_VALUE)
        );
        Evol_neg_imageLayout.setVerticalGroup(
            Evol_neg_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );

        jLabel11.setText("Section détail explication de graph");

        javax.swing.GroupLayout Evolution_negativitéLayout = new javax.swing.GroupLayout(Evolution_negativité);
        Evolution_negativité.setLayout(Evolution_negativitéLayout);
        Evolution_negativitéLayout.setHorizontalGroup(
            Evolution_negativitéLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Evolution_negativitéLayout.createSequentialGroup()
                .addGroup(Evolution_negativitéLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Evolution_negativitéLayout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Evolution_negativitéLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(Evolution_negativitéLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Evol_neg_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        Evolution_negativitéLayout.setVerticalGroup(
            Evolution_negativitéLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_negativitéLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Evol_neg_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Evolution de la négativité", Evolution_negativité);

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
                .addGap(0, 22, Short.MAX_VALUE)
                .addGroup(SourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(neg_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SourceLayout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(pos_nuage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
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

        jLabel12.setText("Découper les graph de chaque personne et analyser");

        jLabel13.setText("N");

        jLabel14.setText(" N");

        Evol_sent_image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Evol_sent_imageLayout = new javax.swing.GroupLayout(Evol_sent_image);
        Evol_sent_image.setLayout(Evol_sent_imageLayout);
        Evol_sent_imageLayout.setHorizontalGroup(
            Evol_sent_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );
        Evol_sent_imageLayout.setVerticalGroup(
            Evol_sent_imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        jLabel23.setText("Section détail explication de graph");

        javax.swing.GroupLayout Evolution_sentimentLayout = new javax.swing.GroupLayout(Evolution_sentiment);
        Evolution_sentiment.setLayout(Evolution_sentimentLayout);
        Evolution_sentimentLayout.setHorizontalGroup(
            Evolution_sentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Evolution_sentimentLayout.createSequentialGroup()
                .addGroup(Evolution_sentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Evolution_sentimentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Evolution_sentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(Evolution_sentimentLayout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Evolution_sentimentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_sentimentLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(Evol_sent_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        Evolution_sentimentLayout.setVerticalGroup(
            Evolution_sentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_sentimentLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(14, 14, 14)
                .addComponent(Evol_sent_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Evolution des sentiments", Evolution_sentiment);

        jLabel16.setText("3");

        jLabel18.setText("3");

        jLabel19.setText("Section détail explication de graph");

        Sentiment_exprimé_chandler.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_chandlerLayout = new javax.swing.GroupLayout(Sentiment_exprimé_chandler);
        Sentiment_exprimé_chandler.setLayout(Sentiment_exprimé_chandlerLayout);
        Sentiment_exprimé_chandlerLayout.setHorizontalGroup(
            Sentiment_exprimé_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );
        Sentiment_exprimé_chandlerLayout.setVerticalGroup(
            Sentiment_exprimé_chandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Sentiment_exprimé_ross.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_rossLayout = new javax.swing.GroupLayout(Sentiment_exprimé_ross);
        Sentiment_exprimé_ross.setLayout(Sentiment_exprimé_rossLayout);
        Sentiment_exprimé_rossLayout.setHorizontalGroup(
            Sentiment_exprimé_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );
        Sentiment_exprimé_rossLayout.setVerticalGroup(
            Sentiment_exprimé_rossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Sentiment_exprimé_joey.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_joeyLayout = new javax.swing.GroupLayout(Sentiment_exprimé_joey);
        Sentiment_exprimé_joey.setLayout(Sentiment_exprimé_joeyLayout);
        Sentiment_exprimé_joeyLayout.setHorizontalGroup(
            Sentiment_exprimé_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );
        Sentiment_exprimé_joeyLayout.setVerticalGroup(
            Sentiment_exprimé_joeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Sentiment_exprimé_monica.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_monicaLayout = new javax.swing.GroupLayout(Sentiment_exprimé_monica);
        Sentiment_exprimé_monica.setLayout(Sentiment_exprimé_monicaLayout);
        Sentiment_exprimé_monicaLayout.setHorizontalGroup(
            Sentiment_exprimé_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );
        Sentiment_exprimé_monicaLayout.setVerticalGroup(
            Sentiment_exprimé_monicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        Sentiment_exprimé_rachel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_rachelLayout = new javax.swing.GroupLayout(Sentiment_exprimé_rachel);
        Sentiment_exprimé_rachel.setLayout(Sentiment_exprimé_rachelLayout);
        Sentiment_exprimé_rachelLayout.setHorizontalGroup(
            Sentiment_exprimé_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 228, Short.MAX_VALUE)
        );
        Sentiment_exprimé_rachelLayout.setVerticalGroup(
            Sentiment_exprimé_rachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Sentiment_exprimé_phoebe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Sentiment_exprimé_phoebeLayout = new javax.swing.GroupLayout(Sentiment_exprimé_phoebe);
        Sentiment_exprimé_phoebe.setLayout(Sentiment_exprimé_phoebeLayout);
        Sentiment_exprimé_phoebeLayout.setHorizontalGroup(
            Sentiment_exprimé_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );
        Sentiment_exprimé_phoebeLayout.setVerticalGroup(
            Sentiment_exprimé_phoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout Sentiment_expriméLayout = new javax.swing.GroupLayout(Sentiment_exprimé);
        Sentiment_exprimé.setLayout(Sentiment_expriméLayout);
        Sentiment_expriméLayout.setHorizontalGroup(
            Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                .addGroup(Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                                .addComponent(Sentiment_exprimé_monica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(Sentiment_exprimé_rachel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Sentiment_exprimé_phoebe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                                    .addComponent(Sentiment_exprimé_chandler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(22, 22, 22)
                                    .addComponent(Sentiment_exprimé_ross, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(Sentiment_exprimé_joey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        Sentiment_expriméLayout.setVerticalGroup(
            Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel19)
                .addGap(12, 12, 12)
                .addComponent(jLabel16)
                .addGap(12, 12, 12)
                .addComponent(jLabel18)
                .addGap(12, 12, 12)
                .addGroup(Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Sentiment_exprimé_joey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Sentiment_expriméLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel17)
                        .addGap(0, 178, Short.MAX_VALUE))
                    .addComponent(Sentiment_exprimé_ross, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Sentiment_exprimé_chandler, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(Sentiment_expriméLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Sentiment_exprimé_monica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Sentiment_exprimé_phoebe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Sentiment_exprimé_rachel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("Sentiment Exprimé", Sentiment_exprimé);

        jLabel24.setText("A réfléchir");

        javax.swing.GroupLayout OpinionLayout = new javax.swing.GroupLayout(Opinion);
        Opinion.setLayout(OpinionLayout);
        OpinionLayout.setHorizontalGroup(
            OpinionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpinionLayout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(391, Short.MAX_VALUE))
        );
        OpinionLayout.setVerticalGroup(
            OpinionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OpinionLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel24)
                .addContainerGap(435, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Opinion", Opinion);

        jTabbedPane1.addTab("Analyse de Sentiment", jTabbedPane3);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(20, 30, 820, 580);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Recherche");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.ImagePanel AnalyseLangagière;
    private javax.swing.JPanel Analyse_Statistique;
    private javax.swing.JTextField Citation_Recherche;
    private controller.ImagePanel Evol_neg_image;
    private controller.ImagePanel Evol_pos_image;
    private controller.ImagePanel Evol_sent_image;
    private controller.ImagePanel Evol_sent_image2;
    private javax.swing.JPanel Evolution_negativité;
    private javax.swing.JPanel Evolution_positivite;
    private javax.swing.JPanel Evolution_sentiment;
    private javax.swing.JPanel Opinion;
    private javax.swing.JPanel Recherche;
    private javax.swing.JPanel Sentiment_exprimé;
    private controller.ImagePanel Sentiment_exprimé_chandler;
    private controller.ImagePanel Sentiment_exprimé_joey;
    private controller.ImagePanel Sentiment_exprimé_monica;
    private controller.ImagePanel Sentiment_exprimé_phoebe;
    private controller.ImagePanel Sentiment_exprimé_rachel;
    private controller.ImagePanel Sentiment_exprimé_ross;
    private javax.swing.JPanel Source;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private controller.ImagePanel neg_nuage;
    private controller.ImagePanel pos_nuage;
    private controller.ImagePanel sentiment_par_personnage_panel;
    // End of variables declaration//GEN-END:variables

}

