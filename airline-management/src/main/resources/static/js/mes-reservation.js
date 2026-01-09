document.addEventListener('DOMContentLoaded', loadMyReservations);

async function loadMyReservations() {
    try {
        const res = await fetch('/api/users/me/reservations');
        if (!res.ok) throw new Error('Erreur chargement');

        const list = await res.json();
        const tbody = document.querySelector('#reservationsTable tbody');
        const msg = document.getElementById('msg');

        tbody.innerHTML = '';
        if (msg) msg.textContent = '';

        if (!list || list.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7">Aucune réservation trouvée.</td></tr>';
            return;
        }

        list.forEach(r => {
            const tr = document.createElement('tr');
            const date = r.dateReservation
                ? new Date(r.dateReservation).toLocaleString()
                : '';

            tr.innerHTML = `
                <td>${r.id}</td>
                <td>${r.volId || ''}</td>
                <td>${date}</td>
                <td>${r.nombrePassagers || ''}</td>
                <td>${r.montantTotal ?? ''}</td>
                <td>${r.statut || ''}</td>
                <td>
                    <button class="btn btn-sm btn-info" onclick="viewDetails('${r.id}')">
                        Détails
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="cancelReservation('${r.id}')">
                        Annuler
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });

    } catch (e) {
        console.error(e);
        const msg = document.getElementById('msg');
        if (msg) msg.textContent = 'Impossible de charger vos réservations.';
    }
}

async function viewDetails(id) {
    try {
        const res = await fetch('/api/reservations/' + id);
        if (!res.ok) {
            alert('Impossible de charger les détails');
            return;
        }

        const r = await res.json();
        alert(
            `Référence: ${r.id}
Vol: ${r.volId || ''}
Passagers: ${r.nombrePassagers || ''}
Montant: ${r.montantTotal || ''}
Statut: ${r.statut || ''}`
        );
    } catch (e) {
        console.error(e);
        alert('Erreur lors du chargement des détails');
    }
}

async function cancelReservation(id) {
    if (!confirm('Confirmer l\'annulation de la réservation ?')) return;

    try {
        const res = await fetch(`/api/reservations/${id}/cancel`, {
            method: 'PATCH'
        });

        if (res.ok) {
            alert('Réservation annulée');
            loadMyReservations();
        } else {
            const txt = await res.text();
            alert('Erreur annulation: ' + txt);
        }
    } catch (e) {
        console.error(e);
        alert('Erreur lors de l\'annulation');
    }
}
