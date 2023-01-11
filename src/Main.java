import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(100, 5.5,"P A");
    static Player enemyA = new Player(100, 4.4, "E A");
    static byte Turn = -1;
    static byte gameRounds;
    public static void main(String[] args) {

        Turn = 0;
        do {
            RoundHeader();

            byte scannerInput = scanner.nextByte();

            options(scannerInput);

            if (Turn == 1) {
                enemyTurn();
            }

        } while (player.health > 0 || enemyA.health < 0);
    }
    static void RoundHeader ()
    {
        System.out.println("=== Enemy stats ===\n");
        System.out.println("Health: " + enemyA.health);
        System.out.println("Damage: " + enemyA.damage);
        System.out.println("============");

        System.out.println("============");
        System.out.println("1) Attack\n");

        System.out.println("=== Player stats ===");
        System.out.println("Health: " + player.health);
        System.out.println("Damage: " + player.damage);
        System.out.println("============");
    }
    static void options (byte scannerInput)
    {
        if (Turn == 0) {
            switch (scannerInput) {
                case 1:
                    player.bonusDamage = ThreadLocalRandom.current().nextInt(0, 10);
                    enemyA.health = (int) (enemyA.health - player.damage - player.bonusDamage);
                    System.out.println(player.name + " did " + (player.damage + player.bonusDamage) + " damage!");
                    if (enemyA.health <= 0) {
                        System.out.println(enemyA.name + " has died!");
                    }
                    Turn = 1;
                    break;
            }
        }
    }
    static void enemyTurn()
    {
        System.out.println("\n Enemy turn to fight!\n");

        System.out.println("Enemy has decided to Attack!");
        enemyA.bonusDamage = ThreadLocalRandom.current().nextInt(1, 16);
        player.health = (int) (player.health - enemyA.damage - enemyA.bonusDamage);
        System.out.println("Enemy did " + (enemyA.damage + enemyA.bonusDamage) + " damage!");
        Turn = 0;
        System.out.println("Turn: " + Turn);

        if (player.health <= 0) {
            System.out.println(player.name + " has died!");
        }
    }
}