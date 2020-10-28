package no.oslomet.cs.algdat.Eksamen;


import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    //Basert på Programkode 5.2.3 a) fra pensum
    public boolean leggInn(T verdi) {
        //Test if null
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        //Hjelpevariabel
        Node<T> p = rot, pParent = null;
        int temp = 0;

        //Søk riktig posisjon og sett sist verdien i "linje" som forelder
        while (p != null){
            if (p.verdi != null) {
                pParent = p;
                temp = this.comp.compare(verdi, p.verdi);
                p = temp < 0 ? p.venstre : p.høyre;
            } else p = null;
        }
        //Lag ny node med verdien
        p = new Node<>(verdi, null, null, null);

        //Hvis ingen andre noder, ny node = rot
        if (pParent == null){
            rot = p;
        } else {
            //Definerer forelder
            p.forelder = pParent;
            //Ellers sjekk om < eller > forelder
            //Sett dem som høyre eller venstre node
            if(temp<0) pParent.venstre = p;
            else pParent.høyre = p;
        }

        antall++;
        return true; //Vellykket!
    }

    //Inspirert av Programkode 5.2.8 d)
    private boolean fjernNode(Node<T> p, T verdi){
        if (p == rot) {
            int temp = comp.compare(verdi, p.verdi);
            if (temp != 0) return false;
        }

        //hvis noden som fjernes har mindre enn to barn
        if (p.venstre == null || p.høyre == null){
            Node<T> barn;
            if (p.venstre != null) barn = p.venstre;
            else barn = p.høyre;

            //hvis noden har ingen barn
            if (barn == null && p != rot){
                if (p == p.forelder.venstre) {
                    p.forelder.venstre = null;
                } else if (p == p.forelder.høyre){
                    p.forelder.høyre = null;
                }
            }
            //hvis noden har et barn
            else {
                if (p == rot) {
                    rot = barn;
                } else if (p == p.forelder.venstre) {
                    p.forelder.venstre = barn;
                } else {
                    p.forelder.høyre = barn;
                }
                if (barn != null) barn.forelder = p.forelder;
            }
            //fjerner alle pekere fra p
            p.forelder = null;
            p.høyre = null;
            p.venstre = null;
        }
        //hvis noden som fjernes har begge barn
        else {
            //bruker inorder til å remplasere fjernet noden
            Node<T> rParent = p, r = p.høyre;
            while (r.venstre != null){
                rParent=r;
                r=r.venstre;
            }

            p.verdi = r.verdi;

            if (rParent != p) rParent.venstre = r.høyre;
            else rParent.høyre = r.høyre;

            //etablerer ny forelder relasjon til r's (former) barn som blir rParents sin barn
            rParent.høyre.forelder = rParent;

            //fjerner alle pekere fra r
            r.forelder = null;
            r.høyre = null;
            r.venstre = null;
        }
        antall--;
        return true;
    }

    //Inspirert av Programkode 5.2.8 d)
    public boolean fjern(T verdi) {
        //handle null verdi
        if (verdi == null || rot.verdi == null) return false;

        //hjelpevariabler
        Node<T> p = førstePostorden(rot);

        //søker etter verdien
        if (p.verdi != null) {
            while (p != rot) {
                int temp = comp.compare(verdi, p.verdi);
                if (temp != 0) {
                    p = nestePostorden(p);
                }
                //finner verdien
                else break;
            }
        }
        return fjernNode(p, verdi);
    }

    public int fjernAlle(T verdi) {
        int fjernet = 0;
        Node<T> p = førstePostorden(rot), q;
        while (p != rot){
            //ser etter neste node i postOrden før p blir slettet
            q=nestePostorden(p);
            //hvis verdien finnes
            if(p.verdi == verdi){
                //slett noden
                boolean ok = fjernNode(p, verdi);
                if (ok) fjernet++;
            }
            p=q;
        }
        //handler edge-case av verdien i rot
        if (rot.verdi == verdi){
            boolean ok = fjernNode(rot, verdi);
            if (ok) fjernet++;
        }

        return fjernet;
    }

    public int antall(T verdi) {
        int result = 0;
        //Sjekk om verdien er null
        if (verdi==null) result=antall;
        else{
            Node<T> p = rot;
            while(p != null){
                //Sjekk om verdien >= p
                if (this.comp.compare(verdi, p.verdi) >= 0){
                    if (this.comp.compare(verdi, p.verdi) == 0) result++; //Sjekk om verdien == p og, hvis true, increase result
                    //True -> p = p.høyre
                    p = p.høyre;
                } else {
                    //False -> p = p.venstre
                    p = p.venstre;
                }
            }
        }

        //Return antall ganger verdien er i lista
        return result;
    }

    public void nullstill() {
        nullstillRek(rot);
    }

    private void nullstillRek(Node<T> p){
        //if venstre != null -> venstre
        if (p.venstre != null) nullstillRek(p.venstre);
        //if høyre != null -> høyre
        if (p.høyre != null) nullstillRek(p.høyre);
        //noden selv
        p.høyre = null;
        p.venstre = null;
        p.forelder = null;
        //ikke reduserer antall hvis rot er null, men gjør det enten når p er ikke rot eller ikke null
        if (p!=rot || p.verdi != null){
            p.verdi = null;
            antall--;
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        //hjelpevariabel: må loop'en fortsette å kjøre?
        boolean fortsett = true;
        Node<T> q = p;

        while (fortsett){
            if (q.venstre == null){
                //if q.venstre = null & q.høyre = null -> false
                if (q.høyre == null){
                    fortsett = false;
                }
                //if q.venstre == null men q.høyre -> true
                else {
                    q = q.høyre;
                }
            }
            //if q.venstre -> true
            else {
                q = q.venstre;
            }
        }
        return q;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> q = null;
        //sjekk if p er venstre eller høyre node (eller rot)

        if (p.forelder == null){
            //p er siste node og null må returneres
        }
        //p er venstre node
        else if (p == p.forelder.venstre){
            //om venstre -> move til høyre laveste nephew
            if (p.forelder.høyre != null){
                q = førstePostorden(p.forelder.høyre);
            } else {
                //om ingen høyre node -> move up
                q = p.forelder;
            }
        }
        //p er høyre node
        else if (p == p.forelder.høyre){
            //om høyre -> move up
            q = p.forelder;
        }
        return q;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        //finne først postorder med rot
        Node<T> p = førstePostorden(rot);

         while (p != null){
            //gjøre oppgave
            oppgave.utførOppgave(p.verdi);
            //finne nest i postorder
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        //if venstre != null -> venstre
        if (p.venstre != null) postordenRecursive(p.venstre, oppgave);
        //if høyre != null -> høyre
        if (p.høyre != null) postordenRecursive(p.høyre, oppgave);
        //noden selv
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        //Lag en kø (LinkedList?)
        Queue<Node<T>> kø = new LinkedList<>();
        //Lag ArrayList
        ArrayList<T> al = new ArrayList<>();

        //Noden som holder verdi som går inn i ArrayList
        Node<T> p = rot;
        //Antall tall som ble lagt til arraylisten
        int innLagt = 0;

        while(innLagt<antall){
            //legger barn til køen
            if (p.venstre != null) kø.add(p.venstre);
            if (p.høyre != null) kø.add(p.høyre);
            //legger verdien til arraylisten
            if (p.verdi != null) al.add(p.verdi);

            //ny p verdien
            p=kø.poll();
            //teller opp lagt inn verdier
            innLagt++;
        }

        //returnere arrayList
        return al;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        //tre som fylles ut
        EksamenSBinTre<K> tre = new EksamenSBinTre<>(c);

        //enhanced for-loop med arraylisten
        for(K d : data){
            //bruk leggIn() til å legge inn verdiene som noder
            tre.leggInn(d);
        }
        return tre;
    }


} // ObligSBinTre
