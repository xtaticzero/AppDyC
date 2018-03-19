function validar(e) {
    var codigo = e.charCode ? e.charCode : e.keyCode;
    var cint = parseInt(codigo);
    if (cint == 46 || cint == 8 || (cint >= 35 && cint <= 39) || (cint >= 48 && cint <= 57))
        return true;
    return false;
}

function manejarClickBtnAceptarPDict() {
    var combos = document.getElementsByTagName('select');
    var comboDictamen = null;
    for (var i = 0;i < combos.length;i++) {
        var comboAux = combos[i];
        if (comboAux.id.indexOf("cmbDictamen") !=  - 1) {
            comboDictamen = comboAux;
            break;
        }
    }

    var accion = comboDictamen.options[comboDictamen.selectedIndex].value;

    /*if(accion == 1)
        dlg2.show();
    else*/
    if (accion >= 2 && accion <= 9) {
        var buttons = document.getElementsByTagName('button');
        for (var i = 0;i < buttons.length;i++) {
            var button = buttons[i];

            if (button.id.indexOf("cbtRealizarAccionEnServ") !=  - 1) {
                button.click();
                break;
            }
        }
    }
    else 
        alert('Se debe seleccionar una opción');
}

function validarRFC(e) {
    var codigo = e.charCode ? e.charCode : e.keyCode;
    var cint = parseInt(codigo);
    if (cint == 8 || (cint >= 35 && cint <= 39) || (cint >= 48 && cint <= 57) || (cint >= 65 && cint <= 90))
        return true;
    else {
        if (cint >= 97 && cint <= 122) {

        }
    }
    return false;
}

function manejarClickBtnEliminarFactura() {
    if (dtbFacturas.selection == '') {
        alert('Se debe seleccionar una factura');
    }
    else {
        dlgConfirmarElimFactura.show();
    }
}

function manejarClickGenerarDocumentoReqinfo() {

}

function confirmarBorrarPapelTrabajo() {
    alert('confirmarBorrarPapelTrabajo');
}

function manejarClickBtnEliminarFactura() {
    if (dtbFacturas.selection == '') {
        alert('Se debe seleccionar una factura');
    }
    else {
        dlgConfirmarElimFactura.show();
    }
}

function manejarBtnConsultarDocs() {
    if (dtbDocumentos.selection == '') {
        alert('Se debe seleccionar un documento');
    }
    else {
        var buttons = document.getElementsByTagName('button');
        for (var i = 0;i < buttons.length;i++) {
            var button = buttons[i];

            if (button.id.indexOf("btnConsultarO") !=  - 1) {
                button.click();
                break;
            }
        }
    }
}

function manejarBtnEnviarAAprob() {
    if (dtbDocumentos.selection == '') {
        alert('Se debe seleccionar un documento');
    }
    else {
        dlgEnvAAprob.show();
    }
}

function manejarBtnAceptarEAA() {
    var combos = document.getElementsByTagName('select');
    var combo = null;
    for (var i = 0;i < combos.length;i++) {
        var comboAux = combos[i];
        if (comboAux.id.indexOf("cmbSuperiorAAprobar") !=  - 1) {
            combo = comboAux;
            break;
        }
    }

    var opcion = combo.options[combo.selectedIndex].value;

    if (opcion == 0) {
        alert('Se debe seleccionar al superior a quien se escala la aprobación');
    }
    else {
        var buttons = document.getElementsByTagName('button');
        for (var i = 0;i < buttons.length;i++) {
            var button = buttons[i];

            if (button.id.indexOf("btnAceptarEAO") !=  - 1) {
                button.click();
                break;
            }
        }
    }
}

function observar() {
    var buttons = document.getElementsByTagName('button');
    var buttonHTML;

    for (var i = 0;i < buttons.length;i++) {
        buttonHTML = buttons[i];

        if (buttonHTML.id.indexOf("btnLiquidar") !=  - 1) {
            break;
        }
    }
    var buttonWidgetVar = btnLiquidar;

    var comboWidgetVar = cmbResolucion.input[0];

    var combos = document.getElementsByTagName('select');
    var comboHTML;
    for (var i = 0;i < combos.length;i++) {
        var comboAux = combos[i];
        if (comboAux.id.indexOf("cmbAccionRCC") !=  - 1) {
            comboHTML = comboAux;
            break;
        }
    }

    var commandButtonWidgetVar = btnAceptarERWV;

    debugger;
}

function manejarClickAceptarEmitirRes() {
    var optSelec = cmbResolucion.value;

    if (optSelec == 0 || optSelec == undefined)
        alert('Se debe elegir la Resolución');
    else if (optSelec == 1)// 1 -> Registrar aviso o caso de compensación
        dlgEnvAAprob.show();
    else if (optSelec == 2)// 2 -> Emitir liquidación aviso o caso de compensación
        btnLiquidar.jq[0].click();
}

function manejarBtnAceptarEAARC() {
    var optSelec = cmbSuperiorRegComp.value;

    if (optSelec == 0 || optSelec == undefined)
        alert('Se debe elegir elegir el superior al que se le registrará la compensación');
    else 
        cbtAceptarDEAAH.jq[0].click();
}

function manejarClickBtnDictaminarBC() {
    if (dtbCompensaciones.selection == '') {
        alert('Se debe seleccionar una compensación');
    }
    else {
        cbtDictaminar.jq[0].click();
    }
}

function manejarClickBtnAceptarPAprobRes() {
    var optSelec = cmbAccionRCC.value;

    if (optSelec == 0 || optSelec == undefined)
        alert('Se debe seleccionar una opción válida');
    else 
        cbtAceptarHPAR.jq[0].click();
}

function manejarClickBtnAceptarEA() {
    var optSelec = cmbJefeAprobacion.value;

    if (optSelec == 0 || optSelec == undefined)
        alert('Se debe elegir el superior al que se le enviará la aprobación');
    else if (fupReqInfo.jq[0].value == '') {
        alert('Se debe seleccionar el documento del requerimiento de información');
    }
    else {
        btnAceptarEAER.jq[0].click();
    }
}
/*Queda pendiente validar por javascript
function manejarClickGenerarDocumentoFacturas()
{
    alert('manejarClickGenerarDocumentoFacturas');

    //var x =
    alert(dtbFacturas);
    var x = dtbFacturas;
    var b = $('#dtbFacturas>tbody >tr');
    debugger;
    var a = $('#dtbFacturas>tbody >tr').length
    debugger;
    alert('despues del debugger');
    //var dt = document.getElementById('dtbFacturas');
    alert(dtbFacturas.rows.length);

}*/

