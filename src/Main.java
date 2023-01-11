import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(100, 5.5,"Player A");
    static Player enemyA = new Player(100, 4.4, "Enemy A");
    static byte Turn = -1;
    static byte Rounds;
    public static void main(String[] args) {

        Turn = 0;
        do {
            RoundHeader();

            byte scannerInput = scanner.nextByte();

            options(scannerInput);

            if (Turn == 1) {
                enemyTurn();
                Rounds++;
            }
        } while (player.health > 0 || enemyA.health < 0);
    }
    static void RoundHeader ()
    {
        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("=== " + enemyA.name + " stats ===\n");
        System.out.println("Health: " + enemyA.health);
        System.out.println("Damage: " + enemyA.damage);
        System.out.println("============");

        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("1) Attack\n");

        System.out.println("=== " + player.name + " stats ===");
        System.out.println("Health: " + player.health);
        System.out.println("Damage: " + player.damage);
        System.out.println("============");
    }
    static void options (byte scannerInput)
    {
        if (Turn == 0) {
            switch (scannerInput) {
                case 1:
                    if (enemyA.health > 0) {
                        player.bonusDamage = ThreadLocalRandom.current().nextInt(0, 10);
                        enemyA.health = (int) (enemyA.health - player.damage - player.bonusDamage);
                        System.out.println(player.name + " did " + (player.damage + player.bonusDamage) + " damage!");
                        Turn = 1;
                        if (enemyA.health <=0) {
                            System.out.println(enemyA.name + " has died!");
                            System.out.println("====== Round(s) " + Rounds + "======");
                        }
                        break;
                    }
                    else {
                        System.out.println(enemyA.name + " has died!");
                    }
            }
        }
    }
    static void enemyTurn()
    {
            System.out.println("\n " + enemyA.name + " turn to fight!\n");
            enemyA.bonusDamage = ThreadLocalRandom.current().nextInt(1, 16);
            player.health = (int) (player.health - enemyA.damage - enemyA.bonusDamage);
            System.out.println(enemyA.name + " did " + (enemyA.damage + enemyA.bonusDamage) + " damage!");
            Turn = 0;
        if (player.health <= 0) {
            System.out.println(player.name + " has died!");
            System.out.println("====== Round(s) " + Rounds + "======");
        }
    }
}