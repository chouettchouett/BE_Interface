# -*- coding: utf-8 -*-
"""Sentiments.ipynb

Automatically generated by Colab.

Original file is located at
    https://colab.research.google.com/drive/14xRfZq0d4F_bkvP3x_gJg8ZKWflHCVzA
"""

#Fichier Fait par Simon VALVERDE

import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os
from wordcloud import WordCloud
from collections import Counter
from textblob import TextBlob

# Création du dossier output s'il n'existe pas
if not os.path.exists("output"):
    os.makedirs("output")

# Fonction pour sauvegarder les diagrammes
def save_plot(fig, filename):
    fig.savefig(f"output/{filename}", bbox_inches="tight")
    plt.close(fig)

# Chargement des données
df = pd.read_csv("friends_dialogues_final.csv", names=["season", "episode", "character", "line"])

# Filtrer les personnages principaux
main_characters = ["Rachel", "Ross", "Chandler", "Joey", "Monica", "Phoebe"]
df = df[df["character"].isin(main_characters)]

# Calcul du score de sentiment
def get_sentiment(text):
    return TextBlob(str(text)).sentiment.polarity

df["sentiment"] = df["line"].apply(get_sentiment)

# Catégorisation en 3 clusters (Négatif, Neutre, Positif)
def categorize_sentiment(score):
    if score > 0.1:
        return "Positif"
    elif score < -0.1:
        return "Négatif"
    else:
        return "Neutre"

df["sentiment_category"] = df["sentiment"].apply(categorize_sentiment)

# Étiquettes binaires pour le calcul des proportions
df["is_positive"] = df["sentiment"] > 0.1
df["is_negative"] = df["sentiment"] < -0.1

# Répartition des sentiments
def plot_sentiment_distribution():
    fig, ax = plt.subplots(figsize=(12,6))
    sns.countplot(
        data=df, y="sentiment_category", hue="sentiment_category",
        palette={"Négatif": "#e63946", "Neutre": "#f4a261", "Positif": "#2a9d8f"},
        legend=False, ax=ax
    )
    ax.set_title("Répartition des sentiments", fontsize=14, fontweight="bold")
    save_plot(fig, "sentiment_distribution.png")
    plt.show()

# Répartition des sentiments par personnage
def plot_sentiment_by_character():
    fig, ax = plt.subplots(figsize=(12,6))
    sns.countplot(
        data=df, x="character", hue="sentiment_category",
        palette={"Négatif": "#e63946", "Neutre": "#f4a261", "Positif": "#2a9d8f"}, ax=ax
    )
    ax.set_title("Répartition des sentiments par personnage", fontsize=14, fontweight="bold")
    save_plot(fig, "sentiment_by_character.png")
    plt.show()

# Nuages de mots
def generate_wordcloud(word_freq, filename, colormap):
    wordcloud = WordCloud(
        width=800,
        height=400,
        background_color="grey",
        colormap=colormap,
        max_font_size=300,
        min_font_size=10,
        relative_scaling=0.5
    ).generate_from_frequencies(word_freq)

    fig, ax = plt.subplots(figsize=(10, 5))
    ax.imshow(wordcloud, interpolation="bilinear")
    ax.axis("off")
    save_plot(fig, filename)
    plt.show()
#Nuage de MOTS Négatifs vs Positifs

positive_words = {
    "love", "great", "happy", "fun", "wonderful", "good", "amazing", "cool", "awesome", "best", "nice",
    "fantastic", "joyful", "excellent", "brilliant", "superb", "positive", "smile", "laugh", "peace", "success",
    "beautiful", "hope", "kind", "grateful", "graceful", "friendly", "cheerful", "optimistic", "victory", "strong",
    "genius", "brave", "winner", "charming", "adorable", "radiant", "passionate", "resilient", "talented", "healthy",
    "thriving", "majestic", "outstanding", "fearless", "admirable", "faithful", "glowing", "incredible", "luminous",
    "euphoric", "blessed", "satisfying", "inspiring", "heartwarming", "miraculous", "uplifting", "harmonious",
    "supportive", "compassionate", "encouraging", "loving", "successful", "charismatic", "joyous", "hopeful",
    "affectionate", "peaceful", "enthusiastic", "graceful", "serene", "triumphant", "unwavering", "elated", "radiating",
    "fortunate", "sparkling", "ecstatic", "motivated", "glorious", "dazzling", "fascinating", "energetic", "innovative",
    "enthusiastic", "devoted", "impressive", "bountiful", "sunny", "brilliant", "fabulous", "sensational", "stunning"
}

