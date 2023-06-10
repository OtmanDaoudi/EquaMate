package GUI.rootFinding;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.*;

public class secant extends JPanel {
    public JTextField function = new JTextField();
    public JTextField x0 = new JTextField();
    public JTextField x1 = new JTextField();
    public JTextField iterations = new JTextField();
    public JTextField error = new JTextField();
    public JTextField solution = new JTextField();
    public JButton solve = new JButton("Solve");

    public secant() {
        setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // font + align center
        Font boldFont = error.getFont().deriveFont(Font.BOLD);

        function.setHorizontalAlignment(SwingConstants.CENTER);
        function.setFont(boldFont);
        function.setForeground(Color.BLACK);

        x0.setHorizontalAlignment(SwingConstants.CENTER);
        x0.setFont(boldFont);
        x0.setForeground(Color.BLACK);

        x1.setHorizontalAlignment(SwingConstants.CENTER);
        x1.setFont(boldFont);
        x1.setForeground(Color.BLACK);

        iterations.setHorizontalAlignment(SwingConstants.CENTER);
        iterations.setFont(boldFont);
        iterations.setForeground(Color.BLACK);

        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setFont(boldFont);
        error.setForeground(Color.BLACK);

        solution.setHorizontalAlignment(SwingConstants.CENTER);
        solution.setFont(boldFont);
        solution.setForeground(Color.BLACK);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        // function input
        JPanel functionPanel = new JPanel();
        functionPanel.setLayout(new FlowLayout());
        functionPanel.add(new JLabel("F(x1): "));
        functionPanel.add(function);
        function.setPreferredSize(new Dimension(400, 30));

        // interval input
        JPanel intervalInput = new JPanel(new FlowLayout());
        intervalInput.add(new JLabel("X0 : "));
        intervalInput.add(x0);
        x0.setPreferredSize(new Dimension(100, 30));
        intervalInput.add(new JLabel("  X1 : "));
        intervalInput.add(x1);
        x1.setPreferredSize(new Dimension(100, 30));
        // intervalInput.setPreferredSize(new Dimension(600, 30));

        // iterations input
        JPanel iterationsInput = new JPanel(new FlowLayout());
        iterationsInput.add(new JLabel("Iterations: "));
        iterationsInput.add(iterations);
        iterations.setPreferredSize(new Dimension(100, 30));

        // error input
        JPanel errorInput = new JPanel(new FlowLayout());
        errorInput.add(new JLabel("Error: "));
        errorInput.add(error);
        error.setPreferredSize(new Dimension(100, 30));

        // solution
        JPanel solutionOuput = new JPanel(new FlowLayout());
        solutionOuput.add(new JLabel("Solution: "));
        solution.setEnabled(false);
        solutionOuput.add(solution);
        solution.setPreferredSize(new Dimension(400, 30));

        panel1.add(functionPanel);
        panel1.add(intervalInput);
        panel1.add(iterationsInput);
        panel1.add(solutionOuput);
        panel1.add(solve);

        add(panel1);

        //setting up graphing panel
        
        add(panel2);
    }
}