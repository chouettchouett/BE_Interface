import pandas as pd
import matplotlib.pyplot as plt
import re

# Charger le fichier
df = pd.read_csv("Analyse_langagiere/friends_dialogues_final.csv", encoding='utf-8')

# Nettoyage des colonnes
df['season'] = df['season'].str.extract(r'S(\d+)').astype(int)
df["line"] = df["line"].fillna("").astype(str)
df["line_clean"] = df["line"].apply(lambda x: re.sub(r"[^\w\s]", "", x.lower()))
df["word_count"] = df["line_clean"].apply(lambda x: len(x.split()))

main_characters = ['Rachel', 'Ross', 'Monica', 'Chandler', 'Joey', 'Phoebe']
df = df[df["character"].isin(main_characters)]

pivot = df.groupby(["season", "character"])["word_count"].sum().unstack().fillna(0)

# Plot en barres groupées
pivot.plot(kind='bar', figsize=(14, 7), colormap='tab10')
plt.title("Nombre de mots par personnage et par saison")
plt.xlabel("Saison")
plt.ylabel("Nombre total de mots")
plt.legend(title="Personnage")
plt.tight_layout()
plt.show()
