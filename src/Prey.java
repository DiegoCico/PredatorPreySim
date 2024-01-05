public class Prey extends Animal{
    private int age = 0;

    public Prey(int x, int y){
        super(x,y);
        age = (int) (Math.random() * Animal.MAXAGE);
    }

    public void increaseAge(){
        age++;
        if(age >= Animal.MAXAGE){
            this.setIsDead(true);
        }
    }

    public Prey breed(){
        if (age == Animal.DUPLICATE && (int) (Math.random() * 100) >= 60){
            System.out.println("BREED");
            return new Prey((int) Math.random() * getX(), (int) Math.random() * getY());
        }
        return null;
    }

    public void move(int maxX, int maxY) {
        // Randomly move the prey within the given range
        int deltaX = (int) (Math.random() * 3) - 1; // -1, 0, or 1
        int deltaY = (int) (Math.random() * 3) - 1; // -1, 0, or 1

        // Update position with boundary checks
        int x = Math.max(0, Math.min(getX() + deltaX, maxX - 1));
        int y = Math.max(0, Math.min(getY() + deltaY, maxY - 1));
        setX(x);
        setY(y);
    }
}
