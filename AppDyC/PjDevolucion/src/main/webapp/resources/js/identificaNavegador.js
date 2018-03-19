var sello="";

/**
 * Esta funcion coloca el valor de la cadena original, la cual se utiliza para obtener el sello digital
 * 
 * @param cadena 
 */
function colocarValor(cadena) {
    generaFirmaIndividual([{cadenaOriginal:cadena}],{validarRFCSession:document.getElementById("rfcSession").value},function (error, resultado) {
	if(!error || error===0) {
            obtenerObjetoJSON(resultado);
        } else {
            alert(catalogoErrores[error].mensaje);
        }
    });
}

/**
 * Obtiene el objeto JSON y extrae la parte del sello digital
 */
function obtenerObjetoJSON(resultado) {
    var objeto  = JSON.stringify(resultado);
    var valor1  = objeto.replace("[", "");
    var valor2  = valor1.replace("]", "");
    var objeto2 = JSON.parse(valor2);
    document.getElementById("firmaDigital").value=objeto2.firmaDigital;    
    document.getElementById("cadenaOriginal").value=objeto2.cadenaOriginal;
    document.getElementById("j_idt4:formFirma:openView").click();    
}
