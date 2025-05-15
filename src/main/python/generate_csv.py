# -*- coding: utf-8 -*-
"""
Script à lancer une seule fois pour générer :
- csv_utils.csv (un seul fichier pour tout le projet)
depuis friends_dialogues.csv
À mettre dans src/main/python/ et à lancer là où il y a le CSV.
"""

import csv
import re
from collections import Counter
import os

STOP_WORDS = {
    "i", "you", "the", "a", "and", "it", "that", "of", "to", "in", "for", "on", "with", "is", "was", "he",
    "she", "we", "they", "be", "been", "being", "am", "are", "as", "by", "at", "this", "which", "not", "but",
    "from", "so", "up", "down", "all", "can", "could", "would", "should", "okay", "what", "oh", "no", "im",
    "just", "me", "yeah", "my", "do", "ok", "her", "its", "him", "did", "youre", "have", "dont", "your"
}

def top_mots_episode(saison, episode):
    mots = []
    mots_episode = []
    for row in mainData:
        if row['season'] == saison and row['episode'] == episode:
            texte = row.get('line') or row.get('dialog') or ""
            texte = re.sub(r"[^\w\s]", "", texte).lower()
            mots_episode += texte.split()
            mots += [mot for mot in mots_episode if mot not in STOP_WORDS]
    compteur = Counter(mots)
    return compteur.most_common(10)

def top_mots_saison(saison):
    mots = []
    mots_saison = []
    for row in mainData:
        if row['season'] == saison:
            texte = row.get('line') or row.get('dialog') or ""
            texte = re.sub(r"[^\w\s]", "", texte).lower()
            mots_saison += texte.split()
            mots += [mot for mot in mots_saison if mot not in STOP_WORDS]
    compteur = Counter(mots)
    return compteur.most_common(10)

input_csv = os.path.join(os.path.dirname(__file__), "friends_dialogues.csv")
mainData = []
with open(input_csv, "r", encoding="utf-8") as csvfile:
    reader = csv.DictReader(csvfile)
    for row in reader:
        mainData.append(row)


saisons = sorted(set(row['season'] for row in mainData), key=lambda x: int(x.lstrip("Ss")))
episodes = sorted(
    set((row['season'], row['episode']) for row in mainData),
    key=lambda x: (int(x[0].lstrip("Ss")), int(x[1].lstrip("Ee")))
)

output_csv = os.path.join(os.path.dirname(__file__), "..", "resources", "csv", "csv_utils.csv")
output_csv = os.path.abspath(output_csv)  
os.makedirs(os.path.dirname(output_csv), exist_ok=True)

with open(output_csv, "w", newline='', encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(['type', 'season', 'episode', 'top10'])

    for saison in saisons:
        top10 = top_mots_saison(saison)
        mots = [mot for mot, _ in top10]
        writer.writerow(['SAISON', saison, '', ";".join(mots)])
        print(saison)

    for saison, episode in episodes:
        top10 = top_mots_episode(saison, episode)
        mots = [mot for mot, _ in top10]
        writer.writerow(['EPISODE', saison, episode, ";".join(mots)])
        print(saison, episode)

print(f"c'est bon")
