package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountList.json");
        try {
            AccountList al = reader.read();
            assertTrue(al.isListEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountList.json");
        try {
            AccountList al = reader.read();
            assertFalse(al.isListEmpty());

            Account account = al.getAccount("jason", "password");
            assertEquals(1, al.length());

            assertEquals(1,account.getId());
            assertEquals("jason",account.getUser());
            assertEquals("password",account.getPass());

            BalanceSheet balanceSheet = account.getBS();
            assertEquals(575,balanceSheet.getCash());
            assertEquals(575,balanceSheet.getNetWorth());
            assertEquals(0,balanceSheet.getOtherAssets());
            assertEquals(0,balanceSheet.getMortgage());
            assertEquals(0,balanceSheet.getOtherLiabilities());
            assertEquals(0,balanceSheet.getRealEstate());
            assertEquals(0,balanceSheet.getInvestments());
            assertEquals(0,balanceSheet.getCreditCard());

            List<IncomeStatement> incomeStatementList = account.getISList();
            assertEquals(2, incomeStatementList.size());

            assertEquals(1, incomeStatementList.get(0).getMonth());
            assertEquals(2020, incomeStatementList.get(0).getYear());
            assertEquals(300, incomeStatementList.get(0).getTotalEarnings());
            assertEquals(85, incomeStatementList.get(0).getTotalExpenses());
            assertEquals(20, incomeStatementList.get(0).getRent());
            assertEquals(10, incomeStatementList.get(0).getSubscriptionExp());
            assertEquals(5, incomeStatementList.get(0).getFood());
            assertEquals(215, incomeStatementList.get(0).getNettEarnings());
            assertEquals(200, incomeStatementList.get(0).getOneOffEarnings());
            assertEquals(100, incomeStatementList.get(0).getRecurringEarnings());
            assertEquals(25, incomeStatementList.get(0).getOtherExp());
            assertEquals(10, incomeStatementList.get(0).getGroceries());
            assertEquals(15, incomeStatementList.get(0).getLeisure());

            assertEquals(2, incomeStatementList.get(1).getMonth());
            assertEquals(2021, incomeStatementList.get(1).getYear());
            assertEquals(500, incomeStatementList.get(1).getTotalEarnings());
            assertEquals(140, incomeStatementList.get(1).getTotalExpenses());
            assertEquals(12, incomeStatementList.get(1).getRent());
            assertEquals(31, incomeStatementList.get(1).getSubscriptionExp());
            assertEquals(42, incomeStatementList.get(1).getFood());
            assertEquals(360, incomeStatementList.get(1).getNettEarnings());
            assertEquals(100, incomeStatementList.get(1).getOneOffEarnings());
            assertEquals(400, incomeStatementList.get(1).getRecurringEarnings());
            assertEquals(23, incomeStatementList.get(1).getOtherExp());
            assertEquals(20, incomeStatementList.get(1).getGroceries());
            assertEquals(12, incomeStatementList.get(1).getLeisure());


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}