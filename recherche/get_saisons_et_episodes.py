import pandas as pd

def get_saisons_et_episodes():
    df = pd.read_csv("../Stats/friends_dialogues.csv", encoding='utf-8')

    df = df[['season', 'episode']].drop_duplicates()
    # conversion en int pour la liste retournée (et optionnellement pour le tri)
    df['season'] = df['season'].str[1:].astype(int)
    df['episode'] = df['episode'].str[1:].astype(int)
    df = df.sort_values(by=['season', 'episode'])

    # convertir le résultat en liste de n-uplets taille 2 de la forme [(<num_season>, (<n_ep>, <n_ep>, ...))]
    return [(season, tuple(episodes)) for season, episodes in df.groupby('season')['episode']]

print(get_saisons_et_episodes())