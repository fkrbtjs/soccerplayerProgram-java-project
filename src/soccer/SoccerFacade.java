public class SoccerFacade {
    private DBConnection dbc;

    public SoccerFacade() {
        dbc = new DBConnection();
        dbc.connect();
    }

     public void playerInputData() {
        // Código para la entrada de datos de los jugadores
        // ...
        dbc.insert(soccerPlayer);
    }

    public void updatePlayerData() {
        // Código para la actualización de datos de los jugadores
        // ...
        dbc.update(imsiSoccerPlayer);
    }

    public void deletePlayerData() {
        // Código para eliminar datos de jugadores
        // ...
        dbc.delete(name);
    }

    public void searchPlayerData() {
        // Código para buscar datos de jugadores
        // ...
        dbc.selectSearch(name);
    }

    public void playerOutput() {
        // Código para mostrar datos de jugadores
        // ...
        dbc.select();
    }

    // Otros métodos para operaciones relacionadas con jugadores
    // ...

    public void closeConnection() {
        dbc.close();
    }
}



