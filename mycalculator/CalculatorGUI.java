package simple.mycalculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CalculatorGUI extends Implementation implements ActionListener {
    // create a constructor without parameter
    public CalculatorGUI() {
        InitializeFrame();
        InitializeButton();
        InitializeTextField();
        InitializeMenuBar();
    }
    private void InitializeFrame() {
        frame.setTitle("Calculator");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private void InitializeButton() {
        // initialize the programmer calculator panel
        JPanel programmer_panel = new JPanel();
        programmer_panel.setLayout(new GridLayout(6, 5, 2, 2));
        // initialize the buttons
        for(int i = 0; i < 10; i++) {
            Jbt[i] = new JButton(PROGRAMMER_KEYS[i]);
            Dimension preferredSize = new Dimension(95, 50);
            Jbt[i].setPreferredSize(preferredSize);
            Jbt[i].setForeground(Color.black);
            Jbt[i].setBorderPainted(false);
            programmer_panel.add(Jbt[i]);

            if(i == 0 || i == 5) {
                Jbt[i].setBackground(Color.white);
                Jbt[i].setFont(new Font("黑体", Font.BOLD, 20));
            } else {
                Jbt[i].setBackground(color3);
                Jbt[i].setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
            }
        }
        for (int i = 10; i < PROGRAMMER_KEYS.length; i++) {
            Jbt[i] = new JButton(PROGRAMMER_KEYS[i]);
            Dimension preferredSize = new Dimension(95, 50);
            Jbt[i].setPreferredSize(preferredSize);
            Jbt[i].setForeground(Color.black);
            Jbt[i].setBorderPainted(false);
            programmer_panel.add(Jbt[i]);

            if((i + 1) % 5 == 0) {
                Jbt[i].setBackground(color3);
                Jbt[i].setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 20));
            } else {
                Jbt[i].setBackground(Color.white);
                Jbt[i].setFont(new Font("黑体", Font.BOLD, 20));
            }
        }
        // set the background color of the button "="
        Jbt[29].setBackground(color2);
        Jbt[29].setForeground(Color.white);
        // set the background color of the panel
        programmer_panel.setBackground(color1);

        frame.getContentPane().add("South", programmer_panel);
        programmer_panel.setBorder(BorderFactory.createMatteBorder(5, 3, 5, 3, color1));
        // add action listener to the buttons
        for (int i = 0; i < PROGRAMMER_KEYS.length; i++) {
            Jbt[i].addActionListener(this);
        }
    }
    private void InitializeTextField() {
        // initialize the result text field
        JPanel resultTextPanel = new JPanel();
        resultTextPanel.setLayout(new BorderLayout());
        resultTextPanel.setSize(400, 50);
        resultTextPanel.add(outputText);
        outputText.setFont(new Font("黑体", Font.BOLD, 55));
        outputText.setHorizontalAlignment(JTextField.RIGHT);
        outputText.setEditable(false);
        outputText.setBorder(null);
        outputText.setBackground(color1);
        // initialize the process text field
        JPanel processTextPanel = new JPanel();
        processTextPanel.setLayout(new BorderLayout());
        processTextPanel.setSize(400, 50);
        processTextPanel.add(inputText);
        inputText.setFont(new Font("黑体", Font.PLAIN, 25));
        inputText.setHorizontalAlignment(JTextField.RIGHT);
        inputText.setEditable(false);
        inputText.setBorder(null);
        inputText.setBackground(color1);

        frame.getContentPane().add("North", inputText);
        frame.getContentPane().add("Center", outputText);
        inputText.setBorder(BorderFactory.createMatteBorder(25, 3, 0, 3, color1));
        outputText.setBorder(BorderFactory.createMatteBorder(0, 3, 5, 3, color1));
    }
    private void InitializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu editMenu = new JMenu("Selection");
        menuBar.add(editMenu);

        JMenu optionMenu = new JMenu("Option");
        ButtonGroup group = new ButtonGroup();
        DECLayout();
        JRadioButtonMenuItem[] items = {new JRadioButtonMenuItem("DEC", true),
                                           new JRadioButtonMenuItem("BIN", false),
                                           new JRadioButtonMenuItem("OCT", false),
                                           new JRadioButtonMenuItem("HEX", false)};
        for (JRadioButtonMenuItem item : items) {
            item.addActionListener(this);
            group.add(item);
            optionMenu.add(item);
        }
        editMenu.add(optionMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        menuBar.add(helpMenu);
        aboutItem = new JMenuItem("About", 'A');
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);
    }
    // action listener
    public void actionPerformed(ActionEvent e) {
        // get the command
        String cmd = e.getActionCommand();

        if(cmd.equals(PROGRAMMER_KEYS[4])) doDelete();
        else if("0123456789.".contains(cmd)) doNumber(cmd);
        else if("+-×÷=".contains(cmd)) doOperator(cmd);
        else if(cmd.equals("C")) doClear();
        else if(cmd.equals("CE")) doClearEntry();
        else doNothing();

        switch(cmd) {
            case "DEC":
                DECLayout();
                break;
            case "BIN":
                BINLayout();
                break;
            case "OCT":
                OCTLayout();
                break;
            case "HEX":
                HEXLayout();
                break;
            case "About":
                showAboutDialog();
                break;
        }
    }
}
