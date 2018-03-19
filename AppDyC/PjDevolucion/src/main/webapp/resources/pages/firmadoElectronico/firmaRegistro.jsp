<%@ page import="mx.gob.sat.siat.modelo.dto.AccesoUsr"%>
<%@ page import="mx.gob.sat.siat.dyc.util.JSFUtils"%>
<%@ page import="mx.gob.sat.siat.dyc.generico.util.Utils"%>
<%@ page language="java"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <link href="<%=request.getContextPath()%>/css/fiel.css" rel="stylesheet" type="text/css" />

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Applet Firma Electr&oacute;nica</title>

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/deployJava.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.6.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/pop-up-jquery.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/FIEL.js"></script>
        <link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/resources/css/xacerror.css" rel="stylesheet" type="text/css"/>

        <script type="text/javascript">

            var attributes = {
                name: 'FIEL',
                codebase: '<%=request.getContextPath()%>/resources/applet',
                code: 'com.novell.applet.firmaElectronica.main.SgiApplet',
                archive: 'appletFIEL.jar',
                width: '0',
                height: '0'
            };
            var parameters = {
                jnlp_href: 'appletFIEL.jnlp'
            };
            var version = '1.5';
            deployJava.runApplet(attributes, parameters, version);
            
            function alertar(e) {
    document.getElementById("msgError").innerHTML = "";
    document.getElementById("msgError").innerHTML = e;
    $('#xacerror').bPopup();
}
        </script>
    </head>
    <body>
    
    <% 
    String rfcSesion = (String) JSFUtils.getExternalContext().getFlash().get("rfcCont");
    String cadenaOriginal = null;
    if(rfcSesion != null)
    {
      cadenaOriginal= (String) JSFUtils.getExternalContext().getFlash().get("datosReq"); 
    }else{
    rfcSesion = (String)request.getAttribute("rfcCont");
    }
    
   //cadenaOriginal = (String) session.getAttribute("cadenaOriginal");
    System.out.println("FIRMADO ELECTRONICO "+rfcSesion+"   "+cadenaOriginal);
    %>
    
        <div id="xacerror">
            <a id="linkClose" class="b-close">x</a>
            <table border="0">
                <tr>
                    <td><img id="imgLogo" src="<%=request.getContextPath()%>/resources/images/satLogoChico.jpg" /></td>
                    <td>&nbsp;</td>
                    <td><p id="msgError"></p></td>
                </tr>
            </table>
        </div>
        <center><h1>Applet de firmado Electr&oacute;nico</h1></center><br><br>
        <form id="FIELForm" name="FIELForm" action="<%=request.getContextPath()%>/faces/resources/pages/registro/adicionarSolicitud.jsf?step=tabDatos" method="post"> <!--demoFirma.jsf-->

            <table border="0" align="center">

                <tr>
                    <td width="auto"></td>
                    <td>
                        <br>
                        <br>
                        <div align="center">
                            <table class="" width="500" bgcolor="" cellpadding="0"
                                   cellspacing="3" border="0">
                                <tr>
                                    <td align="center" colspan="3">
                                        <table class="contrasena" width="100%" border="0">
                                            <tr>

                                                <td width="69%"><div align="center"></div></td>
                                                <td width="20%">
                                                </td>
                                                <td width="2%">|</td>
                                                <td width="9%"><div align="left"><b>FIEL</b></div></td>

                                                <td width="69%">&nbsp;</td>
                                                <td width="20%">&nbsp;</td>
                                                <td width="2%">&nbsp;</td>
                                                <td width="9%"><div align="left"></div></td>
                                            </tr>
                                        </table>
                                        <div>
                                            <b> <span>Firma Electr&oacute;nica Avanzada
                                                </span></b> <br /> <br />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="150" align="left"><b>Certificado (cer)</b></td>
                                    <td width="158" align="left">
                                        <input type="text"	name="certificate" id="certificate" size="25" class="deshabilitado"  
                                               />
                                    </td>
                                    <td width="102" align="left">
                                        <input type="button" value='Buscar' id="btnCert" name="btnCert" onclick="browseForCertificate()" />
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" class="normalFont"><b>Clave privada
                                            (key)</b></td>
                                    <td align="left">
                                        <input name="privateKey" id="privateKey" type="text" size="25" class="deshabilitado"
                                               />
                                    </td>
                                    <td align="left">
                                        <input type="button" value='Buscar'	id="btnPrivateKey" name="btnPrivateKey" onclick="browseForPrivateKey()"/></td>
                                </tr>
                                <tr>
                                    <td align="left" class="normalFont"><b>Contraseña de
                                            clave privada</b></td>
                                    <td colspan="2" align="left"><input type="password"
                                                                        name="privateKeyPassword" id="privateKeyPassword" size="25" class="cajatexto" /></td>
                                </tr>
                                <tr>
                                    <td align="left"><b>RFC</b></td>
                                    <td colspan="2" align="left">
                                        <input type="text" name="userID" id="userID" size="25" class="deshabilitado" readonly="readonly" 
                                               />
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center" height="33" colspan="3">

                                  <input type="button" id="btnSubmit" value="Firmar" onclick="enviarFormulario()"/>
                                        <!-- input type="submit" id="btnSubmit" value="Firmar"/-->
                                        <input type="hidden" id="firmaDigital" name="firmaDigital" title="firma"/> 
                                        <input type="hidden" id="numeroSerie" name="numeroSerie"/> 
                                        <input type="hidden" id="cadenaOriginal" name="cadenaOriginal"	value="<%=cadenaOriginal%>" />
                                        <input type="hidden" id="mode" name="mode" value="uat"/>
                                   <br/> <input type="text" id="rfcSesion" name="rfcSesion" value="<%=rfcSesion%>">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                    <td></td>
                    <td>
                    </td></tr></table>
        </form>

    </body>
</html>
