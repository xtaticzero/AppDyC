package mx.gob.sat.siat.migradordyc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.BuscaDeclsCtrolSaldosStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.ObtenerDeclsDWHStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.SiosConsultaPagosStoredProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.PruebasMasivasSPsBD;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.PruebasSPDeclsDWHIcepDelegate;
import mx.gob.sat.mat.dyc.herramientas.analizador.vista.helper.DeclaracionesDWHModel;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author Huetzin Cruz Lozano
 */
@Component
public class PruebasSPDeclsDWHPanel extends javax.swing.JPanel
{
    @Autowired
    private PruebasSPDeclsDWHIcepDelegate delegate;
    
    @Autowired
    private PruebasMasivasSPsBD delegatePruebasMasivas;

    public PruebasSPDeclsDWHPanel() {
        
    }

    @PostConstruct
    public void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDeclaracionesDWH = new javax.swing.JTable();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblConcepto = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        cmbPeriodo = new javax.swing.JComboBox();
        cmbEjercicio = new javax.swing.JComboBox();
        cmbImpuesto = new javax.swing.JComboBox();
        cmbConcepto = new javax.swing.JComboBox();
        cmbTipoTramite = new javax.swing.JComboBox();

        Dimension d = new Dimension();
        d.setSize(300, 25);

        cmbConcepto.setPreferredSize(d);
        cmbTipoTramite.setPreferredSize(d);
        txtUser = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbTipoPeriodo = new javax.swing.JComboBox();
        lblLog = new javax.swing.JLabel();
        btnProbarSP = new javax.swing.JButton();
        btnProbarSP_TRVCPOE1 = new javax.swing.JButton();
        btnProbarSP_TRVCFAT1 = new javax.swing.JButton();
        btnPruebasMasivas = new javax.swing.JButton();
        
        lblErrores = new javax.swing.JLabel();

        txtUser.setText(PruebasSPDeclsDWHIcepDelegate.PARAM_USUARIO_SUGERIDO);

        List<DeclaracionDWHBean> declaraciones = new ArrayList<DeclaracionDWHBean>();
        tblDeclaracionesDWH.setModel(new DeclaracionesDWHModel(declaraciones));

        jScrollPane1.setViewportView(tblDeclaracionesDWH);

        jInternalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jInternalFrame1.setTitle("Parámetros");
        jInternalFrame1.setVisible(Boolean.TRUE);

        jLabel1.setText("RFC:");

        jLabel2.setText("Periodo:");

        jLabel3.setText("Ejercicio");

        lblConcepto.setText("Concepto:");

        jLabel5.setText("TipoTramite");

        lblUser.setText("User:");

        jLabel8.setText("Impuesto:");

