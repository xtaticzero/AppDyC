package mx.gob.sat.siat.dyc.template.web.util;

import mx.gob.sat.siat.dyc.util.constante.ConstantesTagNumber;


public enum TagNumber {

    TAG_003("agaffAdm", "ADMINISTRACION"),
    TAG_010("agaffConcepto", "CONCEPTO"),
    TAG_011("agaffImpuestos", "IMPUESTOORIGEN"),
    TAG_012("agaffImpComp", "IMPUESTOCOMP"),
    TAG_015("agaffConcICEP", "CONCEPTODESTINO"),
    TAG_017("agaffDomCont", "DOMICILIO"),
    TAG_018("agaffEjerc2", "EJERCICIOORIGEN"),
    TAG_019("agaffEjerc", "EJERCICIO"),
    TAG_022("agaffMotDepE", "MOTIVOSINDEPOSITO"),
    TAG_O24("agaffFactAct", "factorActualizacion"),
    TAG_030("agaffFechaNot", "FECHANOTIFICACION"),
    TAG_035("agaffFecSolDev", "FECHAPRESENTACION"),
    TAG_036("agaffFecComp", "FECHACOMP"),
    TAG_038("agaffFechaAnt", "lFechaAnteriorR"),
    TAG_038A("agaffFecDOF2", "agaffFecDOF2"),
    TAG_039("agaffFechaAntiguo", "lFechaAnteriorA"),
    TAG_039A("agaffFecDOFINPC", "agaffFecDOFINPC"),
    TAG_046("agaffFechaPrimReq", "FECHASOLVENTACION"),
    TAG_047("agaffFecha", "FECHAEXPOFICIO"),
    TAG_048("agaffFracc", "FRACCION"),
    TAG_050(ConstantesTagNumber.AGAFFINFDOCREQ, "infoRequerir"),
    TAG_050A(ConstantesTagNumber.AGAFFINFDOCREQ, "anexosRequerir"),
    TAG_050B(ConstantesTagNumber.AGAFFINFDOCREQ, "otraInfo"),
    TAG_055("agaffImpNeto", "impNetoDevolver"),
    TAG_058("agaffImpAct", "impActualizado"),
    TAG_060("agaffImpAut", "impAutorizado"),
    TAG_061("agaffImpTotComp", "importeCompensacion"),
    TAG_062("agaffImpTotComp", "IMPORTETOTALCOMP"),
    TAG_064("agaffImpTotAct", "agaffImpTotAct"),
    TAG_067("agaffImpMulta", "agaffImpMulta"),
    TAG_070("agaffImpRecLiq", "impRecargo"),
    TAG_076("agaffImpSolDev", "IMPORTESOLICITADO"),
    TAG_077("agaffImpTotRec", "agaffImpTotRec"),
    TAG_079("agaffIniciales", "INICIALES"),
    TAG_081("agaffPerAnt", "lInpcAnteriorR"),
    TAG_082("agaffAntPerAntiguo", "lInpcAnteriorA"),
    TAG_083("agaffINPCMAntMAntD", "agaffINPCMAntMAntD"),
    TAG_084("agaffMesAntPer", "lMesAnteriorA"),
    TAG_084A("agaffINPCMAAnt2", "agaffINPCMAAnt2"),
    TAG_085("agaffMesAnt", "lMesAnteriorR"),
    TAG_086("agaffINPCMA2", "lMesAnteriorR2"),
    TAG_088("agaffLeyenda", "LEYENDA"),
    TAG_097("agaffRazonSocial", "RAZONSOCIAL"),
    TAG_101("agaffNombFuncAut", "NOMBREFUNCIONARIOAUTO"),
    TAG_103("agaffNumControl", "NUMCONTROL"),
    TAG_110("agaffNumCtrlDoc", "NUMERODOCUMENTO"),
    TAG_112("agaffINPCMAntRPer", "listaCredito"),
    TAG_115("agaffOrigDev", "ORIGEN"),
    TAG_118("agaffPerSaldo", "PERIODOORIGEN"),
    TAG_119("agaffPerPago", "PERIODO"),
    TAG_132("agaffRFC", "RFCCONTRIBUYENTE"),
    TAG_138("agaffTasaInt", "tasaInteres"),
    TAG_152("agaffImpTotal", "impTotalIntereses"),
    TAG_155("agaffINPCAntRPer", "listaImporteHistorico"),
    TAG_134("agaffSedeAdm", "CIUDADADMLOCAL"),
    TAG_200("agaffDomALAF", "DOMICILIOALAF"),
    TAG_209("agaffNumOperac", "NUMOPERACION"),
    TAG_232("agaffFirDig", "firmaDigital"),
    TAG_233("agaffCadOrig", "cadenaOriginal"),
    TAG_236("agaffFundMot", "funMotivacion"),
    TAG_237("agaffMontInc", "montoInconsistencias"),
    TAG_239("agaffAAntMRec", "listaClaveConceptoLey"),
    TAG_240("agaffFecDOF", "listaConceptoLey"),
    TAG_242("agaffINPCMAAnt", "listaImporteTotal"),
    TAG_243("agaffMontTotHist", "TotalTable2"),
    TAG_244("agaffMontTotAct", "TotalActTable2"),
    TAG_247("agaffNumCtrlDE", "NUMCONTROLDOC"),
    TAG_270("agaffSerieDoc", "SERIEDOCUMENTAL"),
    TAG_271("agaffRFC", "rfcProveedor"),
    TAG_272("agaffNumFact", "listaFolios"),
    TAG_273("agaffFecCF", "listaFechas"),
    TAG_274("agaffImpFact", "listaImportes"),
    TAG_275("agaffIVATras", "listaIva"),
    TAG_276("agaffTotal", "listaTotales"),
    TAG_278("agaffMesComp", "mesCompensacion"),
    TAG_279("agaffAnoComp", "anoCompensacion"),
    TAG_281("agaffTotAPag", "totalAPagar"),
    TAG_285("agaffSelloDig", "selloDigital"),
    TAG_291("agaffFecNotSReq", "FECHANOTIFICACION2"),
    TAG_292(ConstantesTagNumber.AGAFFINFDOCREQ2, "infoRequerir2"),
    TAG_292A(ConstantesTagNumber.AGAFFINFDOCREQ2, "anexosRequerir2"),
    TAG_292B(ConstantesTagNumber.AGAFFINFDOCREQ2, "otraInfo2"),
    
