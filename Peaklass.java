import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.HPos;

import java.io.IOException;

/**
 * Mängu peaklass.
 * Kasutab Application klassi.
 *
 * Mäng on Tamagotchi-stiilis, kus tuleb hoolitseda oma virtuaalse lemmiku eest.
 * Looma näitajad muutuvad vastavalt tegevustele.
 * Iga 3 tegevuse järel toimub juhuslik sündmus.
 */

public class Peaklass extends Application {

    private static final String FONT = "Segoe UI Emoji";

    private Mänguhaldur mäng;         // Mängu olek
    private Stage peaAken;            // Põhiaken
    private TextArea sündmusteLogi;   // Sündmuste kuvamine
    private ProgressBar tervisRiba;   // Statistika kuvamine
    private ProgressBar nälgRiba;
    private ProgressBar puhtusRiba;
    private ProgressBar meeleoluRiba;
    private Label tervisLabel;        // Sildid, mis näitavad protsenti
    private Label nälgLabel;
    private Label puhtusLabel;
    private Label meeleoluLabel;
    private Label lemmikInfoLabel;    // Näitab lemmiku nime, liiki, vanust
    private Label lemmikEmoji;        // Suur emoji lemmiku liigi järgi
    private Button raviNupp, toidaNupp, peseNupp, mängiNupp;  // Põhinupud
    private Button salvestaNupp, väljuNupp;
    private boolean surmaTeadeKuvatud = false;  // Et surmaakent ei näidataks mitu korda

    /**
     * JavaFX peaminemeetod - käivitatakse automaatselt peale launch() kutsumist.
     */
    @Override
    public void start(Stage peaAken) {
        this.peaAken = peaAken;
        peaAken.setTitle("🐾 Virtuaalne lemmikloom 🐾");
        peaAken.setMinWidth(700);
        peaAken.setMinHeight(550);

        // Avakuva
        näitaAvakuva();

        peaAken.show();
    }

