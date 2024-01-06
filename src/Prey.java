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
        if (age == Animal.DUPLICATE && (int) (Math.random() * 100) >= 60){
            System.out.println("BREED");
            // Fixed: Proper casting of Math.random() to ensure correct position calculation.
            return new Prey((int) (Math.random() * getX()), (int) (Math.random() * getY()));
        }
        return null;
    }

    // Method to move the prey, possibly running away from nearby predators.
    public void move(int maxX, int maxY, List<Predator> predators) {
        // Check for nearby predators and decide on movement.
        boolean isPredatorClose = isPredatorNearby(predators);
        int deltaX, deltaY;

        if (isPredatorClose) {
            // Logic to run away from the predator
            // This is a simple approach; you might want to implement a smarter way
            deltaX = (getX() > maxX / 2) ? -1 : 1;
            deltaY = (getY() > maxY / 2) ? -1 : 1;
        } else {
            // Regular random movement
            deltaX = (int) (Math.random() * 3) - 1;
            deltaY = (int) (Math.random() * 3) - 1;
        }

        // Update position within boundaries.
        int x = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
        int y = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
        setX(x);
        setY(y);
    }

    // Helper method to check if any predator is close to the prey.
    private boolean isPredatorNearby(List<Predator> predators) {
        final int dangerDistance = 50; // Define how close a predator needs to be to trigger running

        for (Predator predator : predators) {
            int distanceX = Math.abs(getX() - predator.getX());
            int distanceY = Math.abs(getY() - predator.getY());
            if (distanceX <= dangerDistance && distanceY <= dangerDistance) {
                return true;
            }
        }
        return false;
    }
}