import GrammarCoder.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchTest {

    @Test
    public void searchActualSimple() {
        Compress c = new Compress();
        String compress = "acgt";
        c.processInput(compress);
        assertTrue(c.search("cg"));
    }

    @Test
    public void searchActualleftDigram() {
        Compress c = new Compress();
        String compress = "acgt";
        c.processInput(compress);
        assertTrue(c.search("ac"));
    }

    @Test
    public void searchActualRIghtdigram() {
        Compress c = new Compress();
        String compress = "acgt";
        c.processInput(compress);
        assertTrue(c.search("gt"));
    }

    // would need every symbol followed by a nonterminal and every symbols precedede by a nonterminal
    // first terminal of every rule, be able to find, then can search by digrams for that
    //g6 , also need last terminal of every rule then can search that 4 c
    // then every single possible match for both??????? 46 yeah
    // so how to efficiently retrieve all nonterminals that start with a particular terminal???
    @Test
    public void searchSmallString() {
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("cgca"));
    }

    @Test
    public void searchSimple() {
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("cgcaa"));
    }

    @Test
    public void searchSubRule() {
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("aa"));
    }

    //todo cant search for single...

    @Test
    public void searchAgain() { // begins in a subrule and ends in main rule
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("aattt"));
    }

    @Test
    public void searchTest() {
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("caac"));
    }

    //tc is not being registered.... the way possible rules are created
    @Test
    public void searchTestTwo() {
        Compress c = new Compress();
        String compress = "agtcgcaatttagacaacagccaa";
        c.processInput(compress);
        assertTrue(c.search("agtc"));
    }

    // gggagaaagcgaggatcag - this string is constructed but not registered as found
    @Test
    public void humdyst() {
        Compress c = new Compress();
        InputOutput io = new InputOutput();
        String originalFile = io.readFile("humdyst");
        c.processInput(originalFile);
        assertTrue(c.search("gaattccgg"));
    }
}
