public class Animal {
    // Constants defining the maximum age and breeding age for an animal.
    public static final int MAXAGE = 12;
    public static final int DUPLICATE = 5;

    // Attributes to track the animal's breeding capability and life status.
    private boolean canBreed = false;
    private boolean isDead = false;

    // Coordinates representing the animal's position.
    private int x;
    private int y;

    // Constructor to initialize the animal's position.
    public Animal(int locationX, int locationY){
        x = locationX;
        y = locationY;
    }

    // Getter and setter methods for the attributes.
    public boolean getCanBreed(){ return canBreed; }
    public void setCanBreed(boolean change){ canBreed = change; }
    public boolean getIsDead(){ return isDead; }
    public void setIsDead(boolean change){ isDead = change; }
    public int getX(){ return x; }
    public int getY(){ return y; }
    public void setX(int change) { x = change; }
    public void setY(int change) { y = change; }
}