Virtuaalne lemmikloom

See on tamagotchi-stiilis mäng, kus kasutaja hoolitseb oma virtuaalse koera, jänese või draakoni eest. Mängu eesmärk on hoida lemmik elus ja õnnelik nii kaua kui võimalik, jälgides tema tervist, nälga, puhtust ja meeleolu ning valides erinevaid tegevusi.

Failid ja klassid

Programm koosneb kaheksast klassist. Peaklass.java on JavaFX põhiklass, mis sisaldab kogu graafilist kasutajaliidest ja meetodit main(), kust programm käivitatakse. Lemmikloom.java on abstraktne põhiklass, mis implementeerib Serializable liidese. Koer.java, Jänes.java ja Draakon.java on Lemmikloom klassi alamklassid. Mänguhaldur.java vastutab mängu loogika eest, sealhulgas statistika hoidmise ja juhuslike sündmuste eest. FailiHaldur.java tegeleb mängu salvestamise ja laadimisega binaarsest failist. SisendErind.java on oma erindiklass valede sisendite jaoks.

Käivitamine IntelliJ IDEA-s

Esiteks tuleb JavaFX SDK alla laadida lehelt https://gluonhq.com/products/javafx/. Vali Windows x64 SDK ja paki see lahti.

Seejärel ava IntelliJ IDEA-s projekt: File, Open ning vali kaust, kus asuvad .java failid.

JavaFX raamatukogu lisamiseks mine File, Project Structure, Libraries ja klõpsa plussmärgile ning vali Java. Liigu JavaFX kausta lib alamkausta ja vali see. Tasub tähele panna, et õige lib kaust asub JavaFX-i sisemises kaustas, mitte juurkaustas. Vale tee oleks näiteks openjfx-26.0.1_windows-x64_bin-sdk\lib, õige tee aga openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib. Lõpuks vajuta Apply ja OK.

Järgmiseks tuleb lisada VM options. Mine Run, Edit Configurations. Kui konfiguratsiooni veel ei ole, klõpsa plussmärgile, vali Application ja määra Main class väärtuseks Peaklass. Leia väli VM options (kui seda ei ole näha, klõpsa Modify options ja vali Add VM options) ning kleebi sinna järgmine tekst, asendades tee oma JavaFX SDK asukohaga:

--module-path "TEE/SINU/JAVAFX/lib" --add-modules javafx.controls

Näiteks võib see välja näha nii:

--module-path "C:\Users\loore-lotta\OneDrive - Tartu Ülikool\Töölaud\kool\OOP\rühmatöö\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls

Pärast Apply ja OK vajutamist saab programmi käivitada rohelise käivitusnupu abil.

Kiirklahvid mängus

Mängus saab tegevusi valida ka klaviatuuri abil. R käivitab ravi, T toitmise, P pesemise, M mängimise ja S salvestamise. Uue mängu aknas kinnitab sisendid Enter ja Escape viib tagasi peamenüüsse.

Mängu salvestus

Salvestus läheb projekti kausta faili nimega lemmikloom_salvestus.dat. Tegemist on binaarse failiga, mis luuakse Serializable liidese abil. Kui lemmik sureb, kustutatakse salvestus automaatselt, et surnud loomaga ei saaks mängu jätkata.

Levinud vead

Kui programm käivitamisel kuvab veateadet "Module javafx.controls not found", on VM options module-path tõenäoliselt vale. Kontrolli, et tee lõpeks osaga \javafx-sdk-XX.X.X\lib, mitte ainult \lib.

Kui kuvatakse "JavaFX runtime components are missing", siis VM options on lisamata ja tuleb tagasi minna seadistuse juurde.

Kui eesti tähed ei tööta korralikult, lisa VM options lõppu -Dfile.encoding=UTF-8.
