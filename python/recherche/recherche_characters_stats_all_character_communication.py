import pandas as pd
from collections import Counter
import re
import matplotlib.pyplot as plt
import networkx as nx
import numpy as np
import seaborn as sns

df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv", encoding='utf-8')

def clean_line(line):
    """
    Enlève les annotations/didascalies, les ensemble de mots entre parenthèses
    Mets en minuscule
    ...
    """
    line = re.sub(r'\([^)]*\)', '', line)
    # lower + clean special char + split
    return re.sub(r'[^\w\s]', '', line.lower()).split()

mentions_by_character = { 'monica': [], 'joey': [], 'chandler': [], 'phoebe': [], 'ross': [], 'rachel': [] }

# Etude des mentions entre personnages, qui mentionne le plus qui
# Nettoyage + Filtre : on garde tous les mots étant des noms des 6 personnages principaux
for _, row in df.iterrows():
    character = row['character'].lower()
    line = row['line']
    if character in mentions_by_character:  # Vérifier si le personnage est l'un des 6 principaux
        words = clean_line(line)  # Nettoyer et diviser la ligne en mots
        mentioned_character = [word for word in words if word in mentions_by_character.keys()]
        mentions_by_character[character].extend(mentioned_character)  # Ajouter les mots au personnage

mentions_stats = mentions_by_character.copy()
for character, mentions in mentions_by_character.items():
    mention_counts = Counter(mentions) # Compter les occurrences des mentions
    mention_counts[character] = 0
    mentions_stats[character] = mention_counts.most_common()  # Récupérer les 10 mots les plus fréquents


def generer_heatmap_global_mentions_personnage():
    # Transformation en DataFrame
    df = pd.DataFrame({
        speaker: dict(mentions) for speaker, mentions in mentions_stats.items()
    }).T # transposé pour avoir les personnages speaker en lignes

    # ordonne la heatmap comme mentions_by_character au-dessus
    df = df[list(mentions_by_character)]

    plt.figure(figsize=(10, 6))
    sns.heatmap(df, annot=True, fmt='d', cmap='Blues')
    plt.title("Nombre de fois où un personnage principal mentionne un autre personnage")
    plt.xlabel("Personnages mentionnés")
    plt.ylabel("Locuteurs")
    plt.tight_layout()
    plt.show()

def generer_graph_oriente_de_mentions_pour_le_character(character):
    plt.figure(figsize=(10, 6))
    # graphe orienté
    G = nx.DiGraph()
    for autre_character, nb_interactions in mentions_stats[character]:
        # on ignore les auto-mentions (un personnage qui se mentionne lui-même)
        if autre_character == character or nb_interactions == 0:
            continue

        G.add_edge(character.capitalize(), autre_character.capitalize(), weight=nb_interactions)
        # Nombre de mentions de autre_character à ce character
        reverse_interactions = dict(mentions_stats.get(autre_character, []))
        # vérification si autre character a bien mentionné au moins une fois character
        if character in reverse_interactions and reverse_interactions[character] > 0:
            G.add_edge(autre_character.capitalize(), character.capitalize(), weight=reverse_interactions[character])

    # Positionnement circulaire avec `character` au centre
    center_node = character.capitalize()
    edge_nodes = set(G) - {center_node}
    pos = nx.circular_layout(G.subgraph(edge_nodes))
    pos[center_node] = np.array([0, 0])

    # On dessine les nœuds et les labels
    nx.draw_networkx_nodes(G, pos, node_color='blue', alpha=0.4, node_size=3000)
    nx.draw_networkx_labels(G, pos, font_weight='bold')

    # On dessine les arcs aller-retour du graphe orienté avec les mentions entre personnages
    for u, v in G.edges():
        # courbe si on a un arc aller-retour, sinon pas de courbe (dans notre graphe on est supposé avoir que des aller-retours)
        rad = 0.1 if (v, u) in G.edges else 0.0
        nx.draw_networkx_edges(
            G, pos, edgelist=[(u, v)],
            arrowstyle='->',
            arrowsize=25,
            connectionstyle=f'arc3,rad={rad}',
            edge_color='grey'
        )

    def calcul_label_coord(p1, p2, rad):
        """
        On veut placer le label X entre deux points p1 et p2
             X
           -----    
          /     \
        p1       >p2
        """
        # point milieu entre p1 et p2
        middle_p1p2 = (p1 + p2) / 2
        # vecteur directeur entre p1 et p2
        director_vector__p1p2 = p2 - p1
        # vecteur perpendiculaire/normal (pointe vers X (ci-dessus)) au vecteur directeur pour placer le label
        norm_vector_p1p2 = np.array([-director_vector__p1p2[1], director_vector__p1p2[0]])
        # Normalisation pour que le label ne soit pas loin de la flèche
        # optionnel dans ce cas précis -> grâce à circular_layout, tous les points sont à une distance (norme) de 1 au point central
        norm_vector_p1p2 = norm_vector_p1p2 / np.linalg.norm(norm_vector_p1p2)
        # Normalisation optionnel ici aussi
        curve = rad * np.linalg.norm(director_vector__p1p2)
        return middle_p1p2 + norm_vector_p1p2 * curve
    
    # affichage des poids près des flèches (personalisés car non implémenté dans networkx)
    label_deja_affiche = set()
    # a : par exemple le point Chandler
    # b : par exemple le point Ross
    # data : donnée contenant le nombre de mentions de Ross par Chandler
    for a, b, data in G.edges(data=True):
        # si une des deux flèches aller ou retour a déjà été traité alors
        if (a, b) in label_deja_affiche or (b, a) in label_deja_affiche:
            continue # ignorer, passer au tour suivant

        label_deja_affiche.add((a, b))

        # si il y a une flèche retour
        # dans notre graphe il y a que des flèches retours
        if (b, a) in G.edges:
            coord_label_fleche_retour = calcul_label_coord(pos[a], pos[b], 0.1)
            coord_label_fleche_aller = calcul_label_coord(pos[a], pos[b], -0.1)

            plt.text(coord_label_fleche_aller[0], coord_label_fleche_aller[1], str(data['weight']))
            plt.text(coord_label_fleche_retour[0], coord_label_fleche_retour[1], str(G[b][a]['weight']))
        # si il y a une simple flèche aller, cas impossible ici, notre graphe n'a que des flèches aller-retour, on traite en cas de problème
        else:
            coord_label_fleche = calcul_label_coord(pos[a], pos[b], 0)
            plt.text(coord_label_fleche[0], coord_label_fleche[1], str(data['weight']))

    # suppression des axes (x, y), inutiles
    plt.axis('off')
    plt.tight_layout()
    plt.show()

generer_heatmap_global_mentions_personnage()
generer_graph_oriente_de_mentions_pour_le_character('chandler')