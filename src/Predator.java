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

    // Method to move the prey randomly within the given range.
    public void move(int maxX, int maxY) {
        // Randomly calculate movement deltas.
        int deltaX = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        int deltaY = (int) (Math.random() * 3) - 1; // -1, 0, or 1

        // Update position within boundaries.
        int x = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
        int y = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
        setX(x);
        setY(y);
    }
}