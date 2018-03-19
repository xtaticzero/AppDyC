package mx.gob.sat.siat.dyc.template.dto;

public class URLCompleteTemplateDTO {
    public URLCompleteTemplateDTO() {
        super();
    }

    private String urlOfConfiguration;
    private String urlOfDestinity;
    private String urlName;


    public URLCompleteTemplateDTO(String urlOfConfiguration, String urlOfDestinity,String urlName) {


        this.urlOfConfiguration = urlOfConfiguration;
        this.urlOfDestinity = urlOfDestinity;
        this.urlName=urlName;

    }


    public void setUrlOfConfiguration(String urlOfConfiguration) {
        this.urlOfConfiguration = urlOfConfiguration;
    }

    public String getUrlOfConfiguration() {
        return urlOfConfiguration;
    }

    public void setUrlOfDestinity(String urlOfDestinity) {
        this.urlOfDestinity = urlOfDestinity;
    }

    public String getUrlOfDestinity() {
        return urlOfDestinity;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlName() {
        return urlName;
    }
}
