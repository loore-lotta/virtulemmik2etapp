import java.io.*;

/**
 * Klass, mis tegeleb mängu salvestamise ja laadimisega failist.
 * Kasutab binaarset I/O ja Serializable liidest (P9 teema - vood).
 * Kõik meetodid püüavad erindid kinni ja tagastavad teate.
 */
public class FailiHaldur {
    // Faili nimi, kuhu mäng salvestatakse
    private static final String FAILI_NIMI = "lemmikloom_salvestus.dat";

    /**
     * Salvestab Mänguhalduri (koos lemmikloomaga) faili.
     * Kasutab ObjectOutputStream-i (puhverdatud) binaarse kirjutamiseks.
     * try-with-resources sulgeb voo automaatselt.
     */
    public static void salvesta(Mänguhaldur mäng) throws IOException {
        try (ObjectOutputStream voog = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(FAILI_NIMI)))) {
            voog.writeObject(mäng);
        }
        // Kui midagi läheb valesti, IOException visatakse edasi ja püütakse GUI-s
    }

    /**
     * Loeb Mänguhalduri failist.
     * Tagastab null, kui faili pole.
     */
    public static Mänguhaldur lae() throws IOException, ClassNotFoundException {
        File fail = new File(FAILI_NIMI);
        if (!fail.exists()) {
            return null; // Faili pole - tagastame null
        }

        try (ObjectInputStream voog = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(FAILI_NIMI)))) {
            return (Mänguhaldur) voog.readObject();
        }
    }

    /**
     * Kontrollib, kas salvestus on olemas (kasutame GUI-s nupu lubamiseks/keelamiseks).
     */
    public static boolean kasSalvestusOnOlemas() {
        return new File(FAILI_NIMI).exists();
    }

    /**
     * Kustutab salvestusfaili (näiteks kui lemmik suri või kasutaja alustab uut mängu).
     */
    public static void kustuta() {
        File fail = new File(FAILI_NIMI);
        if (fail.exists()) {
            fail.delete();
        }
    }
}
