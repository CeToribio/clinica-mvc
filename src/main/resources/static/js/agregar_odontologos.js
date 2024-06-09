const form = document.getElementById("agregarForm");


form.addEventListener("submit", function (event) {
  event.preventDefault();

  const nombre = document.getElementById("nombre").value;
  const apellido = document.getElementById("apellido").value;
  const matricula = document.getElementById("matricula").value;

  // llamando al endpoint de agregar

  fetch(`/odontologo`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ nombre, apellido, matricula }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
       Swal.fire({
              icon: 'success',
              title: '¡Éxito!',
              text: 'Odontólogo agregado correctamente',
              confirmButtonColor: '#565656',
            });
      form.reset(); // Resetear el formulario
    })
    .catch((error) => {
      console.error("Error agregando odontólogo:", error);
    });
});
