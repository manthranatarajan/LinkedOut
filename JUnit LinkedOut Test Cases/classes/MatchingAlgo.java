package classes;

import java.util.ArrayList;

public class MatchingAlgo {
    public Account match(Account accountToMatch) {
        // matching algo TBA...
        // for now just getting random non-self account 

        // get all users from the database
        ArrayList<Account> allUsers = Database.findAllUserRows();

        // filter out the accountToMatch from the list
        allUsers.removeIf(account -> account.getEmail().equals(accountToMatch.getEmail()));

        // if no other accounts are available, return null
        if (allUsers.isEmpty()) {
            return null;
        }

        // get a random account from the remaining list
        int randomIndex = (int) (Math.random() * allUsers.size());
        return allUsers.get(randomIndex);
    }
}
