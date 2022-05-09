import numpy as np
import sqlite3
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.neighbors import NearestNeighbors
from os.path import dirname, join,abspath
link = abspath("/data/data/nhattan.lnt.clothesshop/databases/ClothesDatabase")
conn = sqlite3.connect(link, isolation_level=None,
                       detect_types=sqlite3.PARSE_COLNAMES)
db_df = pd.read_sql_query("SELECT A.IDTAIKHOAN AS IDTAIKHOAN, B.TENSANPHAM AS TENSANPHAM, A.DANHGIA AS DANHGIA FROM DANHGIA A, SANPHAM B WHERE A.IDSANPHAM = B.IDSP", conn)

df = db_df

df.info()
df = df.dropna()
df.info()
df['DANHGIA'] = df['DANHGIA'].astype(float)

from scipy.sparse import csr_matrix
columns = df[['IDTAIKHOAN', 'TENSANPHAM', 'DANHGIA']]
df_new = columns

ratingCount = (df_new. groupby(by=['TENSANPHAM'])['DANHGIA'].count().reset_index().rename(columns={'DANHGIA': 'totalRatingCount'})[['TENSANPHAM', 'totalRatingCount']])

x = ratingCount.groupby('totalRatingCount').count()
plt.hist(ratingCount['totalRatingCount'], bins=20, edgecolor='black')
# plt.show()
rating_with_totalRatingCount = df_new.merge(ratingCount, left_on = 'TENSANPHAM', right_on = 'TENSANPHAM', how = 'left')

popularity_threshold = 10
rating_popular = rating_with_totalRatingCount.query('totalRatingCount >= @popularity_threshold')


user_rating_pivot = rating_popular.pivot_table(index='TENSANPHAM', columns='IDTAIKHOAN', values='DANHGIA').fillna(0)
user_rating_matrix = csr_matrix(user_rating_pivot.values)

def BCTN_HTGY(address):
    add = str(address)
    model_knn = NearestNeighbors(metric='cosine', algorithm='brute')
    model_knn.fit(user_rating_matrix)
    query = add
    query_index = np.random.choice(user_rating_pivot.shape[0])
    for i in range(len(user_rating_pivot)):
        if ( user_rating_pivot.index[i] == query ):
            print("Vị trí:",i)
            break
    print(type(query_index),query_index)
    distances, indices = model_knn.kneighbors(user_rating_pivot.iloc[query_index, :].values.reshape(1, -1), n_neighbors=1)
    array_best = []
    for i in range(0, len(distances.flatten())):
        if i == 0:
            array_best.append(user_rating_pivot.index[query_index])
            print('Recommendations for {0}:\n'.format(user_rating_pivot.index[query_index], distances.flatten()[-i]))
        else:
            print('{0}: {1}, with distance of {2}.'.format(i, user_rating_pivot.index[indices.flatten()[-i]], distances.flatten()[-i]))
    return array_best