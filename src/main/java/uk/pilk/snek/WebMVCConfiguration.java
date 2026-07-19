package uk.pilk.snek;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        var ppp = configurer.getPatternParserOrDefault();
        ppp.setCaseSensitive(true);
        configurer.setPatternParser(ppp);
    }

    @Bean
    public UrlHandlerFilter thing(){
        return UrlHandlerFilter.trailingSlashHandler("/**").
                        redirect(HttpStatus.TEMPORARY_REDIRECT).
                        build();
    }
}
