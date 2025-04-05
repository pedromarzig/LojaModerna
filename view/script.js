const form = document.getElementById("cadastroForm");

form.addEventListener("submit", function(event) {
    event.preventDefault(); // Evita o envio padrão do formulário

    const user = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        badge: document.getElementById("badge").value
    };

    fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (response.status === 201) {
            alert("Usuário cadastrado com sucesso!");
            form.reset();
        } else {
            alert("Erro ao cadastrar usuário.");
        }
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro na requisição.");
    });
});
