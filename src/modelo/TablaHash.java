package modelo;

public class TablaHash {

    private NodoHash[] tabla;

    public TablaHash(int tamaño) {

        tabla = new NodoHash[tamaño];
    }

    public int funcionHash(String texto) {

        int suma = 0;

        for (char c : texto.toCharArray()) {
            suma += c;
        }

        return suma % tabla.length;
    }

    public int insertar(String dato) {

        int indice = funcionHash(dato);

        NodoHash nuevo = new NodoHash(dato, indice);

        if (tabla[indice] == null) {

            tabla[indice] = nuevo;

        } else {

            NodoHash aux = tabla[indice];

            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }

            aux.siguiente = nuevo;
        }

        return indice;
    }
}