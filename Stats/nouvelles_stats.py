import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from collections import Counter
import nltk
import os  
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import os
import glob

# Noah / le 17/03


# Juste pour suppr les graphes deja present(ne pas en prendre compte)
plot_dir = "friends_stats_plots"
if os.path.exists(plot_dir):
    files = glob.glob(f"{plot_dir}/*")
    for f in files:
        os.remove(f)


nltk.download('punkt')
nltk.download('stopwords')

df = pd.read_csv("friends_dialogues.csv", names=["season", "episode", "character", "line"])
main_characters = ['Chandler','Joey','Monica','Phoebe','Rachel','Ross']
df = df[df["character"].isin(main_characters)]
plot_dir = 'friends_stats_plots'
os.makedirs(plot_dir, exist_ok=True)






# 1 Fréquence d’utilisation de la ponctuation par personnage
punctuation_counts = {char: {'exclamation': 0, 'question': 0, 'suspension': 0} for char in main_characters}

for character in main_characters:
    lines = " ".join(df[df['character'] == character]['line'])
    punctuation_counts[character]['exclamation'] = lines.count('!')
    punctuation_counts[character]['question'] = lines.count('?')
    punctuation_counts[character]['suspension'] = lines.count('...')

punctuation_df = pd.DataFrame.from_dict(punctuation_counts, orient='index')
punctuation_df.plot(kind='bar', figsize=(10, 6), color=['red', 'blue', 'green'])
plt.title("Fréquence d’utilisation de la ponctuation expressive par personnage")
plt.xlabel("Personnage")
plt.ylabel("Nombre d'occurrences")
plt.xticks(rotation=0)
plt.legend(["!", "?", "..."], title="Ponctuation")

plt.savefig(f"{plot_dir}/Ponctuation.png")
plt.close()



# 2. Moyenne mots par réplique par personnage
df['line_length'] = df['line'].apply(lambda x: len(x.split()))
average_words_per_character = df.groupby('character')['line_length'].mean().sort_values()
plt.figure(figsize=(8, 6))
average_words_per_character.plot(kind='barh', color='lightgreen')
plt.title('Moyenne de mots par réplique pour chaque personnage')
plt.xlabel('Nombre moyen de mots')
plt.ylabel('Personnage')
for i, v in enumerate(average_words_per_character):
    plt.text(v + 0.1, i, str(round(v, 2)), color='blue', va='center')
plt.savefig(f"{plot_dir}/moyenne_mots_par_replique.png")
plt.close()



# 3. Mot le plus utilisé par chaque personnage
stop_words = set(stopwords.words('english'))
most_used_words = []
for character in main_characters:
    lines = " ".join(df[df['character'] == character]['line'])
    words = word_tokenize(lines.lower())
    filtered_words = [word for word in words if word.isalpha() and word not in stop_words]
    most_common_word, frequency = Counter(filtered_words).most_common(1)[0]
    most_used_words.append((character, most_common_word, frequency))

most_used_words_df = pd.DataFrame(most_used_words, columns=['Character', 'Word', 'Frequency'])
plt.figure(figsize=(8, 6))
plt.bar(most_used_words_df['Character'], most_used_words_df['Frequency'], color='orange')
plt.title('Mot le plus utilisé par chaque personnage')
plt.xlabel('Personnage')
plt.ylabel('Fréquence')
for i, (word, freq) in enumerate(zip(most_used_words_df['Word'], most_used_words_df['Frequency'])):
    plt.text(i, freq + 20, f'{word} ({freq})', ha='center', va='bottom')
plt.savefig(f"{plot_dir}/mot_le_plus_utilise_par_personnage.png")
plt.close()