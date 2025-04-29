import pandas as pd
from collections import Counter
import re
import matplotlib.pyplot as plt
# import nltk
# from nltk.corpus import stopwords

# nltk.download('stopwords')
# stopwords peu fourni
# je garde si plus tard on revient sur ça pour eviter les sources externes
# stopwords_set = set(stopwords.words("english"))

# Charger les stop words à partir du fichier
with open("advanced_stopwords.txt") as stopwords:
    stopwords_set = set(stopwords.read().split("\n"))

stopwords_set.update({'chandler', 'ross', 'rachel', 'phoebe', 'joey', 'monica', 'hey', 'gonna', 'wanna', 'um', 'yknow', 'uh', 'umm', 'yeah', 'ii', 'huh', 'ooh', 'guys'})

df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv", encoding='utf-8')

main_characters = ['Chandler', 'Joey', 'Monica', 'Phoebe', 'Rachel', 'Ross']
df = df[df["character"].isin(main_characters)]

def split_line(line):
    # lower + clean special char + split
    return re.sub(r'[^\w\s]', '', line.lower()).split()

# map Character to list words (str : list[str])
characters_words = { character.lower():[] for character in main_characters }

# Enregistremeent des mots de chaque phrase de chaque personnages
for _, row in df.iterrows():
    character = row['character'].lower()
    words = split_line(row['line'])
    filtered_words = [word for word in words if word not in stopwords_set] # filtre stopwords
    characters_words[character].extend(filtered_words)

# Résultat sans filtre
final_ten_characters_words = {
    character.lower(): Counter(words).most_common(10) for character in main_characters 
    for character, words in characters_words.items()
}

# Identifier les X (200 ici) mots fréquents qui apparaissent chez tous les personnages sauf le courant
common_words = {
    character: {
        word for other_character in characters_words if other_character != character
        for word, _ in Counter(characters_words[other_character]).most_common(200)
    }
    for character in characters_words
}

# Résultat avec filtre common_words
final_ten_characters_words_without_common_words = {
    character.lower(): Counter([word for word in words if word not in common_words[character]]).most_common(10) for character in main_characters 
    for character, words in characters_words.items()
}


def show_graphic(final_ten_characters_words, title):
    fig, axes = plt.subplots(2, 3, figsize=(15, 10)) # (2 lignes, 3 colonnes)
    fig.suptitle(title, fontsize=16)
    axes = axes.flatten()

    for i, (character, final_ten_for_one_character) in enumerate(final_ten_characters_words.items()):
        words, counts = zip(*final_ten_for_one_character)
        axes[i].bar(words, counts, color='skyblue')
        axes[i].set_title(character.capitalize())
        axes[i].set_xticks(range(len(words)))
        axes[i].set_xticklabels(words, rotation=45, ha="right")

    # ajustement des espaces pour pas condenser les textes
    plt.tight_layout(rect=[0, 0, 1, 0.98])
    plt.show()

show_graphic(final_ten_characters_words, "Mots les plus utilisés de chaque personnage")
show_graphic(final_ten_characters_words_without_common_words, "Mots les plus caractéristiques de chaque personnage")