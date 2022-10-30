function getClosestValidDateTime() {

        var today = new Date();

        var hh = today.getHours() +1; // get to closest full hour
        var dd = today.getDate();
        var MM = today.getMonth() +1; // January is 0!
        var yyyy = today.getFullYear();

        // we start taking appointments starting from 09:00 o'clock, up to 17:00 o'clock
        var min = '09';
        var max = 17;

        if(hh <= min){
            hh = '09'
        } else if(hh > max)  {
            hh = '09';
            dd = dd +1;
        }

        if(dd<10){
            dd='0'+dd
        }

        if(MM<10){
            MM='0'+MM
        }


        var todayString = yyyy + '-' + MM + '-' + dd + 'T' + hh + ':' + '00';

        return todayString;
}

function setDefaultDateAndMinDateToToday() {

        var closestValidDateTime = getClosestValidDateTime();

        document.getElementById("dateTimePicker").value = closestValidDateTime;
        document.getElementById("dateTimePicker").setAttribute("min", closestValidDateTime);
}

function checkTimeRange() {

        var pickedDateTime = document.getElementById("dateTimePicker").value;
        var pickedHour = pickedDateTime.substring(11, 13);
        var closestValidDateTime = getClosestValidDateTime();
        var min = '09';
        var max = 17;


        if(pickedHour < min){
            alert("It is too early! The earliest possible appointment time is " + min + ":00.");
            document.getElementById("dateTimePicker").value = closestValidDateTime;
        }

        if(pickedHour > max){
            alert("It is too late! The latest possible appointment time is  " + max + ":00.");
            document.getElementById("dateTimePicker").value = closestValidDateTime;
        }
}