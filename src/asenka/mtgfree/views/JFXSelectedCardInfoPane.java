package asenka.mtgfree.views;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.data.MtgCard;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class JFXSelectedCardInfoPane extends ScrollPane {
	
	private final ObservableList<Card> selectedCards;
	
	private final JFXCardView cardView;
	
	private final JFXMagicText cardCostText;
	
	private final JFXMagicText cardRulesText;
	
	private final Text cardNameText;
	
	private final Text cardTypeText;
	
	private final Text cardFlavorText;
	
	public JFXSelectedCardInfoPane(final ObservableList<Card> selectedCards) {
		
		this.selectedCards = selectedCards;
		this.cardView = new JFXCardView(CardImageSize.LARGE);
		this.cardCostText = new JFXMagicText();
		this.cardRulesText = new JFXMagicText();
		this.cardNameText = new Text();
		this.cardTypeText = new Text();
		this.cardFlavorText = new Text();
		
		this.selectedCards.addListener((ListChangeListener.Change<? extends Card> change) -> {
			
			if(this.selectedCards.isEmpty()) {
				setCard(null);
			} else {
				final Card firstSelectedCard = this.selectedCards.get(0);
				setCard(firstSelectedCard);
			}
		});
		
		buildComponentLayout();
	}
	
	private void setCard(final Card card) {
		
		if(card != null) {
			final MtgCard cardData = card.getPrimaryCardData();
			
			this.cardView.setCard(card);
			this.cardCostText.setText("  (" + cardData.getManaCost() + " )");
			this.cardRulesText.setText(cardData.getText());
			this.cardNameText.setText(cardData.getName());
			this.cardTypeText.setText(cardData.getType());
			this.cardFlavorText.setText(cardData.getFlavor());
		} else {
			this.cardView.setCard(null);
			this.cardCostText.setText("");
			this.cardRulesText.setText("");
			this.cardNameText.setText("");
			this.cardTypeText.setText("");
			this.cardFlavorText.setText("");
		}
		
	}
	
	private void buildComponentLayout() {
		
		this.cardNameText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		this.cardTypeText.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));
		this.cardRulesText.setFont(Font.font("Verdana", FontPosture.REGULAR, 12));
		this.cardFlavorText.setFont(Font.font("Verdana", FontPosture.ITALIC, 12));
		
		GridPane nameCostBox = new GridPane();
		HBox typeBox = new HBox();
		HBox rulesBox = new HBox();
		HBox flavorBox = new HBox();
		
		nameCostBox.setPadding(new Insets(5, 35, 5, 5));
		typeBox.setPadding(new Insets(5, 35, 5, 5));
		rulesBox.setPadding(new Insets(5, 35, 5, 5));
		flavorBox.setPadding(new Insets(5, 35, 5, 5));
		
		nameCostBox.getChildren().addAll(this.cardNameText, this.cardCostText);
		GridPane.setConstraints(this.cardNameText, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setConstraints(this.cardCostText, 1, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
		typeBox.getChildren().addAll(this.cardTypeText);
		rulesBox.getChildren().addAll(this.cardRulesText);
		flavorBox.getChildren().addAll(this.cardFlavorText);
		
		VBox verticalLayout = new VBox();
		verticalLayout.getChildren().add(this.cardView);
		verticalLayout.getChildren().add(nameCostBox);
		verticalLayout.getChildren().add(typeBox);
		verticalLayout.getChildren().add(rulesBox);
		verticalLayout.getChildren().add(flavorBox);

		verticalLayout.setPadding(new Insets(10, 10, 10, 10));
		verticalLayout.setMaxWidth(CardImageSize.LARGE.getWidth());
		super.setContent(verticalLayout);
		super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		super.setHbarPolicy(ScrollBarPolicy.NEVER);
		super.setMaxWidth(CardImageSize.LARGE.getWidth() + 30);
	}
}
