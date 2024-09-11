import java.util.Vector;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class Boids extends JFrame {
    Vector<Boid> boids = new Vector<Boid>();

    int[][] boidDimension = new int[][] { { 0, 20, 0, 5 }, { 0, 10, 20, 10 } };
    BoidPanel boidPanel;

    Boids() {
        for (int index = 0; index < 10; index++) {
            boids.add(new Boid());
        }
        boidPanel = new BoidPanel(boids, boidDimension);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(boidPanel);
        this.pack();
        ActionListener listener = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                boidPanel.removeAll();
                boidPanel.updateUI();
                boidPanel.repaint();
            }
        };
        Timer timer = new Timer(100, listener);
        timer.start();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
