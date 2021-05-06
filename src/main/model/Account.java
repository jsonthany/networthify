package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an account with a id, username, password, income statement list, and balance sheet
public class Account implements Writable {

    // individual security details
    private int nextAccountId = 1;
    private int id;
    private String username;
    private String password;

    private List<IncomeStatement> incomeStatementList;
    private BalanceSheet balanceSheet;

    // REQUIRES: username needs to be unique, password length between 5-12 characters, and password 6 digits
    // MODIFIES: this
    // EFFECTS : initializes an account with a username and password
    public Account(String username, String password) {

        id = nextAccountId++;
        this.username = username;
        this.password = password;

        incomeStatementList = new ArrayList<>();
        balanceSheet = new BalanceSheet();
    }

    // REQUIRES: username needs to be unique, password length between 5-12 characters, and password 6 digits
    // MODIFIES: this
    // EFFECTS: constructs an account with id, username, password, income statement list, and balance sheet
    public Account(int id, String username, String password, List<IncomeStatement> incomeStatementList,
                   BalanceSheet balanceSheet) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.balanceSheet = balanceSheet;
        this.incomeStatementList = incomeStatementList;

    }

    // MODIFIES: this
    // EFFECTS : returns true and adds new income statement if monthly statement not yet in list; false otherwise
    public void addNewIncomeStatement(IncomeStatement incomeStatement) {

        incomeStatementList.add(incomeStatement);
        balanceSheet.addCash(incomeStatement.getNettEarnings());

    }

    // EFFECTS : returns true if there is already an entry in for a given month and year; false otherwise
    public boolean isDateInUse(int month, int year) {
        for (IncomeStatement incomeStatement : incomeStatementList) {
            if ((incomeStatement.getMonth() == month) && (incomeStatement.getYear() == year)) {
                return true;
            }
        }

        return false;

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly recurring income
    public double avgMonthlyRecurring() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getRecurringEarnings();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly non-recurring income
    public double avgMonthlyNonRecurring() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getOneOffEarnings();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly grocery expenses
    public double avgMonthlyGroceries() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getGroceries();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly rental expenses
    public double avgMonthlyRent() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getRent();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly subscription expenses
    public double avgMonthlySubs() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getSubscriptionExp();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly food expenses
    public double avgMonthlyFood() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getFood();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly leisure expenses
    public double avgMonthlyLeisure() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getLeisure();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly other expenses
    public double avgMonthlyOtherExp() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getOtherExp();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly total earnings
    public double avgMonthlyTotalEarnings() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getTotalEarnings();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly total expenses
    public double avgMonthlyTotalExpenses() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getTotalExpenses();
            count++;
        }

        return (balance / count);

    }

    // REQUIRES: list of income statements must not be empty
    // EFFECTS : calculates average monthly nett income
    public double avgMonthlyNettIncome() {

        double balance = 0;
        int count = 0;

        for (IncomeStatement monthly : incomeStatementList) {
            balance += monthly.getNettEarnings();
            count++;
        }

        return (balance / count);

    }

    // EFFECTS : retrieves the account's id
    public int getId() {
        return this.id;
    }

    // EFFECTS : retrieves the account's username
    public String getUser() {
        return this.username;
    }

    // EFFECTS : retrieves the account's password
    public String getPass() {
        return this.password;
    }

    // EFFECTS : retrieves the account's balance sheet
    public BalanceSheet getBS() {
        return balanceSheet;
    }

    // EFFECTS : retrieves the account's list of income statements
    public List<IncomeStatement> getISList() {
        return incomeStatementList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("username", this.username);
        json.put("password", this.password);
        json.put("incomeStatementList", incomeStatementsToJson());
        json.put("balanceSheet", this.balanceSheet);
        return json;
    }

    // EFFECTS: returns income statements list in this account as a JSON array
    private JSONArray incomeStatementsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (IncomeStatement i : incomeStatementList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }

}
