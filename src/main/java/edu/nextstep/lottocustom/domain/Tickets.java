package edu.nextstep.lottocustom.domain;

import edu.nextstep.lottocustom.domain.numbersmaker.CustomNumbersMaker;
import edu.nextstep.lottocustom.domain.numbersmaker.NumbersMaker;

import java.util.*;

public class Tickets {
    private final List<Ticket> tickets;

    private Tickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public static Tickets of(Payment payment,
                             List<String> customTicketsString,
                             NumbersMaker numbersMaker) {

        List<Ticket> tickets = makeCustomTickets(customTicketsString);

        int numberOfAutoTickets = payment.countOfTickets() - customTicketsString.size();
        tickets.addAll(makeAutoTickets(numberOfAutoTickets, numbersMaker));

        return new Tickets(tickets);
    }

    private static List<Ticket> makeCustomTickets(List<String> customTicketsString) {
        List<Ticket> tickets = new ArrayList<>();

        for (String numbersString : customTicketsString) {
            Ticket ticket = Ticket.madeBy(new CustomNumbersMaker(numbersString));
            tickets.add(ticket);
        }
        return tickets;
    }

    private static List<Ticket> makeAutoTickets(int countOfTickets, NumbersMaker numbersMaker) {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < countOfTickets; i++) {
            Ticket ticket = Ticket.madeBy(numbersMaker);
            tickets.add(ticket);
        }
        return tickets;
    }

    public Map<Prize, Integer> checkWinningResult(WinningTicket winningTicket) {
        Map<Prize, Integer> winningResult = createAndInitWinningResult();

        for (Ticket ticket : tickets) {
            Prize prize = winningTicket.checkPrize(ticket);
            winningResult.put(prize, winningResult.getOrDefault(prize, 0)+1);
        }

        return winningResult;
    }

    private Map<Prize, Integer> createAndInitWinningResult() {
        Map<Prize, Integer> winningResult = new LinkedHashMap<>();

        Arrays.stream(Prize.values())
                .forEach(prize -> winningResult.put(prize, winningResult.getOrDefault(prize, 0)));

        return winningResult;
    }

    protected boolean isSameSize(int countOfTickets) {
        return tickets.size() == countOfTickets;
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tickets tickets1 = (Tickets) o;
        return Objects.equals(tickets, tickets1.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tickets);
    }
}
