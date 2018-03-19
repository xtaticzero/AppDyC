package mx.gob.sat.siat.dyc.servicio.dto.devoluciones;

import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Selladora;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteArchivoTemp;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;


public final class ValidaDatosSolicitud {

    public static final int TABTRAMITE = 1;
    public static final int TABICEP = 2;
    public static final int TABSALDO = 3;
    public static final int TABDIOT = 4;
    public static final int TABBANCO = 5;
    public static final int TABANEXOS = 6;
    public static final int TABDOCUMENTO = 7;
    public static final int TABDATOS = 8;
    public static final String PAGE_RFC = "consultaRFC";
    public static final String SHOW_DIALOGO = "dlg.show();";
    public static final String NO_PRESENTA_SALDO = "NO_PRESENTA_SALDO";
    public static final Date FECHANULL = null;
    private static final String TIPO_DE_SELLO = "1";
    private static final int ANIO_2013 = 2013;
    private static final int ANIO_2014 = 2014;
    private static final int ANIO_2015 = 2015;
    private static final String TOKEN = "|";
    private static final String TOKEN_DOBLE = "||";
    private static final int PRIMER_TRIMESTRE = 13;
    private static final int SEGUNDO_TRIMESTRE = 14;
    private static final int TERCER_TRIMESTRE = 15;
    private static final int CUARTO_TRIMESTRE = 16;
    private static final int PRIMER_CUATRIMESTRE = 17;
    private static final int SEGUNDO_CUATRIMESTRE = 18;
    private static final int TERCER_CUATRIMESTRE = 19;
    private static final int PRIMER_SEMESTRE = 20;
    private static final int SEGUNDO_SEMESTRE = 21;
    private static final int PRIMER_BIMESTRE = 36;
    private static final int SEGUNDO_BIMESTRE = 37;
    private static final int TERCER_BIMESTRE = 38;
    private static final int CUARTO_BIMESTRE = 39;
    private static final int QUINTO_BIMESTRE = 40;
    private static final int SEXTO_BIMESTRE = 41;
    private static final String DOMICILIO_NO_LOCALIZADO = "3|21|22|23|24|25|31|32|33|34|35";
    private static final NumberFormat NF = NumberFormat.getNumberInstance(Locale.US);
    private static final Pattern GLOBAL_PATTERN =
        Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+", Pattern.UNICODE_CASE);
    private static final String NO_CERTIFICADA = "X";
    public static final String PAGE_SOLICITUDES_DEVOLUCION = "solicitudDevolucion";
    public static final String PAGE_CONTRIBUYENTE = "datosContribuyente";
    public static final String PAGE_SOLICITUD = "solicitud";
    public static final String PAGE_SOLICITUD_TEMP = "completarSolicitudTemp";
    public static final String UPDATEDIOT = "tabViewAdicionarSolicitud";
    public static final String UPDATEBAR = "form:statusBar";
    public static final String AINSISTENCIA = "seguir.show();";
    public static final String DIALOGPROTESTA = "confirmProtesta2.show();";
    public static final String CONFIRMETEMPORAL = "confirmTemp.show();";
    public static final String SELECTCLABE = "Seleccione una Cuenta CLABE";
    public static final String SELECTCEDOCTA = "Debe adjuntar el estado de cuenta ";
    public static final String SELECT =
        "Verifique!.., no existe documento relacionado o no ha seleccionado un registro.";
    public static final String DELETEDOC = "El documento se eliminó";
    public static final String SHOWINSISTENCIACLABE = "seguirCLABE.show();";
    public static final String MESSAGEEDOCTA = "dlgAddEdoCtaExp.show();";

    public static final String EXIT = "confirExit.show();";
    public static final String ERROR = "error.show();";
    public static final String SHOWACUSE = "dlgAcuse.show();";
    public static final String CONTRIBUYENTE = "CONTRIBUYENTE";
    public static final String DATOSINCORRECTOS = "No";
    public static final String DIALOG_ERROR = "dlgSolError.show();";

