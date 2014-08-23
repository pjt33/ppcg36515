import java.math.BigInteger;

public class Possessions {
	public BigInteger cash;
	public BigInteger shares;

	public Possessions(BigInteger shares) {
		cash = BigInteger.valueOf(5000);
		this.shares = shares;
	}
}
