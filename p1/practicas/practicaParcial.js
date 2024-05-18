const events = [];

function getAvgAssistancePrivateEvents(events) {
    const privateEvents = events.filter(event => event.type === 'privado');
    const totalAssistance = privateEvents.reduce((acc, e) => acc + e.quantity, 0);
    return Math.round(totalAssistance / privateEvents.length * 100) / 100;
}


function ejercicio1 () {
    let finish = false;

    do {
        const eventType = window.prompt('Ingrese el tipo de evento o "fin" para salir \n');
        if (eventType === 'fin') {
            finish = true;
            continue;
        }
        const eventQuantity = window.prompt('Ingrese la cantidad de personas o "fin" para salir \n');
        if (eventQuantity === 'fin') {
            finish = true;
            continue;
        }

        if (eventType === '' || eventQuantity === '') continue;
        const event = {
            type: eventType,
            quantity: Number(eventQuantity)
        }
        events.push(event);

    } while (!finish);

    const avgAssistancePrivateEvents = getAvgAssistancePrivateEvents(events);
    if (events.length === 0) {
        window.alert("No se ingresaron eventos");
        return;
    }

    if (isNaN(avgAssistancePrivateEvents)) {
        window.alert("No hay eventos privados");
        return;
    }

    window.alert("El promedio de asistentes a eventos privados es: ", avgAssistancePrivateEvents);
}


function ejercicio2 () {
    
    const paragraph = window.prompt('Ingrese un párrafo');

    if (paragraph.length > 20) {

        const commas = paragraph.split(',')
        commas.shift();

        const allHaveSpaceInFront = commas.filter(c => c[0] === ' ').length === commas.length;

        if (allHaveSpaceInFront) {
            window.alert("Es valido");
            return;
        }

        window.alert("No es valido");
        return;
    }

    window.alert("No es valido");
}


function ejercicio3 (a, b) {
    const sum = a + b;
    console.log(sum.toString().replace("0", ""));
}
