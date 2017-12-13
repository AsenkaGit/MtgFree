package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Card;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class JFXCardsListView extends FlowPane {
	
	public static final int ALL = Integer.MAX_VALUE;

	private final GameController gameController;

	private final ObservableList<Card> cards;
	
	private final Context context;
	
	private int count;

	public JFXCardsListView(final GameController controller, Context context, final int displayXFirst) {

		super();
		this.getStyleClass().add("mtg-pane");
		this.gameController = controller;
		this.count = displayXFirst;
		this.context = context;
		this.cards = GameController.getContextList(context, gameController.getGameTable().getLocalPlayer());
		
		this.cards.forEach(card -> {
			this.gameController.setVisible(card, count-- > 0);
			addCardView(card);
		});
		buildComponentLayout();
		addListeners();
		
	}

	private void addCardView(final Card card) {

		final Pane cardViewPane = new Pane();
		cardViewPane.setPadding(new Insets(5));
		cardViewPane.getChildren().add(new JFXListCardView(card));
		super.getChildren().add(cardViewPane);
	}
	
	private void buildComponentLayout() {
		
		super.setPadding(new Insets(10));
		super.setPrefSize(1200, 600);
	}
	
	private void addListeners() {
		
		this.cards.addListener((ListChangeListener.Change<? extends Card> change) -> {
			
			Platform.runLater(() -> {
				
				super.getChildren().clear();
				this.cards.forEach(card -> addCardView(card));
			});
		});
	}

	private class JFXListCardView extends JFXCardView {

		public JFXListCardView(Card card) {

			super(card, CardImageSize.MEDIUM);
			super.selectSide(card.isVisible() ? Side.FRONT : Side.BACK);
			
			if(card.isVisible()) {
				buildContextMenu();
			}
		}

		private void buildContextMenu() {
			
			final ContextMenu defaultContextMenu = super.getContextMenu();
			final Card card = super.getCard();
			
			MenuItem playMenuItem = new MenuItem("Play");
			MenuItem destroyMenuItem = new MenuItem("Destroy");
			MenuItem exileMenuItem = new MenuItem("Exile");
			MenuItem toHandMenuItem = new MenuItem("Take");
			MenuItem sendToEndMenuItem = new MenuItem("Send to end");
			MenuItem sendToTopMenuItem = new MenuItem("Send to top");
			
			destroyMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.GRAVEYARD, GameController.TOP, false));
			exileMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.EXILE, GameController.TOP, !card.isVisible()));
			playMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.BATTLEFIELD, GameController.TOP, false));
			toHandMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.HAND, GameController.TOP, true));
			sendToEndMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.LIBRARY, GameController.BOTTOM, !card.isVisible()));
			sendToTopMenuItem.setOnAction(event -> gameController.changeCardContext(card, context, Context.LIBRARY, GameController.TOP, !card.isVisible()));
			
			defaultContextMenu.getItems().addAll(playMenuItem, toHandMenuItem, destroyMenuItem, exileMenuItem, sendToEndMenuItem, sendToTopMenuItem);
		}
	}
}
