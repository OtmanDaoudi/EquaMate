package GUI.rootFinding;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class secant extends JPanel {
    public JTextField function = new JTextField();
    public JTextField x0 = new JTextField();
    public JTextField x1 = new JTextField();
    public JTextField iterations = new JTextField();
    public JTextField error = new JTextField();
    public JTextField solution = new JTextField();
    public JButton solve = new JButton("Solve");

    public secant() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        panel1.add(errorInput);
        panel1.add(solutionOuput);
        panel1.add(solve);

        add(panel1);

        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });

        // Create a dataset to hold the points on the x-axis
        DefaultXYDataset dataset = new DefaultXYDataset();

        // Add data points to the dataset
        double[] xValues = { -5, 0, 5 };
        double[] yValues = { 0, 0, 0 };
        double[][] xyData = { xValues, yValues };
        dataset.addSeries("Points", xyData);

        // Create a chart with the function plot and x-axis points
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Function Plot", // Chart title
                "X", // X-axis label
                "Y", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                false, // Include legend
                true, // Include tooltips
                false // Include URLs
        );

        // Customize the plot
        XYPlot plot = chart.getXYPlot();

        // Highlight specific points on the x-axis
        plot.getRenderer().setSeriesPaint(0, java.awt.Color.RED);
        plot.getRenderer().setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));

        // Set the range for the x-axis
        plot.getRangeAxis().setRange(-1, 1);

        // Create a ChartPanel to display the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 100));
        add(chartPanel);
    }

    public void solve() {
        //check input fields
        double x0_, x1_, error_;
        int iterations_; 
        try
        {
            x0_ = Double.parseDouble(x0.getText());
            x1_ = Double.parseDouble(x1.getText());
            error_ = Double.parseDouble(error.getText());
            Expression f = new ExpressionBuilder(function.getText()).variable("x1").build(); 
    
            iterations_ = Integer.parseInt(iterations.getText());
            try {
                double res = Methods.rootFinding.secant.Secant(f, x0_, x1_, iterations_, error_);    
                solution.setText(res+"");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Secant diverges for this configuration.");
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "invalid input.");
        }
    }

    public void plotResult() {

    }
}