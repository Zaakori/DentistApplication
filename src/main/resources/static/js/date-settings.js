function getClosestValidDateTime() {

        var today = new Date();

        var hh = today.getHours() +1; // get to closest full hour
        var dd = today.getDate();
        var MM = today.getMonth() +1;
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

function checkCorrectTime() {

        var pickedDateTime = document.getElementById("dateTimePicker").value;
        var pickedMinutes = pickedDateTime.substring(14, 16);
        var pickedHour = pickedDateTime.substring(11, 13);

        var min = '09';
        var max = 17;

        var isValidTime = true;


        if(pickedMinutes != '00'){
            alert("Appointments start only at full hour.");
            isValidTime = false;
        }

        if(pickedHour < min){
            alert("It is too early. The earliest possible appointment time is " + min + ":00.");
            isValidTime = false;
        }

        if(pickedHour > max){
            alert("It is too late. The latest possible appointment time is  " + max + ":00.");
            isValidTime = false;
        }

        if(!isValidTime){
            document.getElementById("dateTimePicker").value = getClosestValidDateTime();
        }
}