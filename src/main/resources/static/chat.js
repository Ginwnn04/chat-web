let socket = null;
let sender = null;
let conversation = null;
const btnLogin = document.getElementById("btnLogin");
const senderInput = document.getElementById("sender");
const roomInput = document.getElementById("room");
const messageInput = document.getElementById("message");
const btnSend = document.getElementById("btnSend");
const chatBox = document.querySelector(".chat");
const listChat = document.querySelector(".list-chat");


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
        loadConversations(sender);
        console.log(data);

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
    loadConversations(sender);
    alert(`Logged in as ${sender}`);
}





btnSend.addEventListener("click", () => {
    if (!socket) {
        alert("Please login first!");
        return;
    }

    const content = messageInput.value.trim();
    const data = {
        sender: sender,
        room: conversation,
        type: "CLIENT",
        content: content,
        create_at: new Date().toISOString()
    };

    console.log(data);
    socket.emit("send_message", data);
    messageInput.value = "";
    updateMessage(data);
    return;
    
});


function updateMessage(data) {
    if (conversation === null)
        return;
    let elm = "";
    if (sender === data.sender) {
        elm = `<div class="sender">
                    <p>${data.content}</p>
                </div>`;
    } 
    else {
        elm = `<div class="receiver">
                    <p>${data.content}</p>
                </div>`;
    }
    chatBox.innerHTML += elm;
    chatBox.scrollTop = chatBox.scrollHeight;
}

function updateConversations(data) { 
    let elm = "";
    if (sender === data.sender) {
        elm = `<div class="chat-item" onclick="selectConversation(this)" id="${data.conversationId}">
                    <div class="image">
                    </div>
                    <div class="info">
                        <p>${data.receiver}</p>
                        <p>Bạn: ${data.content}</p>
                    </div>
                </div>`;
    } 
    else {
        elm = `<div class="chat-item" onclick="selectConversation(this)" id="${data.conversationId}">
                    <div class="image">
                    </div>
                    <div class="info">
                        <p>${data.receiver}</p>
                        <p>${data.content}</p>
                    </div>
                </div>`;
    }
    listChat.innerHTML += elm;
}


function selectUsername(obj) {
    chatBox.innerHTML = "";
    document.getElementById("chat-name").textContent = obj.childNodes[1].textContent;
    socket.emit("load_room", socket.id + ", " + obj.getAttribute("id"));
    isExistsConversation(sender, obj.childNodes[1].textContent);
    loadMessage(sender, obj.childNodes[1].textContent);
}

function selectConversation(obj) {
    chatBox.innerHTML = "";
    document.getElementById("chat-name").textContent = obj.querySelector("p").textContent;
    conversation = obj.getAttribute("id");
    loadMessage(sender, obj.querySelector("p").textContent);
}




async function isExistsConversation(user1, user2) {
    const url = `http://localhost:8080/conversations?user1=${user1}&user2=${user2}`;
    try {
        const response = await fetch(url);
        if (response.ok) {
            const data = await response.json();
            if (data.success === true) {
                conversation = data.data.id;
            }
            console.log(conversation);
        }
        else
            conversation = null;
        
    } catch (error) {
      console.error(error.message);
    }
}


async function loadMessage(user1, user2) {
    const url = `http://localhost:8080/messages?user1=${user1}&user2=${user2}`;
    try {
        const response = await fetch(url);
        if (response.ok) {
            const data = await response.json();
            data.data.forEach(msg => {
                updateMessage(msg);
            })   
        }
        else 
            conversation = null;
    } catch (error) {
      console.error(error.message);
    }
}

async function loadConversations(username) { 
    const url = `http://localhost:8080/messages/${username}/conversations`;
    try {
        const response = await fetch(url);
        if (response.ok) {
            const data = await response.json();
            listChat.innerHTML = "";
            data.data.forEach(msg => {
                updateConversations(msg);
            })   
        }
    } catch (error) {
      console.error(error.message);
    }
}