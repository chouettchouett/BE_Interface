from transformers import pipeline
import torch

# Modèle DistilBERT pour l'analyse des émotions
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
emotion_pipeline = pipeline("text-classification", model="bhadresh-savani/distilbert-base-uncased-emotion", device=device)

# Trois phrases à analyser
phrases = [
    "I'm so happy to see you again!",
    "I can't believe this is happening, it's so frustrating.",
    "This is the saddest thing I've ever seen."
]

# Analyse des émotions
emotion_results = emotion_pipeline(phrases)

# Affichage
for phrase, result in zip(phrases, emotion_results):
    print(f"Phrase : \"{phrase}\"")
    print(f"→ Emotion : {result['label']} (score : {round(result['score'], 3)})")
    print()