package modelo;

public class MatrizOrtogonal {

    private NodoCelda[][] matriz;

    public MatrizOrtogonal(
            int filas,
            int columnas
    ) {

        matriz =
                new NodoCelda[filas][columnas];
    }

    // ==================================
    // INSERTAR CELDA
    // ==================================

    public void insertar(
            int fila,
            int columna,
            String valor,
            String formula
    ) {

        NodoCelda nuevo =
                new NodoCelda(
                        fila,
                        columna,
                        valor,
                        formula
                );

        matriz[fila][columna] = nuevo;

        enlazar(fila, columna);
    }

    // ==================================
    // ENLAZAR NODOS
    // ==================================

    private void enlazar(
            int fila,
            int columna
    ) {

        NodoCelda actual =
                matriz[fila][columna];

        // =========================
        // IZQUIERDA
        // =========================

        if (columna > 0 &&
                matriz[fila][columna - 1] != null) {

            actual.izquierda =
                    matriz[fila][columna - 1];

            matriz[fila][columna - 1]
                    .derecha = actual;
        }

        // =========================
        // DERECHA
        // =========================

        if (columna <
                matriz[fila].length - 1 &&
                matriz[fila][columna + 1] != null) {

            actual.derecha =
                    matriz[fila][columna + 1];

            matriz[fila][columna + 1]
                    .izquierda = actual;
        }

        // =========================
        // ARRIBA
        // =========================

        if (fila > 0 &&
                matriz[fila - 1][columna] != null) {

            actual.arriba =
                    matriz[fila - 1][columna];

            matriz[fila - 1][columna]
                    .abajo = actual;
        }

        // =========================
        // ABAJO
        // =========================

        if (fila <
                matriz.length - 1 &&
                matriz[fila + 1][columna] != null) {

            actual.abajo =
                    matriz[fila + 1][columna];

            matriz[fila + 1][columna]
                    .arriba = actual;
        }
    }

    // ==================================
    // OBTENER NODO
    // ==================================

    public NodoCelda obtener(
            int fila,
            int columna
    ) {

        return matriz[fila][columna];
    }

    // ==================================
    // OBTENER VALOR
    // ==================================

    public String obtenerValor(
            int fila,
            int columna
    ) {

        NodoCelda nodo =
                matriz[fila][columna];

        if (nodo == null) {

            return "";
        }

        return nodo.valor;
    }

    // ==================================
    // OBTENER FORMULA
    // ==================================

    public String obtenerFormula(
            int fila,
            int columna
    ) {

        NodoCelda nodo =
                matriz[fila][columna];

        if (nodo == null) {

            return "";
        }

        return nodo.formula;
    }

    // ==================================
    // OBTENER MATRIZ
    // ==================================

    public NodoCelda[][] getMatriz() {

        return matriz;
    }

    // ==================================
    // MOSTRAR ENLACES
    // ==================================

    public void mostrarEnlaces(
            int fila,
            int columna
    ) {

        NodoCelda nodo =
                matriz[fila][columna];

        if (nodo == null) {

            System.out.println(
                    "Celda vacia"
            );

            return;
        }

        System.out.println(
                "Celda ["
                        + fila
                        + ","
                        + columna
                        + "]"
        );

        // =========================
        // IZQUIERDA
        // =========================

        if (nodo.izquierda != null) {

            System.out.println(
                    "Izquierda -> ["
                            + nodo.izquierda.fila
                            + ","
                            + nodo.izquierda.columna
                            + "]"
            );
        }

        // =========================
        // DERECHA
        // =========================

        if (nodo.derecha != null) {

            System.out.println(
                    "Derecha -> ["
                            + nodo.derecha.fila
                            + ","
                            + nodo.derecha.columna
                            + "]"
            );
        }

        // =========================
        // ARRIBA
        // =========================

        if (nodo.arriba != null) {

            System.out.println(
                    "Arriba -> ["
                            + nodo.arriba.fila
                            + ","
                            + nodo.arriba.columna
                            + "]"
            );
        }

        // =========================
        // ABAJO
        // =========================

        if (nodo.abajo != null) {

            System.out.println(
                    "Abajo -> ["
                            + nodo.abajo.fila
                            + ","
                            + nodo.abajo.columna
                            + "]"
            );
        }
    }
}