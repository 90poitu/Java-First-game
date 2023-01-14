import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Player player = new Player(100, 0,"Player");
    static Player enemyA = new Player(100, 0, "Enemy (A.I)");
    static byte Turn = -1;
    static byte Rounds;
    static char prefix = '>';
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
        System.out.println("1) " + enemyA.name.substring(6, 11) + " Attack");
        System.out.println("2) " + enemyA.name.substring(6, 11) + " Health recharge\n");
        System.out.println("2) " + enemyA.name.substring(6, 11) + " Health recharge\n");
        System.out.println("=== " + enemyA.name + " stats ===\n");
        System.out.println("Health: " + enemyA.health + "/100");
        if (Rounds <= 0)
        {
            System.out.println("Damage: " + enemyA.damage + " (bonus damage may apply)");
        }
        else{
            System.out.println("Damage: " + enemyA.damage);
        }
        System.out.println("============");

        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("Options for the " + player.name);
        System.out.println("1) " + player.name + " Attack");

        System.out.println("=== " + player.name + " stats ===");
        System.out.println("Health: " + player.health + "/100");
        if (Rounds <= 0)
        {
            System.out.println("Damage: " + player.damage + " (bonus damage may apply)");
        }
        else{
            System.out.println("Damage: " + player.damage);
        }
        System.out.println("============");
        System.out.println(prefix + " " + player.name + " turn!");
    }
    static void options (byte scannerInput)
    {
        if (Turn == 0) {
            switch (scannerInput) {
                case 1:
                    if (enemyA.health > 0) {
                        playerRandomAttackDamageBonus(0, 2);

                        endTurn((byte) 1, player);

                        if (enemyA.health <=0)
                        {
                            System.out.println(enemyA.name + " has died!");
                            System.out.println("====== Round(s) " + Rounds + "======");
                        }
                        break;
                    }
                default:
                    endTurn((byte)  1, player);
                    break;
            }
        }
    }
    static void enemyTurn()
    {
        if (enemyA.health > 0) {

            System.out.println("\n" + prefix + " " + enemyA.name + " turn!\n");
            // 0 - 2
            int RandomDecision = ThreadLocalRandom.current().nextInt( 0, 4);

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
        System.out.println(prefix + " " + player.name + " did " + player.damage + " base damage \n and " + (int) player.bonusDamage + " bonus damage!");
    }
    static void enemyRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        enemyA.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        player.health = (int) (player.health - enemyA.damage - enemyA.bonusDamage);
        System.out.println(prefix + " " + enemyA.name + " did " + enemyA.damage + " base damage \n and " + (int) enemyA.bonusDamage + " bonus damage!");

    }
    static void enemyRandomHealthRegenerate(byte minRegenerateValue, byte maxRegenerateValue)
    {
            double healthGenerate = ThreadLocalRandom.current().nextDouble(minRegenerateValue, maxRegenerateValue);
            enemyA.health = enemyA.health + (int) healthGenerate;
            if (enemyA.health > 100) {
                enemyA.health = 100;
            }
            System.out.println(prefix + " " + enemyA.name + " use it's ability to heal itself. \n " + enemyA.name + " now has " + (int) enemyA.health + " health. \n");
    }
    static void enemyBoostedDamage(float minBoostedDamageValue, float maxBoostedValue) {
        int randomBoostedDamage = ThreadLocalRandom.current().nextInt((int) minBoostedDamageValue, (int) maxBoostedValue);
        enemyA.damage = enemyA.damage + randomBoostedDamage;
        System.out.println(enemyA.name + " uses it's ability to boost it's damage\n");
    }
    static void enemyRandomDescision(int RandomDecision)
    {
        switch (RandomDecision) {
            case 0:
                enemyRandomAttackDamageBonus(0, 2);
                break;
            case 1:
                if (enemyA.health > 0 && enemyA.health <= 100) {

                    enemyRandomHealthRegenerate((byte) 5, (byte) 15.2);
                }
                else {
                        System.out.println(enemyA.name + " wanted to use it's ability to heal itself but \n is already on " + enemyA.health + "/100\n");
                    }
                break;
            case 2:
                System.out.println(enemyA.name+" skipped their turn!");
                break;
            case 3:
                enemyBoostedDamage(0, 3);
                break;
        }
    }
}