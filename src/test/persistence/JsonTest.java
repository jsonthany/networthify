package persistence;

import model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAccount(int id, String username, String password, List<IncomeStatement> incomeStatementList,
                                BalanceSheet balanceSheet, Account account) {
        assertEquals(id, account.getId());
        assertEquals(username, account.getUser());
        assertEquals(password, account.getPass());
        assertEquals(incomeStatementList, account.getISList());
        assertEquals(balanceSheet, account.getBS());
    }
}