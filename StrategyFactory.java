package phonePeIssueResolutionSystem;

public class StrategyFactory {

	public static AssignStrategy getStartegy(StrategyType stategy) {
		switch (stategy) {
		case EXPERTISE: return new ExpertiseAgentStrategy();
		case AVAILABLE: return new AvailableAgentStrategy();
		}
		return null;
	}

}
