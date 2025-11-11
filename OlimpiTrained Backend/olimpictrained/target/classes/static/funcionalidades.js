// ============================================
// FUNCIONES PARA CONECTAR CON BACKEND
// OlimpicTrained - API Integration
// ============================================

// URL base del backend (ajusta según tu configuración)
const API_BASE_URL = 'http://localhost:8080/index.html';

// ============================================
// GESTIÓN DE USUARIOS
// ============================================

// Cargar todos los usuarios (para administración)
function cargarUsuarios() {
    fetch(`${API_BASE_URL}/api/usuarios`)
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar usuarios');
            return response.json();
        })
        .then(usuarios => {
            console.log('Usuarios cargados:', usuarios);
            // Aquí puedes actualizar la interfaz si necesitas mostrar usuarios
            return usuarios;
        })
        .catch(error => console.error('Error al obtener usuarios:', error));
}

// Registro de usuario
document.getElementById('registerForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const confirmPassword = document.getElementById('registerConfirmPassword').value;
    
    // Validación de contraseñas
    if (password !== confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
    }
    
    const nuevoUsuario = {
        nombre: name,
        correo: email,
        password: password
    };
    
    // Enviar datos al backend
    fetch(`${API_BASE_URL}/usuarios`, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(nuevoUsuario)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || 'Error al registrar usuario');
            });
        }
        return response.json();
    })
    .then(usuario => {
        showSuccess('registerSuccess');
        this.reset();
        setTimeout(() => showPage('login'), 1500);
    })
    .catch(error => {
        console.error('Error en registro:', error);
        alert('Error al registrar: ' + error.message);
    });
});

// Login de usuario
document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    
    // Login mediante query parameters
    fetch(`${API_BASE_URL}/usuarios/login?correo=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                alert("Credenciales incorrectas");
                throw new Error("Error de autenticación");
            }
        })
        .then(usuario => {
            // Guardar usuario en sesión
            currentUser = usuario;
            
            // Opcional: guardar token si el backend lo proporciona
            if (usuario.token) {
                sessionStorage.setItem('authToken', usuario.token);
            }
            
            showSuccess('loginSuccess');
            updateUserInfo();
            this.reset();
            setTimeout(() => showPage('inicio'), 1500);
        })
        .catch(error => {
            console.error('Error en login:', error);
        });
});

// Actualizar usuario
function actualizarUsuario(idUsuario, datosActualizados) {
    fetch(`${API_BASE_URL}/usuarios/${idUsuario}`, {
        method: 'PUT',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('authToken') || ''}`
        },
        body: JSON.stringify(datosActualizados)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al actualizar usuario');
        return response.json();
    })
    .then(usuarioActualizado => {
        console.log('Usuario actualizado:', usuarioActualizado);
        currentUser = usuarioActualizado;
        updateUserInfo();
        alert('Perfil actualizado exitosamente');
    })
    .catch(error => {
        console.error('Error al actualizar usuario:', error);
        alert('Error al actualizar el perfil');
    });
}

// Eliminar usuario
function eliminarUsuario(idUsuario) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta cuenta?')) {
        return;
    }
    
    fetch(`${API_BASE_URL}/usuarios/${idUsuario}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('authToken') || ''}`
        }
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al eliminar usuario');
        alert('Usuario eliminado exitosamente');
        cerrarSesion();
    })
    .catch(error => {
        console.error('Error al eliminar usuario:', error);
        alert('Error al eliminar la cuenta');
    });
}

// Cerrar sesión
function cerrarSesion() {
    currentUser = null;
    sessionStorage.removeItem('authToken');
    updateUserInfo();
    showPage('inicio');
    alert('Sesión cerrada exitosamente');
}

// ============================================
// GESTIÓN DE CONTENIDO (ACERCA DE / AYUDA)
// ============================================

// Cargar contenido de "Acerca de" desde el backend
function cargarAcercaDe() {
    fetch(`${API_BASE_URL}/contenido/acerca`)
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar contenido');
            return response.json();
        })
        .then(contenido => {
            acercaData = contenido.texto;
            document.getElementById('acercaContent').innerHTML = `<p>${contenido.texto}</p>`;
        })
        .catch(error => {
            console.error('Error al cargar Acerca de:', error);
            // Usar contenido por defecto si falla
        });
}

// Cargar contenido de "Ayuda" desde el backend
function cargarAyuda() {
    fetch(`${API_BASE_URL}/contenido/ayuda`)
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar contenido');
            return response.json();
        })
        .then(contenido => {
            ayudaData = contenido.texto;
            document.getElementById('ayudaContent').innerHTML = `<p>${contenido.texto}</p>`;
        })
        .catch(error => {
            console.error('Error al cargar Ayuda:', error);
            // Usar contenido por defecto si falla
        });
}

