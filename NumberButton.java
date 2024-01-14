import javax.swing.*;

class NumberButton extends JButton implements Operator {
    public NumberButton(String label) {
        super(label);
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.inputDigit(getText());
    }
}
