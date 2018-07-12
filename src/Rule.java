public class Rule extends Symbol {
    int count;
    Guard guard;
    static Integer ruleNumber = 0;

    public Rule() {
        this.representation = String.valueOf(ruleNumber);
        ruleNumber++;
        guard = new Guard(this);
        assignRight(guard);
        assignLeft(guard); // seems necessary for hashcode check, to check left symbol, use to point to guard
        guard.assignRight(guard);
        guard.assignLeft(guard);
    }

    /**
     * adds a symbol to the last symbol
     * @param symbol
     */
    public void addNextSymbol(Symbol symbol) {
        symbol.assignLeft(guard.left); // left of symbol is current last, actualguard.left
        symbol.assignRight(guard); // symbol right should be actual guard
        guard.left.assignRight(symbol); // assign current last right to this symbol
        guard.assignLeft(symbol); // assign last to symbol
    }

    /**
     * add the two symbols from a digram to a nonTerminal
     * @param left
     * @param right
     */
    public void addSymbols(Symbol left, Symbol right) {
        this.addNextSymbol(left);
        this.addNextSymbol(right);
    }

    /**
     * return the last element
     * @return
     */
    public Symbol getLast() {
        return guard.left;
    }

    public Guard getGuard() { return guard; }

    public int getCount() { return count; }

    public void incrementCount() { count++;}

    public void decrementCount() { count--;}
}