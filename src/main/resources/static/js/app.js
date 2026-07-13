// API Configuration
const API_BASE = 'http://localhost:8080/api';

// Initialize on page load
window.addEventListener('load', () => {
    loadDashboardData();
});

// Show Dashboard
function showDashboard() {
    hideAllSections();
    document.getElementById('dashboard').style.display = 'block';
    loadDashboardData();
}

// Show Vehicles
function showVehicles() {
    hideAllSections();
    document.getElementById('vehicles').style.display = 'block';
    loadVehicles();
}

// Show Signals
function showSignals() {
    hideAllSections();
    document.getElementById('signals').style.display = 'block';
    loadSignals();
}

// Show Junctions
function showJunctions() {
    hideAllSections();
    document.getElementById('junctions').style.display = 'block';
    loadJunctions();
}

// Show Alerts
function showAlerts() {
    hideAllSections();
    document.getElementById('alerts').style.display = 'block';
    loadAlerts();
}

// Hide all sections
function hideAllSections() {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.style.display = 'none');
}

// Load Dashboard Data
async function loadDashboardData() {
    try {
        // Load active vehicles count
        const vehiclesRes = await fetch(`${API_BASE}/vehicles/count/active`);
        if (vehiclesRes.ok) {
            const vehiclesData = await vehiclesRes.json();
            document.getElementById('activeVehicles').textContent = vehiclesData.data.count || 0;
        }

        // Load total signals
        const signalsRes = await fetch(`${API_BASE}/signals`);
        if (signalsRes.ok) {
            const signalsData = await signalsRes.json();
            document.getElementById('totalSignals').textContent = signalsData.data.length || 0;
        }

        // Load total junctions
        const junctionsRes = await fetch(`${API_BASE}/junctions`);
        if (junctionsRes.ok) {
            const junctionsData = await junctionsRes.json();
            document.getElementById('totalJunctions').textContent = junctionsData.data.length || 0;
        }

        // Load active alerts
        const alertsRes = await fetch(`${API_BASE}/alerts/active/list`);
        if (alertsRes.ok) {
            const alertsData = await alertsRes.json();
            document.getElementById('activeAlerts').textContent = alertsData.data.length || 0;
            displayLatestAlerts(alertsData.data);
        }
    } catch (error) {
        console.error('Error loading dashboard data:', error);
    }
}

// Display latest alerts
function displayLatestAlerts(alerts) {
    const alertsList = document.getElementById('alertsList');
    if (alerts.length === 0) {
        alertsList.innerHTML = '<p>No alerts</p>';
        return;
    }
    
    alertsList.innerHTML = alerts.slice(0, 5).map(alert => `
        <div class="item-card">
            <div class="item-header">
                <div class="item-title">${alert.alertType}</div>
                <span class="item-status status-${alert.severity.toLowerCase()}">${alert.severity}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Message:</span>
                <span class="detail-value">${alert.message}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Location:</span>
                <span class="detail-value">${alert.location}</span>
            </div>
            <div class="detail-row">
                <span class="detail-label">Status:</span>
                <span class="detail-value">${alert.status}</span>
            </div>
        </div>
    `).join('');
}

// Load Vehicles
async function loadVehicles() {
    try {
        const response = await fetch(`${API_BASE}/vehicles`);
        if (!response.ok) throw new Error('Failed to fetch vehicles');
        const data = await response.json();
        displayVehicles(data.data);
    } catch (error) {
        console.error('Error loading vehicles:', error);
        document.getElementById('vehiclesList').innerHTML = '<p>Error loading vehicles</p>';
    }
}

// Display Vehicles
function displayVehicles(vehicles) {
    const list = document.getElementById('vehiclesList');
    if (vehicles.length === 0) {
        list.innerHTML = '<p class="empty-state">No vehicles found</p>';
        return;
    }

    list.innerHTML = vehicles.map(vehicle => `
        <div class="item-card">
            <div class="item-header">
                <div class="item-title">${vehicle.vehicleNumber}</div>
                <span class="item-status status-active">${vehicle.vehicleType}</span>
            </div>
            <div class="item-details">
                <div class="detail-row"><span class="detail-label">Owner:</span><span class="detail-value">${vehicle.ownerName}</span></div>
                <div class="detail-row"><span class="detail-label">Location:</span><span class="detail-value">${vehicle.currentLocation}</span></div>
                <div class="detail-row"><span class="detail-label">Speed:</span><span class="detail-value">${vehicle.speed} km/h</span></div>
                <div class="detail-row"><span class="detail-label">Entry Time:</span><span class="detail-value">${new Date(vehicle.entryTime).toLocaleString()}</span></div>
            </div>
            <div class="item-actions">
                <button class="btn btn-secondary" onclick="deleteVehicle(${vehicle.id})">Delete</button>
                <button class="btn btn-danger" onclick="exitVehicle(${vehicle.id})">Exit</button>
            </div>
        </div>
    `).join('');
}

