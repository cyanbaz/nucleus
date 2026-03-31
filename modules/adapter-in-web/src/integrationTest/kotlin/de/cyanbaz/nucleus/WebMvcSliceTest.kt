package de.cyanbaz.nucleus

import java.lang.annotation.Inherited
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.ActiveProfiles

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@WebMvcTest
@Inherited
@MustBeDocumented
@ActiveProfiles("test")
annotation class WebMvcSliceTest
