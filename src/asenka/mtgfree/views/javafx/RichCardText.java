package asenka.mtgfree.views.javafx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asenka.mtgfree.model.data.MtgCard;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RichCardText extends Group {
	
	private static final char OPENING_CHAR = '{';
	private static final char CLOSING_CHAR = '}';
	
	private static Map<String, Image> mtgSymbols;
	
	static {
		
		mtgSymbols = new HashMap<String, Image>();
		
		for(int i = 0; i <= 16; i++) {
			mtgSymbols.put("{" + i + "}", new Image("file:./resources/images/mtg/icons/" + i + ".jpg"));
		}
		mtgSymbols.put("{G}", new Image("file:./resources/images/mtg/icons/g.jpg"));
		mtgSymbols.put("{B}", new Image("file:./resources/images/mtg/icons/b.jpg"));
		mtgSymbols.put("{U}", new Image("file:./resources/images/mtg/icons/u.jpg"));
		mtgSymbols.put("{R}", new Image("file:./resources/images/mtg/icons/r.jpg"));
		mtgSymbols.put("{W}", new Image("file:./resources/images/mtg/icons/w.jpg"));
		mtgSymbols.put("{T}", new Image("file:./resources/images/mtg/icons/tap.jpg"));
	}
	
	private MtgCard cardData;

	private TextFlow textFlow;

	
	public RichCardText(MtgCard card) {
		
		this.textFlow = new TextFlow();
		this.getChildren().add(textFlow);
		this.setCardData(card);

	}
	

	/**
	 * @param cardData the cardData to set
	 */
	public void setCardData(MtgCard cardData) {

		this.cardData = cardData;
		
		for(String ruleElement : buildRulesTextList(this.cardData.getText())) {
			
			if(mtgSymbols.containsKey(ruleElement)) {
				this.textFlow.getChildren().add(new ImageView(mtgSymbols.get(ruleElement)));
			} else {
				this.textFlow.getChildren().add(new Text(ruleElement));
			}
		}
		
		Text flavorText = new Text("\n" + this.cardData.getFlavor());
		flavorText.setStyle("-fx-font-style: italic");
		this.textFlow.getChildren().add(flavorText);
	}
	
	/**
	 * 
	 * @param rulesText
	 * @return
	 */
	private static List<String> buildRulesTextList(String rulesText) {
		
		List<String> rulesTextList = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();

		for(char c : rulesText.toCharArray()) {	

			if(c == OPENING_CHAR && buf.length() > 0) {
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
