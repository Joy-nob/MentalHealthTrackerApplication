function addGoal() {
    const goalTitle = document.getElementById("goalTitle").value;
    const goalDays = document.getElementById("goalDays").value;

    if (!goalTitle || !goalDays) {
        alert("Please fill in both fields to add a goal.");
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    fetch("/set-goal", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            [csrfHeader]: csrfToken // Include CSRF token in the headers
        },
        body: new URLSearchParams({
            description: goalTitle,
            days: goalDays
        })
    })
    .then(response => {
        if (response.ok) {
            // Refresh the page to fetch updated goals
            window.location.reload();
        } else {
            throw new Error("Failed to add goal.");
        }
    })
    .catch(error => {
        alert("Failed to add goal. Please try again.");
        console.error(error);
    });
}
