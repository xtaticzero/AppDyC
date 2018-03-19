package mx.gob.sat.siat.dyc.registro.service.empresascertificadas.impl;

import mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.EmpresaCertDAO;
import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.registro.service.empresascertificadas.EmpresaCertificadaService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * EmpresaCertificadaService
 * @author JEFG
 * @since 1.0 , 24 de Julio de 2014
 */
@Service(value = "empresaCertificadaService")
public class EmpresaCertificadaServiceImpl implements EmpresaCertificadaService {

    private static final Logger LOG = Logger.getLogger(EmpresaCertificadaServiceImpl.class);

    private static final String EMPRESA_REGISTRADA = "EMPRESA_REGISTRADA";
    private static final String FRC_NO_LOCALIZADO = "0";
    private static final String CERTIFICADA_TIPO_A = "A";
    private static final String CERTIFICADA_TIPO_AA = "AA";
    private static final String CERTIFICADA_TIPO_AAA = "AAA";
    private static final int IVA_CETIFICADO_TIPO_A = 127;
    private static final int IVA_CETIFICADO_TIPO_AA = 128;
    private static final int IVA_CETIFICADO_TIPO_AAA = 129;

    @Autowired
    private EmpresaCertDAO empresaCertDAO;

    @Override
    public ParamOutputTO<EmpresaCertVO> consultaEdoDeCertificado(String rfc, String usr) throws SIATException {
        LOG.info("INIT EMPRESAS CERTIFICADAS " + rfc + " " + usr);
        EmpresaCertVO edoCertificado = new EmpresaCertVO();
        edoCertificado.setTxtRfc(rfc);
        edoCertificado.setTxtUsr(usr);
        return new ParamOutputTO<EmpresaCertVO>(empresaCertDAO.consultaEmpresaCertificada(edoCertificado));
    }

    @Override
    public ParamOutputTO<String> isEmpresaCertificada(String rfc, int tramite) throws SIATException {
        String certificada = ConstantesDyC3.NO_REGISTRADA;
        EmpresaCertVO empresaCertVO =
                empresaCertDAO.consultaEmpresaCertificada(new EmpresaCertVO(rfc, ConstantesDyC.USR_STORED_PROCEDURES));

        if (!isRegistrada(empresaCertVO)) {
            String mod = empresaCertVO.getVdCveModalRfc().trim();
            if ((mod.equals(CERTIFICADA_TIPO_A)) && (tramite == IVA_CETIFICADO_TIPO_A)) {
                certificada = EMPRESA_REGISTRADA;
            } else if ((mod.equals(CERTIFICADA_TIPO_AA)) && (tramite == IVA_CETIFICADO_TIPO_AA)) {
                certificada = EMPRESA_REGISTRADA;
            } else if ((mod.equals(CERTIFICADA_TIPO_AAA)) && (tramite == IVA_CETIFICADO_TIPO_AAA)) {
                certificada = EMPRESA_REGISTRADA;
            } else {
                certificada = ConstantesDyC3.DIFERENTE_REGISTRO;
            }
        }
        return new ParamOutputTO<String>(certificada);
    }

    private boolean isRegistrada(EmpresaCertVO empresaCertVO) {
        if (null == empresaCertVO.getVdCveModalRfc()) {
            return Boolean.TRUE;
        } else if (empresaCertVO.getVdStatus().equals(FRC_NO_LOCALIZADO)) {
            return Boolean.TRUE;
        }

        return false;
    }

}
