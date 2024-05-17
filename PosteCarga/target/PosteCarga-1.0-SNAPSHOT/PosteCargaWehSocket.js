let wsUri = "ws://" + document.location.host + "/posteCarga/websocket";
let webSocket;

// Elementos de la interfaz de usuario (UI) para visualizar el estado del poste
const estadoPoste = document.getElementById("estadoPoste");
const potenciaEntregada = document.getElementById("potenciaEntregada");

// Botones de control
const conectarBtn = document.getElementById("conectarBtn");
const desconectarBtn = document.getElementById("desconectarBtn");
const iniciarBtn = document.getElementById("iniciarBtn");
const detenerBtn = document.getElementById("detenerBtn");
conectarBtn.disabled = true;  
desconectarBtn.disabled = true;
iniciarBtn.disabled = true;
detenerBtn.disabled = true;

const posteSelect = document.getElementById("posteSelect");
let idPoste = null; 

// Inicializar WebSocket
openSocket();

/**
 * Abre la conexión WebSocket y configura los manejadores de eventos.
 */
function openSocket() {
    console.log("Intentando abrir WebSocket en: " + wsUri);

    // Asegura que solo haya una conexión abierta a la vez
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        closeSocket();
    }

    webSocket = new WebSocket(wsUri);

    webSocket.onopen = function(event) {
        console.log("Conexión WebSocket establecida.");
        conectarBtn.disabled = false;
    };

    webSocket.onmessage = function(event) {
        console.log("Mensaje recibido: " + event.data);
        handleWebSocketMessage(event.data);
    };

    webSocket.onclose = function(event) {
        console.log("Conexión WebSocket cerrada.");
        conectarBtn.disabled = true;
        desconectarBtn.disabled = true;
        iniciarBtn.disabled = true;
        detenerBtn.disabled = true;
    };

    webSocket.onerror = function(event) {
        console.error("Error en WebSocket: ", event);
    };
}

/**
 * Maneja los mensajes entrantes de WebSocket.
 * @param {string} message - Mensaje en formato JSON recibido del WebSocket.
 */
function handleWebSocketMessage(message) {
    try {
        const data = JSON.parse(message);  

        // Actualiza los elementos del DOM con los datos recibidos.
        if (data.estado) {
            estadoPoste.textContent = "Estado: " + data.estado;
        }
        if (data.potencia) {
            potenciaEntregada.textContent = "Potencia Entregada: " + data.potencia + " kW";
        }
    } catch (error) {
        console.error("Error parsing message:", error);
    }
}

/**
 * Cierra la conexión WebSocket.
 */
function closeSocket() {
    if (webSocket) {
        webSocket.close();
    }
}

/**
 * Enviar comandos para conectar y desconectar la manguera.
 */
function enviarComandoConectar() {
    if (idPoste) {
        webSocket.send(JSON.stringify({ action: "conectar", idPoste: idPoste }));
    }
}

function enviarComandoDesconectar() {
    if (idPoste) {
        webSocket.send(JSON.stringify({ action: "desconectar", idPoste: idPoste }));
    }
}

/**
 * Maneja el cambio de poste seleccionado.
 */
function handlePosteChange() {
    idPoste = posteSelect.value;
}

// Funciones para conectar, desconectar, iniciar y detener carga
function conectarManguera() {
    const matricula = document.getElementById("matriculaVehiculo").value;
    if (!matricula) {
        alert('Por favor, introduce la matrícula del vehículo.');
        return;
    }
    enviarComandoConectar();
    estadoPoste.textContent = 'Estado del poste: Ocupado';
    conectarBtn.disabled = true;
    desconectarBtn.disabled = false;
    iniciarBtn.disabled = false;
    alert('Manguera conectada');
}

function desconectarManguera() {
    enviarComandoDesconectar();
    estadoPoste.textContent = 'Estado del poste: Desconectado';
    conectarBtn.disabled = false;
    desconectarBtn.disabled = true;
    iniciarBtn.disabled = true;
    detenerBtn.disabled = true;
    alert('Manguera desconectada');
}

function iniciarCarga() {
    estadoPoste.textContent = 'Estado del poste: Cargando...';
    iniciarBtn.disabled = true;
    detenerBtn.disabled = false;
    alert('Carga iniciada');
}

function detenerCarga() {
    estadoPoste.textContent = 'Estado del poste: Libre';
    iniciarBtn.disabled = false;
    detenerBtn.disabled = true;
    alert('Carga detenida');
}

// Obtener la lista de postes al cargar la página
window.onload = function() {
    fetch('/postecarga/obtenerPostes')
        .then(response => response.json())
        .then(data => {
            posteSelect.innerHTML = '';
            data.forEach(poste => {
                const option = document.createElement('option');
                option.value = poste.id;
                option.textContent = `Poste ${poste.id} - Estado: ${poste.estado}`;
                posteSelect.appendChild(option);
            });
            // Seleccionar el primer poste por defecto
            if (data.length > 0) {
                idPoste = data[0].id;
                posteSelect.value = idPoste;
            }
        });
};
