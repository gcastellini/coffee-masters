 document.addEventListener("DOMContentLoaded", function () {
        // Initialize star icons
        const stars = document.querySelectorAll(".fa-star");
        stars.forEach((star) => {
            star.addEventListener("click", toggleStar);
        });

        // Initialize submit button
        const submitBtn = document.getElementById("submitBtn");
        submitBtn.addEventListener("click", onSubmit);

        // Toggle star (check/uncheck)
        function toggleStar(event) {
            const selectedStar = event.target;
            selectedStar.classList.toggle("checked");
        }

        // Handle form submission
        function onSubmit(event) {
            event.preventDefault();

            // Check how many stars are checked
            const checkedStars = document.querySelectorAll(".fa-star.checked").length;

            // Show Snackbar message based on the number of checked stars
            const message = checkedStars > 0
                ? `You have rated with ${checkedStars} stars!`
                : "Please rate your experience before submitting.";

            Snackbar.show({
                text: message,
                pos: 'bottom-center',
                actionTextColor: '#fff',
                backgroundColor: '#333',
            });
        }
    });