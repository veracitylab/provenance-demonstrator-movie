import os


def create_ratings_data(path: str, limit: int):
    """
    Function loads the combined data file and creates a matrix of the data on disk with the following format:
        MOVIE ID, USER ID, RATING
    Temporal information is not retained in this process, however it can by changing line 77
    :param path: Path of the combined data to load
    :param limit: Number of records to load
    """

    d = {}
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'r') as file:
        lines = file.read().splitlines()
        curr_id = None
        for line in lines:
            if line[-1] == ":":
                # last character is colon which indicates movie title
                curr_id = int(line[:-1])

                if curr_id == limit:
                    break

                d[curr_id] = []
            else:
                # Strip the rating date for now
                e = line.split(',')
                d[curr_id].append(",".join(e[:-1]))

    with open('output/ratings_%s.csv' % limit, "w") as file:
        for k, v in d.items():
            for r in v:
                file.write("%s,%s\n" % (k, r))


def main():
    limit = 100
    data_1_path = r"dataset/combined_data_1.txt"
    create_ratings_data(data_1_path, limit)


if __name__ == "__main__":
    main()
