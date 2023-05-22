package GUI;

import GUI.Componenets.matrix;
import Methods.linearSystems.crammer;
import Methods.linearSystems.gaussianElimination;
import Methods.utilities.matrixUtilities.MatrixUtilities;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gaussCrammer extends JPanel {
    public matrix A, B;
    public JPanel matricesContainer; // a wrapper for the above component, used to preserve the old place

    public matrix solution;
    public JPanel solutionPanel;

    public JComboBox<String> method;
    public gaussCrammer instance;

    public gaussCrammer() {
        instance = this;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding

        matricesContainer = new JPanel();
        matricesContainer.setPreferredSize(new Dimension(0, 200)); // prevents size change while redrawing
        matricesContainer.setLayout(new BoxLayout(matricesContainer, BoxLayout.X_AXIS));
        A = new matrix(2, 2, true);
        B = new matrix(2, 1, true);
        matricesContainer.add(new JLabel("A: "));
        matricesContainer.add(A);
        matricesContainer.add(new JLabel(" B: "));
        matricesContainer.add(B);
        add(matricesContainer);

        // add dimensions chooser
        JPanel dimensionsChooser = new JPanel(new FlowLayout());
        dimensionsChooser.setPreferredSize(new Dimension(100, 30));
        SpinnerNumberModel model = new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1);
        JSpinner dimensionSpinner = new JSpinner(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(dimensionSpinner);
        dimensionSpinner.setEditor(editor);
        // Add a change listener to the spinner to print the selected value
        dimensionSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // redraw input matrices
                instance.redraw((Integer) dimensionSpinner.getValue());
            }
        });
        dimensionsChooser.add(new JLabel("Dimensions: "));
        dimensionsChooser.add(dimensionSpinner);
        add(dimensionsChooser);

        // solution matrix
        solutionPanel = new JPanel(); // wrapper
        solutionPanel.setLayout(new FlowLayout());
        solution = new matrix(1, 2, false);
        solution.setPreferredSize(new Dimension(600, 25));
        solutionPanel.add(new JLabel("Solution: "));
        solutionPanel.add(solution);
        add(solutionPanel);

        // method chooser
        JPanel methodList = new JPanel(new FlowLayout());
        methodList.setPreferredSize(new Dimension(0, 30));
        methodList.add(new JLabel("Method: "));
        String[] methods = { "Gauss", "Crammer" };
        method = new JComboBox<>(methods);
        methodList.add(method, BorderLayout.CENTER);

        // Solve button
        JButton solveBtn = new JButton("Solve");
        methodList.add(solveBtn);

        add(methodList);
        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });
    }

    public void redraw(int newDim) {
        matricesContainer.removeAll();
        A = new matrix(newDim, newDim, true);
        B = new matrix(newDim, 1, true);
        matricesContainer.add(new JLabel("A: "));
        matricesContainer.add(A);
        matricesContainer.add(new JLabel("B: "));
        matricesContainer.add(B);

        solutionPanel.remove(solution);
        solution = new matrix(1, newDim, false);
        solutionPanel.add(solution);
        solution.setPreferredSize(new Dimension(600, 25));

        updateUI();
    }

    public void solve() {
        try {
            double[] res;
            if (((String) method.getSelectedItem()).equals("Gauss"))
                res = gaussianElimination.gaussianEliminationMathod(A.getValues(),
                        MatrixUtilities.toVector(B.getValues()));
            else
                res = crammer.crammerMethod(A.getValues(), MatrixUtilities.toVector(B.getValues()));
            solution.setValues(MatrixUtilities.toLineMatrix(res));
        } catch (Exception e) {
            if(e.getMessage().equals("Determinant error")) 
                JOptionPane.showMessageDialog(this, "Warning: det(A) = 0", "Warning", JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Warning: Check your input", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}