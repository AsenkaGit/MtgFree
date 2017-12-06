package asenka.mtgfree.views;

import org.controlsfx.control.InfoOverlay;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.views.utilities.ImagesManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class JFXPlayerHand extends ScrollPane {

	/**
	 * The list of cards in the player hand
	 */
	private ObservableList<Card> cards;

	/**
	 * The pane display the cards horizontaly
	 */
	private HBox horizontalLayout;

	/**
	 * The list of card panes
	 */
	private ObservableList<Pane> cardPanes;

	/**
	 * Flag indicating if the hand is from the local player or another player
	 */
	private boolean isLocalPlayerHand;

	/**
	 * The game controller used to perform actions on the cards
	 */
	private final GameController gameController;

	/**
	 * Build a JFX component displaying the cards in the player's hand
	 * @param gameController the controller used to manipulate the cards
	 * @param player the player
	 */
	public JFXPlayerHand(final GameController gameController, final Player player) {

		super();
		this.cards = player.getHand();
		this.gameController = gameController;
		this.isLocalPlayerHand = player.equals(gameController.getGameTable().getLocalPlayer());
		this.cardPanes = FXCollections.<Pane> observableArrayList();
		this.horizontalLayout = new HBox();
		this.horizontalLayout.setPadding(new Insets(10, 10, 10, 10));

		this.cards.forEach(card -> this.cardPanes.add(createCardPane(card)));
		this.horizontalLayout.getChildren().addAll(this.cardPanes);

		initChangeListener();

		super.setContent(this.horizontalLayout);
	}

	/**
	 * Create a pane displaying a card 
	 * @param card the card to display
	 * @return a pane with all the components necessary for the card
	 */
	private Pane createCardPane(final Card card) {

		// Create the image of the card. The size and the image used depends on the player
		final ImageView cardImageView;

		// If this component display the local player's hand
		if (this.isLocalPlayerHand) {
			cardImageView = new ImageView(ImagesManager.getImage(card.getPrimaryCardData()));
			cardImageView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
			cardImageView.setFitWidth(CardImageSize.MEDIUM.getWidth());
		} else { // else... the player is not the local player
			// The card is display only if it is visible, otherwise the back of the card is displayed
			cardImageView = new ImageView(
				card.isVisible() ? ImagesManager.getImage(card.getPrimaryCardData()) : ImagesManager.IMAGE_MTG_CARD_BACK);
			cardImageView.setFitHeight(CardImageSize.SMALL.getHeigth());
			cardImageView.setFitWidth(CardImageSize.SMALL.getWidth());
		}

		addContextMenuForCardNode(cardImageView, card);
		
		// Add action to set the selected card
		cardImageView.setOnMouseClicked(event -> this.gameController.setSelectedCards(card));

		// Add the tooltip on the card image view
		Tooltip tooltip = new Tooltip(createTextForTooltip(card));
		tooltip.setWrapText(true);
		tooltip.setMaxWidth(300);
		Tooltip.install(cardImageView, tooltip);

		// The card is added in an info overlay (see controlfx library) that display the card name
		final InfoOverlay cardView = new InfoOverlay(cardImageView, card.getPrimaryCardData().getName());

		// When the mouse is over the card view, the card is zoomed 5% bigger
		cardView.setOnMouseEntered(event -> {
			cardView.setScaleX(1.05);
			cardView.setScaleY(1.05);
		});

		// When the mouse leaves the image, it returns to its normal size
		cardView.setOnMouseExited(event -> {
			cardView.setScaleX(1);
			cardView.setScaleY(1);
		});

		// Create and return the pane to put the card view in
		final Pane cardPane = new StackPane(cardView);
		cardPane.setPadding(new Insets(0, 5, 0, 5));
		return cardPane;
	}

	/**
	 * Add a context menu on a node related to a single card.
	 * 
	 * @param node the node where the context menu will be added
	 * @param card the card related to the node where the context menu is created
	 */
	private void addContextMenuForCardNode(final Node node, final Card card) {

		// Create the context menu
		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem playMenuItem = new MenuItem("Play");
		final MenuItem playHiddenMenuItem = new MenuItem("Play (hidden)");
		final MenuItem destroyMenuItem = new MenuItem("Destroy");
		final MenuItem exileMenuItem = new MenuItem("Exile");
		final RadioMenuItem revealRadioMenuItem = new RadioMenuItem("Revealed");
		contextMenu.getItems().addAll(playMenuItem, playHiddenMenuItem, destroyMenuItem, exileMenuItem, revealRadioMenuItem);

		// Set the action associated with the menu items
		destroyMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.GRAVEYARD, GameController.TOP, false));
		exileMenuItem.setOnAction(
			event -> this.gameController.changeCardContext(card, Context.HAND, Context.EXILE, GameController.TOP, card.isVisible()));
		playMenuItem.setOnAction(
			event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, false));
		playHiddenMenuItem
			.setOnAction(event -> this.gameController.changeCardContext(card, Context.HAND, Context.BATTLEFIELD, GameController.TOP, true));
		revealRadioMenuItem.setOnAction(event -> this.gameController.setVisible(card, !card.isVisible()));

		// Add the context menu to the image view
		node.setOnContextMenuRequested(event -> contextMenu.show(node, event.getScreenX(), event.getScreenY()));
	}

	/**
	 * Initializes the change listener that update this component when a card is added or remove
	 */
	private void initChangeListener() {

		// TODO Naive implementation : remove all and add all again... Can do better.
		// Even though this component should not contain more than 7 or 8 cards most of the time
		this.cards.addListener(new ListChangeListener<Card>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Card> change) {

				// To update the JavaFX components, you have to perform the operations in the JavaFX Application Thread
				Platform.runLater(() -> {
					JFXPlayerHand.this.horizontalLayout.getChildren().clear();
					JFXPlayerHand.this.cardPanes.clear();

					JFXPlayerHand.this.cards.forEach(card -> JFXPlayerHand.this.cardPanes.add(createCardPane(card)));
					JFXPlayerHand.this.horizontalLayout.getChildren().addAll(JFXPlayerHand.this.cardPanes);
				});
			}
		});
	}

	/**
	 * Build a string displaying all the major data about the card. The method is able to manage the double cards
	 * 
	 * @param card the card
	 * @return a string with some data about the card
	 */
	private static String createTextForTooltip(final Card card) {

		StringBuffer buf = new StringBuffer();
		buf.append("[" + card.getBattleId() + "]\n");
		buf.append(createTextForTooltip(card.getPrimaryCardData()));

		final MtgCard secondaryCardData = card.getSecondaryCardData();

		if (secondaryCardData != null) {
			buf.append("\n---------------------------------------------------------------------\n");
			buf.append(createTextForTooltip(secondaryCardData));
		}

		return buf.toString();

	}

	/**
	 * Build a string displaying all the major data about the card
	 * 
	 * @param cardData the object that contains the data about the card
	 * @return a string with some data about the card
	 */
	private static String createTextForTooltip(final MtgCard cardData) {

		StringBuffer buf = new StringBuffer();

		buf.append(cardData.getName() + "\t");

		// replaceAll("\\", "") => replace all special characters in the string
		buf.append(cardData.getManaCost() != null ? "( " + cardData.getManaCost().replaceAll("\\W", "") + " )" : "");
		buf.append("\n\n");
		buf.append(cardData.getType());

		if (cardData.getText() != null) {
			buf.append("\n\n");
			buf.append(cardData.getText());
		}

		String power = cardData.getPower();
		String toughness = cardData.getToughness();

		if (power != null && toughness != null) {
			buf.append("\n\n");
			buf.append(power + " / " + toughness);
		}

		int loyalty = cardData.getLoyalty();

		if (loyalty > 0) {
			buf.append("\n\n");
			buf.append("Loyalty: " + loyalty);
		}
		return buf.toString();
	}

}
