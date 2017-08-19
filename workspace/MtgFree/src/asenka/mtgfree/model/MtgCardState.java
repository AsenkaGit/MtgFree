package asenka.mtgfree.model;

import asenka.mtgfree.model.exceptions.MtgContextException;
import asenka.mtgfree.utilities.Location;

/**
 * This card manage the card state :<br />
 * > tapped/untapped (only on battlefield)<br />
 * > visible/hidden (only on battlefield and exile)<br />
 * > secret/revealed (only in the player's hand)<br />
 * > the context of the card : Library, Hand, Graveyard, etc...)<br />
 * 
 * @author Asenka
 */
public class MtgCardState {	

	// ##########################################################
	// #			
	// #				Class parameters
	// #	
	// ##########################################################

	/**
	 * if true => the card is tapped. If the card is not on the battlefield, this value should be false.
	 */
	private boolean isTapped;

	/**
	 * If true => the card is visible. Should be false if the card is neither on the battlefield or the exile area.
	 */
	private boolean isVisible;

	/**
	 * if true => the card is revealed. It means that the card  should be visible for all players/viewers. It should be false if the card is not in the player's hand.
	 */
	private boolean isRevealed;
	
	/**
	 * Location of the card on the battlefield (only when context = BATTLEFIELD)
	 */
	private Location location;

	/**
	 * The context (or location) of the card
	 */
	private MtgContext context;

	
	// ##########################################################
	// #			
	// #				Constructors
	// #	
	// ##########################################################
	
	/**
	 * @param isTapped
	 * @param isVisible
	 * @param isRevealed
	 * @param context
	 */
	public MtgCardState(boolean isTapped, boolean isVisible, boolean isRevealed, MtgContext context, Location location) {
		super();
		this.isTapped = isTapped;
		this.isVisible = isVisible;
		this.isRevealed = isRevealed;
		this.context = context;
		this.location = location;
	}

	// ##########################################################
	// #			
	// #				Methods
	// #	
	// ##########################################################

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
		
		if(isTapped && this.context != MtgContext.BATTLEFIELD) {
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
	 * @param isVisible  <code>true</code> if you want to make visible a card on the battlefield 
	 * or in the exile context
	 */
	public void setVisible(boolean isVisible) {
		// TODO Do I need to have a control here throwing a MtgContextException ?
		this.isVisible = isVisible;
	}


	/**
	 * @return the isRevealed
	 */
	public boolean isRevealed() {
		return isRevealed;
	}


	/**
	 * @param isRevealed <code>true</code> if you want to reveal a card in a player's hand. 
	 * <code>false</code> to go back to the default secret state.
	 * @throws MtgContextException if you try to reveal a card than is not in the HAND context
	 */
	public void setRevealed(boolean isRevealed) {

		if(isRevealed && this.context != MtgContext.HAND) {
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
		this.location.setX(x);
		this.location.setY(y);
	}

	/**
	 * @return the context
	 */
	public MtgContext getContext() {
		return context;
	}


	/**
	 * Change the context of the card. Updating the context change also the values
	 * of the booleans parameters of the card state.<br />
	 * <br />
	 * <i>e.g.</i> 
	 * Setting the context to HAND, will change the parameters like this:<br />
	 * <code>isRevealed = false</code><br />
	 * <code>isVisible = false</code><br />
	 * <code>location = null</code><br />
	 * <code>isTapped = false</code>
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
			this.isVisible = true; // TODO Not sure about this one. By default, a card is sent to battlefield visible, 
			// but in some cases, it could be interesting to send it hidden.
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[isTapped=" + isTapped + ", isVisible=" + isVisible + ", isRevealed=" + isRevealed
				+ ", " + location + ", " + context + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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


	
	
	
	

}