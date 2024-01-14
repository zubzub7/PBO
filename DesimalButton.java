import javax.swing.*;

class DecimalButton extends JButton implements Operator {
    public DecimalButton() {
        super(".");
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.inputDecimal();
    }
}
