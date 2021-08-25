package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.Service;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.beans.AppletInitializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate;

    private Service newService = new Service();

    private ConsoleService consoleService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }


    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.restTemplate = new RestTemplate();
	}

		public void run () {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

		private void mainMenu () {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

		private void viewCurrentBalance () {


		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity(httpHeaders);

		Balance balance = restTemplate.exchange("http://localhost:8080/balance", HttpMethod.GET,
				entity, Balance.class).getBody();

		System.out.println(balance.getBalance());

	}

		private void viewTransferHistory () {
		List<Transfer> usersTransfers = new ArrayList<>();

		Transfer[] transfers = newService.getAllTransfers(currentUser.getToken());
		System.out.println("----------------------------------------------------------------\n" +
				"Transfer ID:     From/To           Amount:" +
				"\n----------------------------------------------------------------");


		for (int i = 0; i < transfers.length; i++) {
			//account based on current user
			Account account = newService.getAccount(currentUser.getUser().getId(), currentUser.getToken());

			//user object for recipient in transaction
			User userTo = newService.getUserByAccountId(transfers[i].getAccountTo(), currentUser.getToken());

			//user object for sender in transaction
			User userFrom = newService.getUserByAccountId(transfers[i].getAccountFrom(), currentUser.getToken());


			if (transfers[i].getAccountFrom() == account.getAccountId()) {

				System.out.println(
						transfers[i].getTransferId() + "           To:  " + userTo.getUsername() +
								"          $" + transfers[i].getAmount()
				);
				usersTransfers.add(transfers[i]);

			} else if (transfers[i].getAccountTo() == account.getAccountId()) {
				System.out.println(
						transfers[i].getTransferId() + "           From: " + userFrom.getUsername() + "         $"
								+ transfers[i].getAmount()
				);
				usersTransfers.add(transfers[i]);
			}

		}
		try {
			System.out.println("\n\nTo view transfer details, enter a transfer ID: ");
			Scanner input = new Scanner(System.in);
			String inputId = input.nextLine();
			int selectedId = Integer.parseInt(inputId);

			boolean foundTransfer = false;

			for (int i = 0; i < usersTransfers.size(); i++) {

				User userFrom = newService.getUserByAccountId(usersTransfers.get(i).getAccountFrom(), currentUser.getToken());
				User userTo = newService.getUserByAccountId(usersTransfers.get(i).getAccountTo(), currentUser.getToken());


				if (usersTransfers.get(i).getTransferId() == selectedId) {
					Transfer transferDetails = usersTransfers.get(i);

					String descriptionType = newService.transferTypeDescription(transferDetails.getTransferTypeId(),
							currentUser.getToken());

					String descriptionStatus = newService.transferStatusDescription(transferDetails.getTransferStatusId(),
							currentUser.getToken());


					System.out.println("---------------------------" +
							"\nTransfer Details" +
							"\n---------------------------");

					System.out.println("ID: " + transferDetails.getTransferId());
					System.out.println("From: " + userFrom.getUsername());
					System.out.println("To: " + userTo.getUsername());
					System.out.println("Type: " + descriptionType);
					System.out.println("Status: " + descriptionStatus);
					System.out.println("Amount: $" + transferDetails.getAmount());
					foundTransfer = true;
				}

			}
			if (!foundTransfer) {
				System.out.println("TRANSFER SELECTION NOT VALID.\n");
				mainMenu();
			}
		} catch (Exception e) {
			System.out.println("ENTRY NOT VALID.");
			mainMenu();
		}
	}

		private void viewPendingRequests () {

	}

		private void sendBucks () {

    	try {
			Scanner input = new Scanner(System.in);

			long recipientId = 0;


			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.setBearerAuth(currentUser.getToken());
			HttpEntity entity = new HttpEntity(httpHeaders);

			User[] allUsers = newService.getAllUsers(currentUser.getToken());

			System.out.println("--------------------------" +
					"\nUser ID       Name" +
					"\n--------------------------");

			for (int i = 0; i < allUsers.length; i++) {

				if (!allUsers[i].getUsername().equals(currentUser.getUser().getUsername())) {


					System.out.println(allUsers[i].getId() + "          " + allUsers[i].getUsername());
				}

			}
			System.out.println("\n");

			System.out.println("Enter ID of user you are sending to (0 to cancel): ");


			input = new Scanner(System.in);
			String inputSelection = input.nextLine();
			long recipient = Long.parseLong(inputSelection);

			if (recipient == 0) {
				System.out.println("TRANSFER CANCELLED.");
				mainMenu();
			}


			String recipientUsername = "";

			boolean foundUser = false;
			for (int i = 0; i < allUsers.length; i++) {
				if (allUsers[i].getId() == recipient) {

					recipientId = allUsers[i].getId();
					recipientUsername = allUsers[i].getUsername();
					foundUser = true;
				}
			}
			if (!foundUser) {
				System.out.println("THAT IS NOT A VALID USER.\n");
				mainMenu();
			}

			System.out.println("\nEnter an amount to send to " + recipientUsername + ": ");


			String dollarInput = input.nextLine();
			BigDecimal amountInput = new BigDecimal(dollarInput);

			if (Integer.parseInt(String.valueOf(amountInput)) == 0) {
				System.out.println("TRANSFER CANCELLED.");
				mainMenu();
			} else {

				Account account = newService.getAccount(currentUser.getUser().getId(), currentUser.getToken());

				Account recipientAccount = newService.getAccount(recipientId, currentUser.getToken());

				Transfer transfer = new Transfer(2, 2, account.getAccountId(),
						recipientAccount.getAccountId(), new BigDecimal(String.valueOf(amountInput)));


				HttpEntity<Transfer> entity1 = new HttpEntity<>(transfer, httpHeaders);


				try {
					restTemplate.exchange(API_BASE_URL + "users-transfers", HttpMethod.POST, entity1,
							Transfer.class).getBody();
					System.out.println("TRANSFER COMPLETED. YOU SENT $" + amountInput + ".");
				} catch (RestClientResponseException e) {

					System.out.println("INSUFFICIENT FUNDS. TRANSFER NOT COMPLETED.");

				}
			}
			} catch(Exception e){
				System.out.println("ENTRY NOT VALID. TRANSFER NOT COMPLETED.");
				mainMenu();
			}

	}

		private void requestBucks () {
		// TODO Auto-generated method stub

	}

		private void exitProgram () {
		System.exit(0);
	}

		private void registerAndLogin () {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

		private boolean isAuthenticated () {
		return currentUser != null;
	}

		private void register () {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

		private void login () {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

		private UserCredentials collectUserCredentials () {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

}
