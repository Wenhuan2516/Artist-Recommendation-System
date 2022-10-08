# Artist-Recommendation-System

## A Social-Network Based Recommendation System for last.fm
   In this assignment, you are asked to design and implement a social network-based recommender system for last.fm. 
   You are given the following dataset (Reference: http://www.lastfm.com):
   Dataset: data.zip file contains social networking, tagging, and music artist listening information from a set of 2K users from Last.fm online music system. http://www.last.fm 
##### • There are 1892 users and 17632 artists
##### • There are 12717 user-friend relations
##### • There are 92834 user-listened artist relations [user, artist, listeningCount]
#### Files:
     • artists.dat: This file contains information about music artists listened and tagged by the users. url and pictureURL will not be used in the assignment.
     • File format: id \t name \t url \t pictureURL
     • user_artists.dat: This file contains the artists listened by each user. It also provides a listening count for each [user, artist] pair.
     • File format: userID \t artistID \t weight
     • user_friends.dat: These files contain the friend relations between users in the database.
     • File format: userID \t friendID
### Functional Requirements:
    • Create a class, called LastFMRecommender.java. The class processes the data sets and provides the following public functionalities: 
     o listFriends(int user): prints the list of friends of the given user
     o commonFriends(int user1, int user2): prints the user1’s friends in common with user2
     o listArtists(int user1, int user2): prints the list of artists listened by both users 
     o listTop10(): prints the list of top 10 most popular artists listened by all users
     o recommend10(int user): recommends 10 most popular artists listened by the given user and his/her friends. 
     • Create a Junit test class to test the methods in LastFMRecorder.java
