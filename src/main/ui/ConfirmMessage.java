package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// confirm message frame
public class ConfirmMessage extends JFrame implements ActionListener {

    // frame and panel + specifications
    private JFrame frame;
    private Font headerFont = new Font("Arial", Font.BOLD, 16);
    private Font messageFontPlain = new Font("Arial", Font.PLAIN, 14);
    private Font messageFontBold = new Font("Arial", Font.BOLD, 14);
    private Font buttonFont = new Font("MS Sans Serif", Font.PLAIN, 12);

    // buttons
    private JButton buttonSubmit;
    private JButton cancelAndChange;

    // username and password
    private String username;
    private String password;

    // constructor input
    private NetWorthAppSwing app;

    // MODIFIES: this
    // EFFECTS: constructs the confirm message frame and panel
    public ConfirmMessage(String user, String password, NetWorthAppSwing app) {
        this.frame = new JFrame();
        this.app = app;

        this.username = user;
        this.password = password;

        JPanel panel = panel(user, password);
        frame.setContentPane(panel);
        frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    }

    // MODIFIES: this
    // EFFECTS: constructs the confirm message panel
    private JPanel panel(String user, String password) {

        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.lightGray);

        JPanel panelOne = panelOne(user, password);
        JPanel panelTwo = panelTwo();

        JLabel header = new JLabel("Would you like to save these changes?");
        header.setFont(headerFont);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));

        messagePanel.add(header);
        messagePanel.add(panelOne);
        messagePanel.add(panelTwo);

        return messagePanel;
    }

    // MODIFIES: this
    // EFFECTS: constructs panels for this frame
    private JPanel subPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,25,5,25));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.lightGray);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: constructs sub-panel for main window panel
    private JPanel panelOne(String user, String password) {
        JPanel panel = subPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel userKeyLabel = createLabel("Username: ", messageFontBold);
        JLabel userLabel = createLabel(user, messageFontPlain);
        JLabel passwordKeyLabel = createLabel("Password: ", messageFontBold);
        JLabel passwordLabel = createLabel(password, messageFontPlain);

        c.insets = new Insets(5,5,5,5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(userKeyLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(userLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(passwordKeyLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(passwordLabel, c);

        return panel;

    }

    // MODIFIES: this
    // EFFECTS: constructs sub-panel for main window panel
    private JPanel panelTwo() {
        JPanel panel = subPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        this.buttonSubmit = new JButton("Save and Submit");
        this.buttonSubmit.setFont(buttonFont);
        this.buttonSubmit.addActionListener(this);

        this.cancelAndChange = new JButton("Cancel / Change");
        this.cancelAndChange.setFont(buttonFont);
        this.cancelAndChange.addActionListener(this);

        c.insets = new Insets(5,5,5,5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(buttonSubmit, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(cancelAndChange, c);

        return panel;
    }

    // EFFECTS: creates a new label with set specifications
    private JLabel createLabel(String string, Font font) {
        JLabel thisLabel = new JLabel(string);
        thisLabel.setFont(font);
        thisLabel.setForeground(Color.BLACK);
        thisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return thisLabel;
    }

    // MODIFIES: this, accountList in NetWorthAppSwing
    // EFFECTS: listens to selected buttons and saves or cancels creation of new account
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonSubmit) {
            this.app.createNewAccount(this.username, this.password);
            System.out.println("saved" + this.username);
            this.app.refreshToMainPanel();
            frame.dispose();
        }

        if (e.getSource() == cancelAndChange) {
            frame.dispose();
        }
    }
}
