import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static PlayerClass player = new PlayerClass(100, .00001,"Player");
    static PlayerClass enemy = new PlayerClass(100, .00001, "Enemy (A.I)");
    static ColorsClass color = new ColorsClass();
    static byte Turn, Rounds, level;
    final static byte minEnemyDecisions = 0;
    final static byte maxEnemyDecisions = 5;
    static short currentExp, minExpGiven, maxExpGiven;
    static short[] exp = new short[10];
    final static char PREFIX = '>';
    public static void main(String[] args) {
        level = 0;
        Turn = 0;
        while (enemy.health > 0 && player.health > 0) {
            levelUp();
            LevelManagement();
            RoundHeader();
                System.out.println(PREFIX + " " + player.name + " turn!");
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
        System.out.println("Options for the " + enemy.name + "\n");
        System.out.println(PREFIX + color.purple + " " + color.yellow + enemy.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Attack" + color.colorReset);
        System.out.println(PREFIX + color.purple + " " + color.yellow + enemy.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Health recharge" + color.colorReset);
        System.out.println(PREFIX + color.purple + " " + color.yellow + enemy.name.substring(6, (11 - 0)) + color.colorReset + color.purple + " Boost damage" + color.colorReset + "\n");
        System.out.println("=== " + enemy.name + " stats ===\n");
        System.out.println(PREFIX + color.purple+ " Health: " + color.colorReset + enemy.health + "/" + 100);
        System.out.println(PREFIX + color.purple+ " Base damage: " + color.colorReset + (byte) enemy.damage + "/" + 20);
        System.out.println("============");

        System.out.println("====== Round(s) " + Rounds + " ======");
        System.out.println("Options for the " + player.name + "\n");
        System.out.println(PREFIX +color.purple + " Attack" + color.colorReset);
        System.out.println(PREFIX +color.purple +" Health recharge\n" + color.colorReset);

        System.out.println("=== " + player.name + " stats ===");
        System.out.println(PREFIX + color.purple + " Health: " + color.colorReset + player.health + "/"+100);
        System.out.println(PREFIX + color.purple + " Base damage: " + color.colorReset + (byte) player.damage);
        System.out.println(PREFIX + color.purple + " Level: " + color.colorReset + level + "/"+( exp.length -1 ));
        System.out.println(PREFIX + color.purple + " Exp: " + color.colorReset + currentExp + "/" + exp[level]);
        System.out.println("============");
    }
    static void playerTurn (String scannerInput)
    {
        if (Turn == 0) {
            switch (scannerInput) {
                case "1":
                        playerRandomAttackDamageBonus(0, 2);

                        if (enemy.health <= 0)
                        {
                            System.out.println(enemy.name + " has died!");
                            System.out.println("====== Round(s) " + Rounds + "======");
                        }
                        break;
                case "2":
                    if (player.health > 0 && player.health < 100)
                    {
                        playerHealthRegenerate((byte) 2, (byte) 25);
                    }
                    else {
                        System.out.println(player.name + " attempted to heal him self" + "\n");
                    }
                    break;
                default:
                    System.out.println(player.name + " Skipped their turn!");
                    break;
            }
            endTurn((byte)  1, player);
        }
    }

    static void playerHealthRegenerate(byte minRegenerateValue, byte maxRegenerateValue)
    {
        double healthRegerate = ThreadLocalRandom.current().nextDouble(minRegenerateValue, maxRegenerateValue);
        player.health = player.health + (byte) healthRegerate;
        if (player.health > 100) {
            player.health = 100;
        }
        System.out.println(PREFIX + " " + player.name + " use it's ability to heal itself. \n " + player.name + " now has " + (int) player.health + " health. \n");
    }
    static void enemyTurn()
    {
        if (enemy.health > 0) {

            System.out.println("\n" + PREFIX + " " + enemy.name + " turn!\n");

            int RandomDecision = ThreadLocalRandom.current().nextInt( minEnemyDecisions, maxEnemyDecisions);

            enemyRandomDescision(RandomDecision);

            endTurn((byte) 0 , enemy);

            if (player.health <= 0) {
                System.out.println(player.name + " has died!");
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
        player.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        enemy.health = (int) (enemy.health - player.damage - player.bonusDamage);
        System.out.println(PREFIX + " " + player.name + " did " + player.damage + " base damage \n and " + (int) player.bonusDamage + " bonus damage!");
    }
    static void enemyRandomAttackDamageBonus(float minAttackDamageValue, float maxAttackDamageValue)
    {
        enemy.bonusDamage = ThreadLocalRandom.current().nextDouble(minAttackDamageValue, maxAttackDamageValue);
        player.health = (int) (player.health - enemy.damage - enemy.bonusDamage);
        System.out.println(PREFIX + " " + enemy.name + " did " + enemy.damage + " base damage \n and " + (int) enemy.bonusDamage + " bonus damage!");

    }
    static void enemyRandomHealthRegenerate(byte minRegenerateValue, byte maxRegenerateValue)
    {
            double healthGenerate = ThreadLocalRandom.current().nextDouble(minRegenerateValue, maxRegenerateValue);
            enemy.health = enemy.health + (byte) healthGenerate;
            if (enemy.health > 100) {
                enemy.health = 100;
            }
            System.out.println(PREFIX + " " + enemy.name + " use it's ability to heal itself. \n " + enemy.name + " now has " + (int) enemy.health + " health. \n");
    }
    static void enemyBoostedDamage(float minBoostedDamageValue, float maxBoostedValue) {
        int randomBoostedDamage = ThreadLocalRandom.current().nextInt((int) minBoostedDamageValue, (int) maxBoostedValue);
        enemy.damage = enemy.damage + randomBoostedDamage;
        System.out.println(enemy.name + " uses it's ability to boost it's damage\n");
    }
    static void enemyRandomDescision(int RandomDecision)
    {
        switch (RandomDecision) {
            case 0:
                enemyRandomAttackDamageBonus(0, 2);
                break;
            case 1:
                if (enemy.health > 0 && enemy.health <= 100) {

                    enemyRandomHealthRegenerate((byte) 5, (byte) 15.2);
                }
                else {
                        System.out.println(enemy.name + " attempted to use it's ability to heal itself\n");
                    }
                break;
            case 2:
                if (enemy.damage > 0 && enemy.damage < 20) {
                    enemyBoostedDamage(0, 3);
                }
                else {
                    System.out.println(enemy.name+" attempted to use it's ability to boost it's damage!");
                }
                break;
        }
    }
    static void levelUp()
    {
        if (currentExp >= exp[level] && level < exp.length - 1)
        {
            currentExp -= exp[level];

            level = (byte) (level + (byte)1);

            System.out.println(PREFIX + " Congratulation, " + player.name + " level is now " + level + ".\n" + PREFIX + " Remaining exp " + currentExp + ".");
        }
    }

    static void LevelManagement()
    {
        switch (level)
        {
            case 0:
                exp[level] = 50;
                break;
            case 1:
                exp[level] = 100;
                break;
            case 2:
                exp[level] = 300;
                break;
            case 3:
                exp[level] = 600;
                break;
            case 4:
                exp[level] = 1000;
                break;
            case 5:
                exp[level] = 1700;
                break;
            case 6:
                exp[level] = 2200;
                break;
            case 7:
                exp[level] = 3000;
                break;
            case 8:
                exp[level] = 3300;
                break;
            case 9:
                exp[level] = 3600;
                break;
        }
    }

    static void EarnedExpForDifferentLevel()
    {
        switch (level)
        {
            case 0:
                currentExp += ThreadLocalRandom.current().nextInt();
                break;
            case 1:
                currentExp += 10;
                break;
        }
    }
}