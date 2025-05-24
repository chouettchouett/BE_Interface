# -*- coding: utf-8 -*-
import csv
import os
from collections import Counter
import re
from java.io import File, IOException, FileOutputStream
from org.jfree.chart import ChartFactory
from org.jfree.chart.plot import PlotOrientation
from org.jfree.data.category import DefaultCategoryDataset
from java.awt import Graphics2D
from javax.imageio import ImageIO
from java.awt.image import BufferedImage
from java.awt import Rectangle

STOP_WORDS = {"i", "you", "the", "a", "and", "it", "that", "of", "to", "in", "for", "on", "with", "is", "was", "he", "she", "we", "they", "be", "been", "being", "am", "are", "as", "by", "at", "this", "which", "not", "but", "from", "so", "up", "down", "all", "can", "could", "would", "should", "okay", "what", "oh", "no", "im", "just", "me", "yeah", "my", "do", "ok", "her", "its", "him", "did", "youre", "have", "dont", "your"}

def nombre_repliques(saison, episode):
    saison_data = []

    for row in mainData:
        if row['season'] == saison:
            if episode is None or row['episode'] == episode:
                saison_data.append(row)

    # Compter les répliques par personnage
    counts = {}
    for row in saison_data:
        personnage = row['character']
        counts[personnage] = counts.get(personnage, 0) + 1

    return counts



def save_chart_as_png(chart, filename):
    #Définir le dossier de sortie
    output_dir = os.path.join(os.getcwd(), "src", "main", "resources", "les_png")

    #Créer le dossier s'il n'existe pas
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    #Chemin complet vers le fichier
    full_path = os.path.join(output_dir, filename)

    #Créer un BufferedImage avec les dimensions souhaitées
    width = 600
    height = 371
    image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    #Créer un objet Graphics2D à partir du BufferedImage
    g2 = image.createGraphics()

    #Définir la zone où le graphique sera dessiné
    chart_area = Rectangle(0, 0, width, height)

    #Dessiner le graphique dans l'image
    chart.draw(g2, chart_area)

    #Sauvegarder l'image au format .png
    try:
        out_file = FileOutputStream(full_path)
        ImageIO.write(image, "PNG", out_file)
        out_file.close()
        print("Graphique sauvegardé sous {}".format(full_path))
    except Exception as e:
        print("Erreur lors de la sauvegarde du graphique : ", e)

def graph_nombre_de_repliques_saison(saison):
    data = nombre_repliques(saison, None)  #Dictionnaire : {personnage: nb_repliques}

    # Créer le dataset
    dataset = DefaultCategoryDataset()
    for personnage, nb_repliques in data.items():
        dataset.addValue(nb_repliques, "Répliques", personnage)

    #Créer le graphique
    chart = ChartFactory.createBarChart(
        "Nombre de repliques par personnage - Saison {}".format(saison),
        "Personnage",
        "Nombre de repliques",
        dataset,
        PlotOrientation.VERTICAL,
        False,
        True,
        False
    )

    #Sauvegarder le graphique
    save_chart_as_png(chart, "repliques_saison_{}.png".format(saison))


def graph_nombre_de_repliques_episode(saison, episode):
    #Filtrer les données pour la saison et l'épisode
    data = nombre_repliques(saison, episode)  # Dictionnaire : {personnage: nb_repliques}

    #Créer le dataset
    dataset = DefaultCategoryDataset()
    for personnage, nb_repliques in data.items():
        dataset.addValue(nb_repliques, "Répliques", personnage)

    #Créer le graphique
    chart = ChartFactory.createBarChart(
        "Nombre de repliques par personnage - Saison {} - Episode {}".format(saison, episode),
        "Personnage",
        "Nombre de repliques",
        dataset,
        PlotOrientation.VERTICAL,
        False,
        True,
        False   #
    )

    #Sauvegarder le graphique
    save_chart_as_png(chart, "repliques_saison_{}_episode_{}.png".format(saison, episode))


