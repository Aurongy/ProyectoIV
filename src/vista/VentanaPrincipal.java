package vista;

import controlador.ControladorFormula;
import controlador.ControladorHoja;
import modelo.ArchivoManager;
import modelo.Hoja;
import modelo.Libro;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private Libro libro;

    private JTextField txtFormula;

    private JTabbedPane pestanas;

    private ArrayList<JTable> tablas;

    private JLabel estado;

    private boolean actualizado = false;

    private JMenuBar barra;

    private boolean modoSeleccionFormula = false;

    private int filaFormulaDestino = -1;

    private int columnaFormulaDestino = -1;

    private String ultimaReferenciaInsertada = "";

    public VentanaPrincipal() {

        libro = new Libro();

        tablas = new ArrayList<>();

        libro.agregarHoja("Hoja 1");

        inicializar();
    }

    // =========================================
    // HOJA ACTUAL
    // =========================================

    private Hoja hojaActual() {

        int indice =
                pestanas.getSelectedIndex();

        return libro.obtenerHoja(indice);
    }

    // =========================================
    // TABLA ACTUAL
    // =========================================

    private JTable tablaActual() {

        int indice =
                pestanas.getSelectedIndex();

        return tablas.get(indice);
    }

    // =========================================
    // COLUMNAS
    // =========================================

    private String[] obtenerColumnas() {

        return new String[]{
                "A","B","C","D","E","F","G","H",
                "I","J","K","L","M","N","O","P",
                "Q","R","S","T","U","V","W","X",
                "Y","Z"
        };
    }

    // =========================================
    // INICIALIZAR
    // =========================================

    private void inicializar() {

        setTitle("Hoja Electronica");

        setSize(1200, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        // =====================================
        // MENU
        // =====================================

        barra = new JMenuBar();

        JMenu archivo =
                new JMenu("Archivo");

        JMenu insertar =
                new JMenu("Insertar");

        JMenu ayuda =
                new JMenu("Ayuda");

        JMenuItem nuevaHoja =
                new JMenuItem("Nueva Hoja");

        JMenuItem guardar =
                new JMenuItem("Guardar");

        JMenuItem abrir =
                new JMenuItem("Abrir");

        JMenuItem hash =
                new JMenuItem("Tabla Hash");

        JMenuItem acerca =
                new JMenuItem("Acerca de");

        JMenuItem insertarSuma =
                new JMenuItem("Formula SUMA");

        JMenuItem insertarResta =
                new JMenuItem("Formula RESTA");

        JMenuItem insertarMultiplicacion =
                new JMenuItem("Formula MULTIPLICACION");

        JMenuItem insertarDivision =
                new JMenuItem("Formula DIVISION");

        JMenuItem insertarPromedio =
                new JMenuItem("Formula PROMEDIO");

        JMenuItem insertarMayor =
                new JMenuItem("Formula MAYOR");

        JMenuItem insertarMenor =
                new JMenuItem("Formula MENOR");

        JMenuItem insertarAyudaFormula =
                new JMenuItem("Ayuda de formulas");

        archivo.add(nuevaHoja);
        archivo.add(guardar);
        archivo.add(abrir);
        archivo.add(hash);

        insertar.add(insertarSuma);
        insertar.add(insertarResta);
        insertar.add(insertarMultiplicacion);
        insertar.add(insertarDivision);
        insertar.addSeparator();
        insertar.add(insertarPromedio);
        insertar.add(insertarMayor);
        insertar.add(insertarMenor);
        insertar.addSeparator();
        insertar.add(insertarAyudaFormula);

        ayuda.add(acerca);

        barra.add(archivo);
        barra.add(insertar);
        barra.add(ayuda);

        setJMenuBar(barra);

        // =====================================
        // PANEL FORMULAS
        // =====================================

        JPanel panelFormula =
                new JPanel(new BorderLayout());

        panelFormula.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );

        JLabel fx =
                new JLabel("fx ");

        fx.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        txtFormula =
                new JTextField();

        txtFormula.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        JButton aplicar =
                new JButton("Aplicar");

        JButton cancelar =
                new JButton("Cancelar");

        JPanel botones =
                new JPanel();

        botones.add(aplicar);
        botones.add(cancelar);

        panelFormula.add(
                fx,
                BorderLayout.WEST
        );

        panelFormula.add(
                txtFormula,
                BorderLayout.CENTER
        );

        panelFormula.add(
                botones,
                BorderLayout.EAST
        );

        add(
                panelFormula,
                BorderLayout.NORTH
        );

        // =====================================
        // PESTAÑAS
        // =====================================

        pestanas =
                new JTabbedPane();

        add(
                pestanas,
                BorderLayout.CENTER
        );

        // =====================================
        // ESTADO
        // =====================================

        estado =
                new JLabel(
                        "Celda:"
                );

        estado.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        add(
                estado,
                BorderLayout.SOUTH
        );

        // =====================================
        // CREAR PRIMERA HOJA
        // =====================================

        crearHojaVisual("Hoja 1");

        // =====================================
        // EVENTOS
        // =====================================

        nuevaHoja.addActionListener(
                e -> crearHoja()
        );

        guardar.addActionListener(
                e -> guardarArchivo()
        );

        abrir.addActionListener(
                e -> abrirArchivo()
        );

        hash.addActionListener(e -> {

            VentanaHash ventana =
                    new VentanaHash();

            ventana.setVisible(true);
        });

        acerca.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    """
                    Hoja Electronica
                    Proyecto Estructuras de Datos
                    
                    - Matriz Ortogonal
                    - Lista Enlazada
                    - Tabla Hash
                    - MVC
                    """
            );
        });

        insertarSuma.addActionListener(
                e -> insertarFormula("=suma()")
        );

        insertarResta.addActionListener(
                e -> insertarFormula("=resta()")
        );

        insertarMultiplicacion.addActionListener(
                e -> insertarFormula("=multi()")
        );

        insertarDivision.addActionListener(
                e -> insertarFormula("=div()")
        );

        insertarPromedio.addActionListener(
                e -> insertarFormula("=promedio()")
        );

        insertarMayor.addActionListener(
                e -> insertarFormula("=mayor()")
        );

        insertarMenor.addActionListener(
                e -> insertarFormula("=menor()")
        );

        insertarAyudaFormula.addActionListener(
                e -> mostrarAyudaFormulas()
        );

        aplicar.addActionListener(
                e -> evaluarFormula()
        );

        cancelar.addActionListener(e -> {

            modoSeleccionFormula = false;
            ultimaReferenciaInsertada = "";
            txtFormula.setText("");
        });

        txtFormula.addActionListener(
                e -> evaluarFormula()
        );

        txtFormula.addKeyListener(
                new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {

                        prepararModoSeleccionFormula();
                    }
                }
        );
    }

    // =========================================
    // CREAR TABLA
    // =========================================

    private JTable crearTabla(
            DefaultTableModel modelo
    ) {

        JTable tabla =
                new JTable(modelo);

        // =====================================
        // ESTILO
        // =====================================

        tabla.setRowHeight(28);

        tabla.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        tabla.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        tabla.getTableHeader().setBackground(
                new Color(230,230,230)
        );

        tabla.setGridColor(
                new Color(200,200,200)
        );

        tabla.setBackground(Color.WHITE);

        tabla.setSelectionBackground(
                new Color(180,220,255)
        );

        tabla.setShowGrid(true);

        tabla.setIntercellSpacing(
                new Dimension(1,1)
        );

        tabla.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        tabla.setCellSelectionEnabled(true);
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(true);
        tabla.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );
        tabla.getColumnModel()
                .getSelectionModel()
                .setSelectionMode(
                        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
                );

        for (int i = 0;
             i < tabla.getColumnCount();
             i++) {

            tabla.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(90);
        }

        // =====================================
        // SELECCION CELDA
        // =====================================

        ListSelectionListener eventoSeleccion = e -> {

            if (modoSeleccionFormula &&
                    esFormulaEditable()) {

                return;
            }

            int fila =
                    tabla.getSelectedRow();

            int columna =
                    tabla.getSelectedColumn();

            if (fila >= 0 &&
                    columna >= 0) {

                filaFormulaDestino = fila;
                columnaFormulaDestino = columna;

                char letra =
                        (char) ('A' + columna);

                String formula =
                        hojaActual()
                                .getMatriz()
                                .obtenerFormula(
                                        fila,
                                        columna
                                );

                String valor =
                        hojaActual()
                                .getMatriz()
                                .obtenerValor(
                                        fila,
                                        columna
                                );

                if (formula != null &&
                        !formula.equals("")) {

                    txtFormula.setText(
                            formula
                    );

                } else {

                    txtFormula.setText(
                            valor
                    );
                }

                estado.setText(
                        "Celda seleccionada: "
                                + letra
                                + (fila + 1)
                );
            }
        };

        tabla.getSelectionModel()
                .addListSelectionListener(
                        eventoSeleccion
                );

        tabla.getColumnModel()
                .getSelectionModel()
                .addListSelectionListener(
                        eventoSeleccion
                );

        tabla.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseReleased(MouseEvent e) {

                        if (modoSeleccionFormula &&
                                esFormulaEditable()) {

                            insertarReferenciaSeleccionada(tabla);
                        }
                    }
                }
        );

        // =====================================
        // GUARDAR DATOS
        // =====================================

        tabla.getModel()
                .addTableModelListener(e -> {

                    if (actualizado) {
                        return;
                    }

                    int fila =
                            e.getFirstRow();

                    int columna =
                            e.getColumn();

                    if (fila < 0 ||
                            columna < 0) {

                        return;
                    }

                    Object valor =
                            tabla.getValueAt(
                                    fila,
                                    columna
                            );

                    if (valor == null) {

                        return;
                    }

                    String texto =
                            valor.toString();

                    String formula = "";

                    // =================================
                    // FORMULAS
                    // =================================

                    if (texto.startsWith("=")) {

                        formula = texto;

                        double resultado =
                                ControladorFormula.evaluar(
                                        texto,
                                        hojaActual()
                                );

                        actualizado = true;

                        tabla.setValueAt(
                                resultado,
                                fila,
                                columna
                        );

                        actualizado = false;

                        texto =
                                String.valueOf(
                                        resultado
                                );
                    }

                    // =================================
                    // GUARDAR EN MATRIZ
                    // =================================

                    Hoja hoja =
                            hojaActual();

                    ControladorHoja controlador =
                            new ControladorHoja(
                                    hoja
                            );

                    controlador.guardarDato(
                            fila,
                            columna,
                            texto,
                            formula
                    );

                    recalcularFormulas();
                });

        return tabla;
    }

    // =========================================
    // NUMEROS DE FILAS
    // =========================================

    private void agregarNumerosFilas(
            JTable tabla,
            JScrollPane scroll
    ) {

        DefaultListModel<String> modeloFilas =
                new DefaultListModel<>();

        for (int i = 1; i <= 50; i++) {

            modeloFilas.addElement(
                    String.valueOf(i)
            );
        }

        JList<String> listaFilas =
                new JList<>(modeloFilas);

        listaFilas.setFixedCellWidth(45);

        listaFilas.setFixedCellHeight(
                tabla.getRowHeight()
        );

        listaFilas.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        listaFilas.setBackground(
                new Color(240,240,240)
        );

        listaFilas.setForeground(
                Color.BLACK
        );

        listaFilas.setCellRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus
                    ) {

                        JLabel label =
                                (JLabel)
                                        super.getListCellRendererComponent(
                                                list,
                                                value,
                                                index,
                                                false,
                                                false
                                        );

                        label.setHorizontalAlignment(
                                SwingConstants.CENTER
                        );

                        label.setBorder(
                                BorderFactory.createMatteBorder(
                                        0,
                                        0,
                                        1,
                                        1,
                                        new Color(200,200,200)
                                )
                        );

                        return label;
                    }
                }
        );

        scroll.setRowHeaderView(
                listaFilas
        );
    }

    // =========================================
    // RECALCULAR FORMULAS
    // =========================================

    private void recalcularFormulas() {

        actualizado = true;

        Hoja hoja =
                hojaActual();

        JTable tabla =
                tablaActual();

        for (int i = 0; i < 50; i++) {

            for (int j = 0; j < 26; j++) {

                String formula =
                        hoja.getMatriz()
                                .obtenerFormula(
                                        i,
                                        j
                                );

                if (formula != null &&
                        !formula.equals("")) {

                    double resultado =
                            ControladorFormula.evaluar(
                                    formula,
                                    hoja
                            );

                    tabla.setValueAt(
                            String.valueOf(resultado),
                            i,
                            j
                    );

                    hoja.getMatriz().insertar(
                            i,
                            j,
                            String.valueOf(resultado),
                            formula
                    );
                }
            }
        }

        actualizado = false;
    }

    // =========================================
    // CREAR HOJA VISUAL
    // =========================================

    private void crearHojaVisual(
            String nombre
    ) {

        DefaultTableModel modelo =
                new DefaultTableModel(
                        obtenerColumnas(),
                        50
                );

        JTable tabla =
                crearTabla(modelo);

        tablas.add(tabla);

        JScrollPane scroll =
                new JScrollPane(tabla);

        agregarNumerosFilas(
                tabla,
                scroll
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        pestanas.add(
                nombre,
                panel
        );
    }

    // =========================================
    // NUEVA HOJA
    // =========================================

    private void crearHoja() {

        int numero =
                pestanas.getTabCount() + 1;

        String nombre =
                "Hoja " + numero;

        libro.agregarHoja(nombre);

        crearHojaVisual(nombre);
    }

    // =========================================
    // CREAR HOJA DESDE ARCHIVO
    // =========================================

    private void crearHojaDesdeArchivo(
            Hoja hoja
    ) {

        DefaultTableModel modelo =
                new DefaultTableModel(
                        obtenerColumnas(),
                        50
                );

        JTable nuevaTabla =
                crearTabla(modelo);

        actualizado = true;

        for (int i = 0; i < 50; i++) {

            for (int j = 0; j < 26; j++) {

                String valor =
                        hoja.getMatriz()
                                .obtenerValor(i, j);

                if (!valor.equals("")) {

                    nuevaTabla.setValueAt(
                            valor,
                            i,
                            j
                    );
                }
            }
        }

        actualizado = false;

        tablas.add(nuevaTabla);

        JScrollPane scroll =
                new JScrollPane(nuevaTabla);

        agregarNumerosFilas(
                nuevaTabla,
                scroll
        );

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        pestanas.add(
                hoja.getNombre(),
                panel
        );
    }

    // =========================================
    // INSERTAR FORMULA DESDE MENU
    // =========================================

    private void insertarFormula(
            String formula
    ) {

        JTable tabla =
                tablaActual();

        if (tabla.getSelectedRow() >= 0 &&
                tabla.getSelectedColumn() >= 0) {

            filaFormulaDestino = tabla.getSelectedRow();
            columnaFormulaDestino = tabla.getSelectedColumn();
        }

        modoSeleccionFormula = true;
        ultimaReferenciaInsertada = "";

        txtFormula.setText(
                formula
        );

        txtFormula.requestFocus();
        txtFormula.setCaretPosition(
                Math.max(
                        0,
                        txtFormula.getText().length() - 1
                )
        );
    }

    // =========================================
    // MODO SELECCION DE FORMULA
    // =========================================

    private void prepararModoSeleccionFormula() {

        if (!esFormulaEditable()) {

            return;
        }

        JTable tabla =
                tablaActual();

        if (filaFormulaDestino == -1 ||
                columnaFormulaDestino == -1) {

            filaFormulaDestino =
                    tabla.getSelectedRow();

            columnaFormulaDestino =
                    tabla.getSelectedColumn();
        }

        modoSeleccionFormula = true;
    }

    private boolean esFormulaEditable() {

        String texto =
                txtFormula.getText()
                        .trim();

        return texto.startsWith("=") &&
                texto.contains("(");
    }

    private void insertarReferenciaSeleccionada(
            JTable tabla
    ) {

        int[] filas =
                tabla.getSelectedRows();

        int[] columnas =
                tabla.getSelectedColumns();

        if (filas.length == 0 ||
                columnas.length == 0) {

            return;
        }

        int filaInicio = filas[0];
        int filaFin = filas[filas.length - 1];
        int columnaInicio = columnas[0];
        int columnaFin = columnas[columnas.length - 1];

        String referencia;

        if (filas.length > 1 ||
                columnas.length > 1) {

            referencia =
                    convertirCelda(filaInicio, columnaInicio)
                            + ":"
                            + convertirCelda(filaFin, columnaFin);

        } else {

            referencia =
                    convertirCelda(filaInicio, columnaInicio);
        }

        if (referencia.equals(ultimaReferenciaInsertada)) {

            return;
        }

        String texto =
                txtFormula.getText();

        int indiceParentesis =
                texto.lastIndexOf(")");

        if (indiceParentesis == -1) {

            texto = texto + ")";
            indiceParentesis =
                    texto.length() - 1;
        }

        String antes =
                texto.substring(
                        0,
                        indiceParentesis
                );

        String despues =
                texto.substring(
                        indiceParentesis
                );

        if (!antes.endsWith("(") &&
                !antes.endsWith(",")) {

            antes = antes + ",";
        }

        txtFormula.setText(
                antes + referencia + despues
        );

        txtFormula.setCaretPosition(
                txtFormula.getText()
                        .lastIndexOf(")")
        );

        ultimaReferenciaInsertada = referencia;

        estado.setText(
                "Referencia agregada: "
                        + referencia
                        + "  |  Resultado en: "
                        + convertirCelda(
                                filaFormulaDestino,
                                columnaFormulaDestino
                        )
        );
    }

    private String convertirCelda(
            int fila,
            int columna
    ) {

        char letra =
                (char) ('A' + columna);

        return String.valueOf(letra) +
                (fila + 1);
    }

    private String completarFormula(
            String formula
    ) {

        String texto =
                formula.trim();

        if (texto.startsWith("=") &&
                texto.contains("(") &&
                !texto.endsWith(")")) {

            texto = texto + ")";
        }

        return texto;
    }

    // =========================================
    // AYUDA FORMULAS
    // =========================================

    private void mostrarAyudaFormulas() {

        JOptionPane.showMessageDialog(
                this,
                """
                Formulas disponibles:

                =suma(A1,A2,A3)
                =suma(A1:A10)
                =resta(A1,A2,A3)
                =multi(A1,A2,A3)
                =div(A1,A2,A3)
                =promedio(A1:A10)
                =mayor(A1:A10)
                =menor(A1:A10)

                Tambien puede usar numeros directos:
                =suma(10,20,A1)

                Pasos:
                1. Seleccione la celda donde quiere el resultado.
                2. Escriba una formula, por ejemplo: =suma(
                3. Haga clic en las celdas que quiere agregar.
                4. Tambien puede arrastrar un rango, por ejemplo A1:A5.
                5. Presione Aplicar.
                """
        );
    }

    // =========================================
    // EVALUAR FORMULA
    // =========================================

    private void evaluarFormula() {

        JTable tabla =
                tablaActual();

        int fila =
                filaFormulaDestino;

        int columna =
                columnaFormulaDestino;

        if (fila == -1 ||
                columna == -1) {

            fila = tabla.getSelectedRow();
            columna = tabla.getSelectedColumn();
        }

        if (fila == -1 ||
                columna == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una celda donde ira el resultado"
            );

            return;
        }

        String formula =
                completarFormula(
                        txtFormula.getText()
                );

        modoSeleccionFormula = false;
        ultimaReferenciaInsertada = "";

        tabla.setValueAt(
                formula,
                fila,
                columna
        );

        tabla.changeSelection(
                fila,
                columna,
                false,
                false
        );
    }

    // =========================================
    // GUARDAR ARCHIVO
    // =========================================

    private void guardarArchivo() {

        JFileChooser chooser =
                new JFileChooser();

        int opcion =
                chooser.showSaveDialog(this);

        if (opcion ==
                JFileChooser.APPROVE_OPTION) {

            String ruta =
                    chooser.getSelectedFile()
                            .getAbsolutePath();

            ArchivoManager.guardar(
                    libro,
                    ruta
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Archivo guardado correctamente"
            );
        }
    }

    // =========================================
    // ABRIR ARCHIVO
    // =========================================

    private void abrirArchivo() {

        JFileChooser chooser =
                new JFileChooser();

        int opcion =
                chooser.showOpenDialog(this);

        if (opcion ==
                JFileChooser.APPROVE_OPTION) {

            String ruta =
                    chooser.getSelectedFile()
                            .getAbsolutePath();

            libro.limpiar();

            pestanas.removeAll();

            tablas.clear();

            ArchivoManager.cargar(
                    libro,
                    ruta
            );

            for (int h = 0;
                 h < libro.totalHojas();
                 h++) {

                Hoja hoja =
                        libro.obtenerHoja(h);

                crearHojaDesdeArchivo(
                        hoja
                );
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Archivo cargado correctamente"
            );
        }
    }
}