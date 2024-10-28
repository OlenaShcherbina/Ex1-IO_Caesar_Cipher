import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 *  Allows its users to decode a message stored in a file
 *  using the Caesar cipher.
 *
 *  The encoded message is stored in a file.
 *
 *  Specification:
 *
 *     input(input file):   an encoded sequence of characters.
 *
 *     output(output file): the sequence of decoded input characters.
 */
public class Decode
{
    public static void main(String[] args){
        Reader inStream = null;
        Writer outStream = null;

        // 1. Create theKeyboard
        BufferedReader theKeyboard = new BufferedReader(new InputStreamReader(System.in));

        // 2. Display introductory message
        System.out.println("\nThis program uses the Caesar cipher to decode the contents of a "
                + "file and writes the decoded characters to another file.");

        // 3. Prompt for and read name of the input file.
        System.out.print("\nEnter the name of the input file: ");
        String inFile = "";
        try {
            inFile = theKeyboard.readLine();

        }catch(IOException ex) {
            System.err.println("Failed to read file name: " + ex.getMessage());
            System.exit(1);
        }

        // 4. Open a BufferedReader named inStream for input from inFile
        FileReader fileReader;

        // 5. If inStream failed to open, display an error message and quit
        URL inResource = Decode.class.getClassLoader().getResource(inFile);
        Objects.requireNonNull(inResource);

        try {
            fileReader = new FileReader(inResource.getPath());
            inStream = new BufferedReader(fileReader);
        }catch(FileNotFoundException e){
            System.err.println("Failed to find the file: " + e.getMessage());
            System.exit(1);
        }

        // 6. Prompt for and read name of the input file.
        System.out.print( "\nEnter the name of the output file: ");
        String outFile = "";

        try {
            outFile = theKeyboard.readLine();
        } catch (IOException ex) {
            System.err.println("Failed to read file name: " + ex.getMessage());
            System.exit(1);
        }

        // 7. Open a stream named outStream for output to outFile
        FileWriter fileWriter;

        // 8. If outStream failed to open, display an error message and quit
        URL outResource = Decode.class.getClassLoader().getResource("");
        Objects.requireNonNull(outResource);

        try {
            fileWriter = new FileWriter(outResource.getPath() + "/" + outFile);
            outStream = new BufferedWriter(fileWriter);
        }catch (IOException e){
            System.err.println("Failed to write the file: " + e.getMessage());
            System.exit(1);
        }

        int  inValue = 0;
        char outChar=' ';

        // 9. Loop
        while(true)
        {
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
            // iii. decode the character using the Caesar cipher
            outChar = caesarDecode((char) inValue, 3);

            // iv. write the decoded character to the output file via outStream
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
            // 10.ii. close the connection to the output file
            outStream.close();
        }catch(IOException e){
            System.err.print("\nFailed to close the file: " + e.getMessage());
        }
        // 11. display a 'successful completion' message
        System.out.println("\nProcessing complete.\n Decoded message is in "
                + outResource.getPath() + outFile);
    }

    /********************************************************************
     * caesarDecode implements the Caesar cipher encoding scheme.       *
     *                                                                  *
     * Receive: ch, a character,                                        *
     *          key, an integer.                                        *
     * Return:  The character that is key positions before ch,          *
     *          with "wrap-around" to the end of the sequence.          *
     ********************************************************************/

    public static char caesarDecode(char ch, int key)
    {
        final int FIRST_UPPER = 65,
                FIRST_LOWER = 97,
                NUM_CHARS = 26;

        if (key <= 0 || key >= 26)
        {
            System.err.println("\n*** CaesarDecode: key must be between 1 and 25!\n");
            System.exit(1);
        }

        if (Character.isUpperCase(ch))
            return (char) ((ch - FIRST_UPPER + NUM_CHARS - key) % NUM_CHARS + FIRST_UPPER);
        else if (Character.isLowerCase(ch))
            return (char) ((ch - FIRST_LOWER + NUM_CHARS - key) % NUM_CHARS + FIRST_LOWER);
        else
            return ch;
    }

}

