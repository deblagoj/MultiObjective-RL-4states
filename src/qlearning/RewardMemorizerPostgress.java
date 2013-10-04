package qlearning;

public class RewardMemorizerPostgress {
	
	protected IRewardStore memory;
	
	public String toString() {
		return memory.toString();
	}
}
