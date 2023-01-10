import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player();
    static Player enemyA = new Player();
    static byte Turn = -1;
    public static void main(String[] args) {

        /*Make an option to end turn for the player.
         Now it is the enemy turn to damage the player then
         the player's turn
         */
        Turn = 0;
        PlayerAndEnemyCreation();

        do {
            PlayerOptionsHeader();

            byte scannerInput = scanner.nextByte();

            options(scannerInput);

            if (Turn == 1) {
                enemyTurn();
            }

        } while (player.health > 0 || enemyA.health < 0);
    }
    static void PlayerAndEnemyCreation ()
    {
        player.health = 100;
        player.name = "Player A";
        player.damage = 5.5;

        enemyA.health = 127;
        enemyA.name = "Enemy A";
        enemyA.damage = 4.4;
    }
    static void PlayerOptionsHeader ()
    {
        System.out.println("\n Your turn to fight!\n");
        System.out.println("============");
        System.out.println("1) Attack");
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

                    if (player.health <= 0) {
                        System.out.println(player.name + " has died!");
                    } else {
                        System.out.println(enemyA.name + " has a remaining health of " + enemyA.health + ".");
                    }
                    Turn = 1;
                    break;
            }
        }
    }
    static void enemyTurn()
    {
        System.out.println("\n Enemy turn to fight!\n");
        System.out.println("=== Enemy stats ===\n");
        System.out.println("Health: " + enemyA.health);
        System.out.println("Damage: " + enemyA.damage);
        System.out.println("============");


        System.out.println("Enemy has decided to Attack!");
        enemyA.bonusDamage = ThreadLocalRandom.current().nextInt(1, 16);
        player.health = (int) (player.health - enemyA.damage - enemyA.bonusDamage);
        System.out.println("Enemy did " + enemyA.damage + " damage");
        Turn = 0;
        System.out.println("Turn: " + Turn);
    }
}