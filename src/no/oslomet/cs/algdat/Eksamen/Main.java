package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        //OPPGAVE 1 Test
        Integer[] a = {4,7,2,9,5,10,8,1,3,6};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.antall());

        //OPPGAVE 2 Test
        a = new Integer[]{4,7,2,9,4,10,8,7,4,6};
        tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.antall());
        System.out.println(tre.antall(5));
        System.out.println(tre.antall(4));
        System.out.println(tre.antall(7));
        System.out.println(tre.antall(10));
    }
}
