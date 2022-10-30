function setDefaultDateAndMinDateToToday() {

        var today = new Date();

        var mm = today.getMinutes();
        var hh = today.getHours();
        var dd = today.getDate();
        var MM = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();


        if(mm<10){
            mm='0'+mm
        }

        if(hh<10){
            hh='0'+hh
        }

        if(dd<10){
            dd='0'+dd
        }

        if(MM<10){
            MM='0'+MM
        }



        var todayString = yyyy + '-' + MM + '-' + dd + 'T' + hh + ':' + mm;

        document.getElementById("dateTimePicker").value = todayString;
        document.getElementById("dateTimePicker").setAttribute("min", todayString);

        // got it from https://stackoverflow.com/questions/32378590/set-date-input-fields-max-date-to-today
}