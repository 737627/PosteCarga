<%@page import="org.eclipse.microprofile.config.Config"%>
<%@page import="org.eclipse.microprofile.config.ConfigProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    // Obtener la configuración del servidor
    Config cfg = ConfigProvider.getConfig();
    String serverUrl = cfg.getValue("stw.posteCargaServer.url", String.class);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PosteCarga Management</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 40px;
                text-align: center;
            }
            .button {
                border: none;
                color: white;
                padding: 15px 32px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
            }
            .available {
                background-color: #4CAF50; /* Green */
            }
            .unavailable {
                background-color: #A9A9A9; /* Dark Gray */
            }
            .status {
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <h1>PosteCarga Management System</h1>

        <form id="posteForm">
            <label for="posteId">ID del Poste:</label>
            <input type="text" id="posteId" name="posteId" required>
            <button type="button" onclick="fetchEstadoPoste()">Ver Estado del Poste</button>
        </form>

        <form id="matriculaForm">
            <label for="matriculaVehiculo">Matrícula del Vehículo:</label>
            <input type="text" id="matriculaVehiculo" name="matriculaVehiculo" required>
        </form>

        <button class="button available" id="conectarBtn" onclick="conectarManguera()">Conectar Manguera</button>
        <button class="button unavailable" id="desconectarBtn" onclick="desconectarManguera()" disabled>Desconectar Manguera</button>
        <button class="button unavailable" id="iniciarBtn" onclick="iniciarCarga()" disabled>Iniciar Carga</button>
        <button class="button unavailable" id="detenerBtn" onclick="detenerCarga()" disabled>Detener Carga</button>

        <div class="status" id="statusDisplay">Estado del poste: Desconectado</div>
        <div class="status" id="matriculaDisplay">Matrícula del vehículo: Ninguna</div>

        <h2>Crear Nuevo Poste</h2>
        <form id="nuevoPosteForm">
            <label for="zonaDeCarga">Zona de Carga:</label>
            <input type="text" id="zonaDeCarga" name="zonaDeCarga" required>
            <label for="potenciaMaxima">Potencia Máxima:</label>
            <input type="number" id="potenciaMaxima" name="potenciaMaxima" required>
            <button type="button" onclick="crearNuevoPoste()">Crear Poste</button>
        </form>

        <div id="postesIdsDisplay"></div>

        <script>
            const SERVER_URL = '<%=serverUrl%>';

            // Variables de estado
            let estadoPoste = "Desconectado";
            const statusDisplay = document.getElementById('statusDisplay');
            const matriculaDisplay = document.getElementById('matriculaDisplay');
            const conectarBtn = document.getElementById('conectarBtn');
            const desconectarBtn = document.getElementById('desconectarBtn');
            const iniciarBtn = document.getElementById('iniciarBtn');
            const detenerBtn = document.getElementById('detenerBtn');

            function updateButtons() {
                switch (estadoPoste) {
                    case "Desconectado":
                        setButtonState(conectarBtn, true);
                        setButtonState(desconectarBtn, false);
                        setButtonState(iniciarBtn, false);
                        setButtonState(detenerBtn, false);
                        break;
                    case "Ocupado":
                        setButtonState(conectarBtn, false);
                        setButtonState(desconectarBtn, true);
                        setButtonState(iniciarBtn, true);
                        setButtonState(detenerBtn, false);
                        break;
                    case "Cargando":
                        setButtonState(conectarBtn, false);
                        setButtonState(desconectarBtn, false);
                        setButtonState(iniciarBtn, false);
                        setButtonState(detenerBtn, true);
                        break;
                    case "Libre":
                        setButtonState(conectarBtn, false);
                        setButtonState(desconectarBtn, true);
                        setButtonState(iniciarBtn, false);
                        setButtonState(detenerBtn, false);
                        break;
                    default:
                        break;
                }
            }

            function setButtonState(button, isAvailable) {
                button.disabled = !isAvailable;
                button.className = isAvailable ? 'button available' : 'button unavailable';
            }

            function fetchEstadoPoste() {
                const idPoste = document.getElementById('posteId').value;
                if (!idPoste) {
                    alert('Por favor, ingrese un ID de poste.');
                    return;
                }
                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/status?idPoste=${idPoste}`)
                    .then(response => response.json())
                    .then(data => {
                        estadoPoste = data.estado;
                        statusDisplay.textContent = `Estado del poste: ${data.estado}`;
                        matriculaDisplay.textContent = `Matrícula del vehículo: ${data.matriculaVehiculo || 'Ninguna'}`;
                        updateButtons();
                    })
                    .catch(error => console.error('Error:', error));
            }

            function conectarManguera() {
                const matricula = document.getElementById("matriculaVehiculo").value;
                if (!matricula) {
                    alert('Por favor, introduce la matrícula del vehículo.');
                    return;
                }
                const idPoste = document.getElementById('posteId').value;
                let conexionDTO = {matriculaVehiculo: matricula, idPoste: parseInt(idPoste)};

                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/connect`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(conexionDTO)
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.text().then(text => {
                                throw new Error(`Server error: ${text}`);
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        estadoPoste = 'Ocupado';
                        statusDisplay.textContent = `Estado del poste: ${estadoPoste}`;
                        matriculaDisplay.textContent = `Matrícula del vehículo: ${matricula}`;
                        updateButtons();
                        alert("Manguera conectada");
                    })
                    .catch(error => console.error('Error:', error.message));
            }

            function desconectarManguera() {
                const idPoste = document.getElementById('posteId').value;
                let desconexionDTO = {idPoste: parseInt(idPoste)};

                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/disconnect`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(desconexionDTO)
                })
                    .then(response => response.json())
                    .then(data => {
                        estadoPoste = 'Desconectado';
                        statusDisplay.textContent = `Estado del poste: ${estadoPoste}`;
                        matriculaDisplay.textContent = 'Matrícula del vehículo: Ninguna';
                        updateButtons();
                        alert('Manguera desconectada');
                    })
                    .catch(error => console.error('Error:', error));
            }

            function iniciarCarga() {
                const idPoste = document.getElementById('posteId').value;
                let inicioCargaDTO = {idPoste: parseInt(idPoste), consignaCarga: 50.0};

                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/start`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(inicioCargaDTO)
                })
                    .then(response => response.json())
                    .then(data => {
                        estadoPoste = 'Cargando';
                        statusDisplay.textContent = `Estado del poste: ${estadoPoste}`;
                        updateButtons();
                        alert('Carga iniciada');
                    })
                    .catch(error => console.error('Error:', error));
            }

            function detenerCarga() {
                const idPoste = document.getElementById('posteId').value;
                let detenerCargaDTO = {idPoste: parseInt(idPoste)};

                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/stop`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(detenerCargaDTO)
                })
                    .then(response => response.json())
                    .then(data => {
                        estadoPoste = 'Libre';
                        statusDisplay.textContent = `Estado del poste: ${estadoPoste}`;
                        updateButtons();
                        alert('Carga detenida');
                    })
                    .catch(error => console.error('Error:', error));
            }

            function mostrarIdsDePostes() {
                fetch(`${SERVER_URL}/PosteCarga/resources/postecarga/listaPostes`)
                    .then(response => response.json())
                    .then(data => {
                        const postesIdsDisplay = document.getElementById('postesIdsDisplay');
                        postesIdsDisplay.innerHTML = ''; // Limpiar el contenido del div antes de agregar nuevos IDs

                        const ids = data.map(poste => poste.id);
                        ids.sort((a, b) => a - b); // Ordenar los IDs
                        ids.forEach(id => {
                            const p = document.createElement('p');
                            p.textContent = id;
                            postesIdsDisplay.appendChild(p);
                        });
                    })
                    .catch(error => console.error('Error:', error));
            }

            document.addEventListener('DOMContentLoaded', () => {
                mostrarIdsDePostes();
            });
        </script>
    </body>
</html>
