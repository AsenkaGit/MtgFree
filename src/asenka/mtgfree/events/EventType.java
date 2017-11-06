package asenka.mtgfree.events;

/**
 * Enumeration for the type of event
 * 
 * @author asenka
 */
public enum EventType {

	SHUFFLE("shuffle"),
	DRAW("draw"),
	DRAW_X("drawX"),
	PLAY("play"),
	DESTROY("destroy"),
	EXILE("exile"),
	BACK_TO_HAND("backToHand"),
	BACK_TO_TOP_LIBRARY("backToTopOfLibrary"),
	BACK_TO_BOTTOM_LIBRARY("backToBottomOfLibrary"),
	TAP("tap"),
	UNTAP("untap"),
	SHOW("show"),
	HIDE("hide"),
	DO_REVEAL("reveal"),
	UNDO_REVEAL("undoReveal"),
	MOVE("move"),
	ADD_COUNTER("addCounter"),
	REMOVE_COUNTER("removeCounter"),
	CLEAR_COUNTERS("clearCounters"),
	ADD_ASSOCIATED_CARD("addAssociatedCard"),
	REMOVE_ASSOCIATED_CARD("removeAssociatedCard"),
	CLEAR_ASSOCIATED_CARDS("clearAssociatedCards"),
	ADD_TO_HAND("addToHand"),
	ADD_TO_BATTLEFIELD("addToBattlefield"),
	ADD_TO_GRAVEYARD("addToGraveyard"), 
	ADD_TO_EXILE("addToExile"),
	ADD_TO_LIBRARY("addToLibrary"),
	ADD_TO_LIBRARY_FROM_TOP("addToLibraryFromTop"),
	ADD_TO_LIBRARY_TOP("addToLibraryTop"),
	ADD_TO_LIBRARY_BOTTOM("addToLibraryBottom"),
	CHANGE_CARD_INDEX("changeCardIndex"),
	REMOVE_FROM_LIBRARY("removeFromLibrary"),
	REMOVE_FROM_HAND("removeFromHand"),
	REMOVE_FROM_BATTLEFIELD("removeFromBattlefield"),
	REMOVE_FROM_GRAVEYARD("removeFromGraveyard"),
	REMOVE_FROM_EXILE("removeFromExile"),
	CLEAR_BATTLEFIELD("clearBattlefield"),
	CLEAR_HAND("clearHand"),
	CLEAR_LIBRARY("clearLibrary"),
	CLEAR_GRAVEYARD("clearGraveyard"),
	CLEAR_EXILE("clearExile"),
	REQUEST_JOIN("requestJoin"),
	SYNCHRONIZE_GAMETABLE_DATA("synchronizeGameTableData"),
	PLAYER_JOIN("playerJoin"),
	PLAYER_LEAVE("playerLeave"),
	SET_PLAYER_NAME("setPlayerName"),
	SET_PLAYER_LIFE("setPlayerLife"),
	SET_PLAYER_LIBRARY("setPlayerLibrary"),
	SET_PLAYER_SELECTED_DECK("setPlayerSelectedDeck"),
	ADD_AVAILABLE_DECK("addAvailableDeck"),
	REMOVE_AVAILABLE_DECK("removeAvailableDeck"),
	CLEAR_AVAILABLE_DECKS("clearAvailableDecks"),
	SET_PLAYER_POISON("setPlayerPoison"),
	SET_DECK_NAME("setDeckName"),
	SET_DECK_DESCRIPTION("setDeckDescription"),
	ADD_CARD_TO_MAIN("addCardToMain"),
	ADD_CARD_TO_SIDEBOARD("addCardToSideboard"),
	REMOVE_CARD_FROM_MAIN("removeCardFromMain"),
	REMOVE_CARD_FROM_SIDEBOARD("removeCardFromSideboard"),
	UPDATE_GAME_LOGS("updateGameLogs");

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 * @param name
	 */
	private EventType(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}
	
	@Override
	public String toString() {

		return this.name;
	}
}
