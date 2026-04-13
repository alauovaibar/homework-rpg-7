package com.narxoz.rpg.observer;
import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

public class BossPhaseObserver implements GameObserver {
    private final DungeonBoss boss;

    public BossPhaseObserver(DungeonBoss boss) {
        this.boss = boss;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            if (event.getValue() == 2) {
                System.out.println("🔥 Босс переходит в Фазу 2! Стратегия: Defensive");
                boss.setStrategy(new DefensiveStrategy());
            } else if (event.getValue() == 3) {
                System.out.println("💀 Босс переходит в Фазу 3 (Ярость)! Стратегия: Aggressive");
                boss.setStrategy(new AggressiveStrategy());
            }
        }
    }
}