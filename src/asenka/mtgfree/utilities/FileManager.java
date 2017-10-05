package asenka.mtgfree.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Singleton class used to load data from files
 * 
 * @author asenka
 *
 */
public final class FileManager {

	private static final Logger LOGGER = Logger.getLogger(FileManager.class);

	private static final char SEPARATOR = '_';

	private static final String FILEPATH_IMAGE_NOT_FOUND = "./resources/images/image_not_found.png";

	private static final String IMAGE_FILE_EXTENSION = ".png";

	private static final String FILEPATH_MTG_CARD = "./resources/images/mtg/cards/";

	private static final String FILENAME_MTG_CARD_BACK = "card_mtg_back.jpg";

	public static final String PREFIX_MTGCARD_FILES = "card_mtg_";

	private static FileManager singleton = new FileManager();

	/**
	 * The back of the Mtg card image will be loaded a lot of time. The unique instance of the file manager
	 * keep an open input stream to the file in order to limit to number of access to this file.
	 */
	private InputStream backImageInputStream;

	/**
	 * Private default constructor. This class cannot be instanciate outside with a constructor
	 */
	private FileManager() {

		super();
	}

	/**
	 * Design pattern Singleton method.
	 * @return the unique instance of the file manager
	 */
	public static FileManager getInstance() {

		return FileManager.singleton;
	}

	/**
	 * Returns the input stream to the file containing the card front image 
	 * @param card the mtg card you want
	 * @return an InputStream to an image file. If the card image is not found, it returns a default image
	 */
	public InputStream getCardImageInputStream(MtgCard card) {

		InputStream imageInputStream;

		try {
			// Create an input stream from the file related to the card image
			imageInputStream = new FileInputStream(getCardFile(card));
		} catch (FileNotFoundException e) {
			LOGGER.error("The image of the card " + card.getName() + " can't be loaded." + "\n\tEXCEPTION MESSAGE: " + e.getMessage());
			try {
				imageInputStream = new FileInputStream(new File(FILEPATH_IMAGE_NOT_FOUND));
			} catch (FileNotFoundException e1) {
				LOGGER.fatal(e1.getMessage());
				imageInputStream = null;
			}
		}
		return imageInputStream;
	}

	/**
	 * Returns the file containing the card front image 
	 * @param card the card you need
	 * @return a File containing the card image
	 * @throws FileNotFoundException if the image cannot be found
	 */
	public File getCardFile(MtgCard card) throws FileNotFoundException {

		// Get the necessary data to look for the image
		final String cardName = card.getName();
		final String cardLanguage = card.getLocale().getLanguage();
		final String cardCollection = card.getCollectionName();
		final String imageFilePath = FILEPATH_MTG_CARD + PREFIX_MTGCARD_FILES + cardLanguage + SEPARATOR + cardCollection + SEPARATOR
				+ cardName + IMAGE_FILE_EXTENSION;
		File file = new File(imageFilePath);

		// Check if the file does exists
		if (file.exists()) {
			return new File(imageFilePath);
		} else {
			throw new FileNotFoundException("The file " + imageFilePath + " does not exists");
		}
	}

	/**
	 * Returns the back image of a mtg card
	 * @return an InputStream to the back of the Card Image
	 * @throws RuntimeErrorException if neither the expected image can be loaded nor the "file not found" image. 
	 * This scenario should not happen and is considered as a serious error
	 */
	public InputStream getMtgBackInputStream() {

		// If the file has been already loaded, it returns the existing input stream
		if (this.backImageInputStream != null) {
			return this.backImageInputStream;
		} else {
			// Initialization of the input stream to the image of the back of a card
			try {
				this.backImageInputStream = new FileInputStream(FILEPATH_MTG_CARD + FILENAME_MTG_CARD_BACK);
			} catch (FileNotFoundException e) {
				LOGGER.error("");
				try {
					this.backImageInputStream = new FileInputStream(new File(FILEPATH_IMAGE_NOT_FOUND));
				} catch (FileNotFoundException e1) {
					LOGGER.fatal(e1.getMessage());
					throw new RuntimeErrorException(new Error(e1));
				}
			}
			return this.backImageInputStream;
		}
	}

}
