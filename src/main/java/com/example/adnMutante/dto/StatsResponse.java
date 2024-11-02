package com.example.adnMutante.dto;

public class StatsResponse {
    private long countMutantAdn;
    private long countHumanAdn;
    private double ratio;

    public StatsResponse(long countMutantAdn, long countHumanAdn) {
        this.countMutantAdn = countMutantAdn;
        this.countHumanAdn = countHumanAdn;
        this.ratio = countHumanAdn == 0 ? 0 : (double) countMutantAdn / countHumanAdn;
    }

    public long getCountMutantAdn() {
        return countMutantAdn;
    }

    public void setCountMutantAdn(long countMutantAdn) {
        this.countMutantAdn = countMutantAdn;
    }

    public long getCountHumanAdn() {
        return countHumanAdn;
    }

    public void setCountHumanAdn(long countHumanAdn) {
        this.countHumanAdn = countHumanAdn;
    }

    public double getRatio() {
        return ratio;
    }
}
