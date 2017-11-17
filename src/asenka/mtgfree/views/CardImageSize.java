package asenka.mtgfree.views;

import java.awt.geom.Point2D;

/**
 * Enumeration for the three default card sizes
 * 
 * @author asenka
 * @see Point2D.Double
 */
public enum CardImageSize {

	LARGE(223d, 310d), MEDIUM(107, 150), SMALL(71, 100);

	private Point2D.Double size;
	
	private CardImageSize(double width, double height) {

		this.size = new Point2D.Double(width, height);
	}


	/**
	 * 
	 * @return
	 */
	public Point2D.Double getSize() {

		return this.size;
	}

	/**
	 * 
	 * @return
	 */
	public double getHeigth() {

		return this.size.getY();
	}

	/**
	 * 
	 * @return
	 */
	public double getWidth() {

		return this.size.getX();
	}
}
