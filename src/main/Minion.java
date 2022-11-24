package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public abstract class Minion extends Card {
    // cardType = 1;
    //protected int hasTank;
    protected int cardType;
    protected int health;
    protected int attackDamage;
    protected Boolean special;
    protected Boolean tank;

    public final Boolean getTank() {
        return tank;
    }

    public final void setTank(final Boolean tank) {
        this.tank = tank;
    }

    /**
     *
     * @return
     */
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

    public Minion(final int mana, final String description, final ArrayList<String> colors,
                  final String name, final int cardType, final int health,
                  final int attackDamage, final Boolean tank,
                  final Boolean placeOnFirstRow,
                  final Boolean placeOnLastRow, final Boolean canAttackEnemy,
                  final Boolean abilityOnSelf, final Boolean abilityOnEnemy) {
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

    /**
     *
     * @return
     */
    public int getHealth() {
        return health;
    }

    abstract void specialAbility(Minion minion);

    /**
     *
     * @param card
     */
    public void destroyCardOnTable(final Card card) {
        this.health = 0;
    }

    @Override
    public final ObjectNode cardTransformToAnObjectNode(final ObjectMapper objectMapper) {
        // functie care transforma o carte intr-un obiect
        //de tip JSON
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.mana);
        objectNode.put("health", this.health);
        objectNode.put("description", this.description);
        objectNode.put("name", this.name);
        objectNode.put("attackDamage", this.attackDamage);
        objectNode.set("colors", showColors(objectMapper));
        return  objectNode;
    }

    /**
     *
     * @param attackDamage
     */
    public void damage(final int attackDamage) {
        if (this.health - attackDamage < 0) {
            this.health = 0;
        } else {
            this.health = this.health - attackDamage;
        }
    }

    /**
     *
     * @param card
     * @param game
     */
    public final void cardUsesAttack(final Card card, final Game game) {
        final int magic = 3;
        if (card.cardType == 1) {
            ((Minion) card).damage(this.attackDamage);
            this.attacked = true;
        }
        if (card.cardType == magic) {
            ((Hero) card).damage(this.attackDamage);
            this.attacked = true;
        }
    }

    /**
     *
     */
    public void unfreeze() {
        this.frozen = false;
    }

    public final void setSpecial(final Boolean special) {
        this.special = special;
    }

    public final Boolean getSpecial() {
        return special;
    }

    public final void setAttacked(final Boolean attacked) {
        this.attacked = attacked;
    }

    public final Boolean isTank() {
        return tank;
    }

    public final Boolean getAttacked() {
        return attacked;
    }

    /**
     *
     * @return
     */
    public abstract Card copy();
}
