package pr3;

import java.security.*;
import java.io.*;

/**
 * In diesem Beispiel wird ein RSA-Schluesselpaar erzeugt, anschliessend werden
 * der Öffentliche,Private-Schlüssel in eine Datei gespeichert.
 */
public class RSAKeyCreation {

	// das Schluesselpaar
	private KeyPair keyPair = null;

	/**
	 * Diese Methode generiert ein neues Schluesselpaar.
	 */
	public void generateKeyPair() {
		try {
			// als Algorithmus verwenden wir RSA
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			// mit gewuenschter Schluessellaenge initialisieren
			gen.initialize(2048);
			keyPair = gen.generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			showErrorAndExit("Es existiert kein KeyPairGenerator fuer RSA", ex);

		}
	}

	/**
	 * Die angegebene Nachricht wird signiert und dann zusammen mit der Signatur
	 * und dem oeffentlichen Schluessel (X.509-Format) in eine Datei
	 * gespeichert.
	 */
	public void saveKeyPair(String ownerName) {

		// Inhaber als Byte-Array
		byte[] owner = ownerName.getBytes();

		// der oeffentliche Schluessel vom Schluesselpaar
		PublicKey pubKey = keyPair.getPublic();
		// wir benoetigen die Default-Kodierung
		byte[] pubKeyEnc = pubKey.getEncoded();
		System.out
				.println("Der Public Key wird in folgendem Format gespeichert: "
						+ pubKey.getFormat());

		// der private Schluessel vom Schluesselpaar
		PrivateKey prvKey = keyPair.getPrivate();
		// wir benoetigen die Default-Kodierung
		byte[] prvKeyEnc = prvKey.getEncoded();
		System.out
				.println("Der Private Key wird in folgendem Format gespeichert: "
						+ prvKey.getFormat());

		try {
			// eine Datei wird erzeugt und danach Länge des Inhaber‐Namens
			// (integer), Inhaber‐Name (Bytefolge)
			// Länge des öffentlichen Schlüssels (integer),Öffentlicher-Privater
			// Schlüssel (Bytefolge) [X.509‐Format][PKCS#8]
			writeOut(ownerName, owner, pubKeyEnc,"pub");
			writeOut(ownerName, owner, prvKeyEnc, "prv");

		} catch (IOException ex) {
			showErrorAndExit("Fehler beim Schreiben der signierten Nachricht.",
					ex);
		}

	}

	/**
	 * Diese Methode gibt eine Fehlermeldung sowie eine Beschreibung der
	 * Ausnahme aus. Danach wird das Programm beendet.
	 * 
	 * @param msg
	 *            eine Beschreibung fuer den Fehler
	 * @param ex
	 *            die Ausnahme, die den Fehler ausgeloest hat
	 */

	private void showErrorAndExit(String msg, Exception ex) {
		System.out.println(msg);
		System.out.println(ex.getMessage());
		System.exit(0);
	}

	private void writeOut(String ownerName, byte[] owner, byte[] key,
			String prvOrPub) throws IOException {
		DataOutputStream osPrv = null;
		switch (prvOrPub) {
		case "prv":
			osPrv = new DataOutputStream(new FileOutputStream(ownerName
					+ ".prv"));
		case "pub":
			osPrv = new DataOutputStream(new FileOutputStream(ownerName
					+ ".pub"));
		}

		osPrv.writeInt(owner.length);
		osPrv.write(owner);
		osPrv.writeInt(key.length);
		osPrv.write(key);

		osPrv.close();

	}

	public static void main(String args[]) {
		// Name des Inhabers = 1. Argument der Kommandozeile
		if (args.length < 1) {
			System.out.println("Usage: java RSAKeyCreation ownerName");
		} else {
			RSAKeyCreation keyPair = new RSAKeyCreation();
			// als erstes wird ein neues Schluesselpaar erzeugt
			keyPair.generateKeyPair();
			// pub,privat Schluesselpaar wird in einer Datei gespeichert
			keyPair.saveKeyPair(args[0]);
		}
	}

}
