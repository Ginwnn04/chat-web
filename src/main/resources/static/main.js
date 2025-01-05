let socket = null;
const btnLogin = document.getElementById("btnLogin");
const senderInput = document.getElementById("sender");
const roomInput = document.getElementById("room");
const messageInput = document.getElementById("message");
const btnSend = document.getElementById("btnSend");
const chatBox = document.querySelector(".chat");

// Xử lý khi nhấn Login
btnLogin.addEventListener("click", () => {
    const sender = senderInput.value.trim();
    const room = roomInput.value.trim();
    if (!sender || !room) {
        alert("Please enter both username and room!");
        return;
    }

    // Kết nối đến server
    socket = io("ws://127.0.0.1:8085", {
        reconnection: false,
        transports: ['websocket'], // Sử dụng WebSocket nếu có
        query: { room }// Tắt việc nâng cấp lên EIO=4// Thêm room vào query
    });

    // Xử lý khi kết nối thành công
    socket.on("connect", () => {
        console.log(`Connected to socket server as ${sender} in room ${room}`);
    });
    


    // Lắng nghe tin nhắn từ server
    socket.on("get_message", (data) => {
        
        updateMessage(data);
    });

    alert(`Logged in as ${sender} in room ${room}`);
});

// Xử lý khi nhấn nút Send
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
            room: room, // Room hiện tại
            type: "CLIENT",
            message: `${sender}: ${message}`, // Tin nhắn kèm username
        };
        console.log(data);
        socket.emit("send_message", data); // Gửi sự kiện 'send_message' đến server
        messageInput.value = ""; // Xóa nội dung sau khi gửi
        updateMessage(data);
    }
    else {
        alert("Please fill in all fields!");
    }
});


function updateMessage(data) {
    let elm = "";
    if (data.type === "CLIENT") {
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