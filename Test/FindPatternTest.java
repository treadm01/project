import org.junit.Test;

import static org.junit.Assert.*;

public class FindPatternTest {
    FindPattern fp = new FindPattern();


    @Test
    public void getRepeatOfPairs() {
        assertEquals("bc", fp.getRepeat("abcdbc").get(0));
        assertEquals("ac", fp.getRepeat("accdac").get(0));
        assertEquals("cc", fp.getRepeat("abdccbecc").get(0));
        assertEquals("cc", fp.getRepeat("cccc").get(0));
        assertEquals("cc", fp.getRepeat("ccccd").get(0));
        assertEquals("cc", fp.getRepeat("ccccde").get(0));
    }

    @Test
    public void addPattern() {
        fp.initGrammar("abcdbc");
        fp.addPattern();
        assertEquals("bc", fp.getGrammars().get("2"));

        fp.initGrammar("abcdbcabcdbc");
        fp.addPattern();
        System.out.println(fp.getGrammars().toString());
        assertEquals("aBdB", fp.getGrammars().get("2"));
    }

    @Test
    public void initGrammar() {
        fp.initGrammar("abcdbc");
        System.out.println(fp.getGrammars().toString());
    }
}