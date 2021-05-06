package ui;

import model.Account;
import model.AccountList;
import model.BalanceSheet;
import model.IncomeStatement;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// application gui
public class NetWorthAppSwing implements ActionListener {

    // data persistence
    private static final String JSON_STORE = "./data/users.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // account reference
    private AccountList accountList;
    private Account newAcc;
    private Account currentAcc;
    private Scanner input;

    // text fields
    private JTextField usernameLogIn;
    private JTextField passwordLogIn;

    private JTextField createUserName;
    private JTextField createNewPassword;
    private JTextField confirmPassword;

    // frame + panel specs
    private JFrame frame;
    private State state;
    private JPanel currentPanel;
    private final int frameWidth = 400;
    private final int frameHeight = 500;

    private Color backgroundColor = Color.DARK_GRAY;
    private Font headerFont = new Font("Helvetica", Font.BOLD, 24);
    private Font largeButtonFont = new Font("Arial", Font.PLAIN, 18);
    private Font smallButtonFont = new Font("Arial", Font.PLAIN, 14);
    private Font textFieldFont = new Font("Arial", Font.PLAIN, 18);

    // buttons
    private JButton signInButton;
    private JButton createAccountButton;
    private JButton loadButton;
    private JButton exitApplicationButton;

    private JButton logInButton;
    private JButton mainPageButton;
    private JButton submitCreateUserButton;

    private JButton goToMonthlyStatement;
    private JButton goToBalanceSheet;
    private JButton goToMonthlySummary;
    private JButton toToDeleteAccount;

    // MODIFIES: this
    // EFFECTS: constructs the GUI for the Net Worth App
    public NetWorthAppSwing() {

        // reference JSON
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // loads data initializes account fields
        init();

        // create the frame
        this.state = State.MAIN_PAGE;
        this.frame = new JFrame();

        // panel for the frame
        this.currentPanel = paint();

        // frame specifications
        this.frame.add(this.currentPanel);
        this.frame.setTitle(this.state.toString());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(frameWidth, frameHeight);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

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

    // MODIFIES: this
    // EFFECTS: selects and changes the current panel
    public JPanel paint() {

        switch (state) {
            case MAIN_PAGE:
                return mainPage();
            case CREATE_NEW_ACCOUNT:
                return createNewAccountPanel();
            case SIGN_IN:
                return signInPanel();
            case HOME_PAGE:
                return homePanel();
            default:
                return currentPanel;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the main panel for the frame
    public JPanel mainPage() {

        JPanel mainPage = new JPanel();
        mainPage.setBounds(0,0,frameWidth, frameHeight);
        mainPage.setBackground(backgroundColor);

        JLabel mainHeader = new JLabel("NET WORTHIFY");
        mainHeader.setFont(headerFont);
        mainHeader.setForeground(Color.WHITE);

        this.signInButton = createButton("Sign In", 300, 50);
        this.createAccountButton = createButton("Create New Account", 300, 50);
        this.loadButton = createButton("Load Data", 300, 50);
        this.exitApplicationButton = createButton("Exit Application & Save", 300, 50);

        this.signInButton.addActionListener(this);
        this.createAccountButton.addActionListener(this);
        this.loadButton.addActionListener(this);
        this.exitApplicationButton.addActionListener(this);

        mainPage.add(Box.createVerticalStrut(75));
        mainPage.add(mainHeader);
        mainPage.add(Box.createVerticalStrut(150));
        mainPage.add(signInButton);
        mainPage.add(createAccountButton);
        mainPage.add(loadButton);
        mainPage.add(exitApplicationButton);

        return mainPage;

    }

    // MODIFIES: this
    // EFFECTS: creates the sign in panel for the frame
    private JPanel signInPanel() {
        JPanel signInPanel = new JPanel();
        signInPanel.setBounds(0,0,frameWidth, frameHeight);
        signInPanel.setBackground(backgroundColor);

        JLabel signInHeader = new JLabel("SIGN IN");
        signInHeader.setFont(headerFont);
        signInHeader.setForeground(Color.WHITE);

        this.usernameLogIn = userAccountTextBox("username");
        this.passwordLogIn = userAccountTextBox("password");

        this.logInButton = createButton("Log In", 300, 50);
        this.mainPageButton = createButton("Back to Main Page", 300, 50);

        this.usernameLogIn.addActionListener(this);
        this.passwordLogIn.addActionListener(this);

        logInButton.addActionListener(this);
        mainPageButton.addActionListener(this);

        signInPanel.add(Box.createVerticalStrut(75));
        signInPanel.add(signInHeader);
        signInPanel.add(Box.createVerticalStrut(150));
        signInPanel.add(usernameLogIn);
        signInPanel.add(passwordLogIn);
        signInPanel.add(logInButton);
        signInPanel.add(mainPageButton);

        return signInPanel;
    }

    // EFFECTS: creates a button for the panels in this class
    private JButton createButton(String name, int width, int height) {

        JButton button = new JButton(name);
        button.setFont(largeButtonFont);
        button.setPreferredSize(new Dimension(width, height));

        return button;

    }

    // EFFECTS: creates a button for the panels in this class (with option to choose font type)
    private JButton createButton(String name, int width, int height, Font font) {

        JButton button = new JButton(name);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));

        return button;

    }

    // EFFECTS: creates the create account panel for this frame
    private JPanel createNewAccountPanel() {
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setBounds(0,0,frameWidth, frameHeight);
        createAccountPanel.setBackground(backgroundColor);

        JLabel createAccountHeader = createHeader("CREATE NEW ACCOUNT");

        this.createUserName = userAccountTextBox("username");
        this.createNewPassword = userAccountTextBox("password");
        this.confirmPassword = userAccountTextBox("confirm your password");

        this.createUserName.addActionListener(this);
        this.createNewPassword.addActionListener(this);
        this.confirmPassword.addActionListener(this);

        this.submitCreateUserButton = createButton("Save and Submit", 300, 50);
        this.mainPageButton = createButton("Back to Main Page", 300, 50);

        submitCreateUserButton.addActionListener(this);
        mainPageButton.addActionListener(this);

        createAccountPanel.add(Box.createVerticalStrut(75));
        createAccountPanel.add(createAccountHeader);
        createAccountPanel.add(Box.createVerticalStrut(150));
        createAccountPanel.add(createUserName);
        createAccountPanel.add(createNewPassword);
        createAccountPanel.add(confirmPassword);
        createAccountPanel.add(submitCreateUserButton);
        createAccountPanel.add(mainPageButton);

        return createAccountPanel;
    }

    // EFFECTS: creates text fields for panels in this class
    private JTextField userAccountTextBox(String string) {
        int fieldWidth = 300;
        int fieldHeight = 40;

        JTextField textField = new JTextField(string);
        textField.setFont(textFieldFont);
        textField.setForeground(Color.gray);
        textField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));

