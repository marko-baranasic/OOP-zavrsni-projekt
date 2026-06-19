# WorkoutLog — Priprema za obranu

Dokument za usmenu obranu projekta: govor za predstavljanje, najvjerojatnija pitanja s odgovorima i objašnjenja ključnih dijelova koda.

---

## 1. Govor za predstavljanje projekta (2–3 minute)

> Dobar dan, ja sam Marko Baranašić i predstavit ću svoj projekt **WorkoutLog** — desktop aplikaciju za praćenje treninga izrađenu u Javi koristeći Swing.
>
> Ideja je nastala iz jednostavnog problema: mnogi ljudi koji redovito vježbaju ne vode evidenciju o svojim treninzima, a postojeće mobilne aplikacije često su prekomplicirane ili traže registraciju i internet vezu. WorkoutLog je namjerno jednostavan — radi bez interneta, bez registracije, i sve podatke sprema lokalno na računalu.
>
> Aplikacija je organizirana po **troslojnoj arhitekturi**. U sloju modela su klasa `Workout`, koja predstavlja jedan trening, i enum `Intensity` za razinu intenziteta. U logičkom sloju je `WorkoutLog`, koji drži listu treninga i računa statistiku, te `DataStorage`, koja se brine za spremanje, učitavanje i izvoz datoteka. Prezentacijski sloj je `MainWindow` — Swing sučelje. Sve pokreće klasa `Main`.
>
> Korisnik u lijevom dijelu prozora unosi trening — naziv, trajanje, dan, intenzitet i kategoriju — i klikom na **Add Workout** dodaje ga u listu na desnoj strani, gdje se odmah ažurira i statistika. Podatke može trajno spremiti u binarnu datoteku serijalizacijom, učitati ih natrag pri sljedećem pokretanju, ili izvesti u čitljivu tekstualnu datoteku.
>
> Kroz projekt sam primijenio ključne OOP koncepte: **enkapsulaciju** kroz privatna polja i gettere, **nasljeđivanje** kroz vlastitu iznimku `InvalidInputException` i kroz `MainWindow` koji nasljeđuje `JFrame`, **polimorfizam** kroz prepisanu metodu `toString`, te **obradu iznimaka** pri unosu i radu s datotekama. Za pohranu podataka u memoriji koristim kolekciju `ArrayList`.
>
> Posebnu pažnju posvetio sam jednostavnosti i čitljivosti koda, a svaku klasu i javnu metodu dokumentirao sam Javadoc komentarima. Hvala, spreman sam za pitanja.

---

## 2. Najvjerojatnija pitanja i odgovori

### A) Općenito o projektu

**Što radi tvoja aplikacija?**
Omogućuje korisniku da bilježi treninge (naziv, trajanje, dan, intenzitet, kategoriju, bilješku), pregledava ih u listi, vidi sažetu statistiku te sprema, učitava i izvozi podatke.

**Koji problem rješava?**
Olakšava praćenje treninga bez kompliciranih aplikacija, registracije ili interneta — sve radi lokalno i jednostavno.

**Zašto desktop aplikacija, a ne web ili mobilna?**
Jer je tema kolegija Java sa Swingom, a desktop rješenje ne traži poslužitelj, bazu ni internet — najjednostavnije je i najprikladnije za ovaj opseg.

### B) Arhitektura i struktura

**Objasni strukturu projekta.**
Troslojna arhitektura: sloj **modela** (`Workout`, `Intensity`), **logički** sloj (`WorkoutLog`, `DataStorage`, `InvalidInputException`) i **prezentacijski** sloj (`MainWindow`), plus klasa `Main` kao ulazna točka. Svaki sloj je u svom paketu.

**Zašto si odvojio logiku od sučelja?**
Da svaka klasa ima jednu odgovornost. `WorkoutLog` zna samo upravljati podacima, `DataStorage` samo datotekama, a `MainWindow` samo prikazom. Tako je kod čitljiviji, lakši za održavanje i testiranje.

**Zašto `WorkoutLog` i `DataStorage` kao odvojene klase?**
`WorkoutLog` drži podatke u memoriji i računa statistiku; `DataStorage` ih zapisuje/čita s diska. To su dvije različite odgovornosti pa su i dvije klase.

