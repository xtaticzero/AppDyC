package mx.gob.sat.siat.migradordyc;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class StoreDeclsDWHPanel extends JPanel
{
    private final static String[] parametros = {"Rfc: ", "Periodo: ", "Ejercicio: ", "Concepto: ", "Usuario: "};
    private JTextField[] textos;
    
    public StoreDeclsDWHPanel() {
        super();
        inicializar();
    }
    
    private void inicializar()
    {
        add(crearPanelParametros(""));
        
        JButton btnInicializarSecuencias = new JButton("probar Store Procedure");
        btnInicializarSecuencias.setSize(100, 60);

        add(btnInicializarSecuencias);
    }
    
    
    public JPanel crearPanelParametros(String titulo)
    {
        JPanel panelParametros = new JPanel();
        Border a = new TitledBorder(new LineBorder(Color.BLUE), titulo);
        this.setBorder(a);
        
        int numPairs = parametros.length;
        textos = new JTextField[numPairs];

        //Create and populate the panel.
        
        panelParametros.setLayout(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(parametros[i], JLabel.TRAILING);
            panelParametros.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            panelParametros.add(textField);
            textos[i] = textField;
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panelParametros,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
        
        panelParametros.setSize(400,300);
        
      
        
        return panelParametros;
        
    }
}
