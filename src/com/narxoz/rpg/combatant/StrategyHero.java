package com.narxoz.rpg.combatant;
import com.narxoz.rpg.strategy.CombatStrategy;

public class StrategyHero extends Hero {
    private CombatStrategy strategy;
    private boolean lowHpTriggered = false;

    public StrategyHero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        super(name, hp, attackPower, defense);
        this.strategy = strategy;
    }

    public CombatStrategy getStrategy() { return strategy; }
    public boolean isLowHpTriggered() { return lowHpTriggered; }
    public void setLowHpTriggered(boolean triggered) { this.lowHpTriggered = triggered; }
}