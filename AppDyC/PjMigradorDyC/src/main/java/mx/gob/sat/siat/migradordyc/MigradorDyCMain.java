package mx.gob.sat.siat.migradordyc;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import mx.gob.sat.mat.dyc.herramientas.analizador.vista.InsertarInfoExtractorPanel2;
import mx.gob.sat.mat.dyc.herramientas.analizador.vista.PruebasSPDB2Panel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class MigradorDyCMain extends JFrame
{
    private static final Logger LOG = Logger.getLogger(MigradorDyCMain.class);
    final JTextField txtOSI = new JTextField(20);
    
    PrimerPanel primerPanel;
    
    @Autowired
    private ExtractorScripts extractorScripts;
    
    @Autowired
    private PruebasSPDeclsDWHPanel panelPruebasSP;

    @Autowired
    private PruebasSPDB2Panel pnlProbarSPDB2;

    @Autowired
    private InsertarInfoExtractorPanel2 pnlInsertarInfoExtactor;

    public MigradorDyCMain()
    {
     
    }
    
    @PostConstruct
    private void inicializar()
    {
        setTitle("Migrador Devoluciones y Compensaciones");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        JPanel panelConexiones = crearPanelConexiones();
        System.out.println("primerPanel ->" + primerPanel + "<-");
        jtp.addTab("Conexiones", panelConexiones);
        jtp.addTab("Utils BD", primerPanel);
        jtp.addTab("Panel querys", crearPanelInserts());
        //jtp.addTab("StoreProcedure decls DWH", new StoreDeclsDWHPanel());
        //jtp.addTab("StoreProcedure decls DWH", new PruebasSPDeclsDWHPanel());
        jtp.addTab("StoreProcedure decls DWH", panelPruebasSP);
        jtp.addTab("Probar SP DB2", pnlProbarSPDB2);
        jtp.addTab("Insertar info extractor", pnlInsertarInfoExtactor);

        this.setSize(900,700);
    }

    private JPanel crearPanelConexiones()
    {
        System.out.println("Inicio crearPanelConexiones");
        JPanel panelConexiones = new JPanel();
        GridLayout gl = new GridLayout(0, 2);
        panelConexiones.setLayout(gl);

        JPanel pnlOrigen = new PanelConexion("Origen");

        JPanel pnlDestino = new PanelConexion("Destino");

        panelConexiones.add(pnlOrigen);
        panelConexiones.add(pnlDestino);

        return panelConexiones;
    }

    private JPanel crearPanelInserts()
    {
        System.out.println("Inicio crearPanelInserts");
        JPanel panelQuerys = new JPanel();
        JLabel l = new JLabel();
        l.setText("Obtener Inserts");
        panelQuerys.add(l);
        
        panelQuerys.add(txtOSI);

        JButton btnObtenerSentsInsert = new JButton("Obtener Sentencias Inserts");
        btnObtenerSentsInsert.setSize(100, 60);
        btnObtenerSentsInsert.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("OSI -->" +  txtOSI.getText()  + "<---");
                
                //extractorScripts.extraerScripts("DYCC_ESTADOSOL");
                extractorScripts.generarArchivoInserts();
                System.out.println("FIN sentencias insert ----------------------------------------");
            } 
        });

        panelQuerys.add(btnObtenerSentsInsert);
        
        return panelQuerys;
    }

    public static void main(String[] argumentos)
    {
        try {
            LOG.info("ejecutandose migrador DyC");
            ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
            MigradorDyCMain validador = context.getBean(MigradorDyCMain.class);
            PrimerPanel p = context.getBean(PrimerPanel.class);
            System.out.println("p ->" + p);
            validador.primerPanel = p;
            validador.inicializar();
            validador.setVisible(Boolean.TRUE);   
        }
        catch (Exception e) {
            System.err.println("error ->" + e.getMessage());    
            e.printStackTrace();
        }
    }

}




