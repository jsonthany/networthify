package ui;

import model.IncomeStatement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// monthly statement frame
public class MonthlyStatement extends JFrame implements ActionListener {

    // constructor inputs
    private IncomeStatement incomeStatement;
    private NetWorthAppSwing app;

    // date fields
    private JComboBox<String> monthField;
    private JComboBox<String> yearField;
    private int monthInt;
    private int yearInt;

    // revenue fields and values
    private JTextField recurringIncomeField;
    private JTextField nonRecurringIncomeField;
    private double recurringEarningsDbl;
    private double oneOffEarningsDbl;

    // cost fields and values
    private JTextField groceriesField;
    private JTextField rentField;
    private JTextField subscriptionField;
    private JTextField foodField;
    private JTextField leisureField;
    private JTextField otherExpField;
    private double groceriesDbl;
    private double rentDbl;
    private double subscriptionExpDbl;
    private double foodDbl;
    private double leisureDbl;
    private double otherExpDbl;

    // summary values
    private double totalEarnings;
    private double totalExpenses;
    private double nettEarnings;
    private double savingRate;

    // buttons
    private JButton calculateButton;
    private JButton submitButton;

    // frame and panel + specifications
    private JFrame frame;
    private JPanel panel;
    private Font font = new Font("MS Sans Serif", Font.BOLD, 16);

    private int frameWidth = 650;
    private int frameHeight = 525;

    private Font buttonFont = new Font("MS Sans Serif", Font.PLAIN, 12);
    private Font summaryFont = new Font("Arial", Font.BOLD, 14);
    private int textFieldSize = 10;

    // MODIFIES: this
    // EFFECTS: constructs new frame for monthly statement inputs
    public MonthlyStatement(NetWorthAppSwing app) {
        this.frame = new JFrame();

        this.recurringEarningsDbl = 0.00;
        this.oneOffEarningsDbl = 0.00;

        this.groceriesDbl = 0.00;
        this.rentDbl = 0.00;
        this.subscriptionExpDbl = 0.00;
        this.foodDbl = 0.00;
        this.leisureDbl = 0.00;
        this.otherExpDbl = 0.00;

        this.totalEarnings = 0.00;
        this.totalExpenses = 0.00;
        this.nettEarnings = 0.00;

        this.app = app;

        this.panel = inputMonthlyPanel();
        frame.setContentPane(panel);
        frame.setSize(frameWidth,frameHeight);
        this.frame.setTitle("Monthly Statement");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    // MODIFIES: this
    // EFFECTS: creates new panel for frame
    private JPanel inputMonthlyPanel() {

        JPanel monthlyStatementDetails = monthAndYearPanel();
        JPanel detailFinancialPanel = financialDetailsPanel();
        JPanel buttonsPanel = addButtonsPanel();

        // main window panel
        JPanel mainWindow = new JPanel();
        mainWindow.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        mainWindow.setLayout(new BoxLayout(mainWindow, BoxLayout.Y_AXIS));

        // header
        JLabel header = new JLabel("Submit New Monthly Statement");
        header.setFont(font);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainWindow.add(header);
        mainWindow.add(monthlyStatementDetails);
        mainWindow.add(detailFinancialPanel);
        mainWindow.add(buttonsPanel);

        return mainWindow;

    }

    // EFFECTS: creates array of values between lower and upper input parameters
    private String[] createStringArray(int lower, int upper) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = lower; i < (upper + 1); i++) {
            list.add(Integer.toString(i));
        }
        String[] stringArray = list.toArray(new String[list.size()]);

