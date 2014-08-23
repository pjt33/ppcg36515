import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Player {
	private static final File workingDir = new File("resources/players").getAbsoluteFile();

	public static final char BUY = 'B';
	public static final char SELL = 'S';

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
