import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Compress {
    private final static int USED_ONCE = 1; // rule used once
    private Map<Symbol, Symbol> digramMap; // - digram points to digram via right hand symbol
    private Integer ruleNumber; // count for created rules
    private Rule firstRule; // main base 'nonterminal'
    private HashSet<Rule> rules;
    String mainInput;

    // TODO reorder rules to rule usage
    // TODO keep digrams from left to right

    /**
     * main constructor for compress, just initialises, maps and first rules etc
     */
    public Compress() {
        Rule.ruleNumber = 0;
        digramMap = new HashMap<>();
        rules = new HashSet<>();
        ruleNumber = 0;
        firstRule = new Rule(); // create first rule;
    }

    /**
     * main method loops through the input and adds the latest symbol to the main rule
     * calls check digram on for last two symbols
     * some output calls
     * @param input
     */
    public void processInput(String input) {
        //TODO probably creating a digram fir first guard and first symbol that isn't needed
        getFirstRule().addNextSymbol(new Terminal(input.substring(0, 0 + 1)));
        mainInput = input;
        for (int i = 1; i < input.length(); i++) {
            //System.out.println(i + " of " + input.length());
            // add next symbol from input to the first rule
            getFirstRule().addNextSymbol(new Terminal(input.substring(i, i + 1)));
            checkDigram(getFirstRule().getLast());
//
//            if (i > 340000) {
//                rules.clear();
//                rules.add(getFirstRule());
//                generateRules(getFirstRule().actualGuard.right);
//                System.out.println(printRules());
//            }
        }

        rules.add(getFirstRule());
        generateRules(getFirstRule().getGuard().getRight());
        System.out.println(printRules());
        // below for reodering ruls
//
//        List<Rule> orderedRules = rules.stream()
//                .sorted(Rule::compareTo)
//                .collect(Collectors.toList());
//
//        //System.out.println(orderedRules);
//
//        for (Rule r : orderedRules) {
//            if (!r.representation.equals("0")) {
//                r.representation = String.valueOf(orderedRules.indexOf(r)+1);
//            }
//        }
//
//        System.out.println(orderedRules);
    }

    /**
     * method that checks through the main options of latest two symbols
     * if new digram not seen beofre, add to map
     * if seen before and already a rule, use that rule instead
     * if seen and not a rule, make a new rule
     * each new digram with the use of a rule must be checked also
     */
    public void checkDigram(Symbol symbol) {
        // check existing digrams for last digram, update them with new rule
        if (digramMap.containsKey(symbol)) {
            // if the existing digram has ? either side, it must be a complete digram rule/ an existing rule
            Symbol existingDigram = digramMap.get(symbol); // existing digram
            // if the matching digram is an overlap do nothing
            if (existingDigram.right != symbol) { // todo find a better way to place this
                if (existingDigram.getLeft().getLeft().isGuard() && existingDigram.getRight().isGuard()) {
                    existingRule(symbol, existingDigram);
                }
                else { // if digram has been seen but only once, no rule, then create new rule
                    createRule(symbol, existingDigram);
                }
            }
        }
        else { // digram not been seen before, add to digram map
            digramMap.putIfAbsent(symbol, symbol);
        }
    }


    /**
     * create the rule for a repeated digram, requires being done twice for both instances
     * of the digram, rule has two instances, the nonterminal it represents only one
     * takes the latest digram and the digram that occured earlier from the digram map
     * @param symbol
     */
    public void createRule(Symbol symbol, Symbol oldSymbol) {
        //ruleNumber++; // increase rule number
        Rule newRule = new Rule(); // create new rule to hold new Nonterminal

        replaceDigram(newRule, oldSymbol); // update rule for first instance of digram
        replaceDigram(newRule, symbol);// update rule for last instance of digram

        newRule.addSymbols(oldSymbol.getLeft(), oldSymbol); // add symbols to the new rule/terminal

    // reduce rule count if being replaced.... if either symbol of digram a nonterminal then rmeove
        replaceRule(oldSymbol.getLeft());
        replaceRule(oldSymbol);
    }

    /**
     * already a rule for the digram found, replace it with that rule
     * this needs looking into - TODO recursive here? consolidate with other method?
     * takes the symbol being the latest digram of the main rule and
     * the already exsiting rule/nonterminal for that digram
     * @param symbol
     */
    public void existingRule(Symbol symbol, Symbol oldSymbol) {
        Guard g = (Guard) oldSymbol.getRight(); // have to get guard and then rule from there
        Rule rule = g.guardRule; // get rule using pointer to it in the guard// right.right will be guard
        replaceDigram(rule, symbol);
        replaceRule(rule.getLast().getLeft());
        replaceRule(rule.getLast());
    }

    /**
     * if a digram is being replaced with a new nonterminal and either symbols of that
     * digram are a rule, their count/usage must be reduced
     * if the count has reached one, then the rule is removed and it's occurence replaced with
     * the symbols it stood for
     * @param symbol
     */
    public void replaceRule(Symbol symbol) {
        if (symbol instanceof NonTerminal) { // if the symbol is a rule reduce usage
            NonTerminal nonTerminal = (NonTerminal) symbol;
            nonTerminal.getRule().decrementCount();
            if (nonTerminal.getRule().getCount() == USED_ONCE) { // if rule is down to one, remove completely
                removeDigramsFromMap(symbol);
                nonTerminal.removeRule(); // uses the rule method to reassign elements of rule
                checkNewDigrams(nonTerminal.getLeft().getRight(), nonTerminal.getRight(), nonTerminal);
            }
        }
    }

    /**
     * might not be needed you know... just work the links of the symbols??
     * replace an instance of a digram with a nonterminal
     * @param newRule
     * @param symbol - the position of the digram to be replaced
     */
    public void replaceDigram(Rule newRule, Symbol symbol) {
        removeDigramsFromMap(symbol);
        NonTerminal nonTerminal = new NonTerminal(newRule);

        // join the links
        nonTerminal.assignRight(symbol.getRight());
        nonTerminal.assignLeft(symbol.getLeft().getLeft());
        symbol.getLeft().getLeft().assignRight(nonTerminal);
        symbol.getRight().assignLeft(nonTerminal);

        checkNewDigrams(nonTerminal, nonTerminal.getRight(), nonTerminal);
    }

    /**
     * when nonterminals are added or removed the old digrams must be removed from the map
     * currently requires some extra checks for ensuring that the digrams being removed do not
     * correspond with the same digram that is overlapping
     * @param symbol
     */
    public void removeDigramsFromMap(Symbol symbol) {
        // don't remove digram if of an overlapping digram
        //TODO better way to do this
        if (digramMap.containsKey(symbol.getLeft())){ // if it's in there and its not overlapping with a rule that you would want to keep, then remove it
            Symbol existing = digramMap.get(symbol.getLeft());
            if (existing == symbol.getLeft()) {
                digramMap.remove(symbol.getLeft());
            }
        }

        if (!symbol.getRight().equals(symbol)) {
            digramMap.remove(symbol.getRight());
        }
    }

    /**
     * when a nonterminal is added or removed the corresponding changes in the surrounding digrams
     * must be checked to maintain digram uniqueness and rule utility
     * @param left
     * @param right
     * @param nonTerminal
     */
    public void checkNewDigrams(Symbol left, Symbol right, NonTerminal nonTerminal) {
        if (!nonTerminal.getRight().isGuard()) {
            checkDigram(right);
        }
        if (!nonTerminal.getLeft().isGuard()) {
            checkDigram(left);
        }
    }

    /**
     * prints out the symbols corresponding to the generated rules
     * @return
     */
    public String printRules() {
        String output = "";
        for (Rule r : rules) {
            output += r + " > ";
            Symbol current = r.getGuard().getRight();
            while (!current.isGuard()) {
                if (current instanceof NonTerminal) {
                    output += ((NonTerminal) current).getRule().representation + " ";
                }
                else {
                    output += current + " ";
                }
                current = current.getRight();
            }
            output += "| ";
        }
        return output;
    }

    /**
     * works through the symbols and collects all the rules in a set
     * @param current
     */
    public void generateRules(Symbol current) {
        while (!current.isGuard()) {
            if (current instanceof NonTerminal) {
                Rule rule = ((NonTerminal) current).getRule();
                rules.add(rule);
                generateRules(rule.getGuard().getRight());
            }
            current = current.getRight();
        }
    }

    /**
     * prints out all the digrams added to the digram map
     */
    public void printDigrams() {
        for (Symbol s : digramMap.values()) {
            System.out.print(s.getLeft() + " " + s + ", ");
        }
        System.out.println();
    }

    /**
     * for debugging, creates the string back from the cfg generated
     * @param rule
     * @return
     */
    public String decompress(Rule rule) {
        Symbol s = rule.getGuard().getRight();
        String output = "";
        do {
            if (s instanceof Terminal) {
                output += s.toString();
                s = s.getRight();
            }
            else {
                output += decompress(((NonTerminal) s).getRule());
                s = s.getRight();
            }

        } while (!s.isGuard());
        return output;
    }

    /**
     * getter for the main rule nonterminal
     * @return
     */
    public Rule getFirstRule() {
        return this.firstRule;
    }
}