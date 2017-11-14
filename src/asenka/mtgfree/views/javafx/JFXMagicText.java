package asenka.mtgfree.views.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * Use this class to display the rules text of a magic card or its mana cost. It replaces the string representation of the mana
 * cost with images.
 * 
 * @author asenka
 * @see FlowPane
 */
public class JFXMagicText extends FlowPane {

	/**
	 * The opening char of the text representation of the mtg symbols (e.g. <code>"{2}", "{U}"</code>)
	 */
	private static final char OPENING_CHAR = '{';

	/**
	 * The closing char of the text representation of the mtg symbols (e.g. <code>"{2}", "{U}"</code>)
	 */
	private static final char CLOSING_CHAR = '}';

	/**
	 * The separator used to create the list of words in the text. It is a simple string with one character : <code>" "</code>
	 */
	private static final String SPACE_SEPARATOR = " ";

	/**
	 * Contains the image symbols (values) associated with their text representation (keys)
	 */
	private static Map<String, Image> mtgSymbols;

	static {
		// TODO Improves the symbols loading images. This is only a basic implementation (a preferences file would be better)

		mtgSymbols = new HashMap<String, Image>();

		for (int i = 0; i <= 16; i++) {
			mtgSymbols.put("{" + i + "}", new Image("file:./resources/images/mtg/icons/" + i + ".jpg"));
		}
		mtgSymbols.put("{X}", new Image("file:./resources/images/mtg/icons/x.jpg"));
		mtgSymbols.put("{Y}", new Image("file:./resources/images/mtg/icons/y.jpg"));
		mtgSymbols.put("{Z}", new Image("file:./resources/images/mtg/icons/z.jpg"));
		mtgSymbols.put("{G}", new Image("file:./resources/images/mtg/icons/g.jpg"));
		mtgSymbols.put("{B}", new Image("file:./resources/images/mtg/icons/b.jpg"));
		mtgSymbols.put("{U}", new Image("file:./resources/images/mtg/icons/u.jpg"));
		mtgSymbols.put("{R}", new Image("file:./resources/images/mtg/icons/r.jpg"));
		mtgSymbols.put("{W}", new Image("file:./resources/images/mtg/icons/w.jpg"));
		mtgSymbols.put("{T}", new Image("file:./resources/images/mtg/icons/tap.jpg"));
	}

	/**
	 * Build an empty magic text
	 */
	public JFXMagicText() {

		this("");
	}

	/**
	 * Build a JFXMagicText
	 * 
	 * @param textWithSymbols the card data
	 */
	public JFXMagicText(final String textWithSymbols) {

		super();
		this.setText(textWithSymbols);
	}

	/**
	 * Change the text to display with the data from <code>cardDate</code>
	 * 
	 * @param textWithSymbols the text to display.
	 */
	public void setText(final String textWithSymbols) {

		ObservableList<Node> children = super.getChildren();

		if (children.size() > 0) {
			children.clear();
		}
		for (String textOrSymbol : splitTextAndSymbols(textWithSymbols)) {

			final Image symbolImage = mtgSymbols.get(textOrSymbol);

			if (symbolImage != null) {
				children.add(new ImageView(symbolImage));
			} else {
				children.add(new Text(textOrSymbol + SPACE_SEPARATOR));
			}
		}
	}

	/**
	 * Use a text to build a list of string elements that separates the regular text of the MTG symbols alias (<code>"{G}"</code>
	 * for green mana for example).
	 * 
	 * @param textWithSymbols a string that may contains symbols (e.g. <code>"{2}", "{U}"</code>)
	 * @return a list where every words and mtg symbols are separated. The size of the list is equal to the number of symbols and the
	 *         number of words in the original string
	 */
	private static List<String> splitTextAndSymbols(String textWithSymbols) {

		List<String> rulesTextList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();

		for (char c : textWithSymbols.toCharArray()) {

			if (c == OPENING_CHAR && buffer.length() > 0) {
				rulesTextList.addAll(Arrays.asList(buffer.toString().split(SPACE_SEPARATOR)));
				buffer = new StringBuffer();
				buffer.append(c);

			} else if (c == CLOSING_CHAR) {
				buffer.append(c);
				rulesTextList.add(buffer.toString());
				buffer = new StringBuffer();
			} else {
				buffer.append(c);
			}
		}
		rulesTextList.addAll(Arrays.asList(buffer.toString().split(SPACE_SEPARATOR)));

		return rulesTextList;
	}
}
