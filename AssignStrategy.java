package phonePeIssueResolutionSystem;

import java.util.List;
import java.util.Map;

import phonePeIssueResolutionSystem.agent.Agent;
import phonePeIssueResolutionSystem.issue.Issue;

public interface AssignStrategy {
	Agent assignAgent(Map<String, Agent> agentMap, Issue issue);
}

class AvailableAgentStrategy implements AssignStrategy {
    @Override
    public Agent assignAgent(Map<String, Agent> map, Issue issue) {
    	for (Agent agent : map.values()) {
            if (agent.isFree()) {
                return agent;
            }
        }
        return null;
    }
}

class ExpertiseAgentStrategy implements AssignStrategy {
    @Override
    public Agent assignAgent(Map<String, Agent> map, Issue issue) {
    	for (Agent agent : map.values()) {
            if (agent.isFree() && agent.isExpert(issue.getType())) {
                return agent;
            }
        }
        return null;
    }
}