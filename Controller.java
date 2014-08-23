import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Controller {
	private static boolean VERBOSE = true;

	private static boolean DAMPED = true;
	private static int INC_MIN = 1;
	private static int INC_MAX = 5;
	private static int DEC_MIN = 2;
	private static int DEC_MAX = 6;

	public static void main(String[] args){
		try {
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("resources/config")));

			int numRounds = Integer.parseInt(br1.readLine());
			int turnsPerRound = Integer.parseInt(br1.readLine());

			List<Player> players = new ArrayList<Player>();
			Map<Player, BigInteger> totals = new HashMap<Player, BigInteger>();
			String line1 = null;
			while ((line1 = br1.readLine()) != null) {
				Player p = new Player(line1);
				players.add(p);
				totals.put(p, BigInteger.ZERO);
			}
			br1.close();

			//Begin processing
			for(int round = 0; round < numRounds; round++){
				Map<Player, BigInteger> finalValue = runRound(players, turnsPerRound);
				for (Map.Entry<Player, BigInteger> e : finalValue.entrySet()) {
					totals.put(e.getKey(), totals.get(e.getKey()).add(e.getValue()));
				}
			}

			for (Player player : players) {
				System.out.println(player + ":\t$" + totals.get(player).divide(BigInteger.valueOf(numRounds)));
			}
		} catch (Exception e) {
			System.err.println("An exception occured while running the controller.");
			e.printStackTrace();
		}
	}

	private static Map<Player, BigInteger> runRound(Collection<Player> players, int turns) {
		//Create players' shares and currency array
		Map<Player, Possessions> vals = new HashMap<Player, Possessions>();
		BigInteger initialShares = BigInteger.valueOf(getRandInt(20, 30)); // Start everyone on the same amount.
		for (Player player : players) vals.put(player, new Possessions(initialShares));

		BigInteger marketValue = BigInteger.valueOf(getRandInt(10,150));
		for (int turn = 0; turn < turns; turn++) {
			BigInteger newValue = marketValue;
			for (Player player : players) {
				Possessions position = vals.get(player);
				try {
					BigInteger move = player.move(marketValue, position);
					if (move.signum() > 0) {
						// Buy
						BigInteger bought = move;
						position.cash = position.cash.subtract(bought.multiply(marketValue));
						position.shares = position.shares.add(bought);

						BigInteger delta = BigInteger.valueOf(getRandInt(INC_MIN, INC_MAX));
						if (!DAMPED) delta = delta.multiply(bought);
						newValue = newValue.add(delta);
					}
					else if (move.signum() < 0) {
						// Sell
						BigInteger sold = move.negate();
						position.cash = position.cash.add(sold.multiply(marketValue));
						position.shares = position.shares.subtract(sold);

						BigInteger delta = BigInteger.valueOf(getRandInt(DEC_MIN, DEC_MAX));
						if (!DAMPED) delta = delta.multiply(sold);
						newValue = newValue.subtract(delta);
					}
				} catch (Exception e) {
					System.err.println("[" + player + "] threw error:");
					e.printStackTrace();
				}
			}

			// Clip.
			if (newValue.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) newValue = BigInteger.valueOf(Integer.MAX_VALUE - 1);
			if (newValue.compareTo(BigInteger.ONE) < 0) newValue = BigInteger.ONE;

			marketValue = newValue;
			if (VERBOSE) System.out.println("Turn " + turn + " over: " + marketValue);
		}
		if (VERBOSE) System.out.println("End of round market value is: " + marketValue);

		Map<Player, BigInteger> finalValue = new HashMap<Player, BigInteger>();
		for (Player player : players) {
			Possessions position = vals.get(player);
			finalValue.put(player, position.cash.add(position.shares.multiply(marketValue)));
		}
		return finalValue;
	}

	private static Random r = new Random();
	/** Gets a random integer from min to max, both inclusive. */
	public static int getRandInt(int min, int max){
		return r.nextInt(max - min + 1) + min;
	}
}
