package mx.gob.sat.siat.dyc.casocomp.dao.districomp;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * CrearCasoCompenDAO
 * @author [LADP] Luis Alberto Dominguez Palomino
 *
 */
public interface CreaCasoCompenDAO {
    
    
    /**Busca caso o aviso de compensacion por ICEP Origen y Destino, fecha de presentacion de la declaracion y numero de operacion*/
    List<DycpCompensacionDTO> buscaCompensacion(DycpCompensacionDTO casoCompensacionVO);
    
    /**Busca caso o aviso de compensacion que sea diferente al ICEP Compensado, fecha de presentacion de la declaracion y numero de operacion*/
    DycpCompensacionDTO buscaCompensacionDiferente(DycpCompensacionDTO dycpCompensacionDTO);
    
    int buscaCompensacionExistente(String numeroOperacion) ;
}
