/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Customizer.java to edit this template
 */
package vue;

import java.awt.event.KeyEvent;

public class Vue extends javax.swing.JFrame {

    public String nom_de_personnage_trouve = " rien trouve ";
    public int numero_episode_trouve = 0;
    public int numero_saison_trouve = 0;
    public String contexte_trouve = " rien trouve ";;
    public String emotion_transmise_trouve = " rien trouve ";;
    
    public Vue() {
        initComponents();
        //Observation.setEditable(false);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel_Citation = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Citation_Recherche = new javax.swing.JTextField();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane4 = new javax.swing.JScrollPane();
        Reponse_citation = new javax.swing.JTextArea();
        jPanel_Statistique = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel_Joey = new javax.swing.JPanel();
        Recap_Joey = new javax.swing.JLabel();
        jPanel_Rachel = new javax.swing.JPanel();
        Recap_Rachel = new javax.swing.JLabel();
        jPanel_Chandler = new javax.swing.JPanel();
        Recap_Chandler = new javax.swing.JLabel();
        jPanel_Phoebe = new javax.swing.JPanel();
        Recap_Phoebe = new javax.swing.JLabel();
        jPanel_Monica = new javax.swing.JPanel();
        Recap_Monica = new javax.swing.JLabel();
        jPanel_Ross = new javax.swing.JPanel();
        Recap_Ross = new javax.swing.JLabel();
        jPanel_Autre = new javax.swing.JPanel();
        Recap_Autre = new javax.swing.JLabel();
        positif = new javax.swing.JLabel();
        different = new javax.swing.JLabel();
        negatif = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        temporaire = new javax.swing.JLabel();
        jPanel_Sentiment = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel_Diagramme_Relation = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(800, 650));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Interface Friends");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 20);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setName(""); // NOI18N

