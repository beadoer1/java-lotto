package edu.nextstep.lottoauto.view;

import edu.nextstep.lottoauto.domain.Prize;
import edu.nextstep.lottoauto.domain.Ticket;
import edu.nextstep.lottoauto.form.WinningResultForm;

import java.util.List;
import java.util.Map;

public class ResultView {
    public static void printTickets(List<Ticket> tickets) {
        System.out.println(tickets.size() + " 개를 구매하셨습니다.");
        for (Ticket ticket : tickets) {
            System.out.println(ticket.getNumbers());
        }
    }

    public static void printWinningResult(WinningResultForm winningResultForm) {
        System.out.println(System.lineSeparator() + "당첨 통계");
        System.out.println("---------");

        Map<Prize, Integer> winningResult = winningResultForm.getWinningResult();
        double rateOfReturn = winningResultForm.getRateOfReturn();

        for(Prize prize : Prize.values()) {
            if (prize.equals(Prize.NULL)) {
                break;
            }
            System.out.println(prize.getCountOfMatch() + "개 일치 " +
                    "(" + prize.getWinningPrize() + "원)- " +
                    winningResult.getOrDefault(prize,0) + "개"
            );
        }
        System.out.printf("총 수익률은 %.2f 입니다.", + rateOfReturn);
    }
}
