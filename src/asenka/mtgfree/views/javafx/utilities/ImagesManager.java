package asenka.mtgfree.views.javafx.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.data.MtgCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * 
 * @author asenka
 *
 */
public abstract class ImagesManager {

	/**
	 * 
	 */
	public static final Image IMAGE_MTG_CARD_BACK = new Image("file:./resources/images/mtg/cards/back.png");

	/**
	 * The string to replace by a real multiver ID of a card
	 */
	private static final String MULTIVERSE_ID = "[multiverseId]";

	/**
	 * The URL of the web site where the MTG card images can be loaded
	 */
	private static final String IMAGE_URL_WIZARD = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + MULTIVERSE_ID
		+ "&type=card";

	/**
	 * 
	 */
	private static final String IMAGE_CACHE_FOLDER = "./resources/images/mtg/cards/cache/";

	/**
	 * 
	 */
	private static final String IMAGE_FORMAT = "png";

	/**
	 *
	 * @param cardData
	 * @return
	 */
	public static Image getImage(MtgCard cardData) {

		return getImage(cardData.getMultiverseid());
	}

	/**
	 * 
	 * @param multiverseId
	 * @return
	 */
	public static Image getImage(int multiverseId) {

		Image cardImage = getCacheImage(multiverseId);

		if (cardImage == null) {
			cardImage = getDistantImage(multiverseId);

			if (cardImage != null) {
				saveImageInLocalCache(cardImage, multiverseId);
			}
		}
		return cardImage;
	}

	/**
	 * Load and returns a card image from the local folder if it exists.
	 * @param multiverseId the multiverse ID of a card
	 * @return an Image representing a Mtg card, <code>null</code> if the card cannot be loaded 
	 */
	private static Image getCacheImage(int multiverseId) {

		final Image localImage = new Image("file:" + IMAGE_CACHE_FOLDER + multiverseId + "." + IMAGE_FORMAT);
		return localImage.isError() ? null : localImage;
	}

	/**
	 * Load and return an card image from the official wizard of the coast web site plateform
	 * @param multiverseId the multiverse ID of a card
	 * @return an Image representing a Mtg card, <code>null</code> if the card cannot be loaded
	 * @see ImagesManager#IMAGE_URL_WIZARD
	 */
	private static Image getDistantImage(int multiverseId) {

		final Image distantImage = new Image(IMAGE_URL_WIZARD.replace(MULTIVERSE_ID, Integer.toString(multiverseId)));
		return distantImage.isError() ? null : distantImage;
	}

	/**
	 * Save a card image in the local cache folder. The method is run in a specific thread. The exceptions are only logged.
	 * 
	 * @param image the JavaFX image to save
	 * @param multiverseId the multiversid of the card
	 * @see SwingFXUtils
	 * @see ImageIO
	 */
	private static void saveImageInLocalCache(final Image image, int multiverseId) {

		new Thread(() -> {

			// create the output image file
			File outputFile = new File(IMAGE_CACHE_FOLDER + multiverseId + "." + IMAGE_FORMAT);

			// Create a buffered image from the JavaFX image
			BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

			// Write the file
			try {
				ImageIO.write(bufferedImage, IMAGE_FORMAT, outputFile);
			} catch (IOException e) {
				Logger.getLogger(ImagesManager.class).error("Unable to save the image (multiverse ID = " + multiverseId + ").", e);
			}
		}).start();
	}

	// /**
	// * Save a card image in the local cache folder. The method is run in a specific thread. The exceptions are only logged.
	// * @param image the JavaFX image to save
	// * @param cardData the card data related to the card image saved
	// * @see SwingFXUtils
	// * @see ImageIO
	// */
	// private static void saveImageInLocalCache(final Image image, final MtgCard cardData) {
	//
	// saveImageInLocalCache(image, cardData.getMultiverseid());
	// }
}