import edu.princeton.cs.algs4.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class LastFMRecommender {
    private final UserArtistRelation fanGraph;
    private final FriendshipNetwork friendNetwork;
    private ArrayList<Artist> artists;
    private ArrayList<User> users;

    public LastFMRecommender(UserArtistRelation graph1, FriendshipNetwork graph2,
                             ArrayList<Artist> ArtistList, ArrayList<User> UserList) {
        fanGraph = graph1;
        friendNetwork = graph2;
        artists = ArtistList;
        users = UserList;
    }

    private ArrayList<Integer> findfriendList(int userId) {
        ArrayList<Integer> friendList = new ArrayList<>();
        for (int e : friendNetwork.friendList(userId)) {
            friendList.add(e);
        }
        return friendList;
    }

    private void printList(ArrayList<Integer> list) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("The list of friends are [");

        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                strBuilder.append("ID:" + list.get(i) + ", ");
            }
            strBuilder.append("ID:" + list.get(list.size() - 1) + "]");
        } else if ( list.size() == 1){
            strBuilder.append("ID:" + list.get(0) + "]");
        } else {
            strBuilder.append("]");
        }

        System.out.println(strBuilder);
    }

    private ArrayList<Integer> commonElement(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> commonElement = new ArrayList<>();
        for (int i = 0; i < list1.size();i++) {
            for (int k = 0; k < list2.size(); k++) {
                if (list1.get(i) == list2.get(k)) {
                    commonElement.add(list1.get(i));
                }
            }
        }
        return commonElement;
    }

    public void listFriends(int userId) {
        ArrayList<Integer> friendList = findfriendList(userId);
        printList(friendList);
    }

    public void commonFriends(int user1, int user2) {
        ArrayList<Integer> list1 = findfriendList(user1);
        ArrayList<Integer> list2 = findfriendList(user2);
        ArrayList<Integer> commonFriend = commonElement(list1, list2);
        HashSet<Integer> set = new HashSet<>();
        for (int e : commonFriend) {
            set.add(e);
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int t : set) {
            list.add(t);
        }
        printList(list);
    }

    private ArrayList<Integer> findArtistList(int userId) {
        ArrayList<Integer> artistList = new ArrayList<>();
        for (int e: fanGraph.listArtist(userId)) {
            artistList.add(e);
        }

        return artistList;
    }

    private ArrayList<Artist> IdToArtist(ArrayList<Integer> IdList) {
        ArrayList<Artist>  list = new ArrayList<>();

        for (int id: IdList) {
            for (int i = 0; i < artists.size();i++) {
                if (id == artists.get(i).getId()) {
                    list.add(artists.get(i));
                }
            }
        }
        return list;
    }

    public void listArtists(int user1, int user2) {
        ArrayList<Integer> list1 = findArtistList(user1);
        ArrayList<Integer> list2 = findArtistList(user2);
        ArrayList<Integer> commonArtist = commonElement(list1, list2);
        ArrayList<Artist> CommonList = IdToArtist(commonArtist);
        System.out.println("The artists that both of them are listening to:");
        for (int i = 0; i < CommonList.size(); i++) {
            System.out.println(CommonList.get(i).getName());
        }
    }

    public void listTop10() {
        HashMap<Artist, Double> weightRecord = popularity(artists);
        List<Map.Entry<Artist, Double>> list = rankPopularity(weightRecord);
        ArrayList<Artist> artistIDlist = new ArrayList<>();
        ArrayList<Double> artistWeight = new ArrayList<>();

        for (int i =0; i < 10; i++) {
            artistIDlist.add(list.get(i).getKey());
            artistWeight.add(list.get(i).getValue());
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Top " + (i+1) + ": " + artistIDlist.get(i).getName() + "-----------" + artistWeight.get(i));
        }
    }

    private HashMap<Artist, Double> popularity(ArrayList<Artist> artistList) {
        HashMap<Artist, Double> weightRecord = new HashMap<>();
        for(int i = 0; i < artistList.size(); i++) {
            double weight = fanGraph.countTotalWeight(artistList.get(i));
            weightRecord.put(artistList.get(i), weight);
        }

        return weightRecord;
    }

    private List<Map.Entry<Artist, Double>> rankPopularity(HashMap<Artist, Double> weightRecord) {
        List<Map.Entry<Artist, Double>> list = new LinkedList<Map.Entry<Artist, Double>>(weightRecord.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Artist, Double>>() {
            @Override
            public int compare(Map.Entry<Artist, Double> o1, Map.Entry<Artist, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

    private HashMap<Artist, Double> popularityInGroup(ArrayList<Artist> artistList, ArrayList<Integer> userList) {
        HashMap<Artist, Double> weightRecord = new HashMap<>();
        for(int i = 0; i < artistList.size(); i++) {
            double weight = fanGraph.countWeight(artistList.get(i), userList);
            weightRecord.put(artistList.get(i), weight);
        }
        return weightRecord;
    }

    public void recommend10(int user) {
        ArrayList<Integer> friendList = findfriendList(user);
        HashSet<Integer> totalArtistSet = new HashSet<>();
        ArrayList<Integer> artistListFromUser = findArtistList(user);

        //add all artists connected to each friend to the set
        for (int i = 0; i < friendList.size(); i++) {
            ArrayList<Integer> artistToFriend = findArtistList(friendList.get(i));
            for (int k = 0; k < artistToFriend.size(); k++) {
                totalArtistSet.add(artistToFriend.get(k));
            }
        }

        //remove the artists that the user is already listening to
        for (int e: artistListFromUser) {
            if ( totalArtistSet.contains(e)) {
                totalArtistSet.remove(e);
            }
        }

        //convert the set to arrayList
        ArrayList<Integer> totalArtist = new ArrayList<>();
        for (int e: totalArtistSet) {
            totalArtist.add(e);
        }

        ArrayList<Artist> artistListFromFriends = IdToArtist(totalArtist);
        HashMap<Artist, Double> weightRecords = popularityInGroup(artistListFromFriends, friendList);
        List<Map.Entry<Artist, Double>> list = rankPopularity(weightRecords);

        ArrayList<Artist> artistIDlist = new ArrayList<>();
        ArrayList<Double> artistWeight = new ArrayList<>();

        for (int i =0; i < 10; i++) {
            artistIDlist.add(list.get(i).getKey());
            artistWeight.add(list.get(i).getValue());
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Recommend #" + (i+1) + ": " + artistIDlist.get(i).getName() + "-----------" + artistWeight.get(i));
        }
    }
}
