package mn.btv.api.config;

import io.ipinfo.api.IPinfo;
import io.ipinfo.spring.IPinfoSpring;
import io.ipinfo.spring.strategies.attribute.SessionAttributeStrategy; // or any other implementation you wish to use
import io.ipinfo.spring.strategies.interceptor.TrueInterceptorStrategy;
import io.ipinfo.spring.strategies.ip.XForwardedForIPStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.ipinfo.spring.strategies.attribute.AttributeStrategy;
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {
    @Value("${ipinfo.io.api}")
    private String API_KEY;
    @Bean
    public IPinfoSpring ipinfoSpring() {
        return new IPinfoSpring.Builder()
                .setIPinfo(new IPinfo.Builder().setToken(API_KEY).build())
                .interceptorStrategy(new TrueInterceptorStrategy())
                .ipStrategy(new XForwardedForIPStrategy())
                .attributeStrategy(new SessionAttributeStrategy())
                .build();
    }

    @Bean
    public AttributeStrategy attributeStrategy() {
        return new SessionAttributeStrategy(); // or any other implementation you want to use
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipinfoSpring());
    }
}