    TAG_293(ConstantesTagNumber.AGAFFMOTIVOS, "listaMotivos"),
    
    TAG_052("agaffHoraCita","agaffHoraCita"),
    TAG_068("agaffImpRec","agaffImpRec"),
    TAG_266("agaffSerDoc","agaffSerDoc"),
    TAG_287("agaffFecEscLib","agaffFecEscLib"),
    TAG_288("agaffFecSegReq","agaffFecSegReq"),
    TAG_290("agaffAreaRes","agaffAreaRes"),
    TAG_320("agaffImpInic","agaffImpInic"),
    TAG_321("agaffMesInic","agaffMesInic"),
    TAG_322("agaffAnoInic","agaffAnoInic"),
    TAG_323("agaffMesFin","agaffMesFin"),
    TAG_324("agaffAnoFin","agaffAnoFin"),
    TAG_325("agaffTotImpPag","agaffTotImpPag"),
    
    TAG_296("agaffResolucion", "resolucion"),
    TAG_299("agaffFactorAct", "lFactorActualizacion"),
    TAG_327("agaffCURP", "CURP"),
    TAG_339("agaffAnoAnt", "lAnioAnteriorR"),
    TAG_340("agaffAnoAntiguo", "lAnioAnteriorA"),
    TAG_400("agaffDomicilio", "DOMICILIOCOMPLETO"),
    TAG_402("agaffNumOficio", "agaffNumOficio"),
    TAG_403("agaffImpCompInd","agaffImpCompInd"),

    TAG_D_O_E( "agaffTipoResolucion","agaffTipoResolucion" ),
    TAG_F( "agaffMontoAplicar","agaffMontoAplicar" );

    private String tagDescreption;
    private String field;

    private TagNumber(String tagDescreption, String field) {
        this.tagDescreption = tagDescreption;
        this.field = field;
    }

    public String getTagDescreption() {
        return tagDescreption;

    }

    public String getField() {
        return field;
    }
}


