package app.Editor;

public class RuleEntry {
    public String rule;
    public double prob;

    public RuleEntry(String rule, double prob){
        this.rule = rule;
        this.prob = prob;
    }

    public String getRule() {
        return rule;
    }

    public double getProb() {
        return prob;
    }
}
