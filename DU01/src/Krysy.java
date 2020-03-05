public class Krysy extends AAnimal implements Savci {
    String zvuk = "Pisti";
    String jidlo = "Vsechno";
    String pohyb = "Behaji";
    public Krysy(String name, float x, float y, int energie) {
        super(name, x, y, energie);
    }

    public void moveTo(float x, float y) {
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
        int energie = getEnergie();
        energie += pridano;
        System.out.println("Energie je: " +energie+" a zvire jedlo "+jidlo);
        setEnergie(energie);

    }


    public void cry(){
        int energie = getEnergie();

        if(energie<=0){
            System.out.println("Zvire je moc unavene");
        } else {
            energie--;
            System.out.println(zvuk);
            setEnergie(energie);
        }
    }

}

