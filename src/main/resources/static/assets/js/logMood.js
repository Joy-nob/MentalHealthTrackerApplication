document.addEventListener("DOMContentLoaded", function () {
    const moodButtons = document.querySelectorAll('.mood-btn');
    const selectedMoodInput = document.getElementById('selectedMood');
    const submitButton = document.getElementById('submitMood');

    // Handle mood selection
    moodButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remove 'active' class from all buttons
            moodButtons.forEach(btn => btn.classList.remove('active'));

            // Add 'active' class to the selected button
            button.classList.add('active');
            selectedMoodInput.value = button.dataset.mood; // Save the selected mood
        });
    });

    // Handle mood submission (this is handled by the form submission)
    submitButton.addEventListener('click', () => {
        if (!selectedMoodInput.value) {
            alert("Please select a mood before submitting.");
            return;
        }
        // Proceed with form submission or AJAX request
    });
});

function toggleForm(formId) {
    const form = document.getElementById(formId);
    form.style.display = form.style.display === "none" ? "block" : "none";
}

function submitMood() {
    // Add logic to handle mood submission
}

function submitNote() {
    // Add logic to handle note submission
}
