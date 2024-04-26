let darkmodeEnabled = sessionStorage.getItem("dark") == "true";

if (darkmodeEnabled) {
    document.body.classList.add("dark");
}

window.addEventListener('load', function () {
    const btn = document.querySelector(".btn-toggle");

    btn.addEventListener("click", function () {
        darkmodeEnabled = !darkmodeEnabled;

        if (darkmodeEnabled) {
            document.body.classList.add("dark");
        } else {
            document.body.classList.remove("dark");
        }

        sessionStorage.setItem("dark", darkmodeEnabled ? "true" : "false");
    });
});