package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

// NetWorth application
public class NetWorthApp {

    private static final String JSON_STORE = "./data/users.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private AccountList accountList;
    private Account newAcc;
    // private Account existingAcc;
    private Account currentAcc;
    private Scanner input;

    // MODIFIES: this
    // EFFECTS: runs the NetWorth application
    public NetWorthApp() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runNetWorthApp();

    }

    // MODIFIES: this
    // EFFECTS : takes user inputs
    private void runNetWorthApp() {

        boolean isAppRunning = true;
        String command;

        init();

        loadAccountList();

        System.out.println("\nWelcome to NetWorthApp.");

        while (isAppRunning) {

            // introductory login menu
            mainPageMenu();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                isAppRunning = false;
            } else {
                processMainMenu(command);
            }

        }

        System.out.println("Thank you for using the application. Goodbye!");

    }

    // EFFECTS : displays main page menu for users
    private void mainPageMenu() {

        System.out.println("\nPlease enter one of the following to continue:");
        System.out.println("[l] login into existing account");
        System.out.println("[c] create a new account");
        System.out.println("[q] quit\n");

    }

    // MODIFIES: this
    // EFFECTS : processes user inputs in the main menu (pre-login)
    private void processMainMenu(String command) {

        if (command.equals("l")) {

            currentAcc = enterLogInDetails();

            boolean loggedIn = true;
            String entry;

            while (loggedIn) {

                mainLogInMenu();
                entry = input.next().toLowerCase();

                if (entry.equals("l")) {
                    loggedIn = false;
                } else if (entry.equals("r")) {
                    loggedIn = !(toExecuteRemoval(currentAcc));
                } else {
                    processLogInPage(entry);
                }

            }

        } else if (command.equals("c")) {
            createNewUserName();
        } else {
            System.out.println("\nYour selection is invalid.");
        }

    }

    // MODIFIES: this
    // EFFECTS : takes user inputs and returns Account based on username and password
    private Account enterLogInDetails() {

        String username = null;
        String password = null;

        boolean isUserNameValid = true;
        boolean isPasswordValid = true;

        while (isUserNameValid) {

            System.out.println("\nUsername:");
            username = input.next();

            if (accountList.isUserNameUsed(username)) {
                isUserNameValid = false;
            } else {
                System.out.println("Your username is invalid. Please enter again.");
            }

        }

        while (isPasswordValid) {

            System.out.println("\nPassword:");
            password = input.next();

            if (accountList.isPasswordMatching(username, password)) {
                isPasswordValid = false;
            } else {
                System.out.println("Your password is invalid. Please enter again.");
            }

        }

        return accountList.getAccount(username, password);

    }

    // MODIFIES: this
    // EFFECTS : takes user input and creates new username
    private void createNewUserName() {

        String username = null;
        boolean isUserNotAvailable = true;

        while (isUserNotAvailable) {

            System.out.println("\nUsername:");
            username = input.next();

            if (accountList.isUserNameUsed(username)) {
                System.out.println("Your username is no longer available or is invalid.");
            } else {
                isUserNotAvailable = false;
            }

        }

        createNewPassword(username);

    }

    // MODIFIES: this
    // EFFECTS : takes user input and creates new password
    private void createNewPassword(String username) {

        String password = null;
        boolean isPasswordNotValid = true;

        while (isPasswordNotValid) {

            System.out.println("\nPassword:");
            password = input.next();

            if (password.length() < 6) {
                System.out.println("Your password is too short. Please make it at least 6 characters long.");
            } else {
                isPasswordNotValid = false;
            }
        }

        finalizeNewAccount(username, password);

    }

    // MODIFIES: this
    // EFFECTS : takes user input and verifies input of new account information before finalizing
    private void finalizeNewAccount(String username, String password) {

        String entry;
        boolean isInputValid = true;

        System.out.println("\nPlease review the details of your new login details below:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        while (isInputValid) {

            System.out.println("\nDo you want to make any changes to your login details? [Y/N]");
            entry = input.next().toLowerCase();

            if (entry.equals("n")) {
                isInputValid = false;
            } else if (entry.equals("y")) {
                createNewUserName();
            } else {
                System.out.println("\nYour selection is invalid.");
            }

        }

        createNewAccount(username, password);

    }

    // MODIFIES: this
    // EFFECTS : creates new account and adds it to the account list
    private void createNewAccount(String username, String password) {

        newAcc = new Account(username, password);
        accountList.addNewAccount(newAcc);
        saveAccountList();

        System.out.println("\nNew account created. Returning to main page. You can now login to your new account.");

    }

    // EFFECTS : displays login page menu
    private void mainLogInMenu() {

        System.out.println("\nHi " + currentAcc.getUser() + ". Please select one of the following options:");
        System.out.println("[m] enter new monthly financial data");
        System.out.println("[b] view and update balance sheet");
        System.out.println("[s] view summary of monthly financials");
        System.out.println("[r] remove / delete my account");
        System.out.println("[l] logout\n");

    }

    // MODIFIES: this
    // EFFECTS : processes user inputs
    private void processLogInPage(String command) {

        if (command.equals("m")) {
            checkDateEntry();
        } else if (command.equals("b")) {
            displayBalanceSheet();
        } else if (command.equals("s")) {
            displayMonthlySummary();
        } else {
            System.out.println("\nYour selection is invalid.");
        }

    }

    // REQUIRES: there can be no more than one entry of monthly financials for a given month
    // EFFECTS : checks to see if there is already a entry for the given month
    private void checkDateEntry() {

        int monthEntry = 0;
        int yearEntry = 0;
        boolean isValidEntry = true;

        while (isValidEntry) {
            System.out.println("Month (MM): ");
            monthEntry = input.nextInt();

            System.out.println("Year(YYYY): ");
            yearEntry = input.nextInt();

            if (currentAcc.isDateInUse(monthEntry, yearEntry)) {
                System.out.println("You have already submitted an input for that month. Please select another month.");
            } else {
                isValidEntry = false;
            }
        }

        newMonthlyEntry(monthEntry, yearEntry);

    }

    // MODIFIES: this
    // EFFECTS : takes user input to create new income statement entry
    public void newMonthlyEntry(int month, int year) {

        System.out.println("\nRecurring nett income:");
        double recurring = input.nextDouble();

        System.out.println("Non-recurring nett income:");
        double nonrecurring = input.nextDouble();

        System.out.println("Groceries:");
        double groceries = input.nextDouble();

        System.out.println("Rent:");
        double rent = input.nextDouble();

        System.out.println("Subscription expenses:");
        double subscriptions = input.nextDouble();

        System.out.println("Food:");
        double food = input.nextDouble();

        System.out.println("Leisure:");
        double leisure = input.nextDouble();

        System.out.println("Other expenses:");
        double others = input.nextDouble();

        IncomeStatement monthly = new IncomeStatement(month, year, recurring, nonrecurring, groceries, rent,
                subscriptions, food, leisure, others);

        currentAcc.addNewIncomeStatement(monthly);

        summarizeMonthly(monthly);

    }

    // EFFECTS : displays monthly income entry
    public void summarizeMonthly(IncomeStatement monthly) {

        System.out.println("\nTotal nett earnings: " + String.format("%.2f", monthly.getTotalEarnings()));
        System.out.println("Total expenses: " + String.format("%.2f", monthly.getTotalExpenses()));
        System.out.println("Total nett earnings: " + String.format("%.2f", monthly.getNettEarnings()));

        System.out.println("\nUpdate completed - monthly nett income was automatically added to your cash balance.");
        System.out.println("If this is not reflective, please update your balance sheet manually.");

        toContinue();

    }

    // EFFECTS : displays balance sheet and takes user input asking whether they would like to to edit
    public void displayBalanceSheet() {

        System.out.println("\nCurrently, your balance sheet allocation is as follows...");

        System.out.println("Cash: " + String.format("%.2f", currentAcc.getBS().getCash()));
        System.out.println("Investments: " + String.format("%.2f", currentAcc.getBS().getInvestments()));
        System.out.println("Real Estate: " + String.format("%.2f", currentAcc.getBS().getRealEstate()));
        System.out.println("Other Assets: " + String.format("%.2f", currentAcc.getBS().getOtherAssets()));

        System.out.println("Credit Card: " + String.format("%.2f", currentAcc.getBS().getCreditCard()));
        System.out.println("Mortgage: " + String.format("%.2f", currentAcc.getBS().getMortgage()));
        System.out.println("Other Liabilities: "
                + String.format("%.2f", currentAcc.getBS().getOtherLiabilities()));

        System.out.println("\nNet Worth: " + String.format("%.2f", currentAcc.getBS().getNetWorth()));

        String entry = "null";

        while ((!entry.equals("n")) && (!entry.equals("y"))) {

            System.out.println("\nWould you like to manually update the details above? [Y/N]");
            entry = input.next().toLowerCase();

        }

        if (entry.equals("y")) {
            updateBalanceSheetManually();
        }

        saveAccountList();

    }

    // MODIFIES: this
    // EFFECTS : takes user input to update their respective balance sheet
    private void updateBalanceSheetManually() {

        System.out.println("\nPlease update the follow categories with their respective figures");

        System.out.println("Cash:");
        double cash = input.nextDouble();

        System.out.println("Investments:");
        double investments = input.nextDouble();

        System.out.println("Real Estate:");
        double realEstate = input.nextDouble();

        System.out.println("Other Assets:");
        double otherAssets = input.nextDouble();

        System.out.println("Credit Card:");
        double creditCard = input.nextDouble();

        System.out.println("Mortgage:");
        double mortgage = input.nextDouble();

        System.out.println("Other Liabilities:");
        double otherLiabilities = input.nextDouble();

        currentAcc.getBS().updateBalanceSheet(cash,investments,realEstate,otherAssets,creditCard,mortgage,
                otherLiabilities);

        System.out.println("\nUpdate completed. Please review your updates.");

        displayBalanceSheet();

    }

    // EFFECTS : displays user's monthly financial summary
    private void displayMonthlySummary() {

        if (currentAcc.getISList().size() == 0) {
            System.out.println("\nYou have no monthly entries; summary not available.");
        } else {

            double savingsRate = (currentAcc.avgMonthlyNettIncome() / currentAcc.avgMonthlyTotalEarnings()) * 100;

            System.out.println("\nPlease view your average monthly financial summary below:");
            System.out.println("- Recurring Income: " + String.format("%.2f", currentAcc.avgMonthlyRecurring()));
            System.out.println("- Non-Recurring Income: " + String.format("%.2f", currentAcc.avgMonthlyNonRecurring()));
            System.out.println("- Groceries: " + String.format("%.2f", currentAcc.avgMonthlyGroceries()));
            System.out.println("- Rent: " + String.format("%.2f", currentAcc.avgMonthlyRent()));
            System.out.println("- Subscriptions: " + String.format("%.2f", currentAcc.avgMonthlySubs()));
            System.out.println("- Food: " + String.format("%.2f", currentAcc.avgMonthlyFood()));
            System.out.println("- Leisure: " + String.format("%.2f", currentAcc.avgMonthlyLeisure()));
            System.out.println("- Leisure: " + String.format("%.2f", currentAcc.avgMonthlyOtherExp()));

            System.out.println("\nMonthly Earnings: " + String.format("%.2f", currentAcc.avgMonthlyTotalEarnings()));
            System.out.println("Monthly Expenses: " + String.format("%.2f", currentAcc.avgMonthlyTotalExpenses()));
            System.out.println("Monthly Savings: " + String.format("%.2f", currentAcc.avgMonthlyNettIncome()));
            System.out.println("Savings Rate: " + String.format("%.2f",savingsRate) + "%");

        }

    }

    // MODIFIES: this
    // EFFECTS : takes user input to remove account from account list
    private boolean toExecuteRemoval(Account currentAccount) {

        String toContinue;

        System.out.println("\nAre you sure you want to delete this account? [Y/N]");
        toContinue = input.next().toLowerCase();

        if (toContinue.equals("n")) {
            return false;
        }

        System.out.println("\nPlease enter your password to finalize removal of account.");
        String password = input.next();

        if (!accountList.isPasswordMatching(currentAccount.getUser(),password)) {
            System.out.println("Your password is incorrect. Returning to main menu.");
        }

        System.out.println("Removal of your account is successful. Thank you for using NetWorth!");
        accountList.removeAccount(currentAccount.getUser(), password);
        saveAccountList();
        return true;

    }

    // EFFECTS : helper function to allow users to continue or not onto next method
    private void toContinue() {

        String next = "n";

        while (!(next.equals("y"))) {

            System.out.println("Continue? [Y/N]");

            next = input.next().toLowerCase();

        }

        saveAccountList();

    }

    // MODIFIES: this
    // EFFECTS : initializes account list, an "existing" account, and input variable (for Scanner)
    private void init() {

        accountList = new AccountList();
        input = new Scanner(System.in);

    }

    // EFFECTS: saves the account list to file
    private void saveAccountList() {
        try {
            jsonWriter.open();
            jsonWriter.write(accountList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account list from file
    private void loadAccountList() {
        try {
            accountList = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
