/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Customizer.java to edit this template
 */
package vue;

import java.awt.Image;

public class VueStat extends javax.swing.JFrame {
    // Attribut
    private Image image;

    // Dans ton constructeur VueStat()
    public VueStat() {
        initComponents(); // crée ton jPanelImage, etc.
        imagePanel1.setImage("/les_png/Evolution_negativité.png");
        imagePanel2.setImage("/les_png/Evolution_positivité.png");
        imagePanel3.setImage("/les_png/Evolution_sentiment.png");
        imagePanel4.setImage("/les_png/Sentiment_exprimé.png");
        imagePanel5.setImage("/les_png/Evolution_positivité.png");
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
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
        jPanel4 = new javax.swing.JPanel();
        imagePanel1 = new controller.ImagePanel();
        imagePanel3 = new controller.ImagePanel();
        imagePanel4 = new controller.ImagePanel();
        imagePanel5 = new controller.ImagePanel();
        Evolution_positivite = new javax.swing.JPanel();
        imagePanel2 = new controller.ImagePanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 650));
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Interface Friends");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 20);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setName(""); // NOI18N

        Recap_Joey.setText("Recap de ce qu'on as tiré du personnage avec ces stats etc...");

        javax.swing.GroupLayout jPanel_JoeyLayout = new javax.swing.GroupLayout(jPanel_Joey);
        jPanel_Joey.setLayout(jPanel_JoeyLayout);
        jPanel_JoeyLayout.setHorizontalGroup(
            jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_JoeyLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Joey, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_JoeyLayout.setVerticalGroup(
            jPanel_JoeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RachelLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Rachel, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_RachelLayout.setVerticalGroup(
            jPanel_RachelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_ChandlerLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Chandler, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_ChandlerLayout.setVerticalGroup(
            jPanel_ChandlerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_PhoebeLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Phoebe, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_PhoebeLayout.setVerticalGroup(
            jPanel_PhoebeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_MonicaLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Monica, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_MonicaLayout.setVerticalGroup(
            jPanel_MonicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
            .addGap(0, 634, Short.MAX_VALUE)
            .addGroup(jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_RossLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(Recap_Ross, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );
        jPanel_RossLayout.setVerticalGroup(
            jPanel_RossLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
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
                .addComponent(Recap_Autre, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_AutreLayout.setVerticalGroup(
            jPanel_AutreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Recap_Autre, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
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
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Statistique", jPanel_Statistique);
        jPanel_Statistique.getAccessibleContext().setAccessibleName("Statistique_");

        imagePanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );

        imagePanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imagePanel3Layout = new javax.swing.GroupLayout(imagePanel3);
        imagePanel3.setLayout(imagePanel3Layout);
        imagePanel3Layout.setHorizontalGroup(
            imagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        imagePanel3Layout.setVerticalGroup(
            imagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        imagePanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imagePanel4Layout = new javax.swing.GroupLayout(imagePanel4);
        imagePanel4.setLayout(imagePanel4Layout);
        imagePanel4Layout.setHorizontalGroup(
            imagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        imagePanel4Layout.setVerticalGroup(
            imagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        imagePanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imagePanel5Layout = new javax.swing.GroupLayout(imagePanel5);
        imagePanel5.setLayout(imagePanel5Layout);
        imagePanel5Layout.setHorizontalGroup(
            imagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanel5Layout.setVerticalGroup(
            imagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagePanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(imagePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagePanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("tab2", jPanel4);

        imagePanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout imagePanel2Layout = new javax.swing.GroupLayout(imagePanel2);
        imagePanel2.setLayout(imagePanel2Layout);
        imagePanel2Layout.setHorizontalGroup(
            imagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 739, Short.MAX_VALUE)
        );
        imagePanel2Layout.setVerticalGroup(
            imagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );

        jLabel2.setText("Comme nous pouvons le voir. Au fil des saisons. Les personnages sont en général plus positif à la fin de la série.    ");

        jLabel3.setText("Avec des grosses tendances visible sur une saison 2 marqué par un grand pic de positivité de phoebe et une grande baisse de Monica.");

        jLabel4.setText(" Nous pouvons noter également les pic de joey saison6 et chandler saison8");

        javax.swing.GroupLayout Evolution_positiviteLayout = new javax.swing.GroupLayout(Evolution_positivite);
        Evolution_positivite.setLayout(Evolution_positiviteLayout);
        Evolution_positiviteLayout.setHorizontalGroup(
            Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_positiviteLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(Evolution_positiviteLayout.createSequentialGroup()
                        .addGroup(Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(imagePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Evolution_positiviteLayout.setVerticalGroup(
            Evolution_positiviteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Evolution_positiviteLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imagePanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("tab3", Evolution_positivite);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(30, 40, 760, 540);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Evolution_positivite;
    private javax.swing.JLabel Recap_Autre;
    private javax.swing.JLabel Recap_Chandler;
    private javax.swing.JLabel Recap_Joey;
    private javax.swing.JLabel Recap_Monica;
    private javax.swing.JLabel Recap_Phoebe;
    private javax.swing.JLabel Recap_Rachel;
    private javax.swing.JLabel Recap_Ross;
    private javax.swing.JLabel different;
    private controller.ImagePanel imagePanel1;
    private controller.ImagePanel imagePanel2;
    private controller.ImagePanel imagePanel3;
    private controller.ImagePanel imagePanel4;
    private controller.ImagePanel imagePanel5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Autre;
    private javax.swing.JPanel jPanel_Chandler;
    private javax.swing.JPanel jPanel_Joey;
    private javax.swing.JPanel jPanel_Monica;
    private javax.swing.JPanel jPanel_Phoebe;
    private javax.swing.JPanel jPanel_Rachel;
    private javax.swing.JPanel jPanel_Ross;
    private javax.swing.JPanel jPanel_Statistique;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel negatif;
    private javax.swing.JLabel positif;
    private javax.swing.JLabel temporaire;
    // End of variables declaration//GEN-END:variables

}

