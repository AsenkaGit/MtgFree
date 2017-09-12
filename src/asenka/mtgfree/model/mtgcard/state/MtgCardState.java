package asenka.mtgfree.model.mtgcard.state;

import asenka.mtgfree.model.exceptions.MtgContextException;
import asenka.mtgfree.model.utilities.Location;

/**
 * This card manage the card state :<br />
 * > tapped/untapped (only on battlefield)<br />
 * > visible/hidden (only on battlefield and exile)<br />
 * > secret/revealed (only in the player's hand)<br />
 * > the context of the card : Library, Hand, Graveyard, etc...)<br />
 * 
 * @author asenka
 */
public class MtgCardState {

	/**
	 * if true => the card is tapped. If the card is not on the battlefield, this value should be false.
	 */
	private boolean isTapped;

	/**
	 * If true => the card is visible. Should be false if the card is neither on the battlefield or the exile area.
	 */
	private boolean isVisible;

	/**
	 * if true => the card is revealed. It means that the card should be visible for all players/viewers. It should be false if
	 * the card is not in the player's hand.
	 */
	private boolean isRevealed;

	/**
	 * Location of the card on the battlefield (only when context = BATTLEFIELD or EXILE or GRAVEYARD)
	 */
	private Location location; // TODO Est-ce que l'on garde cette valeur ? Peut être que cela fait partie de la vue et non du modèle ?

	/**
	 * The context of the card
	 */
	private MtgContext context; 

	/**
	 * Create a copy of this state
	 * @param copyFrom the state to copy
	 */
	public MtgCardState(MtgCardState copyFrom) {
	
		this(copyFrom.isTapped, copyFrom.isVisible, copyFrom.isRevealed, copyFrom.context, null);
		
		if(copyFrom.location != null) {
			this.setLocation(copyFrom.location.getX(), copyFrom.location.getY());
		}
		
	}

	/**
	 * @param isTapped
	 * @param isVisible
	 * @param isRevealed
	 * @param context
	 * @param location
	 */
	public MtgCardState(boolean isTapped, boolean isVisible, boolean isRevealed, MtgContext context, Location location) {

		super();
		this.isTapped = isTapped;
		this.isVisible = isVisible;
		this.isRevealed = isRevealed;
		this.context = context;
		this.location = location;
	}

	/**
	 * @return the isTapped
	 */
	public boolean isTapped() {

		return isTapped;
	}

	/**
	 * @param isTapped <code>true</code> if you want to tap a card on the battlefield
	 * @throws MtgContextException if you try to tap a card that is not on the battlefield
	 */
	public void setTapped(boolean isTapped) {

		if (isTapped && this.context != MtgContext.BATTLEFIELD) {
			throw new MtgContextException(this, "Try to tap a card that is not on the battlefield");
		} else {
			this.isTapped = isTapped;
		}
	}

	/**
	 * 
	 * @return the isVisible
	 */
	public boolean isVisible() {

		return isVisible;
	}

	/**
	 * @param isVisible <code>true</code> if you want to make visible a card on the battlefield or in the exile context
	 */
	public void setVisible(boolean isVisible) {

		this.isVisible = isVisible;
	}

	/**
	 * @return the isRevealed
	 */
	public boolean isRevealed() {

		return isRevealed;
	}

	/**
	 * 
	 * @param isRevealed <code>true</code> if you want to reveal a card in a player's hand. <code>false</code> to go back to the
	 *        default secret state.
	 * @throws MtgContextException if you try to reveal a card than is not in the HAND context
	 */
	public void setRevealed(boolean isRevealed) {

		if (isRevealed && this.context != MtgContext.HAND) {
			throw new MtgContextException(this, "Try to set 'isRevealed' to true when context is not HAND");
		} else {
			this.isRevealed = isRevealed;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Location getLocation() {

		return location;
	}

	/**
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {

		this.location = location;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {

		if (this.location == null) {
			this.setLocation(new Location(x, y));
		} else {
			this.location.setX(x);
			this.location.setY(y);
		}
	}

	/**
	 * @return the context
	 */
	public MtgContext getContext() {

		return context;
	}

	/**
	 * Change the context of the card. Updating the context change also the values of the booleans parameters of the card state.
	 * <br />
	 * <br />
	 * <i>e.g.</i> Setting the context to HAND, will change the parameters like this:<br />
	 * <code>isRevealed = false</code><br />
	 * <code>isVisible = false</code><br />
	 * <code>location = null</code><br />
	 * <code>isTapped = false</code>
	 * 
	 * @param context the context of the card
	 */
	public void setContext(MtgContext context) {

		switch (context) {
			case LIBRARY:
				this.isRevealed = false;
				this.isTapped = false;
				this.isVisible = false;
				this.location = null;
				break;
			case HAND:
				this.isRevealed = false;
				this.isTapped = false;
				this.isVisible = false;
				this.location = null;
				break;
			case BATTLEFIELD:
				this.isRevealed = false;
				this.isTapped = false;
				break;
			case GRAVEYARD:
				this.isRevealed = false;
				this.isTapped = false;
				this.isVisible = true; // when moving a card to graveyard, it becomes visible automatically
				break;
			case EXILE:
				this.isRevealed = false;
				this.isTapped = false;
				// When moving to exile, the visible status is kept as it was
				break;
		}
		this.context = context;
	}

	@Override
	public String toString() {

		return "[isTapped=" + isTapped + ", isVisible=" + isVisible + ", isRevealed=" + isRevealed + ", " + location + ", " + context + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + (isRevealed ? 1231 : 1237);
		result = prime * result + (isTapped ? 1231 : 1237);
		result = prime * result + (isVisible ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MtgCardState other = (MtgCardState) obj;
		if (context != other.context)
			return false;
		if (isRevealed != other.isRevealed)
			return false;
		if (isTapped != other.isTapped)
			return false;
		if (isVisible != other.isVisible)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	/**
	 * Returns a new initial state (for the card when the game starts) :<br />
	 * <ul>
	 * <li><code>isTapped = false</code></li>
	 * <li><code>isVisible = false</code></li>
	 * <li><code>isRevealed = false</code></li>
	 * <li><code>context = LIBRARY</code></li>
	 * <li><code>location = null</code></li>
	 * </ul>
	 * 
	 * @return a initial card state (when the cards are in the library)
	 */
	public static MtgCardState getNewInitialState() {

		return new MtgCardState(false, false, false, MtgContext.LIBRARY, null);
	}
}
