const API_URL = 'http://localhost:8080/api';

// Load admin dashboard
async function loadAdminDashboard() {
    const adminName = localStorage.getItem('username');
    if (adminName) {
        document.getElementById('adminName').innerHTML = `Welcome, ${adminName}! 👋`;
    }
    await loadStats();
    await loadAllTransactions();
}

// Load stats
async function loadStats() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/transactions/stats`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (!response.ok) {
            throw new Error('Failed to load stats');
        }
        
        const stats = await response.json();
        
        document.getElementById('totalTrans').innerText = stats.total || 0;
        document.getElementById('totalUsers').innerText = stats.totalUsers || 0;
        document.getElementById('blockedCount').innerText = stats.blocked || 0;
        document.getElementById('approvedCount').innerText = stats.approved || 0;
    } catch (error) {
        console.error('Error loading stats:', error);
        document.getElementById('totalTrans').innerText = 'Error';
    }
}

// Load all transactions
async function loadAllTransactions() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/admin/all`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (!response.ok) {
            throw new Error('Failed to load transactions');
        }
        
        const transactions = await response.json();
        displayTransactions(transactions);
    } catch (error) {
        console.error('Error loading transactions:', error);
        document.getElementById('transactionTable').innerHTML = '<tr><td colspan="8" style="text-align: center; color: red;">Error loading transactions</td></tr>';
    }
}

// Display transactions
function displayTransactions(transactions) {
    const tbody = document.getElementById('transactionTable');
    tbody.innerHTML = '';
    
    if (transactions.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">No transactions found</td></tr>';
        return;
    }
    
    transactions.forEach(t => {
        const row = `
            <tr>
                <td>${t.transactionId || t.id || 'N/A'}</td>
                <td>${t.userName || t.user || 'Unknown'}</td>
                <td>₹${t.amount || 0}</td>
                <td>${t.merchant || 'N/A'}</td>
                <td>${t.location || 'N/A'}</td>
                <td>${t.riskScore ? t.riskScore.toFixed(2) : 'N/A'}</td>
                <td><span class="badge badge-${(t.status || 'PENDING').toLowerCase()}">${t.status || 'PENDING'}</span></td>
                <td>
                    ${t.status !== 'APPROVED' ? `<button class="btn btn-success" style="padding: 0.25rem 0.5rem; margin-right: 0.25rem;" onclick="reviewTransaction(${t.id}, 'APPROVE')">Approve</button>` : ''}
                    ${t.status !== 'BLOCKED' ? `<button class="btn btn-danger" style="padding: 0.25rem 0.5rem;" onclick="reviewTransaction(${t.id}, 'BLOCK')">Block</button>` : ''}
                </td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

// Review transaction
async function reviewTransaction(id, action) {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/admin/review/${id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ action: action.toLowerCase() })
        });
        
        if (!response.ok) {
            throw new Error('Failed to review transaction');
        }
        
        const result = await response.json();
        alert(`Transaction ${action}D successfully!`);
        await loadAdminDashboard();
    } catch (error) {
        console.error('Error reviewing transaction:', error);
        alert('Error updating transaction!');
    }
}

// Filter transactions
async function filterTransactions(status) {
    if (status === 'ALL') {
        await loadAllTransactions();
    } else {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch(`${API_URL}/transactions/status/${status}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            
            if (!response.ok) {
                throw new Error('Failed to filter transactions');
            }
            
            const transactions = await response.json();
            displayTransactions(transactions);
        } catch (error) {
            console.error('Error filtering:', error);
            alert('Error filtering transactions!');
        }
    }
}

// Initialize
if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", loadAdminDashboard);
} else {
    loadAdminDashboard();
}