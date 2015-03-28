# spring-token

## Token-based authentication with pre-authentication, using Spring Security and Spring Boot.

### A use case, when it is useful

1. Application B wants to grant acces to its user U to Application A, out application.

2. Application B requests a session token from Application A for its user U, through a server-to-server communication. Application B passes some data about user U amongs request parameters.

3. Application B redirects its user to the entry page of Application A, passing the token generated in the previous step with the redirect URL.

4. Application A receives the token from the U's web browser (client-to-server) and uses the token to identify the user and start a HTTP Session.
