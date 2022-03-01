import numpy as np
import pandas as pd

# FEATURES = {"region", "age", "sex", "relationship_status", "job_status"}
FEATURES = {"region", "age", "sex", "relationship_status"}

FEATURES_REGION = ["NA", "EMEA", "SA", "ASOC"]
# Proportions are based on https://www.statista.com/statistics/1090122/netflix-global-subscribers-by-region/
PROPERTIES_REGION_PROP = [0.3630, 0.3275, 0.1843, 0.1252]

FEATURES_AGE_RNG = [10, 60]
PROPERTIES_AGE_BIN = 10

# Proportions are based on https://financesonline.com/number-of-netflix-subscribers/ Allowed 1% for NB persons
FEATURES_SEX = ["M", "F", "O"]
PROPERTIES_GENDER_PROP = [.485, .505, .01]

FEATURES_RELATIONSHIP = ["MARRIED", "PARTNER", "SINGLE", "OTHER"]

# To consider but dont start with
FEATURES_PROFESSION = ["full time", "part time", "unemployed", "self employed", "student"]


def load_users(path: str) -> np.ndarray:
    """
    Loads users into a pandas dataframe from disk, and finds all unique user ID's.
    :param path: Path to load from.
    :return: Numpy array of unique user ID's.
    """
    df = pd.read_csv(path,
                     names=["item", "user", "rating"],
                     dtype={
                         'item': 'int32',
                         'user': 'int32',
                         'rating': 'int8'
                     })

    return df['user'].unique()


def data_gen(users_array: np.ndarray) -> np.ndarray:
    """
    Generates synthetic data for the provided users, specifics of this data are detailed in comments for each section.
    :param users_array: User ID to create synthetic data for.
    :return: Array of synthetic, tokenized data.
    """

    num_users = users_array.shape[0]

    # Creating synthetic ages from a normal distribution
    mean = np.mean(FEATURES_AGE_RNG)
    std_dev = 15

    ages = np.random.normal(mean, std_dev, num_users)

    # Clip ages to range ends and convert to integers
    np.clip(ages, FEATURES_AGE_RNG[0], FEATURES_AGE_RNG[1], out=ages)
    ages = ages.astype(np.int8)

    # Ages (binned and tokenized) Binning is right exclusive
    ranges = np.arange(FEATURES_AGE_RNG[0], FEATURES_AGE_RNG[1], PROPERTIES_AGE_BIN, dtype=np.int8)
    ages_b_t = np.digitize(ages, ranges) - 1

    # Region (tokenized)
    regions_t = np.random.choice(np.arange(0, len(FEATURES_REGION)), size=num_users, p=PROPERTIES_REGION_PROP)

    # Sex (tokenized)
    sex_t = np.random.choice(np.arange(0, len(FEATURES_SEX)), size=num_users, p=PROPERTIES_GENDER_PROP)

    # Relationship status (tokenized)
    relationship_t = np.random.choice(np.arange(0, len(FEATURES_RELATIONSHIP)), size=num_users)

    out = np.zeros((num_users, 5), dtype=np.int32)
    out[:, 0] = users_array
    out[:, 1] = ages_b_t
    out[:, 2] = regions_t
    out[:, 3] = sex_t
    out[:, 4] = relationship_t

    return out


def join_ratings_and_user_attributes(ratings_path: str, attributes_path: str, to_path: str):
    """
    This method joins rating files and attributes files to produce a list of ratings with user attributes attached.
    :param ratings_path: Path to CSV file containing ratings information
    :param attributes_path: Path to CSV file containing user attributes information
    :return:
    """

    ratings_df = pd.read_csv(ratings_path, names=["MOVIE_ID", "USER_ID", "RATING"])
    users_df = pd.read_csv(attributes_path)

    merged = pd.merge(users_df, ratings_df, on="USER_ID")

    merged.to_csv(to_path, index=False)


def data_write(data: np.ndarray, path: str) -> None:
    """
    Writes data to disk.
    :param path: Path to save data to.
    :param data: Array of data to be written.
    """
    pd.DataFrame(data, columns=["USER_ID", "AGE_T", "REGION_T", "SEX_T", "RELATIONSHIP_T"])\
        .to_csv(path, index=False)


def main():
    # users = load_users("outputs/ratings_1000.csv")
    # data = data_gen(users)
    # data_write(data, "outputs/synthetic_data_frm_ratings_1000.csv")

    # Uncomment the following line to generate the joined files, to be used by WEKA learners for association rule
    # generation
    join_ratings_and_user_attributes("outputs/ratings_1000.csv",
                                     "outputs/synthetic_data_frm_ratings_1000.csv",
                                     "outputs/merged_synthetic_data_1000.csv")


if __name__ == "__main__":
    main()
