document.getElementById('productForm').addEventListener('submit', async function (e) {
    e.preventDefault();
  
    const nameProduct = document.getElementById('nameProduct').value;
    const description = document.getElementById('description').value;
    const quantity = parseFloat(document.getElementById('quantity').value);
    const price = parseFloat(document.getElementById('price').value);
  
    const product = {
      nameProduct,
      description,
      quantity,
      price
    };
  
    try {
        const response = await fetch('http://localhost:8080/products/registerp', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
          });
  
      if (response.ok) {
        document.getElementById('responseMsg').textContent = 'Produto cadastrado com sucesso!';
        document.getElementById('productForm').reset();
      } else {
        const text = await response.text(); // Evita erro ao tentar fazer .json() em resposta vazia
        document.getElementById('responseMsg').textContent = 'Erro: ' + (text || 'Falha ao cadastrar produto');
      }
    } catch (error) {
      console.error('Erro:', error);
      document.getElementById('responseMsg').textContent = 'Erro ao conectar com o servidor.';
    }
  });
  