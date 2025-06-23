package phonePeIssueResolutionSystem.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phonePeIssueResolutionSystem.issue.Issue;
import phonePeIssueResolutionSystem.issue.IssueType;

public class AgentManager {
	private static AgentManager amInstance;
    private final Map<String, Agent> agentsMap = new HashMap<String, Agent>();

    public static AgentManager getInstance() {
    	if (amInstance == null) {
    		synchronized (AgentManager.class) {
    			if (amInstance == null) {
    				amInstance = new AgentManager();
    	    	}
			}
    	}
        return amInstance;
    }

    public void addAgent(String ID, List<IssueType> expertise) {
    	agentsMap.put(ID, new Agent(ID, expertise));
    }

    public Map<String, Agent> getAgents() {
        return agentsMap;
    }

    public List<Issue> getWorkHistory(String agentID) {
        return agentsMap.get(agentID).getResolvedIssues();
    }
}
