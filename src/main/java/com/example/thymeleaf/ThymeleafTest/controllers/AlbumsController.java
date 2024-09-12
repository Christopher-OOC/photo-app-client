package com.example.thymeleaf.ThymeleafTest.controllers;

import com.example.thymeleaf.ThymeleafTest.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    private OAuth2AuthorizedClientService auth2ClientService;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient oAuth2Client = auth2ClientService.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        String jwtAccessToken = oAuth2Client.getAccessToken().getTokenValue();
        System.out.println("jtwAccessToken = " + jwtAccessToken);

        System.out.println("Principal = " + principal);

        OidcIdToken idToken = principal.getIdToken();
        String tokenValue = idToken.getTokenValue();
        System.out.println("Token Value = " + tokenValue);
        System.out.println("Authorities Value = " + principal.getAuthorities());


        String url = "http://localhost:8082/albums";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<List<AlbumRest>> entity = new HttpEntity<>(headers);

        ResponseEntity<List<AlbumRest>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });

        List<AlbumRest> albums = responseEntity.getBody();

          model.addAttribute("albums", albums);

        return "main";
    }

}
