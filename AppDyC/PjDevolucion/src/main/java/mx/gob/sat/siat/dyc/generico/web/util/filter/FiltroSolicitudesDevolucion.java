package mx.gob.sat.siat.dyc.generico.web.util.filter;

import java.io.IOException;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 *
 * @author christian.ventura
 */
@Component
public class FiltroSolicitudesDevolucion implements Filter {

    private static final Logger LOGGER = Logger.getLogger(FiltroSolicitudesDevolucion.class);

    @Autowired
    @Qualifier(value = "registraSolDevService")
    private RegistraSolDevService registraSolDevService;

    /**
     *
     * @param fc
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**
     *
     * @param sr
     * @param sr1
     * @param fc
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)sr;
        HttpServletResponse response = (HttpServletResponse)sr1;
        String path = request.getContextPath();
        LOGGER.info("path:" + path);
        LOGGER.info("getRequestURL:" + request.getRequestURL());
        if (request.getRequestURL().indexOf("solicitudesDevolucion.jsf") > -1) {
            if (registraSolDevService != null) {
                try {
                    AccesoUsr usr = (AccesoUsr)request.getSession().getAttribute("acceso");
                    if (usr != null && usr.getUsuario() != null) {
                       LOGGER.info("usuario:" + usr.getUsuario());
                         String tramites=request.getParameter("tramites");
                      if (tramites!=null && tramites.equals("1") && !registraSolDevService.consultaSiRfcAmparado(usr.getUsuario())) {
                            LOGGER.info("filtro solicitud de devolucion");
                                response.sendRedirect(path + "/faces/resources/pages/registro/pruebas.jsf?pantalla=Solicitud de devolución");             
                        } else {
                            List<SolicitudDevolucionRegistroVO> devoluciones =
                                registraSolDevService.solicitudesPendientes(usr.getUsuario()).getOutputs();
                            if (devoluciones.isEmpty()) {
                                LOGGER.info("devoluciones isEmpty()");
                                LOGGER.info("redirect");
                                response.sendRedirect(path + "/faces/resources/pages/registro/datosContribuyente.jsf");
                            } else {
                                LOGGER.info("devoluciones notEmpty()");
                                fc.doFilter(sr, sr1);
                            }
                        }
                    } else {
                        LOGGER.info("usuario is null");
                        fc.doFilter(sr, sr1);
                    }
                } catch (SIATException ex) {
                    LOGGER.error(ex);
                    fc.doFilter(sr, sr1);
                }
            } else {
                LOGGER.info("registraSolDevService is null");
                fc.doFilter(sr, sr1);
            }
        } else {
            LOGGER.info("is not solicitudesDevolucion.jsf");
            fc.doFilter(sr, sr1);
        }
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }

}
