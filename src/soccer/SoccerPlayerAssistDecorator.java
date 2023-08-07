public class SoccerPlayerAssistDecorator implements SoccerPlayerComponent {
    private SoccerPlayerComponent decoratedPlayer;

    public SoccerPlayerAssistDecorator(SoccerPlayerComponent decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void display() {
        decoratedPlayer.display();
        displayAssistStats();
    }

    private void displayAssistStats() {
        System.out.println("Asistencias realizadas: " + decoratedPlayer.getAssist());
    }
}
