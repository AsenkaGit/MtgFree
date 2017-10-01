package asenka.mtgfree.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

import asenka.mtgfree.model.mtg.mtgcard.MtgCard;

/**
 * Singleton class used to load images. It
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

	/**
	 * 
	 */
	public static final String PREFIX_MTGCARD_FILES = "card_mtg_";

	/**
	 * 
	 */
	private static FileManager singleton = new FileManager();

	/**
	 * 
	 */
	private InputStream backImageInputStream;

	/**
	 * 
	 */
	private FileManager() {

		super();
	}

	/**
	 * 
	 * @return
	 */
	public static FileManager getInstance() {

		return FileManager.singleton;
	}

	/**
	 * 
	 * @param card
	 * @return
	 */
	public InputStream getCardImageInputStream(MtgCard card) {

		InputStream imageInputStream;

		try {
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
	 * 
	 * @param card
	 * @return
	 */
	public File getCardFile(MtgCard card) throws FileNotFoundException {

		final String cardName = card.getName();
		final String cardLanguage = card.getLocale().getLanguage();
		final String cardCollection = card.getCollectionName();
		final String imageFilePath = FILEPATH_MTG_CARD + PREFIX_MTGCARD_FILES + cardLanguage + SEPARATOR + cardCollection + SEPARATOR
				+ cardName + IMAGE_FILE_EXTENSION;
		File file = new File(imageFilePath);

		if (file.exists()) {
			return new File(imageFilePath);
		} else {
			throw new FileNotFoundException("The file " + imageFilePath + " does not exists");
		}
	}

	/**
	 * 
	 * @return an InputStream to the back of the Card Image
	 */
	public InputStream getMtgBackInputStream() {

		if (this.backImageInputStream != null) {
			return this.backImageInputStream;
		} else {

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
			return backImageInputStream;
		}
	}

}
