package com.FRA.login_system.assertiontest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Import all required entities used for validation checking logic rules
import com.FRA.login_system.entity.UserAccount;
import com.FRA.login_system.entity.UserProfile;
import com.FRA.login_system.entity.Activity;
import com.FRA.login_system.entity.FRA;
import com.FRA.login_system.entity.Listing;
import com.FRA.login_system.entity.FSACategory;
import com.FRA.login_system.entity.Donation;
import com.FRA.login_system.entity.SavedListing;

// Import all distinct view service layer scripts
import com.FRA.login_system.service.UserAccountService.ViewUserAccountService;
import com.FRA.login_system.service.UserProfileService.ViewUserProfileService;
import com.FRA.login_system.service.ActivityService.ViewActivityService;
import com.FRA.login_system.service.DonationService.ViewDonationHistoryService;
import com.FRA.login_system.service.FRAService.ViewCompletedFRAService;
import com.FRA.login_system.service.FRAService.ViewFRAListService;
import com.FRA.login_system.service.FRAService.ViewFRAService;
import com.FRA.login_system.service.FSACategoryService.ViewFSACategoryService;
import com.FRA.login_system.service.ListingService.ViewListingsService;
import com.FRA.login_system.service.ListingService.ViewSavedListingsService;

@SpringBootTest
public class ViewLogicTest {

    @Autowired
    private ViewUserAccountService viewAccountService;

    @Autowired
    private ViewUserProfileService viewProfileService;

    @Autowired
    private ViewActivityService viewActivityService;

    @Autowired
    private ViewDonationHistoryService viewDonationHistoryService;

    @Autowired
    private ViewCompletedFRAService viewCompletedFRAService;

    @Autowired
    private ViewFRAListService viewFRAListService;

    @Autowired
    private ViewFRAService viewFRAService;

    @Autowired
    private ViewFSACategoryService viewFSACategoryService;

    @Autowired
    private ViewListingsService viewListingsService;

    @Autowired
    private ViewSavedListingsService viewSavedListingsService;

    // =========================================================================
    // SECTION 1: USER ACCOUNT & PROFILE VIEW TESTS
    // =========================================================================

    /**
     * Account View Check: Validates retrieval of a known, existing user account by ID.
     */
    @Test
    public void testGetExistingUserAccount() {
        int testId = 1; 
        UserAccount account = viewAccountService.getUser(testId);

        assertNotNull(account, "Account entry should not return null for valid ID 1");
        assertEquals(testId, account.getId(), "Returned Account ID must accurately map request index");
        assertNotNull(account.getProfile(), "The linked profile metadata object should load eagerly with account structure");
    }

    /**
     * Account View Check: Validates fallback logic when looking up a missing account ID.
     */
    @Test
    public void testGetNonExistentUserAccount() {
        UserAccount account = viewAccountService.getUser(9999);
        assertNull(account, "Should return null fallback response configuration for non-existent ID 9999");
    }

    /**
     * Profile View Check: Validates that the entire system role permissions manifest can be retrieved.
     */
    @Test
    public void testGetAllUserProfiles() {
        List<UserProfile> profiles = viewProfileService.getAllUserProfiles();

        assertNotNull(profiles, "Profile list tracking collection layout container must not be null");
        assertFalse(profiles.isEmpty(), "Profile configuration manifest data table should not load empty");
        
        boolean hasAdmin = profiles.stream()
                                   .anyMatch(p -> p.getRole().equals("USER_ADMIN"));
        assertTrue(hasAdmin, "Profile listing matrix should contain the system default 'USER_ADMIN' role configuration");
    }

    // =========================================================================
    // SECTION 2: ACTIVITY VIEW TESTS
    // =========================================================================

    /**
     * Activity View Check: Asserts system campaign tracking logs load as collections correctly.
     */
    @Test
    public void testViewActivityServiceLogic() {
        List<Activity> activities = viewActivityService.viewActivity();
        
        assertNotNull(activities, "Activity repository data lookup result container must not be null");
        // Checked elements should populate via standard system initial dynamic database table seeding
    }

