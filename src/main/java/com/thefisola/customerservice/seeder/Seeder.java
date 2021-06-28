package com.thefisola.customerservice.seeder;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import com.thefisola.customerservice.model.Agent;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.model.User;
import com.thefisola.customerservice.repository.AgentRepository;
import com.thefisola.customerservice.repository.CustomerRequestRepository;
import com.thefisola.customerservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class Seeder {

    private static final String CSV_HEADER = "User ID,Timestamp (UTC),Message Body";
    private final CustomerRequestRepository customerRequestRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Autowired
    public Seeder(CustomerRequestRepository customerRequestRepository, UserRepository userRepository, AgentRepository agentRepository) {
        this.customerRequestRepository = customerRequestRepository;
        this.userRepository = userRepository;
        this.agentRepository = agentRepository;
    }

    public static int getRandomIndex(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @EventListener
    @Transactional
    public void run(ContextRefreshedEvent event) {
        if (seedDataAlreadyExists()) return;
        seedDefaultData();
    }

    private boolean seedDataAlreadyExists() {
        return (!customerRequestRepository.findAll().isEmpty() && !userRepository.findAll().isEmpty());
    }

    private void seedDefaultData() {
        seedAgents();
        var users = seedUsers();
        seedCustomerMessages(users);
    }

    private void seedCustomerMessages(List<User> users) {
        List<List<String>> customerMessages = getCustomerMessagesFromCSV();
        List<CustomerRequest> customerRequests = new ArrayList<>();

        for (List<String> customerMessage : customerMessages) {
            String messageBody = customerMessage.get(2);
            var customerRequest = CustomerRequest.builder()
                    .message(messageBody)
                    .user(users.get(getRandomIndex(0, 2)))
                    .status(CustomerRequestStatus.AWAITING_RESPONSE)
                    .type(getRequestTypeFromMessage(messageBody))
                    .build();

            customerRequests.add(customerRequest);
        }
        customerRequestRepository.saveAll(customerRequests);
    }

    private List<User> seedUsers() {
        var user1 = User.builder()
                .name("Adigun Adefisola")
                .email("sample-user-1@gmail.com")
                .build();
        var user2 = User.builder()
                .name("Lionel Messi")
                .email("sample-user-2@gmail.com")
                .build();
        var user3 = User.builder()
                .name("Eden Hazard")
                .email("sample-user-3@gmail.com")
                .build();

        return userRepository.saveAll(List.of(user1, user2, user3));
    }

    private List<Agent> seedAgents() {
        var agent1 = Agent.builder()
                .name("Jorge Mendes")
                .email("sample-agent-1@gmail.com")
                .build();
        var agent2 = Agent.builder()
                .name("Romelu Lukaku")
                .email("sample-agent-2@gmail.com")
                .build();
        var agent3 = Agent.builder()
                .name("Reece James")
                .email("sample-agent-3@gmail.com")
                .build();

        return agentRepository.saveAll(List.of(agent1, agent2, agent3));
    }

    private CustomerRequestType getRequestTypeFromMessage(String message) {
        if (message.toLowerCase().contains("loan")) {
            return CustomerRequestType.LOAN;
        } else if (message.toLowerCase().contains("wallet")) {
            return CustomerRequestType.WALLET;
        } else if (message.toLowerCase().contains("enquire")) {
            return CustomerRequestType.ENQUIRY;
        } else {
            return CustomerRequestType.OTHERS;
        }
    }

    private List<List<String>> getCustomerMessagesFromCSV() {
        List<List<String>> customerMessages = new ArrayList<>();
        try (var bufferedReader = new BufferedReader(new FileReader("src/main/resources/db/data/customer_request_sample_data.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains(CSV_HEADER)) {
                    String[] values = line.split(",");
                    customerMessages.add(Arrays.asList(values));
                }
            }
        } catch (IOException e) {
            log.error("Error occurred while trying to parse csv ", e);
        }
        return customerMessages;
    }
}