def top_mots_episode(saison, episode):

    #Retourne les 10 mots les plus fréquents dans un épisode donné

    mots = []
    mots_episode = []

    for row in mainData:
        if row['season'] == saison and row['episode'] == episode:
            texte = row.get('line') or row.get('dialog') or ""

            #Nettoyage : suppression de la ponctuation, passage en minuscule
            texte = re.sub(r"[^\w\s]", "", texte).lower()
            mots_episode += texte.split()

            #enlève les mots inintéressants
            mots += [mot for mot in mots_episode if mot not in STOP_WORDS]

    compteur = Counter(mots)
    return compteur.most_common(10)


def top_mots_saison(saison):

    #Retourne les 10 mots les plus fréquents dans une saison donnée

    mots = []
    mots_saison = []

    for row in mainData:
        if row['season'] == saison:
            texte = row.get('line') or row.get('dialog') or ""

            #Nettoyage : suppression de la ponctuation, passage en minuscule
            texte = re.sub(r"[^\w\s]", "", texte).lower()
            mots_saison += texte.split()

            #enlève les mots inintéressants
            mots += [mot for mot in mots_saison if mot not in STOP_WORDS]

    compteur = Counter(mots)
    return compteur.most_common(10)



def get_personnages_saison(saison):

    #Renvoie la liste des personnages présents dans une saison

    personnages = []
    noms_vus = {}

    for row in mainData:
        if row['season'] == saison:
            nom = row['character'].strip()
            if nom not in noms_vus:
                noms_vus[nom] = True
                personnages.append(nom)

    return personnages

def get_personnages_episode(saison, episode):

    #Renvoie la liste des personnages présents dans un episode

    personnages = []
    noms_vus = {}

    for row in mainData:
        if (row['season'] == saison and row['episode'] == episode):
            nom = row['character'].strip()
            if nom not in noms_vus:
                noms_vus[nom] = True
                personnages.append(nom)

    return personnages

def compter_repliques_episode(saison, episode):

    #compte le nombre de repliques dans un episode donné

    compteur = sum(1 for row in mainData if row['season'] == saison and row['episode'] == episode)
    return compteur



def compter_repliques_saison(saison):

    #compte le nombre de repliques dans un episode donné

    compteur = sum(1 for row in mainData if row['season'] == saison)
    return compteur




def nombre_mot_replique_saison(saison):
    total_mots = 0
    total_repliques = 0

    # Normaliser le format de la saison (accepte "1", "01", "S1", "S01")
    saison_norm = saison.upper().replace("S", "").zfill(2)
    
    for row in mainData:
        row_season = row['season'].upper().replace("S", "").zfill(2)
        if row_season == saison_norm:
            texte = row['line'].strip()
            if texte:
                texte = re.sub(r"[^\w\s']", " ", texte)  # Conserver les apostrophes
                mots = [mot for mot in texte.split() if mot]
                total_mots += len(mots)
                total_repliques += 1

    return round(total_mots / total_repliques, 2) if total_repliques > 0 else 0.0

def nombre_mot_replique_episode(saison, episode):
    total_mots = 0
    total_repliques = 0

    # Normaliser les formats
    saison_norm = saison.upper().replace("S", "").zfill(2)
    episode_norm = episode.upper().replace("E", "").zfill(2)
    
    for row in mainData:
        row_season = row['season'].upper().replace("S", "").zfill(2)
        row_episode = row['episode'].upper().replace("E", "").zfill(2)
        
        if row_season == saison_norm and row_episode == episode_norm:
            texte = row['line'].strip()
            if texte:
                texte = re.sub(r"[^\w\s']", " ", texte)
                mots = [mot for mot in texte.split() if mot]
                total_mots += len(mots)
                total_repliques += 1

    return round(total_mots / total_repliques, 2) if total_repliques > 0 else 0.0

