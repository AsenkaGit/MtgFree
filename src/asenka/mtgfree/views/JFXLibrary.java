package asenka.mtgfree.views;

import asenka.mtgfree.controllers.GameController;
import asenka.mtgfree.model.Player;
import asenka.mtgfree.views.utilities.ImagesManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	
	private final Text localPlayerLife;
	
	private final Text otherPlayerLife;
	
	private final Text localPlayerPoison;
	
	private final Text otherPlayerPoison;
	
	private final Button addLifeButton;
	
	private final Button removeLifeButton;
	
	private final Button addPoisonButton;
	
	private final Button removePoisonButton;
	
	private final ReadOnlyObjectProperty<Player> localPlayerProperty;
	
	private final ObjectProperty<Player> otherPlayerProperty;
	
	
	

	public JFXLibrary(final GameController controller) {

		super();
		this.gameController = controller;
		this.localPlayerProperty = controller.getGameTable().localPlayerProperty();
		this.otherPlayerProperty = controller.getGameTable().otherPlayerProperty();
		this.backCardImageView = new ImageView(ImagesManager.IMAGE_MTG_CARD_BACK);
		this.backCardImageView.setFitHeight(CardImageSize.MEDIUM.getHeigth());
		this.backCardImageView.setFitWidth(CardImageSize.MEDIUM.getWidth());
		
		this.localPlayerLife = new Text();
		this.otherPlayerLife = new Text();
		this.localPlayerPoison = new Text();
		this.otherPlayerPoison = new Text();
		this.addLifeButton = new Button("+");
		this.removeLifeButton = new Button("-");
		this.addPoisonButton = new Button("+");
		this.removePoisonButton = new Button("-");

		buildComponentLayout();
		buildContextMenu();
		addListeners();
	}

	private void buildComponentLayout() {
		
		final Text localPlayerNameText = new Text(this.gameController.getGameTable().getLocalPlayer().getName());
		final Text otherPlayerNameText = new Text("");
		
		this.addLifeButton.setMaxSize(50d, 50d);
		this.removeLifeButton.setMaxSize(50d, 50d);
		this.addPoisonButton.setMaxSize(50d, 50d);
		this.removePoisonButton.setMaxSize(50d, 50d);
		
		HBox localPlayerLifePane = new HBox(new Label("Life: "), this.localPlayerLife, this.addLifeButton, this.removeLifeButton);
		HBox localPlayerPoisonPane = new HBox(new Label("Poison: "), this.localPlayerPoison, this.addPoisonButton, this.removePoisonButton);
		HBox otherPlayerLifePane = new HBox(new Label("Life: "), this.otherPlayerLife);
		HBox otherPlayerPoisonPane = new HBox(new Label("Poison: "), this.otherPlayerPoison);

		super.getChildren().addAll(localPlayerNameText, localPlayerLifePane, localPlayerPoisonPane, 
			otherPlayerNameText, otherPlayerLifePane, otherPlayerPoisonPane, this.backCardImageView);
		
		super.setPadding(new Insets(10));
		GridPane.setConstraints(localPlayerNameText, 	0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(localPlayerLifePane, 	0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(localPlayerPoisonPane, 	0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(otherPlayerNameText, 	0, 3, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(otherPlayerLifePane, 	0, 4, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(otherPlayerPoisonPane, 	0, 5, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		GridPane.setConstraints(this.backCardImageView, 0, 6, 1, 1, HPos.CENTER, VPos.CENTER, Priority.NEVER, Priority.NEVER, new Insets(5));
		
	}

	private void addListeners() {

		this.backCardImageView.setOnMouseClicked(event -> {

			if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 2) {
				this.gameController.draw();
			}
		});
		
		
		final ObjectProperty<Player> otherPlayerProperty = this.gameController.getGameTable().otherPlayerProperty();

		otherPlayerProperty.addListener(observable -> {

			
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
		searchMenuItem.setOnAction(event -> {

			final Stage stage = new Stage();
			stage.setScene(new Scene(new JFXLibraryView(this.gameController)));
			stage.setTitle("Library");
			stage.setMaxWidth(250d);
			stage.setMaxHeight(800d);
			stage.setMinHeight(250d);
			stage.show();
		});

		super.setOnContextMenuRequested(event -> libraryContextMenu.show(this, event.getScreenX(), event.getScreenY()));
	}
}
