const temas = [];
const questions = [];

const colors = [
  '#a4a3bd',
  '#cecb8a',
  '#fdf35d',
  '#9697cc',
];

const colorMap = {};

let playingQuestions = [];
let currentQuestion = null;
let score = 0;

window.addEventListener("load", () => {

  // Node elements
  // forms
  const temasForm = document.getElementById('formTemas');
  const preguntasForm = document.getElementById('formPreguntas');
  const jugarForm = document.getElementById('formJugar');
  // sections
  const adminSection = document.getElementById('gestion');
  const playSection = document.getElementById('jugar');
  // buttons
  const adminButton = document.querySelector('a[href="#gestion"]');
  const playButton = document.querySelector('a[href="#jugar"]');
  const sortButtons = document.querySelectorAll('input[name="order"]');
  const ayudaButton = document.getElementById('ayuda');
  const siguienteButton = document.getElementById('siguiente');
  const terminarButton = document.getElementById('terminar');
  // others
  const maximoPuntaje = document.getElementById('maximoPuntaje');

  maximoPuntaje.innerHTML = localStorage.getItem('highScore') || 'sin datos';


  // Event listeners
  temasForm.addEventListener('submit', agregarTema);
  preguntasForm.addEventListener('submit', agregarPregunta);
  jugarForm.addEventListener('submit', jugar);
  sortButtons.forEach(button => {
    button.addEventListener('change', (event) => {
      const isChecked = event.target.checked;
      const ascending = event.target.id === 'asc';

      cargarPreguntas(true, isChecked && ascending);
    });
  });
  adminButton.addEventListener('click', () => {
    playSection.style.display = 'none';
    adminSection.style.display = 'block';
  });
  playButton.addEventListener('click', () => {
    adminSection.style.display = 'none';
    playSection.style.display = 'block';
  });
  ayudaButton.addEventListener('click', getHelp);
  siguienteButton.addEventListener('click', nextQuestion);
  terminarButton.addEventListener('click', finishGame);

  // Ask if user wants to load data himself or wants to use the test data
  const quiereAgregarDatos = confirm("¿Desea cargar datos?");

  quiereAgregarDatos ?
    adminSection.style.display = 'block'
    : playSection.style.display = 'block';

  cargarTemas(quiereAgregarDatos);
  cargarPreguntas(quiereAgregarDatos, true);

});

// Temas

function actualizarTemas() {
  cargarTemas(true);
  cargarPromedioPreguntasPorTema();
  cargarTemasSinPreguntas();
  assignateColors();
}

function temaExiste(tema) {
  return temas.filter(t => t.nombre === tema.nombre).length > 0;
}

function addTemaToUl(node, tema) {
  const option = document.createElement('li');
  const text = document.createElement('p');

  text.innerHTML = `${ tema.nombre }: ${ tema.descripcion }`;

  option.appendChild(text);
  node.appendChild(option);
}

function addTemaToSelect(node, tema) {
  const option = document.createElement('option');

  option.value = tema.nombre;
  option.text = tema.nombre;

  node.appendChild(option);
}

function cargarTemas(quiereAgregarDatos) {

  const listaTemas = document.getElementById('listaTemas');
  const temasTotal = document.getElementById('temasTotal');
  const temasSelect = document.getElementById('temaPregunta');

  if (!quiereAgregarDatos) {
    preguntas.forEach(pregunta => {
      const temaExists = temaExiste(pregunta.tema);

      if (!temaExists) {
        temas.push(new Tema(pregunta.tema.nombre, pregunta.tema.descripcion));
      }
    });
  }

  if (temas.length > 0) {
    listaTemas.innerHTML = "";

    temasTotal.innerHTML = temas.length;
    temas.forEach(tema => {
      addTemaToUl(listaTemas, tema);
      addTemaToSelect(temasSelect, tema);
    });

    assignateColors();
  }

}

function agregarTema(event) {
  event.preventDefault();

  const nombre = document.getElementById('nombreTema').value;
  const descripcion = document.getElementById('descripcionTema').value;

  const tema = new Tema(nombre, descripcion);

  if (temaExiste(tema)) {
    alert("El tema ya existe");
    return;
  }

  temas.push(tema);
  actualizarTemas();
}

function cargarPromedioPreguntasPorTema() {
  const promedioNode = document.getElementById('promedioPreguntas');
  const promedio = questions.length / temas.length;

  promedioNode.innerHTML = promedio;
}

function cargarTemasSinPreguntas() {
  const node = document.getElementById('listaTemasSinPreguntas');

  node.innerHTML = "";

  temas.forEach(tema => {
    const temaHasQuestion = questions.filter(q => q.tema.nombre === tema.nombre).length > 0;

    if (!temaHasQuestion) {
      addTemaToUl(node, tema);
    }
  });
}

function assignateColors() {
  let i = 0;
  temas.forEach(tema => {
    if (i >= colors.length) {
      i = 0;
    }

    const color = colors[i];
    colorMap[tema.nombre] = color;

    i++;
  });
}

// Preguntas

function actualizarPreguntas() {
  cargarPreguntas(true);
}

function preguntaExiste(pregunta) {
  return questions.filter(q => q.texto === pregunta.texto).length > 0;
}

