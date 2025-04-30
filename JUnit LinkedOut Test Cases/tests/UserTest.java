package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.*;

public class UserTest {
    User user1, user2;
    User invalidUser;

    @Before
    public void setup() {
        String email = "johndoe@email.com",
               username = "johndoe12", 
               password = "johnsPassword123!!!", 
               linkedInLink = "https://linkedin.com/johnspage";
        String email2 = "janedoe@email.com",
               username2 = "janedoe12", 
               password2 = "janesPassword123!!!", 
               linkedInLink2 = "https://linkedin.com/janespage";

        user1 = new User(email, username, password, linkedInLink);
        user2 = new User(email2, username2, password2, linkedInLink2);
        invalidUser = new User("asd", "asd", "asd", "asd");
    }


    @Test
    // user signs up successfully
    public void fillFormTest_Signup_Pass() {
        user1.fillForm("signup"); // user signs up

        String expected = "{User: johndoe12, Email: johndoe@email.com}";
        String actual = user1.getAccount().toString();

        assertEquals("Expected and actual toString() do not match.", expected, actual);
    }
    @Test
    // user attempts sign up with invalid form
    public void fillFormTest_Signup_Fail() {
        invalidUser.fillForm("signup");

        Account actual = invalidUser.getAccount();

        assertNotNull("User Account should NOT be null after signup.", actual);
    }

    @Test
    // user logs in successfully
    public void fillFormTest_Login_Pass() {
        user1.fillForm("signup"); // user creates account
        user1.fillForm("login"); // user logs in

        String expected = "{User: johndoe12, Email: johndoe@email.com}";
        String actual = user1.getAccount().toString();

        assertEquals("User should have account reference upon login.", expected, actual);
    }
    @Test
    // user fails to login
    public void fillFormTest_Login_Fail() {
        user1.fillForm("login"); // user attempts to login without an existing account

        Account actual = user1.getAccount();

        assertNotNull("User account must NOT be null upon login.", actual);
    }

    @Test
    // user deletes account successfully
    public void deleteUser_Pass() {
        user1.fillForm("signup"); // user signs up
        user1.fillForm("login"); // user logs in
        user1.deleteUser();
        
        Account actual = user1.getAccount();

        assertNull("Account should no longer exist (null).", actual);
    }
    
    @Test
    // user updates login successfully
    public void updateUser_Pass() {
        user1.fillForm("signup"); // user signs up
        user1.fillForm("login"); // user logs in
        user1.updateUser("janedoe@email.com janeDoe12 janeDoesPassword123!!! https://linkedin.com/janedoespage");

        String expected = "{User: janeDoe12, Email: janedoe@email.com}";
        String actual = user1.getAccount().toString();

        assertEquals("Account details should have changed.", expected, actual);
    }
    @Test
    // user fails to update login
    public void updateUser_Pass2() {
        user1.fillForm("signup"); // user signs up
        user1.fillForm("login"); // user logs in
    
        // attempt to update with invalid data
        user1.updateUser("invalidemail invalidusername invalidpassword invalidlink");
    
        // the account should remain unchanged
        String expected = "{User: johndoe12, Email: johndoe@email.com}";
        String actual = user1.getAccount().toString();
    
        assertEquals("Account details should remain unchanged after a failed update.", expected, actual);
    }

    @Test
    // user swipes right successfully and matches with another user
    public void swipeTest_Right_Pass() {
        user1.fillForm("signup"); // user signs up
        user1.fillForm("login"); // user logs in
        user2.fillForm("signup");
        user2.fillForm("login");

        // simulate a right swipe
        user1.swipe("r");

        // check if a match was made
        Account matchedAccount = user1.getAccount().getMatchedUsers().get(0); // get the first matched user
        assertNotNull("User should have matched with another account.", matchedAccount);
    }
    @Test
    // user swipes with an invalid direction (intentional fail)
    public void swipeTest_InvalidDirection_Fail() {
        user1.fillForm("signup"); // user signs up
        user1.fillForm("login"); // user logs in

        // simulate an invalid swipe direction
        user1.swipe("invalid");

        // intentionally fail by asserting that a match was made
        assertNotNull("This test should fail because the swipe direction is invalid.", user1.getAccount().getMatchedUsers().get(0));
    }

}
