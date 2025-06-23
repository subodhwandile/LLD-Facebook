package phonePeIssueResolutionSystem.issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import phonePeIssueResolutionSystem.AssignStrategy;
import phonePeIssueResolutionSystem.StrategyFactory;
import phonePeIssueResolutionSystem.StrategyType;
import phonePeIssueResolutionSystem.agent.Agent;
import phonePeIssueResolutionSystem.agent.AgentManager;

public class IssueManager {
	private static IssueManager instance = new IssueManager();
    private final Map<String, Issue> issues = new HashMap<>();
    private static AssignStrategy assignStrategy; 

    public static IssueManager getInstance(StrategyType stategy) {
    	assignStrategy = StrategyFactory.getStartegy(stategy);
        return instance;
    }

    public Issue createIssue(String issueID, String transactionId, IssueType type, String subject, String desc) {
        Issue issue = new Issue(transactionId, type, subject, desc);
        issues.put(issueID, issue);
        assignIssue(issue);
        return issue;
    }

    public void assignIssue(Issue issue) {
        Agent agent = assignStrategy.assignAgent(AgentManager.getInstance().getAgents(), issue);
        if (agent != null) {
            agent.assignIssue(issue);
        }
    }

    public List<Issue> getIssues(Map<String, String> filter) {
    	List<Issue> result = new ArrayList<>();

        for (Issue issue : issues.values()) {
            boolean matchFound = true;

            if (filter.containsKey("desc")) {
                String descFiletr = filter.get("desc");
                if (!issue.getDetail().equalsIgnoreCase(descFiletr)) {
                	matchFound = false;
                }
            }

            if (filter.containsKey("id")) {
                String idFilter = filter.get("id");
                if (!Integer.toString(issue.getIssueId()).equals(idFilter)) {
                	matchFound = false;
                }
            }

            if (matchFound) {
                result.add(issue);
            }
        }

        return result;
    }

    public void updateIssue(int issueId, IssueStatus status, String resolution) {
        for (Issue issue : issues.values()) {
            if (issue.getIssueId() == issueId) {
                issue.setStatus(status);
                issue.setResolution(resolution);
                return;
            }
        }
    }

    public void resolveIssue(Issue issue, String resolution) {
    		Agent agent = issue.getAssignedAgent();
    		agent.resolveIssue(issue);
            agent.resolveCurrentIssue(resolution);
            assignPendingIssues();
            return;
    	
    }

    private void assignPendingIssues() {
        for (Issue issue : issues.values()) {
            if (issue.getStatus() == IssueStatus.PENDING) {
                assignIssue(issue);
            }
        }
    }

    public Map<String, Issue> getAllIssues() {
        return issues;
    }
}
