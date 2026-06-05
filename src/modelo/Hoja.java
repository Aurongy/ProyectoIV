package modelo;

public class Hoja {

    private String nombre;
    private MatrizOrtogonal matriz;

    public Hoja(String nombre) {

        this.nombre = nombre;
        this.matriz = new MatrizOrtogonal(50, 26);
    }

    public String getNombre() {
        return nombre;
    }

    public MatrizOrtogonal getMatriz() {
        return matriz;
    }
}