package classes;

import java.util.ArrayList;

public class WebApp {
    private String token;
    private Account account;

    // CONSTRUCTOR ---
    public WebApp() {
        // get token from server
        token = Server.generateToken();
    }

    // METHODS --- 

    // userLogin(): void - accepts form from user for login 
    // FLOW: User.fillForm("login") --> WebApp.userLogin() --> Server.verifyUser() --> confirms credentials --> login success or fail
    public void userLogin(String formData, User user) {
        Account accountIfValidCredentials = Server.verifyUser(formData);

        if (accountIfValidCredentials.getSuccess()) { // valid credentials
            user.setAccount(accountIfValidCredentials); // set user obj's account

            // notify user of success
            Notification notif = new Notification("Login Successful!");
            notifyUser(notif);
        }
        else { // login failed

            // notify user of failure
            Notification notif = new Notification("Login failed. Please enter valid credentials and try again.");
            notifyUser(notif);
        }
    }

    // setUserInfo(): void - sets account info after user creates account
    // FLOW: User.fillForm("signup") --> WebApp.setUserInfo() --> Server.authorizeUserInfo() --> account created or failed to create
    public void setUserInfo(String formData, User user) {

        // post data to server for input validation & process result
        Account account = Server.authorizeUserInfo(formData);

        // associate account w/ user (for ease of access w/ testing)
        if (account.getSuccess()) {
            user.setAccount(account);
            Notification accountSuccessMsg = new Notification("Account " + account + " created successfully!");
            notifyUser(accountSuccessMsg);
        }
        else { // notify user account failed to create
            Notification accountFailMsg = new Notification("Failed to create account. Please enter valid input and try again.");
            notifyUser(accountFailMsg);
        }
    }

    // getUserInfo(): void - gets user info using Server.findUserRow(Account);
    // FLOW: User.swipe() --> WebApp.getUserInfo() --> Server.matchUsers() --> db.findUserRow()
    public void getUserInfo(Account accountToMatch, String direction) {
        Account matchedAccount = Server.matchUsers(accountToMatch);

        if (direction.equals("r") && matchedAccount.getSuccess()) { // matched account?
            accountToMatch.incConnectionsMade(); 
            accountToMatch.addMatchedUser(matchedAccount);
            Notification notif = new Notification("Match with user " + matchedAccount.getUsername() + "@" + matchedAccount.getLinkedInLink());
            notifyUser(notif);
            
        }
        else { // swiped left, find new match
            Notification notif = new Notification("Finding new match...Hang tight! (**ENDS HERE**)");
            notifyUser(notif);
        }
    }

    // updateUser
    // FLOW: User.updateUser() -> WebApp.updateUser() -> Server.UpdateUser() -> update user in DB
    public Account updateUser(String formData, Account accountToUpdate) {
        Account accountIfUpdated = Server.updateUser(accountToUpdate, formData, token);

        if (accountIfUpdated.getSuccess()) { // updated successfully
            accountToUpdate = accountIfUpdated;
            Notification notif = new Notification("Account Updated Successfully!");
            notifyUser(notif);

            return accountToUpdate;
        }
        else { // failed to update
            Notification notif = new Notification("Account failed to update. Please enter valid form information.");
            notifyUser(notif);

            return null;
        }
    }

    // attempts to deletes user's account in DB
    // FLOW: user.deleteUser() --> WebApp.deleteUser() --> Server.deleteUser() --> DB.deleteUserRow --> report success or fail
    public Account deleteUser(Account accountToDelete) {
        if (Server.deleteUser(accountToDelete) == null) { // if account deleted successfully

            // notify user
            Notification notif = new Notification("Account deleted successfully!");
            notifyUser(notif);
            return null;
        }
        else { // account failed to delete 
            
            // notify user
            Notification notif = new Notification("FATAL; Account failed to delete."); 
            notifyUser(notif);
            return accountToDelete;
        }
    }

    // notifyUser(Notification): void - displays notificaiton to user
    // FLOW: *user ACTION with visible result occurs* --> WebApp.notifyUser() --> notification rendered to screen for user 
    public void notifyUser(Notification notif) {
        // as there is no screen to render to, I am simply outputting messages that would be rendered on the webapp to the console
        System.out.println(notif.getMessage());
    }

}
