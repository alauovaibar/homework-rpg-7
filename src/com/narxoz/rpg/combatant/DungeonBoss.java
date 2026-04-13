package com.narxoz.rpg.combatant;
import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;

public class DungeonBoss {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;
    private final EventManager eventManager;
    private int currentPhase = 1;

    public DungeonBoss(String name, int hp, int attackPower, int defense, CombatStrategy initialStrategy, EventManager eventManager) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = initialStrategy;
        this.eventManager = eventManager;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
    public CombatStrategy getStrategy() { return strategy; }
    public void setStrategy(CombatStrategy strategy) { this.strategy = strategy; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        checkPhaseTransition();
        if (!isAlive()) {
            eventManager.notify(new GameEvent(GameEventType.BOSS_DEFEATED, name, 0));
        }
    }

    private void checkPhaseTransition() {
        double hpPercentage = (double) hp / maxHp;
        if (currentPhase == 1 && hpPercentage <= 0.6) {
            currentPhase = 2;
            eventManager.notify(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, currentPhase));
        } else if (currentPhase == 2 && hpPercentage <= 0.3) {
            currentPhase = 3;
            eventManager.notify(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, currentPhase));
        }
    }
}