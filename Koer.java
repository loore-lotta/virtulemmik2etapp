/**
 * Koera klass - pärib Lemmikloomast.
 * Igal koeral on oma toidu- ja mänguvalikud.
 */
public class Koer extends Lemmikloom {
    private static final long serialVersionUID = 1L;

    public Koer(String nimi, String liik, int vanus) {
        super(nimi, liik, vanus);
    }

    @Override
    public String[] getToiduValikud() {
        return new String[]{"🍗 Krõbinad", "🍖 Liha", "🍦 Jäätis", "🍫 Šokolaad"};
    }

    @Override
    public String[] getMänguValikud() {
        return new String[]{"⚽ Pallimäng", "🏃 Jooksmine", "🏊 Ujumine", "🎆 Ilutulestik"};
    }

    @Override
    public String söö(int valik) {
        // Polümorfism - igal loomal oma reageering toidule
        switch (valik) {
            case 0:
                setNälg(getNälg() + 20);
                return "Koerale meeldivad krõbinad. Söömine +20p";
            case 1:
                setNälg(getNälg() + 10);
                return "Liha oli hea valik. Söömine +10p";
            case 2:
                // Jäätis on koerale halb - vähendab tervist
                setTervis(getTervis() - 30);
                return "Jäätis? Tõsiselt ka või? Tervis -30p";
            case 3:
                // Šokolaad on koertele mürgine
                setTervis(getTervis() - 40);
                setMeeleolu(getMeeleolu() - 10);
                return "Šokolaad on koertele MÜRGINE! Tervis -40p, Meeleolu -10p";
            default:
                // Viskame oma erindi, mis püütakse Mänguhalduris kinni
                throw new SisendErind("Vale toiduvalik koerale!");
        }
    }

    @Override
    public String mängi(int valik) {
        switch (valik) {
            case 0:
                setMeeleolu(getMeeleolu() + 20);
                return "Käisite palli mängimas. Meeleolu +20p";
            case 1:
                setMeeleolu(getMeeleolu() + 10);
                return "Käisite jooksmas. Meeleolu +10p";
            case 2:
                setMeeleolu(getMeeleolu() + 5);
                return "Käisite ujumas. Meeleolu +5p";
            case 3:
                // Ilutulestik hirmutab koeri
                setMeeleolu(getMeeleolu() - 25);
                setTervis(getTervis() - 5);
                return "Ilutulestik ehmatas koera kohutavalt! Meeleolu -25p, Tervis -5p";
            default:
                throw new SisendErind("Vale mänguvalik koerale!");
        }
    }
}
