package asenka.mtgfree.views;

import java.util.Optional;

import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.views.utilities.ImagesManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A JFX component able to properly display the image(s) of a card. It manages a context menu that can be customized in a subclass
 * to perform actions on the card.
 * 
 * 
 * @author asenka
 * @see ImageView
 * @see Card
 */
public class JFXCardView extends ImageView {

	/**
	 * Enumeration used to manage the side of the card to display
	 */
	public enum Side {
		/** The front side of a card */
		FRONT,

		/** The back of a card */
		BACK,

		/**
		 * For double-faced cards only: on these cards the back is not regular magic card back. Instead it is another card.
		 * 
		 * @see MtgCard#layout
		 */
		OTHER_SIDE;
	}

	/**
	 * The string value of the double face layout card.
	 * 
	 * @see MtgCard#layout
	 * @see Side#OTHER_SIDE
	 */
	private static final String DOUBLE_FACED_LAYOUT = "double-faced";

	/**
	 * The ID of the MenuItem able to switch the side of the double-face cards.
	 */
	public static final String OTHER_SIDE_MENU_ITEM_ID = "otherSideMenuItem";

	/**
	 * The front side of any magic card.
	 */
	private Image primaryCardImage;

	/**
	 * The back side of the double-faced layout cards (rare)
	 */
	private Image secondaryCardImage;

	/**
	 * The back side of all the magic cards (except those with double-faced layout)
	 */
	private final Image backCardImage;

	/**
	 * The side of the card currently displayed
	 */
	private Side currentSide;

	/**
	 * The property containing the card to display. The value of the property can be <code>null</code> if no card is displayed.
	 */
	private final ObjectProperty<Card> cardProperty;

	/**
	 * The context menu of the card
	 */
	private ContextMenu contextMenu;
	
	/**
	 * 
	 */
	private Tooltip tooltip;

	/**
	 * Create a card view without a card. The regular back of a card is displayed
	 * 
	 * @param size the size of the image
	 */
	public JFXCardView(CardImageSize size) {

		this.cardProperty = new SimpleObjectProperty<Card>(this, "card");
		this.backCardImage = ImagesManager.IMAGE_MTG_CARD_BACK;
		this.tooltip = createTooltip();
		Tooltip.install(this, this.tooltip);
		
		setFitHeight(size.getHeigth());
		setFitWidth(size.getWidth());
		selectSide(Side.BACK);
		
		

		// Add the listener that update the card view when the card is changed and set the default card
		this.cardProperty.addListener(observable -> intializeCardView());
	}

	/**
	 * Create a card view with a card
	 * 
	 * @param card
	 * @param size the size of the image
	 */
	public JFXCardView(final Card card, CardImageSize size) {

		this(size);
		setCard(card);
	}

	/**
	 * @return the property of the displayed card
	 */
	protected final ObjectProperty<Card> cardProperty() {

		return this.cardProperty;
	}
	
	/**
	 * @return the card displayed by the card view
	 */
	protected final Card getCard() {
		
		return this.cardProperty.get();
	}

	/**
	 * Change the displayed card
	 * 
	 * @param card the new card (<code>null</code> is allowed)
	 */
	public final void setCard(final Card card) {

		this.cardProperty.set(card);
		this.tooltip.setText(card != null ? createTextForTooltip(card) : "");
	}

	/**
	 * Choose the side of the card to display.
	 * 
	 * @param side the side of the card to display
	 * @throws IllegalStateException if you try to display a side that can not be displayed at this moment
	 */
	public final void selectSide(Side side) throws IllegalStateException {

		// If you try to display the FRONT or the OTHER_SIDE when the card property is null, an exception is raised
		if (this.cardProperty.get() == null && side != Side.BACK) {
			throw new IllegalStateException("The card view has no card to display.");
		} else {
			switch (side) {
				case FRONT:
					setImage(this.primaryCardImage);
					this.tooltip.setText(createTextForTooltip(this.cardProperty.get()));
					break;
				case BACK:
					setImage(this.backCardImage);
					this.tooltip.setText("Card hidden");
					break;
				case OTHER_SIDE:
					if (isDoubleFaced(this.cardProperty.get())) {
						setImage(this.secondaryCardImage);
						this.tooltip.setText(createTextForTooltip(this.cardProperty.get()));
					} else {
						throw new IllegalStateException("Cannot display the other side of a card if it is not double faced.");
					}
			}
			this.currentSide = side;
		}
	}

	/**
	 * Use this method if you need to update or change the update menu of the card view.
	 * @return the existing context menu already installed on the card view (do not create a new one)
	 */
	protected final ContextMenu getContextMenu() {
		
		return this.contextMenu;
	}

