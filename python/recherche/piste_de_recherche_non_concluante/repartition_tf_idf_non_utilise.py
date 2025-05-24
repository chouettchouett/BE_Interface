"""
tfidf_matrix = vectorizer.fit_transform(char_docs)
scores = tfidf_matrix.max(axis=0).toarray().ravel()
features = vectorizer.get_feature_names_out()

selected_features = [features[i] for i in range(len(features)) if scores[i] > 0.2]

plt.hist(scores, bins=50)
plt.title("Distribution des scores TF-IDF")
plt.show()

scores
"""