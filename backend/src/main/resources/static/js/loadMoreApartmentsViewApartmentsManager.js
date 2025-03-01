const NUM_RESULTS = 6;

let timesRequested = 1;

async function loadMoreApartmentsManagerView(){

    const start = (timesRequested) * NUM_RESULTS;
    const end = start + NUM_RESULTS;
    timesRequested++;

    const response = await fetch(`/loadMoreApartmentsManagerView/${start}/${end}`);
    const newApartments = await response.text();


    const apartmentsDiv = document.getElementById("managerApartmentsList");
    apartmentsDiv.innerHTML += newApartments;

}
