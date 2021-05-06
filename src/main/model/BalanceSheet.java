package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a balance sheet tied to an account with assets, liabilities, and net worth summary
public class BalanceSheet implements Writable {

    // assets fields
    private double cash;
    private double investments;
    private double realEstate;
    private double otherAssets;

    // liabilities fields
    private double creditCard;
    private double mortgage;
    private double otherLiabilities;

    // summary field
    private double netWorth;

    // MODIFIES: this
    // EFFECT  : constructs the initial balance sheet amount for a new account
    public BalanceSheet() {

        this.cash = 0;
        this.investments = 0;
        this.realEstate = 0;
        this.otherAssets = 0;

        this.creditCard = 0;
        this.mortgage = 0;
        this.otherLiabilities = 0;

        this.netWorth = 0;

    }

    // MODIFIES: this
    // EFFECTS : updates balance sheet fields based on single cash field
    public void updateBalanceSheet(double cash) {

        this.cash = cash;

        this.netWorth = this.cash + this.investments + this.realEstate + this.otherAssets - this.creditCard
                - this.mortgage - this.otherLiabilities;

    }

    // MODIFIES: this
    // EFFECTS : updates all balance sheet fields based on single cash field
    public void updateBalanceSheet(double cash, double investments, double realEstate, double otherAssets,
                                   double creditCard, double mortgage, double otherLiabilities) {

        this.cash = cash;
        this.investments = investments;
        this.realEstate = realEstate;
        this.otherAssets = otherAssets;

        this.creditCard = creditCard;
        this.mortgage = mortgage;
        this.otherLiabilities = otherLiabilities;

        this.netWorth = this.cash + this.investments + this.realEstate + this.otherAssets - this.creditCard
                - this.mortgage - this.otherLiabilities;

    }

    // EFFECTS : retrieves balance of cash
    public double getCash() {
        return this.cash;
    }

    // EFFECTS : retrieves balance of investments
    public double getInvestments() {
        return this.investments;
    }

    // EFFECTS : retrieves balance of real estate assets
    public double getRealEstate() {
        return this.realEstate;
    }

    // EFFECTS : retrieves balance of other assets
    public double getOtherAssets() {
        return this.otherAssets;
    }

    // EFFECTS : retrieves balance of credit card liabilities
    public double getCreditCard() {
        return this.creditCard;
    }

    // EFFECTS : retrieves balance of mortgage liabilities
    public double getMortgage() {
        return this.mortgage;
    }

    // EFFECTS : retrieves balance of other liabilities
    public double getOtherLiabilities() {
        return this.otherLiabilities;
    }

    // EFFECTS : retrieves balance of net worth
    public double getNetWorth() {
        return this.netWorth;
    }

    // MODIFIES: this
    // EFFECTS : changes the cash balance
    public void addCash(double amount) {
        this.cash += amount;
        updateBalanceSheet(this.cash);
    }

    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();

        // assets fields
        json.put("cash", this.cash);
        json.put("investments", this.investments);
        json.put("realEstate", this.realEstate);
        json.put("otherAssets", this.otherAssets);

        // liabilities fields
        json.put("creditCard", this.creditCard);
        json.put("mortgage", this.mortgage);
        json.put("otherLiabilities", this.otherLiabilities);

        // summary field
        json.put("netWorth", this.netWorth);

        return json;

    }

}