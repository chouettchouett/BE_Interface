
import pandas as pd
import re
from collections import Counter
import matplotlib.pyplot as plt

# Liste enrichie de tics de langage connus
TICS_KNOWN = {
    "yeah", "uh", "um", "huh", "well", "ok", "okay", "like", "you know",
    "i mean", "hmm", "oh", "right", "really", "so", "basically", "uhh", "alright"
}

# Charger le CSV
df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv", encoding='utf-8')
main_characters = ['Chandler', 'Joey', 'Monica', 'Phoebe', 'Rachel', 'Ross']
df = df[df["character"].isin(main_characters)]

# Nettoyer les lignes
df["line_clean"] = df["line"].fillna("").astype(str).str.lower()
df["line_clean"] = df["line_clean"].apply(lambda x: re.sub(r"[^\w\s']", "", x))

# Tokeniser et compter les tics pour chaque personnage
results = []

for character in main_characters:
    all_lines = df[df["character"] == character]["line_clean"]
    all_tokens = " ".join(all_lines).split()
    tic_tokens = [w for w in all_tokens if w in TICS_KNOWN]
    counter = Counter(tic_tokens).most_common(10)

    for word, count in counter:
        results.append({"character": character, "tic": word, "count": count})

# Affichage + Export CSV
results_df = pd.DataFrame(results)
results_df.to_csv("tics_langagiers_result.csv", index=False)

for char in main_characters:
    print(f"\n🔹 {char}")
    subset = results_df[results_df["character"] == char]
    for _, row in subset.iterrows():
        print(f"- {row['tic']} ({row['count']})")

    if not subset.empty:
        plt.figure(figsize=(7, 4))
        plt.barh(subset["tic"], subset["count"], color="darkcyan")
        plt.title(f"Tics de langage – {char}")
        plt.xlabel("Occurrences")
        plt.tight_layout()
        plt.grid(axis="x", linestyle="--", alpha=0.5)
        plt.show()

print("\n Résultats exportés dans : tics_langagiers_result.csv")
