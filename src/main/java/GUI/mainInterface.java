package GUI;

import javax.swing.*;
import java.awt.*;

public class mainInterface extends JFrame {
    public mainInterface() {
        this.setTitle("EquaMate");
        this.setBounds(200, 200, 500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        //linear systems
        JTabbedPane linearSystemsPanel = new JTabbedPane();     
        linearSystemsPanel.addTab("Gauss/Crammer", new gaussCrammer());
        linearSystemsPanel.addTab("LU/LLT", new luLlt());
        
        JTabbedPane nonLinearSystemsPanel = new JTabbedPane();     
        nonLinearSystemsPanel.addTab("Newton Raphson", new newtonRaphson());
        nonLinearSystemsPanel.addTab("Fixed Point", new fixedPoint());

        tabbedPane.addTab("Linear systems", linearSystemsPanel);
        tabbedPane.addTab("Non linear systems", nonLinearSystemsPanel);
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        setBounds(200, 50, 900, 600);
        setVisible(true);
    }
}