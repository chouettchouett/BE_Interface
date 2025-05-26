/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author ember
 */
public class RechercheInterpretations {
    
    public static Map<String, List<String>> getPersoFromNom(String nom) {
        return switch (nom) {
            case "ross" -> ROSS;
            case "monica" -> MONICA;
            case "rachel" -> RACHEL;
            case "phoebe" -> PHOEBE;
            case "joey" -> JOEY;
            case "chandler" -> CHANDLER;
            default -> null;
        };
    }
    
    public static final Map<String, List<String>> ROSS = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 1 : Hautes valeurs, on raconte sa vie de famille compliqué, divorce et un enfant bientôt naît, qui se traduit par de l'angoisse (peur qui évolue
dans l'analyse de sentiments), personnage très présent dés la saison 1 (d'après la recherche par saison)
Saison 2 : Triangle amoureux (Ross-Julie-Rachel) : montée d'amour (analyse sentiments) : Ross fait une liste pour comparer Julie et Rachel, il aime
presque autant les deux alors le nombre de dialogues d'amour augmente. Les dialogues sont assez centré sur Rachel et Julie, il est donc moins 
mentionné par les autres mais très actif (meilleur tendance de parole de la série).
Il se met finalement en couple avec Rachel au milieu de la saison, ce qui le rapprochera du groupe. 
Saison 3 : Sommet de son intrigue, Ross montre 3 thèmes connus : l'amour, la séparation et Rachel. Il est ainsi beaucoup mentionné, tout le monde
parle de son fameux "We were on a break" culte de la série, le moment où il croit que Rachel veut rompre et couche avec une fille le soir même, 
alors que Rachel voulait uniquement faire une pause. L'amour moyen malgré le couple chute donc un peu, mais la tristesse est à son maximum
dû donc à la séparation mi-saison.
Saison 8 : On apprend que Rachel est enceinte, grand évènement de la série, Ross doit être présent et est donc mis en avant avec Rachel.
Saison 10 : Assez haut en répliques, cette saison Ross découvre qu'il aime encore Rachel, confirmé par la tristesse et l'amour qui augmente.

Impression globale : Ross a moins de pic d'activité, c'est le personnage le plus mis en avant de la série d'après la recherche par saison, il est
régulier et souvent présent avec nos 6 amis comme en témoigne les graphes ci-dessous ou la centralité est plutôt constantes au alentour de 82%.
Ross fonctionne beaucoup sur l'amour notamment ses querelles amoureuses au début de la série et son retour à la grossesse de Rachel.
Les saisons 1, 2 et 3 seront étudiés via les mots dans l'onglet suivant.""")),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
On remarque directement des lexiques de sciences :
paleontology, evolution
mais aussi des lexiques de professeur :
correct, professor, student, department
En référence à son métier d'enseignant
dans la série et à sa passion pour la
paléontologie.
""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
On remarque beaucoup ses actes très solo,
comme en saison 1 avec Susan et Carol.
Rappel du mot "correct", de son rôle de
professeur.""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Thème : l'enfant de Ross et de son ex petite amie Carol, avec Susan la
nouvelle copine de Carol ("lesbian").
Peu d'évolution, parle juste de son fils et sa vie d'avant.
""", """
Accent mis sur Rachel par rapport à la dernière saison, mais peu de
     mots réellement informatifs.""", """
Mots marquants 2 arcs repéré en analyse de sentiments :
- sweetie, girlfriend (Amour)
- trying, break (Tristesse, Regret)
La recherche par mots confirme que ces 4 mots concerne la
relation avec Rachel, de l'amour à la séparation en saison 3""")),
            
            entry("TEXTS_CENTRALITE", List.of("""
Déjà très bien intégré et central, Ross en pleine
réflexion amoureuse par rapport à Rachel.
Il reçoit beaucoup de conseil de Joey.""", """
Toujours conseillé par Joey, la relation se crée
mais n'est pas visible, ils se mettent ensemble
seulement au milieu de la saison.""", """
Hausse énorme vers Rachel et non pas 
uniquement par amour, pour deux raisons :
- Amour (début de saison)
- Tristesse, regret (fin de saison)""", "Début de ses saisons en baisse de mise en avant", """
Taux de centralité bas par rapport à 
l'observation du rapprochement du groupe""", "", "", """
Relance de sa mise en scène : rapprochement
avec Rachel pour préparer l'arrivée du
bébé. Joey, devenant proche de Rachel, 
parle beaucoup avec Ross.
Très proche des deux, il est moins central
sur les 6 amis""", "", """
Centralité faible : même raison qu'en
saison 8, Joey-Rachel-Ross."""
)));
    
    public static final Map<String, List<String>> MONICA = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 1 : Monica est le centre initial du groupe (soeur de Ross, meilleure amie de Rachel, ami de longue date de Joey et Chandler) et
                le début de l'intrigue se passe dans son appartement. On découvre donc principalement la série avec elle.
