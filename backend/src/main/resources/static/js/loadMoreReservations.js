const NUM_RESULTS = 3;
let timesRequested = 1;

async function loadMoreReservations(){

    const start = (timesRequested) * NUM_RESULTS;
    const end = start + NUM_RESULTS;
    
    const response = await fetch(`/loadMoreReservations/${start}/${end}`);

    if (!response.ok) {
        console.error("Error al cargar más reservas:", response.statusText);
        return;
    }

    const newReservations = await response.text();

    // Verificar si hay contenido nuevo
    if (newReservations.trim() === "") {
        console.warn("No hay más reservas para cargar.");
        return;
    }

    const reservationsDiv = document.getElementById("reservationList");

    // Agregar las nuevas reservas sin sobrescribir
    reservationsDiv.insertAdjacentHTML("beforeend", newReservations);

    timesRequested++;
}
