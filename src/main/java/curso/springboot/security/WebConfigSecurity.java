package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService;
	
	
	@Override //Configura as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable() //Desativa as configurações padrão de memoria do spring
			.authorizeRequests() //Permitir Restringir acessos
			.antMatchers(HttpMethod.GET, "/").permitAll()//Qualquer usuario acessa a página inicial
			.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin().permitAll() //permite qualquer usuário
			.loginPage("/login")
			.defaultSuccessUrl("/cadastropessoa")
			.failureUrl("/login?error=true")
			.and().logout().logoutSuccessUrl("/login") //Mapeia a URL de sair do sistema e invalida usuario autenticado
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //cria a autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());			
		
	}
	
	@Override //Ignora url específicas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}

}
