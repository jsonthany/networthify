package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;

// create a frame of list of months of created monthly statements
public class ListOfMonths extends JFrame {

    private Frame frame;
    private Account acc;
    private JList<String> listOfMonths;

    // MODIFIES: this
    // EFFECTS: constructs frame with list of months of created statements
    public ListOfMonths(Account currentAccount) {
        this.frame = new JFrame();

        this.acc = currentAccount;
        DefaultListModel<String> listToAdd = new DefaultListModel<>();

        for (int i = 0; i < acc.getISList().size(); i++) {
            if (currentAccount.getISList().get(i).getMonth() < 10) {
                listToAdd.addElement("0" + acc.getISList().get(i).getMonth() + "/" + acc.getISList().get(i).getYear());
            } else {
                listToAdd.addElement(acc.getISList().get(i).getMonth() + "/" + acc.getISList().get(i).getYear());
            }
        }

        this.listOfMonths = new JList<>(listToAdd);
        this.frame.add(listOfMonths);

        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

}
