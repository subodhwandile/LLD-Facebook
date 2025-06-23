package phonePeIssueResolutionSystem.issue;

import phonePeIssueResolutionSystem.agent.Agent;

public class Issue {
	private static int idCounter = 1;
    private final int issueId;
    private final String transactionId;
    private final IssueType type;
    private IssueStatus status;
    private String resolution;
    private Agent assignedAgent;
    private String detail;

    public Issue(String transactionId, IssueType type, String subject, String description) {
        this.issueId = idCounter++;
        this.transactionId = transactionId;
        this.type = type;
        this.status = IssueStatus.PENDING;
        this.detail = description;
    }

    public int getIssueId() { return issueId; }
    public IssueType getType() { return type; }
    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
    public void setAssignedAgent(Agent agent) { this.assignedAgent = agent; }
    public Agent getAssignedAgent() { return assignedAgent; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getTransactionId() { return transactionId; }

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + issueId + ", transactionId=" + transactionId + ", type=" + type + ", status="
				+ status + ", resolution=" + resolution + ", assignedAgent=" + assignedAgent.getName() + ", detail=" + detail
				+ "]";
	}
    
}
