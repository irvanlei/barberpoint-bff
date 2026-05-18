package com.barberpoint.bff.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

/**
 * Architecture tests to verify clean architecture and vertical slicing patterns.
 * 
 * The architecture follows a Clean Architecture approach with layers:
 * - API Layer: Controllers, REST endpoints, Exception handlers
 * - Application Layer: Services, DTOs, Ports/Interfaces
 * - Infrastructure Layer: External clients, Configurations
 */
@DisplayName("Architecture Tests - Clean Architecture & Vertical Slicing")
public class ArchTests {

    private static final String BASE_PACKAGE = "com.barberpoint.bff";
    private static final String API_LAYER = "com.barberpoint.bff.api..";
    private static final String APPLICATION_LAYER = "com.barberpoint.bff.application..";
    private static final String INFRASTRUCTURE_LAYER = "com.barberpoint.bff.infrastructure..";
    private static final String CONFIG_LAYER = "com.barberpoint.bff.config..";

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

    @Test
    @DisplayName("Application layer should not depend on API layer")
    void applicationLayerShouldNotDependOnApiLayer() {
        noClasses()
                .that().resideInAPackage(APPLICATION_LAYER)
                .should().dependOnClassesThat().resideInAPackage(API_LAYER)
                .because("Application layer (services) should be independent of API layer (controllers)")
                .check(importedClasses);
    }

    @Test
    @DisplayName("API layer should not depend on Infrastructure layer")
    void apiLayerShouldNotDependOnInfrastructureLayer() {
        noClasses()
                .that().resideInAPackage(API_LAYER)
                .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE_LAYER)
                .because("API layer should be independent of infrastructure layer")
                .check(importedClasses);
    }



    @Test
    @DisplayName("Infrastructure layer should not depend on API layer")
    void infrastructureLayerShouldNotDependOnApiLayer() {
        noClasses()
                .that().resideInAPackage(INFRASTRUCTURE_LAYER)
                .should().dependOnClassesThat().resideInAPackage(API_LAYER)
                .because("Infrastructure layer should be independent of API layer")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Controllers should only be in API layer")
    void controllersShouldOnlyBeInApiLayer() {
        classes()
                .that().areAnnotatedWith(RestController.class)
                .should().resideInAPackage(API_LAYER)
                .because("All controllers must be in the API layer for separation of concerns")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Services should only be in Application layer")
    void servicesShouldOnlyBeInApplicationLayer() {
        classes()
                .that().areAnnotatedWith(Service.class)
                .should().resideInAPackage(APPLICATION_LAYER)
                .because("All services must be in the Application layer")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Controllers should not depend on other controllers")
    void controllersShouldNotDependOnOtherControllers() {
        noClasses()
                .that().areAnnotatedWith(RestController.class)
                .should().dependOnClassesThat().areAnnotatedWith(RestController.class)
                .because("Controllers should not call other controllers directly")
                .check(importedClasses);
    }

    @Test
    @DisplayName("No classes should access standard output streams")
    void noClassesShouldAccessStandardStreams() {
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
                .because("System.out and System.err should not be used in production code")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Verify no cyclic dependencies exist")
    void verifyNoCyclicDependencies() {
        SlicesRuleDefinition.slices()
                .matching(BASE_PACKAGE + ".(*)..")
                .should().beFreeOfCycles()
                .because("Cyclic dependencies create maintenance and testing issues")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Controllers should have proper naming convention")
    void controllersShouldHaveProperNamingConvention() {
        classes()
                .that().resideInAPackage(BASE_PACKAGE + ".api.controllers..")
                .and().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller")
                .because("Controllers must follow naming convention")
                .check(importedClasses);
    }

    @Test
    @DisplayName("API exceptions and handlers should follow naming convention")
    void apiExceptionsShouldFollowNamingConvention() {
        classes()
                .that().resideInAPackage("com.barberpoint.bff.api.exceptions..")
                .should().haveSimpleNameEndingWith("Exception")
                .orShould().haveSimpleNameEndingWith("Handler")
                .because("Exception classes must follow proper naming conventions")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Services should have proper naming convention")
    void servicesShouldHaveProperNamingConvention() {
        classes()
                .that().resideInAPackage(APPLICATION_LAYER)
                .and().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("Service")
                .because("Services must follow naming convention")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Infrastructure clients should be in clients package")
    void infrastructureClientsShouldBeInClientsPackage() {
        classes()
                .that().resideInAPackage("com.barberpoint.bff.infrastructure.clients..")
                .should().haveSimpleNameEndingWith("Client")
                .because("Infrastructure clients must be properly organized")
                .check(importedClasses);
    }

    @Test
    @DisplayName("Verify application only depends on infrastructure and config")
    void verifyApplicationLayerDependencies() {
        classes()
                .that().resideInAPackage(APPLICATION_LAYER)
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(APPLICATION_LAYER, INFRASTRUCTURE_LAYER, CONFIG_LAYER, "java..", "org.springframework..")
                .because("Application layer should only depend on Infrastructure and Config layers")
                .check(importedClasses);
    }

}
