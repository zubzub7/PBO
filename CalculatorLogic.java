public class CalculatorLogic {
    private double currentNumber;
    private double result;
    private String currentOperator;
    private boolean isNewInput;
    private StringBuilder currentExpression;
    private double baseNumber;
    private boolean decimalInput;

    public CalculatorLogic() {
        clear();
    }

    public void inputDigit(String digit) {
        if (isNewInput) {
            currentNumber = Double.parseDouble(digit);
            isNewInput = false;
            currentExpression = new StringBuilder(digit);
        } else {
            if (!decimalInput) {
                currentNumber = currentNumber * 10 + Double.parseDouble(digit);
            } else {
                currentNumber = currentNumber + Double.parseDouble(digit) / Math.pow(10, currentExpression.length() - 1);
            }
            currentExpression.append(digit);
        }
    }

    public void inputDecimal() {
        if (isNewInput) {
            currentNumber = 0.0;
            isNewInput = false;
            currentExpression = new StringBuilder("0.");
            decimalInput = true;
        } else {
            // Periksa apakah ekspresi saat ini sudah mengandung titik desimal
            if (!decimalInput) {
                currentExpression.append(".");
                decimalInput = true;
            }
        }    }

    private String getDigitsAfterDecimal() {
        // Ekstrak digit setelah titik desimal dari ekspresi saat ini
        int indexOfDecimal = currentExpression.indexOf(".");
        return currentExpression.substring(indexOfDecimal + 1);
    }

    public void inputOperator(String operator) {
        if (!isNewInput) {
            currentExpression.append(" ").append(operator).append(" ");
            if (operator.equals(".")) {
                decimalInput = true;
            }
        }
        if (operator.equals("C")) {
            clear();
        } else if (operator.equals("x\u02B8")) {
            baseNumber = currentNumber;
            currentOperator = operator;
            isNewInput = true;
        } else if (!operator.equals("=")) {
            calculateTotal();
            currentOperator = operator;
            result = currentNumber;
            isNewInput = true;
        } else {
            calculateTotal();
            currentOperator = "";
            isNewInput = true;
        }
    }

    public void inputBackspace() {
        if (!isNewInput && currentExpression.length() > 0) {
            currentExpression.deleteCharAt(currentExpression.length() - 1);

            if (currentExpression.length() > 0) {
                String lastInput = currentExpression.substring(currentExpression.length() - 1);
                if ("+-*/.".contains(lastInput)) {
                    isNewInput = false;
                    if (lastInput.equals(".")) {
                        decimalInput = true;
                    }
                } else {
                    currentNumber = Double.parseDouble(currentExpression.toString());
                }
            } else {
                currentNumber = 0;
                isNewInput = true;
                decimalInput = false;
            }
        }
    }

    public void calculateTotal() {
        if (!currentOperator.isEmpty()) {
            double operand2 = currentNumber;
            currentExpression.setLength(0);
            switch (currentOperator) {
                case "sin":
                    result = Math.sin(Math.toRadians(operand2));
                    currentExpression.append("sin(").append(operand2).append(") ");
                    break;
                case "cos":
                    result = Math.cos(Math.toRadians(operand2));
                    currentExpression.append("cos(").append(operand2).append(") ");
                    break;
                case "tan":
                    result = Math.tan(Math.toRadians(operand2));
                    currentExpression.append("tan(").append(operand2).append(") ");
                    break;
                case "log":
                    result = Math.log(operand2);
                    currentExpression.append("log(").append(operand2).append(") ");
                    break;
                case "!":
                    currentExpression.append(operand2).append("! ");
                    if (operand2 < 0) {
                        currentExpression = new StringBuilder("Error! ");
                        result = Double.NaN;
                    } else {
                        result = 1;
                        for (int i = 2; i <= operand2; i++) {
                            result *= i;
                        }
                    }
                    break;
                case "\u03C0": // x phi
                    currentExpression.append(operand2).append(" x \u03C0 ");
                    result = Math.PI * operand2;

                    break;
                case "x\u00B2": // pow 2
                    currentExpression.append(operand2).append("\u00B2 ");
                    result = Math.pow(operand2, 2);
                    break;
                case "x\u00B3": // pow 3
                    currentExpression.append(operand2).append("\u00B3 ");
                    result = Math.pow(operand2, 3);
                    break;
                case "\u221A": // sqrt
                    result = Math.sqrt(operand2);
                    currentExpression.append("\u221A").append(operand2).append(" ");
                    break;
                case "\u0025": // mod
                    currentExpression.append(result).append(" % ").append(operand2).append(" ");
                    result %= operand2;
                    break;
                case "\u2797": // divide
                    currentExpression.append(result).append(" / ").append(operand2).append(" ");
                    try {
                        if (operand2 != 0) {
                            result /= operand2;
                        } else {
                            throw new ArithmeticException("Error: Division By Zero ");
                        }
                    } catch (ArithmeticException e) {
                        String errorMessage = e.getMessage();
                        currentExpression = new StringBuilder(errorMessage);
                        result = Double.NaN;
                    }
                    break;
                case "x\u02B8": // x^y
                    currentExpression.append(baseNumber).append(" ^ ").append(operand2).append(" ");
                    result = Math.pow(baseNumber, operand2);
                    break;
                case "\u2A09": // times
                    currentExpression.append(result).append(" x ").append(operand2).append(" ");
                    result = performDecimalOperation(currentOperator, result, operand2);
                    break;
                case "\u2796": // sub
                    currentExpression.append(result).append(" - ").append(operand2).append(" ");
                    result = performDecimalOperation(currentOperator, result, operand2);
                    break;
                case "\u2795": // sum
                    currentExpression.append(result).append(" + ").append(operand2).append(" ");
                    result = performDecimalOperation(currentOperator, result, operand2);
                    break;
                case "ln":
                    if (result > 0) {
                        currentExpression.append("ln(").append(operand2).append(") ");
                        result = performDecimalOperation(currentOperator, result, operand2);
                    } else {
                        currentExpression = new StringBuilder("Error: ln of non-positive number");
                        result = Double.NaN;
                    }
                    break;
                default:
                    break;
            }
            currentNumber = result;
        }
    }

    private double performDecimalOperation(String operator, double operand1, double operand2) {
        switch (operator) {
            case "\u2A09": // times
            return operand1 * operand2;
            case "\u2796": // sub
                return operand1 - operand2;
            case "\u2795": // sum
                return operand1 + operand2;
            case "ln":
                return Math.log(operand2);
            default:
                return 0;
        }
    }

    public void clear() {
        currentNumber = 0;
        result = 0;
        currentOperator = "";
        currentExpression = new StringBuilder();
        isNewInput = true;
        decimalInput = false;
    }

    public String getCurrentNumberAsString() {
        if (decimalInput) {
            return String.valueOf(currentNumber);
        } else {
            return String.valueOf((int) currentNumber);
        }
    }

    public String getResultAsString() {
        return String.valueOf(result);
    }

    public String getExpressionAsString() {
        return currentExpression.toString();
    }
}