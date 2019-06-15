package ru.sfedu.opencv_project.config;

import ru.sfedu.opencv_project.controller.SegmentationController;
import ru.sfedu.opencv_project.controller.ImageApiController;
import ru.sfedu.opencv_project.controller.MorphologyExecutorController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Saburov Aleksey
 * @date 13.06.2019 21:39
 */

@ApplicationPath("/")
public class RestEasyConfig extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public RestEasyConfig() {
        singletons.add(new ImageApiController());
        singletons.add(new MorphologyExecutorController());
        singletons.add(new SegmentationController());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
