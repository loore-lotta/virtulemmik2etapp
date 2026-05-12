/**
 * Draakoni klass - pärib Lemmikloomast.
 */
public class Draakon extends Lemmikloom {
    private static final long serialVersionUID = 1L;

    public Draakon(String nimi, String liik, int vanus) {
        super(nimi, liik, vanus);
    }

    @Override
    public String[] getToiduValikud() {
        return new String[]{"🍖 Liha", "🔥 Sütt", "🌿 Taimed", "💎 Teemant"};
    }

    @Override
    public String[] getMänguValikud() {
        return new String[]{"☁ Lendamine", "🏊 Ujumine", "⚽ Pallimäng", "🗡 Rüütliga võitlus"};
    }

    @Override
    public String söö(int valik) {
        switch (valik) {
            case 0:
                setNälg(getNälg() + 20);
                return "Su draakon sai liha. Söömine +20p";
            case 1:
                setNälg(getNälg() + 5);
                return "Su draakon sai sütt. Söömine +5p";
            case 2:
                setNälg(getNälg() + 10);
                return "Su draakon sõi taimi. Söömine +10p";
            case 3:
                // Teemant on liiga kõva isegi draakonile - lõhub hambad
                setTervis(getTervis() - 35);
                setNälg(getNälg() - 5);
                return "Draakon murdis hambad ära teemanti närides! Tervis -35p, Söömine -5p";
            default:
                throw new SisendErind("Vale toiduvalik draakonile!");
        }
    }

    @Override
    public String mängi(int valik) {
        switch (valik) {
            case 0:
                setMeeleolu(getMeeleolu() + 20);
                return "Käisite lendamas. Meeleolu +20p";
            case 1:
                // Vesi kustutab draakoni tule - vähendab meeleolu
                setMeeleolu(getMeeleolu() - 15);
                setTervis(getTervis() - 10);
                return "Vesi kustutas draakoni sisemise tule. Meeleolu -15p, Tervis -10p";
            case 2:
                setMeeleolu(getMeeleolu() + 5);
                return "Käisite palli mängimas. Meeleolu +5p";
            case 3:
                // Rüütliga võitlus on ohtlik
                setTervis(getTervis() - 30);
                setMeeleolu(getMeeleolu() + 10);
                return "Rüütel haavas draakonit, aga lõbus oli! Tervis -30p, Meeleolu +10p";
            default:
                throw new SisendErind("Vale mänguvalik draakonile!");
        }
    }
}
