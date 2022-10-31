function showAppointmentTable() {

        var x = document.getElementById("appointmentTable");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }

        // got it from https://stackoverflow.com/questions/49772700/display-field-on-button-click-thymeleaf
    }

function changeButtonValue() {

        var button = document.getElementById("tableButton");

        if(button.value == "Show Appointment Table") button.value = "Close Appointment Table";
        else button.value = "Show Appointment Table";
}
