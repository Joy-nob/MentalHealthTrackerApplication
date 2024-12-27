document.addEventListener("DOMContentLoaded", function() {
    const buttonData = [
        { id: 'logMoodBtn', text: 'Log Mood', section: '/log-mood' },
        { id: 'setGoalBtn', text: 'Set Goal', section: '/set-goal' },
        { id: 'selfAppreciationBtn', text: 'Self Appreciation', section: '/self-appreciation' },
        { id: 'gratitudeBtn', text: 'Gratitude', section: '/gratitude' },
        { id: 'weeklyMoodAnalysisBtn', text: 'Weekly Mood Analysis', section: '/weekly-mood-analysis' }
    ];

    const container = document.querySelector("main");
    container.innerHTML = '<h1><span style="color: magenta;">Mood Tracker</span></h1>'; // Restore welcome text

    const buttons = [];

    // Create buttons only once
    buttonData.forEach((data) => {
        if (!document.getElementById(data.id)) {
            const button = document.createElement('button');
            button.id = data.id;
            button.classList.add('floating-btn');
            button.innerText = data.text;
            button.onclick = () => window.location.href = data.section;

            // Initial random position
            const { x, y } = getRandomPosition();
            button.style.position = "absolute";
            button.style.left = `${x}px`;
            button.style.top = `${y}px`;

            // Stop moving on hover
            button.addEventListener("mouseenter", () => {
                button.style.transition = "none"; // Stop movement
            });

            button.addEventListener("mouseleave", () => {
                button.style.transition = "left 2s ease-in-out, top 2s ease-in-out"; // Resume movement
            });

            buttons.push(button);
            container.appendChild(button);
        }
    });

    // Function to generate random position
    function getRandomPosition() {
        const x = Math.random() * (window.innerWidth - 150); // Random X within screen width
        const y = Math.random() * (window.innerHeight - 100); // Random Y within screen height
        return { x, y };
    }

    // Smoothly move buttons randomly
    function moveButtons() {
        buttons.forEach((button) => {
            const { x, y } = getRandomPosition();
            button.style.transition = "left 2s ease-in-out, top 2s ease-in-out";
            button.style.left = `${x}px`;
            button.style.top = `${y}px`;
        });
    }

    // Function to check for overlap
    function checkOverlap() {
        buttons.forEach((button1, index1) => {
            buttons.forEach((button2, index2) => {
                if (index1 !== index2) {
                    const rect1 = button1.getBoundingClientRect();
                    const rect2 = button2.getBoundingClientRect();
                    if (isOverlapping(rect1, rect2)) {
                        button1.classList.add('fade');
                    } else {
                        button1.classList.remove('fade');
                    }
                }
            });
        });
    }

    // Check if two elements overlap
    function isOverlapping(rect1, rect2) {
        return !(rect1.right < rect2.left || rect1.left > rect2.right || rect1.bottom < rect2.top || rect1.top > rect2.bottom);
    }

    // Move buttons every 2 seconds
    setInterval(moveButtons, 2000);

    // Check overlap every 0.1 second
    setInterval(checkOverlap, 100);
});
