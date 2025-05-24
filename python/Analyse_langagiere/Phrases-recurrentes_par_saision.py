import pandas as pd
import re
from collections import Counter
import matplotlib.pyplot as plt
import sys
sys.path.append("../recherche")
from get_saisons_et_episodes import get_saisons_et_episodes
from Phrases_recurentes import clean_and_split_sentences

# Charger les dialogues
df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv", encoding='utf-8')
df = df[df["character"].isin(["Chandler", "Joey", "Monica", "Phoebe", "Rachel", "Ross"])]

# Nettoyer les colonnes si besoin
df["season"] = df["season"].astype(str).str.extract(r"(\d+)").astype(int)
df["episode"] = df["episode"].astype(str).str.extract(r"(\d+)").astype(int)

# Obtenir la correspondance des épisodes par saison
saison_dict = get_saisons_et_episodes()

# Résultats à stocker
results = []

# Analyser phrases par personnage et saison
for saison, episodes in saison_dict:
    saison_df = df[df["episode"].isin(episodes)]

    for personnage in saison_df["character"].unique():
        perso_lines = saison_df[saison_df["character"] == personnage]["line"].dropna().astype(str)
        text = " ".join(perso_lines)
        phrases = clean_and_split_sentences(text)

        phrase_counts = Counter(phrases)
        top_phrases = phrase_counts.most_common(5)

        # Stocker dans le tableau
        for phrase, count in top_phrases:
            results.append({
                "season": saison,
                "character": personnage,
                "phrase": phrase,
                "count": count
            })

        # Affichage
        if top_phrases:
            phrases_, counts = zip(*top_phrases)
            plt.figure(figsize=(8, 4))
            plt.barh(phrases_[::-1], counts[::-1], color="seagreen")
            plt.title(f"{personnage} – Saison S{saison}")
            plt.xlabel("Occurrences")
            plt.tight_layout()
            plt.grid(axis="x", linestyle="--", alpha=0.3)
            plt.show()

# Export CSV
results_df = pd.DataFrame(results)
results_df.to_csv("phrases_recurrentes_par_saison.csv", index=False, encoding="utf-8")
print(" Exporté vers phrases_recurrentes_par_saison.csv")
