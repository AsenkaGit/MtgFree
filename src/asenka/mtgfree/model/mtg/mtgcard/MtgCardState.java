package asenka.mtgfree.model.mtg.mtgcard;

import java.awt.geom.Point2D;

import asenka.mtgfree.model.mtg.exceptions.MtgContextException;

/**
 * This card manage the card state :
 * <ul>
 * <li>tapped/untapped (only on battlefield)</li>
 * <li>visible/hidden (only on battlefield and exile)</li>
 * <li>secret/revealed (only in the player's hand)</li>
 * <li>the context of the card : Library, Hand, Grave yard, etc...)</li>
 * </ul>
 * @author asenka
 */
public class MtgCardState {

	/**
	 * if <code>true</code> => the card is tapped. If the card is not on the battlefield, this value should be false.
	 */
	private boolean tapped;

	/**
	 * If <code>true</code> => the card is visible. Should be false if the card is neither on the battlefield or the exile area.
	 */
	private boolean visible;

	/**
	 * if <code>true</code> => the card is revealed. It means that the card should be visible for all players/viewers. It should be false if
	 * the card is not in the player's hand.
	 */
	private boolean revealed;

	/**
	 * This value indicates whether or not the card is selected. 
	 */
	// TODO Peut être que cette valeur n'est pas nécessaire dans le modèle ?
	private boolean selected;

	/**
	 * Location of the card on the battlefield (only when context = BATTLEFIELD or EXILE or GRAVEYARD)
	 */
	private Point2D.Double location;

	/**
	 * The context of the card
	 */
	private MtgContext context;

	/**
	 * Create a copy of this state
	 * 
	 * @param copyFrom the state to copy
	 */
	MtgCardState(MtgCardState copyFrom) {

		this(copyFrom.tapped, copyFrom.visible, copyFrom.revealed, copyFrom.context,
				new Point2D.Double(copyFrom.location.getX(), copyFrom.location.getY()));
	}

	/**
	 * Full constructor
	 * 
	 * @param isTapped
	 * @param isVisible
	 * @param isRevealed
	 * @param context
	 * @param location
	 */
	MtgCardState(boolean isTapped, boolean isVisible, boolean isRevealed, MtgContext context, Point2D.Double location) {

		super();
		this.tapped = isTapped;
		this.visible = isVisible;
		this.revealed = isRevealed;
		this.context = context;
		this.location = location;
	}

	/**
	 * @return <code>true</code> if the card is tapped
	 */
	boolean isTapped() {

		return tapped;
	}

	/**
	 * @param isTapped <code>true</code> if you want to tap a card on the battlefield
	 * @throws MtgContextException if you try to tap a card that is not on the battlefield
	 */
	void setTapped(boolean isTapped) throws MtgContextException {

		if (isTapped && this.context != MtgContext.BATTLEFIELD) {
			throw new MtgContextException(this, "Try to tap a card that is not on the battlefield");
		} else {
			this.tapped = isTapped;
		}
	}

	/**
	 * The visibility indicates if the card should display the front side or the back side
	 * @return <code>true</code> if the card should be visible on the battlefield
	 */
	boolean isVisible() {

		return visible;
	}

	/**
	 * Set the visibility of the card on the battlefield
	 * 
	 * @param isVisible <code>true</code> if you want to make visible a card on the battlefield or in the exile context
	 */
	void setVisible(boolean isVisible) {

		this.visible = isVisible;
	}

	/**
	 * Set is a card in a player's hand is revealed to other player(s) or not
	 * 
	 * @return <code>true</code> if the card should be seen by others players
	 */
	boolean isRevealed() {

		return revealed;
	}

	/**
	 * Set if a card from the player's hand is revealed to other or not
	 * @param isRevealed <code>true</code> if you want to reveal a card in a player's hand. <code>false</code> to go back to the
	 *        default secret state.
	 * @throws MtgContextException if you try to reveal a card than is not in the HAND context
	 */
	void setRevealed(boolean isRevealed) throws MtgContextException {

		if (isRevealed && this.context != MtgContext.HAND) {
			throw new MtgContextException(this, "Try to set 'isRevealed' to true when context is not HAND");
		} else {
			this.revealed = isRevealed;
		}
	}

	/**
	 * @return <code>true</code> if the card is selected
	 */
	boolean isSelected() {

		return this.selected;
	}

	/**
	 * Set if the card is selected or not
	 * @param selected 
	 */
	void setSelected(boolean selected) {

		this.selected = selected;
	}

	/**
	 * @return the current location of the card
	 * @see Point2D.Double
	 */
	Point2D.Double getLocation() {

		return this.location;
	}

	/**
	 * Set the location of the card
	 * @param location a new Point2D with the new coordinates (x, y)
	 * @see Point2D.Double
	 */
	void setLocation(Point2D.Double location) {

		this.location = location;
	}

	/**
	 * Set the location of the card
	 * @param x
	 * @param y
	 */
	void setLocation(final double x, final double y) {

		this.location.setLocation(x, y);
	}

	/**
	 * @return the context of the card
	 * @see MtgContext
	 */
	MtgContext getContext() {

		return context;
	}

	/**
	 * Set the context of the card
	 * @param context the context of the card
	 * @see MtgContext
	 */
	void setContext(MtgContext context) {

		this.context = context;
	}

	@Override
	public String toString() {

		return "[isTapped=" + tapped + ", isVisible=" + visible + ", isRevealed=" + revealed + ", " + location + ", " + context + "]";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + (revealed ? 1231 : 1237);
		result = prime * result + (tapped ? 1231 : 1237);
		result = prime * result + (visible ? 1231 : 1237);
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
		if (revealed != other.revealed)
			return false;
		if (tapped != other.tapped)
			return false;
		if (visible != other.visible)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	/**
	 * Returns a new initial state :<br />
	 * <ul>
	 * <li><code>isTapped = false</code></li>
	 * <li><code>isVisible = false</code></li>
	 * <li><code>isRevealed = false</code></li>
	 * <li><code>context = OUT_OF_GAME</code></li>
	 * <li><code>location = (-1, -1)</code></li>
	 * </ul>
	 * 
	 * @return a initial card state (when the cards are in the library)
	 */
	static MtgCardState getNewInitialState() {

		return new MtgCardState(false, false, false, MtgContext.OUT_OF_GAME, new Point2D.Double(-1.0, -1.0));
	}
}
