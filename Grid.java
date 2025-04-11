// Atreyi Seal
// AP Computer Science A 
// Period 4
// Text Excel Project
// Handle help command
// Handle width, rows and cols
// Print the grid of the appropriate size
// Handle commands: row = #, col = #, width = #

import java.io.*;
import java.util.*;

/*
 * The Grid class will hold all the cells. It allows access to the cells via the
 * public methods. It will create a display String for the whole grid and process
 * many commands that update the cells. These command will include
 * sorting a range of cells and saving the grid contents to a file.
 *
 */
public class Grid extends GridBase {

    // These are called instance fields.
    // They are scoped to the instance of this Grid object.
    // Use them to keep track of the count of columns, rows and cell width.
    // They are initialized to the prescribed default values.
    // Notice that there is no "static" here.

    private int colCount = 7;
    private int rowCount = 10;
    private int cellWidth = 9;
    // : Student must create this
    // Initialize all elements of the array
    private Cell[][] matrix;
    public Grid() {
        createMatrix();
    }
    public void createMatrix () {
        matrix = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++)
            for (int j = 0; j < colCount; j++)
                matrix[i][j] = new Cell();        
    }

    /*
     * This method processes a user command.
     * 
     * Checkpoint #1 commands are: print : render a text based version of the matrix
     * width = [value] : set the cell width width : get the cell width rows =
     * [value] : set the row count cols = [value] : set the column count rows : get
     * the row count cols : get the column count
     *
     * Checkpoint #2 commands are: [cell] = [expression] : set the cell's
     * expression, for checkpoint # expressions may be... - a value such as 5.
     * Example: a2 = 5 - a string such as "hello". Example: a3 = "hello" [cell] :
     * get the cell's expression, NOT the cell's value value [cell] : get the cell
     * value expr [cell] : get the cell's expression, NOT the cell's value display
     * [cell] : get the string for how the cell wants to display itself clear :
     * empty out the entire matrix save [file] : saves to a file all the commands
     * necessary to regenerate the grid's contents
     * 
     * Create a 2D matrix of type Cell
        When the Grid is created, instantiate the matrix and populate it with an instance of type Cell.
        Parse “cell location” strings such as: A2, B4 and C1. Map these to integer values for [row][col].
        Implement the command: [cell] = “some text”
        You’ll have to create a new TextCell object and insert it into the matrix at the right [row][col].
        Implement the “[cell]” and “expr [cell]” command to display the expression of a cell. Note that in this checkpoint, NumberCells have a very simple expression… just a number! 
        Implement the “value [cell]” command to display the value of a cell (NOT the expression).
        Implement the “display [cell]” command to retrieve how a cell should be displayed in the grid.
        Update the “print” command to print a matrix showing the display of all cells instead of empty space.

     *
     * Checkpoint #3 commands are: [cell] = [expression] : where the expression is a
     * complicated formula. Example: a1 = ( 3.141 * b3 + b1 - c2 / 4 )
     *
     * Final commands are: [cell] = [expression] : where the expression may contain
     * a single function, sum or avg: Example: a1 = ( sum a1 - a3 ) Example: b1 = (
     * avg a1 - d1 ) clear [cell] : empty out a single cell. Example: clear a1 sorta
     * [range] : sort the range in ascending order. Example: sorta a1 - a5 sortd
     * [range] : sort the range in descending order. Example: sortd b1 - e1
     * 
     *
     * 
     * Parameters: command : The command to be processed. Returns : The results of
     * the command as a string to be printed by the infrastructure.
     */
    // The case where the command cannot be handled
    // Handle the command where the rows, columns, and width can be adjusted
    // Omit the equal sign and include the value of the variable
    public String processCommand(String command) {
        String[] tokens = command.split(" ");
        String result = "Unhandled";
        if (tokens[0].equalsIgnoreCase("print")) {
            result = printGrid();
        } else if (isGridSettingCommand(tokens[0])) {
            result = processGridSettingCommand(tokens);
        } else if (isCellCommand(tokens[0])) {
            result = processCellCommand(tokens);
        } else if (isExprCommand(tokens[0])) {
            result = processExprCommand(tokens);
        } else if (tokens[0].equalsIgnoreCase("display")) {
            result = processDisplayCommand(tokens);
        } else if (tokens[0].equalsIgnoreCase("value")) {
            result = processValueCommand(tokens);
        } else if (tokens[0].equalsIgnoreCase("clear")) {
            createMatrix();
            result = "Grids cleared.";
        } 
        return result;
    }
    // Determine what a cell location's components are

    // Check if a valid command is inputted
    private boolean isGridSettingCommand(String input) {
        if (input.equalsIgnoreCase("rows") 
        || input.equalsIgnoreCase("cols") 
        || input.equalsIgnoreCase("width"))
            return true;
        return false;
    }
    // Handling the situation with amount of tokens
    private String processGridSettingCommand(String[] tokens) {
        String result = "";
        if (tokens.length == 3) {
            if (tokens[1].equals("=")) {
                int value = Integer.parseInt(tokens[2]);
                updateGridSettings(tokens[0], value);
                result = tokens[2];
            }
        } else if (tokens.length == 1) {
            int value = readGridSettings(tokens[0]);
            result = String.valueOf(value);
        } else {
            result = "Invalid";
        }
        return result;
    }
    // Setters depending on input
    private int readGridSettings(String setting) {
        int value = -1;
        switch (setting) {
            case "rows":
                value = rowCount;
                break;
            case "cols":
                value = colCount;
                break;
            case "width":
                value = cellWidth;
                break;
        }
        return value;
    }
    // Value returns a double
    // Display returns a cell's contents (String.valueOf())
    // Expr takes in parentheses
    private void updateGridSettings(String setting, int value) {
        switch (setting) {
            case "rows":
                rowCount = value;
                createMatrix();
                break;
            case "cols":
                colCount = value;
                createMatrix();
                break;
            case "width":
                cellWidth = value;
                break;
        } 
    }
    private boolean isCellCommand(String input) {
        for (int i = 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private String processCellCommand(String[] tokens) {
        String result = "";
        int row, col;
        int[] cellIndices = getCellIndices(tokens[0]);
        col = cellIndices[0];
        row = cellIndices[1];
        if (tokens.length >= 3 && tokens[1].equals("=")) {
            updateCell(tokens, row, col);
        } else {
            result = readCell(row, col);
        }
        return result;
    } 
    private int[] getCellIndices(String input) {
        int[] indices = new int[2];
        int col = input.toLowerCase().charAt(0) - 'a';
        int row = Integer.parseInt(input.substring(1)) - 1;
        indices[0] = col;
        indices[1] = row;
        return indices;
    }
    private String readCell(int row, int col) {
        return matrix[row][col].getExpression();
    }
    private void updateCell(String[] tokens, int row, int col) {
        if (tokens.length < 3) {
            return;
        }
        if (tokens[2].startsWith("\"")) {
            updateTextCellExpression(tokens, row, col);
        } else {
            updateNumberCellExpression(tokens, row, col);
        }
    }
    private void updateTextCellExpression(String[] tokens, int row, int col) {        
        TextCell text = new TextCell();
        String expression = concatTokens(tokens, 2, tokens.length - 1);
        text.setExpression(expression);
        matrix[row][col] = text;
    }
    private void updateNumberCellExpression(String[] tokens, int row, int col) {
        NumberCell number = new NumberCell();
        String expression = concatTokens(tokens, 2, tokens.length - 1);
        number.setExpression(expression);
        matrix[row][col] = number;
    }
    private String processExprCommand(String[] tokens) {
        int row, col;
        int[] cellIndices = getCellIndices(tokens[1]);
        col = cellIndices[0];
        row = cellIndices[1];
        return matrix[row][col].getExpression();
    }
    private boolean isExprCommand(String input) {
        if (input.equalsIgnoreCase("expr")) {
            return true;
        }
        return false;    
    }
    public String processDisplayCommand(String[] tokens) {
        String result = "";
        int row, col;
        int[] cellIndices = getCellIndices(tokens[1]);
        col = cellIndices[0];
        row = cellIndices[1];
        result = matrix[row][col].toString();
        return result;
    }
    public String processValueCommand(String[] tokens) {
        String result = "";
        int row, col;
        int[] cellIndices = getCellIndices(tokens[1]);
        col = cellIndices[0];
        row = cellIndices[1];
        result = String.valueOf(getCellValue(row, col));
        return result;
    }
    public double getCellValue(int row, int col) {
        return matrix[row][col].getValue();
    }
    private static String concatTokens(String[] tokens, int begin, int end) {
        String result = "";
        for (int i = begin; i <= end; i++) {
            result += (tokens[i]);
            if (i < end) {
                result += " ";
            }
        }
        return result;
    }
    // Create the GRID
    // Top border: has spaces and letters (use function for ASCII values)
    // Calculate the amount of total spaces
    // Calculate the amount of spaces to the left
    // If the width is odd, there should be one less left space
    public String printGrid() {
        String result = "";
        result += "    |";
        for (int j = 1; j <= colCount; j++) {
            char letter = (char)('A' + j - 1);
            int spaces = (cellWidth - 1);
            int left_spaces = spaces / 2;
            if (spaces % 2 != 0) {
                left_spaces = (cellWidth) / 2;
            }
            
            for (int k = 1; k <= left_spaces; k++) {
                 result += " ";
            }
            result += String.format("%c", letter);
            for (int k = 1; k <= cellWidth - left_spaces - 1; k++) {
                result += " ";
            }
            result += "|";
        }
        result += "\n";

        // Print the top border
        // Borders always start with four dashes and a plus
        // Print the amount of columns (plus)
        // Print the dashes in between each dash
        // Progress onto the next line
        result += "----+";
        for (int j = 1; j <= colCount; j++) {
            for (int k = 1; k <= cellWidth; k++) {
                result += "-";
            }
            result += "+";
        }
        result += "\n";
        
        // Print the sub-grid with row numbers
        for (int i = 0; i < rowCount; i++) {
            result += String.format("%3d |", i + 1);
            for (int j = 0; j < colCount; j++) {
                if (matrix[i][j] != null) {
                    String cellData = String.format("%" + cellWidth + "s", matrix[i][j].toString());
                    
                    if (cellWidth < cellData.length()) {
                        result += cellData.substring(0, cellWidth);
                    }
                    else {
                        result += cellData;
                    }
                }
                else {
                    for (int k = 1; k <= cellWidth; k++) {
                        result += " ";
                    } 
                }
                
                result += "|";
            }
            result += "\n";

            // Print the border between sub-grid rows
            result += "----+";
            for (int j = 1; j <= colCount; j++) {
                for (int k = 1; k <= cellWidth; k++) {
                    result += "-";
                }
                result += "+";
            }
            result += "\n";
        }
        return result;
    }

        
    /**
     * saveToFile
     *
     * This method will process the command: "save {filename}"
     * <p>
     * Ask the matrix for all formulas for all non-empty cells. Empty cells should
     * not be saved.
     *
     * Save all properties such as grid size and cell width (if available)
     * 
     * @param filename is the name of the file to save
     * @return A message to user about the success/failure of saving to a file.
     */
    private String saveToFile(String filename) {
        File file = new File(filename);
        String result = "Saved to file: " + file.getAbsolutePath();

        try {
            // Get the writer ready
            PrintStream writer = new PrintStream(file);
            saveGrid(writer);
        } catch (FileNotFoundException e) {
            result = "Cannot write to the file: " + file.getAbsolutePath();
        }

        return result;
    }


    /**
     * saveGrid will save the gride to a file.
     *
     * Ask the matrix for all formulas for all non-empty cells. Empty cells should
     * not be saved.
     *
     * Save all properties such as grid size and cell width (if available)
     * 
     * @param writer is the PrintStream to print to
     */
    public void saveGrid(PrintStream writer) {
        // save the rows, cols and width
        writer.println("rows = " + rowCount);
        writer.println("cols = " + colCount);
        writer.println("width = " + cellWidth);

        // save the grid formulas, for every cell that is not empty
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                String formula = matrix[row][col].getExpression();
                if (formula != null && formula.length() > 0) {
                    writer.println("" + (char) ('A' + col) + (row + 1) + " = " + formula);
                }
            }
        }
    }
}