// Guardar contenido de "Ayuda" en el backend
document.getElementById('ayudaForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const text = document.getElementById('ayudaText').value;
    
    if (!text.trim()) {
        alert('Por favor, escribe algo antes de guardar');
        return;
    }
    
    const contenido = {
        tipo: 'ayuda',
        texto: text
    };
    
    fetch(`${API_BASE_URL}/contenido/ayuda`, {
        method: 'PUT',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('authToken') || ''}`
        },
        body: JSON.stringify(contenido)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al guardar contenido');
        return response.json();
    })
    .then(data => {
        ayudaData = text;
        document.getElementById('ayudaContent').innerHTML = `<p>${text}</p>`;
        alert('Contenido guardado exitosamente');
        this.reset();
    })
    .catch(error => {
        console.error('Error al guardar contenido:', error);
        alert('Error al guardar el contenido');
    });
});

// ============================================
// GESTIÓN DE PROGRESO DE JUEGOS
// ============================================

// Guardar progreso del juego
function guardarProgreso(tipoJuego, puntuacion, nivel) {
    if (!currentUser) {
        console.log('Usuario no autenticado');
        return;
    }
    
    const progreso = {
        idUsuario: currentUser.idUsuario,
        tipoJuego: tipoJuego,
        puntuacion: puntuacion,
        nivel: nivel,
        fecha: new Date().toISOString()
    };
    
    fetch(`${API_BASE_URL}/progreso`, {
        method: 'POST',
        headers: { 
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${sessionStorage.getItem('authToken') || ''}`
        },
        body: JSON.stringify(progreso)
    })
    .then(response => {
        if (!response.ok) throw new Error('Error al guardar progreso');
        return response.json();
    })
    .then(data => {
        console.log('Progreso guardado:', data);
    })
    .catch(error => {
        console.error('Error al guardar progreso:', error);
    });
}

// Cargar progreso del usuario
function cargarProgreso() {
    if (!currentUser) {
        console.log('Usuario no autenticado');
        return;
    }
    
    fetch(`${API_BASE_URL}/progreso/usuario/${currentUser.idUsuario}`, {
        headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('authToken') || ''}`
        }
    })
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar progreso');
            return response.json();
        })
        .then(progreso => {
            console.log('Progreso del usuario:', progreso);
            mostrarEstadisticas(progreso);
        })
        .catch(error => {
            console.error('Error al cargar progreso:', error);
        });
}

// Mostrar estadísticas en la interfaz
function mostrarEstadisticas(progreso) {
    // Implementar visualización de estadísticas
    // Puedes agregar una sección en el HTML para mostrar esto
    console.log('Estadísticas:', progreso);
}

// ============================================
// INICIALIZACIÓN
// ============================================

// Cargar datos iniciales al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    // Cargar contenidos estáticos
    cargarAcercaDe();
    cargarAyuda();
    
    // Verificar si hay sesión activa
    const token = sessionStorage.getItem('authToken');
    if (token) {
        // Opcional: validar token con el backend
        verificarSesion(token);
    }
});

// Verificar sesión activa
function verificarSesion(token) {
    fetch(`${API_BASE_URL}/usuarios/verificar`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Sesión inválida');
    })
    .then(usuario => {
        currentUser = usuario;
        updateUserInfo();
    })
    .catch(error => {
        console.error('Error al verificar sesión:', error);
        sessionStorage.removeItem('authToken');
    });
}

// ============================================
// FUNCIONES AUXILIARES
// ============================================

// Agregar botón de cerrar sesión en la navegación
function agregarBotonCerrarSesion() {
    const navLinks = document.querySelector('.nav-links');
    const logoutBtn = document.createElement('button');
    logoutBtn.textContent = 'Cerrar Sesión';
    logoutBtn.onclick = cerrarSesion;
    logoutBtn.style.display = currentUser ? 'block' : 'none';
    logoutBtn.id = 'logoutBtn';
    navLinks.appendChild(logoutBtn);
}

// Actualizar información del usuario (extendida)
function updateUserInfo() {
    const userInfo = document.getElementById('userInfo');
    const logoutBtn = document.getElementById('logoutBtn');
    
    if (currentUser) {
        userInfo.textContent = `Hola, ${currentUser.nombre}`;
        userInfo.style.display = 'block';
        if (logoutBtn) logoutBtn.style.display = 'block';
    } else {
        userInfo.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'none';
    }
}

// Llamar al inicializar
agregarBotonCerrarSesion();