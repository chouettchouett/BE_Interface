import pandas as pd
import os
import re
from collections import Counter
import csv

# Chemin vers le CSV d'entrée
chemin_csv = os.path.join(os.path.dirname(__file__), "..", "Analyse_Sentiments", "friends_dialogues_final.csv")
chemin_csv = os.path.abspath(chemin_csv)

# Chemin vers le CSV de sortie (adapter si besoin)
csv_path = os.path.join(
    os.path.dirname(__file__),
    "..", "..", "src", "main", "resources", "csv", "csv_personnage.csv"
)
csv_path = os.path.abspath(csv_path)

STOP_WORDS = {"i", "you", "the", "a", "and", "it", "that", "of", "to", "in", "for", "on", "with", "is", "was", "he", "she", "we", "they", "be", "been", "being", "am", "are", "as", "by", "at", "this", "which", "not", "but", "from", "so", "up", "down", "all", "can", "could", "would", "should", "okay", "what", "oh", "no", "im", "just", "me", "yeah", "my", "do", "ok", "her", "its", "him", "did", "youre", "have", "dont", "your"}

main_characters = ["Chandler", "Joey", "Monica", "Phoebe", "Rachel", "Ross"]

def count_lines_by_character_and_season():
    df = pd.read_csv(chemin_csv, encoding='utf-8')
    count = df.groupby(['character', 'season']).size().reset_index(name='line_count')
    pivot = count.pivot(index='character', columns='season', values='line_count').fillna(0).astype(int)
    pivot_dict = pivot.reset_index().to_dict(orient='records')
    return pivot_dict

def get_nb_replique(nom_personnage):
    results = count_lines_by_character_and_season()
    for d in results:
        if d["character"].lower() == nom_personnage.lower():
            return sum([v for k, v in d.items() if k != "character"])
    return 0

def get_pourcentage(nom_personnage):
    results = count_lines_by_character_and_season()
    total = 0
    perso_total = 0
    for d in results:
        s = sum([v for k, v in d.items() if k != "character"])
        total += s
        if d["character"].lower() == nom_personnage.lower():
            perso_total = s
    if total == 0:
        return 0.0
    return perso_total / total * 100

def get_position_parmi_les_personnages(nom_personnage):
    results = count_lines_by_character_and_season()
    scores = []
    for d in results:
        total = sum([v for k, v in d.items() if k != "character"])
        scores.append((d["character"], total))
    # Tri décroissant (plus de répliques = plus haut)
    scores.sort(key=lambda x: x[1], reverse=True)
    for i, (name, total) in enumerate(scores, 1):
        if name.lower() == nom_personnage.lower():
            return i, len(scores)
    return None, len(scores)

def get_top_mots_personnage(nom_personnage, n=5):
    df = pd.read_csv(chemin_csv, encoding='utf-8')
    mots = []
    for _, row in df.iterrows():
        if row["character"].lower() == nom_personnage.lower():
            texte = row.get('line') or row.get('dialog') or ""
            texte = re.sub(r"[^\w\s]", "", texte).lower()
            mots += [mot for mot in texte.split() if mot not in STOP_WORDS]
    compteur = Counter(mots)
    return [mot for mot, count in compteur.most_common(n)]

# ------ Génération du CSV final ------
with open(csv_path, "w", newline='', encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(["character", "nb_repliques", "pourcentage", "position", "top_mots"])
    for character in main_characters:
        nb_repliques = get_nb_replique(character)
        pourcentage = get_pourcentage(character)
        position, _ = get_position_parmi_les_personnages(character)
        top_mots = get_top_mots_personnage(character, 5)
        # Si jamais moins de 5 mots (rare), on complète avec vide
        while len(top_mots) < 5:
            top_mots.append("")
        mots_ensemble = ", ".join(top_mots)  # ou " ".join(top_mots) si tu préfères
        writer.writerow([character, nb_repliques, f"{pourcentage:.2f}", position, mots_ensemble])

print("csv_personnage.csv généré dans le dossier resources/csv !")
