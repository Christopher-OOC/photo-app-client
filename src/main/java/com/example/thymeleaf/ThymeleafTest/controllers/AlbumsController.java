package com.example.thymeleaf.ThymeleafTest.controllers;

import com.example.thymeleaf.ThymeleafTest.response.AlbumRest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class AlbumsController {

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {

        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String tokenValue = idToken.getTokenValue();
        System.out.println("Token Value = " + tokenValue);
        System.out.println("Authorities Value = " + principal.getAuthorities());


        AlbumRest album1 = new AlbumRest();
        album1.setAlbumId("albumOne");
        album1.setAlbumTitle("Album one title");
        album1.setAlbumUrl("http://localhost:8082/albums/1");

        AlbumRest album2 = new AlbumRest();
        album2.setAlbumId("albumTwo");
        album2.setAlbumTitle("Album two title");
        album2.setAlbumUrl("http://localhost:8082/albums/2");


        model.addAttribute("albums", Arrays.asList(album1, album2));

        return "main";
    }

}
