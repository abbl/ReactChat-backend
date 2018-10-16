package pl.abbl.reactchat.security;

import java.util.Arrays;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import pl.abbl.reactchat.filter.JwtAuthenticationFilter;
import pl.abbl.reactchat.filter.JwtAuthorizationFilter;
import pl.abbl.reactchat.security.rest.RestAuthenticationEntryPoint;
import pl.abbl.reactchat.security.rest.RestAuthenticationSuccessHandler;

import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

@Component
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private Gson gson;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder);
	}

	//@formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.cors()
				.and()
		.csrf()
				.disable()
				.exceptionHandling()
				.and()
		.authorizeRequests()
                .antMatchers("/api/secure/**").authenticated()
			.anyRequest().permitAll()
			.and()
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), gson))
				.addFilter(new JwtAuthorizationFilter(authenticationManager()))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
    //@formatter:on

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
}
