package de.hsrm.mi.web.projekt.messaging;
public class FotoMessage {
    public static final String FOTO_GESPEICHERT = "fotoGespeichert";
    public static final String FOTO_GELOESCHT = "fotoGeloescht";
    private String operation;
    private long id;

    public FotoMessage() {
    }

    public FotoMessage(String op, long id) {
        this.operation = op;
        this.id = id;
    }

    public static String getFotoGespeichert() {
        return FOTO_GESPEICHERT;
    }

    public static String getFotoGeloescht() {
        return FOTO_GELOESCHT;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FotoMessage [id=" + id + ", operation=" + operation + "]";
    }

}
