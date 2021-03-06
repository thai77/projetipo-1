package environment;

import java.util.ArrayList;
import java.util.Iterator;

import frog.Frog;
import util.Case;
import gameCommons.Game;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<environment.Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;

	private Frog frog;

	private int timer;

	// TODO : Constructeur(s)

	public Lane(Game game, int ord, double density) {
		this.cars = new ArrayList();
		this.game = game;
		this.ord = ord;
		this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
		this.leftToRight = game.randomGen.nextBoolean();
		this.density = density;

		for(int i = 0; i < 4 * game.width; ++i) {
			this.moveCars(true);
			this.mayAddCar();
		}

	}

	public Lane(Game game, int ord) {
		this(game, ord, game.defaultDensity);
	}




	public void update() {

		// TODO

		// Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
		// d'horloge" �gal � leur vitesse
		// Notez que cette m�thode est appel�e � chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut �tre ajout�e

		this.timer++;
		if (timer <= this.speed) {
			this.moveCars(false);
		} else {
			this.moveCars(true);
			this.mayAddCar();
			timer = 0;
		}
	}

	// TODO : ajout de methodes

	private void moveCars(boolean b) {
		Iterator var3 = this.cars.iterator();

		while(var3.hasNext()) {
			environment.Car car = (environment.Car)var3.next();
			car.move(b);
		}

		this.removeOldCars();
	}

	private void removeOldCars() {
		ArrayList<environment.Car> toBeRemoved = new ArrayList();
		Iterator var3 = this.cars.iterator();

		environment.Car c;
		while(var3.hasNext()) {
			c = (environment.Car)var3.next();
			if (!c.appearsInBounds()) {
				toBeRemoved.add(c);
			}
		}

		var3 = toBeRemoved.iterator();

		while(var3.hasNext()) {
			c = (environment.Car)var3.next();
			this.cars.remove(c);
		}
	}

	public boolean isSafe(Case firstCase) {
		Iterator var3 = this.cars.iterator();

		while(var3.hasNext()) {
			environment.Car car = (environment.Car)var3.next();
			if (car.coversCase(firstCase)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
	 * densit�, si la premi�re case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new environment.Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

	public String toString() {
		return "Lane [ord=" + this.ord + ", cars=" + this.cars + "]";
	}

}
