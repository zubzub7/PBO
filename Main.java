public class Main {
    public static void main(String[] args) {
        CalculatorLogic calculatorLogic = new CalculatorLogic();
        CalculatorGUI calculatorGUI = new CalculatorGUI();
        calculatorGUI.setCalculatorLogic(calculatorLogic);
    }
}