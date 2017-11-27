package sandbox;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;

public final class CardsManager {

	private static final int PLAYER_ID_MULTIPLICATOR = 1_000;

	private static CardsManager singleton = null;

	private Map<Integer, Card> cards;

	private Map<Player, Integer> battleIdCounters;

	private CardsManager() {

		this.cards = new HashMap<Integer, Card>();
		this.battleIdCounters = new HashMap<Player, Integer>();
	}

	public static CardsManager getInstance() {

		if (singleton == null) {
			singleton = new CardsManager();
		}
		return singleton;
	}

	public synchronized Card createCard(final Player owner, String name) throws IllegalArgumentException, IllegalStateException {

		final MtgCard cardData = MtgDataUtility.getInstance().getMtgCard(name);

		if (cardData != null) {

			Integer battleId = this.battleIdCounters.get(owner);

			if (battleId == null) {
				battleId = owner.getId() * PLAYER_ID_MULTIPLICATOR;
				this.battleIdCounters.put(owner, battleId);
			}
			final Card card = new Card(battleId, cardData);

			if (this.cards.put(battleId, card) == null) {
				incrementBattleIdCounterOf(owner);
				return card;
			} else {
				throw new IllegalStateException("The battle ID " + battleId + " was already used as a key in the cards map.");
			}
		} else {
			throw new IllegalArgumentException("Unable to create the card with the name: " + name);
		}
	}

	public synchronized Card getCard(final int battleId) throws IllegalArgumentException {
	
		Card localCard = this.cards.get(battleId);
	
		if (localCard != null) {
			return localCard;
		} else {
			throw new IllegalArgumentException("The card with battle ID : " + battleId + " can not be found by the card manager.");
		}
	
	}

	public synchronized Card getLocalCard(final Serializable serializedCard) {

		if (serializedCard instanceof Card) {
			
			final Card card = (Card) serializedCard;
			final Integer battleId = Integer.valueOf(card.getBattleId());
			Card localCard = this.cards.get(battleId);
	
			if (localCard == null) {
	
				this.cards.put(battleId, card);
				localCard = card;
			}
			return localCard;
		} else {
			throw new IllegalArgumentException("The serialized data must be a card: " + serializedCard);
		}
	}

	public synchronized void clear() {

		this.cards.clear();
		this.battleIdCounters.clear();
	}

	private void incrementBattleIdCounterOf(final Player player) throws IllegalArgumentException {

		final Integer currentValue = this.battleIdCounters.get(player);

		if (currentValue != null) {
			this.battleIdCounters.put(player, Integer.valueOf(currentValue.intValue() + 1));
		} else {
			throw new IllegalArgumentException("The player " + player + " is does not have a battle ID counter yet.");
		}
	}
}
