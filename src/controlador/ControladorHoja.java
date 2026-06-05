package controlador;

import modelo.Hoja;

public class ControladorHoja {

    private Hoja hoja;

    public ControladorHoja(Hoja hoja) {

        this.hoja = hoja;
    }

    public void guardarDato(
            int fila,
            int columna,
            String valor,
            String formula
    ) {

        hoja.getMatriz().insertar(
                fila,
                columna,
                valor,
                formula
        );
    }
}