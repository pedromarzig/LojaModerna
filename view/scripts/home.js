document.addEventListener('DOMContentLoaded', function() {
    // Faz a requisição para o backend para buscar os produtos
    fetch('http://localhost:8080/products')  // Atualize a URL conforme necessário
        .then(response => response.json())  // Converte a resposta da API para JSON
        .then(data => {
            // Acessa o elemento da lista onde os produtos serão exibidos
            const productList = document.getElementById('product-list');
            
            // Para cada produto retornado da API, cria um novo item na lista
            data.forEach(product => {
                const li = document.createElement('li');
                li.textContent = `${product.nameProduct} - $${product.price}`;  // Exibe o nome e preço
                productList.appendChild(li);  // Adiciona o item à lista
            });
        })
        .catch(error => {
            console.error('Erro ao carregar os produtos:', error);
        });
});