Saison 5 : remontée soudaine grâce au rapprochement du groupe d'après l'Analyse Sentiment (ainsi que les graphes orientés ci-dessous qui ont,
                 pour chaque personnage, l'une des plus haute valeur de centralité) et au rapprochement avec Chandler (observé dans les sentiments
                 récurrents : montée d'amour en saison 5).
Saison 7 : Augmentation prévisible dû au mariage de Monica et Chandler, tout le monde parle de l'évènement (un pic énorme au niveau des
                mentions qui témoigne d'un réel impact de celles-ci, contrairement à leur taux de répliques qui n'a pas réellement varié)
Saison 9 : Après une saison 8 moins marquée (pause dans leur intrigue dû à un gros pic dans la saison 7 et à la grossesse de Rachel)
                En saison 9 on les revoit après avoir constater qu'ils ne peuvent pas avoir d'enfant, ils envisage l'adoption (Monica parle
                beaucoup mais peu de mentions : les discussions se passent entre les deux compagnons)
Impression globale : des pics assez marquants et logiques vis-à-vis de la série. La saison 5 n'est pas si significative étant donné
que l'évolution marque tout le groupe. Les saisons 1, 7 et 9 seront étudiés via les mots dans l'onglet suivant.""")),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
Vocabulaire de la cuisine directement :
- guest, chip, plates, potatoes
Son métier et sa passion le long de la série.
Thème de l'adoption très évoqué à la fin de la série""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
Comme le graphe à gauche, on nous confirme sa 
passion pour la cuisine. Peu de personnage distinctif.
""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Peu d'informations par rapport à sa saison riche en répliques
""", """
Moment marquant, elle parle de sa famille
(family, parents, dad, mom). Elle parle de son
mariage (dress, engaged, invited).""", """
Un peu moins clair, on remarque un thème : 
l'ovulation, liée à l'infertilité du couple, 
abordé dans cette saison.""")),
            
            entry("TEXTS_CENTRALITE", List.of("""
Monica connait bien Phoebe (ancienne
coloc), et Rachel (meilleur amie)
Joey et Phoebe sont en retrait au 
début de la série.""", """
Baisse de mise en avant
mais renforcement de centralité,
(ses mentions augmentent aussi par
rapport à la saison 1)
plusieurs arcs notamment la
relation avec Richard qui fait
parler sur tout le groupe""", "", "", """
Hausse surprenante significative
de mention entre la saison 4 et 5
entre Chandler et Monica : ils
sortent ensemble.
Monica est très concerné par le
phénomène d'union lobale du 
groupe d'ami en saison 5.""", "", "", """
Centralité très faible, biaisé
par un taux de mentions très faible
observé dans le graphe de mise en 
avant.""", "", "", """
Le couple Chandler-Monica aura
donc été stable de la saison 5
à 10."""
)));
    
    public static final Map<String, List<String>> RACHEL = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 1 : un des personnages impliqués en saison 1 pour l'introduction de la série
Saison 7 : très peu mentionné, elle évolue beaucoup professionnellement chez Ralph Lauren
Saison 8 : augmentation évidente : la grossesse de Rachel et le début de rapprochement avec Joey.

