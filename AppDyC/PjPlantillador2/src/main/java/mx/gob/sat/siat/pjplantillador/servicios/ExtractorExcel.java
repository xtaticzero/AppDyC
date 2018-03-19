/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.servicios;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.JaxbXmlPart;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.SheetData;
import org.xlsx4j.sml.Worksheet;
import mx.gob.sat.siat.pjplantillador.dto.ItemInfoExcel;
import mx.gob.sat.siat.pjplantillador.dto.InfoExcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author socrates
 */
public class ExtractorExcel {
    private static final Logger LOG = Logger.getLogger(ExtractorExcel.class);
    private List<ItemInfoExcel> items = null;
    private List<WorksheetPart> worksheets = new ArrayList<WorksheetPart>();
    private SharedStrings sharedStrings = null;
    private String nombreArchivo;
    private String nombreConfiguracion;        
    /**
     * This HashMap is intended to prevent loops.
     */
    public HashMap<Part, Part> handled = new HashMap<Part, Part>();

    /**
     * 
     * @param nombreConfiguracion
     * @param nombreArchivo 
     */
    public ExtractorExcel(String nombreConfiguracion, String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.nombreConfiguracion = nombreConfiguracion;
    }
    
    /**
     * 
     * @param config
     * @param nombreArchivo 
     */
    public ExtractorExcel(List<ItemInfoExcel> config, String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        items = new ArrayList<ItemInfoExcel>(config);
    }
    
    /**
     * 
     */
    public void cierreRecursos() {   
        if (items != null) {
            if (items.size() > 0) {
                items.clear();
            }
            items = null;
        }
        if (worksheets != null) {
            if (worksheets.size() > 0) {
                worksheets.clear();
            }
            worksheets = null;
        }
        if (sharedStrings != null) {
            sharedStrings = null;
        }
        if (handled != null) {
            if (handled.size() > 0) {
                handled.clear();
            }
            handled = null;
        }
    };
    
    
    /**
     * 
     * @param p
     * @param sb
     * @param indent 
     */
    public void  printInfo(Part p, StringBuilder sb, String indent) {
        sb.append("\n" + indent + "Part " + p.getPartName() + " [" + 
                p.getClass().getName() + "] " );		
        if (p instanceof JaxbXmlPart) {
            Object o = ((JaxbXmlPart)p).getJaxbElement();
            if (o instanceof javax.xml.bind.JAXBElement) {
                sb.append(" containing JaxbElement:" + 
                        XmlUtils.JAXBElementDebug((JAXBElement)o) );
            } else {
                sb.append(" containing JaxbElement:"  + 
                        o.getClass().getName() );
            }
        }
        if (p instanceof WorksheetPart) {
            worksheets.add((WorksheetPart)p);
        } else if (p instanceof SharedStrings) {
            sharedStrings = (SharedStrings)p;
        }

    }    

    /**
     * 
     * @param wordMLPackage
     * @param rp
     * @param sb
     * @param indent 
     */
    public void traverseRelationships(org.docx4j.openpackaging.
            packages.OpcPackage wordMLPackage, 
                RelationshipsPart rp, 
                StringBuilder sb, String indent) {

        // TODO: order by rel id
        for ( Relationship r : rp.getRelationships().getRelationship() ) {

            //LOG.info("For Relationship Id=" + r.getId() 
            //    + " Source is " + rp.getSourceP().getPartName() 
            //    + ", Target is " + r.getTarget() );

            if (r.getTargetMode() != null
                && r.getTargetMode().equals("External") ) {

                sb.append("\n" + indent + "external resource " + r.getTarget() 
                                   + " of type " + r.getType() );
                continue;				
            }
            
            Part part = rp.getPart(r);

            printInfo(part, sb, indent);
            if (handled.get(part)!=null) {
                sb.append(" [additional reference] ");
                continue;
            }
            handled.put(part, part);
            if (part.getRelationshipsPart()==null) {
                // sb.append(".. no rels" );						
            } else {
                traverseRelationships(wordMLPackage, 
                        part.getRelationshipsPart(), sb, indent + "    ");
            }
        }
    }
}