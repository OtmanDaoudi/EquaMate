package GUI;

import GUI.Componenets.matrix;
import Methods.linearSystems.luDecomposition;
import Methods.utilities.matrixUtilities.MatrixUtilities;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class luLlt extends JPanel {
    public matrix A, B;
    public JPanel matricesContainer; // a wrapper for the above component, used to preserve the old place

    public matrix solution;
    public JPanel solutionPanel;

    public matrix L, U;
    public JPanel lPanel, uPanel;

    public JComboBox<String> method;

    public luLlt instance;

    public luLlt() {
        instance = this;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        matricesContainer = new JPanel();
        matricesContainer.setPreferredSize(new Dimension(0, 200)); // prevents size change while redrawing
        matricesContainer.setLayout(new BoxLayout(matricesContainer, BoxLayout.X_AXIS));
        A = new matrix(2, 2, true);
        B = new matrix(2, 1, true);
        matricesContainer.add(new JLabel("A: "));
        matricesContainer.add(A);
        matricesContainer.add(new JLabel("B: "));
        matricesContainer.add(B);
        add(matricesContainer);

        // add dimensions chooser
        JPanel dimensionsChooser = new JPanel(new FlowLayout());
        dimensionsChooser.setPreferredSize(new Dimension(100, 30));
        SpinnerNumberModel model = new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // redraw matrix input
                instance.redraw((Integer) spinner.getValue());
            }
        });

        dimensionsChooser.add(new JLabel("Dimensions: "));
        dimensionsChooser.add(spinner);
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
        // methodList.setPreferredSize(new Dimension(0, 50));
        methodList.add(new JLabel("Method: "));
        String[] methods = { "LU", "LLt" };
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

        // L & U matrices
        JPanel luPanel = new JPanel();
        luPanel.setLayout(new BoxLayout(luPanel, BoxLayout.X_AXIS));
        luPanel.add(new JLabel("L: "));
        lPanel = new JPanel();
        lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.X_AXIS));
        L = new matrix(2, 2, false);
        lPanel.add(L);
        luPanel.add(lPanel);
        luPanel.add(new JLabel("U: "));
        uPanel = new JPanel();
        uPanel.setLayout(new BoxLayout(uPanel, BoxLayout.X_AXIS));
        U = new matrix(2, 2, false);
        uPanel.add(U);
        luPanel.add(uPanel);
        add(luPanel);
        luPanel.setPreferredSize(new Dimension(100, 150));
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

        lPanel.remove(L);
        L = new matrix(newDim, newDim, false);
        lPanel.add(L);

        uPanel.remove(U);
        U = new matrix(newDim, newDim, false);
        uPanel.add(U);

        updateUI();
    }

    public void solve() {
        try {
            if(((String)method.getSelectedItem()).equals("LU"))
            {
                HashMap<String, Object> res = luDecomposition.LU(A.getValues(), MatrixUtilities.toVector(B.getValues()));
                solution.setValues(MatrixUtilities.toLineMatrix((double[]) res.get("result")));
                // set L and U
                L.setValues((double[][]) res.get("L"));
                U.setValues((double[][]) res.get("U"));
            }
            else
            {
                System.out.println("LLt");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Warning: Check your input", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
