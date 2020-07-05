//package com.fth.gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//public class MyWebRoutes {
//    @Bean
//    RouterFunction<?> iconResources() {
//        return RouterFunctions
//                .resources("/favicon.**", new ClassPathResource("images/favicon.ico"));
//    }
//
//    /**
//     * Next we will have a route to the login page.
//     * Since we already overrode the default location in ServerHttpSecurity, we must provide the route to our new login page.
//     * Also included in this example is our logout landing page.
//     * it can be any URL you select, for this example we have a single page to tell the user ‘goodbye’.
//     *
//     * @return
//     */
//    @Bean
//    RouterFunction<?> viewRoutes() {
//
//
//        return RouterFunctions
//                .route(RequestPredicates.GET("/login"),
//                        req -> ServerResponse
//                                .ok()
//                                .render("login-form",
//                                        req.exchange().getAttributes())
//                )
//                .andRoute(RequestPredicates.GET("/bye"),
//                        req -> ServerResponse.ok().render("bye")
//                );
//    }
//}
