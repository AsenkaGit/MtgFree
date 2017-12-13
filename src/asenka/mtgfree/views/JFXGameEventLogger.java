package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.controllers.communication.EventType;
import asenka.mtgfree.controllers.communication.SynchronizationEvent;
import asenka.mtgfree.model.Card;
import asenka.mtgfree.model.Player;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JFXGameEventLogger extends ScrollPane {

	private final GameController gameController;

	private final TextFlow logsTextFlow;

	private final Pane logsPane;

	public JFXGameEventLogger(final GameController controller) {

		super();
		this.getStyleClass().add("JFXGameEventLogger");
		this.gameController = controller;
		this.logsTextFlow = new TextFlow();
		this.logsTextFlow.getStyleClass().add("logsTextFlow");
		this.logsPane = new Pane(this.logsTextFlow);

		buildComponentLayout();
		addListeners();
	}

	private void buildComponentLayout() {

		super.setPadding(new Insets(10));
		super.setHbarPolicy(ScrollBarPolicy.NEVER);
		super.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.logsTextFlow.setMaxWidth(CardImageSize.MEDIUM.getWidth() + 40d);
		this.logsTextFlow.setMinHeight(350d);

//		super.vvalueProperty().bind(this.logsTextFlow.heightProperty());

		super.setContent(this.logsPane);
	}

	private void addListeners() {

		this.gameController.getGameTable().gameEventsProperty()
			.addListener((ListChangeListener.Change<? extends SynchronizationEvent> change) -> {

				while (change.next()) {
					if (change.wasAdded()) {
						change.getAddedSubList().forEach(event -> addLog(event));
					}
				}
			});
	}

	private void addLog(final SynchronizationEvent event) {

		if(event.getEventType() != EventType.SET_LOCATION && event.getEventType() != EventType.SET_VISIBLE) {
			
			final Player player = (Player) event.getParameters()[0];
			
			Text playerText = new Text(player.getName() + " - ");
			Text typeEventText = new Text(createEventText(event) + "\n\n");
			playerText.setWrappingWidth(CardImageSize.MEDIUM.getWidth() + 40d);
			typeEventText.setWrappingWidth(CardImageSize.MEDIUM.getWidth() + 40d);
			
			playerText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
			typeEventText.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
			
			if (player.equals(this.gameController.getGameTable().getLocalPlayer())) {
				playerText.setFill(Color.RED);
			} else {
				playerText.setFill(Color.BLUE);
			}
			
			Platform.runLater(() -> {
				this.logsTextFlow.getChildren().add(0, typeEventText);
				this.logsTextFlow.getChildren().add(0, playerText);
			});
		}
	}
	
	private static String createEventText(final SynchronizationEvent event) {
		
		final Object[] parameters = event.getParameters();

		switch (event.getEventType()) {
			case CHANGE_CARD_CONTEXT: {
				
				Card card = (Card) parameters[1];
				Context destination = (Context) parameters[3];
				
				switch(destination) {
					case BATTLEFIELD: return "Play " + card.getPrimaryCardData().getName();
					case HAND: return "Take a card from library";
					case GRAVEYARD : return "Destroy " + card.getPrimaryCardData().getName();
					case EXILE : return "Exile " + card.getPrimaryCardData().getName();
					case LIBRARY : return "Put a card in its library";
				}
			}
			case SET_TAPPED: {
				
				Card card = (Card) parameters[1];
				return card.isTapped() ? "Tap " + card.getPrimaryCardData().getName() : "Untap " + card.getPrimaryCardData().getName();
			}
			default:
				return event.getEventType().toString();
		}
	}
}
