package asenka.mtgfree.views.javafx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Card;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


/**
 * JavaFX component designed to display data about a card. 
 * 
 * @author asenka
 */
public class JFXCardDataPane extends GridPane {

	/**
	 * The value of double faced layout
	 */
	private static final String DOUBLE_FACED_LAYOUT = "double-faced";

	/**
	 * The string to replace by a real multiver ID of a card
	 */
	private static final String MULTIVERSE_ID = "[multiverseId]";
	
	/**
	 * The URL of the web site where the MTG card images can be loaded
	 */
	private static final String IMAGE_URL_WIZARD = "http://gatherer.wizards.com/Handlers/ImagesManager.ashx?multiverseid=" + MULTIVERSE_ID + "&type=card";
	
	/**
	 * The side of the card to display. We need this to manage the double-faced cards
	 * @see JFXCardDataPane#displayedCardSide
	 */
	private enum Side {
		FRONT, BACK;
	}

	/**
	 * The card displayed by this JavaFX component
	 */
	private Card displayedCard;

	/**
	 * The card name text component
	 */
	private Text cardNameText;

	/**
	 * The card type text component
	 */
	private Text cardTypeText;

	/**
	 * The card displayed side text component
	 */
	private Text sideText;

	/**
	 * The card text component. This componenant is able to display MTG symbols directly in the text
	 */
	private JFXMagicText cardText;

	/**
	 * The card mana cost component. This componenant is able to display MTG symbols directly in the text
	 */
	private JFXMagicText cardManaCost;

	/**
	 * The front card image
	 */
	private Image frontCardImage;

	/**
	 * The back card image (only for double-faced layout cards)
	 */
	private Image backCardImage;

	/**
	 * The card image view where the image is displayed
	 */
	private ImageView cardImageView;

	/**
	 * The button used to switch the displayed side of the card. Only enable for double-faced layout cards
	 */
	private Button switchCardFaceButton;

	/**
	 * The current displayed side of the card
	 */
	private Side displayedCardSide;

	/**
	 * Build a JFXCardDataPane without a card to display
	 */
	public JFXCardDataPane() {
		
		this(null);
	}
	
	/**
	 * Build a JFXCardDataPane with a card
	 * @param card the card to display
	 */
	public JFXCardDataPane(Card card) {

		// Initialize the general grid pane properties
		super();
		super.setPrefWidth(250d);
		super.setMinWidth(250d);
		super.setMaxSize(300d, 500d);
		super.setVgap(10d);
		super.setPadding(new Insets(5d, 5d, 5d, 5d));

		// Initialize the JavaFX components used to display the card data
		this.cardNameText = new Text();
		this.cardTypeText = new Text();
		this.sideText = new Text();
		this.cardText = new JFXMagicText();
		this.cardManaCost = new JFXMagicText();
		this.cardImageView = new ImageView();
		this.switchCardFaceButton = new Button("Switch side");

		// Add the component on the grid
		super.add(this.cardImageView, 0, 0);
		super.add(this.switchCardFaceButton, 0, 1);
		super.add(this.sideText, 1, 1);
		super.add(new Label("Name: "), 0, 2);
		super.add(this.cardNameText, 1, 2);
		super.add(new Label("Cost: "), 0, 3);
		super.add(this.cardManaCost, 1, 3);
		super.add(new Label("Type: "), 0, 4);
		super.add(this.cardTypeText, 1, 4);
		super.add(this.cardText, 0, 5);

		// Set the constraints on some of the components
		GridPane.setHalignment(this.cardImageView, HPos.CENTER);
		GridPane.setColumnSpan(this.cardImageView, 2);
		GridPane.setColumnSpan(this.cardText, 2);
		GridPane.setHalignment(this.switchCardFaceButton, HPos.CENTER);
		GridPane.setHalignment(this.sideText, HPos.CENTER);

		// Prepare the action to perform when the switch button is pressed
		this.switchCardFaceButton.setOnAction(event -> {
			final MtgCard cardData;
			if (this.displayedCardSide == Side.FRONT) {
				cardData = this.displayedCard.getSecondaryCardData();
				this.setSide(Side.BACK);
			} else {
				cardData = this.displayedCard.getPrimaryCardData();
				this.setSide(Side.FRONT);
			}
			displayCardData(cardData);
		});

		// If possible, the card data are loaded and displayed
		if (card != null) {
			this.setDisplayedCard(card);
		}

	}

	/**
	 * Set the card to be displayed on this component
	 * @param card the card to display
	 */
	public void setDisplayedCard(Card card) {

		this.displayedCard = card;
		this.frontCardImage = loadImage(card.getPrimaryCardData());

		// if the card has a double faced layout 
		if (DOUBLE_FACED_LAYOUT.equals(card.getLayout())) {
			this.switchCardFaceButton.setDisable(false);
			this.backCardImage = loadImage(card.getSecondaryCardData());
		} else {
			this.switchCardFaceButton.setDisable(true);
		}

		setSide(Side.FRONT);
		displayCardData(card.getPrimaryCardData());
	}

	/**
	 * Use this method to update the displayCardSide value and the text of the sideText component
	 * @param side the side to display
	 */
	private void setSide(Side side) {
	
		this.displayedCardSide = side;
		this.sideText.setText(side.toString());
	
		if (side == Side.FRONT) {
			this.cardImageView.setImage(this.frontCardImage);
		} else {
			this.cardImageView.setImage(this.backCardImage);
		}
	}

	/**
	 * Set the values displayed on the component about the card (name, cost, type, etc...)
	 * @param cardData the data to display
	 * @see MtgCard
	 */
	private void displayCardData(MtgCard cardData) {

		this.cardNameText.setText(cardData.getName());
		this.cardText.setText(cardData.getText());
		this.cardTypeText.setText(cardData.getType());
		this.cardManaCost.setText(cardData.getManaCost());
	}

	/**
	 * Load and returns an image based on the multiverse ID of the card
	 * @param cardData the card data
	 * @return an ImagesManager loaded from the web site http://gatherer.wizards.com/
	 */
	private Image loadImage(final MtgCard cardData) {
		
		// TODO this code should be place in a utility able to manage the data loading of all the image for the whole application
	
		// This method is based on the new type of Callable/Future concurrent object to have 
		// a parallele thread able to return a value (Runnable is not able to to that)
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		Image result = null;
		
		// Create a task to run in a thread that returns an ImagesManager
		Callable<Image> task = () -> {
			return new Image(IMAGE_URL_WIZARD.replace(MULTIVERSE_ID, Integer.toString(cardData.getMultiverseid())));
		};
		
		// Start the thread
		Future<Image> future = service.submit(task);
	
		try {
			// Wait until the image is available and store in result variable
			result = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
