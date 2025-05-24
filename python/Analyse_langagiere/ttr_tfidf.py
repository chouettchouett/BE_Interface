import pandas as pd
import matplotlib.pyplot as plt
import re
from sklearn.feature_extraction.text import TfidfVectorizer
import string


# Charger les données
df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv", encoding='utf-8')
with open("../recherche/advanced_stopwords.txt", encoding="latin1") as f:
    stopwords_list = [word.strip().lower() for word in f if word.strip()]  # pour TfidfVectorizer
    stopwords_set = set(stopwords_list)  # pour soustraction de mots

# Filtrer les personnages principaux
main_chars = ["Chandler", "Joey", "Monica", "Phoebe", "Rachel", "Ross"]
df = df[df["character"].isin(main_chars)]

# Grouper les lignes par personnage
character_lines = df.groupby("character")["line"].apply(lambda x: " ".join(str(s) for s in x)).to_dict()

# Construire le corpus
corpus = list(character_lines.values())
def clean_word(w):
    return w.translate(str.maketrans('', '', string.punctuation))

stopwords_list = [clean_word(w) for w in stopwords_list]

# Utiliser TfidfVectorizer pour récupérer uniquement l’IDF
vectorizer = TfidfVectorizer(lowercase=True, stop_words=stopwords_list, use_idf=True, smooth_idf=True, norm=None)
X = vectorizer.fit_transform(corpus)
idf_values = vectorizer.idf_
feature_names = vectorizer.get_feature_names_out()
idf_dict = dict(zip(feature_names, idf_values))
max_idf = max(idf_values)

# Tokeniser les dialogues et retirer les stopwords
character_tokens = {
    character: set(re.findall(r"\b\w+\b", text.lower())) - stopwords_set
    for character, text in character_lines.items()
}

# Compter le nombre total de mots du corpus
corpus_word_count = sum(len(re.findall(r"\b\w+\b", text.lower())) for text in corpus)

# Calculer le TTR basé sur l’IDF maximal
character_idf_ttr = {}
for character, words in character_tokens.items():
    unique_idf_words = [word for word in words if word in idf_dict and idf_dict[word] == max_idf]
    character_idf_ttr[character] = len(unique_idf_words) / corpus_word_count

# Stocker les résultats dans un DataFrame et trier
idf_ttr_df = pd.DataFrame(list(character_idf_ttr.items()), columns=["Character", "IDF_TTR"])
idf_ttr_df = idf_ttr_df.sort_values(by="IDF_TTR", ascending=False)

# Sauvegarder les résultats
idf_ttr_df.to_csv("idf_ttr_per_character.csv", index=False)

# Visualisation avec échelle ajustée et couleurs
colors = ['#FF9999','#66B3FF','#99FF99','#FFCC99','#C2C2F0','#FFB6C1']
plt.figure(figsize=(10, 6))
bars = plt.bar(idf_ttr_df["Character"], idf_ttr_df["IDF_TTR"], color=colors)

for bar in bars:
    height = bar.get_height()
    plt.text(bar.get_x() + bar.get_width()/2, height + 0.00001, f"{height:.5f}", 
             ha='center', va='bottom', fontsize=10)

plt.title("TTR basé sur IDF par personnage", fontsize=14)
plt.xlabel("Personnage", fontsize=12)
plt.ylabel("TTR basé sur IDF", fontsize=12)
plt.ylim(0, max(idf_ttr_df["IDF_TTR"]) + 0.0001)
plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.tight_layout()
plt.show()
