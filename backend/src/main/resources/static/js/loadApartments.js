const NUM_RESULTS = 6;

let timesRequested = 1; // it's 1 because the first 6 apartments are loaded with the /index

async function loadMoreApartments(){

    const start = (timesRequested) * NUM_RESULTS+1;
    const end = start + NUM_RESULTS;
    timesRequested++;

    const response = await fetch(`/loadMoreApartments/${start}/${end}`);
    const newApartments = await response.text();


    const apartmentsDiv = document.getElementById("apartmentsList");
    apartmentsDiv.innerHTML += newApartments;

}