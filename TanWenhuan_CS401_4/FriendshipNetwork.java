import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashSet;

public class FriendshipNetwork {
    private Graph friendship;

    public FriendshipNetwork(int n) {
        friendship = new Graph(n);
    }

    public void createFriendship(User user, User friend) {
        friendship.addEdge(user.getUserID(), friend.getUserID());
    }

    public ArrayList<Integer> friendList(int userId) {
        ArrayList<Integer> friends = new ArrayList<>();
        HashSet<Integer> friendSet = new HashSet<>();
        for (int e : friendship.adj(userId)) {
            friendSet.add(e);
        }
        for (int e : friendSet) {
            friends.add(e);
        }
        return friends;
    }

    public Graph getFriendship() {
        return friendship;
    }
}
