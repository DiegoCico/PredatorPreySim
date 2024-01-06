import java.util.List;

public class Prey extends Animal {
    // Attribute to track the age of the prey.
    private int age = 0;

    // Constructor to initialize the prey's position and randomly assign an initial age.
    public Prey(int x, int y){
        super(x, y);
        age = (int) (Math.random() * Animal.MAXAGE);
    }

    // Method to increment the age of the prey and check for its life status.
    public void increaseAge(){
        age++;
        if(age >= Animal.MAXAGE){
            setIsDead(true);
        }
    }

    // Method for the prey to breed under certain conditions.
    public Prey breed(){
        int chancesOfBreed = (int) (Math.random() * 100);
        if (age >= Animal.DUPLICATE && chancesOfBreed >= 50){
            return new Prey((int) (Math.random() * getX()), (int) (Math.random() * getY()));
        }
        return null;
    }

    public void move(int maxX, int maxY, List<Predator> predators) {
        Predator nearestPredator = findNearestPredator(predators);
        int deltaX, deltaY;

        if (nearestPredator != null) {
            // Calculate direction to move away from the predator
            deltaX = (nearestPredator.getX() > getX()) ? -1 : 1;
            deltaY = (nearestPredator.getY() > getY()) ? -1 : 1;
        } else {
            // Regular random movement
            deltaX = (int) (Math.random() * 3) - 1; // -1, 0, or 1
            deltaY = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        }

        // Update position within boundaries.
        int x = Math.max(10, Math.min(getX() + deltaX, maxX - 10));
        int y = Math.max(10, Math.min(getY() + deltaY, maxY - 30));
        setX(x);
        setY(y);
    }

    private Predator findNearestPredator(List<Predator> predators) {
        Predator nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Predator predator : predators) {
            double distance = Math.sqrt(Math.pow(predator.getX() - getX(), 2) + Math.pow(predator.getY() - getY(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearest = predator;
            }
        }
        return nearest;
    }
}