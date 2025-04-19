package mxbville.common.items.documents;

/**
 * The Type of an Invitation Letter. 
 * Different Types have different success, fail and ambush values. 
 * A fail results in no reply at all, an ambush results in 
 * a reply letter, that causes an ambush. A success results in a reply letter 
 * that spawns a villager. 
 */
public enum InvitationType {
	NORMAL(45, 40, 15), 
	APPROVED(75, 10, 15), 
	BAIT(10, 75, 15), 
	OFFICIAL(100, 0, 0), 
	JOB(100, 0, 0);

	// all the following values must add up to 100
	private final int successRate;
	private final int ambushRate;
	private final int failRate;

	private InvitationType(int successRate, int ambushRate, int failRate) {
		this.successRate = successRate;
		this.ambushRate = ambushRate;
		this.failRate = failRate;
	}

	public int getSuccessRate() {
		return successRate;
	}

	public int getAmbushRate() {
		return ambushRate;
	}

	public int getFailRate() {
		return failRate;
	}

}
