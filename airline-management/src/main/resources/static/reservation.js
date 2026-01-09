let aeroports = [];

document.addEventListener('DOMContentLoaded', async () => {
    await loadAeroports();
    loadStats();
});

async function loadAeroports() {
    try {
        const response = await fetch('/api/aeroports');
        aeroports = await response.json();

        const selects = ['searchDepartId', 'searchDestId'];
        selects.forEach(selectId => {
            const select = document.getElementById(selectId);
            aeroports.forEach(a => {
                const option = document.createElement('option');
                option.value = a.idAeroport;
                option.textContent = `${a.codeIata} - ${a.nomAeroport}`;
                select.appendChild(option);
            });
        });
    } catch (error) {
        console.error('Erreur chargement aéroports:', error);
    }
}

async function searchFlights() {
    const departId = document.getElementById('searchDepartId').value;
    const date = document.getElementById('searchDate').value;

    if (!departId || !date) {
        alert('Veuillez sélectionner un aéroport de départ et une date');
        return;
    }

    try {
        const from = `${date}T00:00:00`;
        const to = `${date}T23:59:59`;
        const url = `/api/vols/search?departId=${departId}&from=${from}&to=${to}`;

        const response = await fetch(url);
        const vols = await response.json();

        displayFlightResults(vols);
    } catch (error) {
        console.error('Erreur recherche vols:', error);
        alert('Erreur lors de la recherche');
    }
}

function displayFlightResults(vols) {
    const container = document.getElementById('resultsContainer');
    const results = document.getElementById('flightResults');

    if (!vols || vols.length === 0) {
        results.innerHTML = '<div class="alert alert-info">Aucun vol trouvé</div>';
        container.style.display = 'block';
        return;
    }

    results.innerHTML = vols.map(vol => {
        const depart = aeroports.find(a => a.idAeroport === vol.aeroportDepartId);
        const dest = aeroports.find(a => a.idAeroport === vol.aeroportDestinationId);

        return `
            <div class="card mb-2">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <strong>${vol.numeroVol || 'N/A'}</strong>
                            <p class="mb-0 small text-muted">
                                ${depart?.codeIata} → ${dest?.codeIata}
                            </p>
                        </div>
                        <div class="text-end">
                            <strong class="text-primary">
                                ${vol.prixBase?.toFixed(2)} €
                            </strong>
                            <p class="mb-0 small text-muted">
                                ${new Date(vol.dateDepart).toLocaleTimeString('fr-FR', {
                                    hour: '2-digit',
                                    minute: '2-digit'
                                })}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }).join('');

    container.style.display = 'block';
}

function loadStats() {
    // Simulé pour le moment
    document.getElementById('totalReservations').textContent = '0';
    document.getElementById('confirmedReservations').textContent = '0';
}
