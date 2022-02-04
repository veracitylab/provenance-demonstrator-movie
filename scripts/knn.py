import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors


def main():
    df = pd.read_csv("ratings_100.csv", names=["item", "user", "rating"], dtype={'item': 'int32', 'user': 'int32', 'rating': 'int8'})

    # print("Num ratings: %s" % df.size)
    print("Null values: \n%s\n" % df.isnull().sum())
    print("Shape: \n{0}\n".format(df.shape))

    # Data properties
    movies_avg_rating = df.groupby('item')['rating'].mean().sort_values(ascending=False).reset_index().rename(columns={'rating': 'Mean rating'})
    movies_cnt_rating = df.groupby('item')['rating'].count().sort_values(ascending=False).reset_index().rename(columns={'rating': 'Rating count'})

    print(movies_avg_rating.head())
    print(movies_cnt_rating.head())

    # Plot of the number of ratings given for a particular mean
    plt.hist(movies_avg_rating['Mean rating'], bins=100)
    plt.xlabel('Mean rating')
    plt.ylabel('Qty')
    plt.show()

    # Plot of the quantity of ratings given for a particular bin of rating counts
    plt.hist(movies_cnt_rating['Rating count'], bins=100)
    plt.xlabel('Ratings count')
    plt.ylabel('Qty')
    plt.show()

    print("Description of the rating counts: \n{0}\n".format(movies_cnt_rating['Rating count'].describe()))

    # popularity threshold determined by 75th percentile
    pop_thresh = 2000
    pop_movies = movies_cnt_rating[movies_cnt_rating['Rating count'] >= pop_thresh]

    # Get the ratings of only the movies that are present in the popular movies series
    pop_movies_ratings = df[df['item'].isin(pop_movies['item'])]

    pt = pop_movies_ratings.pivot_table(index='item', columns='user', values='rating').fillna(0)
    print(pt)

    df_matrix = csr_matrix(pt.values)
    model = NearestNeighbors(metric='cosine', algorithm='brute')
    model.fit(df_matrix)

    find = np.random.choice(pt.shape[0])
    distances, indices = model.kneighbors(pt.iloc[find, :].values.reshape(1, -1), n_neighbors=6)

    predictions = np.concatenate((indices, distances)).T

    for x in predictions:
        print(x)

    pass


if __name__ == "__main__":
    main()
