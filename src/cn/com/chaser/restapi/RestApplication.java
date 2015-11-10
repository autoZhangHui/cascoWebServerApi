    package cn.com.chaser.restapi;  
      
    import org.glassfish.jersey.filter.LoggingFilter;  
    import org.glassfish.jersey.server.ResourceConfig;  
      
    public class RestApplication extends ResourceConfig {  
      
        public RestApplication() {  
      
            packages("cn.com.chaser.restapi");  
      
            register(LoggingFilter.class);  
        }  
    }  