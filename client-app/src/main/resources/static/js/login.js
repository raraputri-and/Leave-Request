const pass_field = document.querySelector('.pass-key');
const showBtn = document.querySelector('.show');
showBtn.addEventListener('click', function () {
    if (pass_field.type === "password") {
        pass_field.type = "text";
        showBtn.textContent = "HIDE";
        showBtn.style.color = "#3498db";
    } else {
        pass_field.type = "password";
        showBtn.textContent = "SHOW";
        showBtn.style.color = "#222";
    }
});

var slideIndex = 0;

carousel();

function carousel() {
    var i;
    var x = document.getElementsByClassName("sub-login");
    console.log("Current slide index:", slideIndex);

    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    slideIndex++;

    if (slideIndex > x.length) { slideIndex = 1 }
    x[slideIndex - 1].style.display = "block";
    console.log("Displaying slide:", slideIndex);

    setTimeout(carousel, 3000); // Change image every 3 seconds
}

