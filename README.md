# minim-exam
Examen Minim 1
by: Alba Roma Gómez  

## PART 1
  
Funcionen totes les funcions de la PART 1, tests unitaris funcionen correctament.  
  
**Comentaris respecte les classes/objectes triats:**  
- User (Jugador)  
- Game (Joc)  
- Play (Partida)  
- LevelInfo (Activitat d'un jugador en un nivell de partida) - VO  
  
**Comentari respecte estructures de dades triades per a cada cas:**  
    - IMPLEMENTACIÓ. Hi ha una llista del catàleg de jocs i un hashmap d'usuaris/jugadors     
    - USER. S'ha escollit un hashmap per a guardar les partides jugades per part d'un jugador. <gameId, Play>, ja que segons el meu criteri: només es vol guardar registre d'**una** partida per cada joc jugat. Si es torna a jugar un joc, s'actualitzarà la informació de la partida amb l'id del joc corresponent.   
    - PLAY. Dins d'una partida hi ha una llista de nivells superats (LevelInfo) on s'afegeix l'activitat del jugador si es passa un nivell.  

**Altres comentaris**  
    - S'ha creat un altre VO -**CurrentGame**- que conté un id de joc i el nivell actual, ja que en l'enunciat es demana retornar el nivell actual i el joc en el qual s'esta jugant la partida.  
    - S'ha creat tres Transfer Objects per a facilitar la serialització d'alguns components.  

## PART 2  
El servei de swagger funciona correctament, a l'hora de testejar amb aquesta eina, es poden utilitzar els usuaris predeterminats amb id/noms: (Alba, Maria, Paula) i els jocs amb id: (1A, 2A, 3A)