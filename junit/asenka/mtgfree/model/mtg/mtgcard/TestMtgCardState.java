package asenka.mtgfree.model.mtg.mtgcard;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

import asenka.mtgfree.model.mtg.exceptions.MtgContextException;
import asenka.mtgfree.model.mtg.mtgcard.MtgCardState;
import asenka.mtgfree.model.mtg.mtgcard.MtgContext;

/**
 * 
 * @author asenka
 * @see MtgCardState
 */
public class TestMtgCardState {

	private MtgCardState testedState;

	@Before
	public void setUp() {

		this.testedState = MtgCardState.getNewInitialState();
	}

	@Test
	public void testEquals() {

		assertEquals(MtgCardState.getNewInitialState(), MtgCardState.getNewInitialState());
		assertEquals(this.testedState, MtgCardState.getNewInitialState());
		assertEquals(new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, null),
				new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, null));
		assertNotEquals(new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, null),
				new MtgCardState(true, true, true, MtgContext.EXILE, null));

		assertNotEquals(new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, null),
				new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, new Point2D.Double(0, 0)));
	}

	@Test
	public void testHashCode() {

		assertEquals(new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, new Point2D.Double(0, 0)).hashCode(),
				new MtgCardState(true, true, true, MtgContext.BATTLEFIELD, new Point2D.Double(0, 0)).hashCode());
	}

	@Test
	public void testStateUpdate() {

		assertFalse(this.testedState.isRevealed());
		assertFalse(this.testedState.isTapped());
		assertFalse(this.testedState.isVisible());
		assertNull(this.testedState.getLocation());
		assertEquals(this.testedState.getContext(), MtgContext.OUT_OF_GAME);

		try {
			this.testedState.setRevealed(false);
			this.testedState.setRevealed(true);
			fail("MtgContextException expected");
		} catch (MtgContextException e) {
		}

		try {
			this.testedState.setTapped(false);
			this.testedState.setTapped(true);
			fail("MtgContextException expected");
		} catch (MtgContextException e) {
		}

		// TODO Move those tests to MtgCard
//		this.testedState.setContext(MtgContext.HAND);
//		assertFalse(this.testedState.isRevealed());
//		assertFalse(this.testedState.isTapped());
//		assertFalse(this.testedState.isVisible());
//		assertNull(this.testedState.getLocation());
//
//		this.testedState.setContext(MtgContext.BATTLEFIELD);
//		this.testedState.setLocation(125, 241);
//		assertFalse(this.testedState.isRevealed());
//		assertFalse(this.testedState.isTapped());
//		assertEquals(this.testedState.getLocation(), new Point2D.Double(125, 241));
//		this.testedState.setLocation(200, 241);
//		assertEquals(this.testedState.getLocation(), new Point2D.Double(200, 241));
//		this.testedState.setVisible(true);
//		assertTrue(this.testedState.isVisible());
//		this.testedState.setTapped(true);
//
//		this.testedState.setContext(MtgContext.GRAVEYARD);
//		assertFalse(this.testedState.isRevealed());
//		assertFalse(this.testedState.isTapped());
//		assertTrue(this.testedState.isVisible());
//		assertEquals(this.testedState.getLocation(), new Point2D.Double(200, 241));
//
//		this.testedState.setContext(MtgContext.HAND);
//		assertFalse(this.testedState.isRevealed());
//		assertFalse(this.testedState.isTapped());
//		assertFalse(this.testedState.isVisible());
//		assertNull(this.testedState.getLocation());
	}
}