def graph_evolution_mots_par_replique_episode(saison, episode):
    # Convertir les paramètres en format string standard (accepte int ou string)
    saison_str = str(saison).upper().replace("S", "").zfill(2)
    episode_str = str(episode).upper().replace("E", "").zfill(2)
    
    # Créer le dataset
    dataset = DefaultCategoryDataset()
    
    # Variables pour le calcul par tranche
    tranche_size = 10
    tranche_num = 1
    mots_tranche = 0
    repliques_tranche = 0
    
    for row in mainData:
        row_season = str(row['season']).upper().replace("S", "").zfill(2)
        row_episode = str(row['episode']).upper().replace("E", "").zfill(2)
        
        if row_season == saison_str and row_episode == episode_str:
            texte = row['line'].strip()
            if texte:
                texte = re.sub(r"[^\w\s']", " ", texte)
                mots = texte.split()
                mots_tranche += len(mots)
                repliques_tranche += 1
                
                if repliques_tranche >= tranche_size:
                    moyenne = round(float(mots_tranche) / repliques_tranche, 2)
                    dataset.addValue(moyenne, "Mots par replique", "Tranche " + str(tranche_num))
                    tranche_num += 1
                    mots_tranche = 0
                    repliques_tranche = 0
    
    # Dernière tranche incomplète
    if repliques_tranche > 0:
        moyenne = round(float(mots_tranche) / repliques_tranche, 2)
        dataset.addValue(moyenne, "Mots par replique", "Tranche " + str(tranche_num))
    
    # Créer le graphique
    chart = ChartFactory.createLineChart(
        "Evolution du nombre de mots par replique - Saison " + saison_str + " Episode " + episode_str,
        "Tranches de repliques (par 10)",
        "Nombre moyen de mots par replique",
        dataset,
        PlotOrientation.VERTICAL,
        True,
        True,
        False
    )
    
    # Sauvegarder le graphique
    #save_chart_as_png(chart, "evolution_mots_replique_s" + saison_str + "_e" + episode_str + ".png")
    save_chart_as_png(chart, "evolution_mots_replique_{}_episode_{}.png".format(saison, episode))

def graph_evolution_mots_par_replique_saison(saison):
    # Convertir le paramètre en format string standard
    saison_str = str(saison).upper().replace("S", "").zfill(2)
    
    # Creer le dataset
    dataset = DefaultCategoryDataset()
    
    # Dictionnaire pour stocker les donnees par episode
    episodes_data = {}
    
    # Remplir le dictionnaire avec les donnees
    for row in mainData:
        row_season = str(row['season']).upper().replace("S", "").zfill(2)
        if row_season == saison_str:
            episode_num = int(str(row['episode']).upper().replace("E", ""))
            texte = row['line'].strip()
            
            if texte:
                mots = re.sub(r"[^\w\s']", " ", texte).split()
                if episode_num not in episodes_data:
                    episodes_data[episode_num] = {'total_mots': 0, 'total_repliques': 0}
                
                episodes_data[episode_num]['total_mots'] += len(mots)
                episodes_data[episode_num]['total_repliques'] += 1
    
    # Trier les episodes par numero et ajouter au dataset
    for ep_num in sorted(episodes_data.keys()):
        data = episodes_data[ep_num]
        moyenne = round(float(data['total_mots']) / data['total_repliques'], 2)
        dataset.addValue(moyenne, "Mots/replique", str(ep_num))
    
    # Creer le graphique
    chart = ChartFactory.createLineChart(
        "Evolution du nombre de mots par replique - Saison " + saison_str,
        "Numero d'episode", 
        "Moyenne mots par replique",
        dataset,
        PlotOrientation.VERTICAL,
        True,
        True,
        False
    )
    
    # Sauvegarder le graphique
    #save_chart_as_png(chart, "mots_par_replique_s" + saison_str + ".png")
    save_chart_as_png(chart, "mots_par_replique_{}.png".format(saison))
    
    
    





mainData = []
file_path = os.path.join(os.getcwd(), "src", "main", "python", "friends_dialogues.csv")
with open(file_path, 'r') as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        mainData.append(row)



    
