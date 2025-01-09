

const a = document.getElementById('btnLogin').addEventListener('click', () => {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (username === '' || password === '') {
        alert('Nhập thông tin đẩy đủ');
        return;
    }
    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', 
        },
        body: JSON.stringify({
            username: username,
            password: password
        }),
    })
        .then((res) => res.json())
        .then((data) => {
            console.log(data);
            if (data === true) {
                alert('Đăng nhập thành công');
                window.location.href = `/chating.html?username=${username}`;
            } else {
                alert('Đăng nhập thất bại');
            }
        })
        .catch((error) => console.error('Error:', error));
});