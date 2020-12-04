package example.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //этот класс мы используем,чтобы заменить web.xml файл для конфигурации томкат
    //мы наслудемся от класса другого для определения этих трех методов,а этот класс в своб очередь реализует итерфейс WebApplicationInitializer


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};//SpringConfig выполняет роль ApplicationContext.xml,в кот.мы создавали вручную бины
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};//любой запрос на любой URL(на наш сервер) должен перенаправляться на наш DispatcherServlet
    }
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}
