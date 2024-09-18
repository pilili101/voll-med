package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//component es el estereotipo mas generico de spring
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    //Este es mi filtro, que va luego que del de spring
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener el token del header
        System.out.println("este es el incio de mi filter");
        var authHeader = request.getHeader("Authorization");
        if(authHeader!=null){
            var token = authHeader.replace("Bearer ","");
            var username = tokenService.getSubject(token);
            System.out.println("Dentro del primer if");
            if(username!=null){
                System.out.println("Dentro del segundo if");
                //si entra aqui entonces el token es valido
                //buscamos un usuario por login
                var usuario = usuariosRepository.findByLogin(username);
                //llamamos metodo para forzar inicio de sesion o autenticacion
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());
                //seteamos manualmente esa autenticacion que se pide en securityConfiguration
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
