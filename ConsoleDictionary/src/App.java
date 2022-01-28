/**
 * The {@code App} class is the application's driver class, <b>although it is not completed yet</b>.
 * <p>
 * It can be ran from any windows compatible terminal and offers some functionalities like
 * adding a new {@code word} or {@code translation} as well as some other dictionary management operations.
 * <hr>
 * @author Seyed Mohamad Golshani
 */
public class App
{
	/*
	 * test codes 001 SuperTrie trie = new SuperTrie();
	 * 
	 * System.out.println("Befor Adding Names To The Trie:\n------------------------------"); System.out.println(trie.findWord("Zaniar"));
	 * System.out.println(trie.findWord("Soheib")); System.out.println(trie.findWord("Mohamad"));
	 * 
	 * trie.defineWord("Zaniar"); trie.defineWord("Soheib"); trie.defineWord("Mohamad");
	 * 
	 * System.out.println("\nAfter Adding Names To The Trie:\n------------------------------"); System.out.println(trie.findWord("Zaniar"));
	 * System.out.println(trie.findWord("Soheib")); System.out.println(trie.findWord("Mohamad"));
	 */
	public static void main(String[] args)
	{

		CSVReader reader = new CSVReader();
		SuperTrie trie = reader.getDictionary();

		java.util.Scanner input = new java.util.Scanner(System.in);
		byte action = 0;

		while (action != 6)
		{
			System.out.flush();
			String req_word;
			SuperTrie.Word word;
			System.out.println("Testing Application 'Dictionary'... (Beta Version)");
			System.out.println("---------------------------------------------------------");
			System.out.println("1. Check If A Word Is Defined\n2. See Available Translations Associated To A Word\n3. Define A New Word");
			System.out.println("4. Define A Translation For An Existing Word\n5. Define A New Word And One Associated Translation");
			System.out.print("6. Exit\n\nPlease Choose An Action (1-6): ");

			try
			{
				action = Byte.parseByte(input.nextLine());
			}

			catch (Exception e)
			{
				continue;
			}

			switch (action)
			{
				case 1:
					System.out.println("Enter The Word Text: ");
					req_word = input.nextLine().toLowerCase().strip();
					System.out.println("Results: " + trie.findWordAndRetrieve(req_word));
					input.nextLine();
					break;
				case 2:
					System.out.println("Enter The Word Text: ");
					req_word = input.nextLine().toLowerCase().strip();
					if((word = trie.findWordAndRetrieve(req_word)) == null)
					{
						System.out.print("Undefined Word!\nPress Enter To Continue... ");
						continue;
					}
					System.out.println("Results: " + word.getFirstMeaning());
					input.nextLine();
					break;
				case 3:
					req_word = input.nextLine().toLowerCase().strip();
					trie.defineWordIfNot(req_word);
					System.out.print("The Word '" + req_word + "' Added Successfully!\nPress Enter To Continue... ");
					input.nextLine();
					break;
				case 4:
					System.out.println("Enter The Word Text: ");
					req_word = input.nextLine().toLowerCase().strip();
					if ((word = trie.findWordAndRetrieve(req_word)) == null)
					{
						System.out.print("I Can Not Find The Word!\nPress Enter To Continue... ");
						input.nextLine();
						continue;
					}
					System.out.println("Enter The Translation: ");
					req_word = input.nextLine().toLowerCase().strip();
					word.addMeaning(req_word);
					System.out.println("Translation Defined Successfully!\nPress Enter To Continue... ");
					input.nextLine();
					break;
				case 5:
					//
				case 6:
					System.exit(0);
					break;
			}
		}

		input.close();

	}
}
