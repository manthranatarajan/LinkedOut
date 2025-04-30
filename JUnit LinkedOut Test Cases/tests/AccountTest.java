package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

public class AccountTest {

    private Account validAccount;
    private Account invalidAccount;

    @Before
    public void setup() {
        // create a valid account
        validAccount = new Account("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", true);

        // create an invalid account
        invalidAccount = new Account("", false);
    }

    @Test
    // test account creation successfully
    public void accountCreation_Pass() {
        assertTrue("account should be successfully created.", validAccount.getSuccess());
        assertEquals("email should match.", "johndoe@email.com", validAccount.getEmail());
        assertEquals("username should match.", "johndoe12", validAccount.getUsername());
        assertEquals("password should match.", "johnsPassword123!!!", validAccount.getPassword());
        assertEquals("linkedin link should match.", "https://linkedin.com/johndoe", validAccount.getLinkedInLink());
    }

    @Test
    // test account creation fails (intentional fail)
    public void accountCreation_Fail() {
        assertTrue("this test should fail because the account is invalid.", invalidAccount.getSuccess());
    }

    @Test
    // test adding a matched user successfully
    public void addMatchedUser_Pass() {
        Account matchedAccount = new Account("janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe", true);
        validAccount.addMatchedUser(matchedAccount);

        assertEquals("matched users list should contain one account.", 1, validAccount.getMatchedUsers().size());
        assertEquals("matched account email should match.", "janedoe@email.com", validAccount.getMatchedUsers().get(0).getEmail());
    }

    @Test
    // test adding a matched user fails (intentional fail)
    public void addMatchedUser_Fail() {
        Account matchedAccount = new Account("", false);
        validAccount.addMatchedUser(matchedAccount);

        // intentionally fail by asserting the matched user list is empty
        assertEquals("this test should fail because a matched user was added.", 0, validAccount.getMatchedUsers().size());
    }

    @Test
    // test incrementing connections made successfully
    public void incrementConnectionsMade_Pass() {
        validAccount.incConnectionsMade();
        validAccount.incConnectionsMade();

        assertEquals("connections made should be incremented to 2.", 2, validAccount.getConnectionsMade());
    }

    @Test
    // test incrementing connections made fails (intentional fail)
    public void incrementConnectionsMade_Fail() {
        validAccount.incConnectionsMade();

        // intentionally fail by asserting the connections made is 0
        assertEquals("this test should fail because connections made is not 0.", 0, validAccount.getConnectionsMade());
    }

    @Test
    // test adding a badge successfully
    public void addBadge_Pass() {
        Badge badge = new Badge("Top Connector", "https://example.com/badge.png");
        validAccount.addBadge(badge);

        assertEquals("badges list should contain one badge.", 1, validAccount.getBadges().size());
        assertEquals("badge name should match.", "Top Connector", validAccount.getBadges().get(0).getBadgeName());
    }

    @Test
    // test adding a badge fails (intentional fail)
    public void addBadge_Fail() {
        Badge badge = new Badge("Top Connector", "https://example.com/badge.png");
        validAccount.addBadge(badge);

        // intentionally fail by asserting the badges list is empty
        assertEquals("this test should fail because a badge was added.", 0, validAccount.getBadges().size());
    }

    @Test
    // test account toString method
    public void toString_Pass() {
        String expected = "{User: johndoe12, Email: johndoe@email.com}";
        String actual = validAccount.toString();

        assertEquals("toString output should match.", expected, actual);
    }

    @Test
    // test account toString method fails (intentional fail)
    public void toString_Fail() {
        String expected = "{User: invalid, Email: invalid@email.com}";
        String actual = validAccount.toString();

        // intentionally fail by asserting an incorrect toString output
        assertEquals("this test should fail because the toString output is incorrect.", expected, actual);
    }
}