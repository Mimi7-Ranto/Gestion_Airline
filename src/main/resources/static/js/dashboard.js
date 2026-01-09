// --------------------------
// Chargement des statistiques
// --------------------------
async function loadStats() {
    try {
        const aeroports = await fetch('/api/aeroports').then(r => r.json());
        const vols = await fetch('/api/vols').then(r => r.json());

        document.getElementById('statsAeroports').textContent = aeroports.length;
        document.getElementById('statsVols').textContent = vols.length;
        document.getElementById('statsReservations').textContent = '0';
        document.getElementById('statsPassagers').textContent = '0';
    } catch (error) {
        console.error('Erreur chargement stats:', error);
    }
}

// --------------------------
// Charger la liste des avions pour la gestion des sièges (admin)
// --------------------------
async function loadAvionsForManage() {
    try {
        const res = await fetch('/api/avions');
        const avions = await res.json();
        const sel = document.getElementById('manageAvionSelect');
        sel.innerHTML = '';

        if (!avions || avions.length === 0) {
            const opt = document.createElement('option');
            opt.value = '';
            opt.textContent = 'Aucun avion';
            sel.appendChild(opt);
            return;
        }

        avions.forEach(a => {
            const opt = document.createElement('option');
            opt.value = a.id;
            opt.textContent = a.modele + ' (' + a.id + ')';
            sel.appendChild(opt);
        });

        document.getElementById('manageSeatsBtn').addEventListener('click', () => {
            const id = sel.value;
            const msg = document.getElementById('manageMsg');
            msg.textContent = '';

            if (!id) {
                msg.textContent = 'Sélectionnez un avion.';
                return;
            }

            window.location.href = '/admin/aircraft/' + id + '/seats';
        });

    } catch (e) {
        console.error('Erreur chargement avions:', e);
        const msg = document.getElementById('manageMsg');
        if (msg) msg.textContent = 'Impossible de charger la liste des avions.';
    }
}

// --------------------------
// Recherche de vols (client)
// --------------------------
async function performSearch() {
    const from = document.getElementById('from').value;
    const to = document.getElementById('to').value;
    const date = document.getElementById('date').value;
    const passengers = document.getElementById('passengers').value;

    const resultsContainer = document.getElementById('searchResults');
    resultsContainer.innerHTML = '';

    if (!from || !to || !date || !passengers) {
        resultsContainer.textContent = 'Veuillez remplir tous les champs.';
        return;
    }

    try {
        const url = `/api/vols/search?depart=${encodeURIComponent(from)}&destination=${encodeURIComponent(to)}&date=${date}&passengers=${passengers}`;
        const res = await fetch(url);
        if (!res.ok) throw new Error('Erreur recherche vols');
        const vols = await res.json();

        if (!vols || vols.length === 0) {
            resultsContainer.textContent = 'Aucun vol trouvé.';
            return;
        }

        vols.forEach(vol => {
            const div = document.createElement('div');
            div.className = 'border p-2 mb-2 rounded';
            div.innerHTML = `
                <strong>${vol.numeroVol || 'N/A'}</strong> :
                ${vol.aeroportDepart || ''} → ${vol.aeroportDestination || ''} 
                (${new Date(vol.dateDepart).toLocaleTimeString('fr-FR', {hour:'2-digit',minute:'2-digit'})}) -
                Prix: ${vol.prixBase?.toFixed(2) || '-'} €
            `;
            resultsContainer.appendChild(div);
        });

    } catch (e) {
        console.error(e);
        resultsContainer.textContent = 'Erreur lors de la recherche.';
    }
}

// --------------------------
// Initialisation au chargement
// --------------------------
document.addEventListener('DOMContentLoaded', () => {
    loadStats();

    if (document.getElementById('manageAvionSelect')) {
        loadAvionsForManage();
    }

    if (document.getElementById('searchForm')) {
        document.getElementById('searchForm').addEventListener('submit', e => {
            e.preventDefault();
            performSearch();
        });
    }
});
