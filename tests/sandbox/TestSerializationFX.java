package sandbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import asenka.mtgfree.model.data.utilities.MtgDataUtility;

public class TestSerializationFX {

	public static void main(String[] args) {

		Card c = new Card(MtgDataUtility.getInstance().getMtgCard("black lotus"));
		Card c2 = new Card(MtgDataUtility.getInstance().getMtgCard("glorybringer"));

		File file = new File("card.bin");

		// Write the file
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(c);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		// Read the file
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			Card readCard = (Card) in.readObject(); 
			System.out.println(readCard.equals(c));
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.println("Done");
	}

}
