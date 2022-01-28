/** @author: Seyed Mohamad Golshani */
public class SuperTrie
{
	private static Word root;
	// number of distinct
	private int distinct_words;
	// "DEF_CHARS" stands for defined characters and it is total
	// number of supported characters in the structure
	private static final byte DEF_CHARS = 26;

	public SuperTrie()
	{
		root = new Word();
		distinct_words = 0;
	}

	public Word getRoot() {	return root; }

	public int getDistinctWords() { return distinct_words; }


	static class Word
	{
		private String content;
		private Word[] next_char;
		private Word previous_char;
		private Meaning meaning;
		boolean is_word;

		public Word()
		{
			content = null;
			next_char = new Word[DEF_CHARS];

			for (byte i = 0; i < DEF_CHARS; ++i)
				next_char[i] = null;

			previous_char = null;
			meaning = null;
			is_word = false;
		}

		public Word[] getNextChar() {	return next_char; }
		
		public Word getPreviousChar() {	return previous_char; }
		
		public String getFirstMeaning()
		{
			if (meaning == null)
				return "No Associated Translation Available!";
			
			return meaning.first.meaning;
		}
		
		public Meaning.MeaningNode getFirstMeaningObject()
		{
			if (meaning == null)
				return null;
			
			return meaning.first;
		}
		
		public void addMeaning(String meaning_text)
		{
			if (meaning == null)
			{
				meaning = new Meaning(meaning_text);
				return;
			}

			meaning.defineMeaning(meaning_text);
		}

		@Override
		public String toString()
		{
			if(content == null)
				return "No Text Representation Available!";
			
			return content;
		}


		/**
		 * The {@code Meaning} class represents meanings associated to a particular {@code Word} object.
		 * <p>
		 * It uses a singly linked list data structure in order to store {@code Meaning} objects in a way that the {@code first} element is linked to the first
		 * meaning associated to the {@code Word} object and if no meaning available it will contain {@code null} value.
		 */
		static class Meaning
		{
			// first meaning available for the word
			private MeaningNode first;
			// number of available distinct meanings associated to the word
			private int meanings_count;

			public Meaning()
			{
				first = null;
				first.next_meaning = null;
				meanings_count = 0;
			}

			public Meaning(String first_meaning)
			{
				first = new MeaningNode(first_meaning);
				first.next_meaning = null;
				meanings_count = 1;
			}

			public int getMeaningsCount() { return meanings_count; }


			/**
			 * The {@code MeaningNode} class is a simple implementation of node class in a singly linked list data structure.
			 * <p>
			 * each {@code MeaningNode} element in a {@code Meaning} class demonstrates an available translation associated with the {@code Word} object.
			 */
			static class MeaningNode
			{
				private String meaning;
				private MeaningNode next_meaning;

				public MeaningNode(String meaning_text)
				{
					meaning = meaning_text;
					next_meaning = null;
				}

				public String getMeaning() { return meaning; }
				
				public MeaningNode getNextMeaning() { return next_meaning; }
			}


			public MeaningNode defineMeaning(String meaning_text)
			{
				MeaningNode new_meaning = new MeaningNode(meaning_text);

				if (first == null)
				{
					first = new_meaning;
					++meanings_count;
					return first;
				}

				MeaningNode char_iterator = first;
				while (char_iterator.next_meaning != null)
					char_iterator = char_iterator.next_meaning;

				char_iterator.next_meaning = new_meaning;
				++meanings_count;
				return char_iterator;
			}
		}
	}



	public Word defineWordIfNot(String word_text)
	{
		Word char_iterator = findWordAndRetrieve(word_text);

		if (char_iterator != null)
			return char_iterator;
		
		char_iterator = root;
		word_text = word_text.toLowerCase().strip();

		for (char each_char : word_text.toCharArray())
		{
			byte index = (byte)(each_char - 'a');
			if (index < 0 || index >= DEF_CHARS)
				continue;

			if (char_iterator.next_char[index] == null)
			{
				char_iterator.next_char[index] = new Word();
				char_iterator.next_char[index].previous_char = char_iterator;
			}

			char_iterator = char_iterator.next_char[each_char - 'a'];
		}

		if (char_iterator.is_word == false)
		{
			char_iterator.content = word_text;
			char_iterator.is_word = true;
			++distinct_words;
		}
		
		return char_iterator;
	}

	public Word findWordAndRetrieve(String word)
	{
		Word char_iterator = root;
		word = word.toLowerCase().strip();

		for (char each_char : word.toCharArray())
		{
			byte index = (byte)(each_char - 'a');
			if (index < 0 || index >= DEF_CHARS)
				continue;

			if (char_iterator.next_char[index] == null)
				return null;

			char_iterator = char_iterator.next_char[index];
		}

		return char_iterator;
	}

	public void commonPrefix(String common_prefix) {}

	public void printDictionary() {}

}
