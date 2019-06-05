package jp.wozniak.training.kotlinspring.configuration

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


@EnableWebSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsService,
    val passwordEncoder: BCryptPasswordEncoder, //TODO korede iino???
    val httpMessageConverter: MappingJackson2HttpMessageConverter
    ) : WebSecurityConfigurerAdapter(), AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private val CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        // 認可
        http.authorizeRequests().anyRequest().authenticated()

//        http.authorizeRequests()
//            .antMatchers(TOP_PAGE_URL, ERROR_URL).permitAll() // 全許可
//            .antMatchers(TOP_PAGE_URL, "/plan/detail/**", LOGIN_URL, "/user/password/reminder/**", "/user/add/**").permitAll() // アカウント非保持者
//            .antMatchers("/plan/**", "/user/password/**").hasRole("USER") // アカウント保持者対象
//            .antMatchers("/user/**").hasRole("ADMIN") // 管理権限対象
//            .anyRequest().authenticated().and() // 指定以外はアクセス不可 「/」は/plan/detailにリダイレクトされる

        // ログイン
        http.formLogin()
            .successHandler(this)
            .failureHandler(this)

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

        // ログアウト
//        http.logout().logoutRequestMatcher(AntPathRequestMatcher("/logout**")) // ログアウト処理のパス マッチャーで当てたいのでこうなっている？
//            .logoutSuccessUrl(TOP_PAGE_URL) // ログアウト後の遷移先
//            .deleteCookies("JSESSIONID").and()

        // CSRFトークン生成
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository())
    }
}

class MyResult {
    val message: String?
}