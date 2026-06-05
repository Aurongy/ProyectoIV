package modelo;

public class NodoHoja {

    public Hoja hoja;
    public NodoHoja siguiente;

    public NodoHoja(Hoja hoja) {

        this.hoja = hoja;
        this.siguiente = null;
    }
}