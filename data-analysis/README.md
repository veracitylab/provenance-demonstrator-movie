# Information about the python analysis scripts in this directory

### Directory structure
```
data-analysis/
│   README.txt
│   sql_generation.py   
│   data_morphing.py
│   synthetic_data.py
│   knn.py
├── dataset/
├── outputs/
```
- sql_generation.py: Responsible for generating the SQL used by the SpringBoot application when creating the in memory DB
- data_morphing.py: Used to reformat and/or create data to be used by prediction engines
- synthetic_data.py: Used to create synthetic data to be used by prediction engines
- knn.py: Current doesn't work that well, in progress
- dataset/: This directory should be created and populated with the files from the Kaggle dataset [found here](https://www.kaggle.com/netflix-inc/netflix-prize-data).
- outputs/: This directory should be automatically created by scripts that output data.

### Dependencies
Scripts written using python 3. Dependencies for the python scripts can be found in requirements.txt

To install the requirements run ```pip install -r requirements.txt```

### Tokenized data output (from synthetic_data.py)
#### Ages
Ages are between 10 and 60, and are generated using a Gaussian distribution with clipping.

The tokens are as follows:

| Age range | Token |
|-----------|-------|
| 10-19     | 0     |
| 20-29     | 1     |
| 30-39     | 2     |
| 40-49     | 3     |
| 50-60     | 4     |


#### Regions 
Regions are generated according to the proportion of Netflix uses from each region.

The tokens are as follows:

| Region           | Token |
|------------------|-------|
| North America    | 0     |
| EMEA             | 1     |
| South America    | 2     |
| Asia and Oceania | 3     |


#### Sex
Sexes are generated according to the proportion of Netflix users identifying with each sex.

The tokens are as follows:

| Sex            | Token |
|----------------|-------|
| Male           | 0     |
| Female         | 1     |
| Gender Diverse | 2     |


#### Relationship status
Relationship statuses were randomly generated.

The tokens are as follows:

| Relationship status | Token |
|---------------------|-------|
| Married             | 0     |
| Partner             | 1     |
| Single              | 2     |
| Other               | 3     | 