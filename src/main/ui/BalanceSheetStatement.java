package ui;

import model.BalanceSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// balance sheet frame
public class BalanceSheetStatement extends JFrame implements ActionListener {

    // controls whether fields are editable or not
    private boolean isEditable;

    // assets fields
    private JTextField cashField;
    private JTextField investmentsField;
    private JTextField realEstateField;
    private JTextField otherAssetsField;
    private double cashDbl;
    private double investmentsDbl;
    private double realEstateDbl;
    private double otherAssetsDbl;

    // liabilities fields
    private JTextField creditCardField;
    private JTextField mortgageField;
    private JTextField otherLiabilitiesField;
    private double creditCardDbl;
    private double mortgageDbl;
    private double otherLiabilitiesDbl;

    // summary of figures
    private double totalAssets;
    private double totalLiabilities;
    private double totalEquity;

    // frame and panel + specifications
    private JFrame frame;
    private JPanel panel;
    private Font font = new Font("MS Sans Serif", Font.BOLD, 16);

    private int frameWidth = 700;
    private int frameHeight = 500;

    private Font buttonFont = new Font("MS Sans Serif", Font.PLAIN, 12);
    private Font summaryFont = new Font("Arial", Font.BOLD, 14);
    private int textFieldSize = 10;

    // input to constructor
    private NetWorthAppSwing app;
    private BalanceSheet bs;

    // buttons
    private JButton calculateButton;
    private JButton submitButton;
    private JButton editButton;

    // MODIFIES: this
    // EFFECTS: constructs the balance sheet frame
    public BalanceSheetStatement(NetWorthAppSwing app, BalanceSheet bs) {
        this.frame = new JFrame();

        this.app = app;
        this.bs = bs;
        this.isEditable = false;

        // asset fields
        this.cashDbl = bs.getCash();
        this.investmentsDbl = bs.getInvestments();
        this.realEstateDbl = bs.getRealEstate();
        this.otherAssetsDbl = bs.getOtherAssets();

        // liabilities fields
        this.creditCardDbl = bs.getCreditCard();
        this.mortgageDbl = bs.getMortgage();
        this.otherLiabilitiesDbl = bs.getOtherLiabilities();

        this.totalAssets = this.cashDbl + this.investmentsDbl + this.realEstateDbl + this.otherAssetsDbl;
        this.totalLiabilities = this.creditCardDbl + this.mortgageDbl + this.otherLiabilitiesDbl;
        this.totalEquity = this.totalAssets - this.totalLiabilities;

        this.panel = bsPanel();
        frame.setContentPane(panel);
        frame.setSize(frameWidth,frameHeight);
        this.frame.setTitle("Balance Sheet");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    // MODIFIES: this
    // EFFECTS: creates the balance sheet panel
    private JPanel bsPanel() {

        JPanel detailFinancialPanel = financialDetailsPanel();
        JPanel buttonsPanelEditableOff = addButtonsEditableOff();
        JPanel buttonsPanelEditableOn = addButtonsEditableOn();

        // main window panel
        JPanel mainWindow = new JPanel();
        mainWindow.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        mainWindow.setLayout(new BoxLayout(mainWindow, BoxLayout.Y_AXIS));

        // header
        JLabel header = new JLabel("Balance Sheet Statement");
        header.setFont(font);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainWindow.add(header);
        mainWindow.add(detailFinancialPanel);
        if (this.isEditable) {
            mainWindow.add(buttonsPanelEditableOn);
        } else {
            mainWindow.add(buttonsPanelEditableOff);
        }

        return mainWindow;

    }

    // EFFECTS: creates constraints for the panels creates
    private GridBagConstraints constraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        return c;
    }

