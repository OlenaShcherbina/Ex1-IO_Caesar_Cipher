import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * Allows its users to encode a message stored in a file using the Caesar Cipher.
 *
 * <p>The decoded message is stored in a file.
 *
 * <p>Specification:
 *
 * <p>input(input file): a sequence of characters.
 *
 * <p>output(output file): the sequence of encoded input characters.
 */
public class Encode {
    public static void main(String[] args) {
        BufferedReader inStream = null;
        BufferedWriter outStream = null;

        // 1. Create theKeyboard

        Reader inputReader = new InputStreamReader(System.in);
        BufferedReader theKeyboard = new BufferedReader(inputReader);

        // 2. Display introductory message
        System.out.println(
                "\nThis program uses the Caesar cipher to encode the contents of a"
                        + "\nfile and writes the encoded characters to another file.");

        // 3. Prompt for and read name of the input file.
        System.out.print("\nEnter the name of the input file: ");
        String inFileName = "";
        try {
            inFileName = theKeyboard.readLine();
        } catch(IOException ex) {
            System.err.println("Failed to read file name: " + ex.getMessage());
            System.exit(1);
        }

        // 4. Open a BufferedReader named inStream for input from inFileName
        FileReader fileReader;
        // 5. If inStream failed to open, display an error message and quit
        URL inResource = Encode.class.getClassLoader().getResource(inFileName);
        Objects.requireNonNull(inResource);
        try {
            fileReader = new FileReader(inResource.getPath());
            inStream = new BufferedReader(fileReader);
        }catch(FileNotFoundException e){
            System.err.println("Failed to find the file: " + e.getMessage());
            System.exit(1);
        }


        // 6. Prompt for and read name of the input file.
        System.out.print("\nEnter the name of the output file: ");
        String outFileName = "";

        try {
            outFileName = theKeyboard.readLine();
        } catch (IOException ex) {
            System.err.println("Failed to read the file: " + ex.getMessage());
            System.exit(1);
        }

        // 7. Open an BufferedWriter named outStream for output to outFileName
        FileWriter fileWriter;

        // 8. If outStream failed to open, display an error message and quit
        URL outResource = Encode.class.getClassLoader().getResource("");
        Objects.requireNonNull(outResource);

        try {
            fileWriter = new FileWriter(outResource.getPath() + "/" + outFileName);
            outStream = new BufferedWriter(fileWriter);
        }catch (IOException e){
            System.err.println("Failed to write the file: " + e.getMessage());
            System.exit(1);
        }

        int inValue = 0;
        char outChar = ' ';

        // 9. Loop
        while (true) {
            // i. read a character from the input file via inStream into inValue
            try {
                inValue = inStream.read();
            }catch (IOException e){
                System.err.print("\nFailed to read the file: " + e.getMessage());
                e.printStackTrace();
            }
            // ii. if end-of-file was reached, terminate repetition

            if(inValue == -1){
                break;
            }
            // iii. encode the character using the Caesar cipher
            outChar = caesarEncode((char) inValue, 3);

            // iv. write the encoded character to the output file via outStream

            try{
                outStream.write(outChar);
            }catch (IOException e){
                System.err.print("\nFailed to write the file: " + e.getMessage());
                e.printStackTrace();
            }
        }


        // 10.i. close the connection to the input file
        try {
            inStream.close();

            // 10.i. close the connection to the output file
            outStream.close();
        }catch(IOException e){
            System.err.print("\nFailed to close the file: " + e.getMessage());
        }
        // 11. display a 'successful completion' message
        System.out.println("\nProcessing complete.\n Encoded message is in " + outResource.getPath() + outFileName);
    }

    /*********************************************************************
     * caesarEncode implements the Caesar cipher encoding scheme.        *
     *                                                                   *
     * Receive: ch, a character.                                         *
     *          key, the amount by which to rotate ch.                   *
     * Return:  The character that is key positions after ch,            *
     *          with "wrap-around" to the beginning of the sequence.     *
     *********************************************************************/

    public static char caesarEncode(char ch, int key) {
        final int FIRST_UPPER = 65, FIRST_LOWER = 97, NUM_CHARS = 26;

        if (key <= 0 || key >= NUM_CHARS) {
            System.err.println("\n*** CaesarEncode: key must be between 1 and 25\n");
            System.exit(1);
        }

        if (Character.isUpperCase(ch))
            return (char) ((ch - FIRST_UPPER + key) % NUM_CHARS + FIRST_UPPER);
        else if (Character.isLowerCase(ch))
            return (char) ((ch - FIRST_LOWER + key) % NUM_CHARS + FIRST_LOWER);
        else return ch;
    }
} // end class
