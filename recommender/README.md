# Recommender module

This module is responsible for producing recommendations for the web module. Adding new recommenders is to be done in 
this module. To do so, extend the AbstractMovieRecommender class and implement the unimplemented methods.

There is also a configuration class used by the web module to select and execute a recommender's algorithm

Provenance data is determined in this module, and follows the structure defined in the 