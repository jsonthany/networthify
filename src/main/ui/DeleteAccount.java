package ui;

import model.Account;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// frame to confirm deletion of account
public class DeleteAccount extends JFrame implements ActionListener {

    // sound file
    File trashSoundFile = new File("./data/convertedTrashSound.wav");

    // constructor inputs
    private NetWorthAppSwing app;
    private Account currAccount;

    // text field and buttons
    private JTextField passOneField;
    private JTextField passTwoField;
    private JButton submitButton;

    // panel and frame + specification
    private JFrame frame;
    private JPanel panel;

    private Font headerFont = new Font("Arial", Font.BOLD, 16);
    private Font buttonFont = new Font("MS Sans Serif", Font.PLAIN, 12);

    // MODIFIES: this
    // EFFECTS: constructs the delete account frame
    public DeleteAccount(NetWorthAppSwing app, Account account) {

        this.frame = new JFrame();
        this.app = app;
        this.currAccount = account;

        this.panel = panel();
        frame.setContentPane(panel);
        frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    }

    // MODIFIES: this
    // EFFECTS: creates panel for the delete account frame
    private JPanel panel() {
        JPanel mainPanel = createPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel bodyPanel = createPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        addPassword(bodyPanel, c);

        JLabel header = new JLabel("Please submit your details:");
        header.setFont(headerFont);
        header.setForeground(Color.BLACK);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.submitButton = new JButton("Submit");
        this.submitButton.setFont(buttonFont);
        this.submitButton.addActionListener(this);
        this.submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(header);
        mainPanel.add(bodyPanel);
        mainPanel.add(submitButton);

        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates label and fields for panel
    private void addPassword(JPanel bodyPanel, GridBagConstraints c) {
        JLabel passwordOne = new JLabel("Password");
        JLabel passwordTwo = new JLabel("Confirm Password");

        this.passOneField = new JTextField(20);
        this.passTwoField = new JTextField(20);

        c.gridx = 0;
        c.gridy = 0;
        bodyPanel.add(passwordOne, c);
        c.gridx = 1;
        c.gridy = 0;
        bodyPanel.add(passOneField, c);
        c.gridx = 0;
        c.gridy = 1;
        bodyPanel.add(passwordTwo, c);
        c.gridx = 1;
        c.gridy = 1;
        bodyPanel.add(passTwoField, c);
    }

    // EFFECTS: helpers create new panel with below specifications and constraints
    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.lightGray);

        return panel;
    }

    // EFFECTS: plays sound of select sound file
    private void playTrashSound(File file) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // MODIFIES: this, accountList in NetWorthAppSwing
    // EFFECTS: listens to select buttons to delete account or do nothing
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if (currAccount.getPass().equals(passOneField.getText())
                    && currAccount.getPass().equals(passTwoField.getText())) {
                this.app.removeAccount(currAccount.getUser(), passOneField.getText());
                playTrashSound(trashSoundFile);
                this.app.refreshToMainPanel();
                this.frame.dispose();
            } else {
                new PopUpMessage("Incorrect data input!", Color.red);
            }
        }
    }
}
