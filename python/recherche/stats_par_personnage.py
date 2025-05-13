import pandas as pd
import os

chemin_csv = os.path.join(os.path.dirname(__file__), "..", "Analyse_Sentiments", "friends_dialogues_final.csv")
chemin_csv = os.path.abspath(chemin_csv)

def count_lines_by_character_and_season():
    df = pd.read_csv(chemin_csv, encoding='utf-8')

    df['season'] = df['season'].apply(lambda x: x)
    
    count = df.groupby(['character', 'season']).size().reset_index(name='line_count')

    pivot = count.pivot(index='character', columns='season', values='line_count').fillna(0).astype(int)

    pivot_dict = pivot.reset_index().to_dict(orient='records')

    return pivot_dict

print(count_lines_by_character_and_season())