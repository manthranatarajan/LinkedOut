package classes;

// This class represents the physical user, and the actions they can take.
public class User {
    // INSTANCE VARS -- 
    private String formData; // user filled form data
    private WebApp webAppInstance; // user's WebApp instance
    private Account account; // user's associated account


    // CONSTRUCTORS --
    public User(String email, String username, String password, String linkedInLink) {
        webAppInstance = new WebApp();
        formData = email + " " + username + " " + password + " " + linkedInLink;
    } 


    // ACCESSORS --- 
    public Account getAccount() { return this.account; }

    // MUTATORS --- 
    public void setAccount(Account account) { this.account = account; }


    // METHODS --- *** NOTE: ACTION denotes user action ***

    // fillForm(): void - ACTION calls WebApp method respective of user filled form
    public void fillForm(String action) {
        if (action.equals("signup")) { // user is signing up
            webAppInstance.setUserInfo(formData, this);
        }
        else if (action.equals("login")) { // user is logging in
            webAppInstance.userLogin(formData, this);
        }
    }
    
    // swipe(direction): void - ACTION renders new user to screen
    // FLOW: User.swipe() --> WebApp.GetUserInfo() --> Server.matchUsers()  
    public void swipe(String direction) {
        if (direction.equals("r")) { // right swipe
            webAppInstance.getUserInfo(this.account, "r");
        }
        else { // left swipe
            webAppInstance.getUserInfo(this.account, "l");
        }   
    }

    // deleteUser(): void - ACTION completely deletes account from webapp, database, and cronserver leaderboard
    public void deleteUser() {
        this.account = webAppInstance.deleteUser(account);
    }

    // updateUser(): void - ACTION edit user info from db & cron server leaderboards
    public void updateUser(String formData) {
        Account updatedAccount = webAppInstance.updateUser(formData, this.account);
        
        if (updatedAccount != null) {
            this.account = updatedAccount; 
        }
    }

}
