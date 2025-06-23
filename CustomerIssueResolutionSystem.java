package phonePeIssueResolutionSystem;

import java.util.List;

import phonePeIssueResolutionSystem.agent.AgentManager;
import phonePeIssueResolutionSystem.issue.Issue;
import phonePeIssueResolutionSystem.issue.IssueManager;
import phonePeIssueResolutionSystem.issue.IssueType;

public class CustomerIssueResolutionSystem {

	public static void main(String[] args) {
		AgentManager agentManager = AgentManager.getInstance();
        IssueManager issueManager = IssueManager.getInstance(StrategyType.AVAILABLE);

        agentManager.addAgent("Subodh", List.of(IssueType.PAYMENT, IssueType.INSURANCE));
        agentManager.addAgent("Vaiabhav", List.of(IssueType.GOLD, IssueType.MUTUAL_FUNDS));

        Issue issue1 = issueManager.createIssue("issue1","Transaction1", IssueType.PAYMENT, "Failed Payment", "Payment failed");
        Issue issue2 = issueManager.createIssue("issue2","Transaction2", IssueType.GOLD, "Gold not added", "Didn't receive gold");

        issueManager.resolveIssue(issue1, "resoveled by refund");

        System.out.println("All Issues:");
        for (Issue issue : issueManager.getAllIssues().values()) {
            System.out.println("Issue " + issue.getIssueId() + ": " + issue.getStatus());
        }
        System.out.println("Agent Work History:");
        for (Issue issue : agentManager.getWorkHistory("Subodh")) {
            System.out.println(issue);
        }
	}

}
