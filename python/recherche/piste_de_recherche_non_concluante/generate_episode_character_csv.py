import kagglehub
import os
import re
import pandas as pd

# Dataset depuis Kaggle (voir site)
dataset_path = kagglehub.dataset_download("blessondensil294/friends-tv-series-screenplay-script")
print("Chemin:", dataset_path, "\nAperçu contenu: ", os.listdir(dataset_path)[:4], "...")

# Dataframe vide
mainData = pd.DataFrame(columns=['season', 'episode', 'episodename'])

# Traitement des fichiers
for (x, filename) in enumerate(os.listdir(dataset_path)):
    if filename.endswith(".txt"):  # Vérifier que c'est un fichier texte
        match = re.search(r"[sS](\d{2})E(\d{2})\s+(.*)\.txt", filename)
        if match:
            season = f"S{match.group(1)}"
            episode = f"E{match.group(2)}"
            episode_name = match.group(3)

            mainData = pd.concat([mainData, pd.DataFrame([{'season': season, 'episode': episode, 'episodename': episode_name}])], ignore_index=True)
            

# Création d'un csv avec la liste des noms d'épisodes pour étudier les personnages centraux
mainData.to_csv("friends_episode_names.csv", index=False)



# Liste des personnages
characters = ["Rachel", "Ross", "Monica", "Chandler", "Joey", "Phoebe"]

# Ajouter colonnes booléennes (mention ou non dans le titre)
for character in characters:
    mainData[character] = mainData['episodename'].str.contains(character, case=False, na=False).astype(int)

# Grouper par saison et faire la somme
mention_counts = mainData.groupby('season')[characters].sum().reset_index()

print(mention_counts)