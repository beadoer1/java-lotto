package edu.nextstep.lottoauto.machine;

import edu.nextstep.lottoauto.domain.Prize;
import edu.nextstep.lottoauto.domain.Ticket;
import edu.nextstep.lottoauto.form.WinningResultForm;
import edu.nextstep.lottoauto.ticketmaker.NumbersMaker;
import edu.nextstep.lottoauto.repository.TicketRepository;

import java.util.*;

public class TicketMachine {

    private static final int A_UNIT_PRICE = 1_000;

    private final TicketRepository ticketRepository = new TicketRepository();

    public void createAndSaveTickets(int numberOfTickets, NumbersMaker numbersMaker) {
        for (int i = 0; i < numberOfTickets; i++) {
            Ticket ticket = Ticket.of(numbersMaker);
            ticketRepository.save(ticket);
        }
    }

    public List<Ticket> findTickets() {
        return ticketRepository.findAll();
    }

    public WinningResultForm confirmWinningResult(Ticket winningTicket) {
        List<Ticket> tickets = ticketRepository.findAll();

        Map<Prize, Integer> winningResult = makeWinningResult(winningTicket, tickets);

        double rateOfReturn = ((double)calculateTotalPrize(winningResult) / calculatePayment(tickets));

        return new WinningResultForm(winningResult, rateOfReturn);
    }

    private Map<Prize, Integer> makeWinningResult(Ticket winningTicket, List<Ticket> tickets) {
        Map<Prize, Integer> winningResult = new LinkedHashMap<>();

        for (Ticket ticket : tickets) {
            int countOfMatching = ticket.countMatchingNumbers(winningTicket);
            Prize prize = Prize.of(countOfMatching);
            winningResult.put(prize, (winningResult.getOrDefault(prize,0)+1));
        }

        return winningResult;
    }

    private long calculateTotalPrize(Map<Prize, Integer> winningResult) {
        long totalPrize = 0;
        for(Prize prize : Prize.values()) {
            totalPrize += ((long)prize.getWinningPrize() * winningResult.getOrDefault(prize,0));
        }
        return totalPrize;
    }

    private int calculatePayment(List<Ticket> tickets) {
        return tickets.size() * A_UNIT_PRICE;
    }
}
