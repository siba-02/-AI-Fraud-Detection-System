const CHATBOT_API = 'http://localhost:8080/api/chatbot';

let isChatOpen = false;

function toggleChat() {
    const chatBody = document.getElementById('chatBody');
    isChatOpen = !isChatOpen;
    chatBody.style.display = isChatOpen ? 'block' : 'none';
    
    // Scroll to bottom when opened
    if (isChatOpen) {
        setTimeout(() => {
            chatBody.scrollTop = chatBody.scrollHeight;
        }, 100);
    }
}

async function sendMessage() {
    const input = document.getElementById('chatInput');
    const question = input.value.trim();
    
    if (!question) return;
    
    const chatBody = document.getElementById('chatBody');
    
    // Add user message
    const userMessage = document.createElement('div');
    userMessage.className = 'chat-message user-message';
    userMessage.innerText = question;
    chatBody.insertBefore(userMessage, chatBody.lastElementChild);
    
    input.value = '';
    
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${CHATBOT_API}/ask`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ question: question })
        });
        
        let answer = "I'm here to help with fraud detection questions!";
        
        if (response.ok) {
            const data = await response.json();
            answer = data.answer || data.message || answer;
        } else {
            // Fallback responses if backend chatbot is not available
            const lowerQuestion = question.toLowerCase();
            if (lowerQuestion.includes('fraud')) {
                answer = "Fraud detection uses AI to identify suspicious transactions based on patterns, amounts, locations, and behavior analysis.";
            } else if (lowerQuestion.includes('transaction')) {
                answer = "Transactions are analyzed in real-time for risk scoring. High-risk transactions are flagged or blocked automatically.";
            } else if (lowerQuestion.includes('risk')) {
                answer = "Risk scores range from 0-100. Higher scores indicate higher fraud probability. Scores above 70 are flagged for review.";
            } else {
                answer = "I can help you with questions about fraud detection, transactions, risk scores, and security measures. What would you like to know?";
            }
        }
        
        // Add bot response
        const botMessage = document.createElement('div');
        botMessage.className = 'chat-message bot-message';
        botMessage.innerText = answer;
        chatBody.insertBefore(botMessage, chatBody.lastElementChild);
        
        // Scroll to bottom
        chatBody.scrollTop = chatBody.scrollHeight;
    } catch (error) {
        console.error('Chatbot error:', error);
        
        // Add error message
        const errorMessage = document.createElement('div');
        errorMessage.className = 'chat-message bot-message';
        errorMessage.innerText = "Sorry, I'm having trouble connecting. Please make sure the backend server is running.";
        chatBody.insertBefore(errorMessage, chatBody.lastElementChild);
    }
}

// Allow enter key
const chatInput = document.getElementById('chatInput');
if (chatInput) {
    chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });
}