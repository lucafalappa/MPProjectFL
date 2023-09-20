package it.unicam.cs.fl.mpproject.api.models;

import java.util.Objects;

/**
 * Classe che rappresenta l'etichetta da mostrare
 * dai robot
 */
public class RobotLabel {

    /**
     * Etichetta da segnalare
     */
    private final String label;

    /**
     * Visibilita' dell'etichetta
     */
    private boolean visibility;

    public static final String DEFAULT_LABEL = "DEFAULT_LABEL";

    public RobotLabel(String label, boolean visibility) {
        this.label = label;
        this.visibility = visibility;
    }

    public RobotLabel(String label) {
        this.label = label;
        this.visibility = false;
    }

    public RobotLabel() {
        this.label = DEFAULT_LABEL;
        this.visibility = false;
    }

    public String getString() {
        return label;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Metodo che determina se l'oggetto in parametro e'
     * un'etichetta e se ha caratteristiche uguali
     * all'etichetta chiamante il metodo stesso
     * @param o Istanza della classe Object
     * @return true se il parametro e' un'etichetta con
     * stesse caratteristiche dell'etichetta invocante,
     * false altrimenti
     */
    public boolean equalObject(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        RobotLabel label1 = (RobotLabel) o;
        return this.visibility == label1.isVisible()
                && Objects.equals(this.label, label1.getString());
    }

    /**
     * Metodo che determina se l'etichetta invocante il
     * metodo e quella in parametro hanno stessa stringa e
     * stessa visibilita
     * @param l Istanza di Label(l'etichetta da confrontare)
     * @return true se le due etichette si equivalgono,
     * false altrimenti
     */
    public boolean equalLabel(RobotLabel l) {
        if (this == l) return true;
        if (l == null) return false;
        return this.label.equalsIgnoreCase(l.getString())
                && this.visibility == l.isVisible();
    }

    /**
     * Metodo che riporta in stringa le caratteristiche
     * principali dell'etichetta
     * @return Messaggio di descrizione
     */
    @Override
    public String toString() {
        return "RobotLabel{" +
                "label='" + this.label + '\'' +
                ", visibility=" + this.visibility +
                '}';
    }

}
