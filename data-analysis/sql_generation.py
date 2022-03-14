import os
from time import sleep
from typing import Union

import pandas as pd
import requests


def load_movies(path: str) -> pd.DataFrame:
    """
    Loads movie information provided by the path string. Currently use windows encoding as that is what is used by the
    dataset
    :param path: Path to the movies data file
    :return: DataFrame (implicit index) of the loaded movies
    """
    df = pd.read_csv(path,
                     index_col=0,
                     names=['ID', 'RELEASE', 'TITLE'],
                     encoding='windows-1252',
                     parse_dates=['RELEASE'])
    return df


def load_imdb_data(path: str) -> pd.DataFrame:
    """
    Loads IMDB data provided by the path string
    :param path: Path to IMDB data file
    :return: DataFrame (implicit index) of the loaded IMDB movies
    """
    df = pd.read_csv(path,
                     index_col=0,
                     names=['IMDB_ID', "TITLE_TYPE", "TITLE_1", "TITLE_2", "ADULT",
                            "YEAR_S", "YEAR_E", "RUNTIME", "GENRES"],
                     delimiter="\t")
    return df


def write_movies_sql(movie_data: pd.DataFrame, imdb_data: pd.DataFrame, limit: int):
    """
    Converts a dataframe into SQL statements to be written to disk for database insertion
    :param movie_data: Dataframe to extract movie information from
    :param limit: Set a limit on the number of movies to write SQL statements for
    """

    t_name = "MOVIES"
    t_cols = "(id, title, release_year, poster_url, genres)"
    t_cols_no_img = "(id, title, release_year)"
    t_cols_no_gen = "(id, title, release_year, url)"
    filename = r"outputs/movies.sql"
    indx_file_name = r"outputs/idxsfile_100.csv"
    idxs = []

    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with open(filename, 'w') as file:
        movie_data['RELEASE'] = pd.to_datetime(movie_data['RELEASE'], format="%Y-%m-%d")

        for i, e in movie_data.iterrows():
            if i.__eq__(limit):
                break

            url_and_id = perform_img_query(e['TITLE'])
            title = str(e['TITLE']).replace("'", "''")
            date = str(e['RELEASE']).split(' ')[0]

            if url_and_id is None:
                idxs.append((i, None))
                stmt = f"INSERT INTO {t_name}{t_cols_no_img} VALUES({i}, \'{title}\', \'{date}\');\n"
            else:
                genres_exist = url_and_id[1] in imdb_data.index
                idxs.append((i, url_and_id[1]))

                if genres_exist:
                    genres = imdb_data.loc[url_and_id[1]]['GENRES']
                    stmt = f"INSERT INTO {t_name}{t_cols} VALUES({i}, \'{title}\', \'{date}\', \'{url_and_id[0]}\', " \
                           f"\'{genres}\');\n "
                else:
                    stmt = f"INSERT INTO {t_name}{t_cols_no_gen} VALUES({i}, \'{title}\', \'{date}\', " \
                           f"\'{url_and_id[0]}\');\n "

            file.write(stmt)

    with open(indx_file_name, 'w') as file2:
        for line in idxs:
            file2.write(','.join(str(s) for s in line) + '\n')


def perform_img_query(title: str) -> Union[tuple, None]:
    """
    Performs a query using IMDB public API to find the title of a movie, and then uses the URL for IMDB posters
    to find the poster information. The API has a request limit per second so a timeout is used to get the URL once
    this limit is hit. Also finds the MovieID to use for searching the IMDB data for movie genres
    :param title: Title to search for
    :return: URL of the poster found for the given title
    """

    base_url = r'https://v2.sg.media-imdb.com/suggestion/'
    retries = 5

    title = title.lower()

    query_url = base_url + title[0] + "/" + title.replace(" ", "-") + ".json"
    res = None

    # Retry for n times
    for _ in range(0, retries):
        sleep_time = 1

        try:
            res = requests.get(query_url)
            error = None
        except Exception as e:
            error = str(e)

        if error:
            print("Error encountered: %s\nRetrying..." % error)
            sleep(sleep_time)
            sleep_time *= 2
        else:
            break

    if res is None:
        print("Timeout after " + str(retries) + " for title: " + title)
        return None

    raw_data = res.json()

    if 'd' in raw_data.keys():
        data = raw_data['d']
    else:
        print("No title found for: " + title)
        return None

    # Assume that the poster URL is the first image
    if len(data) > 0:
        if "i" in data[0].keys():
            # Assume that the Movie ID is also in there
            first = (data[0]["i"]["imageUrl"], data[0]["id"])
            return first
        else:
            print("No image found for: " + title)
            return None


def main():
    # Constants
    limit = 1000
    imdb_data_path = r"dataset/imdb_data.tsv"
    movie_title_path = r"dataset/movie_titles.csv"

    # Uncomment these lines to generate SQL for the loaded movie titles and their URL's
    movies_frame = load_movies(movie_title_path)
    imdb_data = load_imdb_data(imdb_data_path)
    write_movies_sql(movies_frame, imdb_data, limit)

    print("jeff")


if __name__ == "__main__":
    main()
