// API URL
const API_URL = 'http://localhost:8080/api';

// REGISTER
async function registerUser() {
    const data = {
        fullName: document.getElementById("fullname").value,
        username: document.getElementById("username").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    console.log("Register Data:", data);

    try {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        console.log("Register Response:", result);

        if (response.ok && result.status === "success") {
            alert("Registration Successful! Please login.");
            window.location.href = "login.html";
        } else {
            alert(result.message || "Registration failed!");
        }
    } catch (error) {
        console.error("Registration error:", error);
        alert("Error connecting to server. Make sure backend is running on port 8080");
    }
}

// LOGIN
async function loginUser() {
    const data = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    console.log("Login Data:", data);

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();
        console.log("Login Response:", result);

        if (response.ok && result.status === "success") {
            // Save user data
            localStorage.setItem("isLoggedIn", "true");
            localStorage.setItem("username", data.username);
            localStorage.setItem("token", result.token || "dummy-token");
            localStorage.setItem("role", result.role || "USER");
            
            alert("Login Successful!");
            
            // Redirect based on role
            if (result.role === "ADMIN") {
                window.location.href = "admin.html";
            } else {
                window.location.href = "dashboard.html";
            }
        } else {
            alert(result.message || "Invalid credentials!");
        }
    } catch (error) {
        console.error("Login error:", error);
        alert("Error connecting to server. Make sure backend is running on port 8080");
    }
}

// LOGOUT
function logout() {
    localStorage.clear();
    alert("Logged out successfully!");
    window.location.href = "index.html";
}

// Check if user is logged in
function checkAuth() {
    const isLoggedIn = localStorage.getItem("isLoggedIn");
    const currentPage = window.location.pathname;
    
    // Protected pages that require login
    const protectedPages = ["dashboard.html", "admin.html"];
    const isProtectedPage = protectedPages.some(page => currentPage.includes(page));
    
    if (isProtectedPage && !isLoggedIn) {
        alert("Please login first!");
        window.location.href = "login.html";
        return false;
    }
    return true;
}

// Run auth check on page load
if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", checkAuth);
} else {
    checkAuth();
}