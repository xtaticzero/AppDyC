/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.servicios;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

/**
 * Clase que implementa los servicios basicos para la generacion de documentos.
 * @author Agustin Romero Mata / Softtek
 */
public class DocumentoBaseServicios extends BaseServicios {    
    /**
     * Constructor parametrizado.
     * @param docTemplate
     * @param docDestino
     */        
    public DocumentoBaseServicios(String docTemplate, String docDestino) {        
        super(docTemplate, docDestino);
    }
    
    /**
     * Funcion de obtencion de objetos de docx
     * @param obj
     * @param toSearch
     * @return 
     */
    protected List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();        
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }
        
        if (obj.getClass().equals(toSearch)) {
            result.add(obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }     
        }
        return result;
    }

    /**
     * Funcion para busqueda y remplazo de tockens
     * 
     * @param template
     * @param name
     * @param placeholder
     */
    protected void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);        
        
        for (Object text : texts) {
            Text textElement = (Text) text;
                if (textElement.getValue().equals(placeholder)) {
                    textElement.setValue(name);
                }
        }
    }
    
    /**
     * Funcion para busqueda y remplazo de tockens
     * 
     * @param template
     * @param name
     * @param placeholder
     */
    protected void replacePlaceholderHeader(WordprocessingMLPackage template, String name, String placeholder ) {
        RelationshipsPart rp = template.getMainDocumentPart().getRelationshipsPart();
        Relationship rel = rp.getRelationshipByType(Namespaces.HEADER);
        Part p = rp.getPart(rel);
        List<Object> texts = getAllElementFromObject(p, Text.class);
     
        for (Object text : texts) {
            Text textElement = (Text) text;
                if (textElement.getValue().equals(placeholder)) {
                    textElement.setValue(name);
                }
        }
    }
    
    /**
     * Funcion para busqueda y remplazo de tockens
     * 
     * @param template
     * @param name
     * @param placeholder
     */
    protected void replacePlaceholderFooter(WordprocessingMLPackage template, String name, String placeholder ) {
        RelationshipsPart rp = template.getMainDocumentPart().getRelationshipsPart();
        Relationship rel = rp.getRelationshipByType(Namespaces.FOOTER);
        Part p = rp.getPart(rel);
        List<Object> texts = getAllElementFromObject(p, Text.class);
     
        for (Object text : texts) {
            Text textElement = (Text) text;
                if (textElement.getValue().equals(placeholder)) {
                    textElement.setValue(name);
                }
        }
    }
    
    /**
     * Funcion de busqueda y replazo de etiquetas en tablas.
     *
     * @param placeholders
     * @param textToAdd
     * @param template
     * @throws Docx4JException
     * @throws JAXBException
     */
    protected void replaceTable(String placeholders, List<Map<String, String>> textToAdd,
                    WordprocessingMLPackage template) throws Docx4JException, JAXBException {
        List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

        // 1. find the table
        Tbl tempTable = getTemplateTable(tables, placeholders);
        
        if (tempTable!=null) {
            List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
    
            // first row is header, second row is content
            if (rows.size() == 2) {
                    // this is our template row
                    Tr templateRow = (Tr) rows.get(1);
    
                    for (Map<String, String> replacements : textToAdd) {
                            // 2 and 3 are done in this method
                            addRowToTable(tempTable, templateRow, replacements);
                    }
    
                    // 4. remove the template row
                    tempTable.getContent().remove(templateRow);
            }
        }
    }
    
    /**
     * Funcion de busqueda de tabla especifica.
     *
     * @param tables
     * @param templateKey
     * @return
     * @throws Docx4JException
     * @throws JAXBException
     */
    protected Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
            for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
                    Object tbl = iterator.next();
                    List<?> textElements = getAllElementFromObject(tbl, Text.class);
                    for (Object text : textElements) {
                            Text textElement = (Text) text;
                            if (textElement.getValue() != null && textElement.getValue().equals(templateKey)) {
                                    return (Tbl) tbl;
                            }
                    }
            }
            return null;
    }
    
    /**
     * Funcion de adicion de un renglon en una tabla.
     *
     * @param reviewtable
     * @param templateRow
     * @param replacements
     */
    protected void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
            Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
            List<?> textElements = getAllElementFromObject(workingRow, Text.class);
            for (Object object : textElements) {
                    Text text = (Text) object;
                    String replacementValue = (String) replacements.get(text.getValue());
                    if (replacementValue != null) {
                            text.setValue(replacementValue);
                    }
            }
    
            reviewtable.getContent().add(workingRow);
    }
    
    /**
     * Funcion para la escritura en documento de un docx.
     *
     * @param template
     * @param target
     * @throws IOException
     * @throws Docx4JException
     */
    protected void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }
    
}
