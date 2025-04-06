// Atreyi Seal
// Period 4
// AP Computer Science A
// Eliminate quotes when necessary using substring method


// This class highly leverages inheritance and requires very little code.
public class TextCell extends Cell {

  /*
  *  This will return the string for how a TextCell wants to display
  *  itself. All it needs to do is remove the bounding quotes from
  *  the expression.
  */
  public String toString() {
      // : Get the expression and remove the quotes
      // return getExpression();
      String expression = getExpression();
      return expression.substring(1,expression.length()-1);
    }
}
