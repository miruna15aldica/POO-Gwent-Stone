package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Minion extends Card{
    // cardType = 1;
    protected boolean isOnFirstRow;
    //protected int hasTank;
    protected boolean isOnLastRow;
    protected int cardType;
    protected int health;
    protected int attackDamage;
    protected Boolean special;
    protected Boolean tank;

    public Boolean getTank() {
        return tank;
    }

    public void setTank(Boolean tank) {
        this.tank = tank;
    }

    public boolean hasTank() {
        return  this.tank;
    }

    protected Boolean frozen;
    protected Boolean attacked;
    protected Boolean placeOnFirstRow;
    protected Boolean placeOnLastRow;
    protected Boolean canAttackEnemy;
    protected Boolean abilityOnSelf;
    protected Boolean abilityOnEnemy;

    public Minion(int mana, String description, ArrayList<String> colors, String name, int cardType, int health,
                  int attackDamage, Boolean tank, Boolean placeOnFirstRow, Boolean placeOnLastRow, Boolean canAttackEnemy,
                  Boolean abilityOnSelf, Boolean abilityOnEnemy) {
        super(mana, description, colors, name, cardType);
        this.health = health;
        this.attackDamage = attackDamage;
        this.tank = tank;
        this.frozen = false;
        this.attacked = false;
        this.placeOnFirstRow = placeOnFirstRow;
        this.placeOnLastRow = placeOnLastRow;
        this.canAttackEnemy = canAttackEnemy;
        this.abilityOnSelf = abilityOnSelf;
        this.abilityOnEnemy = abilityOnEnemy;
        this.cardType = 1;
        this.special = false;
    }

    public int getHealth() {
        return health;
    }

    abstract void specialAbility(Minion minion);
    // freeze, unfreeze sunt usurele :)

    public void destroyCardOnTable(Card card) {
        this.health = 0;
    }

    @Override
    public ObjectNode cardTransformToAnObjectNode(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        objectNode.put("health", this.health);
        objectNode.put("description", this.description);
        objectNode.put("name", this.name);
        objectNode.put("attackDamage", this.attackDamage);
        objectNode.set("colors", showColors(objectMapper));
        return  objectNode;
    }
    public void damage(int attackDamage) {
        if(this.health - attackDamage < 0) {
            this.health = 0;
        }
        else {
            this.health = this.health - attackDamage;
        }
    }

    public void cardUsesAttack(Card card, Game game) {
        if(card.cardType == 1) {
            ((Minion) card).damage(this.attackDamage);
            this.attacked = true;
        }
        if(card.cardType == 3) {
            ((Hero) card).damage(this.attackDamage);
            this.attacked = true;
        }
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setAttacked(Boolean attacked) {
        this.attacked = attacked;
    }

    public Boolean isTank() {
        return tank;
    }

    public Boolean getAttacked() {
        return attacked;
    }
}
