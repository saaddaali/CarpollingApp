package ma.zyn.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;


import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.service.facade.admin.driver.DriverAdminService;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.service.facade.admin.passenger.PassengerAdminService;
import ma.zyn.app.utils.security.bean.*;
import ma.zyn.app.utils.security.common.AuthoritiesConstants;
import ma.zyn.app.utils.security.service.facade.*;


import ma.zyn.app.utils.security.bean.User;
import ma.zyn.app.utils.security.bean.Role;

@SpringBootApplication
//@EnableFeignClients
public class AppApplication {
    public static ConfigurableApplicationContext ctx;


    //state: primary success info secondary warning danger contrast
    //_STATE(Pending=warning,Rejeted=danger,Validated=success)
    public static void main(String[] args) {
        ctx=SpringApplication.run(AppApplication.class, args);
    }


    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    @Bean
    public CommandLineRunner demo(UserService userService, RoleService roleService, ModelPermissionService modelPermissionService, ActionPermissionService actionPermissionService, ModelPermissionUserService modelPermissionUserService , DriverAdminService driverService, PassengerAdminService passengerService) {
    return (args) -> {
        if(true){


        /*
        List<ModelPermission> modelPermissions = new ArrayList<>();
        addPermission(modelPermissions);
        modelPermissions.forEach(e -> modelPermissionService.create(e));
        List<ActionPermission> actionPermissions = new ArrayList<>();
        addActionPermission(actionPermissions);
        actionPermissions.forEach(e -> actionPermissionService.create(e));
        */

		// User Admin
        User userForAdmin = new User("admin");
		userForAdmin.setPassword("123");
		// Role Admin
		Role roleForAdmin = new Role();
		roleForAdmin.setAuthority(AuthoritiesConstants.ADMIN);
		roleForAdmin.setCreatedAt(LocalDateTime.now());
		Role roleForAdminSaved = roleService.create(roleForAdmin);
		RoleUser roleUserForAdmin = new RoleUser();
		roleUserForAdmin.setRole(roleForAdminSaved);
		if (userForAdmin.getRoleUsers() == null)
			userForAdmin.setRoleUsers(new ArrayList<>());

		userForAdmin.getRoleUsers().add(roleUserForAdmin);


        userForAdmin.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        userService.create(userForAdmin);

		// User Driver
        Driver userForDriver = new Driver("driver");
		userForDriver.setPassword("123");
		// Role Driver
		Role roleForDriver = new Role();
		roleForDriver.setAuthority(AuthoritiesConstants.DRIVER);
		roleForDriver.setCreatedAt(LocalDateTime.now());
		Role roleForDriverSaved = roleService.create(roleForDriver);
		RoleUser roleUserForDriver = new RoleUser();
		roleUserForDriver.setRole(roleForDriverSaved);
		if (userForDriver.getRoleUsers() == null)
			userForDriver.setRoleUsers(new ArrayList<>());

		userForDriver.getRoleUsers().add(roleUserForDriver);


        userForDriver.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        driverService.create(userForDriver);

		// User Passenger
        Passenger userForPassenger = new Passenger("passenger");
		userForPassenger.setPassword("123");
		// Role Passenger
		Role roleForPassenger = new Role();
		roleForPassenger.setAuthority(AuthoritiesConstants.PASSENGER);
		roleForPassenger.setCreatedAt(LocalDateTime.now());
		Role roleForPassengerSaved = roleService.create(roleForPassenger);
		RoleUser roleUserForPassenger = new RoleUser();
		roleUserForPassenger.setRole(roleForPassengerSaved);
		if (userForPassenger.getRoleUsers() == null)
			userForPassenger.setRoleUsers(new ArrayList<>());

		userForPassenger.getRoleUsers().add(roleUserForPassenger);


        userForPassenger.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        passengerService.create(userForPassenger);

            }
        };
    }




    private static String fakeString(String attributeName, int i) {
        return attributeName + i;
    }

    private static Long fakeLong(String attributeName, int i) {
        return  10L * i;
    }
    private static Integer fakeInteger(String attributeName, int i) {
        return  10 * i;
    }

    private static Double fakeDouble(String attributeName, int i) {
        return 10D * i;
    }

    private static BigDecimal fakeBigDecimal(String attributeName, int i) {
        return  BigDecimal.valueOf(i*1L*10);
    }

    private static Boolean fakeBoolean(String attributeName, int i) {
        return i % 2 == 0 ? true : false;
    }
    private static LocalDateTime fakeLocalDateTime(String attributeName, int i) {
        return LocalDateTime.now().plusDays(i);
    }


    private static void addPermission(List<ModelPermission> modelPermissions) {
        modelPermissions.add(new ModelPermission("Conversation"));
        modelPermissions.add(new ModelPermission("Passenger"));
        modelPermissions.add(new ModelPermission("Ville"));
        modelPermissions.add(new ModelPermission("Reservation"));
        modelPermissions.add(new ModelPermission("Trajet"));
        modelPermissions.add(new ModelPermission("Driver"));
        modelPermissions.add(new ModelPermission("Message"));
        modelPermissions.add(new ModelPermission("CarteBancaire"));
        modelPermissions.add(new ModelPermission("Vehicule"));
        modelPermissions.add(new ModelPermission("User"));
        modelPermissions.add(new ModelPermission("ModelPermission"));
        modelPermissions.add(new ModelPermission("ActionPermission"));
    }

    private static void addActionPermission(List<ActionPermission> actionPermissions) {
        actionPermissions.add(new ActionPermission("list"));
        actionPermissions.add(new ActionPermission("create"));
        actionPermissions.add(new ActionPermission("delete"));
        actionPermissions.add(new ActionPermission("edit"));
        actionPermissions.add(new ActionPermission("view"));
        actionPermissions.add(new ActionPermission("duplicate"));
    }


}


