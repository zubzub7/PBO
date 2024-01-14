import javax.swing.*;

class MathOperatorButton extends JButton implements Operator {
    public MathOperatorButton(String label) {
        super(label);
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.inputOperator(getText());
    }
}
