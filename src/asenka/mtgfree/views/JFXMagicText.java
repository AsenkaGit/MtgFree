package asenka.mtgfree.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Use this class to display the rules text of a magic card or its mana cost. It replaces the string representation of the mana
 * cost with images.
 * 
 * @author asenka
 */
public class JFXMagicText extends VBox {

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
	 * The string with the line break used in the text. Usually it is <code>"\n"</code>
	 */
	private static final String LINE_BREAK = "\n";

	/**
	 * An JavaFX Text component with an empty Text
	 * 
	 * @see Text
	 */
	private static final Text EMPTY_TEXT = new Text("");

	/**
	 * Contains the image symbols (values) associated with their text representation (keys)
	 */
	private static final Map<String, Image> MTG_SYMBOLS = getSymbols();

	/**
	 * The text displayed
	 */
	private String text;

	/**
	 * The alignement of the text (horizontally)
	 */
	private Pos alignement;

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
	public JFXMagicText(String textWithSymbols) {

		super();
		this.text = textWithSymbols;
		this.alignement = Pos.CENTER_LEFT;
		this.setText(textWithSymbols);
	}

	/**
	 * Change the text to display with the data from <code>cardDate</code>
	 * 
	 * @param textWithSymbols the text to display.
	 */
	public void setText(String textWithSymbols) throws IllegalArgumentException {

		// If the parameter is null, the text display is an empty string
		if (textWithSymbols == null) {
			textWithSymbols = "";
		}

		// The currentFlowPane variable is used to display a line. If the text does not contains line break, only one flow
		// pane will be used
		FlowPane currentFlowPane = new FlowPane();
		currentFlowPane.setAlignment(this.alignement);

		// Object used to add the flow pane(s) to the JFXMagicText
		ObservableList<Node> children = super.getChildren();

		// If the JFXMagicText contains at least one child, the list of children is cleared
		if (children.size() > 0) {
			children.clear();
		}

		// For each text element or magic symbol in the text
		for (String textOrSymbol : splitTextAndSymbols(textWithSymbols)) {

			final Image symbolImage = MTG_SYMBOLS.get(textOrSymbol);

			if (symbolImage != null) {
				currentFlowPane.getChildren().add(new ImageView(symbolImage));
			} else if (LINE_BREAK.equals(textOrSymbol)) {
				children.add(currentFlowPane.getChildren().isEmpty() ? new FlowPane(EMPTY_TEXT) : currentFlowPane);
				currentFlowPane = new FlowPane();
			} else {
				Text text = new Text(textOrSymbol + SPACE_SEPARATOR);
				text.getStyleClass().add("mtg-text");
				currentFlowPane.getChildren().add(text);
			}
		}
		children.add(currentFlowPane);
	}

	/**
	 * Set the alignement of the text (center, left or right)
	 * @param hAlignement
	 */
	public void setHAlignment(Pos hAlignement) {

		this.alignement = hAlignement;
		this.setText(this.text);
	}

	/**
	 * Method during the class loading. It loads the symbols and the images of those symbols
	 * and return then as a map.
	 * @return a map of String and JavaFX Image
	 */
	private static Map<String, Image> getSymbols() {
		
		// TODO Improves the symbols loading images. This is only a basic implementation (a preferences file would be better)
		final Map<String, Image> symbols = new HashMap<String, Image>();
	
		for (int i = 0; i <= 16; i++) {
			symbols.put("{" + i + "}", new Image("file:./resources/images/mtg/icons/" + i + ".jpg"));
		}
		symbols.put("{X}", new Image("file:./resources/images/mtg/icons/x.jpg"));
		symbols.put("{Y}", new Image("file:./resources/images/mtg/icons/y.jpg"));
		symbols.put("{Z}", new Image("file:./resources/images/mtg/icons/z.jpg"));
		symbols.put("{G}", new Image("file:./resources/images/mtg/icons/g.jpg"));
		symbols.put("{B}", new Image("file:./resources/images/mtg/icons/b.jpg"));
		symbols.put("{U}", new Image("file:./resources/images/mtg/icons/u.jpg"));
		symbols.put("{R}", new Image("file:./resources/images/mtg/icons/r.jpg"));
		symbols.put("{W}", new Image("file:./resources/images/mtg/icons/w.jpg"));
		symbols.put("{T}", new Image("file:./resources/images/mtg/icons/tap.jpg"));
		symbols.put("{E}", new Image("file:./resources/images/mtg/icons/e.jpg"));
		symbols.put("{B/R}", new Image("file:./resources/images/mtg/icons/br.jpg"));
		symbols.put("{R/W}", new Image("file:./resources/images/mtg/icons/rw.jpg"));
		symbols.put("{R/G}", new Image("file:./resources/images/mtg/icons/rg.jpg"));
		symbols.put("{G/U}", new Image("file:./resources/images/mtg/icons/gu.jpg"));
		symbols.put("{G/W}", new Image("file:./resources/images/mtg/icons/gw.jpg"));
		symbols.put("{G/U}", new Image("file:./resources/images/mtg/icons/gu.jpg"));
		symbols.put("{U/B}", new Image("file:./resources/images/mtg/icons/ub.jpg"));
		symbols.put("{U/R}", new Image("file:./resources/images/mtg/icons/ur.jpg"));
		return symbols;
	}

	/**
	 * Use a text to build a list of string elements that separates the regular text of the MTG symbols alias (<code>"{G}"</code>
	 * for green mana for example).
	 * 
	 * @param textWithSymbols a string that may contains symbols (e.g. <code>"{2}", "{U}"</code>)
	 * @return a list where every words and mtg symbols are separated. The size of the list is equal to the number of symbols and
	 *         the number of words in the original string
	 */
	private static List<String> splitTextAndSymbols(String textWithSymbols) {
	
		List<String> rulesTextList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
	
		// Prepare the string to easily detect the line breaks (add spaces around the line brakes)
		// The line breaks are double to have more space between paragraphs when the text is displayed
		String preparedString = textWithSymbols.replaceAll(LINE_BREAK,
			(SPACE_SEPARATOR + LINE_BREAK + SPACE_SEPARATOR + LINE_BREAK + SPACE_SEPARATOR));
	
		// for each character in the prepared string
		for (char c : preparedString.toCharArray()) {
	
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
