package tacos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailService;
	
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder encoder(){
		return new StandardPasswordEncoder("53cr3t");
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		//in memeroy
//		auth
//		 .inMemoryAuthentication()
//		   .withUser("buzz")
//		     .password("infinity")
//		     .authorities("ROLE_USER")
//		   .and()
//		   .withUser("woody")
//		     .password("bullseye")
//		     .authorities("ROLE_USER");
		auth
			.userDetailsService(userDetailService)
			.passwordEncoder(encoder());
		     
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/design","/orders")
					.hasRole("USER")
				.antMatchers("/","/**").permitAll()
			
			.and()
				.formLogin()
					.loginPage("/login")
//					.loginProcessingUrl("/authenticate")
//					.usernameParameter("username")
//					.passwordParameter("password")
					.defaultSuccessUrl("/design")
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
				.csrf()
					.ignoringAntMatchers("/h2-console/**")
			//Allow pages to be loaded in frames from the same origin;
			//needed for H2-Console tag::frameOptionsSameOrgin()
			.and()
				.headers()
					.frameOptions()
						.sameOrigin();
	}
	
	
	
}
