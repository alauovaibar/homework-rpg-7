package com.narxoz.rpg.observer;

public class CombatLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        System.out.println("[LOG] Событие: " + event.getType() + " от " + event.getSourceName() + " (Значение: " + event.getValue() + ")");
    }
}