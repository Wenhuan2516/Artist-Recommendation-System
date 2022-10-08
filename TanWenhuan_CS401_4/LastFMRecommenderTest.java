import edu.princeton.cs.algs4.Edge;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class LastFMRecommenderTest {
    private LastFMRecommender recommender;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        FriendshipNetwork friendShip = new FriendshipNetwork(3000);
        UserArtistRelation network = new UserArtistRelation(25000);
        HashSet<Integer> userIdSet = new HashSet<>();

        ArrayList<Artist> artistList  = new ArrayList<>();
        File file1 = new File("artists.dat");
        Scanner input = new Scanner(file1);
        String firstLine1 = input.nextLine();
        while ( input.hasNextLine()) {
            String line = input.nextLine();
            String[] data = line.split("\t");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String url = data[2];
            Artist artist;
            if (data.length == 3) {
                artist = new Artist(id, name, url);
            } else {
                String pictureURL = data[3];
                artist = new Artist(id, name, url, pictureURL);
            }

            artistList.add(artist);
        }


        File file2 = new File("user_friends.dat");
        Scanner sc = new Scanner(file2);
        String firstLine2 = sc.nextLine();

        while (sc.hasNextLine()) {
            String line1 = sc.nextLine();
            String[] s = line1.split("\t");
            int userId = Integer.parseInt(s[0]);
            User user = new User(userId);

            userIdSet.add(userId);
            int friendId = Integer.parseInt(s[1]);
            User friend = new User(friendId);
            userIdSet.add(friendId);
            friendShip.createFriendship(user,friend);
        }

        File file3 = new File("user_artists.dat");
        Scanner sct = new Scanner(file3);
        String firstLine3 = sct.nextLine();

        while (sct.hasNextLine()) {
            String line2 = sct.nextLine();
            String[] s = line2.split("\t");
            int userId = Integer.parseInt(s[0]);
            User user = new User(userId);
            userIdSet.add(userId);
            int artistId = Integer.parseInt(s[1]);
            Artist artist= new Artist(artistId);
            double weight = Double.parseDouble(s[2]);
            network.createConnection(user, artist, weight);
        }
        ArrayList<User> userList = new ArrayList<>();
        for (int u : userIdSet) {
            User e = new User(u);
            userList.add(e);
        }
        recommender = new LastFMRecommender(network, friendShip, artistList, userList);
    }
    @AfterEach
    public void tearDown() {
        recommender = null;
    }

    @Test
    public void listFriendTest() {
        recommender.listFriends(3);
    }

    @Test
    public void commonFriendTest1() {
        recommender.commonFriends(5, 2);
    }

    @Test
    public void commonFriendTest2() {
        recommender.commonFriends(11, 127);
    }

    @Test
    public void listArtistsTest1() {
        recommender.listArtists(4, 5);
    }

    @Test
    public void listArtistsTest2() {
        recommender.listArtists(8, 2);
    }

    @Test
    public void listArtistsTest3() {
        recommender.listArtists(12, 80);
    }

    @Test
    public void listTop10Test() {
        recommender.listTop10();
    }

    @Test
    public void recommend10Test1() {
        recommender.recommend10(15);
    }

    @Test
    public void recommend10Test2() {
        recommender.recommend10(80);
    }

}
