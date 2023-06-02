package GUI.Componenets;

import javax.swing.*;
import java.awt.*;

public class matrix extends JPanel {
    public int lines, columns;
    public JTextField[][] cells;

    public matrix(int lines, int columns, boolean isEnabled) {
        this.lines = lines;
        this.columns = columns;

        cells = new JTextField[lines][columns];

        setLayout(new GridLayout(lines, columns));

        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                JTextField newCell = new JTextField();
                cells[line][column] = newCell;
                newCell.setHorizontalAlignment(SwingConstants.CENTER);
                Font boldFont = newCell.getFont().deriveFont(Font.BOLD);
                newCell.setFont(boldFont);
                newCell.setForeground(Color.BLACK);
                add(newCell);
                newCell.setEnabled(isEnabled);
            }
        }
    }

    public double[][] getValues() {
        double[][] res = new double[lines][columns];
        for (int line = 0; line < lines; line++)
            for (int column = 0; column < columns; column++)
                res[line][column] = Double.parseDouble(cells[line][column].getText());
        return res;
    }

    public void setValues(double[][] values) {
        for (int line = 0; line < values.length; line++)
            for (int column = 0; column < values[line].length; column++)
                cells[line][column].setText("" + values[line][column]);
    }

    public double[] getValuesAsLineMatrix() {
        double[] res = new double[columns];
        for (int i = 0; i < columns; i++)
            res[i] = Double.parseDouble(cells[0][i].getText());
        return res;
    }

    public void setValuesAsLineMatrix(double[] matrix) {
        for (int column = 0; column < columns; column++)
            cells[0][column].setText("" + matrix[column]);
    }
}
