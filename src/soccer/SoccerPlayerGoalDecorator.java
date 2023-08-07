public class SoccerPlayerGoalDecorator implements SoccerPlayerComponent {
    private SoccerPlayerComponent decoratedPlayer;

    public SoccerPlayerGoalDecorator(SoccerPlayerComponent decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void display() {
        decoratedPlayer.display();
        displayGoalStats();
    }

    private void displayGoalStats() {
        System.out.println("Goles marcados: " + decoratedPlayer.getGoal());
    }
}
