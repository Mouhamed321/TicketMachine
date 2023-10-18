package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 :  on n’imprime pas leticket si le montant inséré est insuffisant
	void printTicketIsNotCalledIfNotEnoughMoneyInserted() {
		machine.insertMoney(PRICE - 1);
		assertFalse(machine.printTicket(), "Le ticket n'a pas été imprimé");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void printTicketIsCalledIfEnoughMoneyInserted() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket n'a pas été imprimé");
		assertEquals(PRICE, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S5 : quand on imprime un ticket, la balance est décrémentée du prix du ticket
	void printTicketBalanceDecrementedByTicketPrice() {
		machine.insertMoney(PRICE);
		int initialBalance = machine.getBalance();
		machine.printTicket();
		int updatedBalance = machine.getBalance();
		assertEquals(initialBalance - PRICE, updatedBalance, "La balance n'a pas été correctement mise à jour après impression du ticket");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalAmountIsUpdatedWhenTicketIsPrinted() {
		int initialTotal = machine.getTotal();
		machine.insertMoney(PRICE);
		machine.printTicket();
		int updatedTotal = machine.getTotal();
		assertEquals(initialTotal + PRICE, updatedTotal, "Le montant collecté n'a pas été correctement mis à jour après impression du ticket");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundReturnsCorrectChange() {
		machine.insertMoney(PRICE);
		int refundedAmount = machine.refund();

		assertEquals(PRICE, refundedAmount, "Le remboursement n'est pas correct");
		assertEquals(0, machine.getBalance(), "La balance n'a pas été correctement réinitialisée après remboursement");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundResetsBalanceToZero() {
		machine.insertMoney(PRICE);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été correctement réinitialisée après remboursement");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void cannotInsertNegativeAmount() {
		int initialBalance = machine.getBalance();
		machine.insertMoney(-10);
		int updatedBalance = machine.getBalance();
		assertEquals(initialBalance, updatedBalance, "La balance a été mise à jour avec un montant négatif");
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void cannotCreateMachineWithNegativeTicketPrice() {
		assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-50);
		});
	}






}