        jPanel_Citation.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Les citations favorites des français :");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("\"je vais me suicider avec des yaourts périmés.\"\n“Quand on est vivant on répond au téléphone !”");
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane2.setHorizontalScrollBar(null);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("Ep?Saison?\nEp?Saison?");
        jScrollPane2.setViewportView(jTextArea2);

        jScrollPane3.setHorizontalScrollBar(null);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setText("Chandler\nPhoebe\n");
        jScrollPane3.setViewportView(jTextArea3);

        jLabel4.setText("ZONE A REVOIR/ FABRIQUER");

        jLabel5.setText("Recherchez votre citation favorite :");

        Citation_Recherche.setText("Blablobli");
        Citation_Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Citation_RechercheActionPerformed(evt);
            }
        });
        Citation_Recherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Citation_RechercheKeyPressed(evt);
            }
        });

        Reponse_citation.setEditable(false);
        Reponse_citation.setColumns(20);
        Reponse_citation.setRows(5);
        Reponse_citation.setText("Blablobli est une citation de \"nom_de_personnage\" \nQui ce retrouve à l'épisode? Saison?\nContexte : ......\nEmotion transmise par la citation : ......");
        jScrollPane4.setViewportView(Reponse_citation);

        javax.swing.GroupLayout jPanel_CitationLayout = new javax.swing.GroupLayout(jPanel_Citation);
        jPanel_Citation.setLayout(jPanel_CitationLayout);
        jPanel_CitationLayout.setHorizontalGroup(
            jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CitationLayout.createSequentialGroup()
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel_CitationLayout.createSequentialGroup()
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Citation_Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_CitationLayout.setVerticalGroup(
            jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_CitationLayout.createSequentialGroup()
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
                    .addGroup(jPanel_CitationLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addGap(29, 29, 29)
                .addGroup(jPanel_CitationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Citation_Recherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("Citation", jPanel_Citation);
        jPanel_Citation.getAccessibleContext().setAccessibleName("Citation_");

        Recap_Joey.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_JoeyLayout = new javax.swing.GroupLayout(jPanel_Joey);
        jPanel_Joey.setLayout(jPanel_JoeyLayout);
        jPanel_JoeyLayout.setHorizontalGroup(
            jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_JoeyLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Joey, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_JoeyLayout.setVerticalGroup(
            jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_JoeyLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Joey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Joey", jPanel_Joey);

        Recap_Rachel.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_RachelLayout = new javax.swing.GroupLayout(jPanel_Rachel);
        jPanel_Rachel.setLayout(jPanel_RachelLayout);
        jPanel_RachelLayout.setHorizontalGroup(
            jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RachelLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Rachel, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_RachelLayout.setVerticalGroup(
            jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RachelLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Rachel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Rachel", jPanel_Rachel);

        Recap_Chandler.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_ChandlerLayout = new javax.swing.GroupLayout(jPanel_Chandler);
        jPanel_Chandler.setLayout(jPanel_ChandlerLayout);
        jPanel_ChandlerLayout.setHorizontalGroup(
            jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_ChandlerLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Chandler, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_ChandlerLayout.setVerticalGroup(
            jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_ChandlerLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Chandler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Chandler", jPanel_Chandler);

        Recap_Phoebe.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_PhoebeLayout = new javax.swing.GroupLayout(jPanel_Phoebe);
        jPanel_Phoebe.setLayout(jPanel_PhoebeLayout);
        jPanel_PhoebeLayout.setHorizontalGroup(
            jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PhoebeLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Phoebe, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_PhoebeLayout.setVerticalGroup(
            jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PhoebeLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Phoebe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Phoebe", jPanel_Phoebe);

        Recap_Monica.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_MonicaLayout = new javax.swing.GroupLayout(jPanel_Monica);
        jPanel_Monica.setLayout(jPanel_MonicaLayout);
        jPanel_MonicaLayout.setHorizontalGroup(
            jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MonicaLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Monica, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_MonicaLayout.setVerticalGroup(
            jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MonicaLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Monica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Monica", jPanel_Monica);

        Recap_Ross.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_RossLayout = new javax.swing.GroupLayout(jPanel_Ross);
        jPanel_Ross.setLayout(jPanel_RossLayout);
        jPanel_RossLayout.setHorizontalGroup(
            jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
            .addGroup(jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RossLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Ross, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_RossLayout.setVerticalGroup(
            jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RossLayout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(Recap_Ross, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(4, 4, 4)))
        );

        jTabbedPane2.addTab("Ross", jPanel_Ross);

        Recap_Autre.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_AutreLayout = new javax.swing.GroupLayout(jPanel_Autre);
        jPanel_Autre.setLayout(jPanel_AutreLayout);
        jPanel_AutreLayout.setHorizontalGroup(
            jPanel_AutreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_AutreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Recap_Autre, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_AutreLayout.setVerticalGroup(
            jPanel_AutreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Recap_Autre, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Autre", jPanel_Autre);

        jTabbedPane2.setSelectedComponent(jPanel_Joey);

        positif.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        positif.setText("Positif");

        different.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        different.setText("Différent");

        negatif.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        negatif.setText("Négatif");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Ici des graph serait tracé");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setText("Ici des graph serait tracé");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel8)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Ici des graph serait tracé");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        temporaire.setText("Contenu des graph changeant en fonction du personnage");

        javax.swing.GroupLayout jPanel_StatistiqueLayout = new javax.swing.GroupLayout(jPanel_Statistique);
        jPanel_Statistique.setLayout(jPanel_StatistiqueLayout);
        jPanel_StatistiqueLayout.setHorizontalGroup(
            jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StatistiqueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_StatistiqueLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2)
                    .addGroup(jPanel_StatistiqueLayout.createSequentialGroup()
                        .addComponent(positif)
                        .addGap(151, 151, 151)
                        .addComponent(different)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(negatif)))
                .addGap(66, 66, 66))
            .addGroup(jPanel_StatistiqueLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(temporaire, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_StatistiqueLayout.setVerticalGroup(
            jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_StatistiqueLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(temporaire, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positif)
                    .addComponent(different)
                    .addComponent(negatif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_StatistiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Statistique", jPanel_Statistique);
        jPanel_Statistique.getAccessibleContext().setAccessibleName("Statistique_");

        jLabel12.setText("Expression et tic de language préféré de chaque personnage :");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setText("nom personnage");

        jLabel14.setText("nom personnage");

        jLabel15.setText("nom personnage");

        jLabel16.setText("nom personnage");

        jLabel17.setText("nom personnage");

        jLabel18.setText("nom personnage");

        jLabel19.setText("C'est toute la section qui sera affiché ici, frabriqué par code");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGap(56, 56, 56)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_SentimentLayout = new javax.swing.GroupLayout(jPanel_Sentiment);
        jPanel_Sentiment.setLayout(jPanel_SentimentLayout);
        jPanel_SentimentLayout.setHorizontalGroup(
            jPanel_SentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SentimentLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel_SentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel_SentimentLayout.setVerticalGroup(
            jPanel_SentimentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SentimentLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(197, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sentiment", jPanel_Sentiment);
        jPanel_Sentiment.getAccessibleContext().setAccessibleName("Sentiment_");

        javax.swing.GroupLayout jPanel_Diagramme_RelationLayout = new javax.swing.GroupLayout(jPanel_Diagramme_Relation);
        jPanel_Diagramme_Relation.setLayout(jPanel_Diagramme_RelationLayout);
        jPanel_Diagramme_RelationLayout.setHorizontalGroup(
            jPanel_Diagramme_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
        );
        jPanel_Diagramme_RelationLayout.setVerticalGroup(
            jPanel_Diagramme_RelationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Diagramme Relation", jPanel_Diagramme_Relation);
        jPanel_Diagramme_Relation.getAccessibleContext().setAccessibleName("Diagramme_Relation_");

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 40, 610, 490);
    }// </editor-fold>//GEN-END:initComponents

    private void Citation_RechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Citation_RechercheActionPerformed
        String message = Citation_Recherche.getText();
        // il faudra faire appel à des fonctions pour les trouver/remplacer les x_trouve
        // appel fonctionRechercheCitation =>va set les trouver
            if (!message.isEmpty()){
                Reponse_citation.setText("'"+message + "' est une citation de " + nom_de_personnage_trouve +
                        "\nQui ce retrouve à l'épisode "+ numero_episode_trouve + " Saison " + 
                        numero_saison_trouve + "\nContexte : "+ contexte_trouve + 
                        "\nEmotion transmise par la citation : " + emotion_transmise_trouve);
        }
        
    }//GEN-LAST:event_Citation_RechercheActionPerformed

    private void Citation_RechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Citation_RechercheKeyPressed
        if( evt.getKeyCode() == KeyEvent.VK_ENTER){
            Citation_RechercheActionPerformed(null);
        }
    }//GEN-LAST:event_Citation_RechercheKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Citation_Recherche;
    private javax.swing.JLabel Recap_Autre;
    private javax.swing.JLabel Recap_Chandler;
    private javax.swing.JLabel Recap_Joey;
    private javax.swing.JLabel Recap_Monica;
    private javax.swing.JLabel Recap_Phoebe;
    private javax.swing.JLabel Recap_Rachel;
    private javax.swing.JLabel Recap_Ross;
    private javax.swing.JTextArea Reponse_citation;
    private javax.swing.JLabel different;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_Autre;
    private javax.swing.JPanel jPanel_Chandler;
    private javax.swing.JPanel jPanel_Citation;
    private javax.swing.JPanel jPanel_Diagramme_Relation;
    private javax.swing.JPanel jPanel_Joey;
    private javax.swing.JPanel jPanel_Monica;
    private javax.swing.JPanel jPanel_Phoebe;
    private javax.swing.JPanel jPanel_Rachel;
    private javax.swing.JPanel jPanel_Ross;
    private javax.swing.JPanel jPanel_Sentiment;
    private javax.swing.JPanel jPanel_Statistique;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JLabel negatif;
    private javax.swing.JLabel positif;
    private javax.swing.JLabel temporaire;
    // End of variables declaration//GEN-END:variables

}

