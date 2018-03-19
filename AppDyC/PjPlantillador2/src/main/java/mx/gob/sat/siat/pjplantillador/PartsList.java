/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import javax.xml.bind.JAXBElement;
import org.docx4j.XmlUtils;
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


public class PartsList {

    private static final Logger log = Logger.getLogger(PartsList.class);						

    private static final List<WorksheetPart> worksheets = 
            new ArrayList<WorksheetPart>();

    private static SharedStrings sharedStrings = null;
    /**
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {


        String inputfilepath = "/home/socrates/ANEXO2PRUEBA.xlsx";

        // Open a document from the file system
        // 1. Load the Package - .docx or Flat OPC .xml
        org.docx4j.openpackaging.packages.OpcPackage xlsxPkg = 
                org.docx4j.openpackaging.packages.OpcPackage.load(
                        new java.io.File(inputfilepath));		

        // List the parts by walking the rels tree
        RelationshipsPart rp = xlsxPkg.getRelationshipsPart();
        StringBuilder sb = new StringBuilder();
        printInfo(rp, sb, "");
        traverseRelationships(xlsxPkg, rp, sb, "    ");

        System.out.println(sb.toString());

        // Now lets print the cell content
        for(WorksheetPart sheet: worksheets) {
            System.out.println(sheet.getPartName().getName() );
            Worksheet ws = sheet.getJaxbElement();
            SheetData data = ws.getSheetData();
            for (Row r : data.getRow() ) {
                System.out.println("row " + r.getR() );				
                for (Cell c : r.getC() ) {
                    if (c.getT().equals(STCellType.S)) {
                        System.out.println( "  " + c.getR() + " contains " +
                            sharedStrings.getJaxbElement().getSi().get(
                                    Integer.parseInt(c.getV())).getT()
                                                            );
                    } else {
                        // TODO: handle other cell types
                        System.out.println( "  " + c.getR() + " contains " + 
                                c.getV() );
                    }
                }
            }
        }

    }

    public static void  printInfo(Part p, StringBuilder sb, String indent) {
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
     * This HashMap is intended to prevent loops.
     */
    public static HashMap<Part, Part> handled = new HashMap<Part, Part>();

    public static void traverseRelationships(org.docx4j.openpackaging.
            packages.OpcPackage wordMLPackage, 
                RelationshipsPart rp, 
                StringBuilder sb, String indent) {

        // TODO: order by rel id

        for ( Relationship r : rp.getRelationships().getRelationship() ) {

            log.info("For Relationship Id=" + r.getId() 
                + " Source is " + rp.getSourceP().getPartName() 
                + ", Target is " + r.getTarget() );

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