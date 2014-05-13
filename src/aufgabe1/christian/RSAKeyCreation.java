package aufgabe1.christian;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

public class RSAKeyCreation {
	static final String FILE_PATH = "./out/";
	static final String PUB_KEY_FILE_EXT = ".pub";
	static final String PRV_KEY_FILE_EXT = ".prv";
	static final int RSA_KEY_SIZE = 2048;
	
	private String keyOwnerName; 
	
	private FileOutputStream outFile; 
	
	private KeyPair rsa2048KeyPair;
	
	 public RSAKeyCreation(String name) {
		 this.keyOwnerName = name;
	 }

	public static void main(String[] args) {
		String nameOfKeyOwner = args[0];
		
		RSAKeyCreation keyMaker = new RSAKeyCreation(nameOfKeyOwner);
		
		keyMaker.makePubAndPrvKey();
	}

	public void makePubAndPrvKey() {
		rsa2048KeyPair = this.genKeyPair();
		
		try {
		this.makePublicKey();
		this.makePrivateKey();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	//TODO implement makePrivateKey
	private void makePrivateKey() {
		throw new UnsupportedOperationException("Not Implemented Yet");
	}

	private KeyPair genKeyPair() {
		try {
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(RSA_KEY_SIZE);
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private void makePublicKey() throws FileNotFoundException {
		outFile = new FileOutputStream(FILE_PATH);
		
		
		try (DataOutputStream out = new DataOutputStream(outFile)) { 
		/*
		Der öffentliche Schlüssel soll einer einer Datei <Inhabername>.pub 
		gespeichert werden, deren Struktur wie folgt aussieht:
		*/
		
		//1. Länge des Inhaber‐Namens (integer)
		out.writeInt(keyOwnerName.length());
		//2. Inhaber‐Name (Bytefolge)
		out.write(keyOwnerName.getBytes(Charset.forName("UTF-8")));
		
		//zwischenschritt: jetzt öffentlichen schlüssel holen
		PublicKey pubKey = rsa2048KeyPair.getPublic();
		
		
		//3. Länge des öffentlichen Schlüssels (integer)
		
		//4. Öffentlicher Schlüssel (Bytefolge) [X.509‐Format]
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
