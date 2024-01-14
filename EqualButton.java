import javax.swing.*;

class EqualButton extends JButton implements Operator {
    public EqualButton() {
        super("=");
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.calculateTotal();
    }
}
