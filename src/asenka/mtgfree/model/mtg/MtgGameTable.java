package asenka.mtgfree.model.mtg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import asenka.mtgfree.model.mtg.events.MtgGameTableAddCardEvent;
import asenka.mtgfree.model.mtg.events.MtgGameTableAddPlayerEvent;
import asenka.mtgfree.model.mtg.events.MtgGameTableRemoveCardEvent;
import asenka.mtgfree.model.mtg.events.MtgGameTableRemovePlayerEvent;
import asenka.mtgfree.model.mtg.events.SelectionUpdateEvent;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * This class represents a game table
 * 
 * @author asenka
 */
public class MtgGameTable extends Observable implements Serializable {

	/**
	 * The generated ID used for serialization
	 */
	private static final long serialVersionUID = 0L;

	/**
	 * The table name
	 */
	private String name;

	/**
	 * The maximum number on player on the table
	 */
	private int maxPlayers;

	/**
	 * The players on the table
	 */
	private List<MtgPlayer> players;

	/**
	 * The list of cards on the battlefield
	 */
	private List<MtgCard> cardsOnBattlefield;

	/**
	 * The list of selected cards
	 */
	private List<MtgCard> selectedCards;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param player1
	 * @param player2
	 */
	public MtgGameTable(String name, MtgPlayer player1, MtgPlayer player2) {

		super();
		this.name = name;
		this.players = new ArrayList<MtgPlayer>(2);
		this.players.add(player1);
		this.players.add(player2);
		this.maxPlayers = 2;
		this.cardsOnBattlefield = new ArrayList<MtgCard>();
	}

	/**
	 * @return
	 */
	public String getName() {

		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * @return the maxPlayers
	 */
	public int getMaxPlayers() {

		return maxPlayers;
	}

	/**
	 * @param maxPlayers the maxPlayers to set
	 */
	public void setMaxPlayers(final int maxPlayers) {

		this.maxPlayers = maxPlayers;
	}

	/**
	 * Returns the players on the table
	 * 
	 * @return an unmodifiable list of players
	 */
	public List<MtgPlayer> getPlayers() {

		return Collections.unmodifiableList(players);
	}

	/**
	 * Add a new player on the table and notify the observers
	 * 
	 * @param player
	 */
	public void addPlayer(MtgPlayer player) {

		this.players.add(player);

		super.setChanged();
		super.notifyObservers(new MtgGameTableAddPlayerEvent(player));
	}

	/**
	 * Remove a player from the table and notify the observers
	 * 
	 * @param player
	 */
	public void removePlayer(MtgPlayer player) {

		if (this.players.remove(player)) {

			super.setChanged();
			super.notifyObservers(new MtgGameTableRemovePlayerEvent(player));
		}
	}

	/**
	 * Returns the cards that are on the battlefield
	 * 
	 * @return an unmodifiable list of cards
	 */
	public List<MtgCard> getCardsOnBattlefield() {

		return Collections.unmodifiableList(this.cardsOnBattlefield);
	}

	/**
	 * Add card to the battle field and notify the observers
	 * 
	 * @param card
	 */
	public void addCardToBattlefield(MtgCard card) {

		this.cardsOnBattlefield.add(card);

		super.setChanged();
		MtgGameTableAddCardEvent event = new MtgGameTableAddCardEvent(card);
		super.notifyObservers(event);
	}

	/**
	 * Remove a card from the battlefield and notify the observers
	 * 
	 * @param card the card to remove
	 */
	public void removeCardFromBattlefield(MtgCard card) {

		// If the card was in the battlefield, notify the observers
		if (this.cardsOnBattlefield.remove(card)) {

			super.setChanged();
			super.notifyObservers(new MtgGameTableRemoveCardEvent(card));
		}

	}

	/**
	 * The selected cards
	 * 
	 * @return an unmodifiable list of cards
	 * @see Collections#unmodifiableList(List)
	 */
	public List<MtgCard> getSelectedCards() {

		return Collections.unmodifiableList(selectedCards);
	}

	/**
	 * Set the selected cards and notify the observers
	 * @param selectedCards a list of cards
	 */
	public void setSelectedCards(List<MtgCard> selectedCards) {

		this.selectedCards = selectedCards;
		
		super.setChanged();
		super.notifyObservers(new SelectionUpdateEvent(selectedCards));
	}


	@Override
	public String toString() {

		return "[" + name + ", " + maxPlayers + ", " + players + ", " + cardsOnBattlefield + "]";
	}
}
