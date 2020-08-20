package sudo.lcx.springbootsecurityfuxi01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import sudo.lcx.springbootsecurityfuxi01.service.UserDetailServiceImpl;

import javax.annotation.Resource;

/**
 * 继承WebSecurityConfigurerAdapter类
 * 重写configure方法
 * 必须加上这两个注解才会生效
 * Configuration
 * EnableWebSecurity
 * @author lichengxin
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailServiceImpl userDetailServiceImpl;
    @Resource
    private SuccessHandler successHandler;
    @Resource
    private FailHandler failHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //代表这个路径只能这个角色来访问
                .antMatchers("/admin/**").hasAnyAuthority("admin")
                .antMatchers("/user/**").hasAnyAuthority("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //认证的页面
                .loginPage("/login.html")
                //认证页面提交时候走的路径是什么 这个路径要与登录页面的form中action路径一致
                .loginProcessingUrl("/login")
                //登录成功跳转那个路径
                //.defaultSuccessUrl("/success.html")
                .successHandler(successHandler)
                //认证失败要走的url路径是什么
                //.failureUrl("/fail.html")
                .failureHandler(failHandler)
                .usernameParameter("userName")
                .passwordParameter("UserPassWord")
                .permitAll()
                .and()
                //防止跨域攻击  禁止跨域请求  ignoringAntMatcher代表能够进行跨域请求的路径
                // .csrf().ignoringAntMatchers().disable();
                .csrf().disable();
    }
    /**
     *  重写这个方法验证密码是不是正确的
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            /**
             * 看一下输入的密码跟保存在user中的密码是否一样
             * @param charSequence;
             * @param s;
             * @return boolean;
             */
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.contentEquals(charSequence);
            }
        });
    }
}
