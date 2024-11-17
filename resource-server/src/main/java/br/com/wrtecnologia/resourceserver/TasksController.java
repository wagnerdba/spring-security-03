package br.com.wrtecnologia.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @GetMapping
    public String getTasks(@AuthenticationPrincipal Jwt jwt) {
        return """
                    <h1>Top Secret info for: %s</h1>
                    <ol>
                        <li>Claims: %s</li>
                        <li>Tasks.: %s</li>
                        <li>Token.: %s</li>
                """.formatted(jwt.getSubject(), jwt.getClaims(), jwt.getClaims().get("tasks"), jwt.getTokenValue());
    }
}
