import java.io.Serializable;
import java.util.Random;

/**
 * Mänguhaldur juhib mängu loogikat - statistika muutmist, juhuslikke sündmusi jne.
 * Implementeerib Serializable, et koos lemmikloomaga faili salvestada.
 */
public class Mänguhaldur implements Serializable {
    private static final long serialVersionUID = 1L;

    private Lemmikloom lemmikloom;
    private int tegevusteArv = 0; // Loendab tegevusi - iga 3 tegevuse järel juhuslik sündmus

    public Mänguhaldur(Lemmikloom lemmikloom) {
        this.lemmikloom = lemmikloom;
    }

    public Lemmikloom getLemmikloom() {
        return lemmikloom;
    }

    public int getTegevusteArv() {
        return tegevusteArv;
    }

    /**
     * Suurendab tegevuste loendurit ja tagastab, kas on aeg juhuslikuks sündmuseks.
     */
    public boolean lisaTegevus() {
        tegevusteArv++;
        if (tegevusteArv >= 3) {
            tegevusteArv = 0;
            return true; // Aeg juhuslikuks sündmuseks
        }
        return false;
    }

    /**
     * Ravib lemmikut vastavalt valikule.
     * Tagastab kirjelduse, mida GUI-s kuvada.
     */
    public String ravi(int valik) {
        switch (valik) {
            case 0:
                lemmikloom.setTervis(lemmikloom.getTervis() + 10);
                return "Antud ravimid parandasid tervist. Tervis +10p";
            case 1:
                lemmikloom.setTervis(lemmikloom.getTervis() + 20);
                return "Arst ravis su lemmiku terveks. Tervis +20p";
            case 2:
                lemmikloom.setTervis(lemmikloom.getTervis() + 5);
                return "Puhkus aitas. Tervis +5p";
            default:
                throw new SisendErind("Vale ravivalik!");
        }
    }

    /**
     * Peseb lemmikut vastavalt valikule.
     */
    public String pese(int valik) {
        switch (valik) {
            case 0:
                lemmikloom.setPuhtus(lemmikloom.getPuhtus() + 20);
                return "Lemmik sai korralikult pestud. Puhtus +20p";
            case 1:
                lemmikloom.setPuhtus(lemmikloom.getPuhtus() + 10);
                return "Lemmik käis duši all. Puhtus +10p";
            case 2:
                lemmikloom.setPuhtus(lemmikloom.getPuhtus() + 5);
                return "Lemmiku hambad on puhtad. Puhtus +5p";
            default:
                throw new SisendErind("Vale pesemisvalik!");
        }
    }

    /**
     * Rakendab juhusliku sündmuse.
     * Tagastab sündmuse kirjelduse.
     */
    public String rakendaJuhuslikSündmus() {
        Random rand = new Random();
        int sündmus = rand.nextInt(10);
        String sõnum;

        switch (sündmus) {
            case 0:
                lemmikloom.setPuhtus(lemmikloom.getPuhtus() - 25);
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 15);
                sõnum = "💦 Lemmik hüppas porilompi! Puhtus -25p, Meeleolu +15p";
                break;
            case 1:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 10);
                lemmikloom.setPuhtus(lemmikloom.getPuhtus() - 10);
                sõnum = "🦋 Lemmik ajas liblikat taga ja kukkus lillepeenrasse. Meeleolu +10p, Puhtus -10p";
                break;
            case 2:
                lemmikloom.setNälg(lemmikloom.getNälg() + 20);
                lemmikloom.setTervis(lemmikloom.getTervis() - 15);
                sõnum = "🗑️ Lemmik leidis prügikastist 'hõrgutisi'. Söömine +20p, Tervis -15p";
                break;
            case 3:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() - 20);
                sõnum = "⛈️ Äikesetorm ehmatas lemmikut kohutavalt! Meeleolu -20p";
                break;
            case 4:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 20);
                sõnum = "🎾 Lemmik leidis diivani alt vana kadunud mänguasja! Meeleolu +20p";
                break;
            case 5:
                lemmikloom.setTervis(lemmikloom.getTervis() - 20);
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() - 10);
                sõnum = "🐝 Lemmikut nõelas herilane. Tervis -20p, Meeleolu -10p";
                break;
            case 6:
                lemmikloom.setTervis(lemmikloom.getTervis() + 10);
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 10);
                sõnum = "😴 Lemmik magas pika ja kosutava une. Tervis +10p, Meeleolu +10p";
                break;
            case 7:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() - 15);
                sõnum = "😾 Pahur naabrikass susises su lemmiku peale. Meeleolu -15p";
                break;
            case 8:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 25);
                sõnum = "🥰 Sügasid lemmikut täpselt kõrvatagant, nurr garanteeritud! Meeleolu +25p";
                break;
            case 9:
                lemmikloom.setMeeleolu(lemmikloom.getMeeleolu() + 15);
                lemmikloom.setNälg(lemmikloom.getNälg() - 15);
                sõnum = "💨 Lemmik sai 'suumid' ja jooksis toas ringi. Meeleolu +15p, Söömine -15p";
                break;
            default:
                sõnum = "Midagi juhtus...";
        }

        piiraStaatuseid();
        return sõnum;
    }

    /**
     * Hoiab kõik väärtused vahemikus 0-100 ja kontrollib, kas loom surnud.
     * public, et GUI saaks seda kutsuda peale igat statistika muutust.
     */
    public void piiraStaatuseid() {
        if (lemmikloom.getTervis() > 100) lemmikloom.setTervis(100);
        if (lemmikloom.getTervis() < 0) lemmikloom.setTervis(0);

        if (lemmikloom.getPuhtus() > 100) lemmikloom.setPuhtus(100);
        if (lemmikloom.getPuhtus() < 0) lemmikloom.setPuhtus(0);

        if (lemmikloom.getMeeleolu() > 100) lemmikloom.setMeeleolu(100);
        if (lemmikloom.getMeeleolu() < 0) lemmikloom.setMeeleolu(0);

        if (lemmikloom.getNälg() > 100) lemmikloom.setNälg(100);
        if (lemmikloom.getNälg() < 0) lemmikloom.setNälg(0);

        // Kui mõni näitaja on 0, siis lemmik suri
        if (lemmikloom.getTervis() <= 0 || lemmikloom.getNälg() <= 0 || lemmikloom.getMeeleolu() <= 0) {
            lemmikloom.setSurmaSeisund(true);
        }
    }
}
