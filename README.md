# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har XX commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Løste ved å implementere Programkode 5.2.3 med endringer slik at venstre, høyre og foreldre verdiene er etablert. 
Først søkte jeg riktig plassering av verdien i binærtre og fant siste noden i "linjen". Da etablerte jeg om verdien var større, mindre eller lik foreldernodenverdien.
Den nye noden ble satt til venstre om den var mindre og ellers til høyre. Om det var ingen forelder, ble den rot. 
Metoden returnerer true hvis den er vellykket.  
* Oppgave 2: Løste ved å først sjekke om verdien var null. Hvis ja, da ble resultat lik antall. 
Ellers, begynte jeg å sjekke (større/mindre/lik) verdien mot en hjelpevariabels (p) sin verdi, som started fra roten.
Verdien ble sjekket mot p så lang så p ikke var null, og hver gang den ble lik p, ble antall = antall+1.
Når p ble null, gikk metoden utenfor while-loopen og returnerte den resultaten.
* Oppgave 3: Den første delen ble løst med en hjelpevariabel (Node q, som blir definert som = p) og en for-loop som, først og fremst, søker å gå hele tiden til venstre, slik at q blir den siste venstre noden i "linje".
Om q.venstre node er null men det er en q.høyre, da blir q = q.høyre og loop'er vi slik at vi prøver å finne den nederste noden på venstre. En gang til, om q.venstre=null, men q.høyre!=null, q=q.høyre osv. 
Metoden stopper når både q.venstre og q.høyre er lik null.
Den andre delen ble løst ved å finne ut om p er lik 1.rot (returnerer null), 2.en høyre node (returnerer forelder node), 3.en venstre node (returnerer den dypste nevø/niese til høyre, som finnes med førstePostorden(Node q) hvor roten er p.forelder.høyre).
Mønstrene ble identifiserte ved å tegne trær og bruke piler til å se hvilken node som skulle være neste.
* Oppgave 4: Ikke-rekursive metoden ble løst ved å, først, finne først noden i postorden med p=førstePostorden(rot). 
Da, i en loop, som kjørte til p ble lik null, ble oppgaven kjørt med p.verdi og p ble oppdatert til neste noden i postorden med nestePostorden(p).
Den rekursive metoden ble kjørt ved å, først, kalle postordenRecursive() i p.venstre om den ikke var null. Da ble postordenRecursive() kalt i p.høyre. 
Endelig ble oppgaven utført i p.verdien (noden selv). Den ble inspirert av postordens rekursiv definisjon i pensum, som ligger under Programkode 5.1.7 e).
* Oppgave 5:  
