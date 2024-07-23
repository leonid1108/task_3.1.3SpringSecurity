package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private final UserRepository userRepository;

    @Autowired
    public Init(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void postConstruct() {
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User("admin", "admin", "admin@mail.ru", 20);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user") == null) {
            User user = new User("user", "user", "user@mail.ru", 22);
            userRepository.save(user);
        }
    }
}
