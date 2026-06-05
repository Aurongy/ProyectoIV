package modelo;

public class ListaHojas {

    private NodoHoja inicio;
    private int tamaño;

    public ListaHojas() {

        inicio = null;
        tamaño = 0;
    }

    public void agregar(Hoja hoja) {

        NodoHoja nuevo = new NodoHoja(hoja);

        if (inicio == null) {

            inicio = nuevo;

        } else {

            NodoHoja aux = inicio;

            while (aux.siguiente != null) {

                aux = aux.siguiente;
            }

            aux.siguiente = nuevo;
        }

        tamaño++;
    }

    public Hoja obtener(int index) {

        NodoHoja aux = inicio;

        int contador = 0;

        while (aux != null) {

            if (contador == index) {

                return aux.hoja;
            }

            contador++;

            aux = aux.siguiente;
        }

        return null;
    }

    public int size() {

        return tamaño;
    }

    public NodoHoja getInicio() {

        return inicio;
    }

    public void limpiar() {

        inicio = null;
        tamaño = 0;
    }
}