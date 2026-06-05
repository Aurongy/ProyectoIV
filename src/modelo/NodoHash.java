package modelo;

public class NodoHash {

    public String dato;
    public int indice;

    public NodoHash siguiente;

    public NodoHash(String dato, int indice) {

        this.dato = dato;
        this.indice = indice;
    }
}