    // EFFECTS: creates new panel based on parameters specifications
    private JPanel createPanel(int top, int left, int bottom, int right) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates panel with financial details
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
    // EFFECTS: creates fields and labels for panel
    private void addTwoLinesZero(JPanel panel, GridBagConstraints c) {
        JLabel cashLabel = new JLabel("Cash: ");
        JLabel investmentsLabel = new JLabel("Investments: ");

        this.cashField = new JTextField(String.format("%,.2f",this.cashDbl), textFieldSize);
        this.investmentsField = new JTextField(String.format("%,.2f",this.investmentsDbl), textFieldSize);

        this.cashField.setEditable(this.isEditable);
        this.investmentsField.setEditable(this.isEditable);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(cashLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(cashField, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(investmentsLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(investmentsField, c);
    }

    // MODIFIES: this
    // EFFECTS: creates fields and labels for panel
    private void addTwoLinesOne(JPanel panel, GridBagConstraints c) {
        JLabel realEstateLabel = new JLabel("Real Estate: ");
        JLabel otherAssetsLabel = new JLabel("Other Assets: ");

        this.realEstateField = new JTextField(String.format("%,.2f",this.realEstateDbl), textFieldSize);
        this.otherAssetsField = new JTextField(String.format("%,.2f",this.otherAssetsDbl), textFieldSize);

        this.realEstateField.setEditable(this.isEditable);
        this.otherAssetsField.setEditable(this.isEditable);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(realEstateLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(realEstateField, c);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(otherAssetsLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(otherAssetsField, c);
        c.gridx = 0;
        c.gridy = 4;
        panel.add(Box.createVerticalStrut(10), c);
    }

    // MODIFIES: this
    // EFFECTS: creates fields and labels for panel
    private void addTwoLinesTwo(JPanel panel, GridBagConstraints c) {
        JLabel creditLabel = new JLabel("Credit Card: ");
        JLabel mortgageLabel = new JLabel("Mortgage: ");

        this.creditCardField = new JTextField(String.format("%,.2f",this.creditCardDbl), textFieldSize);
        this.mortgageField = new JTextField(String.format("%,.2f",this.mortgageDbl), textFieldSize);

        this.creditCardField.setEditable(this.isEditable);
        this.mortgageField.setEditable(this.isEditable);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(creditLabel, c);
        c.gridx = 1;
        c.gridy = 5;
        panel.add(creditCardField, c);
        c.gridx = 0;
        c.gridy = 6;
        panel.add(mortgageLabel, c);
        c.gridx = 1;
        c.gridy = 6;
        panel.add(mortgageField, c);
    }

    // MODIFIES: this
    // EFFECTS: creates fields and labels for panel
    private void addTwoLinesThree(JPanel panel, GridBagConstraints c) {
        JLabel otherLiabilitiesLabel = new JLabel("Other Liabilities: ");
        this.otherLiabilitiesField = new JTextField(String.format("%,.2f",this.otherLiabilitiesDbl), textFieldSize);
        this.otherLiabilitiesField.setEditable(this.isEditable);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(otherLiabilitiesLabel, c);
        c.gridx = 1;
        c.gridy = 7;
        panel.add(otherLiabilitiesField, c);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(Box.createHorizontalStrut(20), c);

    }

    // EFFECTS: creates text for summary text
    private JLabel createSummaryText(String string) {
        JLabel text = new JLabel(string);
        text.setFont(summaryFont);
        text.setForeground(Color.BLACK);

        return text;
    }

    // MODIFIES: this
    // EFFECTS: creates fields and labels for panel
    private void addTwoLinesFour(JPanel panel, GridBagConstraints c) {

        JLabel totalAssetsField = createSummaryText("Total Assets: ");
        JLabel totalLiabilitiesField = createSummaryText("Total Liabilities: ");
        JLabel nettField = createSummaryText("Nett Worth: ");

        JLabel totalAssetsNum = createSummaryText(String.format("%,.2f",this.totalAssets));
        JLabel totalLiabilitiesNum = createSummaryText(String.format("%,.2f",this.totalLiabilities));
        JLabel totalNetWorthNum = createSummaryText(String.format("%,.2f",this.totalEquity));

        addAnotherLine(panel, c, totalAssetsField, totalLiabilitiesField, nettField, totalAssetsNum,
                totalLiabilitiesNum, totalNetWorthNum);
    }

    // MODIFIES: this
    // EFFECTS: creates fields and labels for panel
    private void addAnotherLine(JPanel panel, GridBagConstraints c, JLabel assets, JLabel liabilities, JLabel nett,
                                JLabel assetsNum, JLabel liabilitiesNum, JLabel nettNum) {
        c.gridx = 3;
        c.gridy = 0;
        panel.add(assets, c);
        c.gridx = 4;
        c.gridy = 0;
        panel.add(assetsNum, c);
        c.gridx = 3;
        c.gridy = 2;
        panel.add(liabilities, c);
        c.gridx = 4;
        c.gridy = 2;
        panel.add(liabilitiesNum, c);
        c.gridx = 3;
        c.gridy = 4;
        panel.add(nett, c);
        c.gridx = 4;
        c.gridy = 4;
        panel.add(nettNum, c);
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for the panel when isEditable set to False
    private JPanel addButtonsEditableOff() {
        JPanel buttonsPanel = createPanel(25, 25, 25, 25);

        this.editButton = new JButton("Edit");
        this.editButton.setFont(buttonFont);
        this.editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.editButton.addActionListener(this);

        buttonsPanel.add(this.editButton);

        return buttonsPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for the panel when isEditable set to True
    private JPanel addButtonsEditableOn() {
        JPanel buttonsPanel = createPanel(10, 25, 10, 25);
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
    // EFFECTS: updates the values of the balance sheet fields
    private void calculate() {
        try {
            this.cashDbl = Double.parseDouble(this.cashField.getText().replaceAll(",",""));
            this.investmentsDbl = Double.parseDouble(this.investmentsField.getText().replaceAll(",",""));
            this.realEstateDbl = Double.parseDouble(this.realEstateField.getText().replaceAll(",",""));
            this.otherAssetsDbl = Double.parseDouble(this.otherAssetsField.getText().replaceAll(",",""));

            this.creditCardDbl = Double.parseDouble(this.creditCardField.getText().replaceAll(",",""));
            this.mortgageDbl = Double.parseDouble(this.mortgageField.getText().replaceAll(",",""));
            this.otherLiabilitiesDbl = Double.parseDouble(this.otherLiabilitiesField.getText().replaceAll(",",""));

            this.totalAssets = this.cashDbl + this.investmentsDbl + this.realEstateDbl + this.otherAssetsDbl;
            this.totalLiabilities = this.creditCardDbl + this.mortgageDbl + this.otherLiabilitiesDbl;
            this.totalEquity = this.totalAssets - this.totalLiabilities;

            this.bs.updateBalanceSheet(cashDbl, investmentsDbl, realEstateDbl, otherAssetsDbl, creditCardDbl,
                    mortgageDbl, otherLiabilitiesDbl);

        } catch (NumberFormatException e) {
            new PopUpMessage("Input not recognized! Please fix.", Color.red);
            throw new NumberFormatException("Must place double values fields!");
        }
    }

    // MODIFIES: this
    // EFFECTS: does specified action based on button pressed (edit/create balance sheet or do nothing)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.calculateButton) {
            calculate();
            this.frame.getContentPane().removeAll();
            this.panel = bsPanel();
            this.frame.setContentPane(this.panel);
            this.frame.repaint();
            this.frame.validate();
        }

        if (e.getSource() == this.submitButton) {
            calculate();
            app.addBalanceSheet(this.bs);
            this.frame.dispose();
        }

        if (e.getSource() == this.editButton) {
            this.isEditable = true;
            this.frame.getContentPane().removeAll();
            this.panel = bsPanel();
            this.frame.setContentPane(this.panel);
            this.frame.repaint();
            this.frame.validate();
        }
    }

}
