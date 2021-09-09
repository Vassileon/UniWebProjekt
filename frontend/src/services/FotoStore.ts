import { reactive, readonly } from 'vue'
import { Foto } from './Foto'
import { Client, Message } from '@stomp/stompjs';

// Gemeinsame State-Variable(n) auf oberster Ebene,
// also ausserhalb der use-Funktion (dürfen nur je
// einmal und nicht nicht je use-Aufruf angelegt werden)
// Oft auch mit einem reactive()-Objekt gelöst

const fotostate = reactive({
    fotos: Array<Foto>(),
    errormessage: ""
})

const wsurl = "ws://localhost:9090/messagebroker";
const DEST = "/topic/foto";
const stompclient = new Client({ brokerURL: wsurl })

stompclient.onConnect = () => {
    // Callback: erfolgreicher Verbindugsaufbau zu Broker
    stompclient.subscribe(DEST, (message) => {
        // Callback: Nachricht auf DEST empfangen
        // empfangene Nutzdaten in message.body abrufbar,
        // ggf. mit JSON.parse(message.body) zu JS konvertieren
        const fotoMessage = JSON.parse(message.body) as FotoMessage;
        updateFotos();
    });
};
stompclient.activate();


// Composition-Function zur Bereitstellung
// von State-Abfrage- und Bearbeitungsmoeglichkeiten
async function deletFoto(id: number) {
    fetch(`/api/foto/${id}`, {
        method: 'DELETE',
    })
        .catch((fehler) => {
            fotostate.errormessage = fehler;
        });
}

async function updateFotos() {
    const fotoliste = new Array<Foto>();
    fetch('/api/foto', {
        method: 'GET',
    })
        .then((response) => {
            if (!response.ok) {
                fotostate.errormessage = "etwas schief gegeangen";
            } else {
                fotostate.errormessage = "";
            }
            // empfangene Payload -> JSON
            return response.json();
        })
        .then((jsondata: Array<Foto>) => {
            jsondata.forEach(element => {
                fotoliste.push(element);
            })
            fotostate.fotos = fotoliste;
        })
        .catch((fehler) => {
            fotostate.errormessage = fehler;
        });
}

export function useFotoStore() {

    // auch hier könnten ref() und reactive() Objekte
    // angelegt werden, die dann nicht ueber mehrere
    // FE-Komponenten geshared, sondern je
    // Nutzerobjekt einzeln angelegt werden

    // nur diese Auswahl nach aussen geben
    return {
        fotostate: readonly(fotostate),
        updateFotos,
        deletFoto
    }
}

