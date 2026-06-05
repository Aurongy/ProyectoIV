package controlador;

import modelo.TablaHash;

public class ControladorHash {

    private TablaHash tabla;

    public ControladorHash() {

        tabla = new TablaHash(20);
    }

    public int insertarDato(String dato) {

        return tabla.insertar(dato);
    }
}