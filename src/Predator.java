import java.util.List;

public class Predator extends Animal {
    // Attribute to track the age of the prey.
    private int age = 0;

    // Constructor to initialize the prey's position and randomly assign an initial age.
    public Predator(int x, int y){
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

    // Method to move the predator towards the nearest prey.
    public void move(int maxX, int maxY, List<Prey> preys) {
        Prey nearestPrey = findNearestPrey(preys);
        if (nearestPrey != null) {
            // Move towards the nearest prey
            int deltaX = Integer.compare(nearestPrey.getX(), getX());
            int deltaY = Integer.compare(nearestPrey.getY(), getY());

            // Update position within boundaries.
            int x = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
            int y = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
            setX(x);
            setY(y);
        } else {
            // If no prey is found, move randomly
            randomMove(maxX, maxY);
        }
    }

    // Helper method to find the nearest prey.
    private Prey findNearestPrey(List<Prey> preys) {
        Prey nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Prey prey : preys) {
            double distance = Math.sqrt(Math.pow(prey.getX() - getX(), 2) + Math.pow(prey.getY() - getY(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearest = prey;
            }
        }
        return nearest;
    }

    // Method for random movement
    private void randomMove(int maxX, int maxY) {
        int deltaX = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        int deltaY = (int) (Math.random() * 3) - 1; // -1, 0, or 1

        int x = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
        int y = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
        setX(x);
        setY(y);
    }
}