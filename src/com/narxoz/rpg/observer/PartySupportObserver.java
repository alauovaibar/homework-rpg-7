package com.narxoz.rpg.observer;
import com.narxoz.rpg.combatant.Hero;
import java.util.List;

public class PartySupportObserver implements GameObserver {
    private final List<Hero> party;

    public PartySupportObserver(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            for (Hero hero : party) {
                if (hero.getName().equals(event.getSourceName()) && hero.isAlive()) {
                    int healAmount = 20;
                    hero.heal(healAmount);
                    System.out.println("✨ Поддержка группы исцеляет " + hero.getName() + " на " + healAmount + " HP!");
                }
            }
        }
    }
}