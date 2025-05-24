import pandas as pd
import re
from collections import Counter
import matplotlib.pyplot as plt

def clean_and_split_sentences(text):
    # Unifie ponctuation et casse
    text = text.replace("’", "'")
    text = text.lower()
    text = re.sub(r"[!?,:;]", ".", text)
    text = re.sub(r"\s+", " ", text)
    sentences = re.split(r"\.\s*", text)
    return [s.strip() for s in sentences if 3 <= len(s.split()) <= 10]

# --- Code principal protégé ---
if __name__ == "__main__":
    # Charger les dialogues
    csv_path = "../Analyse_Sentiments/friends_dialogues_final.csv"
    df = pd.read_csv(csv_path, encoding='utf-8')
    main_characters = ['Chandler', 'Joey', 'Monica', 'Phoebe', 'Rachel', 'Ross']
    df = df[df["character"].isin(main_characters)]

    results = []

    for character in main_characters:
        all_lines = df[df["character"] == character]["line"].dropna().astype(str)
        text = " ".join(all_lines)
        sentences = clean_and_split_sentences(text)

        sentence_counts = Counter(sentences)
        top_sentences = sentence_counts.most_common(10)

        for phrase, count in top_sentences:
            if count >= 10:
                results.append({"character": character, "phrase": phrase, "count": count})

    # Création du DataFrame + export
    results_df = pd.DataFrame(results)
    results_df.to_csv("phrases_recurrentes.csv", index=False, encoding="utf-8")
    print("✅ Exporté : phrases_recurrentes.csv")

    # Affichage graphique
    for char in results_df["character"].unique():
        sub = results_df[results_df["character"] == char].sort_values("count")
        plt.figure(figsize=(9, 4))
        plt.barh(sub["phrase"], sub["count"], color="mediumpurple")
        plt.title(f"Phrases récurrentes de {char}")
        plt.xlabel("Occurrences")
        plt.tight_layout()
        plt.show()
