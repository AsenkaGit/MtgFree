package asenka.mtgfree.views.javafx.utilities;

import asenka.mtgfree.model.data.MtgCard;
import javafx.scene.image.Image;

/**
 * 
 * @author asenka
 *
 */
public class ImagesManager {
	
	private static final String IMAGE_CACHE_FOLDER = "/resources/images/cards/";
	
	private static final Image IMAGE_NOT_FOUND;
	
	public static final Image IMAGE_MTG_CARD_BACK;
	
	static {
		
		IMAGE_NOT_FOUND = new Image("file:./resources/images/image_not_found.jpg");
		IMAGE_MTG_CARD_BACK = new Image("file:./resources/images/XXXXXX.jpg");
	}

	private static ImagesManager singleton;

	/**
	 * 
	 */
	private ImagesManager() {

	}

	/**
	 * 
	 * @return
	 */
	public ImagesManager getInstance() {

		if (singleton == null) {
			singleton = new ImagesManager();
		}
		return singleton;
	}
	
	/**
	 * 
	 * @param cardData
	 * @return
	 */
	public Image getImage(MtgCard cardData) {
		
		Image cardImage = getCacheImage(cardData.getMultiverseid());
		
		if (cardImage == null) {
			cardImage = getDistantImage(cardData.getMultiverseid());
			
			if(cardImage != null) {
				saveImageInLocalCache(cardImage, cardData.getMultiverseid());
			}
		}
		return cardImage != null ? cardImage : IMAGE_NOT_FOUND;
	}
	
	/**
	 * 
	 * @param multiverseId
	 * @return
	 */
	private Image getCacheImage(int multiverseId) {
		return null;
		
	}
	
	/**
	 * 
	 * @param multiverId
	 * @return
	 */
	private Image getDistantImage(int multiverId) {
		return null;
		
	}
	
	/**
	 * 
	 * @param image
	 * @param multiverseId
	 */
	private void saveImageInLocalCache(Image image, int multiverseId) {
		
		
	}

}