    /**
     * Avakuva - kasutaja saab valida uue või laadida pooleli jäänud mängu.
     * Annab ka lühitutvustuse mängust.
     */
    private void näitaAvakuva() {
        VBox juur = new VBox(20);
        juur.setAlignment(Pos.CENTER);
        juur.setPadding(new Insets(30));
        juur.setStyle("-fx-background-color: linear-gradient(to bottom, #ffecd2, #fcb69f);");

        // Pealkiri
        Label pealkiri = new Label("🐾 Virtuaalne lemmikloom 🐾");
        pealkiri.setFont(Font.font(FONT, FontWeight.BOLD, 32));
        pealkiri.setTextFill(Color.web("#5a3825"));

        // Tutvustav tekst
        Label tutvustus = new Label(
                "Tere tulemast! Selles mängus pead hoolitsema oma virtuaalse lemmiku eest.\n\n" +
                        "🎯 EESMÄRK: Hoia oma lemmik elus ja õnnelik nii kaua kui võimalik!\n\n" +
                        "📋 KUIDAS MÄNGIDA:\n" +
                        "  • Sul on 4 näitajat: Tervis, Söömine, Puhtus, Meeleolu (0-100)\n" +
                        "  • Vali tegevusi: Ravi, Toida, Pese, Mängi\n" +
                        "  • Iga 3 tegevuse järel toimub juhuslik sündmus\n" +
                        "  • Kui mõni näitaja jõuab nullini, lemmik sureb!\n\n" +
                        "💾 Saad oma mängu salvestada ja hiljem jätkata.\n" +
                        "⌨️ Klaviatuur: R=Ravi, T=Toida, P=Pese, M=Mängi, S=Salvesta\n" +
                        "Nooleklahvidega saad liikuda läbi valikute"
        );
        tutvustus.setFont(Font.font(FONT, 14));
        tutvustus.setTextFill(Color.web("#5a3825"));
        tutvustus.setWrapText(true);
        tutvustus.setMaxWidth(550);
        tutvustus.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); " +
                "-fx-padding: 20; -fx-background-radius: 15;");

        // Nupp uue mängu jaoks
        Button uusMängNupp = new Button("🆕 Uus mäng");
        stiliseeriPõhinupp(uusMängNupp, "#ff7e5f");
        uusMängNupp.setOnAction(e -> näitaLooMänguAken());

        // Nupp salvestatud mängu laadimiseks
        Button laeMängNupp = new Button("📂 Laadi salvestatud mäng");
        stiliseeriPõhinupp(laeMängNupp, "#6c5ce7");
        // Lubatud ainult siis, kui salvestus on olemas
        laeMängNupp.setDisable(!FailiHaldur.kasSalvestusOnOlemas());
        laeMängNupp.setOnAction(e -> laeMäng());

        Button välju = new Button("🚪 Välju");
        stiliseeriPõhinupp(välju, "#636e72");
        välju.setOnAction(e -> peaAken.close());

        HBox nuppudeKast = new HBox(15, uusMängNupp, laeMängNupp, välju);
        nuppudeKast.setAlignment(Pos.CENTER);

        juur.getChildren().addAll(pealkiri, tutvustus, nuppudeKast);

        Scene stseen = new Scene(juur, 700, 600);
        peaAken.setScene(stseen);
    }

    /**
     * Uue mängu loomise aken - küsib looma nime, liigi ja vanuse.
     */
    private void näitaLooMänguAken() {
        // GridPane sobib hästi vormide jaoks - kaks veergu (silt + sisendväli)
        GridPane vorm = new GridPane();
        vorm.setAlignment(Pos.CENTER);
        vorm.setPadding(new Insets(30));
        vorm.setHgap(15);
        vorm.setVgap(15);
        vorm.setStyle("-fx-background-color: linear-gradient(to bottom, #a8edea, #fed6e3);");

        Label pealkiri = new Label("✨ Loo uus lemmik");
        pealkiri.setFont(Font.font(FONT, FontWeight.BOLD, 26));
        pealkiri.setTextFill(Color.web("#2d3436"));
        // Pealkiri üle 2 veeru
        GridPane.setColumnSpan(pealkiri, 2);
        GridPane.setHalignment(pealkiri, HPos.CENTER);

        // Nime sisestus
        Label nimiSilt = new Label("Looma nimi:");
        nimiSilt.setFont(Font.font(FONT, 14));
        TextField nimiVäli = new TextField();
        nimiVäli.setPromptText("Nt. Reks");

        // Liigi valik - ComboBox annab valikute nimekirja
        Label liikSilt = new Label("Liik:");
        liikSilt.setFont(Font.font(FONT, 14));
        ComboBox<String> liikValik = new ComboBox<>();
        liikValik.getItems().addAll("Koer 🐕", "Jänes 🐰", "Draakon 🐉");
        liikValik.setValue("Koer 🐕");
        liikValik.setMaxWidth(Double.MAX_VALUE); // Täida saadav ruum

        // Vanuse sisestus
        Label vanusSilt = new Label("Vanus (aastates, max 20):");
        vanusSilt.setFont(Font.font(FONT, 14));
        TextField vanusVäli = new TextField();
        vanusVäli.setPromptText("Nt. 3");

        // Kuulaja - kui liiki muudetakse, uuendame vanuse silti maksimaalse vanusega
        liikValik.setOnAction(e -> {
            String valitudLiik = liikValik.getValue();
            if (valitudLiik == null) return;
            String liikSõna = valitudLiik.split(" ")[0]; // Eemaldame emoji
            int max;
            switch (liikSõna) {
                case "Koer":    max = 20; break;
                case "Jänes":   max = 15; break;
                case "Draakon": max = 100; break;
                default:        max = 100;
            }
            vanusSilt.setText("Vanus (aastates, max " + max + "):");
        });

        // Veateate silt - kuvab punaselt vea
        Label viga = new Label();
        viga.setTextFill(Color.RED);
        viga.setFont(Font.font(FONT, FontWeight.BOLD, 13));
        GridPane.setColumnSpan(viga, 2);
        GridPane.setHalignment(viga, HPos.CENTER);

        // Nupud - alusta või tagasi
        Button alustaNupp = new Button("🎮 Alusta mängu");
        stiliseeriPõhinupp(alustaNupp, "#00b894");
        alustaNupp.setOnAction(e -> {
            // Erinditöötlus - kontrollime kasutaja sisendit
            try {
                String nimi = nimiVäli.getText().trim();
                if (nimi.isEmpty()) {
                    throw new SisendErind("Palun sisesta nimi!");
                }

                String liik = liikValik.getValue();
                if (liik == null) {
                    throw new SisendErind("Palun vali liik!");
                }
                // Eemaldame emoji - võtame ainult esimese sõna
                liik = liik.split(" ")[0];

                int vanus;
                try {
                    // NumberFormatException, kui sisend pole arv
                    vanus = Integer.parseInt(vanusVäli.getText().trim());
                } catch (NumberFormatException nfe) {
                    // Püüame ja viskame oma erindi
                    throw new SisendErind("Vanus peab olema täisarv!");
                }
                if (vanus < 0) {
                    throw new SisendErind("Vanus ei saa olla negatiivne!");
                }

                // Liigispetsiifiline maksimaalne vanus
                // Koer kuni 20, jänes kuni 15, draakon kuni 100
                int maxVanus;
                switch (liik) {
                    case "Koer":    maxVanus = 20; break;
                    case "Jänes":   maxVanus = 15; break;
                    case "Draakon": maxVanus = 100; break;
                    default: throw new SisendErind("Tundmatu liik!");
                }
                if (vanus > maxVanus) {
                    throw new SisendErind(liik + " saab olla kuni " + maxVanus + " aastane!");
                }

                // Polümorfism - loome õige alamklassi vastavalt liigile
                Lemmikloom lemmik;
                switch (liik) {
                    case "Koer":    lemmik = new Koer(nimi, liik, vanus); break;
                    case "Jänes":   lemmik = new Jänes(nimi, liik, vanus); break;
                    case "Draakon": lemmik = new Draakon(nimi, liik, vanus); break;
                    default: throw new SisendErind("Tundmatu liik!");
                }

                mäng = new Mänguhaldur(lemmik);
                surmaTeadeKuvatud = false;  // Reset uue mängu jaoks
                näitaMänguAken();
            } catch (SisendErind se) {
                // Kuvame vea kasutajale
                viga.setText("⚠️ " + se.getMessage());
            }
        });

        Button tagasiNupp = new Button("⬅ Tagasi");
        stiliseeriPõhinupp(tagasiNupp, "#636e72");
        tagasiNupp.setOnAction(e -> näitaAvakuva());

        HBox nupudKast = new HBox(15, tagasiNupp, alustaNupp);
        nupudKast.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(nupudKast, 2);
        GridPane.setHalignment(nupudKast, HPos.CENTER);

        // Lisame elemendid GridPane-i: (element, veerg, rida)
        vorm.add(pealkiri, 0, 0);
        vorm.add(nimiSilt, 0, 1);
        vorm.add(nimiVäli, 1, 1);
        vorm.add(liikSilt, 0, 2);
        vorm.add(liikValik, 1, 2);
        vorm.add(vanusSilt, 0, 3);
        vorm.add(vanusVäli, 1, 3);
        vorm.add(viga, 0, 4);
        vorm.add(nupudKast, 0, 5);

        // Veergude laiused - 2. veerg on laiem
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setMinWidth(150);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setMinWidth(200);
        c2.setHgrow(Priority.ALWAYS); // Kasvab akna suurusega
        vorm.getColumnConstraints().addAll(c1, c2);

        Scene stseen = new Scene(vorm, 700, 500);
        // Enter-klahv käivitab "Alusta" nupu (klaviatuuri sündmus)
        stseen.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alustaNupp.fire();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                näitaAvakuva();
            }
        });
        peaAken.setScene(stseen);
    }

    /**
     * Salvestatud mängu laadimise meetod.
     * Kasutab erinditöötlust failioperatsioonide jaoks.
     */
    private void laeMäng() {
        try {
            Mänguhaldur laetudMäng = FailiHaldur.lae();
            if (laetudMäng == null) {
                näitaTeade("Salvestust ei leitud", "Salvestatud mängu ei leitud!", Alert.AlertType.WARNING);
                return;
            }
            mäng = laetudMäng;
            surmaTeadeKuvatud = false;  // Reset laadimisel
            näitaMänguAken();
            lisaLogiSõnum("📂 Mäng laaditud failist!");
        } catch (IOException ioe) {
            näitaTeade("Viga laadimisel", "Faili lugemine ebaõnnestus: " + ioe.getMessage(),
                    Alert.AlertType.ERROR);
        } catch (ClassNotFoundException cnfe) {
            näitaTeade("Viga laadimisel", "Salvestusfail on rikutud!", Alert.AlertType.ERROR);
        }
    }

    /**
     * Põhi mänguaken - kuvab statistika, tegevuste nupud, sündmuste logi.
     * BorderPane jagab akna 5 ossa (üleval, all, vasak, parem, keskel).
     */
    private void näitaMänguAken() {
        BorderPane juur = new BorderPane();
        juur.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffecd2, #fcb69f);");

        // ÜLEMINE OSA - lemmiku info
        VBox ülemine = new VBox(5);
        ülemine.setAlignment(Pos.CENTER);
        ülemine.setPadding(new Insets(15));
        ülemine.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");

        // Suur emoji
        lemmikEmoji = new Label(saaLemmikuEmoji());
        lemmikEmoji.setFont(Font.font(FONT, 60));

        lemmikInfoLabel = new Label();
        lemmikInfoLabel.setFont(Font.font(FONT, FontWeight.BOLD, 20));
        lemmikInfoLabel.setTextFill(Color.web("#5a3825"));

        ülemine.getChildren().addAll(lemmikEmoji, lemmikInfoLabel);
        juur.setTop(ülemine);

        // VASAK OSA - statistika edenemisribadega
        VBox vasak = new VBox(15);
        vasak.setPadding(new Insets(20));
        vasak.setMinWidth(280);
        vasak.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 15;");

        Label statsPealkiri = new Label("📊 Statistika");
        statsPealkiri.setFont(Font.font(FONT, FontWeight.BOLD, 18));

        // Loome 4 edenemisriba
        tervisRiba = new ProgressBar();
        tervisLabel = new Label();
        VBox tervisKast = looStatistikaKast("❤️ \n" + "Tervis", tervisRiba, tervisLabel, "#e74c3c");

        nälgRiba = new ProgressBar();
        nälgLabel = new Label();
        VBox nälgKast = looStatistikaKast("🍽️ \n" + "Söömine", nälgRiba, nälgLabel, "#f39c12");

        puhtusRiba = new ProgressBar();
        puhtusLabel = new Label();
        VBox puhtusKast = looStatistikaKast("🛁 \n" + "Puhtus", puhtusRiba, puhtusLabel, "#3498db");

        meeleoluRiba = new ProgressBar();
        meeleoluLabel = new Label();
        VBox meeleoluKast = looStatistikaKast("😊 \n" + "Meeleolu", meeleoluRiba, meeleoluLabel, "#2ecc71");

        vasak.getChildren().addAll(statsPealkiri, tervisKast, nälgKast, puhtusKast, meeleoluKast);
        juur.setLeft(vasak);

        // Sündmuste logi
        VBox keskel = new VBox(10);
        keskel.setPadding(new Insets(20));

        Label logiPealkiri = new Label("📜 Sündmuste logi");
        logiPealkiri.setFont(Font.font(FONT, FontWeight.BOLD, 18));

        sündmusteLogi = new TextArea();
        sündmusteLogi.setEditable(false); // Kasutaja ei saa muuta
        sündmusteLogi.setFocusTraversable(false); // Ei võta fookust Tab-iga
        sündmusteLogi.setWrapText(true);
        sündmusteLogi.setFont(Font.font(FONT, 13));
        sündmusteLogi.setStyle("-fx-control-inner-background: rgba(255, 255, 255, 0.9); " +
                "-fx-background-radius: 10;");
        VBox.setVgrow(sündmusteLogi, Priority.ALWAYS); // Kasvab koos aknaga

        keskel.getChildren().addAll(logiPealkiri, sündmusteLogi);
        juur.setCenter(keskel);

        // Tegevuste nupud
        GridPane allOsa = new GridPane();
        allOsa.setPadding(new Insets(15));
        allOsa.setHgap(10);
        allOsa.setVgap(10);
        allOsa.setAlignment(Pos.CENTER);
        allOsa.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");

        raviNupp = new Button("❤️ Ravi (R)");
        stiliseeriPõhinupp(raviNupp, "#e74c3c");
        raviNupp.setOnAction(e -> näitaRaviValikud());

        toidaNupp = new Button("🍽️ Toida (T)");
        stiliseeriPõhinupp(toidaNupp, "#f39c12");
        toidaNupp.setOnAction(e -> näitaToiduValikud());

        peseNupp = new Button("🛁 Pese (P)");
        stiliseeriPõhinupp(peseNupp, "#3498db");
        peseNupp.setOnAction(e -> näitaPesuValikud());

        mängiNupp = new Button("⚽ Mängi (M)");
        stiliseeriPõhinupp(mängiNupp, "#2ecc71");
        mängiNupp.setOnAction(e -> näitaMänguValikud());

        salvestaNupp = new Button("💾 Salvesta (S)");
        stiliseeriPõhinupp(salvestaNupp, "#6c5ce7");
        salvestaNupp.setOnAction(e -> salvestaMäng());

        väljuNupp = new Button("🏠 Peamenüü");
        stiliseeriPõhinupp(väljuNupp, "#636e72");
        väljuNupp.setOnAction(e -> näitaAvakuva());

        // Nupud
        allOsa.add(raviNupp, 0, 0);
        allOsa.add(toidaNupp, 1, 0);
        allOsa.add(peseNupp, 2, 0);
        allOsa.add(mängiNupp, 3, 0);
        allOsa.add(salvestaNupp, 0, 1);
        GridPane.setColumnSpan(salvestaNupp, 2);
        allOsa.add(väljuNupp, 2, 1);
        GridPane.setColumnSpan(väljuNupp, 2);

        // Et nupud kasvaksid aknaga, siis kõik veerud on sama laiad
        for (int i = 0; i < 4; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(25);
            allOsa.getColumnConstraints().add(cc);
        }
        // Nupud täidavad oma lahtri laiuse
        raviNupp.setMaxWidth(Double.MAX_VALUE);
        toidaNupp.setMaxWidth(Double.MAX_VALUE);
        peseNupp.setMaxWidth(Double.MAX_VALUE);
        mängiNupp.setMaxWidth(Double.MAX_VALUE);
        salvestaNupp.setMaxWidth(Double.MAX_VALUE);
        väljuNupp.setMaxWidth(Double.MAX_VALUE);

        juur.setBottom(allOsa);

        Scene stseen = new Scene(juur, 1000, 700);

        // Klaviatuuri sündmused
        // addEventFilter püüab klahvivajutused
        stseen.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            // Kui nupud on keelatud (näit. surnud loom), ei reageeri
            if (raviNupp.isDisabled()) return;
            // Kontrollime, kas hetkel pole avatud mõni dialoog
            switch (e.getCode()) {
                case R: raviNupp.fire(); e.consume(); break;
                case T: toidaNupp.fire(); e.consume(); break;
                case P: peseNupp.fire(); e.consume(); break;
                case M: mängiNupp.fire(); e.consume(); break;
                case S: salvestaNupp.fire(); e.consume(); break;
                default: break;
            }
        });

        peaAken.setScene(stseen);
        uuendaGui();
        lisaLogiSõnum("🎮 Mäng alanud! Hoolitse oma lemmiku eest.");
    }

    /**
     * Statistika kast - silt, edenemisriba, protsendinumber.
     */
    private VBox looStatistikaKast(String nimi, ProgressBar riba, Label protsentLabel, String värv) {
        VBox kast = new VBox(3);
        Label nimiSilt = new Label(nimi);
        nimiSilt.setFont(Font.font(FONT, FontWeight.BOLD, 14));

        riba.setMaxWidth(Double.MAX_VALUE);
        riba.setPrefHeight(20);
        riba.setStyle("-fx-accent: " + värv + ";");

        protsentLabel.setFont(Font.font(FONT, 12));

        HBox ülemine = new HBox();
        ülemine.getChildren().addAll(nimiSilt);
        HBox.setHgrow(nimiSilt, Priority.ALWAYS);
        ülemine.getChildren().add(protsentLabel);

        kast.getChildren().addAll(ülemine, riba);
        return kast;
    }

    /**
     * Värskendab GUI-d vastavalt mängu olekule.
     * Kutsutakse peale igat statistika muutust.
     */
    private void uuendaGui() {
        Lemmikloom l = mäng.getLemmikloom();
        lemmikInfoLabel.setText(l.getNimi() + " | " + l.getLiik() + " | " + l.getVanus() + " a.");
        lemmikEmoji.setText(saaLemmikuEmoji());

        // ProgressBar võtab väärtust 0.0 - 1.0
        tervisRiba.setProgress(l.getTervis() / 100.0);
        nälgRiba.setProgress(l.getNälg() / 100.0);
        puhtusRiba.setProgress(l.getPuhtus() / 100.0);
        meeleoluRiba.setProgress(l.getMeeleolu() / 100.0);

        tervisLabel.setText(l.getTervis() + "/100");
        nälgLabel.setText(l.getNälg() + "/100");
        puhtusLabel.setText(l.getPuhtus() + "/100");
        meeleoluLabel.setText(l.getMeeleolu() + "/100");

        // Kontrollime, kas lemmik suri
        if (l.isSurmaSeisund() && !surmaTeadeKuvatud) {
            surmaTeadeKuvatud = true;
            keelustaTegevused();
            näitaSurma();
        }
    }

    /**
     * Tagastab lemmiku liigi järgi emoji.
     */
    private String saaLemmikuEmoji() {
        if (mäng == null) return "🐾";
        String liik = mäng.getLemmikloom().getLiik();
        // Surnud loom kuvatakse risti emojiga
        if (mäng.getLemmikloom().isSurmaSeisund()) return "💀";
        switch (liik) {
            case "Koer":    return "🐕";
            case "Jänes":   return "🐰";
            case "Draakon": return "🐉";
            default:        return "🐾";
        }
    }

    /**
     * Näitab ravivalikute akent
     */
    private void näitaRaviValikud() {
        String[] valikud = {"💊 Anna ravimeid", "👨‍⚕️ Vii arsti juurde", "😴 Lase magada"};
        Integer valik = küsiValik("Kuidas soovid ravida?", "Ravivalik:", valikud);
        if (valik == null) return; // Kasutaja tühistas

        try {
            String tulemus = mäng.ravi(valik);
            mäng.piiraStaatuseid();
            lisaLogiSõnum("❤️ " + tulemus);
            kontrolliTegevus();
        } catch (SisendErind se) {
            näitaTeade("Viga", se.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Toidu valikute aken.
     */
    private void näitaToiduValikud() {
        String[] valikud = mäng.getLemmikloom().getToiduValikud();
        Integer valik = küsiValik("Mida soovid süüa anda?", "Toiduvalik:", valikud);
        if (valik == null) return;

        try {
            String tulemus = mäng.getLemmikloom().söö(valik);
            mäng.piiraStaatuseid();
            lisaLogiSõnum("🍽️ " + tulemus);
            kontrolliTegevus();
        } catch (SisendErind se) {
            näitaTeade("Viga", se.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Pesu valikute aken.
     */
    private void näitaPesuValikud() {
        String[] valikud = {"🛁 Vii lemmik vanni", "🚿 Kiire dušš", "😁 Pese hambad"};
        Integer valik = küsiValik("Kuidas soovid pesta?", "Pesemisvalik:", valikud);
        if (valik == null) return;

        try {
            String tulemus = mäng.pese(valik);
            mäng.piiraStaatuseid();
            lisaLogiSõnum("🛁 " + tulemus);
            kontrolliTegevus();
        } catch (SisendErind se) {
            näitaTeade("Viga", se.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Mängu valikute aken.
     */
    private void näitaMänguValikud() {
        String[] valikud = mäng.getLemmikloom().getMänguValikud();
        Integer valik = küsiValik("Mida soovid mängida?", "Mänguvalik:", valikud);
        if (valik == null) return;

        try {
            String tulemus = mäng.getLemmikloom().mängi(valik);
            mäng.piiraStaatuseid();
            lisaLogiSõnum("⚽ " + tulemus);
            kontrolliTegevus();
        } catch (SisendErind se) {
            näitaTeade("Viga", se.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Üldine valikuaken - ChoiceDialog.
     * Tagastab valitud variandi indeksi või null, kui kasutaja tühistas.
     */
    private Integer küsiValik(String pealkiri, String küsimus, String[] valikud) {
        ChoiceDialog<String> dialoog = new ChoiceDialog<>(valikud[0], valikud);
        dialoog.setTitle(pealkiri);
        dialoog.setHeaderText(küsimus);
        dialoog.setContentText("Vali:");

        // showAndWait() ootab kasutaja vastust ja tagastab Optional<String>
        // .map(...) muundab vastuse indeksiks (kui on)
        // .orElse(null) tagastab null, kui valikut ei tehtud
        return dialoog.showAndWait().map(vastus -> {
            for (int i = 0; i < valikud.length; i++) {
                if (valikud[i].equals(vastus)) return i;
            }
            return -1;
        }).orElse(null);
    }

    /**
     * Pärast iga tegevust kontrollib, kas on aeg juhuslikuks sündmuseks
     * ja uuendab GUI-d.
     */
    private void kontrolliTegevus() {
        if (mäng.lisaTegevus()) {
            // Aeg juhuslikuks sündmuseks
            String sündmus = mäng.rakendaJuhuslikSündmus();
            lisaLogiSõnum("🎲 JUHUSLIK SÜNDMUS: " + sündmus);
        }
        uuendaGui();
    }

    /**
     * Salvestab mängu faili. Erinditöötlus failioperatsiooni jaoks.
     */
    private void salvestaMäng() {
        try {
            FailiHaldur.salvesta(mäng);
            lisaLogiSõnum("💾 Mäng salvestatud!");
            näitaTeade("Salvestatud", "Mäng on edukalt salvestatud!", Alert.AlertType.INFORMATION);
        } catch (IOException ioe) {
            näitaTeade("Viga", "Salvestamine ebaõnnestus: " + ioe.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    /**
     * Lisab logisse sõnumi koos ajatempliga.
     */
    private void lisaLogiSõnum(String sõnum) {
        // Lisame ülaossa, et viimane sündmus oleks alati nähtav
        sündmusteLogi.appendText(sõnum + "\n");
    }

    /**
     * Keelab kõik tegevuste nupud (kui lemmik suri).
     */
    private void keelustaTegevused() {
        raviNupp.setDisable(true);
        toidaNupp.setDisable(true);
        peseNupp.setDisable(true);
        mängiNupp.setDisable(true);
        salvestaNupp.setDisable(true);
    }

    /**
     * Kui lemmik suri, näitame teate ja kustutame salvestuse.
     */
    private void näitaSurma() {
        lisaLogiSõnum("💀 " + mäng.getLemmikloom().getNimi() + " suri ära... Mäng läbi!");
        // Kustutame salvestuse, et surnud loomaga ei saaks jätkata
        FailiHaldur.kustuta();
        näitaTeade("Mäng läbi", mäng.getLemmikloom().getNimi() + " suri ära... 😢\n" +
                "Sa võid uue mängu alustada peamenüüst.", Alert.AlertType.INFORMATION);
    }

    /**
     * Üldine teate näitamise meetod.
     */
    private void näitaTeade(String pealkiri, String sisu, Alert.AlertType tüüp) {
        Alert teade = new Alert(tüüp);
        teade.setTitle(pealkiri);
        teade.setHeaderText(null);
        teade.setContentText(sisu);
        teade.showAndWait();
    }

    /**
     * Meetod, mis lisab nupule stiili (värv, font, padding, hover-efekt).
     */
    private void stiliseeriPõhinupp(Button nupp, String värv) {
        // Tavaline välimus
        String tavaline = "-fx-background-color: " + värv + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: '" + FONT + "'; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20 10 20; " +
                "-fx-background-radius: 10; " +
                "-fx-cursor: hand;";
        // Hover-ajal veidi tumedam
        String hover = "-fx-background-color: derive(" + värv + ", -20%); " +
                "-fx-text-fill: white; " +
                "-fx-font-family: '" + FONT + "'; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20 10 20; " +
                "-fx-background-radius: 10; " +
                "-fx-cursor: hand;";
        nupp.setStyle(tavaline);

        // Hiiresündmused - mouse enter/exit
        nupp.setOnMouseEntered(e -> nupp.setStyle(hover));
        nupp.setOnMouseExited(e -> nupp.setStyle(tavaline));
    }

    /**
     * main() meetod - JavaFX peameetod, kutsub launch().
     */
    public static void main(String[] args) {
        launch(args);
    }
}
