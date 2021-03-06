package br.com.jonathan.infrastructure.checker.deductions;

import org.springframework.scheduling.annotation.Async;

import br.com.jonathan.infrastructure.checker.IChecker;
import br.com.jonathan.infrastructure.dto.MetricsDTO;
import br.com.jonathan.infrastructure.dto.PasswordParametersDTO;
import br.com.jonathan.infrastructure.dto.ScoreMetricDTO;
import br.com.jonathan.infrastructure.enumeration.EDeductionBonus;

public class SequentialSymbolsChecker implements IChecker {
	private static final String SYMBOLS = ")!@#$%^&*()";

	@Override
	public double score(PasswordParametersDTO dto, MetricsDTO cache) {
		long countSequentialSymbols = getQuantitySequenialSymbols(dto.getPassword());
		double score = countSequentialSymbols * EDeductionBonus.SEQUENCESYMBOLS.getBonus();
		setScoreMetricCache(cache, ScoreMetricDTO.of(countSequentialSymbols, score));
		return score;
	}

	@Override
	public void setScoreMetricCache(MetricsDTO cache, ScoreMetricDTO score) {
		getDeductionMetric(cache).setSequentialSymbols(score);
	}

	@Async
	private long getQuantitySequenialSymbols(String password) {
		return getQuantity(SYMBOLS, password);
	}

}