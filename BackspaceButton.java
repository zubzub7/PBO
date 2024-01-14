import javax.swing.*;

class BackspaceButton extends JButton implements Operator {
    public BackspaceButton() {
        super("\u2190");
    }

    @Override
    public void performOperation(CalculatorLogic calculatorLogic) {
        calculatorLogic.inputBackspace();
    }
}
