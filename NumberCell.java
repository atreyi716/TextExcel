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
        String cellExpression = getExpression();
        String[] tokens = GridBase.smartSplit(cellExpression);
        double value = 0;
        String operator = "+";
        // If parentheses, continue
        // Check for a math operator
        // Check if char is letter
        // Check if char is a digit --> call performMathOperator
        int i = 0;
        while (i < tokens.length) {
            if (tokens[i].equals("(") ||
                tokens[i].equals(")")) {
                i++;
            } else if (tokens[i].equalsIgnoreCase("sum")) {
                // Sum
                double[] result = sumAndAvg(tokens[i + 1], tokens[i + 3]);
                value = result[0];
                i += 4; // Move to the last token
            } else if (tokens[i].equalsIgnoreCase("avg")) {
                // Average
                double[] result = sumAndAvg(tokens[i + 1], tokens[i + 3]);
                value = result[1];
                i += 4; // Move to the last token
            } else if ( tokens[i].equals("+") || 
                        tokens[i].equals("-") ||
                        tokens[i].equals("*") ||
                        tokens[i].equals("/")) { 
                // Different operations
                operator = tokens[i];
                i++;
            } else if (Grid.isCellName(tokens[i])) {
                // Cell case
                String cellValue = GridBase.grid.processCommand("value " + tokens[i]);
                double operand = Double.parseDouble(cellValue);
                value = performMathOperation(operator, operand, value);
                i++;
            } else if (Grid.isNumber(tokens[i])) {
                // Numbers
                double operand = Double.parseDouble(tokens[i]);
                value = performMathOperation(operator, operand, value);
                i++;
            }
        }
        return value;
    }
        
    private static double[] sumAndAvg(String beginCell, String endCell) {
        int startCol = beginCell.charAt(0) - 'a';
        int endCol = endCell.charAt(0) - 'a';
        int startRow = Integer.parseInt(beginCell.substring(1)) - 1;
        int endRow = Integer.parseInt(endCell.substring(1)) - 1;
        double total = 0;
        double avg = 0;
        int cellCount = 0;
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                cellCount++;
                String cellName = (char)(j + 'a') + "" + (i + 1); // to display the cell name as a string
                String cellValue = Grid.getCellValue(cellName);
                if (!cellValue.isEmpty()) {
                    total += Double.parseDouble(cellValue); // conversion into double
                }
            }
        }
        avg = total / cellCount;
        double[] result = { total, avg };
        return result;
    }
    
    // Respective operations
    private static double performMathOperation(String operator, double operand, double value) {
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