Impression globale : Personnage constant et impactant. Deux gros pics différent : un sur les réplique, un
sur les deux facteurs. Les saisons 1, 7 et 8 seront étudiés via les mots dans l'onglet suivant.""")),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
On remarque des mots lié à son métier :
- assistant, desk, copy, shoe
ainsi que Ralph Lauren, l'endroit
principale où elle travaille durant la série, 
qui représente aussi sa passion de la mode.
""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
On remarque principalement ses amours (joshua, barry),
avec qui elle a beaucoup de moment en solo (distinctif)
mais aussi son métier/passion : Ralph Lauren (la mode)""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Peu de mots très similaire et utiles""", """
Vocabulaire lié à son métier
- chair, office, desk, assistant
Elle prend plus de responsabilités chez
Ralph Lauren.""", """
Début de développement de la relation
avec Joey (honey, date..) et grossesse
(pas de mots repérable ici, le bébé n'
est pas encore là.)""")),
            
            entry("TEXTS_CENTRALITE", List.of("""
Personnage très marqué dés le début. Parle moins
à Joey et Phoebe car ces deux sont moins impliqué
au début de la série. Echanges avec Ross déjà
bien représenté.""", "", "", """
Pic de centralité due à son éloignement
de Ross, elle parle plus à tout le monde
en moyenne""", "", "", """
Centralité faible : très centré sur sa
carrière, son développement solo : 
énorme presque-pic de réplique, très peu
mentionnée par le groupe.""", """
Grossesse, implication grandissante 
de Ross et début d'amitié-amour avec Joey""", "", """
Ross est persuadé qu'il aime Rachel et 
l'empêche de partir à Paris."""
)));
    
    public static final Map<String, List<String>> JOEY = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 2 : Premier développement de sa carrière d'acteur après une saison 1 très discrète, se fait virer à la fin de la saison
Saison 4-5 : Deuxième remontée de sa carrière : dans le théâtre
Saison 7 : montée brusque, surtout dans les mentions : Joey retrouve son rôle après s'être fait virer en saison 2 !
Saison 8 : Toujours acteur, il joue un grand soutient émotionnelle pour la grossesse de Rachel, il lui déclare ses sentiments cette saison.

Impression globale : comme Phoebe, c'est un personnage qui ne se développe pas directement, mais dont on repère la profondeur qu'à partir de la
moitié de la série. """)),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
On retrouve ici un "portrait" de Joey (métier/passion, expression culte (comin), 
obsession nourriture (fridge), et le "thursday", un épisode où il ne sait
jamais quel jour on est, montrant sa naïveté intellectuelle.
""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
Encore une fois, comme le graphe à gauche, la passion
d'acteur est confirmé, peu de personnage secondaire ici
par rapport aux autres personnages""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Mots peu intéressants""", """
Retour de la carrière de Joey, 
- audition, jessica (actrice avec lui)
Il commence à parler rapidement de truc
personnel ("parents"), une anectode mais
pas d'évolution.""", """
Carrière pareil qu'en saison 7, mais
une profondeur prend part dans son dialogue, 
concernant Rachel, "stupid", "worry", "date", 
c'est la saison où il lui avoue ses sentiments""", """
Mots peu marquants, assez classique de Joey.""")),
            
            entry("TEXTS_CENTRALITE", List.of("""
Début, naïf et drôle mais peu de répliques, 
ne montrent pas grand chose.
Coloc de Chandler, pourtant peu de discussion""", """
Développement énorme de 
l'amitié avec Chandler""", "", "",  """
Intégration au sein du groupe, confirmé dans
l'analyse sentiment qui évoque une stabilisation
du groupe et une positivité grandissante.""", "", """
Emménagement avec Rachel""", """
Baisse de discussion avec Chandler, dûe
au mariage, centrage sur Rachel-Ross
(grossesse et soutient émotionnel)""", """
Peu d'évolution par rapport à la saison d'avant,
confirmé par ces mots caractéristiques saison 9""", """
Emménagement chez Chandler"""
)));
    
    public static final Map<String, List<String>> CHANDLER = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 4 : Chandler tombe amoureux de Kathy, la copine de Joey.
Saison 6 et 7 : préparatif du mariage avec Monica puis mariage
Saison 8 : centré sur la grossesse de Rachel (simple amie de Chandler), Chandler est complètement invisibilisé
Saison 9 et 10 : adoption et stabilité dans son métier, observation d'une baisse de négativité dans l'analyse de sentiments

