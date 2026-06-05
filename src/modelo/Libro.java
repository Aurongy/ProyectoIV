package modelo;

public class Libro {

    private ListaHojas hojas;

    public Libro() {

        hojas = new ListaHojas();
    }

    public void agregarHoja(String nombre) {

        hojas.agregar(new Hoja(nombre));
    }

    public ListaHojas getHojas() {

        return hojas;
    }

    public Hoja obtenerHoja(int index) {

        return hojas.obtener(index);
    }

    public int totalHojas() {

        return hojas.size();
    }

    public void limpiar() {

        hojas.limpiar();
    }
}