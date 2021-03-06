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

The application can be accessed [in your browser](http://localhost:8080) at http://localhost:8080.


## Using the application
The application can be used without the need to login, and the recommendations produced will contain provenance 
information when viewing the library. There are also three dummy accounts that have been pre-loaded with ratings to 
demonstrate provenance information for different users. The login details are as follows:
```
USERNAME:PASSWORD

jens:password
matthias:password
sam:password
```

## An example of provenance data
-- Todo, insert the guide here for some interesting details


## API specification
This documentation covers the specification of the API used by the web-server. Some endpoints are appended with .json as
they share the same name as some pages of the web server.


#### POST /
Used for logging into the application. Currently, this endpoint doesn't return a different status for login failure, as
the login failure is rendered server-side.

##### 

#### GET /library.json

#### GET /movie/{id}

#### GET /recommendations.json

#### GET /provenance

### PUT /movie/rate

## Additional information
There are several python scripts in the data-analysis directory that are used for creating SQL files and models that
are used by the recommender module. The details of these scripts are in [this documentation](data-analysis/README.md).


## Credits
- Matthias Galster (University of Canterbury)
- Jens Dietrich (Victoria University of Wellington)
- Sam Shankland (University of Canterbury)