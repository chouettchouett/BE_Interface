import pandas as pd
import re
from scipy.stats import zscore

df = pd.read_csv("../Analyse_Sentiments/friends_dialogues_final.csv")

def clean_line(line):
    line = re.sub(r'\([^)]*\)', ' ', line)  # enlève contenu parenthèses
    return re.sub(r'[^\w\s]', '', line.lower()).split()  # minuscule, supprime ponctuation, split

saisons = sorted(df['season'].unique())
characters = ['monica', 'joey', 'chandler', 'phoebe', 'ross', 'rachel']

# comptage mentions par saison & personnage
mentions_per_season = pd.DataFrame(0, index=saisons, columns=characters)

for _, row in df.iterrows():
    saison = row['season']
    ligne = clean_line(row['line'])
    speaker = row['character'].lower()

    for mot in ligne:
        if mot in characters and mot != speaker:
            mentions_per_season.at[saison, mot] += 1

# normalisation mentions par total mentions saison
total_mentions_saison = mentions_per_season.sum(axis=1)
mentions_norm = mentions_per_season.div(total_mentions_saison, axis=0)

# comptage répliques par personnage & saison
repliques_count = df.groupby(['character', 'season']).size().reset_index(name='line_count')
repliques_count['character'] = repliques_count['character'].str.lower()
total_lines_per_season = df.groupby('season').size()
repliques_count['total_lines_in_season'] = repliques_count['season'].map(total_lines_per_season)
repliques_count['line_rate'] = repliques_count['line_count'] / repliques_count['total_lines_in_season']

# pivot pour calculer z-score par personnage sur saisons
mentions_norm_T = mentions_norm.T  # index=personnage, colonnes=saison
repliques_pivot = repliques_count.pivot(index='character', columns='season', values='line_rate').fillna(0)

# calcul z-score par personnage (axis=1 sur saisons)
mentions_zscore = pd.DataFrame(
    zscore(mentions_norm_T, axis=1, nan_policy='omit'),
    index=mentions_norm_T.index,
    columns=mentions_norm_T.columns
)

repliques_zscore = pd.DataFrame(
    zscore(repliques_pivot, axis=1, nan_policy='omit'),
    index=repliques_pivot.index,
    columns=repliques_pivot.columns
)

final_dfs = []
for character in characters:
    df_character = pd.DataFrame({
        'season': mentions_zscore.columns,
        'mentions_zscore': mentions_zscore.loc[character].values,
        'repliques_zscore': repliques_zscore.loc[character].values,
    })
    df_character['average_zscore'] = (df_character['mentions_zscore'] + df_character['repliques_zscore']) / 2
    df_character['character'] = character
    final_dfs.append(df_character)

result_df = pd.concat(final_dfs, ignore_index=True)

# Sauvegarde dans un fichier CSV
result_df.to_csv("friends_characters_mentions_repliques_zscore.csv", index=False)
