package com.thefisola.customerservice.seeder;

import com.thefisola.customerservice.constant.CustomerRequestStatus;
import com.thefisola.customerservice.constant.CustomerRequestType;
import com.thefisola.customerservice.model.CustomerRequest;
import com.thefisola.customerservice.model.User;
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

@Slf4j
@Component
public class Seeder {

    private static final String CSV_HEADER = "User ID,Timestamp (UTC),Message Body";
    private final CustomerRequestRepository customerRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public Seeder(CustomerRequestRepository customerRequestRepository, UserRepository userRepository) {
        this.customerRequestRepository = customerRequestRepository;
        this.userRepository = userRepository;
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
        var user = seedUser();
        seedCustomerMessages(user);
    }

    private void seedCustomerMessages(User user) {
        List<List<String>> customerMessages = getCustomerMessagesFromCSV();
        List<CustomerRequest> customerRequests = new ArrayList<>();

        for (List<String> customerMessage : customerMessages) {
            String messageBody = customerMessage.get(2);
            var customerRequest = CustomerRequest.builder()
                    .message(messageBody)
                    .user(user)
                    .status(CustomerRequestStatus.AWAITING_RESPONSE)
                    .type(getRequestTypeFromMessage(messageBody))
                    .build();

            customerRequests.add(customerRequest);
        }
        customerRequestRepository.saveAll(customerRequests);
    }


    private User seedUser() {
        var user = User.builder()
                .name("Adigun Adefisola")
                .email("sample-user@gmail.com")
                .build();
        return userRepository.save(user);
    }

    private CustomerRequestType getRequestTypeFromMessage(String message) {
        if (message.toLowerCase().contains("loan")) {
            return CustomerRequestType.LOAN;
        } else if (message.toLowerCase().contains("wallet")) {
            return CustomerRequestType.WALLET;
        } else {
            return CustomerRequestType.OTHERS;
        }
    }

    private List<List<String>> getCustomerMessagesFromCSV() {
        List<List<String>> customerMessages = new ArrayList<>();
        try (var bufferedReader = new BufferedReader(new FileReader("src/main/resources/static/customer_request_sample_data.csv"))) {
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
