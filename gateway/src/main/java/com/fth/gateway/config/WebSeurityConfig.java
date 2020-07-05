package com.fth.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import java.net.URI;


@Configuration
@EnableWebFluxSecurity
public class WebSeurityConfig {
    @Autowired
    private MyLogoutHandle myLogoutHandle;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        SecurityWebFilterChain chain = http
                .authorizeExchange()
                .anyExchange().authenticated()
//                .pathMatchers("/**")
//                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                //.authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login?error"))
                //.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/admin"))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutHandler(myLogoutHandle)
                .logoutSuccessHandler(logoutSuccessHandler("/login"))
                .and()
                .csrf()
                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
//                .disable().authorizeExchange()
//                .csrfTokenRepository(customCsrfTokenRepository)
//                .requireCsrfProtectionMatcher(customCsrfMatcher)
                .and()
//                .cors()
//                .and()
                .build();
        return chain;

        /**
         * During ServerHttpSecurity configuration, we added the line for csrf() that has the effect of implementing request/response filtering.
         * The effect of this Filter - CsrfWebFilter is to create, store and validate csrf tokens where seen or needed.
         * We can expose the CSRF token by including the form entry ‘_csrf’ and accessing our view model to extract the token value.
         * Remember that the parameter and headers names may be changed by additional configuration to the CsrfSpec during configuration.
         *
         * This filter allows us to access the CSRF token per request, or as needed if you are explicit about the paths that must be matched.
         * CSRF tokens are stored as the classname org.springframework.security.web.csrf within the request attributes map.
         * We can access this and determine how to expose it to the view by calling the token parameterName() method (then placing the token as that name in the model map).
         */
        /*
        *  .filter((req, resHandler) ->
                        req.exchange()
                                .getAttributeOrDefault(
                                        CsrfToken.class.getName(),
                                        Mono.empty().ofType(CsrfToken.class)
                                )
                                .flatMap(csrfToken -> {
                                    req.exchange()
                                            .getAttributes()
                                            .put(csrfToken.getParameterName(), csrfToken);
                                    return resHandler.handle(req);
                                })

                );
    }
        * */

        /**
         * Data Model for Views
         * The last route will require some information about the user logged in.
         * We can construct the model for our mustache template by incluing a Map<String, Object> as the second argument to the render() method.
         *
         * To get to the logged-in user, we get the principal from the ServerRequest object, cast it to it’s value type, and inject it into request attributes Map under the ‘user’ key.
         * The attributes map (model object) is then passed in to the render() function.
         */
//        .andRoute(RequestPredicates.GET("/"),
//                req -> req.principal()
//                        .ofType(Authentication.class)
//                        .flatMap(auth -> {
//                            User user = User.class.cast(auth.getPrincipal());
//                            req.exchange()
//                                    .getAttributes()
//                                    .putAll(Collections.singletonMap("user", user));
//                            return ServerResponse.ok().render("game",
//                                    req.exchange().getAttributes());
//                        })
//        )
    }

    public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }



//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        System.out.println("userdetails");
//        //内存中缓存权限数据
//        User.UserBuilder userBuilder = User.builder();
//        UserDetails admin = userBuilder.username("admin").password(passwordEncoder.encode("123456")).roles("USER", "ADMIN").build();
//        UserDetails user = userBuilder.username("user").password(passwordEncoder.encode("123456")).roles("USER").build();
//
//        // 输出加密密码
//        String encodePassword = passwordEncoder.encode("123456");
//        System.out.println(admin);
//
//        return new MapReactiveUserDetailsService(admin,user);
//    }
}
