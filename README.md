# Data provenance demonstrator 
This application is used to demonstrate the feasibility and possible implementation of a recommendation system that is
transparent in the way that it performs recommendations. This is done by providing users a way to view the data and 
algorithms used in producing a recommendation. The data used for this demonstrator is the [Kaggle Netflix Prize dataset](https://www.kaggle.com/netflix-inc/netflix-prize-data).
 
 There are 2 modules in this application:

**1. Recommender module**

This module is responsible for producing recommendations, and contains some different algorithms used for recommendation.
Each recommender in this module is responsible for producing its own provenance data, which is then consumed by the web
module. The recommenders in this module can be customized, or a new one written as long as it conforms to the interfaces
specified in [this documentation](recommender/README.md).

**2. Web module**

Written using SpringBoot, this module is the web application that is used for demonstration purposes. The specific 
details of this module can be found in [this documentation](web/README.md). The web application is self-contained and 
requires no setup to build and run.


## Running the application
The application is buildable with Gradle and requires JDK 11 at least to build and run. The following command will build
and run the application:
```./gradlew bootRun```