package com.narxoz.rpg.observer;

public class AchievementSystem implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("🏆 АЧИВКА: Подземелье зачищено!");
        } else if (event.getType() == GameEventType.HERO_LOW_HP) {
            System.out.println("🏆 АЧИВКА: На волоске от смерти (" + event.getSourceName() + ")");
        }
    }
}