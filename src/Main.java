import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(100, 0,"Player A");
    static Player enemyA = new Player(100, 50, "Enemy A");
    static byte Turn = -1;
    static byte Rounds;
    public static void main(String[] args) {

        Turn = 0;
        while (enemyA.health > 0 && player.health > 0) {
            RoundHeader();

            byte scannerInput = scanner.nextByte();

            options(scannerInput);

            if (Turn == 1) {
                enemyTurn();
                Rounds++;
            }
        }
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

                        playerRandomAttackDamageBonus(5, 15);

                        endTurn((byte) 1, player);

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
        if (enemyA.health > 0) {
            System.out.println("\n " + enemyA.name + " turn to fight!\n");

            enemyRandomAttackDamageBonus(5, 25);

            endTurn((byte) 0 , enemyA);

            if (player.health <= 0) {
                System.out.println(player.name + " has died!");
                System.out.println("====== Round(s) " + Rounds + "======");
            }
        }
    }
    static void endTurn(byte turn, Player character)
    {
        Turn = turn;
        System.out.println("End turn for " + character.name);
    }
    static void playerRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        player.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        enemyA.health = (int) (enemyA.health - player.damage - player.bonusDamage);
        System.out.println(player.name + " did " + player.damage + " base damage \n and " + player.bonusDamage + " bonus damage!");
    }
    static void enemyRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        enemyA.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        player.health = (int) (player.health - enemyA.damage - enemyA.bonusDamage);
        System.out.println(enemyA.name + " did " + enemyA.damage + " base damage \n and " + enemyA.bonusDamage + " bonus damage!");

    }
}