Design Patters

## Composite
* com.simplegame.SideMenu
* The Composite interface is Group or Pane from javafx.
* Leaves are Node Objects. e.g. Text, ImageView, etc.

## Observer
* com.simplegame.Controller.PlayerOneController (Subscriber),
* com.simplegame.KeyboardObserverManager (Manager),
* com.simplegame.BasicGameApp (Producer in initInput method)

## Template Method
* com.simplegame.TankMaker.TankMakerTemplate (makeTank method)

        Implementations:
            com.simplegame.TankMaker.EnemyTankMaker,
            com.simplegame.TankMaker.PlayerTankMaker

## Mediator
* com.simplegame.Controller.GameController

        methods:
            enemyDestroyed
            playerDestroyed
            spawnReward
            rewardCollected

## Singleton
* com.simplegame.Controller.GameController,
* com.simplegame.KeyboardObserverManager

## Bridge
* Entity and Components in FXGL,
* TankControlComponent and TankController (bidirectional bridge)

