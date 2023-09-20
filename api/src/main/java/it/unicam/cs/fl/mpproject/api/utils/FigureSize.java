package it.unicam.cs.fl.mpproject.api.utils;

/**
 * Classe record che rappresenta una generica coppia di due numeri
 * (qui utile per rappresentare le dimensioni delle figure
 * costituenti i robot)
 *
 * @param <F>    Tipo del primo elemento della coppia
 * @param <S>    Tipo del secondo elemento della coppia
 * @param first  Primo elemento della coppia
 * @param second Secondo elemento della coppia
 */
public record FigureSize<F extends Number & Comparable<F>, S extends Number & Comparable<S>>(F first, S second) {

    /**
     * Metodo che costruisce una coppia a partire da due elementi
     * passati in parametro
     *
     * @param first  Primo elemento per costruire la coppia
     * @param second Secondo elemento per costruire la coppia
     * @param <F>    Tipo del primo elemento della nuova coppia
     * @param <S>    Tipo del secondo elemento della nuova coppia
     * @return Una nuova coppia di elementi
     * @throws NullPointerException Se almeno uno dei due parametri
     *                              e' nullo
     */
    public static <F extends Number & Comparable<F>, S extends Number & Comparable<S>>
    FigureSize<F, S> generate(F first, S second) {
        if ((first == null || second == null)) {
            throw new NullPointerException("\n" +
                    "CAN NOT GENERATE A FIGURESIZE: A NULL PARAMETER IS PRESENT");
        } else {
            return new FigureSize<>(first, second);
        }
    }

    /**
     * Metodo che riporta in stringa le caratteristiche
     * principali della coppia di elementi
     *
     * @return Messaggio di descrizione
     */
    @Override
    public String toString() {
        return "FigureSize{" +
                "firstDimension=" + first +
                ", secondDimension=" + second +
                '}';
    }

}
