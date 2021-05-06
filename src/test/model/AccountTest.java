package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account newAccount;

    @BeforeEach
    public void setup() {
        newAccount = new Account("jthany", "password");
    }

    @Test
    public void zeroChanges() {

        assertEquals("jthany", newAccount.getUser());
        assertEquals("password", newAccount.getPass());
        assertEquals(1, newAccount.getId());
        assertEquals(0, newAccount.getBS().getNetWorth());
        assertEquals(0, newAccount.getISList().size());

    }

    @Test
    public void addIncomeStatements() {

        // add one new income statement
        IncomeStatement incomeStatement1 = new IncomeStatement(1, 2020, 1, 2,
                3, 4, 5, 6, 7, 8);
        newAccount.addNewIncomeStatement(incomeStatement1);

        // check addition of single income statement
        assertEquals(1, newAccount.getISList().size());
        assertTrue(newAccount.isDateInUse(1, 2020));
        assertFalse(newAccount.isDateInUse(1, 2021));
        assertFalse(newAccount.isDateInUse(2, 2020));

        assertEquals(1, newAccount.avgMonthlyRecurring());
        assertEquals(2, newAccount.avgMonthlyNonRecurring());
        assertEquals(3, newAccount.avgMonthlyGroceries());
        assertEquals(4, newAccount.avgMonthlyRent());
        assertEquals(5, newAccount.avgMonthlySubs());
        assertEquals(6, newAccount.avgMonthlyFood());
        assertEquals(7, newAccount.avgMonthlyLeisure());
        assertEquals(8, newAccount.avgMonthlyOtherExp());

        assertEquals(1 + 2, newAccount.avgMonthlyTotalEarnings());
        assertEquals(3 + 4 + 5 + 6 + 7 + 8, newAccount.avgMonthlyTotalExpenses());
        assertEquals(1 + 2 - 3 - 4 - 5 - 6 - 7 - 8, newAccount.avgMonthlyNettIncome());

        // add a second income statement to the list
        IncomeStatement incomeStatement2 = new IncomeStatement(2, 2020, 10, 10,
                2, 2, 2, 2, 2, 2);
        newAccount.addNewIncomeStatement(incomeStatement2);

        // check for second additional to the list
        assertEquals(2, newAccount.getISList().size());
        assertTrue(newAccount.isDateInUse(2, 2020));
        assertFalse(newAccount.isDateInUse(2, 2021));
        assertFalse(newAccount.isDateInUse(5, 2020));

        assertEquals(5.5, newAccount.avgMonthlyRecurring());
        assertEquals(6, newAccount.avgMonthlyNonRecurring());
        assertEquals(2.5, newAccount.avgMonthlyGroceries());
        assertEquals(3, newAccount.avgMonthlyRent());
        assertEquals(3.5, newAccount.avgMonthlySubs());
        assertEquals(4, newAccount.avgMonthlyFood());
        assertEquals(4.5, newAccount.avgMonthlyLeisure());
        assertEquals(5, newAccount.avgMonthlyOtherExp());

        assertEquals(5.5 + 6, newAccount.avgMonthlyTotalEarnings());
        assertEquals(2.5 + 3 + 3.5 + 4 + 4.5 + 5, newAccount.avgMonthlyTotalExpenses());
        assertEquals(5.5 + 6 - 2.5 - 3 - 3.5 - 4 - 4.5 - 5, newAccount.avgMonthlyNettIncome());

    }

    @Test
    void testToJson() {
        JSONObject jsonObject = new JSONObject();

        Account accountTwo = new Account(2, "jason", "password", new ArrayList<>(),
                new BalanceSheet());

        jsonObject.put("password", "password");
        jsonObject.put("id", 2);
        jsonObject.put("incomeStatementList", accountTwo.getISList());
        jsonObject.put("username", "jason");
        jsonObject.put("balanceSheet", accountTwo.getBS());

        assertEquals(jsonObject.toString(), accountTwo.toJson().toString());

    }

}
