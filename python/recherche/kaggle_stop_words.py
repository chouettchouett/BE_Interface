import kagglehub
import os

# stopwords plus avancés pour rendre plus précise la recherche de mot (1300 stopwords)
# nltk 140 mots, ici 1300
# source : https://www.kaggle.com/datasets/heeraldedhia/stop-words-in-28-languages
path = kagglehub.dataset_download("heeraldedhia/stop-words-in-28-languages")

list_files = os.listdir(path)
english_index = list_files.index("english.txt")
english = list_files[english_index]
english_file_path = os.path.join(path, english)

with open(english_file_path, 'r', encoding='utf-8') as file:
    with open('advanced_stopwords.txt', 'w') as file_stopwords:
    	file_stopwords.write(file.read())