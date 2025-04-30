package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

import java.util.ArrayList;

public class MatchingAlgoTest {

    private MatchingAlgo matchingAlgo;
    private Account accountToMatch;
    private Account matchedAccount1;
    private Account matchedAccount2;

    @Before
    public void setup() {
        // initialize the matching algorithm
        matchingAlgo = new MatchingAlgo();

        // create accounts
        accountToMatch = new Account("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", true);
        matchedAccount1 = new Account("janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe", true);
        matchedAccount2 = new Account("bobsmith@email.com bobsmith12 bobsPassword123!!! https://linkedin.com/bobsmith", true);

        // clear the database and add accounts
        Database.deleteAllUserRows();
        Database.createUserRow(accountToMatch);
        Database.createUserRow(matchedAccount1);
        Database.createUserRow(matchedAccount2);
    }

    @Test
    // test matching an account successfully
    public void matchAccount_Pass() {
        Account matchedAccount = matchingAlgo.match(accountToMatch);

        assertNotNull("a match should be found.", matchedAccount);
        assertNotEquals("matched account should not be the same as the account to match.", accountToMatch.getEmail(), matchedAccount.getEmail());
    }

    @Test
    // test matching an account fails (intentional fail)
    public void matchAccount_Fail() {
        // clear the database to ensure no matches exist
        Database.deleteAllUserRows();

        Account matchedAccount = matchingAlgo.match(accountToMatch);

        // intentionally fail by asserting a match was found
        assertNotNull("this test should fail because no matches exist.", matchedAccount);
    }

    @Test
    // test matching when only one account exists
    public void matchSingleAccount_Fail() {
        // clear the database and add only the account to match
        Database.deleteAllUserRows();
        Database.createUserRow(accountToMatch);

        Account matchedAccount = matchingAlgo.match(accountToMatch);

        // intentionally fail by asserting a match was found
        assertNotNull("this test should fail because no other accounts exist.", matchedAccount);
    }

    @Test
    // test matching when multiple accounts exist
    public void matchMultipleAccounts_Pass() {
        Account matchedAccount = matchingAlgo.match(accountToMatch);

        assertNotNull("a match should be found.", matchedAccount);
        assertTrue("matched account should be one of the other accounts.", 
            matchedAccount.getEmail().equals(matchedAccount1.getEmail()) || 
            matchedAccount.getEmail().equals(matchedAccount2.getEmail()));
    }
}