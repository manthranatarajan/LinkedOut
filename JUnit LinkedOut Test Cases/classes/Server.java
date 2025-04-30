package classes;

public class Server {
    public static String secretKey = "This is an unencrypted and unsecure secret key for LinkedOut.";   
    public static MatchingAlgo matchingAlgo = new MatchingAlgo(); 
    // METHODS

    // for validating user input on account creation
    public static Account authorizeUserInfo(String formData) {  // unprotected

        if (validateAccountInput(formData)) {
            Account account = new Account(formData, true);
            Database.createUserRow(account);
            return account;
        }
        else {
            return new Account(formData, false);
        }
    }

    // for validating user credentials for existing account
    public static Account verifyUser(String formData) { // unprotected

        // interact with Database.java & determine if password is correct for user's email
        Account possibleAccount = new Account(formData);
        Account actualAccount = Database.findUserRow(possibleAccount);
        
        // return account w/ success or fail if credentials are correct or incorrect
        if (actualAccount.getSuccess()) { // return account 
            return actualAccount;
        }
        else { // return failure account obj
            return new Account("", false);
        }
    }

    // get account from db using db's findrow 
    public static Account getUserInfo(Account account) {
        // retrieve the account from the database
        Account retrievedAccount = Database.findUserRow(account);
    
        // check if the account was found
        if (retrievedAccount.getSuccess()) {
            return retrievedAccount; // return the retrieved account
        } else {
            return new Account("", false); // return a failure account if not found
        }
    }

    // updates account info within DB
    public static Account updateUser(Account account, String formData, String token) { // protected
        if (validateToken(token) && validateAccountInput(formData)) { 
            // update account info within DB
            return Database.updateUserRow(account, formData);
        }
        else { // failed to update user
            return new Account("", false);
        }
    }

    // matching two users utilizing matching algorithm
    public static Account matchUsers(Account accountToMatch) {
        Account matchedAccount = matchingAlgo.match(accountToMatch);

        return matchedAccount;
    }

    // for deleting user from db
    public static Account deleteUser(Account accountToDelete) {
        return Database.deleteUserRow(accountToDelete);
    }

    // generate auth token
    public static String generateToken() { // unprotected
        // generate token and sign with secretKey instance var
        String token = java.util.UUID.randomUUID().toString() + ":" + secretKey.hashCode();

        // return token
        return token;
    }

    // helper method for input validation
    private static boolean validateAccountInput(String formData) {

        String input[] = formData.split(" ");
        String email = input[0], username = input[1], password = input[2], linkedinLink = input[3];

        boolean validEmail = false, validUsername = false, validPassword = false, validLinkedinLink = false; // flags
        boolean validAccountInput = false;

        // validate email: must not be null and must match a standard email format
        if (email != null && email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            validEmail = true;
        }

        // validate username: must not be null, must be between 6 and 24 characters, and must contain at least one digit
        if (username != null && username.length() >= 6 && username.length() <= 24 && username.matches(".*\\d.*")) {
            validUsername = true;
        }

        // validate password: must not be null, must be at least 6 characters long, 
        // must contain at least one uppercase letter, one lowercase letter, one digit, and one special symbol
        if (password != null && password.length() >= 6 &&
            password.matches(".*[A-Z].*") && // contains at least one uppercase letter
            password.matches(".*[a-z].*") && // contains at least one lowercase letter
            password.matches(".*\\d.*") &&  // contains at least one digit
            password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) { // contains at least one special symbol
            validPassword = true;
        }

        // validate LinkedIn link: must not be null and must match a standard LinkedIn URL format
        if (linkedinLink != null && linkedinLink.matches("^(https?://)?(www\\.)?linkedin\\.com/.*$")) {
            validLinkedinLink = true;
        }

        // all fields must be valid
        validAccountInput = validEmail && validUsername && validPassword && validLinkedinLink;

        return validAccountInput;
    }
    
    // helper to validate token
    private static boolean validateToken(String token) {
        if (token == null || !token.contains(":")) {
            return false;
        }

        String[] parts = token.split(":");
        if (parts.length != 2) {
            return false;
        }

        String tokenHash = parts[1];
        String expectedHash = String.valueOf(secretKey.hashCode());

        return tokenHash.equals(expectedHash);
    }
}
