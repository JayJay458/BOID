class Boid {
    int[] position = { 200, 200 };
    int[] velocity = { (int) ((0.5 - Math.random()) * 5), (int) ((0.5 - Math.random()) * 5) };

    int[] acceleration = { 0, 0 }; // By spliting a single direction into changes in X and Y axis we can better
                                   // manipulate the object
    int maxVelocity = 5;

    Boid() {
    }

    Boid(int[] position, int[] velocity, int[] acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public void update() {
        position[0] += velocity[0];
        position[1] += velocity[1];
        velocity[0] += acceleration[0];
        velocity[1] += acceleration[1];

    }

    // TODO:loop around the panel
}
