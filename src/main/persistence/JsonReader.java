package persistence;

import model.Account;
import model.AccountList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.BalanceSheet;
import model.IncomeStatement;
import org.json.*;

// the code here is inspired from JsonSerializationDemo

// Represents a reader that reads account list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account list from JSON object and returns it
    private AccountList parseAccountList(JSONObject jsonObject) {
        AccountList accountList = new AccountList();
        addAccounts(accountList, jsonObject);
        return accountList;
    }

    // MODIFIES: al
    // EFFECTS: parses individual accounts from JSON object and adds them to the account list
    private void addAccounts(AccountList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(al, nextAccount);
        }

    }

    // MODIFIES: al
    // EFFECTS: parses an individual account from JSON object and adds it to the account list
    private void addAccount(AccountList al, JSONObject jsonObject) {
        String username = jsonObject.getString("user");
        System.out.println(username);
        String password = jsonObject.getString("pass");
        int id = jsonObject.getInt("id");

        JSONObject bs = jsonObject.getJSONObject("BS");
        BalanceSheet balanceSheet = addBalanceSheet(bs);

        JSONArray isList = jsonObject.getJSONArray("ISList");
        List<IncomeStatement> incomeStatementList = addIncomeStatementList(isList);

        Account account = new Account(id, username, password, incomeStatementList, balanceSheet);
        al.addNewAccount(account);

    }

    // MODIFIES: al
    // EFFECTS: parses a balance sheet from JSON object and returns a balance sheet for a given account
    private BalanceSheet addBalanceSheet(JSONObject bs) {
        double cash = bs.getDouble("cash");
        double investments = bs.getDouble("investments");
        double realEstate = bs.getDouble("realEstate");
        double otherAssets = bs.getDouble("otherAssets");

        double creditCard = bs.getDouble("creditCard");
        double mortgage = bs.getDouble("mortgage");
        double otherLiabilities = bs.getDouble("otherLiabilities");

        double netWorth = bs.getDouble("netWorth");

        BalanceSheet balanceSheet = new BalanceSheet();
        balanceSheet.updateBalanceSheet(cash, investments, realEstate, otherAssets, creditCard,
                mortgage, otherLiabilities);

        return balanceSheet;

    }

    // MODIFIES: al
    // EFFECTS: parses individual income statement from JSON object and returns income statement list for an account
    private List<IncomeStatement> addIncomeStatementList(JSONArray isList) {
        List<IncomeStatement> incomeStatementList = new ArrayList<>();

        for (Object json : isList) {
            JSONObject nextIncomeStatement = (JSONObject) json;
            incomeStatementList.add(addIncomeStatement(nextIncomeStatement));
        }

        return incomeStatementList;

    }

    // MODIFIES: al
    // EFFECTS: parses a income statement from JSON object and returns an income statement for a given account
    private IncomeStatement addIncomeStatement(JSONObject nextIncomeStatement) {
        int month = nextIncomeStatement.getInt("month");
        int year = nextIncomeStatement.getInt("year");

        double recurringEarnings = nextIncomeStatement.getDouble("recurringEarnings");
        double oneOffEarnings = nextIncomeStatement.getDouble("oneOffEarnings");

        double groceries = nextIncomeStatement.getDouble("groceries");
        double rent = nextIncomeStatement.getDouble("rent");
        double subscriptionExp = nextIncomeStatement.getDouble("subscriptionExp");
        double food = nextIncomeStatement.getDouble("food");
        double leisure = nextIncomeStatement.getDouble("leisure");
        double otherExp = nextIncomeStatement.getDouble("otherExp");

        IncomeStatement incomeStatement = new IncomeStatement(month, year, recurringEarnings, oneOffEarnings, groceries,
                rent, subscriptionExp, food, leisure, otherExp);

        return incomeStatement;

    }
}
