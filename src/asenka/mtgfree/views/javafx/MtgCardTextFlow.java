package asenka.mtgfree.views.javafx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asenka.mtgfree.model.data.MtgCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Use this class to display the rules text of a magic card or its mana cost. It replaces the
 * string representation of the mana cost with images.
 * 
 * @author asenka
 * @see TextFlow
 */
public class MtgCardTextFlow extends TextFlow {

	/**
	 * The opening char of the text representation of the mtg symbols (e.g. "{2}", "{U}")
	 */
	private static final char OPENING_CHAR = '{';

	/**
	 * The closing char of the text representation of the mtg symbols (e.g. "{2}", "{U}")
	 */
	private static final char CLOSING_CHAR = '}';

	/**
	 * Contains the image symbols (values) associated with their text representation (keys)
	 */
	private static Map<String, Image> mtgSymbols;

	static {

		mtgSymbols = new HashMap<String, Image>();

		for (int i = 0; i <= 16; i++) {
			mtgSymbols.put("{" + i + "}", new Image("file:./resources/images/mtg/icons/" + i + ".jpg"));
		}
		mtgSymbols.put("{G}", new Image("file:./resources/images/mtg/icons/g.jpg"));
		mtgSymbols.put("{B}", new Image("file:./resources/images/mtg/icons/b.jpg"));
		mtgSymbols.put("{U}", new Image("file:./resources/images/mtg/icons/u.jpg"));
		mtgSymbols.put("{R}", new Image("file:./resources/images/mtg/icons/r.jpg"));
		mtgSymbols.put("{W}", new Image("file:./resources/images/mtg/icons/w.jpg"));
		mtgSymbols.put("{T}", new Image("file:./resources/images/mtg/icons/tap.jpg"));
	}

	/**
	 * Build a MtgCardTextFlow 
	 * @param card the card data
	 * @see MtgCard
	 */
	public MtgCardTextFlow(MtgCard card) {

		super();
		this.setCardData(card);
	}

	/**
	 * Change the text to display with the data from <code>cardDate</code>
	 * @param cardData the cardData to set
	 */
	public void setCardData(MtgCard cardData) {

		for (String ruleElement : buildRulesTextList(cardData.getText())) {

			final Image symbol = mtgSymbols.get(ruleElement);

			if (symbol != null) {
				super.getChildren().add(new ImageView(symbol));
			} else {
				super.getChildren().add(new Text(ruleElement));
			}
		}
		Text flavorText = new Text("\n\n" + cardData.getFlavor());
		flavorText.setStyle("-fx-font-style: italic");
		super.getChildren().add(flavorText);
	}

	/**
	 * Use a mtg card rules text to build a list of string elements that separates the regular text of
	 * the MTG symbols alias (<code>"{G}"</code> for green mana for example).
	 * @param rulesText a string with the rules text from a mtg card
	 * @return a list of strings
	 */ 
	private static List<String> buildRulesTextList(String rulesText) {

		List<String> rulesTextList = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();

		for (char c : rulesText.toCharArray()) {

			if (c == OPENING_CHAR && buf.length() > 0) {
				rulesTextList.add(buf.toString());
				buf = new StringBuffer();
				buf.append(c);

			} else if (c == CLOSING_CHAR) {
				buf.append(c);
				rulesTextList.add(buf.toString());
				buf = new StringBuffer();
			} else {
				buf.append(c);
			}
		}
		rulesTextList.add(buf.toString());

		return rulesTextList;
	}
}
