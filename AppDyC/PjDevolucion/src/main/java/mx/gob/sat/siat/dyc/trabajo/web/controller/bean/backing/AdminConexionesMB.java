package mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;


@ManagedBean(name = "mbAdminConexiones")
@SessionScoped
public class AdminConexionesMB {
    private static final Logger LOG = Logger.getLogger(AdminConexionesMB.class.getName());

    private JdbcTemplate jdbcTemplateDYC;
    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate jdbcTemplatePA;

    private String indicadorConexion;

    private String versionBD;
    private String driver;
    private String url;
    private String userName;
    private String serviceName;

    @PostConstruct
    public void iniciar() {
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        org.springframework.web.context.WebApplicationContext context =
            WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        LOG.debug("context ->" + context);

        jdbcTemplateDYC = (JdbcTemplate)context.getBean("jdbcTemplateDYC");
        jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
        jdbcTemplatePA = (JdbcTemplate)context.getBean("jdbcTemplatePA");

        DataSource ds = jdbcTemplateDYC.getDataSource();
        actualizarInfoConexionActual(ds);
    }

    public void imprimirInfoTodasConexiones() {
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        org.springframework.web.context.WebApplicationContext context =
            WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        Map mapJdbc = context.getBeansOfType(JdbcTemplate.class);

        Iterator entries = mapJdbc.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry)entries.next();
            Object key = thisEntry.getKey();
            Object value = thisEntry.getValue();
            LOG.debug("-----------------------------------key ->" + key + "<- value ->" + value +
                      "-----------------------------------------------");
            jdbcTemplateDYC = (JdbcTemplate)value;
            DataSource ds = jdbcTemplateDYC.getDataSource();
            actualizarInfoConexionActual(ds);
            LOG.debug("ds ->" + ds);
            Connection c = null;
            try {
                c = ds.getConnection();
                DatabaseMetaData dbmt = c.getMetaData();
                LOG.debug("CatalogSeparator ->" + dbmt.getCatalogSeparator() + "<-");
                LOG.debug("CatalogTerm ->" + dbmt.getCatalogTerm() + "<-");
                LOG.debug("DatabaseProductName ->" + dbmt.getDatabaseProductName() + "<-");
                LOG.debug("DatabaseProductVersion ->" + dbmt.getDatabaseProductVersion() + "<-");
                LOG.debug("DriverName ->" + dbmt.getDriverName() + "<-");
                LOG.debug("DriverVersion() ->" + dbmt.getDriverVersion() + "<-");
                LOG.debug("SchemaTerm ->" + dbmt.getSchemaTerm() + "<-");
                LOG.debug("URL ->" + dbmt.getURL() + "<-");
                LOG.debug("UserName ->" + dbmt.getUserName() + "<-");
                LOG.debug("connection ->" + c);
                
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
            finally{
                if(c != null)
                {
                    try{
                        c.close();
                    }
                    catch (SQLException e){
                        LOG.error("Error al cerrar conexión ->" + e.getMessage());
                    }
               }
            }
        }
    }

    public void conectarStdPru() {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("jdbc/dycIntegracion");
            LOG.debug("ds jndi------>" + ds);
            jdbcTemplateDYC.setDataSource(ds);
            jdbcTemplate.setDataSource(ds);
            jdbcTemplatePA.setDataSource(ds);

            actualizarInfoConexionActual(ds);
        } catch (Exception e) {
            LOG.error("ex ->" + e.getMessage());
        }

        LOG.debug("fin conectar PRU");
    }

    public void conectarStdFis() {
        LOG.debug("INICIO ConectarSTD_FIS");
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("jdbc/DyC_app");
            jdbcTemplateDYC.setDataSource(ds);
            jdbcTemplate.setDataSource(ds);
            jdbcTemplatePA.setDataSource(ds);
            actualizarInfoConexionActual(ds);
        } catch (Exception e) {
            LOG.error("ex ->" + e.getMessage());
        }
    }

    public void conectarStuFis() {
        LOG.debug("INICIO ConectarSTU_FIS");
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("jdbc/dyc_uat");
            jdbcTemplateDYC.setDataSource(ds);
            jdbcTemplate.setDataSource(ds);
            jdbcTemplatePA.setDataSource(ds);
            actualizarInfoConexionActual(ds);
        } catch (Exception e) {
            LOG.error("ex ->" + e.getMessage());
        }
    }

    public void actualizarInfoConexionActual(DataSource ds)
    {
        Connection c = null;
        try {
            c = ds.getConnection();
            DatabaseMetaData dbmt = c.getMetaData();

            versionBD = dbmt.getDatabaseProductName();
            driver = dbmt.getDriverName() + " " + dbmt.getDriverVersion();
            url = dbmt.getURL();
            actualizarServiceName(url);
            userName = dbmt.getUserName();
        } catch (SQLException e) {
            LOG.error("ocurrio un error al obtener la conexion a la base de datos ->" + e.getMessage());
        }
        finally{
            if(c != null)
            {
                try{
                    c.close();
                }
                catch (SQLException e){
                    LOG.error("Error al cerrar conexión ->" + e.getMessage());
                }
           }
        }

    }

    public void actualizarServiceName(String url) {
        try {
            setServiceName(url.split("/")[1]);
        } catch (Exception e) {
            LOG.info("error al obtener service name ->" + e.getMessage());
            setServiceName("");
        }
    }

    public String getIndicadorConexion() {
        return indicadorConexion;
    }

    public void setIndicadorConexion(String indicadorConexion) {
        this.indicadorConexion = indicadorConexion;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public String getVersionBD() {
        return versionBD;
    }

    public void setVersionBD(String versionBD) {
        this.versionBD = versionBD;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
