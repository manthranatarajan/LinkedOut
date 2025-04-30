package classes;

import java.util.ArrayList;

public class Account {
    // INSTANCE VARS ---
    private boolean success;
    private String email;
    private String username;
    private String password;
    private String linkedInLink;
    private String description;
    private int connectionsMade;
    private ArrayList<Account> matchedUsers;
    private ArrayList<Badge> badges;

    // CONSTRUCTORS ---
    public Account(String formData, boolean creationSuccess) {
        String[] credentials = formData.split(" ");

        if (creationSuccess) { // if valid account data, set account info
            this.email = credentials[0];
            this.username = credentials[1];
            this.password = credentials[2];
            this.linkedInLink = credentials[3];

            this.connectionsMade = 0;
            this.matchedUsers = new ArrayList<>();
            this.badges = new ArrayList<>();
            this.success = creationSuccess;
        }
        else { // failed to create
            this.success = creationSuccess;
        }
    }

    public Account(String formData) { // for a possible account
        String[] credentials = formData.split(" ");

        this.email = credentials[0];
        this.username = credentials[1];
        this.password = credentials[2];
        this.linkedInLink = credentials[3];

        this.connectionsMade = 0;
        this.matchedUsers = new ArrayList<>();
        this.badges = new ArrayList<>();
    }

    // ACCESSORS --- 
    public boolean getSuccess() { return success; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getLinkedInLink() { return linkedInLink; }
    public String getDescription() { return description; }
    public ArrayList<Account> getMatchedUsers() { return matchedUsers; }
    public ArrayList<Badge> getBadges() { return badges; }
    public int getConnectionsMade() { return connectionsMade; }

    // MUTATORS ---
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setLinkedInLink(String linkedInLink) { this.linkedInLink = linkedInLink; }
    public void setDescription(String description) { this.description = description; }
    public void addMatchedUser(Account account) { this.matchedUsers.add(account); }
    public void addBadge(Badge badge) { this.badges.add(badge); }
    public void incConnectionsMade() { this.connectionsMade++; }

    // METHODS ---

    @Override
    public String toString() {
        return "{User: " + username + ", Email: " + email + "}";
    }



}
