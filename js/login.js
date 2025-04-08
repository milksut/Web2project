document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const togglePassword = document.querySelector('.toggle-password');
    const rememberMe = document.getElementById('remember');

    togglePassword.addEventListener('click', () => {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        togglePassword.classList.toggle('fa-eye');
        togglePassword.classList.toggle('fa-eye-slash');
    });

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        const loginButton = loginForm.querySelector('.login-button');
        const originalText = loginButton.textContent;
        loginButton.disabled = true;
        loginButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging in...';

        try {
            await simulateLogin({
                email: emailInput.value,
                password: passwordInput.value,
                remember: rememberMe.checked
            });

            window.location.href = 'main.html';
        } catch (error) {
            showError(error.message);
            loginButton.disabled = false;
            loginButton.textContent = originalText;
        }
    });

    document.querySelector('.google').addEventListener('click', () => {
        console.log('Google login clicked');
    });

    document.querySelector('.facebook').addEventListener('click', () => {
        console.log('Facebook login clicked');
    });
});

function validateForm() {
    const email = emailInput.value.trim();
    const password = passwordInput.value;
    let isValid = true;

    removeError(emailInput);
    removeError(passwordInput);

    if (!email) {
        showError('Email is required', emailInput);
        isValid = false;
    } else if (!isValidEmail(email)) {
        showError('Please enter a valid email address', emailInput);
        isValid = false;
    }

    if (!password) {
        showError('Password is required', passwordInput);
        isValid = false;
    } else if (password.length < 6) {
        showError('Password must be at least 6 characters', passwordInput);
        isValid = false;
    }

    return isValid;
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function showError(message, inputElement = null) {
    if (inputElement) {
        const formGroup = inputElement.closest('.form-group');
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.style.color = 'var(--error)';
        errorDiv.style.fontSize = '0.875rem';
        errorDiv.style.marginTop = '0.25rem';
        errorDiv.textContent = message;
        
        removeError(inputElement);
        formGroup.appendChild(errorDiv);
        inputElement.style.borderColor = 'var(--error)';
    } else {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message general';
        errorDiv.style.color = 'var(--error)';
        errorDiv.style.textAlign = 'center';
        errorDiv.style.marginBottom = '1rem';
        errorDiv.textContent = message;
        
        const form = document.querySelector('.login-form');
        const existingError = form.querySelector('.error-message.general');
        if (existingError) {
            existingError.remove();
        }
        form.insertBefore(errorDiv, form.firstChild);
    }
}

function removeError(inputElement) {
    const formGroup = inputElement.closest('.form-group');
    const errorMessage = formGroup.querySelector('.error-message');
    if (errorMessage) {
        errorMessage.remove();
    }
    inputElement.style.borderColor = '';
}

function simulateLogin(credentials) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (credentials.email === 'demo@example.com' && credentials.password === 'password123') {
                resolve({ success: true });
            } else {
                reject(new Error('Invalid email or password'));
            }
        }, 1500);
    });
}