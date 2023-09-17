import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static BigInteger p, q, n, e, d;

    public static void main(String[] args) {
        BigInteger start = new BigInteger(256, new Random());
        p = start.nextProbablePrime();
        start = new BigInteger(256, new Random()); // neuer Startwert
        q = start.nextProbablePrime();

        n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // neuer Startwert zwischen 1 und phi
        start = new BigInteger(phi.bitLength() - 1, new Random());
        // generiere e
        while (start.gcd(phi).compareTo(BigInteger.ONE) != 0) {
            start = start.add(BigInteger.ONE);
        }
        e = start;

        d = e.modInverse(phi);

        Scanner sc = new Scanner(System.in);
        System.out.print("Geben Sie Ihre Nachricht an: ");
        String input = sc.nextLine();
        byte[] messageBytes = stringToBytesASCII(input);
        BigInteger[] cipher = new BigInteger[messageBytes.length];

        // Verschlüsseln des Textes
        for (int i = 0; i < messageBytes.length; i++) {
            BigInteger c = new BigInteger("" + messageBytes[i]).modPow(e, n);
            cipher[i] = c;
            System.out.println("c: " + c);
        }

        // Entschlüsseln des Textes
        for (int i = 0; i < messageBytes.length; i++) {
            BigInteger decrypted = cipher[i].modPow(d, n);
            System.out.println(new String(decrypted.toByteArray()));
        }
    }

    public static byte[] stringToBytesASCII(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) str.charAt(i);
        }
        return b;
    }
}