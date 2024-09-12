import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Polygon;
import javax.swing.JPanel;

import java.util.Arrays;
import java.util.Vector;

public class BoidPanel extends JPanel {
    Vector<Boid> boids;
    int[][] boidDimension = new int[][] { { 0, 40, 0, 10 }, { 0, 20, 40, 20 } };
    public static int width=500;
    public static int height=500;
    BoidPanel() {
        this.setPreferredSize(new Dimension(BoidPanel.width, BoidPanel.height));
    }

    BoidPanel(Vector<Boid> boids) {
        this();
        this.boids = boids;

    }

    BoidPanel(Vector<Boid> boids, int[][] boidDimension) {

        this(boids);
        this.boidDimension = boidDimension;
    }


    public int[] newPosition(int[] singleDimension, int currentXorY) {
        int[] newXorY = Arrays.copyOf(singleDimension, singleDimension.length);
        for (int index = 0; index < singleDimension.length; index++)
            newXorY[index] += currentXorY;
        return newXorY;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setStroke(new BasicStroke(1));

        for (Boid boid : boids) {
            g2D.fill(new Polygon(newPosition(boidDimension[0], boid.position[0]),
                    newPosition(boidDimension[1], boid.position[1]), 4));

            
            int[] alignmentForce=boid.align(boids);
            boid.addAcceleration(alignmentForce);
            if(boid.acceleration[0]!=0||boid.acceleration[1]!=0)
                System.out.println("X Acceleration :"+boid.acceleration[0]+" , "+"Y Acceleration: "+boid.acceleration[1]);
            boid.update();
        }

    }
}
