package ArithmeticCoder;/*
 * Reference arithmetic coding
 * Copyright (c) Project Nayuki
 * 
 * https://www.nayuki.io/page/reference-arithmetic-coding
 * https://github.com/nayuki/Reference-arithmetic-coding
 */

import java.io.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Decompression application using adaptive arithmetic coding.
 * <p>Usage: java AdaptiveArithmeticDecompress InputFile OutputFile</p>
 * <p>This decompresses files generated by the "AdaptiveArithmeticCompress" application.</p>
 */
public class AdaptiveArithmeticDecompress {
    static String PATH = System.getProperty("user.dir") + "/compressedFiles";
	
	public static void main(String[] args) throws IOException {

		File inputFile  = new File(PATH + "/compressed.bin");
		File outputFile = new File(PATH + "/compressTwo.txt");
		
		// Perform file decompression
		try (BitInputStream in = new BitInputStream(new BufferedInputStream(new FileInputStream(inputFile)));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile))) {
			decompress(in, out);
		}
	}
	
	//TODO HAVE TO BE ABLE TO DECODE NEW IMPLICIT ENCODINGS - now have different symbols for markers etc
    //todo WILL DIFFERENT MACHINES DECODE THESE IN THE SAME WAY ENCODE IN THE SAME WAY?
	// To allow unit testing, this method is package-private instead of private.
	static void decompress(BitInputStream in, OutputStream out) throws IOException {
	    int ruleSize = readGammaCode(in);
        FlatFrequencyTable initFreqs = new FlatFrequencyTable(ruleSize);
		FrequencyTable freqs = new SimpleFrequencyTable(initFreqs);
		ArithmeticDecoder dec = new ArithmeticDecoder(32, in);
        Boolean isEdit = false; //todo don't forget
        //! = 33
        int lastSymbol = -1;
        String output = "";
		while (true) {
			// Decode and write one byte
            int symbol = dec.read(freqs);

            if (symbol == ruleSize - 1) {  // EOF symbol
                break;
            }

            //TODO IS IT POSSIBLE FOR SYMBOLS TO OVERLAP MARKER ENCODING, OR SYMBOL TO OVERLAP EOF SYMBOL?
//  TODO TRYING TO GET THE FREQUENCY WORKING
			// checking digit here is no good as could be encoded to anything
            //also could be positioned anywhere including over symbols

            if (symbol > 32 && symbol < 128) {
                if (Character.isDigit((char)symbol)) {
                    // todo it is very possible that a marker length could be more than one digit long
                    // yeah issue when 12
                    if (lastSymbol != '*' && lastSymbol != '?' && lastSymbol != '$' && lastSymbol != '#') {
                        output += "!";
                        out.write(33);
                    }
                }
                System.out.println((char) symbol + " " + symbol);
                output += String.valueOf((char) symbol);
                out.write(symbol);
            }
            else { // checking for hash symbol here breaks different, you need a proper solution
                if (lastSymbol != '*' && lastSymbol != '?' && lastSymbol != '$' && lastSymbol != '#') {
                    out.write(33); // remember have to deal with edits too....
                    output += "!";
                }
                String s = String.valueOf(symbol - 128); //todo issue with frequency the offset?
                output += s;
                for (int i = 0; i < s.length(); i++) {
                    out.write((int)s.charAt(i));
                }
            }

            freqs.increment(symbol);
            freqs.set(symbol, freqs.get(symbol) + 10);
            lastSymbol = symbol;
		}

        System.out.println();
        System.out.println(output);
        //
        //todo probably wont ever use just return string to decompress, unless wanted compressed at this level?
        // appears to wipe out when set specifically to text....
        try (PrintWriter outFile = new PrintWriter("/home/tread/IdeaProjects/projectGC/textFiles/compressTest.txt")) {
            outFile.println(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

	static int readGammaCode(BitInputStream in) throws IOException {
        int count = 0;
        while (in.read() != 0) {
            count++;
        }

        String binaryRule = "";
        for (int i = 0; i < count; i++) {
            //todo theres probably a better way, length known multiply just add up the numbers
            binaryRule += String.valueOf(in.read());
        }

        int ruleSize = Integer.parseInt(binaryRule, 2);
        return ruleSize;
    }
}
