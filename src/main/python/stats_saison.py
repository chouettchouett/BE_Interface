# -*- coding: utf-8 -*-
# Version ultra-simple sans dépendances externes
import csv

def compter_repliques_episode(saison, episode):
    """
    Compte le nombre total de répliques pour un épisode spécifique
    :param saison: code de saison (ex: "S01") ou numéro (ex: 1)
    :param episode: code d'épisode (ex: "E01") ou numéro (ex: 1)
    :return: nombre total de répliques (int)
    """
    # Conversion des paramètres si nécessaire
    if isinstance(saison, int):
        saison_id = "S{:02d}".format(saison)
    else:
        saison_id = saison
        
    if isinstance(episode, int):
        episode_id = "E{:02d}".format(episode)
    else:
        episode_id = episode

    # Compteur de répliques
    count = 0
    
    # Lecture du fichier CSV
    with open("friends_dialogues.csv", 'r') as f:
        reader = csv.DictReader(f)
        for row in reader:
            if row['season'] == saison_id and row['episode'] == episode_id:
                count += 1
    
    return count

def compter_repliques_saison(saison):
    """
    Compte le nombre total de répliques pour une saison entière
    :param saison: code de saison (ex: "S01") ou numéro (ex: 1)
    :return: nombre total de répliques (int)
    """
    # Conversion du paramètre si nécessaire
    if isinstance(saison, int):
        saison_id = "S{:02d}".format(saison)
    else:
        saison_id = saison

    # Compteur de répliques
    count = 0
    
    # Lecture du fichier CSV
    with open("friends_dialogues.csv", 'r') as f:
        reader = csv.DictReader(f)
        for row in reader:
            if row['season'] == saison_id:
                count += 1
    
    return count


def nombre_de_repliques_episode(saison, episode):
    data = nombre_repliques(saison, episode)

    personnages = list(data.keys())
    lignes = list(data.values())

    plt.figure(figsize=(10, 6))
    bars = plt.bar(personnages, lignes)

    plt.title(f"Nombre de répliques par personnage - Saison {saison} Episode {episode}")
    plt.xlabel("Personnage")
    plt.ylabel("Nombre de répliques")
    plt.xticks(rotation = 45)

    #Afficher les valeurs sur les barres
    for bar in bars:
        height = bar.get_height()
        plt.text(bar.get_x() + bar.get_width()/2, height + 5, str(height), ha='center', va='bottom')

    plt.tight_layout()
    plt.savefig("repliques.png")


def evolution_repliques_par_episode(saison):
    # Filtrer les données pour la saison donnée
    data = mainData[mainData['season'] == saison]

    # Grouper par épisode et personnage, puis compter
    grouped = data.groupby(['episode', 'character']).size().unstack(fill_value=0)

    # Trier les épisodes dans l'ordre (ex: E01, E02, ..., E24)
    grouped = grouped.sort_index()

    # Tracer les courbes pour chaque personnage
    plt.figure(figsize=(12, 6))
    for character in grouped.columns:
        plt.plot(grouped.index, grouped[character], marker='o', label=character)

    plt.title(f"Évolution des répliques par personnage - {saison}")
    plt.xlabel("Épisode")
    plt.ylabel("Nombre de répliques")
    plt.xticks(rotation=45)
    plt.legend()
    plt.tight_layout()
    plt.show()

def evolution_repliques_par_saison():
    # Grouper par saison et personnage, puis compter
    grouped = mainData.groupby(['season', 'character']).size().unstack(fill_value=0)

    # Trier les saisons dans l'ordre (S01, S02, ...)
    grouped = grouped.sort_index()

    # Tracer les courbes
    plt.figure(figsize=(12, 6))
    for character in grouped.columns:
        plt.plot(grouped.index, grouped[character], marker='o', label=character)

    plt.title("Évolution des répliques par personnage - Toutes saisons")
    plt.xlabel("Saison")
    plt.ylabel("Nombre de répliques")
    plt.xticks(rotation=45)
    plt.legend()
    plt.tight_layout()
    plt.show()

def compter_repliques_saison(saison):
    saison_data = mainData[mainData['season'] == saison]
    return int(len(saison_data))

def compter_repliques_episode(saison, episode):
    episode_data = mainData[(mainData['season'] == saison) & (mainData['episode'] == episode)]
    return int(len(episode_data))

mainData = pd.read_csv("friends_dialogues.csv")
compter_repliques_episode("S03", "E12")
