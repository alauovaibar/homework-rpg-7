package com.narxoz.rpg.engine;
import com.narxoz.rpg.combatant.StrategyHero;
import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.observer.EventManager;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.List;
import java.util.Random;

public class DungeonEngine {
    private final List<StrategyHero> heroes;
    private final DungeonBoss boss;
    private final EventManager eventManager;
    private final Random random = new Random();

    public DungeonEngine(List<StrategyHero> heroes, DungeonBoss boss, EventManager eventManager) {
        this.heroes = heroes;
        this.boss = boss;
        this.eventManager = eventManager;
    }

    public EncounterResult runEncounter() {
        int roundsPlayed = 0;
        System.out.println("=== НАЧАЛО БОЯ ===");

        while (boss.isAlive() && getSurvivingHeroesCount() > 0) {
            roundsPlayed++;
            System.out.println("\n--- Раунд " + roundsPlayed + " ---");

            for (StrategyHero hero : heroes) {
                if (!hero.isAlive() || !boss.isAlive()) continue;

                int damageDealt = calculateDamage(hero.getAttackPower(), hero.getStrategy(), boss.getDefense(), boss.getStrategy());
                boss.takeDamage(damageDealt);
                eventManager.notify(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), damageDealt));

                System.out.printf("%s бьет %s на %d урона. (У босса %d HP)%n", hero.getName(), boss.getName(), damageDealt, boss.getHp());
            }

            if (!boss.isAlive()) break;

            StrategyHero target = getRandomAliveHero();
            if (target != null) {
                int damageDealt = calculateDamage(boss.getAttackPower(), boss.getStrategy(), target.getDefense(), target.getStrategy());
                target.takeDamage(damageDealt);
                eventManager.notify(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), damageDealt));

                System.out.printf("%s бьет %s на %d урона. (У героя %d HP)%n", boss.getName(), target.getName(), damageDealt, target.getHp());

                checkHeroStatus(target);
            }
        }

        boolean heroesWon = boss.getHp() <= 0;
        return new EncounterResult(heroesWon, roundsPlayed, getSurvivingHeroesCount());
    }

    private int calculateDamage(int attackerBaseAttack, com.narxoz.rpg.strategy.CombatStrategy attackerStrategy,
                                int defenderBaseDefense, com.narxoz.rpg.strategy.CombatStrategy defenderStrategy) {
        int actualAttack = attackerStrategy.calculateDamage(attackerBaseAttack);
        int actualDefense = defenderStrategy.calculateDefense(defenderBaseDefense);
        return Math.max(1, actualAttack - actualDefense);
    }

    private StrategyHero getRandomAliveHero() {
        List<StrategyHero> aliveHeroes = heroes.stream().filter(StrategyHero::isAlive).toList();
        if (aliveHeroes.isEmpty()) return null;
        return aliveHeroes.get(random.nextInt(aliveHeroes.size()));
    }

    private int getSurvivingHeroesCount() {
        return (int) heroes.stream().filter(StrategyHero::isAlive).count();
    }

    private void checkHeroStatus(StrategyHero hero) {
        if (!hero.isAlive()) {
            eventManager.notify(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
        } else if ((double) hero.getHp() / hero.getMaxHp() <= 0.3 && !hero.isLowHpTriggered()) {
            hero.setLowHpTriggered(true);
            eventManager.notify(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
        }
    }
}