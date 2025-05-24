# [Enzo LOUIS] [Recherche par mot (et ensemble de mots)]
# Statistiques dynamiques (en fonction d'un mot ou ensemble de mot précis)
# Pour faire de la recherche interactive en plus de l'analyse statique
# Si la personne sur l'interface veut regarder qui est plus passioné de natation par exemple,
# l'analyse statique ne lui permettra pas alors que cette option de recherche si.
# résultats détaillé sous forme de personnages, saison et épisodes...

import pandas as pd
import re
import os
import sys
import json

chemin_csv = os.path.join(os.path.dirname(__file__), "..", "Analyse_Sentiments", "friends_dialogues_final.csv")
chemin_csv = os.path.abspath(chemin_csv)

def recherche_par_mots(words: str, case_sensitive=False):
    df = pd.read_csv(chemin_csv, encoding='utf-8')

    # compter le nombre de <words> dans chaque répliques
    df['word_count'] = df['line'].str.count(words, flags=0 if case_sensitive else re.IGNORECASE)
    
    # réplique contenant <words> "<words> apparait dans les répliques suivantes : (liste)"
    lines_with_words = df[df['word_count'] > 0]

    # nombre de répliques contenant <words>
    nb_lines_with_words = lines_with_words['word_count'].count()

    # nombre total d'utilisation de <words>
    total_word_used = lines_with_words['word_count'].sum()

    # <words> apparait dans <words_usage_ratio>% des répliques de la série
    words_usage_ratio_by_line = lines_with_words['line'].size / df.shape[0]

    # nombre de <words> par personnage
    word_count_by_character = df.groupby('character', as_index=False)['word_count'].sum()
    
    # somme de <words> dans chaque épisodes 
    word_count_by_season = df.groupby('season', as_index=False)['word_count'].sum()
    
    # taux de répartition de <words> selon les saisons 
    word_count_by_season['ratio'] = word_count_by_season['word_count'] / word_count_by_season['word_count'].sum()

    # "Ce mot est mentionné le plus de fois dans l'episode X de la saison Y"
    best_season_word_count = word_count_by_season.loc[word_count_by_season['word_count'].idxmax(), ['season']]

    # somme de <words> dans chaque épisodes
    word_count_by_episode = df.groupby(['season', 'episode'], as_index=False)['word_count'].sum()
    
    # taux de répartition de <words> selon les épisodes  
    word_count_by_episode['ratio'] = word_count_by_episode['word_count'] / word_count_by_episode['word_count'].sum()
    
    # "Ce mot est mentionné le plus de fois dans l'episode X de la saison Y"
    best_episode_word_count = word_count_by_episode.loc[word_count_by_episode['word_count'].idxmax(), ['season', 'episode']]

    # convertir les résultat en type python (interprétable par les modèles de la vue)
    return {
        "nb_word_used": int(total_word_used),
        "lines_with_words": lines_with_words[['line', 'character', 'season', 'episode']].values.tolist(), 
        "number_lines_with_words": int(nb_lines_with_words),
        "words_usage_ratio_by_line": words_usage_ratio_by_line, 
        "word_count_by_character": word_count_by_character.to_records(index=False).tolist(), 
        "word_count_by_season": word_count_by_season.to_records(index=False).tolist(),
        "word_count_by_episode": word_count_by_episode.to_records(index=False).tolist(),
        "best_season_word_count": best_season_word_count.item(),
        "best_episode_word_count": best_episode_word_count.to_dict(),
    }

def recherche_par_mots_json(words):
    result = recherche_par_mots(words)
    return json.dumps(result)

mot = sys.argv[1] if len(sys.argv) > 1 else "Hello"
# ne pas supprimer (liaison java-python)
print(recherche_par_mots_json(mot))