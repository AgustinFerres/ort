
function ejercicio3 () {
    const banderines = prompt("Introduce el string de banderines");

    let lettersArray = banderines.split("");

    const word = prompt("Introduce la palabra a buscar");

    let count = 0;
    let finish = false;

    while (!finish) {
        let letterCount = 0;
        for (let i = 0; i < word.length; i++) {
            const index = lettersArray.indexOf(word[i]);
            if (index === -1) {
                finish = true;
                continue;
            } 

            lettersArray.splice(index, 1);
            letterCount++;
        }
        console.log(banderines);

        if (letterCount === word.length) count++;
    }

    alert(`La palabra ${word} se repite ${count} veces`);
}

const prueba = "dasda"