// Load Signals
async function loadSignals() {
    try {
        const response = await fetch(`${API_BASE}/signals`);
        if (!response.ok) throw new Error('Failed to fetch signals');
        const data = await response.json();
        displaySignals(data.data);
    } catch (error) {
        console.error('Error loading signals:', error);
        document.getElementById('signalsList').innerHTML = '<p>Error loading signals</p>';
    }
}

// Display Signals
function displaySignals(signals) {
    const list = document.getElementById('signalsList');
    if (signals.length === 0) {
        list.innerHTML = '<p class="empty-state">No signals found</p>';
        return;
    }

    list.innerHTML = signals.map(signal => `
        <div class="item-card">
            <div class="item-header">
                <div class="item-title">${signal.signalName}</div>
                <span class="item-status status-${signal.status.toLowerCase()}">${signal.status}</span>
            </div>
            <div class="item-details">
                <div class="detail-row"><span class="detail-label">Location:</span><span class="detail-value">${signal.location}</span></div>
                <div class="detail-row"><span class="detail-label">Timer:</span><span class="detail-value">${signal.timer}s</span></div>
                <div class="detail-row"><span class="detail-label">Green:</span><span class="detail-value">${signal.greenDuration}s</span></div>
                <div class="detail-row"><span class="detail-label">Red:</span><span class="detail-value">${signal.redDuration}s</span></div>
            </div>
            <div class="item-actions">
                <button class="btn btn-secondary" onclick="deleteSignal(${signal.id})">Delete</button>
            </div>
        </div>
    `).join('');
}

// Load Junctions
async function loadJunctions() {
    try {
        const response = await fetch(`${API_BASE}/junctions`);
        if (!response.ok) throw new Error('Failed to fetch junctions');
        const data = await response.json();
        displayJunctions(data.data);
    } catch (error) {
        console.error('Error loading junctions:', error);
        document.getElementById('junctionsList').innerHTML = '<p>Error loading junctions</p>';
    }
}

// Display Junctions
function displayJunctions(junctions) {
    const list = document.getElementById('junctionsList');
    if (junctions.length === 0) {
        list.innerHTML = '<p class="empty-state">No junctions found</p>';
        return;
    }

    list.innerHTML = junctions.map(junction => {
        let statusClass = 'active';
        if (junction.status === 'CONGESTED') statusClass = 'warning';
        if (junction.status === 'HEAVY_TRAFFIC') statusClass = 'alert';
        
        return `
            <div class="item-card">
                <div class="item-header">
                    <div class="item-title">${junction.junctionName}</div>
                    <span class="item-status status-${statusClass}">${junction.status}</span>
                </div>
                <div class="item-details">
                    <div class="detail-row"><span class="detail-label">Location:</span><span class="detail-value">${junction.location}</span></div>
                    <div class="detail-row"><span class="detail-label">Signals:</span><span class="detail-value">${junction.numberOfSignals}</span></div>
                    <div class="detail-row"><span class="detail-label">Density:</span><span class="detail-value">${junction.trafficDensity}%</span></div>
                    <div class="detail-row"><span class="detail-label">Vehicles:</span><span class="detail-value">${junction.vehicleCount}/${junction.capacity}</span></div>
                </div>
                <div class="item-actions">
                    <button class="btn btn-secondary" onclick="deleteJunction(${junction.id})">Delete</button>
                </div>
            </div>
        `;
    }).join('');
}

// Load Alerts
async function loadAlerts() {
    try {
        const response = await fetch(`${API_BASE}/alerts`);
        if (!response.ok) throw new Error('Failed to fetch alerts');
        const data = await response.json();
        displayAlerts(data.data);
    } catch (error) {
        console.error('Error loading alerts:', error);
        document.getElementById('alertsList').innerHTML = '<p>Error loading alerts</p>';
    }
}

// Display Alerts
function displayAlerts(alerts) {
    const list = document.getElementById('alertsList');
    if (alerts.length === 0) {
        list.innerHTML = '<p class="empty-state">No alerts found</p>';
        return;
    }

    list.innerHTML = alerts.map(alert => `
        <div class="item-card">
            <div class="item-header">
                <div class="item-title">${alert.alertType}</div>
                <span class="item-status status-${alert.severity.toLowerCase()}">${alert.severity}</span>
            </div>
            <div class="item-details">
                <div class="detail-row"><span class="detail-label">Message:</span><span class="detail-value">${alert.message}</span></div>
                <div class="detail-row"><span class="detail-label">Location:</span><span class="detail-value">${alert.location}</span></div>
                <div class="detail-row"><span class="detail-label">Status:</span><span class="detail-value">${alert.status}</span></div>
                <div class="detail-row"><span class="detail-label">Created:</span><span class="detail-value">${new Date(alert.createdTime).toLocaleString()}</span></div>
            </div>
            <div class="item-actions">
                <button class="btn btn-secondary" onclick="deleteAlert(${alert.id})">Delete</button>
            </div>
        </div>
    `).join('');
}

// Show/Hide Forms
function showVehicleForm() {
    document.getElementById('vehicleForm').style.display = 'block';
}

function hideVehicleForm() {
    document.getElementById('vehicleForm').style.display = 'none';
}

