document.addEventListener('DOMContentLoaded', function() {
    // Elementos da interface
    const productList = document.getElementById('product-list');
    const loadingIndicator = document.createElement('tr');
    loadingIndicator.innerHTML = '<td colspan="7" style="text-align: center;"><i class="fas fa-spinner fa-spin"></i> Carregando produtos...</td>';
    
    // Mostra indicador de carregamento
    productList.innerHTML = '';
    productList.appendChild(loadingIndicator);

    // Tenta carregar produtos da API
    fetch('http://localhost:8080/products')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na resposta da rede');
            }
            return response.json();
        })
        .then(apiProducts => {
            // Se a API retornar dados, renderiza eles
            renderProducts(apiProducts);
        })
        .catch(apiError => {
            console.error('Erro na API:', apiError);
            // Se a API falhar, usa os dados mockados como fallback
            console.warn('Usando dados mockados como fallback');
            const mockProducts = getMockProducts();
            renderProducts(mockProducts);
            
            // Mostra aviso para o usuário
            const warning = document.createElement('div');
            warning.className = 'api-warning';
            warning.innerHTML = `
                <i class="fas fa-exclamation-triangle"></i>
                <span>Os dados podem estar desatualizados. Problema de conexão com o servidor.</span>
            `;
            document.querySelector('.header').appendChild(warning);
        });

    // Função para gerar dados mockados
    function getMockProducts() {
        return [
            {
                id: 1001,
                name: "Cimento CP II 50kg",
                category: "Cimentos",
                stock: 120,
                price: 32.90,
                minStock: 20
            },
            {
                id: 1002,
                name: "Tijolo Baiano 6 furos",
                category: "Cerâmicos",
                stock: 850,
                price: 0.85,
                minStock: 200
            },
            {
                id: 1003,
                name: "Areia Média 1m³",
                category: "Materiais Básicos",
                stock: 15,
                price: 120.00,
                minStock: 10
            },
            {
                id: 1004,
                name: "Brita 1 1m³",
                category: "Materiais Básicos",
                stock: 8,
                price: 150.00,
                minStock: 5
            },
            {
                id: 1005,
                name: "Tinta Acrílica 18L",
                category: "Tintas",
                stock: 22,
                price: 189.90,
                minStock: 10
            }
        ];
    }

    // Função para determinar o status do estoque
    function getStockStatus(stock, minStock) {
        if (stock === 0) return "out-of-stock";
        if (stock <= minStock) return "low-stock";
        return "in-stock";
    }

    // Função para formatar o status
    function formatStatus(status) {
        const statusText = {
            "out-of-stock": "Sem Estoque",
            "low-stock": "Estoque Baixo",
            "in-stock": "Em Estoque"
        };
        return `<span class="status ${status}">${statusText[status]}</span>`;
    }

    // Função principal para renderizar os produtos
    function renderProducts(products) {
        productList.innerHTML = '';
        
        if (products.length === 0) {
            productList.innerHTML = '<tr><td colspan="7">Nenhum produto cadastrado.</td></tr>';
            return;
        }
        
        products.forEach(product => {
            // Garante que todos os campos necessários existam
            const safeProduct = {
                id: product.id || 'N/A',
                name: product.name || product.nameProduct || 'Produto sem nome',
                category: product.category || 'Sem categoria',
                stock: product.stock || 0,
                price: product.price || 0,
                minStock: product.minStock || 5
            };
            
            const status = getStockStatus(safeProduct.stock, safeProduct.minStock);
            
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${safeProduct.id}</td>
                <td>${safeProduct.name}</td>
                <td>${safeProduct.category}</td>
                <td>${safeProduct.stock} un</td>
                <td>R$ ${safeProduct.price.toFixed(2)}</td>
                <td>${formatStatus(status)}</td>
                <td>
                    <button class="action-btn edit-btn" data-id="${safeProduct.id}">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="action-btn delete-btn" data-id="${safeProduct.id}">
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </td>
            `;
            
            productList.appendChild(row);
        });

        // Adiciona event listeners para os botões
        addEventListeners();
    }

    // Função para adicionar listeners aos botões
    function addEventListeners() {
        // Botões de edição
        document.querySelectorAll('.edit-btn').forEach(button => {
            button.addEventListener('click', function() {
                const productId = this.getAttribute('data-id');
                editProduct(productId);
            });
        });
        
        // Botões de exclusão
        document.querySelectorAll('.delete-btn').forEach(button => {
            button.addEventListener('click', function() {
                const productId = this.getAttribute('data-id');
                deleteProduct(productId);
            });
        });
    }

    // Função para editar produto
    function editProduct(productId) {
        console.log(`Editando produto ${productId}`);
        // Aqui você pode implementar a lógica real de edição
        // Por exemplo, abrir um modal com formulário de edição
        
        // Feedback visual temporário
        const button = document.querySelector(`.edit-btn[data-id="${productId}"]`);
        const originalText = button.innerHTML;
        button.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Editando...';
        button.disabled = true;
        
        setTimeout(() => {
            button.innerHTML = originalText;
            button.disabled = false;
        }, 1500);
    }

    // Função para excluir produto
    function deleteProduct(productId) {
        if (!confirm(`Tem certeza que deseja excluir o produto ${productId}?`)) {
            return;
        }
        
        console.log(`Excluindo produto ${productId}`);
        // Aqui você pode implementar a lógica real de exclusão
        // Por exemplo, fazer uma requisição DELETE para sua API
        
        // Feedback visual
        const button = document.querySelector(`.delete-btn[data-id="${productId}"]`);
        const originalText = button.innerHTML;
        button.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Excluindo...';
        button.disabled = true;
        
        // Simulação de requisição assíncrona
        setTimeout(() => {
            // Em uma implementação real, você removeria a linha somente após confirmação da API
            const row = button.closest('tr');
            row.style.opacity = '0';
            setTimeout(() => row.remove(), 500);
            
            // Mostrar mensagem de sucesso
            showAlert('Produto excluído com sucesso!', 'success');
        }, 1500);
    }

    // Função para mostrar alertas
    function showAlert(message, type = 'info') {
        const alert = document.createElement('div');
        alert.className = `alert alert-${type}`;
        alert.innerHTML = `
            <i class="fas fa-${type === 'success' ? 'check-circle' : 'info-circle'}"></i>
            ${message}
        `;
        
        document.querySelector('.main-content').prepend(alert);
        
        // Remove o alerta após 5 segundos
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    }
});