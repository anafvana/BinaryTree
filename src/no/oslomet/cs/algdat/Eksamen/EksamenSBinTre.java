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

    public boolean leggInn(T verdi) {
        //Test if null
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        //Hjelpevariabel
        Node<T> p = rot, pParent = null;
        int temp = 0;

        //Søk sist p (dybde) og sett den som forelder
        while (p != null){
            pParent = p;
            temp = this.comp.compare(verdi, p.verdi);
            p = temp < 0 ? p.venstre : p.høyre;
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
                    //True -> p = p.venstre
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
