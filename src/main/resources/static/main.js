let socket = null;
let sender = null;
const btnLogin = document.getElementById("btnLogin");
const senderInput = document.getElementById("sender");
const roomInput = document.getElementById("room");
const messageInput = document.getElementById("message");
const btnSend = document.getElementById("btnSend");
const chatBox = document.querySelector(".chat");


btnLogin.addEventListener("click", () => {
    sender = senderInput.value.trim();
    const room = roomInput.value.trim();
    if (!sender || !room) {
        alert("Please enter both username and room!");
        return;
    }
    if (socket) {
        socket.disconnect();
        socket = null;
    }

    // Kết nối đến server
    socket = io("ws://127.0.0.1:8085", {
        reconnection: false,
        transports: ['websocket'],
        query: { room }
    });


    socket.on("connect", () => {
        console.log(`Connected to socket server as ${sender} in room ${room}`);
    });
    



    socket.on("get_message", (data) => {
        
        updateMessage(data);
    });

    alert(`Logged in as ${sender} in room ${room}`);
});


btnSend.addEventListener("click", () => {
    if (!socket) {
        alert("Please login first!");
        return;
    }

    const sender = senderInput.value.trim();
    const room = roomInput.value.trim();
    const message = messageInput.value.trim();

    if (sender && room && message) {
        const data = {
            sender: sender,
            room: room,
            type: "CLIENT",
            message: message
        };
        console.log(data);
        socket.emit("send_message", data);
        messageInput.value = "";
        updateMessage(data);
    }
    else {
        alert("Please fill in all fields!");
    }
});


function updateMessage(data) {
    let elm = "";
    if (sender === data.sender) {
        elm = `<div class="sender">
                    <p>${data.message}</p>
                </div>`;
    } 
    else {
        elm = `<div class="receiver">
                    <p>${data.message}</p>
                </div>`;
    }
    chatBox.innerHTML += elm;
    chatBox.scrollTop = chatBox.scrollHeight;
}