// ==========================
// Variables globales
// ==========================
let users = [];
let currentUser = null;
let acercaData = "OlimpicTrained es una plataforma web que su principal idea o enfoque es plantear múltiples formas de aprendizaje de forma didáctica y eficaz que sea amigable y útil para los usuarios";
let ayudaData = "Aquí puedes proporcionar información de ayuda, preguntas frecuentes o instrucciones para tus usuarios.";

// ==========================
// Funciones generales
// ==========================

// Mostrar página específica
function showPage(pageName) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));
    document.getElementById(pageName).classList.add('active');
}

// Seleccionar juego (mensajes)
function selectGame(gameType) {
    let message = '';
    switch (gameType) {
        case 'matematica':
            message = '¡Has seleccionado Matemáticas! Presiona aceptar para continuar.';
            break;
        case 'lectura':
            message = '¡Has seleccionado Lectura! Presiona aceptar para continuar.';
            break;
        case 'sociales':
            message = '¡Has seleccionado Sociales! Los juegos de sociales estarán disponibles próximamente.';
            break;
    }
    alert(message);
}

// Mostrar mensaje de éxito temporal
function showSuccess(elementId) {
    const element = document.getElementById(elementId);
    element.style.display = 'block';
    setTimeout(() => {
        element.style.display = 'none';
    }, 3000);
}

// Actualizar la barra de usuario
function updateUserInfo() {
    const userInfo = document.getElementById('userInfo');
    if (currentUser) {
        userInfo.textContent = `Hola, ${currentUser.name}`;
        userInfo.style.display = 'block';
    } else {
        userInfo.style.display = 'none';
    }
}

// ==========================
// Eventos del Login
// ==========================
document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    const user = users.find(u => u.email === email && u.password === password);

    if (user) {
        currentUser = user;
        showSuccess('loginSuccess');
        updateUserInfo();
        this.reset();
        setTimeout(() => showPage('inicio'), 1500);
    } else {
        alert('Credenciales incorrectas. Por favor, verifica tu email y contraseña.');
    }
});

// ==========================
// Eventos del Registro
// ==========================
document.getElementById('registerForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const confirmPassword = document.getElementById('registerConfirmPassword').value;

    if (password !== confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
    }

    if (users.find(u => u.email === email)) {
        alert('Este correo ya está registrado');
        return;
    }

    users.push({ name, email, password });
    showSuccess('registerSuccess');
    this.reset();
    setTimeout(() => showPage('login'), 1500);
});

// ==========================
// Editar contenido "Ayuda"
// ==========================
document.getElementById('ayudaForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const text = document.getElementById('ayudaText').value;
    if (text.trim()) {
        ayudaData = text;
        document.getElementById('ayudaContent').innerHTML = `<p>${text}</p>`;
        alert('Contenido guardado exitosamente');
        this.reset();
    }
});

// ==========================
// Carga inicial del contenido
// ==========================
document.getElementById('acercaContent').innerHTML = `<p>${acercaData}</p>`;
document.getElementById('ayudaContent').innerHTML = `<p>${ayudaData}</p>`;
