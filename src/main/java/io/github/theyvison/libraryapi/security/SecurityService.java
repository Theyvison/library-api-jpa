package io.github.theyvison.libraryapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.github.theyvison.libraryapi.model.Usuario;
import org.springframework.security.core.Authentication;
import io.github.theyvison.libraryapi.service.UsuarioService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return usuarioService.obterPorLogin(login);
    }
}
