package GUI;

import net.objecthunter.exp4j.Expression;

import GUI.Componenets.equationsInput;
import GUI.Componenets.matrix;
import Methods.utilities.matrixUtilities.MatrixUtilities;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Methods.nonLinearSystems.fixedPoint.fixedPointIteration;

public class fixedPoint extends JPanel {
    public equationsInput equationsInput;
    public JPanel equationsPanel;

    public matrix initialSolutionMatrix;
    public JPanel initialSolutionPanel;

    public JSpinner iterations;

    public JTextField error;

    public matrix solution;
    public JPanel solutionPanel;

    // public fixedPoint instance;

    public fixedPoint() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fixedPoint instance = this;

        equationsPanel = new JPanel();
        equationsPanel.setLayout(new BoxLayout(equationsPanel, BoxLayout.Y_AXIS));
        equationsInput = new equationsInput(1);

        initialSolutionPanel = new JPanel();
        initialSolutionPanel.setLayout(new BoxLayout(initialSolutionPanel, BoxLayout.X_AXIS));
        initialSolutionMatrix = new matrix(1, 1, true);
        initialSolutionPanel.add(new JLabel("Initial solution : "));
        initialSolutionPanel.add(initialSolutionMatrix);

        solutionPanel = new JPanel();
        solutionPanel.setLayout(new BoxLayout(solutionPanel, BoxLayout.X_AXIS));
        solution = new matrix(1, 1, false);
        solutionPanel.add(new JLabel("Solution : "));
        solutionPanel.add(solution);

        equationsPanel.add(equationsInput);

        // add dimensions chooser
        JPanel dimensionsChooser = new JPanel(new FlowLayout());
        dimensionsChooser.setPreferredSize(new Dimension(100, 30));

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        // Add a change listener to the spinner to print the selected value
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // redraw matrix input
                instance.redraw((Integer) spinner.getValue());
            }
        });
        dimensionsChooser.add(new JLabel("Dimension : "));
        dimensionsChooser.add(spinner);

        // iterations
        JPanel iterationsPanel = new JPanel();
        iterationsPanel.setLayout(new FlowLayout());
        iterationsPanel.setPreferredSize(new Dimension(100, 30));
        iterations = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        iterationsPanel.add(new JLabel("Iterations : "));
        iterationsPanel.add(iterations);

        // error
        JPanel errorPanel = new JPanel(new FlowLayout());
        errorPanel.add(new JLabel("Error : "));
        error = new JTextField();
        errorPanel.add(error);
        error.setPreferredSize(new Dimension(150, 25));

        // solve button
        JButton solve = new JButton("Solve");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });

        add(equationsPanel);
        add(dimensionsChooser);
        add(initialSolutionPanel);
        add(iterationsPanel);
        add(errorPanel);
        add(solutionPanel);
        add(solve);
    }

    public void redraw(int newDimensions) {
        equationsPanel.remove(equationsInput);
        equationsInput = new equationsInput(newDimensions);
        equationsPanel.add(equationsInput);

        initialSolutionPanel.removeAll();
        initialSolutionMatrix = new matrix(1, newDimensions, true);
        initialSolutionPanel.add(new JLabel("Initial solution : "));
        initialSolutionPanel.add(initialSolutionMatrix);

        solutionPanel.removeAll();
        solution = new matrix(1, newDimensions, false);
        solutionPanel.add(new JLabel("Solution : "));
        solutionPanel.add(solution);

        updateUI();
    }

    public void solve() {
        Expression[] expressions;
        double[] initialGuess;
        double error;
        int iterations;
        try {
            expressions = equationsInput.parseExpressions();
            initialGuess = MatrixUtilities.toLineVector(initialSolutionMatrix.getValues());
            for (double num : initialGuess) {
                System.out.println(num);
            }
            iterations = (int) this.iterations.getValue();
            error = Double.parseDouble(this.error.getText());
            for (int i = 0; i < initialGuess.length; i++)
                System.out.print(initialGuess[i] + " ");
            System.out.println("");
            try {
                double[] solution = fixedPointIteration(expressions, initialGuess, iterations, error);
                this.solution.setValues(MatrixUtilities.toLineMatrix(solution));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fixed Point method diverges for this config");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "invalid input");
        }
    }
}
