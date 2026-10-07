const API_URL = 'http://localhost:8080/api';

// Load transactions - DIRECT AND SIMPLE
async function loadTransactions() {
    console.log('🔄 Loading transactions...');
    
    try {
        const response = await fetch(`${API_URL}/transactions/all`);
        const transactions = await response.json();
        
        console.log('✅ Received', transactions.length, 'transactions');
        
        const tbody = document.getElementById('transactionHistory');
        tbody.innerHTML = '';
        
        if (transactions.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No transactions found</td></tr>';
            document.getElementById('myTransactions').innerText = '0';
            document.getElementById('alerts').innerText = '0';
            return;
        }
        
        transactions.forEach(t => {
            const status = t.status || 'PENDING';
            const statusClass = status.toLowerCase();
            const row = `
                <tr>
                    <td>${t.transactionId || t.id}</td>
                    <td>₹${t.amount || 0}</td>
                    <td>${t.receiverId || 'N/A'}</td>
                    <td>${t.senderLocation || 'N/A'}</td>
                    <td><span class="badge badge-${statusClass}">${status}</span></td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
        
        document.getElementById('myTransactions').innerText = transactions.length;
        const alertedCount = transactions.filter(t => t.status === 'FLAGGED' || t.status === 'BLOCKED').length;
        document.getElementById('alerts').innerText = alertedCount;
        
    } catch (error) {
        console.error('❌ Error:', error);
        document.getElementById('transactionHistory').innerHTML = '<tr><td colspan="5" style="text-align: center; color: red;">Error loading transactions</td></tr>';
    }
}

// Add transaction
document.getElementById('transactionForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = localStorage.getItem('username');
    if (!username) {
        alert('Please login first!');
        window.location.href = 'login.html';
        return;
    }
    
    const transaction = {
        amount: parseFloat(document.getElementById('amount').value),
        senderId: username,
        receiverId: document.getElementById('merchant').value,
        senderLocation: document.getElementById('location').value,
        receiverLocation: document.getElementById('location').value,
        paymentMethod: "ONLINE",
        currency: "INR"
    };
    
    try {
        const response = await fetch(`${API_URL}/transactions/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(transaction)
        });
        
        const result = await response.json();
        alert(`✅ Transaction Added!\nStatus: ${result.status}\nRisk: ${result.riskLevel || 'N/A'}`);
        
        document.getElementById('transactionForm').reset();
        loadTransactions(); // Refresh
        
    } catch (error) {
        alert('❌ Error adding transaction!');
    }
});

// Set username
const username = localStorage.getItem('username');
if (username) {
    document.getElementById('userName').innerHTML = `Welcome, ${username}! 👋`;
}

// Load transactions when page loads
loadTransactions();