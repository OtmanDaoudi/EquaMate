package GUI;

import javax.swing.*;
import java.awt.*;

public class mainInterface extends JFrame
{
    public mainInterface()
    {
        this.setTitle("EquaMate");
        this.setBounds(200, 200, 500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        //first tab
        JPanel tab1 = new gaussCrammer();

        // second tab
        JPanel tab2 = new luLlt();

        // //third tab
        JPanel tab3 = new fixedPoint();

        // //fourth tab
        // JPanel tab4 = new newtonRaphson();

        //add tabes into the tabbed pane
        tabbedPane.addTab("Gauss/Crammer", tab1);
        tabbedPane.addTab("LU/LLT", tab2);
        tabbedPane.addTab("Fixed Point", tab3);
        // tabbedPane.addTab("Newton Raphson", tab4);

        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setBounds(200, 50, 900 , 600);
        this.setVisible(true);
    }
}