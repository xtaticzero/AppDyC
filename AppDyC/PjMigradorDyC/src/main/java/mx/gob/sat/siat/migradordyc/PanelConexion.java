package mx.gob.sat.siat.migradordyc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class PanelConexion extends JPanel
{
    String[] labels = {"Usuario", "Password: ", "Host: ", "Puerto: ", "Nombre Servicio: "};
    JTextField[] textos;
    
    public PanelConexion(String titulo)
    {
        Border a = new TitledBorder(new LineBorder(Color.BLUE), titulo);
        this.setBorder(a);
        
        int numPairs = labels.length;
        textos = new JTextField[numPairs];

        //Create and populate the panel.
        JPanel p = new JPanel();
        p.setLayout(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);
            textos[i] = textField;
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
        
        p.setSize(400,300);
        
        this.add(p);

        JButton btnProbarConexion = new JButton("Probar conexión");
        btnProbarConexion.setSize(100, 60);
        btnProbarConexion.addActionListener (new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                System.out.println("---->Probar Conexiones<---");
                System.out.println("source ->" + e.getSource() + "<-");
                javax.swing.JButton boton = (JButton)e.getSource();
                
                PanelConexion panel = (PanelConexion)boton.getParent();
                String usuario = textos[0].getText();
                System.out.println("usuario ->" + usuario + "<-");
            } 
        });

        this.add(btnProbarConexion);
        
    }
}
