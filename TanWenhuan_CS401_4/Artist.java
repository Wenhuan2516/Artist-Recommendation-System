public class Artist {
    private int id;
    private String name;
    private String url;
    private String pictureURL;

    public Artist(int id, String name, String url, String pictureURL) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.pictureURL = pictureURL;
    }

    public Artist(int id) {
        this.id = id;
    }

    public Artist(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("ID: " + id + "; ");
        strBuilder.append("Name: " + name );
        return strBuilder.toString();
    }
}
