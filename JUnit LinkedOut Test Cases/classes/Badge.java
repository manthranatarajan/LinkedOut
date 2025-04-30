package classes;

// no need to test
public class Badge {
    String badgeName;
    String imageURL;

    public Badge(String badgeName, String imageURL) {
        this.badgeName = badgeName;
        this.imageURL = imageURL;
    }

    public String getBadgeName() { return badgeName; }
    public void setBadgeName(String badgeName) { this.badgeName = badgeName; }
    
    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    @Override
    public String toString() {
        return "{Badge: " + badgeName + ", imageURL: " + imageURL + "}";
    }
}
