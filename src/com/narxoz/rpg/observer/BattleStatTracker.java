package com.narxoz.rpg.observer;

public class BattleStatTracker implements GameObserver {
    private int totalAttacks = 0;

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            totalAttacks++;
        } else if (event.getType() == GameEventType.BOSS_DEFEATED || event.getType() == GameEventType.HERO_DIED) {
            System.out.println("📊 Статистика: Всего успешных атак за бой: " + totalAttacks);
        }
    }
}