import java.util.List;

/**
 * Represents a predator in a wildlife simulation.
 * Extends the Animal class, with additional attributes and behaviors specific to predators.
 */
public class Predator extends Animal {
    private static final int EATING_RANGE = 10;

    private int age;
    private Prey lastTargetedPrey;

    /**
     * Constructs a Predator with a random initial age.
     *
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public Predator(int x, int y) {
        super(x, y);
        this.age = (int) (Math.random() * Animal.PREDATORMAXAGE);
        this.lastTargetedPrey = null;
    }

    /**
     * Increments the age of the predator and checks if it has reached its maximum age.
     */
    public void increaseAge() {
        age++;
        if (age >= Animal.PREDATORMAXAGE) {
            setIsDead(true);
        }
    }

    /**
     * Determines if the predator can breed based on its age and a random chance.
     *
     * @return A new Predator object if breeding occurs, otherwise null.
     */
    public Predator breed() {
        int chancesOfBreed = (int) (Math.random() * 100);
        if (age >= Animal.DUPLICATE && chancesOfBreed >= 90) {
            return new Predator((int) (Math.random() * getX()), (int) (Math.random() * getY()));
        }
        return null;
    }

    /**
     * Moves the predator towards the nearest prey or randomly if no prey is nearby.
     *
     * @param maxX The maximum x-bound of the map.
     * @param maxY The maximum y-bound of the map.
     * @param preys A list of prey currently in the simulation.
     */
    public void move(int maxX, int maxY, List<Prey> preys) {
        Prey nearestPrey = findNearestPrey(preys);
        if (nearestPrey != null) {
            chasePrey(nearestPrey, maxX, maxY);
        } else {
            randomMove(maxX, maxY);
        }
    }

    /**
     * Attempts to eat nearby prey.
     *
     * @param preys A list of available prey.
     * @return The eaten prey or null if none are within range.
     */
    public Prey eat(List<Prey> preys) {
        for (Prey prey : preys) {
            if (prey != lastTargetedPrey && isPreyInRange(prey)) {
                lastTargetedPrey = prey;
                return prey;
            }
        }
        return null;
    }

    private Prey findNearestPrey(List<Prey> preys) {
        Prey nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Prey prey : preys) {
            double distance = Math.hypot(prey.getX() - getX(), prey.getY() - getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = prey;
            }
        }
        return nearest;
    }

    private void chasePrey(Prey prey, int maxX, int maxY) {
        int deltaX = Integer.compare(prey.getX(), getX());
        int deltaY = Integer.compare(prey.getY(), getY());
        updatePosition(deltaX, deltaY, maxX, maxY);
    }

    private void randomMove(int maxX, int maxY) {
        int deltaX = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        int deltaY = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        updatePosition(deltaX, deltaY, maxX, maxY);
    }

    private void updatePosition(int deltaX, int deltaY, int maxX, int maxY) {
        int newX = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
        int newY = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
        setX(newX);
        setY(newY);
    }

    private boolean isPreyInRange(Prey prey) {
        return Math.abs(prey.getX() - getX()) <= EATING_RANGE &&
                Math.abs(prey.getY() - getY()) <= EATING_RANGE;
    }
}
