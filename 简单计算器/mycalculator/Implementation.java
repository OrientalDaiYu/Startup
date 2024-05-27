package simple.mycalculator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Implementation extends Component {
    // create a frame
    public static JFrame frame = new JFrame();
    // create menu items
    public static JMenuItem aboutItem;
    public static JRadioButtonMenuItem DECItem, HEXItem, OCTItem, BINItem;
    //programmer calculator panel
    public static final String[] PROGRAMMER_KEYS = {
            "A", "<<", ">>", "C", "Del",
            "B", "(", ")", "%", "÷",
            "C", "7", "8", "9", "×",
            "D", "4", "5", "6", "-",
            "E", "1", "2", "3", "+",
            "F", "+/-", "0", ".", "="
    };
    // create a color set to personalize the components
    Color color1 = new Color(181, 181, 181);
    Color color2 = new Color(126, 192, 238); // "="'s own color
    Color color3 = new Color(232, 232, 232); // the function button background color
    public static JButton[] Jbt = new JButton[PROGRAMMER_KEYS.length];
    // create two text fields for input and output
    public static JTextField inputText = new JTextField();
    public static JTextField outputText = new JTextField("0");
    public boolean firstDigit = true;  // judge whether the first digit is input or the first digit next to an operator
    public double resultNum = 0.0000;
    public static int AMPLIFICATION = 1000;
    public String curOperator = "=";
    public boolean operateValidFlag = true;

    public void DECLayout() {
        HEXLayout();
        int[] enabledIndices = {0, 5, 10, 15, 20, 25};
        for (int index : enabledIndices) {
            Jbt[index].setEnabled(false);
        }
    }
    public void BINLayout() {
        HEXLayout();
        int[] enabledIndices = {0, 5, 10, 11, 12, 13, 15, 16, 17, 18, 20, 22, 23, 25};
        for (int index : enabledIndices) {
            Jbt[index].setEnabled(false);
        }
    }
    public void OCTLayout() {
        HEXLayout();
        int[] enabledIndices = {0, 5, 10, 12, 13, 15, 20, 25};
        for (int index : enabledIndices) {
            Jbt[index].setEnabled(false);
        }
    }
    public void HEXLayout() {
        int[] enabledIndices = {0, 5, 10, 11, 12, 13, 15, 16, 17, 18, 20, 21, 22, 23, 25, 27};
        for (int index : enabledIndices) {
            Jbt[index].setEnabled(true);
        }
    }
    public void showAboutDialog() {
        String message = "版权归来自班级为计算机224\n" +
                "学号为3220421136\n" +
                "名叫范凯炫的个人所有！";
        String title = "Message Dialogue";
        int type = JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    public void doDelete() {
        String text = outputText.getText();
        int i = text.length();
        if (i > 0) {
            text = text.substring(0, i-1);  // delete the last character
            if (text.isEmpty()) {
                outputText.setText("0");// if the text is empty, set the text to "0."
                firstDigit = true;
                curOperator = "=";
            } else outputText.setText(text);// set the text to the new text
        }
    }
    public void doClear() {
        // clear the text
        inputText.setText(null);
        outputText.setText("0");
        firstDigit = true;
        curOperator = "=";
    }
    public void doClearEntry() {
        Jbt[3].setText("C");
        outputText.setText("0");
    }
    public void doNumber(String str) {
        Jbt[3].setText("CE");
        if(firstDigit) {
            // if the first digit is input, then reset the output text(avoid integer by the initial 0)
            outputText.setText(null);
            outputText.setText(str);

        } else if ((str.equals(".")) && (!outputText.getText().contains("."))) {
            // if the input is "." and the result text doesn't contain ".", then add "." to the result text
            outputText.setText(outputText.getText() + ".");
        } else if (!str.equals(".")) { // if the input is not ".", then add the input to the result text
            if(!outputText.getText().equals("0"))
                outputText.setText(outputText.getText() + str);
            else {
                outputText.setText(null);
                outputText.setText(str);
            }
        }
        firstDigit = false;
    }
    public void doOperator(String str) {
        switch (curOperator) {
            case "÷":
                if (getNumberFromText() == 0.0) {
                    operateValidFlag = false;
                    outputText.setText("Math ERROR");
                } else {
                    resultNum = (resultNum * AMPLIFICATION) / (getNumberFromText() * AMPLIFICATION);
                }
                break;
            case "+":
                resultNum = (resultNum * AMPLIFICATION + getNumberFromText() * AMPLIFICATION) / AMPLIFICATION;
                break;
            case "-":
                resultNum = (resultNum * AMPLIFICATION - getNumberFromText() * AMPLIFICATION) / AMPLIFICATION;
                break;
            case "×":
                resultNum = ((resultNum * AMPLIFICATION) * (getNumberFromText() * AMPLIFICATION)) / (AMPLIFICATION * AMPLIFICATION);
                break;
            case "=":
                resultNum = getNumberFromText();
                inputText.setText(null);
                break;
        }
        if ("+-×÷=".contains(str)) {
            inputText.setText(inputText.getText() + outputText.getText() + str);
        }

        isOperateValidFlag(operateValidFlag);
        // set the operator
        curOperator = str;
        firstDigit = true;
        if (!operateValidFlag) {
            inputText.setText(null);
        }
        operateValidFlag = true;
    }
    public void doNothing() {}
    public void isOperateValidFlag(boolean operateValidFlag) {
        if (operateValidFlag) {
            long t1 = (long)resultNum;
            double t2 = resultNum - t1;
            BigDecimal bd = new BigDecimal(String.valueOf(resultNum));
            if (t2 == 0) {
                outputText.setText(String.valueOf(t1));
            } else if (bd.scale() == 1) {
                outputText.setText(String.valueOf(new DecimalFormat("0.0").format(resultNum)));
            } else if (bd.scale() == 2) {
                outputText.setText(String.valueOf(new DecimalFormat("0.00").format(resultNum)));
            } else if (bd.scale() == 3) {
                outputText.setText(String.valueOf(new DecimalFormat("0.000").format(resultNum)));
            } else if (bd.scale() == 4) {
                outputText.setText(String.valueOf(new DecimalFormat("0.0000").format(resultNum)));
            } else if (bd.scale() == 5) {
                outputText.setText(String.valueOf(new DecimalFormat("0.00000").format(resultNum)));
            } else if (bd.scale() == 6) {
                outputText.setText(String.valueOf(new DecimalFormat("0.000000").format(resultNum)));
            } else if (bd.scale() == 7) {
                outputText.setText(String.valueOf(new DecimalFormat("0.0000000").format(resultNum)));
            } else if (bd.scale() == 8) {
                outputText.setText(String.valueOf(new DecimalFormat("0.00000000").format(resultNum)));
            } else {
                outputText.setText(String.valueOf(new DecimalFormat("0.000000000").format(resultNum)));
            }
        }
    }
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.parseDouble(outputText.getText());
        } catch (NumberFormatException e) {
            outputText.setText("ERROR");
        }
        return result;
    }
}
