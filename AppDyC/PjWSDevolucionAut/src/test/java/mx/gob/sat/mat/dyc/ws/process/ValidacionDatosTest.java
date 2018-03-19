/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import mx.gob.sat.mat.dyc.ws.utils.ValidacionDatos;
import java.math.BigInteger;
import mx.gob.sat.mat.dyc.ws.domain.DatosICEP;
import mx.gob.sat.mat.dyc.ws.domain.DatosTramite;
/*import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;*/
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author itlocal
 */
public class ValidacionDatosTest {

    /*
    public ValidacionDatosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    */

    @Test
    public void testRfcFormatoValido() {
        System.out.println("rfcFormato - valido");

        String rfc = "LEOA8107158N1";

        boolean expResult = Boolean.TRUE;
        boolean result = ValidacionDatos.rfcFormatoValido( rfc );

        assertEquals( expResult, result );
    }
    
    @Test
    public void testRfcFormatoInvalido() {
        System.out.println("rfcFormato - invalido");

        String rfc = "LEOA810ASDSD7158N1";

        boolean expResult = false;
        boolean result = ValidacionDatos.rfcFormatoValido( rfc );

        assertEquals( expResult, result );
    }

    @Test
    public void testIcepValido() {
        System.out.println("icepValido - valido");

        DatosICEP icep = getDatosIcepTestValido();

        boolean expResult = Boolean.TRUE;
        boolean result = ValidacionDatos.icepValido( icep );

        assertEquals(expResult, result);
    }
    
    private DatosICEP getDatosIcepTestValido (){
        DatosICEP datosICEP = new DatosICEP();

        datosICEP.setImpuesto( BigInteger.ONE );
        datosICEP.setConcepto( BigInteger.valueOf( 119 ) );
        datosICEP.setEjercicio( BigInteger.valueOf( 2017 ) );
        datosICEP.setPeriodo( "035" );

        return datosICEP;
    }
    
    @Test
    public void testIcepInvalido() {
        System.out.println("icepValido - invalido");

        DatosICEP icep = getDatosIcepTestInValido();

        boolean expResult = false;
        boolean result = ValidacionDatos.icepValido( icep );

        assertEquals(expResult, result);
        
        icep = null;
        result = ValidacionDatos.icepValido( icep );

        assertEquals(expResult, result);
    }
    
    private DatosICEP getDatosIcepTestInValido (){        
        DatosICEP datosICEP = getDatosIcepTestValido();
        datosICEP.setPeriodo( "033" );

        return datosICEP;
    }

    @Test
    public void testDatosTramiteValidos() {
        System.out.println( "datosTramite - validos" );

        DatosTramite datosTramite = getDatosTramiteValidos();

        boolean expResult = Boolean.TRUE;
        boolean result = ValidacionDatos.datosTramiteValidos( datosTramite );

        assertEquals(expResult, result);
    }
    
    @Test
    public void testDatosTramiteNoValidos() {
        System.out.println( "datosTramite - no validos" );

        DatosTramite datosTramite = getDatosTramiteNoValidos();

        boolean expResult = false;
        boolean result = ValidacionDatos.datosTramiteValidos( datosTramite );

        assertEquals(expResult, result);
    }
    
    private DatosTramite getDatosTramiteNoValidos (){
        DatosTramite datosTramite = getDatosTramiteValidos();
        datosTramite.setOrigenSaldo( BigInteger.ZERO );
                
        return datosTramite;
    }

    private DatosTramite getDatosTramiteValidos (){
        DatosTramite datosTramite = new DatosTramite();

        datosTramite.setOrigenSaldo( BigInteger.ONE );
        datosTramite.setTipoTramite( BigInteger.valueOf( 132 ) );

        return datosTramite;
    }
    
}
