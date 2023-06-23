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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class secant extends JPanel {
    public JTextField function = new JTextField();
    public JTextField x0 = new JTextField();
    public JTextField x1 = new JTextField();
    public JTextField iterations = new JTextField();
    public JTextField error = new JTextField();
    public JTextField solution = new JTextField();
    public JButton solve = new JButton("Solve");
    public ChartPanel chartPanel;

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
        functionPanel.add(new JLabel("F(x): "));
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

        // Create a chart with the function plot and x-axis points
        JFreeChart chart = ChartFactory.createXYLineChart(
                null, // Chart title
                "X", // X-axis label
                "Y", // Y-axis label
                null, // Dataset
                PlotOrientation.VERTICAL,
                false, // Include legend
                true, // Include tooltips
                false // Include URLs
        );

        // Create a ChartPanel to display the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 100));
        add(chartPanel);
    }

    public void solve() {
        // check input fields
        double x0_, x1_, error_;
        int iterations_;
        try {
            x0_ = Double.parseDouble(x0.getText());
            x1_ = Double.parseDouble(x1.getText());
            error_ = Double.parseDouble(error.getText());
            Expression f = new ExpressionBuilder(function.getText()).variable("x").build();
            iterations_ = Integer.parseInt(iterations.getText());

            try {
                if (f.setVariable("x", x0_).evaluate() * f.setVariable("x", x1_).evaluate() > 0) {
                    JOptionPane.showMessageDialog(this, "the function has no roots in the specified interval or is not strictly monotonic.");
                    return;
                }
                double res = Methods.rootFinding.secant.Secant(f, x0_, x1_, iterations_, error_);
                solution.setText(res + "");
                try {
                    plotChart(f, x0_, x1_, res);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error drawing the chart");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Secant diverges for this configuration.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "invalid input.");
        }
    }

    public void plotChart(Expression f, double a, double b, double root) {
        // Create dataset for the function plot
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] functionData = calculateFunctionData(f, a - 5, b + 5);
        dataset.addSeries("Function", functionData);

        // Create dataset for the highlighted points
        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Points");
        series.add(a, 0);
        series.add(b, 0);
        series.add(root, 0);
        seriesCollection.addSeries(series);

        // Create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        // Customize the chart
        XYPlot plot = chart.getXYPlot();
        plot.setDataset(1, seriesCollection);
        plot.mapDatasetToRangeAxis(1, 0);
        plot.setRenderer(1, new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer());
        plot.getDomainAxis().setRange(a - 1, b + 1);
        plot.getRangeAxis().setRange(-10, 10);

        // Show y-axis (x=0)
        plot.setDomainZeroBaselineVisible(true);

        // Display the chart in a Swing window
        chartPanel.setChart(chart);
        chartPanel.restoreAutoRangeBounds();
    }

    private double[][] calculateFunctionData(Expression f, double a, double b) {
        int numPoints = 100; // Number of data points
        double[][] data = new double[2][numPoints];

        double step = (b - a) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            double x = a + (i * step);
            double y = f.setVariable("x", x).evaluate(); // Use your function calculation method
            if (x < a || x > b) {
                y = Double.NaN; // Set y-value to NaN (not-a-number) if x is outside the interval
            }
            data[0][i] = x;
            data[1][i] = y;
        }
        return data;
    }

}