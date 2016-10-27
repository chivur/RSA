/* Echipa :  
 * 
 * Bucur Maria
 * Chivu Razvan
 * Vasiliu Mihai
 * 
 * Grupa 341 C2
 * 
 * */

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA {

    private BigInteger p, q;
    private BigInteger n;
    private BigInteger z;
    private BigInteger e, d;

    public RSA() {
        initialize();
    }

    public void initialize() {
        int SIZE = 512;
        /* Se aleg p si q numere prime foarte mari */
        p = new BigInteger(SIZE, 15, new Random());
        q = new BigInteger(SIZE, 15, new Random());
        
        /* Calculam n = p x q */
        n = p.multiply(q);
        
        /* Calculam z = (p - 1)x(q - 1) */
        z = p.subtract(BigInteger.valueOf(1));
        z = z.multiply(q.subtract(BigInteger.valueOf(1)));
        
        /* Alegem e astfel inca cmmdc(e,z) = 1 si 1 < e < z */
        do {
            e = new BigInteger(2* SIZE, new Random());
        } while ((e.compareTo(z) != 1)
                || (e.gcd(z).compareTo(BigInteger.valueOf(1)) != 0));
        
        /* Calculam d astfel incat (e x d)mod z = 1 */
        d = e.modInverse(z);
    }

    /**
     * Cripteaza un caracter
     * @param plaintext caracter in clar
     * @return caracterul criptat
     */
    public BigInteger encrypt(BigInteger plaintext) {
        /* ciphertext = ( plaintext ^ e ) mod n */
    	return plaintext.modPow(e, n);
    }

    /**
     * Decripteaza un caracter
     * @param ciphertext caracterul criptat
     * @return caracterul in clar
     */
    public Integer decrypt(BigInteger ciphertext) {
    	/* plaintext = ( ciphertext ^ e ) mod n */
    	return new Integer(ciphertext.modPow(d, n).intValue());
    }
    
    
    public String getInput(){
    	Scanner scanner = new Scanner(System.in);
    	return scanner.nextLine();
    }
    

    public static void main(String[] args) throws IOException {
        RSA app = new RSA();
        
        System.out.println("Introduceti textul pentru criptare : ");
        String str=new String();
        str = app.getInput();
        
        ArrayList<BigInteger> encryptedText = new ArrayList<>();
        
        for(int i=0; i<str.length(); ++i){
        	encryptedText.add(app.encrypt(BigInteger.valueOf(str.charAt(i))));
        }
        
        System.out.println("Text criptat : \n"+encryptedText);
        
        System.out.println("Text decriptat :");
        for(int i=0; i<str.length(); ++i){
        	System.out.print(Character.toChars(app.decrypt(encryptedText.get(i))));
        }   
    }
}
