package classes;

import java.util.ArrayList;

public class Database {
    private static ArrayList<Account> accountRows = new ArrayList<>();

    // METHODS

    // stores an account in DB
    public static void createUserRow(Account account) {
        accountRows.add(account);
    }

    // finds and returns a user's account
    public static Account findUserRow(Account accountToFind) {
        String emailToFind = accountToFind.getEmail();

        for (Account account : accountRows) {
            String curEmail = account.getEmail();

            if (curEmail.equals(emailToFind)) { // found account via email
                return account; // return account
            }
        }
        
        // if account doesn't exist, return account with failure
        return new Account("", false);
    }

    // updates user account info within db
    public static Account updateUserRow(Account account, String formData) {
        // find account within accountRows
        for (Account curAccount : accountRows) {
            if (curAccount.equals(account)) { // found account
                // compare formData to account attributes
                String[] form = formData.split(" ");
                String email = form[0], username = form[1], password = form[2], linkedInLink = form[3];

                // update changed fields

                if (!email.equals(curAccount.getEmail()))
                    curAccount.setEmail(email);

                if (!username.equals(curAccount.getUsername()))
                    curAccount.setUsername(username);

                if (!password.equals(curAccount.getPassword()))
                    curAccount.setPassword(password);

                if (!linkedInLink.equals(curAccount.getLinkedInLink()))
                    curAccount.setLinkedInLink(linkedInLink);
            }
        }

        // if account not found, return account with failure
        return new Account("", false);
    }

    // deletes a user account from DB
    public static Account deleteUserRow(Account account) {
        String emailToFind = account.getEmail();
        System.out.print(accountRows);
    
        for (Account curAccount : accountRows) {
            String curEmail = curAccount.getEmail();
    
            if (curEmail.equals(emailToFind)) {
                accountRows.remove(curAccount); // remove the account
                return null; // return null to indicate successful deletion
            }
        }
    
        return account; // return the original account if deletion failed
    }

    public static void deleteAllUserRows() {
        accountRows.clear();
    }

    // get all rows from DB
    public static ArrayList<Account> findAllUserRows() {
        return accountRows;
    }
}
