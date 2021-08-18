package edu.nextstep.lottoauto.domain;

import edu.nextstep.lottoauto.exception.PaymentIllegalArgumentException;
import edu.nextstep.lottoauto.domain.ticketmaker.NumbersMaker;

import java.util.*;

public class TicketMachine {

    public static final int TICKET_PRICE = 1_000;

    public static void createAndSaveTickets(int payment, NumbersMaker numbersMaker) {
        validateUnderAUnitPrice(payment);
        validateDivideUnitPrice(payment);

        int numberOfTickets = calculateNumberOfTicketsFrom(payment);

        List<Ticket> tickets = createTickets(numberOfTickets, numbersMaker);

        TicketRepository.saveAll(tickets);
    }

    public static int calculateNumberOfTicketsFrom(int payment) {
        return payment / TICKET_PRICE;
    }

    private static List<Ticket> createTickets(int numberOfTickets, NumbersMaker numbersMaker) {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < numberOfTickets; i++) {
            Ticket ticket = Ticket.madeBy(numbersMaker);
            tickets.add(ticket);
        }
        return tickets;
    }

    private static void validateUnderAUnitPrice(int payment) {
        if (payment < TICKET_PRICE) {
            throw new PaymentIllegalArgumentException("최소 입력 가능 금액 미달. 최소 입력 금액 : " + TICKET_PRICE + " 원");
        }
    }

    private static void validateDivideUnitPrice(int payment) {
        if ((payment % TICKET_PRICE) != 0) {
            throw new PaymentIllegalArgumentException("개 당 금액 " + TICKET_PRICE + " 원으로 해당 단위로 입력 필요.");
        }
    }
}
