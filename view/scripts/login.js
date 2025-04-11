document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede que o formulário seja enviado de forma tradicional

    // Obtém os valores inseridos nos campos do formulário
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch("http://localhost:8080/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email, 
            password: password
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erro no login: ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.token) {
            console.log("Login bem-sucedido, token:", data.token);
        } else {
            console.error("Falha no login:", data);
        }
    })
    .catch(error => {
        console.error("Erro ao fazer login:", error);
    });
});
