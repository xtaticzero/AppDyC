package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.io.IOException;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mx.gob.sat.mat.dyc.controlsaldos.service.ObtenerDeclaracionesDHWService;
import mx.gob.sat.mat.dyc.integrarexpediente.service.impl.IcepServiceImpl;
import mx.gob.sat.mat.dyc.integrarexpediente.service.impl.IcepServiceImpl.EnumSPDB2;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.dao.controlsaldos.icep.SiosConsultaPagosStoredProcedureInterface;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.procedures.IcepUrdcFat1StoreProcedure;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.DeclaracionIcepParamVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service("serviceObtenerDeclaracionesDHW")
public class ObtenerDeclaracionesDHWServiceImpl implements ObtenerDeclaracionesDHWService
{
    private static final Logger LOG = Logger.getLogger(ObtenerDeclaracionesDHWServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private IcepServiceImpl serviceUtilsSP;

    @Autowired
    private JdbcTemplate jdbcTemplateDYCDB2;

    @Autowired
    private SiosConsultaPagosStoredProcedureInterface spBuscarPagos;

    private DateFormat formatFechaSpDB2 = new SimpleDateFormat("yyyy-MM-dd");
        
    private static final Integer[] CONCEPTOS_DECLARACIONES_EN_DB2 = new Integer[]{ConstantesIds.IdsConceptos.ISR_PERSONAS_MORALES, 
                                                                                 ConstantesIds.IdsConceptos.ISR_PERSONAS_FISICAS};

    @Override
    public List<DeclaracionDwhVO> execute(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        LOG.debug("INICIO execute ObtenerDeclaracionesDHWServiceImpl");

        try
        {
            List<DeclaracionDwhVO> declaracionesDWH = new ArrayList<DeclaracionDwhVO>();
            if(saldoIcep.getDyccConceptoDTO() == null)
            {
                DyctSaldoIcepDTO saldoIcepAux = daoSaldoIcep.encontrar(saldoIcep.getIdSaldoIcep());
                saldoIcep.setRfc(saldoIcepAux.getRfc());
                saldoIcep.setDyccConceptoDTO(saldoIcepAux.getDyccConceptoDTO());
                saldoIcep.setDyccPeriodoDTO(saldoIcepAux.getDyccPeriodoDTO());
                saldoIcep.setDyccEjercicioDTO(saldoIcepAux.getDyccEjercicioDTO());
                saldoIcep.setDyccOrigenSaldoDTO(saldoIcepAux.getDyccOrigenSaldoDTO());
                saldoIcep.setFechaFin(saldoIcepAux.getFechaFin());
            }

            if(Arrays.asList(CONCEPTOS_DECLARACIONES_EN_DB2).contains(saldoIcep.getDyccConceptoDTO().getIdConcepto()) )
            {
                LOG.debug("debido al concepto del icep (" + saldoIcep.getDyccConceptoDTO().getIdConcepto() + ") se buscaran declaraciones en DB2");
                LOG.debug("idEjercicio ->" + saldoIcep.getDyccEjercicioDTO().getIdEjercicio());
                LOG.debug("RFC ->" + saldoIcep.getRfc());

                IcepUrdcFatDTO paramsSpDB2 = new IcepUrdcFatDTO();
                paramsSpDB2.setRfc(saldoIcep.getRfc());
                paramsSpDB2.setBoId(saldoIcep.getBoId());
                paramsSpDB2.setPeriodo(saldoIcep.getDyccPeriodoDTO().getIdPeriodo().toString());
                paramsSpDB2.setEjercicio(saldoIcep.getDyccEjercicioDTO().getIdEjercicio().toString());
                paramsSpDB2.setConcepto(saldoIcep.getDyccConceptoDTO().getIdConcepto().toString());
                paramsSpDB2.setUsuario(ConstantesDyC.USR_STORED_PROCEDURES);
                LOG.debug("saldoIcep.getDyccConceptoDTO().getIdConcepto() ->" + saldoIcep.getDyccConceptoDTO().getIdConcepto());
                
                if(saldoIcep.getDyccConceptoDTO().getIdConcepto() == ConstantesIds.IdsConceptos.ISR_PERSONAS_MORALES)
                {
                    LOG.debug("El concepto es de una persona moral");
                    paramsSpDB2.setTipoTramite(Integer.toString(ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSMORAL));
                    if(saldoIcep.getDyccEjercicioDTO().getIdEjercicio() >= ConstanteValidacionRNFDC10.YEAR_2014 )
                    {
                        String callStoreProcedure = serviceUtilsSP.retornaSpDb2(EnumSPDB2.CVE_SP_TRVFAT1.getClave());
                        LOG.debug("callStoreProcedure ->" + callStoreProcedure);
                        IcepUrdcFat1StoreProcedure storeProcedure = new IcepUrdcFat1StoreProcedure(jdbcTemplateDYCDB2, callStoreProcedure);
                        LOG.debug("Ejercicio ->" + paramsSpDB2.getEjercicio());
                        List<IcepUrdcFatDTO> declsDB2 =   storeProcedure.ejecutarSP(paramsSpDB2);
                        LOG.debug("numero de declaraciones encontradas en el SP de DB2 ->" + declsDB2.size());
                        declaracionesDWH.addAll(convertir(declsDB2));
                    }
                }
                else if(saldoIcep.getDyccConceptoDTO().getIdConcepto() == ConstantesIds.IdsConceptos.ISR_PERSONAS_FISICAS)
                {
                    paramsSpDB2.setTipoTramite(Integer.toString(ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSFISICA));
                    if(saldoIcep.getDyccEjercicioDTO().getIdEjercicio() >= ConstanteValidacionRNFDC10.YEAR_2013 )
                    {
                        LOG.debug("PERSONA FISICA ->" + saldoIcep.getRfc() + "<-");
                        String callStoreProcedure = serviceUtilsSP.retornaSpDb2(EnumSPDB2.CVE_SP_TRVFAT1.getClave());
                        LOG.debug("callStoreProcedure ->" + callStoreProcedure);
                        LOG.debug("Ejercicio ->" + paramsSpDB2.getEjercicio());
                        
                        IcepUrdcFat1StoreProcedure storeProcedure = new IcepUrdcFat1StoreProcedure(jdbcTemplateDYCDB2, callStoreProcedure);
                        LOG.debug("Ejercicio ->" + paramsSpDB2.getEjercicio());
                        List<IcepUrdcFatDTO> declsDB2 =   storeProcedure.ejecutarSP(paramsSpDB2);
                        LOG.debug("numero de declaraciones encontradas en el SP de DB2 ->" + declsDB2.size());
                        declaracionesDWH.addAll(convertir(declsDB2));
                    }
                }
            }        

            DeclaracionIcepParamVO paramsSP = new DeclaracionIcepParamVO();
            paramsSP.setIdConcepto(saldoIcep.getDyccConceptoDTO().getIdConcepto());
            paramsSP.setIdEjercicio(saldoIcep.getDyccEjercicioDTO().getIdEjercicio());
            paramsSP.setIdPeriodo(saldoIcep.getDyccPeriodoDTO().getIdPeriodo());
            //TODO: verificar porque el tipotramite no debería ser necesario para encontrar las declaraciones y en su caso quitarlo porque esta en duro
            paramsSP.setIdTipoTramite(ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSFISICA);
            paramsSP.setRfc(saldoIcep.getRfc());
            paramsSP.setUsuario(ConstanteInfoAdicional.USUARIO_DYC);
    
            List<DeclaracionDwhVO> declaracionesDWHIcep = obtenerDeclsXIcep(paramsSP);
            declaracionesDWH.addAll(declaracionesDWHIcep);

            List<DeclaracionDwhVO> declaracionesDwhTramite = spBuscarPagos.execute(paramsSP);
            eliminarDeclsVacias(declaracionesDwhTramite);
            declaracionesDWH.addAll(declaracionesDwhTramite);

            declaracionesDWH = depurarDeclaracionesRepetidas(declaracionesDWH);
            return declaracionesDWH;
        }
        catch (SQLException e) {
            LOG.error("SQLException Ocurrio un error al buscar declaraciones en los sistemas legados ->" + e.getMessage());
            LOG.error("SQLException Se regresara una lista vacia de declaraciones; paramsRecibidos ->" + saldoIcep);
            ManejadorLogException.getTraceError(e);
            return new ArrayList<DeclaracionDwhVO>();
        }
        catch (IOException e) {
            LOG.error("IOException Ocurrio un error al buscar declaraciones en los sistemas legados ->" + e.getMessage());
            LOG.error("IOException Se regresara una lista vacia de declaraciones; paramsRecibidos ->" + saldoIcep);
            ManejadorLogException.getTraceError(e);
            return new ArrayList<DeclaracionDwhVO>();
        }
        catch (SIATException e) {
            LOG.error("SIATException Ocurrio un error al buscar declaraciones en los sistemas legados ->" + e.getMessage());
            LOG.error("SIATException Se regresara una lista vacia de declaraciones; paramsRecibidos ->" + saldoIcep);
            ManejadorLogException.getTraceError(e);
            return new ArrayList<DeclaracionDwhVO>();
        }
    }

    private List<DeclaracionDwhVO> convertir(List<IcepUrdcFatDTO> listaOriginal) throws SIATException{
        try {
            List<DeclaracionDwhVO> listaResult = new ArrayList<DeclaracionDwhVO>();
            for(IcepUrdcFatDTO dto : listaOriginal)
            {
                LOG.debug("IcepUrdcFatDTO (objeto recibido del DB2) ->" + ExtractorUtil.ejecutar(dto));
                DeclaracionDwhVO vo = new DeclaracionDwhVO();
                vo.setTipoDeclaracion(Integer.parseInt(dto.getTipoDeclaracion()));
                vo.setFechaPresentacion(formatFechaSpDB2.parse(dto.getFechPresentacion()));
                LOG.debug("fechaPresentacion ->" + dto.getFechPresentacion());
                vo.setNumOperacion(Long.parseLong(dto.getNumOper()));
                vo.setSaldo(Double.parseDouble(dto.getSaldoFavor()));
                listaResult.add(vo);
            }

            return listaResult;
        }
        catch (ParseException e) 
        {
            LOG.error("Error al convertir la fecha de presentacion del objeto IcepUrdcFatDTO ->" + e.getMessage());
            throw new SIATException(e);
        }
    }

    private List<DeclaracionDwhVO> obtenerDeclsXIcep(DeclaracionIcepParamVO declaracionIcepParam) throws SIATException
    {
        try {
            List<DeclaracionDwhVO> lstDeclaracionDwhVO = spBuscarPagos.execute(declaracionIcepParam);
            eliminarDeclsVacias(lstDeclaracionDwhVO);
            LOG.debug("numero de declaraciones encontradas en el SP por ICEP: ->" + lstDeclaracionDwhVO.size());
            return lstDeclaracionDwhVO;
        } catch (DataAccessException dae) {

            LOG.error (ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage () + ConstantesDyC1.TEXTO_7_ERROR_DAO +
                       spBuscarPagos);
            ManejadorLogException.getTraceError(dae);
            throw dae;
        }
    }

    private void eliminarDeclsVacias(List<DeclaracionDwhVO> declaraciones)
    {
        List<DeclaracionDwhVO> declsVacias = new ArrayList<DeclaracionDwhVO>();

        for(DeclaracionDwhVO declaracion : declaraciones)
        {
            if(declaracion.getFechaPresentacion() == null){
                declsVacias.add(declaracion);
            }
        }

        declaraciones.removeAll(declsVacias);
    }

    private List<DeclaracionDwhVO> depurarDeclaracionesRepetidas(List<DeclaracionDwhVO> declaraciones)
    {
        List<DeclaracionDwhVO> declsDepuradas = new ArrayList<DeclaracionDwhVO>();
        for(DeclaracionDwhVO declAux : declaraciones)
        {
            if(!existeEnLista(declsDepuradas, declAux)){
                declsDepuradas.add(declAux);
            }
        }
        return declsDepuradas;
    }
    
    private boolean existeEnLista(List<DeclaracionDwhVO> declaraciones, DeclaracionDwhVO declaracion)
    {
        for(DeclaracionDwhVO declAux : declaraciones)
        {
            if(declaracion.getNumOperacion().longValue() == declAux.getNumOperacion().longValue()){
                return Boolean.TRUE;
            }
        }

        return false;
    }
}
