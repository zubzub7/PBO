import javax.swing.*;

class BackspaceButton extends JButton implements Operator {
    public BackspaceButton() {
        super("del");
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.inputBackspace();
    }
}
