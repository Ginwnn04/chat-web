let stompClient = null;
let sender = null;
let receiver = null;

document.getElementById('btnLogin').addEventListener('click', () => {
    sender = document.getElementById('sender').value.trim();
    if(sender) {
        var socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            // stompClient.subscribe('/topic/public', onMessageReceived);
            stompClient.subscribe('/user/queue/private', onMessageReceived);
        });
    }
    alert("Đăng nhập thành công");
});
 

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    const component = document.querySelector('.chat');
    component.innerHTML += `<div class="sender">
                                <p>${message.content}</p>
                            </div>`;
    
}


document.getElementById('btnSend').addEventListener('click', () => { 
    receiver = document.getElementById('receiver').value.trim();
    var messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient && receiver) {
        var chatMessage = {
            sender: sender,
            receiver: receiver,
            content: messageContent,
        };
        // stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));
        stompClient.send("/app/sendMessagePrivate", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
});


document.addEventListener("DOMContentLoaded", () => {
    const chat = document.querySelector('.chat');

    // Hàm cuộn xuống cuối
    function scrollToBottom() {
        chat.scrollTop = chat.scrollHeight;
    }

    // Sử dụng MutationObserver để theo dõi thay đổi trong div
    const observer = new MutationObserver(scrollToBottom);
    observer.observe(chat, { childList: true });

    // Cuộn xuống khi tải trang
    scrollToBottom();
});