    /**private static final String ESTADO_CUENTA = "EdoCuenta.show();";*/
    public static final String UPDATE_ADICIONAR_SOLICITUD = ":form:tabViewAdicionarSolicitud";
    public static final String UPDATE_MENSAJE_A_INSISTENCIA = "form:mensajeIns";
    public static final String DIALOGO_A_INSISTENCIA = "dlgInst.show();";
    public static final String GUION = "-";
    public static final String INICIO = "-01";
    public static final String MENSAJE_ARCHIVO =
        "Error: La logitud del nombre de archivo sobre pasa lo permitido, verifique";
    public static final String URL_FIRMA = "/faces/resources/pages/firmadoElectronico/firmaRegistro.jsp?rfc=";
    public static final String DELETE = "rm -f ";
    public static final String CARPETATEMP = "temporal_";
    public static final String LOADINGBAR_HIDE = "loadingbar.hide();";
    public static final String FIEL = "/faces/resources/pages/gestionsol/firmaFIEL.jsf";
    public static final String CONSULTAS = "/faces/resources/pages/consulta/consultaDevISRDetalle.jsf";
    public static final String FIRMAR_FIEL = "FIRMAR_FIEL";
    public static final String URL_CONTRIBUYENTE = "./datosContribuyente.jsf";


    public static boolean consultaCertificadasIMMEX(int id, String flag) {
        if ((id == ConstantesDyCNumerico.VALOR_105 || id == ConstantesDyCNumerico.VALOR_106)) {
            return isIMMEXCertificada(flag);
        }
        return Boolean.FALSE;
    }

