package mx.gob.sat.siat.dyc.template.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;


/**
 * @author Yolanda Martínez Sánchez
 * @since 12/03/2014
 *
 */
@XmlType(propOrder = { "URLCONFIGURADOR" })

public class URLTemplateDTO implements Serializable {
    public URLTemplateDTO() {
        super();
    }

    @SuppressWarnings("compatibility:-442522290153171877777")
    private static final long serialVersionUID = 9L;


    /*This is the access to  template  Catalog */

    private String urlOfConfiguration;
    private String urlOfTemplate;
    private String nameOfTemplate;


    public URLTemplateDTO(String urlOfConfiguration, String urlOfTemplate, String nameOfTemplate) {


        this.urlOfConfiguration = urlOfConfiguration;
        this.urlOfTemplate = urlOfTemplate;
        this.nameOfTemplate = nameOfTemplate;
    }


    public void setUrlOfConfiguration(String urlOfConfiguration) {
        this.urlOfConfiguration = urlOfConfiguration;
    }

    public String getUrlOfConfiguration() {
        return urlOfConfiguration;
    }

    public void setUrlOfTemplate(String urlOfTemplate) {
        this.urlOfTemplate = urlOfTemplate;
    }

    public String getUrlOfTemplate() {
        return urlOfTemplate;
    }

    public void setNameOfTemplate(String nameOfTemplate) {
        this.nameOfTemplate = nameOfTemplate;
    }

    public String getNameOfTemplate() {
        return nameOfTemplate;
    }
}

