public class PlayerClass {
    int health;
    byte level;
    double damage, bonusDamage;
     String name;
     public PlayerClass(int health, double damage, String title, byte level)
     {
         this.health = health;
         this.damage = damage;
         name = title;
         this.level = level;
     }
}
