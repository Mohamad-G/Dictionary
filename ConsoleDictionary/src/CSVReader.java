/**
 * The {@code CSVReader} class is a utility class that deals with data file wich is in .csv format.
 * <hr>
 * Taking care of operations like reading every defined word and their associated meanings along with converting them into class objects {@code Word} and {@code Meaning} respectivly.
 * It Uses a {@code SuperTrie} object as a container.
 * <hr>
 * @author Seyed Mohamad Golshani
 */
public class CSVReader
{
    // the scanner object wich retrieves data from file
    java.util.Scanner reader;
    // holds csv file's path on hard disk
    private static final String csv_pathname = "lib\\EnglishPersianDatabase.csv";

    public CSVReader()
    {
        // it gets true only if a successfull connection to data file will be made
        boolean success = false;
        byte try_num = 0;

        while (success == false)
        {
            try
            {
                reader = new java.util.Scanner(new java.io.File(csv_pathname));
                success = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();

                if (++try_num > 5)
                    return;
                    
                System.out.println("Try #" + try_num + " To Read Database File... ");
            }
        }

        // specifying the character wich seperates values in a record
        // in this case it is a 'comma'
        reader.useDelimiter("\\n");
    }

    /**
     * The {@code getDictionary} method is the primary method in {@link CSVReader} class
     * wich does implement a dictionary by constructing a {@link SuperTrie} object.
     * <hr>
     * @return a {@link SuperTrie} object wich is filled with data by {@link #fillOutDictionary()} method.
     */
    public SuperTrie getDictionary()
    {
        SuperTrie dictionary = new SuperTrie();
        fillOutDictionary(dictionary);
        return dictionary;
    }

    private void fillOutDictionary(SuperTrie dictionary)
    {
        try
        {
            while (reader.hasNext())
            {
                String record = reader.next().strip();
                if (record == reader.delimiter().toString())
                    continue;
                
                SuperTrie.Word current_node = dictionary.defineWordIfNot(getFinglish(record.split(",")[0]));

                current_node.addMeaning(record.split(",")[1]);
            }
        }

        catch (Exception e) { e.printStackTrace(); }
        
        finally { reader.close(); }
    }

    /**
     * An alternative method for {@link #fillOutDictionary()} wich is compatible with unicode characters.
     * <hr>
     * @param dictionary
     */
    public void fillOutDictionaryUTFCharset(SuperTrie dictionary)
    {
        try
        {
            String content = java.nio.file.Files.readString(java.nio.file.Path.of(csv_pathname), java.nio.charset.StandardCharsets.UTF_8);

            String[] records = content.split("\n");
            for (String each_field : records)
            {
                SuperTrie.Word current_node = dictionary.defineWordIfNot(each_field.strip().split(",")[0]);
                System.out.println(getFinglish(each_field.strip().split(",")[1]));
                current_node.addMeaning(each_field.strip().split(",")[1]);
            }
        }

        catch (Exception e) { e.printStackTrace(); }
        
        finally { reader.close(); }
    }
    
    /**
     * A wellknown writing language that iranian boys and girls use most likely when
     * they chatting with two different people at the same time.
     * <hr>
     * @param text
     * @return a <b>String</b> representation of fingled! text
     */
    public static String getFinglish(String text)
    {
        StringBuilder fingled_text = new StringBuilder();

        for (Character each_char : text.toCharArray()) 
        {
            if (each_char.equals('\n') || each_char.equals('\0'))
                break;
            if (each_char.equals('ا') || each_char.equals('آ') || each_char.equals('ع'))
                fingled_text.append('a');
            else if (each_char.equals('ب'))
                fingled_text.append('b');
            else if (each_char.equals('س'))
                fingled_text.append('c');
            else if (each_char.equals('د'))
                fingled_text.append('d');
            else if (each_char.equals('ِ'))
                fingled_text.append('e');
            else if (each_char.equals('ف'))
                fingled_text.append('f');
            else if (each_char.equals('ج') || each_char.equals('غ') || each_char.equals('گ'))
                fingled_text.append('g');
            else if (each_char.equals('ه'))
                fingled_text.append('h');
            else if (each_char.equals('ی'))
                fingled_text.append('i');
            else if (each_char.equals('ج'))
                fingled_text.append('j');
            else if (each_char.equals('ک'))
                fingled_text.append('k');
            else if (each_char.equals('ل'))
                fingled_text.append('l');
            else if (each_char.equals('م'))
                fingled_text.append('m');
            else if (each_char.equals('ن'))
                fingled_text.append('n');
            else if (each_char.equals('و'))
                fingled_text.append('o');
            else if (each_char.equals('پ'))
                fingled_text.append('p');
            else if (each_char.equals('ق'))
                fingled_text.append('q');
            else if (each_char.equals('ر'))
                fingled_text.append('r');
            else if (each_char.equals('س'))
                fingled_text.append('s');
            else if (each_char.equals('ش'))
                fingled_text.append("sh");
            else if (each_char.equals('ت') || each_char.equals('ط'))
                fingled_text.append('t');
            else if (each_char.equals('ت'))
                fingled_text.append('u');
            else if (each_char.equals('و'))
                fingled_text.append('v');
            else if (each_char.equals('ژ'))
                fingled_text.append('x');
            else if (each_char.equals('ي'))
                fingled_text.append('y');
            else if (each_char.equals('ز') || each_char.equals('ظ') || each_char.equals('ذ'))
                fingled_text.append('z');
            else
                fingled_text.append(' ');
        }

        return fingled_text.toString();
    }

}
