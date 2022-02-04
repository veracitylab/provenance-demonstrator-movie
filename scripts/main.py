from time import sleep

import pandas as pd
import requests


def load_movies(path: str) -> pd.DataFrame:
    df = pd.read_csv(path,
                     index_col=0,
                     names=['ID', 'RELEASE', 'TITLE'],
                     encoding='windows-1252',
                     parse_dates=['RELEASE'])
    return df


def write_movies_sql(df: pd.DataFrame, limit: int) -> None:
    t_name = "MOVIES"
    t_cols = "(id, title, release_year, poster_url)"
    t_cols_no_img = "(id, title, release_year)"
    sql_str = ""

    with open('movies.sql', 'w') as file:
        lines = []
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


def load_data(path: str, limit: int) -> dict:
    d = {}
    with open(path, 'r') as file:
        lines = file.read().splitlines()
        curr_id = None
        for line in lines:
            if line[-1] == ":":
                # last character is colon which indicates movie title
                # print(int(line[:-1]))
                curr_id = int(line[:-1])

                if curr_id == limit:
                    break

                d[curr_id] = []
            else:
                # Strip the rating date for now
                e = line.split(',')
                d[curr_id].append(",".join(e[:-1]))
                pass

    with open('ratings_%s.csv' % limit, "w") as file:
        for k, v in d.items():
            for r in v:
                file.write("%s,%s\n" % (k, r))

        print("bp")

    return d


def perform_img_query(title: str):
    base_url = r'https://v2.sg.media-imdb.com/suggestion/'
    retries = 5

    title = title.lower()

    query_url = base_url + title[0] + "/" + title.replace(" ", "-") + ".json"
    res = None

    for x in range(0, retries):
        sleep_time = 1
        error = None
        try:
            res = requests.get(query_url)
            error = None
        except Exception as e:
            error = str(e)
            pass

        if error:
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
            print(first)
            return first
        else:
            print("No image found for: " + title)
            return None


def main():
    movie_path = r"data/movie_titles.csv"
    data_1_path = r"data/combined_data_1.txt"
    # movies_frame = load_movies(movie_path)

    # write_movies_sql(movies_frame, 1000)

    load_data(data_1_path, 100)

    # print(movies_frame)
    print("bp")


if __name__ == "__main__":
    main()
