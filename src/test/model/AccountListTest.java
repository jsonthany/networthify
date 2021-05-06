package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.JsonReader;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountListTest {

    private AccountList newListOfAccounts;

    @BeforeEach
    public void setup() {
        newListOfAccounts = new AccountList();
    }

    @Test
    public void emptyAccountList() {

        // checks for empty list
        assertTrue(newListOfAccounts.isListEmpty());
        assertEquals(0, newListOfAccounts.length());
        assertFalse(newListOfAccounts.isUserNameUsed("jthany"));

    }

    @Test
    void checkExceptions() {
        try {
            newListOfAccounts.getAccountName(1);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        try {
            newListOfAccounts.getAccountName(-1);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        Account account1 = new Account("jthany", "password");
        assertTrue(newListOfAccounts.addNewAccount(account1));

        try {
            assertEquals("jthany", newListOfAccounts.getAccountName(0));
        } catch (IndexOutOfBoundsException e) {
            fail();
        }

    }

    @Test
    public void addOneAccount() {

        // add a new account
        Account account1 = new Account("jthany", "password");
        assertTrue(newListOfAccounts.addNewAccount(account1));

        assertEquals(1, newListOfAccounts.length());

        assertTrue(newListOfAccounts.isUserNameUsed("jthany"));
        assertFalse(newListOfAccounts.isUserNameUsed("jsonthany"));
        assertEquals("jthany", newListOfAccounts.getAccountName(0));

        assertTrue(newListOfAccounts.isPasswordMatching("jthany","password"));
        assertFalse(newListOfAccounts.isPasswordMatching("jthany","wrongpass"));

        assertEquals(0, newListOfAccounts.getPosition("jthany", "password"));
        assertEquals(account1, newListOfAccounts.getAccount("jthany", "password"));

        // remove a account
        newListOfAccounts.removeAccount("jthany", "password");
        assertTrue(newListOfAccounts.isListEmpty());
        assertFalse(newListOfAccounts.isUserNameUsed("jthany"));

    }

    @Test
    public void addTwoAccounts() {

        // add two new accounts
        Account account1 = new Account("jthany", "password1");
        assertTrue(newListOfAccounts.addNewAccount(account1));

        assertFalse(newListOfAccounts.addNewAccount(account1));

        Account account2 = new Account("jsonthany", "password2");
        assertTrue(newListOfAccounts.addNewAccount(account2));

        assertFalse(newListOfAccounts.addNewAccount(account2));

        // check existence of the two accounts
        assertEquals(2, newListOfAccounts.length());

        assertTrue(newListOfAccounts.isUserNameUsed("jthany"));
        assertTrue(newListOfAccounts.isUserNameUsed("jsonthany"));

        assertTrue(newListOfAccounts.isPasswordMatching("jthany","password1"));
        assertTrue(newListOfAccounts.isPasswordMatching("jsonthany","password2"));
        assertFalse(newListOfAccounts.isPasswordMatching("jthany","password"));
        assertFalse(newListOfAccounts.isPasswordMatching("jakethany","password2"));

        assertEquals(0, newListOfAccounts.getPosition("jthany", "password1"));
        assertEquals(1, newListOfAccounts.getPosition("jsonthany", "password2"));
        assertEquals(-1, newListOfAccounts.getPosition("jsonthany", "password"));
        assertEquals(-1, newListOfAccounts.getPosition("jakethany", "password2"));

        assertEquals(-1, newListOfAccounts.getPosition("jeremie", "password3"));

        assertEquals(account1, newListOfAccounts.getAccount("jthany", "password1"));
        assertEquals(account2, newListOfAccounts.getAccount("jsonthany", "password2"));

        // remove a account
        assertFalse(newListOfAccounts.removeAccount("jakethany", "password1"));
        assertTrue(newListOfAccounts.removeAccount("jthany", "password1"));
        assertEquals(1, newListOfAccounts.length());
        assertFalse(newListOfAccounts.isListEmpty());
        assertFalse(newListOfAccounts.isUserNameUsed("jthany"));
        assertTrue(newListOfAccounts.isUserNameUsed("jsonthany"));

        // remove the other account
        assertFalse(newListOfAccounts.removeAccount("jsonthany", "password"));
        assertTrue(newListOfAccounts.removeAccount("jsonthany", "password2"));
        assertEquals(0, newListOfAccounts.length());
        assertTrue(newListOfAccounts.isListEmpty());
        assertFalse(newListOfAccounts.isUserNameUsed("jsonthany"));

        // remove non-existent account
        assertFalse(newListOfAccounts.removeAccount("jsonthany", "password2"));

    }

}
