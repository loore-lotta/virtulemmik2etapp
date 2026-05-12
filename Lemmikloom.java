import java.io.Serializable;

/**
 * Abstraktne põhiklass kõikidele lemmikloomadele.
 * Sisaldab ühiseid atribuute (nimi, liik, vanus, tervis, nälg, puhtus, meeleolu).
 * Implementeerib Serializable, et saaksime objekti faili salvestada (P9 teema - vood).
 */
public abstract class Lemmikloom implements Serializable {
    // serialVersionUID on vajalik Serializable klassidele - tagab versioonide ühilduvuse
    private static final long serialVersionUID = 1L;

    private String nimi;
    private String liik;
    private int vanus;
    private int tervis = 50;
    private int nälg = 50;
    private int puhtus = 50;
    private int meeleolu = 50;
    private boolean surmaSeisund = false;

    public Lemmikloom(String nimi, String liik, int vanus) {
        this.nimi = nimi;
        this.liik = liik;
        this.vanus = vanus;
    }

    // Abstraktsed meetodid - igal lemmikloomal on oma toidu- ja mänguvalikud
    // Tagastavad sõnumi, mida GUI-s kuvada
    public abstract String söö(int valik);
    public abstract String mängi(int valik);

    // Toidu- ja mänguvalikute nimekirjad GUI nuppude jaoks
    public abstract String[] getToiduValikud();
    public abstract String[] getMänguValikud();

    // Getterid ja setterid
    public String getNimi() { return nimi; }
    public String getLiik() { return liik; }
    public int getVanus() { return vanus; }
    public int getTervis() { return tervis; }
    public int getNälg() { return nälg; }
    public int getPuhtus() { return puhtus; }
    public int getMeeleolu() { return meeleolu; }
    public boolean isSurmaSeisund() { return surmaSeisund; }

    public void setNimi(String nimi) { this.nimi = nimi; }
    public void setLiik(String liik) { this.liik = liik; }
    public void setVanus(int vanus) { this.vanus = vanus; }
    public void setTervis(int tervis) { this.tervis = tervis; }
    public void setNälg(int nälg) { this.nälg = nälg; }
    public void setPuhtus(int puhtus) { this.puhtus = puhtus; }
    public void setMeeleolu(int meeleolu) { this.meeleolu = meeleolu; }
    public void setSurmaSeisund(boolean surmaSeisund) { this.surmaSeisund = surmaSeisund; }
}
