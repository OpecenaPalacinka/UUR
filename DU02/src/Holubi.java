public class Holubi extends Ptaci {
    String zvuk = "Pipani";
    String vajicka = "2 holubi vejce";
    String jidlo = "Zrni";
    String pohyb = "Leta nizko";

    public Holubi(String name, float x, float y, int energie) {
        super(name, x, y, energie);
    }

    public void moveTo(float x, float y) {
        super.moveTo(x,y);
        int energie = getEnergie();
        if (energie<=0){
            System.out.println("Nemas dostatek energie");

        } else {
            setX(x);
            setY(y);
            System.out.println("Nova pozice je: " + x + ";" + y + " a "+pohyb);
            energie--;
            setEnergie(energie);
        }

    }

    public void eat(int pridano){
        super.eat(pridano);
        int energie = getEnergie();
        energie += pridano;
        System.out.println("Energie je: " +energie+" a zvire jedlo "+jidlo);
        setEnergie(energie);

    }


    public void cry(){
        super.cry();
        int energie = getEnergie();

        if(energie<=0){
            System.out.println("Zvire je moc unavene");
        } else {
            energie--;
            System.out.println(zvuk);
            setEnergie(energie);
        }
    }

    public void layEggs(){
        super.layEggs();
        int energie = getEnergie();
        if (energie<=4) {
            System.out.println("Zvire je moc unavene na kladeni vajicek");
        } else {
            System.out.println(vajicka);
            energie = energie -5;
            setEnergie(energie);
        }
    }
}
