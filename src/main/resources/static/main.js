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
    socket.on("connect_error", (err) => {
        console.log("Connection Error:", err);
    });
    socket.on("connect_timeout", () => {
        console.log("Connection Timeout");
    });

    // Lắng nghe tin nhắn từ server
    socket.on("get_message", (data) => {
        
        chatBox.innerHTML +=    `<div class="sender">
                                    <p>${data.message}</p>
                                </div>`;
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
    }
    else {
        alert("Please fill in all fields!");
    }
});
