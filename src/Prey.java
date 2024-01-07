import java.util.List;

/**
 * The Prey class represents a prey animal in a simulation.
 * It extends the Animal class, adding specific behaviors and attributes
 * such as aging, breeding, and movement strategies.
 */
public class Prey extends Animal {
    private static final int CORNER_TIME_LIMIT = 3;
    private static final int CORNER_THRESHOLD = 15;

    private int age;
    private int cornerTimer;

    /**
     * Constructs a new Prey instance with a random initial age.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Prey(int x, int y) {
        super(x, y);
        this.age = (int) (Math.random() * Animal.MAXAGE);
        this.cornerTimer = 0;
    }

    /**
     * Increments the age of the prey and checks if it has reached its maximum age.
     */
    public void increaseAge() {
        age++;
        if (age >= Animal.MAXAGE) {
            setIsDead(true);
        }
    }

    /**
     * Determines if the prey can breed based on its age and a random chance.
     *
     * @return A new Prey object if breeding occurs, otherwise null.
     */
    public Prey breed() {
        int chancesOfBreed = (int) (Math.random() * 100);
        if (age >= Animal.DUPLICATE && chancesOfBreed >= 60) {
            return new Prey((int) (Math.random() * getX()), (int) (Math.random() * getY()));
        }
        return null;
    }

    /**
     * Moves the prey based on its proximity to predators and its position relative to map corners.
     *
     * @param maxX      The maximum x-bound of the map.
     * @param maxY      The maximum y-bound of the map.
     * @param predators A list of predators currently in the simulation.
     */
    public void move(int maxX, int maxY, List<Predator> predators) {
        Predator nearestPredator = findNearestPredator(predators);
        int deltaX = calculateDelta(nearestPredator, maxX, 'x');
        int deltaY = calculateDelta(nearestPredator, maxY, 'y');

        setX(Math.max(10, Math.min(getX() + deltaX, maxX-10)));
        setY(Math.max(10, Math.min(getY() + deltaY, maxY-30)));
    }

    /**
     * Finds the nearest predator to this prey.
     *
     * @param predators A list of predators.
     * @return The nearest predator object, or null if none are nearby.
     */
    private Predator findNearestPredator(List<Predator> predators) {
        Predator nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Predator predator : predators) {
            double distance = calculateDistance(predator);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = predator;
            }
        }
        return nearest;
    }

    /**
     * Calculates the distance to a given predator.
     *
     * @param predator The predator to measure distance from.
     * @return The distance to the predator.
     */
    private double calculateDistance(Predator predator) {
        return Math.sqrt(Math.pow(predator.getX() - getX(), 2) + Math.pow(predator.getY() - getY(), 2));
    }

    /**
     * Calculates the movement delta value based on predator proximity and corner avoidance.
     *
     * @param nearestPredator The nearest predator.
     * @param maxBound        The maximum bound in the current dimension.
     * @param axis            The axis of movement ('x' or 'y').
     * @return The delta value for movement in the specified axis.
     */
    private int calculateDelta(Predator nearestPredator, int maxBound, char axis) {
        int delta = 0;
        int currentPosition = (axis == 'x') ? getX() : getY();
        int randomFactor = (int) (Math.random() * 3) - 1; // -1, 0, or 1 for random movement

        if (nearestPredator != null) {
            // Increase speed when predator is near
            int speedFactor = (calculateDistance(nearestPredator) < 20) ? 2 : 1;
            int evadeDirection = (axis == 'x') ?
                    ((nearestPredator.getX() > currentPosition) ? -1 : 1) :
                    ((nearestPredator.getY() > currentPosition) ? -1 : 1);
            delta = evadeDirection * speedFactor + randomFactor; // Evasion with speed variation
        } else {
            delta = randomFactor; // Random movement when no predator is near
        }

        // Corner avoidance logic
        if (isInCorner(getX(), getY(), maxBound, maxBound)) {
            cornerTimer++;
            if (cornerTimer > CORNER_TIME_LIMIT) {
                delta = moveTowardsCenter(delta, currentPosition, maxBound);
            }
        } else {
            cornerTimer = 0; // Reset corner timer
        }

        return delta;
    }

    /**
     * Checks if the prey is currently in a corner of the map.
     *
     * @param x     The current x-coordinate.
     * @param y     The current y-coordinate.
     * @param maxX  The maximum x-coordinate.
     * @param maxY  The maximum y-coordinate.
     * @return True if the prey is in a corner, false otherwise.
     */
    private boolean isInCorner(int x, int y, int maxX, int maxY) {
        return (x+10 < CORNER_THRESHOLD || x > (maxX-10) - CORNER_THRESHOLD) &&
                (y+10 < CORNER_THRESHOLD || y > (maxY-30) - CORNER_THRESHOLD);
    }

    /**
     * Adjusts the movement direction towards the center of the map if in a corner.
     *
     * @param delta         The current movement delta.
     * @param position      The current position.
     * @param maxPosition   The maximum position.
     * @return Adjusted delta to move towards the map center.
     */
    private int moveTowardsCenter(int delta, int position, int maxPosition) {
        if (position < maxPosition / 2) {
            return Math.max(delta, 1);
        } else {
            return Math.min(delta, -1);
        }
    }
}
