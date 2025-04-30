package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

public class ServerTest {

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
    // test authorizing user info successfully
    public void authorizeUserInfo_Pass() {
        String formData = "janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe";
        Account account = Server.authorizeUserInfo(formData);

        assertTrue("account should be successfully created.", account.getSuccess());
        assertEquals("email should match.", "janedoe@email.com", account.getEmail());
    }

    @Test
    // test authorizing user info fails (intentional fail)
    public void authorizeUserInfo_Fail() {
        String formData = "invalidemail invalid invalid invalid";
        Account account = Server.authorizeUserInfo(formData);

        // intentionally fail by asserting the account was created successfully
        assertTrue("this test should fail because the account is invalid.", account.getSuccess());
    }

    @Test
    // test verifying user credentials successfully
    public void verifyUser_Pass() {
        String formData = "johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe";
        Account account = Server.verifyUser(formData);

        assertTrue("account should be successfully verified.", account.getSuccess());
        assertEquals("email should match.", validAccount.getEmail(), account.getEmail());
    }

    @Test
    // test verifying user credentials fails (intentional fail)
    public void verifyUser_Fail() {
        String formData = "invalid@email.com invalid invalid invalid";
        Account account = Server.verifyUser(formData);

        // intentionally fail by asserting the account was verified successfully
        assertTrue("this test should fail because the account does not exist.", account.getSuccess());
    }

    @Test
    // test deleting user successfully
    public void deleteUser_Pass() {
        Account deletedAccount = Server.deleteUser(validAccount);

        assertNull("account should be deleted successfully.", Database.findUserRow(validAccount).getEmail());
    }

    @Test
    // test deleting user fails (intentional fail)
    public void deleteUser_Fail() {
        Account deletedAccount = Server.deleteUser(invalidAccount);

        // intentionally fail by asserting the account was deleted
        assertNull("this test should fail because the account does not exist.", deletedAccount);
    }

    @Test
    // test generating token successfully
    public void generateToken_Pass() {
        String token = Server.generateToken();

        assertNotNull("token should be generated successfully.", token);
        assertTrue("token should contain a colon.", token.contains(":"));
    }
}