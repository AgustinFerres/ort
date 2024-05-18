
function ejercicio1 () {
    const numero = Number(window.prompt('Introduce un número'));

    const pagos = [];

    for (let i = 0; i < numero; i++) {
        const gastos = Number(window.prompt('Ingresa gastos'));

        let propina = gastos + 1;
        do {
            propina = Number(window.prompt('Ingresa propina'));
        } while (propina >= gastos);

        pagos.push({gastos, propina});
    }
    const pagosPropinaAlta = pagos.filter(p => p.propina > p.gastos * 0.2);

    let total = 0;
    pagos.forEach(p => total += p.gastos);

    window.alert(`El total de pagos con propina mayor al 20% es: ${pagosPropinaAlta.length}`);
    window.alert(`El total de gastos es: ${total}`);
}


function ejercicio2 () {
    const numero = window.prompt('Introduce un número');
    const numeroArray = numero.split('');

    const ultimoNumero = numeroArray[numero.length - 1];
    const tiene5 = numeroArray.indexOf('5');
    const tiene0 = numeroArray.indexOf('0');

    if (ultimoNumero == 5 || ultimoNumero == 0) {
        window.alert("Es multiplo");
    } else if (tiene5 != -1 || tiene0 != -1) {
        
        tiene5 != -1 ? numeroArray.splice(tiene5, 1) : numeroArray.splice(tiene0, 1);

        tiene5 != -1 ? numeroArray.push('5') : numeroArray.push('0');

        window.alert("No es multiplo, posible reacomodo: " + numeroArray.join(''));
    } else {
        window.alert("No es multiplo");
    }
}


function ejercicio3 () {
    const numero = window.prompt('Introduce un número');

    for (let i = 0; i < numero; i++) {
        let string = "";
        for (let j = 0; j < i + 1; j++) {
            string += " 1/" + (j + 1);            
        }
        console.log(string);
    }
        
}