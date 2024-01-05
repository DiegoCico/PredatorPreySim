public class Animal {

    public static final int MAXAGE = 12;
    public static final int DUPLICATE = 5;
    private boolean canBreed = false;
    private boolean isDead = false;
    private int x;
    private int y;

    public Animal(int locationX, int locationY){
        x = locationX;
        y = locationY;
    }

    public boolean getCanBreed(){
        return canBreed;
    }

    public void setCanBreed(boolean change){
        canBreed = change;
    }

    public boolean getIsDead(){
        return isDead;
    }

    public void setIsDead(boolean change){
        isDead = change;
    }

    public void setX(int change) { x = change; }
    public void setY(int change) { y = change; }

    public int getX(){ return x; }

    public int getY(){ return y; }

}
