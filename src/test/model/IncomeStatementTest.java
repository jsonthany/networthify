package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncomeStatementTest {

    private IncomeStatement newIncomeStatement;

    // date fields
    int MONTH = 2;
    int YEAR = 2020;

    // revenue fields
    double RECURRING_EARNINGS = 10_000.318;
    double ONE_OFF_EARNINGS = 500.70;

    // cost fields
    double GROCERIES = 50.31;
    double RENT = 500.70;
    double SUBSCRIPTIONS = 100.00;
    double FOOD = 300.70;
    double LEISURE = 800.41;
    double OTHERS = 100.70;

    @BeforeEach
    public void setup() {
        newIncomeStatement = new IncomeStatement(MONTH, YEAR, RECURRING_EARNINGS, ONE_OFF_EARNINGS, GROCERIES, RENT,
                SUBSCRIPTIONS, FOOD, LEISURE, OTHERS);
    }

    @Test
    public void getEachValue() {

        assertEquals(MONTH, newIncomeStatement.getMonth());
        assertEquals(YEAR, newIncomeStatement.getYear());

        assertEquals(RECURRING_EARNINGS, newIncomeStatement.getRecurringEarnings());
        assertEquals(ONE_OFF_EARNINGS, newIncomeStatement.getOneOffEarnings());

        assertEquals(GROCERIES, newIncomeStatement.getGroceries());
        assertEquals(RENT, newIncomeStatement.getRent());
        assertEquals(SUBSCRIPTIONS, newIncomeStatement.getSubscriptionExp());
        assertEquals(FOOD, newIncomeStatement.getFood());
        assertEquals(LEISURE, newIncomeStatement.getLeisure());
        assertEquals(OTHERS, newIncomeStatement.getOtherExp());

        assertEquals(RECURRING_EARNINGS, newIncomeStatement.getRecurringEarnings());
        assertEquals(RECURRING_EARNINGS, newIncomeStatement.getRecurringEarnings());

        assertEquals(RECURRING_EARNINGS + ONE_OFF_EARNINGS, newIncomeStatement.getTotalEarnings());
        assertEquals(GROCERIES + RENT + SUBSCRIPTIONS + FOOD + LEISURE + OTHERS,
                newIncomeStatement.getTotalExpenses());

        assertEquals(8648.198, newIncomeStatement.getNettEarnings());
        assertEquals("8648.20", String.format("%.2f", newIncomeStatement.getNettEarnings()));

    }

    @Test
    void testToJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("totalEarnings", RECURRING_EARNINGS + ONE_OFF_EARNINGS);
        jsonObject.put("year", YEAR);
        jsonObject.put("totalExpenses", GROCERIES + RENT + SUBSCRIPTIONS + FOOD + LEISURE + OTHERS);
        jsonObject.put("rent", RENT);
        jsonObject.put("subscriptionExp", SUBSCRIPTIONS);
        jsonObject.put("food", FOOD);
        jsonObject.put("nettEarnings", 8648.198);
        jsonObject.put("oneOffEarnings", ONE_OFF_EARNINGS);
        jsonObject.put("recurringEarnings", RECURRING_EARNINGS);
        jsonObject.put("month", MONTH);
        jsonObject.put("otherExp", OTHERS);
        jsonObject.put("groceries", GROCERIES);
        jsonObject.put("leisure", LEISURE);

        assertEquals(jsonObject.toString(), newIncomeStatement.toJson().toString());

    }

}
