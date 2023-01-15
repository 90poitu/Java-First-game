import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static PlayerClass playerClass = new PlayerClass(100, .00001,"Player");
    static PlayerClass enemyA = new PlayerClass(100, .00001, "Enemy (A.I)");
    static ColorsClass color = new ColorsClass("\u001B[35m", "\u001B[33m", "\u001B[0m");
    static ArrayList<String> enemyOptions, playerOptions, enemyDetails,playerDetails = new ArrayList<String>();
    //static ArrayList<String> playerOptions = new ArrayList<String>();
    //static ArrayList<String> enemyDetails = new ArrayList<String>();
    //static ArrayList<String> playerDetails = new ArrayList<String>();
    static byte Turn, Rounds;
    static char prefix = '>';
    public static void main(String[] args) {

        enemyOptions.add(prefix + color.purple + " " + color.yellow + enemyA.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Attack" + color.colorReset);
        enemyOptions.add(prefix + color.purple + " " + color.yellow + enemyA.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Health recharge" + color.colorReset);
        enemyOptions.add(prefix + color.purple + " " + color.yellow + enemyA.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Skip its turn" + color.colorReset);
        enemyOptions.add(prefix + color.purple + " " + color.yellow + enemyA.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Boost damage" + color.colorReset);

        playerOptions.add(prefix +color.purple + " Attack" + color.colorReset);
        playerOptions.add(prefix +color.purple +" Health recharge\n" + color.colorReset);

        enemyDetails.add(prefix + color.purple+ " Health: " + enemyA.health + color.colorReset);
        enemyDetails.add(prefix + color.purple+ "Base damage: " + (byte)enemyA.damage + color.colorReset);

        playerDetails.add(prefix + color.purple + " Health: " + playerClass.health + color.colorReset);
        playerDetails.add(prefix + "Base damage: " + (byte) playerClass.damage + color.colorReset);

        Turn = 0;

            while (enemyA.health > 0 && playerClass.health > 0) {

                RoundHeader();

                String scannerInput = scanner.nextLine();

                playerTurn(scannerInput);

                if (Turn == 1) {
                    enemyTurn();
                    Rounds++;
                }
            }
        }
    static void RoundHeader ()
    {
        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("Options for the " + enemyA.name + "\n");
        System.out.println(enemyOptions.get(0));
        System.out.println(enemyOptions.get(1));
        System.out.println(enemyOptions.get(2));
        System.out.println(enemyOptions.get(3) + "\n");
        System.out.println("=== " + enemyA.name + " stats ===\n");
        System.out.println(enemyDetails.get(0));
        System.out.println(enemyDetails.get(1));
        System.out.println("============");

        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("Options for the " + playerClass.name + "\n");
        System.out.println(playerOptions.get(0));
        System.out.println(playerOptions.get(1));

        System.out.println("=== " + playerClass.name + " stats ===");
        System.out.println("Health: " + playerClass.health + "/100");
        System.out.println("Damage: " + (byte) playerClass.damage);
        System.out.println("============");
        System.out.println(prefix + " " + playerClass.name + " turn!");
    }
    static void playerTurn (String scannerInput)
    {
        if (Turn == 0) {
            switch (scannerInput) {
                case "1":
                    if (enemyA.health > 0) {
                        playerRandomAttackDamageBonus(0, 2);

                        if (enemyA.health <= 0)
                        {
                            System.out.println(enemyA.name + " has died!");
                            System.out.println("====== Round(s) " + Rounds + "======");
                        }
                        endTurn((byte)  1, playerClass);
                        break;
                    }
                case "2":
                    if (playerClass.health > 0 && playerClass.health < 100)
                    {
                        playerHealthRegenerate((byte) 2, (byte) 25);
                    }
                    else {
                        System.out.println(playerClass.name + " wanted to use it's ability to heal itself but \nis already on " + playerClass.health + "/"+ playerClass.health + "\n");
                    }
                    endTurn((byte)  1, playerClass);
                    break;
                default:
                    endTurn((byte)  0, enemyA);
                    System.out.println(prefix + " " + "Please enter an input.");
                    break;
            }
        }
    }

    static void playerHealthRegenerate(byte minRegenerateValue, byte maxRegenerateValue)
    {
        double healthRegerate = ThreadLocalRandom.current().nextDouble(minRegenerateValue, maxRegenerateValue);
        playerClass.health = playerClass.health + (byte) healthRegerate;
        if (playerClass.health > 100) {
            playerClass.health = 100;
        }
        System.out.println(prefix + " " + playerClass.name + " use it's ability to heal itself. \n " + playerClass.name + " now has " + (int) playerClass.health + " health. \n");
    }

    static void enemyTurn()
    {
        if (enemyA.health > 0) {

            System.out.println("\n" + prefix + " " + enemyA.name + " turn!\n");

            int RandomDecision = ThreadLocalRandom.current().nextInt( 0, 4);

            enemyRandomDescision(RandomDecision);

            endTurn((byte) 0 , enemyA);

            if (playerClass.health <= 0) {
                System.out.println(playerClass.name + " has died!");
                System.out.println("====== Round(s) " + Rounds + "======");
            }
        }
    }
    static void endTurn(byte turn, PlayerClass character)
    {
        Turn = turn;
        System.out.println("End turn for " + character.name);
    }
    static void playerRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        playerClass.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        enemyA.health = (int) (enemyA.health - playerClass.damage - playerClass.bonusDamage);
        System.out.println(prefix + " " + playerClass.name + " did " + playerClass.damage + " base damage \n and " + (int) playerClass.bonusDamage + " bonus damage!");
    }
    static void enemyRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        enemyA.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        playerClass.health = (int) (playerClass.health - enemyA.damage - enemyA.bonusDamage);
        System.out.println(prefix + " " + enemyA.name + " did " + enemyA.damage + " base damage \n and " + (int) enemyA.bonusDamage + " bonus damage!");

    }
    static void enemyRandomHealthRegenerate(byte minRegenerateValue, byte maxRegenerateValue)
    {
            double healthGenerate = ThreadLocalRandom.current().nextDouble(minRegenerateValue, maxRegenerateValue);
            enemyA.health = enemyA.health + (byte) healthGenerate;
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
                endTurn((byte) 0, enemyA);
                break;
            case 3:
                enemyBoostedDamage(0, 3);
                break;
        }
    }
}