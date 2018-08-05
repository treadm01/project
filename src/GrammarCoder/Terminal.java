package GrammarCoder;

public class Terminal extends Symbol {
    long originalValue;

    public Terminal(long representation) { //TODO make long??
        originalValue = representation;
        this.representation = (representation * 2) + 1;
        left = new Symbol(); //todo having to add for equality check, not needed otherwise
    }

    @Override
    public String toString() {
        return String.valueOf((char) originalValue);
    }

    public long getOriginalValue() {
        return originalValue;
    }


    @Override
    public boolean equals(Object obj) {
        //TODO add all checks
        Symbol symbol = (Symbol) obj;

        if (symbol.getLeft() instanceof NonTerminal) {
            return ((representation == symbol.getRepresentation())
                    && (left.representation == (symbol.left.getRepresentation()))
                    && left.isComplement == symbol.left.isComplement
            ); // switched check to look at left symbol ra
        }
        else {
            return super.equals(obj);
        }
    }

    public static char reverseSymbol(char symbol) {
        char complement = 0;
        if (symbol == 'a') {
            complement = 't';
        }
        else if (symbol == 'c') {
            complement = 'g';
        }
        else if (symbol == 'g') {
            complement = 'c';
        }
        else if (symbol == 't') {
            complement = 'a';
        }
        return complement;
    }
}
