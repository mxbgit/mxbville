package mxbville.common.items.documents;

public enum InvitationType {
	NORMAL(45,40,15),
	APPROVED(75,10,15),
	BAIT(10,75,15),
	OFFICIAL(100,0,0),
	JOB(100,0,0);
	
	// all the following values must add up to 100
	private final int successRate;
	private final int ambushRate;
	private final int failRate;
	
	private InvitationType(int successRate, int ambushRate,int failRate) {
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
