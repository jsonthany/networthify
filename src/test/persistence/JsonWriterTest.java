package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountList wr = new AccountList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountList() {
        try {
            AccountList al = new AccountList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountList.json");
            al = reader.read();
            assertTrue(al.isListEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AccountList al = new AccountList();
            al.addNewAccount(new Account("jason", "password"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountList.json");
            al = reader.read();
            assertEquals(1, al.length());
            Account account = al.getAccount("jason","password");

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}