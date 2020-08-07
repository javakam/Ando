package design.behavioral.state;

import design.behavioral.state.ui.Player;
import design.behavioral.state.ui.UI;

/**
 * Demo class. Everything comes together here.
 */
public class Main {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}