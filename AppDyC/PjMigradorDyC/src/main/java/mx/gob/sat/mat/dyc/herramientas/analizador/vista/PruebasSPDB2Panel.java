package mx.gob.sat.mat.dyc.herramientas.analizador.vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.ObtenerDeclsDWHStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.PruebasSPDB2Delegate;
import mx.gob.sat.mat.dyc.herramientas.analizador.vista.helper.DeclaracionesDWHModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author Huetzin Cruz Lozano
 */
@Component
public class PruebasSPDB2Panel extends javax.swing.JPanel
{
    @Autowired
    private PruebasSPDB2Delegate delegate;
    
    public PruebasSPDB2Panel() {
        
    }

    @PostConstruct
    public void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeclaracionesDWH = new javax.swing.JTable();
        lblRfc = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JLabel();
        lblEjercicio = new javax.swing.JLabel();
        lblConcepto = new javax.swing.JLabel();
        lblTipoTramite = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField(10);
        txtPeriodo = new javax.swing.JTextField(10);
        txtEjercicio = new javax.swing.JTextField(10);
        txtConcepto = new javax.swing.JTextField(10);
        txtTipoTramite = new javax.swing.JTextField(10);

        Dimension d = new Dimension();
        d.setSize(300, 25);

        txtConcepto.setPreferredSize(d);
        txtTipoTramite.setPreferredSize(d);
        btnProbarSP = new javax.swing.JButton();
        
        List<DeclaracionDWHBean> declaraciones = new ArrayList<DeclaracionDWHBean>();
        tblDeclaracionesDWH.setModel(new DeclaracionesDWHModel(declaraciones));

        jScrollPane1.setViewportView(tblDeclaracionesDWH);

        lblRfc.setText("RFC:");

        lblPeriodo.setText("Periodo:");

        lblEjercicio.setText("Ejercicio");

        lblConcepto.setText("Concepto:");

        lblTipoTramite.setText("TipoTramite");

      //  cmbEjercicio.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerEjercicios()));

       // cmbTipoTramite.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerTiposTramite()));
        txtTipoTramite.setMaximumSize( txtTipoTramite.getPreferredSize() );


        System.out.println("delegate --->" + delegate);
       // cmbTipoPeriodo.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerTiposPeriodo()));
        
        btnProbarSP.setText(ObtenerDeclsDWHStoreProcedure.NOMBRE);
        btnProbarSP.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Probar SP_DYC_TRVCFAT1");
                Map<String, Object> params = crearCapsulaParams();
               
               /* params.put("rfc", "");
                params.put("periodo", "");  //"35"
                params.put("ejercicio", "");// "2014"
                params.put("concepto", ""); // "101"
                params.put("tipoTramite", ""); // 111*/
                delegate.ejecutarSPDB2(params);
            } 
        });

        hacerLayout();
    }

    private  Map<String, Object> crearCapsulaParams(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rfc", txtRfc.getText());
        params.put("periodo", txtPeriodo.getText());
        params.put("ejercicio", txtEjercicio.getText());
        params.put("concepto", txtConcepto.getText());
        params.put("tipoTramite", txtTipoTramite.getText());

        return params;    
    }

    private void hacerLayout(){
      /*  jInternalFrame1.add(txtRfc);
        jInternalFrame1.add(cmbPeriodo);
        jInternalFrame1.setSize(300, 300);*/
        add(lblRfc);
        add(txtRfc);
        add(lblPeriodo);
        add(txtPeriodo);
        add(lblEjercicio);
        add(txtEjercicio);
        add(lblConcepto);
        add(txtConcepto);
        add(lblTipoTramite);
        add(txtTipoTramite);
        
        add(btnProbarSP);
        add(tblDeclaracionesDWH);
    }

    private javax.swing.JButton btnProbarSP;
    private javax.swing.JLabel lblRfc;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblEjercicio;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel lblTipoTramite;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDeclaracionesDWH;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtPeriodo;
    private javax.swing.JTextField txtEjercicio;
    private javax.swing.JTextField txtConcepto;
    private javax.swing.JTextField txtTipoTramite;
}