    private static boolean isIMMEXCertificada(String flag) {
        if (null != flag && !flag.equalsIgnoreCase(NO_CERTIFICADA)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isTramiteIMMEX(int id) {
        if ((id == ConstantesDyCNumerico.VALOR_105 || id == ConstantesDyCNumerico.VALOR_106 ||
             id == ConstantesDyCNumerico.VALOR_107 || id == ConstantesDyCNumerico.VALOR_108)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isTramitePadronCert(int id) {
        if (id == ConstantesDyCNumerico.VALOR_127 || id == ConstantesDyCNumerico.VALOR_128 ||
            id == ConstantesDyCNumerico.VALOR_129) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static int tipoDeclaracion(int id) {
        if (id == ConstantesDyCNumerico.VALOR_1 || id == ConstantesDyCNumerico.VALOR_41) {
            return ConstantesDyCNumerico.VALOR_1;
        }
        return ConstantesDyCNumerico.VALOR_2;
    }

    public static boolean validaRN3ImporteCantidadFavor(int id, String asesor, int origenDev) {
        if (asesor.equals(ConstanteValidacionRNFDC10.ALSC) && origenDev == ConstantesDyC.PAGO_DE_LO_INDEBIDO &&
            validaTramitesRN3(id)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private static boolean validaTramitesRN3(int id) {
        if (id == ConstantesDyCNumerico.VALOR_358 || id == ConstantesDyCNumerico.VALOR_359 ||
            id == ConstantesDyCNumerico.VALOR_360 || id == ConstantesDyCNumerico.VALOR_373) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean datosALTEX(int id) {
        if (id == ConstantesDyCNumerico.VALOR_101 || id == ConstantesDyCNumerico.VALOR_102) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static int presentarDocumentos(boolean dictaminado, int competencia, int tramite) {
        int presentaDoc = ConstantesDyCNumerico.VALOR_0;
        switch (competencia) {
        case ConstantesTipoRol.AGGC:
            presentaDoc = ConstantesDyCNumerico.VALOR_30;
            break;
        case ConstantesTipoRol.AGAFF:
            if (tramite == ConstantesDyCNumerico.VALOR_115) {
                presentaDoc = ConstantesDyCNumerico.VALOR_31;
            } else if (dictaminado) {
                presentaDoc = ConstantesDyCNumerico.VALOR_31;
            }
            break;
        default:
        }
        return presentaDoc;
    }

    public static boolean subtipoTramites(int tramite) {
        if (tramite == ConstantesDyCNumerico.VALOR_114 || tramite == ConstantesDyCNumerico.VALOR_117 ||
            tramite == ConstantesDyCNumerico.VALOR_124) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isSectorAgropecuario(int tramite) {
        if (tramite == ConstantesDyCNumerico.VALOR_130) {
            return Boolean.TRUE;
        }
        return false;
    }

    public static boolean validaPeriodoIEPSDisel(int tramite) {
        Calendar mes = null;
        boolean isTrue = Boolean.TRUE;

        if (tramite == ConstantesDyCNumerico.VALOR_109) {
            mes = Calendar.getInstance(Locale.getDefault());
            int diaActual = mes.DAY_OF_MONTH;
            int ultimoDiaMes = mes.getActualMaximum(Calendar.DAY_OF_MONTH);
            isTrue = String.valueOf(mes.get(Calendar.MONTH) + 1).matches("1|4|7|10");
            if ((isTrue) &&
                (diaActual == ultimoDiaMes && mes.get(Calendar.HOUR_OF_DAY) == ConstantesDyCNumerico.VALOR_18)) {
                isTrue = false;
            }
        }
        return isTrue;
    }

    private ValidaDatosSolicitud() {
    }


    public static boolean anual(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valAnual = false;
        if ((idPeriodo == selectPeriodo) && (periodo < selectPeriodo)) {
            valAnual = Boolean.TRUE;

        } else if (bimestre1(idPeriodo, periodo, selectPeriodo)) {
            valAnual = Boolean.TRUE;
        }
        return valAnual;
    }

    public static boolean trimestral(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valTrimestral = false;
        switch (idPeriodo) {
        case PRIMER_TRIMESTRE:
            valTrimestral = isEneroMarzo(periodo, selectPeriodo);
            break;
        case SEGUNDO_TRIMESTRE:
            valTrimestral = isAbrilJunio(periodo, selectPeriodo);
            break;
        case TERCER_TRIMESTRE:
            valTrimestral = isJulioSeptiembre(periodo, selectPeriodo);
            break;
        case CUARTO_TRIMESTRE:
            valTrimestral = isOctubreDiciembre(periodo, selectPeriodo);
            break;
        default:
        }
        return valTrimestral;
    }

    public static boolean cuatrimestral(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valCuatri = false;
        switch (idPeriodo) {
        case PRIMER_CUATRIMESTRE:
            valCuatri = isEneroAbril(periodo, selectPeriodo);
            break;
        case SEGUNDO_CUATRIMESTRE:
            valCuatri = isMayoAgosto(periodo, selectPeriodo);
            break;
        case TERCER_CUATRIMESTRE:
            valCuatri = isSeptiembreDiciembre(periodo, selectPeriodo);
            break;
        default:
        }
        return valCuatri;
    }

    public static boolean semestral(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valSem = false;
        if (idPeriodo == PRIMER_SEMESTRE) {
            if (periodo < selectPeriodo && periodo < ConstantesDyCNumerico.VALOR_6) {
                valSem = Boolean.TRUE;
            }
        } else if ((idPeriodo == SEGUNDO_SEMESTRE) &&
                   (periodo < selectPeriodo && periodo >= ConstantesDyCNumerico.VALOR_6)) {
            valSem = Boolean.TRUE;
        }
        return valSem;
    }

    public static boolean bimestral(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valBim = false;
        switch (idPeriodo) {
        case PRIMER_BIMESTRE:
            if (menorQ(periodo, selectPeriodo)) {
                valBim = Boolean.TRUE;
            }
            break;
        case SEGUNDO_BIMESTRE:
            valBim = isBimenstre2(periodo, selectPeriodo);
            break;
        case TERCER_BIMESTRE:
            valBim = isBimenstre2(periodo, selectPeriodo);
            break;
        case CUARTO_BIMESTRE:
            valBim = isBimestre3(periodo, selectPeriodo);
            break;
        case QUINTO_BIMESTRE:
            valBim = isBimestre4(periodo, selectPeriodo);
            break;
        case SEXTO_BIMESTRE:
            valBim = isBimestre5(periodo, selectPeriodo);
            break;
        default:
        }
        return valBim;
    }


    private static boolean bimestre1(int idPeriodo, int periodo, int selectPeriodo) {
        boolean bimestre1 = false;
        if (periodo22a23(idPeriodo, periodo, selectPeriodo)) {
            bimestre1 = Boolean.TRUE;
        }
        if (periodo24a25(idPeriodo, periodo, selectPeriodo)) {
            bimestre1 = Boolean.TRUE;
        }
        if (periodo26a27(idPeriodo, periodo, selectPeriodo)) {
            bimestre1 = Boolean.TRUE;
        }
        if (bimeste2(idPeriodo, periodo, selectPeriodo)) {
            bimestre1 = Boolean.TRUE;
        }
        return bimestre1;
    }

    private static boolean bimeste2(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valBim2 = false;
        if (periodo28a29(idPeriodo, periodo, selectPeriodo)) {
            valBim2 = Boolean.TRUE;
        }
        if (periodo30a31(idPeriodo, periodo, selectPeriodo)) {
            valBim2 = Boolean.TRUE;
        }
        if (periodo32a33(idPeriodo, periodo, selectPeriodo)) {
            valBim2 = Boolean.TRUE;
        }
        return valBim2;
    }

    private static boolean isEneroMarzo(int periodo, int selectPeriodo) {
        boolean valEnero = false;
        if (periodo < selectPeriodo && periodo < ConstantesDyCNumerico.VALOR_3) {
            valEnero = Boolean.TRUE;
        }
        return valEnero;
    }

    private static boolean isAbrilJunio(int periodo, int selectPeriodo) {
        boolean valAbril = false;
        if ((periodo < selectPeriodo) && (periodo < ConstantesDyCNumerico.VALOR_4) ||
            (ConstantesDyCNumerico.VALOR_6 > periodo)) {
            valAbril = Boolean.TRUE;
        }
        return valAbril;
    }

    private static boolean isJulioSeptiembre(int periodo, int selectPeriodo) {
        boolean valJulio = false;
        if ((periodo < selectPeriodo) && (periodo < ConstantesDyCNumerico.VALOR_7) ||
            (ConstantesDyCNumerico.VALOR_9 > periodo)) {
            valJulio = Boolean.TRUE;
        }
        return valJulio;
    }

    private static boolean isOctubreDiciembre(int periodo, int selectPeriodo) {
        boolean valOctubre = false;
        if (periodo < selectPeriodo) {
            valOctubre = Boolean.TRUE;
        }
        return valOctubre;
    }

    private static boolean isEneroAbril(int periodo, int selectPeriodo) {
        boolean isEneroAbril = false;
        if (periodo < selectPeriodo && periodo < ConstantesDyCNumerico.VALOR_4) {
            isEneroAbril = Boolean.TRUE;
        }
        return isEneroAbril;
    }

    private static boolean isMayoAgosto(int periodo, int selectPeriodo) {
        boolean valMayo = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_5 || periodo > ConstantesDyCNumerico.VALOR_8)) {
            valMayo = Boolean.TRUE;
        }
        return valMayo;
    }

    private static boolean isSeptiembreDiciembre(int periodo, int selectPeriodo) {
        boolean valSep = false;
        if (periodo < selectPeriodo && periodo < ConstantesDyCNumerico.VALOR_9) {
            valSep = Boolean.TRUE;
        }
        return valSep;
    }

    private static boolean isBimenstre2(int periodo, int selectPeriodo) {
        boolean valBim2 = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_3 || periodo > ConstantesDyCNumerico.VALOR_4)) {
            valBim2 = Boolean.TRUE;
        }
        return valBim2;
    }

    private static boolean isBimestre3(int periodo, int selectPeriodo) {
        boolean valBim3 = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_7 || periodo > ConstantesDyCNumerico.VALOR_8)) {
            valBim3 = Boolean.TRUE;
        }
        return valBim3;
    }

    private static boolean isBimestre4(int periodo, int selectPeriodo) {
        boolean valBim4 = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_3 || periodo > ConstantesDyCNumerico.VALOR_4)) {
            valBim4 = Boolean.TRUE;
        }
        return valBim4;
    }

    private static boolean isBimestre5(int periodo, int selectPeriodo) {
        boolean valBim5 = false;
        if (periodo < selectPeriodo) {
            valBim5 = Boolean.TRUE;
        }
        return valBim5;
    }

    private static boolean periodo22a23(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_22:
            if (periodo < selectPeriodo && periodo < ConstantesDyCNumerico.VALOR_6) {
                valPer = Boolean.TRUE;
            }
            break;
        case ConstantesDyCNumerico.VALOR_23:
            if (periodo < selectPeriodo &&
                (periodo < ConstantesDyCNumerico.VALOR_2 || periodo > ConstantesDyCNumerico.VALOR_7)) {
                valPer = Boolean.TRUE;
            }
            break;
        default:
        }
        return valPer;
    }

    private static boolean periodo24a25(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer2 = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_24:
            valPer2 = isPeriodo24(periodo, selectPeriodo);
            break;
        case ConstantesDyCNumerico.VALOR_25:
            valPer2 = isPeriodo25(periodo, selectPeriodo);
            break;
        default:
        }
        return valPer2;
    }

