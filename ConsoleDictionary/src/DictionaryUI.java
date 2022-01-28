import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextArea;

/**
 * The {@code DictionaryUI} class constructs application's user interface.
 * <p>
 * It inherites from the {@link Frame} class wich is a top level class object in java offers a simple windows style box and some other components as well.
 * <p>
 * It also implements the {@link KeyListener} interface in order to react to the user's various key related operations like {@code KeyPressed} and
 * {@code Key Released}...
 * <hr>
 * 
 * @author Seyed Mohamad Golshani
 */
public class DictionaryUI extends Frame implements KeyListener
{
	private static CSVReader reader;
	private static SuperTrie trie;
	private static SuperTrie.Word current_char;

	private static JTextArea status_bar;
	private static JTextArea text_box;

	public DictionaryUI()
	{
		status_bar = new JTextArea();
		status_bar.setBounds(20, 220, 360, 160);
		status_bar.setBackground(new Color(216, 191, 216));

		text_box = new JTextArea();
		text_box.setBounds(20, 50, 360, 160);
		text_box.setBackground(new Color(245, 245, 245));
		text_box.addKeyListener(this);

		add(status_bar);
		add(text_box);

		setSize(400, 400);
		setLayout(null);
		setVisible(true);

		reader = new CSVReader();
		trie = reader.getDictionary();
		// reader.fillOutDictionaryUTFCharset(trie);
		current_char = trie.getRoot();
		status_bar.setText(current_char.toString());
	}

	@Override
	public void keyPressed(KeyEvent key) {}

	@Override
	public void keyReleased(KeyEvent key)
	{
		byte index = (byte)(key.getKeyChar());

		if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			current_char = current_char.getPreviousChar();

		else if (index >= 97 && index <= 123)
			current_char = current_char.getNextChar()[index - 'a'];

		translateIfPossible();

	}

	@Override
	public void keyTyped(KeyEvent key) {}

	private void translateIfPossible()
	{
		if (current_char.is_word)
		{
			StringBuilder translation = new StringBuilder();
			translation.append("Available Translation(s) For The Word '" + current_char + "': \n");

			SuperTrie.Word.Meaning.MeaningNode meaning = current_char.getFirstMeaningObject();
			while (meaning != null)
			{
				translation.append(meaning.getMeaning() + "\n");
				meaning = meaning.getNextMeaning();
			}
			status_bar.setText(translation.toString());
		}
	}

	public static void main(String[] args)
	{
		new DictionaryUI();
	}
}