### C) OOP koncepti

**Gdje koristiš enkapsulaciju?**
Sva polja u `Workout` i `WorkoutLog` su `private`, a pristup je samo kroz javne gettere. Time kontroliram pristup podacima i sprječavam neovlaštene izmjene izvana.

**Gdje koristiš nasljeđivanje?**
Dva mjesta: `InvalidInputException extends Exception` i `MainWindow extends JFrame`. U oba slučaja preuzimam ponašanje roditeljske klase i nadograđujem ga.

**Gdje koristiš polimorfizam?**
Prepisao sam (`@Override`) metodu `toString()` u klasama `Workout` i `Intensity`. Kad `JList` ili `System.out.println` pozovu `toString()`, izvršava se moja verzija, a ne ona iz `Object`.

**Što je apstrakcija u tvom projektu?**
Metoda `getWorkouts()` vraća tip `List`, a ne konkretni `ArrayList` — korisnik metode ne mora znati koja je točno implementacija. Također koristim sučelje `Serializable`.

**Zašto enum `Intensity`, a ne String?**
Enum garantira da intenzitet može biti samo `LIGHT`, `MEDIUM` ili `HARD`. String bi dopustio greške u pisanju koje compiler ne bi uhvatio. Enum daje tip-sigurnost i može imati metode (`getDisplayName()`).

### D) Kolekcije

**Zašto `ArrayList`, a ne obični niz?**
Jer ne znam unaprijed koliko će biti treninga. `ArrayList` se automatski povećava i nudi gotove metode `add`, `remove`, `size`.

**Koja je razlika između `List` i `ArrayList`?**
`List` je sučelje (definira što kolekcija radi), `ArrayList` je konkretna implementacija (kako to radi). U poljima koristim `ArrayList`, a vraćam tip `List` radi apstrakcije.

### E) Rad s datotekama

**Što je serijalizacija?**
Pretvaranje Java objekta u niz bajtova koji se može zapisati u datoteku. Deserijalizacija je obrnuti postupak — čitanje bajtova natrag u objekt.

**Što omogućuje sučelje `Serializable`?**
To je "marker" sučelje bez metoda; označava da se klasa smije serijalizirati. Bez njega bi serijalizacija bacila `NotSerializableException`.

**Čemu služi `serialVersionUID`?**
To je identifikator verzije klase. Pomaže pri provjeri kompatibilnosti spremljenih podataka ako se klasa kasnije promijeni.

**Zašto dvije vrste datoteka?**
Binarna (`workouts.dat`) za trajnu pohranu (brza, čuva točan objekt) i tekstualna (`workouts_export.txt`) za korisnika (čitljiva, može se otvoriti u bilo kojem editoru).

**Što je `try-with-resources`?**
Oblik `try` bloka koji automatski zatvara resurse (streamove) na kraju, čak i ako dođe do iznimke. Tako ne moram ručno pisati `finally` i `close()`.

### F) Iznimke

**Razlika između checked i unchecked iznimke?**
Checked (`extends Exception`) compiler zahtijeva da obradiš (`try-catch` ili `throws`). Unchecked (`extends RuntimeException`) ne moraš obraditi. Moja `InvalidInputException` je checked jer želim da se loš unos uvijek svjesno obradi.

**Zašto si napravio vlastitu iznimku?**
Da jasno i čitljivo prijavim grešku u korisničkom unosu (npr. prazno ime, neispravno trajanje) i da pokažem primjenu nasljeđivanja.

**Gdje sve hvataš iznimke?**
Pri unosu (`InvalidInputException`, `NumberFormatException`) i pri radu s datotekama (`IOException`, `ClassNotFoundException`). Korisniku se greška prikaže kroz `JOptionPane`.

**Što se dogodi ako u trajanje upišeš slovo?**
`Integer.parseInt` baci `NumberFormatException`, ja je uhvatim i pretvorim u `InvalidInputException` s jasnom porukom korisniku.

### G) Swing / GUI

