import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(100, 4.4,"Player");
    static Player enemyA = new Player(100, 5.5, "Enemy (A.I)");
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
        System.out.println("Options for the " + enemyA.name);
        System.out.println("1) (A.I) Attack");
        System.out.println("2) (A.I) Health recharge");
        System.out.println("=== " + enemyA.name + " stats ===\n");
        System.out.println("Health: " + enemyA.health);
        System.out.println("Damage: " + enemyA.damage);
        System.out.println("============");

        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("Options for the " + player.name);
        System.out.println("1) Attack\n");

        System.out.println("=== " + player.name + " stats ===");
        System.out.println("Health: " + player.health);
        System.out.println("Damage: " + player.damage);
        System.out.println("============");
        System.out.println(player.name + " turn to fight!");
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
            }
        }
    }
    static void enemyTurn()
    {
        if (enemyA.health > 0) {

            System.out.println("\n " + enemyA.name + " turn to fight!\n");
            // 0 - 2
            int RandomDecision = ThreadLocalRandom.current().nextInt( 0, 3);

            enemyRandomDescision(RandomDecision);

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
    static void enemyRandomDescision(int RandomDecision)
    {
        switch (RandomDecision) {
            case 0:
                enemyRandomAttackDamageBonus(5, 25);
                break;
            case 1:
                System.out.println("Haven't programmed yet");
                break;
            case 2:
                System.out.println("Haven't programmed yet");
                break;
        }
    }
}