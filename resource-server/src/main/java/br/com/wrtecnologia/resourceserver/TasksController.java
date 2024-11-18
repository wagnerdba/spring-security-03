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
               <html>
                  <head>
                     <style>
                        body {
                             font-family: 'Courier New', Arial, sans-serif;
                             font-size: 16px;
                             color: black;
                        }
                        li, h1 {
                             font-size: 18px;
                             color: red;
                        }
                     </style>
                  </head>
                  <body>
                     <h1>Top Secret info for: %s</h1>
                        <ol>
                          <li>Claims:</li>%s
                          <li>Tasks:</li>%s
                          <li>Token:</li>%s
                        </ol>
                  </body>
               </html>
               """.formatted(jwt.getSubject(), jwt.getClaims(), jwt.getClaims().get("tasks"), jwt.getTokenValue());
    }
}