    private static boolean isPeriodo24(int periodo, int selectPeriodo) {
        boolean valP24 = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_3 || periodo > ConstantesDyCNumerico.VALOR_8)) {
            valP24 = Boolean.TRUE;
        }
        return valP24;
    }

    private static boolean isPeriodo25(int periodo, int selectPeriodo) {
        boolean valP25 = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_4 || periodo > ConstantesDyCNumerico.VALOR_9)) {
            valP25 = Boolean.TRUE;
        }
        return valP25;
    }

    private static boolean periodo26a27(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer3 = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_26:
            if ((periodo < selectPeriodo) &&
                (isPeriodo5o6(periodo, ConstantesDyCNumerico.VALOR_5, ConstantesDyCNumerico.VALOR_10))) {
                valPer3 = Boolean.TRUE;
            }
            break;
        case ConstantesDyCNumerico.VALOR_27:
            if ((periodo < selectPeriodo) &&
                (isPeriodo5o6(periodo, ConstantesDyCNumerico.VALOR_6, ConstantesDyCNumerico.VALOR_11))) {
                valPer3 = Boolean.TRUE;
            }
            break;
        default:
        }
        return valPer3;
    }

    private static boolean isPeriodo5o6(int periodo, int var1, int var2) {
        boolean val = false;
        if (periodo < var1 || periodo > var2) {
            val = Boolean.TRUE;
        }
        return val;
    }

    private static boolean periodo28a29(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer4 = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_28:
            valPer4 = isPeriodo28(periodo, selectPeriodo);
            break;
        case ConstantesDyCNumerico.VALOR_29:
            valPer4 = isPeriodo29(periodo, selectPeriodo);
            break;
        default:
        }
        return valPer4;
    }

    private static boolean isPeriodo28(int periodo, int selectPeriodo) {
        boolean val = false;
        if ((periodo < selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_7 || periodo > ConstantesDyCNumerico.VALOR_12)) {
            val = Boolean.TRUE;
        }
        return val;
    }

    private static boolean isPeriodo29(int periodo, int selectPeriodo) {
        boolean valP29 = false;
        if ((periodo > selectPeriodo) &&
            (periodo < ConstantesDyCNumerico.VALOR_8 || periodo < ConstantesDyCNumerico.VALOR_1)) {
            valP29 = Boolean.TRUE;
        }
        return valP29;
    }

    private static boolean periodo30a31(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer5 = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_30:
            if ((periodo > selectPeriodo) &&
                (isPeriodo32o33(periodo, ConstantesDyCNumerico.VALOR_9, ConstantesDyCNumerico.VALOR_2))) {
                valPer5 = Boolean.TRUE;
            }
            break;
        case ConstantesDyCNumerico.VALOR_31:
            if ((periodo > selectPeriodo) &&
                (isPeriodo32o33(periodo, ConstantesDyCNumerico.VALOR_10, ConstantesDyCNumerico.VALOR_3))) {
                valPer5 = Boolean.TRUE;
            }
            break;
        default:
        }
        return valPer5;

    }

    private static boolean periodo32a33(int idPeriodo, int periodo, int selectPeriodo) {
        boolean valPer6 = false;
        switch (idPeriodo) {
        case ConstantesDyCNumerico.VALOR_32:
            if (periodo > selectPeriodo &&
                isPeriodo32o33(periodo, ConstantesDyCNumerico.VALOR_11, ConstantesDyCNumerico.VALOR_4)) {
                valPer6 = Boolean.TRUE;
            }
            break;
        case ConstantesDyCNumerico.VALOR_33:
            if (periodo > selectPeriodo &&
                isPeriodo32o33(periodo, ConstantesDyCNumerico.VALOR_12, ConstantesDyCNumerico.VALOR_5)) {
                valPer6 = Boolean.TRUE;
            }
            break;
        default:
        }
        return valPer6;
    }

    private static boolean isPeriodo32o33(int periodo, int var1, int var2) {
        boolean val = false;
        if (periodo < var1 || periodo < var2) {
            val = Boolean.TRUE;
        }
        return val;
    }

    private static boolean menorQ(int a, int b) {
        boolean res = false;
        if (a < b) {
            res = Boolean.TRUE;
        }
        return res;
    }

    public static String generaCadenaOriginal(TramiteDTO tramite) {
        String nombre = null;
        if (null != tramite.getPersona().getPersonaIdentificacion().getRazonSocial()) {
            nombre = tramite.getPersona().getPersonaIdentificacion().getRazonSocial();
        } else {
            nombre =
                    tramite.getPersona().getPersonaIdentificacion().getNombre() + tramite.getPersona().getPersonaIdentificacion().getApPaterno() +
                    tramite.getPersona().getPersonaIdentificacion().getApMaterno();
        }
        StringBuilder cadenaOriginalSB = new StringBuilder(TOKEN_DOBLE);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getPersona().getRfcVigente()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(nombre));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getPersona().getDomicilio().getClaveAdmonLocal()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getOrigenSaldo().getDescripcion()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getTipoTramite().getDescripcion()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getImpuesto().getDescripcion()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getConcepto().getIdConcepto()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getEjercicio().getIdNum()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getPeriodo().getDescripcion()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(tramite.getSaldoFavor().getImporteSolicitadoDevolucion().intValue()));
        cadenaOriginalSB.append(TOKEN);
        cadenaOriginalSB.append(Utilerias.isNull(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        cadenaOriginalSB.append(TOKEN_DOBLE);
        return cadenaOriginalSB.toString();
    }

    public static boolean isTipoACDC(Integer depedenciaAsesorFiscal, boolean granContrib) {
        boolean valACDC = false;
        if (depedenciaAsesorFiscal.equals(ConstantesIds.ACDC) && !granContrib) {
            valACDC = Boolean.TRUE;
        }
        return valACDC;
    }

    public static boolean isTipoAGGC(int depedenciaAsesorFiscal, boolean granContrib) {
        boolean valAGGC = false;
        if ((depedenciaAsesorFiscal == ConstantesIds.ACFECF || depedenciaAsesorFiscal == ConstantesIds.ACFSF ||
             depedenciaAsesorFiscal == ConstantesIds.ACFGCD) && granContrib) {
            valAGGC = Boolean.TRUE;
        }
        return valAGGC;
    }

    public static boolean isTipoALSC(boolean alsc, boolean tipoPersona, boolean granContrib) {
        boolean valALSC = false;
        if (alsc && tipoPersona && !granContrib) {
            valALSC = Boolean.TRUE;
        }
        return valALSC;
    }

    public static boolean isTipoALAF(boolean alaf, boolean granContrib) {
        boolean valALAF = false;
        if (alaf && !granContrib) {
            valALAF = Boolean.TRUE;
        }
        return valALAF;
    }

    public static boolean isTipoAGACE(boolean agace, boolean granContrib) {
        boolean valAgace = false;
        if (agace && !granContrib) {
            valAgace = Boolean.TRUE;
        }
        return valAgace;
    }

    public static int isRolPersona(boolean rol) {
        if (rol) {
            return 1;
        }
        return 0;
    }

    public static Map<String, Object> generaCadenaySello(TramiteDTO tramite) throws IOException {
        Map<String, Object> datos = null;
        String cadOriginal = null;
        Selladora selladora = new Selladora();
        if (null != tramite) {
            datos = new HashMap<String, Object>();
            cadOriginal = Utilerias.isNull(generaCadenaOriginal(tramite));
            datos.put("cadena", cadOriginal);
            datos.put("sello", Utilerias.isNull(selladora.retornarParametros(TIPO_DE_SELLO, cadOriginal)));
        }
        return datos;
    }

    public static boolean isPeriodo2014(int ejercicio) {
        boolean val2014 = false;
        if (ejercicio >= ANIO_2014) {
            val2014 = Boolean.TRUE;
        }
        return val2014;
    }

    public static boolean isPeriodoAnterior2015(int tramite, int ejercicio) {
        boolean val2015 = false;
        if ((tramite == ConstantesIds.IVA_MEDICINA || tramite == ConstantesIds.IVA_PRODUCTOS_ALIMENTACION ||
             tramite == ConstantesIds.IVA_EXPORTACION) && (ejercicio < ANIO_2015)) {
            val2015 = Boolean.TRUE;
        }
        return val2015;
    }


    public static boolean isImporteNuevasInversiones(int importe, int ejercicio) {
        boolean valImporte = false;
        /** if (importe < ConstantesDyC.IMPORTE_NUEVAS_INVERSIONES || !isPeriodo2014(ejercicio)) { */
        if (importe == -1 || ejercicio <= ANIO_2013) {
            valImporte = Boolean.TRUE;
        }
        return valImporte;
    }

    public static DyctSaldoIcepDTO getSaldoIcep(int eje, int tipoP, int origen) {
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
        ejercicio.setIdEjercicio(eje);
        periodo.setIdPeriodo(tipoP);
        saldoIcep.setDyccPeriodoDTO(periodo);
        saldoIcep.setDyccEjercicioDTO(ejercicio);

        saldoIcep.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo(origen));
        return saldoIcep;
    }

    public static DyccActividadDTO getActividad(int actividad, int subOrig) {
        DyccSubOrigSaldoDTO subOrigSaldo = new DyccSubOrigSaldoDTO();
        DyccActividadDTO dyccActividadDTO = new DyccActividadDTO();

        subOrigSaldo.setIdSuborigenSaldo(subOrig != 0 ? subOrig : null);
        dyccActividadDTO.setIdActividad(actividad != 0 ? actividad : null);
        dyccActividadDTO.setDyccSubOrigSaldoDTO(subOrigSaldo);
        return dyccActividadDTO;
    }

    public static DyccConceptoDTO getConcepto(int idImpuesto, int idConcepto) {
        DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
        DyccConceptoDTO conceptoD = new DyccConceptoDTO();
        impuesto.setIdImpuesto(idImpuesto);
        conceptoD.setIdConcepto(idConcepto);
        conceptoD.setDyccImpuestoDTO(impuesto);
        return conceptoD;
    }

    public static DyccEstadoSolDTO getEdoSolicitud(int idEdoSol) {
        DyccEstadoSolDTO edoSolicitud = new DyccEstadoSolDTO();
        edoSolicitud.setIdEstadoSolicitud(idEdoSol != ConstantesDyC1.CERO ? ConstantesDyCNumerico.VALOR_14 :
                                          ConstantesDyCNumerico.VALOR_2);
        return edoSolicitud;
    }

    public static Long obtieneNumOperacion(Integer numDoc, String numOpera) {
        if (null != numDoc) {
            return Long.valueOf(numDoc);
        } else if (null != numOpera) {
            return Long.valueOf(numOpera);
        }
        return 0L;
    }

    public static Double getSaldo(Double saldo) {
        if (null != saldo && saldo > 0) {
            return saldo;
        }
        return new Double(0.0);
    }

    public static int isCatalogo(CatalogoTO object) {
        return null != object ? object.getIdNum() : 0;
    }

    public static boolean validaTipoDec(Object fechaPres, Object fechaCau) {
        boolean valTipoD = false;
        if (null != fechaCau || null == fechaPres) {
            valTipoD = Boolean.TRUE;
        }
        return valTipoD;
    }

    public static int esAsesor(Integer depedenciaAsesorFiscal) {
        if (depedenciaAsesorFiscal.equals(ConstantesIds.ACFECF)) {
            return ConstantesDyCNumerico.VALOR_1;
        }
        if (depedenciaAsesorFiscal.equals(ConstantesIds.ACFSF)) {
            return ConstantesDyCNumerico.VALOR_1;
        }
        if (depedenciaAsesorFiscal.equals(ConstantesIds.ACFGCD)) {
            return ConstantesDyCNumerico.VALOR_1;
        }
        if (depedenciaAsesorFiscal.equals(ConstantesIds.ACDC)) {
            return ConstantesDyCNumerico.VALOR_1;
        }
        return ConstantesDyCNumerico.VALOR_0;
    }

    public static boolean validaFechaBajaRol(Date fechaBaja) {
        boolean valFechaB = Boolean.TRUE;
        if ((null != fechaBaja) && (fechaBaja.getTime() < (new Date().getTime()))) {
            valFechaB = Boolean.FALSE;
        }
        return valFechaB;
    }

    public static void deleteDocumentos(String url) throws IOException {
        if (null != url) {
            Runtime.getRuntime().exec(DELETE + url);
        }
    }

    public static String formatoMoneda(BigDecimal cantidad) {
        String format = "";
        if (cantidad != null) {
            DecimalFormat df = (DecimalFormat)NF;
            df.applyPattern("$ ###,##0.00");
            format = df.format(cantidad);
        }
        return format;
    }

    public static boolean isImportePeriodo(int i, int ejercicio) {
        if (i == 1 || !isPeriodo2014(ejercicio)) {
            return false;
        }
        return Boolean.TRUE;
    }

    public static Object recuperaPersona(InputStream datosPersona) throws SIATException {
        JAXBContext jaxbContext = null;
        Unmarshaller jaxbUnmarshaller = null;
        Object object = null;
        try {
            jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            object = jaxbUnmarshaller.unmarshal(datosPersona);
        } catch (JAXBException e) {
            throw new SIATException(e);
        }
        return object;
    }

    public static RolesContribuyenteDTO recuperaRolesContribuyente(DyctContribTempDTO dyctContribuyenteDTO) throws SIATException {
        DyctContribTempDTO datosContTemp = null;
        RolesContribuyenteDTO recuperaRolesTemp = null;
        if (null != dyctContribuyenteDTO) {
            datosContTemp = dyctContribuyenteDTO;
            recuperaRolesTemp = new RolesContribuyenteDTO();
            recuperaRolesTemp.setAmparado(!datosContTemp.getAmparado().equals(ConstantesDyC1.CERO) ? Boolean.TRUE : false);
            recuperaRolesTemp.setDictaminado(datosContTemp.getRolDictaminado());
            recuperaRolesTemp.setPersonaFisica(datosContTemp.getRolPf());
            recuperaRolesTemp.setPersonaMoral(datosContTemp.getRolPm());
            recuperaRolesTemp.setGranContribuyente(datosContTemp.getRolGranContrib());
            recuperaRolesTemp.setSociedadControladora(datosContTemp.getRolSociedadControl());
            recuperaRolesTemp.setEsContribuyente(CONTRIBUYENTE);
        }
        return recuperaRolesTemp;
    }

    public static CatalogoTO recuperaObjeto(Integer object) {
        if (null != object && object != 0) {
            return new CatalogoTO(object);
        }
        return null;
    }

    public static boolean isPeriodoValido(int valor, int idPeriodo, int selectPeriodo) {
        boolean res = false;
        int periodo = ConstantesDyC1.CERO;
        Date mes = new Date();
        DateFormat dateFormat = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT_MONTH);
        periodo = Integer.parseInt(dateFormat.format(mes));
        switch (valor) {
        case ConstanteArchivoTemp.PERIODO_ANUAL:
            res = anual(idPeriodo, periodo, selectPeriodo);
            break;
        case ConstanteArchivoTemp.PERIODO_CUATRIMESTRAL:
            res = cuatrimestral(idPeriodo, periodo, selectPeriodo);
            break;
        case ConstanteArchivoTemp.PERIODO_TRIMESTRAL:
            res = trimestral(idPeriodo, periodo, selectPeriodo);
            break;
        case ConstanteArchivoTemp.PERIODO_BIMESTRAL:
            res = bimestral(idPeriodo, periodo, selectPeriodo);
            break;
        case ConstanteArchivoTemp.PERIODO_SEMESTRAL:
            res = semestral(idPeriodo, periodo, selectPeriodo);
            break;
        default:
            break;
        }
        return res;
    }

    public static boolean isPeriodoBimestral(String periodo, int tramite) {
        boolean valPBim = false;
        if (periodo.equals("B") && tramite == ConstantesIds.IVA_CONVENCIONAL) {
            valPBim = Boolean.TRUE;
        }
        return valPBim;
    }

    public static String validaCaracteres(String str) {
        return GLOBAL_PATTERN.matcher(Normalizer.normalize(str, Normalizer.Form.NFD)).replaceAll("");
    }

    public static boolean isDomNoLocalizado(String regex) {
        String val = "0";
        if (null != regex) {
            val = regex;
        }
        return val.trim().matches(DOMICILIO_NO_LOCALIZADO);
    }


    public static boolean validaTabDIOT(Integer... s) {
        boolean isTrue = false;
        if ("5".equals(s[0].toString()) && "4".equals(s[1].toString())) {
            isTrue = Boolean.TRUE;
        }
        return isTrue;
    }

    public static boolean validaTabAnexos(Integer... s) {
        boolean isTrue = false;
        if ("7".equals(s[0].toString()) && "6".equals(s[1].toString())) {
            isTrue = Boolean.TRUE;
        }
        return isTrue;
    }


    public static boolean isCompetenciaAGGC(int competencia, boolean presentaDoc) {
        boolean isAGCC = false;
        if (competencia == ConstantesTipoRol.AGGC && presentaDoc) {
            isAGCC = Boolean.TRUE;
        }
        return isAGCC;
    }

    public static boolean isCompetenciaAGAFF(int competencia, boolean presentaDoc, boolean isDictaminado) {
        boolean isAGAF = false;
        if ((competencia == ConstantesTipoRol.AGAFF) && presentaDoc && isDictaminado) {
            isAGAF = Boolean.TRUE;
        }
        return isAGAF;
    }

    public static boolean isPeriodoSemestral(int idTipoPeriodo) {
        boolean isSemestral = false;
        if (idTipoPeriodo == ConstantesIds.IdsTipoPeriodo.SEMESTRAL_A ||
            idTipoPeriodo == ConstantesIds.IdsTipoPeriodo.SEMESTRAL_B) {
            isSemestral = Boolean.TRUE;
        }
        return isSemestral;

    }
    
    public static boolean isPeriodoMensual(int idTipoPeriodo) {
       return idTipoPeriodo == ConstantesIds.IdsTipoPeriodo.MENSUAL ? Boolean.TRUE:false;
        
    }

    public static String cadenaMovil(String cadena) {
        StringBuilder builder = new StringBuilder();
        if (cadena.contains(ConstantesDyC3.TELEFONO_MOVIL_ISO) || cadena.contains(ConstantesDyC3.TELEFONO_MOVIL_UTF)) {
            StringTokenizer token = new StringTokenizer(cadena, ConstantesDyC3.SEPERADOR_TELEFONO);
            while (token.hasMoreElements()) {
                builder.append(token.nextToken()).append(ConstantesDyC3.SEPERADOR_TELEFONO).append(ConstantesDyC3.TELEFONO_MOVIL_UTF);
                break;
            }

        } else {
            return cadena;
        }
        return builder.toString();
    }
}
