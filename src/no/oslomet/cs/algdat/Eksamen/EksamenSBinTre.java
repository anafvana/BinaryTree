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
            } else { p = null; }
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

    public boolean fjern(T verdi) {
        //hjelpevariabel node
        Node<T> p = førstePostorden(rot);
        Node<T> q = null;

        //test om verdi = null -> return false
        if (verdi == null || p == null) return false;

        //søk på verdien i treet i post orden
        while(p != null){
            q = p;
            p = nestePostorden(p);
        }

        //blir det ikke fant, returner false
        if (q.verdi != verdi) return false;

        Node<T> qParent = q.forelder;

        //blir det fant, rearrangere peker
        //N.B.: den laveste duplicate kan kun ha høyre barn
        if (qParent != null) {
            if (q == qParent.venstre) qParent.venstre = q.høyre;
            else if (q == qParent.høyre) qParent.høyre = q.høyre;
        }

        //slett noden
        q.venstre = null;
        q.høyre = null;
        q.forelder = null;
        q.verdi = null;

        //redusere antall
        antall--;

        return true;
    }

    public int fjernAlle(T verdi) {
        //hjelpevariabel -> hvor mye har blitt fjernet
        int fjernet = 0;

        //test om verdi og tre != null
        if (verdi != null && !tom()) {
            //etablere hvor mye ganger skal verdien fjernes
            int antallLoops = antall(verdi);

            if (antallLoops > 0) {
                //hjelpevariabel node
                Node<T> p = rot;
                Node<T> pParent = null;

                //søke treet antallLoops ganger
                for (int i = 1; i <= antallLoops; i++) {

                    //om p.verdi != null, søk på verdien i treet
                    while (p != null) {
                        int temp = this.comp.compare(verdi, p.verdi);
                        //if verdi < p.verdi
                        if (temp < 0) {
                            pParent = p;
                            p = p.venstre;
                        } else if (temp > 0) {
                            pParent = p;
                            p = p.høyre;
                        } else {
                            break;
                        }
                    }

                    //når den blir fant, rearrangere peker
                    Node<T> barn;
                    //p er venstre barn
                    if (pParent.venstre != null && p == pParent.venstre){
                        //p har ingen barn
                        if (p.høyre == null && p.venstre == null){
                            pParent.venstre = null;
                        }
                        //p har venstre barn
                        else if (p.venstre != null){
                            barn = p.venstre;
                            pParent.venstre = barn;
                            barn.forelder = pParent;
                            p.venstre = null;
                        }
                        //p har høyre barn
                        else {
                            barn = p.høyre;
                            pParent.venstre = barn;
                            barn.forelder = pParent;
                            p.høyre = null;
                        }
                        p.forelder = null;

                    }
                    //p er høyre barn
                    else if (p == pParent.høyre){
                        //p har ingen barn
                        if (p.høyre == null && p.venstre == null){
                            pParent.høyre = null;
                        }
                        //p har venstre barn
                        else if (p.venstre != null){
                            barn = p.venstre;
                            pParent.høyre = barn;
                            barn.forelder = pParent;
                            p.venstre = null;
                        }
                        //p har høyre barn
                        else {
                            barn = p.høyre;
                            pParent.høyre = barn;
                            barn.forelder = pParent;
                            p.høyre = null;
                        }
                        p.forelder = null;
                    } else {
                        throw new IllegalArgumentException("Noe er galt med noden.");
                    }

                    //fjerning fullført
                    fjernet++;
                    antall--;

                    //Start fra hvor metoden sist fjernet en verdi
                    p = pParent;
                    pParent = p.forelder;
                }
            }
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
        antall--;
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
