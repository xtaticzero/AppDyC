    /*
    * Todos los Derechos Reservados 2013 SAT.
    * Servicio de Administracion Tributaria (SAT).
    *
    * Este software contiene informacion propiedad exclusiva del SAT considerada
    * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    **/
    
    package mx.gob.sat.mat.dyc.integrarexpediente.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.SQLException;

import java.util.List;
import java.util.Properties;

import mx.gob.sat.mat.dyc.integrarexpediente.service.IcepService;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.IcepDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Israel Chavez
 */
    @Service(value = "icepService")
    public class IcepServiceImpl implements IcepService {
        private static final Logger LOG = Logger.getLogger(IcepServiceImpl.class.getName());
    
        @Autowired
        private IcepDAO icepDAO;
    
        @Autowired
        private DyctSaldoIcepDAO dyctSaldoIcepDAO;
    
        @Override
        public IcepUrdcFatDTO obtieneIcepUrdFat(IcepUrdcFatDTO icep) throws SQLException{
            return this.icepDAO.obtieneIcepUrdFat(icep);
        }
    
        @Override
        public IcepUrdcsilDTO obtieneIcepUrdcsil(IcepUrdcsilDTO icepUrdcsilDTO) throws SQLException {
            IcepUrdcsilDTO retrievedIcepUrdcsilDTO = new IcepUrdcsilDTO();
            List<IcepUrdcsilDTO> icepUrdcsilDTOArray = this.icepDAO.obtieneIcepUrdcsil(icepUrdcsilDTO);
            if (icepUrdcsilDTOArray.size() != ConstantesDyC2.SIZE_DE_LISTA_VACIA) {
                retrievedIcepUrdcsilDTO = (IcepUrdcsilDTO)icepUrdcsilDTOArray.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA);
            }
            return retrievedIcepUrdcsilDTO;
        }

        @Override
        public IcepSioUrucple1DTO obtieneIcepSioUrucple1DTO(IcepSioUrucple1DTO icepSioUrucple1DTO) throws SQLException {
    
            IcepSioUrucple1DTO retrievedIcepSioUrucple1DTO = this.icepDAO.retrieveIcepSpSioUrucple1(icepSioUrucple1DTO);
    
            return retrievedIcepSioUrucple1DTO;
        }
    
    
        @Override
        public IcepSioUrucple1DTO obtenerIcepPorTipoTramite(IcepSioUrucple1DTO icepDTO) throws SQLException {
            IcepSioUrucple1DTO icepDTOPorTipoTramite = null;
            if (icepDTO.getTipoTramite().equals(String.valueOf(ConstantesIds.TIPO_TRAMITE_NO334))) {
                icepDTOPorTipoTramite = icepDAO.retrieveIcepSpSioUrucple1(icepDTO);
            } else if (icepDTO.getTipoTramite().equals(String.valueOf(ConstantesIds.TIPO_TRAMITE_NO120)) ||
                       icepDTO.getTipoTramite().equals(String.valueOf(ConstantesIds.TIPO_TRAMITE_NO347)) ||
                       icepDTO.getTipoTramite().equals(String.valueOf(ConstantesIds.TIPO_TRAMITE_NO451))) {
                icepDTOPorTipoTramite = icepDAO.retrieveIcepSpSioUrucple1(icepDTO);
            }
            return icepDTOPorTipoTramite;
        }
    
        public IcepUrdcFatDTO seteaIcepFat(ObtieneIcepDTO obtieneIcepDTO) {
            IcepUrdcFatDTO icepFatDTO = new IcepUrdcFatDTO();
            icepFatDTO.setBoId(obtieneIcepDTO.getBoId());
            icepFatDTO.setRfc(obtieneIcepDTO.getRfc());
            icepFatDTO.setPeriodo(obtieneIcepDTO.getPeriodo());
            icepFatDTO.setEjercicio(obtieneIcepDTO.getEjercicio());
            icepFatDTO.setConcepto(obtieneIcepDTO.getConcepto());
            icepFatDTO.setTipoTramite(obtieneIcepDTO.getTipoTramite());
            icepFatDTO.setUsuario(obtieneIcepDTO.getUsuario());
            return icepFatDTO;
    
        }
    
        public IcepUrdcsilDTO seteaIcepUrd(ObtieneIcepDTO obtieneIcepDTO) {
            IcepUrdcsilDTO icepUrd = new IcepUrdcsilDTO();
    
            icepUrd.setRfc(obtieneIcepDTO.getRfc());
            icepUrd.setRfc1(ConstantesDyC2.CADENA_VACIA);
            icepUrd.setRfc2(ConstantesDyC2.CADENA_VACIA);
            icepUrd.setPeriodo(obtieneIcepDTO.getPeriodo());
            icepUrd.setEjercicio(obtieneIcepDTO.getEjercicio());
            icepUrd.setConcepto(obtieneIcepDTO.getConcepto());
            icepUrd.setTipoTramite(obtieneIcepDTO.getTipoTramite());
            icepUrd.setUsuario(obtieneIcepDTO.getUsuario());
            return icepUrd;
    
        }
    
        public IcepSioUrucple1DTO seteaIcepSioUru(ObtieneIcepDTO obtieneIcepDTO) {
            IcepSioUrucple1DTO icepSioUru = new IcepSioUrucple1DTO();
            icepSioUru.setRfc(obtieneIcepDTO.getRfc());
            icepSioUru.setPeriodo(obtieneIcepDTO.getPeriodo());
            icepSioUru.setEjercicio(obtieneIcepDTO.getEjercicio());
            icepSioUru.setConcepto(obtieneIcepDTO.getConcepto());
            icepSioUru.setTipoTramite(obtieneIcepDTO.getTipoTramite());
            icepSioUru.setUsuario(obtieneIcepDTO.getUsuario());
            return icepSioUru;
        }
    
    @Override
    @Transactional(isolation = Isolation.DEFAULT, readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public ObtieneIcepDTO obtenerIcep(ObtieneIcepDTO params) throws SQLException, IOException
    {
        LOG.debug("INICIO obtenerIcep");
        IcepUrdcFatDTO icepFatDTO = seteaIcepFat(params);

        if (ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSMORAL == Integer.parseInt(params.getTipoTramite())
                || ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSFISICA == Integer.parseInt(params.getTipoTramite())) 
        {
            if( ConstantesIds.IdsTiposTramite.DEVOL_SAF_ISR_PERSMORAL == Integer.parseInt(params.getTipoTramite()))
            {
                if(Integer.parseInt(params.getEjercicio()) >= ConstanteValidacionRNFDC10.YEAR_2014 ){
                    icepFatDTO = this.icepDAO.obtieneIcepUrdcFat1(icepFatDTO, retornaSpDb2(EnumSPDB2.CVE_SP_URDFAT1.getClave()));
                }else if (Integer.parseInt(params.getEjercicio()) < ConstanteValidacionRNFDC10.YEAR_2014){
                    icepFatDTO = this.icepDAO.obtieneIcepUrdFat(icepFatDTO);
                }
            }
            else{
                if(Integer.parseInt(params.getEjercicio()) >= ConstanteValidacionRNFDC10.YEAR_2013 ){
                    icepFatDTO = this.icepDAO.obtieneIcepUrdcFat1(icepFatDTO, retornaSpDb2(EnumSPDB2.CVE_SP_URDFAT1.getClave()));
                }else if (Integer.parseInt(params.getEjercicio()) < ConstanteValidacionRNFDC10.YEAR_2013){
                    icepFatDTO = this.icepDAO.obtieneIcepUrdFat(icepFatDTO);
                }
            }

            if (icepFatDTO.getEstatus().equals(String.valueOf(ConstantesDyC1.UNO)))
            {
                params.setEstatus(icepFatDTO.getEstatus());
                params.setTipoDeclaracion(icepFatDTO.getTipoDeclaracion());
                params.setFechPresentacion(icepFatDTO.getFechPresentacion());
                params.setNumOper(icepFatDTO.getNumOper());
                params.setSaldoFavor(!icepFatDTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE) ? icepFatDTO.getSaldoFavor() :
                                             String.valueOf(ConstantesDyC1.CERO));
                return params;
            }
        }

        icepFatDTO = seteaIcepFat(params);
        icepFatDTO = obtieneIcepUrdFat(icepFatDTO);

        if (icepFatDTO.getEstatus().equals(String.valueOf(ConstantesDyC1.UNO))) {
            params.setEstatus(icepFatDTO.getEstatus());
            params.setTipoDeclaracion(icepFatDTO.getTipoDeclaracion());
            params.setFechPresentacion(icepFatDTO.getFechPresentacion());
            params.setNumOper(icepFatDTO.getNumOper());
            params.setSaldoFavor(!icepFatDTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE) ? icepFatDTO.getSaldoFavor() :
                                         String.valueOf(ConstantesDyC1.CERO));
            return params;
        }

        if (params.getConcepto().equals(Integer.toString(ConstantesIds.IdsConceptos.IEPS_ALCOHOL_DESNAT_MIELES))) 
        {
            IcepSioUrucple1DTO inputIcep = new IcepSioUrucple1DTO();
            inputIcep.setRfc(params.getRfc());
            inputIcep.setPeriodo(params.getPeriodo());
            inputIcep.setConcepto(params.getConcepto());
            inputIcep.setEjercicio(params.getEjercicio());
            inputIcep.setTipoTramite(params.getTipoTramite());
            inputIcep.setEstatus(String.valueOf(ConstantesDyC1.CERO));

            inputIcep = this.icepDAO.obtieneIcepUrdcsil1(inputIcep);

            if (inputIcep.getEstatus().equals("1")) {
                params.setEstatus(inputIcep.getEstatus());
                params.setTipoDeclaracion(inputIcep.getTipoDeclaracion());
                params.setFechPresentacion(inputIcep.getFechPresentacion());
                params.setNumOper(inputIcep.getNumOper());
                params.setSaldoFavor(!inputIcep.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE) ? inputIcep.getSaldoFavor() :
                                             String.valueOf(ConstantesDyC1.CERO));
                return params;
            }
        }

        IcepSioUrucple1DTO icepSioUru = seteaIcepSioUru(params);
        icepSioUru = obtieneIcepSioUrucple1DTO(icepSioUru);
        
        if (icepSioUru.getEstatus().equals("1"))
        {
            params.setEstatus(icepSioUru.getEstatus());
            params.setTipoDeclaracion(icepSioUru.getTipoDeclaracion());
            params.setFechPresentacion(icepSioUru.getFechPresentacion());
            params.setNumOper(icepSioUru.getNumOper());
            params.setSaldoFavor(!icepSioUru.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE) ? icepSioUru.getSaldoFavor() :
                                         String.valueOf(ConstantesDyC1.CERO));
            return params;
        }

        LOG.info("NO EXISTEN DECLARACIONES PARA EL ICEP");
        return params;
    }
    
    public boolean obtenerIcepUrdFat(IcepUrdcFatDTO icepFatDTO)
    {
        boolean banderaIcepFat = false;
        try {
            IcepUrdcFatDTO icepFat = obtieneIcepUrdFat(icepFatDTO);
            if (icepFat.getEstatus() == null || icepFat.getEstatus().equals("0") || icepFat.getEstatus().equals("2")) {
                banderaIcepFat = false;
            } else {
                banderaIcepFat = Boolean.TRUE;
            }
        } catch (SQLException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        return banderaIcepFat;
    }
    
    public boolean obtenerIcepUrdcsil(IcepUrdcsilDTO icepUrd) {
        boolean banderaIcepUrd = false;
        try {
            IcepUrdcsilDTO icepU = obtieneIcepUrdcsil(icepUrd);
            if (icepU.getEstatus() == null || icepU.getEstatus().equals("0") || icepU.getEstatus().equals("2")) {
                banderaIcepUrd = false;
            } else {
                banderaIcepUrd = Boolean.TRUE;
            }
        } catch (SQLException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        return banderaIcepUrd;
    }

    public boolean obtenerIcepSioUrucple1DTO(IcepSioUrucple1DTO icepSioUru) throws SQLException {
        boolean banderaIcepSioUru = false;
        IcepSioUrucple1DTO icepSio = obtieneIcepSioUrucple1DTO(icepSioUru);
        if (icepSio.getEstatus() == null || icepSio.getEstatus().equals("0") || icepSio.getEstatus().equals("2")) {
            banderaIcepSioUru = false;
        } else {
            banderaIcepSioUru = Boolean.TRUE;
        }
        return banderaIcepSioUru;
    }
    
    @Override
    public IcepSioUrucple1DTO obtieneIcepUrdcsil1(IcepSioUrucple1DTO icepDTO) {

        IcepSioUrucple1DTO retrievedIcepSioUrucple1DTO = null;

        try {
            retrievedIcepSioUrucple1DTO = this.icepDAO.obtieneIcepUrdcsil1(icepDTO);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return retrievedIcepSioUrucple1DTO;
    }
        
        /**
         * Retorna nombre y owner de stored procedured del DB2.
         *
         * @param  idSpDb2 : Identificador del Store Procedured;.
         * @claveSp 1: SP_DYC_URDCFAT1
         * @claveSp 2: SP_DYC_TRVCFAT1.
         * @return spDb2 : Owner y Store Procedured DB2.
         * 
         */
        public String retornaSpDb2(int idSpDb2) throws IOException {
            StringBuffer spDb2 = new StringBuffer();
            String nombreSP = null;
            InputStream is = null;
            try {
                Properties prop = new Properties();
                is = new FileInputStream(ConstantesDyC3.SELLADORA);
                prop.load(is);
                
                nombreSP = EnumSPDB2.CVE_SP_URDFAT1.getClave() == idSpDb2? prop.getProperty(EnumSPDB2.SP_URDFAT1.getValor())
                                                                : prop.getProperty(EnumSPDB2.SP_TRVFAT1.getValor());
                spDb2.append(prop.getProperty(EnumSPDB2.OWNER.getValor())).append(EnumSPDB2.SEPARADOR.getValor()).append(nombreSP);
                is.close();
            } finally {
                try {
                    if (is instanceof InputStream) {
                        is.close();
                    }
                } catch (Exception e) {
                    LOG.info(e.getCause());
                }
            }
            return spDb2.toString();
        }

    @Override
    public DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo, DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO) {
        
        
        return dyctSaldoIcepDAO.encontrar(rfc, concepto, ejercicio, periodo, dyccOrigenSaldoEjercicioDTO);
    }

        /**
         * Enum Valores DB2.
         *
         */
        public enum EnumSPDB2{
        OWNER("db2.owner")
        ,SP_URDFAT1("db2.sp.urdFat1")
        ,SP_TRVFAT1("db2.sp.trvFat1")
        ,SEPARADOR(".") 
        ,CVE_SP_URDFAT1(1)
        ,CVE_SP_TRVFAT1(2);
            
        private String valor;
        private int clave;
            
        private EnumSPDB2(String valor){
            this.valor = valor;
        }
            
        private EnumSPDB2(int clave){
            this.clave = clave;
        }


        public String getValor() {
            return valor;
        }

        public int getClave() {
            return clave;
        }
    }

    public DyctSaldoIcepDAO getDyctSaldoIcepDAO() {
        return dyctSaldoIcepDAO;
    }

    public void setDyctSaldoIcepDAO(DyctSaldoIcepDAO dyctSaldoIcepDAO) {
        this.dyctSaldoIcepDAO = dyctSaldoIcepDAO;
    }
    
        
    }
