import requests


def perform_img_query(title: str):
    base_url = r'https://v2.sg.media-imdb.com/suggestion/'

    query_url = base_url + title[0] + "/" + title.replace(" ", "-") + ".json"
    res = requests.get(query_url)
    raw_data = res.json()

    if 'd' in raw_data.keys():
        images = raw_data['d']
    else:
        print("No title found for: " + title)
        return None

    # Assume that the poster URL is the first image
    if len(images) > 0:
        first = images[0]["i"]["imageUrl"]
        return first


def main():
    perform_img_query("the expanse")
    pass


if __name__ == "__main__":
    main()
