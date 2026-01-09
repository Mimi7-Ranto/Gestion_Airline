// vols.js

const API_URL = '/api/vols';
const AEROPORTS_URL = '/api/aeroports';
let modal;
let aeroports = [];

document.addEventListener('DOMContentLoaded', async () => {
    modal = new bootstrap.Modal(document.getElementById('volModal'));
    await loadAeroports();
    await loadAvionsAndCompanies();
    loadVols();
});

async function loadAeroports() {
    try {
        const response = await fetch(AEROPORTS_URL);
        aeroports = await response.json();
        
        const selects = ['aeroportDepartId', 'aeroportDestinationId', 'searchDepartId'];
        selects.forEach(selectId => {
            const select = document.getElementById(selectId);
            const keepFirst = selectId === 'searchDepartId';
            if (!keepFirst) select.innerHTML = '<option value="">Sélectionnez...</option>';
            
            aeroports.forEach(a => {
                const option = document.createElement('option');
                option.value = a.idAeroport;
                option.textContent = `${a.codeIata} - ${a.nomAeroport} (${a.ville})`;
                select.appendChild(option);
            });
        });
    } catch (error) {
        console.error('Erreur chargement aéroports:', error);
    }
}

async function loadAvionsAndCompanies() {
    try {
        const [avionsResp, compsResp] = await Promise.all([fetch('/api/avions'), fetch('/api/companies')]);
        const avions = await avionsResp.json();
        const comps = await compsResp.json();

        const avionSelect = document.getElementById('avionId');
        const compSelect = document.getElementById('companyId');

        avionSelect.innerHTML = '<option value="">Sélectionnez...</option>';
        compSelect.innerHTML = '<option value="">Sélectionnez...</option>';

        avions.forEach(a => {
            const o = document.createElement('option');
            o.value = a.idAvion;
            o.textContent = a.modele;
            avionSelect.appendChild(o);
        });

        comps.forEach(c => {
            const o = document.createElement('option');
            o.value = c.idCompany;
            o.textContent = c.nomCompany;
            compSelect.appendChild(o);
        });
    } catch (err) {
        console.error('Erreur chargement avions/companies', err);
    }
}

async function loadVols() {
    try {
        const response = await fetch(API_URL);
        const vols = await response.json();
        displayVols(vols);
    } catch (error) {
        console.error('Erreur:', error);
    }
}

async function searchVols() {
    const departId = document.getElementById('searchDepartId').value;
    const from = document.getElementById('searchFrom').value;
    const to = document.getElementById('searchTo').value;

    if (!departId || !from || !to) {
        loadVols();
        return;
    }

    try {
        const url = `${API_URL}/search?departId=${departId}&from=${from}&to=${to}`;
        const response = await fetch(url);
        const vols = await response.json();
        displayVols(vols);
    } catch (error) {
        console.error('Erreur:', error);
    }
}

function displayVols(vols) {
    const tbody = document.querySelector('#volsTable tbody');
    tbody.innerHTML = '';
    
    vols.forEach(vol => {
        const tr = document.createElement('tr');
        const departAeroport = aeroports.find(a => a.idAeroport === vol.aeroportDepartId);
        const destAeroport = aeroports.find(a => a.idAeroport === vol.aeroportDestinationId);
        
        tr.innerHTML = `
            <td><strong>${vol.numeroVol || 'N/A'}</strong></td>
            <td>${departAeroport?.codeIata || 'N/A'}</td>
            <td>${destAeroport?.codeIata || 'N/A'}</td>
            <td>${formatDate(vol.dateDepart)}</td>
            <td>${formatDate(vol.dateArrivee)}</td>
            <td>${vol.prixBase?.toFixed(2)} €</td>
            <td><span class="badge bg-info">${vol.etatVolId || 'N/A'}</span></td>
            <td>
                <button class="btn btn-sm btn-warning" onclick='editVol(${JSON.stringify(vol)})'>
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="deleteVol('${vol.idVol}')">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function formatDate(dateStr) {
    if (!dateStr) return 'N/A';
    const date = new Date(dateStr);
    return date.toLocaleString('fr-FR');
}

function resetForm() {
    document.getElementById('volForm').reset();
    document.getElementById('volId').value = '';
    document.getElementById('modalTitle').textContent = 'Nouveau Vol';
}

function editVol(vol) {
    document.getElementById('volId').value = vol.idVol;
    document.getElementById('numeroVol').value = vol.numeroVol;
    document.getElementById('prixBase').value = vol.prixBase;
    document.getElementById('aeroportDepartId').value = vol.aeroportDepartId || vol.idAeroportDepart || vol.aeroportDepart;
    document.getElementById('aeroportDestinationId').value = vol.aeroportDestinationId || vol.idAeroportDestination || vol.aeroportDestination;
    document.getElementById('avionId').value = vol.idAvion || vol.avionId || '';
    document.getElementById('companyId').value = vol.idCompany || vol.companyId || '';
    document.getElementById('dateDepart').value = vol.dateDepart?.slice(0, 16);
    document.getElementById('dateArrivee').value = vol.dateArrivee?.slice(0, 16);
    document.getElementById('modalTitle').textContent = 'Modifier Vol';
    modal.show();
}

async function saveVol() {
    const id = document.getElementById('volId').value;
    const data = {
        numeroVol: document.getElementById('numeroVol').value,
        prixBase: parseFloat(document.getElementById('prixBase').value),
        aeroportDepartId: document.getElementById('aeroportDepartId').value,
        aeroportDestinationId: document.getElementById('aeroportDestinationId').value,
        idAvion: document.getElementById('avionId').value,
        idCompany: document.getElementById('companyId').value,
        dateDepart: document.getElementById('dateDepart').value,
        dateArrivee: document.getElementById('dateArrivee').value
    };

    try {
        const url = id ? `${API_URL}/${id}` : API_URL;
        const method = id ? 'PUT' : 'POST';
        
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            modal.hide();
            loadVols();
            alert('Vol enregistré avec succès');
        } else {
            const error = await response.text();
            alert('Erreur: ' + error);
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de l\'enregistrement');
    }
}

async function deleteVol(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce vol ?')) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadVols();
            alert('Vol supprimé');
        }
    } catch (error) {
        console.error('Erreur:', error);
    }
}
