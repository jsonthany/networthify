package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceSheetTest {

    private BalanceSheet balanceSheet;

    @BeforeEach
    public void setup() {
        balanceSheet = new BalanceSheet();
    }

    @Test
    public void emptyBalanceSheet() {

        // assets
        assertEquals(0,balanceSheet.getCash());
        assertEquals(0,balanceSheet.getInvestments());
        assertEquals(0,balanceSheet.getRealEstate());
        assertEquals(0,balanceSheet.getOtherAssets());

        // liabilities
        assertEquals(0,balanceSheet.getCreditCard());
        assertEquals(0,balanceSheet.getMortgage());
        assertEquals(0,balanceSheet.getOtherLiabilities());

        assertEquals(0,balanceSheet.getNetWorth());

    }

    @Test
    public void addCashBalanceSheet() {

        balanceSheet.addCash(50);
        assertEquals(50, balanceSheet.getNetWorth());

    }

    @Test
    public void updatedBalanceSheet() {

        balanceSheet.updateBalanceSheet(1,2,3,4,5,6,7);

        // assets
        assertEquals(1,balanceSheet.getCash());
        assertEquals(2,balanceSheet.getInvestments());
        assertEquals(3,balanceSheet.getRealEstate());
        assertEquals(4,balanceSheet.getOtherAssets());

        // liabilities
        assertEquals(5,balanceSheet.getCreditCard());
        assertEquals(6,balanceSheet.getMortgage());
        assertEquals(7,balanceSheet.getOtherLiabilities());

        assertEquals(1 + 2 + 3 + 4 - 5 - 6 - 7,balanceSheet.getNetWorth());

    }

    @Test
    void testToJson() {
        JSONObject jsonObject = new JSONObject();
        balanceSheet.addCash(575);

        jsonObject.put("otherAssets", 0);
        jsonObject.put("mortgage", 0);
        jsonObject.put("otherLiabilities", 0);
        jsonObject.put("netWorth", 575);
        jsonObject.put("realEstate", 0);
        jsonObject.put("investments", 0);
        jsonObject.put("creditCard", 0);
        jsonObject.put("cash", 575);

        assertEquals(jsonObject.toString(), balanceSheet.toJson().toString());

    }

}
