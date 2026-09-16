# JustPizza

App Android (Java) per ordinare da una pizzeria locale.

**Corso:** Programmazione di Dispositivi Mobili (PDM) — Università di Milano-Bicocca  
**A.A.:** 2025/2026

## Gruppo

**Nome gruppo:** JustPizza

| Nome   | Cognome | Matricola |
|--------|---------|-----------|
| Yevhen | Kukhar  | 899720    |

## Descrizione

JustPizza è un’applicazione Android in Java per ordinare da una pizzeria locale. L’utente si registra e accede con Firebase Authentication, consulta il menu (pizze, bibite e contorni), aggiunge i prodotti al carrello e conferma l’ordine dopo aver inserito un indirizzo di consegna entro 5 km dalla pizzeria.

Il menu e gli ordini sono sincronizzati con Cloud Firestore; in locale i dati restano in Room, così l’app resta usabile anche offline. Il pagamento è simulato: se l’indirizzo è nel raggio e c’è connessione, l’ordine viene salvato e il carrello svuotato.