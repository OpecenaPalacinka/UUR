import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Orli prvni = new Orli("Prvni", 2, 3, 12);
        Orli druhy = new Orli("Druhy", 2, 3, 3);
        Holubi treti = new Holubi("Treti", 4, 5, -2);
        Holubi ctvrty = new Holubi("Ctvrty", 4, 5, 18);
        Barakudy paty = new Barakudy("Paty", 8, 9, 15);
        Barakudy sesty = new Barakudy("Sesty", 8, 9, 9);
        Kapri sedmy = new Kapri("Sedmy", 6, 7, 12);
        Kapri osmy = new Kapri("Osmy", 6, 7, 12);
        Krysy devaty = new Krysy("Devaty", 2, 2, 82);
        Krysy desaty = new Krysy("BBC", 2, 1, 12);
        Lenochodi jedenacty = new Lenochodi("Jedenacty", 2, 5, 31);
        Lenochodi dvanacty = new Lenochodi("CBA", 2, 12, 11);
/**
 AAnimal[] vsechny = new AAnimal[12];
 vsechny[0] = prvni;
 vsechny[1] = druhy;
 vsechny[2] = treti;
 vsechny[3] = ctvrty;
 vsechny[4] = paty;
 vsechny[5] = sesty;
 vsechny[6] = sedmy;
 vsechny[7] = osmy;
 vsechny[8] = devaty;
 vsechny[9] = desaty;
 vsechny[10] = jedenacty;
 vsechny[11] = dvanacty;

 AAnimal[] vejcorody = new AAnimal[8];
 vejcorody[0] = prvni;
 vejcorody[1] = druhy;
 vejcorody[2] = treti;
 vejcorody[3] = ctvrty;
 vejcorody[4] = paty;
 vejcorody[5] = sesty;
 vejcorody[6] = sedmy;
 vejcorody[7] = osmy;

 AAnimal[] hlucna = new AAnimal[8];
 hlucna[0] = prvni;
 hlucna[1] = druhy;
 hlucna[2] = treti;
 hlucna[3] = ctvrty;
 hlucna[4] = devaty;
 hlucna[5] = desaty;
 hlucna[6] = jedenacty;
 hlucna[7] = dvanacty;

 Ptaci[] ptaci = new Ptaci[4];
 ptaci[0] = prvni;
 ptaci[1] = druhy;
 ptaci[2] = treti;
 ptaci[3] = ctvrty;

 //prvni cyklus
 for (AAnimal zvirata: vsechny
 ) {
 zvirata.eat(2);
 }
 //druhy cyklus
 for (Ptaci zviratka: ptaci
 ) {
 ((AAnimal)zviratka).moveTo(4,6);
 }
 //treti cyklus
 for (AAnimal hlasita: hlucna
 ) {
 if(hlasita instanceof Ptaci){
 ((Ptaci) hlasita).cry();
 }
 if(hlasita instanceof Savci){
 ((Savci) hlasita).cry();
 }
 }
 //ctvrty cyklus
 for (AAnimal vejce: vejcorody
 ) {
 if(vejce instanceof Ryby){
 ((Ryby) vejce).layEggs();
 }
 if(vejce instanceof Ptaci){
 ((Ptaci) vejce).layEggs();
 }
 }
 */

        //DU02
        //1
        System.out.println("Prvni kolekce:");
        ArrayList<Ptaci> ptaciArrayList = new ArrayList<>(6);
        ptaciArrayList.add(prvni);
        ptaciArrayList.add(druhy);
        ptaciArrayList.add(treti);
        ptaciArrayList.add(ctvrty);
        ptaciArrayList.add(new Orli("tretiOrel", 5, 8, 10));
        ptaciArrayList.add(new Orli("ctvrtyOrel", 1, 2, 11));

        for (Ptaci posunPtaci : ptaciArrayList
        ) {
            ((AAnimal) posunPtaci).moveTo(5, 5);
        }

        //2
        System.out.println("");
        System.out.println("Druha kolekce:");
        ArrayList<AAnimal> ruznaZvirata = new ArrayList<>(6);
        ruznaZvirata.add(prvni);
        ruznaZvirata.add(druhy);
        ruznaZvirata.add(paty);
        ruznaZvirata.add(sesty);
        ruznaZvirata.add(jedenacty);
        ruznaZvirata.add(dvanacty);

        System.out.println(ruznaZvirata.get(1).getClass());

        for (AAnimal hledamBarakudy : ruznaZvirata
        ) {
            if (hledamBarakudy instanceof Barakudy) {
                System.out.println(hledamBarakudy.getEnergie());
                break;
            }
        }

        boolean prvniPrvekJePtak = ruznaZvirata.get(0) instanceof Ptaci;
        boolean druhyPrvekJePtak = ruznaZvirata.get(1) instanceof Ptaci;
        System.out.println("První prvek je pták: " + prvniPrvekJePtak);
        System.out.println("Druhý prvek je pták: " + druhyPrvekJePtak);

        //3
        System.out.println("");
        System.out.println("Třetí kolekce:");
        ArrayList<Savci> pouzeSavci = new ArrayList<>(6);
        pouzeSavci.add(devaty);
        pouzeSavci.add(desaty);
        pouzeSavci.add(jedenacty);
        pouzeSavci.add(dvanacty);
        pouzeSavci.add(new Krysy("ACAB", 4, 3, 5));
        pouzeSavci.add(new Krysy("BACA", 2, 2, 2));

        for (Savci jenSavci : pouzeSavci
        ) {
            System.out.print(((AAnimal) jenSavci).getName() + ", ");
        }
        System.out.println("");

        ArrayList<Character> chary = new ArrayList<>();
        chary.add('a');
        chary.add('b');
        chary.add('c');
        System.out.println("");

        pouzeSavci.stream().filter(savci -> {
            for (char c : ((AAnimal) savci).getName().toCharArray()
            ) {
                if (!chary.contains(Character.toLowerCase(c))) {
                    return false;
                }
            }
            return true;
        }).forEach(c -> System.out.println(((AAnimal)c).getName()));
        System.out.println("");
        System.out.println("S vetsi energii nez 3");

        pouzeSavci.stream().filter(savci -> {
            for(char c:((AAnimal)savci).getName().toCharArray()){
                if(!chary.contains(Character.toLowerCase(c))||((AAnimal) savci).getEnergie()<3){
                    return false;
                }
            }
            return true;
        }).forEach(c->System.out.println(((AAnimal)c).getName()));

        //4
        System.out.println("");
        System.out.println("Ctvrta kolekce:");
        ArrayList<Ptaci> jenomPtaci = new ArrayList<>();
        jenomPtaci.add(prvni);
        jenomPtaci.add(druhy);
        jenomPtaci.add(treti);
        jenomPtaci.add(ctvrty);
        jenomPtaci.add(new Orli("novejOrel",4,5,123));
        jenomPtaci.add(new Holubi("novejHolub",2,46,22));

        for (Ptaci ptaciEnergie: jenomPtaci
             ) {
            if(((AAnimal)ptaciEnergie).getEnergie()>10){
                System.out.println(((AAnimal)ptaciEnergie).getName());
            }
        }
        int prumer =0;
        int pocitac=0;
        for (Ptaci ptaciPrumer: jenomPtaci
             ) {
            prumer+=((AAnimal)ptaciPrumer).getEnergie();
            pocitac++;
        }
        System.out.println("Prumerna energie: "+prumer/pocitac);

        int prumerOrlu =0;
        int pocitacOrlu =0;
        for (Ptaci ptaciOrli: jenomPtaci
             ) {
            if(ptaciOrli instanceof Orli){
                prumerOrlu+=((AAnimal)ptaciOrli).getEnergie();
                pocitacOrlu++;
            }
        }
        System.out.println("Prumerna energie Orlu je: " +prumerOrlu/pocitacOrlu);


        //5
        System.out.println("");
        System.out.println("Pata kolekce: ");
        ArrayList<String> listJmen = new ArrayList<>();
        listJmen.add("BlaBla");
        listJmen.add("Bubla");
        listJmen.add("LoremIpsum");
        listJmen.add("Hupinek");
        listJmen.add("Palacinka");
        listJmen.add("Stanik");

        for (String pouzeJmena: listJmen
             ) {
            System.out.print(pouzeJmena+ " ");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("Serazena:");

        Collections.sort(listJmen);
        for (String serazenaJmena: listJmen
             ) {
            System.out.println(serazenaJmena);
        }
        System.out.println("");
        System.out.println("Nahodne prohazena: ");
        Collections.shuffle(listJmen);
        for (String prohazenaJmena: listJmen
             ) {
            System.out.println(prohazenaJmena);
        }
    }

}
