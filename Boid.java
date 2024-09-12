import java.util.Vector;

//TODO:change velocity and acceleration into double
class Boid {
    int[] position = { (int)(Math.random()*BoidPanel.width), (int)(Math.random()*BoidPanel.height) };
    int[] velocity = { (int) Math.ceil((0.5 - Math.random()) * 5), (int) Math.ceil((0.5 - Math.random()) * 5) };

    int[] acceleration = { 0, 0 }; // By spliting a single direction into changes in X and Y axis we can better
                                   // manipulate the object
    int maxAcceleration=3;                               
    int maxVelocity = 5;

    Boid() {
    }

    Boid(int[] position, int[] velocity, int[] acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void addAcceleration(int[] acceleration){
        this.acceleration[0]=(acceleration[0]<=maxAcceleration)?acceleration[0]:(int)Math.signum(acceleration[0])*maxAcceleration;
        this.acceleration[1]=(acceleration[1]<=maxAcceleration)?acceleration[1]:(int)Math.signum(acceleration[1])*maxAcceleration;

    }
    public int[] align(Vector<Boid> boids) {
        int[] averageVelocity = { 0, 0 };
        int total = 0;
        int perceptionRadius = 100;
        for (Boid boid : boids) {
            int distance = (int) Math.hypot(this.position[0] - boid.position[0], this.position[1] - boid.position[1]);
            if (distance <= perceptionRadius && boid != this) {
                averageVelocity[0] += boid.velocity[0];
                averageVelocity[1] += boid.velocity[1];
                total++;
            }
        }
        if (total > 0) {
            averageVelocity[0] = (int)Math.ceil(averageVelocity[0] / (double)total);
            averageVelocity[1] = (int)Math.ceil(averageVelocity[1] / (double)total);
            int xAccel=Math.abs(averageVelocity[0] - this.velocity[0])<=3?averageVelocity[0] - this.velocity[0]:maxAcceleration*(int)Math.signum((double)(averageVelocity[0] - this.velocity[0]));
            int yAccel=Math.abs(averageVelocity[1] - this.velocity[1])<=3?averageVelocity[1] - this.velocity[1]:maxAcceleration*(int)Math.signum((double)(averageVelocity[1] - this.velocity[1]));
            return new int[] {xAccel,yAccel};
        }
        return averageVelocity;
    }


    public void update() {
        position[0] += velocity[0];
        position[1] += velocity[1];

        if(velocity[0] + acceleration[0]<maxVelocity)
        velocity[0] += acceleration[0];
        else
            velocity[0]=maxVelocity*(int)Math.signum((double)(velocity[0] + acceleration[0]));
        if(velocity[1] + acceleration[1]<maxVelocity)
            velocity[1] += acceleration[1];
        else
            velocity[1]=maxVelocity*(int)Math.signum((double)(velocity[1] + acceleration[1]));

    }

    // TODO:loop around the panel
}
