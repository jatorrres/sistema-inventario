const API_URL = "/productos";

async function cargarProductos() {
    const tabla = document.getElementById("tablaProductos");
    if (!tabla) return; 

    try {
        const respuesta = await fetch(API_URL);
        const productos = await respuesta.json();
        
        tabla.innerHTML = "";
        let valorTotalInventario = 0;

        productos.forEach(producto => {
            const precio = Number(producto.precio) || 0;
            const cantidad = Number(producto.cantidad) || 0;
            valorTotalInventario += (precio * cantidad);

            tabla.innerHTML += `
                <tr>
                    <td>${producto.id}</td>
                    <td>${producto.codigo}</td>
                    <td>${producto.nombre}</td>
                    <td>${producto.categoria}</td>
                    <td>$${precio}</td>
                    <td>${cantidad}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="cargarParaEditar(${producto.id})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${producto.id})">Eliminar</button>
                    </td>
                </tr>
            `;
        });

        const spanTotal = document.getElementById("totalInventario");
        if (spanTotal) {
            spanTotal.textContent = `$${valorTotalInventario.toLocaleString('es-CO')}`;
        }
    } catch (error) {
        console.error("ERROR AL CONSULTAR LA API:", error);
    }
}


cargarProductos();


function cargarParaEditar(id) {
    window.location.href = `registrar.html?id=${id}`;
}


async function eliminarProducto(id) {
    const confirmar = confirm("¿Está seguro de eliminar este producto?");
    if (!confirmar) return;

    try {
        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (respuesta.ok) {
            alert("Producto eliminado correctamente");
            cargarProductos();
        } else {
            alert("No fue posible eliminar el producto");
        }
    } catch (error) {
        console.error("Error al intentar eliminar:", error);
        alert("No se pudo conectar con el servidor backend");
    }
}


const formProducto = document.getElementById("formProducto");

if (formProducto) {
    
    const urlParams = new URLSearchParams(window.location.search);
    const productoId = urlParams.get("id");

    if (productoId) {
      
        const titulo = document.querySelector("h2");
        if (titulo) titulo.textContent = "Modificar Producto";


        fetch(`${API_URL}/${productoId}`)
            .then(res => res.json())
            .then(producto => {
                document.getElementById("codigo").value = producto.codigo;
                document.getElementById("nombre").value = producto.nombre;
                document.getElementById("categoria").value = producto.categoria;
                document.getElementById("precio").value = producto.precio;
                document.getElementById("cantidad").value = producto.cantidad;
            })
            .catch(err => console.error("Error al cargar producto para editar:", err));
    }

    formProducto.addEventListener("submit", async function(event) {
        event.preventDefault(); 

        const producto = {
            codigo: document.getElementById("codigo").value,
            nombre: document.getElementById("nombre").value,
            categoria: document.getElementById("categoria").value,
            precio: Number(document.getElementById("precio").value),
            cantidad: Number(document.getElementById("cantidad").value)
        };

        const metodo = productoId ? "PUT" : "POST";
        const urlDestino = productoId ? `${API_URL}/${productoId}` : API_URL;

        try {
            const respuesta = await fetch(urlDestino, {
                method: metodo,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(producto)
            });

            if (respuesta.ok) {
                alert(productoId ? "Producto modificado correctamente" : "Producto registrado correctamente");
                formProducto.reset(); 
                window.location.href = "Productos.html"; 
            } else {
                alert("No fue posible guardar el producto");
            }
        } catch (error) {
            console.error("Error de red:", error);
            alert("No se pudo conectar con el servidor backend");
        }
    });
}