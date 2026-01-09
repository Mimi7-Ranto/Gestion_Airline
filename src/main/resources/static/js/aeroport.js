const API_URL = '/api/aeroports';
let modal;

document.addEventListener('DOMContentLoaded', () => {
    const modalElement = document.getElementById('aeroportModal');
    if (modalElement) {
        modal = new bootstrap.Modal(modalElement);
    }
    loadAeroports();
});

async function loadAeroports() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Erreur chargement');
        const aeroports = await response.json();
        displayAeroports(aeroports);
    } catch (error) {
        console.error(error);
        alert('Erreur lors du chargement des aéroports');
    }
}

function displayAeroports(aeroports) {
    const tbody = document.querySelector('#aeroportsTable tbody');
    tbody.innerHTML = '';

    aeroports.forEach(aeroport => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${aeroport.codeIata}</strong></td>
            <td>${aeroport.codeIcao}</td>
            <td>${aeroport.nomAeroport}</td>
            <td>${aeroport.ville}</td>
            <td>${aeroport.pays}</td>
            <td>${aeroport.fuseauHoraire}</td>
            <td>
                <button class="btn btn-sm btn-warning"
                    onclick='editAeroport(${JSON.stringify(aeroport)})'>
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-danger"
                    onclick="deleteAeroport('${aeroport.idAeroport}')">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function resetForm() {
    document.getElementById('aeroportForm').reset();
    document.getElementById('aeroportId').value = '';
    document.getElementById('modalTitle').textContent = 'Nouvel Aéroport';
}

function editAeroport(aeroport) {
    document.getElementById('aeroportId').value = aeroport.idAeroport;
    document.getElementById('codeIata').value = aeroport.codeIata;
    document.getElementById('codeIcao').value = aeroport.codeIcao;
    document.getElementById('nomAeroport').value = aeroport.nomAeroport;
    document.getElementById('ville').value = aeroport.ville;
    document.getElementById('pays').value = aeroport.pays;
    document.getElementById('fuseauHoraire').value = aeroport.fuseauHoraire;
    document.getElementById('modalTitle').textContent = 'Modifier Aéroport';
    modal.show();
}

async function saveAeroport() {
    const id = document.getElementById('aeroportId').value;

    const data = {
        codeIata: document.getElementById('codeIata').value,
        codeIcao: document.getElementById('codeIcao').value,
        nomAeroport: document.getElementById('nomAeroport').value,
        ville: document.getElementById('ville').value,
        pays: document.getElementById('pays').value,
        fuseauHoraire: document.getElementById('fuseauHoraire').value
    };

    try {
        const url = id ? `${API_URL}/${id}` : API_URL;
        const method = id ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            modal.hide();
            loadAeroports();
            alert('Aéroport enregistré avec succès');
        } else {
            alert('Erreur lors de l’enregistrement');
        }
    } catch (error) {
        console.error(error);
        alert('Erreur lors de l’enregistrement');
    }
}

async function deleteAeroport(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cet aéroport ?')) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            loadAeroports();
            alert('Aéroport supprimé');
        } else {
            alert('Erreur lors de la suppression');
        }
    } catch (error) {
        console.error(error);
        alert('Erreur lors de la suppression');
    }
}
