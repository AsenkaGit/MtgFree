package asenka.mtgfree.views.javafx;

import java.util.Observable;
import java.util.Observer;

import javax.management.RuntimeErrorException;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Card;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class JFXSelectedCardPane extends AnchorPane implements Observer {

	private Text cardNameText;

	private Text cardTypeText;

	private JFXMagicText cardText;

	private JFXMagicText cardManaCost;

	private Pane cardImagePane;
	
	private ImageView cardImageView;

	public JFXSelectedCardPane(Card card) {

		super();
		this.cardNameText = new Text();
		this.cardTypeText = new Text();
		this.cardText = new JFXMagicText();
		this.cardManaCost = new JFXMagicText();
		this.cardImageView = new ImageView();
		this.cardImagePane = new Pane();
		this.cardImagePane.setPrefHeight(400d);
		this.cardImagePane.getChildren().add(this.cardImageView);
		
		
		AnchorPane.setLeftAnchor(this.cardNameText, 10d);
		AnchorPane.setLeftAnchor(this.cardImagePane, 10d);
		AnchorPane.setLeftAnchor(this.cardText, 10d);
		AnchorPane.setLeftAnchor(this.cardTypeText, 10d);
		AnchorPane.setLeftAnchor(this.cardManaCost, 150d);

		AnchorPane.setTopAnchor(this.cardNameText, 10d);
		AnchorPane.setTopAnchor(this.cardImagePane, 50d);
		AnchorPane.setTopAnchor(this.cardText, 400d);
		AnchorPane.setTopAnchor(this.cardTypeText, 550d);
		AnchorPane.setTopAnchor(this.cardManaCost, 550d);
		
		ObservableList<Node> children = super.getChildren();
		
		children.add(this.cardNameText);
		children.add(this.cardImagePane);
		children.add(this.cardText);
		children.addAll(this.cardTypeText, this.cardManaCost);
		
		if (card != null) {
			this.setSelectedCardData(card);
		}

	}

	public void setSelectedCardData(Card card) {

		MtgCard cardData = card.getPrimaryCardData();
		
		this.cardNameText.setText(cardData.getName());
		this.cardText.setText(cardData.getText());
		this.cardTypeText.setText(cardData.getType());
		this.cardManaCost.setText(cardData.getManaCost());
		
		new Thread(() -> this.cardImageView.setImage(
				new Image("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + cardData.getMultiverseid() + "&type=card")))
						.start();
	}

	@Override
	public void update(Observable card, Object arg) {

		if (card instanceof Card) {
			this.setSelectedCardData((Card) card);
		} else {
			throw new RuntimeErrorException(new Error("Incompatible type for observed object: " + card));
		}
	}
}
