package GUI.Componenets;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class equationsInput extends JPanel {
    public JTextField[] cells;
    public int dimension;

    public equationsInput(int dimension) {
        this.dimension = dimension;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        cells = new JTextField[dimension];

        for (int i = 0; i < dimension; i++) {
            JPanel equationPanel = new JPanel();
            equationPanel.setLayout(new BoxLayout(equationPanel, BoxLayout.X_AXIS));
            equationPanel.add(new JLabel("X" + (i + 1) + " = "));
            JTextField inpt = new JTextField();
            inpt.setHorizontalAlignment(SwingConstants.CENTER);
            Font boldFont = inpt.getFont().deriveFont(Font.BOLD);
            inpt.setFont(boldFont);
            inpt.setForeground(Color.BLACK);
            cells[i] = inpt;
            equationPanel.add(inpt);
            add(equationPanel);
        }
    }

    public Expression[] parseExpressions() {
        Expression[] res = new Expression[dimension];
        String[] variables = new String[dimension];
        for (int i = 0; i < dimension; i++)
            variables[i] = "x" + (i + 1);
        for (int i = 0; i < dimension; i++) {
            res[i] = new ExpressionBuilder(cells[i].getText())
                    .variables(variables)
                    .build();
        }
        return res;
    }
}
