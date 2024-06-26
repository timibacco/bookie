package core.bookie.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {


    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final CustomDetailService userDetailsService;



    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {


        final var header = request.getHeader("Authorization");

        final String token;

        final String userId;

        try {
            if (header == null || header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return ;
            }

            token = header.substring(7); // get the set of strings after "Bearer" .......................................

            userId = tokenService.getUsername(token);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetails = userDetailsService.loadUserByUsername(userId);



                var isTokenValid = tokenService.isTokenValid(token, userDetails);

            if (tokenService.isTokenValid(token, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
        // let's catch some errors and set some status.

        catch (
    ExpiredJwtException e){
        logger.error(e.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        System.out.println("Token has expired");
        response.getWriter().write("Token has expired");
    }
        catch (
    UsernameNotFoundException e){
        logger.error(e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("User not found");

    }
}
}







