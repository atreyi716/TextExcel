// Atreyi Seal
// Period 4
// AP Computer Science A

/*
 * In Checkpoint #1: this class is ignored.
 * In Checkpoint #2: the NumberCell will hold a number only.
 *    Example:  a1 = 5
 * In Checkpoint #3: the NumberCell may hold an expression.
 *    Example:  a1 = ( 2 + a2 / a3 * a4 )
 * In Final Submission: the NumberCell may hold functions.
 *    Example:  a1 = ( sum b1 - b5 )
 */

public class NumberCell extends Cell {
  /*
   * This returns the string to be presented in the grid.
   */
  public String toString() {
    // Leverage this method when fulfilling the command, "display [cell]"
    return getValue() + "";
  }
  /*
   * This will return the number for this cell.
   */
  public double getValue() {
    // Leverage this method when fulfilling the command, "value [cell]"
    String[] tokens = GridBase.smartSplit(getExpression());
    double value = 0;
    String operator = "+";
    // If parentheses, continue
    // Check for a math operator
    // Check if char is letter
    // Check if char is a digit --> call performMathOperator
    for (int i = 0; i < tokens.length; i++) {
      if (tokens[i].equals("(")) {
        continue;
      }
      // Different operations
      if (tokens[i].equals("+")
        || tokens[i].equals("-")
        || tokens[i].equals("*")
        || tokens[i].equals("/")) {
        operator = tokens[i];
      }
      if (Character.isLetter(tokens[i].charAt(0))) {
        String cellValue = GridBase.grid.processCommand("value " + tokens[i]);
        double operand = Double.parseDouble(cellValue);
        value = performMathOperation(operator, operand, value);
      }
      if (Character.isDigit(tokens[i].charAt(0))) {
        double operand = Double.parseDouble(tokens[i]);
        value = performMathOperation(operator, operand, value);
      }
    }
    return value;
  }

  public double performMathOperation(String operator, double operand, double value) {
    switch (operator) {
      case "+":
        value += operand;
        break;
      case "-":
        value -= operand;
        break;
      case "*":
        value *= operand;
        break;
      case "/":
        value /= operand;
        break;
    }
    return value;
  }
}