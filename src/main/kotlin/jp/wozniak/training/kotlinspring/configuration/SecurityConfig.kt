package jp.wozniak.training.kotlinspring.configuration

import org.springframework.beans.factory.annotation.Autowired
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
import java.io.IOException
import javax.servlet.ServletException

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsService,
    val httpMessageConverter: MappingJackson2HttpMessageConverter
) : WebSecurityConfigurerAdapter()
    , AuthenticationSuccessHandler, AuthenticationFailureHandler
    , AuthenticationEntryPoint, AccessDeniedHandler
{
    private val CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8

    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(
        auth: AuthenticationManagerBuilder
    ) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

//        http.authorizeRequests().anyRequest().permitAll()

        http.authorizeRequests()
            .mvcMatchers("/login","/users","/users/**").permitAll()
//            .mvcMatchers("/user/**").hasRole("USER")
//            .mvcMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()

        http.exceptionHandling()
            .authenticationEntryPoint(this)
            .accessDeniedHandler(this)

        http.formLogin()
            .loginProcessingUrl("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
            .successHandler(this)
            .failureHandler(this)

        http.logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler())
            /* .addLogoutHandler(CookieClearingLogoutHandler()) */

        http.csrf()
            .disable()
//            .ignoringAntMatchers("/login")
//            .csrfTokenRepository(CookieCsrfTokenRepository())
    }

    // implementing AuthenticationSuccessHandler
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val result = MyResult("認証成功") // JSONにするオブジェクト
        val outputMessage: HttpOutputMessage = ServletServerHttpResponse(response)
        this.httpMessageConverter.write(result, CONTENT_TYPE_JSON, outputMessage) // Responseに書き込む
        response.status = HttpStatus.OK.value() // 200 OK.
    }

    // implementing AuthenticationFailureHandler
    @Throws(IOException::class, ServletException::class)
    override fun  onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val result = MyResult("認証失敗") // JSONにするオブジェクト
        val outputMessage: HttpOutputMessage = ServletServerHttpResponse(response)
        this.httpMessageConverter.write(result, CONTENT_TYPE_JSON, outputMessage) // Responseに書き込む
        response.status = HttpStatus.UNAUTHORIZED.value() // 401 Unauthorized.
    }

    // implementing AuthenticationEntryPoint
    @Throws(IOException::class, ServletException::class)
    override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authenticationException: AuthenticationException
    ) {
        if (response.isCommitted()) {
            //log.info("Response has already been committed.")
            return
        }
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase())
    }

    // implementing AccessDeniedHandler
    @Throws(IOException::class, ServletException::class)
    override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException
    ) {
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase())
    }


    fun logoutSuccessHandler() : LogoutSuccessHandler {
        return HttpStatusReturningLogoutSuccessHandler()
    }
}



class MyResult (val message: String)