        return stringArray;

    }

    // EFFECTS: constraints for panel
    private GridBagConstraints constraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        return c;
    }

    // EFFECTS: helps create panels for the frame
    private JPanel createPanel(int top, int left, int bottom, int right) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates frame for month and year drop-down menu inputs
    private JPanel monthAndYearPanel() {

        JPanel monthlyStatementDetails = createPanel(25, 25, 25, 25);
        GridBagConstraints c = constraints();

        String[] monthsArray = createStringArray(1,12);
        String[] yearsArray = createStringArray(2000,2050);

        JLabel monthLabel = new JLabel("Month (MM): ");
        JLabel yearLabel = new JLabel("Year (YYYY): ");
        this.monthField = new JComboBox<String>(monthsArray);
        this.yearField = new JComboBox<String>(yearsArray);

        c.insets = new Insets(5,5,5,5);

        c.gridx = 0;
        c.gridy = 0;
        monthlyStatementDetails.add(monthLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        monthlyStatementDetails.add(monthField, c);
        c.gridx = 2;
        c.gridy = 0;
        monthlyStatementDetails.add(yearLabel, c);
        c.gridx = 3;
        c.gridy = 0;
        monthlyStatementDetails.add(yearField, c);

        return monthlyStatementDetails;

    }

    // MODIFIES: this
    // EFFECTS: creates panel for financial detail inputs
    private JPanel financialDetailsPanel() {

        JPanel detailFinancialsPanel = createPanel(25, 25, 25, 25);
        GridBagConstraints c = constraints();

        addTwoLinesZero(detailFinancialsPanel, c);
        addTwoLinesOne(detailFinancialsPanel, c);
        addTwoLinesTwo(detailFinancialsPanel, c);
        addTwoLinesThree(detailFinancialsPanel, c);
        addTwoLinesFour(detailFinancialsPanel, c);

        return detailFinancialsPanel;

    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addTwoLinesZero(JPanel panel, GridBagConstraints c) {
        JLabel recurringIncome = new JLabel("Recurring Income: ");
        JLabel nonRecurringIncome = new JLabel("Non-Recurring Income: ");

        this.recurringIncomeField = new JTextField(String.format("%,.2f",this.recurringEarningsDbl), textFieldSize);
        this.nonRecurringIncomeField = new JTextField(String.format("%,.2f",this.oneOffEarningsDbl), textFieldSize);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(recurringIncome, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(recurringIncomeField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(nonRecurringIncome, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(nonRecurringIncomeField, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(Box.createVerticalStrut(10), c);
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addTwoLinesOne(JPanel panel, GridBagConstraints c) {
        JLabel groceries = new JLabel("Groceries: ");
        JLabel rent = new JLabel("Rent: ");

        this.groceriesField = new JTextField(String.format("%,.2f",this.groceriesDbl), textFieldSize);
        this.rentField = new JTextField(String.format("%,.2f",this.rentDbl), textFieldSize);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(groceries, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(groceriesField, c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(rent, c);
        c.gridx = 1;
        c.gridy = 4;
        panel.add(rentField, c);
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addTwoLinesTwo(JPanel panel, GridBagConstraints c) {
        JLabel subscription = new JLabel("Subscriptions: ");
        JLabel food = new JLabel("Food: ");

        this.subscriptionField = new JTextField(String.format("%,.2f",this.subscriptionExpDbl), textFieldSize);
        this.foodField = new JTextField(String.format("%,.2f",this.foodDbl), textFieldSize);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(subscription, c);
        c.gridx = 1;
        c.gridy = 5;
        panel.add(subscriptionField, c);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(food, c);
        c.gridx = 1;
        c.gridy = 6;
        panel.add(foodField, c);
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addTwoLinesThree(JPanel panel, GridBagConstraints c) {
        JLabel leisure = new JLabel("Leisure: ");
        JLabel otherExp = new JLabel("Other Expenses: ");

        this.leisureField = new JTextField(String.format("%,.2f",this.leisureDbl),textFieldSize);
        this.otherExpField = new JTextField(String.format("%,.2f",this.otherExpDbl), textFieldSize);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(leisure, c);
        c.gridx = 1;
        c.gridy = 7;
        panel.add(leisureField, c);
        c.gridx = 0;
        c.gridy = 8;
        panel.add(otherExp, c);
        c.gridx = 1;
        c.gridy = 8;
        panel.add(otherExpField, c);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(Box.createHorizontalStrut(20), c);

    }

    // EFFECTS: helps create text for summary with given specifications below
    private JLabel createSummaryText(String string) {
        JLabel text = new JLabel(string);
        text.setFont(summaryFont);
        text.setForeground(Color.BLACK);

        return text;
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addTwoLinesFour(JPanel panel, GridBagConstraints c) {

        this.savingRate = (this.nettEarnings / this.totalEarnings) * 100;
        JLabel totalIncome = createSummaryText("Total Earnings: ");
        JLabel totalExp = createSummaryText("Total Expenses: ");
        JLabel nett = createSummaryText("Nett Earnings: ");

        JLabel savingLabel = new JLabel("Savings Rate");
        Font savingsRateFont = new Font("Arial", Font.BOLD + Font.ITALIC, 14);

        savingLabel.setFont(savingsRateFont);
        savingLabel.setForeground(Color.DARK_GRAY);

        JLabel totalIncomeField = createSummaryText(String.format("%,.2f",this.totalEarnings));
        JLabel totalExpField = createSummaryText(String.format("%,.2f",this.totalExpenses));
        JLabel nettField = createSummaryText(String.format("%,.2f",this.nettEarnings));
        JLabel savingRateField = new JLabel(String.format("%,.2f",savingRate) + "%");

        savingRateField.setFont(savingsRateFont);
        savingRateField.setForeground(Color.DARK_GRAY);

        addAnotherLine(panel, c, totalIncome, totalExp, nett, totalIncomeField, totalExpField, nettField);
        addAnotherLineTwo(panel, c, savingLabel, savingRateField);
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addAnotherLine(JPanel panel, GridBagConstraints c, JLabel income, JLabel exp, JLabel nett,
                                JLabel incomeField, JLabel expField, JLabel nettField) {
        c.gridx = 3;
        c.gridy = 0;
        panel.add(income, c);
        c.gridx = 4;
        c.gridy = 0;
        panel.add(incomeField, c);
        c.gridx = 3;
        c.gridy = 2;
        panel.add(exp, c);
        c.gridx = 4;
        c.gridy = 2;
        panel.add(expField, c);
        c.gridx = 3;
        c.gridy = 4;
        panel.add(nett, c);
        c.gridx = 4;
        c.gridy = 4;
        panel.add(nettField, c);
    }

    // MODIFIES: this
    // EFFECTS: adds text and fields for financial detail panel
    private void addAnotherLineTwo(JPanel panel, GridBagConstraints c, JLabel save, JLabel savingsField) {
        c.gridx = 3;
        c.gridy = 6;
        panel.add(save, c);
        c.gridx = 4;
        c.gridy = 6;
        panel.add(savingsField, c);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons for the main panel
    private JPanel addButtonsPanel() {
        JPanel buttonsPanel = createPanel(25, 25, 25, 25);
        GridBagConstraints c = constraints();

        this.calculateButton = new JButton("Calculate");
        this.calculateButton.setFont(buttonFont);
        this.calculateButton.addActionListener(this);

        this.submitButton = new JButton("Submit");
        this.submitButton.setFont(buttonFont);
        this.submitButton.addActionListener(this);

        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add(calculateButton, c);
        c.gridx = 1;
        c.gridy = 0;
        buttonsPanel.add(submitButton, c);

        return buttonsPanel;

    }

    // MODIFIES: this
    // EFFECTS: assigns values for Income Statement fields
    private void calculate() {
        try {
            this.monthInt = Integer.parseInt(this.monthField.getSelectedItem().toString());
            this.yearInt = Integer.parseInt(this.yearField.getSelectedItem().toString());

            this.recurringEarningsDbl = Double.parseDouble(this.recurringIncomeField.getText().replaceAll(",",""));
            this.oneOffEarningsDbl = Double.parseDouble(this.nonRecurringIncomeField.getText().replaceAll(",",""));
            this.groceriesDbl = Double.parseDouble(this.groceriesField.getText().replaceAll(",",""));
            this.rentDbl = Double.parseDouble(this.rentField.getText().replaceAll(",",""));
            this.subscriptionExpDbl = Double.parseDouble(this.subscriptionField.getText().replaceAll(",",""));
            this.foodDbl = Double.parseDouble(this.foodField.getText().replaceAll(",",""));
            this.leisureDbl = Double.parseDouble(this.leisureField.getText().replaceAll(",",""));
            this.otherExpDbl = Double.parseDouble(this.otherExpField.getText().replaceAll(",",""));

            this.totalEarnings = this.recurringEarningsDbl + this.oneOffEarningsDbl;
            this.totalExpenses = this.groceriesDbl + this.rentDbl + this.subscriptionExpDbl + this.foodDbl
                    + this.leisureDbl + this.otherExpDbl;
            this.nettEarnings = this.totalEarnings - this.totalExpenses;

        } catch (NumberFormatException e) {
            new PopUpMessage("Input not recognized! Please fix.", Color.red);
            throw new NumberFormatException("Must place double values fields!");
        }
    }

    // MODIFIES: this
    // EFFECTS: listens to selected buttons to create new Income Statement or refresh page; else do nothing
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.calculateButton) {
            calculate();
            this.frame.getContentPane().removeAll();
            this.panel = inputMonthlyPanel();
            this.frame.setContentPane(this.panel);
            this.frame.repaint();
            this.frame.validate();
        }

        if (e.getSource() == this.submitButton) {

            calculate();

            if (app.isMonthYearAvailable(this.monthInt, this.yearInt)) {
                this.incomeStatement = new IncomeStatement(monthInt, yearInt, recurringEarningsDbl, oneOffEarningsDbl,
                        groceriesDbl, rentDbl, subscriptionExpDbl, foodDbl, leisureDbl, otherExpDbl);
                app.addIncomeStatement(this.incomeStatement);
                this.frame.dispose();
            } else {
                new PopUpMessage("There exists a submission for that given date. "
                        + "\nPlease select another date.", Color.red);
            }

        }
    }
}
