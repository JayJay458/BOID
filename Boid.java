import java.util.Vector;

class Boid {
    int[] position = { (int) (Math.random() * BoidPanel.width), (int) (Math.random() * BoidPanel.height) };
    double maxVelocity = 4;
    double[] velocity = { Math.ceil((0.5 - Math.random()) * maxVelocity),
            Math.ceil((0.5 - Math.random()) * maxVelocity) };

    double[] acceleration = { 0, 0 }; // By spliting a single direction into changes in X and Y axis we can better
                                      // manipulate the object
    double maxAcceleration = 1;

    Boid() {
    }

    Boid(int[] position, double[] velocity, double[] acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void addAcceleration(double[] acceleration) {
        this.acceleration[0] += acceleration[0];
        this.acceleration[1] += acceleration[1];

    }

    public double[] align(Vector<Boid> boids) { // TODO: this function such spit out direction only
        double[] averageVelocity = { 0, 0 };
        int total = 0;
        double perceptionRadius = 100;
        for (Boid boid : boids) {
            double distance = Math.hypot(this.position[0] - boid.position[0], this.position[1] - boid.position[1]);
            if (distance <= perceptionRadius && boid != this) {
                averageVelocity[0] += boid.velocity[0];
                averageVelocity[1] += boid.velocity[1];
                total++;
            }
        }
        if (total > 0) {
            averageVelocity[0] /= total;
            averageVelocity[1] /= total;

            double xAccel = 0, yAccel = 0;
            double currnetMag = Math.hypot(averageVelocity[0], averageVelocity[1]);
            if (currnetMag != 0) {
                xAccel = averageVelocity[0] / currnetMag * maxVelocity;
                yAccel = averageVelocity[1] / currnetMag * maxVelocity;
            }
            xAccel -= this.velocity[0];
            yAccel -= this.velocity[1];
            xAccel = limitToMax(xAccel, maxAcceleration);
            yAccel = limitToMax(yAccel, maxAcceleration);

            return new double[] { xAccel, yAccel };
        }
        return averageVelocity;
    }

    public double[] cohesion(Vector<Boid> boids) { 
        int[] centerOfMass = {0,0};
        int total = 0;
        double perceptionRadius = 100;
        for (Boid boid : boids) {
            double distance = Math.hypot(this.position[0] - boid.position[0], this.position[1] - boid.position[1]);
            if (distance <= perceptionRadius && boid != this) {
                centerOfMass[0] += boid.position[0];
                centerOfMass[1] += boid.position[1];
                total++;
            }
        }
        if (total > 0) {
            centerOfMass[0] /= total;
            centerOfMass[1] /= total;
            centerOfMass[0] -= this.position[0];
            centerOfMass[1] -= this.position[1];
            double currnetMag = Math.hypot(centerOfMass[0], centerOfMass[1]);
            double xAccel = 0, yAccel = 0;

            if (currnetMag != 0) {
                xAccel = centerOfMass[0] / currnetMag * maxVelocity;
                yAccel = centerOfMass[1] / currnetMag * maxVelocity;
            }
            xAccel -= this.velocity[0];
            yAccel -= this.velocity[1];
            xAccel = limitToMax(xAccel, maxAcceleration);
            yAccel = limitToMax(yAccel, maxAcceleration);

            return new double[] { xAccel, yAccel };

        }
        return new double[] {0,0};
    }

    public double[] separation(Vector<Boid> boids) { 
        double[] separationForce = {0,0};
        double perceptionRadius = 50;
        int total = 0;
        for (Boid boid : boids) {
            double distance = Math.hypot(this.position[0] - boid.position[0], this.position[1] - boid.position[1]);
            if (distance <= perceptionRadius && boid != this) {
                separationForce[0] += (this.position[0]-boid.position[0])/distance/distance;
                separationForce[1] += (this.position[1]-boid.position[1])/distance/distance;

                total++;
            }
        }
        if (total > 0) {
            separationForce[0] /= total;
            separationForce[1] /= total;

            double currnetMag = Math.hypot(separationForce[0], separationForce[1]);
            double xAccel = 0, yAccel = 0;

            if (currnetMag != 0) {
                xAccel = separationForce[0] / currnetMag * maxVelocity;
                yAccel = separationForce[1] / currnetMag * maxVelocity;
            }
            xAccel -= this.velocity[0];
            yAccel -= this.velocity[1];
            xAccel = limitToMax(xAccel, maxAcceleration);
            yAccel = limitToMax(yAccel, maxAcceleration);

            return new double[] { xAccel, yAccel };

        }
        return new double[] {0,0};
    }

    public void updatePosition() {
        position[0] += (int) Math.round(velocity[0]);
        if (position[0] >= 0)
            position[0] %= BoidPanel.width;
        else {
            position[0] = BoidPanel.width + position[0];
        }
        position[1] += (int) Math.round(velocity[1]);
        if (position[1] >= 0)
            position[1] %= BoidPanel.height;
        else {
            position[1] = BoidPanel.height + position[1];
        }

    }

    public double limitToMax(double value, double maximum) {
        if (Math.abs(value) > maximum)
            return Math.signum(value) * maximum;
        else
            return value;
    }

    public void update() {
        updatePosition();

        if (velocity[0] == 0 && velocity[1] == 0) {
            acceleration[0] = Math.floor(Math.random() * maxAcceleration + 1);
            acceleration[1] = Math.floor(Math.random() * maxAcceleration + 1);
            System.out.println("x Acceleration: "+acceleration[0]+" y Acceleration: "+acceleration[1]);

        }
        velocity[0] += acceleration[0];
        velocity[1] += acceleration[1];
        velocity[0] = limitToMax(velocity[0], maxVelocity);
        velocity[1] = limitToMax(velocity[1], maxVelocity);
        acceleration[0] = 0;
        acceleration[1] = 0;
    }

    // TODO:loop around the panel
}