function showSignalForm() {
    document.getElementById('signalForm').style.display = 'block';
}

function hideSignalForm() {
    document.getElementById('signalForm').style.display = 'none';
}

function showJunctionForm() {
    document.getElementById('junctionForm').style.display = 'block';
}

function hideJunctionForm() {
    document.getElementById('junctionForm').style.display = 'none';
}

function showAlertForm() {
    document.getElementById('alertForm').style.display = 'block';
}

function hideAlertForm() {
    document.getElementById('alertForm').style.display = 'none';
}

// Create Vehicle
async function createVehicle(event) {
    event.preventDefault();
    const vehicle = {
        vehicleNumber: document.getElementById('vehicleNumber').value,
        vehicleType: document.getElementById('vehicleType').value,
        ownerName: document.getElementById('ownerName').value,
        currentLocation: document.getElementById('currentLocation').value,
        speed: parseFloat(document.getElementById('speed').value) || 0
    };

    try {
        const response = await fetch(`${API_BASE}/vehicles`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(vehicle)
        });
        if (response.ok) {
            alert('Vehicle created successfully!');
            document.getElementById('vehicleForm').reset();
            hideVehicleForm();
            loadVehicles();
        }
    } catch (error) {
        console.error('Error creating vehicle:', error);
        alert('Error creating vehicle');
    }
}

// Create Signal
async function createSignal(event) {
    event.preventDefault();
    const signal = {
        signalName: document.getElementById('signalName').value,
        location: document.getElementById('signalLocation').value,
        status: document.getElementById('signalStatus').value
    };

    try {
        const response = await fetch(`${API_BASE}/signals`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(signal)
        });
        if (response.ok) {
            alert('Signal created successfully!');
            document.getElementById('signalForm').reset();
            hideSignalForm();
            loadSignals();
        }
    } catch (error) {
        console.error('Error creating signal:', error);
        alert('Error creating signal');
    }
}

// Create Junction
async function createJunction(event) {
    event.preventDefault();
    const junction = {
        junctionName: document.getElementById('junctionName').value,
        location: document.getElementById('junctionLocation').value,
        numberOfSignals: parseInt(document.getElementById('numberOfSignals').value),
        trafficDensity: parseInt(document.getElementById('trafficDensity').value),
        status: 'NORMAL',
        vehicleCount: 0,
        capacity: 100
    };

    try {
        const response = await fetch(`${API_BASE}/junctions`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(junction)
        });
        if (response.ok) {
            alert('Junction created successfully!');
            document.getElementById('junctionForm').reset();
            hideJunctionForm();
            loadJunctions();
        }
    } catch (error) {
        console.error('Error creating junction:', error);
        alert('Error creating junction');
    }
}

// Create Alert
async function createAlert(event) {
    event.preventDefault();
    const alert_obj = {
        alertType: document.getElementById('alertType').value,
        location: document.getElementById('alertLocation').value,
        message: document.getElementById('alertMessage').value,
        severity: document.getElementById('alertSeverity').value,
        status: 'ACTIVE'
    };

    try {
        const response = await fetch(`${API_BASE}/alerts`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(alert_obj)
        });
        if (response.ok) {
            alert('Alert created successfully!');
            document.getElementById('alertForm').reset();
            hideAlertForm();
            loadAlerts();
        }
    } catch (error) {
        console.error('Error creating alert:', error);
        alert('Error creating alert');
    }
}

// Delete Vehicle
async function deleteVehicle(id) {
    if (!confirm('Are you sure you want to delete this vehicle?')) return;
    try {
        const response = await fetch(`${API_BASE}/vehicles/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Vehicle deleted successfully!');
            loadVehicles();
        }
    } catch (error) {
        console.error('Error deleting vehicle:', error);
    }
}

// Exit Vehicle
async function exitVehicle(id) {
    try {
        const response = await fetch(`${API_BASE}/vehicles/${id}/exit`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        });
        if (response.ok) {
            alert('Vehicle exited successfully!');
            loadVehicles();
        }
    } catch (error) {
        console.error('Error exiting vehicle:', error);
    }
}

// Delete Signal
async function deleteSignal(id) {
    if (!confirm('Are you sure you want to delete this signal?')) return;
    try {
        const response = await fetch(`${API_BASE}/signals/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Signal deleted successfully!');
            loadSignals();
        }
    } catch (error) {
        console.error('Error deleting signal:', error);
    }
}

// Delete Junction
async function deleteJunction(id) {
    if (!confirm('Are you sure you want to delete this junction?')) return;
    try {
        const response = await fetch(`${API_BASE}/junctions/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Junction deleted successfully!');
            loadJunctions();
        }
    } catch (error) {
        console.error('Error deleting junction:', error);
    }
}

// Delete Alert
async function deleteAlert(id) {
    if (!confirm('Are you sure you want to delete this alert?')) return;
    try {
        const response = await fetch(`${API_BASE}/alerts/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Alert deleted successfully!');
            loadAlerts();
        }
    } catch (error) {
        console.error('Error deleting alert:', error);
    }
}
