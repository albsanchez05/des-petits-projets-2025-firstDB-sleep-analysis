// --- ÉTAPE 1 : CAPTURER LES ÉLÉMENTS ---
// (On capture les nouveaux ID de 'sleep.html')

// Les panneaux
const statusPanel = document.getElementById('notifications');
const resultsPanel = document.getElementById('results-panel');

// Les boutons d'analyse
const btnGetAll = document.getElementById('btn-get-all');
const btnAvgOccupation = document.getElementById('btn-avg-occupation');
const btnAvgGender = document.getElementById('btn-avg-gender');

// Les listes déroulantes (SELECT) et leurs boutons
const selectOccupation = document.getElementById('select-occupation');
const btnQueryOccupation = document.getElementById('btn-query-occupation');
const selectDisorder = document.getElementById('select-disorder');
const btnQueryDisorder = document.getElementById('btn-query-disorder');


// --- ÉTAPE 2 : FONCTIONS HELPER (POUR RESTER PROPRE) ---

// Une fonction générique pour appeler l'API et afficher le résultat
// C'est mieux que de copier-coller le 'fetch' 5 fois !
async function callApi(url, statusMessage) {
    statusPanel.textContent = 'Loading... ' + statusMessage;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network error: ' + response.statusText);
        }
        const data = await response.json();

        // On affiche le JSON "joli-fié"
        renderDataAsTable(data);
        statusPanel.textContent = 'Success: ' + statusMessage;
    } catch (error) {
        console.error('API Error:', error);
        statusPanel.textContent = 'Error: ' + error.message;
        resultsPanel.textContent = 'Failed to fetch data.';
    }
}

// Une fonction pour remplir une liste déroulante (un <select>)
async function populateSelect(selectElement, apiUrl) {
    try {
        const response = await fetch(apiUrl);
        const options = await response.json(); // Ex: ["Doctor", "Engineer", ...]

        // On crée une <option> pour chaque item de la liste
        options.forEach(optionText => {
            const option = document.createElement('option');
            option.value = optionText;
            option.textContent = optionText;
            selectElement.appendChild(option);
        });
    } catch (error) {
        console.error('Failed to populate select:', error);
    }
}

// --- ÉTAPE 3 : "WIRING" (CÂBLAGE) ---

// Attacher les écouteurs de clics aux boutons d'analyse
btnGetAll.addEventListener('click', () => {
    callApi('/api/sleep', 'Loading all records...');
});

btnAvgOccupation.addEventListener('click', () => {
    callApi('/api/sleep/analysis/average-by-occupation', 'Analyzing average by occupation...');
});

btnAvgGender.addEventListener('click', () => {
    callApi('/api/sleep/analysis/average-by-gender', 'Analyzing average by gender...');
});

// Attacher les écouteurs de clics aux boutons de FILTRE
btnQueryOccupation.addEventListener('click', () => {
    // On lit la valeur SÉLECTIONNÉE dans la liste !
    const selectedOccupation = selectOccupation.value;
    if (selectedOccupation) { // On vérifie que ce n'est pas "-- Select --"
        callApi('/api/sleep/occupation/' + selectedOccupation, 'Querying for ' + selectedOccupation);
    } else {
        statusPanel.textContent = 'Please select an occupation first.';
    }
});

btnQueryDisorder.addEventListener('click', () => {
    // On lit la valeur SÉLECTIONNÉE dans la liste !
    const selectedDisorder = selectDisorder.value;
    if (selectedDisorder) {
        callApi('/api/sleep/sleepdisorder/' + selectedDisorder, 'Querying for ' + selectedDisorder);
    } else {
        statusPanel.textContent = 'Please select a disorder first.';
    }
});

// --- ÉTAPE 4 : INITIALISATION (TON IDÉE !) ---
// Ce code s'exécute dès que la page est chargée

function initializePage() {
    console.log('Page is loading... Populating dropdowns.');
    // On remplit les deux listes en appelant notre API
    populateSelect(selectOccupation, '/api/sleep/occupations');
    populateSelect(selectDisorder, '/api/sleep/sleepdisorders');
}

// Lance l'initialisation !
initializePage();

/**
 * Prend les données JSON et les "dessine" sous forme de tableau HTML
 * dans le panneau de résultats.
 */

function renderDataAsTable(data) {
    // 1. Vider les anciens résultats
    resultsPanel.innerHTML = '';

    // 2. Gérer le cas "pas de résultats"
    if (!data || (Array.isArray(data) && data.length === 0)) {
        resultsPanel.textContent = '--- No results found for this query. ---';
        return;
    }

    let tableHTML = '<table>';

    // 3. CAS A : C'est une LISTE (comme "Load All" ou "Query by Occupation")
    if (Array.isArray(data)) {
        const headers = Object.keys(data[0]);
        tableHTML += '<thead><tr>';
        headers.forEach(header => tableHTML += `<th>${header}</th>`);
        tableHTML += '</tr></thead>';

        tableHTML += '<tbody>';
        data.forEach(row => {
            tableHTML += '<tr>';
            headers.forEach(header => {
                // --- C'EST LA CORRECTION ---
                let value = row[header];
                // Si c'est un nombre, on l'arrondit à 2 décimales
                if (typeof value === 'number') {
                    value = value.toFixed(2);
                }
                tableHTML += `<td>${value}</td>`;
                // --- FIN CORRECTION ---
            });
            tableHTML += '</tr>';
        });
        tableHTML += '</tbody>';

    }
    // 4. CAS B : C'est un OBJET (une Map) (comme "Average by...")
    else {
        tableHTML += '<thead><tr><th>Category</th><th>Value</th></tr></thead>';
        tableHTML += '<tbody>';
        for (const key in data) {
            // --- C'EST LA CORRECTION ---
            let value = data[key];
            // Si c'est un nombre, on l'arrondit à 2 décimales
            if (typeof value === 'number') {
                value = value.toFixed(2);
            }
            tableHTML += `<tr><td>${key}</td><td>${value}</td></tr>`;
            // --- FIN CORRECTION ---
        }
        tableHTML += '</tbody>';
    }

    tableHTML += '</table>';
    resultsPanel.innerHTML = tableHTML;
}