**Koje Swing komponente koristiš?**
`JFrame`, `JPanel`, `JTextField`, `JTextArea`, `JComboBox`, `JRadioButton` + `ButtonGroup`, `JCheckBox`, `JButton`, `JList` + `DefaultListModel`, `JLabel`, `JMenuBar`, `JToolBar`, `JOptionPane`, `JScrollPane`.

**Što je EDT i zašto `SwingUtilities.invokeLater`?**
EDT (*Event Dispatch Thread*) je dretva na kojoj Swing izvodi sve GUI operacije. `invokeLater` osigurava da se prozor stvori na toj dretvi, što je pravilan i siguran način pokretanja Swing aplikacije.

**Čemu služi `ButtonGroup`?**
Grupira radio gumbe tako da može biti odabran samo jedan intenzitet istovremeno.

**Kako radi gumb "Add Workout"?**
Na njega je vezan `ActionListener` (lambda) koji poziva metodu `handleAddWorkout()`. Ona čita formu, validira unos, stvara `Workout` i dodaje ga u `WorkoutLog`, pa osvježi prikaz.

**Kako se lista osvježava?**
Metoda `refreshList()` očisti `DefaultListModel` i ponovno ga napuni iz `WorkoutLog`. `JList` prikazuje sadržaj modela.

**Zašto trening može biti i cardio i snaga?**
Jer kategoriju predstavljaju dva odvojena `boolean` polja (dva checkboxa), pa korisnik može označiti oba.

### H) Pitanja "iz druge perspektive"

**Što se dogodi pri prvom pokretanju kad nema spremljenih podataka?**
`DataStorage.load()` provjeri postoji li datoteka; ako ne postoji, vrati praznu listu i aplikacija se normalno pokrene.

**Kako bi dodao novo polje, npr. datum treninga?**
Dodao bih polje u `Workout` (+ getter, konstruktor, `toString`), polje u formu u `MainWindow`, te ga uključio pri stvaranju objekta. Ostatak (spremanje, lista) radio bi automatski.

**Kako bi dodao sortiranje liste po trajanju?**
Dodao bih metodu u `WorkoutLog` koja sortira listu (npr. `Collections.sort` s usporedbom po trajanju) i pozvao `refreshList()`.

---

## 3. Objašnjenje ključnih klasa

| Klasa | Ključna uloga | Što istaknuti na obrani |
|-------|---------------|--------------------------|
| `Workout` | Model jednog treninga | Privatna polja + getteri (enkapsulacija), `implements Serializable`, prepisan `toString()` |
| `Intensity` | Enum intenziteta | Tip-sigurnost, polje `displayName` + metoda, prepisan `toString()` |
| `WorkoutLog` | Lista + statistika | `ArrayList<Workout>`, `for` petlje za statistiku, vraća `List` (apstrakcija) |
| `DataStorage` | Datoteke | Serijalizacija (`save`/`load`), tekstualni izvoz (`export`), `try-with-resources`, privatni konstruktor |
| `InvalidInputException` | Vlastita iznimka | `extends Exception` (nasljeđivanje), checked iznimka |
| `MainWindow` | Swing sučelje | `extends JFrame`, komponente, `ActionListener`, validacija, `JOptionPane` |
| `Main` | Ulazna točka | Učitavanje podataka, `SwingUtilities.invokeLater`, privatni konstruktor |

---

## 4. Prijedlog tijeka demonstracije (uživo)

1. **Pokreni** aplikaciju — prozor se otvori (s učitanim podacima ako postoje).
2. **Unesi trening** (npr. Running, 30, Monday, Medium, Cardio) i klikni *Add Workout* → pojavi se u listi, statistika se ažurira.
3. **Pokaži validaciju** — ostavi prazno ime ili upiši slovo u trajanje → iskoči poruka o grešci.
4. **Obriši** odabrani trening → potvrda → nestane iz liste.
5. **Save** → poruka o uspjehu (stvori se `workouts.dat`).
6. **Export** → otvori `workouts_export.txt` i pokaži čitljiv ispis.
7. (Opcionalno) zatvori i ponovno pokreni → podaci se učitaju (*Load* ili automatski).
