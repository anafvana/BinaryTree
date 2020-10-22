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
