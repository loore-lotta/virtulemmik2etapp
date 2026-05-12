# 🐾 Virtuaalne lemmikloom

Tamagotchi-stiilis mäng. Hoolitse oma virtuaalse koera, jänese või draakoni eest!

## 📁 Failid ja klassid

| Fail                                            | Roll                                                |
| ----------------------------------------------- | --------------------------------------------------- |
| **Peaklass.java**                               | JavaFX põhiklass, kogu GUI (peaminemeetod `main()`) |
| **Lemmikloom.java**                             | Abstraktne klass, implementeerib `Serializable`     |
| **Koer.java**, **Jänes.java**, **Draakon.java** | Alamklassid (pärimine + polümorfism)                |
| **Mänguhaldur.java**                            | Mängu loogika (statistika, juhuslikud sündmused)    |
| **FailiHaldur.java**                            | Mängu salvestamine ja laadimine binaarsest failist  |
| **SisendErind.java**                            | Oma erindiklass valede sisendite jaoks              |

## 🚀 Käivitamine (IntelliJ IDEA)

### 1. Lae JavaFX alla

https://gluonhq.com/products/javafx/ → vali Windows x64 SDK → paki lahti.

### 2. Ava projekt IntelliJ-s

`File` → `Open` → vali kaust, kus on `.java` failid.

### 3. Lisa JavaFX raamatukogu

- `File` → `Project Structure` → `Libraries` → klõpsa **`+`** → `Java`
- Liigu JavaFX kausta **`lib`** alamkausta ja vali see
- ⚠️ Õige `lib` kaust on **JavaFX-i sisemises kaustas**, mitte juurkaustas!
  - ❌ Vale: `openjfx-26.0.1_windows-x64_bin-sdk\lib`
  - ✅ Õige: `openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib`
- `Apply` → `OK`

### 4. Lisa VM options

- `Run` → `Edit Configurations`
- Kui konfiguratsiooni pole: klõpsa **`+`** → `Application` → `Main class:` vali `Peaklass`
- Leia väli **VM options** (kui ei näe, klõpsa `Modify options` → `Add VM options`)
- Kleebi sinna (asenda oma JavaFX teega):

```
--module-path "TEE/SINU/JAVAFX/lib" --add-modules javafx.controls
```

**Näide:**

```
--module-path "C:\Users\loore-lotta\OneDrive - Tartu Ülikool\Töölaud\kool\OOP\rühmatöö\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\lib" --add-modules javafx.controls
```

- `Apply` → `OK`

### 5. Käivita

Klõpsa rohelist ▶️ nuppu.

## ⌨️ Kiirklahvid

| Klahv  | Tegevus                 |
| ------ | ----------------------- |
| R      | Ravi                    |
| T      | Toida                   |
| P      | Pese                    |
| M      | Mängi                   |
| S      | Salvesta                |
| Enter  | Kinnita uue mängu aknas |
| Escape | Tagasi uue mängu aknast |

## 💾 Mängu salvestus

Salvestus läheb faili **`lemmikloom_salvestus.dat`** projekti kausta.
Kui lemmik sureb, salvestus kustutatakse automaatselt.

## 🚨 Levinud vead

**"Module javafx.controls not found"**
→ VM options module-path on vale. Kontrolli, et tee lõpeks `\javafx-sdk-XX.X.X\lib` (mitte lihtsalt `\lib`).

**"JavaFX runtime components are missing"**
→ Unustasid VM options lisada. Mine tagasi sammu 4 juurde.

**Eesti tähed ei tööta korralikult**
→ `Run` → `Edit Configurations` → VM options lõppu lisa: `-Dfile.encoding=UTF-8`

## ⌨️ Kiirklahvid mängus

| Klahv  | Tegevus                 |
| ------ | ----------------------- |
| R      | Ravi                    |
| T      | Toida                   |
| P      | Pese                    |
| M      | Mängi                   |
| S      | Salvesta                |
| Enter  | Kinnita uue mängu aknas |
| Escape | Tagasi uue mängu aknast |

## 💾 Mängu salvestus

Salvestus läheb faili **`lemmikloom_salvestus.dat`** (binaarne, Serializable).
Kui lemmik sureb, salvestus kustutatakse automaatselt.
