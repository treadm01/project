import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticEncoderTest {

    @Test
    public void testArithmeticEncoder() {
        ArithmeticEncoder ae = new ArithmeticEncoder();
        String input = "210";
        ae.processInput(input);
        assertEquals("10110", ae.getBinaryString());
    }

    @Test
    public void testAlphabetSetter() {
        ArithmeticEncoder ae = new ArithmeticEncoder();
        String input = "210";
        ae.setSourceAlphabet(input);
        ae.calculateSymbolProbabilityRatio();
        assertEquals("{0=0.2, 1=0.4, 2=0.4}", ae.sourceAlphabet.toString());
    }

    @Test
    public void processInput() {
    }

    @Test
    public void getBinaryString() {
    }

    @Test
    public void setSourceAlphabet() {
    }

    @Test
    public void getProbability() {

    }

    @Test
    public void calculateRationalDenominator() {
        ArithmeticEncoder ae = new ArithmeticEncoder();
        String input = "210";
        ae.setSourceAlphabet(input);
        assertEquals("10", ae.calculateRationalDenominator().toString());
    }

    @Test
    public void calculateSymbolProbabilityRatio() {
    }

    @Test
    public void setSymbolSegmentStart() {
        ArithmeticEncoder ae = new ArithmeticEncoder();
        String input = "012";
        ae.setSourceAlphabet(input);
        ae.setSymbolSegment();
        // should be 0, 2, 6
        for (ArithmeticSymbol as : ae.sourceAlphabet.values()) {
            System.out.println(as.getSegmentStart());
        }
    }

    @Test
    public void setSymbolSegmentEnd() {
        ArithmeticEncoder ae = new ArithmeticEncoder();
        String input = "012";
        ae.setSourceAlphabet(input);
        ae.setSymbolSegment();
        // should be 2, 6, 10
        for (ArithmeticSymbol as : ae.sourceAlphabet.values()) {
            System.out.println(as.getSegmentEnd());
        }
    }
}