package asenka.mtgfree.controllers;

import java.util.List;

import asenka.mtgfree.model.mtg.MtgGameTable;
import asenka.mtgfree.model.mtg.MtgPlayer;
import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * @author asenka
 *
 */
public class MtgGameTableController {
	
	/**
	 * 
	 */
	private final MtgGameTable gameTable;

	/**
	 * @param gameTable
	 */
	public MtgGameTableController(MtgGameTable gameTable) {
		
		this.gameTable = gameTable;
	}

	/**
	 * @return
	 */
	public MtgGameTable getGameTable() {

		return gameTable;
	}
	
	/**
	 * 
	 * @param player
	 */
	public void addPlayer(final MtgPlayer player) {
		
		this.gameTable.addPlayer(player);
	}
	
	/**
	 * 
	 * @param player
	 */
	public void removePlayer(final MtgPlayer player) {
		
		this.gameTable.removePlayer(player);
	}
	
	/**
	 * 
	 * @param card
	 */
	public void addCardToBattlefield(final MtgCard card) {
		
		this.gameTable.addCardToBattlefield(card);
	}
	
	/**
	 * 
	 * @param card
	 */
	public void removeCardFromBattlefield(final MtgCard card) {
		
		this.gameTable.removeCardFromBattlefield(card);
	}
	
	/**
	 * 
	 * @param selectedCards
	 */
	public void setSelectedCards(List<MtgCard> selectedCards) {
		
		this.gameTable.setSelectedCards(selectedCards);
	}
}
