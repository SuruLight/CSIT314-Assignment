package com.FRA.login_system.assertiontest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Import all entities explicitly from their correct entity domain packages
import com.FRA.login_system.entity.UserAccount;
import com.FRA.login_system.entity.UserProfile;

// Import all distinct update service layer scripts
import com.FRA.login_system.service.UserAccountService.UpdateUserAccountService;
import com.FRA.login_system.service.UserProfileService.UpdateUserProfileService;
import com.FRA.login_system.service.ActivityService.UpdateActivityService;
import com.FRA.login_system.service.FSACategoryService.UpdateFSACategoryService;

@SpringBootTest
public class UpdateLogicTest {

    @Autowired
    private UpdateUserAccountService updateAccountService;

    @Autowired
    private UpdateUserProfileService updateProfileService;

    @Autowired
    private UpdateActivityService updateActivityService;

    @Autowired
    private UpdateFSACategoryService updateFSACategoryService;

    /**
     * Service Check 1: User Account Profile Role and Permission Modifications
     */
    @Test
    public void testUpdateUserAccountLogic() {
        // 1. Action: Modify seed user profile permissions metadata mapping matrix
        String testUser = "admin";
        String newPermissions = "SEARCH_USER,UPDATE_USER";
        
        UserAccount updatedAccount = updateAccountService.updateUserAccount(testUser, "USER_ADMIN", newPermissions);

        // 2. Equation: Assert the target fields updated correctly in data layer cache
        assertNotNull(updatedAccount, "Updated user account instance should not return null");
        assertEquals(newPermissions, updatedAccount.getPermissions(), "Permissions must match updated properties string");
    }

    /**
     * Service Check 2: Core User Profile Configuration Updates
     */
    @Test
    public void testUpdateUserProfileLogic() {
        // 1. Action: Change the system string identity descriptors of the 'FR' role profile setup
        String newName = "Senior Fund Raiser";
        String newPerms = "VIEW_USER,UPDATE_USER";
        
        UserProfile updatedProfile = updateProfileService.updateProfile("FR", newName, newPerms);

        // 2. Equation: Assert modifications successfully persisted onto core metadata entity profile
        assertNotNull(updatedProfile, "Updated user profile structural entry should not return null");
        assertEquals(newName, updatedProfile.getProfileName(), "Profile name property should match updated state value");
        assertEquals(newPerms, updatedProfile.getPermissions(), "Profile authorization string array should match updated parameters");
    }

    /**
     * Service Check 3: Ongoing Campaign Activity Target Parameters Modification
     */
    @Test
    public void testUpdateActivityServiceLogic() {
        // 1. Action: Execute update modification logic on target entry ID 1
        int targetActivityId = 1;
        String updatedName = "Emergency Medical Supplies Drive";
        String updatedDesc = "Revised resource collection execution scope tracking.";
        double updatedTargetAmount = 25000.00;

        String serviceResult = updateActivityService.updateActivity(
            targetActivityId, 
            updatedName, 
            updatedDesc, 
            updatedTargetAmount
        );

        // 2. Equation: Verify execution state sequence returns structural confirmation
        assertNotNull(serviceResult, "Service message payload response must not return null");
        
        // Check clean alternate handling logic scenario fallback routing path
        String edgeCaseResult = updateActivityService.updateActivity(9999, updatedName, updatedDesc, updatedTargetAmount);
        assertEquals("Activity not found.", edgeCaseResult, "System validation layer must reject non-existent entity targets");
    }

    /**
     * Service Check 4: Core Rotating FSA Category Description Parameters Modification
     */
    @Test
    public void testUpdateFSACategoryServiceLogic() {
        // 1. Action: Modify localized unique tracking category descriptors using target index ID 1
        int targetCategoryId = 1;
        String updatedCatName = "Advanced Medical Care";
        String updatedCatDesc = "Primary coverage parameters handling emergency operation assistance and clinical distributions.";

        String serviceResult = updateFSACategoryService.updateFSACategory(
            targetCategoryId, 
            updatedCatName, 
            updatedCatDesc
        );

        // 2. Equation: Assert state modification script processes string validation sequences correctly
        assertNotNull(serviceResult, "Category update pipeline status string tracking response must not return null");
        
        // 3. Negative validation edge boundary verification rules checklist check
        String invalidInputResult = updateFSACategoryService.updateFSACategory(targetCategoryId, "", updatedCatDesc);
        assertEquals("Failed to update FSA Category. Please check and try again.", invalidInputResult, 
            "The update validation layer must flag block tracking processing on blank parameters text strings");
    }
}