function cargarPreguntas(quiereAgregarDatos, ascending) {
  const tablaPreguntas = document.getElementById('tablaPreguntas');
  const preguntasTotal = document.getElementById('preguntasTotal');
  const temasSelectJugar = document.getElementById('temaJugar');

  if (!quiereAgregarDatos) {
    preguntas.forEach(pregunta => {
      const preguntaExists = preguntaExiste(pregunta);

      if (!preguntaExists) {
        questions.push(pregunta);
      }
    });
  }

  if (questions.length > 0) {
    const body = tablaPreguntas.getElementsByTagName('tbody')[0];
    body.innerHTML = "";
    preguntasTotal.innerHTML = questions.length;

    const questionsSorted = sortQuestions(ascending);

    questionsSorted.forEach(pregunta => {
      const row = body.insertRow();
      const cell1 = row.insertCell(0);
      const cell2 = row.insertCell(1);
      const cell3 = row.insertCell(2);
      const cell4 = row.insertCell(3);
      const cell5 = row.insertCell(4);

      cell1.innerHTML = pregunta.tema.nombre;
      cell2.innerHTML = pregunta.nivel;
      cell3.innerHTML = pregunta.texto;
      cell4.innerHTML = pregunta.respuestaCorrecta;
      cell5.innerHTML = pregunta.respuestasIncorrectas.join(', ');

      row.style.backgroundColor = colorMap[pregunta.tema.nombre];

    });

    temas.forEach(tema => {
      const hasQuestion = questions.filter(q => q.tema.nombre === tema.nombre).length > 0;

      if (hasQuestion) {
        addTemaToSelect(temasSelectJugar, tema);
      }
    });
  }
}

function agregarPregunta(event) {
  event.preventDefault();

  const texto = document.getElementById('textoPregunta').value;
  const respuestaCorrecta = document.getElementById('respuestaPregunta').value;
  const respuestasIncorrectas = document.getElementById('respuestasIncorrectas').value;
  const nivel = document.getElementById('nivelPregunta').value;
  const nombreTema = document.getElementById('temaPregunta').value;

  const pregunta = new Pregunta(
    texto,
    respuestaCorrecta,
    respuestasIncorrectas.split(", "),
    nivel, temas.find(t => t.nombre === nombreTema),
  );

  if (preguntaExiste(pregunta)) {
    alert("La pregunta ya existe");
    return;
  }

  questions.push(pregunta);
  actualizarPreguntas();
}

function sortQuestions(ascending) {
  // Sort questions by text
  if (ascending) {
    return questions
      .sort((a, b) => a.nivel - b.nivel)
      .sort((a, b) => a.tema.nombre.localeCompare(b.tema.nombre));
  }

  return questions
    .sort((a, b) => a.nivel - b.nivel)
    .sort((a, b) => b.tema.nombre.localeCompare(a.tema.nombre));
}

// Jugar

function jugar(event) {
  event.preventDefault();

  const tema = document.getElementById('temaJugar').value;
  const nivel = document.getElementById('nivelPreguntas').value;
  const playArea = document.getElementById('jugarPreguntas');

  const preguntasTema = questions.filter(q => q.tema.nombre === tema && q.nivel == nivel);
  playingQuestions = [...preguntasTema];

  currentQuestion = getRandQuestion(playingQuestions);
  generateButtons(currentQuestion);

  playArea.style.display = 'block';
}

function getRandQuestion(questions) {
  return questions[Math.floor(Math.random() * questions.length)];
}

function generateButtons(question) {
  const textoPreguntaNode = document.getElementById('textoPreguntaJugar');
  const respuestasNode = document.getElementById('respuestasJugar');
  const puntajeNode = document.getElementById('puntajeActual');
  const nextQuestionButton = document.getElementById('siguiente');

  const correctAudio = document.getElementById('correct');
  const wrongAudio = document.getElementById('wrong');

  const answers = [ question.respuestaCorrecta, ...question.respuestasIncorrectas ];

  textoPreguntaNode.innerHTML = question.texto;
  textoPreguntaNode.style.backgroundColor = colorMap[question.tema.nombre];

  respuestasNode.innerHTML = "";

  answers.sort(() => Math.random() - 0.5).forEach(answer => {
    const button = document.createElement('button');

    button.innerHTML = answer;
    button.addEventListener('click', (event) => {
      if (answer === question.respuestaCorrecta) {
        score += 10;
        event.target.style.backgroundColor = 'green';
        correctAudio.play();
      } else {
        score = score < 1 ? 0 : score - 1;
        event.target.style.backgroundColor = 'red';
        wrongAudio.play();
      }

      puntajeNode.innerHTML = score;
    });

    button.style.backgroundColor = colorMap[question.tema.nombre];

    respuestasNode.appendChild(button);
  });

  if (!hasNextQuestion()) {
    nextQuestionButton.disabled = true;
  }
}

function getHelp() {
  const help = currentQuestion.respuestaCorrecta.charAt(0);

  alert(`La respuesta correcta comienza con la letra: ${ help }`);
}

function nextQuestion() {
  playingQuestions = playingQuestions.filter(q => q.texto !== currentQuestion.texto);

  if (playingQuestions.length < 1) {
    alert('No hay más preguntas');
    return;
  }

  currentQuestion = getRandQuestion(playingQuestions);
  generateButtons(currentQuestion);
}

function finishGame() {
  const playArea = document.getElementById('jugarPreguntas');
  const maximoPuntaje = document.getElementById('maximoPuntaje');

  const highScore = localStorage.getItem('highScore') || 0;

  if (score > highScore) {
    localStorage.setItem('highScore', score);
    maximoPuntaje.innerHTML = score;
  }

  playArea.style.display = 'none';

  alert(`Puntaje final: ${ score }`);
}

function hasNextQuestion() {
  return playingQuestions.length > 1;
}



