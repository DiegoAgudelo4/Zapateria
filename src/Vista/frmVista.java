package Vista;

import Empresa.Zapato;
import Procesos.Modificar_txt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class frmVista extends javax.swing.JFrame {

    private String ruta_txt = "Zapatos.txt";
    Zapato p;
    Modificar_txt rp;
    int clic_tabla;

    public frmVista() {
        initComponents();
        this.setTitle("Zapatería");
        this.setLocationRelativeTo(null);
        rp = new Modificar_txt();

        try {
            cargar_txt();
            listarRegistro();

        } catch (Exception ex) {
            mensaje("No existe el archivo txt");
        }
    }//fin frmVista

    public ArrayList<Zapato> deserializar() {
        ArrayList<Zapato> a = new ArrayList<Zapato>();
        try {

            FileInputStream fis = new FileInputStream("serializado.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            a = (ArrayList<Zapato>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Deserializacion correcta de serializado.dat");
        } catch (IOException ioe) {
            ioe.printStackTrace();
//            return ;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
//            return;
        }

        return a;

    }

    public void cargar_txt() {
        try {
            ArrayList<Zapato> a = this.deserializar(); //deserializa el arrchivo 
            for (Zapato temp : a) { //inserta todos los datos al arraylist
                p = new Zapato();
                p.setIdZapatos((temp.getIdZapatos()));
                p.setNombre(temp.getNombre());
                p.setTamaño(temp.getTamaño());
                p.setTipo(temp.getTipo());
                p.setMarca(temp.getMarca());
                p.setPrecio((temp.getPrecio()));
                p.setColor(temp.getColor());
                rp.agregarRegistro(p);
            }
        } catch (Exception ex) {
//            if(ex.getMessage().equals("null")){
//                mensaje("EL archivo está vacío");
//            }else{
            mensaje("Error al cargar archivo: " + ex.getMessage());
//            }

            System.out.println(ex.getMessage());
        }
    }//fin cargar txt

    public void serializar() {

        try {
            try (FileOutputStream fileOut = new FileOutputStream("serializado.dat");
                    ObjectOutputStream escribir = new ObjectOutputStream(fileOut)) {
                escribir.writeObject(rp.getA()); //obtiene el arraylist para luego ser serializado
                escribir.close();
                fileOut.close();
            }
            System.out.printf("Serialización de datos correcta en archivo serializado.dat\n");
        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }//fin try/catch
    }//fin serializar

    public void ingresarRegistro(File ruta) {
        try {
            if (leerId() == -666) {
                mensaje("Ingresar id entero");
            } else if (leerNombre().equals("")) {
                mensaje("Ingresar Nombre");
            } else if (leerTamaño().equals("")) {
                mensaje("ingrese tamaño");
            } else if (leerTipo().equals("")) {
                mensaje("ingrese tipo");
            } else if (leerMarca().equals("")) {
                mensaje("ingrese marca");
            } else if (leerPrecio() == -666) {
                mensaje("Ingresar Precio");
            } else if (leerColor().equals("")) {
                mensaje("ingrese color");
            } else {
                p = new Zapato(leerId(), leerNombre(), leerTamaño(), leerTipo(), leerMarca(), leerPrecio(), leerColor());
                if (rp.buscaId(p.getIdZapatos()) != -1) {
                    mensaje("Este ID ya existe");
                } else {
                    rp.agregarRegistro(p);
                }
                serializar();
                listarRegistro();
                this.limpiar_texto(panel);
            }
        } catch (Exception ex) {
            mensaje("Ingresar registro Error: " + ex.getMessage());
        }
    } //fin ingresar registro

    public void modificarRegistro(File ruta) {
        try {
            if (leerId() == -666) {
                mensaje("Ingresar id entero");
            } else if (leerNombre().equals("")) {
                mensaje("Ingresar Nombre");
            } else if (leerTamaño().equals("")) {
                mensaje("ingrese tamaño");
            } else if (leerTipo().equals("")) {
                mensaje("ingrese tipo");
            } else if (leerMarca().equals("")) {
                mensaje("ingrese marca");
            } else if (leerPrecio() == 0) {
                mensaje("Ingresar Precio");
            } else if (leerColor().equals("")) {
                mensaje("ingrese color");
            } else {
                int id = rp.buscaId(leerId());
                p = new Zapato(leerId(), leerNombre(), leerTamaño(), leerTipo(), leerMarca(), leerPrecio(), leerColor());
                //lee tdos los J TEXT FIELD en una variable tipo ZAPATO para luego ser modificada en 'MODIFICARrEGISTRO'
                if (id == -1) {
                    rp.agregarRegistro(p);
                } else {
                    rp.modificarRegistro(id, p);
                }

                serializar();
                listarRegistro();
                this.limpiar_texto(panel);

            }
        } catch (Exception ex) {

            mensaje("Modificar registro error:" + ex.getMessage());
        }

    }//fin modificar registro
    /////////////////////////////////////

    public void buscar() {
        Zapato z = rp.buscarCelda(leerId());//se busca por el valor del 'jtf_id'

        if (z != null) {//si es diferente de null significa que lo encontró
            int id = (int) z.getIdZapatos();
            String nombre = "" + z.getNombre();
            String tamaño = "" + z.getTamaño();
            String Tipo = "" + z.getTipo();
            String marca = "" + z.getMarca();
            double precio = (double) z.getPrecio();
            String color = "" + z.getColor();
            //se reciben los datos y se insertan en los jtf
            jtf_id.setText(String.valueOf(id));
            jtf_Nombre.setText(nombre);
            jtf_Tamaño.setText(tamaño);
            jtf_Tipo.setText(Tipo);
            jtf_Marca.setText(marca);
            jtf_Precio.setText(String.valueOf(precio));
            jtf_Color.setText(color);
        } else {
            mensaje("El elemento no Existe");
        }

    }//fin buscar

    public void eliminarRegistro() {
        try {
            if (leerId() == -666) {
                mensaje("Ingrese el ID entero");
            } else {
                int id = rp.buscaId(leerId());
                if (id == -1) {
                    mensaje("El ID no existe");
                } else {
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto", "Si/No", 0);
                    if (s == 0) {
                        rp.eliminarRegistro(id);
                        serializar();
                        listarRegistro();
                        this.limpiar_texto(panel);
                    }
                }
            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }//fin eliminar registro

    public void listarRegistro() {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dt.addColumn("Id");
        dt.addColumn("Nombre");
        dt.addColumn("Tamaño");
        dt.addColumn("Tipo");
        dt.addColumn("Marca");
        dt.addColumn("Precio");
        dt.addColumn("Color");

        Object fila[] = new Object[dt.getColumnCount()];
        for (int i = 0; i < rp.cantidadRegistro(); i++) {
            p = rp.obtenerRegistro(i);
            fila[0] = p.getIdZapatos();
            fila[1] = p.getNombre();
            fila[2] = p.getTamaño();
            fila[3] = p.getTipo();
            fila[4] = p.getMarca();
            fila[5] = p.getPrecio();
            fila[6] = p.getColor();
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(30);
    }//fin listar registro

    //metodo para limpiar cualquier panel con campos de 'J TEXT FIELD'
    public void limpiar_texto(JPanel panel) {
        for (int i = 0; panel.getComponents().length > i; i++) {
            if (panel.getComponents()[i] instanceof JTextField) {
                ((JTextField) panel.getComponents()[i]).setText("");
            } else if (panel.getComponents()[i] instanceof JPasswordField) {
                ((JPasswordField) panel.getComponents()[i]).setText("");
            }
        }
    }//fin limpiar texto

    // METODOS PARA OBTENER LOS DATOS DE LOS 'J TEXT FIELD'
    //buscan el campo y retornan el valor
    public int leerId() {
        try {
            int id = Integer.parseInt(jtf_id.getText().trim());
            return id;
        } catch (Exception ex) {
            return -666;
        }
    }//leer id

    public String leerNombre() {
        try {
            String nombre = jtf_Nombre.getText();//.trim().replace(" ", "_");
            return nombre;
        } catch (Exception ex) {
            return null;
        }
    }//leerNombre

    public String leerTamaño() {
        try {
            String tamaño = jtf_Tamaño.getText();//.trim().replace(" ", "_");
            return tamaño;
        } catch (Exception ex) {
            return null;
        }
    }//fin leer tamaño

    public String leerTipo() {
        try {
            String tipo = jtf_Tipo.getText();//.trim().replace(" ", "_");
            return tipo;
        } catch (Exception ex) {
            return null;
        }
    }//fin leerTipo

    public String leerMarca() {
        try {
            String marca = jtf_Marca.getText();//.trim().replace(" ", "_");
            return marca;
        } catch (Exception ex) {
            return null;
        }
    }//leerMarca

    public double leerPrecio() {
        try {
            double precio = Integer.parseInt(jtf_Precio.getText().trim());
            return precio;
        } catch (Exception ex) {
            return -666;
        }
    }//fin leerPrecio

    public String leerColor() {
        try {
            String color = jtf_Color.getText();//.trim().replace(" ", "_");
            return color;
        } catch (Exception ex) {
            return null;
        }
    }//fin leerColor

    //FIN METODOS PARA OBTENER LOS DATOS DE LOS 'J TEXT FIELD'
    //metodo para no estar escribiendo el metodo de JOptionPane
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }//fin mensaje

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel8 = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jtp_Registrar = new javax.swing.JTabbedPane();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtf_id = new javax.swing.JTextField();
        jtf_Nombre = new javax.swing.JTextField();
        jtf_Tamaño = new javax.swing.JTextField();
        jtf_Tipo = new javax.swing.JTextField();
        jtf_Marca = new javax.swing.JTextField();
        jtf_Precio = new javax.swing.JTextField();
        jtf_Color = new javax.swing.JTextField();
        btn_Guardar = new javax.swing.JButton();
        btn_Modificar = new javax.swing.JButton();
        btn_Borrar = new javax.swing.JButton();
        btn_Salir = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btn_Buscar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtp_Registrar.setAutoscrolls(true);
        jtp_Registrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtp_Registrar.setFocusable(false);
        jtp_Registrar.setName(""); // NOI18N
        jtp_Registrar.setOpaque(true);
        jtp_Registrar.setPreferredSize(new java.awt.Dimension(700, 610));

        panel.setBackground(new java.awt.Color(204, 255, 204));
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        panel.setFont(new java.awt.Font("Leelawadee UI", 0, 11)); // NOI18N
        panel.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setText("Tamaño:");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Tipo: ");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Marca:");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("Precio:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setText("Color:");

        jtf_id.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        btn_Guardar.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        btn_Guardar.setText("Guardar");
        btn_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarActionPerformed(evt);
            }
        });

        btn_Modificar.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        btn_Modificar.setText("Modificar");
        btn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModificarActionPerformed(evt);
            }
        });

        btn_Borrar.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        btn_Borrar.setText("Borrar");
        btn_Borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BorrarActionPerformed(evt);
            }
        });

        btn_Salir.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        btn_Salir.setText("Salir");
        btn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalirActionPerformed(evt);
            }
        });

        txtRuta.setEditable(false);
        txtRuta.setBackground(new java.awt.Color(204, 255, 204));
        txtRuta.setForeground(new java.awt.Color(204, 255, 204));
        txtRuta.setBorder(null);
        txtRuta.setCaretColor(new java.awt.Color(204, 255, 204));
        txtRuta.setDisabledTextColor(new java.awt.Color(204, 255, 204));

        tabla.setAutoCreateRowSorter(true);
        tabla.setBackground(new java.awt.Color(153, 153, 255));
        tabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Tamaño", "Tipo", "Marca", "Precio", "Color"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setToolTipText("");
        tabla.setEnabled(false);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);
        tabla.getAccessibleContext().setAccessibleName("");

        jButton1.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        jButton1.setText("Limpiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_Buscar.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        btn_Buscar.setText("Buscar");
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });

        jLabel9.setText("Nota: Para modificar un objeto debe dar click sobre él en la tabla y luego modificalo como deseee.");

        jLabel10.setText("Para buscar ingrese el id que desea encontrar y luego presiona \"Buscar\"");

        jLabel11.setText("Precione \"Limpiar\" si desea vaciar todas las casillas.");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtf_Tipo, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(jtf_Nombre)
                    .addComponent(jtf_Tamaño))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(36, 36, 36)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                            .addComponent(jtf_Precio, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(78, 78, 78))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(jtf_Color, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtf_Marca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRuta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jtf_Marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jtf_Precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtf_Tamaño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtf_Color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addGap(27, 27, 27)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_Tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(7, 7, 7)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtp_Registrar.addTab("Registrar ", panel);
        panel.getAccessibleContext().setAccessibleName("Zapateria");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtp_Registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtp_Registrar, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtp_Registrar.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        this.limpiar_texto(panel);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        // TODO add your handling code here:

        clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int id = (int) tabla.getValueAt(clic_tabla, 0);
        String nombre = "" + tabla.getValueAt(clic_tabla, 1);
        String tamaño = "" + tabla.getValueAt(clic_tabla, 2);
        String Tipo = "" + tabla.getValueAt(clic_tabla, 3);
        String marca = "" + tabla.getValueAt(clic_tabla, 4);
        double precio = (double) tabla.getValueAt(clic_tabla, 5);
        String color = "" + tabla.getValueAt(clic_tabla, 6);

        jtf_id.setText(String.valueOf(id));
        jtf_Nombre.setText(nombre);
        jtf_Tamaño.setText(tamaño);
        jtf_Tipo.setText(Tipo);
        jtf_Marca.setText(marca);
        jtf_Precio.setText(String.valueOf(precio));
        jtf_Color.setText(color);

    }//GEN-LAST:event_tablaMouseClicked

    private void btn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_SalirActionPerformed

    private void btn_BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BorrarActionPerformed
        this.eliminarRegistro();
    }//GEN-LAST:event_btn_BorrarActionPerformed

    private void btn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModificarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.modificarRegistro(ruta);
        this.serializar();
        this.deserializar();

    }//GEN-LAST:event_btn_ModificarActionPerformed

    private void btn_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarActionPerformed
        File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);
        this.serializar();
        this.deserializar();
    }//GEN-LAST:event_btn_GuardarActionPerformed

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btn_BuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Borrar;
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JButton btn_Guardar;
    private javax.swing.JButton btn_Modificar;
    private javax.swing.JButton btn_Salir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jtf_Color;
    private javax.swing.JTextField jtf_Marca;
    private javax.swing.JTextField jtf_Nombre;
    private javax.swing.JTextField jtf_Precio;
    private javax.swing.JTextField jtf_Tamaño;
    private javax.swing.JTextField jtf_Tipo;
    private javax.swing.JTextField jtf_id;
    private javax.swing.JTabbedPane jtp_Registrar;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables

}
