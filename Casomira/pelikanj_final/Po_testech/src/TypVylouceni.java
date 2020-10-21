public enum TypVylouceni {
   NULA(0), DVE(2),PET(5);

   private int value;
   TypVylouceni(int i) {
        this.value = i;
    }

    public int getValue(){
        return value;
    }

    public String toString() {
        switch (this) {
            case DVE:
                return "2 minuty";
            case PET:
                return "5 minut";
        }
        return null;
    }
}