        return textField;
    }

    // MODIFIES: this
    // EFFECTS: creates the home panel for this frame
    private JPanel homePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setBackground(backgroundColor);

        JLabel homePanelHeader = createHeader("HI " + currentAcc.getUser().toUpperCase() + "!");

        this.goToMonthlyStatement = createButton("Add Monthly Statement", 300, 50, smallButtonFont);
        this.goToBalanceSheet = createButton("Check / Edit Balance Sheet", 300, 50, smallButtonFont);
        this.goToMonthlySummary = createButton("Summary of Statements", 300, 50,smallButtonFont);
        this.toToDeleteAccount = createButton("Delete My Account", 300, 50, smallButtonFont);
        this.mainPageButton = createButton("Sign Out", 300, 50, smallButtonFont);

        this.goToMonthlyStatement.addActionListener(this);
        this.goToBalanceSheet.addActionListener(this);
        this.goToMonthlySummary.addActionListener(this);
        this.toToDeleteAccount.addActionListener(this);
        this.mainPageButton.addActionListener(this);

        homePanel.add(Box.createVerticalStrut(75));
        homePanel.add(homePanelHeader);
        homePanel.add(Box.createVerticalStrut(150));
        homePanel.add(goToMonthlyStatement);
        homePanel.add(goToBalanceSheet);
        homePanel.add(goToMonthlySummary);
        homePanel.add(toToDeleteAccount);
        homePanel.add(mainPageButton);

        return homePanel;
    }

    // EFFECTS: creates label header for each of the panels in this class
    private JLabel createHeader(String string) {
        JLabel header = new JLabel(string);
        header.setFont(headerFont);
        header.setForeground(Color.WHITE);

        return header;
    }

    // MODIFIES: this
    // EFFECTS: switches panels or constructs new frames based on buttons selected
    @Override
    public void actionPerformed(ActionEvent e) {

        // from main page --> sign in, create account page, load data, or exit application
        checkMainPage(e);

        if (e.getSource() == mainPageButton) {
            goToMainPage();
        }

        if (e.getSource() == logInButton) {
            validateLogInDetails();
        }

        if (e.getSource() == submitCreateUserButton) {
            validateNewUserAccount();
        }

        if (e.getSource() == goToMonthlyStatement) {
            new MonthlyStatement(this);
        }

        if (e.getSource() == goToBalanceSheet) {
            new BalanceSheetStatement(this, this.currentAcc.getBS());
        }

        if (e.getSource() == goToMonthlySummary) {
            goToMonthlySummary();
        }

        if (e.getSource() == toToDeleteAccount) {
            goToDeleteAccount();
        }

        updatePanel();

    }

    // MODIFIES: this
    // EFFECTS: switches to different panels based on button pressed, loads data, or exits application
    private void checkMainPage(ActionEvent e) {
        if (e.getSource() == signInButton) {
            goToSignIn();
        } else if (e.getSource() == createAccountButton) {
            goToCreateAccount();
        } else if (e.getSource() == loadButton) {
            init();
            loadAccountList();
            new PopUpMessage("Data Successfully Loaded!", Color.DARK_GRAY);
        } else if (e.getSource() == exitApplicationButton) {
            goToExitApplication();
        }
    }

    // MODIFIES: this
    // EFFECTS: validates login details; if invalid, go to home page and assigns to current account
    private void validateLogInDetails() {

        String username = usernameLogIn.getText();
        String password = passwordLogIn.getText();

        if (accountList.isPasswordMatching(username, password)) {
            currentAcc = accountList.getAccount(username, password);
            goToHomePage();
        } else if (!accountList.isUserNameUsed(username)) {
            new PopUpMessage("Username does not seem to exist!", Color.red);
        } else {
            new PopUpMessage("Username and password does not match!", Color.red);
        }

    }

    // MODIFIES: this, ConfirmMessage
    // EFFECTS: validates new user account; if new user details are valid, create new user, do nothing otherwise
    private void validateNewUserAccount() {

        String username = this.createUserName.getText();
        String password = this.createNewPassword.getText();
        String confirmPassword = this.confirmPassword.getText();

        if ((!accountList.isUserNameUsed(username)) && (password.length() >= 6) && (password.equals(confirmPassword))) {
            new ConfirmMessage(username, password, this);
        } else if (accountList.isUserNameUsed(username)) {
            new PopUpMessage("Username has already been taken.", Color.red);
        } else if (password.length() < 6) {
            new PopUpMessage("Password needs to be at least 6 characters.", Color.red);
        } else if (!password.equals(confirmPassword)) {
            new PopUpMessage("The two password entries not matching.", Color.red);
        }
    }

    // MODIFIES: this
    // EFFECTS : creates new account and adds it to the account list
    public void createNewAccount(String username, String password) {

        newAcc = new Account(username, password);
        accountList.addNewAccount(newAcc);
        saveAccountList();

    }

    // MODIFIES: this
    // EFFECTS: changes panel to main page
    public void goToMainPage() {
        this.state = State.MAIN_PAGE;
        this.currentPanel = paint();
    }

    // MODIFIES: this
    // EFFECTS: changes panel to sign in page
    public void goToSignIn() {
        this.state = State.SIGN_IN;
        this.currentPanel = paint();
    }

    // MODIFIES: this
    // EFFECTS: changes panel to create new account page
    private void goToCreateAccount() {
        this.state = State.CREATE_NEW_ACCOUNT;
        this.currentPanel = paint();
    }

    // MODIFIES: this
    // EFFECTS: exits panel
    private void goToExitApplication() {
        saveAccountList();
        frame.dispose();
    }

    // MODIFIES: this
    // EFFECTS: changes panel to home page
    private void goToHomePage() {
        this.state = State.HOME_PAGE;
        this.currentPanel = paint();
    }

    // EFFECTS: creates new frame to delete the current account
    private void goToDeleteAccount() {
        new DeleteAccount(this, currentAcc);
    }

    // EFFECTS: creates new frame to view month statement summary
    private void goToMonthlySummary() {
        new MonthlySummary(currentAcc);
    }

    // MODIFIES: this
    // EFFECTS: refreshes the current panel
    private void updatePanel() {
        frame.getContentPane().removeAll();
        frame.add(currentPanel);
        this.frame.setTitle(this.state.toString());

        frame.repaint();
        frame.validate();
    }

    // EFFECTS: checks to see if the year is available to add new monthly statement
    public boolean isMonthYearAvailable(int month, int year) {
        return !(currentAcc.isDateInUse(month, year));
    }

    // MODIFIES: this
    // EFFECTS: adds new monthly income statement to the current account
    public void addIncomeStatement(IncomeStatement incomeStatement) {
        currentAcc.addNewIncomeStatement(incomeStatement);
        saveAccountList();
    }

    // MODIFIES: this
    // EFFECTS: adds newly edited balance sheet to the current account
    public void addBalanceSheet(BalanceSheet bs) {
        currentAcc.getBS().updateBalanceSheet(bs.getCash(), bs.getInvestments(), bs.getRealEstate(),
                bs.getOtherAssets(), bs.getCreditCard(), bs.getMortgage(), bs.getOtherLiabilities());
        saveAccountList();
    }

    // MODIFIES: this
    // EFFECTS: removes the current account from the current account list
    public void removeAccount(String user, String password) {
        accountList.removeAccount(user, password);
        saveAccountList();
    }

    // MODIFIES: this
    // EFFECTS: changes current panel to main page and refrshes the page
    public void refreshToMainPanel() {
        goToMainPage();
        updatePanel();
    }

}
