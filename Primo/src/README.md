
# Primo Esercizio Socket
### TRACCIA
Un tecnico di rete deve testare un server che trasforma i messaggi in maiuscolo per verificare
la qualità della rete e della trasmissione dei dati. Realizza un’applicazione client-server in cui
il client invia messaggi di testo al server, che a sua volta risponde con lo stesso testo convertito
in maiuscolo. Nel momento in cui il client invia un messaggio vuoto, il server restituisce un
messaggio di errore. Alla digitazione del numero 0, il client chiude la connessione e termina.
N.B. Il tecnico, non conoscendo bene le librerie Java, non sa che esiste una funzione dedicata
alla conversione delle stringhe da minuscolo a maiuscolo, quindi per aiutarlo dovrai codificare
la logica di conversione


### CONSIDERAZIONI
Per poter effettuare la conversione da caratteri in minuscolo a maiuscolo ho deciso di utilizzare la tabella ASCII e convertire ogni carattere partendo dal suo valore ASCII da minuscolo e calcolarmi utilizzando una differenza il suo valore da maiuscolo, in questo caso la differenza è pari a 32. Un' altra soluzione che si poteva applicare era creare una relazione tra ogni carattere minuscolo e il suo valore maiuscolo e usare questa associazione carattere per carattere per ottenenere la frase maiuscola.

### AMBIENTE DI SVILUPPO
- INTELLIJ IDEA 2026.2.3

- JAVA VERSIONE 26.0.1


