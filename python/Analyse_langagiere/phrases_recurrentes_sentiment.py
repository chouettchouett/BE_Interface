import pandas as pd
from transformers import pipeline
import torch
import matplotlib.pyplot as plt
import seaborn as sns

# lecture du csv phrases récurrentes par personnage
df = pd.read_csv("phrases_recurrentes.csv")  # colonne : character, phrase, count

# initialisation du modele bert comme dans analyse_sentiment.py 
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
emotion_pipeline = pipeline("text-classification", model="bhadresh-savani/distilbert-base-uncased-emotion", device=device)

# analyse des phrases avec le modele
print("Analyse des émotions en cours...")
emotion_results = emotion_pipeline(df["phrase"].tolist())
df["emotion"] = [r["label"] for r in emotion_results]
df["score_emotion"] = [r["score"] for r in emotion_results]
print("Analyse terminée.")

# export du csv enrichi
df.to_csv("phrases_recurrentes_avec_emotions.csv", index=False, encoding="utf-8")

# Lecture du CSV généré
df = pd.read_csv("phrases_recurrentes_avec_emotions.csv")

# GRAPHIQUE PAR PERSONNAGE : PHRASES RÉCURRENTES ET EMOTION PAR PHRASE
# Palette des émotions
emotion_colors_fixed = {
    'joy': 'green',
    'sadness': 'blue',
    'anger': 'red',
    'fear': 'orange',
    'love': 'purple',
    'surprise': 'brown'
}
 
characters = df["character"].unique()

for char in characters:
    df_char = df[df["character"] == char].sort_values("count", ascending=False)

    plt.figure(figsize=(12, 6))
    sns.barplot(data=df_char, x="count", y="phrase", hue="emotion", dodge=False, palette=emotion_colors_fixed)
    plt.title(f"Émotions des phrases récurrentes de {char}")
    plt.xlabel("Nombre d'occurrences")
    plt.ylabel("Phrase")
    plt.legend(title="Émotion", bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.tight_layout()
    plt.show()

#  RÉPARTITION DES ÉMOTIONS PAR PERSONNAGE 

# Compter le nombre de phrases par personnage et par émotion
emotion_counts = df.groupby(["character", "emotion"]).size().reset_index(name="count")


plt.figure(figsize=(12, 6))
sns.barplot(data=emotion_counts, x="character", y="count", hue="emotion", palette=emotion_colors_fixed)
plt.title("Répartition des émotions dans les phrases récurrentes par personnage")
plt.xlabel("Personnage")
plt.ylabel("Nombre de phrases")
plt.legend(title="Émotion", bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.show()
