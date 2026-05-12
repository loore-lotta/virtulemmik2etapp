/**
 * Jänese klass - pärib Lemmikloomast.
 */
public class Jänes extends Lemmikloom {
    private static final long serialVersionUID = 1L;

    public Jänes(String nimi, String liik, int vanus) {
        super(nimi, liik, vanus);
    }

    @Override
    public String[] getToiduValikud() {
        return new String[]{"🥕 Porgand", "🍫 Šokolaad", "🥬 Kapsas", "🍄 Seen"};
    }

    @Override
    public String[] getMänguValikud() {
        return new String[]{"🦘 Hüppamine", "🏃 Jooks", "🐕 Mängi koeraga"};
    }

    @Override
    public String söö(int valik) {
        switch (valik) {
            case 0:
                setNälg(getNälg() + 20);
                return "Jänku sai porgandi. Söömine +20p";
            case 1:
                // Šokolaad on jänestele mürgine
                setTervis(getTervis() - 30);
                return "Mida sa teed?? Šokolaad on jänestele mürgine! Tervis -30p";
            case 2:
                setNälg(getNälg() + 10);
                return "Jänku sai kapsa. Söömine +10p";
            case 3:
                // Mürgine seen
                setTervis(getTervis() - 35);
                setNälg(getNälg() - 10);
                return "See seen oli mürgine! Tervis -35p, Söömine -10p";
            default:
                throw new SisendErind("Vale toiduvalik jänesele!");
        }
    }

    @Override
    public String mängi(int valik) {
        switch (valik) {
            case 0:
                setMeeleolu(getMeeleolu() + 20);
                return "Käisite hüppamas. Meeleolu +20p";
            case 1:
                setMeeleolu(getMeeleolu() + 10);
                return "Käisite jooksmas. Meeleolu +10p";
            case 2:
                // Koer hirmutab jänku ära
                setMeeleolu(getMeeleolu() - 20);
                setTervis(getTervis() - 15);
                return "Koer ehmatas jänku ära! Meeleolu -20p, Tervis -15p";
            default:
                throw new SisendErind("Vale mänguvalik jänesele!");
        }
    }
}
