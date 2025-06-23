package phonePeIssueResolutionSystem.agent;

import java.util.ArrayList;
import java.util.List;

import phonePeIssueResolutionSystem.issue.Issue;
import phonePeIssueResolutionSystem.issue.IssueStatus;
import phonePeIssueResolutionSystem.issue.IssueType;

public class Agent {
    private final String agentID;
    private final List<IssueType> expertise;
    private final List<Issue> resolveHistory = new ArrayList<>();
    private Issue ongoingIssue;

    public Agent(String name, List<IssueType> expertise) {
        this.agentID = name;
        this.expertise = expertise;
    }

    public boolean isFree() {
        return ongoingIssue == null;
    }

    public boolean isExpert(IssueType type) {
        return expertise.contains(type);
    }

    public void assignIssue(Issue issue) {
        this.ongoingIssue = issue;
        issue.setStatus(IssueStatus.ASSIGNED);
        issue.setAssignedAgent(this);
    }

    public void resolveCurrentIssue(String resolution) {
        if (ongoingIssue != null) {
            ongoingIssue.setStatus(IssueStatus.RESOLVED);
            ongoingIssue.setResolution(resolution);
            resolveHistory.add(ongoingIssue);
            ongoingIssue = null;
        }
    }

    public List<Issue> getResolvedIssues() {
        return resolveHistory;
    }

    public String getName() { return agentID; }

	public Issue getCurrentIssue() {
		return ongoingIssue;
	}

	public void setCurrentIssue(Issue currentIssue) {
		this.ongoingIssue = currentIssue;
	}

	public void resolveIssue(Issue issue) {
		resolveHistory.add(issue);
	}
}
