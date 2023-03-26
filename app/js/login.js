// validacion formulario
const validarForm = () => {
  // funciones auxiliares
  const validadorMail = (mail) => mail && mail.includes("@");
  const validadorNombreApellido = (nombre) => nombre && nombre.length > 4;
  const tieneNumeros = (str) => /\d/.test(str);
  const validadorContrasena = (pswd) => {
    const malas = ["1234", "admin1", "odio a mis Aux >:(2"];
    return tieneNumeros(pswd) && !malas.includes(pswd);
  };

  // obtener el fomulario del DOM por el ID
  // let loginForm = document.getElementById("login-form");

  // obtener inputs del DOM por el ID
  let emailInput = document.getElementById("email");
  let nombreInput = document.getElementById("nombre");
  let apellidoInput = document.getElementById("apellido");
  let pswdInput = document.getElementById("contrasenna");

  let msg = "";

  if (!validadorMail(emailInput.value)) {
    msg += "Mail malo!\n";
    emailInput.style.borderColor = "red"; // cambiar estilo con JS!!
  } else {
    emailInput.style.borderColor = "";
  }

  if (!validadorNombreApellido(nombreInput.value)) {
    msg += "Nombre malo!\n";
    nombreInput.style.borderColor = "red";
  } else {
    nombreInput.style.borderColor = "";
  }

  if (!validadorNombreApellido(apellidoInput.value)) {
    msg += "Apellido malo!\n";
    apellidoInput.style.borderColor = "red";
  } else {
    apellidoInput.style.borderColor = "";
  }

  if (!validadorContrasena(pswdInput.value)) {
    msg += "Contraseña mala!\n";
    pswdInput.style.borderColor = "red";
  } else {
    pswdInput.style.borderColor = "";
  }

  if (msg === "") {
    msg = "Felicidades ya tienes una cuenta!";
    // loginForm.submit();
  }
  alert(msg); // alertas JS
};

// recuperamos el boton que envia el form
let submitBtn = document.getElementById("envio");
submitBtn.addEventListener("click", validarForm);
