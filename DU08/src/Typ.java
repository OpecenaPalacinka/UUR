public enum Typ {
    SOUBOR, ADRESAR;

    public String toString() {
        switch (this) {
            case SOUBOR:
                return "Soubor";
            case ADRESAR:
                return "Adresar";
        }
        return null;
    }

    public String getSymbol() {
        switch (this) {
            case SOUBOR:
                return "\uD83D\uDDC8";
            case ADRESAR:
                return "\uD83D\uDCC1";
        }
        return null;
    }
}