	/**
	 * Load the images according to the current value of the card property. If it is <code>null</code>, the regular back of a card
	 * is displayed. You can overwride this method if you want to change the way the card view is displayed.
	 */
	protected void intializeCardView() {

		final Card card = this.cardProperty.get();

		if (card != null) {
			this.primaryCardImage = ImagesManager.getImage(card.getPrimaryCardData());
			this.secondaryCardImage = isDoubleFaced(card) ? ImagesManager.getImage(card.getSecondaryCardData()) : null;
			this.contextMenu = createDefaultContextMenu();
			setOnContextMenuRequested(event -> this.contextMenu.show(this, event.getScreenX(), event.getScreenY()));
			
			
			
			selectSide(Side.FRONT);

		} else {
			this.primaryCardImage = null;
			this.secondaryCardImage = null;
			this.contextMenu = null;
			setOnContextMenuRequested(null);
			selectSide(Side.BACK);
		}
	}

	/**
	 * Create the context menu on the card view.
	 * 
	 * @return a new context menu based on the card property values
	 */
	private ContextMenu createDefaultContextMenu() {
	
		final ContextMenu contextMenu = new ContextMenu();
		final MenuItem detailedInfoMenuItem = new MenuItem("Details");
	
		// TODO Create de details action about a card.
		detailedInfoMenuItem.setOnAction(null);
		detailedInfoMenuItem.setDisable(true);
		contextMenu.getItems().add(detailedInfoMenuItem);
		
//		// This code create a menu displaying all the sets of the cards
//		Menu setMenus = new Menu("Sets");
//		MtgCard cardData = this.cardProperty.get().getPrimaryCardData();
//		final MtgDataUtility du = MtgDataUtility.getInstance();
//		Arrays.stream(cardData.getPrintings()).forEach(set -> setMenus.getItems().add(new MenuItem(du.getMtgSet(set).getName())));
//		contextMenu.getItems().add(setMenus);
	
		// If the card displayed is double-faced, another menu item is added to view the other side of the card
		if (isDoubleFaced(this.cardProperty.get())) {
			final MenuItem otherSideMenuItem = new MenuItem("Other Side");
			otherSideMenuItem.setId(OTHER_SIDE_MENU_ITEM_ID);
			otherSideMenuItem.setOnAction(event -> {
				if (this.currentSide == Side.FRONT) {
					selectSide(Side.OTHER_SIDE);
				} else {
					selectSide(Side.FRONT);
				}
			});
			contextMenu.getItems().add(otherSideMenuItem);
		}
		return contextMenu;
	}

	/**
	 * @param card the card to check
	 * @return <code>true</code> if the card is double-faced, <code>false</code> otherwise
	 * @see MtgCard#layout
	 */
	protected static final boolean isDoubleFaced(final Card card) {

		return card != null ? DOUBLE_FACED_LAYOUT.equals(card.getPrimaryCardData().getLayout()) : false;
	}

	/**
	 * Create a tooltip with the text from a card
	 * @param card the card
	 * @return the tooltip to install with the text about the card
	 */
	private static Tooltip createTooltip() {

		Tooltip tooltip = new Tooltip("");
		tooltip.setWrapText(true);
		tooltip.setMaxWidth(300);
		tooltip.setStyle("-fx-font-size:12px;");
		return tooltip;
	}

	/**
	 * Build a string displaying all the major data about the card. The method is able to manage the double cards
	 * 
	 * @param card the card
	 * @return a string with some data about the card
	 */
	private static String createTextForTooltip(final Card card) {

		StringBuffer buf = new StringBuffer();
		// buf.append("[" + card.getBattleId() + "]\n");
		buf.append(createTextForTooltip(card.getPrimaryCardData()));

		final MtgCard secondaryCardData = card.getSecondaryCardData();

		if (secondaryCardData != null) {
			buf.append("\n--------------------------------------------------------\n");
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
		String manaCost = cardData.getManaCost() != null ? cardData.getManaCost() : " ";
		manaCost = manaCost.replace('}', ',');
		manaCost = manaCost.replace('{', Character.MIN_VALUE);
		manaCost = manaCost.substring(0, manaCost.length()-1);
		
		buf.append(manaCost.isEmpty() ? "" : "( " + manaCost + " )");
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

	
	/**
	 * Look for a menu item with a specific ID in a context menu
	 * 
	 * @param contextMenu the context menu
	 * @param id the id of the menu item in the context menu to look for
	 * @return the menu item id with the desired ID or <code>null</code>
	 */
	public static final MenuItem findMenuItemByID(final ContextMenu contextMenu, String id) {

		// Return one and only one menu item matching the ID
		final Optional<MenuItem> result = contextMenu.getItems().stream().filter(item -> id.equals(item.getId())).findFirst();
		return result.isPresent() ? result.get() : null;
	}
}
