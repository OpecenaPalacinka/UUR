public enum TypVylouceni {
    DVE,PET;

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
