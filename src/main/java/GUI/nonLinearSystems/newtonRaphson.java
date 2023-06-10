package GUI.nonLinearSystems;

import GUI.Componenets.equationsInput;
import GUI.Componenets.jacobi;
import GUI.Componenets.matrix;
import Methods.nonLinearSystems.newtonRaphsonMathod;
import net.objecthunter.exp4j.Expression;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newtonRaphson extends JPanel {
    public equationsInput equationsInput;
    public JPanel equationsPanel;

    public matrix initialSolution;
    public JPanel initialSolutionPanel;
    public JPanel initialSolutionMatrixPanel;

    public JSpinner iterations;

    public JTextField error;

    public matrix solution;
    public JPanel solutionPanel;
    public JPanel solutionMatrixPanel;

    public jacobi jacobi;
    public JPanel jacobiPanel;
    public JPanel jacobiMatrixPanel;

    public newtonRaphson() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        equationsPanel = new JPanel();
        equationsPanel.setLayout(new BoxLayout(equationsPanel, BoxLayout.Y_AXIS));
        equationsInput = new equationsInput(1);
        equationsPanel.add(equationsInput);

        initialSolutionPanel = new JPanel();
        initialSolutionPanel.setLayout(new FlowLayout());
        initialSolutionPanel.add(new JLabel("Initial solution : "));
        initialSolution = new matrix(1, 2, true);
        initialSolution.setPreferredSize(new Dimension(600, 25));
        initialSolutionMatrixPanel = new JPanel();
        initialSolutionMatrixPanel.setLayout(new BoxLayout(initialSolutionMatrixPanel, BoxLayout.X_AXIS));
        initialSolutionMatrixPanel.setPreferredSize(new Dimension(600, 25));
        initialSolutionMatrixPanel.add(initialSolution);
        initialSolutionPanel.add(initialSolutionMatrixPanel);

        solutionPanel = new JPanel();
        solutionPanel.setLayout(new FlowLayout());
        solutionPanel.add(new JLabel("Solution : "));

        solutionMatrixPanel = new JPanel();
        solutionMatrixPanel.setLayout(new BoxLayout(solutionMatrixPanel, BoxLayout.X_AXIS));
        solutionMatrixPanel.setPreferredSize(new Dimension(600, 25));
        solution = new matrix(1, 2, false);
        solutionMatrixPanel.add(solution);
        solutionPanel.add(solutionMatrixPanel);

        // jacobi
        jacobiPanel = new JPanel();
        jacobiPanel.setLayout(new FlowLayout());
        jacobiPanel.add(new JLabel("Jacobi : "));
        jacobiMatrixPanel = new JPanel();
        jacobiMatrixPanel.setLayout(new BoxLayout(jacobiMatrixPanel, BoxLayout.X_AXIS));
        jacobi = new jacobi(1);
        jacobiMatrixPanel.add(jacobi);
        jacobiMatrixPanel.setPreferredSize(new Dimension(600, 100));
        jacobiPanel.add(jacobiMatrixPanel);

        // add dimensions chooser
        JPanel dimensionsChooser = new JPanel(new FlowLayout());
        dimensionsChooser.setPreferredSize(new Dimension(100, 30));
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // redraw matrix input
                redraw((Integer) spinner.getValue());
            }
        });
        dimensionsChooser.add(new JLabel("Dimension : "));
        dimensionsChooser.add(spinner);

        // iterations
        JPanel iterationsPanel = new JPanel();
        iterationsPanel.setLayout(new FlowLayout());
        iterationsPanel.setPreferredSize(new Dimension(100, 25));
        iterations = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        iterationsPanel.add(new JLabel("Iterations : "));
        iterationsPanel.add(iterations);

        // error
        JPanel errorPanel = new JPanel(new FlowLayout());
        errorPanel.add(new JLabel("Error : "));
        error = new JTextField();
        error.setHorizontalAlignment(SwingConstants.CENTER);
        Font boldFont = error.getFont().deriveFont(Font.BOLD);
        error.setFont(boldFont);
        error.setForeground(Color.BLACK);
        error.setPreferredSize(new Dimension(100, 25));
        errorPanel.add(error);

        // solve button
        JButton solve = new JButton("Solve");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });

        add(equationsPanel);
        add(jacobiPanel);
        add(dimensionsChooser);
        add(iterationsPanel);
        add(errorPanel);
        add(initialSolutionPanel);
        add(solutionPanel);
        add(solve);
    }

    public void redraw(int newDimensions) {
        equationsPanel.remove(equationsInput);
        equationsInput = new equationsInput(newDimensions);
        equationsPanel.add(equationsInput);

        initialSolutionMatrixPanel.removeAll();
        initialSolution = new matrix(1, newDimensions, true);
        initialSolutionMatrixPanel.add(initialSolution);

        solutionMatrixPanel.removeAll();
        solution = new matrix(1, newDimensions, false);
        solutionMatrixPanel.add(solution);

        jacobiMatrixPanel.removeAll();
        jacobi = new jacobi(newDimensions);
        jacobiMatrixPanel.add(jacobi);

        updateUI();
    }

    public void solve() {
        Expression[] expressions;
        Expression[][] jacobi;
        double[] initialGuess;
        double error;
        int iterations;
        try {
            initialGuess = initialSolution.getValuesAsLineVector();
            expressions = equationsInput.parseExpressions();
            jacobi = this.jacobi.parseExpressions();
            iterations = (int) this.iterations.getValue();
            error = Double.parseDouble(this.error.getText());
            try {
                double[] solution = newtonRaphsonMathod.newton(expressions, initialGuess, jacobi, iterations, error);
                this.solution.setValuesAsLineMatrix(solution);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Newton-raphson method diverges for this config");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "invalid input");
        }
    }
}