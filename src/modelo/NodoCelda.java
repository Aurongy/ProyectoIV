package modelo;

public class NodoCelda {

    public int fila;
    public int columna;

    public String valor;
    public String formula;

    public NodoCelda arriba;
    public NodoCelda abajo;
    public NodoCelda izquierda;
    public NodoCelda derecha;

    public NodoCelda(
            int fila,
            int columna,
            String valor,
            String formula
    ) {

        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
        this.formula = formula;
    }
}