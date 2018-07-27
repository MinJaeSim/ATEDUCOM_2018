package ac.ajou.simminje.ateducom.trigger;


public class Trigger {
    private String userName;
    private int type;
    private String description;
    private String profileImageUrl;
    private int id;
    private String uid;

    public Trigger() {

    }

    public Trigger(String userName, int type, String description, String profileImageUrl, int id, String uid) {
        this.userName = userName;
        this.type = type;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
        this.id = id;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
