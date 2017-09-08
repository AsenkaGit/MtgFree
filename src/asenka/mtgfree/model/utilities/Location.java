package asenka.mtgfree.model.utilities;

/**
 * Class used to store a position/location of a card on the battlefield
 * 
 * @author asenka
 */
public class Location {

	/**
	 * 
	 */
	private int x;

	/**
	 * 
	 */
	private int y;

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {

		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {

		return x;
	}

	/**
	 * @param x
	 */
	public void setX(int x) {

		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {

		return y;
	}
	
	/**
	 * 
	 * @param increment
	 */
	public void moveVerticaly(int increment) {
		
		if((y + increment) >= 0) {
			y += increment;
		} else {
			throw new IllegalArgumentException("Try to update y = " + y + " with increment value " + increment);
		}
	}
	
	/**
	 * 
	 * @param increment
	 */
	public void moveHorizontaly(int increment) {
		
		if((x + increment) >= 0) {
			x += increment;
		} else {
			throw new IllegalArgumentException("Try to update x = " + x + " with increment value " + increment);
		}
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {

		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "[" + x + ", " + y + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
