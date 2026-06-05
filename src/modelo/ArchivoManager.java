package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class ArchivoManager {

    // ==================================
    // GUARDAR ARCHIVO
    // ==================================

    public static void guardar(
            Libro libro,
            String ruta
    ) {

        try {

            FileWriter writer =
                    new FileWriter(ruta);

            // =========================
            // RECORRER HOJAS
            // =========================

            NodoHoja aux =
                    libro.getHojas().getInicio();

            while (aux != null) {

                Hoja hoja =
                        aux.hoja;

                writer.write(
                        "HOJA:"
                                + hoja.getNombre()
                                + "\n"
                );

                NodoCelda[][] matriz =
                        hoja.getMatriz()
                                .getMatriz();

                // =========================
                // RECORRER MATRIZ
                // =========================

                for (int i = 0;
                     i < matriz.length;
                     i++) {

                    for (int j = 0;
                         j < matriz[i].length;
                         j++) {

                        if (matriz[i][j] != null) {

                            NodoCelda nodo =
                                    matriz[i][j];

                            writer.write(
                                    i + ";"
                                            + j + ";"
                                            + nodo.valor + ";"
                                            + nodo.formula
                                            + "\n"
                            );
                        }
                    }
                }

                aux = aux.siguiente;
            }

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ==================================
    // CARGAR ARCHIVO
    // ==================================

    public static void cargar(
            Libro libro,
            String ruta
    ) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(ruta)
                    );

            String linea;

            Hoja hojaActual = null;

            while ((linea = br.readLine()) != null) {

                // =========================
                // NUEVA HOJA
                // =========================

                if (linea.startsWith("HOJA:")) {

                    String nombre =
                            linea.replace(
                                    "HOJA:",
                                    ""
                            );

                    libro.agregarHoja(nombre);

                    hojaActual =
                            libro.obtenerHoja(
                                    libro.totalHojas() - 1
                            );

                } else {

                    // =========================
                    // DATOS CELDA
                    // =========================

                    String[] datos =
                            linea.split(";");

                    int fila =
                            Integer.parseInt(
                                    datos[0]
                            );

                    int columna =
                            Integer.parseInt(
                                    datos[1]
                            );

                    String valor =
                            datos[2];

                    String formula = "";

                    if (datos.length > 3) {

                        formula = datos[3];
                    }

                    hojaActual.getMatriz()
                            .insertar(
                                    fila,
                                    columna,
                                    valor,
                                    formula
                            );
                }
            }

            br.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}