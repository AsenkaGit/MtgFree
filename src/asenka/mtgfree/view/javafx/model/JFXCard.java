package asenka.mtgfree.view.javafx.model;

import asenka.mtgfree.model.data.MtgCard;
import asenka.mtgfree.model.game.Card;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;


public class JFXCard extends Card implements Observable {

	public JFXCard(MtgCard cardData) {
		super(cardData);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addListener(InvalidationListener listener) {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {

		// TODO Auto-generated method stub
		
	}

}
