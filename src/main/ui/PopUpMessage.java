package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// pop up message frame
public class PopUpMessage extends JFrame implements ActionListener {

    private Font errorFont = new Font("Arial", Font.PLAIN, 14);
    private JFrame frame;
    private Font buttonFont = new Font("MS Sans Serif", Font.PLAIN, 12);
    private Color color;
    private JButton buttonSubmit;

    // MODIFIES: this
    // EFFECTS: constructs new pop up message frame
    public PopUpMessage(String errorMessage, Color color) {
        this.frame = new JFrame();
        this.color = color;

        JPanel errorPanel = errorPanel(errorMessage);
        frame.setContentPane(errorPanel);
        frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

    }

    // MODIFIES: this
    // EFFECTS: creates panel for pop up message frame
    private JPanel errorPanel(String errorMessage) {
        JPanel errorPanel = new JPanel();
        errorPanel.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));

        errorPanel.setBackground(this.color);

        JLabel errorLabel = new JLabel(errorMessage);
        errorLabel.setFont(errorFont);
        errorLabel.setForeground(Color.WHITE);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.buttonSubmit = new JButton("OK");
        this.buttonSubmit.setFont(buttonFont);
        this.buttonSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.buttonSubmit.addActionListener(this);

        errorPanel.add(errorLabel);
        errorPanel.add(Box.createVerticalStrut(5));
        errorPanel.add(buttonSubmit);

        return errorPanel;

    }

    // MODIFIES: this
    // EFFECTS: dispose frame when button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonSubmit) {
            System.out.println("exit application");
            frame.dispose();
        }
    }
}
