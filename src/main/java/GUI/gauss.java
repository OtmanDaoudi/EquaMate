package GUI;

import GUI.Componenets.matricesInput;
import GUI.Componenets.solutionHeader;
import Methods.linearSystems.gaussianElimination;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gauss extends JPanel {
    public matricesInput matricesInput;
    public JPanel matricesContainer; // a wrapper for the above component, used to preserve the old place
    public solutionHeader solutionMatrix;
    public JPanel solutionPanel;
    public JComboBox<String> method;
    public gauss instance;
    public JSpinner spinner; 

    public gauss() {
        instance = this;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //add padding

        matricesContainer = new JPanel();
        matricesContainer.setPreferredSize(new Dimension(0, 200)); // prevents size change while redrawing
        matricesContainer.setLayout(new BoxLayout(matricesContainer, BoxLayout.X_AXIS));
        matricesInput = new matricesInput(2);

        matricesContainer.add(matricesInput);

        this.add(matricesContainer);

        // add dimensions chooser
        JPanel dimensionsChooser = new JPanel(new FlowLayout());
        dimensionsChooser.setPreferredSize(new Dimension(100, 30));

        SpinnerNumberModel model = new SpinnerNumberModel(2, 2, Integer.MAX_VALUE, 1);
        spinner = new JSpinner(model);
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

        dimensionsChooser.add(new JLabel("Dimensions: "));
        dimensionsChooser.add(spinner);
        this.add(dimensionsChooser);

        // solution matrix
        solutionPanel = new JPanel(); // wrapper
        solutionPanel.setLayout(new FlowLayout());
        solutionMatrix = new solutionHeader(2, false, "Solution : ");
        solutionPanel.add(solutionMatrix);
        this.add(solutionPanel);

        // method chooser
        JPanel methodList = new JPanel(new FlowLayout());
        methodList.setPreferredSize(new Dimension(0, 30));
        methodList.add(new JLabel("Method: "));

        String[] methods = { "Gauss", "Crammer" };
        method = new JComboBox<>(methods);
        methodList.add(method, BorderLayout.CENTER);

        // solve btn
        JButton solveBtn = new JButton("Solve");
        methodList.add(solveBtn);

        this.add(methodList);
        solveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });
    }

    public void redraw(int newDim) {
        matricesContainer.remove(matricesInput);
        matricesInput = new matricesInput(newDim);
        matricesContainer.add(matricesInput);

        solutionPanel.remove(solutionMatrix);
        solutionMatrix = new solutionHeader(newDim, false, "Solution : ");
        solutionPanel.add(solutionMatrix);

        this.updateUI();
    }

    public void solve() {
        try {
            if (((String) method.getSelectedItem()).equals("Gauss")) {
                double[] res = gaussianElimination.gaussianEliminationMathod(matricesInput.getA(),
                        matricesInput.getB());
                solutionMatrix.setValues(res);
            } else {
                System.out.println("crammer");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Warning: Check your input", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}