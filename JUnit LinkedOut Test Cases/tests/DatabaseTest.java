package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

import java.util.ArrayList;

public class DatabaseTest {

    private Account validAccount;
    private Account invalidAccount;

    @Before
    public void setup() {
        // create a valid account
        validAccount = new Account("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", true);

        // create an invalid account
        invalidAccount = new Account("invalid@email.com invalid invalid invalid", false);

        // clear the database before each test
        Database.deleteAllUserRows();
        Database.createUserRow(validAccount); // add a valid account to the database
    }

    @Test
    // test creating a user row successfully
    public void createUserRow_Pass() {
        Account newAccount = new Account("janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe", true);
        Database.createUserRow(newAccount);

        Account retrievedAccount = Database.findUserRow(newAccount);
        assertEquals("account should be successfully added to the database.", newAccount.getEmail(), retrievedAccount.getEmail());
    }

    @Test
    // test creating a user row fails (intentional fail)
    public void createUserRow_Fail() {
        Account duplicateAccount = new Account("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", true);
        Database.createUserRow(duplicateAccount);

        // intentionally fail by asserting the database contains no accounts
        assertEquals("this test should fail because the account exists.", 0, Database.findAllUserRows().size());
    }

    @Test
    // test finding a user row successfully
    public void findUserRow_Pass() {
        Account retrievedAccount = Database.findUserRow(validAccount);
        assertNotNull("account should be found in the database.", retrievedAccount);
        assertEquals("retrieved account should match the valid account.", validAccount.getEmail(), retrievedAccount.getEmail());
    }

    @Test
    // test finding a user row fails (intentional fail)
    public void findUserRow_Fail() {
        Account retrievedAccount = Database.findUserRow(invalidAccount);

        // intentionally fail by asserting the account is found
        assertNotNull("this test should fail because the account does not exist.", retrievedAccount.getEmail());
    }

    @Test
    // test deleting a user row successfully
    public void deleteUserRow_Pass() {
        Account deletedAccount = Database.deleteUserRow(validAccount);

        assertNull("deleted account should be null.", Database.findUserRow(validAccount).getEmail());
    }

    @Test
    // test deleting a user row fails (intentional fail)
    public void deleteUserRow_Fail() {
        Account deletedAccount = Database.deleteUserRow(invalidAccount);

        // intentionally fail by asserting the account was deleted
        assertNull("this test should fail because the account does not exist.", deletedAccount);
    }

    @Test
    // test finding all user rows successfully
    public void findAllUserRows_Pass() {
        Account newAccount = new Account("janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe", true);
        Database.createUserRow(newAccount);

        assertEquals("database should contain two accounts.", 2, Database.findAllUserRows().size());
    }

    @Test
    // test finding all user rows fails (intentional fail)
    public void findAllUserRows_Fail() {
        Database.deleteAllUserRows();

        // intentionally fail by asserting the database is not empty
        assertNotEquals("this test should fail because the database is empty.", 0, Database.findAllUserRows().size());
    }
}