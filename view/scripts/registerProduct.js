
    document.getElementById('productForm').addEventListener('submit', async function (e) {
      e.preventDefault();
      
      // Mostrar loading no botão
      const submitBtn = document.querySelector('.btn-submit');
      const originalBtnText = submitBtn.innerHTML;
      submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Cadastrando...';
      submitBtn.disabled = true;
      
      // Coletar dados do formulário
      const product = {
        nameProduct: document.getElementById('nameProduct').value,
        description: document.getElementById('description').value,
        category: document.getElementById('category').value,
        quantity: parseInt(document.getElementById('quantity').value),
        minStock: parseInt(document.getElementById('minStock').value),
        price: parseFloat(document.getElementById('price').value),
        imageUrl: document.getElementById('imageUrl').value || null
      };
      
      const responseMsg = document.getElementById('responseMsg');
      responseMsg.style.display = 'none';
      
      try {
        const response = await fetch('http://localhost:8080/products/registerp', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(product)
        });
        
        if (response.ok) {
          responseMsg.textContent = 'Produto cadastrado com sucesso!';
          responseMsg.className = 'success';
          responseMsg.style.display = 'block';
          document.getElementById('productForm').reset();
          
          // Feedback visual
          setTimeout(() => {
            responseMsg.style.display = 'none';
          }, 3000);
        } else {
          const errorData = await response.json().catch(() => null);
          const errorMsg = errorData?.message || 'Falha ao cadastrar produto';
          responseMsg.textContent = `Erro: ${errorMsg}`;
          responseMsg.className = 'error';
          responseMsg.style.display = 'block';
        }
      } catch (error) {
        console.error('Erro:', error);
        responseMsg.textContent = 'Erro ao conectar com o servidor. Verifique sua conexão.';
        responseMsg.className = 'error';
        responseMsg.style.display = 'block';
      } finally {
        // Restaurar botão
        submitBtn.innerHTML = originalBtnText;
        submitBtn.disabled = false;
      }
    });

    // Validação em tempo real para o preço
    document.getElementById('price').addEventListener('change', function() {
      if (this.value <= 0) {
        this.setCustomValidity('O preço deve ser maior que zero');
      } else {
        this.setCustomValidity('');
      }
    });

    // Validação para quantidade mínima de estoque
    document.getElementById('minStock').addEventListener('change', function() {
      const quantity = parseInt(document.getElementById('quantity').value);
      if (this.value > quantity) {
        this.setCustomValidity('O estoque mínimo não pode ser maior que a quantidade em estoque');
      } else {
        this.setCustomValidity('');
      }
    });