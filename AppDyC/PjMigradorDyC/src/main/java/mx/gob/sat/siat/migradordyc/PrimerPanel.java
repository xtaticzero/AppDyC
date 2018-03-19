package mx.gob.sat.siat.migradordyc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import mx.gob.sat.mat.dyc.herramientas.analizador.negocio.MigrarVPagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class PrimerPanel extends JPanel
{
    @Autowired
    private JdbcTemplate jdbcTemplateOrigen;
     
    @Autowired
    private JdbcTemplate jdbcTemplateDestino;
     
    @Autowired
    ManejadorSecuencias manejadorSecuencias;
     
     private LinkedList<TablaDTO> tablasOrigen;
     
     private LinkedList<TablaDTO> tablasDestino;
     
     @Autowired
     private InsertadorInfo insertador;
     
     @Autowired
     private Comparador comparador;
    
     @Autowired
     private CargadorInfoDB cargador;
     
     @Autowired
     private Limpiador limpiador;
     
     @Autowired
     private ManejadorSecuencias migradorSecuencias;
     
    @Autowired
    private MigrarVPagosService serviceMigrarVPagos;
     
    JTextField t;
    
    
    public PrimerPanel()
    {
        t = new JTextField(20);
        this.add(t);

          JButton b = new JButton("Migrar Tabla");
          b.setSize(100, 60);
          b.addActionListener (new ActionListener ()
          {
             public void actionPerformed (ActionEvent e)
             {
                System.out.println("Inicio migrar tabla");
                System.out.println("Se copiará de origen a destino los registros de la tabla -->" +  t.getText() + "<---");
                System.out.println("insertador ->" + insertador);
                insertador.copiarInfoTabla(t.getText());
                System.out.println("Fin proceso Migrar Tabla");
             } 
          });

          this.add(b);
          
        JButton buttonBTV = new JButton("Buscar Tablas Vacias");
        buttonBTV.setSize(100, 60);
        buttonBTV.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Buscando tablas vacias");
                comparador.buscarTablasSinRegistros(jdbcTemplateDestino);
            } 
        });
        this.add(buttonBTV);
        
        JButton buttonCEBD = new JButton("Comparar Estructuras BD");
        buttonCEBD.setSize(100, 60);
        buttonCEBD.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Se comparará estructuras de la Bases de Datos");
                tablasOrigen = cargador.obtenerInfoDataBase(jdbcTemplateOrigen);
                tablasDestino = cargador.obtenerInfoDataBase(jdbcTemplateDestino);
                comparador.compararEstructurasBasesDatos(tablasOrigen, tablasDestino);
            } 
        });
        this.add(buttonCEBD);
        
        JButton buttonDT = new JButton("DROP Tablas");
        buttonDT.setSize(100, 60);
        buttonDT.addActionListener (new ActionListener ()
        {
          public void actionPerformed (ActionEvent e)
          {
           System.out.println("DROP A todas las tablas");
           limpiador.dropearTablas();
          } 
        });
        this.add(buttonDT);
        
        JButton buttonDropSeq = new JButton("DROP Secuencias");
        buttonDropSeq.setSize(100, 60);
        buttonDropSeq.addActionListener (new ActionListener ()
        {
          public void actionPerformed (ActionEvent e)
          {
           System.out.println("DROP ALL_SEQUENCES");
           limpiador.dropearSecuencias();
          } 
        });
        this.add(buttonDropSeq);

        
        JButton buttonMTB = new JButton("Cargar BD Destino (migrar toda la BD)");
        buttonMTB.setSize(100, 60);
        buttonMTB.addActionListener (new ActionListener ()
        {
          public void actionPerformed (ActionEvent e)
          {
              System.out.println("se migrará toda la Base ....");

              if(tablasOrigen == null || tablasOrigen.isEmpty())
              {
                  tablasOrigen = cargador.obtenerInfoDataBase(jdbcTemplateOrigen);
                  System.out.println("la lista tablasOrigen no estaba cargada, se cargo con " + tablasOrigen.size() + " tablas.");
              }

              if(tablasDestino == null || tablasDestino.isEmpty())
              {
                  tablasDestino = cargador.obtenerInfoDataBase(jdbcTemplateDestino);
                  System.out.println("la lista tablasDestino no estaba cargada, se cargo con " + tablasDestino.size() + " tablas.");
              }
        
              insertador.copiarInfoTablas(tablasOrigen);
              System.out.println("Fin proceso copiar info toda la base");
          } 
        });
        this.add(buttonMTB);
       
        JButton buttonSS = new JButton("Sincronizar Secuencias");
                 buttonSS.setSize(100, 60);
                 buttonSS.addActionListener (new ActionListener ()
                 {
                     public void actionPerformed (ActionEvent e)
                     {
                         System.out.println("INICIO Sincronizar Secuencias....");
                         migradorSecuencias.sincronizarSecuencias();
                         System.out.println("FIN Sincronizar Secuencias");
                     } 
                 });
        this.add(buttonSS);
        
        JButton buttonLT = new JButton("Limpiar Tablas");
        buttonLT.setSize(100, 60);
        buttonLT.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("se limpiara toda la Base ....");
                if(tablasDestino == null || tablasDestino.isEmpty())
                {
                    tablasDestino = cargador.obtenerInfoDataBase(jdbcTemplateDestino);
                    System.out.println("la lista tablasDestino no estaba cargada, se cargo con " + tablasDestino.size() + " tablas.");
                }
            
                 limpiador.limpiarTablas(tablasDestino);
                 System.out.println("Fin proceso limpiar toda la base");
            } 
        });
        this.add(buttonLT);
        
        JButton btnObtnerInfoRS = new JButton("Obtener info ResultSet");
        btnObtnerInfoRS.setSize(100, 60);
        btnObtnerInfoRS.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println(" INICIO Obtener info ResultSet");
                cargador.obtenerInfoResultSet(jdbcTemplateDestino, " select * from vpagos where 1 = 0 ");
            } 
        });
        this.add(btnObtnerInfoRS);
        
        JButton btnMigrarVPagos = new JButton("Migrar VPagos");
        btnMigrarVPagos.setSize(100, 60);
        btnMigrarVPagos.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println(" migrar vpagos");
                serviceMigrarVPagos.execute();
            } 
        });
        this.add(btnMigrarVPagos);
        
        JButton btnInicializarSecuencias = new JButton("Inicializar Secuencias");
        btnInicializarSecuencias.setSize(100, 60);
        btnInicializarSecuencias.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("Inicializar seucencias ... de destino");
                manejadorSecuencias.inicializarSecuencias();
                System.out.println("FIN inicializar secuencias");
            } 
        });
        this.add(btnInicializarSecuencias);
        
        JTextArea textArea = new JTextArea(5, 20);
        this.add(textArea);
        textArea.setEditable(false);
                
    }
}
