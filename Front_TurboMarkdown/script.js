
document.getElementById('login-form')?.addEventListener('submit', function (event) {
    event.preventDefault(); 
   
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    
    const user = JSON.parse(localStorage.getItem(username)); 

    
    if (user && user.password === password) {
        alert('Login Successful!');
        window.location.href = 'mainpage.html'; 
    } else {
        alert('Invalid username or password. Please try again!');
    }
});


document.querySelector('.create-account')?.addEventListener('click', function () {
    window.location.href = 'signup.html'; 
});


document.querySelector('.forgot-password')?.addEventListener('click', function () {
    alert('Forgot Password feature is not implemented yet.');
});
