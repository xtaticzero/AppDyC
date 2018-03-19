var FIND_CERTIFICATE = "Buscar Certificado";
var FIND_PRIVATE_KEY = "Buscar Clave Privada";


// javascript messages
var GENERIC_INVALID_MSG = "Certificado, clave privada o contrase\u00f1a de clave privada inv\u00e1lidos, int\u00e9ntelo nuevamente.";
var INVALID_CERTIFICATE_MSG = "Certificado no emitido por el SAT";
var INVALID_PRIVATE_KEY_MSG = "Clave privada inv\u00e1lida";
var INVALID_PRIVATE_KEY_PASSWORD_MSG = "Clave privada o contrase\u00f1a de clave privada inv\u00e1lida";
var JAVA_PROBLEM_MSG = "Su navegador no tiene Java instalado. Esta aplicaci\u00f3n no funcionar\u00e1.";

var NPOBJECT_ERROR = "Los datos introducidos no corresponden, favor de verificar su contrase\u00f1a de clave privada, clave privada y/o certificado nuevamente";

//applet messages
var INVALID_CERT_TYPE = "Certificado no emitido por el SAT: Debe usar un certificado de FIEL";
var INVALID_FILE = "Certificado no emitido por el SAT: Archivo inv\u00e1lido";
var INVALID_CERT = "Certificado no emitido por el SAT:";
var INVALID_USERID = "El certificado no contiene un RFC";
var CORRESPOND_ERROR = "El certificado no corresponde con la clave privada.";

//****************************************************************************************************************
function onload() {
    document.getElementByd("btnSubmit").setAttribute("disabled", "disabled");
}

function validarFormulario() {

    if (document.getElementById("privateKey").value == "") {
        alertar("Debe seleccionar el archivo de Clave Privada");
        return false;
    }
    if (document.getElementById("certificate").value == "") {
        alertar("Debe seleccionar el Certificado ");
        return false;
    }
    if (document.getElementById("privateKeyPassword").value == "") {
        alertar("Debe ingresar la contraseña del Archivo de Clave Privada");
        return false;
    }
    if (document.getElementById("userID").value == "") {
        alertar("Debe ingresar el RFC");
        return false;
    }
    return true;
}


//****************************************************************************************************************
function browseForCertificate() {
    try {

        var filename = document.FIEL.showFileDialog(FIND_CERTIFICATE, "*.cer");

        document.getElementById("certificate").value = filename;

        

        if (filename != null && filename.length > 0) {
            var userId = document.FIEL.getRfcFormCertificate(filename);
            if (userId != null) {
                document.getElementById("userID").value = userId;
                respRfcSesion=document.FIEL.validarRfcSesion(document.getElementById("rfcSesion").value,filename);
		if(respRfcSesion!="ok"){
		   alertar(respRfcSesion);
		   document.FIELForm.userID.value="";
	           document.FIELForm.certificate.value="";
		}
            } else {
                document.getElementById("certificate").value = '';
                throw INVALID_CERTIFICATE_MSG;
            }
        } else {
            document.getElementById("userID").value = '';
        }
    } catch (err) {
        document.getElementById("certificate").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') > -1) {
            desc = err.description;
        }
        // if a key is specified, it returns this message from the SgiCripto libs (nice)
        if (desc.indexOf('5 >= 2') > -1) {
            desc = INVALID_CERTIFICATE_MSG;
        }
        // if the applet hasn't loaded yet, or won't load
        if (desc.indexOf('TypeError') > -1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }
        if (desc.indexOf("java.security.PrivilegedActionException: java.lang.Exception: ")==0){
            desc = desc.substring(31);
        }
        //$('#error').html(INVALID_FILE); //
        alertar(desc);
    }
}
function browseForPrivateKey() {
    try {

        var filename = document.FIEL.showFileDialog(FIND_PRIVATE_KEY, "*.key");

        document.getElementById("privateKey").value = filename;

    } catch (err) {
        document.getElementById("privateKey").value = '';

        var desc = err.toString();
        if (navigator.appName.indexOf('Microsoft') > -1) {
            desc = INVALID_PRIVATE_KEY_MSG;
        }
        if (desc.indexOf('TypeError') > -1) {
            desc = JAVA_PROBLEM_MSG;
        }
        if (desc.indexOf("java.lang.Exception: ") == 0) {
            desc = desc.substring(21);
        }
        if (desc.indexOf("java.io.FileNotFoundException: ") == 0) {
            desc = desc.substring(31);
        }
        
//        $('#error').html(INVALID_FILE);
        alertar(INVALID_FILE);
        //addMessage(desc);
    }
}
//****************************************************************************************************************

function enviarFormulario() {

    var rfc = document.getElementById("userID").value;
    var userPwd = document.getElementById("privateKeyPassword").value;
    var certFilePath = document.getElementById("certificate").value;
    var keyFilePath = document.getElementById("privateKey").value;
    var numeroSerie = document.getElementById("numeroSerie").value;
    var cadenaOriginal = document.getElementById("cadenaOriginal").value;
    var mode = document.getElementById("mode").value;


    var selloDigital;


    if (validarFormulario()) {

        try {
            document.FIEL.firmarElectronicamente(rfc, userPwd, certFilePath, keyFilePath, numeroSerie, cadenaOriginal, mode);
            cadenaOriginal = document.FIEL.getCadena();
            selloDigital = document.FIEL.getFirma();
            numeroSerie = document.FIEL.getNumeroSerieCertificado();
            document.getElementById("cadenaOriginal").value = cadenaOriginal;
            document.getElementById("firmaDigital").value = selloDigital;
            document.getElementById("numeroSerie").value = numeroSerie;
            document.FIELForm.submit();
            alertar("Numero de serie del certificado: " + document.getElementById("numeroSerie").value + "\n\
            Cadena Original: " + cadenaOriginal + "\n\
            Firma digital:  " + selloDigital);
        } catch (err) {
            document.getElementById("privateKeyPassword").value = '';
            var desc = err.toString();

            if (navigator.appName.indexOf('Microsoft') > -1) {
                desc = err.description;
            }
            // if a key is specified, it returns this message from the SgiCripto libs (nice)
            if (desc.indexOf('5 >= 2') > -1) {
                desc = INVALID_CERTIFICATE_MSG;
            }
            // if the applet hasn't loaded yet, or won't load
            if (desc.indexOf('TypeError') > -1) {
                desc = JAVA_PROBLEM_MSG;
            }
            if (desc.indexOf('Bad sequence') > -1 || desc.indexOf('cannot be cast') > -1) {
                desc = INVALID_PRIVATE_KEY_MSG;
            }
            // invalid private key password
            if (desc.indexOf('pad block corrupted') > -1) {
                desc = INVALID_PRIVATE_KEY_PASSWORD_MSG;
            }
            // applet is not loaded yet
            if (desc.indexOf('TypeError') > -1) {
                desc = JAVA_PROBLEM_MSG;
            }
            // chrome bug - message when key and pwd do not correspond
            if (desc.toLowerCase().indexOf('npobject') > -1) {
                desc = NPOBJECT_ERROR;
            }
            if (desc.indexOf("java.lang.Exception: ") == 0) {
                desc = desc.substring(21);
            } else {
                desc = GENERIC_INVALID_MSG;
            }

            alertar(desc);
        }
    }
}