Impression globale : Chandler est toujours relativement impliquant de manière constante, sauf en saison 8""")),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
Personnage difficile à cerner, le sarcasme est difficile à
repérer à travers une analyse de mots, il l'utilise beaucoup.
On retrouve "dum", "gym" référence à des épisodes où Joey
l'inscrit à la salle de sport et il n'arrive pas à refuser,
"smoking" révèle son anxiété mais il fait des blagues aussi
sur ça.
""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
On retrouve des amours comme Janice, Tulsa, 
mais aussi Richard innatendu alors qu'il sortait
avec Monica, et le mot 'ring' en TOP 1
référence au mariage""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Peu de mots révélateurs.
""", """
Préparation de sa demande en mariage, 
"ring", "marry", "flowers"
""", "Mariage (wear, money)")),
            
            entry("TEXTS_CENTRALITE", List.of("", """
Amitié avec Joey tellement énorme
qu'il atteint un taux de centralité de
65%, un des plus bas score toute saisons
et personnage confondus""", "", """
Chandler parle beaucoup plus de Joey
avec le rapprochement avec Kathy""", """
Chandler se met en couple avec Monica et
parle beaucoup moins de Joey.""", "", """
Peu de changements, préparatif du mariage.""", """
""", "", "", "", ""
)));
    
    public static final Map<String, List<String>> PHOEBE = Map.ofEntries(
            entry("TEXT_TENDANCE", List.of("""
Saison 1 et 2 : peu intégré, histoire solo, de plus elle habite chez sa grand mère
Saison 3 : Première hausse de présence dûe principalement à son soutient prononcé à Monica après sa rupture avec Richard.
Saison 5 : Deuxième hausse observé pour beaucoup de personnage grâce à l'unification du groupe, Phoebe s'intègre grâce nottament à l'épisode
               du mariage secret de Monica et Chandler
Saison 7 : Comme Joey, investissement dans le mariage de Monica et Chandler
Saison 10 : Mariage avec Mike, beaucoup de répliques mais moins de mentions qui s'explique par l'épisode de fin qui mets en avant la fin de tous
les personnages

Impression globale : un profil comme Joey : naïf, décalé, drôle, qui ne s'intègre pas directement. Personnage sous-représenté d'après la recherche
par saison, qui parle à certains personnages en particulier (voir graphes ci-dessous).""")),
            
            entry("TEXT_MOT_GLOBAL", List.of("""
Les mots caractéristiques d'un personnage désignent les mots
fréquents chez ce personnage et peu fréquents chez tous les autres.
On retrouve un lexique de musique :
- songs, singing, guitar
ainsi que sa musique phare : "Smelly cat"
""")),
            
            entry("TEXT_MOT_GLOBAL_JOIE", List.of("""
On voit des personnages distinctif, David et Frank, 
dont elle parle peu au groupe.""")),
            
            entry("TEXTS_MOT_SAISON_IMPORTANTE", List.of("""
Difficiles à interpréter, on retrouve "selfness", un grand
épisode de dilemme morale, où Phoebe parle de son acte
de porter le futur enfant de son frère
""", """
Phoebe revoit David son amoureux en
saison 1.""", """
Le mot "married" arrive...""", """
Phoebe marie mike hannigan, elle utilise
du vocabulaire d'amis "friends", "friend", soit
pour un signe des scénariste au nom de la série, 
soit lié au fait que c'est l'un des seuls
personnages à avoir un haut taux de centralité""")),
            
            entry("TEXTS_CENTRALITE", List.of("""
Ancienne coloc de Monica.""", "", "", "", """
Meilleur taux de centralité de la série, 
on remarque que les deux personnages
les moins représenté et les plus
timides au début ont des taux 
énormes en saison 5.""", "", """
Préparatif du mariage avec Monica, 
hausse énorme entre Monica et Phoebe
""", "", """
Surprenant, Ross et Phoebe se parle
beaucoup plus, ils ont moins de
désaccord entre la spiritualité de
Phoebe et la rationnalité de Ross""", """
Phoebe conseille beaucoup Joey qui a, par 
rapport aux 5 autres personnages, une fin
moyenne.
"""
)));
}
