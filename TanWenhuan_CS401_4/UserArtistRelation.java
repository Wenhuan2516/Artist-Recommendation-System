import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

import java.util.ArrayList;

public class UserArtistRelation {
    private EdgeWeightedGraph g;

    public UserArtistRelation(int n) {
        g = new EdgeWeightedGraph(n);
    }

    public void createConnection(User user, Artist artist, double weight) {
        Edge e = new Edge(user.getUserID(), artist.getId() + 5000, weight);
        g.addEdge(e);

    }

    public ArrayList<Integer> listArtist(int userId) {
        ArrayList<Edge> connections = new ArrayList<>();
        for (Edge e: g.adj(userId)) {
            connections.add(e);
        }
        ArrayList<Integer> ArtistList = new ArrayList<>();

        for (int i = 0; i < connections.size();i++) {
            int artistId= connections.get(i).other(userId);
            ArtistList.add(artistId - 5000);
        }
        return ArtistList;
    }


    public ArrayList<Integer> listUsers(int artistId) {
        ArrayList<Edge> connections = new ArrayList<>();
        for(Edge e: g.adj(artistId + 5000)) {
            connections.add(e);
        }
        ArrayList<Integer>  userList = new ArrayList<>();
        for (int i = 0; i < connections.size();i++) {
            int userId= connections.get(i).other(artistId + 5000);
            userList.add(userId);
        }
        return userList;
    }

    public double countTotalWeight(Artist artist) {
        ArrayList<Edge> connections = new ArrayList<>();
        for (Edge e : g.adj(artist.getId()+5000)) {
            connections.add(e);
        }
        double total = 0;
        for (int i = 0; i < connections.size();i++) {
            total = total + connections.get(i).weight();
        }
        return total;
    }

    public double countWeight(Artist artist, ArrayList<Integer> userList) {
        ArrayList<Edge> connections = new ArrayList<>();
        for ( int i = 0; i < userList.size(); i++) {
            for (Edge e : g.adj(userList.get(i))) {
                int artistId = e.other(userList.get(i)) - 5000;
                if ( artistId == artist.getId()) {
                    connections.add(e);
                }
            }
        }
        double total = 0;
        for (int i = 0; i < connections.size();i++) {
            total = total + connections.get(i).weight();
        }
        return total;
    }

    public EdgeWeightedGraph getG() {
        return g;
    }
}
