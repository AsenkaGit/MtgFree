package sandbox;

import java.util.HashMap;
import java.util.Map;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.data.utilities.MtgDataUtility;

public final class CardManager {

	private static CardManager singleton = null;

	private Map<Integer, Card> cards;

	private Map<Player, Integer> battleIdCounters;

	private CardManager() {

		this.cards = new HashMap<Integer, Card>();
		this.battleIdCounters = new HashMap<Player, Integer>();
	}

	public CardManager getInstance() {

		if (singleton == null) {
			singleton = new CardManager();
		}
		return singleton;
	}

	public Card createCard(final Player owner, String name) throws GameException {

		Integer battleId = this.battleIdCounters.get(owner);

		if (battleId == null) {
			battleId = owner.getId() * 1_000_000;
			this.battleIdCounters.put(owner, battleId);
		}
		final MtgCard cardData = MtgDataUtility.getInstance().getMtgCard(name);

		if (cardData != null) {
			final Card card = new Card(battleId, cardData);
			this.cards.putIfAbsent(battleId, card);
			battleId++;
			return card;
		} else {
			throw new GameException("Unable to create the card with the name: " + name);
		}
	}

	public Card getLocalCard(final Card serializedCard) {

		final Integer battleId = Integer.valueOf(serializedCard.getBattleId());
		Card localCard = this.cards.get(battleId);

		if (localCard == null) {

			this.cards.put(battleId, serializedCard);
			localCard = serializedCard;
		}
		return localCard;
	}

	public void clear() {

		this.cards.clear();
		this.battleIdCounters.clear();
	}

	// public void addCard(final Card card) {
	//
	// this.cards.putIfAbsent(card.getBattleId(), card);
	// }
	//
	// public Card getCard(final int battleId) {
	//
	// return this.cards.get(Integer.valueOf(battleId));
	// }

}
