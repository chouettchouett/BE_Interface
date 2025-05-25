import pandas as pd
from collections import Counter
import re
from sklearn.feature_extraction.text import TfidfVectorizer, ENGLISH_STOP_WORDS

df = pd.read_csv("../Stats/friends_dialogues_emotions.csv")

# Fonction pour nettoyer et découper le texte
def tokenize(text):
    text_clean = re.sub(r'\([^)]*\)', '', text.lower())  # enlever ce qu'il y a dans ( )
    words = re.findall(r'\b\w+\b', text_clean)
    with open("advanced_stopwords.txt") as stopwords:
        stopwords_set = set(stopwords.read().split("\n"))

    return [word for word in words if word not in stopwords_set]

# Groupement global par personnage (sur l'émotion JOIE)
grouped_global = df[df['emotion'] == "joy"].groupby(['character'])['line'].apply(lambda texts: ' '.join(texts)).reset_index()

rows = []
for _, row in grouped_global.iterrows():
    words = tokenize(row['line'])
    counter = Counter(words)
    top_words = counter.most_common(10)  # Top 10 mots

    data = [row['character'], "ALL"]
    for word, freq in top_words:
        data += [word, freq]

    rows.append(data)

grouped = df[df['emotion'] == "joy"].groupby(['character', 'season'])['line'].apply(lambda texts: ' '.join(texts)).reset_index()

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

# sauvegarde en CSV
result_df.to_csv("top_10_mots_par_personnage_et_saison_joy.csv", index=False)

def clean_line(line):
    """
    Enlève les annotations/didascalies (ensemble de mots entre parenthèses)
    Mets en minuscule
    Enlève les caractères spéciaux
    :param line: réplique de dialogue
    :return: 
    """
    line = re.sub(r'\([^)]*\)', '', line)
    
    # Supprimer les mots avec majuscules (type "Joey", "Monica")
    # à améliorer : ne pas enlever les premiers mots de phrases
    # line = ' '.join([word for word in line.split() if not word[0].isupper()])
    
    # lower + clean special char + split
    line = re.sub(r'[^\w\s]', ' ', line.lower())
    return re.sub(r'\s+', ' ', line).strip()  #.split()

characters = ['Chandler', 'Joey', 'Ross', 'Rachel', 'Phoebe', 'Monica']

def liste_mots_importants_saison(saison, emotion):
    # concaténation de toutes les répliques par personnage
    char_docs = []
    for char in characters:
        lines = df[df['character'] == char]
        if saison != None:
            lines = lines[lines['season'] == saison]
        lines = lines[lines['emotion'] == emotion]
        full_text = " ".join(lines['line'])
        full_text = clean_line(full_text)
        char_docs.append(full_text)

    def clean_text(doc, min_freq=0 if saison else 8):
        tokens = doc.lower().split()
        counts = Counter(tokens)
        return " ".join([t for t in tokens if counts[t] >= min_freq])

    char_docs = [clean_text(doc) for doc in char_docs]

    # TF-IDF avec document=personnage
    vectorizer = TfidfVectorizer(max_df=0.4, stop_words='english', min_df=1, token_pattern=r"(?u)\b\w\w\w+\b")
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
    general_rows = liste_mots_importants_saison(None, "joy")
    rows.extend(general_rows)
    for saison in df['season'].unique():
        saison_rows = liste_mots_importants_saison(saison, "joy")
        rows.extend(saison_rows)

    return rows

columns = ['character', 'season']
for i in range(10):
    columns += [f'mot_{i+1}', f'score_{i+1}']

df_result = pd.DataFrame(liste_mots_importants(), columns=columns)
df_result.to_csv("top_10_mots_important_par_personnage_et_saison_joy.csv", index=False)
