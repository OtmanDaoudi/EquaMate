package GUI;

import javax.swing.*;

import GUI.linearSystems.gaussCrammer;
import GUI.linearSystems.luLlt;
import GUI.nonLinearSystems.fixedPoint;
import GUI.nonLinearSystems.newtonRaphson;
import GUI.rootFinding.secant;

import java.awt.*;

public class mainInterface extends JFrame {
    public mainInterface() {
        setTitle("EquaMate");
        setBounds(200, 200, 500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        //linear systems
        JTabbedPane linearSystemsPanel = new JTabbedPane();     
        linearSystemsPanel.addTab("Gauss/Crammer", new gaussCrammer());
        linearSystemsPanel.addTab("LU/LLT", new luLlt());
        
        JTabbedPane nonLinearSystemsPanel = new JTabbedPane();     
        nonLinearSystemsPanel.addTab("Newton Raphson", new newtonRaphson());
        nonLinearSystemsPanel.addTab("Fixed Point", new fixedPoint());

        JTabbedPane rootFindingPanel = new JTabbedPane();     
        rootFindingPanel.addTab("Secant", new secant());

        tabbedPane.addTab("Linear systems", linearSystemsPanel);
        tabbedPane.addTab("Non linear systems", nonLinearSystemsPanel);
        tabbedPane.addTab("Root finding", rootFindingPanel);
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        setBounds(200, 50, 900, 600);
        setVisible(true);
    }
}