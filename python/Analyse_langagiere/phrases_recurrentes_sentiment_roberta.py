import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification, pipeline

# chargement du csv
df = pd.read_csv("phrases_recurrentes.csv") 

# initialisation du modele roberta
model_name = "cardiffnlp/twitter-roberta-base-emotion"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForSequenceClassification.from_pretrained(model_name)
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
emotion_pipeline = pipeline("text-classification", model=model, tokenizer=tokenizer, device=device, top_k=1)

# analyse des phrases 
print("Analyse des émotions avec RoBERTa en cours...")
emotion_results = emotion_pipeline(df["phrase"].tolist())
df["emotion"] = [r[0]["label"] for r in emotion_results]
df["score_emotion"] = [r[0]["score"] for r in emotion_results]
print("Analyse terminée.")

# fichier enrichi
df.to_csv("phrases_recurrentes_avec_emotions_roberta.csv", index=False, encoding="utf-8")

# affichage par personnage
emotion_colors_fixed = {
    'joy': 'green',
    'sadness': 'blue',
    'anger': 'red',
    'fear': 'orange',
    'love': 'purple',
    'surprise': 'brown',
    'neutral': 'gray',
    'optimism': 'gold'  # ← ajout cle supp pour ce nouveau modele
}


for char in df["character"].unique():
    df_char = df[df["character"] == char].sort_values("count", ascending=False)

    plt.figure(figsize=(12, 6))
    sns.barplot(data=df_char, x="count", y="phrase", hue="emotion", dodge=False, palette=emotion_colors_fixed)
    plt.title(f"Émotions (RoBERTa) des phrases récurrentes de {char}")
    plt.xlabel("Nombre d'occurrences")
    plt.ylabel("Phrase")
    plt.legend(title="Émotion", bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.tight_layout()
    plt.show()
