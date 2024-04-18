const updateInterval = 5000;

function updateStatus() {
    const mainView = document.querySelector('my-element');
    mainView.$server.getCurrentStatus("ignore");
    console.log("1")
}

setInterval(updateStatus, updateInterval);