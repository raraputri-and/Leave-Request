document.getElementById("dateStart").onchange = calculateDateDiff;
document.getElementById("dateEnd").onchange = calculateDateDiff;

function calculateDateDiff() {
    var start = new Date(document.getElementById("dateStart").value);
    console.log("Start Date: ", start); // Debugging line

    var end = new Date(document.getElementById("dateEnd").value);
    console.log("End Date: ", end); // Debugging line

    if (start && end) {
        var diffDays = countWeekdays(start, end);
        console.log("Diff Days: ", diffDays); // Debugging line

        document.getElementById("quantity").value = isNaN(diffDays) ? '' : diffDays;

        console.log("Quantity Value:", document.getElementById("quantity").value); // Debugging line
    }
}

function countWeekdays(start, end) {
    var numDays = 0;
    var currentDay = new Date(start);

    while (currentDay <= end) {
        var dayOfWeek = currentDay.getDay();
        if (dayOfWeek != 0 && dayOfWeek != 6) // excluding Sunday (0) and Saturday (6)
            numDays++;
        currentDay.setDate(currentDay.getDate() + 1); // increment to next day
    }
    return numDays;
}



var today = new Date().toISOString().split('T')[0];
document.getElementById("dateStart").min = today;
document.getElementById("dateEnd").min = today;

document.getElementById("dateStart").onchange = function () {
    document.getElementById("dateEnd").min = this.value;
};
