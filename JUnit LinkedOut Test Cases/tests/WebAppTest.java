package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

public class WebAppTest {

    private WebApp webApp;
    private User user;
    private Account validAccount;
    private Account invalidAccount;

    @Before
    public void setup() {
        // create a valid account
        validAccount = new Account("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", true);

        // create an invalid account
        invalidAccount = new Account("invalid@email.com invalid invalid invalid", false);

        // initialize webapp and user
        webApp = new WebApp();
        user = new User("johndoe@email.com", "johndoe12", "johnsPassword123!!!", "https://linkedin.com/johndoe");

        // clear the database before each test
        Database.deleteAllUserRows();
        Database.createUserRow(validAccount); // add a valid account to the database
    }

    @Test
    // test user login successfully
    public void userLogin_Pass() {
        webApp.userLogin("johndoe@email.com johndoe12 johnsPassword123!!! https://linkedin.com/johndoe", user);

        assertNotNull("user should have a valid account after login.", user.getAccount());
        assertEquals("email should match after login.", validAccount.getEmail(), user.getAccount().getEmail());
    }

    @Test
    // test user login fails (intentional fail)
    public void userLogin_Fail() {
        webApp.userLogin("invalid@email.com invalid invalid invalid", user);

        // intentionally fail by asserting the user has a valid account
        assertNotNull("this test should fail because the login credentials are invalid.", user.getAccount());
    }

    @Test
    // test setting user info successfully
    public void setUserInfo_Pass() {
        webApp.setUserInfo("janedoe@email.com janedoe12 janesPassword123!!! https://linkedin.com/janedoe", user);

        assertNotNull("user should have a valid account after signup.", user.getAccount());
        assertEquals("email should match after signup.", "janedoe@email.com", user.getAccount().getEmail());
    }

    @Test
    // test setting user info fails (intentional fail)
    public void setUserInfo_Fail() {
        webApp.setUserInfo("invalidemail invalid invalid invalid", user);

        // intentionally fail by asserting the user has a valid account
        assertNotNull("this test should fail because the signup data is invalid.", user.getAccount());
    }


    @Test
    // test deleting user successfully
    public void deleteUser_Pass() {
        Account deletedAccount = webApp.deleteUser(validAccount);

        assertNull("account should be deleted successfully.", Database.findUserRow(validAccount).getEmail());
    }

    @Test
    // test deleting user fails (intentional fail)
    public void deleteUser_Fail() {
        Account deletedAccount = webApp.deleteUser(invalidAccount);

        // intentionally fail by asserting the account was deleted
        assertNull("this test should fail because the account does not exist.", deletedAccount);
    }
}