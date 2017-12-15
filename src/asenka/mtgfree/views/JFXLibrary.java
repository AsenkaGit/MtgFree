package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.controllers.GameController.Context;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.views.utilities.ImagesManager;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Component to manage the library of the local player.
 * 
 * @author asenka
 *
 */
public class JFXLibrary extends GridPane {

	private final GameController gameController;

	private final ImageView backCardImageView;
	
	private final Text localPlayerLifeText;
	
	private final Text otherPlayerLifeText;
	
	private final Text localPlayerPoisonText;
	
	private final Text otherPlayerPoisonText;
	
	private final Text otherPlayerNameText;
	
	private final Button addLifeButton;
	
	private final Button removeLifeButton;
	
	private final Button addPoisonButton;
	
	private final Button removePoisonButton;
	
	private final Button displayGraveyardButton;;
	
	private final Button displayExileButton;

	private final ObjectProperty<Player> otherPlayerProperty;

	public JFXLibrary(final GameController controller) {

		super();
		this.getStyleClass().add("mtg-pane");
		this.getStyleClass().add("JFXLibrary");
		this.gameController = controller;
		this.otherPlayerProperty = controller.getGameTable().otherPlayerProperty();
		this.backCardImageView = new ImageView(ImagesManager.IMAGE_MTG_CARD_BACK);
		this.backCardImageView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
		this.backCardImageView.setFitWidth(CardImageSize.MEDIUM.getWidth());
		
		final Player localPlayer = controller.getGameTable().getLocalPlayer();
		
		this.localPlayerLifeText = new Text(Integer.toString(localPlayer.getLife()));
		this.otherPlayerLifeText = new Text("?");
		this.localPlayerPoisonText = new Text(Integer.toString(localPlayer.getPoison()));
		this.otherPlayerPoisonText = new Text("?");
		this.addLifeButton = new Button("+");
		this.removeLifeButton = new Button("-");
		this.addPoisonButton = new Button("+");
		this.removePoisonButton = new Button("-");
		this.otherPlayerNameText = new Text("");
		this.displayExileButton = new Button("Exile");
		this.displayGraveyardButton = new Button("Graveyard");

		buildComponentLayout();
		buildContextMenu();
		addListeners();
	}

