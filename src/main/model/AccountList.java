package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a list of accounts
public class AccountList implements Writable {

    // field for list of accounts
    private List<Account> accountList;

    // EFFECTS : constructs an empty list of accounts
    public AccountList() {
        this.accountList = new ArrayList<>();
    }

    // REQUIRES: username has to be unique
    // MODIFIES: this
    // EFFECTS : adds a new account to account list
    public boolean addNewAccount(Account account) {

        for (Account acc : accountList) {
            if (acc.getUser().equals(account.getUser())) {
                return false;
            }
        }

        accountList.add(account);
        return true;

    }

    // REQUIRES: username and password are already within available in the system
    // EFFECTS : retrieves account position given username and password
    public int getPosition(String username, String password) {
        for (int i = 0; i < accountList.size(); i++) {
            if ((accountList.get(i).getUser().equals(username)) && (accountList.get(i).getPass().equals(password))) {
                return i;
            }
        }

        return -1;

    }

    // REQUIRES: assumes that username and password are within in the system and valid
    // EFFECTS : retrieves account given username and password
    public Account getAccount(String username, String password) {

        return accountList.get(this.getPosition(username, password));

    }

    // EFFECTS : returns true if username is already used; false otherwise
    public boolean isUserNameUsed(String username) {

        for (Account acc : accountList) {
            if (acc.getUser().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public String getAccountName(int pos) {
        try {
            return accountList.get(pos).getUser();
        } catch (IndexOutOfBoundsException e) {
            return "exception thrown; index is out of bounds!";
        }
    }

    // EFFECTS : returns true if the password matches the given username
    public boolean isPasswordMatching(String username, String password) {

        for (Account acc : accountList) {
            if (acc.getUser().equals(username)) {
                return (acc.getPass().equals(password));
            }
        }

        return false;

    }

    // EFFECTS : returns true if account list is empty; false otherwise
    public boolean isListEmpty() {
        return (accountList.size() == 0);
    }

    // EFFECTS : returns integer length of the list
    public int length() {
        return accountList.size();
    }

    // REQUIRES: username and password are already existing in the account list
    // MODIFIES: this
    // EFFECTS : returns true and removes account from account list if and username password matches; false otherwise
    public boolean removeAccount(String username, String password) {

        // if username and passcode valid, remove account
        for (int i = 0; i < accountList.size(); i++) {
            if ((accountList.get(i).getUser().equals(username)) && (accountList.get(i).getPass().equals(password))) {
                accountList.remove(i);
                return true;
            }
        }

        // else return false
        return false;

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", this.accountList);
        return json;

    }

}