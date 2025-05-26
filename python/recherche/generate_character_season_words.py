# [Enzo LOUIS] [Recherche personnage)]
# Stockage des mots les plus fréquents pour chaque personnage au global et pour chaque saison
# (Utilisé par la partie analyse langagière)
# top_10_mots_par_personnage_et_saison.csv
# Stockage des mots les plus importants pour chaque personnage (TF-IDF) au global et pour chaque saison
# top_10_mots_important_par_personnage_et_saison.csv

import pandas as pd
from collections import Counter
import re
from sklearn.feature_extraction.text import TfidfVectorizer, ENGLISH_STOP_WORDS

df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv")

# Fonction pour nettoyer et découper le texte
def tokenize(text):
    text_clean = re.sub(r'\([^)]*\)', '', text.lower())  # enlever ce qu'il y a dans ( )
    words = re.findall(r'\b\w+\b', text_clean)
    with open("advanced_stopwords.txt") as stopwords:
        stopwords_set = set(stopwords.read().split("\n"))

    return [word for word in words if word not in stopwords_set]

# Groupement global par personnage
grouped_global = df.groupby(['character'])['line'].apply(lambda texts: ' '.join(texts)).reset_index()

rows = []
for _, row in grouped_global.iterrows():
    words = tokenize(row['line'])
    counter = Counter(words)
    top_words = counter.most_common(10)  # Top 10 mots

    data = [row['character'], "ALL"]
    for word, freq in top_words:
        data += [word, freq]

    rows.append(data)

grouped = df.groupby(['character', 'season'])['line'].apply(lambda texts: ' '.join(texts)).reset_index()

for _, row in grouped.iterrows():
    words = tokenize(row['line'])
    counter = Counter(words)
    top_words = counter.most_common(10)

    data = [row['character'], row['season']]
    for word, freq in top_words:
        data += [word, freq]

    rows.append(data)

columns = ['character', 'season']
for i in range(10):
    columns += [f'mot_{i+1}', f'freq_{i+1}']

result_df = pd.DataFrame(rows, columns=columns)

# Sauvegarder en CSV
result_df.to_csv("top_10_mots_par_personnage_et_saison.csv", index=False)

### Sauvegarde du top 10 des mots par personnage en terme de rareté par rapport aux autres (TF-IDF)

def clean_line(line):
    """
    Enlève les annotations/didascalies, les ensemble de mots entre parenthèses
    Mets en minuscule
    Enlève les caractères spéciaux
    :param line: réplique de dialogue
    :return: 
    """
    line = re.sub(r'\([^)]*\)', '', line)
    
    # lower + clean special char + split
    line = re.sub(r'[^\w\s]', ' ', line.lower())
    return re.sub(r'\s+', ' ', line).strip()  #.split()

characters = ['Chandler', 'Joey', 'Ross', 'Rachel', 'Phoebe', 'Monica']

def liste_mots_importants_saison(saison, stopwords_extended_=False):
    # concaténation de toutes les répliques par personnage
    char_docs = []
    for char in characters:
        lines = df[df['character'] == char]
        if saison != None:
            lines = lines[lines['season'] == saison]
        full_text = " ".join(lines['line'])
        full_text = clean_line(full_text)
        char_docs.append(full_text)

    custom_stopwords = [
        
    ]
    if stopwords_extended_:
        custom_stopwords = [
            'susan', 'joe', 'janice', 'nina', 'tailor', 'owen', 'wayne', 'maurice', 'sergei', 'minsk', 'adelman', 'eddie', 'estelle', 'joseph', 'elizabeth', 'marcel', 'mona',
            'joshua', 'tag', 'barry', 'paolo', 'gavin', 'amy', 'ursula', 'buffay', 'phoebe', 'buffay', 'monica', 'geller', 'chandler', 'bing', 'ross', 'geller', 'rachel', 'green',
            'zack', 'doug', 'brian', 'kate', 'carols', 'joanna', 'melissa', 'zelner', 'wiener', 'wendy', 'matthews', 'leslie', 'ree', 'lily', 'mikes', 'mindy', 'kim',
            'pete', 'michel', 'erica', 'michelle', 'nana', 'kathy', 'tulsa', 'jessica', 'joey', 'rach', 'pheebs', 'paul', 'alan', 'frank', 'david','alice', 'jason', 'bob', 'ben', 'carol', 'julie', 'mark',
            'vince', 'angela'
        
        ]

    def clean_text(doc, min_freq=4):
        tokens = doc.lower().split()
        counts = Counter(tokens)
        return " ".join([t for t in tokens if counts[t] >= min_freq])

    char_docs = [clean_text(doc) for doc in char_docs]

    # advanced stopwords + stopwords personalisé
    full_stopwords = list(ENGLISH_STOP_WORDS.union(custom_stopwords))

    # TF-IDF avec chaque personnage = un document
    vectorizer = TfidfVectorizer(max_df=0.6, stop_words=full_stopwords, min_df=1, token_pattern=r"(?u)\b\w\w\w+\b")
    X = vectorizer.fit_transform(char_docs)
    words = vectorizer.get_feature_names_out()

    rows_ = []
    results_characters = {}
    for i, char in enumerate(characters):
        row = X[i].toarray().flatten()
        top_indices = row.argsort()[::-1][:10]  # Top 10 mots
        top_words = [(words[j], row[j]) for j in top_indices]
        results_characters[char] = top_words
        row_data = [char, saison if saison else "ALL"]
        for word, score in top_words:
            row_data.extend([word, score])
        
        rows_.append(row_data)

    return rows_

def liste_mots_importants():
    rows = []
    general_rows = liste_mots_importants_saison(None)
    general_rows2 = liste_mots_importants_saison(None, stopwords_extended_=True)
    merged = [row1 + row2[2:] for row1, row2 in zip(general_rows, general_rows2)]
    rows.extend(merged)
    for saison in df['season'].unique():
        saison_rows = liste_mots_importants_saison(saison)
        saison_rows2 = liste_mots_importants_saison(saison, stopwords_extended_=True)
        merged = [row1 + row2[2:] for row1, row2 in zip(saison_rows, saison_rows2)]
        rows.extend(merged)

    return rows

columns = ['character', 'season']
for i in range(10):
    columns += [f'mot_{i+1}', f'score_{i+1}']
for i in range(10):
    columns += [f'mot_{i+1}_filtre', f'score_{i+1}_filtre']

df_result = pd.DataFrame(liste_mots_importants(), columns=columns)

# Sauvegarder en CSV
df_result.to_csv("top_10_mots_important_par_personnage_et_saison.csv", index=False)