	private void buildComponentLayout() {
		
		this.backCardImageView.getStyleClass().add("JFXCardView");
		
		final Label localPlayerNameLabel = new Label(this.gameController.getGameTable().getLocalPlayer().getName());
		localPlayerNameLabel.getStyleClass().add("strong");
		localPlayerNameLabel.getStyleClass().add("text");
		
		this.addLifeButton.setMaxSize(50d, 50d);
		this.removeLifeButton.setMaxSize(50d, 50d);
		this.addPoisonButton.setMaxSize(50d, 50d);
		this.removePoisonButton.setMaxSize(50d, 50d);
		
		Label labelLocalPlayerLifeLabel = new Label("Life: ");
		Label labelOtherPlayerLifeLabel = new Label("Life: ");
		Label labelLocalPlayerPoisonLabel = new Label("Poison: ");
		Label labelOtherPlayerPoisonLabel = new Label("Poison: ");
		
		this.localPlayerLifeText.getStyleClass().add("text");
		this.localPlayerPoisonText.getStyleClass().add("text");
		this.otherPlayerNameText.getStyleClass().add("text");
		this.otherPlayerNameText.getStyleClass().add("strong");
		this.otherPlayerLifeText.getStyleClass().add("text");
		this.otherPlayerPoisonText.getStyleClass().add("text");

		super.getChildren().addAll(
			localPlayerNameLabel, 
			labelLocalPlayerLifeLabel, localPlayerLifeText, addLifeButton, removeLifeButton,
			labelLocalPlayerPoisonLabel, localPlayerPoisonText, addPoisonButton, removePoisonButton,
			otherPlayerNameText,
			labelOtherPlayerLifeLabel, otherPlayerLifeText,
			labelOtherPlayerPoisonLabel, otherPlayerPoisonText,
			this.backCardImageView,
			this.displayGraveyardButton, 
			this.displayExileButton);
		
		final Insets cellInsets = new Insets(5);
		
		super.setPadding(new Insets(10));
		GridPane.setConstraints(localPlayerNameLabel, 	0, 0, 4, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(labelLocalPlayerLifeLabel, 	0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(localPlayerLifeText, 	1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(addLifeButton, 			2, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(removeLifeButton, 		3, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(labelLocalPlayerPoisonLabel, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(localPlayerPoisonText, 	1, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(addPoisonButton, 		2, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(removePoisonButton, 	3, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(otherPlayerNameText, 	0, 3, 4, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(labelOtherPlayerLifeLabel, 	0, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(otherPlayerLifeText, 	1, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(labelOtherPlayerPoisonLabel, 0, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		GridPane.setConstraints(otherPlayerPoisonText, 	1, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(backCardImageView, 		0, 6, 4, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(displayGraveyardButton, 0, 7, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
		
		GridPane.setConstraints(displayExileButton, 	2, 7, 2, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, cellInsets);
	}

	private void addListeners() {
		
		final Player localPlayer = gameController.getGameTable().getLocalPlayer();
		
		this.localPlayerLifeText.textProperty().bind(localPlayer.lifeProperty().asString());
		this.localPlayerPoisonText.textProperty().bind(localPlayer.poisonProperty().asString());
		
		this.addLifeButton.setOnAction(event -> this.gameController.setPlayerLife(localPlayer.getLife() + 1));
		this.removeLifeButton.setOnAction(event -> this.gameController.setPlayerLife(localPlayer.getLife() - 1));
		this.addPoisonButton.setOnAction(event -> this.gameController.setPlayerPoison(localPlayer.getPoison() + 1));
		this.removePoisonButton.setOnAction(event -> this.gameController.setPlayerPoison(localPlayer.getPoison() - 1));

		this.backCardImageView.setOnMouseClicked(event -> {

			if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 2) {
				this.gameController.draw();
			}
		});
		
		if(this.otherPlayerProperty.isNull().get()) {

			final InvalidationListener listener =  (observable -> {

				@SuppressWarnings("unchecked")
				final ObjectProperty<Player> otherPlayerProperty = (ObjectProperty<Player>) observable;

				if(otherPlayerProperty != null && otherPlayerProperty.isNotNull().get()) {
					this.otherPlayerNameText.textProperty().bind(otherPlayerProperty.get().nameProperty());
					this.otherPlayerLifeText.textProperty().bind(otherPlayerProperty.get().lifeProperty().asString());
					this.otherPlayerPoisonText.textProperty().bind(otherPlayerProperty.get().poisonProperty().asString());
				}
			});	
			this.otherPlayerProperty.addListener(listener);
		} else {
			this.otherPlayerNameText.textProperty().bind(otherPlayerProperty.get().nameProperty());
			this.otherPlayerLifeText.textProperty().bind(otherPlayerProperty.get().lifeProperty().asString());
			this.otherPlayerPoisonText.textProperty().bind(otherPlayerProperty.get().poisonProperty().asString());
		}
		
		this.displayGraveyardButton.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.GRAVEYARD, JFXCardsListView.ALL)));
			stage.setTitle("Graveyard");
			stage.show();
		});
		
		this.displayExileButton.setOnAction(event -> {
			
			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.EXILE, JFXCardsListView.ALL)));
			stage.setTitle("Exile");
			stage.show();
		});
	}

	private void buildContextMenu() {

		ContextMenu libraryContextMenu = new ContextMenu();
		MenuItem drawMenuItem = new MenuItem("Draw");
		MenuItem shuffleMenuItem = new MenuItem("Shuffle");
		Menu searchLibraryMenu = new Menu("Search");
		MenuItem scry1MenuItem = new MenuItem("Scry 1");
		MenuItem scry2MenuItem = new MenuItem("Scry 2");
		MenuItem scry3MenuItem = new MenuItem("Scry 3");
		MenuItem scryXMenuItem = new MenuItem("Scry X");
		MenuItem searchMenuItem = new MenuItem("Search");
		searchLibraryMenu.getItems().addAll(scry1MenuItem, scry2MenuItem, scry3MenuItem, scryXMenuItem, searchMenuItem);
		libraryContextMenu.getItems().addAll(drawMenuItem, shuffleMenuItem, searchLibraryMenu);

		drawMenuItem.setOnAction(event -> this.gameController.draw());
		shuffleMenuItem.setOnAction(event -> this.gameController.shuffle());
		
		scry1MenuItem.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.LIBRARY, 1)));
			stage.setTitle("Library");
			stage.show();
		});
		
		scry2MenuItem.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.LIBRARY, 2)));
			stage.setTitle("Library");
			stage.show();
		});
		
		scry3MenuItem.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.LIBRARY, 3)));
			stage.setTitle("Library");
			stage.show();
		});
		
		scryXMenuItem.setDisable(true);
		
		searchMenuItem.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXCardsListView(this.gameController, Context.LIBRARY, JFXCardsListView.ALL)));
			stage.setTitle("Library");
			stage.show();
		});

		super.setOnContextMenuRequested(event -> libraryContextMenu.show(this, event.getScreenX(), event.getScreenY()));
	}
}
