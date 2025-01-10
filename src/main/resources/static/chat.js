let socket = null;
let sender = null;
const btnLogin = document.getElementById("btnLogin");
const senderInput = document.getElementById("sender");
const roomInput = document.getElementById("room");
const messageInput = document.getElementById("message");
const btnSend = document.getElementById("btnSend");
const chatBox = document.querySelector(".chat");



window.onload = () => {

    sender = window.location.search.split("=")[1];
    // Kết nối đến server

    socket = io("ws://127.0.0.1:8085", {
        reconnection: false,
        transports: ['websocket'],
        query: { username: sender }
    });




    socket.on("connect", () => {
        console.log(`Connected to socket server as ${sender}`);
    });
    



    socket.on("get_message", (data) => {
        updateMessage(data);
    });
    socket.on("notification", (data) => { 
        console.log(data);
        let listUser = data.slice(1, data.length - 1);
        console.log(listUser);
        if (listUser.indexOf(', ') !== -1) {
            listUser = listUser.split(', ');
        }
        else {
            listUser = [listUser];
        }
        console.log(listUser);
        let users = "";
        listUser.forEach(user => {
            const tmp = user.split("=");
            if (tmp[0] !== sender) {
                users += `<div id="${tmp[1]}" class="user" onclick="selectUsername(this)">
                        <p id="username-online">${tmp[0]}</p>
                    </div>`;
                console.log(user);
            }
        });
        document.getElementById("users-online").innerHTML = users;
    });

    alert(`Logged in as ${sender}`);
}



btnLogin.addEventListener("click", () => {
    // sender = senderInput.value.trim();
    // const room = roomInput.value.trim();
    // if (!sender || !room) {
    //     alert("Please enter both username and room!");
    //     return;
    // }
    // if (socket) {
    //     socket.disconnect();
    //     socket = null;
    // }

    // // Kết nối đến server
    // socket = io("ws://127.0.0.1:8085", {
    //     reconnection: false,
    //     transports: ['websocket'],
    //     query: { room }
    // });


    // socket.on("connect", () => {
    //     console.log(`Connected to socket server as ${sender} in room ${room}`);
    // });
    



    // socket.on("get_message", (data) => {
        
    //     updateMessage(data);
    // });

    // alert(`Logged in as ${sender} in room ${room}`);
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

function selectUsername(obj) {
    document.getElementById("chat-name").textContent = obj.childNodes[1].textContent;
    console.log(socket.id);
    socket.emit("create_room", socket.id + ", " + obj.getAttribute("id"));

}