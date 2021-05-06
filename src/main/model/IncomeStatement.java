package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents an income statement that has month, year, revenue, and cost fields for a given month
public class IncomeStatement implements Writable {

    // date fields
    private int month;
    private int year;

    // revenue fields
    private double recurringEarnings;
    private double oneOffEarnings;

    // cost fields
    private double groceries;
    private double rent;
    private double subscriptionExp;
    private double food;
    private double leisure;
    private double otherExp;

    // summary fields
    private double totalEarnings;
    private double totalExpenses;
    private double nettEarnings;

    // REQUIRES: input values >= 0
    // MODIFIES: this
    // EFFECTS : initializes a new income statement
    public IncomeStatement(int month, int year, double recurringEarnings, double oneOffEarnings, double groceries,
                           double rent, double subscriptionExp, double food, double leisure, double otherExp) {

        this.month = month;
        this.year = year;

        this.recurringEarnings = recurringEarnings;
        this.oneOffEarnings = oneOffEarnings;

        this.groceries = groceries;
        this.rent = rent;
        this.subscriptionExp = subscriptionExp;
        this.food = food;
        this.leisure = leisure;
        this.otherExp = otherExp;

        this.totalEarnings = recurringEarnings + oneOffEarnings;
        this.totalExpenses = groceries + rent + subscriptionExp + food + leisure + otherExp;
        this.nettEarnings = totalEarnings - totalExpenses;

    }

    // EFFECTS : retrieves month
    public int getMonth() {
        return this.month;
    }

    // EFFECTS : retrieves year
    public int getYear() {
        return this.year;
    }

    // EFFECTS : retrieves recurring earnings
    public double getRecurringEarnings() {
        return this.recurringEarnings;
    }

    // EFFECTS : retrieves non-recurring earnings
    public double getOneOffEarnings() {
        return this.oneOffEarnings;
    }

    // EFFECTS : retrieves groceries expense
    public double getGroceries() {
        return this.groceries;
    }

    // EFFECTS : retrieves rent expense
    public double getRent() {
        return this.rent;
    }

    // EFFECTS : retrieves subscription expense
    public double getSubscriptionExp() {
        return this.subscriptionExp;
    }

    // EFFECTS : retrieves food expenses
    public double getFood() {
        return this.food;
    }

    // EFFECTS : retrieves leisure expense
    public double getLeisure() {
        return this.leisure;
    }

    // EFFECTS : retrieves other expense
    public double getOtherExp() {
        return this.otherExp;
    }

    // EFFECTS : retrieves total earnings
    public double getTotalEarnings() {
        return this.totalEarnings;
    }

    // EFFECTS : retrieves total expense
    public double getTotalExpenses() {
        return this.totalExpenses;
    }

    // EFFECTS : retrieves nett earnings
    public double getNettEarnings() {
        return this.nettEarnings;
    }

    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();

        // date fields
        json.put("month", this.month);
        json.put("year", this.year);

        // revenue fields
        json.put("recurringEarnings", this.recurringEarnings);
        json.put("oneOffEarnings", this.oneOffEarnings);

        // cost fields
        json.put("groceries", this.groceries);
        json.put("rent", this.rent);
        json.put("subscriptionExp", this.subscriptionExp);
        json.put("food", this.food);
        json.put("leisure", this.leisure);
        json.put("otherExp", this.otherExp);

        // summary fields
        json.put("totalEarnings", this.totalEarnings);
        json.put("totalExpenses", this.totalExpenses);
        json.put("nettEarnings", this.nettEarnings);

        return json;

    }

}