negative_words = {
    "hate", "bad", "terrible", "worst", "annoying", "stupid", "awful", "upset", "horrible", "angry",
    "disgusting", "sad", "pain", "sick", "hurt", "miserable", "failure", "cry", "depressed", "scared",
    "frustrated", "pathetic", "weak", "disappointed", "useless", "worthless", "boring", "ashamed", "guilty",
    "dreadful", "hopeless", "loser", "unhappy", "toxic", "abandoned", "helpless", "exhausted", "vicious", "hateful",
    "vile", "doomed", "jealous", "desperate", "wrecked", "terrified", "ignored", "neglected", "tortured",
    "unbearable", "pessimistic", "desolate", "heartbroken", "bleak", "unloved", "damaged", "resentful",
    "despair", "agonizing", "detestable", "frightening", "sorrowful", "dismal", "mournful", "cold-hearted",
    "fearful", "manipulative", "unforgiving", "vengeful", "hostile", "harsh", "bitter", "brutal", "regretful",
    "merciless", "grief-stricken", "destructive", "malicious", "nightmarish", "insufferable", "vindictive",
    "cruel", "obsessive", "toxic", "chaotic", "oppressive", "deceitful", "gruesome", "morbid", "suffocating"
}

negative_words_list = df["line"].dropna().apply(lambda x: [word for word in x.lower().split() if word in negative_words]).explode().dropna()
positive_words_list = df["line"].dropna().apply(lambda x: [word for word in x.lower().split() if word in positive_words]).explode().dropna()

negative_word_freq = Counter(negative_words_list)
positive_word_freq = Counter(positive_words_list)

#Évolution de la positivité par personnage
def plot_sentiment_character_trend_positive():
    sentiment_trend = df.groupby(["season", "character"])["is_positive"].mean() * 100
    fig, ax = plt.subplots(figsize=(12, 6))

    for character in main_characters:
        if character in sentiment_trend.index.get_level_values("character"):
            data = sentiment_trend.xs(character, level="character")
            ax.plot(data.index, data, marker="o", linestyle="-", label=character)

    ax.set_title("Évolution de la positivité des dialogues par personnage (%)", fontsize=14, fontweight="bold")
    ax.set_xlabel("Saison")
    ax.set_ylabel("Proportion de dialogues positifs (%)")
    ax.legend(title="Personnage")

    save_plot(fig, "sentiment_character_trend_positive.png")
    plt.show()

#Évolution de la négativité par personnage
def plot_sentiment_character_trend_negative():
    sentiment_trend = df.groupby(["season", "character"])["is_negative"].mean() * 100
    fig, ax = plt.subplots(figsize=(12, 6))

    for character in main_characters:
        if character in sentiment_trend.index.get_level_values("character"):
            data = sentiment_trend.xs(character, level="character")
            ax.plot(data.index, data, marker="o", linestyle="-", label=character)

    ax.set_title("Évolution de la négativité des dialogues par personnage (%)", fontsize=14, fontweight="bold")
    ax.set_xlabel("Saison")
    ax.set_ylabel("Proportion de dialogues négatifs (%)")
    ax.legend(title="Personnage")

    save_plot(fig, "sentiment_character_trend_negative.png")
    plt.show()



# Exécution des visualisations
plot_sentiment_distribution()
plot_sentiment_by_character()
generate_wordcloud(negative_word_freq, "negative_wordcloud.png", "Reds")
generate_wordcloud(positive_word_freq, "positive_wordcloud.png", "Greens")
plot_sentiment_character_trend_positive()
plot_sentiment_character_trend_negative()
