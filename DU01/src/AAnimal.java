public abstract class AAnimal {
    private String name;
    private float x;
    private float y;
    public int energie;



    public AAnimal(String name, float x, float y, int energie) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.energie = energie;
    }
    public void moveTo(float x, float y) {}

    public String getName() {
        return name;
    }

    public void eat(int pridano){}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int energie) {
        this.energie = energie;
    }
}
