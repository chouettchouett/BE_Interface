import pandas as pd
from collections import Counter
import re
from sklearn.feature_extraction.text import TfidfVectorizer, ENGLISH_STOP_WORDS

df = pd.read_csv("../Stats/friends_dialogues_emotions.csv")

def clean_line(line):
    """
    Enlève les annotations/didascalies, les ensemble de mots entre parenthèses
    Mets en minuscule
    Enlève les caractères spéciaux
    :param line: réplique de dialogue
    :return: 
    """
    line = re.sub(r'\([^)]*\)', '', line)
    
    # Supprimer les mots avec majuscules (type "Joey", "Monica")
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
    vectorizer = TfidfVectorizer(max_df=0.2, stop_words='english', min_df=1, token_pattern=r"(?u)\b\w\w\w+\b")
    X = vectorizer.fit_transform(char_docs)
    words = vectorizer.get_feature_names_out()

    rows_ = []
    results_characters = {}
    for i, char in enumerate(characters):
        row = X[i].toarray().flatten()
        top_indices = row.argsort()[::-1][:10]  # Top 10 mots
        top_words = [(words[j], row[j]) for j in top_indices]
        results_characters[char] = top_words
        row_data = [char, saison if saison else "ALL", emotion]
        for word, score in top_words:
            row_data.extend([word, score])
        
        rows_.append(row_data)

    return rows_

def liste_mots_importants():
    rows = []
    for emotion in ("joy", "surprise", "sadness", "fear", "anger", "love"):
        general_rows = liste_mots_importants_saison(None, emotion)
        rows.extend(general_rows)
        for saison in df['season'].unique():
            saison_rows = liste_mots_importants_saison(saison, emotion)
            rows.extend(saison_rows)

    return rows

columns = ['character', 'season']
for i in range(10):
    columns += [f'mot_{i+1}', f'score_{i+1}']

columns += ["emotion"]
df_result = pd.DataFrame(liste_mots_importants(), columns=columns)

df_result.to_csv("top_10_mots_important_par_personnage_et_saison_emotion.csv", index=False)
