package jp.wozniak.training.kotlinspring.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpOutputMessage
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsService,
    val httpMessageConverter: MappingJackson2HttpMessageConverter,
    val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
) : WebSecurityConfigurerAdapter(),
    AuthenticationSuccessHandler, AuthenticationFailureHandler,
    AuthenticationEntryPoint, AccessDeniedHandler {

    private val CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        // 認可
        http.authorizeRequests().anyRequest().authenticated()

        http
            // AUTHORIZE
//            .authorizeRequests()
//                .mvcMatchers("/prelogin", "/hello/**")
//                    .permitAll()
//                .mvcMatchers("/user/**")
//                    .hasRole("USER")
//                .mvcMatchers("/admin/**")
//                    .hasRole("ADMIN")
//                .anyRequest()
//                    .authenticated()
//            .and()
            // EXCEPTION
            .exceptionHandling()
                .authenticationEntryPoint(this)
                .accessDeniedHandler(this)
            .and()
            // LOGIN
            .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(this)
                .failureHandler(this)
            .and()
            // LOGOUT
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler())
            //.addLogoutHandler(new CookieClearingLogoutHandler())
            .and()
            // CSRF
            .csrf()
            //.disable()
            //.ignoringAntMatchers("/login")
            .csrfTokenRepository(CookieCsrfTokenRepository())

    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val result: MyResult = MyResult("認証成功") // JSONにするオブジェクト
        val outputMessage: HttpOutputMessage = ServletServerHttpResponse(response)
        this.httpMessageConverter.write(result, CONTENT_TYPE_JSON, outputMessage) // Responseに書き込む
        response.status = HttpStatus.OK.value() // 200 OK.
    }
    override fun  onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val result: MyResult = MyResult("認証失敗"); // JSONにするオブジェクト
        val outputMessage: HttpOutputMessage = ServletServerHttpResponse(response);
        this.httpMessageConverter.write(result, CONTENT_TYPE_JSON, outputMessage); // Responseに書き込む
        response.status = HttpStatus.UNAUTHORIZED.value(); // 401 Unauthorized.
    }

//    fun authenticationEntryPoint() : AuthenticationEntryPoint {
//        return SimpleAuthenticationEntryPoint()
//    }
//    fun accessDeniedHandler() : AccessDeniedHandler {
//        return SimpleAccessDeniedHandler()
//    }

    // implementing AuthenticationEntryPoint
    override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authenticationException: AuthenticationException
    ) /*throws IOException, ServletException*/ {
        if (response.isCommitted()) {
            //log.info("Response has already been committed.")
            return
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase())
    }

    // implementing AuthenticationFailureHandler
    override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException
    ) /*throws IOException, ServletException*/ {
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase())
    }


    fun logoutSuccessHandler() : LogoutSuccessHandler {
        return HttpStatusReturningLogoutSuccessHandler()
    }
}



class MyResult (val message: String)

