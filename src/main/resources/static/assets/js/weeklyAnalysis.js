// Mock data for the graph
const moodData = {
    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    datasets: [{
        label: 'Mood Score',
        data: [6, 7, 5, 8, 6, 7, 9], // Replace with actual data
        borderColor: '#4CAF50',
        backgroundColor: 'rgba(76, 175, 80, 0.2)',
        borderWidth: 1,
        tension: 0.4
    }]
};

// Setting up the Chart.js graph
const ctx = document.getElementById('moodChart').getContext('2d');
const moodChart = new Chart(ctx, {
    type: 'line', // Can be 'bar', 'line', etc.
    data: moodData,
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                max: 10 // Adjust as needed
            }
        }
    }
});

// Mock data for mood summary (You can fetch this from your backend)
const mostFrequentMood = "Happy";
const highestScore = 8;
const lowestScore = 2;

// Display mood summary
document.getElementById('mostFrequentMood').textContent = mostFrequentMood;
document.getElementById('highestScore').textContent = highestScore;
document.getElementById('lowestScore').textContent = lowestScore;
