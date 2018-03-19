package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.util.List;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.VPagosInformixDAO;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.VPagosOracleDAO;
import mx.gob.sat.siat.migradordyc.dto.VPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Huetzin Cruz Lozano
 */
@Service
public class MigrarVPagosService
{
    @Autowired
    private VPagosInformixDAO daoVPagosInfx;

    @Autowired
    private VPagosOracleDAO daoVPagosOracle;

    private static final char[] VOCALES = new char[]{'A', 'E', 'I', 'O', 'U'};
    
    public void execute(){
        System.out.println("INICIO MigrarVPagosService ya en el service");

        for(int codPrimerLetra = 65; codPrimerLetra <= 90; codPrimerLetra++)
        {
            for(int posVocal = 0; posVocal < VOCALES.length; posVocal++)
            {
                
                {
                    String prefijoLike = Character.toString ((char) codPrimerLetra) + VOCALES[posVocal] + "%";
                    List<VPagoDTO> pagos = daoVPagosInfx.selecLikeXRfc(prefijoLike);
                    System.out.println(prefijoLike + "numero de pagos encontrados ->" + pagos.size());
                    if( !pagos.isEmpty())
                    {
                        daoVPagosOracle.insertarLote(pagos);
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        char a='a';
        char A='A';
        char Z = 'Z';
        System.out.println((int)a +" "+(int)A);
        System.out.println((int)Z);
        //65 - 90
        for(int codPrimerLetra = 65; codPrimerLetra <= 90; codPrimerLetra++)
        {
            for(int posVocal = 0; posVocal < VOCALES.length; posVocal++)
            {
                for(int codTercerLetra = 65; codTercerLetra <= 90; codTercerLetra++)
                {
                    String prefijoLike = Character.toString ((char) codPrimerLetra) + VOCALES[posVocal] +  Character.toString ((char) codTercerLetra) + "%";
                    System.out.println("->" + prefijoLike);
                }
            }
        }
    }
}
