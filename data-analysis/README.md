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
