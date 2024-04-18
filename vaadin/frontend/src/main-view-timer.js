const updateInterval = 5000;

function updateStatus() {
    const mainView = document.querySelector('my-element');
    mainView.$server.getCurrentStatus();
}

setInterval(updateStatus, updateInterval);