        cmbEjercicio.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerEjercicios()));

        cmbImpuesto.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerImpuestos()));
        cmbImpuesto.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                cmbConcepto.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerConceptos((DyccImpuestoDTO)cmbImpuesto.getSelectedItem())));
            } 
        });

        cmbTipoTramite.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerTiposTramite()));
        cmbTipoTramite.setMaximumSize( cmbTipoTramite.getPreferredSize() );
        txtUser.setName("txtUser"); // NOI18N

        jLabel9.setText("Tipo periodo:");
        System.out.println("delegate --->" + delegate);
        cmbTipoPeriodo.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerTiposPeriodo()));
        
        cmbTipoPeriodo.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                cmbPeriodo.setModel(new javax.swing.DefaultComboBoxModel(delegate.obtenerPeriodos((DyccTipoPeriodoDTO)cmbTipoPeriodo.getSelectedItem())));
            } 
        });

        btnProbarSP.setText(ObtenerDeclsDWHStoreProcedure.NOMBRE);
        btnProbarSP.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Probar " + ObtenerDeclsDWHStoreProcedure.NOMBRE);
                Map<String, Object> params = crearCapsulaParams();
                Map<String, Object> respSP = delegate.probarSP(params);

                tblDeclaracionesDWH.setModel(new DeclaracionesDWHModel((List<DeclaracionDWHBean>)respSP.get("declaraciones")));
                lblLog.setText(new Date() + ": " + ObtenerDeclsDWHStoreProcedure.NOMBRE + "; \n->" + params);
            } 
        });

        btnProbarSP_TRVCPOE1.setText(BuscaDeclsCtrolSaldosStoreProcedure.NOMBRE);
        btnProbarSP_TRVCPOE1.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                Map<String, Object> respSP = delegate.ejecutarSP_TRVCPOE1(crearCapsulaParams());

                tblDeclaracionesDWH.setModel(new DeclaracionesDWHModel((List<DeclaracionDWHBean>)respSP.get("declaraciones")));
                lblLog.setText(new Date() + ": Se ejecutó el Store procedure TRVCPOE1 ");
            } 
        });

        btnProbarSP_TRVCFAT1.setText(SiosConsultaPagosStoredProcedure.NOMBRE);
        btnProbarSP_TRVCFAT1.setToolTipText(delegate.obtenerInfoSP_SIO_TRVCFAT1());
        btnProbarSP_TRVCFAT1.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                Map<String, Object> respSP = delegate.ejecutarSP_SIO_TRVCFAT1(crearCapsulaParams());

                tblDeclaracionesDWH.setModel(new DeclaracionesDWHModel((List<DeclaracionDWHBean>)respSP.get("declaraciones")));
                lblLog.setText(new Date() + ": Se ejecutó el Store procedure ejecutar " + SiosConsultaPagosStoredProcedure.NOMBRE);
            } 
        });

        btnPruebasMasivas.setText("pruebas masivas");
        btnPruebasMasivas.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                delegatePruebasMasivas.probar();  
                System.out.println("FIN pruebas masivas");
            } 
        });

        hacerLayout();
    }

    private  Map<String, Object> crearCapsulaParams(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("rfc", txtRfc.getText());
        params.put("periodo", cmbPeriodo.getSelectedItem());
        params.put("ejercicio", cmbEjercicio.getSelectedItem());
        params.put("concepto", cmbConcepto.getSelectedItem());
        params.put("tipoTramite", cmbTipoTramite.getSelectedItem());
        params.put("user", txtUser.getText());

        return params;    
    }

    private void hacerLayout(){
        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(lblConcepto)
                            .addComponent(jLabel5)
                            .addComponent(lblUser))
                        .addGap(27, 27, 27)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUser)
                            .addComponent(cmbTipoTramite, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbConcepto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbImpuesto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbEjercicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jInternalFrame1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(26, 26, 26)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipoPeriodo, 0, 251, Short.MAX_VALUE)
                            .addComponent(txtRfc, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbTipoPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbEjercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConcepto)
                    .addComponent(cmbConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbTipoTramite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        lblLog.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblErrores.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblErrores, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(btnProbarSP, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProbarSP_TRVCPOE1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProbarSP_TRVCFAT1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPruebasMasivas, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                          
        
                          )
        
                .addGap(49, 49, 49))
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblLog, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnProbarSP)
                        .addComponent(btnProbarSP_TRVCPOE1)
                        .addComponent(btnProbarSP_TRVCFAT1)
                        .addComponent(btnPruebasMasivas)
                        .addGap(29, 29, 29)
                        .addComponent(lblErrores, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLog, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    private javax.swing.JComboBox cmbConcepto;
    private javax.swing.JComboBox cmbEjercicio;
    private javax.swing.JComboBox cmbImpuesto;
    private javax.swing.JComboBox cmbPeriodo;
    private javax.swing.JComboBox cmbTipoPeriodo;
    private javax.swing.JComboBox cmbTipoTramite;
    private javax.swing.JButton btnProbarSP;
    private javax.swing.JButton btnProbarSP_TRVCPOE1;
    private javax.swing.JButton btnProbarSP_TRVCFAT1;
    private javax.swing.JButton btnPruebasMasivas;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblLog;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDeclaracionesDWH;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtUser;
}
