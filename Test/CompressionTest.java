import org.junit.Test;

import static org.junit.Assert.*;

    public class CompressionTest {

        @Test
        public void compressBook() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("15000");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println(compare);
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void writeFile() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("testLong");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void chmpxx() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("chmpxx");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void chntxx() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("chntxx");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void hehcmv() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("hehcmv");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void humdyst() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("humdyst");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void humghcs() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("humghcs");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void humhbb() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("humhbb");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void vaccg() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("vaccg");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void mtpacga() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("mtpacga");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void mpomtcg() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("mpomtcg");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void humprtb() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("humprtb");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

        @Test
        public void humhdab() {
            Compress c = new Compress();
            InputOutput io = new InputOutput();
            String originalFile = io.readFile("humhdab");
            c.processInput(originalFile);
            String compare = c.encode(c.getFirstRule().getGuard().getRight(), "");
            System.out.println("original " + originalFile.length());
            System.out.println("compressed " + compare.length());
            System.out.println("Difference " + (originalFile.length() - compare.length()));
            assertTrue(compare.length() < originalFile.length());
        }

}
