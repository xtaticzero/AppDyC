PrimeFaces.locales['es'] = {
    closeText : 'Cerrar', prevText : 'Anterior', nextText : 'Siguiente', currentText : 'Inicio', monthNames : ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'], monthNamesShort : ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'], dayNames : ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'], dayNamesShort : ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'], dayNamesMin : ['D', 'L', 'M', 'Mi', 'J', 'V', 'S'], weekHeader : 'Semana', firstDay : 1, isRTL : false, showMonthAfterYear : false, yearSuffix : '', timeOnlyTitle : 'Sólo hora', timeText : 'Tiempo', hourText : 'Hora', minuteText : 'Minuto', secondText : 'Segundo', currentText : 'Fecha actual', ampm : false, month : 'Mes', week : 'Semana', day : 'Día', allDayText : 'Todo el día'
};

function validarNumero(numb) {
    var number2 = isFinite(numb.value);
    if (number2) {
        if (numb.value < 0) {
            numb.value =  - numb.value;
        }

        var x = numb.value.split('.');
        numb.value = x[0];
    }
    else {
        numb.value = "";
    }
};

function justNumbers(e) {
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
        return true;
    return /\d/.test(String.fromCharCode(keynum));
};

function soloNumeros(evt) {
    var keynum = window.event ? window.event.keyCode : evt.which;
    if ((keynum >= 48) && (keynum <= 57))
        return true;

    return /\d/.test(String.fromCharCode(keynum));
};