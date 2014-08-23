import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Controller {
	private static final File workingDir = new File("resources/players").getAbsoluteFile();

	public static final char BUY = 'B';
	public static final char SELL = 'S';

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

	private static class Possessions {
		public BigInteger cash;
		public BigInteger shares;

		public Possessions(BigInteger shares) {
			cash = BigInteger.valueOf(5000);
			this.shares = shares;
		}
	}

	private static class Player {
		private List<String> cmd;

		public Player(String configLine) {
			String[] parts = configLine.split("&");
			cmd = new ArrayList<String>();
			for (int i = 0; i < parts.length; i++) cmd.add(parts[i]);
		}

		public String run(BigInteger stockPrice, Possessions position) throws IOException {
			List<String> fullCmd = new ArrayList<String>(cmd);
			fullCmd.add(stockPrice.toString());
			fullCmd.add(position.cash.toString());
			fullCmd.add(position.shares.toString());

			Process proc = new ProcessBuilder(fullCmd).directory(workingDir).start();
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				try {
					return br.readLine();
				}
				finally {
					br.close();
				}
			}
			finally {
				proc.destroy();
			}
		}

		/**
		 * Finds out the desired action. Does not update the state.
		 * @return the change in the stockholding: i.e. a positive number if they bought, a negative
		 * number if they sold, or zero otherwise.
		 */
		public BigInteger move(BigInteger marketValue, Possessions position) throws IOException {
			String line = run(marketValue, position);
			switch (line == null ? '\u0000' : line.charAt(0)) {
				case BUY:
					BigInteger bought = new BigInteger(line.substring(1).trim());
					if (bought.signum() < 0) {
						System.err.println(this + " attempted to buy a negative number: " + line);
						return BigInteger.ZERO;
					}

					BigInteger cost = bought.multiply(marketValue);
					return cost.compareTo(position.cash) <= 0 ? bought : BigInteger.ZERO;

				case SELL:
					BigInteger sold = new BigInteger(line.substring(1).trim());
					if (sold.signum() < 0) {
						System.err.println(this + " attempted to sell a negative number: " + line);
						return BigInteger.ZERO;
					}

					return sold.compareTo(position.shares) <= 0 ? sold.negate() : BigInteger.ZERO;

				default:
					return BigInteger.ZERO;
			}
		}

		@Override
		public String toString() {
			return cmd.get(cmd.size() - 1);
		}
	}
}
