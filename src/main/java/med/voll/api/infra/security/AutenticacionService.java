package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    //recordar que autowired aqui no es recomendable, va en setters o constructores
    @Autowired
    private UsuariosRepository usuariosRepository;

    //lo que tenemos que decir en este metodo es de que forma y de donde voy
    //cargar el usario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosRepository.findByLogin(username);
    }
}
