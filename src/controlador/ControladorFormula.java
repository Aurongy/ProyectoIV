package controlador;

import modelo.Hoja;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class ControladorFormula {

    public static double evaluar(
            String formula,
            Hoja hoja
    ) {

        try {

            formula = formula.trim().toLowerCase();

            if (!formula.startsWith("=") ||
                    !formula.contains("(") ||
                    !formula.endsWith(")")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Formato de formula incorrecto. Ejemplo: =suma(A1,A2,A3)"
                );

                return 0;
            }

            String funcion =
                    formula.substring(
                            1,
                            formula.indexOf("(")
                    );

            String contenido =
                    formula.substring(
                            formula.indexOf("(") + 1,
                            formula.length() - 1
                    );

            List<Double> valores =
                    obtenerValores(
                            contenido,
                            hoja
                    );

            if (valores.isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "La formula no tiene valores validos"
                );

                return 0;
            }

            switch (funcion) {

                case "suma":
                    return sumar(valores);

                case "multi":
                case "multiplicacion":
                    return multiplicar(valores);

                case "resta":
                    return restar(valores);

                case "div":
                case "division":
                    return dividir(valores);

                case "promedio":
                    return sumar(valores) / valores.size();

                case "max":
                case "mayor":
                    return mayor(valores);

                case "min":
                case "menor":
                    return menor(valores);

                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "Funcion no reconocida: " + funcion
                    );
                    return 0;
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error en formula"
            );

            return 0;
        }
    }

    // ==================================
    // OPERACIONES CON VARIOS VALORES
    // ==================================

    private static double sumar(List<Double> valores) {

        double resultado = 0;

        for (double valor : valores) {
            resultado += valor;
        }

        return resultado;
    }

    private static double multiplicar(List<Double> valores) {

        double resultado = 1;

        for (double valor : valores) {
            resultado *= valor;
        }

        return resultado;
    }

    private static double restar(List<Double> valores) {

        double resultado = valores.get(0);

        for (int i = 1; i < valores.size(); i++) {
            resultado -= valores.get(i);
        }

        return resultado;
    }

    private static double dividir(List<Double> valores) {

        double resultado = valores.get(0);

        for (int i = 1; i < valores.size(); i++) {

            double divisor = valores.get(i);

            if (divisor == 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "No se puede dividir entre 0"
                );

                return 0;
            }

            resultado /= divisor;
        }

        return resultado;
    }

    private static double mayor(List<Double> valores) {

        double resultado = valores.get(0);

        for (double valor : valores) {
            if (valor > resultado) {
                resultado = valor;
            }
        }

        return resultado;
    }

    private static double menor(List<Double> valores) {

        double resultado = valores.get(0);

        for (double valor : valores) {
            if (valor < resultado) {
                resultado = valor;
            }
        }

        return resultado;
    }

    // ==================================
    // OBTENER VARIOS VALORES
    // ACEPTA: A1,A2,A3  O  A1:A10
    // ==================================

    private static List<Double> obtenerValores(
            String contenido,
            Hoja hoja
    ) {

        List<Double> valores =
                new ArrayList<>();

        String[] partes =
                contenido.split(",");

        for (String parte : partes) {

            parte = parte.trim();

            if (parte.contains(":")) {

                valores.addAll(
                        obtenerValoresRango(
                                parte,
                                hoja
                        )
                );

            } else {

                valores.add(
                        obtenerValor(
                                parte,
                                hoja
                        )
                );
            }
        }

        return valores;
    }

    // ==================================
    // OBTENER VALORES DE UN RANGO
    // EJEMPLO: A1:A5, A1:C3
    // ==================================

    private static List<Double> obtenerValoresRango(
            String rango,
            Hoja hoja
    ) {

        List<Double> valores =
                new ArrayList<>();

        try {

            String[] limites =
                    rango.split(":");

            int[] inicio =
                    obtenerPosicionCelda(
                            limites[0]
                    );

            int[] fin =
                    obtenerPosicionCelda(
                            limites[1]
                    );

            int filaInicio =
                    Math.min(
                            inicio[0],
                            fin[0]
                    );

            int filaFin =
                    Math.max(
                            inicio[0],
                            fin[0]
                    );

            int columnaInicio =
                    Math.min(
                            inicio[1],
                            fin[1]
                    );

            int columnaFin =
                    Math.max(
                            inicio[1],
                            fin[1]
                    );

            for (int fila = filaInicio; fila <= filaFin; fila++) {

                for (int columna = columnaInicio; columna <= columnaFin; columna++) {

                    String valor =
                            hoja.getMatriz()
                                    .obtenerValor(
                                            fila,
                                            columna
                                    );

                    if (valor != null &&
                            !valor.equals("")) {

                        valores.add(
                                Double.parseDouble(valor)
                        );
                    }
                }
            }

        } catch (Exception e) {
            return valores;
        }

        return valores;
    }

    // ==================================
    // OBTENER VALOR CELDA O NUMERO DIRECTO
    // ==================================

    private static double obtenerValor(
            String referencia,
            Hoja hoja
    ) {

        try {

            referencia = referencia.trim();

            // Permite usar numeros directos:
            // =suma(10,20,A1)
            if (referencia.matches("-?\\d+(\\.\\d+)?")) {
                return Double.parseDouble(referencia);
            }

            int[] posicion =
                    obtenerPosicionCelda(
                            referencia
                    );

            int fila = posicion[0];
            int columna = posicion[1];

            if (fila < 0 ||
                    fila >= 50 ||
                    columna < 0 ||
                    columna >= 26) {

                return 0;
            }

            String valor =
                    hoja.getMatriz()
                            .obtenerValor(
                                    fila,
                                    columna
                            );

            if (valor == null ||
                    valor.equals("")) {

                return 0;
            }

            return Double.parseDouble(valor);

        } catch (Exception e) {

            return 0;
        }
    }

    // ==================================
    // CONVERTIR REFERENCIA A POSICION
    // A1 = fila 0, columna 0
    // ==================================

    private static int[] obtenerPosicionCelda(
            String referencia
    ) {

        referencia =
                referencia.trim().toUpperCase();

        char letra =
                referencia.charAt(0);

        int columna =
                letra - 'A';

        int fila =
                Integer.parseInt(
                        referencia.substring(1)
                ) - 1;

        return new int[]{fila, columna};
    }
}
