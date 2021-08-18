package edu.nextstep.lottoauto.domain;

import edu.nextstep.lottoauto.domain.ticketmaker.CustomNumbersMaker;
import edu.nextstep.lottoauto.view.form.WinningResultForm;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WinningCheckMachine {

    private static final int TICKET_PRICE = TicketMachine.TICKET_PRICE;

    public static WinningResultForm confirmWinningResult(String winningNumbersString) {
        Ticket winningTicket = Ticket.madeBy(new CustomNumbersMaker(winningNumbersString));
        List<Ticket> tickets = TicketRepository.findAll();

        Map<Prize, Integer> winningResult = makeWinningResult(winningTicket, tickets);

        double rateOfReturn = ((double)calculateTotalPrize(winningResult) / calculatePayment(tickets));

        return new WinningResultForm(winningResult, rateOfReturn);
    }

    private static Map<Prize, Integer> makeWinningResult(Ticket winningTicket, List<Ticket> tickets) {
        Map<Prize, Integer> winningResult = new LinkedHashMap<>();

        for (Ticket ticket : tickets) {
            int countOfMatching = ticket.countMatchingNumbers(winningTicket);
            Prize prize = Prize.of(countOfMatching);
            winningResult.put(prize, (winningResult.getOrDefault(prize,0)+1));
        }

        return winningResult;
    }

    private static long calculateTotalPrize(Map<Prize, Integer> winningResult) {
        long totalPrize = 0;
        for(Prize prize : Prize.values()) {
            totalPrize += ((long)prize.getWinningPrize() * winningResult.getOrDefault(prize,0));
        }
        return totalPrize;
    }

    private static int calculatePayment(List<Ticket> tickets) {
        return tickets.size() * TICKET_PRICE;
    }
}
