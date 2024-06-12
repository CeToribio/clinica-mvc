const tableBody = document.querySelector("#odontologosTable tbody");
  function fetchOdontologos() {
    // listando los odontologos

    fetch(`/odontologo`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        // Limpiar el contenido actual de la tabla
        tableBody.innerHTML = "";

        // Insertar los datos en la tabla
        data.forEach((odontologo, index) => {
          const row = document.createElement("tr");

          row.innerHTML = `
                  <td>${odontologo.id}</td>
                  <td>${odontologo.nombre}</td>
                  <td>${odontologo.apellido}</td>
                  <td>${odontologo.matricula}</td>
                  <td>
                    <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nombre}', '${odontologo.apellido}', '${odontologo.matricula}')">Modificar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
                  </td>
                `;

          tableBody.appendChild(row);
        });
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });

  }

  fetchOdontologos();

    // modificar un odontologo
    function editOdontologo(id, nombre, apellido, matricula) {
      // Mostrar el modal de SweetAlert con un formulario
      Swal.fire({
        title: "Editar Odontólogo",
        html:
          '<input id="nombre" class="swal2-input" placeholder="Nombre">' +
          '<input id="apellido" class="swal2-input" placeholder="Apellido">' +
          '<input id="matricula" class="swal2-input" placeholder="Matrícula">',
        focusConfirm: false,
        preConfirm: () => {
          // Recoger los valores ingresados por el usuario
          const nombre = Swal.getPopup().querySelector("#nombre").value;
          const apellido = Swal.getPopup().querySelector("#apellido").value;
          const matricula = Swal.getPopup().querySelector("#matricula").value;

          // Crear el objeto con los nuevos datos
          const newData = { nombre, apellido, matricula };

          // Realizar la petición PUT para actualizar el odontólogo en el servidor
          fetch(`/odontologo`, {
            // Asegúrate de incluir el ID del odontólogo en la URL
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              id: id,
              matricula: matricula,
              nombre: nombre,
              apellido: apellido,
            }), // Pasar los datos como JSON
          })
            .then((response) => response.text()) // Parsear la respuesta
            .then((data) => {
              console.log(data);
              Swal.fire({
                position: "center",
                icon: "success",
                text: "Odontólogo actualizado correctamente.",
                showConfirmButton: false,
                timer: 2000,
              });

              fetchOdontologos();
            }) // Llama a la función que recarga los odontólogos}) // Manejar la respuesta
            .catch((error) => {
              console.error("Error:", error);
              Swal.fire({
                position: "center",
                icon: "error",
                text: "Hubo un error al editar el odontólogo.",
                showConfirmButton: false,
                timer: 2000,
              });
            }); // Manejar errores
        },
      });
    }



   // eliminar un odontologo
   function deleteOdontologo(id) {
     Swal.fire({
       text: "¿Estás seguro de querer eliminar este odontólogo?",
       icon: "question",
       showCancelButton: true,
       confirmButtonColor: "#3085d6",
       cancelButtonColor: "#d33",
       confirmButtonText: "Sí",
       cancelButtonText: "Cancelar",
     }).then((result) => {
       if (result.isConfirmed) {
         fetch(`/odontologo/${id}`, { method: "DELETE" })
           .then((response) => {
             console.log(response);
             if (response.ok) {
               Swal.fire({
                 position: "center",
                 icon: "success",
                 text: "Odontólogo eliminado correctamente",
                 showConfirmButton: false,
                 timer: 2000,
               });

               fetchOdontologos(); // Llama a la función que recarga los odontólogos
             } else {
               throw new Error("Error al eliminar el odontólogo");
             }
           })
           .catch((error) => {
             console.error("Error:", error);
             Swal.fire({
               position: "center",
               icon: "error",
               text: "Hubo un error al eliminar el odontólogo.",
               showConfirmButton: false,
               timer: 2000,
             });
           });
       }
     });
   }
