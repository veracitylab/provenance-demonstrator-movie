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


def write_movies_sql(df: pd.DataFrame, limit: int):
    """
    Converts a dataframe into SQL statements to be written to disk for database insertion
    :param df: Dataframe to extract movie information from
    :param limit: Set a limit on the number of movies to write SQL statements for
    """

    t_name = "MOVIES"
    t_cols = "(id, title, release_year, poster_url)"
    t_cols_no_img = "(id, title, release_year)"
    filename = r"output/movies.sql"

    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with open(filename, 'w') as file:
        df['RELEASE'] = pd.to_datetime(df['RELEASE'], format="%Y-%m-%d")
        for i, e in df.iterrows():
            if i.__eq__(limit):
                break

            url = perform_img_query(e['TITLE'])
            title = str(e['TITLE']).replace("'", "''")
            date = str(e['RELEASE']).split(' ')[0]

            if url is None:
                stmt = f"INSERT INTO {t_name}{t_cols_no_img} VALUES({i}, \'{title}\', \'{date}\');\n"
            else:
                stmt = f"INSERT INTO {t_name}{t_cols} VALUES({i}, \'{title}\', \'{date}\', \'{url}\');\n"

            file.write(stmt)


def perform_img_query(title: str) -> Union[str, None]:
    """
    Performs a query using IMDB public API to find the title of a movie, and then uses the URL for IMDB posters
    to find the poster information. The API has a request limit per second so a timeout is used to get the URL once
    this limit is hit
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
        images = raw_data['d']
    else:
        print("No title found for: " + title)
        return None

    # Assume that the poster URL is the first image
    if len(images) > 0:
        if "i" in images[0].keys():
            first = images[0]["i"]["imageUrl"]
            return first
        else:
            print("No image found for: " + title)
            return None


def main():
    # Constants
    limit = 100
    movie_title_path = r"dataset/movie_titles.csv"

    # Uncomment these lines to generate SQL for the loaded movie titles and their URL's
    movies_frame = load_movies(movie_title_path)
    write_movies_sql(movies_frame, limit)


if __name__ == "__main__":
    main()
