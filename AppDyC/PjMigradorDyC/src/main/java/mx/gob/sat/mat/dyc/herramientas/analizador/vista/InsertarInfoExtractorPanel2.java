package mx.gob.sat.mat.dyc.herramientas.analizador.vista;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.InsertarInfoExtractorBDelegate;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.ObtenerConexionesServiceImpl;
import mx.gob.sat.siat.migradordyc.MigradorDyCMain;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Component
public class InsertarInfoExtractorPanel2 extends javax.swing.JPanel 
{
    private static final Logger LOG = Logger.getLogger(MigradorDyCMain.class);

    @Autowired
    private InsertarInfoExtractorBDelegate delegate;

    @Autowired
    private ObtenerConexionesServiceImpl serviceObtenerConexiones;

    @Autowired
    private ApplicationContext appContext;

    private static final String SEP_CONEXION = " --- ";

    public InsertarInfoExtractorPanel2()
    {
        initComponents();
    }

    @PostConstruct
    private void cargarCmbConexiones()
    {
        Map<String, javax.sql.DataSource> conexiones = serviceObtenerConexiones.execute();
        List<String> descripcionesConexiones = new ArrayList<String>();
        
        Iterator<Map.Entry<String, javax.sql.DataSource>> it = conexiones.entrySet().iterator();

        while (it.hasNext()) {
          Map.Entry<String, javax.sql.DataSource> entry = it.next();
          DataSource conex = entry.getValue();
            try {
                descripcionesConexiones.add(entry.getKey() + SEP_CONEXION + conex.getConnection().getMetaData().getURL());
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(InsertarInfoExtractorPanel2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        cmbDataSourcesDest.setModel(new javax.swing.DefaultComboBoxModel(descripcionesConexiones.toArray()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cmbDataSourcesDest = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaInfoExtraida = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setText("Destino:");

        jLabel2.setText("Texto info:");

        jtaInfoExtraida.setColumns(20);
        jtaInfoExtraida.setRows(5);
        jScrollPane1.setViewportView(jtaInfoExtraida);

        jButton1.setText("Insertar info");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("DejaVu LGC Sans", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Insertar información extraída");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(59, 59, 59))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbDataSourcesDest, 0, 740, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbDataSourcesDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(92, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("INICIO INSERTAR INFO");
        String strConexion = (String)cmbDataSourcesDest.getModel().getSelectedItem();
        DataSource destino = (DataSource)appContext.getBean(strConexion.split(SEP_CONEXION)[0]);
        delegate.insertarInfo(destino, jtaInfoExtraida.getText());
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbDataSourcesDest;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtaInfoExtraida;
    // End of variables declaration//GEN-END:variables
}
