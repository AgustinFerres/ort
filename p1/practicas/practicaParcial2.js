function ejercicio1() {

    const qtyRain = [];


    for (let i = 0; i < 30; i++) {

        let rainNumber;
        let finish = false;
        while (!finish) {
            const rain = window.prompt('Ingrese la cantidad de lluvia');
            rainNumber = Number(rain);
            if (isNaN(rainNumber)) {
                continue;
            }
            if (rainNumber > 0 && rainNumber <= 300) {
                finish = true;
            }
        }

        qtyRain.push(rainNumber);
    }

    const rainByDay = qtyRain.map((rain, index) => {
        return {
            day: index + 1,
            rain: rain
        }
    });

    const rainSorted = rainByDay.sort((a, b) => {
        const result = b.rain - a.rain;
        return result === 0 ? a.day - b.day : result;
    });

    console.log(rainByDay);
    console.log(rainSorted);
} 