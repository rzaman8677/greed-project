public class ZamanGreedStrategy extends GreedStrategy {

    public String playerName() {
        return "Zaman Greed Strategy";
    }

    public String author() {
        return "Your Name";
    }

    public int choose(GreedOption[] options, int[] dice, int bank) {
        if (options.length == 0) {
            return -1;
        }

        int bestOptionIndex = -1;
        int bestOptionScore = -1;
        double bestOptionRisk = 0.0;

        for (int i = 0; i < options.length; i++) {
            GreedOption option = options[i];
            if (option.optionType() == GreedOption.SCORE) {
                ScoringCombination combination = (ScoringCombination) option;

                if (combination.contains(dice)) {
                    int optionScore = combination.getValue();
                    double optionRisk = calculateRisk(dice, combination.numDice(), bank);

                    if (optionScore > bestOptionScore || (optionScore == bestOptionScore && optionRisk < bestOptionRisk)) {
                        bestOptionIndex = i;
                        bestOptionScore = optionScore;
                        bestOptionRisk = optionRisk;
                    }
                }
            }
        }

        if (bestOptionIndex != -1) {
            return bestOptionIndex;
        }

        int highestValue = -1;

        for (int i = 0; i < options.length; i++) {
            GreedOption option = options[i];
            if (option.optionType() == GreedOption.SCORE) {
                ScoringCombination combination = (ScoringCombination) option;
                int optionValue = combination.getValue();

                if (optionValue > highestValue) {
                    highestValue = optionValue;
                    bestOptionIndex = i;
                }
            }
        }

        return bestOptionIndex;
    }

    private double calculateRisk(int[] dice, int numDiceUsed, int bank) {
        int remainingDice = dice.length - numDiceUsed;
        double bankRiskFactor = 1.0;

        if (bank > 750) {
            bankRiskFactor = 1.35;
        } else if (bank > 250) {
            bankRiskFactor = 0.9;
        }

        double risk = Math.pow(0.38, remainingDice) * bankRiskFactor;

        return risk;
    }
}
