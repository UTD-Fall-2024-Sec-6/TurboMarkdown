document.addEventListener('DOMContentLoaded', (event) => {
    const signInForm = document.querySelector('.sign-in-container form');
    const createAccountForm = document.querySelector('.create-account-container form');

    signInForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const email = signInForm.querySelector('input[type="email"]').value;
        const password = signInForm.querySelector('input[type="password"]').value;
        alert(`Sign In:\nEmail: ${email}\nPassword: ${password}`);
        // Add your sign-in logic here
    });

    createAccountForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const email = createAccountForm.querySelector('input[type="email"]').value;
        const password = createAccountForm.querySelector('input[type="password"]').value;
        alert(`Create Account:\nEmail: ${email}\nPassword: ${password}`);
        // Add your account creation logic here
    });
});
