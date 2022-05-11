import numpy as np
import sqlite3
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from os.path import dirname, join,abspath
link = abspath("/data/data/nhattan.lnt.clothesshop/databases/ClothesDatabase")
conn = sqlite3.connect(link, isolation_level=None,
                       detect_types=sqlite3.PARSE_COLNAMES)
db_df = pd.read_sql_query("SELECT IDTAIKHOAN, IDSANPHAM, DANHGIA FROM DANHGIA", conn)

df = db_df

df.info()
df = df.dropna()
df.info()
df['DANHGIA'] = df['DANHGIA'].astype(float)

from scipy.sparse import csr_matrix
columns = df[['IDTAIKHOAN', 'IDSANPHAM', 'DANHGIA']]
df_new = columns

ratingCount = (df_new. groupby(by=['IDSANPHAM'])['DANHGIA'].count().reset_index().rename(columns={'DANHGIA': 'totalRatingCount'})[['IDSANPHAM', 'totalRatingCount']])

# plt.show()
rating_with_totalRatingCount = df_new.merge(ratingCount, left_on = 'IDSANPHAM', right_on = 'IDSANPHAM', how = 'left')

popularity_threshold = 3
rating_popular = rating_with_totalRatingCount.query('totalRatingCount >= @popularity_threshold')

user_rating_pivot = rating_popular.pivot_table(index='IDSANPHAM', columns='IDTAIKHOAN', values='DANHGIA').fillna(0)
user_rating_matrix = csr_matrix(user_rating_pivot.values)

def BCTN_HTGY(IDTAIKHOAN):
    model_knn = NearestNeighbors(metric='cosine', algorithm='brute')
    model_knn.fit(user_rating_matrix)
    query_index = 0
    for i in range(len(user_rating_pivot)):
        if ( user_rating_pivot.index[i] == IDTAIKHOAN ):
            print("Vị trí:",i)
            query_index = i
            break
    print(type(query_index),query_index)
    distances, indices = model_knn.kneighbors(user_rating_pivot.iloc[query_index, :].values.reshape(1, -1), n_neighbors=6)
    array_best = []
    for i in range(0, len(distances.flatten())):
        if i == 0:

            print('Recommendations for {0}:\n'.format(user_rating_pivot.index[query_index], distances.flatten()[-i]))
        else:
            array_best.append(user_rating_pivot.index[indices.flatten()[-i]])
            print('{0}: {1}, with distance of {2}.'.format(i, user_rating_pivot.index[indices.flatten()[-i]], distances.flatten()[-i]))
    return array_best