    // =========================================================================
    // SECTION 3: DONATION HISTORY VIEW TESTS
    // =========================================================================

    /**
     * Donation View Check: Validates that global transactional donation histories map cleanly.
     */
    @Test
    public void testViewDonationHistoryServiceLogic() {
        List<Donation> history = viewDonationHistoryService.getDonationHistory();
        
        assertNotNull(history, "Donation financial history logging table view tracking should not be null");
    }

    // =========================================================================
    // SECTION 4: FRA (FUND RAISING ASSIGNMENT) VIEW TESTS
    // =========================================================================

    /**
     * FRA List View Check: Validates retrieval of total tracking data listings arrays.
     */
    @Test
    public void testViewFRAListServiceLogic() {
        List<FRA> fraList = viewFRAListService.getFRAList();
        
        assertNotNull(fraList, "Core asset registry structural folder collection must not be null");
    }

    /**
     * FRA Info View Check: Asserts string formatting responses behave on metadata entity lookups.
     */
    @Test
    public void testViewFRAServiceLogic() {
        int validFraId = 1;
        String infoResult = viewFRAService.getFRAInfo(validFraId);
        
        assertNotNull(infoResult, "Individual asset textual data response layout must not return null");
        
        // Negative test validation edge routing path
        String missingResult = viewFRAService.getFRAInfo(9999);
        assertEquals("FRA not found.", missingResult, "System index boundary configuration error message matching failed");
    }

    /**
     * Completed FRA View Check: Verifies history layout data matches formatting standards.
     */
    @Test
    public void testViewCompletedFRAServiceLogic() {
        int targetFraId = 1;
        String executionResult = viewCompletedFRAService.getFRADonationHistory(targetFraId);
        
        assertNotNull(executionResult, "Asset completed donation log tracking matrix summary text payload must not be null");
        
        // Validate clean alternate handling execution scenarios boundary block
        String missingResult = viewCompletedFRAService.getFRADonationHistory(9999);
        assertEquals("No Matches Found.", missingResult, "System logic failed to provide alternate fallback error string sequence validation tracking");
    }

    // =========================================================================
    // SECTION 5: FSA CATEGORY VIEW TESTS
    // =========================================================================

    /**
     * FSA Category View Check: Validates system database tables load active dynamic routing categories.
     */
    @Test
    public void testViewFSACategoryServiceLogic() {
        List<FSACategory> categories = viewFSACategoryService.getFSACategories();
        
        assertNotNull(categories, "FSA Category system metadata configuration directory structure lookup layer container should not load null");
    }

    // =========================================================================
    // SECTION 6: LISTINGS & SAVED LISTINGS VIEW TESTS
    // =========================================================================

    /**
     * Global Listings View Check: Asserts structural properties match core data layer models.
     */
    @Test
    public void testViewListingsServiceLogic() {
        List<Listing> listings = viewListingsService.getListings();
        assertNotNull(listings, "Global marketing listing data block elements array collection framework must not return null");
        
        // Assert individual detail string mapping behavior paths
        String validDetails = viewListingsService.getListingDetails(1);
        assertNotNull(validDetails, "Details summary string payload text trace tracking asset check must not be null");
        
        String invalidDetails = viewListingsService.getListingDetails(9999);
        assertEquals("Listing not found.", invalidDetails, "System detail retrieval process layer framework did not cleanly identify out-of-bounds metrics path");
    }

    /**
     * Saved Listings View Check: Validates bookmarks tracking registry layers load and safely route.
     */
    @Test
    public void testViewSavedListingsServiceLogic() {
        List<SavedListing> savedListings = viewSavedListingsService.getSavedListings();
        assertNotNull(savedListings, "User short-listed dashboard tracking bookmarks collection array framework must not return null");
        
        // Verify detail resolution sequence path parameters behavior 
        String alternateDetails = viewSavedListingsService.getListingDetails(9999);
        assertEquals("Saved listing not found.", alternateDetails, "Fallback boundary handling validation layer logic matching tracking failed to flag block tracking processing on missing bookmark items");
    }
}